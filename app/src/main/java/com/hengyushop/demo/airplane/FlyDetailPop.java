package com.hengyushop.demo.airplane;

import java.io.Serializable;

public class FlyDetailPop implements Serializable {
	private String name;
	private String tagL;
	private String tagC;
	private String num;
	private String mob;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagL() {
		return tagL;
	}

	public void setTagL(String tagL) {
		this.tagL = tagL;
	}

	public String getTagC() {
		return tagC;
	}

	public void setTagC(String tagC) {
		this.tagC = tagC;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

}
