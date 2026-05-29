import java.time.LocalDate;

public class LoanRecord {
    // Properties to store book data and transaction context
    private Book book;
    private String studentId;
    private LocalDate borrowDate;

    // Normal constructor: used when a student borrows a book today
    public LoanRecord(Book book, String studentId) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = LocalDate.now(); // Automatically sets to today's date
    }

    // Testing constructor: used when we want to simulate an old borrowing date
    public LoanRecord(Book book, String studentId, LocalDate borrowDate) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = borrowDate;
    }

    // Getters to allow other classes to access the details safely
    public Book getBook() {
        return book;
    }

    public String getStudentId() {
        return studentId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
}