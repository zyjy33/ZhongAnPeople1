package com.lglottery.www.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ComboDetailDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private String list_price;
	private String current_price;
	private String purchase_count;
	private String image_url;
	private String is_required;
	private String is_refundable;
	private String tips;
	private String total;
	private String review_url;
	private String businessDeals;
	private String purchase_deadline;
	private String details;
	private ArrayList<ComboOtherItem> items;
	
	public ArrayList<ComboOtherItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<ComboOtherItem> items) {
		this.items = items;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getPurchase_deadline() {
		return purchase_deadline;
	}
	public void setPurchase_deadline(String purchase_deadline) {
		this.purchase_deadline = purchase_deadline;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getPurchase_count() {
		return purchase_count;
	}
	public void setPurchase_count(String purchase_count) {
		this.purchase_count = purchase_count;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getIs_required() {
		return is_required;
	}
	public void setIs_required(String is_required) {
		this.is_required = is_required;
	}
	public String getIs_refundable() {
		return is_refundable;
	}
	public void setIs_refundable(String is_refundable) {
		this.is_refundable = is_refundable;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getReview_url() {
		return review_url;
	}
	public void setReview_url(String review_url) {
		this.review_url = review_url;
	}
	public String getBusinessDeals() {
		return businessDeals;
	}
	public void setBusinessDeals(String businessDeals) {
		this.businessDeals = businessDeals;
	}
	
	
}
