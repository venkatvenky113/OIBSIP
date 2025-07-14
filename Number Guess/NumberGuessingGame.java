import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        int totalScore = 0;
        boolean playAgain;

        System.out.println("🎮 Welcome to the Number Guessing Game!");

        do {
            int secretNumber = random.nextInt(100) + 1; // 1 to 100
            int maxAttempts = 5;
            int attempt = 0;
            int points = 0;
            boolean guessedCorrectly = false;

            System.out.println("\n🤔 I have selected a number between 1 and 100.");
            System.out.println("💡 You have " + maxAttempts + " attempts to guess it.");

            while (attempt < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attempt++;

                if (userGuess == secretNumber) {
                    guessedCorrectly = true;
                    points = (maxAttempts - attempt + 1) * 10;
                    totalScore += points;
                    System.out.println("🎉 Correct! You've guessed it in " + attempt + " attempts.");
                    System.out.println("🏆 Points this round: " + points);
                    break;
                } else if (userGuess < secretNumber) {
                    System.out.println("🔼 Too low! Try a higher number.");
                } else {
                    System.out.println("🔽 Too high! Try a lower number.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("❌ Sorry! You've used all your attempts.");
                System.out.println("🕵️ The correct number was: " + secretNumber);
            }

            System.out.print("\nDo you want to play another round? (yes/no): ");
            playAgain = sc.next().equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("\n📊 Game Over! Your total score: " + totalScore);
        System.out.println("🙏 Thanks for playing!");
    }
}
