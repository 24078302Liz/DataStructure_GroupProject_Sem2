/**
 * A Binary Search Tree (BST) data structure specifically for storing and searching Book objects.
 * Books are organized and searched based on their ISBN.
 */
public class BookBST {
    
    // The starting point (top node) of the tree
    private Book root;

    /**
     * Public entry point to insert a new book into the tree.
     * 
     * @param isbn   The ISBN of the new book.
     * @param title  The title of the new book.
     * @param author The author of the new book.
     */
    public void insert(int isbn, String title, String author){
        // If the tree is empty, the new book becomes the root
        if(isEmpty()){
            setRoot(new Book(isbn, title, author));
        }
        else{
            // Otherwise, start the recursive insertion process from the root
            recursiveInsert(root, new Book(isbn, title, author));        
        }
    }

    /**
     * Recursive helper method to find the correct spot for the new book.
     * 
     * @param currentNode The current node being compared against the new book.
     * @param newBook     The book being inserted into the tree.
     */
    private void recursiveInsert(Book currentNode, Book newBook){
        int newIsbn = newBook.getIsbn();
        int currentIsbn = currentNode.getIsbn();

        // Handling duplicate ISBN value
        if(newIsbn == currentIsbn){
            System.out.println("Insertion failed: A book with ISBN " + newIsbn + " already exists!");
            return;
        }

        // If the new book's ISBN is smaller, it belongs in the left subtree
        else if(newIsbn < currentIsbn){
            if(currentNode.hasLeft()){
                // Keep traversing down the left side
                recursiveInsert(currentNode.getLeft(), newBook);
            }
            else{
                // Found an empty spot, insert it here
                currentNode.setLeft(newBook);
            }
        }

        // If the new book's ISBN is greater, it belongs in the right subtree
        else{
            if(currentNode.hasRight()){
                // Keep traversing down the right side
                recursiveInsert(currentNode.getRight(), newBook);
            }
            else{
                // Found an empty spot, insert it here
                currentNode.setRight(newBook);
            }
        }
    }

    
    // --- Utility Methods ---

    /**
     * Checks if the tree is currently empty.
     * 
     * @return true if there is no root node, false otherwise.
     */
    private boolean isEmpty(){
        return root == null;
    }
    
    // Setter and Getter methods for root

    private void setRoot(Book root){
        this.root = root;
    }

    public Book getRoot(){
        return root;
    }
    










    // ... Recursive Search(To be implemented by YuDong) ...

    /**
     * Public entry point to search for a book by its ISBN.
     * (To be implemented)
     * 
     * @param isbn The ISBN of the book to find.
     * @return The Book object if found, or null if it doesn't exist in the tree.
     */
    public Book search(int isbn) {
        return recursiveSearch(root, isbn);
    }

    /**
     * Recursive helper method for searching.
     * (YuDong: Add your recursive logic here! Add any necessary helper method if needed. If you need any change in the Book class, just let me know ;)
     * 
     * @param currentNode The node currently being checked.
     * @param isbn        The ISBN we are looking for.
     * @return The found Book, or null if we reach a dead end.
     */
    private Book recursiveSearch(Book currentNode, int isbn) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.getIsbn() == isbn) {
            return currentNode;
        }

        if (isbn < currentNode.getIsbn()) {
            return recursiveSearch(currentNode.getLeft(), isbn);
        } else {
            return recursiveSearch(currentNode.getRight(), isbn);
        }
    }
}