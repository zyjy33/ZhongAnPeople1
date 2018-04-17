package com.zams.www;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.movie.adapter.OneAllAdapter;
import com.hengyushop.movie.adapter.OneAllBean;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneAllActivity extends BaseActivity {
	private OneAllAdapter adapter;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					ArrayList<OneAllBean> lists = (ArrayList<OneAllBean>) msg.obj;
					adapter.putData(lists);
					break;

				default:
					break;
			}
		};
	};
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_all_layout);
		ArrayList<OneAllBean> lists = new ArrayList<OneAllBean>();
		adapter = new OneAllAdapter(getApplicationContext(), lists, imageLoader);
		listview = (ListView) findViewById(R.id.lists);
		listview.setAdapter(adapter);
		Bundle bundle = getIntent().getExtras();
		if (bundle.containsKey("item_id")) {
			Map<String, String> params = new HashMap<String, String>();
			// mi/getdata.ashx?act=GetLuckYiYuanJuGouRecords&yth=test或为空&ProductItemId=1&LuckDrawBatchOrderNumber=1
			params.put("act", "GetLuckYiYuanJuGouRecords");
			params.put("yth", "test");
			params.put("productItemId",
					getIntent().getExtras().getString("item_id"));
			params.put("luckDrawBatchOrderNumber", getIntent().getExtras()
					.getString("LuckDrawBatchOrderNumber"));

			AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								ArrayList<OneAllBean> lists = new ArrayList<OneAllBean>();
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								if (TextUtils.equals(status, "1")) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("items");
									int len = jsonArray.length();
									for (int i = 0; i < len; i++) {
										JSONObject object = jsonArray
												.getJSONObject(i);
										OneAllBean bean = new OneAllBean();
										bean.setTime(object
												.getString("LuckDrawTime"));
										bean.setName(object
												.getString("username"));
										bean.setCode(object
												.getString("HengYuCode"));
										bean.setIp(object
												.getString("LuckIpAddress"));
										bean.setId(object.getString("YiYuanID"));
										bean.setCount(object
												.getString("LuckBuyCount"));
										lists.add(bean);
									}
									Message msg = new Message();
									msg.what = 1;
									msg.obj = lists;
									handler.sendMessage(msg);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}
}
