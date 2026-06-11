import java.time.LocalDate;

public class LoanRecord {
    // Properties to store book data and transaction context
    private Book book;
    private String studentId;
    private LocalDate borrowDate;

    // Fine processing status
    private boolean fineProcessed;

    // Normal constructor: used when a student borrows a book today
    public LoanRecord(Book book, String studentId) {
        this.book = copyBook(book);
        this.studentId = studentId;
        this.borrowDate = LocalDate.now();
        this.fineProcessed = false;
    }

    // Demo/testing constructor: used to simulate an old borrowing date
    public LoanRecord(Book book, String studentId, LocalDate borrowDate) {
        this.book = copyBook(book);
        this.studentId = studentId;
        this.borrowDate = borrowDate;
        this.fineProcessed = false;
    }

    public Book getBook() {
        return book;
    }

    public String getStudentId() {
        return studentId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public boolean isFineProcessed() {
        return fineProcessed;
    }

    public void markFineProcessed() {
        this.fineProcessed = true;
    }

    // Store a snapshot of the borrowed book, not the mutable BST node
    private Book copyBook(Book original) {
        if (original == null) {
            return null;
        }

        return new Book(original.getIsbn(), original.getTitle(), original.getAuthor());
    }
}