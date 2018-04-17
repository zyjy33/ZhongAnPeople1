package com.hengyushop.entity;

import java.util.ArrayList;

public class GoodsListData {

	public String trade_title;
	public String logo_url;
	public String img_url;
	public String name;
	public String point;
	public String price;
	public String id;
	public String title;
	public String goods_price;
	public String user_id;
	
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	private ArrayList<GoodsListBean> list;
	
	public String getTrade_title() {
		return trade_title;
	}
	public void setTrade_title(String trade_title) {
		this.trade_title = trade_title;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}
	public ArrayList<GoodsListBean> getList() {
		return list;
	}
	public void setList(ArrayList<GoodsListBean> list) {
		this.list = list;
	}
	

}
