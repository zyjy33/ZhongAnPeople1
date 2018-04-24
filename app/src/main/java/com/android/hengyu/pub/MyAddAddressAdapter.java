package com.android.hengyu.pub;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hengyushop.entity.AddressViewHolder;
import com.hengyushop.entity.UserAddressData;
import com.zams.www.R;

public class MyAddAddressAdapter extends BaseAdapter {
	private ArrayList<UserAddressData> list;
	Activity activity;
	private int temp = -1;
	private AddressViewHolder holder;
	private Handler handler;

	// private ImageLoader loader;

	public MyAddAddressAdapter(ArrayList<UserAddressData> list,
			Activity activity, Handler handler) {

		this.activity = activity;
		this.list = list;
		this.handler = handler;
		// loader = new ImageLoader(context);
	}

	public int getCount() {

		// return list.size();
		return list.size();
	}

	public Object getItem(int position) {

		return list.get(position);
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null
				|| convertView.getTag(R.drawable.btn_add + position) == null) {
			holder = new AddressViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.listitem_order_address, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_user_address);
			holder.button = (RadioButton) convertView
					.findViewById(R.id.cb_style);
			holder.name.setText(list.get(position).consigneeUserName);
			holder.address.setText(list.get(position).consigneeAddressInfo);
			holder.button.setChecked(false);
			convertView.setTag(R.drawable.btn_add + position);
		} else {
			holder = (AddressViewHolder) convertView.getTag(R.drawable.btn_add
					+ position);
		}
		// holder.position = position;
		holder.button.setId(position);
		holder.button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					if (temp != -1) {
						RadioButton tempRadio = (RadioButton) activity
								.findViewById(temp);
						if (tempRadio != null) {
							tempRadio.setChecked(false);
						}
					}
					temp = buttonView.getId();
					int addressId = list.get(temp).consigneeAddressId;
					Message message = new Message();
					message.what = 1;
					message.arg1 = addressId;
					handler.sendMessage(message);

					Log.v("data1", list.get(temp).consigneeAddressId + "");
				}
			}
		});
		if (position == temp) {
			holder.button.setChecked(true);
		} else {
			holder.button.setChecked(false);
		}

		return convertView;
	}
}
