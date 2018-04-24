package com.hengyushop.demo.home;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
/**
 * 抽奖结果
 * @author
 *
 */
public class ZyZTiShiActivity extends Activity implements OnClickListener{
	private TextView btnConfirm;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences_qq;
	String login_sign,amount;
	public static String yue_zhuangtai;
	String user_name,user_id,headimgurl,access_token,sex,unionid,area,real_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_tip_text);
		initUI();
	}


	protected void initUI() {
		Button lglottery_pop_closed = (Button) findViewById(R.id.lglottery_pop_closed);
		//		TextView start_f_name = (TextView) findViewById(R.id.start_f_name);
		//		start_f_name.setText(circleBean.getMsg1());
		TextView start_f_name0 = (TextView) findViewById(R.id.start_f_name0);
		start_f_name0.setText(getIntent().getStringExtra("drawn"));
		Button ji_xu = (Button) findViewById(R.id.ji_xu);
		Button ji_ls = (Button) findViewById(R.id.ji_ls);
		String quxiao = getIntent().getStringExtra("quxiao");
		if (quxiao != null) {
			ji_xu.setText(quxiao);
		}
		lglottery_pop_closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
		ji_ls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//				Intent intent = new Intent(ZyZTiShiActivity.this,JulsActivity.class);
				//				startActivity(intent);
				getjiangxiang();
				finish();
			}
		});
		ji_xu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 8:

				}
			}
		};
	}


	/**
	 * 保存幸运奖幸奖项(转一转)
	 */
	private void getjiangxiang() {
		SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		String user_id = spPreferences.getString("user_id", "");
		String user_name = spPreferences.getString("user", "");
		String login_sign = spPreferences.getString("login_sign", "");
		String id = getIntent().getStringExtra("id");

		String strUrlone = RealmName.REALM_NAME_LL +
				"/add_article_activity_award?user_id="+user_id+"&user_name="+user_name+"&award_id="+id+"&sign="+login_sign+"";
		System.out.println("======输出抽奖幸奖项============="+strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======输出抽奖幸奖项============="+arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					if (status.equals("y")) {
						Toast.makeText(ZyZTiShiActivity.this, info, 200).show();
						//							JSONObject obct = object.getJSONObject("data");
						//							    id = obct.getString("id");
						//								String title = obct.getString("title");
						//								String drawn = obct.getString("drawn");
					}else{
						Toast.makeText(ZyZTiShiActivity.this, info, 200).show();
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			};

			@Override
			public void onFailure(Throwable arg0, String arg1) {

				super.onFailure(arg0, arg1);
				System.out.println("======访问接口失败============="+arg1);
				//					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
			}
		}, ZyZTiShiActivity.this);

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
				finish();
				break;

			default:
				break;
		}
	}



}