package com.android.hengyu.post;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

public class WorkDetailActivity extends BaseActivity {
	private TextView work_dis, v1, v2, v3, v4, v5, v6, v7, v8;

	private void init() {
		work_dis = (TextView) findViewById(R.id.work_dis);
		v1 = (TextView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		v3 = (TextView) findViewById(R.id.v3);
		v4 = (TextView) findViewById(R.id.v4);
		v5 = (TextView) findViewById(R.id.v5);
		v6 = (TextView) findViewById(R.id.v6);
		v7 = (TextView) findViewById(R.id.v7);
		v8 = (TextView) findViewById(R.id.v8);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_detail);
		init();
		String id = getIntent().getExtras().getString("id");
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("yth", "sd");
		params.put("act", "ZhaoPinDetail");
		AsyncHttp.post_1(RealmName.REALM_NAME
				+ "/mi/getData.ashx?act=ZhaoPinDetail", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							work_dis.setText(jsonObject
									.getString("JobRequirements"));
							v1.setText(jsonObject.getString("JobTitle"));
							v2.setText(jsonObject.getString("NatureName"));
							v3.setText(jsonObject
									.getString("MonthlySalary_tmp"));
							v4.setText(jsonObject.getString("CompanyName"));
							v5.setText(jsonObject.getString("workAddress"));
							v6.setText(jsonObject
									.getString("WorkExperience_tmp"));
							v7.setText(jsonObject.getString("CompanyWebsite"));
							v8.setText(jsonObject
									.getString("RecruitmentContactTel"));
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				});

	}
}
