package org.example;

import java.util.Scanner;
/**
 * Utility class for input validation and retrieval.
 * Provides methods for validating and getting user inputs.
 */
public class InputChecker {
    public static Scanner scanner = new Scanner(System.in);




    /**
     * Gets user input within a specified range.
     *
     * @param min The minimum valid integer.
     * @param max The maximum valid integer.
     * @return The user's choice within the specified range.
     */
    public static int getUserInput(int min, int max) {
        int num;
        while (true) {
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
                scanner.nextLine();
                if (num >= min && num <= max) {
                    return num;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ":");
                }
            } else if (scanner.hasNextLine()) {
                System.out.println("Please enter a number!");
                scanner.nextLine(); // consume rest of the current line to read future input
            }
        }
    }

    /**
     * Gets a positive integer from the user.
     *
     * @return A positive integer entered by the user.
     */

    public static int getPositiveInteger() {
        while (true) {
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                scanner.nextLine(); // consume the rest of input
                if (number >= 0) {
                    return number;
                } else {
                    System.out.println("Please enter a positive number:");
                }
            } else {
                System.out.println("Please enter a number:");
                scanner.nextLine(); // consume the rest of input
            }
        }

    }


    /**
     * Gets a positive double from the user.
     *
     * @return A positive double entered by the user.
     */
    public static double getPositiveDouble() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double number = scanner.nextDouble();
                scanner.nextLine(); // consume the rest of input
                if (number >= 0) {
                    return number;
                } else {
                    System.out.println("Please enter a positive number:");
                }
            } else {
                System.out.println("Please enter a number:");
                scanner.nextLine(); // consume the rest of input
            }
        }
    }
    /**
     * Gets a non-empty string input from the user.
     *
     * @return A non-empty string entered by the user.
     */
    public static String getStringInput() {
        String result;
        while (true) {
            result = scanner.nextLine();
            if (result.isEmpty()) {
                System.out.println("Please Enter Something!");
            } else {
                return result;
            }
        }
    }
    /**
     * Gets a positive balance input from the user.
     *
     * @return A positive balance entered by the user.
     */
    public static double getBalanceInput() {
        double result;
        while (true){
            if (scanner.hasNextDouble()) {
                result = scanner.nextDouble();
                if (result >= 0) {
                    // Round to two decimal places
                    result = Math.round(result * 100.0) / 100.0;
                    return result;
                } else {
                    System.out.println("Invalid input. Please enter a balance greater than or equal to 0.");
                    scanner.next();
                }
            } else {
                System.out.println("Invalid input. Please enter a valid balance.");
                scanner.next();
            }
        }
    }
}

