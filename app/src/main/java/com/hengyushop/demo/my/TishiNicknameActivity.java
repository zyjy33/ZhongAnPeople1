package com.hengyushop.demo.my;

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
import com.hengyushop.demo.home.YangLaoChongZhiActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.UserSenJiBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.PersonCenterActivity;
import com.zams.www.R;
/**
 * 修改性别
 * @author
 *
 */
public class TishiNicknameActivity extends Activity implements OnClickListener{
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	String user_name, user_id,nichen,order_no;
	private EditText zhidupess;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	public static String yue_zhuangtai;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_gender);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(TishiNicknameActivity.this);
		initUI();
	}



	protected void initUI() {
		zhidupess = (EditText) findViewById(R.id.et_user_pwd);
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);//
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
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {


		intent = new Intent();
		switch (v.getId()) {
			case R.id.btnConfirm://取消
				finish();
				break;
			case R.id.btnCancle://
				nichen = zhidupess.getText().toString().trim();
				System.out.println("nichen-------------"+nichen);
				if (nichen.equals("")) {
					Toast.makeText(TishiNicknameActivity.this, "请输入修改的昵称", 200).show();
				}else{
					userloginqm();
				}
				break;

			default:
				break;
		}
	}

	/**
	 * 获取登录签名
	 * @param order_no
	 */
	private void userloginqm() {
		String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						UserRegisterllData data = new UserRegisterllData();
						data.login_sign = obj.getString("login_sign");
						login_sign = data.login_sign;
						System.out.println("======login_sign============="+login_sign);
						loadusersex(login_sign);
					}else{
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			};
		}, null);
	}
	/**
	 * 修改昵称
	 * @param login_sign
	 * @param payment_id
	 */
	private void loadusersex(String login_sign) {
		try {
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/user_update_field?user_id="+user_id+"&user_name="+user_name+"" +
							"&field=nick_name&value="+nichen+"&sign="+login_sign+"",
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
									//									Toast.makeText(TishiNicknameActivity.this, info, 200).show();
									finish();
								} else {
									progress.CloseProgress();
									Toast.makeText(TishiNicknameActivity.this, info, 200).show();
									finish();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}