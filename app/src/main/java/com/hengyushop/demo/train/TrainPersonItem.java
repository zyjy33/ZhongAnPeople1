package com.hengyushop.demo.train;

import java.io.Serializable;

public class TrainPersonItem implements Serializable{
	private boolean flag;
	private String TrainUserContactID;
	private String ContactUserName;
	private String ContactUserPhone;
	private String PiaoZhong;
	private String DocumentType;
	private String DocumentNumber;
	
	private String tempPiao;
	private String tempPrice;
	
	
	public String getTempPrice() {
		return tempPrice;
	}
	public void setTempPrice(String tempPrice) {
		this.tempPrice = tempPrice;
	}
	public String getTempPiao() {
		return tempPiao;
	}
	public void setTempPiao(String tempPiao) {
		this.tempPiao = tempPiao;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getTrainUserContactID() {
		return TrainUserContactID;
	}
	public void setTrainUserContactID(String trainUserContactID) {
		TrainUserContactID = trainUserContactID;
	}
	public String getContactUserName() {
		return ContactUserName;
	}
	public void setContactUserName(String contactUserName) {
		ContactUserName = contactUserName;
	}
	public String getContactUserPhone() {
		return ContactUserPhone;
	}
	public void setContactUserPhone(String contactUserPhone) {
		ContactUserPhone = contactUserPhone;
	}
	public String getPiaoZhong() {
		return PiaoZhong;
	}
	public void setPiaoZhong(String piaoZhong) {
		PiaoZhong = piaoZhong;
	}
	public String getDocumentType() {
		return DocumentType;
	}
	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}
	public String getDocumentNumber() {
		return DocumentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		DocumentNumber = documentNumber;
	}
	
}
