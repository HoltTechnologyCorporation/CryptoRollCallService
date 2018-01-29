package com.crc.models;

import java.util.List;

public class CoinDetails {

	private String id;
	private String imageUrl;
	private String symbol;
	private String name;
	private String algorithm;
	private String proofType;
	private boolean isPreMined;
	private String twitterUrl;
	private String redditUrl;
	private String facebookUrl;
	private List<String> codeRepoLinks;
	private String websiteUrl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getProofType() {
		return proofType;
	}
	public void setProofType(String proofType) {
		this.proofType = proofType;
	}
	public boolean isPreMined() {
		return isPreMined;
	}
	public void setPreMined(boolean isPreMined) {
		this.isPreMined = isPreMined;
	}
	public String getTwitterUrl() {
		return twitterUrl;
	}
	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}
	public String getRedditUrl() {
		return redditUrl;
	}
	public void setRedditUrl(String redditUrl) {
		this.redditUrl = redditUrl;
	}
	public String getFacebookUrl() {
		return facebookUrl;
	}
	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}
	public List<String> getCodeRepoLinks() {
		return codeRepoLinks;
	}
	public void setCodeRepoLinks(List<String> codeRepoLinks) {
		this.codeRepoLinks = codeRepoLinks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CoinDetails [id=" + id + ", imageUrl=" + imageUrl + ", symbol=" + symbol + ", algorithm=" + algorithm
				+ ", proofType=" + proofType + ", isPreMined=" + isPreMined + ", twitterUrl=" + twitterUrl
				+ ", redditUrl=" + redditUrl + ", facebookUrl=" + facebookUrl + ", codeRepoLinks=" + codeRepoLinks
				+ "]";
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
}
