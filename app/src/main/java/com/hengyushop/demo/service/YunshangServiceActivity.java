package com.hengyushop.demo.service;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.airplane.adapter.ServiceListAdaper;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.WareInformationData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.UserLoginWayActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YunshangServiceActivity extends Fragment {
	private ListView list_shop_cart;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf,tv_shanchu;
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
	private CheckBox in_jf,shopcart_item_check;
	ArrayList<ShopCartData> list_ll;
	static StringBuffer sb;
	int shopping_id;
	private int ID;
	String user_id;
	private CheckBox ck_xuanzhe;
	private Button btn_register;
	private SharedPreferences spPreferences;
	private ImageView iv_biaoti,iv_biaoti1,iv_biaoti2,iv_biaoti3,iv_biaoti4;
	private ImageView zams_fw_1,zams_fw_2,zams_fw_3,zams_fw_4,zams_fw_5,zams_fw_6,zams_fw_7,
			zams_fw_8,zams_fw_9;
	ArrayList<WareInformationData> datas;
	private MyGridView myGridView;
	private MyPosterView advPager = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.activity_zams_service, null);
		progress = new DialogProgress(getActivity());
		spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		//		user_name = spPreferences.getString("user", "");
		//		nickname = spPreferences.getString("nickname", "");
		ininate(layout);
		//		loadWeather();
		//		get_service_list();
		return layout;
	}

	String user_name_weixin = "";
	String user_name_qq = "";
	String weixin = "";
	String qq = "";
	String nickname = "";
	String user_name = "";
	String user_name_phone = "";
	String user_name_key = "";
	String oauth_name;
	String datall;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");

		System.out.println("nickname================="+nickname);
		if (!nickname.equals("")) {
			getjianche();//后台检测是否绑定手机
		}else {
			getuserxinxi();
		}

	}


	private void getjianche() {
		// TODO Auto-generated method stub
		SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");
		String headimgurl = spPreferences_login.getString("headimgurl", "");
		String unionid = spPreferences_login.getString("unionid", "");
		String access_token = spPreferences_login.getString("access_token", "");
		String sex = spPreferences_login.getString("sex", "");

		System.out.println("UserLoginActivity====================="+UserLoginActivity.oauth_name);
		System.out.println("UserLoginWayActivity====================="+UserLoginWayActivity.oauth_name);

		if (UserLoginActivity.oauth_name.equals("weixin")) {
			oauth_name = "weixin";
		} else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
			oauth_name = "qq";
			unionid = "";
		}

		System.out.println("nickname-----1-----"+nickname);
		String nick_name = nickname.replaceAll("\\s*", "");
		System.out.println("nick_name-----2-----"+nick_name);

		//				String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
		//				"&province=&city=&country=&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";

		String oauth_openid = spPreferences_login.getString("oauth_openid", "");
		String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0217?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
				"&province=&city=&country=&oauth_name="+oauth_name+"&oauth_unionid="+unionid+"" +
				"&oauth_openid="+oauth_openid+"";

		System.out.println("我的======11======1======="+strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				System.out.println("我的======输出=====1========"+arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					//							if (status.equals("y")) {
					datall = object.getString("data");
					//								JSONObject obj = object.getJSONObject("data");
					//								data.id = obj.getString("id");
					//								data.user_name = obj.getString("user_name");
					//								province = obj.getString("province");
					//								city = obj.getString("city");
					//								area = obj.getString("area");

					System.out.println("datall=============="+datall);
					if (datall.equals("null")) {

						SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
						weixin = spPreferences_tishi.getString("weixin", "");
						qq = spPreferences_tishi.getString("qq", "");
						System.out.println("=================weixin==" + weixin);
						System.out.println("=================qq==" + qq);

						System.out.println("UserLoginActivity.panduan====1=="+UserLoginActivity.panduan_tishi);
						System.out.println("UserLoginWayActivity.panduan====2=="+UserLoginWayActivity.panduan_tishi);
						if (!nickname.equals("")) {

							if (UserLoginActivity.panduan_tishi == true) {
								if (weixin.equals("weixin")) {
								}else {
									Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
									startActivity(intent1);
									UserLoginActivity.panduan_tishi = false;
								}

							}else if (UserLoginWayActivity.panduan_tishi == true) {
								if (qq.equals("qq")) {
								}else {
									Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
									startActivity(intent2);
									UserLoginWayActivity.panduan_tishi = false;
								}
							}
						}

					}else {
						UserRegisterllData data = new UserRegisterllData();
						JSONObject obj = object.getJSONObject("data");
						data.id = obj.getString("id");
						data.user_name = obj.getString("user_name");
						user_id = data.id;
						System.out.println("---data.user_name-------------------"+data.user_name);
						System.out.println("---user_id-------------------"+user_id);
						if (data.user_name.equals("匿名")) {
							//										if (data.id.equals("0")) {
							System.out.println("---微信还未绑定-------------------");
							Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
							startActivity(intent1);
						}else {
							SharedPreferences spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
							String user = spPreferences.getString("user", "");
							System.out.println("---1-------------------"+user);
							data.login_sign = obj.getString("login_sign");

							Editor editor = spPreferences.edit();
							editor.putString("user",data.user_name);
							editor.putString("user_id", data.id);
							editor.putString("login_sign", data.login_sign);
							editor.commit();

							String user_name = spPreferences.getString("user", "");
							System.out.println("---2-------------------"+user_name);
						}
					}

					getuserxinxi();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, getActivity());

	}
	private void getuserxinxi() {
		// TODO Auto-generated method stub
		spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		System.out.println("user_name_phone================="+user_name);
		System.out.println("user_id================="+user_id);
		//接口调用user_name的参数值
		System.out.println("user_name================"+user_name);
	}
	/**
	 * 初始化控件类别
	 */
	private void ininate(View layout) {
		try {
			iv_biaoti = (ImageView) layout.findViewById(R.id.iv_biaoti);
			//		iv_biaoti1 = (ImageView) layout.findViewById(R.id.iv_biaoti1);
			//		iv_biaoti2 = (ImageView) layout.findViewById(R.id.iv_biaoti2);
			//		iv_biaoti3 = (ImageView) layout.findViewById(R.id.iv_biaoti3);
			//		iv_biaoti4 = (ImageView) layout.findViewById(R.id.iv_biaoti4);
			//		iv_biaoti.setBackgroundResource(R.drawable.zams_fuwu);
			Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.zams_fuwu);
			BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
			iv_biaoti.setBackgroundDrawable(bd);
			zams_fw_1 = (ImageView) layout.findViewById(R.id.zams_fw_1);
			zams_fw_2 = (ImageView) layout.findViewById(R.id.zams_fw_2);
			zams_fw_3 = (ImageView) layout.findViewById(R.id.zams_fw_3);
			zams_fw_4 = (ImageView) layout.findViewById(R.id.zams_fw_4);
			zams_fw_5 = (ImageView) layout.findViewById(R.id.zams_fw_5);
			zams_fw_6 = (ImageView) layout.findViewById(R.id.zams_fw_6);
			zams_fw_7 = (ImageView) layout.findViewById(R.id.zams_fw_7);
			zams_fw_8 = (ImageView) layout.findViewById(R.id.zams_fw_8);
			zams_fw_9 = (ImageView) layout.findViewById(R.id.zams_fw_9);
			//		zams_fw_1.setBackgroundResource(R.drawable.fw_tp1);
			//		zams_fw_2.setBackgroundResource(R.drawable.fw_tp2);
			//		zams_fw_3.setBackgroundResource(R.drawable.fw_tp3);
			//		zams_fw_4.setBackgroundResource(R.drawable.fw_tp4);
			//		zams_fw_5.setBackgroundResource(R.drawable.fw_tp5);
			//		zams_fw_6.setBackgroundResource(R.drawable.fw_tp6);
			//		zams_fw_7.setBackgroundResource(R.drawable.fw_tp7);
			//		zams_fw_8.setBackgroundResource(R.drawable.fw_tp8);
			//		zams_fw_9.setBackgroundResource(R.drawable.fw_tp9);

			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp1);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			zams_fw_1.setBackgroundDrawable(bd1);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp2);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			zams_fw_2.setBackgroundDrawable(bd2);
			Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp3);
			BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
			zams_fw_3.setBackgroundDrawable(bd3);
			Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp4);
			BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
			zams_fw_4.setBackgroundDrawable(bd4);
			Bitmap bm5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp5);
			BitmapDrawable bd5 = new BitmapDrawable(this.getResources(), bm5);
			zams_fw_5.setBackgroundDrawable(bd5);
			Bitmap bm6 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp6);
			BitmapDrawable bd6 = new BitmapDrawable(this.getResources(), bm6);
			zams_fw_6.setBackgroundDrawable(bd6);
			Bitmap bm7 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp7);
			BitmapDrawable bd7 = new BitmapDrawable(this.getResources(), bm7);
			zams_fw_7.setBackgroundDrawable(bd7);
			Bitmap bm8 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp8);
			BitmapDrawable bd8 = new BitmapDrawable(this.getResources(), bm8);
			zams_fw_8.setBackgroundDrawable(bd8);
			Bitmap bm9 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fw_tp9);
			BitmapDrawable bd9 = new BitmapDrawable(this.getResources(), bm9);
			zams_fw_9.setBackgroundDrawable(bd9);




			//		Bitmap thumb_1 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp1);
			////		Bitmap thumb_11 = BitmapUtil.comp(thumb_1);
			//		zams_fw_1.setImageBitmap(thumb_1);
			//		Bitmap thumb_2 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp2);
			////		Bitmap thumb_22 = BitmapUtil.comp(thumb_2);
			//		zams_fw_2.setImageBitmap(thumb_2);
			//		Bitmap thumb_3 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp3);
			////		Bitmap thumb_33 = BitmapUtil.comp(thumb_3);
			//		zams_fw_3.setImageBitmap(thumb_3);
			//		Bitmap thumb_4 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp4);
			////		Bitmap thumb_44 = BitmapUtil.comp(thumb_4);
			//		zams_fw_4.setImageBitmap(thumb_4);
			//		Bitmap thumb_5 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp5);
			////		Bitmap thumb_55 = BitmapUtil.comp(thumb_5);
			//		zams_fw_5.setImageBitmap(thumb_5);
			//		Bitmap thumb_6 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp6);
			////		Bitmap thumb_66 = BitmapUtil.comp(thumb_6);
			//		zams_fw_6.setImageBitmap(thumb_6);
			//		Bitmap thumb_7 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp7);
			////		Bitmap thumb_77 = BitmapUtil.comp(thumb_7);
			//		zams_fw_7.setImageBitmap(thumb_7);
			//		Bitmap thumb_8 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp8);
			////		Bitmap thumb_88 = BitmapUtil.comp(thumb_8);
			//		zams_fw_8.setImageBitmap(thumb_8);
			//		Bitmap thumb_9 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fw_tp9);
			////		Bitmap thumb_99 = BitmapUtil.comp(thumb_9);
			//		zams_fw_9.setImageBitmap(thumb_9);

			//		iv_biaoti1.setBackgroundResource(R.drawable.zams_service_1);
			//		iv_biaoti2.setBackgroundResource(R.drawable.zams_service_2);
			//		iv_biaoti3.setBackgroundResource(R.drawable.zams_service_3);
			//		iv_biaoti4.setBackgroundResource(R.drawable.zams_service_4);

			zams_fw_5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), PlatformhotlineActivity.class);
					startActivity(intent);
				}
			});

			myGridView = (MyGridView) layout.findViewById(R.id.gridView);
			myGridView.setFocusable(false);
			advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);

			myGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					System.out.println("====================="+datas.get(arg2).id);
					if (!nickname.equals("")) {
						if (!user_name.equals("")) {
							Intent intent13 = new Intent(getActivity(), Webview1.class);
							intent13.putExtra("web_id", datas.get(arg2).id);
							startActivity(intent13);
						} else {
							Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
							startActivity(intent2);
						}
					}else {
						if (user_name.equals("")) {
							Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
							startActivity(intent48);
						}else {
							Intent intent13 = new Intent(getActivity(), Webview1.class);
							intent13.putExtra("web_id", datas.get(arg2).id);
							startActivity(intent13);
						}
					}
				}
			});

			//		btn_register = (Button) layout.findViewById(R.id.btn_register);
			//		list_shops = (LinearLayout)layout.findViewById(R.id.list_shops);
			//		list_shop_cart = (ListView)layout.findViewById(R.id.list_shop_cart);
			//		tv_amount_jf = (EditText)layout.findViewById(R.id.tv_amount_jf);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
	 * 广告滚动
	 */
	private void loadWeather() {
		// TODO Auto-generated method stub
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_adbanner_list?advert_id=21",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							System.out.println("广告滚动-----------"+arg1);
							JSONObject object = new JSONObject(arg1);
							JSONArray array = object.getJSONArray("data");
							int len = array.length();
							ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
							for (int i = 0; i < len; i++) {
								AdvertDao1 ada = new AdvertDao1();
								JSONObject json = array.getJSONObject(i);
								ada.setId(json.getString("id"));
								ada.setAd_url(json.getString("ad_url"));
								ada.setLink_url(json.getString("link_url"));
								//	 String ad_url = ada.getAd_url();
								ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
								images.add(ada);
							}
							Message msg = new Message();
							msg.obj = images;
							msg.what = 0;
							childHandler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, getActivity());
	}
	/**
	 * 详情
	 * @param
	 */
	private void get_service_list(){
		//		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_top_list?channel_name=content&top=0&strwhere=status=0%20and%20category_id=2946"
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							System.out.println("详情===================="+arg1);
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								datas = new ArrayList<WareInformationData>();
								for (int i = 0; i < jsonArray.length(); i++) {
									WareInformationData data = new WareInformationData();
									JSONObject object = jsonArray.getJSONObject(i);
									data.id = object.getInt("id");
									data.img_url = object.getString("img_url");
									data.title = object.getString("title");
									//								data.sell_price = object.getString("sell_price");
									//								data.marketPrice = object.getString("market_price");
									//								data.article_id = object.getString("article_id");
									//								data.goods_id = object.getString("goods_id");
									datas.add(data);
								}
								//							handler.sendEmptyMessage(110);
							}else {
								Toast.makeText(getActivity(), info, 200).show();
							}
							ServiceListAdaper jdhadapter = new ServiceListAdaper(datas, getActivity());
							myGridView.setAdapter(jdhadapter);
							progress.CloseProgress();

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						try {
							System.out.println("=============================="+arg1);
							progress.CloseProgress();
							Toast.makeText(getActivity(), "异常", 200).show();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}, getActivity());

	}

	/**
	 * 广告
	 */
	ArrayList<AdvertDao1> tempss;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					tempss = (ArrayList<AdvertDao1>) msg.obj;
					ArrayList<String> urls = new ArrayList<String>();
					for (int i = 0; i < tempss.size(); i++) {
						urls.add(tempss.get(i).getAd_url());
					}
					// addvie(context, tempss,urls);
					ImageLoader imageLoader = ImageLoader.getInstance();
					advPager.setData(urls, new MyPosterOnClick() {
						@Override
						public void onMyclick(int position) {
							// TODO Auto-generated method stub
							if (!nickname.equals("")) {
								if (!user_name.equals("")) {
									//								String web_id = tempss.get(position).getId();
									//								System.out.println("web_id=============" + web_id);
									String link_url = tempss.get(position).getLink_url();
									System.out.println("link_url=============" + link_url);
									//								if (link_url.contains("goods")) {
									//									String id = link_url.substring(33, 37);
									//									System.out.println("id=============" + id);
									//									Intent intent = new Intent(getActivity(), WareInformationActivity.class);
									//									intent.putExtra("id", id);
									//									startActivity(intent);
									//								}else {
									Intent intent13 = new Intent(getActivity(), Webview1.class);
									//								intent13.putExtra("web_id", web_id);
									intent13.putExtra("link_url", link_url);
									startActivity(intent13);
									//								}
								} else {
									Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
									startActivity(intent2);
								}
							}else {
								if (user_name.equals("")) {
									Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
									startActivity(intent48);
								}else {
									//							String web_id = tempss.get(position).getId();
									//							System.out.println("web_id=============" + web_id);
									String link_url = tempss.get(position).getLink_url();
									System.out.println("link_url=============" + link_url);
									//							if (link_url.contains("goods")) {
									//								String id = link_url.substring(33, 37);
									//								System.out.println("id=============" + id);
									//								Intent intent = new Intent(getActivity(), WareInformationActivity.class);
									//								intent.putExtra("id", id);
									//								startActivity(intent);
									//							}else {
									Intent intent13 = new Intent(getActivity(), Webview1.class);
									//							intent13.putExtra("web_id", web_id);
									intent13.putExtra("link_url", link_url);
									startActivity(intent13);
									//							}
								}
							}

						}
					}, true, imageLoader, true);

					break;
				default:
					break;
			}
		};
	};

	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onStop() {
		super.onStop();
	}

	public void onDestroy() {
		super.onDestroy();
		try {
			BitmapDrawable bd = (BitmapDrawable)iv_biaoti.getBackground();
			iv_biaoti.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd.setCallback(null);
			bd.getBitmap().recycle();
			BitmapDrawable bd1 = (BitmapDrawable)zams_fw_1.getBackground();
			zams_fw_1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)zams_fw_2.getBackground();
			zams_fw_2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
			BitmapDrawable bd3 = (BitmapDrawable)zams_fw_3.getBackground();
			zams_fw_3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd3.setCallback(null);
			bd3.getBitmap().recycle();
			BitmapDrawable bd4 = (BitmapDrawable)zams_fw_4.getBackground();
			zams_fw_4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd4.setCallback(null);
			bd4.getBitmap().recycle();
			BitmapDrawable bd5 = (BitmapDrawable)zams_fw_5.getBackground();
			zams_fw_5.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd5.setCallback(null);
			bd5.getBitmap().recycle();
			BitmapDrawable bd6 = (BitmapDrawable)zams_fw_6.getBackground();
			zams_fw_6.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd6.setCallback(null);
			bd6.getBitmap().recycle();
			BitmapDrawable bd7 = (BitmapDrawable)zams_fw_7.getBackground();
			zams_fw_7.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd7.setCallback(null);
			bd7.getBitmap().recycle();
			BitmapDrawable bd8 = (BitmapDrawable)zams_fw_8.getBackground();
			zams_fw_8.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd8.setCallback(null);
			bd8.getBitmap().recycle();
			BitmapDrawable bd9 = (BitmapDrawable)zams_fw_9.getBackground();
			zams_fw_9.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd9.setCallback(null);
			bd9.getBitmap().recycle();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//			System.out.println("MyPosterView.type=======1=========="+MyPosterView.type);
		//			System.out.println("HomeActivity.type=======1==============="+HomeActivity.type);
		//			System.out.println("GouWuCheAGoodsAdaper.type=======1==============="+ GouWuCheAGoodsAdaper.type);
		//			if (MyPosterView.type == true) {
		//				MyPosterView.mQuery.clear();
		//				MyPosterView.type = false;
		//			}
		//			if (HomeActivity.type == true) {
		//				HomeActivity.mAq.clear();
		//				HomeActivity.type = false;
		//			}
		//
		//			if (GouWuCheAGoodsAdaper.type == true) {
		//				GouWuCheAGoodsAdaper.mAq.clear();
		//				GouWuCheAGoodsAdaper.type = false;
		//			}
		//			if (MyShopPingCarActivity.type == true) {
		//				MyShopPingCarActivity.query.clear();
		//				MyShopPingCarActivity.type = false;
		//			}
		//			System.out.println("HomeActivity.type=======2=========="+ HomeActivity.type);
		//			System.out.println("MyPosterView.type=======2==============="+ MyPosterView.type);
		//			System.out.println("GouWuCheAGoodsAdaper.type=======2==============="+ GouWuCheAGoodsAdaper.type);
	};
}