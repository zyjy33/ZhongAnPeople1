package com.hengyushop.demo.train;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrainInformationActivity extends BaseActivity {

	private ListView list_station;
	private TextView v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private TrainInfoBean bean;
	private TextView tv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.train_information);

		example();
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					TrainInfoBean item = (TrainInfoBean) msg.obj;
					TrainInfoChild child = item.getChild();
					System.out.println((v1 == null) + "~~~" + (child == null));
					v1.setText(child.getV1());
					v2.setText(child.getV2());
					v3.setText(child.getV3());
					v4.setText(child.getV4());
					v5.setText(child.getV5());
					v6.setText(child.getV6());
					v7.setText(child.getV7());
					v8.setText(child.getV8());
					v9.setText(child.getV9());
					System.out.println(item.getInfoChilds().size());
					TrainInfoAdapter adapter = new TrainInfoAdapter(
							getApplicationContext(), item.getInfoChilds());
					list_station.setAdapter(adapter);
					break;

				default:
					break;
			}
		};
	};

	private void example() {
		v1 = (TextView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		v3 = (TextView) findViewById(R.id.v3);
		v4 = (TextView) findViewById(R.id.v4);
		v5 = (TextView) findViewById(R.id.v5);
		v6 = (TextView) findViewById(R.id.v6);
		v7 = (TextView) findViewById(R.id.v7);
		v8 = (TextView) findViewById(R.id.v8);
		v9 = (TextView) findViewById(R.id.v9);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setText(getIntent().getStringExtra("add"));
		list_station = (ListView) findViewById(R.id.list_station);
		String code = getIntent().getStringExtra("code");
		RequestParams params = new RequestParams();
		params.put("userid", Common.TRAIN_USERID);
		params.put("seckey", Common.TRAIN_SECKEY);
		params.put("trainCode", code);
		AsyncHttp.post("http://www.chepiao100.com/api/checi", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						getBean(arg1);
					}
				}, getApplicationContext());

		// listticketdata();
		// liststationdata();
	}

	private void getBean(String result) {
		System.out.println(result);
		bean = new TrainInfoBean();
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONObject childObject = jsonObject.getJSONObject("data");
			JSONArray headArray = childObject.getJSONArray("head");
			TrainInfoChild child = new TrainInfoChild();
			String childHead1 = headArray.getString(0);
			String childHead2 = headArray.getString(1);
			String childHead3 = headArray.getString(2);
			String childHead4 = headArray.getString(3);
			String childHead5 = headArray.getString(4);
			String childHead6 = headArray.getString(5);
			String childHead7 = headArray.getString(6);
			String childHead8 = headArray.getString(7);
			String childHead9 = headArray.getString(8);
			child.setV1(childHead1);
			child.setV2(childHead2);
			child.setV3(childHead3);
			child.setV4(childHead4);
			child.setV5(childHead5);
			child.setV6(childHead6);
			child.setV7(childHead7);
			child.setV8(childHead8);
			child.setV9(childHead9);
			bean.setChild(child);
			// 时刻
			ArrayList<TrainInfoChild> childs = new ArrayList<TrainInfoChild>();
			JSONArray itemArray = childObject.getJSONArray("item");
			int itemLen = itemArray.length();
			System.out.println(itemLen);
			for (int j = 0; j < itemLen; j++) {
				JSONArray itemChilds = itemArray.getJSONArray(j);
				TrainInfoChild itemChild = new TrainInfoChild();
				String itemChild1 = itemChilds.getString(0);
				String itemChild2 = itemChilds.getString(1);
				String itemChild3 = itemChilds.getString(2);
				String itemChild4 = itemChilds.getString(3);
				String itemChild5 = itemChilds.getString(4);
				String itemChild6 = itemChilds.getString(5);
				String itemChild7 = itemChilds.getString(6);
				String itemChild8 = itemChilds.getString(7);
				String itemChild9 = itemChilds.getString(8);
				itemChild.setV1(itemChild1);
				itemChild.setV2(itemChild2);
				itemChild.setV3(itemChild3);
				itemChild.setV4(itemChild4);
				itemChild.setV5(itemChild5);
				itemChild.setV6(itemChild6);
				itemChild.setV7(itemChild7);
				itemChild.setV8(itemChild8);
				itemChild.setV9(itemChild9);
				childs.add(itemChild);

			}
			bean.setInfoChilds(childs);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.what = 0;
		msg.obj = bean;
		handler.sendMessage(msg);

	}

}
