import math

from django.db.models import Count

from circulation.models import Loan, Reader
from manager.models import Book


ALGORITHM_NAME = 'user_cf_cosine_implicit'


def _reader_book_matrix():
    interactions = Loan.objects.values_list('reader_id', 'book_id')
    reader_to_books = {}
    for reader_id, book_id in interactions:
        reader_to_books.setdefault(reader_id, set()).add(book_id)
    return reader_to_books


def _cosine_similarity(first_books, second_books):
    if not first_books or not second_books:
        return 0.0
    overlap = len(first_books.intersection(second_books))
    if overlap == 0:
        return 0.0
    return overlap / math.sqrt(len(first_books) * len(second_books))


def _popular_books(limit, exclude_book_ids):
    exclude_book_ids = exclude_book_ids or set()
    queryset = (
        Book.objects.exclude(id__in=exclude_book_ids)
        .annotate(loan_count=Count('loans'))
        .order_by('-loan_count', '-sale_num', 'id')[:limit]
    )
    return [
        {
            'book_id': book.id,
            'book_name': book.name,
            'score': float(book.loan_count),
            'reason': 'popular_fallback',
        }
        for book in queryset
    ]


def get_recommendations(reader_id, top_k_users, limit):
    if not Reader.objects.filter(id=reader_id).exists():
        return None

    reader_to_books = _reader_book_matrix()
    target_books = reader_to_books.get(reader_id, set())

    if not target_books:
        return {
            'reader_id': reader_id,
            'algorithm': ALGORITHM_NAME,
            'results': _popular_books(limit=limit, exclude_book_ids=set()),
        }

    similarities = []
    for other_reader_id, other_books in reader_to_books.items():
        if other_reader_id == reader_id:
            continue
        similarity = _cosine_similarity(target_books, other_books)
        if similarity > 0:
            similarities.append((other_reader_id, similarity))

    similarities.sort(key=lambda row: (-row[1], row[0]))
    neighbors = similarities[:top_k_users]

    score_map = {}
    support_map = {}
    for neighbor_reader_id, similarity in neighbors:
        for book_id in reader_to_books.get(neighbor_reader_id, set()):
            if book_id in target_books:
                continue
            score_map[book_id] = score_map.get(book_id, 0.0) + similarity
            support_map[book_id] = support_map.get(book_id, 0) + 1

    if not score_map:
        return {
            'reader_id': reader_id,
            'algorithm': ALGORITHM_NAME,
            'results': _popular_books(limit=limit, exclude_book_ids=target_books),
        }

    ranked = sorted(
        score_map.items(),
        key=lambda row: (-row[1], -support_map[row[0]], row[0]),
    )[:limit]
    book_ids = [book_id for book_id, _score in ranked]
    books = Book.objects.filter(id__in=book_ids).values('id', 'name')
    book_name_map = {book['id']: book['name'] for book in books}

    results = []
    for book_id, score in ranked:
        if book_id not in book_name_map:
            continue
        results.append(
            {
                'book_id': book_id,
                'book_name': book_name_map[book_id],
                'score': round(float(score), 6),
                'neighbor_support': support_map[book_id],
                'reason': 'collaborative_filtering',
            }
        )

    return {
        'reader_id': reader_id,
        'algorithm': ALGORITHM_NAME,
        'neighbors_used': len(neighbors),
        'results': results,
    }
