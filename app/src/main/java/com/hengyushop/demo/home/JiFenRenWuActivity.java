package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.TuiGuang1Adapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.GuaYiGuaActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.adapter.TuiGuang2Adapter;
import com.lglottery.www.domain.TuiGuangBean;
import com.lglottery.www.widget.InScrollListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class JiFenRenWuActivity extends BaseActivity {
	private InScrollListView scrool, scroo2;
	private ListView listView;
	private LinearLayout item0, item1, item2, ll_zhuti, ll_zhuti2;
	private int screenWidth;
	private TuiGuang1Adapter adapter1;
	private TuiGuang2Adapter adapter;
	private String yth;
	private ArrayList<TuiGuangBean> lists;
	private ArrayList<TuiGuangBean> lists_ll;
	private WareDao wareDao;
	private TextView tv_zhuti;
	String list_id, id2, login_sign;
	private DialogProgress progress;
	public static String id;
	public static String drawn = "";
	private ArrayList<XsgyListData> list;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jifen_renwu);
		// Thread.setDefaultUncaughtExceptionHandler(this);
		progress = new DialogProgress(JiFenRenWuActivity.this);
		tv_zhuti = (TextView) findViewById(R.id.tv_zhuti);
		// item0 = (LinearLayout) findViewById(R.id.item0);
		// item1 = (LinearLayout) findViewById(R.id.item1);
		// item2 = (LinearLayout) findViewById(R.id.item2);
		ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);
		ll_zhuti2 = (LinearLayout) findViewById(R.id.ll_zhuti2);
		scrool = (InScrollListView) findViewById(R.id.scrool);
		scroo2 = (InScrollListView) findViewById(R.id.scroo2);
		listView = (ListView) findViewById(R.id.listview);

		// item0.setBackgroundResource(R.drawable.qiandao_home);//qiandao_ysj
		// tuiguang2_0
		// item1.setBackgroundResource(R.drawable.tuiguang2_1);

		// item2.setBackgroundResource(R.drawable.tuiguang2_2);
		// item0.clearAnimation();
		// item1.clearAnimation();
		// item2.clearAnimation();
		// load();

		// userloginqm();
		// loadCate();

		loadCatel();
		loadCatell();

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		ll_zhuti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(JiFenRenWuActivity.this,
						QianMingActivity.class);
				intent.putExtra("id", "29");
				intent.putExtra("tv_name", "1");
				startActivity(intent);
			}
		});
		ll_zhuti2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(JiFenRenWuActivity.this,
						QianMingActivity.class);
				intent.putExtra("id2", "2930");
				intent.putExtra("tv_name", "2");
				startActivity(intent);
			}
		});

	};

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					// TuiGuangBean content = (TuiGuangBean) msg.obj;
					String id = (String) msg.obj;
					Intent intent = new Intent(JiFenRenWuActivity.this,
							JuFaFaXunaZheActivity.class);
					intent.putExtra("exam_id", id);
					startActivity(intent);
					break;
				case 1:
					String web_id = (String) msg.obj;
					Intent intent1 = new Intent(JiFenRenWuActivity.this,
							Webview1.class);
					intent1.putExtra("web_id", web_id);
					startActivity(intent1);
					break;
				case 2:
					try {
						adapter1 = new TuiGuang1Adapter(getApplicationContext(),
								lists, imageLoader, handler);
						scrool.setAdapter(adapter1);
						TuiGuang1Adapter.aQuery.clear();
						// setListViewHeightBasedOnChildren(listView);
					} catch (Exception e) {

						e.printStackTrace();
					}
					break;
				case 3:
					adapter = new TuiGuang2Adapter(getApplicationContext(),
							lists_ll, imageLoader, handler);
					scroo2.setAdapter(adapter);
					TuiGuang2Adapter.aQuery.clear();
					break;

				default:
					break;
			}
		};
	};

	// 商品列表
	// private void loadCate(){
	// progress.CreateProgress();
	// AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_lesson_model?" +
	// "lesson_id=23", new AsyncHttpResponseHandler(){
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	//
	// super.onSuccess(arg0, arg1);
	// try {
	// System.out.println("arg1=========="+arg1);
	// JSONObject jsonObject = new JSONObject(arg1);
	// String status = jsonObject.getString("status");
	// if (status.equals("y")) {
	// JSONObject obj = jsonObject.getJSONObject("data");
	// String title = obj.getString("title");
	// list_id = obj.getString("id");
	// tv_zhuti.setText(title);
	// }else {
	//
	// }
	// loadCatel(list_id);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }, null);
	//
	// }

	/**
	 * 你来答 我来送
	 */
	private void loadCatel() {
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_test_exam_list?"
						+ "channel_name=examine&lesson_id=" + 29
						+ "&page_size=3&page_index=1&strwhere=&orderby=",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							System.out.println("（商品列表）==========" + arg1);
							lists = new ArrayList<TuiGuangBean>();
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									TuiGuangBean data = new TuiGuangBean();
									JSONObject object = jsonArray
											.getJSONObject(i);
									data.id = object.getString("id");
									data.title = object.getString("title");
									data.subtitle = object
											.getString("subtitle");
									data.img_url = object.getString("img_url");
									data.add_time = object
											.getString("add_time");
									Log.v("data1", data.add_time + "");
									lists.add(data);
								}

								handler.sendEmptyMessage(2);
							} else {
								progress.CloseProgress();
							}
							progress.CloseProgress();
							// loadCatell();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, null);

	}

	/**
	 * 推广传播
	 */
	private void loadCatell() {
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_page_size_list?"
						+ "channel_name=content&category_id=" + 2930
						+ "&page_size=3&page_index=1&strwhere=&orderby=",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							System.out.println("推广传播==========" + arg1);
							lists_ll = new ArrayList<TuiGuangBean>();
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									TuiGuangBean data = new TuiGuangBean();
									JSONObject object = jsonArray
											.getJSONObject(i);
									data.id = object.getString("id");
									// id2 = object.getString("id");
									data.title = object.getString("title");
									data.subtitle = object
											.getString("subtitle");
									data.img_url = object.getString("img_url");
									data.add_time = object
											.getString("add_time");
									Log.v("data1", data.id + "");
									lists_ll.add(data);
								}

								handler.sendEmptyMessage(3);
							} else {
								progress.CloseProgress();
							}
							progress.CloseProgress();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, null);

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
}
