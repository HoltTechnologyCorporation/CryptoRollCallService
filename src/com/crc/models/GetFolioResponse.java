package com.crc.models;

public class GetFolioResponse {
	private String name;
	private String encodedCreds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncodedCreds() {
		return encodedCreds;
	}

	public void setEncodedCreds(String encodedCreds) {
		this.encodedCreds = encodedCreds;
	}
}
