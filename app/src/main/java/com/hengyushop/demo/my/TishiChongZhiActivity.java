package com.hengyushop.demo.my;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.ZhiFuOKActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.R;
/**
 * ÊäÈëÃÜÂë
 * @author
 *
 */
public class TishiChongZhiActivity extends Activity implements OnClickListener{
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	String user_name, user_id,pwd;
	private EditText zhidupess;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	public static String yue_zhuangtai;
	public static String province,city,area,user_address,accept_name,user_mobile;
	public static String recharge_no,order_no,datetime,sell_price,give_pension,article_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_carxing);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		//		pwd = spPreferences.getString("pwd", "");
		progress = new DialogProgress(TishiChongZhiActivity.this);
		order_no = getIntent().getStringExtra("order_no");
		System.out.println("order_no-------------"+order_no);
		useryue();
		initUI();
	}



	protected void initUI() {
		zhidupess = (EditText) findViewById(R.id.et_user_pwd);
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);//
		//		tv_yue =(TextView) findViewById(R.id.tv_yue);
		//		System.out.println("amount-------------"+amount);
		//		tv_yue.setText("ÄãÊ£ÓàµÄÓà¶îÎª£¤"+amount);

		btnCancle.setOnClickListener(this);//

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 8:

				}
			}
		};
	}


	/**
	 * µã»÷´¥·¢ÊÂ¼þ
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		intent = new Intent();
		switch (v.getId()) {
			case R.id.btnConfirm://È¡Ïû
				//			String yue_fanhui = getIntent().getStringExtra("yue");
				finish();
				yue_zhuangtai = "1";
				break;
			case R.id.btnCancle://
				pwd = zhidupess.getText().toString().trim();
				System.out.println("pwd-------------"+pwd);
				if (pwd.equals("")) {
					Toast.makeText(TishiChongZhiActivity.this, "ÇëÊäÈëÃÜÂë", 200).show();
				}else{
					//				String yue = getIntent().getStringExtra("yue");
					String chuangke_sj = getIntent().getStringExtra("chuangke_sj");
					//				String order_yue = getIntent().getStringExtra("order_yue");
					//				if (yue != null) {
					//					userloginqm();
					//				}else
					if (chuangke_sj != null) {
						userloginqm();
					}
					//				else if (order_yue != null) {
					//					userloginqm();
					//				}else {
					//					ShouhuoOK(order_no);
					//				}

				}
				break;

			default:
				break;
		}
	}

	/**
	 * ÅÐ¶Ïµ±Ç°¾Û±ÒÓëÓà¶îµÄÖµ
	 * @param order_no
	 */
	private void useryue() {
		String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						amount = obj.getString("amount");
						String point = obj.getString("point");
						String jubi = getIntent().getStringExtra("jubi");
						tv_yue =(TextView) findViewById(R.id.tv_yue);
						if (jubi != null) {
							tv_yue.setText("ÄãÊ£ÓàµÄ¾Û±ÒÎª£¤"+point);
							System.out.println("point-------------"+point);
						}else {
							tv_yue.setText("ÄãÊ£ÓàµÄÓà¶îÎª£¤"+amount);
							System.out.println("amount-------------"+amount);
						}
					}else{
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, null);

	}

	/**
	 * »ñÈ¡µÇÂ¼Ç©Ãû
	 * @param order_no
	 */
	private void userloginqm() {
		try{
			//			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			//			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							amount = obj.getString("amount");
							login_sign = data.login_sign;
							System.out.println("======login_sign============="+login_sign);
							System.out.println("======recharge_no============="+order_no);
							loadYue(order_no,login_sign);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Óà¶îÖ§¸¶
	 * @param login_sign
	 * @param payment_id
	 */
	private void loadYue(String order_no, String login_sign) {
		try {
			//			String total_fee = String.valueOf(Double.parseDouble(retailPrice) + Double.parseDouble(String.valueOf(express_fee)));
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/payment_balance?user_id="+user_id+"&user_name="+user_name+"" +
							"&order_no="+order_no+"&paypassword="+pwd+"&sign="+login_sign+"",

					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("2================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									//							    	JSONObject jsonObject = object.getJSONObject("data");
									//										JSONArray jay = jsonObject.getJSONArray("orders");
									//										for (int j = 0; j < jay.length(); j++){
									//										   JSONObject objc= jay.getJSONObject(j);
									//										   accept_name = objc.getString("accept_name");
									//										    province = objc.getString("province");
									//											city = objc.getString("city");
									//											area = objc.getString("area");
									//											user_mobile = objc.getString("mobile");
									//											user_address = objc.getString("address");
									//											recharge_no = objc.getString("order_no");
									//											datetime = objc.getString("add_time");
									//											sell_price = objc.getString("payable_amount");
									//											JSONArray jsonArray = objc.getJSONArray("order_goods");
									//											for (int i = 0; i < jsonArray.length(); i++) {
									//												JSONObject json = jsonArray.getJSONObject(i);
									//												article_id = json.getString("article_id");
									////												sell_price = json.getString("sell_price");
									//												give_pension = json.getString("give_pension");
									//											}
									//										}
									Toast.makeText(TishiChongZhiActivity.this, info, 200).show();
									finish();
								}else {
									Toast.makeText(TishiChongZhiActivity.this, info, 200).show();
									finish();
								}
								progress.CloseProgress();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1);
							System.out.println("arg0-------------"+arg0);
							System.out.println("arg1-------------"+arg1);
							Toast.makeText(TishiChongZhiActivity.this, "Òì³£", 200).show();
						}

					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}