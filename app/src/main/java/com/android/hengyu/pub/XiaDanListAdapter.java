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
import com.hengyushop.entity.ShopCartData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.List;

public class XiaDanListAdapter extends BaseAdapter {

	private List<ShopCartData> list;
	private Context context;
	private ImageLoader loader;
	public static String jiaguo;

	public XiaDanListAdapter(List<ShopCartData> list, Context context,ImageLoader loader) {

		this.context = context;
		this.list = list;
		this.loader = loader;
	}

	public void putData(List<ShopCartData> list){
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
			convertView = LinearLayout.inflate(context,R.layout.listitem_xiadan, null);
		}
		System.out.println("=================6=" + list.size());
		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
		TextView tv_price = (TextView) convertView.findViewById(R.id.tv_sell_price);
		TextView tv_market_price = (TextView) convertView.findViewById(R.id.tv_market_price);
		TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);


		tv_name.setText(list.get(position).title);
		tv_price.setText("价格:￥" + list.get(position).sell_price);
		tv_market_price.setText("市场价：￥" + list.get(position).market_price);
		//		tv_num.setText("x" + list.get(position).quantity);
		loader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).img_url, image);
		tv_market_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

		jiaguo = list.get(position).sell_price;

		//		Message msg = new Message();
		//		msg.what = 0;
		//		msg.obj = list;
		//		handler.sendMessage(msg);

		return convertView;
	}
}
