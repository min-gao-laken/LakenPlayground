from datetime import timedelta

from django.db.models import Sum
from django.db.models.functions import Coalesce
from django.utils import timezone

from manager.models import Book, BorrowRecord


def get_analytics_overview(days):
    end_date = timezone.localdate()
    start_date = end_date - timedelta(days=days - 1)

    top_books = [
        {
            'id': book.id,
            'name': book.name,
            'borrow_count': int(book.borrow_count or 0),
            'sale_num': int(book.sale_num or 0),
        }
        for book in Book.objects.annotate(
            borrow_count=Coalesce(Sum('borrow_records__quantity'), 0),
        ).order_by('-borrow_count', '-sale_num', 'id')[:8]
    ]

    trend_rows = (
        BorrowRecord.objects.filter(
            borrowed_on__gte=start_date, borrowed_on__lte=end_date)
        .values('borrowed_on')
        .annotate(total=Coalesce(Sum('quantity'), 0))
        .order_by('borrowed_on')
    )
    trend_map = {row['borrowed_on']: int(
        row['total'] or 0) for row in trend_rows}

    borrow_trend = []
    total_borrowed = 0
    for offset in range(days):
        current_date = start_date + timedelta(days=offset)
        value = trend_map.get(current_date, 0)
        total_borrowed += value
        borrow_trend.append(
            {'date': current_date.isoformat(), 'borrow_count': value})

    return {
        'top_books': top_books,
        'borrow_trend': borrow_trend,
        'summary': {
            'days': days,
            'total_borrowed': total_borrowed,
            'records': BorrowRecord.objects.count(),
        },
    }
