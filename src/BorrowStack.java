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
            System.out.println("------------------------------------------------");
        }
    }

    // Finds the latest borrowing record by student ID
    public LoanRecord getLatestByStudent(String studentId) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);

            if (record.getStudentId().equals(studentId)) {
                return record;
            }
        }

        return null;
    }

    public java.util.List<LoanRecord> getAllByStudent(String studentId) {
        java.util.List<LoanRecord> result = new java.util.ArrayList<>();

        for (int i = stack.size() - 1; i >= 0; i--) {
            LoanRecord record = stack.get(i);

            if (record.getStudentId().equals(studentId)) {
                result.add(record);
            }
        }

        return result;
    }
}