public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("      SMART LIBRARY: COMPREHENSIVE TEST SUITE     ");
        System.out.println("==================================================\n");

        runBorrowStackTests();
        System.out.println("\n");
        runBookBSTTests();
    }

    // ==========================================
    // MODULE 1: BORROW STACK TESTING (BINGYAN) -- Kindly make another trial with updated Book Class
    // ==========================================
    private static void runBorrowStackTests() {
        System.out.println(">>> TESTING MODULE 1: BORROWING HISTORY (STACK) <<<");
        BorrowStack history = new BorrowStack();

        System.out.println("\n[Test 1.1] Viewing history before borrowing anything:");
        history.show(); // Expected: "History is empty."

        System.out.println("\n[Test 1.2] Borrowing 3 books sequentially...");
        history.push(new Book(101, "Java Basics", "Liang"));
        history.push(new Book(102, "Data Structures", "Smith"));
        history.push(new Book(103, "Advanced Algorithms", "Doe"));
        System.out.println("Books pushed to history.");

        System.out.println("\n[Test 1.3] Viewing history after borrowing (Testing LIFO order):");
        history.show(); // Expected: 103 on top, then 102, then 101.
    }

    // ==========================================
    // MODULE 2: CATALOGUE TESTING (NAZEEF & YUDONG) -- Search to be implemneted
    // ==========================================
    private static void runBookBSTTests() {
        System.out.println(">>> TESTING MODULE 2: CATALOGUE SEARCH (BST) <<<");
        BookBST catalogue = new BookBST();

        System.out.println("\n[Test 2.1] Adding Test Books (50, 30, 70, 20, 40)...");
        catalogue.insert(50, "Java Basics", "Liang");
        catalogue.insert(30, "Clean Code", "Robert C. Martin");
        catalogue.insert(70, "Design Patterns", "Gang of Four");
        catalogue.insert(20, "Effective Java", "Joshua Bloch");
        catalogue.insert(40, "Algorithms", "Robert Sedgewick");
        System.out.println("All initial books inserted successfully.");

        System.out.println("\n[Test 2.2] Confirming BST Structure (In-Order Traversal):");
        printAllBooks(catalogue); // Expected: 20 30 40 50 70

        System.out.println("\n[Test 2.3b] Searching for Existing ISBNs (50, 20, 40, 70):");

        int[] existingIsbns = {50, 20, 40, 70};

        for (int isbn : existingIsbns) {
            Book book = catalogue.search(isbn);

            if (book != null) {
                System.out.println("Result: Found -> ISBN " + book.getIsbn()
                        + " | Title: '" + book.getTitle() + "'");
            } else {
                System.out.println("Result: Book not found for ISBN " + isbn + ". (FAIL)");
            }
        }


        System.out.println("\n[Test 2.4] Searching for Missing ISBN (99):");
        Book missingBook = catalogue.search(99);
        if (missingBook == null) {
            System.out.println("Result: Book not found. (SUCCESS - Expected result for 99)");
        } else {
            System.out.println("Result: Found -> " + missingBook.getTitle() + " (FAIL)");
        }

        System.out.println("\n[Test 2.5] Testing Duplicate ISBN Insertion (50):");
        catalogue.insert(50, "Duplicate Java Basics", "Fake Author");
        // Expected: Should print your "Insertion failed" message from BookBST.
    }

    // --- BST Traversal Helper Methods ---
    
    public static void printAllBooks(BookBST catalogue){
        if (catalogue.getRoot() == null) {
            System.out.println("The tree is currently empty.");
        } else {
            System.out.print("Sorted ISBNs: ");
            recursivePrint(catalogue.getRoot());
            System.out.println();
        }
    }

    private static void recursivePrint(Book root){
        if(root != null){
            recursivePrint(root.getLeft());
            System.out.print(root.getIsbn() + " ");
            recursivePrint(root.getRight());
        }
    }
}