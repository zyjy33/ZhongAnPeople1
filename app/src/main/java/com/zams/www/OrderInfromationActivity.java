package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.pub.MyOrderInfromationAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.OrderInfromationData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderInfromationActivity extends BaseActivity {

	private TextView tv_title;
	private ListView listView;
	private int index;
	private String strUrl;
	private Map<String, String> params;
	private WareDao wareDao;
	private DialogProgress progress;
	private String yth, key;
	private ArrayList<OrderInfromationData> list;
	private MyPopupWindowMenu popupWindowMenu;
	private ArrayList<CardItem> banks = null;
	private String bankNames[] = null;
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					list = (ArrayList<OrderInfromationData>) msg.obj;
					MyOrderInfromationAdapter adapter = new MyOrderInfromationAdapter(
							list, OrderInfromationActivity.this, imageLoader);
					listView.setAdapter(adapter);
					break;

				default:
					break;
			}
		};
	};
	private boolean tag_flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderinformation);

		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(OrderInfromationActivity.this);
		tv_title = (TextView) findViewById(R.id.tv1);
		listView = (ListView) findViewById(R.id.list_order);
		listView.setCacheColorHint(0);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		index = (Integer) bundle.get("num");

		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();

		if (index == 0) {
			tag_flag = true;
			tv_title.setText("待付款订单列表");

			params = new HashMap<String, String>();
			params.put("act", "orderunpay");
			params.put("key", key);
			params.put("yth", yth);

		} else if (index == 1) {
			tag_flag = false;
			tv_title.setText("待发货订单列表");

			params = new HashMap<String, String>();
			params.put("act", "orderdelivered");
			params.put("key", key);
			params.put("yth", yth);
		} else if (index == 2) {
			tag_flag = false;
			tv_title.setText("待收货订单列表");

			params = new HashMap<String, String>();
			params.put("act", "orderreceived");
			params.put("key", key);
			params.put("yth", yth);
		} else if (index == 3) {
			tag_flag = false;
			tv_title.setText("已成功订单列表");

			params = new HashMap<String, String>();
			params.put("act", "orderpayed");
			params.put("key", key);
			params.put("yth", yth);
		}
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						parse(arg1, tag_flag);
					}
				});
		if (index == 0) {
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {


					// String total = list.get(arg2).Price;
					// String code = list.get(arg2).SerialNumber;
					// String reno = list.get(arg2).trno;
					Intent intent = new Intent(OrderInfromationActivity.this,
							OrderDetailActivity.class);

					Bundle bundle = new Bundle();
					bundle.putSerializable("obj", list.get(arg2));
					if (banks != null && banks.size() != 0) {
						bundle.putInt("tag", 1);
						bundle.putStringArray("bank_names", bankNames);
						bundle.putSerializable("bank_objs", banks);
					} else {

						bundle.putInt("tag", 0);
					}
					intent.putExtras(bundle);

					startActivity(intent);
				}
			});
		}
	}

	private void parse(String st, boolean flag) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<OrderInfromationData> list = new ArrayList<OrderInfromationData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				OrderInfromationData data = new OrderInfromationData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.SerialNumber = object.getString("orderSerialNumber");
				data.Time = object.getString("orderTime");
				data.UserName = object.getString("consigneeUserName");
				data.Address = object.getString("consignessAddress");
				data.Price = object.getString("totalPrice");
				data.trno = object.getString("UMPtrade_no");
				data.proName = object.getString("proName");
				data.count = object.getString("productCount");
				data.image = object.getString("proFaceImg");
				data.jf = object.getString("ConsumptionCredits");
				list.add(data);
			}
			if (flag) {
				JSONArray array = jsonObject.getJSONArray("UserSignedBankList");
				int len = array.length();
				if (len != 0) {
					banks = new ArrayList<CardItem>();
					bankNames = new String[len + 1];
					for (int i = 0; i < len; i++) {
						JSONObject object2 = array.getJSONObject(i);
						CardItem item = new CardItem();
						item.setType(object2.getString("pay_type"));
						item.setBankName(object2.getString("gate_id"));
						item.setLastId(object2.getString("last_four_cardid"));
						item.setId(object2.getString("UserSignedBankID"));
						banks.add(item);
						bankNames[i] = ParseBank.parseBank(item.getBankName(),
								getApplicationContext())
								+ "("
								+ ParseBank.paseName(item.getType())
								+ ")"
								+ item.getLastId();
					}
					CardItem item = new CardItem();
					item.setBankName("-1");
					item.setId("-1");
					item.setLastId("-1");
					item.setType("-1");
					banks.add(item);
					bankNames[len] = "新支付方式";
				}
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
