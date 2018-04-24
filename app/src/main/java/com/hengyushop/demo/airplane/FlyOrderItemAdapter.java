package com.hengyushop.demo.airplane;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlyOrderItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FlyDetailPop> detailPops;

	public FlyOrderItemAdapter(Context context,
							   ArrayList<FlyDetailPop> detailPops) {

		this.context = context;
		this.detailPops = detailPops;
	}

	@Override
	public int getCount() {

		return detailPops.size();
	}

	public void putData(ArrayList<FlyDetailPop> detailPops) {
		this.detailPops = detailPops;
		notifyDataSetChanged();

	}

	public void remove(int index) {
		detailPops.remove(index);
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int arg0) {

		return detailPops.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(final int index, View view, ViewGroup arg2) {
		  ViewHolder holder = null;
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.fly_order_item, null);
			holder.name = (TextView) view.findViewById(R.id.fly_order_item1);
			holder.mob = (TextView) view.findViewById(R.id.fly_order_item2);
			holder.card = (TextView) view.findViewById(R.id.fly_order_item3);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		String tag = "";
		if (detailPops.get(index).getTagL().equals("1")) {
			tag = "儿童";

		} else if (detailPops.get(index).getTagL().equals("2")) {
			tag = "成人";
		}
		holder.name.setText(detailPops.get(index).getName() + tag);
		holder.mob.setText(detailPops.get(index).getMob());
		holder.card.setText(detailPops.get(index).getNum());
		return view;
	}

	public class ViewHolder {
		private TextView name, mob, card;
	}

}
