package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MyWareAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.WareData;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WareClassifyActivity extends BaseActivity implements OnClickListener {

	private ListView list_classify_one;
	private String strUrl = RealmName.REALM_NAME
			+ "/mi/getdata.ashx";
	private WareDao waredao;
	private WareData ware;
	private ArrayList<WareData> list;
	private MyWareAdapter adapter;
	private List<WareData> allid;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 100:
					list = (ArrayList<WareData>) msg.obj;
					adapter = new MyWareAdapter(list, WareClassifyActivity.this, imageLoader);
					list_classify_one.setAdapter(adapter);
					progress.CloseProgress();
					break;
				case 1:
					Map<String, String> params = new HashMap<String, String>();
					params.put("act", "ProductCateInfo");
					params.put("yth", "test");
					params.put("key", "test");
					params.put("productCateParentId", "1");
					///mi/getdata.ashx?act=ProductCateInfo&yth=test&key=test&ProductCateParentId=1
					AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler(){
						public void onSuccess(int arg0, String arg1) {
							if (allid.size() == 0) {
								Map<String, String> map = new HashMap<String, String>();

								parse(arg1);
								selectwarename();
							} else {
								selectwarename();
							}
						};
					});
					break;
				default:
					break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_ware_classify);
		// 创建和打开数据库
		new Thread(){
			public void run() {

				waredao = new WareDao(getApplicationContext());

				list_classify_one = (ListView) findViewById(R.id.list_classify_one);
				list_classify_one.setCacheColorHint(0);

				allid = waredao.finAddID();
				handler.sendEmptyMessage(1);
			};
		}.start();
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(WareClassifyActivity.this);
		progress.CreateProgress();


		list_classify_one.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				String typename = list.get(arg2).productTypeName;
				int id = waredao.findbyTypeName(typename).ID;

				Intent intent = new Intent(WareClassifyActivity.this,
						WareClassifyTwoActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("name", typename);
				startActivity(intent);
			}
		});

	}

	// 解析网络数据 并插入到Sqlite数据库中
	public void parse(String st) {
		try {
			// List<WareData> allids = waredao.finAddID();

			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("Data");
			for (int i = 0; i < jsonArray.length(); i++) {
				WareData data = new WareData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.ID = object.getInt("id");
				data.productTypeName = object.getString("productTypeName");
				data.parentId = object.getInt("parentId");
				data.layer = object.getInt("layer");
				data.openUrl = object.getString("openUrl");
				data.SpecCommend = object.getInt("SpecCommend");
				Log.v("data1", data.ID + "");

				JSONArray jsonArray2 = object.getJSONArray("ProductName");
				for (int j = 0; j < jsonArray2.length(); j++) {
					WareData data2 = new WareData();
					JSONObject object2 = jsonArray2.getJSONObject(j);
					data2.ID = object2.getInt("id");
					data2.productTypeName = object2
							.getString("productTypeName");
					data2.parentId = object2.getInt("parentId");
					data2.layer = object2.getInt("layer");
					data2.openUrl = object2.getString("openUrl");
					data2.SpecCommend = object2.getInt("SpecCommend");

					Log.v("data2", data2.ID + "");

					JSONArray jsonArray3 = object2.getJSONArray("ProductName");
					for (int k = 0; k < jsonArray3.length(); k++) {
						WareData data3 = new WareData();
						JSONObject object3 = jsonArray3.getJSONObject(k);
						data3.ID = object3.getInt("id");
						data3.productTypeName = object3
								.getString("productTypeName");
						data3.parentId = object3.getInt("parentId");
						data3.layer = object3.getInt("layer");
						data3.openUrl = object3.getString("openUrl");
						data3.SpecCommend = object3.getInt("SpecCommend");
						Log.v("data3", data3.ID + "");

						ware = new WareData();
						ware.setID(data3.ID);
						ware.setProductTypeName(data3.productTypeName);
						ware.setParentId(data3.parentId);
						ware.setLayer(data3.layer);
						ware.setOpenUrl(data3.openUrl);
						ware.setSpecCommend(data3.SpecCommend);
						waredao.insertWare(ware);
					}
					ware = new WareData();
					ware.setID(data2.ID);
					ware.setProductTypeName(data2.productTypeName);
					ware.setParentId(data2.parentId);
					ware.setLayer(data2.layer);
					ware.setOpenUrl(data2.openUrl);
					ware.setSpecCommend(data2.SpecCommend);
					waredao.insertWare(ware);
				}

				ware = new WareData();
				ware.setID(data.ID);
				ware.setProductTypeName(data.productTypeName);
				ware.setParentId(data.parentId);
				ware.setLayer(data.layer);
				ware.setOpenUrl(data.openUrl);
				ware.setSpecCommend(data.SpecCommend);
				waredao.insertWare(ware);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 查询编号为1的商品名称 （商品分类第一级信息）
	private void selectwarename() {
		new Thread() {
			public void run() {
				List<WareData> allnames = waredao.findAllWare(1);
				Message message = new Message();
				message.what = 100;
				message.obj = allnames;
				handler.sendMessage(message);
			};

		}.start();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
		}
		return true;
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
