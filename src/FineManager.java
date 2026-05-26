import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

//aigoii

/**
 * Manages late fees and student balances for the Smart Library system.
 * Calculates fines based on overdue days and tracks each student's balance.
 */

public class FineManager {

    // fine rate per day
    private static final double FINE_PER_DAY = 0.50;

    // standard loan period in days before a book is considered overdue
    private static final int LOAN_PERIOD_DAYS = 14;

    // stores each students total outstanding fine balance
    // rmb key = studentId, value = total fine amount
    private HashMap<String, Double> studentBalances;

    // constructor initializes an empty balance map
    public FineManager() {
        this.studentBalances = new HashMap<>();
    }

    // Fine calculation

    /**
     * Calculates the fine for a given loan record based on how overdue it is.
     * Fine = overdue days x RM0.50 per day.
     * Returns 0.0 if the book is returned on time.
     */

    public double calculateFine(LoanRecord record) {
        // figure out the due date based on when the book was borrowed
        LocalDate dueDate = record.getBorrowDate().plusDays(LOAN_PERIOD_DAYS);
        LocalDate today = LocalDate.now();

        // calculate how many days overdue
        long overdueDays = ChronoUnit.DAYS.between(dueDate, today);

        // no fine if returned on time or early
        if (overdueDays <= 0) {
            return 0.0;
        }

        return overdueDays * FINE_PER_DAY;
    }

    // Balance tracking

    /**
     * Adds a calculated fine to a student's balance.
     * Creates a new entry if the student has no existing balance.
     */

    public void addFine(String studentId, double amount) {
        // get existing balance or default to 0 if student is new
        double currentBalance = studentBalances.getOrDefault(studentId, 0.0);
        studentBalances.put(studentId, currentBalance + amount);
    }

    /**
     * Processes a fine for a loan record and adds it to the student's balance.
     * Combines calculateFine() and addFine() into one step.
     */

    public void processFine(LoanRecord record) {
        double fine = calculateFine(record);
        String studentId = record.getStudentId();

        if (fine > 0) {
            addFine(studentId, fine);
            System.out.println("Fine of RM " + String.format("%.2f", fine)
                    + " added to student: " + studentId);
        } else {
            System.out.println("No fine for student: " + studentId + " (returned on time).");
        }
    }

    /**
     * Pays off a student's balance fully.
     * Resets their balance to 0.
     */

    public void payFine(String studentId) {
        if (!studentBalances.containsKey(studentId) || studentBalances.get(studentId) == 0.0) {
            System.out.println("No outstanding balance for student: " + studentId);
            return;
        }
        double paid = studentBalances.get(studentId);
        studentBalances.put(studentId, 0.0);
        System.out.println("Payment of RM " + String.format("%.2f", paid)
                + " received. Balance cleared for student: " + studentId);
    }

    // Display methods

    /**
     * Shows the fine balance for a specific student.
     */

    public void showStudentBalance(String studentId) {
        double balance = studentBalances.getOrDefault(studentId, 0.0);
        System.out.println("Student: " + studentId
                + " | Outstanding Balance: RM " + String.format("%.2f", balance));
    }

    /**
     * Displays all student balances tracked in the system.
     * Only shows students who have an outstanding fine.
     */

    public void showAllBalances() {
        if (studentBalances.isEmpty()) {
            System.out.println("No fine records found.");
            return;
        }

        System.out.println("\n--- Student Fine Balances ---");
        boolean hasOutstanding = false;

        for (Map.Entry<String, Double> entry : studentBalances.entrySet()) {
            if (entry.getValue() > 0) {
                System.out.println("Student: " + entry.getKey()
                        + " | Balance: RM " + String.format("%.2f", entry.getValue()));
                hasOutstanding = true;
            }
        }

        if (!hasOutstanding) {
            System.out.println("All students have cleared their balances.");
        }
        System.out.println("-----------------------------");
    }
}