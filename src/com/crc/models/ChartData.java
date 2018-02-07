package com.crc.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ChartData - Chart labels and values")
public class ChartData {
	
	public ChartData(String label, Double data) {
		super();
		this.label = label;
		this.data = data;
	}

	@ApiModelProperty(value = "Chart label typically the name of the coin.")
	private String label;
	
	@ApiModelProperty(value = "Chart data value numeric value")
	private Double data;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}

}
