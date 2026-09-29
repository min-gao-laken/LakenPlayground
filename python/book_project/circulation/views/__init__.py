from circulation.views.api import (
    BorrowBookView,
    PingView,
    RecommendBooksView,
    ReturnBookView,
    RecommendationsCsvView,
)

ping = PingView.as_view()
borrow_book = BorrowBookView.as_view()
return_book = ReturnBookView.as_view()
recommend_books = RecommendBooksView.as_view()
export_recommendations_csv = RecommendationsCsvView.as_view()
