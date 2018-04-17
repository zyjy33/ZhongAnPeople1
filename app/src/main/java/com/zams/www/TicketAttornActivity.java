package com.zams.www;

import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TicketAttornActivity extends BaseActivity {

	private EditText et_to_name, et_cession_num, et_paypassword;
	private Button btn_cashcession;
	private String yth, rnd, toname, number, paypassword, mm, mi;
	private WareDao wareDao;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;
	private TicketAttornActivity md5;
	private MessageDigest md;
	private String inStr, strUrl;

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					String str = (String) msg.obj;
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), str, 200).show();
					finish();
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket_cession);

		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		md5 = new TicketAttornActivity();

		example();
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		rnd = registerData.getUserrnd();
	}

	private void example() {
		et_cession_num = (EditText) findViewById(R.id.et_cession_num);
		et_to_name = (EditText) findViewById(R.id.et_to_name);
		et_paypassword = (EditText) findViewById(R.id.et_paypassword);
		btn_cashcession = (Button) findViewById(R.id.btn_cashcession);

		btn_cashcession.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toname = et_to_name.getText().toString();
				number = et_cession_num.getText().toString();
				paypassword = et_paypassword.getText().toString();

				md5.setInStr(paypassword);
				md5.init();
				mm = md5.compute();
				mm = mm.toUpperCase();
				System.out.println(mm);
				String myrnd = rnd;
				md5.setInStr(mm + myrnd);
				md5.init();
				mi = md5.compute();
				Log.v("data1", toname + "  " + number + "  " + mi + "  " + yth
						+ "  " + rnd);
				strUrl = RealmName.REALM_NAME + "/mi/TransPassTicket.ashx?yth="
						+ yth + "&toyth=" + toname + "&tofee=" + number
						+ "&paypwd=" + mi + "&toname=";
				progress.CreateProgress();
				AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);

						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String msg = jsonObject.getString("msg");

							Message message = new Message();
							message.what = 0;
							message.obj = msg;
							handler.sendMessage(message);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, getApplicationContext());
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

}
