package com.android.hengyu.pub;

import java.util.ArrayList;

import com.hengyushop.entity.WareParameterData;
import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyWareParameterAdapter extends BaseAdapter {

	private ArrayList<WareParameterData> list;
	private Context context;

	public MyWareParameterAdapter(ArrayList<WareParameterData> list,
			Context context) {
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
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,
					R.layout.listiten_ware_parameter, null);
		}
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_parameter_name);
		TextView tv_value = (TextView) convertView
				.findViewById(R.id.tv_parameter_attribute);
		tv_name.setText(list.get(position).specParameterName+":");
		tv_value.setText(list.get(position).specParameterValue);

		return convertView;
	}

}
