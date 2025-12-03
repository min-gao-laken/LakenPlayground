package lab3.object.objectminibook;

/**
 * @author Laken
 * @date 2025-12-01
 * @description TRY THIS
 * ------------------------------------------------------------
 * 1. Create a class called Book with fields title and pages.
 * 2. Write a constructor Book(String title, int pages) that
 * uses this to assign both values.
 * 3. Create a Book object and print the fields to confirm the
 * values were stored correctly.
 * ------------------------------------------------------------
 */
public class Chapter6_ThisKeyword {
    static class Book {
        String title;
        int pages;

        public Book(String title, int pages) {
            this.title = title;
            this.pages = pages;
        }

        public String getTitle() {
            return title;
        }

        public int getPages() {
            return pages;
        }
    }

    public static void main(String[] args) {

        Book book = new Book("Hi", 200);
        System.out.println(book.getTitle() + "_" + book.getPages());
    }
}
