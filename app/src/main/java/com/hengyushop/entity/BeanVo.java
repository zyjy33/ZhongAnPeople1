package com.hengyushop.entity;

public class BeanVo {
  private String name;;
  private String age;
  public String id;
  public String title;
  public String img_url;
  
  
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
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public BeanVo(String name, String age) {
	super();
	this.name = name;
	this.age = age;
}
  
}
