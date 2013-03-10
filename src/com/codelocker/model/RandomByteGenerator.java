package com.codelocker.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

public class RandomByteGenerator {
	
	/**
	 * Generates a cryptographically strong array of random bytes.
	 * @param byteLen the length of the array in bytes
	 * @return an array of random bytes
	 */
	public static byte[] generateSecureRandomBytes(int byteLen) {
		final String algorithm = "SHA1PRNG";
		byte[] bytes = new byte[byteLen];
		
		try {
			//uses the SHA1PRNG pseudo-random number generator algorithm.
			SecureRandom sRand = SecureRandom.getInstance(algorithm);
			sRand.nextBytes(bytes);
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(RandomByteGenerator.class).error(e.getMessage() + algorithm + " algorithm not valid!");
			System.exit(1);
		}
		
		return bytes;
	}
	
}
