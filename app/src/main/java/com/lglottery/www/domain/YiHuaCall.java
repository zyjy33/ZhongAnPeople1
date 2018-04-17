package com.lglottery.www.domain;

import java.io.Serializable;

public class YiHuaCall implements Serializable{
	/**
	 * 益话的通话记录
	 */
	private int type;
	private String time;
	private String name;
	private String phone;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
