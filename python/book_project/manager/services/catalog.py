from manager.models import Author, Book, BorrowRecord, Publisher


def list_publishers():
    return Publisher.objects.all()


def get_publisher(publisher_id):
    return Publisher.objects.filter(id=publisher_id).first()


def create_publisher(publisher_name, publisher_address):
    return Publisher.objects.create(
        publisher_name=publisher_name,
        publisher_address=publisher_address,
    )


def update_publisher(publisher, publisher_name, publisher_address):
    publisher.publisher_name = publisher_name
    publisher.publisher_address = publisher_address
    publisher.save()
    return publisher


def delete_publisher(publisher):
    publisher.delete()


def list_books():
    return Book.objects.select_related('publisher').all()


def get_book(book_id):
    return Book.objects.select_related('publisher').filter(id=book_id).first()


def create_book(name, price, inventory, sale_num, publisher_id):
    book = Book.objects.create(
        name=name,
        price=price,
        inventory=inventory,
        sale_num=sale_num,
        publisher_id=publisher_id,
    )
    return get_book(book.id)


def update_book(book, name, price, inventory, sale_num, publisher_id):
    book.name = name
    book.price = price
    book.inventory = inventory
    book.sale_num = sale_num
    book.publisher_id = publisher_id
    book.save()
    return get_book(book.id)


def delete_book(book):
    book.delete()


def list_authors():
    return Author.objects.prefetch_related('book').all()


def get_author(author_id):
    return Author.objects.prefetch_related('book').filter(id=author_id).first()


def get_author_book_ids(author):
    return list(author.book.values_list('id', flat=True))


def create_author(name, book_ids=()):
    author = Author.objects.create(name=name)
    if book_ids:
        author.book.set(book_ids)
    return get_author(author.id)


def update_author(author, name, book_ids=()):
    author.name = name
    author.save()
    author.book.set(book_ids)
    return get_author(author.id)


def delete_author(author):
    author.delete()


def list_borrow_records(limit=100):
    return BorrowRecord.objects.select_related('book').all()[:limit]


def get_borrow_record(record_id):
    return BorrowRecord.objects.select_related('book').filter(id=record_id).first()


def create_borrow_record(book_id, quantity, borrowed_on):
    record = BorrowRecord.objects.create(
        book_id=book_id,
        quantity=quantity,
        borrowed_on=borrowed_on,
    )
    return get_borrow_record(record.id)


def delete_borrow_record(record):
    record.delete()
