package com.hengyushop.demo.airplane;

import java.io.Serializable;
import java.util.ArrayList;

import com.hengyushop.dao.CardItem;
import com.loopj.android.http.RequestParams;

public class FlyOrderBean implements Serializable {
	private ArrayList<FlyDetailPop> detailPops;
	private String orderNumber;
	private String userName;
	private String phoneMobile;
	private String airCompany;
	private String airNumber;
	private String date;
	private String startTime;
	private String endTime;
	private String startAirPort;
	private String endAirPort;
	private String startAirNum;
	private String endAirNum;
	private String loginSession;
	private String PolicyOrderId;
	private String[] bankNames;
	private ArrayList<CardItem> banks;
	private String trade_no;

	public String[] getBankNames() {
		return bankNames;
	}

	public void setBankNames(String[] bankNames) {
		this.bankNames = bankNames;
	}

	public ArrayList<CardItem> getBanks() {
		return banks;
	}

	public void setBanks(ArrayList<CardItem> banks) {
		this.banks = banks;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getPolicyOrderId() {
		return PolicyOrderId;
	}

	public void setPolicyOrderId(String policyOrderId) {
		PolicyOrderId = policyOrderId;
	}

	public String getLoginSession() {
		return loginSession;
	}

	public void setLoginSession(String loginSession) {
		this.loginSession = loginSession;
	}

	public String getAirNumber() {
		return airNumber;
	}

	public void setAirNumber(String airNumber) {
		this.airNumber = airNumber;
	}

	public ArrayList<FlyDetailPop> getDetailPops() {
		return detailPops;
	}

	public void setDetailPops(ArrayList<FlyDetailPop> detailPops) {
		this.detailPops = detailPops;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public String getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartAirPort() {
		return startAirPort;
	}

	public void setStartAirPort(String startAirPort) {
		this.startAirPort = startAirPort;
	}

	public String getEndAirPort() {
		return endAirPort;
	}

	public void setEndAirPort(String endAirPort) {
		this.endAirPort = endAirPort;
	}

	public String getStartAirNum() {
		return startAirNum;
	}

	public void setStartAirNum(String startAirNum) {
		this.startAirNum = startAirNum;
	}

	public String getEndAirNum() {
		return endAirNum;
	}

	public void setEndAirNum(String endAirNum) {
		this.endAirNum = endAirNum;
	}

}
