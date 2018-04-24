package com.zams.www;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.domain.Lglottery_Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class JulsActivity extends BaseActivity {
	private ArrayList<Lglottery_Log> lists;
	private ListView lglottery_log_list;
	private JuLogAdapter lglotteryLogAdapter;
	private SharedUtils sharedUtils;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					lists = (ArrayList<Lglottery_Log>) msg.obj;
					lglotteryLogAdapter.putList(lists);
					break;

				default:
					break;
			}
		};
	};

	/**
	 *
	 */
	private void init() {
		lists = new ArrayList<Lglottery_Log>();
		lglottery_log_list = (ListView) findViewById(R.id.lglottery_log_list);
		lglotteryLogAdapter = new JuLogAdapter(getApplicationContext(), lists,
				imageLoader);
		lglottery_log_list.setAdapter(lglotteryLogAdapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_log_activity);
		init();
		connect();
	}

	/**
	 * 链接
	 */
	private void connect() {
		sharedUtils = new SharedUtils(getApplicationContext(),
				Config.LOGIN_STATUS);
		RequestParams params = new RequestParams();
		params.put("yth", sharedUtils.getStringValue("yth"));
		AsyncHttp.post(RealmName.REALM_NAME
						+ "/mi/LotteryGame.ashx?act=GetUserLuckDrawHistoryList",
				params, new AsyncHttpResponseHandler() {
					public void onStart() {
					};

					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						ArrayList<Lglottery_Log> list = new ArrayList<Lglottery_Log>();
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat formatter1 = new SimpleDateFormat(
									"yyyy-MM-dd");
							int len = jsonArray.length();
							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								Lglottery_Log lglottery_Log = new Lglottery_Log();
								lglottery_Log.setId(object
										.getString("EventName"));

								try {
									lglottery_Log.setTime(formatter
											.format(formatter.parse(object
													.getString("LuckDrawTime")
													.replaceAll("/", "-"))));
								} catch (ParseException e) {

									e.printStackTrace();
								}
								// String sta =
								// object.getString("LotteryGameStatus");
								// if (sta.equals("1")) {
								lglottery_Log.setName(object
										.getString("PrizeName"));
								// lglottery_Log.setImg(object.getString("proFaceImg"));

								lglottery_Log.setType(object
										.getString("PrizeName"));
								// }
								// lglottery_Log.setStatus(sta);
								list.add(lglottery_Log);
								Message msg = new Message();
								msg.what = 0;
								msg.obj = list;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFinish() {
						super.onFinish();
					}
				}, getApplicationContext());
	}
}
