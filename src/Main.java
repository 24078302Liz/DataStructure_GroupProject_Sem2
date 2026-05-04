public class Main {
    public static void main(String[] args) {
        BorrowStack history = new BorrowStack();
        System.out.println("Test: Viewing history before any borrowing...");
        history.show();
        System.out.println("\nAction: Borrowing 5 books (Full Team)...\n");
        history.push(new Book(101, "Catalogue Architecture", "Nazeef"));
        history.push(new Book(102, "LIFO Stack Logic", "BingYan"));
        history.push(new Book(103, "Recursive Search BST", "YuDong"));
        history.push(new Book(104, "ADT & Information Hiding", "Hilal"));
        history.push(new Book(105, "Admin Logic & UI", "Han"));
        history.show();
    }
}
