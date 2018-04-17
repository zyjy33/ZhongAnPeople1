package com.android.hengyu.post;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostMainActivity extends BaseActivity {
	private LinearLayout buts, buts1, buts2, buts3, but, buts4;
	private int width, height;
	private Button jydt_more1, jydt_more2, jydt_more3;
	private ListView post_list1, post_list2, post_list3;
	private ArrayList<PostStandDo> list1, list2, list3;
	private PostStandAdapter adapter1, adapter2, adapter3;
	private Button post_btn1, post_btn2, post_btn3;

	private void init() {
		but = (LinearLayout) findViewById(R.id.but);
		buts = (LinearLayout) findViewById(R.id.buts);
		buts1 = (LinearLayout) findViewById(R.id.buts1);
		buts2 = (LinearLayout) findViewById(R.id.buts2);
		buts3 = (LinearLayout) findViewById(R.id.buts3);
		buts4 = (LinearLayout) findViewById(R.id.buts4);
		jydt_more1 = (Button) findViewById(R.id.jydt_more1);
		jydt_more1.setOnClickListener(clickListener);
		jydt_more2 = (Button) findViewById(R.id.jydt_more2);
		jydt_more2.setOnClickListener(clickListener);

		jydt_more3 = (Button) findViewById(R.id.jydt_more3);
		jydt_more3.setOnClickListener(clickListener);
		post_btn1 = (Button) findViewById(R.id.post_btn1);
		post_btn2 = (Button) findViewById(R.id.post_btn2);
		post_btn3 = (Button) findViewById(R.id.post_btn3);
		post_btn1.setOnClickListener(clickListener);
		post_btn2.setOnClickListener(clickListener);
		post_btn3.setOnClickListener(clickListener);
		post_list1 = (ListView) findViewById(R.id.post_list1);
		post_list2 = (ListView) findViewById(R.id.post_list2);
		post_list3 = (ListView) findViewById(R.id.post_list3);
		connect();
	}

	/**
	 * 建立链接
	 */
	private void connect() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "CompanyEmploymentNews");
		params.put("yth", "admin");
		AsyncHttp.post_1(RealmName.REALM_NAME
						+ "/mi/getData.ashx",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						System.out.println(arg1);
						parseResult(arg1);
					}
				} );
	}

	/**
	 * 解析数据
	 *
	 * @param result
	 */
	private void parseResult(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			int len = jsonArray.length();
			list1 = new ArrayList<PostStandDo>();
			list2 = new ArrayList<PostStandDo>();
			list3 = new ArrayList<PostStandDo>();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				PostStandDo standDo = new PostStandDo();
				standDo.setTitle(object.getString("NewsTitle"));
				standDo.setImg(object.getString("NewsTitleImgURL"));
				standDo.setTime(object.getString("CreateTime"));
				standDo.setInfo(object.getString("NewsInfo"));
				int type = Integer.parseInt(object
						.getString("EmploymentNewsTypeId"));
				switch (type) {
					case 1:
						list1.add(standDo);
						break;
					case 2:
						list2.add(standDo);
						break;
					case 3:
						list3.add(standDo);
						break;
					default:
						break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);

	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					LayoutParams params = new LayoutParams(width, width);
					buts1.setLayoutParams(params);
					buts2.setLayoutParams(params);
					buts3.setLayoutParams(params);
					break;
				case 1:
					adapter1 = new PostStandAdapter(list1, getApplicationContext());
					adapter2 = new PostStandAdapter(list2, getApplicationContext());
					adapter3 = new PostStandAdapter(list3, getApplicationContext());
					post_list1.setAdapter(adapter1);
					post_list2.setAdapter(adapter2);
					post_list3.setAdapter(adapter3);
					break;

				default:
					break;
			}
		};
	};
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.jydt_more1:
					Intent intent = new Intent(PostMainActivity.this,
							PostJYDT_Activity.class);
					intent.putExtra("list", list1);
					startActivity(intent);
					break;
				case R.id.jydt_more2:
					Intent intent2 = new Intent(PostMainActivity.this,
							PostJYDT_Activity.class);
					intent2.putExtra("list", list2);
					startActivity(intent2);
					break;
				case R.id.jydt_more3:
					Intent intent3 = new Intent(PostMainActivity.this,
							PostJYDT_Activity.class);
					intent3.putExtra("list", list3);
					startActivity(intent3);
					break;
				case R.id.post_btn1:
					Intent intent4 = new Intent(PostMainActivity.this,
							PostWorkIndexActivity.class);
					startActivity(intent4);
					break;
				case R.id.post_btn2:
					Intent intent5 = new Intent(PostMainActivity.this,
							PostQiuActivity.class);
					startActivity(intent5);
					break;
				case R.id.post_btn3:
					Intent intent6 = new Intent(PostMainActivity.this,
							PostCYPX_Activity.class);
					intent6.putExtra("list", list1);
					startActivity(intent6);
					break;
				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);
		init();
		buts.post(new Runnable() {
			@Override
			public void run() {
				width = buts.getMeasuredWidth() / 3;
				handler.sendEmptyMessage(0);
			}
		});
	}
}
