import java.util.Scanner;

/**
 * Implements the library operations and provides a console menu for users.
 */
public class SmartLibrary implements LibraryADT {
    private BookBST catalogue = new BookBST();
    private BorrowStack history = new BorrowStack();
    private FineManager fineManager = new FineManager();

    // Adds a new book to the library
    @Override
    public void addBook(int isbn, String title, String author) {
        catalogue.insert(isbn, title, author);
    }

    // Returns the Book object if found, otherwise returns null
    @Override
    public Book SearchBook(int isbn) {
        return catalogue.search(isbn);
    }

    // Allows a user to borrow a book by its ISBN and student ID
    @Override
    public void borrowBook(int isbn, String studentId) {
        Book b = catalogue.search(isbn);

        if (b != null) {
            LoanRecord record = new LoanRecord(b, studentId);
            history.push(record);
            catalogue.delete(isbn);

            System.out.println("Success! Book borrowed by student " + studentId + ".");
            System.out.println("Book moved from catalogue to borrowing history.");
        } else {
            System.out.println("Book not found in catalogue.");
        }
    }

    // Allows a user to return a borrowed book by ISBN
    @Override
    public void returnBook(int isbn) {
        // Check whether the book is already in the catalogue
        Book existingBook = catalogue.search(isbn);

        if (existingBook != null) {
            System.out.println("Return failed: This book is already in the catalogue.");
            return;
        }

        // Find the latest active borrowing record for this ISBN
        LoanRecord record = history.getLatestByIsbn(isbn);

        if (record == null) {
            System.out.println("Return failed: No active borrowing record found for ISBN " + isbn + ".");
            return;
        }

        // Calculate fine automatically during return
        fineManager.processFine(record);

        // Add the returned book back into the catalogue
        Book returnedBook = record.getBook();

        catalogue.insert(
                returnedBook.getIsbn(),
                returnedBook.getTitle(),
                returnedBook.getAuthor()
        );

        // Mark the loan record as returned
        record.markReturned();

        System.out.println("Book returned successfully.");
        System.out.println("Returned Book: [ISBN: " + returnedBook.getIsbn() + "] "
                + returnedBook.getTitle() + " by " + returnedBook.getAuthor());
    }

    // Allows a user to view history of borrowed books
    @Override
    public void viewLatestHistory() {
        history.show();
    }

    // Keeps the menu running in a loop until the user picks exit
    public void runMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Choice: ");

            String menuInput = sc.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(menuInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number between 1-8.");
                continue;
            }

            if (choice == 8) {
                System.out.println("Goodbye !!");
                break;
            }

            handleChoice(choice, sc);
        }

        sc.close();
    }

    // Prints the menu options
    private void printMenu() {
        System.out.println("\n--- Smart Library Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. View History");
        System.out.println("6. Fine Manager");
        System.out.println("7. Demo for Overdue Record");
        System.out.println("8. Exit");
    }

    // Handles choice based on what the user picked
    private void handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1:
                handleAddBook(sc);
                break;

            case 2:
                handleSearch(sc);
                break;

            case 3:
                handleBorrowBook(sc);
                break;

            case 4:
                handleReturnBook(sc);
                break;

            case 5:
                viewLatestHistory();
                break;

            case 6:
                handleFineManager(sc);
                break;

            case 7:
                addDemoOverdueRecord();
                break;

            default:
                System.out.println("Invalid option. Please choose 1-8.");
        }
    }

    // Handles adding a book
    private void handleAddBook(Scanner sc) {
        System.out.print("Enter ISBN: ");
        String isbnInput = sc.nextLine();

        int addIsbn;

        try {
            addIsbn = Integer.parseInt(isbnInput.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ISBN. Please enter a number.");
            return;
        }

        System.out.print("Enter Title: ");
        String title = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.print("Enter Author: ");
        String author = sc.nextLine().trim();

        if (author.isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }

        addBook(addIsbn, title, author);
    }

    // Handles borrowing a book
    private void handleBorrowBook(Scanner sc) {
        System.out.print("Enter ISBN to borrow: ");
        String borrowInput = sc.nextLine();

        int borrowIsbn;

        try {
            borrowIsbn = Integer.parseInt(borrowInput.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ISBN. Please enter a number.");
            return;
        }

        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine().trim();

        if (studentId.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        borrowBook(borrowIsbn, studentId);
    }

    // Handles returning a book
    private void handleReturnBook(Scanner sc) {
        System.out.print("Enter ISBN to return: ");
        String returnInput = sc.nextLine();

        int returnIsbn;

        try {
            returnIsbn = Integer.parseInt(returnInput.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ISBN. Please enter a number.");
            return;
        }

        returnBook(returnIsbn);
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
                System.out.print("Enter title keyword: ");
                String titleKeyword = sc.nextLine().trim();

                if (titleKeyword.isEmpty()) {
                    System.out.println("Keyword cannot be empty.");
                } else {
                    catalogue.searchByTitle(titleKeyword);
                }
                break;

            case "3":
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

    // Fine manager submenu
    private void handleFineManager(Scanner sc) {
        System.out.println("\n--- Fine Manager ---");
        System.out.println("1. Calculate Fine for a Student");
        System.out.println("2. View Student Balance");
        System.out.println("3. View All Balances");
        System.out.println("4. Pay Fine");
        System.out.print("Choice: ");

        String input = sc.nextLine();

        switch (input) {
            case "1":
                System.out.print("Enter Student ID: ");
                String studentId = sc.nextLine().trim();

                if (studentId.isEmpty()) {
                    System.out.println("Student ID cannot be empty.");
                    break;
                }

                LoanRecord record = history.getLatestByStudent(studentId);

                if (record == null) {
                    System.out.println("No active borrowing record found for student: " + studentId);
                } else {
                    fineManager.processFine(record);
                }
                break;

            case "2":
                System.out.print("Enter Student ID: ");
                String sid = sc.nextLine().trim();

                if (sid.isEmpty()) {
                    System.out.println("Student ID cannot be empty.");
                } else {
                    fineManager.showStudentBalance(sid);
                }
                break;

            case "3":
                fineManager.showAllBalances();
                break;

            case "4":
                System.out.print("Enter Student ID to pay fine: ");
                String payId = sc.nextLine().trim();

                if (payId.isEmpty()) {
                    System.out.println("Student ID cannot be empty.");
                } else {
                    fineManager.payFine(payId);
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // Demo function: creates an overdue record borrowed 20 days ago
    private void addDemoOverdueRecord() {
        Book demoBook = new Book(999, "Demo Overdue Book", "Test Author");

        String demoStudentId = "UM001";

        java.time.LocalDate oldBorrowDate = java.time.LocalDate.now().minusDays(20);

        LoanRecord demoRecord = new LoanRecord(demoBook, demoStudentId, oldBorrowDate);

        history.push(demoRecord);

        System.out.println("Demo overdue record added for student: " + demoStudentId);
        System.out.println("Borrow date: " + oldBorrowDate);
    }
}