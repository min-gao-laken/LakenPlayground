from django.urls import path, re_path

from manager import views


urlpatterns = [
    re_path(r'^$', views.index),

    path('login/', views.manager_login),

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
