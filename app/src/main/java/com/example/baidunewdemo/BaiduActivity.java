package com.example.baidunewdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.zams.www.R;

public class BaiduActivity extends Activity {
	private MapView mMapView = null;
	MyBaiduApp app;
	private ImageView pos_IV;
	private MapController mMapController = null;
	MKMapViewListener mMapListener = null;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	MKSearch mMKSearch;
	LocationData locData = null;
	GeoPoint geoPoint;// 当前位置

	MyLocationOverlay myLocationOverlay = null;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 定位当前位置调用reverseGeocode方法获取实现接口类里的当前位置Toast信息
			mMKSearch.reverseGeocode(geoPoint);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baidu);
		findViews();
		setApp();
		initMapView();
		getListener();
		setListener();
		getMapClient();
	}

	/*
	 * 获取监听
	 */
	private void getListener() {
		mMapListener = new MKMapViewListener() {

			@Override
			public void onMapMoveFinish() {
				// T在此处理地图移动完成消息回调
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {

				// TODO Auto-generated method stub
				String title = "";
				if (mapPoiInfo != null) {
					title = mapPoiInfo.strText;
					Toast.makeText(BaiduActivity.this, title,
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap bitmap) {
				// 回调图片数据，保存在"/mnt/sdcard/test"目录下
				System.out.println("test" + "onGetCurrentMap");
				File file = new File("/mnt/sdcard/baidupos.png");
				FileOutputStream out;
				try {
					out = new FileOutputStream(file);
					// 获取状态栏高度
					Rect frame = new Rect();
					BaiduActivity.this.getWindow().getDecorView()
							.getWindowVisibleDisplayFrame(frame);
					int statusBarHeight = frame.top;
					bitmap = Bitmap
							.createBitmap(
									bitmap,
									Integer.parseInt(""
											+ (Config.deviceWidthHeight[0] / 2 - Config.deviceWidthHeight[0] / 6)),
									Integer.parseInt(""
											+ (Config.deviceWidthHeight[1]
											/ 2
											- Config.deviceWidthHeight[0]
											/ 6 - statusBarHeight / 2)),
									Config.deviceWidthHeight[0] / 3,
									Config.deviceWidthHeight[0] / 3);
					pos_IV.setImageBitmap(BitmapUtils.toRoundCorner(bitmap, 15));
					if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
						out.flush();
						out.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMapAnimationFinish() {
				// TODO Auto-generated method stub

			}
		};

	}

	public class MyMKSearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo res, int error) {
			// 获取地理位置信息
			if (error != 0) {
				String str = String.format("错误号：%d", error);
				return;
			}
			String posInfo = String.format("纬度：%f 经度：%f 地址：%s\r\n",
					res.geoPt.getLatitudeE6() / 1e6,
					res.geoPt.getLongitudeE6() / 1e6,
					res.addressComponents.city + res.addressComponents.district
							+ res.addressComponents.street
							+ res.addressComponents.streetNumber);
			double zhou = res.geoPt.getLatitudeE6() / 1e6;
			System.out.println("------------------" + zhou);

			Toast.makeText(BaiduActivity.this, posInfo, 1).show();
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

	}

	private void getMapClient() {
		myLocationOverlay = new MyLocationOverlay(mMapView);
		locData = new LocationData();
		myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		mMapView.refresh();
	}

	/*
	 * 设置监听
	 */
	private void setListener() {

		mMapView.regMapViewListener(MyBaiduApp.getInstance().mBMapManager,
				mMapListener);

	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {

				return;
			}
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			myLocationOverlay.setData(locData);
			mMapView.refresh();
			geoPoint = new GeoPoint((int) (locData.latitude * 1e6),
					(int) (locData.longitude * 1e6));
			mMapController.animateTo(geoPoint, mHandler.obtainMessage(1));
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	/*
	 * 获取控件
	 */
	private void findViews() {
		// mMapView = (MapView) findViewById(R.id.bmapView);
		pos_IV = (ImageView) findViewById(R.id.pos_IV);
		Config.deviceWidthHeight = DensityUtil.getDeviceInfo(this);
		System.out.println("widthpx=" + Config.deviceWidthHeight[0]
				+ "**heightpx=" + Config.deviceWidthHeight[1]);
	}

	/*
	 * 设置APP
	 */
	private void setApp() {
		app = (MyBaiduApp) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(MyBaiduApp.strKey,
					new MyBaiduApp.MyGeneralListener());
		}

	}

	/*
	 * 初始化地图 数据
	 */
	private void initMapView() {
		mMapController = mMapView.getController();
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02 可以无偏差的显示在百度地图上
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPriority(LocationClientOption.GpsFirst);// gps优先
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocClient.setLocOption(option);
		mLocClient.start();
		mMapView.getController().setZoom(15);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);

		GeoPoint centerpt = mMapView.getMapCenter();
		int maxLevel = mMapView.getMaxZoomLevel();
		// int zoomlevel = mMapView.getZoomLevel();
		boolean isTraffic = mMapView.isTraffic();
		boolean isSatillite = mMapView.isSatellite();
		boolean isDoubleClick = mMapView.isDoubleClickZooming();
		mMapView.setLongClickable(true);

		// 初始化
		mMKSearch = new MKSearch();
		mMKSearch.init(app.mBMapManager, new MyMKSearchListener());

	}

	/*
	 * 按钮点击事件 获取截图，异步方法
	 */
	public void capturemapclick(View view) {
		mMapView.getCurrentMap();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_baidu, menu);
	// return true;
	// }

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		MyBaiduApp app = (MyBaiduApp) this.getApplication();
		if (app.mBMapManager != null) {
			app.mBMapManager.destroy();
			app.mBMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
		}
	}
}
