package com.hengyushop.movie.adapter;

import java.io.Serializable;

public class OneBuyBean implements Serializable {
	private String id;
	private String num;
	private String price;
	private String name;
	private String img;
	private String time;
	private String market;
	private String recommend;
	private String joinNum;
	private String longImg;

	public String getLongImg() {
		return longImg;
	}

	public void setLongImg(String longImg) {
		this.longImg = longImg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(String joinNum) {
		this.joinNum = joinNum;
	}

}
