package com.codelocker.controller;

import org.apache.commons.codec.binary.Base64;

import com.codelocker.model.HashDigestGenerator;
import com.codelocker.model.RandomByteGenerator;
import com.codelocker.model.SQLConnection;
import com.codelocker.model.UserProfile;
import com.codelocker.model.VerificationEmailer;

public class VerificationController {

	//variables for the user to enter later
	private String smtpHostName = "";
	private String smtpUserName = "";
	private String smtpPassword = "";
	private int smtpPortNumber = 0;
	private String sqlHostName = "";
	private String sqlUserName = "";
	private String sqlPassword = "";
	
	
	private SQLConnection connection;
	private UserProfile currentUser;
	private final int BYTE_LENGTH = 96;
	private boolean sentStatus;
	
	/**
	 * Instantiates the class along with the SQLConnection and the UserProfile. 
	 * @param email the recipients email
	 */
	public VerificationController(String email) {
		connection = new SQLConnection(sqlHostName, sqlUserName, sqlPassword);
		UserProfile currentUser = new UserProfile(email, connection.getConnection());
	}
	
	/**
	 * Generates the verification code, stores it in the UserProfile, and sends it in an email to the user.
	 */
	public void sendEmail() {
		VerificationEmailer mail = new VerificationEmailer(smtpHostName, smtpPortNumber, smtpUserName, smtpPassword);
		RandomByteGenerator randByteGen = new RandomByteGenerator();
		HashDigestGenerator digestGen = new HashDigestGenerator();
		
		String verification_code = Base64.encodeBase64URLSafeString(
																	digestGen.getByteDigest(
																	randByteGen.generateSecureRandomBytes(BYTE_LENGTH)));
		
		currentUser.setVerificationCode(verification_code);
		
		sentStatus = mail.sendVerification(currentUser.getEmail(), verification_code);
	}
	
	/**
	 * Just a boolean to keep track of whether the email actually sent or not.
	 * @return true if the email was sent, false otherwise
	 */
	public boolean didEmailSend() {
		return sentStatus;
	}
}
