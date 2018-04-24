package com.zams.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity {
	/**
	 * 关于引导页的界面
	 */
	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	SharedPreferences preferences;
	private ImageView i0;
	private ViewPager i1;
	/** Called when the activity is first created. */
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					preferences = getSharedPreferences("guide",
							Activity.MODE_PRIVATE);
					// 如果程序已经进入
					if (preferences.getString("flow", "").equals("yes")) {

						System.out.println("111111111111111111111111");
						getgaoguan();
						//					Intent intent = new Intent(GuideActivity.this,SecondActivity.class);
						//					startActivity(intent);
						//					AppManager.getAppManager().finishActivity();
					} else {
						System.out.println("2222222222222222222222222222");
						// i1.setVisibility(View.VISIBLE);
						i0.setVisibility(View.GONE);
						Intent intent = new Intent(GuideActivity.this,Guide2Activity.class);
						Editor editor = preferences.edit();
						editor.putString("flow", "yes");
						editor.commit();
						startActivity(intent);
						finish();
					}
					break;

				default:
					break;
			}
		};
	};

	//当Activity被销毁时会调用onDestory方法
	@Override
	protected void onDestroy() {
		super.onDestroy();
		BitmapDrawable bd1 = (BitmapDrawable)i0.getBackground();
		i0.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
		bd1.setCallback(null);
		bd1.getBitmap().recycle();
	}

	/**
	 * 判断是否有广告
	 */
	private void getgaoguan() {


		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/?advert_id=15",new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					System.out.println("-----------------" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if (status.equals("y")) {
						Intent intent = new Intent(GuideActivity.this,SecondActivity.class);
						startActivity(intent);
						AppManager.getAppManager().finishActivity();
					}else {
						Intent intent = new Intent(GuideActivity.this,MainFragment.class);
						startActivity(intent);
						AppManager.getAppManager().finishActivity();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {

				super.onFailure(arg0, arg1);
				System.out.println("异常-----------------" + arg1);
				Intent intent = new Intent(GuideActivity.this,MainFragment.class);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			}
		}, GuideActivity.this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item01);
		i0 = (ImageView) findViewById(R.id.i0);
		//		i0.setBackgroundResource(R.drawable.zams_qdy);
		Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.zams_qdy);
		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
		i0.setBackgroundDrawable(bd);
		i1 = (ViewPager) findViewById(R.id.i1);
		i0.setVisibility(View.VISIBLE);
		i1.setVisibility(View.GONE);
		//http://blog.csdn.net/mq2856992713/article/details/52005253
		i0.postDelayed(new Runnable() {

			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}, 3000);

	}

	// @Override
	// protected void onStart() {
	//
	// super.onStart();
	// //获得得到页面的引用对象
	// LayoutInflater inflater = getLayoutInflater();
	// pageViews = new ArrayList<View>();
	// pageViews.add(inflater.inflate(R.layout.item2, null));
	// pageViews.add(inflater.inflate(R.layout.item3, null));
	// pageViews.add(inflater.inflate(R.layout.item4, null));
	// pageViews.add(inflater.inflate(R.layout.item5, null));
	// pageViews.add(inflater.inflate(R.layout.item6, null));
	// // group是R.layou.main中的负责包裹小圆点的LinearLayout.
	// viewPager = (ViewPager)findViewById(R.id.i1);
	// viewPager.setAdapter(new GuidePageAdapter());
	// viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	// }
	//
	// /** 指引页面Adapter */
	// class GuidePageAdapter extends PagerAdapter {
	// @Override
	// public int getCount() {
	// return pageViews.size();
	// }
	// @Override
	// public boolean isViewFromObject(View arg0, Object arg1) {
	// return arg0 == arg1;
	// }
	// @Override
	// public int getItemPosition(Object object) {
	//
	// return super.getItemPosition(object);
	// }
	// @Override
	// public void destroyItem(View arg0, int arg1, Object arg2) {
	//
	// ((ViewPager) arg0).removeView(pageViews.get(arg1));
	// }
	// @Override
	// public Object instantiateItem(View arg0, int arg1) {
	//
	// ((ViewPager) arg0).addView(pageViews.get(arg1));
	// pageViews.get(4).setOnClickListener(new OnClickListener() {
	// public void onClick(View v) {
	//
	//
	// Intent intent = new Intent(GuideActivity.this,MainFragment.class);
	// startActivity(intent);
	// finish();
	// Editor editor = preferences.edit();
	// editor.putString("flow", "yes");
	// editor.commit();
	//
	//
	// }
	// });
	// return pageViews.get(arg1);
	// }
	// @Override
	// public void restoreState(Parcelable arg0, ClassLoader arg1) {
	//
	// }
	// @Override
	// public Parcelable saveState() {
	//
	// return null;
	// }
	// @Override
	// public void startUpdate(View arg0) {
	//
	// }
	// @Override
	// public void finishUpdate(View arg0) {
	//
	// }
	// }
	// /** 指引页面改监听器 */
	// class GuidePageChangeListener implements OnPageChangeListener {
	// public void onPageScrollStateChanged(int arg0) {
	//
	//
	// }
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	//
	// }
	// public void onPageSelected(int arg0) {
	// }
	// }
}