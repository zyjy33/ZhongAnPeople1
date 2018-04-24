package com.hengyushop.demo.hotel;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotelImagesAdapter extends BaseAdapter {
	private ArrayList<HotelImageDo> lists;
	private Context context;
	private ImageLoader imageLoader;

	public HotelImagesAdapter(ArrayList<HotelImageDo> lists, Context context,
			ImageLoader imageLoader) {

		this.context = context;
		this.imageLoader = imageLoader;
		this.lists = lists;
	}

	@Override
	public int getCount() {

		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {

		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {

		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.hotel_image, null);
			holder.item_image = (ImageView) view.findViewById(R.id.item_image);
			holder.item_tag = (TextView) view.findViewById(R.id.item_tag);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		imageLoader.displayImage(lists.get(arg0).getUrl(), holder.item_image);
		holder.item_tag.setText(lists.get(arg0).getTag());
		System.out.println("Adapter:" + lists.get(arg0).getUrl());
		return view;
	}

	public class ViewHolder {
		private TextView item_tag;
		private ImageView item_image;
	}

}
