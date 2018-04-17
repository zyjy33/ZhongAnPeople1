package com.hengyushop.demo.my;

import java.util.ArrayList;
import java.util.List;

import com.zams.www.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的订单
 * 
 * @author Administrator
 * 
 */
public class Myorder3Acitivity extends FragmentActivity {
	private Intent intent;
	private Context context;
	private ViewPager viewPager;// 页卡内容
	private ImageView imageView, imageView1, imageView2, imageView3,
			imageView4, imageView5;// 动画图片
	private TextView voiceAnswer, healthPedia, pDected, jingping, yiwancheng;// 选项名称
	private List<Fragment> fragments;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int selectedColor, unSelectedColor;
	private ImageButton button;
	/** 页卡总数 **/
	private static final int pageSize = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myorder3);
		initView();
	}

	class MyOnItemClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			intent = new Intent();
			switch (v.getId()) {
			// case R.id.bt_left:// 返回
			// finish();
			// break;
			}

		}
	}

	private void initView() {
		try {

			selectedColor = getResources().getColor(R.color.juhongse);
			unSelectedColor = getResources().getColor(
					R.color.tab_title_normal_color);

			InitImageView();
			InitTextView();
			InitViewPager();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 初始化Viewpager页
	 */
	private void InitViewPager() {
		try {
			viewPager = (ViewPager) findViewById(R.id.vPager);
			fragments = new ArrayList<Fragment>();

			fragments.add(new AllorderFragment());
			fragments.add(new CompleteyesFragment());
			fragments.add(new PaymentbeenFragment());
			fragments.add(new ForDeliveryFragment());
			fragments.add(new ForgoodsFragment());

			viewPager.setAdapter(new myPagerAdapter(
					getSupportFragmentManager(), fragments));
			viewPager.setCurrentItem(3);
			viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		try {

			voiceAnswer = (TextView) findViewById(R.id.tab_1);// 1
			healthPedia = (TextView) findViewById(R.id.tab_2);// 2
			pDected = (TextView) findViewById(R.id.tab_3);// 3
			jingping = (TextView) findViewById(R.id.tab_4);// 4
			yiwancheng = (TextView) findViewById(R.id.tab_5);

			voiceAnswer.setTextColor(unSelectedColor);
			healthPedia.setTextColor(unSelectedColor);
			pDected.setTextColor(unSelectedColor);
			jingping.setTextColor(selectedColor);
			yiwancheng.setTextColor(unSelectedColor);

			voiceAnswer.setText("全部");
			healthPedia.setText("待付款");
			pDected.setText("已付款");
			jingping.setText("待发货");
			yiwancheng.setText("待收货");

			voiceAnswer.setOnClickListener(new MyOnClickListener(0));
			healthPedia.setOnClickListener(new MyOnClickListener(1));
			pDected.setOnClickListener(new MyOnClickListener(2));
			jingping.setOnClickListener(new MyOnClickListener(3));
			yiwancheng.setOnClickListener(new MyOnClickListener(4));
			// button.setOnClickListener(new MyOnItemClickListener());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */

	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		imageView1 = (ImageView) findViewById(R.id.cursor1);
		imageView2 = (ImageView) findViewById(R.id.cursor2);
		imageView3 = (ImageView) findViewById(R.id.cursor3);
		imageView4 = (ImageView) findViewById(R.id.cursor4);
		imageView5 = (ImageView) findViewById(R.id.cursor5);

		// bmpW =
		// BitmapFactory.decodeResource(getResources(),R.drawable.tupian).getWidth();//
		// 获取图片宽度
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// int screenW = dm.widthPixels;// 获取分辨率宽度
		// offset = (screenW / pageSize - bmpW) / 4;//
		// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
		// // = 偏移量

		// Matrix matrix = new Matrix();
		// System.out.println("============"+offset);
		// matrix.postTranslate(offset, 0);
		// System.out.println("============"+offset);
		// imageView1.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {

			switch (index) {
			case 0:
				voiceAnswer.setTextColor(selectedColor);
				healthPedia.setTextColor(unSelectedColor);
				pDected.setTextColor(unSelectedColor);
				jingping.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.VISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 1:
				healthPedia.setTextColor(selectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				pDected.setTextColor(unSelectedColor);
				jingping.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.VISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 2:
				pDected.setTextColor(selectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				healthPedia.setTextColor(unSelectedColor);
				jingping.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.VISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 3:
				jingping.setTextColor(selectedColor);
				pDected.setTextColor(unSelectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				healthPedia.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.VISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 4:
				yiwancheng.setTextColor(selectedColor);
				jingping.setTextColor(unSelectedColor);
				pDected.setTextColor(unSelectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				healthPedia.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.VISIBLE);
				break;
			}
			viewPager.setCurrentItem(index);
		}

	}

	/**
	 * 为选项卡绑定监听器
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 4 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 4;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				voiceAnswer.setTextColor(selectedColor);
				healthPedia.setTextColor(unSelectedColor);
				pDected.setTextColor(unSelectedColor);
				jingping.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.VISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 1:
				healthPedia.setTextColor(selectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				pDected.setTextColor(unSelectedColor);
				jingping.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.VISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 2:
				pDected.setTextColor(selectedColor);
				jingping.setTextColor(unSelectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				healthPedia.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.VISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 3:
				jingping.setTextColor(selectedColor);
				pDected.setTextColor(unSelectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				healthPedia.setTextColor(unSelectedColor);
				yiwancheng.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.VISIBLE);
				imageView5.setVisibility(View.INVISIBLE);
				break;
			case 4:
				yiwancheng.setTextColor(selectedColor);
				jingping.setTextColor(unSelectedColor);
				pDected.setTextColor(unSelectedColor);
				voiceAnswer.setTextColor(unSelectedColor);
				healthPedia.setTextColor(unSelectedColor);
				imageView1.setVisibility(View.INVISIBLE);
				imageView2.setVisibility(View.INVISIBLE);
				imageView3.setVisibility(View.INVISIBLE);
				imageView4.setVisibility(View.INVISIBLE);
				imageView5.setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}

}
