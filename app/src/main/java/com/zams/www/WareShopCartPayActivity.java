package com.zams.www;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class WareShopCartPayActivity extends BaseActivity {

	private TextView tv_username, tv_tongquan, tv_shoptongquan;
	private EditText et_paypwd;
	private Button btn_pay;
	private String username, PassTicket, shopPassTicket, orderSerialNumber,
			endmoney, imei, paypwd;
	private String strUrl, str2;
	private DialogProgress progress;
	private WareDao wareDao;
	private double money, ticket, shopticket;
	private MyPopupWindowMenu popupWindowMenu;
	private String yth, key;
	private int id;
	private UserRegisterData data2;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					String str = (String) msg.obj;
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), str, 200).show();
					break;
				case 1:
					String str2 = (String) msg.obj;
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), str2, 200).show();
					break;
				case 2:
					UserRegisterData registerData = (UserRegisterData) msg.obj;
					tv_username.setText(registerData.userName);
					tv_tongquan.setText(registerData.PassTicketBalance);
					tv_shoptongquan.setText(registerData.shopPassTicket);
					progress.CloseProgress();
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
		setContentView(R.layout.ware_shopcart_pay);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(WareShopCartPayActivity.this);
		wareDao = new WareDao(getApplicationContext());

	}
	@Override
	protected void onResume() {

		super.onResume();
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();

		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		orderSerialNumber = (String) bundle.get("orderSerialNumber");
		id = (Integer) bundle.get("id");
		if (id == 1) {
		/*	ShopCartData data = wareDao.findResult();
			endmoney = data.getEndmoney();*/
			endmoney = getIntent().getStringExtra("endmoney");
		} else if (id == 2) {
			endmoney = (String) bundle.get("money");
		}
		Log.v("data1", orderSerialNumber + "    " + endmoney);

		examble();

		wareDao.deleteAllShopCart();

		str2 = RealmName.REALM_NAME + "/mi/getdata.ashx";
		progress.CreateProgress();

		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "myInfo");
		params.put("key", key);
		params.put("yth", yth);
		AsyncHttp.post_1( str2, params,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);

				try {
					JSONObject object2 = new JSONObject(arg1);
					data2 = new UserRegisterData();
					data2.userName = object2.getString("username");
					data2.PassTicketBalance = object2
							.getString("PassTicketBalance");
					data2.shopPassTicket = object2.getString("shopPassTicket");

					username = data2.userName;
					money = Float.parseFloat(endmoney);
					ticket = Float.parseFloat(data2.shopPassTicket);
					shopticket = Float.parseFloat(data2.PassTicketBalance);

					Log.v("data1", money + "   " + ticket + "   " + shopticket
							+ "    " + (ticket + shopticket));

					Message msg = new Message();
					msg.what = 2;
					msg.obj = data2;
					handler.sendMessage(msg);
				} catch (NumberFormatException e) {

					e.printStackTrace();
				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
		});
	}
	private void examble() {
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_tongquan = (TextView) findViewById(R.id.tv_tongquan);
		tv_shoptongquan = (TextView) findViewById(R.id.tv_shoptongquan);
		et_paypwd = (EditText) findViewById(R.id.et_user_paypwd);
		btn_pay = (Button) findViewById(R.id.btn_confirm_pay);
		btn_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				paypwd = et_paypwd.getText().toString();
				if ("".equals(paypwd)) {
					Toast.makeText(getApplicationContext(), "请输入支付密码!", 200)
							.show();
				} else {
					if (money > (ticket + shopticket)) {
						Toast.makeText(getApplicationContext(),
								"余额不足!", 200).show();
					} else {
						strUrl = RealmName.REALM_NAME
								+ "/mi/receiveOrderInfo_business.ashx?"
								+ "imei="
								+ imei
								+ "&act=ConfirmPayPassTicket&bossUid=1&yth="
								+ yth + "&buyPwd=" + paypwd
								+ "&orderSerialNumber=" + orderSerialNumber
								+ "&sourceType=phone";
						Log.v("data1", "付款地址:" + strUrl);
						progress.CreateProgress();

						AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(){
							public void onSuccess(int arg0, String arg1) {

								try {
									JSONObject object = new JSONObject(arg1);
									int status = object.getInt("status");
									if (status == 1) {
										String str = object.getString("msg");
										Message message = new Message();
										message.what = 0;
										message.obj = str;
										handler.sendMessage(message);
										// wareDao.deleteAllShopCart();
									} else {
										String str = object.getString("msg");
										Message message = new Message();
										message.what = 1;
										message.obj = str;
										handler.sendMessage(message);
									}
								} catch (JSONException e) {

									e.printStackTrace();
								}
								Intent intent = new Intent(
										WareShopCartPayActivity.this,
										MainFragment.class);
								startActivity(intent);
								AppManager.getAppManager().finishActivity();

							};
						}, getApplicationContext());
					}
				}
			}
		});
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
