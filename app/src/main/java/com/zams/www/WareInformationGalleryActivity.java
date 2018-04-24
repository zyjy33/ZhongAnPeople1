package com.zams.www;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.pub.MyWareInfromationGalleryAdapter;
import com.android.hengyu.pub.MyWareShopAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.json.HttpClientUtil;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Gallery;

public class WareInformationGalleryActivity extends BaseActivity {

	private Gallery gallery;
	private String proFaceImg, proInverseImg, proDoDetailImg, proDesignImg,
			proSupplementImg;
	private String strUrl;
	private List<Map<String, String>> allGriddatas = null;
	private DialogProgress progress;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				allGriddatas = getData();
				MyWareInfromationGalleryAdapter adapter = new MyWareInfromationGalleryAdapter(
						allGriddatas, WareInformationGalleryActivity.this, imageLoader);

				gallery.setAdapter(adapter);
				progress.CloseProgress();
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
		setContentView(R.layout.ware_infromation_gallery);
		
		gallery = (Gallery) findViewById(R.id.ga_wareinfromation_img);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String wareId = (String) bundle.get("id");
		Log.v("data1", wareId + "");

		strUrl = RealmName.REALM_NAME
				+ "/mi/getdata.ashx"
				+ wareId;
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "OneProductItemInfo");
		params.put("yth", "test");
		params.put("key", "test");
		params.put("productItemId", wareId+"");
		
		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
		progress = new DialogProgress(WareInformationGalleryActivity.this);
		progress.CreateProgress();
	}

	private void parse(String st) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				proFaceImg = object.getString("proFaceImg");
				proInverseImg = object.getString("proInverseImg");
				proDoDetailImg = object.getString("proDoDetailImg");
				proDesignImg = object.getString("proDesignImg");
				proSupplementImg = object.getString("proSupplementImg");
				Log.v("data1", proDesignImg);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	private List<Map<String, String>> getData() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> lines = null;
		if (!"".equals(proFaceImg)) {
			lines = new HashMap<String, String>();
			lines.put("img", proFaceImg + "");
			list.add(lines);
		}
		if (!"".equals(proInverseImg)) {
			lines = new HashMap<String, String>();
			lines.put("img", proInverseImg + "");
			list.add(lines);
		}
		if (!"".equals(proDoDetailImg)) {
			lines = new HashMap<String, String>();
			lines.put("img", proDoDetailImg + "");
			list.add(lines);
		}
		if (!"".equals(proDesignImg)) {
			lines = new HashMap<String, String>();
			lines.put("img", proDesignImg + "");
			list.add(lines);
		}
		if (!"".equals(proSupplementImg)) {
			lines = new HashMap<String, String>();
			lines.put("img", proSupplementImg + "");
			list.add(lines);
		}
		return list;
	}

}
