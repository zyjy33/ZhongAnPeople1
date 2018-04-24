package com.hengyushop.demo.hotel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class HotelItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HotelItem> list;
	private ImageLoader imageLoader;

	public HotelItemAdapter(Context context, ArrayList<HotelItem> list,
							ImageLoader imageLoader) {
		this.context = context;
		this.list = list;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		System.out.println("adapter" + list.size());
		return list.size();

	}

	public void putData() {
		notifyDataSetChanged();

	}

	public void remove(int index) {
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);

	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context,
					R.layout.hotel_listitem_select_result, null);
			holder.v2 = (TextView) view.findViewById(R.id.address);
			holder.v1 = (TextView) view.findViewById(R.id.tv_hotel_name);
			holder.imageView = (ImageView) view.findViewById(R.id.img_hotel);
			holder.start = (TextView) view.findViewById(R.id.start);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.v2.setText(list.get(index).getAddress());
		holder.v1.setText(list.get(index).getName());
		imageLoader.displayImage(list.get(index).getImg(), holder.imageView);
		holder.start.setText(list.get(index).getStart() + "星级");
		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, start;
		private ImageView imageView;
	}

}
