import java.util.Stack;

// Manages the student's borrowing history using a Stack (LIFO)
public class BorrowStack {

    // private to prevent outside access
    private Stack<Book> stack;

    // Initialize an empty stack
    public BorrowStack() {
        this.stack = new Stack<>();
    }

    // Push a borrowed book to the top
    // null check prevents invalid entries from being stored
    public void push(Book book) {
        if (book != null) {
            stack.push(book);
        }else{
            System.out.println("Error:Cannot add a null book to history.");
        }
    }

    // Displays borrowing history with most recently borrowed book first
    // Iterates from top of stack down to bottom
    public void show() {
        if (stack.isEmpty()) {
            System.out.println("History is empty.");
            return;
        }
        System.out.println("--- Borrowing History (Most Recent First) ---");
        for (int i = stack.size() - 1; i >= 0; i--) {
            Book b = stack.get(i);
            System.out.println("[ISBN: " + b.getIsbn() + "] " + b.getTitle() + " by " + b.getAuthor());
        }
        System.out.println("---------------------------------------------");
    }
}