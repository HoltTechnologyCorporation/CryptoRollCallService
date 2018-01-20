package com.crc.models;

import java.util.List;

public class ChartDataContainer {

	private List<ChartData> hourlyList;
	private List<ChartData> dailyList;
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
