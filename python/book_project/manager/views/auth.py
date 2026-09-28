from django.http import JsonResponse
from django.shortcuts import redirect, render
from django.views import View

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


class LogoutView(View):
    def get(self, request):
        request.session.flush()
        return redirect('/login/')
