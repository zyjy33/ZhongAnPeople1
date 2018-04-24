package com.hengyushop.demo.airplane;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlyCangAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FlyCangItem> listCangItems;
	private Handler handler;

	public FlyCangAdapter(Context context,
						  ArrayList<FlyCangItem> listCangItems, Handler handler) {

		this.context = context;
		this.listCangItems = listCangItems;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return listCangItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listCangItems.get(arg0);
	}

	/**
	 * change this adapter of listview
	 *
	 * @param listCangItems
	 */
	public void upDataAdapter(ArrayList<FlyCangItem> listCangItems) {
		this.listCangItems = listCangItems;
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {

		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.fly_cang_item, null);
			holder.fly_cang1 = (TextView) view.findViewById(R.id.fly_cang1);
			holder.fly_cang2 = (TextView) view.findViewById(R.id.fly_cang2);
			holder.fly_cang3 = (TextView) view.findViewById(R.id.fly_cang3);
			holder.fly_cang4 = (TextView) view.findViewById(R.id.fly_cang4);
			holder.fly_cang5 = (Button) view.findViewById(R.id.fly_cang5);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final FlyCangItem item = listCangItems.get(index);
		holder.fly_cang2.setText("退改签");
		holder.fly_cang1.setText(item.getSeatMsg()
				+ (item.getSeatStatus().equals("A") ? ">9" : item
				.getSeatStatus()));
		holder.fly_cang3.setText("￥" + item.getParPrice());
		holder.fly_cang4.setText("(" + item.getDiscount() + "折)");
		holder.fly_cang5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Message msg = new Message();
				msg.what = 4;
				msg.obj = item;
				handler.sendMessage(msg);
			}
		});
		return view;
	}

	public class ViewHolder {
		private TextView fly_cang1, fly_cang2, fly_cang3, fly_cang4;
		private Button fly_cang5;
	}
}
