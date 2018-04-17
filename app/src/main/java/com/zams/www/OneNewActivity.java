package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.movie.adapter.OneNewAdapter;
import com.hengyushop.movie.adapter.OneNewBean;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneNewActivity extends BaseActivity {
	private ListView one_new;
	private TextView item1, item2, item3, item4;
	private ImageView item0;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					// 获得数据
					final ArrayList<OneNewBean> lists = (ArrayList<OneNewBean>) msg.obj;
					OneNewAdapter adapter = new OneNewAdapter(
							getApplicationContext(), lists, imageLoader);
					one_new.setAdapter(adapter);
					// 关于最新揭晓的列表点击事件
					one_new.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							Intent intent = new Intent(OneNewActivity.this,
									One_JiexiaoActivity.class);
							intent.putExtra("id", lists.get(arg2).getId());
							intent.putExtra("LuckDrawBatchOrderNumber",
									lists.get(arg2).getLuckDrawBatchOrderNumber());
							intent.putExtra("idex", lists.get(arg2)
									.getLuckDrawBatchOrderNumber());
							startActivity(intent);
						}
					});
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
		setContentView(R.layout.one_new_layout);
		one_new = (ListView) findViewById(R.id.one_new);
		item1 = (TextView) findViewById(R.id.item1);
		item2 = (TextView) findViewById(R.id.item2);
		item3 = (TextView) findViewById(R.id.item3);
		item4 = (TextView) findViewById(R.id.item4);
		item0 = (ImageView) findViewById(R.id.item0);
		Map<String, String> params = new HashMap<String, String>();
		// mi/getdata.ashx?act=GetLuckYiYuanGameAllNewResult&yth=test或为空&topNum=1
		params.put("act", "GetLuckYiYuanGameAllNewResult");
		params.put("yth", "");
		params.put("topNum", "");
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							if (status.equals("1")) {
								ArrayList<OneNewBean> lists = new ArrayList<OneNewBean>();
								JSONArray jsonArray = object
										.getJSONArray("items");
								int len = jsonArray.length();
								for (int i = 0; i < len; i++) {
									JSONObject obj = jsonArray.getJSONObject(i);
									OneNewBean bean = new OneNewBean();
									bean.setCode(obj.getString("HengYuCode"));
									bean.setCount(obj
											.getString("AllJuGouCount"));
									bean.setId(obj.getString("ProductItemId"));
									bean.setImg(obj.getString("proFaceImg"));
									bean.setNumber(obj
											.getString("ActualLuckNumber"));
									bean.setProname(obj.getString("proName"));
									bean.setTime(obj.getString("AnnouncedTime"));
									bean.setLuckDrawBatchOrderNumber(obj
											.getString("LuckDrawBatchOrderNumber"));
									bean.setUsername(obj.getString("username"));
									lists.add(bean);
								}
								Message msg = new Message();
								msg.what = 1;
								msg.obj = lists;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});

	}
}
