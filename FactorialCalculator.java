//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


//import library for input
import java.util.Scanner;
import java.util.InputMismatchException;

public class FactorialCalculator {

  // recursive method to calculate factorial
  public static int factorialRecursive(int n) {
    // return case for stop looping
    if (n == 0) {
      return 1;
    }
    return n * factorialRecursive(n - 1);
  }

  // interative method to calculate factorial
  public static int factorialInterative(int n) {
    int result = 1;
    for (int i = 1; i <= n; i++) {
      result *= i;
    }
    return result;
  }

  //calculate the sum of all integer fom 1 to n
  public static int sumOfIntegers(int n) {
    int sum = 0;
    for (int i=1; i<=n; i++) {
      sum += i;
    }
    return sum;
  }

  // start main class
  public static void main(String[] args) {

    try {
      // user input
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a number to calculate its factorial: ");
      int number = scan.nextInt();
      scan.close();

      //verification of negative input
      if (number < 0) {
        System.out.println("Factorial is not defined for negative numbers. Please enter a non-negative integer.");
        return;
      }

      // returning the result of both methods
      System.out.println("Factorial of " + number + " (recursive): " + factorialRecursive(number));
      System.out.println("Factorial of " + number + " (iterative): " + factorialInterative(number));
      System.out.println("Sum of integers from 1 to " + number + ": " + sumOfIntegers(number));

    //verification of invalid input
    } catch (InputMismatchException e) {
      System.out.println("Invalid input. Please enter a valid integer.");
    } finally {
      System.out.println("Program has ended.");
    }
  }
}