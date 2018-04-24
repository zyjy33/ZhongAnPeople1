package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.pub.MyWareAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.WareData;

import java.util.ArrayList;
import java.util.List;

public class WareClassifyTwoActivity extends BaseActivity implements
		OnClickListener {

	private ListView list_classify_one;
	private TextView tv_title_classifytwo;
	private WareDao waredao;
	private WareData ware;
	private ArrayList<WareData> list;
	private MyWareAdapter adapter;
	private int id;
	private String titlename;
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
					adapter = new MyWareAdapter(list, WareClassifyTwoActivity.this,
							imageLoader);
					list_classify_one.setAdapter(adapter);
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
		setContentView(R.layout.menu_ware_classify_two);
		list_classify_one = (ListView) findViewById(R.id.list_classify_one);
		list_classify_one.setCacheColorHint(0);
		tv_title_classifytwo = (TextView) findViewById(R.id.tv_title_calssifytwo);
		popupWindowMenu = new MyPopupWindowMenu(this);

		waredao = new WareDao(getApplicationContext());
		ware = new WareData();

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id = (Integer) bundle.get("id");
		titlename = (String) bundle.get("name");
		tv_title_classifytwo.setText(titlename);

		progress = new DialogProgress(WareClassifyTwoActivity.this);
		progress.CreateProgress();
		new Thread() {
			public void run() {
				List<WareData> allnames = waredao.findAllWareTwo(id);
				Message message = new Message();
				message.what = 100;
				message.obj = allnames;
				handler.sendMessage(message);
			};

		}.start();

		list_classify_one.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {

				String typename = list.get(arg2).productTypeName;
				int id = waredao.findbyTypeName(typename).ID;
				Intent intent = new Intent(WareClassifyTwoActivity.this,
						WareClassifyThreeActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("name", typename);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}
}
