def publisher_to_dict(publisher):
    return {
        'id': publisher.id,
        'publisher_name': publisher.publisher_name,
        'publisher_address': publisher.publisher_address,
    }


def book_to_dict(book):
    return {
        'id': book.id,
        'name': book.name,
        'price': str(book.price),
        'inventory': book.inventory,
        'sale_num': book.sale_num,
        'publisher_id': book.publisher_id,
        'publisher_name': book.publisher.publisher_name if book.publisher_id else None,
    }


def author_to_dict(author):
    return {
        'id': author.id,
        'name': author.name,
        'book_ids': list(author.book.values_list('id', flat=True)),
        'book_names': list(author.book.values_list('name', flat=True)),
    }


def borrow_record_to_dict(record):
    return {
        'id': record.id,
        'book_id': record.book_id,
        'book_name': record.book.name if record.book_id else None,
        'borrowed_on': record.borrowed_on.isoformat(),
        'quantity': record.quantity,
    }
