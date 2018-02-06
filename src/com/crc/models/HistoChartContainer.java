package com.crc.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HistoChartContainer - Container for historical performance")
public class HistoChartContainer {

	@ApiModelProperty(value = "Historical performance based on market cap")
	private double[][] market_cap;
	
	@ApiModelProperty(value = "Historical performance based on price")
	private double[][] price;
	
	@ApiModelProperty(value = "Historical performance based on volume")
	private double[][] volume;

	public double[][] getMarket_cap() {
		return market_cap;
	}

	public void setMarket_cap(double[][] market_cap) {
		this.market_cap = market_cap;
	}

	public double[][] getPrice() {
		return price;
	}

	public void setPrice(double[][] price) {
		this.price = price;
	}

	public double[][] getVolume() {
		return volume;
	}

	public void setVolume(double[][] volume) {
		this.volume = volume;
	}

}
