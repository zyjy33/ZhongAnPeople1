package com.lglottery.www.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;

public class LglotteryBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tag;
	private int balance;
	private int jinbi;
	private String id;// ß[‘ò½MÐòÌ–

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getJinbi() {
		return jinbi;
	}

	public void setJinbi(int jinbi) {
		this.jinbi = jinbi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Map<String, Queue<String>> maps;

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public Map<String, Queue<String>> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Queue<String>> maps) {
		this.maps = maps;
	}

}
