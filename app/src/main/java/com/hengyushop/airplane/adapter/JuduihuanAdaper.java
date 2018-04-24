package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.JuTuanGouData;
import com.zams.www.R;

import java.util.ArrayList;

public class JuduihuanAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<JuTuanGouData> list;
	private LayoutInflater mInflater;
	private int clickTemp = 0;
	public static AQuery mAq;
	public static boolean type = false;
	public JuduihuanAdaper(ArrayList<JuTuanGouData> list,Context context){
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}


	@Override
	public int getCount() {
		if (list.size()<1) {

			return 0;
		}else{

			return list.size();
		}
	}

	public void setSeclection(int position) {
		clickTemp = position;
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
				convertView = mInflater.inflate(R.layout.gridview_juduihuan_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
				holder.tv_jifengduihuan = (TextView) convertView.findViewById(R.id.tv_jifengduihuan);
				holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_biaoti.setText(list.get(position).title);

			holder.tv_jifengduihuan.setText(list.get(position).exchange_point+"福利"+"+"+list.get(position).exchange_price+"元");
			//		holder.tv_jifengduihuan.setText(list.get(position).point+"分");
			holder.tv_shichangjia.setText("市场价:￥"+list.get(position).market_price);
			holder.tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			//		ImageLoader imageLoader=ImageLoader.getInstance();
			//        imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+list.get(position).img_url,holder.img);
			mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).img_url);
			type = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}


	class ViewHolder{
		ImageView img;
		TextView tv_biaoti;
		TextView tv_jifengduihuan;
		TextView tv_shichangjia;
		RadioButton radioButton;
	}
}