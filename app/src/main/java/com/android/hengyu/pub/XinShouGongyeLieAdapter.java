package com.android.hengyu.pub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.XsgyListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class XinShouGongyeLieAdapter extends BaseAdapter {

	private ArrayList<XsgyListData> list;
	private Context context;
	private ImageLoader loader;
	public static AQuery aQuery;

	public XinShouGongyeLieAdapter(ArrayList<XsgyListData> list,
								   Context context, ImageLoader loader) {

		this.context = context;
		this.list = list;
		this.loader = loader;
		aQuery = new AQuery(context);
	}

	public void putData(ArrayList<XsgyListData> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	public int getCount() {

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
			// convertView =
			// LinearLayout.inflate(context,R.layout.listitem_xsgy, null);
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_jutoutiao, null);
		}

		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name);
		TextView tv_ware_time = (TextView) convertView
				.findViewById(R.id.tv_ware_time);
		TextView tv_price = (TextView) convertView
				.findViewById(R.id.tv_ware_price);
		TextView tv_total = (TextView) convertView
				.findViewById(R.id.tv_ware_total);

		// tv_name.setText(list.get(position).proName);
		// tv_price.setText("￥" + list.get(position).retailPrice);
		// tv_total.setText("收藏人气      " + list.get(position).collectTotal);

		tv_name.setText(list.get(position).title);
		tv_ware_time.setText(list.get(position).add_time);
		// loader.displayImage(RealmName.REALM_NAME_HTTP +
		// list.get(position).img_url, image);
		aQuery.id(image).image(
				RealmName.REALM_NAME_HTTP + list.get(position).img_url);

		return convertView;
	}
}
