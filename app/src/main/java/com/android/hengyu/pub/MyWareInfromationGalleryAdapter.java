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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyWareInfromationGalleryAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private Context context;
	private ImageLoader loader;

	public MyWareInfromationGalleryAdapter(
			List<Map<String, String>> allGriddatas, Context context,
			ImageLoader loader) {

		this.list = allGriddatas;
		this.context = context;
		this.loader = loader;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = RelativeLayout.inflate(context,
					R.layout.galleryitem_ware_information, null);
		}
		ImageView imgView = (ImageView) convertView
				.findViewById(R.id.img_information);
		Log.v("data1", list.get(position).get("img"));
		loader.displayImage(
				RealmName.REALM_NAME + "/admin/"
						+ list.get(position).get("img"), imgView);

		return convertView;
	}

}
