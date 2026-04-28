from django.urls import path, re_path
from manager import views


urlpatterns = [
    re_path(r'^$', views.index),

    path('login/', views.manager_login),
    path('api/login/', views.api_login),
    path('api/me/', views.api_me),
    path('api/publishers/', views.api_publishers),
    path('api/publishers/<int:publisher_id>/', views.api_publisher_detail),
    path('api/books/', views.api_books),
    path('api/books/<int:book_id>/', views.api_book_detail),
    path('api/authors/', views.api_authors),
    path('api/authors/<int:author_id>/', views.api_author_detail),
    path('api/borrow-records/', views.api_borrow_records),
    path('api/borrow-records/<int:record_id>/', views.api_borrow_record_detail),
    path('api/analytics/overview/', views.api_analytics_overview),

    path('add_publisher/', views.add_publisher),
    path('publisher_list/', views.publisher_list),
    path('edit_publisher/', views.edit_publisher),
    path('delete_publisher/', views.delete_publisher),

    path('add_book/', views.add_book),
    path('book_list/', views.book_list),
    path('edit_book/', views.edit_book),
    path('delete_book/', views.delete_book),

    path('add_author/', views.add_author),
    path('author_list/', views.author_list),
    path('edit_author/', views.edit_author),
    path('delete_author/', views.delete_author),

    path('logout/', views.logout),
]
