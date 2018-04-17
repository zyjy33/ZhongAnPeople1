package com.hengyushop.demo.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.ZamsHuoDong2Adapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.GoodsMyGridViewAdaper;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.DBFengXiangActivity;
import com.hengyushop.demo.home.JuTuanGouXqActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.XiangqingData;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.NewDataToast;
import com.lglottery.www.widget.NewDataToast_activity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;

/**
 * 活动详情
 *
 * @author Administrator
 *
 */
public class ZhongAnMinShenXqActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout index_item0, index_item1, index_item2;
	private ImageView ling_tip, ling_tip1;
	private TextView tv_ware_name, tv_jiaguo, tv_time, tv_zhuti,tv_activity_num,tv_activity_ent;
	public static String strUrlone, user_name, user_id, login_sign, mobile, real_name;
	private SharedPreferences spPreferences;
	private Button fanhui;
	private ArrayList<XiangqingData> lists;
	String pensions, pensions_yue;
	private DialogProgress progress;
	XiangqingData xqdata;
	public AQuery mAq;
	private WebView webview;
	public static String huodong_zf_type = "0";
	int type = 0;
	String id, buy_no;
	String qiandao_type = "0";
	public static String province, city, area, user_address, user_accept_name,
			user_mobile, shopping_address_id,record,stock_quantity;
	public static String activity_id, proFaceImg, proInverseImg,
			proDoDetailImg, proDesignImg, proName, proTip, retailPrice,
			marketPrice, proSupplementImg, goods_price, price, category_title,
			proComputerInfo, yth, key, releaseBossUid, AvailableJuHongBao,
			Atv_integral, company_id, productCount, title_ll, spec_ids,
			article_id, goods_id, subtitle, spec_text, point_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zams_xq);// activity_zams_xq
		progress = new DialogProgress(ZhongAnMinShenXqActivity.this);
		mAq = new AQuery(this);
		try {

			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			user_id = spPreferences.getString("user_id", "");
			login_sign = spPreferences.getString("login_sign", "");
			mobile = spPreferences.getString("mobile", "");
			real_name = spPreferences.getString("real_name", "");
			user_mobile = getIntent().getStringExtra("user_mobile");
			Initialize();
			getactivitynum();
			loadWeather();
			// getuseraddress();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void onDestroy() {
		super.onDestroy();
		System.out.println("ZamsHuoDong2Adapter.type=======1=========="+ZamsHuoDong2Adapter.type);
		try {

			if (ZamsHuoDong2Adapter.type == true) {
				ZamsHuoDong2Adapter.mAq.clear();
				ZamsHuoDong2Adapter.type = false;
			}
			if (MyPosterView.type == true) {
				MyPosterView.mQuery.clear();
				MyPosterView.type = false;
			}

			//			mAq.clear();
			if (lists.size() > 0) {
				lists.clear();
				mAq.clear();
			}

			System.out.println("ZamsHuoDong2Adapter.type=======1=========="+ZamsHuoDong2Adapter.type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	};


	private void Initialize() {
		try {

			tv_activity_ent = (TextView) findViewById(R.id.tv_activity_ent);
			ling_tip = (ImageView) findViewById(R.id.ling_tip);
			ling_tip1 = (ImageView) findViewById(R.id.ling_tip1);
			// ling_tip.setBackgroundResource(R.drawable.yanglaoyinhang);
			tv_ware_name = (TextView) findViewById(R.id.tv_titel);
			tv_jiaguo = (TextView) findViewById(R.id.tv_jiaguo);
			tv_time = (TextView) findViewById(R.id.tv_time);
			tv_zhuti = (TextView) findViewById(R.id.tv_zhuti);
			tv_activity_num = (TextView) findViewById(R.id.tv_activity_num);
			// tv_snyly = (TextView) findViewById(R.id.tv_snyly);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			fanhui = (Button) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);

			webview = (WebView) findViewById(R.id.webview);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.addJavascriptInterface(new JavascriptHandler(), "handler");
			webview.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
				}
			});

			ImageView img_shared = (ImageView) findViewById(R.id.img_shared);
			img_shared.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					try {
						Intent intentll = new Intent(ZhongAnMinShenXqActivity.this,DBFengXiangActivity.class);
						intentll.putExtra("activity_id", activity_id);
						intentll.putExtra("title", xqdata.title);
						intentll.putExtra("subtitle", subtitle);
						intentll.putExtra("img_url", xqdata.imgs_url);
						startActivity(intentll);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	class JavascriptHandler {
		@JavascriptInterface
		public void getContent(String htmlContent) {
		}
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 2:
					try {
						System.out.println("=========数据xqdata.img_ur============"
								+ xqdata.img_url);
						System.out.println("=========数据xqdata.title============"
								+ xqdata.title);
						mAq.id(ling_tip).image(RealmName.REALM_NAME_HTTP + xqdata.img_url);
						mAq.id(ling_tip1).image(RealmName.REALM_NAME_HTTP + xqdata.img_url);
						tv_ware_name.setText(xqdata.title);
						System.out
								.println("=========数据xqdata.sell_price============"
										+ xqdata.sell_price);
						if (xqdata.sell_price.equals("0")) {
							type = 1;
							tv_jiaguo.setText("免费");
						} else if (xqdata.sell_price.equals("0.0")) {
							type = 1;
							tv_jiaguo.setText("免费");
						} else {
							type = 2;
							tv_jiaguo.setText("￥" + xqdata.sell_price);
						}
						tv_zhuti.setText(xqdata.address);
						System.out
								.println("webview--------------------------------"
										+ RealmName.REALM_NAME_HTTP
										+ "/mobile/goods/conent-" + article_id
										+ ".html");
						// webview.loadUrl("http://mobile.zams.cn/goods/conent-"+article_id+".html");
						webview.loadUrl(RealmName.REALM_NAME_HTTP
								+ "/mobile/goods/conent-" + article_id + ".html");// 商品介绍
						// if (xqdata.start_time != null) {
						tv_time.setText(xqdata.start_time.subSequence(0, 16) + "--" + xqdata.end_time.subSequence(0, 16));
						// }else {
						//
						// }

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;

				default:
					break;
			}
		};
	};

	/**
	 * 取得活动已报名人数
	 */
	private void getactivitynum() {
		String id = getIntent().getStringExtra("id");
		System.out.println("=========1============" + id);// 5897
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_order_signup_count?article_id="
				+ id + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("=========报名人数解析数据============" + arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					record = object.getString("data");
					//					tv_activity_num.setText("已报名"+record+"名/剩余可报名人数"+stock_quantity+"人");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, null);
	}

	/**
	 * 获取商品详情
	 */
	private void loadWeather() {
		String id = getIntent().getStringExtra("id");
		System.out.println("=========2============" + id);// 5897
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_id_content?id="+ id + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("=========解析数据============" + arg1);
				formatWeather(arg1);
			}
		}, null);
	}

	public String datetime;
	java.util.Date now_1;
	java.util.Date date_1;

	private void formatWeather(String result) {
		lists = new ArrayList<XiangqingData>();
		try {
			System.out.println("=======详情数据==" + result);
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			String info = object.getString("info");
			datetime = object.getString("datetime");
			if (status.equals("y")) {
				JSONObject jobt = object.getJSONObject("data");
				xqdata = new XiangqingData();
				xqdata.title = jobt.getString("title");
				xqdata.setSubtitle(jobt.getString("subtitle"));
				xqdata.setId(jobt.getString("id"));
				xqdata.img_url = jobt.getString("img_url");
				xqdata.imgs_url = jobt.getString("imgs_url");
				xqdata.start_time = jobt.getString("start_time");
				xqdata.end_time = jobt.getString("end_time");
				xqdata.address = jobt.getString("address");
				// company_id = jobt.getString("company_id");
				// proName = xqdata.getTitle();
				subtitle = xqdata.getSubtitle();
				activity_id = xqdata.getId();
				JSONObject jobte = jobt.getJSONObject("default_spec_item");
				stock_quantity = jobte.getString("stock_quantity");
				JSONArray jsonay = jobt.getJSONArray("spec_item");
				for (int i = 0; i < jsonay.length(); i++) {
					JSONObject objt = jsonay.getJSONObject(i);
					// xqdata.setSub_title(job.getString("sub_title"));
					xqdata.setSpec_text(objt.getString("spec_text"));
					xqdata.setSell_price(objt.getString("sell_price"));
					xqdata.setMarket_price(objt.getString("market_price"));
					xqdata.setCost_price(objt.getString("cost_price"));
					xqdata.setRebate_price(objt.getString("rebate_price"));
					xqdata.setSpec_ids(objt.getString("spec_ids"));
					xqdata.setGoods_id(objt.getString("goods_id"));
					xqdata.setArticle_id(objt.getString("article_id"));
					xqdata.goods_id = objt.getString("goods_id");
					xqdata.cashing_packet = objt.getString("cashing_packet");
					xqdata.give_pension = objt.getString("give_pension");
					// spec_ids = xqdata.getSpec_ids();
					// proTip = xqdata.getSub_title();
					retailPrice = xqdata.getSell_price();
					// marketPrice = xqdata.getMarket_price();
					// AvailableJuHongBao = xqdata.getCost_price();
					// Atv_integral = xqdata.getRebate_price();
					// goods_id = xqdata.getGoods_id();
					article_id = xqdata.getArticle_id();
					// spec_text =xqdata.getSpec_text();
					System.out.println("=========数据article_id============"+article_id);
				}
				try {

					JSONArray jsonArray1 = jobt.getJSONArray("category");
					for (int i = 0; i < jsonArray1.length(); i++) {
						JSONObject obj = jsonArray1.getJSONObject(i);
						category_title = obj.getString("category_title");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				tv_activity_num.setText("已报名"+record+"名/剩余可报名人数"+stock_quantity+"人");
				try {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						now_1 = df.parse(xqdata.end_time);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						date_1 = df.parse(datetime);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					long end_time = now_1.getTime();
					long time = date_1.getTime();
					System.out.println("end_time-------------" + end_time);
					System.out.println("time-------------" + time);
					String datatype_id = getIntent().getStringExtra("datatype_id");
					System.out.println("datatype_id-------------------------------"+datatype_id);
					if (end_time > time) {
						System.out.println("1-------立即参与------");
						index_item2.setBackgroundColor(getResources().getColor(R.color.hongse));
						tv_activity_ent.setTextColor(this.getResources().getColor(R.color.white));
						if (getIntent().getStringExtra("datatype_id").equals("8")) {
							tv_activity_ent.setText("我要签到");
						}else if (getIntent().getStringExtra("datatype_id").equals("6")) {
							tv_activity_ent.setText("我要报名");
						}else if (getIntent().getStringExtra("datatype_id").equals("5")) {
							tv_activity_ent.setText("我要报名");
						}else if (getIntent().getStringExtra("datatype_id").equals("4")) {
							tv_activity_ent.setText("我要投票");
						}
					} else {
						System.out.println("2-----已结束--------");
						index_item2.setBackgroundColor(getResources().getColor(R.color.heihuise));
						tv_activity_ent.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
						tv_activity_ent.setText("已结束");
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				lists.add(xqdata);
				handler.sendEmptyMessage(2);
				progress.CloseProgress();
				//				xqdata = null;
			} else {
				progress.CloseProgress();
				Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String sur_api = "";
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.fanhui:
				finish();
				break;
			case R.id.index_item0:
				qiandao_type = "1";
				try {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						now_1 = df.parse(xqdata.end_time);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						date_1 = df.parse(datetime);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					long end_time = now_1.getTime();
					long time = date_1.getTime();
					System.out.println("end_time-------------" + end_time);
					System.out.println("time-------------" + time);
					if (end_time > time) {
						System.out.println("1-------立即参与------");
						//					sur_api = "check_order_signin";
						getjianche_activity_1();
					} else {
						System.out.println("2-----已结束--------");
						Toast.makeText(ZhongAnMinShenXqActivity.this, "活动已经结束了",200).show();
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case R.id.index_item1:
				AsyncHttp.get(RealmName.REALM_NAME_LL
								+ "/user_favorite?article_id=" + xqdata.article_id
								+ "&goods_id=" + goods_id + "&user_name=" + user_name + ""
								+ "&user_id=" + user_id + "&tags=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									System.out.println("收藏================" + arg1);
									// progress.CloseProgress();
									String info = jsonObject.getString("info");
									Toast.makeText(getApplicationContext(), info,
											200).show();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}, getApplicationContext());
				break;
			case R.id.index_item2:
				try {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						now_1 = df.parse(xqdata.end_time);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						date_1 = df.parse(datetime);
					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					long end_time = now_1.getTime();
					long time = date_1.getTime();
					System.out.println("end_time-------------" + end_time);
					System.out.println("time-------------" + time);
					if (end_time > time) {
						System.out.println("1-------立即参与------");
						if (getIntent().getStringExtra("datatype_id").equals("8")) {
							getjianche_activity();
						}else {
							//					sur_api = "check_order_signup";
							Intent intent = new Intent(ZhongAnMinShenXqActivity.this,BaoMinTiShiActivity.class);
							intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
							intent.putExtra("sell_price",xqdata.sell_price);
							intent.putExtra("article_id",xqdata.article_id);
							intent.putExtra("goods_id",xqdata.goods_id);
							intent.putExtra("title", xqdata.title);
							intent.putExtra("img_url",xqdata.img_url);
							intent.putExtra("start_time",xqdata.start_time);
							intent.putExtra("end_time", xqdata.end_time);
							intent.putExtra("address", xqdata.address);
							intent.putExtra("id", xqdata.id);
							startActivity(intent);
						}
					} else {
						System.out.println("2-----已结束--------");
						//					Toast.makeText(ZhongAnMinShenXqActivity.this, "活动已经结束了",200).show();
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case R.id.enter_shop:
				// Intent intent4 = new
				// Intent(ZhongAnMinShenXqActivity.this,Webview1.class);
				// intent4.putExtra("ylyh_id", "6239");
				// startActivity(intent4);
				break;

			default:
				break;
		}

	}


	/**
	 * 检测报名活动是否签到
	 * @param sur_api
	 */
	private void getjianche_activity_1() {
		// TODO Auto-generated method stub
		// progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/check_order_signin?mobile="
						+ mobile + "&article_id=" + xqdata.article_id + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							System.out.println("检测是否签到================"+ arg1);
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								//已报名未签到或已签到，查看电子票
								progress.CloseProgress();
								//								Toast.makeText(ZhongAnMinShenXqActivity.this,info, 200).show();
								JSONObject obj = jsonObject.getJSONObject("data");
								String trade_no = obj.getString("trade_no");
								Intent intent = new Intent(ZhongAnMinShenXqActivity.this,TishiBaoMinOkActivity.class);
								intent.putExtra("bm_tishi", "1");
								intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
								intent.putExtra("trade_no", trade_no);
								intent.putExtra("title", xqdata.title);
								intent.putExtra("start_time",xqdata.start_time);
								intent.putExtra("end_time", xqdata.end_time);
								intent.putExtra("address", xqdata.address);
								intent.putExtra("id", xqdata.id);
								intent.putExtra("real_name",real_name);
								intent.putExtra("mobile",mobile);
								startActivity(intent);
							} else {
								progress.CloseProgress();
								//未报名
								Toast.makeText(ZhongAnMinShenXqActivity.this,info, 200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						progress.CloseProgress();
						System.out.println("==========================" + arg1);
						Toast.makeText(ZhongAnMinShenXqActivity.this, "异常", 200).show();
						super.onFailure(arg0, arg1);
					}

				}, ZhongAnMinShenXqActivity.this);
	}



	/**
	 * 检测签到活动是否报名
	 * @param sur_api
	 */
	private void getjianche_activity() {
		// TODO Auto-generated method stub
		// progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/check_order_signup?mobile="
						+ mobile + "&article_id=" + xqdata.article_id + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							System.out.println("检测签到活动是否报名================"+ arg1);
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								//未签到未报名
								progress.CloseProgress();
								//								Toast.makeText(ZhongAnMinShenXqActivity.this,info, 200).show();
								getguowuqingdan();
								//									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,BaoMinTiShiActivity.class);
								//									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
								//									intent.putExtra("sell_price",xqdata.sell_price);
								//									intent.putExtra("article_id",xqdata.article_id);
								//									intent.putExtra("goods_id",xqdata.goods_id);
								//									intent.putExtra("title", xqdata.title);
								//									intent.putExtra("img_url",xqdata.img_url);
								//									intent.putExtra("start_time",xqdata.start_time);
								//									intent.putExtra("end_time", xqdata.end_time);
								//									intent.putExtra("address", xqdata.address);
								//									intent.putExtra("id", xqdata.id);
								//									startActivity(intent);
							} else {
								progress.CloseProgress();
								//已签到
								String code = jsonObject.getString("code");
								if (code.equals("sinup")) {
									Toast.makeText(ZhongAnMinShenXqActivity.this,"您已签到", 200).show();
								}else {
									Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
									//									JSONObject obj = jsonObject.getJSONObject("data");
									//									String trade_no = obj.getString("trade_no");
									//									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,TishiBaoMinOkActivity.class);
									//									intent.putExtra("bm_tishi", "1");
									//									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
									//									intent.putExtra("trade_no", trade_no);
									//									intent.putExtra("title", xqdata.title);
									//									intent.putExtra("start_time",xqdata.start_time);
									//									intent.putExtra("end_time", xqdata.end_time);
									//									intent.putExtra("address", xqdata.address);
									//									intent.putExtra("id", xqdata.id);
									//									intent.putExtra("real_name",real_name);
									//									intent.putExtra("mobile",mobile);
									//									startActivity(intent);
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						progress.CloseProgress();
						System.out.println("==========================访问接口失败！");
						System.out.println("==========================" + arg1);
						Toast.makeText(ZhongAnMinShenXqActivity.this, "异常", 200).show();
						super.onFailure(arg0, arg1);
					}

				}, ZhongAnMinShenXqActivity.this);
	}



	/**
	 * 购物清单
	 *
	 * @param payment_id
	 * @param kou_hongbao
	 */
	private void getguowuqingdan() {
		// TODO Auto-generated method stub
		try {
			progress.CreateProgress();
			System.out.println("real_name=========================="+ real_name);
			if (real_name.equals("")) {
				real_name = "空";
			}
			login_sign = spPreferences.getString("login_sign", "");
			System.out.println("login_sign=====================" + login_sign);
			AsyncHttp.get(RealmName.REALM_NAME_LL + "/add_signup_buy?user_id="
							+ user_id + "&user_name=" + user_name + "&user_sign="
							+ login_sign + "&signup_mobile=" + mobile + "&signup_name="
							+ real_name + "&article_id=" + xqdata.article_id + ""
							+ "&goods_id=" + xqdata.goods_id + "&quantity=" + 1 + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								System.out.println("购物清单================"+ arg1);
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									try {
										progress.CloseProgress();
										JSONObject obj = jsonObject.getJSONObject("data");
										buy_no = obj.getString("buy_no");
										String count = obj.getString("count");
										// Toast.makeText(ZhongAnMinShenXqActivity.this,info, 200).show();
										loadusertijiao(buy_no);

									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								} else {
									progress.CloseProgress();
									//									String code = jsonObject.getString("code");
									//									if (code.equals("Exist")) {
									//										JSONObject obj = jsonObject.getJSONObject("data");
									//										String trade_no = obj.getString("trade_no");
									//										Intent intent = new Intent(ZhongAnMinShenXqActivity.this,TishiBaoMinOkActivity.class);
									//										intent.putExtra("bm_tishi", "1");
									//										intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
									//										intent.putExtra("trade_no", trade_no);
									//										intent.putExtra("title", xqdata.title);
									//										intent.putExtra("start_time",xqdata.start_time);
									//										intent.putExtra("end_time", xqdata.end_time);
									//										intent.putExtra("address", xqdata.address);
									//										intent.putExtra("id", xqdata.id);
									//										  intent.putExtra("real_name",real_name);
									//										  intent.putExtra("mobile",mobile);
									//										startActivity(intent);
									//									}else {
									Toast.makeText(ZhongAnMinShenXqActivity.this,info, 200).show();
									//									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							progress.CloseProgress();
							System.out.println("=========================="+ arg1);
							Toast.makeText(ZhongAnMinShenXqActivity.this, "异常",200).show();
							super.onFailure(arg0, arg1);
						}

					}, ZhongAnMinShenXqActivity.this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 提交用户报名订单
	 *
	 * @param payment_id
	 * @param kou_hongbao
	 */
	private void loadusertijiao(String buy_no) {
		try {
			// progress.CreateProgress();
			System.out.println("buy_no=====================" + buy_no);
			login_sign = spPreferences.getString("login_sign", "");
			System.out.println("login_sign=====================" + login_sign);
			String url = RealmName.REALM_NAME_LL + "/add_order_signup_2017?user_id="
					+ user_id + "&user_name=" + user_name + "&user_sign="
					+ login_sign + "&buy_no=" + buy_no
					+ "&payment_id=1&is_invoice=0&invoice_title=0&remark=";
			AsyncHttp.get(url, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					super.onSuccess(arg0, arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						System.out.println("提交用户订单 ================================="+ arg1);
						try {

							String status = object.getString("status");
							String info = object.getString("info");
							//							retailPrice ="0.00";
							if (status.equals("y")) {
								try {
									progress.CloseProgress();
									JSONObject jsonObject = object.getJSONObject("data");
									String trade_no = jsonObject.getString("trade_no");
									// String total_amount = jsonObject.getString("total_amount");
									// order_no = jsonObject.getString("order_no");

									System.out.println("=========retailPrice==========="+ retailPrice);
									//								if (retailPrice.equals("0") || retailPrice.equals("0.0") || retailPrice.equals("0.00")) {
									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,BaoMinOKActivity.class);
									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
									intent.putExtra("trade_no", trade_no);
									intent.putExtra("total_c", retailPrice);
									intent.putExtra("img_url", xqdata.img_url);
									intent.putExtra("hd_title", xqdata.title);
									intent.putExtra("start_time",xqdata.start_time);
									intent.putExtra("end_time", xqdata.end_time);
									intent.putExtra("address", xqdata.address);
									intent.putExtra("id", xqdata.id);
									intent.putExtra("real_name",real_name);
									intent.putExtra("mobile",mobile);
									startActivity(intent);
									//								} else{
									//									huodong_zf_type = "1";// 活动支付成功不显示详情
									//									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,MyOrderZFActivity.class);
									//									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
									//									intent.putExtra("order_no", trade_no);
									//									intent.putExtra("total_c", retailPrice);
									//									intent.putExtra("img_url", xqdata.img_url);
									//									intent.putExtra("title", xqdata.title);
									//									intent.putExtra("start_time",xqdata.start_time);
									//									intent.putExtra("end_time", xqdata.end_time);
									//									intent.putExtra("address", xqdata.address);
									//									intent.putExtra("id", xqdata.id);
									//									intent.putExtra("real_name",real_name);
									//									  intent.putExtra("mobile",mobile);
									//									startActivity(intent);
									//								}
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							} else {
								progress.CloseProgress();
								Toast.makeText(ZhongAnMinShenXqActivity.this,info, 1000).show();
								//								String datall = object.getString("data");
								//								if (datall.equals("null")) {
								//									Toast.makeText(ZhongAnMinShenXqActivity.this,info, 1000).show();
								//									NewDataToast_activity.makeText(getApplicationContext(), info,false, 0).show();
								//								}else {
								//								JSONObject jsonObject = object.getJSONObject("data");
								//								String trade_no = jsonObject.getString("trade_no");
								//								System.out.println("=========qiandao_type==========="+ qiandao_type);
								//									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,TishiBaoMinOkActivity.class);
								//									intent.putExtra("bm_tishi", "1");
								//									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
								//									intent.putExtra("trade_no", trade_no);
								//									intent.putExtra("title", xqdata.title);
								//									intent.putExtra("start_time",xqdata.start_time);
								//									intent.putExtra("end_time", xqdata.end_time);
								//									intent.putExtra("address", xqdata.address);
								//									intent.putExtra("id", xqdata.id);
								//									intent.putExtra("real_name",real_name);
								//									intent.putExtra("mobile",mobile);
								//									startActivity(intent);
								//								}
							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					progress.CloseProgress();
					System.out.println("=========================="+ arg1);
					Toast.makeText(ZhongAnMinShenXqActivity.this, "异常",200).show();
					super.onFailure(arg0, arg1);
				}

			}, getApplicationContext());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	/**
	 * 检测是否报名
	 * @param sur_api
	 */
	//	private void getjianche_activity_2() {
	//		// TODO Auto-generated method stub
	//		// progress.CreateProgress();
	//		AsyncHttp.get(RealmName.REALM_NAME_LL + "/check_order_signup?mobile="
	//				+ mobile + "&article_id=" + xqdata.article_id + "",
	//				new AsyncHttpResponseHandler() {
	//					@Override
	//					public void onSuccess(int arg0, String arg1) {
	//						// TODO Auto-generated method stub
	//						super.onSuccess(arg0, arg1);
	//						try {
	//							JSONObject jsonObject = new JSONObject(arg1);
	//							String status = jsonObject.getString("status");
	//							System.out.println("检测是否报名================"+ arg1);
	//							String info = jsonObject.getString("info");
	//
	//							if (status.equals("y")) {
	//								//未报名
	//								 progress.CloseProgress();
	////								Toast.makeText(ZhongAnMinShenXqActivity.this,info, 200).show();
	//									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,BaoMinTiShiActivity.class);
	//									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
	//									intent.putExtra("sell_price",xqdata.sell_price);
	//									intent.putExtra("article_id",xqdata.article_id);
	//									intent.putExtra("goods_id",xqdata.goods_id);
	//									intent.putExtra("title", xqdata.title);
	//									intent.putExtra("img_url",xqdata.img_url);
	//									intent.putExtra("start_time",xqdata.start_time);
	//									intent.putExtra("end_time", xqdata.end_time);
	//									intent.putExtra("address", xqdata.address);
	//									intent.putExtra("id", xqdata.id);
	//									startActivity(intent);
	//							} else {
	//								//已报名
	//								 progress.CloseProgress();
	////								 String datall = jsonObject.getString("data");
	////									if (datall.equals("null")) {
	////										Toast.makeText(ZhongAnMinShenXqActivity.this,info, 1000).show();
	////									}else {
	////									JSONObject obj = jsonObject.getJSONObject("data");
	////									String trade_no = obj.getString("trade_no");
	////									Intent intent = new Intent(ZhongAnMinShenXqActivity.this,TishiBaoMinOkActivity.class);
	////									intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
	////									intent.putExtra("trade_no", trade_no);
	////									intent.putExtra("title", xqdata.title);
	////									intent.putExtra("start_time",xqdata.start_time);
	////									intent.putExtra("end_time", xqdata.end_time);
	////									intent.putExtra("address", xqdata.address);
	////									intent.putExtra("id", xqdata.id);
	////									intent.putExtra("real_name",real_name);
	////									intent.putExtra("mobile",mobile);
	////									startActivity(intent);
	////									}
	//							}
	//						} catch (JSONException e) {
	//							// TODO Auto-generated catch block
	//							e.printStackTrace();
	//						}
	//
	//					}
	//
	//					@Override
	//					public void onFailure(Throwable arg0, String arg1) {
	//						// TODO Auto-generated method stub
	//						progress.CloseProgress();
	//						System.out.println("==========================访问接口失败！");
	//						System.out.println("==========================" + arg1);
	//						Toast.makeText(ZhongAnMinShenXqActivity.this, "异常", 200).show();
	//						super.onFailure(arg0, arg1);
	//					}
	//
	//				}, ZhongAnMinShenXqActivity.this);
	//	}

}