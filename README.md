<div align="center">

# 📚 Smart Library Management System

### *A Java-based terminal library management system powered by BSTs, Stacks, and HashMaps*

**Sem II 2025/2026** · Dr. Mohamed N. M. Lubani · Occurrence 10, Team 2

</div>

---

## 👥 Team

| 👤 Member | 🆔 Matric No. | 🛠️ Role |
|---|---|---|
| Nazeef Nuwaisir Khan | 24085530 | 🌳 Catalogue Architect |
| Chen Bingyan | 24078302 | 📚 Borrowing History |
| Yudong Chen | 24074901 | 🔍 Record Finder |
| Hilal Mumtaz Saleh Harahap | 24200675 | 🧩 ADT Designer |
| Beby Azzura | 24201647 | ⚙️ Admin Logic & Integration |

---

## 🌟 Overview

The **Smart Library Management System** is a terminal-based Java application that simulates the everyday operations of a university library. It uses a **dual-engine architecture**, pairing each type of data with the data structure best suited to its access pattern:

| 🗃️ Data | 🏗️ Structure | ⚡ Why |
|---|---|---|
| **Catalogue** | Binary Search Tree | O(log n) lookups, inserts, and deletes by ISBN |
| **Borrowing Registry** | Stack (LIFO) | Most recent borrow always shows first |
| **Fine Balances** | HashMap | O(1) average lookup/update per student |

A shared `LibraryADT` interface enforces a clean contract between the console UI and the backend — full **information hiding** across all internal structures.

---

## ✨ Features

### 🖥️ Main Menu

| # | Option | Description |
|---|--------|-------------|
| 1️⃣ | **Add Book** | Insert ISBN, title, author into the BST. Rejects duplicate ISBNs. |
| 2️⃣ | **Search Book** | Submenu: search by ISBN (O(log n)), Title, or Author (full traversal, case-insensitive). |
| 3️⃣ | **Borrow Book** | Creates a `LoanRecord`, pushes it to history, removes the book from the catalogue. |
| 4️⃣ | **View History** | Shows all borrowed books, most recent first (non-destructive). |
| 5️⃣ | **Fine Manager** | Calculate fines, view balances, pay fines. |
| 6️⃣ | **Demo Overdue Record** | Generates a sample overdue loan (RM 3.00 fine) for testing. |
| 7️⃣ | **Exit** | Terminates the program. |

> 💡 Options **1–4 & 7** form the original required five-option menu. Options **5 & 6** are enhancements layered on top, without changing the required flow.

---

## 🏛️ Architecture

```
SmartLibrary  ──implements──▶  LibraryADT
     │
     ├── BookBST        (catalogue, private root)
     ├── BorrowStack     (history, private Stack<LoanRecord>)
     └── FineManager     (balances, private HashMap<String, Double>)
```

### 🧱 Core Classes

<details>
<summary><b>📘 Book</b> — bibliographic entity & BST node</summary>

<br>

`isbn`, `title`, `author`, `left`, `right`

</details>

<details>
<summary><b>🌳 BookBST</b> — the catalogue engine</summary>

<br>

- `recursiveInsert()` — insert + reject duplicates
- `recursiveSearch()` — O(log n) ISBN lookup
- `recursiveDelete()` — handles all 3 BST deletion scenarios (leaf, single child, two children via in-order successor)
- `searchByTitle()` / `searchByAuthor()` — O(n) full traversal, case-insensitive

</details>

<details>
<summary><b>🧾 LoanRecord</b> — immutable transaction record</summary>

<br>

Snapshot of the borrowed book + `studentId` + `borrowDate` + one-way `fineProcessed` flag

</details>

<details>
<summary><b>📥 BorrowStack</b> — LIFO transaction ledger</summary>

<br>

- `push()` — null-safe insertion
- `show()` — non-destructive reverse traversal (most recent first)
- `getAllByStudent()` / `getLatestByStudent()` — fine-evaluation lookups

</details>

<details>
<summary><b>💰 FineManager</b> — overdue fine subsystem</summary>

<br>

- `calculateFine()` — RM 0.50/day, 14-day loan period
- `processFine()` — idempotent charging
- `addFine()`, `payFine()`, `showStudentBalance()`, `showAllBalances()`

</details>

<details>
<summary><b>🧩 LibraryADT</b> — interface contract</summary>

<br>

```java
public interface LibraryADT {
    void addBook(int isbn, String title, String author);
    Book searchBook(int isbn);
    void borrowBook(int isbn, String studentId);
    void viewLatestHistory();
}
```

</details>

---

## 🔐 Information Hiding

- 🌳 `BookBST.root` → `private`
- 📥 `BorrowStack.stack` → `private`
- 💰 Fine balance map → `private`
- 🏛️ `SmartLibrary`'s `catalogue`, `history`, `fineManager` → `private`
- 🧾 `LoanRecord` exposes data only via getters (`fineProcessed` flippable once)

---

## ⚡ Algorithmic Complexity

| Operation | Structure | Complexity |
|---|---|---|
| Add Book | BST Insert | Avg `O(log n)` / Worst `O(n)` |
| Search by ISBN | BST Search | Avg `O(log n)` / Worst `O(n)` |
| Search by Title/Author | Full Traversal | `O(n)` |
| Borrow Book | Search + Delete + Push | Avg `O(log n)` |
| View History | Stack Traversal | `O(k)` |
| Retrieve Student Records | Reverse Stack Scan | `O(k)` |
| Calculate Fine | Date Math + HashMap | `O(1)` per record |
| Lookup/View Balances | HashMap | `O(1)` avg / `O(s)` full |

---

## ✅ Input Validation

- All numeric input parsed as a `String` → `Integer.parseInt()` inside try/catch
- Invalid input (`"abc"`) → warning + return to menu (no crash)
- Empty title/author/keyword inputs trimmed and rejected before processing

---

## 🚀 Getting Started

```bash
javac *.java
java Main
```

---

## 🧪 Testing

All features were verified through a full QA matrix: invalid menu input, non-integer ISBNs, empty metadata, duplicate ISBNs, successful/failed search & borrow, LIFO history ordering, and the complete fine lifecycle (overdue, on-time, duplicate charging, balance viewing, payment).

**Result: ✅ All test cases PASSED**

---

## 🔗 Links & Resources

* 📄 **Documentation:** [Smart Library Technical Report (PDF)](./Smart%20Library%20Technical%20Report.pdf)

---

<div align="center">

*Built with ☕ and recursion by Team 2 — WIA1002 Data Structure*

</div>