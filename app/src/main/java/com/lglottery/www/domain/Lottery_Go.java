package com.lglottery.www.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Lottery_Go implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8974146276530485518L;
	private String gameOrder;// �齱����
	private String userName;// ����
	private String yth;// �˺�
	private String ProductItemId;//���еĽ�Ʒid
	private String LotteryGameTypeId;//���еĽ�Ʒ����
	
	private ArrayList<Lottery_Gobean> arrayList;

	public String getProductItemId() {
		return ProductItemId;
	}

	public void setProductItemId(String productItemId) {
		ProductItemId = productItemId;
	}

	public String getLotteryGameTypeId() {
		return LotteryGameTypeId;
	}

	public void setLotteryGameTypeId(String lotteryGameTypeId) {
		LotteryGameTypeId = lotteryGameTypeId;
	}

	public String getGameOrder() {
		return gameOrder;
	}

	public void setGameOrder(String gameOrder) {
		this.gameOrder = gameOrder;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getYth() {
		return yth;
	}

	public void setYth(String yth) {
		this.yth = yth;
	}

	public ArrayList<Lottery_Gobean> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<Lottery_Gobean> arrayList) {
		this.arrayList = arrayList;
	}

}
