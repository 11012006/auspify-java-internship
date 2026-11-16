import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Task 2 - Number Guessing Game (Console)
 * Features: Random number generation, user interaction, score tracking
 */
public class NumberGuessingGame {

    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int gamesPlayed = 0;
        int gamesWon = 0;
        int bestScore = Integer.MAX_VALUE; // fewest attempts in a win

        System.out.println("===== Number Guessing Game =====");
        System.out.println("I'm thinking of a number between " + MIN + " and " + MAX
                + ". You have " + MAX_ATTEMPTS + " attempts.");

        boolean playAgain = true;
        while (playAgain) {
            // Step 1: Generate a random number
            int target = random.nextInt(MAX - MIN + 1) + MIN;
            int attempts = 0;
            boolean won = false;
            gamesPlayed++;

            while (attempts < MAX_ATTEMPTS) {
                // Step 2: Take user input for guessing
                System.out.print("\nAttempt " + (attempts + 1) + "/" + MAX_ATTEMPTS + " - Enter your guess: ");
                int guess;
                try {
                    guess = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Please enter a valid number.");
                    continue; // invalid input does not use up an attempt
                }
                if (guess < MIN || guess > MAX) {
                    System.out.println("Guess must be between " + MIN + " and " + MAX + ".");
                    continue;
                }

                // Step 4: Count number of attempts
                attempts++;

                // Step 3: Provide hints (higher/lower)
                if (guess == target) {
                    won = true;
                    break;
                } else if (guess < target) {
                    System.out.println("Too low! Try a higher number.");
                } else {
                    System.out.println("Too high! Try a lower number.");
                }
            }

            // Step 5: Display result
            if (won) {
                gamesWon++;
                bestScore = Math.min(bestScore, attempts);
                System.out.println("\nCorrect! You guessed " + target + " in " + attempts + " attempt(s).");
            } else {
                System.out.println("\nOut of attempts! The number was " + target + ".");
            }

            // Score tracking
            System.out.println("--- Score Summary ---");
            System.out.println("Games played: " + gamesPlayed + " | Won: " + gamesWon
                    + " | Best (fewest attempts): " + (bestScore == Integer.MAX_VALUE ? "N/A" : bestScore));

            System.out.print("\nPlay again? (y/n): ");
            String ans = sc.next().trim().toLowerCase();
            playAgain = ans.equals("y") || ans.equals("yes");
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }
}
