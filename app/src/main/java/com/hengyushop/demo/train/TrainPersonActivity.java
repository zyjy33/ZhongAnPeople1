package com.hengyushop.demo.train;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

public class TrainPersonActivity extends BaseActivity {
	private ListView train_persons;
	private Button add, submit;
	private WareDao wareDao;
	private String yth;
	private ArrayList<TrainPersonItem> arrayList;
	private Map<String, TrainPersonItem> map;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					if (arrayList != null) {
						map = new HashMap<String, TrainPersonItem>();
						TrainPersonItemAdapter itemAdapter = new TrainPersonItemAdapter(
								getApplicationContext(), arrayList, handler);
						train_persons.setAdapter(itemAdapter);
					}
					break;
				case 0:
					TrainPersonItem key = (TrainPersonItem) msg.obj;
					switch (msg.arg1) {
						case 0:
							if (map.containsKey(key.getTrainUserContactID())) {
								System.out.println("删掉存在项");
								map.remove(key.getTrainUserContactID());
							}
							break;
						case 1:
							if (!map.containsKey(key.getTrainUserContactID())) {
								System.out.println("增加新数值");
								map.put(key.getTrainUserContactID(), key);
							}
							break;
						default:
							break;
					}
					Iterator<String> i = map.keySet().iterator();
					while (i.hasNext()) {
						System.out.println("map的值" + i.next());

					}

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
		setContentView(R.layout.train_person_layout);
		add = (Button) findViewById(R.id.add);
		submit = (Button) findViewById(R.id.submit);
		train_persons = (ListView) findViewById(R.id.persons);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(TrainPersonActivity.this,
						TrainAddPersonActivity.class);
				startActivity(intent);
			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				MapSer ser = new MapSer();
				ser.setMap(map);
				intent.putExtra("result", ser);
				setResult(10001, intent);
				AppManager.getAppManager().finishActivity();
			}
		});

	}

	@Override
	protected void onResume() {

		super.onResume();
		if (yth == null) {
			Toast.makeText(getApplicationContext(), "未登录!", 200).show();

		} else {
			RequestParams params = new RequestParams();
			// http://wxpalm.com.cn/mi/TrainHandler.ashx?yth=114930899&act=GetTrainUserContacts
			params.put("yth", yth);
			AsyncHttp.post(RealmName.REALM_NAME
							+ "/mi/TrainHandler.ashx?act=GetTrainUserContacts", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {

							super.onSuccess(arg0, arg1);
							parse(arg1);
							handler.sendEmptyMessage(1);
						}
					}, getApplicationContext());
		}
	}

	private void parse(String result) {
		try {
			arrayList = new ArrayList<TrainPersonItem>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				TrainPersonItem item = new TrainPersonItem();
				item.setContactUserName(object.getString("ContactUserName"));
				item.setContactUserPhone(object.getString("ContactUserPhone"));
				item.setDocumentNumber(object.getString("DocumentNumber"));
				item.setDocumentType(object.getString("DocumentType"));
				item.setPiaoZhong(object.getString("PiaoZhong"));
				item.setTrainUserContactID(object
						.getString("TrainUserContactID"));
				arrayList.add(item);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}
}
