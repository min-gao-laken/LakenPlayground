from django.urls import path

from manager.views.analytics import AnalyticsOverviewView
from manager.views.auth import ApiLoginView, ApiLogoutView, CurrentManagerView
from manager.views.base import ApiRootView
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

urlpatterns = [
    path('', ApiRootView.as_view()),
    path('api/login/', ApiLoginView.as_view()),
    path('api/logout/', ApiLogoutView.as_view()),
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
]
