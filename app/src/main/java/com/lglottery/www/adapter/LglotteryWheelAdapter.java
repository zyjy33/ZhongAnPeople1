package com.lglottery.www.adapter;

import java.util.ArrayList;

import com.lglottery.www.activity.LglotteryGoActivity;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.Lottery_Gobean;
import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class LglotteryWheelAdapter extends AbstractWheelAdapter {
	private ArrayList<Lottery_Gobean> goBeans;

	// Image size
	private final int HEIGHT = LglotteryGoActivity.HEIGHT;

	// Slot machine symbols
	private final String items[] = new String[] { "A1类商品", "B1类商品", "C1类商品",
			"A2类商品", "B2类商品", "C2类商品" };

	// Layout inflater
	private Context context;

	/**
	 * Constructor
	 */
	public LglotteryWheelAdapter(Context context,
			ArrayList<Lottery_Gobean> goBeans) {
		this.context = context;
		this.goBeans = goBeans;
	}

	/**
	 * Loads image from resources
	 */

	@Override
	public int getItemsCount() {
		return items.length;
	}

	// Layout params for image view
	final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
			HEIGHT);

	@Override
	public View getItem(int index, View view, ViewGroup parent) {

		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.lottery_view, null);
			holder.textView = (TextView) view.findViewById(R.id.lottery_view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.textView.setLayoutParams(params);
		WLog.v("goBeans.get(index).getProName():"+goBeans.get(index).getProName());
		holder.textView.setText(goBeans.get(index).getProName());
		return view;
		/*
		 * SoftReference<Bitmap> bitmapRef = images.get(index); Bitmap bitmap =
		 * bitmapRef.get(); if (bitmap == null) { bitmap =
		 * loadImage(items[index]); images.set(index, new
		 * SoftReference<Bitmap>(bitmap)); } img.setImageBitmap(bitmap);
		 * 
		 * return img;
		 */}

	public class ViewHolder {
		public TextView textView;
	}
}