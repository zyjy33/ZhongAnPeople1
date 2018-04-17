package com.android.hengyu.pub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class SpListDataAdapter extends BaseAdapter {

	private ArrayList<shangpingListData> lists;
	private Context context;
	private ImageLoader loader;

	public SpListDataAdapter(ArrayList<shangpingListData> lists,
							 Context context, ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lists = lists;
		this.loader = loader;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return list.size();
		return lists.size();
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
			convertView = LinearLayout.inflate(context, R.layout.goods_list_ll,
					null);
		}
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name);
		// TextView tv_maPrice = (TextView)
		// convertView.findViewById(R.id.tv_market_money);
		// ImageView img_ware = (ImageView)
		// convertView.findViewById(R.id.img_ware);
		// TextView tv_id = (TextView)
		// convertView.findViewById(R.id.tv_ware_id);

		// tv_maPrice.getPaint().setFlags(
		// Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

		// tv_name.setText(lists.get(position).getTitle());
		tv_name.setText(lists.get(position).title);
		// tv_rePrice.setText("￥" + lists.get(position).getSell_price());
		// tv_maPrice.setText("￥" + lists.get(position).getMarket_price());
		// tv_rePrice.setText("￥" + lists.get(position).sell_price);
		// tv_maPrice.setText("￥" + lists.get(position).market_price);
		// tv_id.setText(list.get(position).id + "");

		// loader.displayImage(RealmName.REALM_NAME_HTTP +
		// lists.get(position).getImg_url(), img_ware);
		// loader.displayImage(RealmName.REALM_NAME_HTTP +
		// lists.get(position).img_url, img_ware);

		return convertView;
	}
}
