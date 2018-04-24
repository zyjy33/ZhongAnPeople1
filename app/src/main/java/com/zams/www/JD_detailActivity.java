package com.zams.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

public class JD_detailActivity extends BaseActivity {
	private MyPosterView img_detail;
	private ArrayList<String> images;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				img_detail.setData(images, imageLoader);
				break;

			default:
				break;
			}
		};
	};

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jd_detail_activity);
		img_detail = (MyPosterView) findViewById(R.id.img_detail);

		if (getIntent().hasExtra("id")) {
			RequestParams params = new RequestParams();
			params.put("sceneryId", getIntent().getStringExtra("id"));
			System.out.println("id" + getIntent().getStringExtra("id"));
			AsyncHttp
					.post("http://www.wxpalm.com.cn/mi/LY_Scenery.ashx?act=GetSceneryDetail",
							params, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {

									System.out.println(arg1);
									super.onSuccess(arg0, arg1);
									try {
										JSONObject jsonObject = new JSONObject(
												arg1);
										JSONArray jsonArray = jsonObject
												.getJSONArray("SceneryImgList");
										int len = jsonArray.length();
										images = new ArrayList<String>();
										for (int i = 0; i < len; i++) {
											JSONObject object = jsonArray
													.getJSONObject(i);
											images.add(object
													.getString("sceneryImageUrl"));
										}
										handler.sendEmptyMessage(1);

									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}, null);
		}

	};
}
