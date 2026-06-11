import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages late fees and student balances for the Smart Library system.
 * Calculates fines based on overdue days and tracks each student's balance.
 */
public class FineManager {

    // Fine rate per day
    private static final double FINE_PER_DAY = 0.50;

    // Standard loan period in days before a book is considered overdue
    private static final int LOAN_PERIOD_DAYS = 14;

    // Stores each student's total outstanding fine balance
    // key = studentId, value = total fine amount
    private HashMap<String, Double> studentBalances;

    // Constructor initializes an empty balance map
    public FineManager() {
        this.studentBalances = new HashMap<>();
    }

    // Calculates the fine for a given loan record
    public double calculateFine(LoanRecord record) {
        LocalDate dueDate = record.getBorrowDate().plusDays(LOAN_PERIOD_DAYS);
        LocalDate today = LocalDate.now();

        long overdueDays = ChronoUnit.DAYS.between(dueDate, today);

        if (overdueDays <= 0) {
            return 0.0;
        }

        return overdueDays * FINE_PER_DAY;
    }

    // Adds a calculated fine to a student's balance
    public void addFine(String studentId, double amount) {
        double currentBalance = studentBalances.getOrDefault(studentId, 0.0);
        studentBalances.put(studentId, currentBalance + amount);
    }

    // Processes a fine for a loan record and prevents duplicate fine charging
    public void processFine(LoanRecord record) {
    String studentId = record.getStudentId();

    // Check if fine was already charged
    if (record.isFineProcessed()) {
        System.out.println("Fine has already been processed for student: " + studentId);
        return;
    }

    double fine = calculateFine(record);

    if (fine > 0) {
        addFine(studentId, fine);
        record.markFineProcessed(); // Only mark if fine > 0
        System.out.println("Fine of RM " + String.format("%.2f", fine)
                + " added to student: " + studentId);
    } else {
        // Do NOT mark fine as processed if fine is 0
        System.out.println("No fine currently due for student: " + studentId);
    }
}

    // Pays off a student's balance fully
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

    // Shows the fine balance for a specific student
    public void showStudentBalance(String studentId) {
        double balance = studentBalances.getOrDefault(studentId, 0.0);

        System.out.println("Student: " + studentId
                + " | Outstanding Balance: RM " + String.format("%.2f", balance));
    }

    // Displays all student balances tracked in the system
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