from django.http import HttpResponse
from django.shortcuts import redirect, render
from django.views import View

from manager.services import catalog as catalog_service
from manager.services.auth import authenticate_manager


class IndexView(View):
    def get(self, request):
        return redirect('/login/')


class ManagerLoginPageView(View):
    def get(self, request):
        return render(request, 'admin/admin.html')

    def post(self, request):
        manager = authenticate_manager(
            request.POST.get('number'),
            request.POST.get('password'),
        )
        if manager:
            request.session['name'] = manager.name
            request.session['manager_id'] = manager.id
            request.session['role'] = manager.role
            return redirect('/book_list/')
        return redirect('/login/')


class PublisherCreatePageView(View):
    def get(self, request):
        return render(request, 'publisher/add_publisher.html')

    def post(self, request):
        publisher_name = request.POST.get('publisher_name')
        publisher_address = request.POST.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return render(
                request,
                'publisher/add_publisher.html',
                {'error': 'Please fill in the publisher name and address.'},
            )

        catalog_service.create_publisher(publisher_name, publisher_address)
        return redirect('/publisher_list/')


class PublisherListPageView(View):
    def get(self, request):
        return render(
            request,
            'publisher/publisher_list.html',
            {'publisher_obj_list': catalog_service.list_publishers()},
        )


class PublisherEditPageView(View):
    def _get_publisher(self, request):
        publisher_id = request.GET.get(
            'id') if request.method == 'GET' else request.POST.get('id')
        if not publisher_id:
            return None
        return catalog_service.get_publisher(publisher_id)

    def get(self, request):
        publisher = self._get_publisher(request)
        if not publisher:
            return redirect('/publisher_list/')
        return render(request, 'publisher/edit_publisher.html', {'publisher_obj': publisher})

    def post(self, request):
        publisher = self._get_publisher(request)
        if not publisher:
            return redirect('/publisher_list/')

        publisher_name = request.POST.get('publisher_name')
        publisher_address = request.POST.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return render(
                request,
                'publisher/edit_publisher.html',
                {'publisher_obj': publisher,
                    'error': 'Please fill in the publisher name and address.'},
            )

        catalog_service.update_publisher(
            publisher, publisher_name, publisher_address)
        return redirect('/publisher_list/')


class PublisherDeletePageView(View):
    def get(self, request):
        publisher_id = request.GET.get('id')
        publisher = catalog_service.get_publisher(
            publisher_id) if publisher_id else None
        if publisher:
            catalog_service.delete_publisher(publisher)
        return redirect('/publisher_list/')


class BookCreatePageView(View):
    def get(self, request):
        return render(
            request,
            'book/add_book.html',
            {'publisher_list': catalog_service.list_publishers()},
        )

    def post(self, request):
        name = request.POST.get('name')
        price = request.POST.get('price')
        inventory = request.POST.get('inventory')
        sale_num = request.POST.get('sale_num') or 0
        publisher_id = request.POST.get('publisher_id')
        if not all([name, price, inventory, publisher_id]):
            return render(
                request,
                'book/add_book.html',
                {
                    'publisher_list': catalog_service.list_publishers(),
                    'error': 'Please fill in all required fields.',
                },
            )

        catalog_service.create_book(
            name, price, inventory, sale_num, publisher_id)
        return redirect('/book_list/')


class BookListPageView(View):
    def get(self, request):
        return render(
            request,
            'book/book_list.html',
            {
                'book_obj_list': catalog_service.list_books(),
                'name': request.session.get('name', 'Administrator'),
            },
        )


class BookEditPageView(View):
    def _get_book(self, request):
        book_id = request.GET.get(
            'id') if request.method == 'GET' else request.POST.get('id')
        if not book_id:
            return None
        return catalog_service.get_book(book_id)

    def get(self, request):
        book = self._get_book(request)
        if not book:
            return redirect('/book_list/')
        return render(
            request,
            'book/edit_book.html',
            {'book_obj': book, 'publisher_list': catalog_service.list_publishers()},
        )

    def post(self, request):
        book = self._get_book(request)
        if not book:
            return redirect('/book_list/')

        name = request.POST.get('name')
        price = request.POST.get('price')
        inventory = request.POST.get('inventory')
        sale_num = request.POST.get('sale_num') or 0
        publisher_id = request.POST.get('publisher_id')
        if not all([name, price, inventory, publisher_id]):
            return render(
                request,
                'book/edit_book.html',
                {
                    'book_obj': book,
                    'publisher_list': catalog_service.list_publishers(),
                    'error': 'Please fill in all required fields.',
                },
            )

        catalog_service.update_book(
            book, name, price, inventory, sale_num, publisher_id)
        return redirect('/book_list/')


class BookDeletePageView(View):
    def get(self, request):
        book_id = request.GET.get('id')
        book = catalog_service.get_book(book_id) if book_id else None
        if book:
            catalog_service.delete_book(book)
        return redirect('/book_list/')


class AuthorCreatePageView(View):
    def get(self, request):
        return render(
            request,
            'author/add_author.html',
            {'book_obj_list': catalog_service.list_books()},
        )

    def post(self, request):
        name = request.POST.get('name')
        book_ids = request.POST.getlist('book_ids')
        if not name:
            return render(
                request,
                'author/add_author.html',
                {
                    'book_obj_list': catalog_service.list_books(),
                    'error': 'Author name cannot be empty.',
                },
            )

        catalog_service.create_author(name, book_ids)
        return redirect('/author_list/')


class AuthorListPageView(View):
    def get(self, request):
        return render(
            request,
            'author/author_list.html',
            {'author_obj_list': catalog_service.list_authors()},
        )


class AuthorEditPageView(View):
    def _get_author(self, request):
        author_id = request.GET.get(
            'id') if request.method == 'GET' else request.POST.get('id')
        if not author_id:
            return None
        return catalog_service.get_author(author_id)

    def _render_form(self, request, author, error=None):
        context = {
            'author_obj': author,
            'book_obj_list': catalog_service.list_books(),
            'selected_book_ids': catalog_service.get_author_book_ids(author),
        }
        if error:
            context['error'] = error
        return render(request, 'author/edit_author.html', context)

    def get(self, request):
        author = self._get_author(request)
        if not author:
            return redirect('/author_list/')
        return self._render_form(request, author)

    def post(self, request):
        author = self._get_author(request)
        if not author:
            return redirect('/author_list/')

        name = request.POST.get('name')
        book_ids = request.POST.getlist('book_ids')
        if not name:
            return self._render_form(request, author, 'Author name cannot be empty.')

        catalog_service.update_author(author, name, book_ids)
        return redirect('/author_list/')


class AuthorDeletePageView(View):
    def get(self, request):
        author_id = request.GET.get('id')
        author = catalog_service.get_author(author_id) if author_id else None
        if author:
            catalog_service.delete_author(author)
        return redirect('/author_list/')
