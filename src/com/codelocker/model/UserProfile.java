package com.codelocker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class UserProfile {

	private Connection connection;
	private ResultSet results;
	
	private static Logger logger;
	
	private String email;
	
	/**
	 * UserProfile creates a new user profile based on the users email address.
	 * @param email the users email address
	 * @param connection the current SQL connection
	 */
	public UserProfile(String email, Connection connection) {
		//creates a new logger
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("./configs/UserProfile.configuration");

		//sets local email and connection variables for later use
		this.connection = connection;
		this.email = email;
		
		//pushes the email to the database immediately
		setEmail();
		setAllNull();
	}
	
	/**
	 * Fills the current rows will NULL.
	 */
	private void setAllNull() {
		PreparedStatement prepared;
		try {
			prepared = connection.prepareStatement("INSERT INTO CodeLocker.Credentials VALUES(default, default, default)");
			prepared.executeUpdate();
			prepared = connection.prepareStatement("INSERT INTO CodeLocker.UnverifiedUsers VALUES(default, default)");
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Gets the user_id field value from the database using the email address.
	 * @return the unique int that represents the user_id
	 */
	private int getUserID() {
		PreparedStatement prepared;
		//sets the into to -1 so we know we messed
		//up somewhere if we get this as a result.
		int user_id = 1;
		
		try {
			//builds the prepared statement.
			prepared = connection.prepareStatement("SELECT user_id FROM CodeLocker.Users WHERE email=?");
			prepared.setString(1, email);
			results = prepared.executeQuery();
			
			//grabs the first result, that's all
			//we need since it should be unique.
			while(results.next()) {
				user_id = results.getInt(1);
			}
			
		} catch (SQLException e){
			//uh oh! this just logs the errors we get.
			logger.error(e.getMessage());
		}
		
		return user_id;
	}
	
	/**
	 * Sets the users email field in the database.
	 */
	private void setEmail() {
		PreparedStatement prepared;
		try {
			prepared = connection.prepareStatement("INSERT IGNORE INTO CodeLocker.Users (email) VALUES(?)");
			prepared.setString(1, email);
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Gets the email field value from the database using the user_id.
	 * @return the email address associated with the specific user_id
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the password_digest field in the Credentials database.
	 * @param password_digest the byte array to store in the database
	 */
	public void setPasswordDigest(byte[] password_digest) {
		PreparedStatement prepared;
		try {
			prepared = connection.prepareStatement("UPDATE CodeLocker.Credentials SET password_digest=? WHERE user_id=?");
			prepared.setBytes(1, password_digest);
			prepared.setInt(2, getUserID());
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Gets the password_digest field value from the database using the user_id.
	 * @return the byte array associated with the specific user_id
	 */
	public byte[] getPasswordDigest() {
		PreparedStatement prepared;
		byte[] password_digest = null;
		
		try {
			prepared = connection.prepareStatement("SELECT password_digest FROM CodeLocker.Credentials WHERE user_id=?");
			prepared.setInt(1, getUserID());
			results = prepared.executeQuery();
			
			while(results.next()) {
				password_digest = results.getBytes(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return password_digest;
	}
	
	/**
	 * Sets the password_salt field in the Credentials database.
	 * @param password_salt the byte array to store in the database
	 */
	public void setSalt(byte[] password_salt) {
		PreparedStatement prepared;
		try {
			prepared = connection.prepareStatement("UPDATE CodeLocker.Credentials SET password_salt=? WHERE user_id=?");
			prepared.setBytes(1, password_salt);
			prepared.setInt(2, getUserID());
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Gets the password_salt field value from the database using the user_id.
	 * @return the byte array associated with the specific user_id
	 */
	public byte[] getSalt() {
		PreparedStatement prepared;
		byte[] password_salt = null;
		
		try {
			prepared = connection.prepareStatement("SELECT password_salt FROM CodeLocker.Credentials WHERE user_id=?");
			prepared.setInt(1, getUserID());
			results = prepared.executeQuery();
			
			while(results.next()) {
				password_salt = results.getBytes(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return password_salt;
	}
	
	/**
	 * Sets the verification_code field in the UnverifiedUsers database.
	 * @param verification_code the string to store in the database
	 */
	public void setVerificationCode(String verification_code) {
		PreparedStatement prepared;
		try {
			prepared = connection.prepareStatement("UPDATE CodeLocker.UnverifiedUsers SET verification_code=? WHERE user_id=?");
			prepared.setString(1, verification_code);
			prepared.setInt(2, getUserID());
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Gets the verification_code field value from the database using the user_id.
	 * @return the string associated with the specific user_id
	 */
	public String getVerificationCode() {
		PreparedStatement prepared;
		String verification_code = "";
		
		try {
			prepared = connection.prepareStatement("SELECT verification_code FROM CodeLocker.UnverifiedUsers WHERE user_id=?");
			prepared.setInt(1, getUserID());
			results = prepared.executeQuery();
			
			while(results.next()) {
				verification_code = results.getString(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return verification_code;
	}
}
