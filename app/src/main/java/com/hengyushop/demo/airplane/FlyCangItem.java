package com.hengyushop.demo.airplane;

import java.io.Serializable;

public class FlyCangItem implements Serializable {
	private String flightNo;
	private String seatCode;
	private String seatStatus;
	private String discount;
	private String seatMsg;
	private String parPrice;
	private String seatType;
	private String settlePrice;

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getSeatMsg() {
		return seatMsg;
	}

	public void setSeatMsg(String seatMsg) {
		this.seatMsg = seatMsg;
	}

	public String getParPrice() {
		return parPrice;
	}

	public void setParPrice(String parPrice) {
		this.parPrice = parPrice;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(String settlePrice) {
		this.settlePrice = settlePrice;
	}

}
