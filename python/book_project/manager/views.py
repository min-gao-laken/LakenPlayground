import json
from datetime import timedelta

from django.db.models import Sum
from django.db.models.functions import Coalesce
from django.http import HttpResponse, JsonResponse
from django.shortcuts import redirect, render
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt

from manager import models
from manager.permissions import login_required_api


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
            manager_obj = manager.first()
            request.session['name'] = manager_obj.name
            request.session['manager_id'] = manager_obj.id
            request.session['role'] = manager_obj.role
            return redirect('/book_list/')

    return redirect('/login/')


@csrf_exempt
def api_login(request):
    if request.method != 'POST':
        return JsonResponse({'detail': 'Method not allowed'}, status=405)

    try:
        payload = json.loads(request.body.decode('utf-8'))
    except json.JSONDecodeError:
        payload = {}

    number = payload.get('number')
    password = payload.get('password')
    manager = models.Manager.objects.filter(number=number, password=password).first()

    if not manager:
        return JsonResponse({'success': False, 'message': 'Invalid credentials'}, status=401)

    request.session['name'] = manager.name
    request.session['manager_id'] = manager.id
    request.session['role'] = manager.role
    return JsonResponse({'success': True, 'name': manager.name, 'role': manager.role})


@login_required_api
def api_me(request):
    if request.method != 'GET':
        return JsonResponse({'detail': 'Method not allowed'}, status=405)
    return JsonResponse(
        {
            'manager_id': request.session.get('manager_id'),
            'name': request.session.get('name'),
            'role': request.session.get('role'),
        }
    )


def _require_any_role(request, roles):
    if not request.session.get('manager_id'):
        return JsonResponse({'detail': 'Authentication required'}, status=401)
    if request.session.get('role') not in roles:
        return JsonResponse({'detail': 'Permission denied', 'required_roles': list(roles)}, status=403)
    return None


def _publisher_to_dict(publisher):
    return {
        'id': publisher.id,
        'publisher_name': publisher.publisher_name,
        'publisher_address': publisher.publisher_address,
    }


@csrf_exempt
def api_publishers(request):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method == 'GET':
        data = [_publisher_to_dict(publisher) for publisher in models.Publisher.objects.all()]
        return JsonResponse({'results': data})

    if request.method == 'POST':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        publisher_name = payload.get('publisher_name')
        publisher_address = payload.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        publisher = models.Publisher.objects.create(
            publisher_name=publisher_name,
            publisher_address=publisher_address,
        )
        return JsonResponse(_publisher_to_dict(publisher), status=201)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


