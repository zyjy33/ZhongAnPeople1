package com.zams.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.MyPosterllView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends BaseActivity {
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
	private MyPosterllView advPager;
	public static String proFaceImg, proInverseImg;
	private SharedUtils u;
	private Context context;
	AdvertDao1 ada;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_layout);

		ImageView gd_ll = (ImageView) findViewById(R.id.gd_ll);
		gd_ll.setBackgroundResource(R.drawable.zamswz);
		TextView tv_tiaoguo = (TextView) findViewById(R.id.tv_tiaoguo);
		tv_tiaoguo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(SecondActivity.this,MainFragment.class);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			}
		});
		// iv_umage = (ImageView) findViewById(R.id.gd);

		// u = new SharedUtils(getApplicationContext(), "url");
		// if(u.getStringValue("url").length()!=0){
		// imageLoader.displayImage(u.getStringValue("url"), iv_umage);
		// }
		Log.i("1", "这里出错了！---");
		// advPager = (MyPosterView)
		// findViewById(R.id.market_information_images);
		// loadguanggao();

		// 广告滚动
		advPager = (MyPosterllView) findViewById(R.id.adv_pagerll);

		AsyncHttp.get(RealmName.REALM_NAME_LL
						+ "/get_adbanner_list?advert_id=15",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							System.out.println("-----------------" + arg1);
							JSONObject object = new JSONObject(arg1);
							JSONArray array = object.getJSONArray("data");
							int len = array.length();
							images = new ArrayList<AdvertDao1>();
							for (int i = 0; i < len; i++) {
								ada = new AdvertDao1();
								JSONObject json = array.getJSONObject(i);
								ada.setId(json.getString("id"));
								ada.setAd_url(json.getString("ad_url"));
								String ad_url = ada.getAd_url();
								ada.setAd_url(RealmName.REALM_NAME_HTTP+ json.getString("ad_url"));
								System.out.println("ad_url--------------------"+RealmName.REALM_NAME_HTTP+ json.getString("ad_url"));
								images.add(ada);
							}
							ada = null;
							Message msg = new Message();
							msg.obj = images;
							msg.what = 0;
							childHandler.sendMessage(msg);
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




		// System.out.println("images.size()-----------------"+images.size());

		// if (images.size() == 1) {
		// advPager.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// handler.sendEmptyMessage(0);
		//
		// }
		// }, 1000);
		// }else if (images.size() == 2) {
		// advPager.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// handler.sendEmptyMessage(0);
		//
		// }
		// }, 3000);
		// }else if (images.size() == 3) {
		// advPager.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// handler.sendEmptyMessage(0);
		//
		// }
		// }, 6000);
		// }

	}

	public void onDestroy() {
		super.onDestroy();
		try {

			if (MyPosterView.type == true) {
				MyPosterView.mQuery.clear();
				MyPosterView.type = false;
				images = null;
				tempss = null;
				urls = null;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	};

	ArrayList<AdvertDao1> tempss;
	ArrayList<String> urls;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					tempss = (ArrayList<AdvertDao1>) msg.obj;

					urls = new ArrayList<String>();
					for (int i = 0; i < tempss.size(); i++) {
						urls.add(tempss.get(i).getAd_url());
					}
					// addvie(context, tempss,urls);
					ImageLoader imageLoader = ImageLoader.getInstance();
					advPager.setData(urls, new MyPosterOnClick() {
						@Override
						public void onMyclick(int position) {

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
					// Toast.makeText(SecondActivity.this, "自动登录首页", 200).show();
					// Intent intent = new
					// Intent(SecondActivity.this,MainFragment.class);
					// startActivity(intent);
					// AppManager.getAppManager().finishActivity();

					preferences = getSharedPreferences("guide",
							Activity.MODE_PRIVATE);
					// 如果程序已经进入
					if (preferences.getString("flow", "").equals("yes")) {

						System.out.println("111111111111111111111111");
						Intent intent1 = new Intent(SecondActivity.this,
								MainFragment.class);
						startActivity(intent1);
						AppManager.getAppManager().finishActivity();
					} else {
						System.out.println("2222222222222222222222222222");
						Intent intent1 = new Intent(SecondActivity.this,
								Guide2Activity.class);
						startActivity(intent1);
						finish();
						Editor editor = preferences.edit();
						editor.putString("flow", "yes");
						editor.commit();
					}
					break;
				case 1:
					// if(u.getStringValue("url").length()!=0){
					// imageLoader.displayImage(u.getStringValue("url"), iv_umage);
					// }
					break;
				case 2:
					// ImageLoader imageLoader=ImageLoader.getInstance();
					// // imageLoader.displayImage(RealmName.REALM_NAME_HTTP +
					// images.get(index), i0);
					//
					// ArrayList<String> images = getDatall();
					// imageLoader.clearMemoryCache();
					// // market_information_images.clearMemory();
					// System.out.println("=========图片值============"+images.size());
					//
					// //动态广告
					// if(images.size()==1){
					// market_information_images.setData(images,new
					// MyPosterOnClick() {
					//
					// @Override
					// public void onMyclick(int position) {
					//
					//
					// }
					// }, true, imageLoader, false);
					// }else{
					// market_information_images.setData(images,new
					// MyPosterOnClick() {
					//
					// @Override
					// public void onMyclick(int position) {
					//
					//
					// }
					// }, true, imageLoader, true);
					// }
					break;
				default:
					break;
			}
		};
	};

	private void loadguanggao() {
		// try {
		// //广告滚动
		// AsyncHttp.get(RealmName.REALM_NAME_LL
		// + "/get_adbanner_list?advert_id=15",
		// new AsyncHttpResponseHandler() {
		// @Override
		// public void onSuccess(int arg0, String arg1) {
		// super.onSuccess(arg0, arg1);
		// try {
		// JSONObject object = new JSONObject(arg1);
		// JSONArray array = object.getJSONArray("data");
		// int len = array.length();
		// images = new ArrayList<AdvertDao1>();
		// for (int i = 0; i < len; i++) {
		// AdvertDao1 ada = new AdvertDao1();
		// JSONObject json = array.getJSONObject(i);
		// ada.setId(json.getString("id"));
		// ada.setAd_url(json.getString("ad_url"));
		// String ad_url = ada.getAd_url();
		//
		// System.out.println("================1="+ad_url);
		// images.add(ada);
		// handler.sendEmptyMessage(2);
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// }, null);
		//
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
	}

	// private ArrayList<String> getDatall() {
	// ArrayList<String> list = new ArrayList<String>();
	// if (!"".equals(images.get(0).getAd_url())) {
	// list.add(RealmName.REALM_NAME_HTTP + images.get(0).getAd_url());
	// }
	// if (!"".equals(images.get(0).getAd_url())) {
	// list.add(RealmName.REALM_NAME_HTTP + images.get(1).getAd_url());
	// }
	// return list;
	// }

	@Override
	protected void onStart() {

		super.onStart();

	}

	// @Override
	// protected void onPause() {
	//
	// super.onPause();
	// market_information_images.puseExecutorService();
	// }

}