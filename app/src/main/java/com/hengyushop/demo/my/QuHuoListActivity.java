package com.hengyushop.demo.my;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.MyjuTouTiaoAdapter;
import com.android.hengyu.pub.QuHuoListAdapter;
import com.android.hengyu.pub.XiaDanListAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.HaomaActivity;
import com.hengyushop.demo.my.SaoYiSaoPlaceActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

/**
 * 下单
 * 
 * @author Administrator
 * 
 */
public class QuHuoListActivity extends BaseActivity {

	private ArrayList<ShopCartData> list;
	private DialogProgress progress;
	private ListView listView;
	// XinShouGongyeLieAdapter adapter;
	ShopCartData data;
	int len;
	String yanhuo_haoma;
	private Button btn_add_shop_cart;
	private TextView tv_yhm;
	public static String user_name, user_id, mobile, login_sign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quhuo_list);
		progress = new DialogProgress(QuHuoListActivity.this);
		progress.CreateProgress();
		SharedPreferences spPreferences = getSharedPreferences("longuserset",
				MODE_PRIVATE);
		user_id = spPreferences.getString("user_id", "");
		user_name = spPreferences.getString("user", "");
		mobile = spPreferences.getString("mobile", "");
		login_sign = spPreferences.getString("login_sign", "");

		yanhuo_haoma = getIntent().getStringExtra("yanhuo_haoma");
		System.out.println("content================" + yanhuo_haoma);

		initdata();

		// String content = "6920110106345";
		// System.out.println("content================"+content);

		load_list(yanhuo_haoma);

	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	//
	//
	// }

	/**
	 * 列表数据解析
	 * 
	 * @param content
	 */
	private void load_list(String content) {
		try {
			progress.CloseProgress();
			list = new ArrayList<ShopCartData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/order_goods_accept?accept_no=" + content + "",

			new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					System.out.println("=====================二级值11" + arg1);
					try {
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						String info = jsonObject.getString("info");
						if (status.equals("y")) {
							try {
								JSONObject jsonobt = jsonObject
										.getJSONObject("data");
								data = new ShopCartData();
								// data.setId(jsonobt.getString("id"));
								// data.setTitle(jsonobt.getString("title"));
								// data.setImg_url(jsonobt.getString("img_url"));
								// data.quantity = jsonobt.getInt("quantity");
								String order_goods = jsonobt
										.getString("order_goods");
								JSONArray ja = new JSONArray(order_goods);
								for (int j = 0; j < ja.length(); j++) {
									JSONObject obct = ja.getJSONObject(j);
									data.setTitle(obct.getString("goods_title"));
									data.setImg_url(obct.getString("img_url"));
									// data.goods_id =
									// obct.getString("goods_id");
									data.article_id = obct
											.getString("article_id");
									data.market_price = obct
											.getString("market_price");
									data.sell_price = obct
											.getString("sell_price");
									data.quantity = obct.getInt("quantity");
								}
								list.add(data);
								System.out
										.println("====11=====================");
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else {
							progress.CloseProgress();
							Toast.makeText(QuHuoListActivity.this, info, 200)
									.show();
						}
						System.out.println("=====22=====================");
						handler.sendEmptyMessage(0);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, QuHuoListActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {

					System.out.println("=================5=" + list.size());
					QuHuoListAdapter adapter = new QuHuoListAdapter(list,
							QuHuoListActivity.this, imageLoader);
					listView.setAdapter(adapter);
					progress.CloseProgress();

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

	private void initdata() {
		listView = (ListView) findViewById(R.id.list_ware_collect);
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		btn_add_shop_cart = (Button) findViewById(R.id.btn_add_shop_cart);
		tv_yhm = (TextView) findViewById(R.id.tv_yhm);
		tv_yhm.setText(yanhuo_haoma);
		btn_add_shop_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				load_dingdan();
			}
		});

		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	/**
	 * 发货完成
	 * 
	 * @param jsonString2
	 * @param content
	 * @param goods_id
	 */
	private void load_dingdan() {
		try {
			progress.CloseProgress();
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/order_goods_sendout?consigner_id=" + user_id
					+ "&consigner_name=" + user_name + "&accept_no="
					+ yanhuo_haoma + "&sign=" + login_sign + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println("=====================二级值11"
									+ arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									// JSONObject jsonobt =
									// jsonObject.getJSONObject("data");
									// data = new ShopCartData();
									// data.setId(jsonobt.getString("id"));
									// data.setTitle(jsonobt.getString("title"));
									// data.setImg_url(jsonobt.getString("img_url"));
									// // data.quantity =
									// jsonobt.getInt("quantity");
									// String spec_item =
									// jsonobt.getString("spec_item");
									// JSONArray ja = new JSONArray(spec_item);
									// for (int j = 0; j < ja.length(); j++) {
									// JSONObject obct = ja.getJSONObject(j);
									// data.setMarket_price(obct.getString("market_price"));
									// data.setSell_price(obct.getString("sell_price"));
									// }
									// list.add(data);
									progress.CloseProgress();
									Toast.makeText(QuHuoListActivity.this,
											info, 200).show();
									finish();
									QuHuoHaomaActivity.handler1
											.sendEmptyMessage(0);
								} else {
									progress.CloseProgress();
									Toast.makeText(QuHuoListActivity.this,
											info, 200).show();
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, QuHuoListActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
