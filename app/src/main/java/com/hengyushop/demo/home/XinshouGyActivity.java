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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.MyjuTouTiaoAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
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

public class XinshouGyActivity extends BaseActivity {

	private ArrayList<XsgyListData> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private PullToRefreshView refresh;
	XinShouGongyeLieAdapter adapter;
	int len;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinshougongye);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(XinshouGyActivity.this);
		progress.CreateProgress();
		initdata();

		list = new ArrayList<XsgyListData>();
		adapter = new XinShouGongyeLieAdapter(list,XinshouGyActivity.this, imageLoader);
		listView.setAdapter(adapter);

		load_list(true);
		//		load_P();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				try {

					System.out.println("=================1="+list.size());
					Intent intent= new Intent(XinshouGyActivity.this,Webview1.class);
					intent.putExtra("list_xsgy", list.get(arg2).id);
					startActivity(intent);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

	}
	/**
	 * 商品详情
	 * @param st
	 */
	private void load_P() {
		//		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_top_list?channel_name=goods&top=10&strwhere=status=0%20and%20is_top=1"
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("arg1================================="+arg1);
					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						System.out.println("11================================="+arg0);
						System.out.println("22================================="+arg1);
						Toast.makeText(XinshouGyActivity.this, "更新订单网络超时异常", 200).show();
					}
				}, XinshouGyActivity.this);

	}
	/**
	 * 列表数据解析
	 * @param content
	 */
	private void load_list(String content){
		try {
			progress.CloseProgress();
			//		list = new ArrayList<ShopCartData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_goods_content?goods_no="+content+"",

					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println("=====================二级值11" + arg1);
							//						try {
							//							JSONObject jsonObject = new JSONObject(arg1);
							//							String status = jsonObject.getString("status");
							//							String info = jsonObject.getString("info");
							//							if (status.equals("y")) {
							//								JSONObject jsonobt = jsonObject.getJSONObject("data");
							//								data = new ShopCartData();
							//								data.setId(jsonobt.getString("id"));
							//								data.title = jsonobt.getString("title");
							//								data.img_url = jsonobt.getString("img_url");
							//								// data.quantity = jsonobt.getInt("quantity");
							//								String groupon_item = jsonobt.getString("spec_item");
							//								JSONArray ja = new JSONArray(groupon_item);
							//								for (int j = 0; j < ja.length(); j++) {
							//									JSONObject obct = ja.getJSONObject(j);
							//									data.setId(obct.getString("id"));
							//									data.title = obct.getString("title");
							//									data.market_price = obct.getString("market_price");
							//									data.sell_price = obct.getString("sell_price");
							//									// data.quantity = obct.getInt("quantity");
							//									// System.out.println("=====22====================="+zhou2);
							//									list.add(data);
							//								}
							//							} else {
							//								progress.CloseProgress();
							//								Toast.makeText(SaoYiSaoPlaceActivity.this,info, 200).show();
							//							}
							//
							//							Message msg = new Message();
							//							msg.what = 0;
							//							msg.obj = list;
							//							handler.sendMessage(msg);
							//
							//						} catch (JSONException e) {
							//							// TODO Auto-generated catch block
							//							e.printStackTrace();
							//						}
						}
					}, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					System.out.println("=================5="+list.size());
					list = (ArrayList<XsgyListData>) msg.obj;
					adapter.putData(list);
					XinShouGongyeLieAdapter.aQuery.clear();
					//				XinShouGongyeLieAdapter adapter = new XinShouGongyeLieAdapter(list,XinshouGyActivity.this, imageLoader);
					//				listView.setAdapter(adapter);
					progress.CloseProgress();
					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
												long arg3) {
							// TODO Auto-generated method stub
							try {

								System.out.println("=================1="+list.size());
								Intent intent= new Intent(XinshouGyActivity.this,Webview1.class);
								intent.putExtra("list_xsgy", list.get(arg2).id);
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
						//					if(RUN_METHOD==0){
						//						System.out.println("RUN_METHOD========="+RUN_METHOD);
						//						load_list2(true);
						//					}else {
						load_list(false);
						//					}
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
	private void load_list(boolean flag) {
		RUN_METHOD = 1;
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			list = new ArrayList<XsgyListData>();
		}
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?channel_name=content&category_id=52" +
						"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
				new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1"+arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								len = jsonArray.length();
								for(int i=0;i<jsonArray.length();i++){
									JSONObject object = jsonArray.getJSONObject(i);
									XsgyListData spList = new XsgyListData();
									spList.id = object.getString("id");
									spList.title = object.getString("title");
									spList.img_url = object.getString("img_url");
									spList.add_time = object.getString("add_time");
									list.add(spList);
									int user_id = spList.user_id ;//
									System.out.println("二级值2====================="+user_id);
								}
							}else {
								progress.CloseProgress();
								Toast.makeText(XinshouGyActivity.this, info, 200).show();
							}

							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);

							if(len!=0){
								CURRENT_NUM =CURRENT_NUM+1;
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}
	/**
	 * 第2个列表数据解析
	 */
	private void load_list2(boolean flag) {
		list = new ArrayList<XsgyListData>();
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 0;
			list = new ArrayList<XsgyListData>();
		}
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?channel_name=content&category_id=52" +
						"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
				new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1"+arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								int len = jsonArray.length();
								for(int i=0;i<jsonArray.length();i++){
									JSONObject object = jsonArray.getJSONObject(i);
									XsgyListData spList = new XsgyListData();
									spList.id = object.getString("id");
									spList.title = object.getString("title");
									spList.img_url = object.getString("img_url");
									list.add(spList);
								}
							}else {
								progress.CloseProgress();
								Toast.makeText(XinshouGyActivity.this, info, 200).show();
							}

							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}






}
