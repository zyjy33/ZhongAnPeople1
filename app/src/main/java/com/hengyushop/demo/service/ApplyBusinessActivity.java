package com.hengyushop.demo.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请成为商家
 *
 * @author Administrator
 *
 */
public class ApplyBusinessActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {

	private ViewPager vp;
	private ImageView iv_fanhui;
	private ViewPagerAdapter vpAdapter;
	private Button btn_settle_accounts;
	public static Handler handlerll;
	ArrayList<AdvertDao1> images;
	List<String> list;
	public static AQuery mAq;
	// String[] pics;
	String ad_url;
	Bitmap bitmap_tx, bitmap_touxiang;
	ImageView iv;
	// 引导图片资源
	private static final int[] pics = { R.drawable.sj_sq1 };
	// private static final int[] pics = { R.drawable.sj_sq1, R.drawable.pic1};
	// R.drawable.pic4,R.drawable.pic5
	// ,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8};

	// 底部小点图片

	private ImageView[] dots;

	// 记录当前选中位置

	private int currentIndex;

	private List<View> views;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loadguanggao();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shenqing_shangjia);// activity_shenqing_shangjia
		// activity_shengqing
		mAq = new AQuery(ApplyBusinessActivity.this);
		loadguanggao();

		handlerll = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 2:
						finish();
						break;
				}
			}
		};

		// iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		// iv_fanhui.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// finish();
		// }
		// });

		// iv_fanhui.setOnClickListener(this);
		btn_settle_accounts = (Button) findViewById(R.id.btn_settle_accounts);
		btn_settle_accounts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent(ApplyBusinessActivity.this,
							ApplyBusiness1Activity.class);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

	}

	private void initDots() {

		LinearLayout ss = (LinearLayout) findViewById(R.id.ss);
		ImageView iv_anniu1 = (ImageView) findViewById(R.id.iv_anniu1);
		ImageView iv_anniu2 = (ImageView) findViewById(R.id.iv_anniu2);
		ImageView iv_anniu3 = (ImageView) findViewById(R.id.iv_anniu3);
		ImageView iv_anniu4 = (ImageView) findViewById(R.id.iv_anniu4);
		ImageView iv_anniu5 = (ImageView) findViewById(R.id.iv_anniu5);
		dots = new ImageView[pics.length];

		// 循环取得小点图片

		for (int i = 0; i < pics.length; i++) {

			// if (i == 0) {
			// iv_anniu1.setVisibility(View.VISIBLE);
			// }
			if (i == 1) {
				iv_anniu1.setVisibility(View.VISIBLE);
				iv_anniu2.setVisibility(View.VISIBLE);
			}
			if (i == 2) {
				iv_anniu3.setVisibility(View.VISIBLE);
			}
			if (i == 3) {
				iv_anniu4.setVisibility(View.VISIBLE);
			}
			if (i == 4) {
				iv_anniu5.setVisibility(View.VISIBLE);
			}
			// 得到一个LinearLayout下面的每一个子元素

			dots[i] = (ImageView) ss.getChildAt(i);

			dots[i].setEnabled(true);// 都设为灰色

			dots[i].setOnClickListener(this);

			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应

		}

		currentIndex = 0;

		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态

	}

	private void setCurView(int position)

	{

		if (position < 0 || position >= pics.length) {

			return;

		}

		vp.setCurrentItem(position);

	}

	private void setCurDot(int positon)

	{

		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {

			return;

		}

		dots[positon].setEnabled(false);

		dots[currentIndex].setEnabled(true);

		currentIndex = positon;

	}

	// 当滑动状态改变时调用

	public void onPageScrollStateChanged(int arg0) {

		// TODO Auto-generated method stub

	}

	// 当当前页面被滑动时调用

	public void onPageScrolled(int arg0, float arg1, int arg2) {

		// TODO Auto-generated method stub

	}

	// 当新的页面被选中时调用

	public void onPageSelected(int arg0) {

		// 设置底部小点选中状态

		setCurDot(arg0);

	}

	public void onClick(View v) {
		int position = (Integer) v.getTag();

		setCurView(position);

		setCurDot(position);

		// switch (v.getId()) {
		// case R.id.iv_fanhui:
		// finish();
		// break;
		//
		// default:
		// break;
		// }
	}

	public class ViewPagerAdapter extends PagerAdapter {

		// 界面列表

		private List<View> views;

		public ViewPagerAdapter(List<View> views) {

			this.views = views;

		}

		// 销毁arg1位置的界面

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {

			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

			// TODO Auto-generated method stub

		}

		// 获得当前界面数

		@Override
		public int getCount() {

			if (views != null)

			{

				return views.size();

			}

			return 0;

		}

		// 初始化arg1位置的界面

		@Override
		public Object instantiateItem(View arg0, int arg1) {

			((ViewPager) arg0).addView(views.get(arg1), 0);

			return views.get(arg1);

		}

		// 判断是否由对象生成界面

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return (arg0 == arg1);

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {

			// TODO Auto-generated method stub

			return null;

		}

		@Override
		public void startUpdate(View arg0) {

			// TODO Auto-generated method stub

		}

	}

	private void loadguanggao() {
		try {
			list = new ArrayList<String>();
			// 广告滚动
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_adbanner_list?advert_id=1017",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							System.out
									.println("======输出33=============" + arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
									JSONArray array = object
											.getJSONArray("data");
									int len = array.length();
									// ArrayList<AdvertDao1>
									images = new ArrayList<AdvertDao1>();
									for (int i = 0; i < len; i++) {
										AdvertDao1 ada = new AdvertDao1();
										JSONObject json = array
												.getJSONObject(i);
										ada.setId(json.getString("id"));
										ada.setAd_url(json.getString("ad_url"));
										ad_url = ada.getAd_url();
										// ImageLoader
										// imageLoader=ImageLoader.getInstance();
										// imageLoader.displayImage(RealmName.REALM_NAME_HTTP
										// + ad_url, ling_tip);
										// imageLoader.clearMemoryCache();//清除内存缓存
										images.add(ada);
										list.add(ad_url);
										// new Thread(getPicByUrl2).start();
									}
								} else {
								}
								if (list.size() > 0) {
									getzhou();
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1);
							System.out.println("======输出1=============" + arg0);
							System.out.println("======输出2=============" + arg1);
						}

					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void getzhou() {
		// TODO Auto-generated method stub
		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		try {

			// 将ArrayList<Integer>转化成int[]数组
			ArrayList<Integer> tmpList1 = new ArrayList<Integer>();// 这个必须有，因为容器类不接受基本类型，只接受对象的引用
			int num = 2;
			tmpList1.add(new Integer(num));
			tmpList1.add(new Integer(2));
			tmpList1.add(new Integer(num + 1));

			// 创建数组
			// String tmpInteger1[] = new String[list.size()];
			// String tmpInt1[] = new String[list.size()];
			// list.toArray(tmpInteger1);
			// // 赋值输出
			// for (int i = 0; i < tmpInteger1.length; i++) {
			// tmpInt1[i] = tmpInteger1[i];
			// System.out.print("==========================================="+tmpInt1[i]
			// + " ");
			// }

			// List<String> list=new ArrayList<String>();
			// list.add("王利虎");
			// list.add("张三");
			// list.add("李四");
			// http://ju918.com/upload/201706/06/201706061608344247.jpg
			// list.add("http://ju918.com/upload/201706/06/201706061608344247.jpg");
			// list.add("http://ju918.com/upload/201706/06/201706061608344247.jpg");
			// list.add("http://ju918.com/upload/201706/06/201706061608344247.jpg");

			// System.out.println("list.size()-------------------"+list.size());
			// int size=list.size();
			// pics = (String[])list.toArray(new String[size]);
			// for(int i=0;i<pics.length;i++){
			// System.out.println("-------------------"+pics[i]);
			// }

			// 初始化引导图片列表

			for (int i = 0; i < pics.length; i++) {

				ImageView iv = new ImageView(this);

				iv.setLayoutParams(mParams);
				iv.setImageResource(pics[i]);
				// Bitmap bitmap_dihua =
				// BitmapFactory.decodeResource(getResources(),
				// R.drawable.pic1);
				// iv.setImageBitmap(bitmap_touxiang);
				// iv.setBackgroundResource(R.drawable.sj_sq1);
				// ImageLoader imageLoader=ImageLoader.getInstance();
				// imageLoader.displayImage(RealmName.REALM_NAME_HTTP + pics[i],
				// iv);
				// imageLoader.clearMemoryCache();//清除内存缓存
				// mAq.id(iv).image(RealmName.REALM_NAME_HTTP + pics[i]);
				views.add(iv);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		vp = (ViewPager) findViewById(R.id.viewpager);

		// LinearLayout layout = (LinearLayout) findViewById(R.id.ll_buju);
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
		// layout.getLayoutParams();
		// params.height = dm.heightPixels/2;
		// params.width = dm.widthPixels;
		// layout.setLayoutParams(params);

		vpAdapter = new ViewPagerAdapter(views);

		vp.setAdapter(vpAdapter);

		// 绑定回调

		vp.setOnPageChangeListener(this);

		// 初始化底部小点

		initDots();
	}

	// Runnable getPicByUrl2 = new Runnable() {
	// @Override
	// public void run() {
	// try {
	// String img_url2 = RealmName.REALM_NAME_HTTP +ad_url;
	// System.out.println("img_url2=============="+img_url2);
	// bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
	// // Bitmap bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);//
	// 这个时候的图片已经被处理成圆形的了
	// // touxiang = BitUtil.bitmaptoString(bitMap_tx);
	// System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
	// if (list.size() > 0) {
	// getzhou();
	// }
	// // iv.setImageBitmap(bitmap_touxiang);
	// } catch (Exception e) {
	// Log.i("ggggg", e.getMessage());
	// }
	// }
	// };
}
