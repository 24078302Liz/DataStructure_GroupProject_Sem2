public class Book {
    private int isbn;
    private String title;
    private String author;
    public Book(int isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + " | '" + title + "' by " + author;
    }
}
