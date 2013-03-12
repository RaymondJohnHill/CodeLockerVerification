package com.codelocker.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.codelocker.model.HashDigestGenerator;
import com.codelocker.model.RandomByteGenerator;
import com.codelocker.model.SQLConnection;
import com.codelocker.model.UserProfile;

public class PasswordController {

	private SQLConnection connection;
	private DataSource dataSource;
	private UserProfile currentUser;
	private final int BYTE_LENGTH = 24;
	
	public PasswordController(String email) {
		
		try {
            //get the datasource
            Context initContext  = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource)envContext.lookup("codelocker_connection");
            connection = new SQLConnection(dataSource.getConnection());             
        } catch (NamingException e) {
        	Logger.getLogger(SQLConnection.class).error(e.getMessage());
        	System.exit(1);
        } catch (SQLException e) {
        	Logger.getLogger(SQLConnection.class).error(e.getMessage());
        	System.exit(1);
		}
		
		currentUser = new UserProfile(email, connection.getConnection());
	}
	
	public void savePasswordAndSalt(String verification_code, String password) {
		if(verificationCheck(verification_code)) {			
			final String encoding = "UTF-8";
			final byte[] password_salt = RandomByteGenerator.generateSecureRandomBytes(BYTE_LENGTH);
			byte[] password_digest = null;
			
			try {
				password_digest = HashDigestGenerator.getByteDigestWithSalt(password.getBytes(encoding), password_salt);
			} catch (UnsupportedEncodingException e) {
				Logger.getLogger(this.getClass()).error(e.getMessage() + encoding + " is not a valid method of encoding.");
				System.exit(1);
			}
			
			currentUser.setPasswordDigest(password_digest);
			currentUser.setSalt(password_salt);
		}
	}
	
	/**
	 * Checks to see if the verification code from the URL matches the one previously stored in the UserProfile.
	 * @param verification_code the verification code from the URL
	 * @return true if the verification codes match, false otherwise
	 */
	private boolean verificationCheck(String verification_code) {
		return verification_code.equals(currentUser.getVerificationCode());
	}
	
}
