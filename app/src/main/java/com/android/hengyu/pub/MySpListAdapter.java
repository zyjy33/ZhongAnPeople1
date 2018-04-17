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
import com.androidquery.AQuery;
import com.hengyushop.entity.SpListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class MySpListAdapter extends BaseAdapter {

	private ArrayList<SpListData> lists;
	private Context context;
	private ImageLoader loader;
	public static AQuery query;
	public static boolean type = false;
	public MySpListAdapter(ArrayList<SpListData> lists,
						   Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lists = lists;
		this.loader = loader;
		query = new AQuery(context);
		//		HuanCunClear zhou = new HuanCunClear();
		//		query = zhou.query;
		//		query = HuanCunClear.query;

	}
	public void putData(ArrayList<SpListData> lists){
		this.lists = lists;
		this.notifyDataSetChanged();
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
			convertView = LinearLayout.inflate(context,R.layout.listitem_goods_time, null);
		}
		LinearLayout ll_kedikou = (LinearLayout) convertView.findViewById(R.id.ll_kedikou);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
		TextView tv_rePrice = (TextView) convertView.findViewById(R.id.tv_hengyu_money);
		TextView tv_maPrice = (TextView) convertView.findViewById(R.id.tv_market_money);
		TextView tv_hongbao = (TextView) convertView.findViewById(R.id.tv_hongbao);
		ImageView img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
		View vi_ = (View) convertView.findViewById(R.id.vi_);
		vi_.setVisibility(View.GONE);

		tv_maPrice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

		//		tv_name.setText(lists.get(position).getTitle());
		tv_name.setText(lists.get(position).title);
		tv_rePrice.setText("￥" + lists.get(position).getSell_price());
		tv_maPrice.setText("￥" + lists.get(position).getMarket_price());
		tv_hongbao.setText("￥" + lists.get(position).getCashing_packet());
		if (lists.get(position).getCashing_packet().equals("0.0")) {
			ll_kedikou.setVisibility(View.GONE);
		}else {
			ll_kedikou.setVisibility(View.VISIBLE);
		}

		//		tv_maPrice.setText("￥" + lists.get(position).market_price);
		//		tv_id.setText(list.get(position).id + "");

		//		if (position == 0) {
		//			img_ware.setBackgroundResource(R.drawable.ic_launcher);
		//			String tupian = lists.get(position).img_url;
		//			System.out.println("--tupian------------------"+tupian);
		//			try {
		//				loader.displayImage(RealmName.REALM_NAME_HTTP + lists.get(position).img_url, img_ware);
		//				String  zhou  =RealmName.REALM_NAME_HTTP + lists.get(position).img_url;
		//				System.out.println("--zhou------------------"+zhou);
		//			} catch (Exception e) {
		//				// TODO: handle exception
		//				e.printStackTrace();
		//			}
		//		} else {
		//			loader.displayImage(RealmName.REALM_NAME_HTTP + lists.get(position).img_url, img_ware);
		//			String tupian = lists.get(position).img_url;
		//			System.out.println("--tupian------------------"+tupian);
		//		}
		query.id(img_ware).image(RealmName.REALM_NAME_HTTP+lists.get(position).img_url);
		type = true;
		return convertView;
	}
}
