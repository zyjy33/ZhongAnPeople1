package com.zams.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class ChuGouDetailActivity extends BaseActivity {
	private WareDao wareDao;
	private UserRegisterData registerData;
	private String yth;
	private String key;
	private String pwd;
	private String trade_no;
	private ArrayList<CardItem> banks;
	private String[] bankNames;
	private RelativeLayout click0, click1, click2, click3;

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 2:
					// 在此弹出选择框
					System.out.println("???");
					// 跳转到付款页面
					if (banks != null && banks.size() != 0) {
						// 表示是第二次支付
						System.out.println("写第二次支付");
						// initPopupWindow1();
						// showPopupWindow1(btn_OK);
						Intent intent = new Intent(ChuGouDetailActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 1);
						bundle.putSerializable("trade_no", trade_no);
						bundle.putStringArray("bank_names", bankNames);
						bundle.putSerializable("bank_objs", banks);
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						// 表示首次支付
						Intent intent = new Intent(ChuGouDetailActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 0);
						bundle.putSerializable("trade_no", trade_no);
						intent.putExtras(bundle);
						startActivity(intent);
						// initPopupWindow();
						// showPopupWindow(btn_OK);
					}

					break;
				case 3:
					String str = (String) msg.obj;
					Toast.makeText(getApplicationContext(), str, 200).show();
					break;

				default:
					break;
			}
		};
	};
	Map<String, String> params;
	private String url;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chugou_in_detail);
		String price = (String) getIntent().getSerializableExtra("price");

		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		wareDao = new WareDao(getApplicationContext());
		registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode().toString();
		key = registerData.getUserkey().toString();
		pwd = registerData.getPassword().toString();
		click0 = (RelativeLayout) findViewById(R.id.pay_click0);
		click1 = (RelativeLayout) findViewById(R.id.pay_click1);
		click2 = (RelativeLayout) findViewById(R.id.pay_click2);
		click3 = (RelativeLayout) findViewById(R.id.pay_click3);
		params = new HashMap<String, String>();
		params.put("act", "BuyPassTicketShopOnLine");
		params.put("yth", yth);
		params.put("payPassword", pwd);
		params.put("payType", "1");
		params.put("payPassTickets", price);
		url = RealmName.REALM_NAME + "/mi/getdata.ashx";
		click1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AsyncHttp.post_1(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onStart() {
						super.onStart();
					}

					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						System.out.println(arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							int status = object.getInt("status");
							if (status == 1) {

								trade_no = object.getString("trade_no");

								JSONArray array = object.getJSONArray("items");
								int len = array.length();
								if (len != 0) {
									banks = new ArrayList<CardItem>();
									bankNames = new String[len + 1];
									for (int i = 0; i < len; i++) {
										JSONObject object2 = array
												.getJSONObject(i);
										CardItem item = new CardItem();
										item.setType(object2
												.getString("pay_type"));
										item.setBankName(object2
												.getString("gate_id"));
										item.setLastId(object2
												.getString("last_four_cardid"));
										item.setId(object2
												.getString("UserSignedBankID"));
										banks.add(item);
										bankNames[i] = ParseBank.parseBank(
												item.getBankName(),
												getApplicationContext())
												+ "("
												+ ParseBank.paseName(item
												.getType())
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
								Message message = new Message();
								message.what = 2;
								handler.sendMessage(message);
							} else {
								String msg = object.getString("msg");
								Message message = new Message();
								message.what = 3;
								message.obj = msg;
								handler.sendMessage(message);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	};
}
