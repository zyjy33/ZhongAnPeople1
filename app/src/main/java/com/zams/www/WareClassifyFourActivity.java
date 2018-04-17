package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.hengyu.pub.MyWareFourAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.WareInformationData;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WareClassifyFourActivity extends BaseActivity implements
		OnClickListener {

	private TabHost tabHost;
	private ListView list_money, list_sales_volume, list_time;
	private TextView tv_titilename;
	private int id;
	private Map<String, String> strSalesVolume ,strUrlPrice,strUrlTime;
	private ArrayList<WareInformationData> list;
	private ArrayList<WareInformationData> list2;
	private ArrayList<WareInformationData> list3;
	private MyWareFourAdapter adapter;
	private DialogProgress progress;
	private String titlename;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 10:
					list = (ArrayList<WareInformationData>) msg.obj;
					adapter = new MyWareFourAdapter(list,
							WareClassifyFourActivity.this, imageLoader);
					list_money.setAdapter(adapter);
					progress.CloseProgress();
					break;
				case 20:
					list2 = (ArrayList<WareInformationData>) msg.obj;
					adapter = new MyWareFourAdapter(list2,
							WareClassifyFourActivity.this, imageLoader);
					list_sales_volume.setAdapter(adapter);
					break;
				case 30:
					list3 = (ArrayList<WareInformationData>) msg.obj;
					adapter = new MyWareFourAdapter(list3,
							WareClassifyFourActivity.this, imageLoader);
					list_time.setAdapter(adapter);
					break;

				default:
					break;
			}
		};
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_ware_classify_four);

		popupWindowMenu = new MyPopupWindowMenu(this);
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());
		progress = new DialogProgress(WareClassifyFourActivity.this);

		tv_titilename = (TextView) findViewById(R.id.tv_wareclassify_name);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		titlename = bundle.getString("name");
		tv_titilename.setText(titlename);
		System.out.println("four:" + id);
		if(getIntent().hasExtra("city")){
			String city = getIntent().getStringExtra("city");
			strSalesVolume = new HashMap<String, String>();
			strSalesVolume.put("yth", "test");
			strSalesVolume.put("key", "key");
			strSalesVolume.put("productTypeId3", id+"");
			strSalesVolume.put("startIndex", "0");
			strSalesVolume.put("count", "13");
			strSalesVolume.put("orderWhat", "TotalXiaoLiang");
			strSalesVolume.put("cityCode", city);
			strSalesVolume.put("act", "ProductItemsInfo");
			strSalesVolume.put("productTypeId2", "");
			//
			strUrlPrice = new HashMap<String, String>();
			strUrlPrice.put("yth", "test");
			strUrlPrice.put("key", "key");
			strUrlPrice.put("productTypeId3", id+"");
			strUrlPrice.put("startIndex", "0");
			strUrlPrice.put("count", "13");
			strUrlPrice.put("orderWhat", "retailPrice");
			strUrlPrice.put("act", "ProductItemsInfo");
			strUrlPrice.put("cityCode", city);
			strUrlPrice.put("productTypeId2", "");
			//
			strUrlTime = new HashMap<String, String>();
			strUrlTime.put("yth", "test");
			strUrlTime.put("key", "key");
			strUrlTime.put("productTypeId3", id+"");
			strUrlTime.put("productTypeId2", "");
			strUrlTime.put("startIndex", "0");
			strUrlTime.put("count", "13");
			strUrlTime.put("orderWhat", "createTime");
			strUrlTime.put("act", "ProductItemsInfo");
			strUrlTime.put("cityCode", city);


		}else {

			strSalesVolume = new HashMap<String, String>();
			strSalesVolume.put("yth", "test");
			strSalesVolume.put("productTypeId2", "");
			strSalesVolume.put("key", "key");
			strSalesVolume.put("productTypeId3", id+"");
			strSalesVolume.put("startIndex", "0");
			strSalesVolume.put("count", "13");
			strSalesVolume.put("orderWhat", "TotalXiaoLiang");
			strSalesVolume.put("act", "ProductItemsInfo");

			strUrlPrice = new HashMap<String, String>();
			strUrlPrice.put("yth", "test");
			strUrlPrice.put("productTypeId2", "");
			strUrlPrice.put("key", "key");
			strUrlPrice.put("productTypeId3", id+"");
			strUrlPrice.put("startIndex", "0");
			strUrlPrice.put("count", "13");
			strUrlPrice.put("orderWhat", "retailPrice");
			strUrlPrice.put("act", "ProductItemsInfo");
			strUrlTime = new HashMap<String, String>();
			strUrlTime.put("yth", "test");
			strUrlTime.put("key", "key");
			strUrlTime.put("productTypeId3", id+"");
			strUrlTime.put("startIndex", "0");
			strUrlTime.put("count", "13");
			strUrlTime.put("orderWhat", "createTime");
			strUrlTime.put("act", "ProductItemsInfo");
			strUrlTime.put("productTypeId2", "");

		}


		createTab1();
		createTab2();
		createTab3();
		initdata();

	}

	public void createTab1() {
		TabHost.TabSpec localTabSpec = this.tabHost.newTabSpec("0");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("价格");
		localTabSpec.setIndicator(localView).setContent(R.id.tab1);
		this.tabHost.addTab(localTabSpec);
		progress.CreateProgress();
		AsyncHttp.post_1(RealmName.REALM_NAME
				+ "/mi/getdata.ashx", strUrlPrice, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
	}

	public void createTab2() {
		TabHost.TabSpec localTabSpec = this.tabHost.newTabSpec("1");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("销量");
		localTabSpec.setIndicator(localView).setContent(R.id.tab2);
		this.tabHost.addTab(localTabSpec);

		AsyncHttp.post_1(RealmName.REALM_NAME
				+ "/mi/getdata.ashx", strSalesVolume, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse2(arg1);
			}
		});
	}

	public void createTab3() {
		TabHost.TabSpec localTabSpec = this.tabHost.newTabSpec("2");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("上架时间");
		localTabSpec.setIndicator(localView).setContent(R.id.tab3);
		this.tabHost.addTab(localTabSpec);
		AsyncHttp.post_1(RealmName.REALM_NAME
				+ "/mi/getdata.ashx", strUrlTime, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse3(arg1);
			}
		});
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<WareInformationData> list = new ArrayList<WareInformationData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				WareInformationData data = new WareInformationData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.proThumbnailImg = object.getString("proThumbnailImg");
				data.proName = object.getString("proName");
				data.retailPrice = object.getString("retailPrice");
				data.marketPrice = object.getString("marketPrice");
				list.add(data);
			}
			Message msg = new Message();
			msg.what = 10;
			msg.obj = list;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void parse2(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<WareInformationData> list2 = new ArrayList<WareInformationData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				WareInformationData data = new WareInformationData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.proThumbnailImg = object.getString("proThumbnailImg");
				data.proName = object.getString("proName");
				data.retailPrice = object.getString("retailPrice");
				data.marketPrice = object.getString("marketPrice");
				list2.add(data);
			}
			Message msg = new Message();
			msg.what = 20;
			msg.obj = list2;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void parse3(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<WareInformationData> list3 = new ArrayList<WareInformationData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				WareInformationData data = new WareInformationData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.proThumbnailImg = object.getString("proThumbnailImg");
				data.proName = object.getString("proName");
				data.retailPrice = object.getString("retailPrice");
				data.marketPrice = object.getString("marketPrice");
				list3.add(data);
			}
			Message msg = new Message();
			msg.what = 30;
			msg.obj = list3;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void initdata() {
		list_money = (ListView) findViewById(R.id.list_money);
		list_sales_volume = (ListView) findViewById(R.id.list_sales_volume);
		list_time = (ListView) findViewById(R.id.list_time);
		list_money.setCacheColorHint(0);
		list_sales_volume.setCacheColorHint(0);
		list_time.setCacheColorHint(0);

		list_money.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				int wareId = list.get(arg2).id;
				Intent intent = new Intent(WareClassifyFourActivity.this,
						WareInformationActivity.class);
				intent.putExtra("id", wareId);
				startActivity(intent);
			}
		});
		list_sales_volume.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				int wareId = list2.get(arg2).id;
				Intent intent = new Intent(WareClassifyFourActivity.this,
						WareInformationActivity.class);
				intent.putExtra("id", wareId);
				startActivity(intent);
			}
		});
		list_time.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				int wareId = list3.get(arg2).id;
				Intent intent = new Intent(WareClassifyFourActivity.this,
						WareInformationActivity.class);
				intent.putExtra("id", wareId);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

}
