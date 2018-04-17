package com.hengyushop.demo.hotel;

import java.io.Serializable;

public class HotelImageDo implements Serializable{
	private String url;
	private String tag;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
