package com.guanggao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.TextView;

public class G  extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {


		TelephonyManager telM = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		telM.listen(new TelListener(context), PhoneStateListener.LISTEN_CALL_STATE);
	}
	private TelephonyManager getSystemService(String telephonyService) {

		return null;
	}
	public class TelListener extends PhoneStateListener {


		private Context context;
		private WindowManager wm;
		private TextView tv;
		public TelListener(Context context){
			this.context = context;
		}

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);
			if(state == TelephonyManager.CALL_STATE_RINGING){

				wm = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				WindowManager.LayoutParams params = new WindowManager.LayoutParams();
				params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
				params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

				params.width = WindowManager.LayoutParams.WRAP_CONTENT;
				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
				tv = new TextView(context);
				tv.setText("这是悬浮窗口，来电号码：" + incomingNumber);
				wm.addView(tv, params);

			}else if(state == TelephonyManager.CALL_STATE_IDLE){
				if(wm != null){
					wm.removeView(tv);
				}
			}
		}
	}
}
