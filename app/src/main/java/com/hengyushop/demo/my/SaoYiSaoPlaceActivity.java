package com.hengyushop.demo.my;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.pub.XiaDanListAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.ShopCartData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class SaoYiSaoPlaceActivity extends BaseActivity {

	private ArrayList<ShopCartData> list;
	private DialogProgress progress;
	private ListView listView;
	XinShouGongyeLieAdapter adapter;
	ShopCartData data;
	int len;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sp_list);
		progress = new DialogProgress(SaoYiSaoPlaceActivity.this);
		// progress.CreateProgress();

		initdata();
	}

	public void onResume() {
		try {

			// String bianma = getIntent().getStringExtra("bianma");
			// Toast.makeText(SaoYiSaoPlaceActivity.this, content, 200).show();
			// System.out.println("bianma================"+bianma);

			// String content = CaptureActivity.bianma;
			String content = "6920110106345";
			System.out.println("content================" + content);
			if (content != null) {
				load_list(content);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					System.out.println("=================5=" + list.size());
					XiaDanListAdapter adapter = new XiaDanListAdapter(list,
							SaoYiSaoPlaceActivity.this, imageLoader);
					listView.setAdapter(adapter);

					progress.CloseProgress();
					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {

							try {

								System.out.println("=================1="
										+ list.size());
								Intent intent = new Intent(
										SaoYiSaoPlaceActivity.this, Webview1.class);
								intent.putExtra("list_xsgy", list.get(arg2).id);
								startActivity(intent);

							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
					break;

				default:
					break;
			}
		};
	};

	private void initdata() {
		listView = (ListView) findViewById(R.id.new_list);

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		TextView tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
		tv_xiabu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent Intent2 = new Intent(SaoYiSaoPlaceActivity.this,
						CaptureActivity.class);
				startActivity(Intent2);
			}
		});
	}

	/**
	 * 列表数据解析
	 *
	 * @param content
	 */
	private void load_list(String content) {
		try {
			list = new ArrayList<ShopCartData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_goods_content?goods_no=" + content + "",

					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {

							super.onSuccess(arg0, arg1);
							System.out.println("=====================二级值1" + arg1);
							// try {
							// JSONObject jsonObject = new JSONObject(arg1);
							// String status = jsonObject.getString("status");
							// String info = jsonObject.getString("info");
							// if (status.equals("y")) {
							// JSONObject jsonobt = jsonObject.getJSONObject("data");
							// data = new ShopCartData();
							// data.setId(jsonobt.getString("id"));
							// data.title = jsonobt.getString("title");
							// data.img_url = jsonobt.getString("img_url");
							// // data.quantity = jsonobt.getInt("quantity");
							// String groupon_item = jsonobt.getString("spec_item");
							// JSONArray ja = new JSONArray(groupon_item);
							// for (int j = 0; j < ja.length(); j++) {
							// JSONObject obct = ja.getJSONObject(j);
							// data.setId(obct.getString("id"));
							// data.title = obct.getString("title");
							// data.market_price = obct.getString("market_price");
							// data.sell_price = obct.getString("sell_price");
							// // data.quantity = obct.getInt("quantity");
							// //
							// System.out.println("=====22====================="+zhou2);
							// list.add(data);
							// }
							// } else {
							// progress.CloseProgress();
							// Toast.makeText(SaoYiSaoPlaceActivity.this,info,
							// 200).show();
							// }
							//
							// Message msg = new Message();
							// msg.what = 0;
							// msg.obj = list;
							// handler.sendMessage(msg);
							//
							// } catch (JSONException e) {
							//
							// e.printStackTrace();
							// }
						}
					}, null);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
