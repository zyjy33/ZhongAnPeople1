package com.zams.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.RechargeDao;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.RechargeQQData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RechargeQQActivity extends BaseActivity implements OnClickListener {

	private Spinner sp_qq_business, sp_qq_money;
	private Button btn_qq;
	private TextView tv_name, tv_money;
	private EditText et_qq_number, et_confrim_number;
	private LinearLayout ll_information;
	private List<String> qq_money;
	private String business, number, money, PassTicket;
	private String yth, qq_number, confrim_number;
	private RechargeDao rechargeDao;
	private ArrayAdapter aa;
	private ArrayList<String> al_type;
	private List<UserRegisterData> list2;
	private WareDao wareDao;
	private DialogProgress progress;
	private String strUrl;
	private int code;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					al_type = (ArrayList<String>) msg.obj;
					aa = new ArrayAdapter(RechargeQQActivity.this,
							android.R.layout.simple_spinner_item, al_type);
					aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_qq_business.setAdapter(aa);
					break;
				case 1:
					ll_information.setVisibility(View.VISIBLE);
					tv_money.setText(money);
					tv_name.setText(business);
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
		setContentView(R.layout.recharge_qq);
		popupWindowMenu = new MyPopupWindowMenu(this);
		rechargeDao = new RechargeDao(getApplicationContext());
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(RechargeQQActivity.this);
		btn_qq = (Button) findViewById(R.id.btn_qq);
		SpinnerData();
		getSpinnerData();
		btn_qq.setOnClickListener(this);
		ArrayList<RechargeQQData> type = rechargeDao.findAllType();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < type.size(); i++) {
			list.add(type.get(i).type);
		}
		Message message = new Message();
		message.what = 0;
		message.obj = list;
		handler.sendMessage(message);

		list2 = wareDao.findisLogin();
		if (list2.size() != 0) {
			UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
			yth = registerData.getHengyuCode();
		}

	}

	private void SpinnerData() {
		sp_qq_business = (Spinner) findViewById(R.id.sp_qq_business);
		sp_qq_money = (Spinner) findViewById(R.id.sp_qq_money);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_money = (TextView) findViewById(R.id.tv_money);
		et_qq_number = (EditText) findViewById(R.id.et_qq_numner);
		et_confrim_number = (EditText) findViewById(R.id.et_confrim_qq_number);
		sp_qq_business.setPrompt("请选择充值类型");
		sp_qq_money.setPrompt("请选择充值数量");
		ll_information = (LinearLayout) findViewById(R.id.ll_information);
		ll_information.setVisibility(View.GONE);

		String[] str2 = new String[] { "请输入充值数量", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", };
		qq_money = new ArrayList<String>();
		for (int i = 0; i < str2.length; i++) {
			qq_money.add(str2[i]);
		}
		ArrayAdapter aa2 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, qq_money);
		aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_qq_money.setAdapter(aa2);
	}

	private void getSpinnerData() {
		sp_qq_business.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				business = al_type.get(arg2);
				if (!business.equals("请输入充值类型") && !number.equals("请输入充值数量")) {
					getAllMoney();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp_qq_money.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				number = qq_money.get(arg2);
				if (!number.equals("请输入充值数量") && !business.equals("请输入充值类型")) {
					getAllMoney();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void getAllMoney() {
		RechargeQQData data = rechargeDao.findCodeByType(business);
		code = data.getCode();
		Toast.makeText(getApplicationContext(), code + "", 200).show();
		progress.CreateProgress();
		strUrl = RealmName.REALM_NAME
				+ "/mi/getdata.ashx";

		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "QQRechargeCalls");
		params.put("key", "");
		params.put("yth", yth);
		params.put("productid", code+"");
		params.put("quantity", number);
		AsyncHttp.post_1(strUrl, params,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);

				// System.out.println(httpToServer.getJsonString(strUrl));
				try {
					JSONObject object = new JSONObject(arg1);
					int status = object.getInt("status");
					if (status == 1) {
						money = object.getString("telFee1");
						PassTicket = object.getString("PassTicket");
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} );
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_qq:
				if (business.equals("请输入充值类型") || number.equals("请输入充值数量")) {
					Toast.makeText(getApplicationContext(), "请输入充值信息", 200).show();
				} else {
					qq_number = et_qq_number.getText().toString();
					confrim_number = et_confrim_number.getText().toString();
					if (!"".equals(qq_number) || !"".equals(confrim_number)) {
						if (qq_number.equals(confrim_number)) {
							if (list2.size() != 0) {
								Intent intent = new Intent(RechargeQQActivity.this,
										RechargePayQQActivity.class);
								intent.putExtra("name", business);
								intent.putExtra("money", money);
								intent.putExtra("number", number);
								intent.putExtra("qqnumber", qq_number);
								intent.putExtra("PassTicket", PassTicket);
								startActivity(intent);
							} else {
								int index = 1;
								Intent intent = new Intent(RechargeQQActivity.this,
										UserLoginActivity.class);
								intent.putExtra("login", index);
								startActivity(intent);
								finish();
							}
						} else {
							et_confrim_number.setError("两次QQ号码输入不一致");
						}
					} else {
						Toast.makeText(getApplicationContext(), "请输入QQ号码", 200)
								.show();
					}

				}
				break;

			default:
				break;
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
			popupWindowMenu.showAtLocation(findViewById(R.id.recharge_qq),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}
}
