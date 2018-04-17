package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.Vip_dAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.dot.data.VipDomain;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProvideBankActivity extends BaseActivity {
	private ListView vip_list;
	private LinearLayout test;
	private int screenWidth;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					final ArrayList<VipDomain> lists = (ArrayList<VipDomain>) msg.obj;
					Vip_dAdapter adapter = new Vip_dAdapter(
							getApplicationContext(), lists, imageLoader,
							screenWidth);
					vip_list.setAdapter(adapter);
					vip_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(ProvideBankActivity.this,
									WareInformationActivity.class);
							intent.putExtra("id",
									Integer.parseInt(lists.get(arg2).getId()));
							intent.putExtra("vip", lists.get(arg2)
									.getProductBenchmarkPriceID());
							intent.putExtra("price", lists.get(arg2).getPrice());
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
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shengji_ju);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;// 宽度height = dm.heightPixels ;
		test = (LinearLayout) findViewById(R.id.test);
		vip_list = (ListView) findViewById(R.id.vip_list);
		double scale_1 = (double) 169 / 426;
		test.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) (scale_1 * screenWidth)));
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "ProductBenchmarkPriceSetList");
		params.put("yth", "test");
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getData.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);

						try {
							ArrayList<VipDomain> lists = new ArrayList<VipDomain>();
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("Data");
							int len = jsonArray.length();
							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								VipDomain d = new VipDomain();
								d.setBao(object.getString("HongBao"));
								d.setDes(object.getString("CurrentDescription"));
								d.setProductBenchmarkPriceID(object
										.getString("ProductBenchmarkPriceID"));
								d.setId(object.getString("productItemId"));
								d.setImg(object.getString("proFaceImg"));
								d.setImg1(object.getString("proInverseImg"));
								d.setImg2(object.getString("proDoDetailImg"));
								d.setName(object.getString("ProductName"));
								d.setPrice(object.getString("BasePrice"));
								d.setPro(object.getString("proName"));
								lists.add(d);
							}
							Message msg = new Message();
							msg.what = 1;
							msg.obj = lists;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});

	}
}
