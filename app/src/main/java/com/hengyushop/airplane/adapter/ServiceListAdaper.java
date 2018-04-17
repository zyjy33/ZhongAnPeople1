package com.hengyushop.airplane.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
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
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.WareInformationData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

public class ServiceListAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<WareInformationData> list;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	public ServiceListAdaper(ArrayList<WareInformationData> list,Context context){
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
			
		if (convertView == null) {
			holder =  new ViewHolder();
			convertView = mInflater.inflate(R.layout.gridview_service_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
//			holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
//			holder.tv_jifengduihuan = (TextView) convertView.findViewById(R.id.tv_jifengduihuan);
//			holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
        mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).img_url);
		
		} catch (Exception e) { 
			// TODO: handle exception
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