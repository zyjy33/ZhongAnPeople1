package com.lglottery.www.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.airplane.adapter.Distance;
import com.hengyushop.db.SharedUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class JDLayoutAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<JDbean> list;
	private SharedUtils utils;
	private ImageLoader imageLoader;
	private double lat, log;

	public JDLayoutAdapter(Context context, ArrayList<JDbean> list,
						   ImageLoader imageLoader, double lat, double log) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.imageLoader = imageLoader;
		this.lat = lat;
		this.log = log;
	}

	public void putLists(ArrayList<JDbean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (view == null || view.getTag(R.drawable.icon + index) == null) {
			holder = new ViewHolder();

			view = LinearLayout.inflate(context, R.layout.jd_item, null);
			holder.item0 = (ImageView) view.findViewById(R.id.item0);
			holder.item1 = (TextView) view.findViewById(R.id.item1);
			holder.item2 = (TextView) view.findViewById(R.id.item2);
			holder.item3 = (TextView) view.findViewById(R.id.item3);
			holder.item4 = (TextView) view.findViewById(R.id.item4);
			holder.item5 = (TextView) view.findViewById(R.id.item5);

			view.setTag(R.drawable.icon + index);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.icon + index);
		}
		imageLoader.displayImage(list.get(index).getPath(), holder.item0);
		holder.item1.setText(list.get(index).getName());
		holder.item2.setText(list.get(index).getScenSum());
		holder.item3.setText(list.get(index).getAmountAdv());
		holder.item4.setText(list.get(index).getAmount() + "元");

		System.out.println("lat:" + lat + "----lon:" + log);
		try {
			Double jl = Distance.GetDistance(
					Double.parseDouble(list.get(index).getLat()),
					Double.parseDouble(list.get(index).getLon()), lat, log);
			System.out.println("得到的数据:" + jl);
			if (jl > 1000) {
				holder.item5.setText(String.valueOf(Math.floor(jl / 1000))
						+ "公里");
			} else {
				holder.item5.setText(String.valueOf(jl) + "米");
			}

		} catch (NumberFormatException e) {

		}

		return view;
	}

	public class ViewHolder {
		ImageView item0;
		TextView item1, item2, item3, item4, item5;
	}
}
