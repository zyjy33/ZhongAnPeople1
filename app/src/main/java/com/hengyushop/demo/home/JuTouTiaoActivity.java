package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MyjuTouTiaoAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.CollectWareData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class JuTouTiaoActivity extends BaseActivity {

	private ArrayList<CollectWareData> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				list = (ArrayList<CollectWareData>) msg.obj;
				MyjuTouTiaoAdapter adapter = new MyjuTouTiaoAdapter(list,
						JuTouTiaoActivity.this, imageLoader);
				listView.setAdapter(adapter);
				progress.CloseProgress();
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
		setContentView(R.layout.activity_jutoutiao);

		popupWindowMenu = new MyPopupWindowMenu(this);
		// wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(JuTouTiaoActivity.this);

		listView = (ListView) findViewById(R.id.list_ware_collect);
		// listView.setCacheColorHint(0);
		//
		// UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		// yth = registerData.getHengyuCode();
		// key = registerData.getUserkey();

		progress.CreateProgress();

		loadWeather();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try {

					System.out.println("=================1="
							+ list.get(arg2).id_ll);
					Intent intent = new Intent(JuTouTiaoActivity.this,
							Webview1.class);
					intent.putExtra("web_id", list.get(arg2).id_ll);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private void loadWeather() {

		strUrl = RealmName.REALM_NAME_LL
				+ "/get_article_category_id_list?channel_name=news&category_id=3"
				+ "&top=10&strwhere=";

		System.out.println(" ’≤ÿ" + strUrl);
		AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		}, null);
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			System.out.println("====================" + st);
			list = new ArrayList<CollectWareData>();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				CollectWareData data = new CollectWareData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id_ll = object.getString("id");
				data.title = object.getString("title");
				data.img_url = object.getString("img_url");
				data.add_time = object.getString("add_time");
				// data.retailPrice = object.getString("retailPrice");
				// data.proFaceImg = object.getString("proFaceImg");
				// data.collectTotal = object.getString("collectTotal");

				list.add(data);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = list;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
