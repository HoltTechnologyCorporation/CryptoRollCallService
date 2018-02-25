package com.crc.models;

import java.util.List;

import com.crc.models.news.CryptoNews;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CoinDetails - Details of the coin : images, social stats, source code and more")
public class CoinDetails {

	@ApiModelProperty(value = "Id of the crypto currency")
	private String id;
	
	@ApiModelProperty(value = "Image url of the crypto currency")
	private String imageUrl;
	
	@ApiModelProperty(value = "Symbol of the crypto currency")
	private String symbol;
	
	@ApiModelProperty(value = "Name of the crypto currency")
	private String name;
	
	@ApiModelProperty(value = "Mining algorithm used by the crypto currency")
	private String algorithm;
	
	@ApiModelProperty(value = "Proof type for the crypto currency")
	private String proofType;
	
	@ApiModelProperty(value = "Boolean representing if the coin is pre-mined")
	private boolean isPreMined;
	
	@ApiModelProperty(value = "Official twitter url of the crypto currency")
	private String twitterUrl;
	
	@ApiModelProperty(value = "Official reddit url of the crypto currency")
	private String redditUrl;
	
	@ApiModelProperty(value = "Official facebook url of the crypto currency")
	private String facebookUrl;
	
	@ApiModelProperty(value = "Links to the source code")
	private List<String> codeRepoLinks;
	
	@ApiModelProperty(value = "Official website url of the crypto currency")
	private String websiteUrl;
	
	@ApiModelProperty(value = "Exchanges where the crypto currency is available for trade")
	private List<String> availableExchanges;
	
	@ApiModelProperty(value = "Title for SEO")
	private String seoTitle;
	
	@ApiModelProperty(value = "Description for SEO")
	private String seoDescription;
	
	@ApiModelProperty(value = "Coin name or friendly name")
	private String fullName;
	
	@ApiModelProperty(value = "Latest news about the coin")
	private CryptoNews coinNews;
	
	public String getFullName() {
		return fullName;
	}

	public CryptoNews getCoinNews() {
		return coinNews;
	}

	public void setCoinNews(CryptoNews coinNews) {
		this.coinNews = coinNews;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

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
		return "CoinDetails [id=" + id + ", imageUrl=" + imageUrl + ", symbol=" + symbol + ", name=" + name
				+ ", algorithm=" + algorithm + ", proofType=" + proofType + ", isPreMined=" + isPreMined
				+ ", twitterUrl=" + twitterUrl + ", redditUrl=" + redditUrl + ", facebookUrl=" + facebookUrl
				+ ", codeRepoLinks=" + codeRepoLinks + ", websiteUrl=" + websiteUrl + ", availableExchanges="
				+ availableExchanges + ", seoTitle=" + seoTitle + ", seoDescription=" + seoDescription + "]";
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public List<String> getAvailableExchanges() {
		return availableExchanges;
	}

	public void setAvailableExchanges(List<String> availableExchanges) {
		this.availableExchanges = availableExchanges;
	}
}
