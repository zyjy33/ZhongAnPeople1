package com.example.taobaohead;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

public class Myapplication extends Application {

	private static Myapplication instance;

	/** 屏幕宽度 */
	public static int screenWidth;
	/** 屏幕高度 */
	public static int screenHeight;
	/** 缓存路径 */
	public static String cachePath;

	public static SharedPreferences sp;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		getScreenSize();
		// 初始化图片加载
		// 数据库操作对象
		// 异常处理

		sp = getSharedPreferences("meiya", 0);

	}

	public static Context getContext() {
		return instance;
	}

	/**
	 * SD卡路径
	 *
	 * @return String
	 */

	/**
	 * 获取缓存路径
	 *
	 * @return String
	 */
	public static String getCachePath() {
		return cachePath;
	}

	/**
	 * 获取屏幕宽高 void
	 */
	private void getScreenSize() {

		DisplayMetrics dm = new DisplayMetrics();
		dm = getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels; // 1024
		screenHeight = dm.heightPixels; // 720
		float density = dm.density; // 1.0
		float densityDpi = dm.densityDpi; // 160.0
	}
	/**
	 * 图片下载器
	 */
}
