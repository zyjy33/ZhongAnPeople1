package com.lglottery.www.adapter;

import java.io.Serializable;

public class JDbean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String path;
	private String scenID;
	private String scenAdd;
	private String scenSum;
	private String cityId;
	private String cityName;
	private String countId;
	private String countName;
	private String disrance;
	private String amount;
	private String amountAdv;
	private String lon;
	private String lat;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getScenID() {
		return scenID;
	}

	public void setScenID(String scenID) {
		this.scenID = scenID;
	}

	public String getScenAdd() {
		return scenAdd;
	}

	public void setScenAdd(String scenAdd) {
		this.scenAdd = scenAdd;
	}

	public String getScenSum() {
		return scenSum;
	}

	public void setScenSum(String scenSum) {
		this.scenSum = scenSum;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountId() {
		return countId;
	}

	public void setCountId(String countId) {
		this.countId = countId;
	}

	public String getCountName() {
		return countName;
	}

	public void setCountName(String countName) {
		this.countName = countName;
	}

	public String getDisrance() {
		return disrance;
	}

	public void setDisrance(String disrance) {
		this.disrance = disrance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountAdv() {
		return amountAdv;
	}

	public void setAmountAdv(String amountAdv) {
		this.amountAdv = amountAdv;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

}
