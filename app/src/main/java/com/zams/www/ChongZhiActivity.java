package com.zams.www;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.common.Config;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class ChongZhiActivity extends BaseActivity {
	private int screenWidth = 0;
	private Button chongzhi_submit;
	private ImageView chongzhi_ico;
	private TextView yuee;
	private SharedUtils in;
	private WareDao wareDao;
	private String yth;
	private String key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chongzhi);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		chongzhi_ico = (ImageView) findViewById(R.id.chongzhi_ico);
		yuee = (TextView) findViewById(R.id.yuee);
		in = new SharedUtils(getApplicationContext(), "shouyi");
		process_ico(chongzhi_ico, 294, 452);
		chongzhi_submit = (Button) findViewById(R.id.chongzhi_submit);
		chongzhi_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChongZhiActivity.this,
						YuActivity.class);
				startActivity(intent);

			}
		});
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		findViewById(R.id.chongzhi_t).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "暂不支持提现", 200).show();
			}
		});

	}

	private void process_ico(View view, int h, int w) {
		double mix_f = screenWidth * (double) h / w;
		FrameLayout.LayoutParams paramsf = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) mix_f);
		view.setLayoutParams(paramsf);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadInfo();
		yuee.setText("￥" + in.getStringValue("PassTicketBalance"));
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					yuee.setText("￥" + in.getStringValue("PassTicketBalance"));
					break;

				default:
					break;
			}
		};
	};

	private void loadInfo() {

		String str2 = RealmName.REALM_NAME + "/mi/getdata.ashx";
		Map<String, String> params0 = new HashMap<String, String>();
		params0.put("act", "myInfo");
		params0.put("key", key);
		params0.put("yth", yth);
		AsyncHttp.post_1(str2, params0, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject object2 = new JSONObject(arg1);
					/**
					 *
					 */
					if (object2.getInt("status") == -1) {
						// 用户不存在
						int isLogin = 0;
						int index = 0;
						UserRegisterData data = new UserRegisterData();
						data.setIsLogin(isLogin);
						wareDao.updateQuitIsLogin(data);
						wareDao.deleteAllShopCart();
						wareDao.deleteAllUserInformation();
						Intent intent2 = new Intent(ChongZhiActivity.this,
								UserLoginActivity.class);
						intent2.putExtra("login", index);
						startActivity(intent2);
					} else {
						UserRegisterData data2 = new UserRegisterData();
						data2.hengyuCode = object2.getString("HengYuCode");
						data2.userName = object2.getString("username");
						data2.PassTicketBalance = object2
								.getString("PassTicketBalance");
						data2.shopPassTicket = object2
								.getString("shopPassTicket");
						data2.avatarimageURL = object2
								.getString("avatarimageURL");
						data2.credits = object2.getString("credits");
						data2.daijifen = object2.getString("JuHongBao");
						SharedUtils in = new SharedUtils(ChongZhiActivity.this,
								"shouyi");
						in.setStringValue("HengYuCode",
								object2.getString("HengYuCode"));
						in.setStringValue("IsVipPrivilege",
								object2.getString("IsVipPrivilege"));
						in.setStringValue("yesterdayIncome",
								object2.getString("yesterdayIncome"));
						in.setStringValue("totalIncome",
								object2.getString("totalIncome"));
						in.setStringValue("grouptitle",
								object2.getString("grouptitle"));
						in.setStringValue("WaitActivatedCredits",
								object2.getString("WaitActivatedCredits"));
						in.setStringValue("JuHongBao",
								object2.getString("JuHongBao"));
						in.setStringValue("ReserveCredits",
								object2.getString("ReserveCredits"));
						in.setStringValue("shopPassTicket",
								object2.getString("shopPassTicket"));// 储购宝余额
						in.setStringValue("PassTicketBalance",
								object2.getString("PassTicketBalance"));
						in.setStringValue("avatarimageURL",
								object2.getString("avatarimageURL"));
						in.setStringValue("ChannelTitle",
								object2.getString("ChannelTitle"));
						in.setStringValue("ChannelUserID",
								object2.getString("ChannelUserID"));
						Message msg = new Message();
						msg.what = 1;
						msg.obj = data2;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);

			}
		});
	}

}
