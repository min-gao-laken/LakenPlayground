import csv

from django.http import HttpResponse, JsonResponse
from django.utils import timezone
from rest_framework.views import APIView
from rest_framework.response import Response

from circulation.services.loans import CirculationError, borrow_book, return_loan
from circulation.services.recommendations import get_recommendations
from manager.models import Manager
from manager.views.base import JsonView, RoleRequiredJsonView


class PingView(JsonView):
    def get(self, request):
        return JsonResponse({'module': 'circulation', 'ok': True})


class BorrowBookView(RoleRequiredJsonView):
    role_permissions = {'POST': (Manager.Role.ADMIN,)}

    def post(self, request):
        payload, error = self.parse_json_body(request)
        if error:
            return error

        reader_id = payload.get('reader_id')
        book_id = payload.get('book_id')
        due_date_value = payload.get('due_date')
        if not all([reader_id, book_id, due_date_value]):
            return JsonResponse({'detail': 'Missing required fields'}, status=400)

        try:
            due_date = timezone.datetime.fromisoformat(due_date_value).date()
        except (TypeError, ValueError):
            return JsonResponse(
                {'detail': 'Invalid due_date format, use YYYY-MM-DD'},
                status=400,
            )

        try:
            loan = borrow_book(reader_id, book_id, due_date)
        except CirculationError as error:
            return JsonResponse({'detail': error.detail}, status=error.status_code)

        return JsonResponse(
            {
                'id': loan.id,
                'reader_id': loan.reader_id,
                'book_id': loan.book_id,
                'status': loan.status,
                'borrow_date': loan.borrow_date.isoformat(),
                'due_date': loan.due_date.isoformat(),
            },
            status=201,
        )


class ReturnBookView(RoleRequiredJsonView):
    role_permissions = {'POST': (Manager.Role.ADMIN,)}

    def post(self, request, loan_id):
        try:
            loan, book = return_loan(loan_id)
        except CirculationError as error:
            return JsonResponse({'detail': error.detail}, status=error.status_code)

        return JsonResponse(
            {
                'id': loan.id,
                'status': loan.status,
                'return_date': loan.return_date.isoformat(),
                'book_id': loan.book_id,
                'book_inventory': book.inventory,
            }
        )


def _parse_recommendation_limits(request):
    try:
        top_k_users = max(1, min(int(request.GET.get('top_k_users', 10)), 100))
    except (TypeError, ValueError):
        top_k_users = 10
    try:
        limit = max(1, min(int(request.GET.get('limit', 10)), 50))
    except (TypeError, ValueError):
        limit = 10
    return top_k_users, limit


class RecommendBooksView(RoleRequiredJsonView):
    role_permissions = {'GET': (Manager.Role.ADMIN, Manager.Role.ANALYST)}

    def get(self, request, reader_id):
        top_k_users, limit = _parse_recommendation_limits(request)
        payload = get_recommendations(reader_id, top_k_users, limit)
        if payload is None:
            return JsonResponse({'detail': 'Reader not found'}, status=404)
        return JsonResponse(payload)


class RecommendationsCsvView(RoleRequiredJsonView):
    role_permissions = {'GET': (Manager.Role.ADMIN, Manager.Role.ANALYST)}

    def get(self, request, reader_id):
        top_k_users, limit = _parse_recommendation_limits(request)
        payload = get_recommendations(reader_id, top_k_users, limit)
        if payload is None:
            return JsonResponse({'detail': 'Reader not found'}, status=404)

        response = HttpResponse(content_type='text/csv; charset=utf-8')
        response['Content-Disposition'] = (
            f'attachment; filename="reader_{reader_id}_recommendations.csv"'
        )
        response.write('\ufeff')

        writer = csv.writer(response)
        writer.writerow(
            ['reader_id', 'algorithm', 'book_id', 'book_name',
                'score', 'neighbor_support', 'reason']
        )
        for item in payload.get('results', []):
            writer.writerow(
                [
                    payload.get('reader_id'),
                    payload.get('algorithm'),
                    item.get('book_id'),
                    item.get('book_name'),
                    item.get('score'),
                    item.get('neighbor_support', ''),
                    item.get('reason'),
                ]
            )

        return response
