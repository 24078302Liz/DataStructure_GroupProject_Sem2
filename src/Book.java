/**
 * Represents a single Book, which also acts as a node in the BookBST.
 * It stores the book's details and references to its left and right children.
 */
public class Book {
    
    // Book details
    private int isbn;
    private String title;
    private String author;

    // Pointers to child nodes in the Binary Search Tree
    private Book left;
    private Book right;

    /**
     * Constructs a new Book node.
     * 
     * @param isbn   The ISBN of the book (used as the sorting key in the BST)
     * @param title  The title of the book
     * @param author The author of the book
     */
    public Book(int isbn, String title, String author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
 
    // --- Getters for Book Details ---

    public int getIsbn(){
        return isbn;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getAuthor(){
        return author;
    }

    // --- Tree Node Utility Methods ---

    /** Checks if this node has a left child. */
    public boolean hasLeft(){
        return left != null;
    }
    
    /** Checks if this node has a right child. */
    public boolean hasRight(){
        return right != null;
    }

    // --- Getters and Setters for Tree Traversal and Search ---

    public void setLeft(Book book){
        this.left = book;
    }
    
    public void setRight(Book book){
        this.right = book;
    }
    
    public Book getLeft(){
        return left;
    }
    
    public Book getRight(){
        return right;
    }

}