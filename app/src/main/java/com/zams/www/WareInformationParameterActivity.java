package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ListView;

import com.android.hengyu.pub.MyWareParameterAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.ListviewAttribute;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.WareParameterData;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WareInformationParameterActivity extends BaseActivity {
	private int wareid;
	private String strUrl;
	private ListView list_parameter;
	private MyPopupWindowMenu popupWindowMenu;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					ArrayList<WareParameterData> list = (ArrayList<WareParameterData>) msg.obj;
					MyWareParameterAdapter adapter = new MyWareParameterAdapter(
							list, WareInformationParameterActivity.this);
					list_parameter.setAdapter(adapter);
					ListviewAttribute
							.setListViewHeightBasedOnChildren(list_parameter);
					break;
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ware_infromation_parameter);
		popupWindowMenu = new MyPopupWindowMenu(this);
		list_parameter = (ListView) findViewById(R.id.list_parameter);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		wareid = (Integer) bundle.get("wareid");

		strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx" + wareid;

		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "OneProductItemInfo");
		params.put("yth", "test");
		params.put("key", "test");
		params.put("ProductItemId", wareid + "");

		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {

					WareParameterData[] adc = parse(arg1);
					ArrayList<WareParameterData> list = new ArrayList<WareParameterData>();
					for (int i = 0; i < adc.length; i++) {
						WareParameterData parameterData = new WareParameterData();
						WareParameterData data = adc[i];
						String parameter = data.getSpecParameterName() + "  "
								+ data.getSpecParameterValue() + "\n";
						Log.v("data3", parameter);
						parameterData.specParameterName = data
								.getSpecParameterName();
						parameterData.specParameterValue = data
								.getSpecParameterValue();
						list.add(parameterData);

						Message message = new Message();
						message.what = 0;
						message.obj = list;
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private WareParameterData[] parse(String st) {
		WareParameterData advrt[] = null;
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);

				JSONArray jsonArray2 = object.getJSONArray("SpecParameterName");
				advrt = new WareParameterData[jsonArray2.length()];
				ArrayList<String> name = new ArrayList<String>();
				for (int j = 0; j < jsonArray2.length(); j++) {
					JSONObject object2 = jsonArray2.getJSONObject(j);
					WareParameterData data = new WareParameterData();
					String str1 = object2.getString("specParameterName");
					name.add(str1);
					data.setSpecParameterName(str1);

					JSONArray jsonArray3 = object2
							.getJSONArray("ParameterValue");
					ArrayList<String> value = new ArrayList<String>();
					for (int k = 0; k < jsonArray3.length(); k++) {
						JSONObject object3 = jsonArray3.getJSONObject(k);
						String str2 = object3.getString("specParameterValue");
						value.add(str2);
						data.setSpecParameterValue(str2);
					}
					advrt[j] = data;
				}
			}
		} catch (Exception e) {
		}
		return advrt;
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
