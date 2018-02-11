package com.crc.models;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ChartDataContainer - Container for charts of top performing coins")
public class ChartDataContainer {

	@ApiModelProperty(value = "List of top 5 performers in the previous hour")
	private List<ChartData> hourlyList;
	
	@ApiModelProperty(value = "List of top 5 performers in the previous day")
	private List<ChartData> dailyList;
	
	@ApiModelProperty(value = "List of top 5 performers in the previous week")
	private List<ChartData> weeklyList;

	public List<ChartData> getHourlyList() {
		return hourlyList;
	}

	public void setHourlyList(List<ChartData> hourlyList) {
		this.hourlyList = hourlyList;
	}

	public List<ChartData> getDailyList() {
		return dailyList;
	}

	public void setDailyList(List<ChartData> dailyList) {
		this.dailyList = dailyList;
	}

	public List<ChartData> getWeeklyList() {
		return weeklyList;
	}

	public void setWeeklyList(List<ChartData> weeklyList) {
		this.weeklyList = weeklyList;
	}

	public ChartDataContainer(List<ChartData> hourlyList, List<ChartData> dailyList, List<ChartData> weeklyList) {
		super();
		this.hourlyList = hourlyList;
		this.dailyList = dailyList;
		this.weeklyList = weeklyList;
	}

}
