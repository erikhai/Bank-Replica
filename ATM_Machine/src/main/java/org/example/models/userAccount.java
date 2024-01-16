package org.example.models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * This class represents a user account and includes methods for managing user details,
 * such as creating a new user, setting and retrieving user information, and handling password hashing.
 *
 */
@DatabaseTable
public class userAccount {

    public final static String ID_FIELD_NAME = "id";
    public final static String USERNAME_FIELD_NAME = "username";
    public final static String PHONE_NUMBER_FIELD_NAME = "phoneNumber";
    public final static String EMAIL_FIELD_NAME = "email";
    public final static String FULL_NAMEL_FIELD_NAME = "fullName";
    public final static String PASSWORD_FIELD_NAME = "password_md5";
    public final static String IS_ADMIN_FIELD_NAME = "isAdmin";
    public final static String USER_BALANCE_FIELD_NAME = "balance";

    @DatabaseField(id = true)
    private static int id;

    @DatabaseField(unique = true, canBeNull = false)
    private static String username;

    @DatabaseField
    private static String phoneNumber;

    @DatabaseField
    private static String email;

    @DatabaseField
    private static String fullName;

    @DatabaseField(canBeNull = false)
    private String password_md5;

    @DatabaseField(canBeNull = false)
    private boolean isAdmin;

    @DatabaseField(canBeNull = false)
    private static double balance;



    /**
     * Default constructor required by ORMLite.
     */
    userAccount() {
        // all persisted classes must define a no-arg constructor with at least package
        // visibility
    }

    /**
     * Constructor for creating a new user account.
     *
     * @param id           The unique ID of the user.
     * @param username     The username of the user.
     * @param password_raw The raw (unhashed) password of the user.
     * @param phoneNumber  The phone number of the user.
     * @param email        The email address of the user.
     * @param fullName     The full name of the user.
     * @param isAdmin      A flag indicating whether the user has admin privileges.
     * @param balance      The initial balance of the user's account.
     */
    public userAccount(int id, String username, String password_raw, String phoneNumber, String email, String fullName, Boolean isAdmin, double balance) {
        try {
            this.id = id;

            this.username = username;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.fullName = fullName;
            this.isAdmin = isAdmin;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password_raw.getBytes());
            this.password_md5 = String.format("%032x", new BigInteger(1, md5.digest()));
            this.balance = balance;
        } catch (NoSuchAlgorithmException ex) {}
    }
    /**
     * Retrieves the current balance of the user's account.
     *
     * @return The user's account balance.
     */
    public static double getBalance(){return balance;}
    /**
     * Sets the balance of the user's account.
     *
     * @param balance The new balance to set.
     */
    public void setBalance(double balance){this.balance = balance;}

    /**
     * @return the id
     */
    public static int getId() {
        return id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the md5 hash of the password
     */
    public String getPassword_md5() {
        return password_md5;
    }

    /**
     * @param password_raw the (unhashed) password to set
     */
    public void setPassword(String password_raw) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password_raw.getBytes());
            this.password_md5 = String.format("%032x", new BigInteger(1, md5.digest()));
        } catch (NoSuchAlgorithmException ex) {}
    }

    /**
     * @return whether the user is an Admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }


    /**
     * Retrieves a string representation of the user's details.
     *
     * @return A string containing the user's details.
     */
    public static String userDetails() {
        return "These are your details\nID: " + id + "\nUser Name: " + username + "\nFull Name: " + fullName + "\nPhone: " + phoneNumber +
                "\nEmail: " + email + "\nBalance: " + balance + "\n";
    }
}