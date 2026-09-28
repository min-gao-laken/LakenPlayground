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

index = IndexView.as_view()
manager_login = ManagerLoginPageView.as_view()
api_login = ApiLoginView.as_view()
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
add_publisher = PublisherCreatePageView.as_view()
publisher_list = PublisherListPageView.as_view()
edit_publisher = PublisherEditPageView.as_view()
delete_publisher = PublisherDeletePageView.as_view()
add_book = BookCreatePageView.as_view()
book_list = BookListPageView.as_view()
edit_book = BookEditPageView.as_view()
delete_book = BookDeletePageView.as_view()
add_author = AuthorCreatePageView.as_view()
author_list = AuthorListPageView.as_view()
edit_author = AuthorEditPageView.as_view()
delete_author = AuthorDeletePageView.as_view()
logout = LogoutView.as_view()
