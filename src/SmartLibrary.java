import java.util.Scanner;

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
    public void borrowBook(int isbn){
        Book b = catalogue.search(isbn);
        if (b != null) {
            // Push to BingYan's history stack
            history.push(b);

            // Remove from Nazeef's BST catalogue
            catalogue.delete(isbn);

            System.out.println("Book with ISBN " + isbn + " has been borrowed.");
        } else {
            System.out.println("Book not in catalogue.");
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
                // same isbn validation here
                System.out.print("Enter ISBN to search: ");
                String searchInput = sc.nextLine();
                int searchIsbn;
                try {
                    searchIsbn = Integer.parseInt(searchInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ISBN. Please enter a number.");
                    break;
                }
                Book found = SearchBook(searchIsbn);

                // show details if found, tell user it doesnt exist otherwise
                if (found != null) {
                    System.out.println("Found: [ISBN: " + found.getIsbn() + "] "
                            + found.getTitle() + " by " + found.getAuthor());
                } else {
                    System.out.println("Book not found.");
                }
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
}