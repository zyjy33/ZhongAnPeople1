package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.WareData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

public class MyWareAdapter extends BaseAdapter {

	private ArrayList<WareData> list;
	private Context context;

	private ImageLoader loader;

	public MyWareAdapter(ArrayList<WareData> list, Context context,
			ImageLoader loader) {

		this.context = context;
		this.list = list;
		this.loader = loader;
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
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_menu_classify_one, null);
		}
		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv1 = (TextView) convertView.findViewById(R.id.tv_ware_name);
		TextView tv2 = (TextView) convertView.findViewById(R.id.tv_ware_name2);

		tv1.setText("" + list.get(position).productTypeName);
		String temp[] = list.get(position).strname;
		StringBuilder builder = new StringBuilder();
		try {
			int len = temp.length;
			for (int i = 0; i < (len > 3 ? 3 : len); i++) {
				if (i == temp.length - 1) {
					builder.append(list.get(position).strname[i]);
				} else {
					builder.append(list.get(position).strname[i] + " ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("process NullPoint");
		}
		try {
			tv2.setText(builder.toString());
		} catch (NullPointerException e) {
			System.out.println("NullPoint");
			e.printStackTrace();
		}
		loader.displayImage(RealmName.REALM_NAME + "/"
				+ list.get(position).openUrl, image);

		return convertView;
	}
}
