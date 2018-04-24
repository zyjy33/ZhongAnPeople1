package com.guanggao;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class D extends BaseActivity{

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guanggao);
		//来电监听，获取系统服务“TELEPHONY_SERVICE
	    /*TelephonyManager telM = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);  
		telM.listen(new TelListener(this), PhoneStateListener.LISTEN_CALL_STATE);*/
		
		 /*CountDownTimer timer = new CountDownTimer(3000,100) {
			@Override
			public void onTick(long arg0) {

			}
			@Override
			public void onFinish() {

				AppManager.getAppManager().finishAllActivity();
			}
		};
		timer.start();*/
	};
}
