package com.guanggao;

import com.zams.www.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class B extends BroadcastReceiver {
	private TextView overlay;

	public void onReceive(android.content.Context arg0,
			android.content.Intent arg1) {
		if (arg1.getAction().equals("rings")) {
			System.out.println("============================");
			// Toast.makeText(arg0, "广告", 200).show();
			/*
			 * Intent intent = new Intent(); intent.setClassName("com.zams.www",
			 * "com.guanggao.D"); // intent.setAction("start_rings");
			 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * arg0.startActivity(intent);
			 */
			//
			// initOverlay(arg0);
			createDragImage(arg0);
		}
	};

	private WindowManager.LayoutParams mWindowLayoutParams;
	private WindowManager mWindowManager;

	private void createDragImage(Context arg0) {
		mWindowManager = (WindowManager) arg0
				.getSystemService(Context.WINDOW_SERVICE);
		mWindowLayoutParams = new WindowManager.LayoutParams();
		mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; // 图片之外的其他地方透明
		mWindowLayoutParams.gravity = Gravity.CENTER;
		// mWindowLayoutParams.type = 2003;
		mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要 
		// mWindowLayoutParams.format=1; 
		// mWindowLayoutParams.flags =
		// WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点 
		// mWindowLayoutParams.flags = mWindowLayoutParams.flags |
		// WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		// mWindowLayoutParams.flags = mWindowLayoutParams.flags |
		// WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排
		// mWindowLayoutParams.x=100;
		// mWindowLayoutParams.y=100;
		// mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
		// mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top
		// - mStatusHeight;
		// mWindowLayoutParams.alpha = 0.55f; // 透明度
		mWindowLayoutParams.width = 300;
		mWindowLayoutParams.height = 300;
		mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		// View la = LinearLayout.inflate(arg0, R.layout.guanggao, null);
		TextView teView = new TextView(arg0);
		teView.setText("广告");
		teView.setTextColor(arg0.getResources().getColor(R.color.blue));
		teView.setTextSize(22);
		mWindowManager.addView(teView, mWindowLayoutParams);
	}

}
