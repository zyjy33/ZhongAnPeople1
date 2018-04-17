package com.hengyushop.demo.airplane;

import java.io.Serializable;

public class FlyResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstTime;
	private String endTime;
	private String flyCompany;
	private String price;
	private String discount;
	private String OrgJetquay;
	private String DstJetquay;
	private String date;
	private String OrgCity;
	private String DstCity;
	private String FlightNo;
	private String PlaneType;
	private String Insurance;
	private String AudletAirportTax;
	private String AudletFuelTax;
	private String SeatCode;
	private String SeatItems_SerialCode;
	private String cangName;

	public String getCangName() {
		return cangName;
	}

	public void setCangName(String cangName) {
		this.cangName = cangName;
	}

	public String getSeatItems_SerialCode() {
		return SeatItems_SerialCode;
	}

	public void setSeatItems_SerialCode(String seatItems_SerialCode) {
		SeatItems_SerialCode = seatItems_SerialCode;
	}

	public String getSeatCode() {
		return SeatCode;
	}

	public void setSeatCode(String seatCode) {
		SeatCode = seatCode;
	}

	public String getInsurance() {
		return Insurance;
	}

	public void setInsurance(String insurance) {
		Insurance = insurance;
	}

	public String getAudletAirportTax() {
		return AudletAirportTax;
	}

	public void setAudletAirportTax(String audletAirportTax) {
		AudletAirportTax = audletAirportTax;
	}

	public String getAudletFuelTax() {
		return AudletFuelTax;
	}

	public void setAudletFuelTax(String audletFuelTax) {
		AudletFuelTax = audletFuelTax;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrgCity() {
		return OrgCity;
	}

	public void setOrgCity(String orgCity) {
		OrgCity = orgCity;
	}

	public String getDstCity() {
		return DstCity;
	}

	public void setDstCity(String dstCity) {
		DstCity = dstCity;
	}

	public String getFlightNo() {
		return FlightNo;
	}

	public void setFlightNo(String flightNo) {
		FlightNo = flightNo;
	}

	public String getPlaneType() {
		return PlaneType;
	}

	public void setPlaneType(String planeType) {
		PlaneType = planeType;
	}

	public String getOrgJetquay() {
		return OrgJetquay;
	}

	public void setOrgJetquay(String orgJetquay) {
		OrgJetquay = orgJetquay;
	}

	public String getDstJetquay() {
		return DstJetquay;
	}

	public void setDstJetquay(String dstJetquay) {
		DstJetquay = dstJetquay;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFlyCompany() {
		return flyCompany;
	}

	public void setFlyCompany(String flyCompany) {
		this.flyCompany = flyCompany;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

}
