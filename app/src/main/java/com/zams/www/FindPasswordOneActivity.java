package com.zams.www;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindPasswordOneActivity extends BaseActivity {
	private EditText et_user_phone,et_user_yz,et_user_pwd,et_useryanshi_pwd;
	private Button get_yz,btn_cz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
		setContentView(R.layout.find_password);
		init();
	}
	private void init(){
		et_user_phone = (EditText) findViewById(R.id.et_user_phone);
		et_user_yz = (EditText) findViewById(R.id.et_user_yz);
		et_user_pwd = (EditText) findViewById(R.id.et_user_pwd);
		et_useryanshi_pwd = (EditText) findViewById(R.id.et_useryanshi_pwd);
		//et_user_pwd_again = (EditText) findViewById(R.id.et_user_pwd_again);
		get_yz = (Button) findViewById(R.id.get_yz);
		btn_cz = (Button) findViewById(R.id.btn_cz);
		get_yz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String phone = et_user_phone.getText().toString();
				AsyncHttp.get("http://www.hengyushop.com/mi/SmsAndMms.ashx?mobile="+phone+"&companyName=云商聚&frontContents=您正在重置密码，请在15分钟内按页面提示提交验证码，切勿将验证码泄露于他人。&RequestType=", new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), 200).show();

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, getApplicationContext());
			}
		});
		btn_cz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String phone = et_user_phone.getText().toString();
				String yz = et_user_yz.getText().toString();
				// TODO Auto-generated method stub
				String pass1 = et_user_pwd.getText().toString();//输入新密码
				String ys_pass = et_useryanshi_pwd.getText().toString();//输入原始密码

				//				if(pass1.length()<8){
				//					Toast.makeText(getApplicationContext(), "密码输入有误", 200).show();
				//				}else
				if(pass1.equals("")){
					Toast.makeText(getApplicationContext(), "密码为空", 200).show();
				}else if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "手机号码为空", 200).show();
				}else if(ys_pass.equals("")){
					Toast.makeText(getApplicationContext(), "原始密码为空", 200).show();
				}else{
					System.out.println(RealmName.REALM_NAME_LL+"/user_update_password?" +
							"username="+phone+"&password="+pass1+"&type=oldpassword&code="+pass1);
					Map<String, String> params= new HashMap<String, String>();
					params.put("username", phone);
					params.put("password", pass1);
					params.put("type", "oldpassword");
					params.put("code", pass1);
					//					params.put("act", "SetPasswordOnValidatecode");
					//					params.put("phone", phone);
					//					params.put("newPwd", pass1);
					//					params.put("code", "");
					//					params.put("regValidatecode", yz);
					System.out.println("11=================");
					//					AsyncHttp.post_1(RealmName.REALM_NAME_LL+"/user_update_password",params, new AsyncHttpResponseHandler(){
					AsyncHttp.get(RealmName.REALM_NAME_LL+"/user_update_password?username="+phone+"&password="+pass1+"&type=oldpassword&code="+ys_pass+"", new AsyncHttpResponseHandler() {
						public void onSuccess(int arg0, String arg1) {
							///mi/getdata.ashx?act=SetPasswordOnValidatecode&phone=15889698754&newPwd=新密码&yth=空值&regValidatecode=验证码
							System.out.println("================="+arg1);
							try {
								JSONObject jsonObject  = new JSONObject(arg1);
								String info = jsonObject.getString("info");
								//								Toast.makeText(getApplicationContext(), "修改成功", 200).show();
								Toast.makeText(getApplicationContext(), info, 200).show();
								//								if(jsonObject.getInt("status")==1){
								//									AppManager.getAppManager().finishActivity();
								//								}
								finish();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}, null );
				}
			}
		});

		//		mi/SmsAndMms.ashx?mobile=15889698754&companyName=微行掌&frontContents=前缀内容&RequestType=1


	}


}
