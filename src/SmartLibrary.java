public class SmartLibrary implements LibraryADT {
    //some logic

    // Adds a new book to the library
    @Override
    public void addBook(int isbn, String title, String author){
        System.out.println("Book with ISBN " + isbn + " has been added to the library.");
    }
    
    // Returns the Book object if found, otherwise returns null
    @Override
    public Book SearchBook(int isbn){
        return null;
    }

    // Allows a user to borrow a book by its ISBN
    @Override
    public void borrowBook(int isbn){
        System.out.println("Book with ISBN " + isbn + " has been borrowed.");
    }

    // Allows a user to view history of borrowed books
    @Override
    public void viewLatestHistory(){
        System.out.println("Displaying the latest history of borrowed books.");
    }
}
