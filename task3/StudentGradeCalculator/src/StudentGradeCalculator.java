import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════╗
 *    Student Grade Calculator - Java Project
 *    Author  : Your Name
 *    Version : 1.0
 * ╚══════════════════════════════════════════╝
 *
 * Features:
 *  - Takes marks for multiple subjects (out of 100)
 *  - Calculates total marks & average percentage
 *  - Assigns a letter grade with remark
 *  - Displays a clean formatted result card
 */
public class StudentGradeCalculator {

    // ─── Grade boundaries ───────────────────────────────────────────
    private static final double A_PLUS  = 90;
    private static final double A       = 80;
    private static final double B_PLUS  = 70;
    private static final double B       = 60;
    private static final double C_PLUS  = 50;
    private static final double C       = 40;
    // below 40 → F

    // ─── Entry point ─────────────────────────────────────────────────
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        printBanner();

        // ── Student details ──
        System.out.print("  Enter Student Name  : ");
        String studentName = sc.nextLine().trim();

        System.out.print("  Enter Roll Number   : ");
        String rollNumber = sc.nextLine().trim();

        // ── Number of subjects ──
        int numSubjects = 0;
        while (true) {
            System.out.print("  How many subjects?  : ");
            try {
                numSubjects = Integer.parseInt(sc.nextLine().trim());
                if (numSubjects <= 0) {
                    System.out.println("  [!] Please enter a positive number.\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Numbers only.\n");
            }
        }

        // ── Subject names & marks ──
        String[] subjectNames = new String[numSubjects];
        double[] marks        = new double[numSubjects];

        System.out.println();
        System.out.println("  ── Enter subject-wise marks (out of 100) ──");
        System.out.println();

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("  Subject " + (i + 1) + " Name  : ");
            subjectNames[i] = sc.nextLine().trim();

            while (true) {
                System.out.print("  Marks Obtained    : ");
                try {
                    double m = Double.parseDouble(sc.nextLine().trim());
                    if (m < 0 || m > 100) {
                        System.out.println("  [!] Marks must be between 0 and 100.\n");
                    } else {
                        marks[i] = m;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  [!] Invalid input. Numbers only.\n");
                }
            }
            System.out.println();
        }

        // ── Calculations ──
        double totalMarks      = calculateTotal(marks);
        double averagePercent  = totalMarks / numSubjects;
        String grade           = assignGrade(averagePercent);
        String remark          = assignRemark(averagePercent);
        boolean isPass         = averagePercent >= 40;

        // ── Result card ──
        printResultCard(studentName, rollNumber, subjectNames, marks,
                        numSubjects, totalMarks, averagePercent, grade, remark, isPass);

        sc.close();
    }

    // ─── Calculate total marks ────────────────────────────────────────
    private static double calculateTotal(double[] marks) {
        double total = 0;
        for (double m : marks) total += m;
        return total;
    }

    // ─── Grade logic ──────────────────────────────────────────────────
    private static String assignGrade(double avg) {
        if (avg >= A_PLUS)  return "A+";
        if (avg >= A)       return "A";
        if (avg >= B_PLUS)  return "B+";
        if (avg >= B)       return "B";
        if (avg >= C_PLUS)  return "C+";
        if (avg >= C)       return "C";
        return "F";
    }

    // ─── Remark logic ─────────────────────────────────────────────────
    private static String assignRemark(double avg) {
        if (avg >= A_PLUS)  return "Outstanding  🏆";
        if (avg >= A)       return "Excellent    ⭐";
        if (avg >= B_PLUS)  return "Very Good    👍";
        if (avg >= B)       return "Good         ✔";
        if (avg >= C_PLUS)  return "Average      📘";
        if (avg >= C)       return "Pass         📋";
        return "Fail — Needs Improvement  ❌";
    }

    // ─── Banner ───────────────────────────────────────────────────────
    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════════╗");
        System.out.println("  ║       STUDENT GRADE CALCULATOR  v1.0          ║");
        System.out.println("  ╚═══════════════════════════════════════════════╝");
        System.out.println();
    }

    // ─── Result card ──────────────────────────────────────────────────
    private static void printResultCard(String name, String roll,
                                        String[] subjects, double[] marks,
                                        int n, double total, double avg,
                                        String grade, String remark, boolean pass) {

        System.out.println();
        System.out.println("  ┌──────────────────────────────────────────────┐");
        System.out.println("  │            ★  RESULT CARD  ★                 │");
        System.out.println("  ├──────────────────────────────────────────────┤");
        System.out.printf ("  │  Student Name  : %-28s│%n", name);
        System.out.printf ("  │  Roll Number   : %-28s│%n", roll);
        System.out.println("  ├──────────────────────────────────────────────┤");
        System.out.printf ("  │  %-20s  %-10s  %-10s │%n", "Subject", "Max Marks", "Obtained");
        System.out.println("  │  ─────────────────────────────────────────── │");

        for (int i = 0; i < n; i++) {
            System.out.printf("  │  %-20s  %-10s  %-10.1f │%n",
                              subjects[i], "100", marks[i]);
        }

        System.out.println("  ├──────────────────────────────────────────────┤");
        System.out.printf ("  │  Total Marks      : %5.1f / %-17s│%n", total, (n * 100) + "");
        System.out.printf ("  │  Average %%        : %5.1f%%%-18s│%n", avg, "");
        System.out.printf ("  │  Grade            : %-28s│%n", grade);
        System.out.printf ("  │  Remark           : %-28s│%n", remark);
        System.out.printf ("  │  Result           : %-28s│%n", pass ? "PASS ✅" : "FAIL ❌");
        System.out.println("  └──────────────────────────────────────────────┘");
        System.out.println();
    }
}
