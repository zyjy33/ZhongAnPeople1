package com.hengyushop.demo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hengyu.pub.QianDaoAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.TishiBaoMinQianDaoActivity;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QianDaoListActivity extends BaseActivity {

	private ArrayList<XsgyListData> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private PullToRefreshView refresh;
	QianDaoAdapter adapter;
	private EditText et_user_yz;
	private Button  get_yz;
	private LinearLayout ll_chaxun;
	int len;
	public static String shoujihao;
	//15220072931
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouji_qiandao);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(QianDaoListActivity.this);
		initdata();

		//		list = new ArrayList<XsgyListData>();
		//		adapter = new QianDaoAdapter(list,QianDaoListActivity.this, imageLoader);
		//		listView.setAdapter(adapter);
		//		listView.setOnItemClickListener(new OnItemClickListener() {
		//
		//			@Override
		//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		//					long arg3) {
		//				// TODO Auto-generated method stub
		//				try {
		//
		//				System.out.println("=================1="+list.size());
		//				Intent intent= new Intent(QianDaoListActivity.this,Webview1.class);
		//				intent.putExtra("list_xsgy", list.get(arg2).id);
		//				startActivity(intent);
		//
		//				} catch (Exception e) {
		//					// TODO: handle exception
		//					e.printStackTrace();
		//				}
		//			}
		//		});
		String trade_no1 = getIntent().getStringExtra("bianma");
		System.out.println("=========trade_no1============" + trade_no1);
		if (trade_no1 != null) {
			load_list(trade_no1);
		}else {
			ll_chaxun.setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("trade_no====================="+QianDaoAdapter.trade_no);
		if (QianDaoAdapter.trade_no != null) {
			load_list(shoujihao);
			QianDaoAdapter.trade_no = null;
		}
	}

	public void onDestroy() {
		super.onDestroy();
		try {

			if (list.size() > 0) {
				list.clear();
				list = null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	};

	private void initdata() {
		//		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		//		refresh.setOnHeaderRefreshListener(listHeadListener);
		//		refresh.setOnFooterRefreshListener(listFootListener);
		ll_chaxun = (LinearLayout) findViewById(R.id.ll_chaxun);
		listView = (ListView) findViewById(R.id.new_list);
		et_user_yz = (EditText) findViewById(R.id.et_user_yz);
		get_yz = (Button) findViewById(R.id.get_yz);
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		get_yz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				shoujihao = et_user_yz.getText().toString().trim();
				if (shoujihao.equals("")) {
					Toast.makeText(QianDaoListActivity.this, "请输入手机号码", 200).show();
				}else {
					load_list(shoujihao);
				}
			}
		});
	}

	/**
	 * 获取签到信息
	 */
	XsgyListData spList;
	private void load_list(String shoujihao) {
		progress.CreateProgress();
		list = new ArrayList<XsgyListData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_order_signup?mobile="+shoujihao+"",
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
								for(int i=0;i<jsonArray.length();i++){
									JSONObject object = jsonArray.getJSONObject(i);
									spList = new XsgyListData();
									spList.company_name = object.getString("company_name");
									//										spList.article_title = object.getString("article_title");
									spList.payment_time = object.getString("payment_time");
									spList.trade_no = object.getString("trade_no");
									JSONArray json = object.getJSONArray("order_goods");
									for (int k = 0; k < json.length(); k++) {
										JSONObject objet2 = json.getJSONObject(k);
										spList.article_title = objet2.getString("article_title");
										spList.article_id = objet2.getString("article_id");
									}
									list.add(spList);
								}
								spList = null;
							}else {
								progress.CloseProgress();
								Toast.makeText(QianDaoListActivity.this, info, 2000).show();
							}
							progress.CloseProgress();
							//										adapter = new QianDaoAdapter(list,QianDaoListActivity.this, imageLoader);
							//										listView.setAdapter(adapter);

							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						Toast.makeText(QianDaoListActivity.this, "加载异常", 2000).show();
					}
				}, QianDaoListActivity.this);
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					System.out.println("=================5="+list.size());
					adapter = new QianDaoAdapter(list,QianDaoListActivity.this, handler);
					listView.setAdapter(adapter);

					//				list = (ArrayList<XsgyListData>) msg.obj;
					//				adapter.putData(list);
					//				QianDaoAdapter.aQuery.clear();
					//				XinShouGongyeLieAdapter adapter = new XinShouGongyeLieAdapter(list,XinshouGyActivity.this, imageLoader);
					//				listView.setAdapter(adapter);
					//				progress.CloseProgress();
					//				listView.setOnItemClickListener(new OnItemClickListener() {
					//
					//					@Override
					//					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					//							long arg3) {
					//						// TODO Auto-generated method stub
					//						try {
					//
					//						System.out.println("=================1="+list.size());
					//						Intent intent= new Intent(QianDaoListActivity.this,Webview1.class);
					//						intent.putExtra("list_xsgy", list.get(arg2).id);
					//						startActivity(intent);
					//
					//						} catch (Exception e) {
					//							// TODO: handle exception
					//							e.printStackTrace();
					//						}
					//					}
					//				});
					break;

				case 1:
					System.out.println("shoujihao====================="+shoujihao);
					//		    	Intent intent = new Intent(QianDaoListActivity.this,TishiBaoMinQianDaoActivity.class);
					//		    	intent.putExtra("bianma",QianDaoAdapter.trade_no);
					//		    	intent.putExtra("article_id",QianDaoAdapter.article_id);
					//		    	intent.putExtra("qiandao","1");
					////		    	intent.putExtra("sp_sys", getIntent().getStringExtra("sp_sys"));
					//		    	startActivity(intent);

					loadzhifuweix();
					//		    	load_list();
					break;

				default:
					break;
			}
		};
	};

	/**
	 * 签到查询
	 */
	String mobile;
	SharedPreferences spPreferences;
	private void loadzhifuweix() {
		try {
			progress.CreateProgress();
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			mobile = spPreferences.getString("mobile", "");
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/check_order_signin?mobile="+mobile+"&article_id="+QianDaoAdapter.article_id+"",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("签到查询================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									Toast.makeText(QianDaoListActivity.this, info, 200).show();
								}else {
									progress.CloseProgress();
									Intent intent = new Intent(QianDaoListActivity.this,TishiBaoMinQianDaoActivity.class);
									intent.putExtra("bianma",QianDaoAdapter.trade_no);
									intent.putExtra("article_id",QianDaoAdapter.article_id);
									intent.putExtra("qiandao","1");
									startActivity(intent);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1);
							progress.CloseProgress();
							System.out.println("异常================================="+arg1);
							Toast.makeText(QianDaoListActivity.this, "异常", 200).show();
						}
					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	/**
	 * 报名签到
	 */
	private void load_list() {
		SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		String user_name = spPreferences.getString("user", "");
		String user_id = spPreferences.getString("user_id", "");
		String login_sign = spPreferences.getString("login_sign", "");
		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/signup_award?sales_id="
						+ user_id + "&sales_name=" + user_name + "" + "&trade_no="
						+ QianDaoAdapter.trade_no + "&sign=" + login_sign + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=========数据接口============" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								//								JSONObject obj = object.getJSONObject("data");
								progress.CloseProgress();
								Intent intent = new Intent(QianDaoListActivity.this,TishiBaoMinQianDaoActivity.class);
								intent.putExtra("bianma",QianDaoAdapter.trade_no);
								intent.putExtra("article_id",QianDaoAdapter.article_id);
								intent.putExtra("qiandao","1");
								startActivity(intent);
							} else {
								progress.CloseProgress();
								Toast.makeText(QianDaoListActivity.this, info, 200).show();
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						progress.CloseProgress();
						System.out.println("==========================访问接口失败！");
						System.out.println("==========================" + arg1);
						Toast.makeText(QianDaoListActivity.this, "异常", 200).show();
						super.onFailure(arg0, arg1);
					}

				}, QianDaoListActivity.this);
	}


}
