package com.hengyushop.demo.train;

import java.io.Serializable;

public class ChePiaoData implements Serializable {
	private String trainNum = null;
	private String trainCode = null;
	private String fromStation = null;
	private String fromTime = null;
	private String toStation = null;
	private String toTime = null;
	private String takeTime = null;
	// private String day_diff = null;
	private String business = null;// 商务座
	private String best_seat = null;// 特等座
	private String one_seat = null;// 一等座
	private String two_seat = null;// 二等座
	private String vag_sleeper = null;// 高级软座
	private String soft_sleeper = null;// 软卧
	private String hard_sleeper = null;// 硬卧
	private String soft_seat = null;// 软座
	private String hard_seat = null;// 硬座
	private String none_seat = null;// 无座
	private String startStationCode = null;
	private String endStationCode = null;
	private String fromStationCode = null;
	private String toStationCode = null;
	private String seat_types = null;
	private String from_station_no=null;
	private String to_station_no = null;
	private String day_difference = null;
	private String start_train_date = null;
	
	public String getStart_train_date() {
		return start_train_date;
	}

	public void setStart_train_date(String start_train_date) {
		this.start_train_date = start_train_date;
	}

	public String getDay_difference() {
		return day_difference;
	}

	public void setDay_difference(String day_difference) {
		this.day_difference = day_difference;
	}

	public String getFrom_station_no() {
		return from_station_no;
	}

	public void setFrom_station_no(String from_station_no) {
		this.from_station_no = from_station_no;
	}

	public String getTo_station_no() {
		return to_station_no;
	}

	public void setTo_station_no(String to_station_no) {
		this.to_station_no = to_station_no;
	}

	public String getSeat_types() {
		return seat_types;
	}

	public void setSeat_types(String seat_types) {
		this.seat_types = seat_types;
	}

	public String getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}

	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBest_seat() {
		return best_seat;
	}

	public void setBest_seat(String best_seat) {
		this.best_seat = best_seat;
	}

	public String getOne_seat() {
		return one_seat;
	}

	public void setOne_seat(String one_seat) {
		this.one_seat = one_seat;
	}

	public String getTwo_seat() {
		return two_seat;
	}

	public void setTwo_seat(String two_seat) {
		this.two_seat = two_seat;
	}

	public String getVag_sleeper() {
		return vag_sleeper;
	}

	public void setVag_sleeper(String vag_sleeper) {
		this.vag_sleeper = vag_sleeper;
	}

	public String getSoft_sleeper() {
		return soft_sleeper;
	}

	public void setSoft_sleeper(String soft_sleeper) {
		this.soft_sleeper = soft_sleeper;
	}

	public String getHard_sleeper() {
		return hard_sleeper;
	}

	public void setHard_sleeper(String hard_sleeper) {
		this.hard_sleeper = hard_sleeper;
	}

	public String getSoft_seat() {
		return soft_seat;
	}

	public void setSoft_seat(String soft_seat) {
		this.soft_seat = soft_seat;
	}

	public String getHard_seat() {
		return hard_seat;
	}

	public void setHard_seat(String hard_seat) {
		this.hard_seat = hard_seat;
	}

	public String getNone_seat() {
		return none_seat;
	}

	public void setNone_seat(String none_seat) {
		this.none_seat = none_seat;
	}

	public String getStartStationCode() {
		return startStationCode;
	}

	public void setStartStationCode(String startStationCode) {
		this.startStationCode = startStationCode;
	}

	public String getEndStationCode() {
		return endStationCode;
	}

	public void setEndStationCode(String endStationCode) {
		this.endStationCode = endStationCode;
	}

	public String getFromStationCode() {
		return fromStationCode;
	}

	public void setFromStationCode(String fromStationCode) {
		this.fromStationCode = fromStationCode;
	}

	public String getToStationCode() {
		return toStationCode;
	}

	public void setToStationCode(String toStationCode) {
		this.toStationCode = toStationCode;
	}

	/*
	 * private String startType = null; private String arriveType = null;
	 * private String price_url = null; private String business_prc =
	 * null;//商务座价格 private String best_seat_prc = null;//特等座价格 private String
	 * one_seat_prc = null;//一等座价格 private String two_seat_prc = null;//二等座价格
	 * private String vag_sleeper_prc = null;//高级软座价格 private String
	 * soft_sleeper_prc = null;//软卧价格 private String hard_sleeper_prc =
	 * null;//硬卧价格 private String soft_seat_prc = null;//软座 private String
	 * hard_seat_prc = null;//硬座
	 */

}
