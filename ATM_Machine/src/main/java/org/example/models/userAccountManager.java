package org.example.models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * This class manages user accounts, including operations such as creating, updating, and deleting accounts,
 * as well as handling user login and privileges.
 *
 */
public class userAccountManager {
    public final static String GUEST_USERNAME = "Guest";
    public final static String GUEST_PASSWORD = "Guest";

    private static String loggedInUsername = GUEST_USERNAME, loggedFullName;


    /**
     * Retrieves the user object matching a given username.
     *
     * @param username The username of the user object to retrieve.
     * @return The user object possessing the given username.
     * @throws SQLException if a database access error occurs.
     */
    private static userAccount getUser(String username) throws SQLException {
        List<userAccount> userResults = dbManager.getInstance().getUserDao().queryForEq(userAccount.USERNAME_FIELD_NAME, username);
        if (userResults.isEmpty()) {
            return null;
        }
        return userResults.get(0);
    }

    /**
     * @return the username string of the currently logged in user (default "Guest")
     */
    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static String getLoggedFullName(){
        return loggedFullName;
    }

    public static void setLoggedFullName(String newName){
        loggedFullName = newName;
    }

    /**
     * Generates a new 'user' object and stores that object within the database
     * @author Jamie Denovan
     * @param id The user ID (unique int)
     * @param username The username (unique string)
     * @param password_raw The user's password, in plaintext
     * @param phoneNumber
     * @param email
     * @param fullName
     * @param isAdmin boolean flag for user privileges
     */
    public static boolean addUser(int id, String username, String password_raw, String phoneNumber, String email,
                                  String fullName, boolean isAdmin, double balance) throws SQLException {
        if (username == null || username.compareTo("") == 0) {
            return false;
        }
        if (password_raw == null || password_raw.compareTo("") == 0) {
            return false;
        }
        userAccount newUser = new userAccount(id, username, password_raw, phoneNumber, email, fullName, isAdmin, balance);
        loggedInUsername = username;
        loggedFullName = fullName;
        return dbManager.getInstance().getUserDao().create(newUser) == 1;
    }


