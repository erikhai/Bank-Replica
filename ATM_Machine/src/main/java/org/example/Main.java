package org.example;

import org.example.models.userAccount;
import org.example.models.userAccountManager;

import java.sql.SQLException;



/**
 * The main class for the banking application.
 * Handles user login, account creation, and user interactions.
 * Author: Erik Hai
 */
public class Main {

    private static boolean loggedin = false;
    private static boolean isAdmin = false;


    /**
     * Handles various options for a general user.
     *
     * @param userInput The user's choice.
     * @throws SQLException If there is a database error.
     */
    public static void inputOptionsGeneralUser(int userInput) throws SQLException {
        String username = userAccountManager.getLoggedInUsername(); //Username of the logged in account
        int choice;
        switch (userInput){
            case 1:
                if (isAdmin){
                    System.out.println("Are you creating an admin account?\n1. Yes\n2. No");
                    choice = InputChecker.getUserInput(1,2);
                    if (choice == 1){
                        Users.createAccounts(true);
                        break;
                    }
                }
                Users.createAccounts(false);
                break;
            case 2:
                if (isAdmin){
                    while (true){
                        System.out.println("Please enter the id of the account you wish to change the details of");
                        int userID = InputChecker.getPositiveInteger();
                        username = userAccountManager.getUsername(userID); //Get the username associated with that ID
                        if (username != null){
                            break;
                        } else {
                            System.out.println("Please enter a valid ID");
                        }
                    }

                } else {
                    username = userAccountManager.getLoggedInUsername(); //Ensuring that the username is not changed
                }

                System.out.println("You can change the following details\n" +
                        "1. ID\n" +
                        "2. User Name\n" +
                        "3. Password\n" +
                        "4. Full Name\n" +
                        "5. Phone Number\n" +
                        "6. Email\n" +
                        "7. Go Back\n");
                choice = InputChecker.getUserInput(1,7);

                if (choice == 7){
                    break;
                } else if (choice == 1){
                    System.out.println("Please enter the new ID number:");
                    int newID = InputChecker.getPositiveInteger();
                    while (!userAccountManager.setUserId(username, newID)) {
                        System.out.println("Please choose another ID");
                        newID = InputChecker.getPositiveInteger();
                    }
                    System.out.println("The ID number has now been set to " + newID + ".");
                } else if (choice == 2){
                    System.out.println("Please enter the new username:");
                    String newUsername = InputChecker.getStringInput();

                    while (!userAccountManager.setUsername(username, newUsername)) {
                        System.out.println("Sorry, that username is taken. Please select another:");
                        newUsername = InputChecker.getStringInput();
                    }
                    System.out.println("The username has now been set to " + newUsername + ".");
                } else if (choice == 3){
                    System.out.println("Please enter the new password:");
                    String newp1 = InputChecker.getStringInput();
                    System.out.println("Please re-enter the new password:");
                    String newp2 = InputChecker.getStringInput();

                    while (newp1.compareTo(newp2) != 0) {
                        System.out.println("Your passwords must match!");
                        System.out.println("Please enter your new password:");
                        newp1 = InputChecker.getStringInput();
                        System.out.println("Please re-enter your new password:");
                        newp2 = InputChecker.getStringInput();
                    }
                    if (userAccountManager.setUserPassword(username, newp1)) {
                        System.out.println("The password has been reset.");
                    } else {
                        System.out.println("You have not entered a valid password");
                    }
                } else if (choice == 4){
                    System.out.println("Please enter the new name:");
                    String newName = InputChecker.getStringInput();
                    while (!userAccountManager.setUserFullName(username, newName)) {
                        System.out.println("Please input another name!");
                        newName = InputChecker.getStringInput();
                    }
                    System.out.println("The name has been changed to " + newName);
                    userAccountManager.setLoggedFullName(newName);

                } else if (choice == 5){
                    System.out.println("Please enter the new phone number:");
                    String newPhoneNumber = InputChecker.getStringInput();
                    while (!userAccountManager.setUserPhoneNumber(username, newPhoneNumber)) {
                        System.out.println("Please input another phone number!");
                        newPhoneNumber = InputChecker.getStringInput();
                    }
                    System.out.println("The phone number has been set to " + newPhoneNumber);
                } else if (choice == 6){
                    System.out.println("Please enter the new email address:");
                    String newEmail = InputChecker.getStringInput();
                    while (!userAccountManager.setUserEmail(username, newEmail)) {
                        System.out.println("Please input another email option!");
                        newEmail = InputChecker.getStringInput();
                    }
                    System.out.println("The email has been set to " + newEmail);
                }
                break;
            case 3:
                System.out.println(userAccount.userDetails());
                break;
            case 4:
                System.out.println("Hmm now we are talking!");
                System.out.println("How much do you wish to deposit?");
                double userDeposit = InputChecker.getPositiveDouble();
                while (!userAccountManager.setUserBalanceDeposit(username, userDeposit)){
                    System.out.println("""
                            Something went wrong
                            1. Re enter deposit
                            2. Go back
                            """);
                    choice = InputChecker.getUserInput(1,2);
                    if (choice == 2){
                        break;
                    } else {
                        userDeposit = InputChecker.getPositiveDouble();
                    }
                }
                break;
            case 5:

                System.out.println("How much do you wish to withdraw?");
                double userWithdrawal = InputChecker.getPositiveDouble();
                while (!userAccountManager.setUserBalanceWithdraw(username, userWithdrawal)){
                    System.out.println(
                            "1. Re enter withdrawal\n" +
                            "2. Go back\n");
                    choice = InputChecker.getUserInput(1,2);
                    if (choice == 2){
                        break;
                    } else {
                        userWithdrawal = InputChecker.getPositiveDouble();
                    }
                }
                break;
            case 6:
                boolean anotherAccount = false;
                if (isAdmin){
                    while (true){
                        System.out.println("Please enter the id of the account you wish to delete");
                        int userID = InputChecker.getPositiveInteger();
                        username = userAccountManager.getUsername(userID);
                        if (username != null){
                            System.out.println("Are you sure you want to remove this account?\n" +
                                    "1. Yes\n" +
                                    "2. No");
                            anotherAccount = true;
                            break;
                        } else {
                            System.out.println("Please enter a valid ID");
                        }
                    }

                } else {
                    username = userAccountManager.getLoggedInUsername();
                    System.out.println("Are you sure you want to remove your account with us?\n" +
                            "1. Yes\n" +
                            "2. No");
                }

                choice = InputChecker.getUserInput(1,2);
                if (choice == 1){
                    if (!userAccountManager.deleteUser(username)){
                        System.out.println("Something went wrong internally");
                    }
                    if (!anotherAccount){
                        loggedin = false;
                        isAdmin = false;
                        System.out.println("We are sad to see you go :(");
                    }

                }
                break;
            case 7:
                while(true){
                    System.out.println("Your balance is: " + userAccount.getBalance());
                    System.out.println("Please provide the ID of who you want to give money to.");
                    int id = InputChecker.getPositiveInteger();
                    int myID = userAccount.getId();
                    System.out.println("Please tell us the amount you wish to transfer: ");
                    double fundsForTransfer = InputChecker.getPositiveDouble();
                    if (userAccountManager.transferOfFunds(myID, id, fundsForTransfer)){
                        break;
                    } else {
                        System.out.println("Well we could not transfer the funds." +
                                "\n1. Go Back" +
                                "\n2. Retry");
                        choice = InputChecker.getUserInput(1,2);
                        if (choice == 1){
                            break;
                        }

                    }

                }
                break;



            default:
                break;
        }
    }

