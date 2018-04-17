package com.android.hengyu.pub;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.hengyu.web.RealmName;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.WareInformationActivity;
import com.zams.www.WareInformationGalleryActivity;

public class MyWareInfromationViewPagerAdapter extends PagerAdapter {

	private List<Map<String, String>> list;
	private Context context;
	private ImageLoader loader;

	public MyWareInfromationViewPagerAdapter(
			List<Map<String, String>> allGriddatas, Context context,
			ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.list = allGriddatas;
		this.context = context;
		this.loader = loader;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView(context);
		((ViewPager) container).addView(imageView);
		loader.displayImage(
				RealmName.REALM_NAME + "/admin/"
						+ list.get(position).get("img"), imageView);
		final String id = list.get(position).get("id");

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,
						WareInformationGalleryActivity.class);
				intent.putExtra("id", id);
				Log.v("data2", id + "");
				context.startActivity(intent);
			}
		});

		return imageView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		// TODO Auto-generated method stub
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

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
