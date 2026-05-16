/**
 * A Binary Search Tree (BST) data structure specifically for storing and searching Book objects.
 * Books are organized and searched based on their ISBN.
 */
public class BookBST {

    // The starting point (top node) of the tree
    private Book root;

    /**
     * Public entry point to insert a new book into the tree.
     * * @param isbn   The ISBN of the new book.
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


    // --- BST Search Operations ---

    /**
     * Public entry point to search for a book by its ISBN.
     * @param isbn The ISBN of the book to find.
     * @return The Book object if found, or null if it doesn't exist in the catalogue.
     */
    public Book search(int isbn) {
        // Start the recursive search from the root of the tree
        return recursiveSearch(root, isbn);
    }

    /**
     * Recursive helper method to traverse the BST and locate a specific book.
     */
    private Book recursiveSearch(Book currentNode, int isbn) {
        // Base case 1: Reached a dead end; the book does not exist in this path
        if (currentNode == null) {
            return null;
        }

        // Base case 2: The current node's ISBN matches the target ISBN
        if (currentNode.getIsbn() == isbn) {
            return currentNode;
        }

        // Recursive case 1: Target ISBN is smaller, continue search in the left subtree
        if (isbn < currentNode.getIsbn()) {
            return recursiveSearch(currentNode.getLeft(), isbn);
        }

        // Recursive case 2: Target ISBN is larger, continue search in the right subtree
        else {
            return recursiveSearch(currentNode.getRight(), isbn);
        }
    }


    /**
     * Helper method to find the node with the minimum value (leftmost leaf).
     * Used to find the in-order successor during deletion.
     */
    private Book getMinValueNode(Book node) {
        Book current = node;
        // Loop down to find the leftmost leaf
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }
}