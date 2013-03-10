package com.codelocker.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

public class VerificationEmailer {
	
	private final String host, user, pass;
	private final int port;
	
	/**
	 * Builds a new VerificationEmailer with the host name, port number, user name, and password.
	 * @param host the host name of the smtp server to use
	 * @param port the port number that smtp is using on the smtp server
	 * @param user the user name to log in to the smtp server
	 * @param pass the password to log in to the smtp server
	 */
	public VerificationEmailer(String host, int port, String user, String pass) {		
		//assigns class variables
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	
	/**
	 * Sends the verification email to the specified user.
	 * @param email the email address of the recipient
	 * @param encodedVerification the url-safe, base64 encoded hash
	 * @return true if the email sent successfully, false otherwise
	 */
	public void sendVerification(String email, String encodedVerification) {
		SimpleEmail mail = new SimpleEmail();
		mail.setHostName(host);
		mail.setSmtpPort(port);
		mail.setAuthenticator(new DefaultAuthenticator(user, pass));
		mail.setSSLOnConnect(true);
		try {
			mail.setDebug(false);
			mail.setFrom("verification@codelocker.com", "CodeLocker Verification");
			mail.setSubject("CodeLocker.com Email Verification");
			mail.setMsg("Hello there! \n \n" +
						"Welcome to CodeLocker! Please verify your account by clicking the link below. \n" +
						"http://www.codelocker.com/verify?email=" +
						URLEncoder.encode(email, "UTF-8") +
						"&verification=" +
						encodedVerification);
			mail.addTo(email,"Newest Member of CodeLocker");
			mail.send();
		} catch (EmailException e) {
			Logger.getLogger(this.getClass()).fatal(e.getMessage());
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(this.getClass()).fatal(e.getMessage());
			System.exit(1);
		}
	}
	
}
