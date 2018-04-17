package com.hengyushop.demo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BaoMinOKActivity extends BaseActivity {
	private String yth, key, strUrl;
	private DialogProgress progress;
	Button btn_holdr;
	ImageView img_ware;
	private TextView tv_activity_ent,textView1;//
	//	TextView
	public static AQuery mAq;
	String user_name, user_id,login_sign;
	private SharedPreferences spPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baomin_ok);
		progress = new DialogProgress(BaoMinOKActivity.this);
		mAq = new AQuery(BaoMinOKActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		login_sign = spPreferences.getString("login_sign", "");
		//		progress.CreateProgress();
		initdata();
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					break;

				default:
					break;
			}
		};
	};
	private void initdata() {
		try{
			TextView tv_ware_name = (TextView) findViewById(R.id.tv_ware_name);
			TextView tv_dizhi = (TextView) findViewById(R.id.tv_dizhi);
			TextView tv_time = (TextView) findViewById(R.id.tv_time);
			tv_ware_name.setText(getIntent().getStringExtra("hd_title"));
			tv_time.setText(getIntent().getStringExtra("start_time")+"--"+getIntent().getStringExtra("end_time"));
			tv_dizhi.setText(getIntent().getStringExtra("address"));

			ImageView img_ware = (ImageView) findViewById(R.id.img_ware);
			mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + getIntent().getStringExtra("img_url"));


			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			btn_holdr = (Button) findViewById(R.id.btn_holdr);
			textView1 = (TextView) findViewById(R.id.textView1);
			tv_activity_ent = (TextView) findViewById(R.id.tv_activity_ent);
			if (getIntent().getStringExtra("datatype_id").equals("8")) {
				textView1.setText("签到");
				//			tv_activity_ent.setText("签到成功");
				btn_holdr.setVisibility(View.GONE);
				baomingqueren();
			}

			btn_holdr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(BaoMinOKActivity.this, DianZiPiaoActivity.class);
					intent.putExtra("hd_title",getIntent().getStringExtra("hd_title"));
					intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
					intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
					intent.putExtra("address",getIntent().getStringExtra("address"));
					intent.putExtra("trade_no",getIntent().getStringExtra("trade_no"));
					intent.putExtra("id",getIntent().getStringExtra("id"));
					intent.putExtra("real_name",getIntent().getStringExtra("real_name"));
					intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
					startActivity(intent);
					finish();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 签到
	 */
	private void baomingqueren() {
		try {
			//			progress.CreateProgress();
			String trade_no = getIntent().getStringExtra("trade_no");
			System.out.println("trade_no--------------------"+trade_no);
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/signup_award?sales_id="+ user_id + "&sales_name=" + user_name + "" + "&trade_no="
							+ trade_no + "&sign=" + login_sign + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("签到================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									tv_activity_ent.setText("签到成功");
									//							    	   Toast.makeText(BaoMinOKActivity.this, info, 200).show();
								}else {
									progress.CloseProgress();
									tv_activity_ent.setText("签到失败");
									//									Toast.makeText(BaoMinOKActivity.this, info, 200).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1);
							progress.CloseProgress();
							System.out.println("异常================================="+arg1);
							Toast.makeText(BaoMinOKActivity.this, "异常", 200).show();
						}
					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}




}
