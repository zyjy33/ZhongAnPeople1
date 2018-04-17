package com.zams.www;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.movie.adapter.OneBuyBean;
import com.hengyushop.movie.adapter.OneBuyListAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneBuyListActivity extends BaseActivity {
	private OneBuyListAdapter buyAdapter;
	private ListView one_views;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_buy_list_activity);
		init();
	};

	private ArrayList<OneBuyBean> lists;

	private void init() {
		one_views = (ListView) findViewById(R.id.one_views);
		lists = new ArrayList<OneBuyBean>();
		buyAdapter = new OneBuyListAdapter(OneBuyListActivity.this, lists,
				imageLoader);
		one_views.setAdapter(buyAdapter);
		onload();
		one_views.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				Intent intent = new Intent(OneBuyListActivity.this,
						OneBuyInformation.class);

				intent.putExtra("id", lists.get(arg2).getId());
				startActivity(intent);
			}
		});
	}

	/*
	 * 获得数据
	 */
	private void onload() {
		// /mi/getdata.ashx?act=LuckYiYuanGameItems&yth=admin
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "LuckYiYuanGameItems");
		params.put("yth", "admin");
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
							for (int i = 0; i < len; i++) {
								OneBuyBean bean = new OneBuyBean();
								JSONObject object = jsonArray.getJSONObject(i);
								bean.setId(object.getString("ProductItemId"));
								bean.setImg(object.getString("proFaceImg"));
								bean.setJoinNum(object
										.getString("HasJoinedNum"));
								bean.setMarket(object.getString("marketPrice"));
								bean.setName(object.getString("proName"));
								// bean.setNum(num)
								bean.setNum(object.getString("NeedGameUserNum"));
								bean.setPrice(object.getString("marketPrice"));
								bean.setRecommend(object
										.getString("IsRecommend"));
								bean.setTime(object.getString("BeginTime"));
								lists.add(bean);
							}
							handler.sendEmptyMessage(0);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					buyAdapter.putData(lists);
					break;
				default:
					break;
			}
		};
	};
}
