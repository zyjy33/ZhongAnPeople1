package com.hengyushop.demo.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.ctrip.openapi.java.utils.Validator;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DaiRegisterActivity extends BaseActivity implements
		OnClickListener {
	private EditText et_user_name, et_user_sfzh, userpwd, userphone,
			et_user_yz;
	private Button btn_register, get_yz;
	private String dai_user_name, phone, user_sfzh, pwd, pwdagain, insertdata,
			yz, shoujihao;
	private UserRegisterData data;
	private String str, hengyuName;
	private DialogProgress progress;
	private String strUrl;
	private TextView regise_tip;
	private String yanzhengma;
	private SharedPreferences spPreferences;
	String user_name, user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dai_zhuce);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.title_register);
		initdata();

		ImageView img_menu = (ImageView) findViewById(R.id.iv_fanhui);
		img_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

	}

	Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {

			switch (msg.what) {
				case 0:
					// et_user_yz.setText("");
					// username.setText("");
					// userphone.setText("");
					// userpostbox.setText("");
					// userpwd.setText("");
					// userpwdagain.setText("");
					String strhengyuname = (String) msg.obj;
					// dialog(strhengyuname);
					NewDataToast.makeText(getApplicationContext(), strhengyuname,
							false, 0).show();
					progress.CloseProgress();
					// Intent intent = new Intent(UserRegisterActivity.this,
					// UserLoginActivity.class);
					// startActivity(intent);
					finish();
					break;
				case 1:
					String strmsg = (String) msg.obj;
					NewDataToast
							.makeText(getApplicationContext(), strmsg, false, 0)
							.show();
					break;
				case 2:
					NewDataToast.makeText(getApplicationContext(), "验证码已发送", false,
							0).show();
					new Thread() {
						public void run() {
							for (int i = 120; i >= 0; i--) {
								if (i == 0) {
									handler.sendEmptyMessage(4);
								} else {
									Message msg = new Message();
									msg.arg1 = i;
									msg.what = 5;
									handler.sendMessage(msg);

									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {

										e.printStackTrace();
									}

								}
							}
						};
					}.start();
					break;
				case 3:

					NewDataToast.makeText(getApplicationContext(), "验证码已发送", false,
							0).show();
					break;
				case 4:
					get_yz.setEnabled(true);
					get_yz.setText("获取验证码");
					break;
				case 5:
					get_yz.setEnabled(false);
					get_yz.setText(msg.arg1 + "s");
					break;
				default:
					break;
			}
		};

	};

	private void initdata() {
		try {
			et_user_name = (EditText) findViewById(R.id.et_user_name);
			et_user_sfzh = (EditText) findViewById(R.id.et_user_sfzh);
			regise_tip = (TextView) findViewById(R.id.regise_tip);
			et_user_yz = (EditText) findViewById(R.id.et_user_yz);
			get_yz = (Button) findViewById(R.id.get_yz);
			userphone = (EditText) findViewById(R.id.et_user_phone);
			userpwd = (EditText) findViewById(R.id.et_user_pwd);
			btn_register = (Button) findViewById(R.id.btn_register);
			btn_register.setOnClickListener(this);
			get_yz.setOnClickListener(this);
			regise_tip.setOnClickListener(this);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		try {


			switch (v.getId()) {
				case R.id.regise_tip:
					Intent intent4 = new Intent(DaiRegisterActivity.this,
							Webview1.class);
					intent4.putExtra("zhuce_id", "5997");
					startActivity(intent4);
					break;
				case R.id.get_yz:
					phone = userphone.getText().toString().trim();
					if (phone.equals("")) {
						Toast.makeText(DaiRegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT)
								.show();
					}
					// else
					// if (phone.length() < 11 ) {
					// Toast.makeText(UserRegisterActivity.this, "手机号少于11位",
					// 200).show();
					// }
					else {
						if (Validator.isMobile(phone)) {
							// if (phone != null && phone.length() == 11) {
							strUrl = RealmName.REALM_NAME_LL
									+ "/user_verify_smscode?mobile=" + phone + "";

							AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {
									super.onSuccess(arg0, arg1);
									System.out.println("=============" + arg1);
									try {
										JSONObject object = new JSONObject(arg1);
										String result = object.getString("status");//
										String info = object.getString("info");// info
										if (result.equals("y")) {
											// Toast.makeText(UserRegisterActivity.this,
											// info, 200).show();
											yanzhengma = object.getString("data");
											handler.sendEmptyMessage(2);
										} else {
											Toast.makeText(
													DaiRegisterActivity.this, info,
													Toast.LENGTH_SHORT).show();
											// handler.sendEmptyMessage(3);
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}, getApplicationContext());

						} else {
							// NewDataToast.makeText(getApplicationContext(),
							// "请输入手机号码",false,0).show();
							Toast.makeText(DaiRegisterActivity.this, "手机号码不正确", Toast.LENGTH_SHORT)
									.show();
						}
						// } else {
						// // NewDataToast.makeText(getApplicationContext(),
						// "请输入手机号码",false,0).show();
						// Toast.makeText(UserRegisterActivity.this, "手机号码不能为空",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					break;
				case R.id.btn_register:
					dai_user_name = et_user_name.getText().toString().trim();
					user_sfzh = et_user_sfzh.getText().toString().trim();
					yz = et_user_yz.getText().toString().trim();
					phone = userphone.getText().toString().trim();
					pwd = userpwd.getText().toString().trim();

					if (dai_user_name.equals("")) {
						Toast.makeText(DaiRegisterActivity.this, "用户姓名不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (user_sfzh.equals("")) {
						Toast.makeText(DaiRegisterActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (phone.equals("")) {
						Toast.makeText(DaiRegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (phone.length() < 11) {
						Toast.makeText(DaiRegisterActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT)
								.show();
					} else if (yz.equals("")) {
						Toast.makeText(DaiRegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT)
								.show();
					}
					// else if (yz.length() < 6 ) {
					// Toast.makeText(UserRegisterActivity.this, "验证码少于六位",
					// Toast.LENGTH_SHORT).show();
					// }
					else if (pwd.equals("")) {
						Toast.makeText(DaiRegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (pwd.length() < 6) {
						Toast.makeText(DaiRegisterActivity.this, "密码不得小于6位", Toast.LENGTH_SHORT)
								.show();
					} else if (!(userpwd.getText().toString().length() <= 20 && userpwd
							.getText().toString().length() >= 6)) {
						Toast.makeText(DaiRegisterActivity.this, "密码在6-20位之间", Toast.LENGTH_SHORT)
								.show();
					} else {
						if (Validator.isIDCard(user_sfzh)) {
							try {
								progress = new DialogProgress(
										DaiRegisterActivity.this);
								progress.CreateProgress();
								try {
									strUrl = RealmName.REALM_NAME_LL
											+ "/parent_register?site=mobile&code="
											+ yz + "&parentid=" + user_id
											+ "&parentname=" + user_name + ""
											+ "&username=" + phone + "&realname="
											+ dai_user_name + "&password=" + pwd
											+ "&mobile=" + phone + "&identitycard="
											+ user_sfzh + "";
									// strUrl =
									// RealmName.REALM_NAME_LL+"/parent_register?site=mobile&code="+yz+"&parentid="+user_id+"&parentname="+user_name+""
									// +
									// "&username="+phone+"&password="+pwd+"&mobile="+phone+"&identitycard="+user_sfzh+"";
									System.out.println("注册" + strUrl);

									AsyncHttp.get(strUrl,
											new AsyncHttpResponseHandler() {
												@Override
												public void onSuccess(int arg0,
																	  String arg1) {
													// stub
													super.onSuccess(arg0, arg1);
													try {
														JSONObject jsonObject = new JSONObject(
																arg1);
														System.out
																.println("=================1=="
																		+ arg1);
														String status = jsonObject
																.getString("status");
														String info = jsonObject
																.getString("info");
														// if (status.equals("n")) {
														// System.out.println("=================2==");
														// str =
														// jsonObject.getString("info");
														// // String no =
														// jsonObject.getString("info");
														// //// str =
														// jsonObject.getString("info");
														// //
														// NewDataToast.makeText(getApplicationContext(),
														// no,false, 0).show();
														// progress.CloseProgress();
														// Message message = new
														// Message();
														// message.what = 1;
														// message.obj = str;
														// handler.sendMessage(message);
														// } else
														if (status.equals("y")) {
															try {
																System.out
																		.println("=================3=="
																				+ info);
																// hengyuName =
																// jsonObject.getString("info");

																SharedPreferences spPreferences = getSharedPreferences(
																		"longuserset_user",
																		MODE_PRIVATE);
																Editor editor = spPreferences
																		.edit();
																// editor.putBoolean("save",
																// true);
																// editor.putString("user_name",
																// userphone.getText().toString());
																// editor.putString("pwd",
																// userpwd.getText().toString());
																editor.commit();

																// Log.v("data1",
																// hengyuName + "");
																progress.CloseProgress();
																// Message message =
																// new Message();
																// message.what = 0;
																// message.obj =
																// hengyuName;
																// handler.sendMessage(message);
																NewDataToast
																		.makeText(
																				getApplicationContext(),
																				info,
																				false,
																				0)
																		.show();
																finish();
															} catch (Exception e) {
																e.printStackTrace();
															}
														} else {
															progress.CloseProgress();
															NewDataToast
																	.makeText(
																			getApplicationContext(),
																			info,
																			false,
																			0)
																	.show();
														}
													} catch (JSONException e) {
														// block
														e.printStackTrace();
													}
												}
											}, getApplicationContext());

								} catch (Exception e) {
									e.printStackTrace();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							showToast("验证身份证失败!");
						}
					}

					break;

				default:
					break;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

}
