from django.db import transaction
import json
import math
import csv
# Create your views here.
from django.http import JsonResponse, HttpResponse
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt
from django.db.models import Count

from circulation.models import Reader, Loan
from manager.models import Book, Manager


def ping(request):
    return JsonResponse({"module": "circulation", "ok": True})


def _require_any_role(request, roles):
    if not request.session.get('manager_id'):
        return JsonResponse({'detail': 'Authentication required'}, status=401)
    if request.session.get('role') not in roles:
        return JsonResponse({'detail': 'Permission denied', 'required_roles': list(roles)}, status=403)
    return None


@csrf_exempt
def borrow_book(request):
    denied = _require_any_role(request, [Manager.Role.ADMIN])
    if denied:
        return denied

    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    try:
        payload = json.loads(request.body.decode("utf-8"))
    except json.JSONDecodeError:
        return JsonResponse({"detail": "Invalid JSON body"}, status=400)

    reader_id = payload.get("reader_id")
    book_id = payload.get("book_id")
    due_date_str = payload.get("due_date")  # 渚嬪 "2026-03-20"

    if not all([reader_id, book_id, due_date_str]):
        return JsonResponse({"detail": "Missing required fields"}, status=400)
    try:
        due_date = timezone.datetime.fromisoformat(due_date_str).date()
    except ValueError:
        return JsonResponse({"detail": "Invalid due_date format, use YYYY-MM-DD"}, status=400)

    reader = Reader.objects.filter(id=reader_id).first()
    if not reader:
        return JsonResponse({"detail": "Reader not found"}, status=404)

    with transaction.atomic():
        # 閿佽锛岄伩鍏嶅苟鍙戝€熶功鎶婂簱瀛樺€熸垚璐熸暟
        book = Book.objects.select_for_update().filter(id=book_id).first()
        if not book:
            return JsonResponse({"detail": "Book not found"}, status=404)

        if book.inventory <= 0:
            return JsonResponse({"detail": "No inventory left"}, status=400)
        loan = Loan.objects.create(
            reader=reader,
            book=book,
            borrow_date=timezone.localdate(),
            due_date=due_date,
            status=Loan.STATUS_BORROWED,
        )

        book.inventory -= 1
        book.save(update_fields=["inventory"])

    return JsonResponse(
        {
            "id": loan.id,
            "reader_id": loan.reader_id,
            "book_id": loan.book_id,
            "status": loan.status,
            "borrow_date": loan.borrow_date.isoformat(),
            "due_date": loan.due_date.isoformat(),
        },
        status=201,
    )


@csrf_exempt
@transaction.atomic
def return_book(request, loan_id):
    denied = _require_any_role(request, [Manager.Role.ADMIN])
    if denied:
        return denied

    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)
    # 閿佷綇鍊熼槄鍗曪紝閬垮厤骞跺彂閲嶅杩樹功
    loan = Loan.objects.select_for_update().filter(id=loan_id).first()
    if not loan:
        return JsonResponse({"detail": "Loan not found"}, status=404)

    if loan.status == Loan.STATUS_RETURNED:
        return JsonResponse({"detail": "Already returned"}, status=400)
        # 閿佷綇鍥句功骞跺洖琛ュ簱瀛?    book = Book.objects.select_for_update().filter(id=loan.book_id).first()
    if not book:
        return JsonResponse({"detail": "Book not found"}, status=404)

    loan.status = Loan.STATUS_RETURNED
    loan.return_date = timezone.localdate()
    loan.save(update_fields=["status", "return_date"])

    book.inventory += 1
    book.save(update_fields=["inventory"])

    return JsonResponse(
        {
            "id": loan.id,
            "status": loan.status,
            "return_date": loan.return_date.isoformat(),
            "book_id": loan.book_id,
            "book_inventory": book.inventory,
        }
    )


def _reader_book_matrix():
    interactions = Loan.objects.values_list("reader_id", "book_id")
    reader_to_books = {}
    for reader_id, book_id in interactions:
        reader_to_books.setdefault(reader_id, set()).add(book_id)
    return reader_to_books

# Cosine Similarity
def _cosine_similarity(a_books, b_books):
    if not a_books or not b_books:
        return 0.0
    overlap = len(a_books.intersection(b_books))
    if overlap == 0:
        return 0.0
    return overlap / math.sqrt(len(a_books) * len(b_books))


