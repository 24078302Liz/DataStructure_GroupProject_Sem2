public class Main {
    public static void main(String[] args) {
        BorrowStack history = new BorrowStack();

        System.out.println("Test 1: Viewing history before borrowing.");
        history.show();

        System.out.println("\nAction: Borrowing 3 books...\n");

        history.push(new Book(101, "Java Basics", "Liang"));
        history.push(new Book(102, "Data Structures", "Smith"));
        history.push(new Book(103, "Advanced Algorithms", "Doe"));

        System.out.println("Test 2: Viewing history after borrowing.");
        history.show();
    }
}
