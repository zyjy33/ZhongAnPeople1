package com.hengyushop.demo.service;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.DialogProgress;
import com.zams.www.R;

/**
 * 平台热线
 *
 * @author
 *
 */
public class PlatformhotlineActivity extends Activity implements OnClickListener {
	private Intent intent;
	public Activity mContext;
	String user_name, user_id, nichen, order_no;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign, amount;
	public static String give_pension, article_id;
	TextView tv_dianhua1,tv_dianhua2;
	ImageView iv_guanxi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pingtai_rexian);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(PlatformhotlineActivity.this);
		initUI();
	}

	protected void initUI() {
		try {
			tv_dianhua1 = (TextView) findViewById(R.id.tv_dianhua1);
			tv_dianhua2 = (TextView) findViewById(R.id.tv_dianhua2);
			tv_dianhua1.setOnClickListener(this);
			tv_dianhua2.setOnClickListener(this);
			iv_guanxi = (ImageView) findViewById(R.id.iv_guanxi);
			iv_guanxi.setBackgroundResource(R.drawable.limitbuy_esoterica_close);
			iv_guanxi.setOnClickListener(this);
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
			case R.id.iv_guanxi:// 取消
				iv_guanxi.setVisibility(View.INVISIBLE);
				finish();
				break;
			case R.id.tv_dianhua1://
				Intent intent = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
				intent.setData(Uri.parse("tel:" + "400-606-1201"));
				startActivity(intent);
				finish();
				break;
			case R.id.tv_dianhua2://
				Intent intent1 = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
				intent1.setData(Uri.parse("tel:" + "010-62575060"));
				startActivity(intent1);
				finish();
				break;
			default:
				break;
		}
	}

}