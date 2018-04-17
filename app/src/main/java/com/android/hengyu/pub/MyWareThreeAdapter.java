package com.android.hengyu.pub;

import java.util.ArrayList;

import com.hengyushop.entity.WareData;
import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyWareThreeAdapter extends BaseAdapter {

	private ArrayList<WareData> list;
	private Context context;

	// private ImageLoader loader;

	public MyWareThreeAdapter(ArrayList<WareData> list, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		// loader = new ImageLoader(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return list.size();
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_menu_classify_three, null);
		}
		// ImageView image (ImageView) convertView.findViewById(R.id.ima);
		TextView tv1 = (TextView) convertView.findViewById(R.id.tv01);

		tv1.setText("" + list.get(position).productTypeName);

		return convertView;
	}
}
