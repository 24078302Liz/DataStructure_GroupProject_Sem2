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
            System.out.println("Book with ISBN " + isbn + " has been added to the library.");
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

        System.out.println("Book with ISBN " + newIsbn + " has been added to the library.");
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

    // BST Delete Operation

    /**
     * Public entry point to delete a book from the tree by its ISBN.
     * @param isbn The ISBN of the book to remove.
     */

    public void delete(int isbn) {
        // start recursive deletion from the root
        root = recursiveDelete(root, isbn);
    }

    /**
     * Recursive helper method to find and remove the book from the BST.
     * handles 3 cases: no child, one child, two children
     */

    private Book recursiveDelete(Book currentNode, int isbn) {
        // base: book not found in the tree
        if (currentNode == null) {
            System.out.println("Book with ISBN " + isbn + " not found in catalogue.");
            return null;
        }

        // traverse left if isbn is smaller
        if (isbn < currentNode.getIsbn()) {
            currentNode.setLeft(recursiveDelete(currentNode.getLeft(), isbn));
        }

        // traverse right if isbn is larger
        else if (isbn > currentNode.getIsbn()) {
            currentNode.setRight(recursiveDelete(currentNode.getRight(), isbn));
        }

        // found node to delete
        else {
            // no left child, then replace with right child
            if (!currentNode.hasLeft()) {
                return currentNode.getRight();
            }

            // no right child, then replace with left child
            else if (!currentNode.hasRight()) {
                return currentNode.getLeft();
            }

            // Find the in-order successor, which is the smallest node in the right subtree.
            Book successor = getMinValueNode(currentNode.getRight());

            // Replace the current node's book information with the successor's information.
            currentNode.setIsbn(successor.getIsbn());
            currentNode.setTitle(successor.getTitle());
            currentNode.setAuthor(successor.getAuthor());

            // Remove the successor from its original position to avoid duplicate ISBNs.
            currentNode.setRight(recursiveDelete(currentNode.getRight(), successor.getIsbn()));
        }

        return currentNode;
    }
}