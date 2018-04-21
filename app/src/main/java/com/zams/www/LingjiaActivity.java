package com.zams.www;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.json.LingjiaDo;
import com.hengyushop.json.LingjiaDomain;
import com.lglottery.www.adapter.LingjiaAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LingjiaActivity extends BaseActivity {
	private LinearLayout horiz;
	private ArrayList<LingjiaDomain> lingjias;
	private TextView[] tvs;
	private ListView lis;
	private ImageView ling_tip;
	private LingjiaAdapter adapter;
	private HorizontalScrollView hor_scrollview;
	int index = 0;
	int cindex = 0;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					if (lingjias != null) {
						int len = lingjias.size();
						horiz.removeAllViews();
						tvs = new TextView[len];
						for (int i = 0; i < len; i++) {

							TextView textView = new TextView(
									getApplicationContext());
							tvs[i] = textView;
							tvs[i].setId(Integer.parseInt(lingjias.get(i).getId()));
							tvs[i].setText(lingjias.get(i).getName());
							tvs[i].setTextSize(20);
							tvs[i].setTextColor(getResources().getColor(
									R.color.black));
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT);
							params.setMargins(10, 8, 10, 8);
							tvs[i].setLayoutParams(params);
							horiz.addView(tvs[i]);
							final String id = lingjias.get(i).getId();
							final String cindex = String.valueOf(i);
							if (i == 0) {
								load(id);
								tvs[0].setTextColor(getResources().getColor(
										R.color.fl));
							}
							tvs[i].setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									System.out.println("ID号码是:" + id);
									load(id);
									int width = hor_scrollview.getWidth();
									System.out.println("父类的长度:" + width);
									int childIndex = Integer.parseInt(cindex);
									if (childIndex != 0 || childIndex != tvs.length) {
										hor_scrollview.scrollTo(
												Integer.parseInt(cindex) * 130, 0);
									}
									int jen = lingjias.size();
									for (int j = 0; j < jen; j++) {
										if (Integer.parseInt(cindex) == j) {
											tvs[j].setTextColor(getResources()
													.getColor(R.color.fl));
										} else {
											tvs[j].setTextColor(getResources()
													.getColor(R.color.black));
										}
									}
								}
							});
						}
					}
					break;
				case 1:
					final ArrayList<LingjiaDo> list = (ArrayList<LingjiaDo>) msg.obj;
					adapter = new LingjiaAdapter(getApplicationContext(), list,
							imageLoader);
					lis.setAdapter(adapter);
					lis.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(LingjiaActivity.this,
									WareInformationActivity.class);
							intent.putExtra("id",
									Integer.parseInt(list.get(arg2).getId()));
							intent.putExtra("cid", list.get(arg2).getCid());
							intent.putExtra("fen", list.get(arg2).getJifen());
							intent.putExtra("tag", "0");
							startActivity(intent);
						}
					});
					break;
				default:
					break;
			}
		};
	};

	private void load(String i) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("yth", "test");
		params.put("act", "GetZeroProductList");
		params.put("layer", i);
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							ArrayList<LingjiaDo> list = new ArrayList<LingjiaDo>();

							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								LingjiaDo jiaDo = new LingjiaDo();
								jiaDo.setGoods(object.getString("marketPrice"));
								jiaDo.setImg(object
										.getString("ZeroAdvertisingImgShowUrl"));
								jiaDo.setJifen(object.getString("ZeroPoints"));
								jiaDo.setName(object.getString("proName"));
								jiaDo.setNum(object.getString("integral"));
								jiaDo.setPrice(object.getString("ZeroPrice"));
								jiaDo.setId(object.getString("ProductItemId"));
								jiaDo.setCid(object.getString("ID"));
								list.add(jiaDo);
							}
							Message msg = new Message();
							msg.what = 1;
							msg.obj = list;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lingjia_layout);
		lis = (ListView) findViewById(R.id.lis);
		ling_tip = (ImageView) findViewById(R.id.ling_tip);
		imageLoader.displayImage("drawable://" + R.drawable.demo15, ling_tip);
		hor_scrollview = (HorizontalScrollView) findViewById(R.id.hor_scrollview);
		horiz = (LinearLayout) findViewById(R.id.horiz);
		Map<String, String> params = new HashMap<String, String>();

		params.put("yth", "test");
		params.put("act", "GetZeroClassifiedAdsCategory");

		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						super.onFailure(arg0);
						System.out.println(arg0);
					}

					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);

						System.out.println("结果" + arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							lingjias = new ArrayList<LingjiaDomain>();

							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								LingjiaDomain domain = new LingjiaDomain();
								domain.setId(object.getString("ID"));
								domain.setName(object
										.getString("ZeroProductTypeName"));
								lingjias.add(domain);

							}
							Message msg = new Message();
							msg.what = 0;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}
}
