package com.zams.www.health;

import java.util.List;

public class HealthListModel {
	private String status;
	private String info;
	private String record;
	private List<HealthManagerModel>  data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public List<HealthManagerModel> getData() {
		return data;
	}
	public void setData(List<HealthManagerModel> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "HealthListModel [status=" + status + ", info=" + info
				+ ", record=" + record + ", data=" + data + "]";
	}
	

}
