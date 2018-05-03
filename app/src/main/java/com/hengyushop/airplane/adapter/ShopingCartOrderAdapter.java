package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.ShopCartData;
import com.zams.www.R;

import java.util.ArrayList;

public class ShopingCartOrderAdapter extends BaseAdapter {
	private ArrayList<ShopCartData> list;
	private LayoutInflater inflater = null;
	private Context context;
	private ShopCartData cartData;
	AQuery aQuery;
	// 构造器
	public ShopingCartOrderAdapter(ArrayList<ShopCartData> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		aQuery = new AQuery(context);
		// 初始化数据
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	public static class ViewHolder {
		TextView tv;
		ImageButton btn_order_cancle;
		ImageView img_ware;
		TextView tv_warename;
		TextView tv_color;
		TextView tv_size,tv_quantity;
		ImageButton btn_reduce;
		EditText et_number;
		ImageButton btn_add;
		TextView tv_money;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		try {
			ViewHolder holder = null;
			if (convertView == null) {
				System.out.println("6================");
				holder = new ViewHolder();
				convertView = RelativeLayout.inflate(context,R.layout.listitem_shopping_cart_order, null);
				holder.btn_order_cancle = (ImageButton) convertView.findViewById(R.id.cb_style);
				holder.img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
				holder.tv_warename = (TextView) convertView.findViewById(R.id.tv_ware_name);
				holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
				holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
				holder.btn_reduce = (ImageButton) convertView.findViewById(R.id.img_btn_reduce);
				holder.et_number = (EditText) convertView.findViewById(R.id.et_number);
				holder.btn_add = (ImageButton) convertView.findViewById(R.id.img_btn_add);
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.btn_order_cancle = (ImageButton) convertView.findViewById(R.id.cb_style);
				convertView.setTag(holder);

			} else {
				// 取出holder
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_warename.setText(list.get(position).getTitle());
			holder.tv_color.setText("￥" + list.get(position).getSell_price());
			int zhoull = list.get(position).getQuantity();
			System.out.println("=============00="+zhoull);
			holder.tv_quantity.setText("x"+String.valueOf(list.get(position).getQuantity()));

			String zhou = list.get(position).getImg_url();
			aQuery.id(holder.img_ware).image(RealmName.REALM_NAME_HTTP +zhou);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return convertView;
	}






}
