package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lglottery.www.domain.ComboOtherItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class ComboOtherAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ComboOtherItem> items;
	private ImageLoader imageLoader;
	private Handler handler;

	public ComboOtherAdapter(Context context, ArrayList<ComboOtherItem> items,
							 ImageLoader imageLoader, Handler handler) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.items = items;
		this.imageLoader = imageLoader;
		this.handler = handler;
	}

	/**
	 * 装载数据
	 */
	public void putLists(ArrayList<ComboOtherItem> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (view == null || view.getTag(R.drawable.icon + index) == null) {
			holder = new ViewHolder();

			view = LinearLayout.inflate(context, R.layout.combo_other_item,
					null);
			holder.text1 = (TextView) view.findViewById(R.id.text1);
			holder.text2 = (TextView) view.findViewById(R.id.text2);
			holder.text3 = (TextView) view.findViewById(R.id.text3);
			//
			view.setTag(R.drawable.icon + index);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.icon + index);
		}
		holder.text1.setText(items.get(index).getText1());
		holder.text2.setText(items.get(index).getText2() + "元");
		holder.text3.setText(items.get(index).getText3() + "元");
		return view;
	}

	public class ViewHolder {
		TextView text1, text2, text3;

	}
	/*
	 * holder.combo_image_item = (ImageView) view
	 * .findViewById(R.id.combo_image_item);
	 * 
	 * holder.current_price = (TextView) view .findViewById(R.id.current_price);
	 * holder.list_price = (TextView) view.findViewById(R.id.list_price);
	 * holder.item_vtag = (TextView) view.findViewById(R.id.item_vtag);
	 */
}
