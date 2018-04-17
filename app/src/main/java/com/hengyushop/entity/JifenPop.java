package com.hengyushop.entity;

import java.io.Serializable;

public class JifenPop implements Serializable{
	private String DayOfWeek;
	private String MonthAndDay;
	private String CurrentStatus;
	private String CanGetNum;
	public String getDayOfWeek() {
		return DayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		DayOfWeek = dayOfWeek;
	}
	public String getMonthAndDay() {
		return MonthAndDay;
	}
	public void setMonthAndDay(String monthAndDay) {
		MonthAndDay = monthAndDay;
	}
	public String getCurrentStatus() {
		return CurrentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		CurrentStatus = currentStatus;
	}
	public String getCanGetNum() {
		return CanGetNum;
	}
	public void setCanGetNum(String canGetNum) {
		CanGetNum = canGetNum;
	}
	
}
