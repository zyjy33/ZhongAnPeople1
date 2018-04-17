package com.hengyushop.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.hengyu.web.DialogProgress;
import com.zams.www.R;

/**
 * 提示已签到
 *
 * @author
 *
 */
public class TishiQianDaoOKActivity extends Activity implements OnClickListener {
	private TextView btnConfirm;//
	private TextView btnCancle, tv_name;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign, amount;
	String user_name, user_id, headimgurl, access_token, unionid, area,
			real_name,birthday,sex,datetime,mobile,nianlin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_qiandao_ok);
		try {
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			real_name = spPreferences.getString("real_name", "");
			mobile = spPreferences.getString("mobile", "");
			//		birthday = spPreferences.getString("birthday", "").substring(0, 4);
			//		sex = spPreferences.getString("sex", "");
			//		datetime = spPreferences.getString("datetime", "").substring(0, 4);
			//		int birthday1 = Integer.valueOf(birthday);
			//		int datetime2 = Integer.valueOf(datetime);
			//		System.out.println("birthday1-------------------"+birthday1);
			//		System.out.println("datetime2-------------------"+datetime2);
			//		int age = datetime2 - birthday1;
			//		System.out.println("age/////////////////////"+age);
			//		nianlin = String.valueOf(age);
			//		System.out.println("nianlin////////////////"+nianlin);

			// user_id = spPreferences.getString("user_id", "");
			// progress = new DialogProgress(TishiQianDaoOKActivity.this);
			initUI();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	protected void initUI() {
		btnConfirm = (TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle = (TextView) findViewById(R.id.btnCancle);//
		btnCancle.setOnClickListener(this);//
		TextView tv_name = (TextView) findViewById(R.id.tv_name);//
		TextView tv_nianlin = (TextView) findViewById(R.id.tv_nianlin);
		//		tv_name.setText(real_name + "(" + mobile + ")");
		//		tv_nianlin.setText(sex +"  "+nianlin+"岁");
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
		// TODO Auto-generated method stub

		intent = new Intent();
		switch (v.getId()) {
			case R.id.btnConfirm:// 取消
				finish();
				break;
			case R.id.btnCancle://
				Intent intent = new Intent(TishiQianDaoOKActivity.this,
						DianZiPiaoActivity.class);
				intent.putExtra("hd_title", getIntent().getStringExtra("title"));
				intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
				intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
				intent.putExtra("address", getIntent().getStringExtra("address"));
				intent.putExtra("trade_no", getIntent().getStringExtra("trade_no"));
				intent.putExtra("id", getIntent().getStringExtra("id"));
				startActivity(intent);
				finish();
				break;

			default:
				break;
		}
	}

}