    /**
     * Main method to run the banking application.
     *
     * @param args Command line arguments (not used).
     * @throws SQLException If there is a database error.
     */
    public static void main(String[] args) throws SQLException {

        int userInput;
        while (true){
            if (loggedin){
                homepage();
            } else {
                System.out.println("Please select one of the following options");
                System.out.println("1. Login as an admin");
                System.out.println("2. Login as a customer");
                System.out.println("3. Create an account with us");
                System.out.println("4. Exit");
                userInput = InputChecker.getUserInput(1,4);
                if (userInput < 3) {
                    while (true) {
                        System.out.println("Please enter your username:");
                        String loginName = InputChecker.getStringInput();

                        System.out.println("Please enter your password:");
                        String password = InputChecker.getStringInput();

                        System.out.println("Please enter your id:");
                        int id = InputChecker.getPositiveInteger();

                        if (userAccountManager.loginUser(loginName, password, id)) {
                            loggedin = true;
                            if (userInput == 1){
                                isAdmin = true;
                            }
                            System.out.println("Hello there \nThis is your local bank.");
                            break;
                        } else {
                            System.out.println("Sorry, your username or password or id is incorrect.");
                        }
                    }
                } else if (userInput == 3) {
                    Users.createAccounts(false);
                    loggedin = true;
                    System.out.println("Hello there \nThis is your local bank.");
                } else if (userInput == 4){
                    break;

                }

            }



        }



    }

    /**
     * Displays the user's homepage based on their role.
     *
     * @throws SQLException If there is a database error.
     */
    public static void homepage() throws SQLException {

        if (!isAdmin){
            customerHomepage();
        } else {
            adminHomepage();
        }

    }
    /**
     * Displays the homepage for an admin user.
     *
     * @throws SQLException If there is a database error.
     */
    private static void adminHomepage() throws SQLException {
        int userInput;
        String fullName = userAccountManager.getLoggedFullName();
        System.out.println("You can perform a wide range of actions " + fullName + " \n" +
                "1. Make a new account\n" +
                "2. Update anyone's account details\n" +
                "3. View your account details\n" +
                "4. Make a deposit\n" +
                "5. Make a withdrawal\n" +
                "6. Close any account\n" +
                "7. Transfer money to another account\n" +
                "8. You do not want to do anything :/\n");
        System.out.println("Please Enter Your Choice:");

        userInput = InputChecker.getUserInput(1,8);


        if (userInput > 0 && userInput < 8) {
            inputOptionsGeneralUser(userInput);
        } else if (userInput == 8){
            System.out.println("Thanks for your time!");
            loggedin = false;
            isAdmin = false;
        }
    }
    /**
     * Displays the homepage for a customer user.
     *
     * @throws SQLException If there is a database error.
     */
    public static void customerHomepage() throws SQLException {
        int userInput;
        String fullName = userAccountManager.getLoggedFullName();
        System.out.println("You can perform a wide range of actions " + fullName + " \n" +
                "1. Make a new account\n" +
                "2. Update current account details\n" +
                "3. View your account details\n" +
                "4. Make a deposit\n" +
                "5. Make a withdrawal\n" +
                "6. Close your account\n" +
                "7. Transfer money to another account\n" +
                "8. You do not want to do anything :/\n");
        System.out.println("Please Enter Your Choice:");
        userInput = InputChecker.getUserInput(1,8);


        if (userInput > 0 && userInput < 8) {
            inputOptionsGeneralUser(userInput);
        } else if (userInput == 8){
            System.out.println("Thanks for your time!");
            loggedin = false;
            isAdmin = false;
        }
    }

}