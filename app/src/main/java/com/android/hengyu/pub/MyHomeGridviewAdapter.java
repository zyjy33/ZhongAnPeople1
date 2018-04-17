package com.android.hengyu.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hengyushop.entity.WareShopData;
import com.zams.www.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyHomeGridviewAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private Context context;

	// private ImageLoader loader;

	public MyHomeGridviewAdapter(List<Map<String, String>> allGriddatas,
			Context context) {
		// TODO Auto-generated constructor stub
		this.list = allGriddatas;
		this.context = context;
		// loader = new ImageLoader(context);
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
			convertView = RelativeLayout.inflate(context,
					R.layout.grideitem_home, null);
		}
		ImageView imgView = (ImageView) convertView
				.findViewById(R.id.imageView1);

		return convertView;
	}

}
