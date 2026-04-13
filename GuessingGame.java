//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    // incilialize a class
    private int numberToGuess;
    private int numberOfAttempts;
    private int playerNumber; // lest imput from player
    private Scanner scanner;
    private boolean gameOver;
    private int maxAttempts;

    // create a constuctor of class
    public GuessingGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100);
        numberOfAttempts = 0;
        scanner = new Scanner(System.in);
        playerNumber = -1; // innitial vallue
        gameOver = false;
        maxAttempts = 30; // max attempts
    }

    // create a method to start the game
    public void main() {
        System.out.println("Welcome to the Guessing Game!");
        System.out.println("I have selected a number between 0 and 99. Can you guess it?");

        while (!gameOver) {
            System.out.print("Enter your guess: ");
            playerNumber = scanner.nextInt();
            numberOfAttempts++;

            if (playerNumber < numberToGuess) {
                System.out.println("Too low! Try again.");
            } else if (playerNumber > numberToGuess) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("Congratulations! You've guessed the number in " + numberOfAttempts + " attempts.");
                gameOver = true;
            }

            if (numberOfAttempts >= maxAttempts) {
                System.out.println("Game over! You've used all your attempts. The number was: " + numberToGuess);
                gameOver = true;
            }
        }
    }
}