package com.hengyushop.dot.data;

import java.io.Serializable;

public class VipDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String price;
	private String bao;
	private String des;
	private String img;
	private String img1;
	private String img2;
	private String pro;
	private String w;

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	private String ProductBenchmarkPriceID;

	public String getProductBenchmarkPriceID() {
		return ProductBenchmarkPriceID;
	}

	public void setProductBenchmarkPriceID(String productBenchmarkPriceID) {
		ProductBenchmarkPriceID = productBenchmarkPriceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBao() {
		return bao;
	}

	public void setBao(String bao) {
		this.bao = bao;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

}
