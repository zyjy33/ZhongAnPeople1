package com.zams.www;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyWareThreeAdapter;
import com.android.hengyu.pub.MyWareThreeGridAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.WareClassifyThreeData;
import com.hengyushop.entity.WareData;
import com.hengyushop.json.HttpClientUtil;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

public class WareClassifyThreeActivity extends BaseActivity {

	private GridView gridView;
	private HorizontalScrollView scrollView;
	private ListView listView;
	private TextView tv_title_classifythree;
	private WareDao waredao;
	private WareData ware;
	private ArrayList<WareData> list;
	private MyWareThreeAdapter adapter;
	private MyWareThreeGridAdapter gridAdapter;
	private int id;
	private String titlename;
	private ArrayList<WareClassifyThreeData> listgird;
	private String strUrl;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
		}
		return true;
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 100:
				list = (ArrayList<WareData>) msg.obj;
				adapter = new MyWareThreeAdapter(list,
						WareClassifyThreeActivity.this);
				listView.setAdapter(adapter);
				break;
			case 200:
				listgird = (ArrayList<WareClassifyThreeData>) msg.obj;
				gridAdapter = new MyWareThreeGridAdapter(listgird,
						WareClassifyThreeActivity.this, imageLoader);
				gridView.setAdapter(gridAdapter);

				int size = listgird.size();
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				float density = dm.density;
				int allWidth = (int) (110 * size * density);
				int itemWidth = (int) (100 * density);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						allWidth, LinearLayout.LayoutParams.FILL_PARENT);
				gridView.setLayoutParams(params);
				gridView.setColumnWidth(itemWidth);
				gridView.setHorizontalSpacing(10);
				gridView.setStretchMode(GridView.NO_STRETCH);
				gridView.setNumColumns(size);
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
		setContentView(R.layout.menu_ware_classify_three);

		popupWindowMenu = new MyPopupWindowMenu(this);
		gridView = (GridView) findViewById(R.id.gridview);
		scrollView = (HorizontalScrollView) findViewById(R.id.mScrollView);
		listView = (ListView) findViewById(R.id.list_classify_three);
		tv_title_classifythree = (TextView) findViewById(R.id.tv_title_classifythree);
		listView.setCacheColorHint(0);
		scrollView.setHorizontalScrollBarEnabled(false);
		waredao = new WareDao(getApplicationContext());
		ware = new WareData();

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id = (Integer) bundle.get("id");
		titlename = (String) bundle.get("name");
		tv_title_classifythree.setText(titlename);

		strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "ProductPropertyProductItems");
		params.put("yth", "test");
		params.put("key", "test");
		params.put("productTypeId2", id + "");
		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
		progress = new DialogProgress(WareClassifyThreeActivity.this);
		progress.CreateProgress();
		new Thread() {
			public void run() {
				List<WareData> allnames = waredao.findAllWare(id);
				Message message = new Message();
				message.what = 100;
				message.obj = allnames;
				handler.sendMessage(message);

			};

		}.start();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				String typename = list.get(arg2).productTypeName;
				int id = waredao.findbyTypeName(typename).ID;
				Intent intent = new Intent(WareClassifyThreeActivity.this,
						WareClassifyFourActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("name", typename);
				startActivity(intent);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				int id = listgird.get(arg2).productItemId;
				Toast.makeText(getApplicationContext(), id + "", 200).show();
				Intent intent = new Intent(WareClassifyThreeActivity.this,
						WareInformationActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});

	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<WareClassifyThreeData> list = new ArrayList<WareClassifyThreeData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				WareClassifyThreeData data = new WareClassifyThreeData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.productItemId = object.getInt("productItemId");
				data.proName = object.getString("proName");
				data.proThumbnailImg = object.getString("proThumbnailImg");
				data.retailPrice = object.getString("retailPrice");
				data.marketPrice = object.getString("marketPrice");
				list.add(data);
				Log.v("number", data.productItemId + "");
			}
			Message message = new Message();
			message.what = 200;
			message.obj = list;
			handler.sendMessage(message);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
