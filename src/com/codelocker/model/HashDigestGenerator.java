package com.codelocker.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class HashDigestGenerator {

	private static Logger logger;
	
	public HashDigestGenerator() { }
	
	/**
	 * Takes any object and gets a hash digest of that object using the SHA-512 algorithm.
	 * @param obj the object to get the hash digest of
	 * @return the hash digest in byte array form
	 */
	public byte[] getByteDigest(Object obj) {
		//creates a new logger
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("./configs/HashDigestGenerator.configuration");
		
		MessageDigest msgDigest;
		byte[] digest = null;
		
		try {
			//creates a new MessageDigest instance using the SHA-512 algorithm
			msgDigest = MessageDigest.getInstance("SHA-512");
			//creates the digest using the byte array of the string of the object
			digest = msgDigest.digest(obj.toString().getBytes());
		} catch (NoSuchAlgorithmException e) {
			//logs as error
			logger.error(e.getMessage());
		}
		
		return digest;
	}
	
}
