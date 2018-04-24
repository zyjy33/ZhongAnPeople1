package com.android.hengyu.pub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.OrderDetailData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class MyOrderDetailAdapter extends BaseAdapter {

	private ArrayList<OrderDetailData> list;
	private Context context;
	private ImageLoader imageLoader;

	public MyOrderDetailAdapter(ArrayList<OrderDetailData> list,
								Context context, ImageLoader loader) {

		this.context = context;
		this.imageLoader = loader;
		this.list = list;
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
					R.layout.listitem_order_detail, null);
		}
		ImageView img_ware = (ImageView) convertView
				.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
		TextView tv_number = (TextView) convertView
				.findViewById(R.id.tv_number);
		imageLoader.displayImage(
				RealmName.REALM_NAME + "/admin/"
						+ list.get(position).proFaceImg, img_ware);
		tv_name.setText(list.get(position).Name);
		tv_price.setText("￥" + list.get(position).Price);
		tv_number.setText(list.get(position).Count);

		return convertView;
	}
}
