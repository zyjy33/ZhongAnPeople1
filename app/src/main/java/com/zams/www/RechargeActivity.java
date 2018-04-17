package com.zams.www;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.android.hengyu.ui.CustomTabHost;
import com.hengyushop.db.DBRecharge;

public class RechargeActivity extends TabActivity implements
		OnTabChangeListener, OnGestureListener {
	private GestureDetector gestureDetector;
	private CustomTabHost tabHost;
	private TabWidget tabWidget;
	private static final int FLEEP_DISTANCE = 120;
	private DBRecharge dbRecharge;

	/** 记录当前分页ID */
	private int currentTabID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recharge);
		tabHost = (CustomTabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		tabHost.setOnTabChangedListener(this);
		dbRecharge = new DBRecharge(this);
		dbRecharge.openDatabase();
		dbRecharge.closeDatabase();

		createRechargeMobile();
		createRechargeQQ();
		createRechargeGame();

		gestureDetector = new GestureDetector(this);
		new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
	}

	public void createRechargeMobile() {
		TabHost.TabSpec localTabSpec = this.tabHost.newTabSpec("0");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("手机话费");
		Intent localIntent = new Intent(this, RechargeMobileActivity.class);
		localTabSpec.setIndicator(localView).setContent(localIntent);
		this.tabHost.addTab(localTabSpec);
	}

	private void createRechargeQQ() {
		TabHost.TabSpec localTabSpec1 = this.tabHost.newTabSpec("1");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("QQ充值");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(this, RechargeQQActivity.class);
		localTabSpec2.setContent(localIntent);
		this.tabHost.addTab(localTabSpec1);

	}

	private void createRechargeGame() {
		TabHost.TabSpec localTabSpec1 = this.tabHost.newTabSpec("2");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("游戏点卡");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(this, RechargeGameActivity.class);
		localTabSpec2.setContent(localIntent);
		this.tabHost.addTab(localTabSpec1);
	}

	@Override
	public void onTabChanged(String tabId) {
		// tabId值为要切换到的tab页的索引位置
		int tabID = Integer.valueOf(tabId);
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			if (i == tabID) {
				tabWidget.getChildAt(Integer.valueOf(i));
			} else {
				tabWidget.getChildAt(Integer.valueOf(i));
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		if (e1.getX() - e2.getX() <= (-FLEEP_DISTANCE)) {// 从左向右滑动
			currentTabID = tabHost.getCurrentTab() - 1;
			if (currentTabID < 0) {
				currentTabID = tabHost.getTabCount() - 1;
			}
		} else if (e1.getX() - e2.getX() >= FLEEP_DISTANCE) {// 从右向左滑动
			currentTabID = tabHost.getCurrentTab() + 1;
			if (currentTabID >= tabHost.getTabCount()) {
				currentTabID = 0;
			}
		}
		tabHost.setCurrentTab(currentTabID);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}
