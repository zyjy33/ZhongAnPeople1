package com.zams.www;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.AdvertData;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuAdvertActivity extends BaseActivity {
	@SuppressWarnings("unused")
	private ListView list_adverts;
	private TextView name;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					final ArrayList<AdvertData> lists = (ArrayList<AdvertData>) msg.obj;
					AdvertAdapter adapter = new AdvertAdapter(lists,
							getApplicationContext());
					list_adverts.setAdapter(adapter);
					list_adverts.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							System.out.println("=========1============"+lists.get(arg2).getId());//5897
							Intent intent = new Intent(MenuAdvertActivity.this,WareInformationActivity.class);
							//						intent.putExtra("id",Integer.parseInt(lists.get(arg2).getId()));
							intent.putExtra("id",lists.get(arg2).getId());
							intent.putExtra("seri",lists.get(arg2).getSeri());
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

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_advert_layout);
		init();
		int index = getIntent().getIntExtra("index", 0);
		String url = "";
		if(index==1){
			url = RealmName.REALM_NAME+"/mi/getData.ashx";
			Map<String, String> params = new HashMap<String, String>();
			params.put("act", "SalesRankProductItems");
			params.put("yth", "");
			params.put("key", "");

			AsyncHttp.post_1(url,params, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, String arg1) {
					super.onSuccess(arg0, arg1);
					parse1(arg1);
				}
			} );
		}else if (index==2) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("act", "ClassifiedAdsProductItems");
			params.put("yth", "");
			params.put("key", "");
			url = RealmName.REALM_NAME+"/mi/getData.ashx";
			AsyncHttp.post_1(url,params, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, String arg1) {

					super.onSuccess(arg0, arg1);
					parse(arg1);
				}
			} );
		}

		//		new Thread() {
		//			@Override
		//			public void run() {
		//
		//				super.run();
		//				Map<String, String> param = new HashMap<String, String>();
		//				param.put("", "");
		//				param.put("", "");
		//				try {
		//					InputStream ip = HttpClientUtil
		//							.getRequest(RealmName.REALM_NAME
		//									+ "/mi/getData.ashx?act=ClassifiedAdsProductItems&yth=&key=");
		//
		//					String st = new HttpUtils().convertStreamToString(ip,
		//							"utf-8").trim();
		//					// 解析st
		//
		//				} catch (Exception e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		}.start();
	}

	/**
	 * 解析数据返回的信息
	 *
	 * @param result
	 */
	private void parse(String result) {
		try {
			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("Data");
			int len = array.length();
			ArrayList<AdvertData> lists = new ArrayList<AdvertData>();

			for (int i = 0; i < len; i++) {
				JSONObject object = array.getJSONObject(i);
				AdvertData data = new AdvertData();
				data.setImage(object.getString("proFaceImg"));
				data.setName(object.getString("proName"));
				data.setId(object.getString("productItemId"));
				data.setSeri(object.getString("AdvertisingSerialNumber"));
				lists.add(data);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = lists;
			handler.sendMessage(msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parse1(String result) {
		try {
			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("Data");
			int len = array.length();
			ArrayList<AdvertData> lists = new ArrayList<AdvertData>();

			for (int i = 0; i < len; i++) {
				JSONObject object = array.getJSONObject(i);
				AdvertData data = new AdvertData();
				data.setImage(object.getString("proFaceImg"));
				data.setName(object.getString("proName"));
				data.setId(object.getString("productItemId"));
				data.setSeri(object.getString("proCode"));
				lists.add(data);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = lists;
			handler.sendMessage(msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化数据
	 */
	private void init() {
		name = (TextView) findViewById(R.id.name);
		name.setText(getIntent().getStringExtra("name"));
		list_adverts = (ListView) findViewById(R.id.list_adverts);
	}

	class AdvertAdapter extends BaseAdapter {
		Holder holder;
		ArrayList<AdvertData> lists;
		Context context;

		public AdvertAdapter(ArrayList<AdvertData> lists, Context con) {
			this.lists = lists;
			this.context = con;
		}

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				holder = new Holder();
				arg1 = LinearLayout.inflate(getApplicationContext(),
						R.layout.advertlist, null);
				holder.view3 = (ImageView) arg1.findViewById(R.id.ima);
				holder.view2 = (TextView) arg1.findViewById(R.id.tv2);
				holder.view1 = (TextView) arg1.findViewById(R.id.tv1);
				arg1.setTag(holder);
			} else {
				holder = (Holder) arg1.getTag();
			}

			imageLoader.displayImage(RealmName.REALM_NAME + "/admin/"
					+ lists.get(arg0).getImage(), holder.view3);
			holder.view2.setText(lists.get(arg0).getName());
			return arg1;
		}

		class Holder {
			private TextView view1, view2;
			private ImageView view3;
		}

	}
}
