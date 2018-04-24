package com.zams.www;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;

public class UserSettingActivity extends BaseActivity implements
		OnClickListener {

	private WareDao wareDao;
	private TextView tv_cancle;
	private MyPopupWindowMenu popupWindowMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hengyu_setting);

		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		example();
	}

	private void example() {
		tv_cancle = (TextView) findViewById(R.id.tv_cancle);
		tv_cancle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.tv_cancle:
				int isLogin = 0;
				UserRegisterData data = new UserRegisterData();
				data.setIsLogin(isLogin);
				wareDao.updateQuitIsLogin(data);
				wareDao.deleteAllShopCart();
				wareDao.deleteAllUserInformation();
				AppManager.getAppManager().finishAllActivity();
				break;

			default:
				break;
		}
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
