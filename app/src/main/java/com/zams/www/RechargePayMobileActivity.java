package com.zams.www;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.MyLetterListView.OnTouchingLetterChangedListener;

public class RechargePayMobileActivity extends BaseActivity {

	private TextView tv_order;
	private EditText et_paypwd;
	private Button btn_pay;
	private String infromation, money, spmoney, orderid;
	private MessageDigest md;
	private String yth, rnd, pwd, PassTicket, mm1, mm2, mipwd, mipaypwd,
			paypwd;
	private int status;
	private String inStr;
	private WareDao wareDao;
	private String strUrl;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					String str = (String) msg.obj;
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), str, 200).show();
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
		setContentView(R.layout.recgarge_pay);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(RechargePayMobileActivity.this);
		examble();
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		infromation = (String) bundle.get("infromation");
		money = (String) bundle.get("money");
		spmoney = (String) bundle.get("spmoney");
		PassTicket = bundle.getString("PassTicket");

		// 获取订单号
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		orderid = formatter.format(curDate);
		Log.v("data2", orderid);

		// 获取用户信息
		List<UserRegisterData> list = wareDao.findisLogin();
		if (list.size() != 0) {
			UserRegisterData data = wareDao.findIsLoginHengyuCode();
			yth = data.getHengyuCode();
			pwd = data.getPassword();
			rnd = data.getUserrnd();
			Log.v("data2", yth + "  " + pwd + "   " + rnd);
			// 密码加密
			RechargePayMobileActivity md5 = new RechargePayMobileActivity();
			md5.setInStr(pwd);
			md5.init();
			mm1 = md5.compute();
			mm1 = mm1.toUpperCase();
			md5.setInStr(mm1 + rnd);
			md5.init();
			mipwd = md5.compute();
			System.out.println(mipwd);
		}

		tv_order.setText("充值信息:" + infromation + "\n充值金额:" + spmoney
				+ "\n所需付款:" + money + "\n\n所剩现金券:" + PassTicket);
	}

	private void examble() {
		tv_order = (TextView) findViewById(R.id.tv_order);
		et_paypwd = (EditText) findViewById(R.id.et_user_paypwd);
		btn_pay = (Button) findViewById(R.id.btn_confirm_pay);

		btn_pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				paypwd = et_paypwd.getText().toString();
				// 支付密码加密
				RechargePayMobileActivity md5s = new RechargePayMobileActivity();
				md5s.setInStr(paypwd);
				md5s.init();
				mm2 = md5s.compute();
				mm2 = mm2.toUpperCase();
				md5s.setInStr(mm2 + rnd);
				md5s.init();
				mipaypwd = md5s.compute();
				System.out.println(mipaypwd);
				progress.CreateProgress();

				strUrl = RealmName.REALM_NAME + "/mi/99recharge.ashx?" + "yth="
						+ yth + "&pwd=" + mipwd + "&paypwd=" + mipaypwd
						+ "&mobile=" + infromation + "&fee=" + spmoney
						+ "&orderid=" + orderid;
				Map<String, String> param = new HashMap<String, String>();
				param.put("yth", yth);
				param.put("pwd", mipwd);
				param.put("paypwd", mipaypwd);
				param.put("mobile", infromation);
				param.put("fee", spmoney);
				param.put("orderid", orderid);

				AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/99recharge.ashx",
						param, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);

								try {
									JSONObject object = new JSONObject(arg1);
									status = object.getInt("status");
									if (status != 0) {
										String str = object.getString("msg");
										Message message = new Message();
										message.what = 0;
										message.obj = str;
										handler.sendMessage(message);
									} else {
										String str = object.getString("msg");
										Message message = new Message();
										message.what = 0;
										message.obj = str;
										handler.sendMessage(message);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

			}
		});
	}

	public void MD5() {
		inStr = null;
		md = null;
	}

	public void setInStr(String str) {
		this.inStr = str;
	}

	public void init() {
		try {
			this.md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public String compute() {
		char[] charArray = this.inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] mdBytes = this.md.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			int val = (mdBytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
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
