package com.example.taobaohead.headview;

public class Utils {
	/**
	 * 将dip转成px
	 *
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue) {
		//		float scale = Myapplication.getContext().getResources().getDisplayMetrics().density;
		float scale = (float) 3.0;
		//		System.out.println("11===================="+scale);
		return (int) (dpValue * scale + 0.5F);
	}
}
