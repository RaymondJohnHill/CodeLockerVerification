package com.codelocker.controller;

import com.codelocker.model.HashDigestGenerator;
import com.codelocker.model.RandomByteGenerator;
import com.codelocker.model.SQLConnection;
import com.codelocker.model.UserProfile;

public class PasswordController {

	//variables for the user to enter later
	private String sqlHostName = "";
	private String sqlUserName = "";
	private String sqlPassword = "";
	
	
	private SQLConnection connection;
	private UserProfile currentUser;
	private final int BYTE_LENGTH = 24;
	
	public PasswordController(String email) {
		connection = new SQLConnection(sqlHostName, sqlUserName, sqlPassword);
		UserProfile currentUser = new UserProfile(email, connection.getConnection());
	}
	
	public void savePasswordAndSalt(String verification_code, String password) {
		if(verificationCheck(verification_code)) {
			RandomByteGenerator randByteGen = new RandomByteGenerator();
			HashDigestGenerator digestGen = new HashDigestGenerator();
			
			byte[] password_salt = randByteGen.generateSecureRandomBytes(BYTE_LENGTH);
			byte[] password_digest = digestGen.getByteDigest(password + password_salt);
			
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
		return verification_code.contentEquals(currentUser.getVerificationCode());
	}
	
}
