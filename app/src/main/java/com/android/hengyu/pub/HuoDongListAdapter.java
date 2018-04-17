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
import com.hengyushop.entity.JuTuanGouData;
import com.zams.www.R;

import java.util.ArrayList;

public class HuoDongListAdapter extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private Context context;
	public static  AQuery aQuery;

	public HuoDongListAdapter(ArrayList<JuTuanGouData> list, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		aQuery = new AQuery(context);
	}

	public void putData(ArrayList<JuTuanGouData> list){
		this.list = list;
		this.notifyDataSetChanged();
	}


	public int getCount() {
		// TODO Auto-generated method stub
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
			convertView = LinearLayout.inflate(context,R.layout.huodong_list_item, null);
		}

		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);//
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
		TextView tv_ware_time = (TextView) convertView.findViewById(R.id.tv_ware_time);
		TextView tv_conten = (TextView) convertView.findViewById(R.id.tv_conten);
		TextView tv_total = (TextView) convertView.findViewById(R.id.tv_ware_total);

		//		tv_name.setText(list.get(position).proName);
		//		tv_price.setText("￥" + list.get(position).retailPrice);
		//		tv_total.setText("收藏人气      " + list.get(position).collectTotal);

		tv_name.setText(list.get(position).title);
		tv_conten.setText(list.get(position).category_title);
		tv_ware_time.setText("活动时间:"+list.get(position).add_time);
		//		loader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).img_url, image);
		aQuery.id(image).image(RealmName.REALM_NAME_HTTP + list.get(position).img_url);



		return convertView;
	}
}
