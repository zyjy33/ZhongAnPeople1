package com.zams.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.adapter.ShouYiAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class ShouYiActivity extends BaseActivity {
	private ListView shouyi;
	private TextView item0, item1;
	String type = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shouyi_paihang);
		shouyi = (ListView) findViewById(R.id.shouyi);
		item0 = (TextView) findViewById(R.id.item0);
		item1 = (TextView) findViewById(R.id.item1);
		item0.setBackgroundColor(getResources().getColor(R.color.content_text));
		item1.setBackgroundColor(getResources().getColor(R.color.white));
		item0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				item0.setBackgroundColor(getResources().getColor(
						R.color.content_text));
				item1.setBackgroundColor(getResources().getColor(R.color.white));
				type = "1";
				load();
			}
		});
		item1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				item1.setBackgroundColor(getResources().getColor(
						R.color.content_text));
				item0.setBackgroundColor(getResources().getColor(R.color.white));
				type = "2";
				load();
			}
		});

		load();

	}

	private void load() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetPaiHangBangUsers");
		params.put("yth", "test");
		params.put("number", "10");
		params.put("requestType", type);
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							ArrayList<ContentValues> lists = new ArrayList<ContentValues>();
							ContentValues values;
							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								values = new ContentValues();
								values.put("name", object.getString("username"));
								values.put("url",
										object.getString("avatarimageURL"));
								values.put("count",
										object.getString("sumCount"));
								values.put("price", object.getString("shouRu"));
								lists.add(values);
							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = lists;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				ArrayList<ContentValues> lists = (ArrayList<ContentValues>) msg.obj;
				ShouYiAdapter adapter = new ShouYiAdapter(
						getApplicationContext(), lists, imageLoader, type);
				shouyi.setAdapter(adapter);
				break;

			default:
				break;
			}
		};
	};
}
