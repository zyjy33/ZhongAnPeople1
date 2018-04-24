package com.zams.www;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class TelListener extends PhoneStateListener {


    private Context context;
    private WindowManager wm;
    private ImageView image;
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
            params.height = 300;
/*<span style="white-space: pre;">            </span>params.format = PixelFormat.RGBA_8888;*/
            // tv= new TextView(context);
            image = new ImageView(context);
            image.setBackgroundResource(R.drawable.guanggao);
            //tv.setText("这是悬浮窗口，来电号码：" + 123456);
            wm.addView(image, params);

        }else if(state == TelephonyManager.CALL_STATE_IDLE){
            if(wm != null){
                wm.removeView(image);
            }
        }
    }
}