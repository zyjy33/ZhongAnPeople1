package com.hengyushop.demo.my;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
/**
 * 服务顾问选项
 * @author Administrator
 *
 */
public class ChuanKeXuanZeActivity extends BaseActivity implements OnClickListener{
	private DialogProgress progress;
	String user_name, user_id,login_sign;
	public static String value;
	private SharedPreferences spPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chuangke_fangshi);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(ChuanKeXuanZeActivity.this);
		setUpViews();
	}
	
	private void setUpViews() {
		System.out.println("======login_sign============="+login_sign);
		RelativeLayout rl_nan = (RelativeLayout) findViewById(R.id.rl_nan);
		RelativeLayout rl_nv = (RelativeLayout) findViewById(R.id.rl_nv);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		rl_nan.setOnClickListener(this); 
		rl_nv.setOnClickListener(this);
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.rl_nan:
				 value = "普通服务顾问";
//				 userloginqm();
				 finish();
				break;
			case R.id.rl_nv:
				 value = "精英服务顾问";
//				 userloginqm();
				 finish();
				break;
			case R.id.btn_login:
				finish();
				break;
		default:
			break;
		}
	}
	
	/**
	 * 获取登录签名
	 * @param order_no 
	 */
//	private void userloginqm() {
//			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
//			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
//				public void onSuccess(int arg0, String arg1) {
//					try {
//						JSONObject object = new JSONObject(arg1);
//						String status = object.getString("status");
//						if (status.equals("y")) {
//							JSONObject obj = object.getJSONObject("data");
//							UserRegisterllData data = new UserRegisterllData();
//							data.login_sign = obj.getString("login_sign");
//							login_sign = data.login_sign;
//							System.out.println("======login_sign============="+login_sign);
//							loadusersex(login_sign);
//						}else{
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				};
//			}, null);
//	}
	/**
	 * 修改昵称
	 * @param login_sign 
	 * @param payment_id 
	 */
	private void loadusersex(String login_sign) {
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/user_update_field?user_id="+user_id+"&user_name="+user_name+"" +
						"&field=sex&value="+value+"&sign="+login_sign+"",
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
//									Toast.makeText(GenderFangShiActivity.this, info, 200).show();
									finish();
							    } else {
							    	progress.CloseProgress();
									Toast.makeText(ChuanKeXuanZeActivity.this, info, 200).show();
									finish();
								}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, null);
	}
	
}