@csrf_exempt
def api_publisher_detail(request, publisher_id):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    publisher = models.Publisher.objects.filter(id=publisher_id).first()
    if not publisher:
        return JsonResponse({'detail': 'Publisher not found'}, status=404)

    if request.method == 'PUT':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        publisher_name = payload.get('publisher_name')
        publisher_address = payload.get('publisher_address')
        if not all([publisher_name, publisher_address]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        publisher.publisher_name = publisher_name
        publisher.publisher_address = publisher_address
        publisher.save()
        return JsonResponse(_publisher_to_dict(publisher))

    if request.method == 'DELETE':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        publisher.delete()
        return JsonResponse({}, status=204)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


def _book_to_dict(book):
    return {
        'id': book.id,
        'name': book.name,
        'price': str(book.price),
        'inventory': book.inventory,
        'sale_num': book.sale_num,
        'publisher_id': book.publisher_id,
        'publisher_name': book.publisher.publisher_name if book.publisher_id else None,
    }


@csrf_exempt
def api_books(request):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method == 'GET':
        books = models.Book.objects.select_related('publisher').all()
        data = [_book_to_dict(book) for book in books]
        return JsonResponse({'results': data})

    if request.method == 'POST':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        name = payload.get('name')
        price = payload.get('price')
        inventory = payload.get('inventory')
        sale_num = payload.get('sale_num', 0)
        publisher_id = payload.get('publisher_id')

        if not all([name, price, inventory, publisher_id]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        book = models.Book.objects.create(
            name=name,
            price=price,
            inventory=inventory,
            sale_num=sale_num,
            publisher_id=publisher_id,
        )
        book = models.Book.objects.select_related('publisher').get(id=book.id)
        return JsonResponse(_book_to_dict(book), status=201)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


def _author_to_dict(author):
    return {
        'id': author.id,
        'name': author.name,
        'book_ids': list(author.book.values_list('id', flat=True)),
        'book_names': list(author.book.values_list('name', flat=True)),
    }


@csrf_exempt
def api_authors(request):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method == 'GET':
        authors = models.Author.objects.prefetch_related('book').all()
        return JsonResponse({'results': [_author_to_dict(author) for author in authors]})

    if request.method == 'POST':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        name = payload.get('name')
        book_ids = payload.get('book_ids', [])
        if not name:
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        author = models.Author.objects.create(name=name)
        if book_ids:
            author.book.set(book_ids)
        author = models.Author.objects.prefetch_related('book').get(id=author.id)
        return JsonResponse(_author_to_dict(author), status=201)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


@csrf_exempt
def api_author_detail(request, author_id):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    author = models.Author.objects.prefetch_related('book').filter(id=author_id).first()
    if not author:
        return JsonResponse({'detail': 'Author not found'}, status=404)

    if request.method == 'PUT':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        name = payload.get('name')
        book_ids = payload.get('book_ids', [])
        if not name:
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        author.name = name
        author.save()
        author.book.set(book_ids)
        author = models.Author.objects.prefetch_related('book').get(id=author.id)
        return JsonResponse(_author_to_dict(author))

    if request.method == 'DELETE':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        author.delete()
        return JsonResponse({}, status=204)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


@csrf_exempt
def api_book_detail(request, book_id):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    book = models.Book.objects.select_related('publisher').filter(id=book_id).first()
    if not book:
        return JsonResponse({'detail': 'Book not found'}, status=404)

    if request.method == 'PUT':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        name = payload.get('name')
        price = payload.get('price')
        inventory = payload.get('inventory')
        sale_num = payload.get('sale_num', 0)
        publisher_id = payload.get('publisher_id')
        if not all([name, price, inventory, publisher_id]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        book.name = name
        book.price = price
        book.inventory = inventory
        book.sale_num = sale_num
        book.publisher_id = publisher_id
        book.save()
        book = models.Book.objects.select_related('publisher').get(id=book.id)
        return JsonResponse(_book_to_dict(book))

    if request.method == 'DELETE':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        book.delete()
        return JsonResponse({}, status=204)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)

def _borrow_record_to_dict(record):
    return {
        'id': record.id,
        'book_id': record.book_id,
        'book_name': record.book.name if record.book_id else None,
        'borrowed_on': record.borrowed_on.isoformat(),
        'quantity': record.quantity,
    }


@csrf_exempt
def api_borrow_records(request):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method == 'GET':
        records = models.BorrowRecord.objects.select_related('book').all()[:100]
        return JsonResponse({'results': [_borrow_record_to_dict(record) for record in records]})

    if request.method == 'POST':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except json.JSONDecodeError:
            return JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        book_id = payload.get('book_id')
        quantity = payload.get('quantity', 1)
        borrowed_on = payload.get('borrowed_on')

        if not book_id:
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        book = models.Book.objects.filter(id=book_id).first()
        if not book:
            return JsonResponse({'detail': 'Book not found'}, status=404)

        if borrowed_on:
            try:
                borrowed_on = timezone.datetime.fromisoformat(borrowed_on).date()
            except ValueError:
                return JsonResponse({'detail': 'Invalid borrowed_on format'}, status=400)
        else:
            borrowed_on = timezone.localdate()

        record = models.BorrowRecord.objects.create(
            book_id=book_id,
            quantity=max(int(quantity), 1),
            borrowed_on=borrowed_on,
        )
        record = models.BorrowRecord.objects.select_related('book').get(id=record.id)
        return JsonResponse(_borrow_record_to_dict(record), status=201)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


@csrf_exempt
def api_borrow_record_detail(request, record_id):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    record = models.BorrowRecord.objects.select_related('book').filter(id=record_id).first()
    if not record:
        return JsonResponse({'detail': 'Borrow record not found'}, status=404)

    if request.method == 'DELETE':
        denied = _require_any_role(request, [models.Manager.Role.ADMIN])
        if denied:
            return denied
        record.delete()
        return JsonResponse({}, status=204)

    return JsonResponse({'detail': 'Method not allowed'}, status=405)


def api_analytics_overview(request):
    denied = _require_any_role(request, [models.Manager.Role.ADMIN, models.Manager.Role.ANALYST])
    if denied:
        return denied

    if request.method != 'GET':
        return JsonResponse({'detail': 'Method not allowed'}, status=405)

    days = request.GET.get('days', '30')
    try:
        days = max(7, min(int(days), 365))
    except ValueError:
        days = 30

    end_date = timezone.localdate()
    start_date = end_date - timedelta(days=days - 1)

    top_books_qs = (
        models.Book.objects.annotate(
            borrow_count=Coalesce(Sum('borrow_records__quantity'), 0),
        )
        .order_by('-borrow_count', '-sale_num', 'id')[:8]
    )
    top_books = [
        {
            'id': book.id,
            'name': book.name,
            'borrow_count': int(book.borrow_count or 0),
            'sale_num': int(book.sale_num or 0),
        }
        for book in top_books_qs
    ]

    trend_rows = (
        models.BorrowRecord.objects.filter(borrowed_on__gte=start_date, borrowed_on__lte=end_date)
        .values('borrowed_on')
        .annotate(total=Coalesce(Sum('quantity'), 0))
        .order_by('borrowed_on')
    )
    trend_map = {row['borrowed_on']: int(row['total'] or 0) for row in trend_rows}

    borrow_trend = []
    total_borrowed = 0
    for offset in range(days):
        current_date = start_date + timedelta(days=offset)
        value = trend_map.get(current_date, 0)
        total_borrowed += value
        borrow_trend.append({'date': current_date.isoformat(), 'borrow_count': value})

    return JsonResponse(
        {
            'top_books': top_books,
            'borrow_trend': borrow_trend,
            'summary': {
                'days': days,
                'total_borrowed': total_borrowed,
                'records': models.BorrowRecord.objects.count(),
            },
        }
    )

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

