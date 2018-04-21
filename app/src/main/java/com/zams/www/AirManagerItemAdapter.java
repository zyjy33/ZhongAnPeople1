package com.zams.www;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.airplane.data.AirDo;
import com.hengyushop.demo.airplane.CityDB;

import java.util.ArrayList;

public class AirManagerItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<AirDo> list;
	private Handler handler;

	public AirManagerItemAdapter(Context context, ArrayList<AirDo> list,
								 Handler handler) {
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
			view = LinearLayout.inflate(context, R.layout.air_manager_item,
					null);
			holder.v1 = (TextView) view.findViewById(R.id.v1);
			holder.v2 = (TextView) view.findViewById(R.id.v2);
			holder.v3 = (TextView) view.findViewById(R.id.v3);
			holder.v4 = (TextView) view.findViewById(R.id.v4);
			holder.v5 = (TextView) view.findViewById(R.id.v5);
			holder.v6 = (TextView) view.findViewById(R.id.v6);
			holder.v7 = (TextView) view.findViewById(R.id.v7);
			holder.v8 = (TextView) view.findViewById(R.id.v8);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.v1.setText(list.get(index).getAirName()
				+ list.get(index).getFlightNo());
		holder.v2.setText(list.get(index).getTime());
		CityDB db1 = new CityDB(context);
		holder.v3.setText(db1.getJicBySam(list.get(index).getOrgCity()));
		holder.v4.setText(list.get(index).getDepTime().substring(0, 2)+":"+list.get(index).getDepTime().substring(2));
		holder.v5.setText("￥"+list.get(index).getPrice());
		CityDB db2 = new CityDB(context);
		holder.v6.setText(db2.getJicBySam(list.get(index).getDstCity()));
		holder.v7.setText(list.get(index).getArrTime().substring(0, 2)+":"+list.get(index).getArrTime().substring(2));
		String tag = list.get(index).getTag();
		if (tag.equals("1")) {
			holder.v8.setText("等待付款");
			holder.v8.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list.get(index).getTrade_no();
					handler.sendMessage(msg);
				}
			});
		} else if (tag.equals("2")) {
			holder.v8.setText("等待付款");
		} else if (tag.equals("3")) {
			holder.v8.setText("已发货");
		}



		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, v3, v4, v5, v6, v7, v8;

	}

}
