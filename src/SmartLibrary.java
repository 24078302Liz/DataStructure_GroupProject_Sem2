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
    public void addBook(int isbn, String title, String author){
        catalogue.insert(isbn, title, author);
    }

    // Returns the Book object if found, otherwise returns null
    @Override
    public Book SearchBook(int isbn){
        return catalogue.search(isbn);
    }

    // Allows a user to borrow a book by its ISBN and student ID
    @Override
    public void borrowBook(int isbn, String studentId) {
        // 1. Find the book in the tree catalogue
        Book b = catalogue.search(isbn);

        if (b != null) {
            // 2. Create a LoanRecord using the real student ID entered by the user
            LoanRecord record = new LoanRecord(b, studentId);

            // 3. Push the loan record into the borrowing history stack
            history.push(record);

            // 4. Remove the borrowed book from the BST catalogue
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
        // 1. Check whether the book is already in the catalogue
        Book existingBook = catalogue.search(isbn);

        if (existingBook != null) {
            System.out.println("Return failed: This book is already in the catalogue.");
            return;
        }

        // 2. Find the latest borrowing record for this ISBN from the history stack
        LoanRecord record = history.getLatestByIsbn(isbn);

        if (record == null) {
            System.out.println("Return failed: No borrowing record found for ISBN " + isbn + ".");
            return;
        }

        // 3. Get the borrowed book from the loan record
        Book returnedBook = record.getBook();

        // 4. Add the book back into the BST catalogue
        catalogue.insert(
                returnedBook.getIsbn(),
                returnedBook.getTitle(),
                returnedBook.getAuthor()
        );

        System.out.println("Book returned successfully.");
        System.out.println("Returned Book: [ISBN: " + returnedBook.getIsbn() + "] "
                + returnedBook.getTitle() + " by " + returnedBook.getAuthor());
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
                System.out.println("Invalid option. Please enter a number between 1-8.");
                continue;
            }

            // exit condition
            if (choice == 8) {
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
        System.out.println("4. Return Book");
        System.out.println("5. View History");
        System.out.println("6. Fine Manager");
        System.out.println("7. Add Demo Overdue Record");
        System.out.println("8. Exit");
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
                // Validate ISBN first
                System.out.print("Enter ISBN to borrow: ");
                String borrowInput = sc.nextLine();

                int borrowIsbn;
                try {
                    borrowIsbn = Integer.parseInt(borrowInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ISBN. Please enter a number.");
                    break;
                }

                // Ask for real student ID instead of using a hardcoded value
                System.out.print("Enter Student ID: ");
                String studentId = sc.nextLine().trim();

                if (studentId.isEmpty()) {
                    System.out.println("Student ID cannot be empty.");
                    break;
                }

                // Borrow the book using ISBN + student ID
                borrowBook(borrowIsbn, studentId);
                break;

            case 4:
                // Return book by ISBN
                System.out.print("Enter ISBN to return: ");
                String returnInput = sc.nextLine();

                int returnIsbn;
                try {
                    returnIsbn = Integer.parseInt(returnInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ISBN. Please enter a number.");
                    break;
                }

                returnBook(returnIsbn);
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
                // ask for student id then process fine based on their loan record
                System.out.print("Enter Student ID: ");
                String studentId = sc.nextLine().trim();

                if (studentId.isEmpty()) {
                    System.out.println("Student ID cannot be empty.");
                    break;
                }

                // get their latest loan record from history to calculate fine
                LoanRecord record = history.getLatestByStudent(studentId);

                if (record == null) {
                    System.out.println("No borrowing record found for student: " + studentId);
                } else {
                    fineManager.processFine(record);
                }
                break;

            case "2":
                // show balance for a specific student
                System.out.print("Enter Student ID: ");
                String sid = sc.nextLine().trim();

                if (sid.isEmpty()) {
                    System.out.println("Student ID cannot be empty.");
                } else {
                    fineManager.showStudentBalance(sid);
                }
                break;

            case "3":
                // show all students with outstanding fines
                fineManager.showAllBalances();
                break;

            case "4":
                // pay off a student's full balance
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
    private void addDemoOverdueRecord() {
        Book demoBook = new Book(999, "Demo Overdue Book", "Test Author");

        String demoStudentId = "UM001";

        // Simulate that the book was borrowed 20 days ago
        java.time.LocalDate oldBorrowDate = java.time.LocalDate.now().minusDays(20);

        LoanRecord demoRecord = new LoanRecord(demoBook, demoStudentId, oldBorrowDate);

        history.push(demoRecord);

        System.out.println("Demo overdue record added for student: " + demoStudentId);
        System.out.println("Borrow date: " + oldBorrowDate);
    }
}