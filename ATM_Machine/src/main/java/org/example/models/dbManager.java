package org.example.models;

import java.io.File;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


/**
 * Manages the database connection and provides access to DAOs.
 */
public class dbManager {


    private Dao<userAccount, Integer> userDao;
    private JdbcConnectionSource connSource;

    private static dbManager INSTANCE;
    /**
     * Constructor for the database manager.
     *
     * @param isTest Indicates whether the manager is used for testing.
     */
    dbManager(boolean isTest) {
        Logger.setGlobalLogLevel(Level.ERROR);
        try {
            if (!isTest) {
                // Load pre-existing DB from file
                File path = new File("ATM");
                connSource = new JdbcConnectionSource("jdbc:h2:file:" + path.getAbsolutePath());
            } else {
                // Create an empty temp DB in memory
                connSource = new JdbcConnectionSource("jdbc:h2:mem:ScrollAppTEST");
            }
            setupDatabase(connSource);

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Gets the instance of the database manager (Singleton pattern).
     *
     * @return The database manager instance.
     */
    public static dbManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new dbManager(false);
        }
        return INSTANCE;
    }



    /**
     * Sets up the database and DAOs.
     *
     * @param connectionSource The connection source for the database.
     * @throws SQLException If there is an error setting up the database.
     */
    private void setupDatabase(ConnectionSource connectionSource) throws SQLException {
        // Create DAOs for each class and associated table.
        userDao = DaoManager.createDao(connectionSource, userAccount.class);

        // Create tables if they do not exist.
        TableUtils.createTableIfNotExists(connectionSource, userAccount.class);

        // Create default user accounts if they do not exist.
        userAccount guestUser = new userAccount(0, userAccountManager.GUEST_USERNAME, userAccountManager.GUEST_PASSWORD, null, null, null, false, 0);
        userDao.createIfNotExists(guestUser);
        userAccount adminUser = new userAccount(1, "Admin", "Admin", null, null, null, true, 0);
        userDao.createIfNotExists(adminUser);

     
    }


    /**
     * Gets the DAO for user accounts.
     *
     * @return The DAO for user accounts.
     */
    public Dao<userAccount, Integer> getUserDao() {
        return this.userDao;
    }
}
