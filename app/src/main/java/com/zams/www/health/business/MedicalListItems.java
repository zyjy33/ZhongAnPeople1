package com.zams.www.health.business;

import java.util.List;

public class MedicalListItems {
	private String status;
	private String code;
	private String info;
	private List<MedicalItems> data;
	private int cecord;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


	public List<MedicalItems> getData() {
		return data;
	}

	public void setData(List<MedicalItems> data) {
		this.data = data;
	}

	public int getCecord() {
		return cecord;
	}

	public void setCecord(int cecord) {
		this.cecord = cecord;
	}

}
