package com.android.hengyu.pub;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.WareInformationData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class MyWareFourAdapter extends BaseAdapter {

	private ArrayList<WareInformationData> list;
	private Context context;

	private ImageLoader loader;

	public MyWareFourAdapter(ArrayList<WareInformationData> list,
							 Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.loader = loader;
	}
	public void putData(ArrayList<WareInformationData> list){
		this.list = list;
		this.notifyDataSetChanged();
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
					R.layout.listitem_menu_classify_four, null);
		}
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name);
		TextView tv_rePrice = (TextView) convertView
				.findViewById(R.id.tv_hengyu_money);
		TextView tv_maPrice = (TextView) convertView
				.findViewById(R.id.tv_market_money);
		ImageView img_ware = (ImageView) convertView
				.findViewById(R.id.img_ware);
		TextView tv_id = (TextView) convertView.findViewById(R.id.tv_ware_id);

		tv_maPrice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

		tv_name.setText(list.get(position).proName + "");
		tv_rePrice.setText("￥" + list.get(position).retailPrice);
		tv_maPrice.setText("￥" + list.get(position).marketPrice);
		tv_id.setText(list.get(position).id + "");

		loader.displayImage(
				RealmName.REALM_NAME + "/admin/"
						+ list.get(position).proThumbnailImg, img_ware);

		return convertView;
	}
}