    /**
     * Verifies a username + password pair and updates the class attribute tracking the currently logged user
     * @param username The username to log into
     * @param password_raw The password of the user (in plaintext)
     * @return Whether the operation was successful
     */
    public static boolean loginUser(String username, String password_raw, int id) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null ) {
            return false;
        }
        int userID = retrievedUser.getId();

        if (id != userID){
            return false;
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password_raw.getBytes());
            String password_md5 = String.format("%032x", new BigInteger(1, md5.digest()));
            if (password_md5.compareTo(retrievedUser.getPassword_md5()) == 0) {
                loggedInUsername = username;
                loggedFullName = retrievedUser.getFullName();
                return true;
            }
            return false;
        } catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }


    /**
     * @param username The username of the user to delete
     * @return Whether the operation was successful
     */
    public static boolean deleteUser(String username) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        return dbManager.getInstance().getUserDao().delete(retrievedUser) == 1;
    }

    /**
     * @param username The username of an arbitrary user
     * @return Whether the user (a) exists AND (b) has Admin privileges
     */
    public static boolean isUserAdmin(String username) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        return retrievedUser.isAdmin();
    }

    /**
     * @param username The username of the user
     * @return The user ID
     */
    public static Integer getUserId(String username) throws SQLException {
        userAccount retrievedUser = getUser(username);

        if (retrievedUser == null) {
            return null;
        }
        return retrievedUser.getId();
    }


    /**
     * @param id The id of the user
     * @return The username of the user
     */
    public static String getUsername(int id) throws SQLException {
        userAccount retrievedUser = dbManager.getInstance().getUserDao().queryForId(id);
        if (retrievedUser == null) {
            return null;
        }
        return retrievedUser.getUsername();
    }
    /**
     * Transfers funds from one user to another.
     *
     * @param supplierID    The ID of the user supplying funds.
     * @param receiverID    The ID of the user receiving funds.
     * @param transferFunds The amount of funds to transfer.
     * @return true if the transfer was successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean transferOfFunds(int supplierID, int receiverID, double transferFunds) throws SQLException {
        userAccount receiverUser = dbManager.getInstance().getUserDao().queryForId(receiverID);
        String recieverUserName = getUsername(receiverID);
        userAccount supplierUser = dbManager.getInstance().getUserDao().queryForId(supplierID);
        String supplierUserName = getUsername(supplierID);
        if (!setUserBalanceDeposit(recieverUserName, transferFunds)){
            return false;
        }
        if (!setUserBalanceWithdraw(supplierUserName, transferFunds)){

            return false;
        }

        return (dbManager.getInstance().getUserDao().update(receiverUser) == 1) && (dbManager.getInstance().getUserDao().update(supplierUser) == 1);
    }

    /**
     * @param username The username of the user to update
     * @param new_id The integer to set as the user's ID
     * @return Whether the operation was successful
     */
    public static boolean setUserId(String username, int new_id) throws SQLException {
        userAccount existingUserWithNewId = dbManager.getInstance().getUserDao().queryForId(new_id);
        if (existingUserWithNewId != null) {
            return false;
        }
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }

        return dbManager.getInstance().getUserDao().updateId(retrievedUser, new_id) == 1;
    }

    /**
     * @param oldUsername The original username of the user to update
     * @param newUsername The new username of the user
     * @return Whether the operation was successful
     */
    public static boolean setUsername(String oldUsername, String newUsername) throws SQLException {
        if (newUsername == null || newUsername.compareTo("") == 0) {
            return false;
        }
        List<userAccount> existingUserWithNewUsernameResults = dbManager.getInstance().getUserDao().queryForEq(userAccount.USERNAME_FIELD_NAME, newUsername);
        if (!existingUserWithNewUsernameResults.isEmpty()) {
            return false;
        }
        userAccount retrievedUser = getUser(oldUsername);
        if (retrievedUser == null) {
            return false;
        }
        retrievedUser.setUsername(newUsername);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;
    }

    /**
     * @param username The username of the user to update
     * @param password The new password of the user (in plaintext)
     * @return Whether the operation was successful
     */
    public static boolean setUserPassword(String username, String password) throws SQLException {
        if (password == null || password.compareTo("") == 0) {
            return false;
        }
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        retrievedUser.setPassword(password);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;
    }

    /**
     * @param username The username of the user to update
     * @param email The new email of the user
     * @return Whether the operation was successful
     */
    public static boolean setUserEmail(String username, String email) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        retrievedUser.setEmail(email);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;
    }
    /**
     * @param username The username of the user to update
     * @param phoneNumber The new phone number of the user
     * @return Whether the operation was successful
     */

    public static boolean setUserPhoneNumber(String username, String phoneNumber) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        retrievedUser.setPhoneNumber(phoneNumber);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;
    }


    public static boolean setUserBalanceDeposit(String username, double deposit) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        double currentBalance = retrievedUser.getBalance();
        retrievedUser.setBalance(currentBalance + deposit);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;


    }
    public static boolean setUserBalanceWithdraw(String username, double withdraw) throws SQLException {

        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        double currentBalance = retrievedUser.getBalance();
        if ((currentBalance - withdraw) < 0){
            System.out.println("Man, you are trying to withdraw money you do not have :/");
            return false;
        }
        retrievedUser.setBalance(currentBalance - withdraw);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;


    }
    /**
     * @param username The username of the user to update
     * @param fullName The new full name of the user
     * @return Whether the operation was successful
     */
    public static boolean setUserFullName(String username, String fullName) throws SQLException {
        userAccount retrievedUser = getUser(username);
        if (retrievedUser == null) {
            return false;
        }
        retrievedUser.setFullName(fullName);
        return dbManager.getInstance().getUserDao().update(retrievedUser) == 1;
    }


}