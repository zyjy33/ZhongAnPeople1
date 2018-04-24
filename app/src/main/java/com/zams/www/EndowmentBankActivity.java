package com.zams.www;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 养老银行
 *
 * @author Administrator
 *
 */
public class EndowmentBankActivity extends BaseActivity {
	private ListView vip_list;
	private LinearLayout test;
	private ImageView ling_tip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endowment_bank_activity);// endowment_bank_activity

		Initialize();
		loadguanggao();

	}

	private void Initialize() {
		ling_tip = (ImageView) findViewById(R.id.ling_tip);

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	}

	private void loadguanggao() {
		try {

			// 广告滚动
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_adbanner_list?advert_id=14",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									AdvertDao1 ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									String ad_url = ada.getAd_url();
									ImageLoader imageLoader = ImageLoader
											.getInstance();
									imageLoader.displayImage(
											RealmName.REALM_NAME_HTTP + ad_url,
											ling_tip);
									images.add(ada);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// private Handler handler = new Handler(){
	// public void dispatchMessage(android.os.Message msg) {
	// switch (msg.what) {
	// case 1:
	// break;
	//
	// default:
	// break;
	// }
	// };
	// };
}
