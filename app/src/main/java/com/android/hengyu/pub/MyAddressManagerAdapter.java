package com.android.hengyu.pub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengyushop.entity.UserAddressData;
import com.zams.www.R;

import java.util.ArrayList;

public class MyAddressManagerAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<UserAddressData> list = null;

	public MyAddressManagerAdapter(Context context,
			ArrayList<UserAddressData> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = RelativeLayout.inflate(context,
					R.layout.listitem_address, null);
		}

		// data.user_accept_name = jsot.getString("user_accept_name");
		// data.user_area = jsot.getString("area");
		// data.user_mobile = jsot.getString("user_mobile");
		// data.user_address = jsot.getString("user_address");

		// TextView tv = (TextView)
		// convertView.findViewById(R.id.tv_user_address);
		TextView tv_user_name = (TextView) convertView
				.findViewById(R.id.tv_user_name);
		TextView tv_user_address = (TextView) convertView
				.findViewById(R.id.tv_user_address);
		TextView tv_user_phone = (TextView) convertView
				.findViewById(R.id.tv_user_phone);
		tv_user_name.setText("�ջ��ˣ�" + list.get(position).user_accept_name);
		tv_user_phone.setText(list.get(position).user_mobile);
		String user_area = list.get(position).user_area;
		String user_address = list.get(position).user_address;
		// tv_user_address.setText(user_area+" "+user_address);
		tv_user_address.setText("��ַ��" + list.get(position).province + "��"
				+ list.get(position).city + "��" + user_area + "��"
				+ user_address);

		// tv.setText(list.get(position).consigneeAddressInfo);

		return convertView;
	}
}
