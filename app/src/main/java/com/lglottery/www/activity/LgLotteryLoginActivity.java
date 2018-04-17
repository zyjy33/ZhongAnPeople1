package com.lglottery.www.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.LoginDomain;
import com.lglottery.www.http.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LgLotteryLoginActivity extends BaseActivity {
	private final int GET_RND_SUCCESS = 0;
	private SharedUtils sharedUtils, personUtil;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_RND_SUCCESS:
				LoginDomain domain = (LoginDomain) msg.obj;
				RequestParams params = new RequestParams();
				params.put("yth", domain.getUserName());
				params.put(
						"pwd",
						HttpUtils.MD5(HttpUtils.MD5(domain.getPassWord())
								+ domain.getRnd()));
				WLog.v("有");
				AsyncHttp.post(U.LOTTERY_LOGIN, params,
						new AsyncHttpResponseHandler() {
							public void onSuccess(int arg0, String arg1) {
								System.out.println(arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									if (jsonObject.getInt("status") == 1) {
										sharedUtils.clear();
										sharedUtils.setStringValue("key",
												jsonObject.getString("key"));
										sharedUtils.setStringValue("yth",
												jsonObject.getString("yth"));
										sharedUtils.setStringValue("username",
												jsonObject
														.getString("username"));
										sharedUtils.setStringValue("phone",
												jsonObject.getString("phone"));
										Toast.makeText(getApplicationContext(),
												"登录成功!", 100).show();
										AppManager.getAppManager()
												.finishActivity();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							};

							@SuppressWarnings("deprecation")
							public void onFinish() {

								getPerson();
							};
						}, getApplicationContext());
				break;

			default:
				break;
			}
		};
	};

	private void getPerson() {
		if (sharedUtils.isHere("key")) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("key", sharedUtils.getStringValue("key"));
			params.put("yth", sharedUtils.getStringValue("yth"));
			params.put("act", "myInfo");
			AsyncHttp.post_1(U.LOTTERY_PERSONAL_INFO, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
							// showDialog(Config.SHOW_LOADING);
						}

						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							WLog.v(arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								Iterator<String> iterator = jsonObject.keys();
								if (jsonObject.getInt("status") == 1) {
									while (iterator.hasNext()) {
										String key = iterator.next();
										personUtil.setStringValue(key,
												jsonObject.getString(key));
									}

								} else {
									// 表示有错误
									Toast.makeText(getApplicationContext(),
											"身份验证过期，请重新登录!", 200).show();
									sharedUtils.clear();
									AppManager.getAppManager().finishActivity();
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// for()

						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							super.onFinish();
							// dismissDialog(Config.SHOW_LOADING);
							// removeDialog(Config.SHOW_LOADING);

						}
					});
		}
	}

	/**
	 * 組件的初始化
	 */
	private void init() {
		personUtil = new SharedUtils(getApplicationContext(),
				Config.PERSONAL_STATUS);
		sharedUtils = new SharedUtils(getApplicationContext(),
				Config.LOGIN_STATUS);
		Bundle bundle = getIntent().getExtras();
		String user = bundle.getString("userName");
		String pass = bundle.getString("passWord");

		login(user, pass);
	}

	private void login(final String userName, final String passWord) {

		if (userName.length() != 0 && passWord.length() != 0) {
			// 可以联网
			RequestParams params = new RequestParams();
			WLog.v(userName + "//" + passWord);
			params.put("yth", userName);
			AsyncHttp.post(U.GET_RND, params, new AsyncHttpResponseHandler() {
				@SuppressWarnings("deprecation")
				public void onStart() {
				};

				public void onSuccess(int arg0, String arg1) {
					WLog.v(arg1);

					try {
						JSONObject jsonObject = new JSONObject(arg1);
						if (jsonObject.getInt("status") == 1) {
							LoginDomain domain = new LoginDomain();
							domain.setPassWord(passWord);
							domain.setUserName(userName);
							domain.setRnd(jsonObject.getString("rnd"));
							Message msg = new Message();
							msg.what = GET_RND_SUCCESS;
							msg.obj = domain;
							handler.sendMessage(msg);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				};
			}, getApplicationContext());
		} else {
			Toast.makeText(getApplicationContext(), "请检查登录信息", 200).show();
		}
	}

	/**
	 * 登录事件
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_login_activity);
		init();
	}

}
