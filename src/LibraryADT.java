public interface LibraryADT {
    // Adds a new book to the library
    void addBook(int isbn, String title, String author);
    
    // Returns the Book object if found, otherwise returns null
    Book SearchBook(int isbn);

    // Allows a user to borrow a book by its ISBN
    void borrowBook(int isbn);

    // Allows a user to view history of borrowed books
    void viewLatestHistory();
}
