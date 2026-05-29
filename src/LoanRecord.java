import java.time.LocalDate;

public class LoanRecord {
    // Properties to store book data and transaction context
    private Book book;
    private String studentId;
    private LocalDate borrowDate;

    // Return status information
    private boolean returned;
    private LocalDate returnDate;

    // Fine processing status
    private boolean fineProcessed;

    // Normal constructor: used when a student borrows a book today
    public LoanRecord(Book book, String studentId) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = LocalDate.now();

        // When a book is first borrowed, it is not returned yet
        this.returned = false;
        this.returnDate = null;

        // Fine has not been processed yet
        this.fineProcessed = false;
    }

    // Demo/testing constructor: used to simulate an old borrowing date
    public LoanRecord(Book book, String studentId, LocalDate borrowDate) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = borrowDate;

        // Demo overdue record is also treated as currently borrowed first
        this.returned = false;
        this.returnDate = null;

        // Fine has not been processed yet
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

    public boolean isReturned() {
        return returned;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void markReturned() {
        this.returned = true;
        this.returnDate = LocalDate.now();
    }

    public boolean isFineProcessed() {
        return fineProcessed;
    }

    public void markFineProcessed() {
        this.fineProcessed = true;
    }
}