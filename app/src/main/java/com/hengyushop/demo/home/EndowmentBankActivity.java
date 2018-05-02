package com.hengyushop.demo.home;

import java.lang.Thread.UncaughtExceptionHandler;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.ChuangKeActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.YlyhData;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;

/**
 * 养老基金
 *
 * @author Administrator
 *
 */
public class EndowmentBankActivity extends BaseActivity implements
		OnClickListener {
	private ListView vip_list;
	private LinearLayout index_item0, index_item1, index_item2;
	private ImageView ling_tip;
	String strUrlone;
	private SharedPreferences spPreferences;
	private TextView tv_ylyhye, tv_xfylj, tv_yljye, tv_snyly;
	private Button fanhui, btn_login;
	private ArrayList<YlyhData> list = new ArrayList<YlyhData>();
	private ArrayList<UserRegisterllData> list1 = new ArrayList<UserRegisterllData>();
	private ArrayList<YlyhData> list2 = new ArrayList<YlyhData>();
	String pensions, pensions_yue;
	public static AQuery mAq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ylyx_title);// endowment_bank_activity
		mAq = new AQuery(EndowmentBankActivity.this);
		// Thread.setDefaultUncaughtExceptionHandler(this);
		Button enter_shop = (Button) findViewById(R.id.enter_shop);
		// enter_shop.getBackground().setAlpha(50);
		enter_shop.setOnClickListener(this);
		try {

			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			// Initialize();
			// loadguanggao();
			// loadyue();
			// loadxfylj();
			// loadlilv();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// public void uncaughtException(Thread arg0, Throwable arg1) {
	//
	// //在此处理异常， arg1即为捕获到的异常
	// Log.i("AAA", "uncaughtException   " + arg1);
	// }
	@Override
	protected void onResume() {

		super.onResume();
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		Initialize();
		loadguanggao();
		loadyue();
		loadxfylj();
		loadlilv();
	}

	//当Activity被销毁时会调用onDestory方法
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		BitmapDrawable bd1 = (BitmapDrawable)ling_tip.getBackground();
//		ling_tip.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
//		bd1.setCallback(null);
//		bd1.getBitmap().recycle();
	}

	private void Initialize() {
		try {

			ling_tip = (ImageView) findViewById(R.id.ling_tip);
//			ling_tip.setBackgroundResource(R.drawable.yanglaoyinhang);
//			Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ditu);
//			BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
//			ling_tip.setBackgroundDrawable(bd);
			tv_ylyhye = (TextView) findViewById(R.id.tv_ylyhye);
			tv_xfylj = (TextView) findViewById(R.id.tv_xfylj);
			tv_snyly = (TextView) findViewById(R.id.tv_snyly);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			fanhui = (Button) findViewById(R.id.fanhui);
			btn_login = (Button) findViewById(R.id.btn_login);

			// btn_login.setOnClickListener(this);
			fanhui.setOnClickListener(this);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 养老基金余额
	 */
	private void loadyue() {
		String user_name = spPreferences.getString("user", "");
		if (user_name != null) {
			strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="
					+ user_name + "";

			System.out.println("======养老基金余额=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======养老基金余额=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.user_name = obj.getString("user_name");
							data.user_code = obj.getString("user_code");
							data.agency_id = obj.getInt("agency_id");
							data.amount = obj.getString("amount");
							data.pension = obj.getString("pension");
							data.packet = obj.getString("packet");
							data.point = obj.getString("point");
							data.group_id = obj.getString("group_id");
							pensions = data.pension;
							tv_ylyhye.setText(data.pension + "元");// 养老基金余额
							list1.add(data);
						} else {

						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, null);
		} else {

		}
	}

	/**
	 * 本月消费养老金
	 */
	private void loadxfylj() {
		String user_name = spPreferences.getString("user", "");
		String to_user_id = spPreferences.getString("user_id", "");
		if (to_user_id != null) {
			strUrlone = RealmName.REALM_NAME_LL
					+ "/get_payrecord_income_sum?to_user_id=" + to_user_id + ""
					+ "&fund_id=4&day=0";

			System.out.println("======本月消费养老金=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======本月消费养老金=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							YlyhData data = new YlyhData();
							data.pensions = obj.getString("pensions");
							pensions_yue = data.pensions;
							tv_xfylj.setText(data.pensions + "元");// 本月消费养老金
							list.add(data);
						} else {

						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, null);
		} else {

		}
	}

	/**
	 * 养老基金收益率
	 */
	private void loadlilv() {
		String user_id = spPreferences.getString("user_id", "");
		strUrlone = RealmName.REALM_NAME_LL + "/get_payrecord_rate?user_id="
				+ user_id + "" + "&months=600";

		System.out.println("======养老基金收益率=============" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======养老基金收益率=============" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						JSONArray jaArray = obj.getJSONArray("years");
						for (int i = 0; i < jaArray.length(); i++) {
							JSONObject objc = jaArray.getJSONObject(i);
							YlyhData data = new YlyhData();
							data.add = objc.getString("add");
							data.pensions = objc.getString("pensions");

							System.out.println("======22222============="
									+ data.pensions);
							list2.add(data);
						}
						try {

							String zhou = list2.get(9).pensions;
							System.out
									.println("======zhou=============" + zhou);
							// if (!zhou.equals("0")) {
							BigDecimal pen1 = new BigDecimal(Double
									.parseDouble(zhou));
							double ye1 = pen1.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue();

							tv_snyly.setText(String.valueOf(ye1) + "元");
							// tv_snyly.setText(list2.get(9).pensions.substring(0,6)+"元");//
							// }

						} catch (Exception e) {

							e.printStackTrace();
						}
					} else {

					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			};
		}, null);
	}

	private void loadguanggao() {
		try {

			// 广告滚动
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_adbanner_list?advert_id=14",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							System.out
									.println("======输出33=============" + arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									AdvertDao1 ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									String ad_url = ada.getAd_url();
//									ImageLoader imageLoader = ImageLoader.getInstance();
//									imageLoader.displayImage(RealmName.REALM_NAME_HTTP + ad_url,ling_tip);
									mAq.id(ling_tip).image(RealmName.REALM_NAME_HTTP + ad_url);
									images.add(ada);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							super.onFailure(arg0, arg1);
							System.out.println("======输出112============="
									+ arg0);
							System.out.println("======输出113============="
									+ arg1);
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:

					break;

				default:
					break;
			}
		};
	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.index_item0:
				try {
					Intent intent = new Intent(EndowmentBankActivity.this,
							EndowmentBankMxActivity.class);
					intent.putExtra("pensions", pensions);
					intent.putExtra("pensions_yue", pensions_yue);
					System.out.println("======pensions=============" + pensions);
					intent.putExtra("add1", list2.get(9).add);
					intent.putExtra("add2", list2.get(19).add);
					intent.putExtra("add3", list2.get(29).add);
					intent.putExtra("add4", list2.get(39).add);
					intent.putExtra("add5", list2.get(49).add);
					intent.putExtra("pensions1", list2.get(9).pensions);
					intent.putExtra("pensions2", list2.get(19).pensions);
					intent.putExtra("pensions3", list2.get(29).pensions);
					intent.putExtra("pensions4", list2.get(39).pensions);
					intent.putExtra("pensions5", list2.get(49).pensions);
					startActivity(intent);
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			case R.id.index_item1:
				Intent intent1 = new Intent(EndowmentBankActivity.this,
						EndowmentBankMxActivity.class);
				intent1.putExtra("pensions", pensions);
				intent1.putExtra("pensions_yue", pensions_yue);
				intent1.putExtra("add1", list2.get(9).add);
				intent1.putExtra("add2", list2.get(19).add);
				intent1.putExtra("add3", list2.get(29).add);
				intent1.putExtra("add4", list2.get(39).add);
				intent1.putExtra("add5", list2.get(49).add);
				intent1.putExtra("pensions1", list2.get(9).pensions);
				intent1.putExtra("pensions2", list2.get(19).pensions);
				intent1.putExtra("pensions3", list2.get(29).pensions);
				intent1.putExtra("pensions4", list2.get(39).pensions);
				intent1.putExtra("pensions5", list2.get(49).pensions);
				startActivity(intent1);
				break;
			case R.id.index_item2:
				Intent intent2 = new Intent(EndowmentBankActivity.this,
						EndowmentBankMxActivity.class);
				intent2.putExtra("pensions", pensions);
				intent2.putExtra("pensions_yue", pensions_yue);
				intent2.putExtra("add1", list2.get(9).add);
				intent2.putExtra("add2", list2.get(19).add);
				intent2.putExtra("add3", list2.get(29).add);
				intent2.putExtra("add4", list2.get(39).add);
				intent2.putExtra("add5", list2.get(49).add);
				intent2.putExtra("pensions1", list2.get(9).pensions);
				intent2.putExtra("pensions2", list2.get(19).pensions);
				intent2.putExtra("pensions3", list2.get(29).pensions);
				intent2.putExtra("pensions4", list2.get(39).pensions);
				intent2.putExtra("pensions5", list2.get(49).pensions);
				startActivity(intent2);
				break;
			case R.id.fanhui:
				finish();
				break;
			case R.id.btn_login:
				Intent intent3 = new Intent(EndowmentBankActivity.this,
						YangLaoChongZhiActivity.class);
				startActivity(intent3);
				break;
			case R.id.enter_shop:
				Intent intent4 = new Intent(EndowmentBankActivity.this,
						Webview1.class);
				intent4.putExtra("ylyh_id", "6239");
				startActivity(intent4);

				break;

			default:
				break;
		}

	}
}
