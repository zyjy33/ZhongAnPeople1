package com.lglottery.www.common;

import android.util.Log;

public class WLog {
	private final static String TAG = "wellmay";

	public static void v(String msg) {

		if (Config.DEBUG) {
			Log.i(TAG, msg);
		}
	}
}
