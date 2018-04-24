package com.hengyushop.demo.train;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainInfoAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<TrainInfoChild> list;

	public TrainInfoAdapter(Context context, ArrayList<TrainInfoChild> list) {

		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		System.out.println("adapter"+list.size());
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
	public View getView(int index, View view, ViewGroup arg2) {
		  ViewHolder holder = null;
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context,
					R.layout.train_listitem_station, null);

			holder.v1 = (TextView) view.findViewById(R.id.v1);
			holder.v2 = (TextView) view.findViewById(R.id.v2);
			holder.v3 = (TextView) view.findViewById(R.id.v3);
			holder.v4 = (TextView) view.findViewById(R.id.v4);
			holder.v5 = (TextView) view.findViewById(R.id.v5);
			holder.v6 = (TextView) view.findViewById(R.id.v6);
			holder.v7 = (TextView) view.findViewById(R.id.v7);
			holder.v8 = (TextView) view.findViewById(R.id.v8);
			holder.v9 = (TextView) view.findViewById(R.id.v9);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.v1.setText(list.get(index).getV1());
		holder.v2.setText(list.get(index).getV2());
		holder.v3.setText(list.get(index).getV3());
		holder.v4.setText(list.get(index).getV4());
		holder.v5.setText(list.get(index).getV5());
		holder.v6.setText(list.get(index).getV6());
		holder.v7.setText(list.get(index).getV7());
		holder.v8.setText(list.get(index).getV8());
		holder.v9.setText(list.get(index).getV9());
		
		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, v3, v4, v5, v6, v7, v8, v9;
	}

}
