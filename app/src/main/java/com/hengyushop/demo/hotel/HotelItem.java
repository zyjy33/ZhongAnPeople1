package com.hengyushop.demo.hotel;

import java.io.Serializable;

public class HotelItem implements Serializable {
	private String id;
	private String name;
	private String img;
	private String address;
	private String start;
	private String jingdu;
	private String weidu;

	public String getJingdu() {
		return jingdu;
	}

	public void setJingdu(String jingdu) {
		this.jingdu = jingdu;
	}

	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
