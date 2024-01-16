package org.example;


import org.example.models.userAccountManager;

import java.sql.SQLException;

/**
 * Utility class for creating user accounts.
 */
public class Users {
    /**
     * Constructor for the Users class.
     */
    public Users(){

    }
    /**
     * Creates a new user account based on user input.
     *
     * @param isNewAdmin Indicates whether the account being created is an admin account.
     */
    public static void createAccounts(boolean isNewAdmin) {
        try {
            System.out.println("Please enter a username:");
            String name = InputChecker.getStringInput();
            while (userAccountManager.getUserId(name) != null) {
                System.out.println("Sorry, that username is taken. Please select another:");
                name = InputChecker.getStringInput();
            }

            System.out.println("Please enter a password:");
            String password1 = InputChecker.getStringInput();
            System.out.println("Please re-enter your password:");
            String password2 = InputChecker.getStringInput();
            while (password1.compareTo(password2) != 0) {
                System.out.println("Your passwords must match!\nPlease select a password:");
                password1 = InputChecker.getStringInput();
                System.out.println("Please re-enter your password:");
                password2 = InputChecker.getStringInput();
            }

            System.out.println("Please select a unique ID number:");
            int number = InputChecker.getPositiveInteger();

            while (userAccountManager.getUsername(number) != null) {
                System.out.println("Sorry, that ID number is taken. Please select another:");
                number = InputChecker.getPositiveInteger();
            }

            System.out.println("Please enter your phone number:");
            String phoneNumber = InputChecker.getStringInput();

            System.out.println("Please enter your email:");
            String email = InputChecker.getStringInput();

            System.out.println("Please enter your full name:");
            String fullName = InputChecker.getStringInput();


            System.out.println("Please enter your balance:");
            double balance = InputChecker.getBalanceInput();


            // Add the user to the userAccountManager
            userAccountManager.addUser(number, name, password2, phoneNumber, email, fullName, isNewAdmin, balance);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
