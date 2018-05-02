package com.zams.www;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.hengyu.web.FtpImage;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SoftUpdataActivity extends BaseActivity {
	private TextView v1, v2, v3,price;
	private LinearLayout l1, l2, l3;
	private Button btn, updata_ico;
	private EditText text1, text2, pass1, pass2;
	private int INDEX = 0;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	private String path;
	private WareDao wareDao;
	private String yth;
	private String key;
	private String image_url;

	private void setImage() {

		// 使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		getAlbum.setType(IMAGE_TYPE);
		startActivityForResult(getAlbum, IMAGE_CODE);
	}

	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK) { // 此处的 RESULT_OK 是系统自定义得一个常量
			Log.e("TAG->onresult", "ActivityResult resultCode error");
			return;
		}
		Bitmap bm = null;
		// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
		ContentResolver resolver = getContentResolver();
		// 此处的用于判断接收的Activity是不是你想要的那个
		if (requestCode == IMAGE_CODE) {
			try {
				Uri originalUri = data.getData(); // 获得图片的uri
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				// 显得到bitmap图片
				// img_head.setImageBitmap(bm);
				// 这里开始的第二部分，获取图片的路径：
				String[] proj = { MediaStore.Images.Media.DATA };
				// 好像是android多媒体数据库的封装接口，具体的看Android文档
				Cursor cursor = managedQuery(originalUri, proj, null, null,
						null);
				// 按我个人理解 这个是获得用户选择的图片的索引值
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
				cursor.moveToFirst();
				// 最后根据索引值获取图片路径
				path = cursor.getString(column_index);

				new Thread() {
					public void run() {

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmssSSS");
						Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
						// fileName = formatter.format(curDate);

						try {
							FtpImage.ftpUpload(path, yth, yth + "_card");
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (FTPIllegalReplyException e) {
							e.printStackTrace();
						} catch (FTPException e) {

							e.printStackTrace();
						} catch (FTPDataTransferException e) {

							e.printStackTrace();
						} catch (FTPAbortedException e) {
							e.printStackTrace();
						} catch (IOException e) {

							e.printStackTrace();
						}

						String imgUrl = "/ftpFiles/PhoneImages/" + yth + "/"
								+ yth + "_card.jpg";
						String strUrl = RealmName.REALM_NAME
								+ "/mi/receiveOrderInfo.ashx?act=UpdateUserAvatarimage&yth="
								+ yth + "&AvatarimageURL=" + imgUrl + "&key="
								+ key;
						image_url = imgUrl;
						AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
							public void onSuccess(int arg0, String arg1) {
								updata_ico.setText("上传完成");

							};
						}, getApplicationContext());

					};
				}.start();

			} catch (IOException e) {
				Log.e("TAG-->Error", e.toString());
			}
		}
	}

	private String trade_no;
	private ArrayList<CardItem> banks;
	private String[] bankNames;

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			if (banks != null && banks.size() != 0) {
				// 表示是第二次支付
				System.out.println("写第二次支付");
				// initPopupWindow1();
				// showPopupWindow1(btn_OK);
				Intent intent = new Intent(SoftUpdataActivity.this,
						PayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("tag", 1);
				bundle.putSerializable("trade_no", trade_no);
				bundle.putStringArray("bank_names", bankNames);
				bundle.putSerializable("bank_objs", banks);
				intent.putExtras(bundle);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			} else {
				// 表示首次支付
				Intent intent = new Intent(SoftUpdataActivity.this,
						PayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("tag", 0);
				bundle.putSerializable("trade_no", trade_no);
				intent.putExtras(bundle);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
				// initPopupWindow();
				// showPopupWindow(btn_OK);
			}
		};
	};

	private void init() {
		pass1 = (EditText) findViewById(R.id.pass1);
		pass2 = (EditText) findViewById(R.id.pass2);
		text1 = (EditText) findViewById(R.id.text11);
		text2 = (EditText) findViewById(R.id.text22);
		price = (TextView) findViewById(R.id.price);
		price.setText(getIntent().getStringExtra("price")+"元");
		v1 = (TextView) findViewById(R.id.text1);
		v2 = (TextView) findViewById(R.id.text2);
		v3 = (TextView) findViewById(R.id.text3);
		l1 = (LinearLayout) findViewById(R.id.layout1);
		l2 = (LinearLayout) findViewById(R.id.layout2);
		l3 = (LinearLayout) findViewById(R.id.layout3);
		btn = (Button) findViewById(R.id.btn);
		l1.setVisibility(View.VISIBLE);
		l2.setVisibility(View.INVISIBLE);
		l3.setVisibility(View.INVISIBLE);
		v1.setTextColor(getResources().getColor(R.color.green));
		v2.setTextColor(getResources().getColor(R.color.black));
		v3.setTextColor(getResources().getColor(R.color.black));
		updata_ico = (Button) findViewById(R.id.updata_ico);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();
		if (yth == null) {
			NewDataToast.makeText(getApplicationContext(), "用户未登录", false, 0).show();
			AppManager.getAppManager().finishActivity();
		}else {
			SharedUtils utils = new SharedUtils(getApplicationContext(), "shouyi");
			if(utils.getStringValue("IsVipPrivilege").equals("1")){
				NewDataToast.makeText(getApplicationContext(), "无需再次升级", false, 0).show();
				AppManager.getAppManager().finishActivity();
			}
		}
		updata_ico.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setImage();
			}
		});

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (INDEX == 2) {
					// 在这里进行数据的提交
					String name = text1.getText().toString();
					String card_id = text2.getText().toString();
					String pass_1 = pass1.getText().toString();
					String pass_2 = pass2.getText().toString();
						/*
						 * http://www.ehaoyy.com//mi/getdata.ashx?act=
						 * RealNameAuthentication_Phone
						 * &identityCard=55&identityimageURL=55
						 * &payPassword=1&actualname=1&yth=admin
						 * &PayType=1&PayPassTickets=1
						 */
					Map<String, String> params = new HashMap<String, String>();
					params.put("identityCard", card_id);
					params.put("identityimageURL", image_url);
					params.put("payPassword", pass_1);
					params.put("yth", yth);
					params.put("actualname", name);
					params.put("payType", "1");
					params.put("productBenchmarkPriceID", getIntent().getStringExtra("vip"));
					params.put("payPassTickets", getIntent().getStringExtra("price"));
					params.put("act", "RealNameAuthentication_Phone");
					System.out.println(card_id + "--" + name + "--"
							+ pass_1 + "---" + image_url + "--" + yth);
					AsyncHttp
							.post_1(RealmName.REALM_NAME
											+ "/mi/getdata.ashx",
									params, new AsyncHttpResponseHandler() {
										public void onSuccess(int arg0,
															  String arg1) {
											super.onSuccess(arg0, arg1);
											System.out.println(arg1);
											try {
												JSONObject object = new JSONObject(
														arg1);
												trade_no = object
														.getString("trade_no");
												JSONArray array = object
														.getJSONArray("items");
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
																+ ParseBank
																.paseName(item
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
												handler.sendEmptyMessage(0);
											} catch (JSONException e) {
												// block
												e.printStackTrace();
											}

										};
									} );

				} else if (INDEX == 1) {
					if (!(pass1.getText().toString().length()<=20&&pass1.getText().toString().length()>=6)) {
						NewDataToast.makeText(getApplicationContext(), "密码在6-20位之间", false, 0).show();
					}else if (!(pass2.getText().toString().length()<=20&&pass2.getText().toString().length()>=6)) {
						NewDataToast.makeText(getApplicationContext(), "密码在6-20位之间", false, 0).show();
					}else if (!pass1.getText().toString().equals(pass2.getText().toString())) {
						Toast.makeText(getApplicationContext(), "两次交易密码不一致",
								Toast.LENGTH_SHORT).show();
					}else {
						INDEX = INDEX + 1;
					}
				}else {
					INDEX = INDEX + 1;
				}
				switch (INDEX) {
					case 0:
						v1.setTextColor(getResources().getColor(R.color.green));
						v2.setTextColor(getResources().getColor(R.color.black));
						v3.setTextColor(getResources().getColor(R.color.black));
						l1.setVisibility(View.VISIBLE);
						l2.setVisibility(View.INVISIBLE);
						l3.setVisibility(View.INVISIBLE);
						break;
					case 1:
						v2.setTextColor(getResources().getColor(R.color.green));
						v1.setTextColor(getResources().getColor(R.color.black));
						v3.setTextColor(getResources().getColor(R.color.black));
						l1.setVisibility(View.INVISIBLE);
						l2.setVisibility(View.VISIBLE);
						l3.setVisibility(View.INVISIBLE);
						break;
					case 2:
						v3.setTextColor(getResources().getColor(R.color.green));
						v2.setTextColor(getResources().getColor(R.color.black));
						v1.setTextColor(getResources().getColor(R.color.black));
						l1.setVisibility(View.INVISIBLE);
						l2.setVisibility(View.INVISIBLE);
						l3.setVisibility(View.VISIBLE);
						break;
					default:
						break;
				}
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soft_updata_layout);
		init();
	}
}
