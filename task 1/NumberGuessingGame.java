import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * NumberGuessingGame - A console-based number guessing game in Java.
 
 * Features:
 *  - Random number generation within a custom range
 *  - Limited attempts per round
 *  - Too high / Too low feedback
 *  - Multiple rounds with play-again option
 *  - Score tracking (rounds won + best guess count)
 */
public class NumberGuessingGame {

    // ─── Constants ────────────────────────────────────────────────
    static final int DEFAULT_MIN     = 1;
    static final int DEFAULT_MAX     = 100;
    static final int DEFAULT_MAX_ATT = 7;

    // ─── Game State ───────────────────────────────────────────────
    static int  roundNumber  = 1;
    static int  totalWins    = 0;
    static int  bestGuesses  = Integer.MAX_VALUE;  // fewest guesses in a won round

    static final Scanner scanner = new Scanner(System.in);
    static final Random  random  = new Random();

    // ══════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        printBanner();

        // ── Read settings once (reused across rounds) ──
        int min    = readInt("Enter range minimum   (default " + DEFAULT_MIN + "): ",
                             DEFAULT_MIN, 0, 9999);
        int max    = readInt("Enter range maximum   (default " + DEFAULT_MAX + "): ",
                             DEFAULT_MAX, min + 1, 9999);
        int maxAtt = readInt("Enter max attempts    (default " + DEFAULT_MAX_ATT + "): ",
                             DEFAULT_MAX_ATT, 1, 20);

        // ── Play rounds ──
        boolean playAgain = true;
        while (playAgain) {
            playRound(min, max, maxAtt);
            printStats();
            playAgain = askYesNo("\nPlay another round? (y/n): ");
            if (playAgain) roundNumber++;
        }

        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       Thanks for playing!    ║");
        System.out.printf ("║  Final score: %2d round(s) won ║%n", totalWins);
        if (bestGuesses < Integer.MAX_VALUE)
            System.out.printf("║  Best round : %2d guess(es)    ║%n", bestGuesses);
        System.out.println("╚══════════════════════════════╝");
        scanner.close();
    }

    // ══════════════════════════════════════════════════════════════
    //  PLAY ONE ROUND
    // ══════════════════════════════════════════════════════════════
    static void playRound(int min, int max, int maxAtt) {
        int secret     = random.nextInt(max - min + 1) + min;
        int attLeft    = maxAtt;
        int guessCount = 0;
        List<String> history = new ArrayList<>();

        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.printf ("│  Round %-3d  |  Range: %d – %d%n", roundNumber, min, max);
        System.out.printf ("│  You have %d attempt(s). Good luck!%n", maxAtt);
        System.out.println("└─────────────────────────────────────┘");

        while (attLeft > 0) {
            System.out.printf("%nAttempts left: %s%n", dotsBar(maxAtt, attLeft));
            System.out.print("Your guess: ");

            int guess;
            try {
                guess = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clear bad input
                System.out.println("  ✖  Please enter a valid number.");
                continue;
            }

            // Range validation
            if (guess < min || guess > max) {
                System.out.printf("  ✖  Please enter a number between %d and %d.%n", min, max);
                continue;
            }

            guessCount++;
            attLeft--;

            if (guess == secret) {
                // ── WIN ──
                history.add(guess + " ✓");
                System.out.println("\n  ✔  CORRECT! The number was " + secret + ".");
                System.out.println("  ✔  You guessed it in " + guessCount +
                        " guess" + (guessCount > 1 ? "es" : "") + ".");
                printHistory(history);
                totalWins++;
                if (guessCount < bestGuesses) bestGuesses = guessCount;
                return;

            } else if (guess < secret) {
                // ── TOO LOW ──
                System.out.println("  ↑  Too low!");
                history.add(guess + " ↑");

            } else {
                // ── TOO HIGH ──
                System.out.println("  ↓  Too high!");
                history.add(guess + " ↓");
            }

            if (attLeft == 0) {
                // ── LOSE ──
                System.out.println("\n  ✖  Out of attempts! The number was " + secret + ".");
                printHistory(history);
                return;
            }
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════════════════════

    /** Prints a visual dot-bar for remaining attempts. */
    static String dotsBar(int max, int left) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            sb.append(i < left ? "● " : "○ ");
        }
        return sb.toString().trim();
    }

    /** Prints the guess history for the round. */
    static void printHistory(List<String> history) {
        System.out.print("  History: ");
        System.out.println(String.join("  |  ", history));
    }

    /** Prints the running score. */
    static void printStats() {
        System.out.println("\n──────────────────────────────");
        System.out.printf("  Round: %-3d  |  Won: %-3d%n", roundNumber, totalWins);
        if (bestGuesses < Integer.MAX_VALUE)
            System.out.printf("  Best performance: %d guess(es)%n", bestGuesses);
        System.out.println("──────────────────────────────");
    }

    /**
     * Reads an integer from the user with a default fallback.
     * Keeps asking until input is within [lo, hi].
     */
    static int readInt(String prompt, int defaultVal, int lo, int hi) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return defaultVal;
            try {
                int val = Integer.parseInt(line);
                if (val >= lo && val <= hi) return val;
                System.out.printf("  Please enter a value between %d and %d.%n", lo, hi);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input — using default: " + defaultVal);
                return defaultVal;
            }
        }
    }

    /** Asks a yes/no question and returns true for 'y'. */
    static boolean askYesNo(String prompt) {
        System.out.print(prompt);
        String ans = scanner.nextLine().trim().toLowerCase();
        return ans.equals("y") || ans.equals("yes");
    }

    /** Prints the game banner. */
    static void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       NUMBER  GUESSING  GAME         ║");
        System.out.println("║   Guess the secret number to win!    ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
    }
}
