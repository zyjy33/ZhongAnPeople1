package com.android.hengyu.pub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hengyushop.entity.SpListData;
import com.zams.www.R;

import java.util.ArrayList;

public class QiYeJinMianAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<SpListData> list;
	private LayoutInflater mInflater;
	private int clickTemp = 0;
	public static AQuery mAq;
	public static boolean type = false;
	public QiYeJinMianAdaper(ArrayList<SpListData> list,Context context){
		try {

			System.out.println("position=====1================");
			this.list = list;
			this.mContext = context;
			mInflater = LayoutInflater.from(context);
			mAq = new AQuery(context);
			System.out.println("position=====2================");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		if (list.size()<1) {
			return 0;
		}else{
			return list.size();
		}
	}

	//	public void setSeclection(int position) {
	//		clickTemp = position;
	//	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
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
			System.out.println("position====================="+position);
			if (convertView == null) {
				holder =  new ViewHolder();
				convertView = mInflater.inflate(R.layout.gridview_rxzq_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
				holder.tv_jifengduihuan = (TextView) convertView.findViewById(R.id.tv_jifengduihuan);
				//			holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_biaoti.setText(list.get(position).title);
			if (position == 0) {
				holder.img.setBackgroundResource(R.drawable.zams_hb_1);
			}else if (position == 0) {
				holder.img.setBackgroundResource(R.drawable.zams_hb_2);
			}else if (position == 0) {
				holder.img.setBackgroundResource(R.drawable.zams_hb_3);
			}
			//		holder.tv_jifengduihuan.setText("￥"+list.get(position).sell_price);
			//		holder.tv_shichangjia.setText("市场价:￥"+list.get(position).market_price);
			//		holder.tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			//		mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).img_url);
			type = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}


	class ViewHolder{
		ImageView img;
		TextView tv_biaoti,yh2;
		TextView tv_jifengduihuan;
		TextView tv_shichangjia;
		RadioButton radioButton;
	}
}