package com.lglottery.www.domain;

import java.io.Serializable;

public class Lglottery_Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playName;
	private String id;
	private int balance;
	private int jinbi;
	private boolean isClick;

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getJinbi() {
		return jinbi;
	}

	public void setJinbi(int jinbi) {
		this.jinbi = jinbi;
	}

}
