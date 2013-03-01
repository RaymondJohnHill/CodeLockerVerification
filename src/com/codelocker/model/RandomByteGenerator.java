package com.codelocker.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class RandomByteGenerator {

	private static Logger logger;
	
	/**
	 * Class instantiator.
	 */
	public RandomByteGenerator() {
		//creates a new logger
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("./configs/RandomByteGenerator.configuration");
	}
	
	/**
	 * Generates a cryptographically strong array of random bytes.
	 * @param byteLen the length of the array in bytes
	 * @return an array of random bytes
	 */
	public byte[] generateSecureRandomBytes(int byteLen) {
		byte[] bytes = new byte[byteLen];
		
		try {
			//uses the SHA1PRNG pseudo-random number generator algorithm.
			SecureRandom sRand = SecureRandom.getInstance("SHA1PRNG");
			sRand.nextBytes(bytes);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
		
		return bytes;
	}
	
}
