package com.lglottery.www.widget;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.zams.www.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

/**
 * ���չʾ�ؼ���ͼƬ����չʾ��
 * 
 * @author ��ͳǿ
 */
public class MyPoster_activity_View extends FrameLayout{
	// ����ļ����¼�
	private MyPosterOnClick clickListener = null;
	private Context context;
	// ����չʾ�Ŀؼ�
	public static ViewPager viewPage = null;
	// ����չʾ�ؼ���adapter
	private PageAdapter adapter = null;
	// Ҫչʾ�Ľ���
	private List<ImageView> views = null;
	// չʾͼƬ��ҳ�루һ�ŵ��־չʾ����ͼ��
	private List<ImageView> imgs = null;
	// ����ʱ���¼�����
	private PageChangeListener listener = null;
	// ����ǰ����ҳ���ı�־�㲼��
	private LinearLayout layoutShowPoint = null;
	// ����ؼ��ڵ�ͼƬ��drawable������
	private ArrayList<String> imgDrawable = null;
	// �Ƿ���ʾҳ���־��
	private boolean isPointOut = true;
	// //�Ƿ񲻶ϵĹ���
	// private boolean isScroll=true;
	// �����߳�����ʱ�䡾�롿
	private int sleepTime = 1;
	private int curPosition = 0;// ��ǰҳ��
	private int maxPage = 0;// ���ҳ��
	public static AQuery mQuery;
	public static boolean type = false;
	// �Զ��������߳�
	private ScheduledExecutorService scheduledExecutorService;

	public MyPoster_activity_View(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.common_viewpager_activity_1, this, true);
		listener = new PageChangeListener();
		viewPage = (ViewPager) findViewById(R.id.common_viewpager);

		views = new ArrayList<ImageView>();
		layoutShowPoint = (LinearLayout) findViewById(R.id.common_point);
		// ((android.widget.RelativeLayout.LayoutParams)
		// layoutShowPoint.getLayoutParams())
		// .setMargins(0,0,0,MyApplication.getAdapH(10));

		// ʵ��һ��PagerAdapter
	
		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					viewPage.getContext(), new AccelerateInterpolator());
//			 scroller.setFixedDuration(5000);
			mScroller.set(viewPage, scroller);
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
	}
	class FixedSpeedScroller extends Scroller {

		private int mDuration = 500;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator,
				boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}
	}
	private class ScrollTask implements Runnable {

		public void run() {
			if (handler != null)
				handler.sendMessage(Message.obtain(handler));
		}

	}
	/**
	 * �رչ���
	 */
	public void puseExecutorService() {
		if (scheduledExecutorService != null) {
			scheduledExecutorService.shutdown();
		}
	}
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageForEmptyUri(R.color.no_color)
	.showImageOnFail(R.color.no_color).resetViewBeforeLoading()
	.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.displayer(new SimpleBitmapDisplayer()).build();
	/**
	 * ��������
	 */
	public void setData(ArrayList<String> _imgDrawable, ImageLoader imageLoader) {
		if (_imgDrawable == null)
			return;
		imgDrawable = _imgDrawable;
//		ImageView imgView = null;
		ImageView img = null;
		if (isPointOut)
			imgs = new ArrayList<ImageView>();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 0, 5, 0);
		System.out.println("imageload:"+imgDrawable.size());
		for (int i = 0; i < imgDrawable.size(); i++) {
			if (isPointOut) {
				img = new ImageView(context);
				img.setBackgroundResource((i == 0) ? R.drawable.common_point_normal
						: R.drawable.common_point_select);
				img.setLayoutParams(params);
				imgs.add(img);
				layoutShowPoint.addView(img);
			}
			// ����ViewPager��ʾ��ҳ������
			ImageView  imgView = new ImageView(context);
			imgView.setScaleType(ScaleType.FIT_XY);
			imgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			// imgView.setBackgroundDrawable(imgDrawable[i]);
			System.out.println("���ص�ͼƬ��Դ:"+_imgDrawable.get(i));
			
//			imageLoader.displayImage(uri, imageView);
			
			
			final int position = i;
			if (clickListener != null) {
				imgView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clickListener.onMyclick(position);
					}
				});
			}
			views.add(imgView);
		}
