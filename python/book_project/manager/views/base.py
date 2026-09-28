import json
from django.http import JsonResponse
from django.utils.decorators import method_decorator
from django.views import View
from django.views.decorators.csrf import csrf_exempt


@method_decorator(csrf_exempt, name='dispatch')
class JsonView(View):
    def http_method_not_allowed(self, request, *args, **kwargs):
        return JsonResponse({'detail': 'Method not allowed'}, status=405)

    @staticmethod
    def parse_json_body(request):
        try:
            payload = json.loads(request.body.decode('utf-8'))
        except (UnicodeDecodeError, json.JSONDecodeError):
            return None, JsonResponse({'detail': 'Invalid JSON body'}, status=400)

        if not isinstance(payload, dict):
            return None, JsonResponse({'detail': 'Invalid JSON body'}, status=400)
        return payload, None


@method_decorator(csrf_exempt, name='dispatch')
class LoginRequiredJsonView(JsonView):
    def dispatch(self, request, *args, **kwargs):
        if not request.session.get('manager_id'):
            return JsonResponse({'detail': 'Authentication required'}, status=401)
        return super().dispatch(request, *args, **kwargs)


@method_decorator(csrf_exempt, name='dispatch')
class RoleRequiredJsonView(JsonView):
    role_permissions = {}

    def permission_error(self, request):
        required_roles = self.role_permissions.get(request.method)
        if required_roles is None:
            return JsonResponse({'detail': 'Method not allowed'}, status=405)

        if not request.session.get('manager_id'):
            return JsonResponse({'detail': 'Authentication required'}, status=401)

        if request.session.get('role') not in required_roles:
            return JsonResponse(
                {'detail': 'Permission denied',
                    'required_roles': list(required_roles)},
                status=403,
            )

    def dispatch(self, request, *args, **kwargs):
        error = self.permission_error(request)
        if error:
            return error
        return super().dispatch(request, *args, **kwargs)
