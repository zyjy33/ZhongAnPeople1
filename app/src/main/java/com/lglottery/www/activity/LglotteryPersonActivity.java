package com.lglottery.www.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zams.www.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LglotteryPersonActivity extends BaseActivity {
	private ImageView lglottery_person_ico;
	private DisplayImageOptions options;
	private Button lglottery_person_log;
	private SharedUtils sharedUtils, personUtil;
	private TextView lglottery_person_title, lglottery_person_dai,
			lglottery_person_dou, lglottery_person_xian;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					lglottery_person_dai.setText(getString(
							R.string.person_view_dai,
							personUtil.getValue("shopPassTicket")));
					lglottery_person_dou.setText(getString(
							R.string.person_view_dou,
							personUtil.getValue("credits")));
					lglottery_person_title.setText(personUtil
							.getStringValue("username"));
					lglottery_person_xian.setText(getString(
							R.string.person_view_xian,
							personUtil.getValue("PassTicketBalance")));
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_person_activity);
		sharedUtils = new SharedUtils(getApplicationContext(),
				Config.LOGIN_STATUS);
		personUtil = new SharedUtils(getApplicationContext(),
				Config.PERSONAL_STATUS);
		initOptions();
		init();
	}

	private void initOptions() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.color.no_color)
				.showImageForEmptyUri(R.drawable.lglottery_login_ico)
				.showImageOnFail(R.drawable.lglottery_login_ico)
				.cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(200)).build();
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.lglottery_person_log:
					Intent logIntent = new Intent(LglotteryPersonActivity.this,
							LglotteryLogActivity.class);
					startActivity(logIntent);
					break;

				default:
					break;
			}
		}
	};

	protected void onResume() {
		super.onResume();
		if (sharedUtils.isHere("key")) {
			SharedUtils in = new SharedUtils(getApplicationContext(), "shouyi");
			if (in.isHere("avatarimageURL")) {
				imageLoader.displayImage(
						RealmName.REALM_NAME
								+ in.getStringValue("avatarimageURL"),
						lglottery_person_ico);
			} else {
				imageLoader.displayImage("drawable://"
								+ R.drawable.lglottery_login_ico, lglottery_person_ico,
						options);
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("key", sharedUtils.getStringValue("key"));
			params.put("yth", sharedUtils.getStringValue("yth"));
			params.put("act", "myInfo");
			AsyncHttp.post_1(U.LOTTERY_PERSONAL_INFO, params,
					new AsyncHttpResponseHandler() {
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

									handler.sendEmptyMessage(0);

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

						}
					});
		} else {
			imageLoader.displayImage("drawable://"
							+ R.drawable.lglottery_login_ico, lglottery_person_ico,
					options);
		}
	};

	private void init() {
		lglottery_person_log = (Button) findViewById(R.id.lglottery_person_log);
		lglottery_person_ico = (ImageView) findViewById(R.id.lglottery_person_ico);
		imageLoader.displayImage("drawable://" + R.drawable.no_image,
				lglottery_person_ico, options);
		lglottery_person_title = (TextView) findViewById(R.id.lglottery_person_title);
		lglottery_person_dai = (TextView) findViewById(R.id.lglottery_person_dai);
		lglottery_person_dou = (TextView) findViewById(R.id.lglottery_person_dou);
		lglottery_person_xian = (TextView) findViewById(R.id.lglottery_person_xian);
		lglottery_person_log.setOnClickListener(clickListener);
	}
}
