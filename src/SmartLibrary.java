import java.util.Scanner;
/**
 * Implements the library operations and provides a console menu for users.
 */
public class SmartLibrary implements LibraryADT {
    private BookBST catalogue = new BookBST();
    private BorrowStack history = new BorrowStack();

    // Adds a new book to the library
    @Override
    public void addBook(int isbn, String title, String author){
        catalogue.insert(isbn, title, author);
    }

    // Returns the Book object if found, otherwise returns null
    @Override
    public Book SearchBook(int isbn){
        return catalogue.search(isbn);
    }

    // Allows a user to borrow a book by its ISBN
    @Override
    public void borrowBook(int isbn) {
        // 1. Find the book in the tree catalog
        Book b = catalogue.search(isbn);

        if (b != null) {
            // For demonstration, use a placeholder or scan a real student ID
            String activeStudent = "UM-2026-AI";

            // 2. CREATE THE LOAN RECORD (Your class)
            LoanRecord record = new LoanRecord(b, activeStudent);

            // 3. PUSH TO THE HISTORY STACK (Your class method)
            history.push(record);

            // 4. PHYSICAL DELETION (Admin Logic task)
            // Permanently deletes the node from the BST catalog as required by the PDF
            catalogue.delete(isbn);

            System.out.println("Success! Book moved from catalog to your history stack.");
        } else {
            System.out.println("Book not found in catalogue.");
        }
    }

    // Allows a user to view history of borrowed books
    @Override
    public void viewLatestHistory(){
        history.show();
    }

    // keeps the menu running in a loop until the user picks exit
    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Choice: ");

            // using string first then parsing so it won't crash if user types letters
            String menuInput = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(menuInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number between 1-5.");
                continue;
            }

            // exit condition
            if (choice == 5) {
                System.out.println("Goodbye !!");
                break;
            }
            handleChoice(choice, sc);
        }
        sc.close();
    }

    // just prints the menu options
    private void printMenu() {
        System.out.println("\n--- Smart Library Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. View History");
        System.out.println("5. Exit");
    }

    // handles choice based on what the user picked
    private void handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1:
                // validate isbn first before doing anything
                System.out.print("Enter ISBN: ");
                String isbnInput = sc.nextLine();
                int addIsbn;
                try {
                    addIsbn = Integer.parseInt(isbnInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ISBN. Please enter a number.");
                    break;
                }

                // reject empty title
                System.out.print("Enter Title: ");
                String title = sc.nextLine().trim();
                if (title.isEmpty()) {
                    System.out.println("Title cannot be empty.");
                    break;
                }

                // reject empty author
                System.out.print("Enter Author: ");
                String author = sc.nextLine().trim();
                if (author.isEmpty()) {
                    System.out.println("Author cannot be empty.");
                    break;
                }

                addBook(addIsbn, title, author);
                break;


            case 2:
                handleSearch(sc);
                break;

            case 3:

                // validate isbn then pass to borrowBook to handle the rest
                System.out.print("Enter ISBN to borrow: ");
                String borrowInput = sc.nextLine();
                int borrowIsbn;
                try {
                    borrowIsbn = Integer.parseInt(borrowInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ISBN. Please enter a number.");
                    break;
                }
                borrowBook(borrowIsbn);
                break;

            case 4:
                // just calls viewLatestHistory which uses bingyan's show() method
                viewLatestHistory();
                break;

            default:
                // catches anything outside 1-5
                System.out.println("Invalid option. Please choose 1-5.");
        }
    }
    // Displays the search menu and handles the selected search method
    private void handleSearch(Scanner sc) {
        System.out.println("\n--- Search Options ---");
        System.out.println("1. Search by ISBN");
        System.out.println("2. Search by Title");
        System.out.println("3. Search by Author");
        System.out.print("Choice: ");

        String input = sc.nextLine();

        switch (input) {
            case "1":
                // ISBN search can use the BST search directly because ISBN is the sorting key
                System.out.print("Enter ISBN to search: ");
                String isbnInput = sc.nextLine();

                try {
                    int isbn = Integer.parseInt(isbnInput.trim());
                    Book found = SearchBook(isbn);

                    if (found != null) {
                        System.out.println("Found: [ISBN: " + found.getIsbn() + "] "
                                + found.getTitle() + " by " + found.getAuthor());
                    } else {
                        System.out.println("Book not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ISBN. Please enter a number.");
                }
                break;

            case "2":
                // Title search accepts partial keywords, so the user does not need the full title
                System.out.print("Enter title keyword: ");
                String titleKeyword = sc.nextLine().trim();

                if (titleKeyword.isEmpty()) {
                    System.out.println("Keyword cannot be empty.");
                } else {
                    catalogue.searchByTitle(titleKeyword);
                }
                break;

            case "3":
                // Author search also supports partial and case-insensitive matching
                System.out.print("Enter author keyword: ");
                String authorKeyword = sc.nextLine().trim();

                if (authorKeyword.isEmpty()) {
                    System.out.println("Keyword cannot be empty.");
                } else {
                    catalogue.searchByAuthor(authorKeyword);
                }
                break;

            default:
                System.out.println("Invalid search option.");
        }
    }
}