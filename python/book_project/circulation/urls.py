from django.urls import path, re_path
from circulation import views

urlpatterns = [
    # re_path(r'^$', views.index),
    path("ping/", views.ping),
    path("loans/borrow/", views.borrow_book),
    path("loans/<int:loan_id>/return/", views.return_book),
    path("readers/<int:reader_id>/recommendations/", views.recommend_books),
    path("readers/<int:reader_id>/recommendations/export-csv/", views.export_recommendations_csv),
]
