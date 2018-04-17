package com.lglottery.www.domain;

import java.io.Serializable;

public class PreP implements Serializable{
	private String market;
	private String price;
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
