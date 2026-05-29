import java.time.LocalDate;

public class LoanRecord {
    private Book book;
    private String studentId;
    private LocalDate borrowDate;

    // Normal constructor: borrowed today
    public LoanRecord(Book book, String studentId) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = LocalDate.now();
    }

    // Demo/testing constructor: allows old borrow date
    public LoanRecord(Book book, String studentId, LocalDate borrowDate) {
        this.book = book;
        this.studentId = studentId;
        this.borrowDate = borrowDate;
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
}