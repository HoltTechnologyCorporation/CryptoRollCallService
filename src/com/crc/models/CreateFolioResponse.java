package com.crc.models;

public class CreateFolioResponse {

	private String name;
	private String passPhrase;
	private String encodedCreds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassPhrase() {
		return passPhrase;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}

	public String getEncodedCreds() {
		return encodedCreds;
	}

	public void setEncodedCreds(String encodedCreds) {
		this.encodedCreds = encodedCreds;
	}
}
