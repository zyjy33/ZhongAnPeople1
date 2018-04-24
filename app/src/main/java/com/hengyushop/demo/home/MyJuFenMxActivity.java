package com.hengyushop.demo.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.MyJuFenData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyJuFenMxActivity extends BaseActivity {

	private ArrayList<MyJuFenData> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private PullToRefreshView refresh;
	private SharedPreferences spPreferences;
	String user_name, user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_jufen_mx);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(MyJuFenMxActivity.this);
		progress.CreateProgress();
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		initdata();
		load_list(true);

	}

	private void initdata() {
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		listView = (ListView) findViewById(R.id.new_list);

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	}

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {

			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};

	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {

			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						if (RUN_METHOD == 0) {
							System.out.println("RUN_METHOD========="
									+ RUN_METHOD);
							load_list2(true);
						} else {
							load_list(false);
						}
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					list = (ArrayList<MyJuFenData>) msg.obj;
					// MyJuFenMxAdapter adapter = new
					// MyJuFenMxAdapter(list,MyJuFenMxActivity.this, imageLoader);
					// listView.setAdapter(adapter);
					progress.CloseProgress();
					break;

				default:
					break;
			}
		};
	};
	/**
	 * 第1个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag) {
		RUN_METHOD = 1;
		list = new ArrayList<MyJuFenData>();
		if (flag) {
			// 计数和容器清零
			CURRENT_NUM = 0;
			list = new ArrayList<MyJuFenData>();
		}
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_user_child_list?user_id="
						+ user_id + "&user_name=" + user_name + "" + "&page_size="
						+ VIEW_NUM + "&page_index=" + CURRENT_NUM + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1" + arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("data");
							int len = jsonArray.length();
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								MyJuFenData data = new MyJuFenData();
								// data.sales_name =
								// object.getString("sales_name");
								data.login_sign = object
										.getString("login_sign");
								String user_id = data.login_sign;//
								System.out.println("二级值2====================="
										+ user_id);
								list.add(data);
							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);
							if (len != 0) {
								CURRENT_NUM = CURRENT_NUM + VIEW_NUM;
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				}, null);
	}

	/**
	 * 第2个列表数据解析
	 */
	private void load_list2(boolean flag) {
		list = new ArrayList<MyJuFenData>();
		if (flag) {
			// 计数和容器清零
			CURRENT_NUM = 0;
			list = new ArrayList<MyJuFenData>();
		}
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
								+ "/get_article_page_size_list?channel_name=content&category_id=52"
								+ "&page_size=" + VIEW_NUM + "&page_index="
								+ CURRENT_NUM + "&strwhere=&orderby=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								System.out.println("=====================二级值1"
										+ arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");
									int len = jsonArray.length();
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject object = jsonArray
												.getJSONObject(i);
										MyJuFenData data = new MyJuFenData();
										// data.sales_name =
										// object.getString("sales_name");
										data.login_sign = object
												.getString("login_sign");
										list.add(data);
									}
									Message msg = new Message();
									msg.what = 0;
									msg.obj = list;
									handler.sendMessage(msg);
								} catch (JSONException e) {

									e.printStackTrace();
								}
							}
						}, null);
	}

}
