package com.hengyushop.demo.hotel;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HotelCityAdapter extends BaseAdapter {
	private ArrayList<HotelCity> list;
	private Context context;

	public HotelCityAdapter(ArrayList<HotelCity> list, Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		TextView view = new TextView(context);
		view.setTextColor(R.color.black);
		view.setPadding(5, 5, 0, 5);
		view.setText(list.get(arg0).getName());
		view.setTag(list.get(arg0).getId());
		return view;
	}

}
