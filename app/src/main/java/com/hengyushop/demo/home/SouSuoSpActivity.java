package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.SpListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class SouSuoSpActivity extends BaseActivity {

	private ArrayList<SpListData> lists;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private PullToRefreshView refresh;
	MySpListAdapter myadapter;
	int len;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinshougongye);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(SouSuoSpActivity.this);
		//		progress.CreateProgress();
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		String strwhere_zhi = getIntent().getStringExtra("strwhere_zhi");
		if (!strwhere_zhi.equals("")) {
			textView1.setText(strwhere_zhi);
		}else {
			textView1.setText("搜索商品");
		}
		initdata();

		lists = new ArrayList<SpListData>();
		myadapter = new MySpListAdapter(lists,getApplicationContext(), imageLoader);
		listView.setAdapter(myadapter);

		load_list(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {

				try {

					System.out.println("=================1="+lists.size());
					Intent intent= new Intent(SouSuoSpActivity.this,WareInformationActivity.class);
					intent.putExtra("id", lists.get(arg2).id);
					startActivity(intent);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					System.out.println("=================5="+lists.size());
					myadapter.putData(lists);
					progress.CloseProgress();

					//				listView.setOnItemClickListener(new OnItemClickListener() {
					//
					//					@Override
					//					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					//							long arg3) {
					//
					//						try {
					//
					//						System.out.println("=================1="+lists.size());
					//						Intent intent= new Intent(SouSuoSpActivity.this,Webview1.class);
					//						intent.putExtra("list_xsgy", lists.get(arg2).id);
					//						startActivity(intent);
					//
					//						} catch (Exception e) {
					//
					//							e.printStackTrace();
					//						}
					//					}
					//				});
					break;

				default:
					break;
			}
		};
	};
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
						load_list(false);
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	/**
	 * 第1个列表数据解析
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	String URL;
	private void load_list(boolean flag) {
		progress.CreateProgress();
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			lists = new ArrayList<SpListData>();
		}
		String strwhere_zhi = getIntent().getStringExtra("strwhere_zhi");
		String channel_name = getIntent().getStringExtra("channel_name");
		String home_sousuo = getIntent().getStringExtra("home_sousuo");
		System.out.println("strwhere_zhi====================="+strwhere_zhi);
		System.out.println("channel_name====================="+channel_name);

		if (home_sousuo != null) {
			URL = "/get_article_page_search_all?page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"" +
					"&keyword="+strwhere_zhi+"&orderby=id%20desc";
		}else {
			URL = "/get_article_page_search_list?channel_name="+channel_name+"&category_id=0&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"" +
					"&keyword="+strwhere_zhi+"&orderby=id%20desc";
		}

		AsyncHttp.get(RealmName.REALM_NAME_LL+ URL,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1"+arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							JSONArray jsonArray = jsonObject.getJSONArray("data");
							len = jsonArray.length();
							System.out.println("len================"+len);
							if (len != 0) {
								if (status.equals("y")) {
									for(int i=0;i<jsonArray.length();i++){
										JSONObject object = jsonArray.getJSONObject(i);
										SpListData spList = new SpListData();
										spList.id = object.getString("id");
										spList.img_url = object.getString("img_url");
										spList.title = object.getString("title");
										//										spList.market_price = object.getString("market_price");
										//										spList.sell_price = object.getString("sell_price");
										//										spList.cashing_packet = object.getString("cashing_packet");

										JSONObject jsot = object.getJSONObject("default_spec_item");
										spList.market_price = jsot.getString("market_price");
										spList.sell_price = jsot.getString("sell_price");
										spList.cashing_packet = jsot.getString("cashing_packet");
										lists.add(spList);
									}
								}else {
									progress.CloseProgress();
									Toast.makeText(SouSuoSpActivity.this, info, 200).show();
								}
								handler.sendEmptyMessage(0);
								if(len!=0){
									CURRENT_NUM =CURRENT_NUM+1;
								}
								progress.CloseProgress();
							}else {
								progress.CloseProgress();
								Toast.makeText(SouSuoSpActivity.this, "没有该商品！", 200).show();
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				}, null);
	}




}
