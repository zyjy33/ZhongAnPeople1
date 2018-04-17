package com.lglottery.www.domain;

import java.io.Serializable;

public class Lglottery_Enter_Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6284991741381825973L;
	private String playId;
	private String num;
	private String img;
	private String name;
	private String itemId;
	private String price;

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
