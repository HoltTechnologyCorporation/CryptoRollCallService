package com.crc.service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.crc.constants.Constants;

/**
 * Class responsible for generating salt, hash and handling passphrase validations
 * @author shankarganesh1234
 *
 */
public class HashServiceImpl {

	/**
	 * Generates hash, taking into account the portfolio name and the passphrase
	 * 
	 * @param passPhrase
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException 
	 */
	public String generateHash(String passPhrase)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

		char[] chars = passPhrase.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, Constants.ITERATIONS, Constants.KEY_LENGTH);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(Constants.KEY_FACTORY);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return toHex(hash);
	}

	/**
	 * Generate a pseudo random salt
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException 
	 */
	private byte[] getSalt() throws NoSuchAlgorithmException, IOException {
		Path staticSaltPath = Paths.get(Constants.STATIC_SALT_LOC);
		return Files.readAllBytes(staticSaltPath);
	}

	/**
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 * 
	 */
	public void generateOneTimeSalt() throws NoSuchAlgorithmException, IOException {
		Path staticSaltPath = Paths.get(Constants.STATIC_SALT_LOC);
		if(Files.exists(staticSaltPath))
			return;
		
		SecureRandom sr = SecureRandom.getInstance(Constants.SECURE_RANDOM_INSTANCE);
		byte[] salt = new byte[128];
		sr.nextBytes(salt);
		
		Files.createFile(staticSaltPath);
		Files.write(staticSaltPath, salt);
	}
	
	/**
	 * Generate hex equivalent of hash
	 * 
	 * @param array
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
}
