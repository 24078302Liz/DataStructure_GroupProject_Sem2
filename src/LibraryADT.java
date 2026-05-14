public interface LibraryADT {
    void addBook(int isbn, String title, String author);
    
    Book SearchBook(int isbn);

    void borrowBook(int isbn);

    void viewLatestHistory();
}
