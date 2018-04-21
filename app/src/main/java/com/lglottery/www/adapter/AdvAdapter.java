package com.lglottery.www.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hengyushop.dao.AdvertDao1;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class AdvAdapter extends PagerAdapter {
	private ImageLoader imageLoader;
	private ArrayList<AdvertDao1> temps;
	private Context context;
	private Handler han;
	private DisplayImageOptions options;
	@SuppressWarnings("unused")
	public AdvAdapter(ArrayList<AdvertDao1> temps, Context cont, Handler han,ImageLoader imageLoader,DisplayImageOptions options) {
		this.temps = temps;
		this.imageLoader = imageLoader;
		this.context = cont;
		this.han = han;
		this.options  = options;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
		super.finishUpdate(container);
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}


	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = LinearLayout.inflate(context, R.layout.lanucher_image,
				null);
		((ViewPager) container).addView(view);
		ImageView imageView = (ImageView) view
				.findViewById(R.id.lanucher_image);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("shijian");
				Message msg = new Message();
				msg.what = 13;
				msg.obj = temps.get(position % temps.size());
				han.sendMessage(msg);
			}
		});
		// ImageView imageView = new ImageView(context);position %
		// lists.size()
		imageLoader.displayImage(temps.get(position % temps.size())
				.getImage(), imageView,options);
		System.out.println("位置：" + position % temps.size());
			/*
			 * imageLoader.displayImage("file://" + lists.get(position %
			 * lists.size()).getPath(), imageView, options);
			 */

		// if (temps.get(position).getId().length() != 0) {
		// }
		// mm = temps.get(position).getId();
			/*
			 * int com = temps.size(); if(position==0||position==1){ m1 = 0;
			 * }else if (position==com) { m1 = position; }else { m1 =
			 * position-1; }
			 */
		// m1 = position;

		return view;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

}