//		viewPage.setCurrentItem(imgDrawable.size() * 100);
//		viewPage.setCurrentItem(0);
		viewPage = (ViewPager) findViewById(R.id.common_viewpager);
		
		adapter = new PageAdapter(views, imageLoader, imgDrawable);
		viewPage.setAdapter(adapter);
		viewPage.setOnPageChangeListener(listener);
	}

	/**
	 * ��������
	 * 
	 * @param _imgDrawable
	 */
	public void setData(ArrayList<String> _imgDrawable, boolean isPointOut,
			ImageLoader imageLoader) {
		this.isPointOut = isPointOut;
		setData(_imgDrawable, imageLoader);
		if (!isPointOut && layoutShowPoint != null) {
			layoutShowPoint.setVisibility(LinearLayout.GONE);
		}
	}

	/**
	 * ��������
	 * 
	 * @param _imgDrawable
	 */
	public void setData(ArrayList<String> _imgDrawable,
			MyPosterOnClick _Listener, boolean isPointOut,
			ImageLoader imageLoader,boolean isRun) {
		setMyOnClickListener(_Listener);
		this.isPointOut = isPointOut;
		setData(_imgDrawable, imageLoader);
		if (!isPointOut && layoutShowPoint != null) {
			layoutShowPoint.setVisibility(LinearLayout.GONE);
		}
		if (isRun) {
			scheduledExecutorService = Executors
					.newSingleThreadScheduledExecutor();
			// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
			scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1,
					4, TimeUnit.SECONDS);
		}
	}

	/**
	 * ��������
	 * 
	 * @param _imgDrawable
	 */
	public void setData(ArrayList<String> _imgDrawable,
			MyPosterOnClick _Listener, boolean isPointOut, int time,
			ImageLoader imageLoader,boolean isRun) {
		setData(_imgDrawable, _Listener, isPointOut, imageLoader,isRun);
		sleepTime = time;
		// thread= new Thread(this);
		// thread.start();
	}

	/**
	 * ���õ������
	 */
	public void setMyOnClickListener(MyPosterOnClick _Listener) {
		this.clickListener = _Listener;
	}

	/**
	 * �������
	 */
	public void leftScroll() {
		if (viewPage != null)
			viewPage.setCurrentItem((--curPosition <= 0) ? maxPage
					: curPosition);
	}

	/**
	 * ���ҹ���
	 */
	public void rightScroll() {
		if (viewPage != null)
			viewPage.setCurrentItem((++curPosition >= maxPage) ? 0
					: curPosition);
	}

	/**
	 * ���õ�ǰҳ
	 */
	public void setCurrentPage(int position) {
		if (imgDrawable != null)
			viewPage.setCurrentItem(position + (imgDrawable.size() * 100));
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			rightScroll();
		}
	};

	/**
	 * ������ݣ��ڴ����
	 */
	public void clearMemory() {
		// TODO Auto-generated method stub
	 
		clickListener = null;
		context = null;
		// ����չʾ�Ŀؼ�
		viewPage = null;
		// ����չʾ�ؼ���adapter
		adapter = null;
		// Ҫչʾ�Ľ���
		if (views != null) {
			views.clear();
			views = null;
		}
		// չʾͼƬ��ҳ�루һ�ŵ��־չʾ����ͼ��
		if (imgs != null) {
			imgs.clear();
			imgs = null;
		}
		// ����ʱ���¼�����
		listener = null;
		// ����ǰ����ҳ���ı�־�㲼��
		layoutShowPoint = null;
		// ����ؼ��ڵ�ͼƬ��drawable������
		imgDrawable = null;
		handler = null;
	}

	/** =========================�ڲ���===>ҳ���л�����================================ */
	class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			curPosition = position;
			if (isPointOut && imgs != null) {
				for (int i = 0; i < imgs.size(); i++) {
					// ���ǵ�ǰѡ�е�page����СԲ������Ϊδѡ�е�״̬
					imgs.get(i)
							.setBackgroundResource(
									(position % imgs.size() != i) ? R.drawable.common_point_normal
											: R.drawable.common_point_select);
				}
			}
		}

	}

	class PageAdapter extends PagerAdapter {
		List<ImageView> views;
		ImageLoader imageLoader;
		ArrayList<String> images;
		public PageAdapter(List<ImageView> views,ImageLoader imageLoader,ArrayList<String> images) {
			this.views = views;
			this.imageLoader = imageLoader;
			mQuery = new AQuery(context);
			this.images = images;
		}

		/**
		 * Ҫ��ʾ��ҳ��ĸ���
		 */
		@Override
		public int getCount() {
			// ���ó����ֵ�Ա�ѭ������
			int cont = ((views == null) ? 0 : Integer.MAX_VALUE);
			maxPage = cont;
			return cont;
		}

		/**
		 * ��ȡһ��ָ��ҳ���title���� �������null��ζ�����ҳ��û�б��⣬Ĭ�ϵ�ʵ�־��Ƿ���null
		 * ���Ҫ��ʾҳ���ϵ�title��˷�������ʵ��
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// System.out.println("==����==>"+titles[position]);
			// return titles[position];
			return null;
		}

		/**
		 * ����ָ��position��ҳ�档����������Ὣҳ��ӵ�����container�С�
		 * 
		 * @param container
		 *            ��������ʵ���ŵ�container�У������container����viewPager
		 * @return ����һ���ܱ�ʾ��ҳ��Ķ��󣬲�һ��Ҫ��view������������������ҳ�档
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			try {
				((ViewPager) container).addView(
						views.get(position % views.size()), 0);
//				imageLoader.displayImage(images.get(position % views.size()), views.get(position % views.size()));
				mQuery.id(views.get(position % views.size())).image(images.get(position % views.size()));
				type = true;
			} catch (Exception e) {
			}
			
			return (views.size() > 0) ? views.get(position % views.size())
					: null;
		}

		/**
		 * �˷����Ὣ������ָ��ҳ����Ƴ� �÷����еĲ���container��position��instantiateItem�����е�����һ��
		 * 
		 * @param object
		 *            ���object ���� instantiateItem�����з��ص��Ǹ�Object
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// ������Ҫ��ѭ�����������Բ��ܽ����������
			// if(position<views.size())
			// {
			// container.removeView(views.get(position));
			// }
		}

		/**
		 * ����������ǱȽ�һ��������ҳ���instantiateItem�������ص�Object�ǲ���ͬһ��
		 * 
		 * @param arg0
		 *            ViewPager�е�һ��ҳ��
		 * @param arg1
		 *            instantiateItem�������صĶ���
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	// @Override
	// public void onClick(View v)
	// {
	// // TODO Auto-generated method stub
	// switch(v.getId())
	// {
	// case R.id.showNextPageBtn:
	// Intent intent = new Intent();
	// intent.setClass(this, HomeActivity.class);
	// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// startActivity(intent);
	// finish();
	// intent=null;
	// clearMemory();
	// break;
	// }
	// }

	// @Override
	// public void clearMemory() {
	// // TODO Auto-generated method stub
	//
	// }

}
