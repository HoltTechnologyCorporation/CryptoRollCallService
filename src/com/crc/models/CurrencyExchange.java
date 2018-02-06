package com.crc.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CurrencyExchange - Container for currency exchange rates")
public class CurrencyExchange {

	@ApiModelProperty(value = "Base currency used for calculating exchange rates", example = "USD")
	private String base;
	
	@ApiModelProperty(value = "Date on which the exchange rates were calculated")
	private String date;
	
	@ApiModelProperty(value = "Exchange rates based on the base currency")
	private String rates;
	
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRates() {
		return rates;
	}
	public void setRates(String rates) {
		this.rates = rates;
	}
}
