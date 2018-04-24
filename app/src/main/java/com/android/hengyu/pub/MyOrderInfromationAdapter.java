package com.android.hengyu.pub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.OrderInfromationData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class MyOrderInfromationAdapter extends BaseAdapter {

	private ArrayList<OrderInfromationData> list;
	private Context context;
	private ImageLoader imageLoader;

	public MyOrderInfromationAdapter(ArrayList<OrderInfromationData> list,
									 Context context, ImageLoader imageLoader) {

		this.context = context;
		this.list = list;
		this.imageLoader = imageLoader;
	}

	public int getCount() {

		// return list.size();
		return list.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_orderinfromation, null);
		}
		TextView co = (TextView) convertView.findViewById(R.id.im_co);
		TextView pr = (TextView) convertView.findViewById(R.id.im_pr);
		TextView na = (TextView) convertView.findViewById(R.id.im_na);
		ImageView im = (ImageView) convertView.findViewById(R.id.im_im);
		imageLoader.displayImage(
				RealmName.REALM_NAME + "/admin/" + list.get(position).image, im);
		co.setText("共" + list.get(position).count + "件商品");
		pr.setText("￥" + list.get(position).Price);
		na.setText(list.get(position).proName);
		return convertView;
	}
}
