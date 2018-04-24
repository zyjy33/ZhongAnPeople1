package com.hengyushop.demo.service;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.HomeActivity;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.UserLoginWayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CopyOfYunshangServiceActivity extends Fragment {
	private ListView list_shop_cart;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf, tv_shanchu;
	private LinearLayout list_shops, list_none;
	private WareDao wareDao;
	private ShopCartData dm;
	private ShopCartData data;
	private DialogProgress progress;
	private String strUrl;
	private String yth;
	private MyPopupWindowMenu popupWindowMenu;
	private EditText tv_amount_jf;
	private UserRegisterData registerData;
	private CheckBox in_jf, shopcart_item_check;
	ArrayList<ShopCartData> list_ll;
	static StringBuffer sb;
	int shopping_id;
	private int ID;
	String user_id;
	private CheckBox ck_xuanzhe;
	private Button btn_register;
	private SharedPreferences spPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.activity_yun_service, null);
		progress = new DialogProgress(getActivity());
		spPreferences = getActivity().getSharedPreferences("longuserset",
				Context.MODE_PRIVATE);
		// user_name = spPreferences.getString("user", "");
		// nickname = spPreferences.getString("nickname", "");
		ininate(layout);
		// loadWeather();
		setTotalCost();
		return layout;
	}

	String user_name_weixin = "";
	String user_name_qq = "";
	String weixin = "";
	String qq = "";
	String nickname = "";
	String user_name = "";
	String user_name_phone = "";
	String user_name_3_wx = "";
	String user_name_3_qq = "";
	String user_name_3 = "";
	String user_name_key = "";
	String oauth_name;
	String datall;

	@Override
	public void onResume() {

		super.onResume();
		SharedPreferences spPreferences_login = getActivity()
				.getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");

		System.out.println("MyPosterView.type=======1=========="
				+ MyPosterView.type);
		System.out.println("HomeActivity.type=======1==============="
				+ HomeActivity.type);
		if (MyPosterView.type == true) {
			MyPosterView.mQuery.clear();
			MyPosterView.type = false;
		}
		if (HomeActivity.type == true) {
			HomeActivity.mAq.clear();
			HomeActivity.type = false;
		}

		System.out.println("HomeActivity.type=======2=========="
				+ HomeActivity.type);
		System.out.println("MyPosterView.type=======2==============="
				+ MyPosterView.type);

		System.out.println("nickname=================" + nickname);
		if (!nickname.equals("")) {
			getjianche();// 后台检测是否绑定手机
		} else {
			getuserxinxi();
		}

	}

	private void getjianche() {

		SharedPreferences spPreferences_login = getActivity()
				.getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");
		String headimgurl = spPreferences_login.getString("headimgurl", "");
		String unionid = spPreferences_login.getString("unionid", "");
		String access_token = spPreferences_login.getString("access_token", "");
		String sex = spPreferences_login.getString("sex", "");

		System.out.println("UserLoginActivity====================="
				+ UserLoginActivity.oauth_name);
		System.out.println("UserLoginWayActivity====================="
				+ UserLoginWayActivity.oauth_name);

		if (UserLoginActivity.oauth_name.equals("weixin")) {
			oauth_name = "weixin";
		} else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
			oauth_name = "qq";
			unionid = "";
		}

		System.out.println("nickname-----1-----" + nickname);
		String nick_name = nickname.replaceAll("\\s*", "");
		System.out.println("nick_name-----2-----" + nick_name);

		// String strUrlone = RealmName.REALM_NAME_LL +
		// "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+""
		// +
		// "&province=&city=&country=&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";

		String oauth_openid = spPreferences_login.getString("oauth_openid", "");
		String strUrlone = RealmName.REALM_NAME_LL
				+ "/user_oauth_register_0217?nick_name=" + nick_name + "&sex="
				+ sex + "&avatar=" + headimgurl + ""
				+ "&province=&city=&country=&oauth_name=" + oauth_name
				+ "&oauth_unionid=" + unionid + "" + "&oauth_openid="
				+ oauth_openid + "";

		System.out.println("我的======11======1=======" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				System.out.println("我的======输出=====1========" + arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					// if (status.equals("y")) {
					datall = object.getString("data");
					// JSONObject obj = object.getJSONObject("data");
					// data.id = obj.getString("id");
					// data.user_name = obj.getString("user_name");
					// province = obj.getString("province");
					// city = obj.getString("city");
					// area = obj.getString("area");

					System.out.println("datall==============" + datall);
					if (datall.equals("null")) {

						SharedPreferences spPreferences_tishi = getActivity()
								.getSharedPreferences("longuserset_tishi",
										Context.MODE_PRIVATE);
						weixin = spPreferences_tishi.getString("weixin", "");
						qq = spPreferences_tishi.getString("qq", "");
						System.out
								.println("=================weixin==" + weixin);
						System.out.println("=================qq==" + qq);

						System.out.println("UserLoginActivity.panduan====1=="
								+ UserLoginActivity.panduan_tishi);
						System.out
								.println("UserLoginWayActivity.panduan====2=="
										+ UserLoginWayActivity.panduan_tishi);
						if (!nickname.equals("")) {

							if (UserLoginActivity.panduan_tishi == true) {
								if (weixin.equals("weixin")) {
								} else {
									Intent intent1 = new Intent(getActivity(),
											TishiWxBangDingActivity.class);
									startActivity(intent1);
									UserLoginActivity.panduan_tishi = false;
								}

							} else if (UserLoginWayActivity.panduan_tishi == true) {
								if (qq.equals("qq")) {
								} else {
									Intent intent2 = new Intent(getActivity(),
											TishiWxBangDingActivity.class);
									startActivity(intent2);
									UserLoginWayActivity.panduan_tishi = false;
								}
							}
						}

					} else {
						UserRegisterllData data = new UserRegisterllData();
						JSONObject obj = object.getJSONObject("data");
						data.id = obj.getString("id");
						data.user_name = obj.getString("user_name");
						user_id = data.id;
						System.out
								.println("---data.user_name-------------------"
										+ data.user_name);
						System.out.println("---user_id-------------------"
								+ user_id);
						if (data.user_name.equals("匿名")) {
							// if (data.id.equals("0")) {
							System.out.println("---微信还未绑定-------------------");
							Intent intent1 = new Intent(getActivity(),
									TishiWxBangDingActivity.class);
							startActivity(intent1);
						} else {
							SharedPreferences spPreferences = getActivity()
									.getSharedPreferences("longuserset",
											Context.MODE_PRIVATE);
							String user = spPreferences.getString("user", "");
							System.out
									.println("---1-------------------" + user);
							data.login_sign = obj.getString("login_sign");

							Editor editor = spPreferences.edit();
							editor.putString("user", data.user_name);
							editor.putString("user_id", data.id);
							editor.putString("login_sign", data.login_sign);
							editor.commit();

							String user_name = spPreferences.getString("user",
									"");
							System.out.println("---2-------------------"
									+ user_name);
						}
					}

					getuserxinxi();

				} catch (JSONException e) {

					e.printStackTrace();
				}

				// try {
				// JSONObject object = new JSONObject(arg1);
				// String status = object.getString("status");
				// String info = object.getString("info");
				// if (status.equals("y")) {
				// JSONObject obj = object.getJSONObject("data");
				// UserRegisterllData data = new UserRegisterllData();
				// data.id = obj.getString("id");
				// data.user_name = obj.getString("user_name");
				// // province = obj.getString("province");
				// // city = obj.getString("city");
				// // area = obj.getString("area");
				// System.out.println("---data.user_name-------------------"+data.user_name);
				// user_name_phone = data.user_name;
				// user_id = data.id;
				// System.out.println("---user_name_phone-------------------"+user_name_phone);
				// System.out.println("---user_id-------------------"+user_id);
				// SharedPreferences spPreferences =
				// getActivity().getSharedPreferences("longuserset_user",
				// Context.MODE_PRIVATE);
				// // spPreferences_qq.edit().clear().commit();
				// String user = spPreferences.getString("user", "");
				// System.out.println("---1-------------------"+user);
				// Editor editor = spPreferences.edit();
				// editor.putString("user",data.user_name);
				// editor.putString("user_id", data.id);
				// editor.commit();
				//
				// String user_name = spPreferences.getString("user", "");
				// System.out.println("---2-------------------"+user_name);
				//
				// getuserxinxi();
				// }else {
				// Intent intent = new Intent(getActivity(),
				// TishiWxBangDingActivity.class);
				// startActivity(intent);
				// // Toast.makeText(getActivity(), info, 200).show();
				// }
				// } catch (JSONException e) {
				//
				// e.printStackTrace();
				// }
			};
		}, getActivity());

	}

	private void getuserxinxi() {

		spPreferences = getActivity().getSharedPreferences("longuserset",
				Context.MODE_PRIVATE);
		user_name_phone = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		System.out
				.println("user_name_phone=================" + user_name_phone);
		System.out.println("user_id=================" + user_id);
		// if (user_name_phone == null) {
		// Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
		// startActivity(intent48);
		// }else {
		// 接口调用user_name的参数值
		if (!user_name_phone.equals("")) {
			user_name_key = user_name_phone;
		}

		// SharedPreferences spPreferences =
		// getActivity().getSharedPreferences("longuserset",
		// Context.MODE_PRIVATE);
		// Editor editor = spPreferences.edit();
		// editor.putString("user", user_name_key);
		// editor.putString("user_id", user_id);
		// editor.commit();

		System.out.println("user_name================" + user_name);
		System.out.println("user_name_3_wx=================" + user_name_3_wx);
		System.out.println("user_name_3_qq=================" + user_name_3_qq);
		// }
	}

	/**
	 * 初始化控件类别
	 */
	private void ininate(View layout) {
		// list_none = (LinearLayout)layout.findViewById(R.id.list_none);
		// list_shops = (LinearLayout)layout.findViewById(R.id.list_shops);
		ck_xuanzhe = (CheckBox) layout.findViewById(R.id.ck_xuanzhe);
		// shopcart_item_check =
		// (CheckBox)layout.findViewById(R.id.shopcart_item_check);
		btn_register = (Button) layout.findViewById(R.id.btn_register);
		// list_shop_cart = (ListView)layout.findViewById(R.id.list_shop_cart);
		// tv_endnumber = (TextView)layout.findViewById(R.id.tv_number);
		// tv_shanchu = (TextView) layout.findViewById(R.id.tv_shanchu);
		// tv_endmarketprice =
		// (TextView)layout.findViewById(R.id.tv_original_price);
		// tv_preferential =
		// (TextView)layout.findViewById(R.id.tv_preferential);
		// tv_endmoney = (TextView)layout.findViewById(R.id.tv_amount_payable);
		// tv_amount_jf = (EditText)layout.findViewById(R.id.tv_amount_jf);
		// jf = (TextView)layout.findViewById(R.id.jf);
	}

	public void setTotalCost() {

		ck_xuanzhe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ck_xuanzhe.isChecked()) {

					// Toast.makeText(getActivity(), "登录成功1", 1000).show();
					getShowButton();
				} else {
					getNisabledButton();
					// Toast.makeText(getActivity(), "登录成功2", 2000).show();
				}
			}
		});

		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!nickname.equals("")) {
					if (!user_name_phone.equals("")) {
						Intent Intent2 = new Intent(getActivity(),
								ServiceGuangGaoActivity.class);
						startActivity(Intent2);
					} else {
						Intent intent = new Intent(getActivity(),
								TishiWxBangDingActivity.class);
						startActivity(intent);
					}
				} else {
					if (user_name_phone.equals("")) {
						Intent intentll = new Intent(getActivity(),
								UserLoginActivity.class);
						startActivity(intentll);
					} else {
						Intent intent = new Intent(getActivity(),
								ServiceGuangGaoActivity.class);
						startActivity(intent);
					}
				}
			}
		});
	}

	/*
	 * 不可用登录按钮
	 */

	public void getNisabledButton() {
		btn_register.setClickable(false);
		btn_register.setBackgroundResource(R.drawable.bg_ccc_3_5_bg);
	}

	/*
	 * 恢复登录按钮
	 */
	public void getShowButton() {
		btn_register.setClickable(true);
		btn_register.setBackgroundResource(R.drawable.btn_red_3_5_bg);
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
				case 0:
					break;
				case 1:
					break;
				default:
					break;
			}
			super.dispatchMessage(msg);
		}
	};

	/**
	 * 获取购物车列表数据
	 */
	// private void loadWeather() {
	// list_ll = new ArrayList<ShopCartData>();
	// progress.CreateProgress();
	// String id = UserLoginActivity.id;
	// System.out.println("结果呢1=============="+id);
	// AsyncHttp.get(RealmName.REALM_NAME_LL+
	// "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="+19+""
	// ,new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int arg0,String arg1) {
	//
	// super.onSuccess(arg0, arg1);
	// try {
	// JSONObject jsonObject = new JSONObject(arg1);
	// System.out.println("1================"+arg1);
	// JSONArray jsot = jsonObject.getJSONArray("data");
	// data = new ShopCartData();
	// for (int i = 0; i < jsot.length(); i++) {
	// JSONObject object = jsot.getJSONObject(i);
	// dm = new ShopCartData();
	// data.title = object.getString("title");
	// data.market_price = object.getString("market_price");
	// data.sell_price = object.getString("sell_price");
	// data.id = object.getString("id");
	// data.quantity = object.getInt("quantity");
	// data.img_url = object.getString("img_url");
	//
	// dm.setTitle(object.getString("title"));
	// dm.setMarket_price(object.getString("market_price"));
	// dm.setSell_price(object.getString("sell_price"));
	// dm.setId(object.getString("id"));
	// dm.setImg_url(object.getString("img_url"));
	// dm.setQuantity(object.getInt("quantity"));
	//
	// String zhou = dm.getSell_price();
	// System.out.println("21================"+zhou);
	// int geshu = object.getInt("sell_price");
	// System.out.println("22================"+geshu);
	// int sum = 0;
	// sum +=geshu;
	// // for (int j = 0; j < geshu; j++) {
	// // sum +=geshu;
	// // }
	// System.out.println("总额================"+sum);
	// list_ll.add(dm);
	// // list_ll.add(data);
	// }
	//
	// System.out.println("2================"+list_ll.size());
	// String zhou = dm.getSell_price();
	// // tv_endmoney.setText("￥" + zhou);
	// handler.sendEmptyMessage(0);
	// progress.CloseProgress();
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	// }
	//
	// }
	//
	// }, null);
	// }

}
