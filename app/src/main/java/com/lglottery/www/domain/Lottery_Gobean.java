package com.lglottery.www.domain;

import java.io.Serializable;

public class Lottery_Gobean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeId;//第几类商品
	private String itemId;//商品ID
	private String proName;//商品名字
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	
	
}
