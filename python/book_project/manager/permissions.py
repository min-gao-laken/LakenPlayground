from functools import wraps

from django.http import JsonResponse


def _auth_failed():
    return JsonResponse({'detail': 'Authentication required'}, status=401)


def _forbidden(required_roles):
    return JsonResponse(
        {
            'detail': 'Permission denied',
            'required_roles': list(required_roles),
        },
        status=403,
    )


def login_required_api(view_func):
    @wraps(view_func)
    def _wrapped(request, *args, **kwargs):
        if not request.session.get('manager_id'):
            return _auth_failed()
        return view_func(request, *args, **kwargs)

    return _wrapped


def roles_required(*roles):
    def _decorator(view_func):
        @wraps(view_func)
        def _wrapped(request, *args, **kwargs):
            manager_id = request.session.get('manager_id')
            role = request.session.get('role')
            if not manager_id:
                return _auth_failed()
            if role not in roles:
                return _forbidden(roles)
            return view_func(request, *args, **kwargs)

        return _wrapped

    return _decorator
