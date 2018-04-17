package com.lglottery.www.common;

import com.android.hengyu.web.RealmName;

/**
 * 关于数据链接地址的管理
 * @author cloor
 *
 */
public class U {
	/**
	 * 获取数据的服务器地址
	 */
	public final static String IP = RealmName.REALM_NAME+"/";
	public static String GET_RND = IP + "mi/getRnd.ashx?key=jes800&msisdn=0";
	// http://183.62.138.31:1717/mi/login.ashx?yth=lemon123&pwd=md5(md5(pwd)+rnd)&msisdn=0
	public static String LOTTERY_LOGIN = IP + "mi/login.ashx?msisdn=0";
	// http://183.62.138.31:1717/mi/LotteryGame.ashx?yth=lemon123&act=GetGame_LotteryGameGroup
	public static String GET_LOTTERY_ITEM = IP
			+ "mi/LotteryGame.ashx?act=GetGame_LotteryGameGroup";// yth=lemon123&
	// http://183.62.138.31:1717/mi/LotteryGame.ashx?act=GetGame_LotteryGameItems&
	public static String LOTTERY_ITEM_BY_CLICK = IP
			+ "mi/LotteryGame.ashx?act=GetGame_LotteryGameItems";
	// http://183.62.138.31:1717/mi/getdata.ashx?act=myInfo&key=1&yth=1
	public static String LOTTERY_PERSONAL_INFO = IP
			+ "mi/getdata.ashx";
	//http://183.62.138.31:1717/mi/LotteryGame.ashx?yth=994444499&act=GetLotteryHistoryResult
	public static String LOTTERY_LOGS=  IP+"mi/LotteryGame.ashx?act=GetLotteryHistoryResult";
	//http://183.62.138.31:1717/mi/LotteryGame.ashx?yth=994444499&act=SubmitLotteryInfo&LotteryGameGroupId=1&GameGroupCostshopPassTicket=1&GameGroupCostCredit=0&LotteryGameItemId=1_2_3_4_5_6&paypwd=0&OnePhaseCostPassTicket=30&OnePhaseGroupCostCredit=10
	public static String LOTTERY_READLY=IP+"mi/LotteryGame.ashx?act=SubmitLotteryInfo";
	//http://183.62.138.31:1717/mi/LotteryGame.ashx?yth=994444499&act=GetLotteryResult&GamePhaseOrder=1
	public static String LOTTERY_GO = IP+"mi/LotteryGame.ashx?act=GetLotteryResult";
}
