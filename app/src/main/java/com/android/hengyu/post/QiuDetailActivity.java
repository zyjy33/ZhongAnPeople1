package com.android.hengyu.post;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QiuDetailActivity extends BaseActivity {
	private TextView v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private TextView v10, v11, v12, v13, v14, v15, v16, v17, v18;

	private void init() {
		v1 = (TextView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		v3 = (TextView) findViewById(R.id.v3);
		v4 = (TextView) findViewById(R.id.v4);
		v5 = (TextView) findViewById(R.id.v5);
		v6 = (TextView) findViewById(R.id.v6);
		v7 = (TextView) findViewById(R.id.v7);
		v8 = (TextView) findViewById(R.id.v8);
		v9 = (TextView) findViewById(R.id.v9);
		v10 = (TextView) findViewById(R.id.v10);
		v11 = (TextView) findViewById(R.id.v11);
		v12 = (TextView) findViewById(R.id.v12);
		v13 = (TextView) findViewById(R.id.v13);
		v14 = (TextView) findViewById(R.id.v14);
		v15 = (TextView) findViewById(R.id.v15);
		v16 = (TextView) findViewById(R.id.v16);
		v17 = (TextView) findViewById(R.id.v17);
		v18 = (TextView) findViewById(R.id.v18);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_qiu_detail);
		init();
		String id = getIntent().getExtras().getString("id");
		Map<String, String> params = new HashMap<String, String>();
		params.put("yth", "");
		params.put("id", id);
		params.put("act", "QiuZhiDetail");
		AsyncHttp.post_1(RealmName.REALM_NAME
						+ "/mi/getData.ashx?act=QiuZhiDetail", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						System.out.println(arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							v1.setText(jsonObject.getString("username"));
							v2.setText(jsonObject.getString("ResumeTitle"));
							v3.setText(jsonObject.getString("ResumeTitle"));
							v4.setText(jsonObject
									.getString("WorkExperience_tmp"));
							v5.setText(jsonObject.getString("Education_tmp"));
							v6.setText(jsonObject
									.getString("MonthlySalary_tmp"));
							v7.setText(jsonObject.getString("JobWantArea"));
							v8.setText(jsonObject.getString("HometownAddress"));
							v9.setText(jsonObject.getString("TelPhone"));
							JSONArray jsonArray1 = jsonObject
									.getJSONArray("WorkExperienceDetail");
							int len1 = jsonArray1.length();
							if (len1 != 0) {
								JSONObject object1 = jsonArray1
										.getJSONObject(0);
								String temp1 = object1
										.getString("StartJobTime");
								String temp2 = object1.getString("EndJobTime");
								String tempTime1 = HttpUtils.getSimpleTime(
										temp1, "yyyy年MM月");
								String tempTime2 = HttpUtils.getSimpleTime(
										temp2, "yyyy年MM月");
								v10.setText(tempTime1 + "-" + tempTime2);
								v11.setText(object1
										.getString("MonthlySalary_tmp"));
								v12.setText(object1.getString("JobName"));
								v13.setText(object1
										.getString("HangYeCategoryName"));
								v14.setText(object1.getString("WorkContent"));

							}

							JSONArray jsonArray2 = jsonObject
									.getJSONArray("EducationExperienceList");
							int len2 = jsonArray2.length();
							if (len2 != 0) {
								JSONObject object2 = jsonArray2
										.getJSONObject(0);
								String temp1 = object2
										.getString("StartSchoolTime");
								String temp2 = object2
										.getString("EndSchoolTime");
								String tempTime1 = HttpUtils.getSimpleTime(
										temp1, "yyyy年MM月");
								String tempTime2 = HttpUtils.getSimpleTime(
										temp2, "yyyy年MM月");
								v15.setText(tempTime1 + "-" + tempTime2);
								v16.setText(object2.getString("SchoolName"));
								v17.setText(object2.getString("Specialty"));
							}
							JSONArray jsonArray3 = jsonObject
									.getJSONArray("ProfessionalSkillDetail");
							int len3 = jsonArray3.length();
							ArrayList<String> s = new ArrayList<String>();
							for (int i = 0; i < len3; i++) {
								JSONObject o = jsonArray3.getJSONObject(i);
								s.add(o.getString("ProfessionalSkillCategoryName"));
							}
							v18.setText(s.toString().replace("[", "")
									.replace("]", ""));
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
				});

	}
}
