package com.lglottery.www.domain;

import java.io.Serializable;

public class ComboDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7787715140190505513L;
	private String required;
	private String list_price;
	private String current_price;
	private String image_url;
	private String title;
	private String publish_data;
	private String deal_id;

	public String getPublish_data() {
		return publish_data;
	}

	public void setPublish_data(String publish_data) {
		this.publish_data = publish_data;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getList_price() {
		return list_price;
	}

	public void setList_price(String list_price) {
		this.list_price = list_price;
	}

	public String getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(String current_price) {
		this.current_price = current_price;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeal_id() {
		return deal_id;
	}

	public void setDeal_id(String deal_id) {
		this.deal_id = deal_id;
	}

}
