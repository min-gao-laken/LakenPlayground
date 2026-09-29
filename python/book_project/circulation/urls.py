from django.urls import path

from circulation.views.api import (
    BorrowBookView,
    PingView,
    RecommendBooksView,
    RecommendationsCsvView,
    ReturnBookView,
)

urlpatterns = [
    path("ping/", PingView.as_view()),
    path("loans/borrow/", BorrowBookView.as_view()),
    path("loans/<int:loan_id>/return/", ReturnBookView.as_view()),
    path("readers/<int:reader_id>/recommendations/",
         RecommendBooksView.as_view()),
    path(
        "readers/<int:reader_id>/recommendations/export-csv/",
        RecommendationsCsvView.as_view(),
    ),
]
