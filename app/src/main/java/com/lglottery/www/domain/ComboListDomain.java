package com.lglottery.www.domain;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 关于列表的实体对象
 * @author cloor
 *
 */
public class ComboListDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String bussiness_id;
	private String name;
	private String branch_name;
	private String first_region;
	private String second_region;
	private String distance;
	private ArrayList<ComboDomain> domains;
	public String getBussiness_id() {
		return bussiness_id;
	}
	public void setBussiness_id(String bussiness_id) {
		this.bussiness_id = bussiness_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getFirst_region() {
		return first_region;
	}
	public void setFirst_region(String first_region) {
		this.first_region = first_region;
	}
	public String getSecond_region() {
		return second_region;
	}
	public void setSecond_region(String second_region) {
		this.second_region = second_region;
	}
 
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public ArrayList<ComboDomain> getDomains() {
		return domains;
	}
	public void setDomains(ArrayList<ComboDomain> domains) {
		this.domains = domains;
	}
	
	 

}
