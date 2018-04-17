package com.lglottery.www.domain;

import java.io.Serializable;

public class CircleBean implements Serializable {
	private String msg;
	private String msg1;
	private String PrizeTypeID;
	private String LuckDrawSerialNumber;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String getPrizeTypeID() {
		return PrizeTypeID;
	}

	public void setPrizeTypeID(String prizeTypeID) {
		PrizeTypeID = prizeTypeID;
	}

	public String getLuckDrawSerialNumber() {
		return LuckDrawSerialNumber;
	}

	public void setLuckDrawSerialNumber(String luckDrawSerialNumber) {
		LuckDrawSerialNumber = luckDrawSerialNumber;
	}

}
