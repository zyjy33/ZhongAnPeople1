package com.hengyushop.demo.train;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zams.www.R;

import java.util.ArrayList;

public class TrainPersonItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<TrainPersonItem> list;
	private Handler handler;

	public TrainPersonItemAdapter(Context context,
								  ArrayList<TrainPersonItem> list, Handler handler) {
		this.context = context;
		this.list = list;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void putData() {
		notifyDataSetChanged();
	}

	public void remove(int index) {
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub ViewHolder holder = null;
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.train_person_item,
					null);
			holder.v1 = (TextView) view.findViewById(R.id.v1);
			holder.v2 = (TextView) view.findViewById(R.id.v2);
			holder.v3 = (TextView) view.findViewById(R.id.v3);
			holder.checkedMap = (CheckBox) view.findViewById(R.id.ck);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.v1.setText(list.get(index).getContactUserName());
		holder.v2.setText(list.get(index).getPiaoZhong());
		holder.v3.setText(list.get(index).getDocumentNumber());
		if (handler != null) {
			holder.checkedMap
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton arg0,
													 boolean arg1) {
							// TODO Auto-generated method stub
							TrainPersonItem key = list.get(index);
							Message msg = new Message();
							msg.what = 0;
							msg.obj = key;
							if (arg1) {
								msg.arg1 = 1;// 代表添加
								list.get(index).setFlag(true);
							} else {
								msg.arg1 = 0;
								list.get(index).setFlag(false);
							}
							handler.sendMessage(msg);

							notifyDataSetChanged();
						}
					});
			if (list.get(index).isFlag()) {
				// 默认是没有点击的
				holder.checkedMap.setChecked(true);
			} else {
				holder.checkedMap.setChecked(false);
			}
			holder.checkedMap.setVisibility(View.VISIBLE);
		} else {
			holder.checkedMap.setVisibility(View.GONE);
		}
		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, v3;
		private CheckBox checkedMap;

	}

}
