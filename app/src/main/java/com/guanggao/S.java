package com.guanggao;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import com.lglottery.www.widget.NewDataToast;
import com.zams.www.R;
import com.zams.www.TelListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class S extends Service {
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				createDragImage();
				break;

			default:
				break;
			}
		};
	};
	TelephonyManager tManager;
	private TextView overlay;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 来电监听，获取系统服务“TELEPHONY_SERVICE
		TelephonyManager telM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telM.listen(new TelListener(this), PhoneStateListener.LISTEN_CALL_STATE);
		// TODO Auto-generated method stub
		/*
		 * if(intent.hasExtra("start")){ // initOverlay();
		 * System.out.println("这里"); // 取得TelephonyManager对象 tManager =
		 * (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 * 
		 * // 创建一个通话状态监听器 PhoneStateListener listener = new PhoneStateListener()
		 * {
		 * 
		 * @Override public void onCallStateChanged(int state, String number) {
		 * // TODO Auto-generated method stub
		 * 
		 * switch (state) { // 无任何状态 case TelephonyManager.CALL_STATE_IDLE:
		 * break; case TelephonyManager.CALL_STATE_OFFHOOK: break; // 来电铃响时 case
		 * TelephonyManager.CALL_STATE_RINGING:
		 * Toast.makeText(getApplicationContext(), "来电",200).show();
		 * System.out.println("有吗");
		 * 
		 * // initOverlay(); CountDownTimer countDownTimer = new
		 * CountDownTimer(3000,100) {
		 * 
		 * @Override public void onTick(long arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * }
		 * 
		 * @Override public void onFinish() { // TODO Auto-generated method stub
		 * Intent intent = new Intent(); intent.setAction("rings");
		 * sendBroadcast(intent);
		 * 
		 * Intent intent = new Intent(); intent.setClassName("com.zams.www",
		 * "com.guanggao.D"); // intent.setAction("start_rings");
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * startActivity(intent); NewDataToast.makeText(getApplicationContext(),
		 * "测试", false, 0).show(); //handler.sendEmptyMessage(1); } };
		 * countDownTimer.start(); //createDragImage(); break; default: break; }
		 * super.onCallStateChanged(state, number); } }; // 监听电话通话状态的改变
		 * tManager.listen(new TelListener(this),
		 * PhoneStateListener.LISTEN_CALL_STATE); }
		 */
		return super.onStartCommand(intent, flags, startId);
	}

	private WindowManager.LayoutParams mWindowLayoutParams;
	private WindowManager mWindowManager;

	private void createDragImage() {
		mWindowLayoutParams = new WindowManager.LayoutParams();
		// mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; // 图片之外的其他地方透明
		mWindowLayoutParams.gravity = Gravity.CENTER;
		// mWindowLayoutParams.type = 2003;
		mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要 
		// mWindowLayoutParams.format=1; 
		mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点 
		mWindowLayoutParams.flags = mWindowLayoutParams.flags
				| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		mWindowLayoutParams.flags = mWindowLayoutParams.flags
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排
		// mWindowLayoutParams.x=100;
		// mWindowLayoutParams.y=100;
		// mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
		// mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top
		// - mStatusHeight;
		// mWindowLayoutParams.alpha = 0.55f; // 透明度
		mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// mWindowLayoutParams.flags =
		// WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
		// | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;

		View la = LinearLayout.inflate(getApplicationContext(),
				R.layout.guanggao, null);
		mWindowManager.addView(la, mWindowLayoutParams);
	}

}
