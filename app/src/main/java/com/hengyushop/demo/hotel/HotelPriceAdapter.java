package com.hengyushop.demo.hotel;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotelPriceAdapter extends BaseAdapter {
	private ArrayList<HotelDetialPriceDo> lists;
	private Context context;
	@SuppressWarnings("unused")
	private ImageLoader imageLoader;
	private Handler handler;

	public HotelPriceAdapter(ArrayList<HotelDetialPriceDo> lists,
			Context context, ImageLoader imageLoader, Handler handler) {

		this.context = context;
		this.imageLoader = imageLoader;
		this.lists = lists;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(final int arg0, View view, ViewGroup arg2) {

		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.hotel_detail_price,
					null);
			holder.room_name = (TextView) view.findViewById(R.id.room_name);
			holder.roo_price = (TextView) view.findViewById(R.id.roo_price);
			holder.room_yd = (TextView) view.findViewById(R.id.room_yd);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.room_name.setText(lists.get(arg0).getRoomName());
		holder.roo_price.setText(lists.get(arg0).getRoomPrice() + "Ԫ");
		holder.room_yd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 2;
				msg.obj = lists.get(arg0).getId();
				handler.sendMessage(msg);
			}
		});
		return view;
	}

	public class ViewHolder {
		private TextView room_name, roo_price, room_yd;
	}

}
