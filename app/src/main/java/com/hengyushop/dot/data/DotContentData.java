package com.hengyushop.dot.data;

public class DotContentData {

	public int ProductCategoryID;
	public int ProID;
	public String ProName;
	public String proFaceImg;
	public String proFaceBigImg;
	public String originalityPrice;
	public String retailPrice;
	public String proComputerInfo;

	public int getProductCategoryID() {
		return ProductCategoryID;
	}

	public void setProductCategoryID(int productCategoryID) {
		ProductCategoryID = productCategoryID;
	}

	public int getProID() {
		return ProID;
	}

	public void setProID(int proID) {
		ProID = proID;
	}

	public String getProName() {
		return ProName;
	}

	public void setProName(String proName) {
		ProName = proName;
	}

	public String getProFaceImg() {
		return proFaceImg;
	}

	public void setProFaceImg(String proFaceImg) {
		this.proFaceImg = proFaceImg;
	}

	public String getProFaceBigImg() {
		return proFaceBigImg;
	}

	public void setProFaceBigImg(String proFaceBigImg) {
		this.proFaceBigImg = proFaceBigImg;
	}

	public String getOriginalityPrice() {
		return originalityPrice;
	}

	public void setOriginalityPrice(String originalityPrice) {
		this.originalityPrice = originalityPrice;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getProComputerInfo() {
		return proComputerInfo;
	}

	public void setProComputerInfo(String proComputerInfo) {
		this.proComputerInfo = proComputerInfo;
	}

}
