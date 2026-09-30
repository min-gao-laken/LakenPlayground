from django.http import JsonResponse
from manager.services.auth import authenticate_manager
from manager.views.base import JsonView, LoginRequiredJsonView


class ApiLoginView(JsonView):
    def post(self, request):
        payload, error = self.parse_json_body(request)
        if error:
            payload = {}

        manager = authenticate_manager(
            payload.get('number'), payload.get('password'))
        if not manager:
            return JsonResponse({'success': False, 'message': 'Invalid credentials'}, status=401)

        request.session['name'] = manager.name
        request.session['manager_id'] = manager.id
        request.session['role'] = manager.role
        return JsonResponse({'success': True, 'name': manager.name, 'role': manager.role})


class CurrentManagerView(LoginRequiredJsonView):
    def get(self, request):
        return JsonResponse(
            {
                'manager_id': request.session.get('manager_id'),
                'name': request.session.get('name'),
                'role': request.session.get('role'),
            }
        )


class ApiLogoutView(LoginRequiredJsonView):
    def post(self, request):
        request.session.flush()
        return JsonResponse({'success': True})
