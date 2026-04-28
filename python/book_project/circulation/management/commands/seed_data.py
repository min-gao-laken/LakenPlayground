import random
from datetime import timedelta
from decimal import Decimal

from django.core.management.base import BaseCommand
from django.db import transaction
from django.utils import timezone

from circulation.models import Loan, Reader
from manager.models import Author, Book, BorrowRecord, Manager, Publisher


class Command(BaseCommand):
    help = 'Seed demo data for local development.'

    def add_arguments(self, parser):
        parser.add_argument('--books', type=int, default=30, help='Number of books to create')
        parser.add_argument('--publishers', type=int, default=5, help='Number of publishers to create')
        parser.add_argument('--authors', type=int, default=12, help='Number of authors to create')
        parser.add_argument('--readers', type=int, default=20, help='Number of readers to create')
        parser.add_argument('--loans', type=int, default=40, help='Number of loans to create')
        parser.add_argument('--borrow-records', type=int, default=60, help='Number of borrow records to create')
        parser.add_argument('--manager-number', type=str, default='admin001', help='Manager account number')
        parser.add_argument('--manager-password', type=str, default='123456', help='Manager account password')

    @transaction.atomic
    def handle(self, *args, **options):
        book_count = max(options['books'], 0)
        publisher_count = max(options['publishers'], 1)
        author_count = max(options['authors'], 0)
        reader_count = max(options['readers'], 0)
        loan_count = max(options['loans'], 0)
        borrow_record_count = max(options['borrow_records'], 0)

        manager_obj, manager_created = Manager.objects.get_or_create(
            number=options['manager_number'],
            defaults={
                'password': options['manager_password'],
                'name': 'Admin User',
            },
        )

        if not manager_created:
            manager_obj.password = options['manager_password']
            manager_obj.name = manager_obj.name or 'Admin User'
            manager_obj.save(update_fields=['password', 'name'])

        publishers = []
        for index in range(1, publisher_count + 1):
            publisher, _ = Publisher.objects.get_or_create(
                publisher_name=f'Seed Publisher {index}',
                defaults={'publisher_address': f'Seed Address {index}'},
            )
            publishers.append(publisher)

        books = []
        for index in range(1, book_count + 1):
            default_price = Decimal(str(round(random.uniform(19.9, 99.9), 2)))
            book, created = Book.objects.get_or_create(
                name=f'Seed Book {index}',
                defaults={
                    'price': default_price,
                    'inventory': random.randint(10, 120),
                    'sale_num': random.randint(0, 500),
                    'publisher': random.choice(publishers),
                },
            )
            if not created:
                changed = False
                if book.publisher_id is None:
                    book.publisher = random.choice(publishers)
                    changed = True
                if book.inventory < 1:
                    book.inventory = random.randint(10, 120)
                    changed = True
                if changed:
                    book.save()
            books.append(book)

        authors = []
        if books:
            for index in range(1, author_count + 1):
                author, _ = Author.objects.get_or_create(name=f'Seed Author {index}')
                sample_size = min(len(books), random.randint(1, min(4, len(books))))
                author.book.set(random.sample(books, k=sample_size))
                authors.append(author)

        readers = []
        for index in range(1, reader_count + 1):
            reader, _ = Reader.objects.get_or_create(
                phone=f'1390000{index:04d}',
                defaults={'name': f'Seed Reader {index}'},
            )
            readers.append(reader)

        created_loans = 0
        if books and readers:
            today = timezone.localdate()
            for _ in range(loan_count):
                book = random.choice(books)
                reader = random.choice(readers)

                borrow_date = today - timedelta(days=random.randint(1, 30))
                due_date = borrow_date + timedelta(days=random.randint(7, 21))
                returned = random.random() < 0.45
                status = Loan.STATUS_RETURNED if returned else Loan.STATUS_BORROWED
                return_date = due_date - timedelta(days=random.randint(0, 5)) if returned else None

                Loan.objects.create(
                    reader=reader,
                    book=book,
                    borrow_date=borrow_date,
                    due_date=due_date,
                    return_date=return_date,
                    status=status,
                )
                created_loans += 1

        created_borrow_records = 0
        if books:
            today = timezone.localdate()
            for _ in range(borrow_record_count):
                BorrowRecord.objects.create(
                    book=random.choice(books),
                    borrowed_on=today - timedelta(days=random.randint(0, 29)),
                    quantity=random.randint(1, 4),
                )
                created_borrow_records += 1

        self.stdout.write(self.style.SUCCESS('seed_data completed'))
        self.stdout.write(
            f"manager={Manager.objects.count()} publishers={Publisher.objects.count()} books={Book.objects.count()} "
            f"authors={Author.objects.count()} readers={Reader.objects.count()} loans(+{created_loans}) borrow_records(+{created_borrow_records})"
        )
