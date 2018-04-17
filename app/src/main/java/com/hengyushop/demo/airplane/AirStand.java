package com.hengyushop.demo.airplane;

import java.io.Serializable;

public class AirStand implements Serializable {
	private String fly_code;
	private String arr_code;
	private String fly_city;
	private String arr_city;
	private String time;

	public String getFly_code() {
		return fly_code;
	}

	public void setFly_code(String fly_code) {
		this.fly_code = fly_code;
	}

	public String getArr_code() {
		return arr_code;
	}

	public void setArr_code(String arr_code) {
		this.arr_code = arr_code;
	}

	public String getFly_city() {
		return fly_city;
	}

	public void setFly_city(String fly_city) {
		this.fly_city = fly_city;
	}

	public String getArr_city() {
		return arr_city;
	}

	public void setArr_city(String arr_city) {
		this.arr_city = arr_city;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
