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
	private Handler handler;
	AQuery aQuery;
	// 构造器
	public ShopingCartOrderAdapter(ArrayList<ShopCartData> list, Context context
			,Handler handler) {
		this.context = context;
		this.handler = handler;
		this.list = list;
		inflater = LayoutInflater.from(context);
		aQuery = new AQuery(context);
		// 初始化数据
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public static class ViewHolder {
		TextView tv;
		public CheckBox cb;

		ImageButton btn_order_cancle;
		ImageView img_ware;
		TextView tv_warename;
		TextView tv_color;
		TextView tv_1;
		TextView tv_2;
		TextView tv_size,tv_quantity;
		ImageButton btn_reduce;
		EditText et_number;
		ImageButton btn_add;
		TextView tv_money;
		TextView market_information_seps_add;
		TextView market_information_seps_del;
		TextView market_information_seps_num;
		CheckBox shopcart_item_check;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			ViewHolder holder = null;
			if (convertView == null) {

				System.out.println("6================");
				holder = new ViewHolder();
				convertView = RelativeLayout.inflate(context,R.layout.listitem_shopping_cart_order, null);
				//			convertView = inflater.inflate(R.layout.listviewitem, null);
				holder.btn_order_cancle = (ImageButton) convertView.findViewById(R.id.cb_style);
				holder.img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
				holder.tv_warename = (TextView) convertView.findViewById(R.id.tv_ware_name);
				holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
				//			holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
				//			holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
				//			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
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
			//		holder.tv_size.setText("￥" + list.get(position).getMarket_price());
			int zhoull = list.get(position).getQuantity();
			System.out.println("=============00="+zhoull);
			holder.tv_quantity.setText("x"+String.valueOf(list.get(position).getQuantity()));

			//		ImageLoader imageLoaderll=ImageLoader.getInstance();
			String zhou = list.get(position).getImg_url();
			//		imageLoaderll.displayImage(RealmName.REALM_NAME_HTTP + zhou,holder.img_ware);
			aQuery.id(holder.img_ware).image(RealmName.REALM_NAME_HTTP +zhou);

			//		holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);



		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}






}
