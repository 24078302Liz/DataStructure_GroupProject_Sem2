import java.util.Stack;

public class BorrowStack {
    private Stack<Book> stack;
    public BorrowStack() {
        this.stack = new Stack<>();
    }
    public void push(Book book) {
        if (book != null) {
            stack.push(book);
        }
    }
    public void show() {
        if (stack.isEmpty()) {
            System.out.println("History is empty.");
            return;
        }
        System.out.println("--- Borrowing History (Most Recent First) ---");
        for (int i = stack.size() - 1; i >= 0; i--) {
            Book b = stack.get(i);
            System.out.println("[ISBN: " + b.isbn + "] " + b.title);
        }
        System.out.println("---------------------------------------------");
    }
}