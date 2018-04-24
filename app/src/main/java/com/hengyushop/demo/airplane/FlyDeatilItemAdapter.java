package com.hengyushop.demo.airplane;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlyDeatilItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FlyDetailPop> detailPops;

	public FlyDeatilItemAdapter(Context context,
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
			view = LinearLayout
					.inflate(context, R.layout.fly_detail_item, null);
			holder.fly_detail_item_card = (TextView) view
					.findViewById(R.id.fly_detail_item_card);
			holder.fly_detail_item_name = (TextView) view
					.findViewById(R.id.fly_detail_item_name);
			holder.fly_detail_item_del = (Button) view
					.findViewById(R.id.fly_detail_item_del);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.fly_detail_item_card.setText(detailPops.get(index).getNum());
		holder.fly_detail_item_name.setText(detailPops.get(index).getName());
		holder.fly_detail_item_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				remove(index);
			}
		});
		return view;
	}

	public class ViewHolder {
		private TextView fly_detail_item_name, fly_detail_item_card;
		private Button fly_detail_item_del;
	}

}
