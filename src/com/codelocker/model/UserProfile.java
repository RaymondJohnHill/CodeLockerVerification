package com.codelocker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfile {

	private Connection connection;
	private PreparedStatement prepared;
	private ResultSet results;
	
	private ExceptionLogger logger;
	
	private String email;
	
	/**
	 * UserProfile creates a new user profile based on the users email address.
	 * @param email the users email address
	 * @param connection the current SQL connection
	 */
	public UserProfile(String email, Connection connection) {
		//creates a new logger with this class.
		logger = new ExceptionLogger(this.getClass());
		//sets local email and connection variables for later use.
		this.connection = connection;
		this.email = email;
		//pushes the email to the database immediately.
		setEmail();
	}
	
	/**
	 * Gets the user_id field value from the database using the email address.
	 * @return the unique int that represents the user_id
	 */
	private int getUserID() {
		//sets the into to -1 so we know we messed
		//up somewhere if we get this as a result.
		int user_id = -1;
		
		try {
			//builds the prepared statement.
			prepared = connection.prepareStatement("SELECT user_id FROM Users WHERE email=?");
			prepared.setString(1, email);
			prepared.executeUpdate();
			
			//grabs the first result, that's all
			//we need since it should be unique.
			while(results.next()) {
				user_id = results.getInt(1);
			}
			
		} catch (SQLException e){
			//uh oh! this just logs the errors we get.
			logger.generateFatalReport(e.getMessage());
		}
		
		return user_id;
	}
	
	/**
	 * Sets the users email field in the database.
	 */
	private void setEmail() {
		try {
			prepared = connection.prepareStatement("INSERT INTO Users (email) VALUES(?)");
			prepared.setString(1, email);
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
	}
	
	/**
	 * Gets the email field value from the database using the user_id.
	 * @return the email address associated with the specific user_id
	 */
	public String getEmail() {
		try {
			prepared = connection.prepareStatement("SELECT email FROM Users WHERE user_id=?");
			prepared.setInt(1, getUserID());
			prepared.executeUpdate();
			
			while(results.next()) {
				email = results.getString(1);
			}
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
		
		return email;
	}
	
	/**
	 * Sets the password_digest field in the Credentials database.
	 * @param password_digest the byte array to store in the database
	 */
	public void setPasswordDigest(byte[] password_digest) {
		try {
			prepared = connection.prepareStatement("INSERT INTO Credentials (password_digest) VALUES(?) WHERE user_id=?");
			prepared.setBytes(1, password_digest);
			prepared.setInt(2, getUserID());
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
	}
	
	/**
	 * Gets the password_digest field value from the database using the user_id.
	 * @return the byte array associated with the specific user_id
	 */
	public byte[] getPasswordDigest() {
		byte[] password_digest = null;
		
		try {
			prepared = connection.prepareStatement("SELECT password_digest FROM Credentials WHERE user_id=?");
			prepared.setInt(1, getUserID());
			prepared.executeUpdate();
			
			while(results.next()) {
				password_digest = results.getBytes(1);
			}
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
		
		return password_digest;
	}
	
	/**
	 * Sets the password_salt field in the Credentials database.
	 * @param password_salt the byte array to store in the database
	 */
	public void setSalt(byte[] password_salt) {
		try {
			prepared = connection.prepareStatement("INSERT INTO Credentials (password_salt) VALUES(?) WHERE user_id=?");
			prepared.setBytes(1, password_salt);
			prepared.setInt(2, getUserID());
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
	}
	
	/**
	 * Gets the password_salt field value from the database using the user_id.
	 * @return the byte array associated with the specific user_id
	 */
	public byte[] getSalt() {
		byte[] password_salt = null;
		
		try {
			prepared = connection.prepareStatement("SELECT password_salt FROM Credentials WHERE user_id=?");
			prepared.setInt(1, getUserID());
			prepared.executeUpdate();
			
			while(results.next()) {
				password_salt = results.getBytes(1);
			}
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
		
		return password_salt;
	}
	
	/**
	 * Sets the verification_code field in the UnverifiedUsers database.
	 * @param verification_code the string to store in the database
	 */
	public void setVerificationCode(String verification_code) {
		try {
			prepared = connection.prepareStatement("INSERT INTO UnverifiedUsers (verification_code) VALUES(?) WHERE user_id=?");
			prepared.setString(1, verification_code);
			prepared.setInt(2, getUserID());
			prepared.executeUpdate();
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
	}
	
	/**
	 * Gets the verification_code field value from the database using the user_id.
	 * @return the string associated with the specific user_id
	 */
	public String getVerificationCode() {
		String verification_code = "";
		
		try {
			prepared = connection.prepareStatement("SELECT verification_code FROM UnverifiedUsers WHERE user_id=?");
			prepared.setInt(1, getUserID());
			prepared.executeUpdate();
			
			while(results.next()) {
				verification_code = results.getString(1);
			}
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
		
		return verification_code;
	}
	
}
