package com.crc.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

import com.crc.constants.Constants;
import com.crc.models.CreateFolioRequest;
import com.crc.models.CreateFolioResponse;
import com.crc.models.GetFolioRequest;
import com.crc.models.GetFolioResponse;

public class FolioServiceImpl {

	DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
	HashServiceImpl hashService = new HashServiceImpl();

	/**
	 * Create a folio - As part of creation - Generate passphrase - Generate
	 * hash - Check if hash insert was successful and return passphrase and
	 * folio name
	 * 
	 * @param request
	 * @return
	 */
	public CreateFolioResponse createFolio(CreateFolioRequest request) {

		if (request == null || request.getName() == null || request.getName().isEmpty())
			return null;

		CreateFolioResponse res = null;
		try {
			// generate random passphrase
			String randomPassphrase = databaseService.getRandomPassPhrase(Constants.DIFFICULTY);

			// generate hash from pass phrase
			String hash = hashService.generateHash(randomPassphrase);

			// check if hash insert is successful
			if (databaseService.createHashEntry(request.getName(), hash)) {
				res = new CreateFolioResponse();
				res.setName(request.getName());
				res.setPassPhrase(randomPassphrase);
				res.setEncodedCreds(encodeBase64(randomPassphrase));
			}
			// return success response
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * Get the folio corresponding to name and encoded pass phrase
	 * @param request
	 * @return
	 */
	public GetFolioResponse getFolio(GetFolioRequest request) {

		if (request == null || StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getEncodedCreds()))
			return null;

		GetFolioResponse res = null;
		try {
			
			// validate whether hash exists
			String hash = validateHash(request);

			if(StringUtils.isNotBlank(hash)) {
				res = new GetFolioResponse();
				res.setName(request.getName());
				res.setEncodedCreds(hash);
			}
			// return success response
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * Validate whether the supplied pass phrase and name correspond to a valid
	 * hash
	 * 
	 * @param request
	 * @return
	 */
	protected String validateHash(GetFolioRequest request) {

		if (request == null || StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getEncodedCreds()))
			return null;

		String hash = null;
		try {
			hash = hashService.generateHash(decodeBase64(request.getEncodedCreds()));

			if (hash == null)
				return null;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
		return databaseService.getHashEntry(request.getName(), hash);
	}

	/**
	 * Get Base64 encoded string
	 * 
	 * @param input
	 * @return
	 */
	protected String encodeBase64(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}

	/**
	 * Decode Base64 encoded string
	 * 
	 * @param input
	 * @return
	 */
	protected String decodeBase64(String input) {
		return new String(Base64.getDecoder().decode(input.getBytes()));
	}
}
