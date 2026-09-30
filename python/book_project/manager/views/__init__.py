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
api_root = ApiRootView.as_view()
api_login = ApiLoginView.as_view()
api_logout = ApiLogoutView.as_view()
api_me = CurrentManagerView.as_view()
api_publishers = PublisherCollectionView.as_view()
api_publisher_detail = PublisherDetailView.as_view()
api_books = BookCollectionView.as_view()
api_book_detail = BookDetailView.as_view()
api_authors = AuthorCollectionView.as_view()
api_author_detail = AuthorDetailView.as_view()
api_borrow_records = BorrowRecordCollectionView.as_view()
api_borrow_record_detail = BorrowRecordDetailView.as_view()
api_analytics_overview = AnalyticsOverviewView.as_view()
