from django.http import HttpResponse
from django.shortcuts import redirect, render

from manager import models


# Default route
def index(request):
    return redirect('/login/')


def manager_login(request):
    if request.method == 'GET':
        return render(request, 'admin/admin.html')

    if request.method == 'POST':
        number = request.POST.get('number')
        password = request.POST.get('password')
        manager = models.Manager.objects.filter(number=number, password=password)

        if manager.exists():
            request.session['name'] = manager.first().name
            return redirect('/book_list/')

    return redirect('/login/')


def add_publisher(request):
    if request.method == 'GET':
        return render(request, 'publisher/add_publisher.html')

    if request.method == 'POST':
        publisher_name = request.POST.get('publisher_name')
        publisher_address = request.POST.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return render(
                request,
                'publisher/add_publisher.html',
                {'error': 'Please fill in the publisher name and address.'},
            )

        models.Publisher.objects.create(
            publisher_name=publisher_name,
            publisher_address=publisher_address,
        )
        return redirect('/publisher_list/')

    return HttpResponse(status=405)


def publisher_list(request):
    publisher_obj_list = models.Publisher.objects.all()
    return render(
        request,
        'publisher/publisher_list.html',
        {'publisher_obj_list': publisher_obj_list},
    )


def edit_publisher(request):
    publisher_id = request.GET.get('id') if request.method == 'GET' else request.POST.get('id')
    if not publisher_id:
        return redirect('/publisher_list/')

    publisher_obj = models.Publisher.objects.filter(id=publisher_id).first()
    if not publisher_obj:
        return redirect('/publisher_list/')

    if request.method == 'GET':
        return render(
            request,
            'publisher/edit_publisher.html',
            {'publisher_obj': publisher_obj},
        )

    if request.method == 'POST':
        publisher_name = request.POST.get('publisher_name')
        publisher_address = request.POST.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return render(
                request,
                'publisher/edit_publisher.html',
                {'publisher_obj': publisher_obj, 'error': 'Please fill in the publisher name and address.'},
            )

        publisher_obj.publisher_name = publisher_name
        publisher_obj.publisher_address = publisher_address
        publisher_obj.save()
        return redirect('/publisher_list/')

    return HttpResponse(status=405)


def delete_publisher(request):
    publisher_id = request.GET.get('id')
    if publisher_id:
        models.Publisher.objects.filter(id=publisher_id).delete()
    return redirect('/publisher_list/')


def add_book(request):
    if request.method == 'GET':
        publisher_list = models.Publisher.objects.all()
        return render(request, 'book/add_book.html', {'publisher_list': publisher_list})

    if request.method == 'POST':
        name = request.POST.get('name')
        price = request.POST.get('price')
        inventory = request.POST.get('inventory')
        sale_num = request.POST.get('sale_num') or 0
        publisher_id = request.POST.get('publisher_id')

        if not all([name, price, inventory, publisher_id]):
            publisher_list = models.Publisher.objects.all()
            return render(
                request,
                'book/add_book.html',
                {'publisher_list': publisher_list, 'error': 'Please fill in all required fields.'},
            )

        models.Book.objects.create(
            name=name,
            price=price,
            inventory=inventory,
            sale_num=sale_num,
            publisher_id=publisher_id,
        )
        return redirect('/book_list/')

    return HttpResponse(status=405)


def book_list(request):
    name = request.session.get('name', 'Administrator')
    book_obj_list = models.Book.objects.select_related('publisher').all()
    return render(
        request,
        'book/book_list.html',
        {'book_obj_list': book_obj_list, 'name': name},
    )


def edit_book(request):
    book_id = request.GET.get('id') if request.method == 'GET' else request.POST.get('id')
    if not book_id:
        return redirect('/book_list/')

    book_obj = models.Book.objects.filter(id=book_id).first()
    if not book_obj:
        return redirect('/book_list/')

    if request.method == 'GET':
        publisher_list = models.Publisher.objects.all()
        return render(
            request,
            'book/edit_book.html',
            {'book_obj': book_obj, 'publisher_list': publisher_list},
        )

    if request.method == 'POST':
        name = request.POST.get('name')
        price = request.POST.get('price')
        inventory = request.POST.get('inventory')
        sale_num = request.POST.get('sale_num') or 0
        publisher_id = request.POST.get('publisher_id')
        if not all([name, price, inventory, publisher_id]):
            publisher_list = models.Publisher.objects.all()
            return render(
                request,
                'book/edit_book.html',
                {
                    'book_obj': book_obj,
                    'publisher_list': publisher_list,
                    'error': 'Please fill in all required fields.',
                },
            )

        book_obj.name = name
        book_obj.price = price
        book_obj.inventory = inventory
        book_obj.sale_num = sale_num
        book_obj.publisher_id = publisher_id
        book_obj.save()
        return redirect('/book_list/')

    return HttpResponse(status=405)


def delete_book(request):
    book_id = request.GET.get('id')
    if book_id:
        models.Book.objects.filter(id=book_id).delete()
    return redirect('/book_list/')


def add_author(request):
    if request.method == 'GET':
        book_obj_list = models.Book.objects.all()
        return render(request, 'author/add_author.html', {'book_obj_list': book_obj_list})

    if request.method == 'POST':
        name = request.POST.get('name')
        book_ids = request.POST.getlist('book_ids')
        if not name:
            book_obj_list = models.Book.objects.all()
            return render(
                request,
                'author/add_author.html',
                {'book_obj_list': book_obj_list, 'error': 'Author name cannot be empty.'},
            )

        author_obj = models.Author.objects.create(name=name)
        if book_ids:
            author_obj.book.set(book_ids)
        return redirect('/author_list/')

    return HttpResponse(status=405)


def author_list(request):
    author_obj_list = models.Author.objects.prefetch_related('book').all()
    return render(
        request,
        'author/author_list.html',
        {'author_obj_list': author_obj_list},
    )


def edit_author(request):
    author_id = request.GET.get('id') if request.method == 'GET' else request.POST.get('id')
    if not author_id:
        return redirect('/author_list/')

    author_obj = models.Author.objects.filter(id=author_id).first()
    if not author_obj:
        return redirect('/author_list/')

    if request.method == 'GET':
        book_obj_list = models.Book.objects.all()
        selected_book_ids = list(author_obj.book.values_list('id', flat=True))
        return render(
            request,
            'author/edit_author.html',
            {
                'author_obj': author_obj,
                'book_obj_list': book_obj_list,
                'selected_book_ids': selected_book_ids,
            },
        )

    if request.method == 'POST':
        name = request.POST.get('name')
        book_ids = request.POST.getlist('book_ids')
        if not name:
            book_obj_list = models.Book.objects.all()
            selected_book_ids = list(author_obj.book.values_list('id', flat=True))
            return render(
                request,
                'author/edit_author.html',
                {
                    'author_obj': author_obj,
                    'book_obj_list': book_obj_list,
                    'selected_book_ids': selected_book_ids,
                    'error': 'Author name cannot be empty.',
                },
            )

        author_obj.name = name
        author_obj.save()
        author_obj.book.set(book_ids)
        return redirect('/author_list/')

    return HttpResponse(status=405)


def delete_author(request):
    author_id = request.GET.get('id')
    if author_id:
        models.Author.objects.filter(id=author_id).delete()
    return redirect('/author_list/')


def logout(request):
    request.session.flush()
    return redirect('/login/')
