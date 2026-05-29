/**
 * Defines the main operations supported by the smart library system.
 */
public interface LibraryADT {

    // Adds a new book to the library
    void addBook(int isbn, String title, String author);

    // Returns the Book object if found, otherwise returns null
    Book SearchBook(int isbn);

    // Allows a user to borrow a book by its ISBN and student ID
    void borrowBook(int isbn, String studentId);

    // Allows a user to return a borrowed book by ISBN
    void returnBook(int isbn);

    // Allows a user to view history of borrowed books
    void viewLatestHistory();
}