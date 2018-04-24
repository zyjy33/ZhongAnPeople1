package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.SpListData;
import com.zams.www.R;

import java.util.ArrayList;

public class TuiJianGoodsGridViewAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<SpListData> list;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	public static boolean type = false;
	public TuiJianGoodsGridViewAdaper(ArrayList<SpListData> list,Context context){
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	public void putData(ArrayList<SpListData> lists){
		this.list = lists;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list.size()<1) {

			return 0;
		}else{

			return list.size();
		}
	}


	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		try {

			if (convertView == null) {
				holder =  new ViewHolder();
				convertView = mInflater.inflate(R.layout.gridview_gouwuche_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
				holder.tv_hengyu_money = (TextView) convertView.findViewById(R.id.tv_jiaguo);
				//			holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_biaoti.setText(list.get(position).getTitle());
			holder.tv_hengyu_money.setText("￥"+list.get(position).getSell_price());
			//		holder.tv_shichangjia.setText("￥"+list.get(position).getMarket_price());
			//		holder.tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).getImg_url());
			type = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}


	class ViewHolder{
		ImageView img;
		TextView tv_biaoti;
		TextView tv_hengyu_money;
		TextView tv_shichangjia,tv_hongbao;
		LinearLayout ll_kedikou;
		RadioButton radioButton;
	}
}