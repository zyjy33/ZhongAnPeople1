package com.hengyushop.movie.adapter;

import java.io.Serializable;
import java.util.ArrayList;

public class One_Person_Bean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String YiYuanID;
	private String LuckDrawBatchOrderNumber;
	private String CurrentGameDone;
	private String ProductItemId;
	private String proName;
	private String proFaceImg;
	private String NeedGameUserNum;
	private String HasJoinedNum;
	private String HaveTatalJuGouMa;
	private ArrayList<One_PersonChld> child;

	public ArrayList<One_PersonChld> getChild() {
		return child;
	}

	public void setChild(ArrayList<One_PersonChld> child) {
		this.child = child;
	}

	public String getYiYuanID() {
		return YiYuanID;
	}

	public void setYiYuanID(String yiYuanID) {
		YiYuanID = yiYuanID;
	}

	public String getLuckDrawBatchOrderNumber() {
		return LuckDrawBatchOrderNumber;
	}

	public void setLuckDrawBatchOrderNumber(String luckDrawBatchOrderNumber) {
		LuckDrawBatchOrderNumber = luckDrawBatchOrderNumber;
	}

	public String getCurrentGameDone() {
		return CurrentGameDone;
	}

	public void setCurrentGameDone(String currentGameDone) {
		CurrentGameDone = currentGameDone;
	}

	public String getProductItemId() {
		return ProductItemId;
	}

	public void setProductItemId(String productItemId) {
		ProductItemId = productItemId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProFaceImg() {
		return proFaceImg;
	}

	public void setProFaceImg(String proFaceImg) {
		this.proFaceImg = proFaceImg;
	}

	public String getNeedGameUserNum() {
		return NeedGameUserNum;
	}

	public void setNeedGameUserNum(String needGameUserNum) {
		NeedGameUserNum = needGameUserNum;
	}

	public String getHasJoinedNum() {
		return HasJoinedNum;
	}

	public void setHasJoinedNum(String hasJoinedNum) {
		HasJoinedNum = hasJoinedNum;
	}

	public String getHaveTatalJuGouMa() {
		return HaveTatalJuGouMa;
	}

	public void setHaveTatalJuGouMa(String haveTatalJuGouMa) {
		HaveTatalJuGouMa = haveTatalJuGouMa;
	}

	/*
	 * "LuckDrawBatchOrderNumber": "1", "CurrentGameDone": "0", "ProductItemId":
	 * "7692", "proName": "TO COOL FOR SCHOOL¼¦µ°Ä½Ë¹ÃæÄ¤", "proFaceImg":
	 * "uploadFiles/2015-02-03/201523163026756778.jpg", "NeedGameUserNum": "38",
	 * "HasJoinedNum": "7", "HaveTatalJuGouMa"
	 */
}
