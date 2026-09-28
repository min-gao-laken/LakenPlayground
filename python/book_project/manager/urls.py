from django.urls import path, re_path

from manager.views.analytics import AnalyticsOverviewView
from manager.views.auth import ApiLoginView, CurrentManagerView, LogoutView
from manager.views.catalog import (
    AuthorCollectionView,
    AuthorDetailView,
    BookCollectionView,
    BookDetailView,
    BorrowRecordCollectionView,
    BorrowRecordDetailView,
    PublisherCollectionView,
    PublisherDetailView,
)
from manager.views.pages import (
    AuthorCreatePageView,
    AuthorDeletePageView,
    AuthorEditPageView,
    AuthorListPageView,
    BookCreatePageView,
    BookDeletePageView,
    BookEditPageView,
    BookListPageView,
    IndexView,
    ManagerLoginPageView,
    PublisherCreatePageView,
    PublisherDeletePageView,
    PublisherEditPageView,
    PublisherListPageView,
)


urlpatterns = [
    re_path(r'^$', IndexView.as_view()),

    path('login/', ManagerLoginPageView.as_view()),
    path('api/login/', ApiLoginView.as_view()),
    path('api/me/', CurrentManagerView.as_view()),
    path('api/publishers/', PublisherCollectionView.as_view()),
    path('api/publishers/<int:publisher_id>/', PublisherDetailView.as_view()),
    path('api/books/', BookCollectionView.as_view()),
    path('api/books/<int:book_id>/', BookDetailView.as_view()),
    path('api/authors/', AuthorCollectionView.as_view()),
    path('api/authors/<int:author_id>/', AuthorDetailView.as_view()),
    path('api/borrow-records/', BorrowRecordCollectionView.as_view()),
    path('api/borrow-records/<int:record_id>/',
         BorrowRecordDetailView.as_view()),
    path('api/analytics/overview/', AnalyticsOverviewView.as_view()),

    path('add_publisher/', PublisherCreatePageView.as_view()),
    path('publisher_list/', PublisherListPageView.as_view()),
    path('edit_publisher/', PublisherEditPageView.as_view()),
    path('delete_publisher/', PublisherDeletePageView.as_view()),

    path('add_book/', BookCreatePageView.as_view()),
    path('book_list/', BookListPageView.as_view()),
    path('edit_book/', BookEditPageView.as_view()),
    path('delete_book/', BookDeletePageView.as_view()),

    path('add_author/', AuthorCreatePageView.as_view()),
    path('author_list/', AuthorListPageView.as_view()),
    path('edit_author/', AuthorEditPageView.as_view()),
    path('delete_author/', AuthorDeletePageView.as_view()),

    path('logout/', LogoutView.as_view()),
]
