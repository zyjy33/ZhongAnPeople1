package com.hengyushop.movie.adapter;

import java.io.Serializable;

public class OneNewBean implements Serializable {
	private String id;
	private String proname;
	private String img;
	private String time;
	private String username;
	private String code;
	private String number;
	private String count;
	private String LuckDrawBatchOrderNumber;

	public String getLuckDrawBatchOrderNumber() {
		return LuckDrawBatchOrderNumber;
	}

	public void setLuckDrawBatchOrderNumber(String luckDrawBatchOrderNumber) {
		LuckDrawBatchOrderNumber = luckDrawBatchOrderNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
