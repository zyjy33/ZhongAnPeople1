package com.android.hengyu.post;

import java.io.Serializable;

public class QiuListDo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String UserResumeID;
	private String ResumeTitle;
	private String username;
	private String gender;
	private String age;
	private String WorkExperience_tmp;
	private String Education_tmp;
	private String CreateTime;
	public String getUserResumeID() {
		return UserResumeID;
	}
	public void setUserResumeID(String userResumeID) {
		UserResumeID = userResumeID;
	}
	public String getResumeTitle() {
		return ResumeTitle;
	}
	public void setResumeTitle(String resumeTitle) {
		ResumeTitle = resumeTitle;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getWorkExperience_tmp() {
		return WorkExperience_tmp;
	}
	public void setWorkExperience_tmp(String workExperience_tmp) {
		WorkExperience_tmp = workExperience_tmp;
	}
	public String getEducation_tmp() {
		return Education_tmp;
	}
	public void setEducation_tmp(String education_tmp) {
		Education_tmp = education_tmp;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	
}
