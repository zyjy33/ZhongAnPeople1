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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class GoodsLieAdapter extends BaseAdapter {

	private ArrayList<String> list;
	private ArrayList data1;
	private ArrayList data2;
	private Context context;
	private ImageLoader loader;
	AQuery aQuery;

	public GoodsLieAdapter(ArrayList data1, ArrayList data2, Context context,
						   ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data1 = data1;
		this.data2 = data2;
		// this.list = list;
		this.loader = loader;
		aQuery = new AQuery(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return data1.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data1.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context, R.layout.goods_item,
					null);
		}
		System.out.println("--------- position-------------" + position);
		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name);
		// TextView tv_ware_time = (TextView)
		// convertView.findViewById(R.id.tv_ware_time);
		// TextView tv_price = (TextView)
		// convertView.findViewById(R.id.tv_ware_price);
		// TextView tv_total = (TextView)
		// convertView.findViewById(R.id.tv_ware_total);

		// tv_name.setText(list.get(position).proName);
		// tv_price.setText("￥" + list.get(position).retailPrice);
		// tv_total.setText("收藏人气      " + list.get(position).collectTotal);

		tv_name.setText((String) data1.get(position));
		aQuery.id(image).image(
				RealmName.REALM_NAME_HTTP + (String) data2.get(position));

		return convertView;
	}
}
