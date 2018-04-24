package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.my.JiaZhiMxActivity;
import com.hengyushop.demo.my.MyAssetsActivity;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.WareData;
import com.hengyushop.entity.XsgyListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

public class JiaZhiAdapter extends BaseAdapter {

	private ArrayList<MyAssetsBean> list;
	private Context context;

	private ImageLoader loader;

	public JiaZhiAdapter(ArrayList<MyAssetsBean> list, Context context,ImageLoader loader) {

		this.context = context;
		this.list = list;
		this.loader = loader;
	}

	public void putData(ArrayList<MyAssetsBean> list){
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
			convertView = LinearLayout.inflate(context,R.layout.listitem_jiazhi, null);
		}
		TextView tv_income = (TextView) convertView.findViewById(R.id.tv_income);
		TextView tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
		TextView tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);

		//12 8 13 14 15
		if (JiaZhiMxActivity.fund_id.equals("12")) {
			tv_income.setTextColor(Color.parseColor("#21AE12"));
		}else if (JiaZhiMxActivity.fund_id.equals("8")) {
			tv_income.setTextColor(Color.parseColor("#FF6D00"));
		}else if (JiaZhiMxActivity.fund_id.equals("13")) {
			tv_income.setTextColor(Color.parseColor("#FF798E"));
		}else if (JiaZhiMxActivity.fund_id.equals("14")) {
			tv_income.setTextColor(Color.parseColor("#DD0303"));
		}else if (JiaZhiMxActivity.fund_id.equals("15")) {
			tv_income.setTextColor(Color.parseColor("#FF6D00"));
		}
		
			if (!list.get(position).expense.equals("")){
				tv_income.setText("-"+list.get(position).expense+"");
			}else if (!list.get(position).income.equals("")){
				tv_income.setText("+"+list.get(position).income+"");
			}
			
		tv_remark.setText(list.get(position).remark);
		tv_add_time.setText(list.get(position).add_time);
 

		return convertView;
	}
}
