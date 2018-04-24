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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.activity.DianZiPiaoActivity;
import com.hengyushop.demo.activity.TishiQianDaoOKActivity;
import com.hengyushop.demo.activity.ZhongAnMinShenXqActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.OrderBean;
import com.hengyushop.entity.XiangqingData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

/**
 * 报名签到
 *
 * @author
 *
 */
public class TishiBaoMinQianDaoActivity extends Activity implements OnClickListener {
	private TextView btnConfirm, tv_conent, tv_name,tv_nianlin,tv_group_name;//
	private TextView btnCancle, tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	LinearLayout ll_qiandao_ok;
	String login_sign, amount;
	String user_name, user_id, headimgurl, access_token, unionid, area,
			real_name,birthday,sex,datetime,mobile,nianlin,trade_no,group_name;
	int shuzi = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_baomin_qiandao);
		try {
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			//		real_name = spPreferences.getString("real_name", "");
			//		mobile = spPreferences.getString("mobile", "");
			group_name = spPreferences.getString("group_name", "");
			user_id = spPreferences.getString("user_id", "");
			login_sign = spPreferences.getString("login_sign", "");
			//		birthday = spPreferences.getString("birthday", "").substring(0, 4);
			//		sex = spPreferences.getString("sex", "");
			datetime = spPreferences.getString("datetime", "").substring(0, 4);
			progress = new DialogProgress(TishiBaoMinQianDaoActivity.this);
			//		int birthday1 = Integer.valueOf(birthday);
			//		int datetime2 = Integer.valueOf(datetime);
			//		System.out.println("birthday1-------------------"+birthday1);
			//		System.out.println("datetime2-------------------"+datetime2);
			//		int age = datetime2 - birthday1;
			//		System.out.println("age/////////////////////"+age);
			//		nianlin = String.valueOf(age);
			//		System.out.println("nianlin////////////////"+nianlin);
			initUI();
			load_list();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 报名签到
	 */
	private void load_list() {
		progress.CreateProgress();
		String trade_no1 = getIntent().getStringExtra("bianma");
		System.out.println("=========trade_no1============" + trade_no1);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/signup_award?sales_id="
						+ user_id + "&sales_name=" + user_name + "" + "&trade_no="
						+ trade_no1 + "&sign=" + login_sign + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("=========数据接口============" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONObject obj = object.getJSONObject("data");
								//								 JSONArray jsonArray = object.getJSONArray("data");
								//								 for (int i = 0; i < jsonArray.length(); i++) {
								//								 JSONObject obj= jsonArray.getJSONObject(i);
								trade_no = obj.getString("trade_no");
								real_name = obj.getString("real_name");
								mobile = obj.getString("mobile");
								sex = obj.getString("sex");
								birthday = obj.getString("birthday");
								//								 birthday = obj.getString("birthday").substring(0, 4);
								nianlin = obj.getString("age");
								String group_name = obj.getString("group_name");
								//								 }
								//								int birthday1 = Integer.valueOf(birthday);
								//								int datetime2 = Integer.valueOf(datetime);
								//								System.out.println("birthday1-------------------"+birthday1);
								//								System.out.println("datetime2-------------------"+datetime2);
								//								nianlin = String.valueOf(datetime2 - birthday1);
								//								System.out.println("nianlin////////////////"+nianlin);

								ll_qiandao_ok.setVisibility(View.VISIBLE);
								tv_name.setText(real_name + "(" + mobile+ ")");
								tv_nianlin.setText(sex +"  "+nianlin+"岁");
								tv_group_name.setText(group_name);
								progress.CloseProgress();
							} else {
								progress.CloseProgress();
								tv_conent.setVisibility(View.VISIBLE);
								tv_conent.setText(info);
							}
						} catch (Exception e) {

							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {

						progress.CloseProgress();
						System.out.println("==========================访问接口失败！");
						System.out.println("==========================" + arg1);
						Toast.makeText(TishiBaoMinQianDaoActivity.this, "异常", 200)
								.show();
						super.onFailure(arg0, arg1);
					}

				}, TishiBaoMinQianDaoActivity.this);
	}

	/**
	 * 报名确认
	 */
	private void baomingqueren() {
		try {
			//			progress.CreateProgress();
			//			String id = getIntent().getStringExtra("id");
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/signup_award_confirm?trade_no="+trade_no+"&express_status="+shuzi+"",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("报名确认================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									//							    	   Toast.makeText(TishiBaoMinQianDaoActivity.this, info, 200).show();
									finish();
								}else {
									progress.CloseProgress();
									Toast.makeText(TishiBaoMinQianDaoActivity.this, info, 200).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							super.onFailure(arg0, arg1);
							progress.CloseProgress();
							System.out.println("异常================================="+arg1);
							//						Toast.makeText(DianZiPiaoActivity.this, "网络超时异常", 200).show();
						}
					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void initUI() {
		try {

			tv_conent = (TextView) findViewById(R.id.tv_conent);
			btnConfirm = (TextView) findViewById(R.id.btnConfirm);
			tv_nianlin = (TextView) findViewById(R.id.tv_nianlin);
			tv_group_name = (TextView) findViewById(R.id.tv_group_name);
			ll_qiandao_ok = (LinearLayout) findViewById(R.id.ll_qiandao_ok);
			btnConfirm.setOnClickListener(this);//
			btnCancle =(TextView) findViewById(R.id.btnCancle);
			btnCancle.setOnClickListener(this);//
			// tv_conent.setText(getIntent().getStringExtra("bianma"));
			tv_name = (TextView) findViewById(R.id.tv_name);//

			handler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
						case 8:

					}
				}
			};
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {

		intent = new Intent();
		switch (v.getId()) {
			case R.id.btnConfirm:// 确认
				shuzi = 2;
				baomingqueren();
				//			finish();
				String qiandao1 = getIntent().getStringExtra("qiandao");
				System.out.println("qiandao1================================="+qiandao1);
				if (qiandao1.equals("1")) {//输入手机号查询判断为1不跳转到扫一扫界面

				}else {
					Intent intent = new Intent(TishiBaoMinQianDaoActivity.this,CaptureActivity.class);
					intent.putExtra("sp_sys", getIntent().getStringExtra("sp_sys"));
					startActivity(intent);
				}
				break;
			case R.id.btnCancle://取消
				shuzi = 1;
				baomingqueren();
				//		    finish();
				String qiandao2 = getIntent().getStringExtra("qiandao");
				System.out.println("qiandao2================================="+qiandao2);
				if (qiandao2.equals("1")) {//输入手机号查询判断为1不跳转到扫一扫界面

				}else {
					Intent intent1 = new Intent(TishiBaoMinQianDaoActivity.this,CaptureActivity.class);
					intent1.putExtra("sp_sys", getIntent().getStringExtra("sp_sys"));
					startActivity(intent1);
				}
				break;


			default:
				break;
		}
	}

}