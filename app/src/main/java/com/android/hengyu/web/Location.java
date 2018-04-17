package com.android.hengyu.web;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hengyushop.dao.CityDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.Common;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Location extends Application {
	private boolean isOpenLocation = false;
	public LocationClient mLocationClient = null;
	public String mData, dProvince, dCity, dDistrict;
	public MyLocationListenner myListener = new MyLocationListenner();
	public TextView mTv, mProvince, mCity, mDistrict;
	public NotifyLister mNotifyer = null;
	public Vibrator mVibrator01;
	public static String TAG = "LocTestDemo";
	private int myLocationTime = 60 * 1000;
	private static Location mInstance = null;
	public boolean m_bKeyRight = true;
	// BMapManager mBMapManager = null;
	private LocationClientOption option = null;
	private MyUncaughtExceptionHandler uncaughtExceptionHandler;
	private String packgeName;
	public static final String strKey = "kSK5DdCaxWgKpNhv5le5lx7Z";

	@Override
	public void onCreate() {
		reloadClient();
		packgeName = getPackageName();
		cauchException();
		/*
		 * mLocationClient = new LocationClient(this);
		 * mLocationClient.registerLocationListener(myListener);
		 * System.out.println(myListener + "监听");
		 */
		mInstance = this;
		// initEngineManager(this);
		super.onCreate();
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
		initImageLoader(getApplicationContext());
	}

	private void cauchException() {
		System.out
				.println("-----------------------------------------------------");

		// 程序崩溃时触发线程
		uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
	}

	// 创建服务用于捕获崩溃异常
	private class MyUncaughtExceptionHandler implements
			UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// 保存错误日志
			saveCatchInfo2File(ex);

			// 关闭当前应用
			AppManager.getAppManager().finishAllActivity();
		}
	};

	/**
	 * 保存错误信息到文件中
	 *
	 * @return 返回文件名称
	 */
	private String saveCatchInfo2File(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String sb = writer.toString();
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time = formatter.format(new Date());
			String fileName = time + ".txt";
			System.out.println("fileName:" + fileName);
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String filePath = Environment.getExternalStorageDirectory()
						+ "/ysj/" + packgeName + "/crash/";
				File dir = new File(filePath);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						// 创建目录失败: 一般是因为SD卡被拔出了
						return "";
					}
				}
				System.out
						.println("filePath + fileName:" + filePath + fileName);
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				fos.write(sb.getBytes());
				fos.close();
				// 文件保存完了之后,在应用下次启动的时候去检查错误日志,发现新的错误日志,就发送给开发者
			}
			return fileName;
		} catch (Exception e) {
			System.out.println("an error occured while writing file..."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * end 定位
	 */
	private void closeLocation() {
		try {
			mLocationClient.stop(); // 结束定位
			isOpenLocation = false; // 标识为已经结束了定位
		} catch (Exception e) {
		}
	}

	public void reloadClient() {
		mLocationClient = new LocationClient(this);
		mLocationClient.setAK("1939028c0d2440124051a00d9f773ae1");
		mLocationClient.registerLocationListener(myListener);
	}

	/**
	 * 开启定位
	 */
	public void startLocation() {
		try {
			if (!isOpenLocation) // 如果没有打开
			{
				option = new LocationClientOption();
				option.setCoorType("gcj02"); // 设置返回的坐标类型
				option.setScanSpan(myLocationTime); // 设置时间
				option.setOpenGps(true);
				option.setPriority(LocationClientOption.NetWorkFirst);
				option.setAddrType("all"); // 返回地址类型
				mLocationClient.setLocOption(option);
				mLocationClient.start(); // 打开定位

				if (mLocationClient != null && mLocationClient.isStarted())
					mLocationClient.requestLocation();
				else

					isOpenLocation = true; // 标识为已经打开了定位

			}
		} catch (Exception e) {
		}
	}

	/**
	 *
	 *
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if (mTv != null)
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 装载照片加载驱动
	 *
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.MAX_PRIORITY).threadPoolSize(8)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO) // Not
				// necessary
				// in
				// common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/**
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			System.out.println("local" + location);
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				System.out.println("1..........");
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				System.out.println("2..........");
				dProvince = location.getProvince();
				dCity = location.getCity();
				dDistrict = location.getDistrict();
				Intent intent = new Intent();
				Bundle b = new Bundle();

				SharedUtils utils = new SharedUtils(getApplicationContext(),
						Common.locationName);
				utils.setStringValue("dProvince", dProvince);
				utils.setStringValue("dCity", dCity);
				utils.setStringValue("dDistrict", dDistrict);
				// 将地址数据保存在本地数据中
				b.putString("dProvince", dProvince);
				b.putString("dCity", dCity);
				b.putString("dDistrict", dDistrict);

				String city = location.getCity();
				String address = location.getAddrStr();
				double lat = location.getLatitude();
				double log = location.getLongitude();
				utils.setStringValue("city", city);
				utils.setStringValue("address", address);
				utils.setStringValue("lat", String.valueOf(lat));
				utils.setStringValue("log", String.valueOf(log));

				CityDao pDao = new CityDao(getApplicationContext());
				utils.setStringValue("pcode", pDao.pCode(dProvince));
				CityDao cDao = new CityDao(getApplicationContext());
				utils.setStringValue("ccode", cDao.cCode(dCity));
				CityDao dDao = new CityDao(getApplicationContext());
				utils.setStringValue("dcode", dDao.dCode(dDistrict));
				intent.putExtra("TEST", b);
				intent.setAction("test.test");
				sendBroadcast(intent);
				System.out.println("send bro!");

				sb.append(location.getAddrStr());
			}
			closeLocation();
			logMsg(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			System.out.println("po" + poiLocation);
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			logMsg(sb.toString());
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
			mVibrator01.vibrate(1000);
		}
	}

	// public void initEngineManager(Context context) {
	// if (mBMapManager == null) {
	// mBMapManager = new BMapManager(context);
	// }
	//
	// if (!mBMapManager.init(strKey, new MyGeneralListener())) {
	// Toast.makeText(
	// DemoApplication.getInstance().getApplicationContext(),
	// "地址管理", Toast.LENGTH_LONG).show();
	// }
	// }

	public static Location getInstance() {
		return mInstance;
	}

	// static class MyGeneralListener implements MKGeneralListener {
	//
	// @Override
	// public void onGetNetworkState(int iError) {
	// if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
	//
	// } else if (iError == MKEvent.ERROR_NETWORK_DATA) {
	//
	// }
	// // ...
	// }
	//
	// @Override
	// public void onGetPermissionState(int iError) {
	// if (iError != 0) {
	// Location.getInstance().m_bKeyRight = false;
	// } else {
	// Location.getInstance().m_bKeyRight = true;
	// }
	// }
	// }
}