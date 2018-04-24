package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.entity.TicketData;
import com.hengyushop.entity.WareInformationData;
import com.zams.www.R;

public class MyShopTicketAdapter extends BaseAdapter {

	private ArrayList<TicketData> list;
	private Context context;

	public MyShopTicketAdapter(ArrayList<TicketData> list, Context context) {

		this.context = context;
		this.list = list;
	}

	public int getCount() {

		// return list.size();
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
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_shop_ticket, null);
		}
		TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tv_income = (TextView) convertView
				.findViewById(R.id.tv_income);
		TextView tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
		TextView tv_balance = (TextView) convertView
				.findViewById(R.id.tv_balance);

		tv_time.setText(list.get(position).time);
		tv_income.setText(list.get(position).income);
		tv_pay.setText(list.get(position).expenses);
		tv_balance.setText(list.get(position).balance);

		return convertView;
	}
}
