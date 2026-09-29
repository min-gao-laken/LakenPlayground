from django.db import transaction
from django.utils import timezone

from circulation.models import Loan, Reader
from manager.models import Book


class CirculationError(Exception):
    def __init__(self, detail, status_code):
        super().__init__(detail)
        self.detail = detail
        self.status_code = status_code


@transaction.atomic
def borrow_book(reader_id, book_id, due_date):
    reader = Reader.objects.filter(id=reader_id).first()
    if not reader:
        raise CirculationError('Reader not found', 404)

    book = Book.objects.select_for_update().filter(id=book_id).first()
    if not book:
        raise CirculationError('Book not found', 404)

    if book.inventory <= 0:
        raise CirculationError('No inventory left', 400)

    loan = Loan.objects.create(
        reader=reader,
        book=book,
        borrow_date=timezone.localdate(),
        due_date=due_date,
        status=Loan.STATUS_BORROWED,
    )
    book.inventory -= 1
    book.save(update_fields=['inventory'])
    return loan


@transaction.atomic
def return_loan(loan_id):
    loan = Loan.objects.select_for_update().filter(id=loan_id).first()
    if not loan:
        raise CirculationError('Loan not found', 404)

    if loan.status == Loan.STATUS_RETURNED:
        raise CirculationError('Already returned', 400)

    book = Book.objects.select_for_update().filter(id=loan.book_id).first()
    if not book:
        raise CirculationError('Book not found', 404)

    loan.status = Loan.STATUS_RETURNED
    loan.return_date = timezone.localdate()
    loan.save(update_fields=['status', 'return_date'])

    book.inventory += 1
    book.save(update_fields=['inventory'])
    return loan, book
