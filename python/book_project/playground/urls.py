from django.urls import path

from playground import views

urlpatterns = [
    path("", views.index),
    path("api/roadmap/", views.api_roadmap),
    path("api/tasks/", views.api_tasks),
    path("api/tasks/<int:task_id>/", views.api_task_detail),
    path("api/recommendation-lab/", views.api_recommendation_lab),

    #     lake go
    path("api/drill/tasks/", views.api_drill_tasks),
    path("api/drill/tasks/<int:task_id>/", views.api_drill_task_detail),

    # 事务 并发
    path("api/concurrency/setup/", views.api_concurrency_setup),
    path("api/concurrency/borrow-unlocked/", views.api_concurrency_borrow_unlocked),
    path("api/concurrency/borrow-locked/", views.api_concurrency_borrow_locked),
    path("api/concurrency/state/", views.api_concurrency_state),

    path("api/student/concurrency/setup/", views.api_student_concurrency_setup),
    path("api/student/concurrency/borrow-unlocked/", views.api_student_concurrency_borrow_unlocked),
    path("api/student/concurrency/borrow-locked/", views.api_student_concurrency_borrow_locked),
    path("api/student/concurrency/state/", views.api_student_concurrency_state),
]
