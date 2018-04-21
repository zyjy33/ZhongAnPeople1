package com.hengyushop.movie.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * @author yangyu
 *	åŠŸèƒ½æè¿°ï¼šViewPageré€‚é…å™¨ï¼Œç”¨æ¥ç»‘å®šæ•°æ®å’Œview
 */
public class ViewPagerAdapter extends PagerAdapter {

	//ç•Œé¢åˆ—è¡¨
	private ArrayList<View> views;

	public ViewPagerAdapter (ArrayList<View> views){
		this.views = views;
	}

	/**
	 * èŽ·å¾—å½“å‰ç•Œé¢æ•?
	 */
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	/**
	 * åˆå§‹åŒ–positionä½ç½®çš„ç•Œé?
	 */
	@Override
	public Object instantiateItem(View view, int position) {

		((ViewPager) view).addView(views.get(position), 0);

		return views.get(position);
	}

	/**
	 * åˆ¤æ–­æ˜¯å¦ç”±å¯¹è±¡ç”Ÿæˆç•Œé?
	 */
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

	/**
	 * é”?æ¯positionä½ç½®çš„ç•Œé?
	 */
	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeView(views.get(position));
	}
}
