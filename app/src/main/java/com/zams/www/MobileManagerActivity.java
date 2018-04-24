package com.zams.www;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;

import com.android.hengyu.pub.MyShopTicketAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.TicketData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobileManagerActivity extends BaseActivity {

	private ListView listView;
	private WareDao wareDao;
	private String yth, key, str2;
	private ArrayList<TicketData> list;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					list = (ArrayList<TicketData>) msg.obj;
					MyShopTicketAdapter adapter = new MyShopTicketAdapter(list,
							MobileManagerActivity.this);
					listView.setAdapter(adapter);
					progress.CloseProgress();
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mobile_manager_layout);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(MobileManagerActivity.this);

		listView = (ListView) findViewById(R.id.list_ticket);
		listView.setCacheColorHint(0);

		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();

		str2 = RealmName.REALM_NAME + "/mi/getdata.ashx";
		Map<String, String> param = new HashMap<String, String>();
		param.put("act", "GetRecharge");
		param.put("yth", yth);
		AsyncHttp.post_1(str2, param, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
		progress.CreateProgress();
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<TicketData> list = new ArrayList<TicketData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				TicketData data = new TicketData();
				JSONObject object = jsonArray.getJSONObject(i);

				data.time = object.getString("rechargeTime");
				data.expenses = object.getString("rechargePrice");
				data.income = object.getString("rechargeObject");
				// 0是失败订单，1是成功订单，2是正在充值状态
				String status = object.getString("orderStatus");
				if (status.equals("1")) {
					data.balance = "充值成功";
				} else if (status.equals("0")) {
					data.balance = "充值失败";
				} else if (status.equals("2")) {
					data.balance = "等待充值";
				}
				list.add(data);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = list;
			handler.sendMessage(msg);
		} catch (Exception e) {

			e.printStackTrace();
		}
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
