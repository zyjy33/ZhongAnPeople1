package com.lglottery.www.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CallRevice extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		// TODO Auto-generated method stub
	if(arg1.getAction().equals(Intent.ACTION_CALL)){
		System.err.println("dadianh");
	}
	}
	
}
