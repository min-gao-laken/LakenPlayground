from django.db import models
from django.utils import timezone
from manager.models import Book


# Create your models here.

class Reader(models.Model):
    name = models.CharField(max_length=32)
    phone = models.CharField(max_length=32)

    class Meta:
        db_table = 'reader'


class Loan(models.Model):
    STATUS_BORROWED = "borrowed"
    STATUS_RETURNED = "returned"
    STATUS_CHOICES = [
        (STATUS_BORROWED, "Borrowed"),
        (STATUS_RETURNED, "Returned"),
    ]

    reader = models.ForeignKey(Reader, on_delete=models.CASCADE, related_name="loans")
    book = models.ForeignKey(Book, on_delete=models.CASCADE, related_name="loans")

    borrow_date = models.DateField(default=timezone.localdate)
    due_date = models.DateField()
    return_date = models.DateField(null=True, blank=True)

    status = models.CharField(max_length=20, choices=STATUS_CHOICES, default=STATUS_BORROWED)

    class Meta:
        db_table = "loan"

    def __str__(self):
        return f"Loan<{self.id}> {self.reader_id}-{self.book_id} {self.status}"