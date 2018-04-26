package com.hengyushop.airplane.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.ZhongAnYlBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class ZaylAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ZhongAnYlBean> items;
	private ImageLoader imageLoader;
	public static AQuery aQuery;
	public static boolean type = false;
	public ZaylAdapter(ImageLoader imageLoader) {

		this.imageLoader = imageLoader;
	}

	public ZaylAdapter(ArrayList<ZhongAnYlBean> items2, Context mContext) {
		super();
		this.items = items2;
		this.mContext = mContext;
		aQuery = new AQuery(mContext);
	}

	@Override
	public int getCount() {

		return items.size();
	}

	@Override
	public Object getItem(int position) {

		return items.get(position);
	}


	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {


			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.zayl_list_item, parent, false);
			}
			TextView tv = BaseViewHolder.get(convertView, R.id.tv_zhuti);
			TextView tv_market_price = BaseViewHolder.get(convertView, R.id.tv_market_price);
			TextView tv_sell_price = BaseViewHolder.get(convertView, R.id.tv_sell_price);
			ImageView img_ware = BaseViewHolder.get(convertView, R.id.img_ware);;
			tv.setText(items.get(position).getTitle());
			tv_market_price.setText("市场价￥"+items.get(position).getMarket_price());
			tv_sell_price.setText("价格￥"+items.get(position).getSell_price());
			tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			aQuery.id(img_ware).image(RealmName.REALM_NAME_HTTP+ items.get(position).getImg_url());
			type = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

}