def _popular_books(limit, exclude_book_ids):
    exclude_book_ids = exclude_book_ids or set()
    queryset = (
        Book.objects.exclude(id__in=exclude_book_ids)
        .annotate(loan_count=Count("loans"))
        .order_by("-loan_count", "-sale_num", "id")[:limit]
    )
    return [
        {
            "book_id": book.id,
            "book_name": book.name,
            "score": float(book.loan_count),
            "reason": "popular_fallback",
        }
        for book in queryset
    ]


def _build_recommendation_payload(reader_id, top_k_users, limit):
    if not Reader.objects.filter(id=reader_id).exists():
        return None

    reader_to_books = _reader_book_matrix()
    target_books = reader_to_books.get(reader_id, set())

    # Cold start: the current reader has no history.
    if not target_books:
        return {
            "reader_id": reader_id,
            "algorithm": "user_cf_cosine_implicit",
            "results": _popular_books(limit=limit, exclude_book_ids=set()),
        }

    similarities = []
    for other_reader_id, other_books in reader_to_books.items():
        if other_reader_id == reader_id:
            continue
        sim = _cosine_similarity(target_books, other_books)
        if sim > 0:
            similarities.append((other_reader_id, sim))

    similarities.sort(key=lambda row: (-row[1], row[0]))
    neighbors = similarities[:top_k_users]

    score_map = {}
    support_map = {}
    for neighbor_reader_id, sim in neighbors:
        for book_id in reader_to_books.get(neighbor_reader_id, set()):
            if book_id in target_books:
                continue
            score_map[book_id] = score_map.get(book_id, 0.0) + sim
            support_map[book_id] = support_map.get(book_id, 0) + 1

    if not score_map:
        return {
            "reader_id": reader_id,
            "algorithm": "user_cf_cosine_implicit",
            "results": _popular_books(limit=limit, exclude_book_ids=target_books),
        }

    ranked = sorted(score_map.items(), key=lambda row: (-row[1], -support_map[row[0]], row[0]))[:limit]
    book_ids = [book_id for book_id, _score in ranked]
    books = Book.objects.filter(id__in=book_ids).values("id", "name")
    book_name_map = {book["id"]: book["name"] for book in books}

    results = []
    for book_id, score in ranked:
        if book_id not in book_name_map:
            continue
        results.append(
            {
                "book_id": book_id,
                "book_name": book_name_map[book_id],
                "score": round(float(score), 6),
                "neighbor_support": support_map[book_id],
                "reason": "collaborative_filtering",
            }
        )

    return {
        "reader_id": reader_id,
        "algorithm": "user_cf_cosine_implicit",
        "neighbors_used": len(neighbors),
        "results": results,
    }


def recommend_books(request, reader_id):
    denied = _require_any_role(request, [Manager.Role.ADMIN, Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method != "GET":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    if not Reader.objects.filter(id=reader_id).exists():
        return JsonResponse({"detail": "Reader not found"}, status=404)

    try:
        top_k_users = max(1, min(int(request.GET.get("top_k_users", 10)), 100))
    except ValueError:
        top_k_users = 10
    try:
        limit = max(1, min(int(request.GET.get("limit", 10)), 50))
    except ValueError:
        limit = 10

    payload = _build_recommendation_payload(reader_id=reader_id, top_k_users=top_k_users, limit=limit)
    return JsonResponse(payload)


def export_recommendations_csv(request, reader_id):
    denied = _require_any_role(request, [Manager.Role.ADMIN, Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method != "GET":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    if not Reader.objects.filter(id=reader_id).exists():
        return JsonResponse({"detail": "Reader not found"}, status=404)

    try:
        top_k_users = max(1, min(int(request.GET.get("top_k_users", 10)), 100))
    except ValueError:
        top_k_users = 10
    try:
        limit = max(1, min(int(request.GET.get("limit", 10)), 50))
    except ValueError:
        limit = 10

    payload = _build_recommendation_payload(reader_id=reader_id, top_k_users=top_k_users, limit=limit)
    response = HttpResponse(content_type="text/csv; charset=utf-8")
    response["Content-Disposition"] = f'attachment; filename="reader_{reader_id}_recommendations.csv"'
    response.write("\ufeff")

    writer = csv.writer(response)
    writer.writerow(["reader_id", "algorithm", "book_id", "book_name", "score", "neighbor_support", "reason"])
    for item in payload.get("results", []):
        writer.writerow(
            [
                payload.get("reader_id"),
                payload.get("algorithm"),
                item.get("book_id"),
                item.get("book_name"),
                item.get("score"),
                item.get("neighbor_support", ""),
                item.get("reason"),
            ]
        )

    return response

