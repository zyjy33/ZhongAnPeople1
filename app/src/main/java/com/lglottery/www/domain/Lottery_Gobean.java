package com.lglottery.www.domain;

import java.io.Serializable;

public class Lottery_Gobean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeId;//�ڼ�����Ʒ
	private String itemId;//��ƷID
	private String proName;//��Ʒ����
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
