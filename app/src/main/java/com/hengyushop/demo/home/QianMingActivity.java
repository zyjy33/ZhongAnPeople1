package com.hengyushop.demo.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.pub.TuiGuangListAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.domain.TuiGuangBean;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QianMingActivity extends BaseActivity {

	private ArrayList<TuiGuangBean> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private PullToRefreshView refresh;
	TuiGuangListAdapter adapter;
	int len;
	String id,tv_name;
	String id2 = "1726";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinshougongye);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(QianMingActivity.this);
		progress.CreateProgress();
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		tv_name = getIntent().getStringExtra("tv_name");
		if (tv_name.equals("1")) {
			textView1.setText("你来答，我来送");
		}else {
			textView1.setText("推广传播");
		}

		initdata();

		list = new ArrayList<TuiGuangBean>();
		adapter = new TuiGuangListAdapter(getApplicationContext(), list,imageLoader, handler);
		listView.setAdapter(adapter);
		TuiGuangListAdapter.aQuery.clear();
		id = getIntent().getStringExtra("id");
		if (id != null) {
			load_list(true,id);
		}else {
			String id2 = getIntent().getStringExtra("id2");
			load_listll(true,id2);
		}

		//		listView.setOnItemClickListener(new OnItemClickListener() {
		//
		//			@Override
		//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		//					long arg3) {
		//				// TODO Auto-generated method stub
		//				try {
		//
		//				System.out.println("=================1="+list.size());
		//				Intent intent= new Intent(QianMingActivity.this,Webview1.class);
		//				intent.putExtra("list_xsgy", list.get(arg2).id);
		//				startActivity(intent);
		//
		//				} catch (Exception e) {
		//					// TODO: handle exception
		//					e.printStackTrace();
		//				}
		//			}
		//		});

	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {

				case 0:
					//				TuiGuangBean content = (TuiGuangBean) msg.obj;
					String id = (String) msg.obj;
					Intent intent = new Intent(QianMingActivity.this,JuFaFaXunaZheActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
					break;
				case 1:
					String web_id = (String) msg.obj;
					Intent intent1 = new Intent(QianMingActivity.this,Webview1.class);
					intent1.putExtra("web_id", web_id);
					startActivity(intent1);
					break;
				case 2:
					System.out.println("=================5="+list.size());
					adapter.putData(list);
					progress.CloseProgress();

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
												long arg3) {
							// TODO Auto-generated method stub
							try {
								Intent intent= new Intent(QianMingActivity.this,JuFaFaXunaZheActivity.class);
								System.out.println("===list.get(arg2).id)==============="+list.get(arg2).id);
								intent.putExtra("exam_id", list.get(arg2).id);
								startActivity(intent);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					});

					break;

				case 3:
					adapter.putData(list);
					progress.CloseProgress();

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
												long arg3) {
							// TODO Auto-generated method stub
							try {

								Intent intent= new Intent(QianMingActivity.this,Webview1.class);
								intent.putExtra("web_id", list.get(arg2).id);
								startActivity(intent);

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					});

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
				// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						if (id != null) {
							load_list(true,id);
						}else {
							load_listll(true,id2);
						}
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	/**
	 * 第1个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(boolean flag, String id) {
		RUN_METHOD = 1;
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			list = new ArrayList<TuiGuangBean>();
		}
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_exam_list?" +
				"channel_name=examine&lesson_id="+id+"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=", new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("=====================二级值1"+arg1);
				try {
					System.out.println("（商品列表）=========="+arg1);
					list = new ArrayList<TuiGuangBean>();
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						for (int i = 0; i < jsonArray.length(); i++) {
							TuiGuangBean data = new TuiGuangBean();
							JSONObject object = jsonArray.getJSONObject(i);
							data.id = object.getString("id");
							data.title = object.getString("title");
							data.subtitle = object.getString("subtitle");
							data.img_url = object.getString("img_url");
							data.add_time = object.getString("add_time");
							Log.v("data1", data.id + "");
							list.add(data);
						}
					}else {
					}
					handler.sendEmptyMessage(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);
	}

	/**
	 * 第2个列表数据解析
	 */
	private void load_listll(boolean flag, String id) {
		RUN_METHOD = 1;
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			list = new ArrayList<TuiGuangBean>();
		}
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?" +
						"channel_name=content&category_id="+id+"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
				new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1"+arg1);
						try {
							System.out.println("（商品列表）=========="+arg1);
							list = new ArrayList<TuiGuangBean>();
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									TuiGuangBean data = new TuiGuangBean();
									JSONObject object = jsonArray.getJSONObject(i);
									data.id = object.getString("id");
									data.title = object.getString("title");
									data.subtitle = object.getString("subtitle");
									data.img_url = object.getString("img_url");
									data.add_time = object.getString("add_time");
									Log.v("data1", data.id + "");
									list.add(data);
								}
							}else {
							}
							handler.sendEmptyMessage(3);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, null);
	}

}
