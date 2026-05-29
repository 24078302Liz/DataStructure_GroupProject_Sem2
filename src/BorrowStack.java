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
        // Validation check to prevent adding empty entries
        if (record != null) {
            stack.push(record);
        } else {
            System.out.println("History Error: Cannot log an empty transaction record.");
        }
    }

    // Displays the entire history from most recent to oldest (LIFO order)
    public void show() {
        // Edge case handling if the stack has no items
        if (stack.isEmpty()) {
            System.out.println("Your borrowing history is currently empty.");
            return;
        }

        System.out.println("\n--- Borrowing History (LIFO Order) ---");

        // Loop backwards starting from the top index down to 0
        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);
            Book b = record.getBook();

            // Print out individual transaction details
            System.out.println("[ISBN: " + b.getIsbn() + "] " + b.getTitle() + " by " + b.getAuthor());
            System.out.println("   \u21b3 Borrowed by: " + record.getStudentId() + " on " + record.getBorrowDate());
            System.out.println("------------------------------------------------");
        }
    }

    // used by fine manager to calc fines based on their latest borrow
    public LoanRecord getLatestByStudent(String studentId) {
        // loop from top of stack downwards to find the most recent record
        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);
            if (record.getStudentId().equals(studentId)) {
                return record;
            }
        }
        // null if no record found for the student
        return null;
    }
}