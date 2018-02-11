package com.crc.constants;

public enum TimePeriodEnum {
	DAY("1day"), WEEK("7day"), MONTH("30day"), THREE_MONTHS("90day"), SIX_MONTHS("180day"), YEAR("365day");

	private final String value;

	private TimePeriodEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}	
}
