package com.crc.models;

public class ChartData {
	
	public ChartData(String label, Double data) {
		super();
		this.label = label;
		this.data = data;
	}

	private String label;
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
