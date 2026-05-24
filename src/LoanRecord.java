import java.time.LocalDate;

public class LoanRecord {
    // Properties to store book data and transaction context
    private Book book;
    private String studentId;
    private LocalDate borrowDate;

    // Constructor to initialize a new borrowing transaction record
    public LoanRecord(Book book, String studentId) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = LocalDate.now(); // Automatically sets to today's date
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