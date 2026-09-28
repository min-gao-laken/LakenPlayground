from django.http import JsonResponse
from django.utils import timezone

from manager.models import Manager
from manager.services import catalog as catalog_service
from manager.views.base import RoleRequiredJsonView
from manager.views.serializers import (
    author_to_dict,
    book_to_dict,
    borrow_record_to_dict,
    publisher_to_dict,
)


READ_ROLES = (Manager.Role.ADMIN, Manager.Role.ANALYST)
ADMIN_ONLY = (Manager.Role.ADMIN,)


class PublisherCollectionView(RoleRequiredJsonView):
    role_permissions = {'GET': READ_ROLES, 'POST': ADMIN_ONLY}

    def get(self, request):
        publishers = catalog_service.list_publishers()
        return JsonResponse({'results': [publisher_to_dict(item) for item in publishers]})

    def post(self, request):
        payload, error = self.parse_json_body(request)
        if error:
            return error

        publisher_name = payload.get('publisher_name')
        publisher_address = payload.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        publisher = catalog_service.create_publisher(
            publisher_name, publisher_address)
        return JsonResponse(publisher_to_dict(publisher), status=201)


class PublisherDetailView(RoleRequiredJsonView):
    role_permissions = {'PUT': ADMIN_ONLY, 'DELETE': ADMIN_ONLY}

    def put(self, request, publisher_id):
        publisher = catalog_service.get_publisher(publisher_id)
        if not publisher:
            return JsonResponse({'detail': 'Publisher not found'}, status=404)

        payload, error = self.parse_json_body(request)
        if error:
            return error

        publisher_name = payload.get('publisher_name')
        publisher_address = payload.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        publisher = catalog_service.update_publisher(
            publisher,
            publisher_name,
            publisher_address,
        )
        return JsonResponse(publisher_to_dict(publisher))

    def delete(self, request, publisher_id):
        publisher = catalog_service.get_publisher(publisher_id)
        if not publisher:
            return JsonResponse({'detail': 'Publisher not found'}, status=404)
        catalog_service.delete_publisher(publisher)
        return JsonResponse({}, status=204)


class BookCollectionView(RoleRequiredJsonView):
    role_permissions = {'GET': READ_ROLES, 'POST': ADMIN_ONLY}

    def get(self, request):
        books = catalog_service.list_books()
        return JsonResponse({'results': [book_to_dict(item) for item in books]})

    def post(self, request):
        payload, error = self.parse_json_body(request)
        if error:
            return error

        name = payload.get('name')
        price = payload.get('price')
        inventory = payload.get('inventory')
        sale_num = payload.get('sale_num', 0)
        publisher_id = payload.get('publisher_id')
        if not all([name, price, inventory, publisher_id]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        book = catalog_service.create_book(
            name, price, inventory, sale_num, publisher_id)
        return JsonResponse(book_to_dict(book), status=201)


class BookDetailView(RoleRequiredJsonView):
    role_permissions = {'PUT': ADMIN_ONLY, 'DELETE': ADMIN_ONLY}

    def put(self, request, book_id):
        book = catalog_service.get_book(book_id)
        if not book:
            return JsonResponse({'detail': 'Book not found'}, status=404)

        payload, error = self.parse_json_body(request)
        if error:
            return error

        name = payload.get('name')
        price = payload.get('price')
        inventory = payload.get('inventory')
        sale_num = payload.get('sale_num', 0)
        publisher_id = payload.get('publisher_id')
        if not all([name, price, inventory, publisher_id]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        book = catalog_service.update_book(
            book,
            name,
            price,
            inventory,
            sale_num,
            publisher_id,
        )
        return JsonResponse(book_to_dict(book))

    def delete(self, request, book_id):
        book = catalog_service.get_book(book_id)
        if not book:
            return JsonResponse({'detail': 'Book not found'}, status=404)
        catalog_service.delete_book(book)
        return JsonResponse({}, status=204)


class AuthorCollectionView(RoleRequiredJsonView):
    role_permissions = {'GET': READ_ROLES, 'POST': ADMIN_ONLY}

    def get(self, request):
        authors = catalog_service.list_authors()
        return JsonResponse({'results': [author_to_dict(item) for item in authors]})

    def post(self, request):
        payload, error = self.parse_json_body(request)
        if error:
            return error

        name = payload.get('name')
        book_ids = payload.get('book_ids', [])
        if not name:
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        author = catalog_service.create_author(name, book_ids)
        return JsonResponse(author_to_dict(author), status=201)


class AuthorDetailView(RoleRequiredJsonView):
    role_permissions = {'PUT': ADMIN_ONLY, 'DELETE': ADMIN_ONLY}

    def put(self, request, author_id):
        author = catalog_service.get_author(author_id)
        if not author:
            return JsonResponse({'detail': 'Author not found'}, status=404)

        payload, error = self.parse_json_body(request)
        if error:
            return error

        name = payload.get('name')
        book_ids = payload.get('book_ids', [])
        if not name:
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        author = catalog_service.update_author(author, name, book_ids)
        return JsonResponse(author_to_dict(author))

    def delete(self, request, author_id):
        author = catalog_service.get_author(author_id)
        if not author:
            return JsonResponse({'detail': 'Author not found'}, status=404)
        catalog_service.delete_author(author)
        return JsonResponse({}, status=204)


class BorrowRecordCollectionView(RoleRequiredJsonView):
    role_permissions = {'GET': READ_ROLES, 'POST': ADMIN_ONLY}

    def get(self, request):
        records = catalog_service.list_borrow_records()
        return JsonResponse({'results': [borrow_record_to_dict(item) for item in records]})

    def post(self, request):
        payload, error = self.parse_json_body(request)
        if error:
            return error

        book_id = payload.get('book_id')
        if not book_id:
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        book = catalog_service.get_book(book_id)
        if not book:
            return JsonResponse({'detail': 'Book not found'}, status=404)

        quantity = payload.get('quantity', 1)
        try:
            quantity = max(int(quantity), 1)
        except (TypeError, ValueError):
            return JsonResponse({'detail': 'Invalid quantity'}, status=400)

        borrowed_on = payload.get('borrowed_on')
        if borrowed_on:
            try:
                borrowed_on = timezone.datetime.fromisoformat(
                    borrowed_on).date()
            except (TypeError, ValueError):
                return JsonResponse({'detail': 'Invalid borrowed_on format'}, status=400)
        else:
            borrowed_on = timezone.localdate()

        record = catalog_service.create_borrow_record(
            book.id, quantity, borrowed_on)
        return JsonResponse(borrow_record_to_dict(record), status=201)


class BorrowRecordDetailView(RoleRequiredJsonView):
    role_permissions = {'DELETE': ADMIN_ONLY}

    def delete(self, request, record_id):
        record = catalog_service.get_borrow_record(record_id)
        if not record:
            return JsonResponse({'detail': 'Borrow record not found'}, status=404)
        catalog_service.delete_borrow_record(record)
        return JsonResponse({}, status=204)
