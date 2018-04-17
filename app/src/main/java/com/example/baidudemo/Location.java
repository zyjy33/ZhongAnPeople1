package com.example.baidudemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.os.Process;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.hengyushop.demo.at.AppManager;
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

	public LocationClient mLocationClient = null;
	public GeofenceClient mGeofenceClient;
	public static String mData, sheng_1, shi_1, xian_1;
	public MyLocationListenner myListener = new MyLocationListenner();
	public String mTv;
	public NotifyLister mNotifyer = null;
	public Vibrator mVibrator01;
	public static String TAG = "LocTestDemo";

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

		mLocationClient = new LocationClient(this);
		/**
		 * ——————————————————————————————————————————————————————————————————
		 * 这里的AK和应用签名包名绑定，如果使用在自己的工程中需要替换为自己申请的Key
		 * ——————————————————————————————————————————————————————————————————
		 */
		mLocationClient.setAK("0Fa49070e3d9a18fb1df084293c5a335");
		mLocationClient.registerLocationListener(myListener);
		mGeofenceClient = new GeofenceClient(this);
		// 位置提醒相关代码
		// mNotifyer = new NotifyLister();
		// mNotifyer.SetNotifyLocation(40.047883,116.312564,3000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
		// mLocationClient.registerNotify(mNotifyer);

		super.onCreate();
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
	}

	/**
	 * 显示请求字符串
	 *
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if (mTv != null)
				// mTv.setText(mData);
				System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			StringBuffer city = new StringBuffer(256);
			// sb.append("time : ");
			// sb.append(location.getTime());
			// sb.append("\nerror code : ");
			// sb.append(location.getLocType());
			// sb.append("\nlatitude : ");
			// sb.append(location.getLatitude());
			// sb.append("\nlontitude : ");
			// sb.append(location.getLongitude());
			// sb.append("\nradius : ");
			// sb.append(location.getRadius());
			// if (location.getLocType() == BDLocation.TypeGpsLocation){
			// sb.append("\nspeed : ");
			// sb.append(location.getSpeed());
			// sb.append("\nsatellite : ");
			// sb.append(location.getSatelliteNumber());
			// } else if (location.getLocType() ==
			// BDLocation.TypeNetWorkLocation){
			/**
			 * 格式化显示地址信息
			 */
			// sb.append("\n省：");
			// sb.append(location.getProvince());
			// sb.append("\n");
			// sb.append(location.getCity());
			// sb.append("\n区/县：");
			// sb.append(location.getDistrict());
			// sb.append("\naddr : ");
			// sb.append(location.getAddrStr());

			// 定位当前城市
			city.append(location.getCity());
			// 定位当前地址
			sb.append(location.getProvince());
			sb.append("、");
			sb.append(location.getCity());

			sb.append("、");
			sb.append(location.getDistrict());

			StringBuffer sheng = new StringBuffer(256);
			sheng.append(location.getProvince());
			StringBuffer shi = new StringBuffer(256);
			shi.append(location.getCity());
			StringBuffer xian = new StringBuffer(256);
			xian.append(location.getDistrict());
			// }
			// sb.append("\nsdk version : ");
			// sb.append(mLocationClient.getVersion());
			// sb.append("\nisCellChangeFlag : ");
			// sb.append(location.isCellChangeFlag());
			sheng_1 = sheng.toString();
			shi_1 = shi.toString();
			xian_1 = xian.toString();

			logMsg(sb.toString());
			// logMsg(city.toString());
			String zhou = sb.toString();
			// System.out.println("1-------------------------------------"+zhou);

			String tv_city = city.toString();
			// System.out.println("tv_city-------------------------------------"+tv_city);
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			Editor editor = spPreferences.edit();
			editor.putString("dingwei", tv_city);
			editor.commit();
			// Log.i(TAG, sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
			mVibrator01.vibrate(1000);
		}
	}

	public String dProvince, dCity, dDistrict;
	public TextView mProvince, mCity, mDistrict;
	private static Location mInstance = null;
	public boolean m_bKeyRight = true;
	private MyUncaughtExceptionHandler uncaughtExceptionHandler;
	private String packgeName;
	public static final String strKey = "kSK5DdCaxWgKpNhv5le5lx7Z";

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

	public void reloadClient() {
		mLocationClient = new LocationClient(this);
		// mLocationClient.setAK("1939028c0d2440124051a00d9f773ae1");
		mLocationClient.registerLocationListener(myListener);
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

	public static Location getInstance() {
		return mInstance;
	}
}