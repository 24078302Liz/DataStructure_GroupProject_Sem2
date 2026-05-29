import java.util.Stack;

public class BorrowStack {
    // Encapsulated stack to hold the history records privately
    private Stack<LoanRecord> stack;

    // Constructor to initialize an empty history stack
    public BorrowStack() {
        this.stack = new Stack<>();
    }

    // Pushes a new borrowing transaction onto the top of the stack
    public void push(LoanRecord record) {
        if (record != null) {
            stack.push(record);
        } else {
            System.out.println("History Error: Cannot log an empty transaction record.");
        }
    }

    // Displays the entire history from most recent to oldest
    public void show() {
        if (stack.isEmpty()) {
            System.out.println("Your borrowing history is currently empty.");
            return;
        }

        System.out.println("\n--- Borrowing History (LIFO Order) ---");

        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);
            Book b = record.getBook();

            System.out.println("[ISBN: " + b.getIsbn() + "] " + b.getTitle() + " by " + b.getAuthor());
            System.out.println("   \u21b3 Borrowed by: " + record.getStudentId() + " on " + record.getBorrowDate());

            if (record.isReturned()) {
                System.out.println("   Status: Returned on " + record.getReturnDate());
            } else {
                System.out.println("   Status: Currently borrowed");
            }

            System.out.println("------------------------------------------------");
        }
    }

    // Finds the latest ACTIVE borrowing record by student ID
    // Active means the book has not been returned yet
    public LoanRecord getLatestByStudent(String studentId) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);

            if (record.getStudentId().equals(studentId) && !record.isReturned()) {
                return record;
            }
        }

        return null;
    }

    // Finds the latest ACTIVE borrowing record of a book by ISBN
    // Active means the book has not been returned yet
    public LoanRecord getLatestByIsbn(int isbn) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);
            Book book = record.getBook();

            if (book.getIsbn() == isbn && !record.isReturned()) {
                return record;
            }
        }

        return null;
    }
}