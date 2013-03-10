package com.codelocker.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class HashDigestGenerator {
	
	/**
	 * Takes any object and gets a hash digest of that object using the SHA-512 algorithm.
	 * @param obj the object to get the hash digest of
	 * @return the hash digest in byte array form
	 */
	public static byte[] getByteDigest(byte[] bytes) {
		final String algorithm = "SHA-512";
		final MessageDigest msgDigest;
		byte[] digest = null;
		
		try {
			//creates a new MessageDigest instance using the SHA-512 algorithm
			msgDigest = MessageDigest.getInstance(algorithm);
			//creates the digest using the byte array of the string of the object
			digest = msgDigest.digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			//logs as error
			Logger.getLogger(HashDigestGenerator.class).error(e.getMessage() + algorithm + " algorithm not valid!");
			System.exit(1);
		}
		
		return digest;
	}
	
	/**
	 * 
	 * @param bytes
	 * @param salt
	 * @return
	 */
	public static byte[] getByteDigestWithSalt(byte[] bytes, byte[] salt) {
		final String algorithm = "SHA-512";
		final MessageDigest msgDigest;
		byte[] digest = null;
		
		try {
			//creates a new MessageDigest instance using the SHA-512 algorithm
			msgDigest = MessageDigest.getInstance(algorithm);
			msgDigest.update(salt);
			//creates the digest using the byte array of the string of the object
			digest = msgDigest.digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			//logs as error
			Logger.getLogger(HashDigestGenerator.class).error(e.getMessage() + algorithm + " algorithm not valid!");
			System.exit(1);
		}
		
		return digest;
	}
	
}
