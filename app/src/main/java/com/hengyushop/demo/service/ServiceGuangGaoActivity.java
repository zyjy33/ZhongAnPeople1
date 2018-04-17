package com.hengyushop.demo.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterServiceView;
import com.lglottery.www.widget.MyPosterllView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

public class ServiceGuangGaoActivity extends BaseActivity {
	/**
	 * 关于引导页的界面
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	SharedPreferences preferences;
	// private ImageView iv_umage;
	private ViewPager i1;
	ArrayList<AdvertDao1> images;
	/** Called when the activity is first created. */
	private MyPosterServiceView advPager;
	public static String proFaceImg, proInverseImg;
	private SharedUtils u;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sj_sq);

		Log.i("1", "这里出错了！---");

		Button btn_settle_accounts = (Button) findViewById(R.id.btn_settle_accounts);
		btn_settle_accounts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent(ServiceGuangGaoActivity.this,
							ApplyBusiness1Activity.class);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

		// 广告滚动
		advPager = (MyPosterServiceView) findViewById(R.id.adv_pagerll);

		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_adbanner_list?advert_id=1017",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							System.out.println("-----------------" + arg1);
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							if (status.equals("y")) {
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									AdvertDao1 ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									String ad_url = ada.getAd_url();
									ada.setAd_url(RealmName.REALM_NAME_HTTP
											+ json.getString("ad_url"));
									images.add(ada);
								}
								Message msg = new Message();
								msg.obj = images;
								msg.what = 0;
								childHandler.sendMessage(msg);
							} else {
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, context);
		// System.out.println("images.size()-----------------"+images.size());

		advPager.postDelayed(new Runnable() {

			@Override
			public void run() {
				handler.sendEmptyMessage(0);

			}
		}, 6000);

	}

	ArrayList<AdvertDao1> tempss;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				tempss = (ArrayList<AdvertDao1>) msg.obj;

				ArrayList<String> urls = new ArrayList<String>();
				for (int i = 0; i < tempss.size(); i++) {
					urls.add(tempss.get(i).getAd_url());
				}
				// addvie(context, tempss,urls);
				ImageLoader imageLoader = ImageLoader.getInstance();
				advPager.setData(urls, new MyPosterOnClick() {
					@Override
					public void onMyclick(int position) {
						// TODO Auto-generated method stub
						// Message msg = new Message();
						// msg.what = 13;
						// msg.obj = tempss.get(position).getId();
						// handler.sendMessage(msg);
					}
				}, true, imageLoader, true);
				break;
			default:
				break;
			}
		};
	};

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

}