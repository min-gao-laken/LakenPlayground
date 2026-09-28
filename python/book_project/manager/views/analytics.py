from django.http import JsonResponse

from manager.models import Manager
from manager.services.analytics import get_analytics_overview
from manager.views.base import RoleRequiredJsonView


class AnalyticsOverviewView(RoleRequiredJsonView):
    role_permissions = {'GET': (Manager.Role.ADMIN, Manager.Role.ANALYST)}

    def get(self, request):
        try:
            days = max(7, min(int(request.GET.get('days', '30')), 365))
        except (TypeError, ValueError):
            days = 30

        return JsonResponse(get_analytics_overview(days))
