# Smart Library Management System 📚

**Universiti Malaya | WIA1002 DATA STRUCTURE | SEM II 2025/2026**
**Instructor:** DR. MOHAMED N. M. LUBANI
**Occurrence 10, Team 2**

[📘 Read the full Smart Library Technical Report (PDF)](./Smart_Library_Technical_Report.pdf)

## 📖 Executive Summary
The Smart Library Management System is a terminal-based tool built to handle the everyday administrative work of a university library. Designed from the ground up using object-oriented principles, the system explores what happens when different data access strategies are pitted against each other inside the same system.

To bridge the opposing requirements of rapid catalogue lookups and reliable chronological transaction records, the system utilizes a dual-engine approach:
* **The Catalogue Inventory:** Managed entirely through a custom Binary Search Tree (BST) to ensure lookups stay fast in logarithmic time as the collection grows.
* **The Borrowing Registry Ledger:** Governed by a Last-In-First-Out (LIFO) stack, mirroring how people naturally think about their recent borrowing history.

## 🏗️ System Architecture

### 1. Abstract Data Type (ADT) & Information Hiding
The system guarantees a security boundary via the `LibraryADT` interface, defining operational capabilities without leaking architectural details. By enforcing constraints like encapsulated tree pointers (`private Book root;`) and protected stack arrays (`private Stack<Book> stack;`), the internal states remain unexposed to client-side routines.

### 2. Catalogue Architecture: Binary Search Tree (BST)
The `Book` class acts as both the business entity and the tree node, containing bibliographic metadata alongside self-referential `left` and `right` node pointers.
* **Recursive Insertion:** Rejects duplicate ISBNs and dynamically navigates the tree structure.
* **Logarithmic Search:** Discards half the search space at every step, allowing rapid lookups.
* **Multi-Scenario Deletion:** When a book is borrowed, it is excised via structural deletion, correctly handling leaf nodes, single children, and nodes with two children via an In-Order Successor.

### 3. Transaction Registry: Stack Design
Built around `BorrowStack`, the system wraps Java's built-in `java.util.Stack` inside a custom wrapper to block unauthorized structural modifications.
* **Null-Safety Boundaries:** An absolute guard rail rejects null pointers, preventing runtime NullPointerExceptions.
* **Reverse-Chronological Rendering:** Iterates via a reverse index loop (`size() - 1` down to `0`), displaying history correctly without wiping the stack data.

### 4. Integration & UI Layer
The `SmartLibrary` class links the modules into an atomic transaction flow encompassing Query Despatch, History Preservation, Catalogue Excision, and Fault Handling. The system utilizes robust type-parsing scanners (`Integer.parseInt`) to catch `NumberFormatException` errors, ensuring invalid text inputs loop back to the menu cleanly instead of crashing the program.

## ⚡ Performance Analysis

| System Operation | Underlying Structure | Asymptotic Complexity | Algorithmic Rationale |
| :--- | :--- | :--- | :--- |
| **Add Book** | BST Dynamic Insertion | Avg: O(log n) <br> Worst: O(n) | Each step eliminates half the remaining paths. |
| **Search Book** | BST Key Comparison | Avg: O(log n) <br> Worst: O(n) | Logarithmic depth ensures fast lookups by comparing keys. |
| **Borrow Book** | BST Search + Delete + Stack Push | Avg: O(log n) | Combines a search lookup, tree re-linking, and a constant time stack push. |
| **View History** | Sequential Stack Traversal | Strictly O(k) | Requires rendering every borrowed entry exactly once (k = size of ledger). |

*Note on Bottlenecks:* BST performance can degrade to linear time if keys are added in strictly sorted order. Future iterations may adopt self-balancing trees (like AVL or Red-Black Trees) to maintain optimal structure.

## 👨‍💻 Team Distribution

| Engineer                                 | Role Assignment | Core Architectural Responsibility |
|:-----------------------------------------| :--- | :--- |
| **Chen Bingyan** <br>*(24078302)*        | Borrowing History | Constructed the `BorrowStack` transactional layer, enforced null-safety, and implemented non-destructive reverse-index stack traversal. |
| **Beby Azzura** <br>*(24201647)*         | Integration & UI | Wrote the main loop console harness, implemented safe token-based input scanners, and fused the BST-to-Stack workflow. |
| **Yudong Chen** <br>*(24074901)*         | Record Finder | Designed and optimized the recursive lookup algorithms establishing key-matching conditional branches. |
| **Hilal Mumtaz S. H.** <br>*(24200675)*  | ADT Designer | Authored the structural contract (`LibraryADT`), governed architectural compliance, and verified information hiding. |
| **Nazeef Nuwaisir Khan** <br>*(24085530)*| Catalogue Architect | Engineered the `Book` node topology and recursive leaf/branch node balancing insertion mechanics. |
