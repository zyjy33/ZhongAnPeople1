package com.zams.www.health.business;
/**
 * 健康 管理列表对象
 * @author yunsenA
 *
 */
public class HealthManagerModel {
	//	"id": 3512,
	//	"company_id": 5808,
	//	"company_name": "朝阳服务大厅",
	//	"img_url": null,
	//	"count": 1812
	private int id;
	private int company_id;
	private String company_name;
	private String img_url;
	private int count;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}



}
