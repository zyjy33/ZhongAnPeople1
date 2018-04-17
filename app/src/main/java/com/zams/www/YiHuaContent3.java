package com.zams.www;

import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

public class YiHuaContent3 extends LinearLayout{
	private Handler handler;
	public YiHuaContent3(Context context,Handler handler) {
		super(context);
		this.handler = handler;
		init(context);
	}
	private void init(Context context) {
		View child = LinearLayout.inflate(context, R.layout.yihua_con,
				null);
		this.addView(child);
		Message msg = new Message();
		msg.what = 1;
		msg.obj = child;
		handler.sendMessage(msg);
	}
	
}
