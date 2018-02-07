package com.crc.models;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Ticker - Container for crypto currency ticker")
@XmlRootElement
public class Ticker {

	@ApiModelProperty(value = "Id of the crypto currency")
	private String id;
	
	@ApiModelProperty(value = "Name of the crypto currency")
	private String name;
	
	@ApiModelProperty(value = "Symbol of the crypto currency")
	private String symbol;
	
	@ApiModelProperty(value = "Rank of the crypto currency")
	private String rank;
	
	@ApiModelProperty(value = "Price value in USD")
	private String price_usd;
	
	@ApiModelProperty(value = "Price value in BTC")
	private String price_btc;
	
	@ApiModelProperty(value = "Market capitalization in USD")
	private String market_cap_usd;
	
	@ApiModelProperty(value = "Available coin supply")
	private String available_supply;
	
	@ApiModelProperty(value = "Percent change in price in the previous hour")
	private double percent_change_1h;
	
	@ApiModelProperty(value = "Percent change in price in the previous day")
	private double percent_change_24h;
	
	@ApiModelProperty(value = "Percent change in price in the previous week")
	private double percent_change_7d;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(String price_usd) {
		this.price_usd = price_usd;
	}

	public String getPrice_btc() {
		return price_btc;
	}

	public void setPrice_btc(String price_btc) {
		this.price_btc = price_btc;
	}

	public String getMarket_cap_usd() {
		return market_cap_usd;
	}

	public void setMarket_cap_usd(String market_cap_usd) {
		this.market_cap_usd = market_cap_usd;
	}

	public String getAvailable_supply() {
		return available_supply;
	}

	public void setAvailable_supply(String available_supply) {
		this.available_supply = available_supply;
	}

	public double getPercent_change_1h() {
		return percent_change_1h;
	}

	public void setPercent_change_1h(double percent_change_1h) {
		this.percent_change_1h = percent_change_1h;
	}

	public double getPercent_change_24h() {
		return percent_change_24h;
	}

	public void setPercent_change_24h(double percent_change_24h) {
		this.percent_change_24h = percent_change_24h;
	}

	public double getPercent_change_7d() {
		return percent_change_7d;
	}

	public void setPercent_change_7d(double percent_change_7d) {
		this.percent_change_7d = percent_change_7d;
	}

	@Override
	public String toString() {
		return "TIcker [id=" + id + ", name=" + name + ", symbol=" + symbol + ", rank=" + rank + ", price_usd="
				+ price_usd + ", price_btc=" + price_btc + ", market_cap_usd=" + market_cap_usd + ", available_supply="
				+ available_supply + ", percent_change_1h=" + percent_change_1h + ", percent_change_24h="
				+ percent_change_24h + ", percent_change_7d=" + percent_change_7d + "]";
	}
}
