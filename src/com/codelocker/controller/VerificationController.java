package com.codelocker.controller;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.codelocker.model.HashDigestGenerator;
import com.codelocker.model.RandomByteGenerator;
import com.codelocker.model.SQLConnection;
import com.codelocker.model.UserProfile;
import com.codelocker.model.VerificationEmailer;

public class VerificationController {

	//variables for the user to enter later
	private final String smtpHostName = "smtp.gmail.com";
	private final String smtpUserName = "codemaptest@gmail.com";
	private final String smtpPassword = "123qweASDzxc";
	private final int smtpPortNumber = 587;
	
	private SQLConnection connection;
	private DataSource dataSource;
	private UserProfile currentUser;
	private final int BYTE_LENGTH = 96;
	
	/**
	 * Instantiates the class along with the SQLConnection and the UserProfile. 
	 * @param email the recipients email
	 */
	public VerificationController(String email) {
		PropertyConfigurator.configure("./configs/base.configuration");
		
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
	
	/**
	 * Generates the verification code, stores it in the UserProfile, and sends it in an email to the user.
	 */
	public void sendEmail() {
		VerificationEmailer mail = new VerificationEmailer(smtpHostName, smtpPortNumber, smtpUserName, smtpPassword);
		
		String verification_code = Base64.encodeBase64URLSafeString(
				HashDigestGenerator.getByteDigest(
						RandomByteGenerator.generateSecureRandomBytes(BYTE_LENGTH)));
		
		currentUser.setVerificationCode(verification_code);
		
		mail.sendVerification(currentUser.getEmail(), verification_code);
	}

}
