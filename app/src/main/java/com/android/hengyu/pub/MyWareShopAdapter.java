package com.android.hengyu.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.WareShopData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyWareShopAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private Context context;
	private ImageLoader loader;

	public MyWareShopAdapter(List<Map<String, String>> allGriddatas,
			Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.list = allGriddatas;
		this.context = context;
		this.loader = loader;
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
					R.layout.griditem_ware_information, null);
		}
		ImageView imgView = (ImageView) convertView
				.findViewById(R.id.img_ware_information);
		loader.displayImage(
				RealmName.REALM_NAME + "/admin/"
						+ list.get(position).get("img"), imgView);

		return convertView;
	}

}
