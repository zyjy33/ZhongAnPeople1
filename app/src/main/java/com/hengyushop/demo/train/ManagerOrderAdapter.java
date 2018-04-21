package com.hengyushop.demo.train;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.airplane.data.ManagerTrainDo;
import com.zams.www.R;

import java.util.ArrayList;

public class ManagerOrderAdapter extends BaseAdapter {
	private ArrayList<ManagerTrainDo> lists;
	private Context context;
	private Handler handler;

	public ManagerOrderAdapter(ArrayList<ManagerTrainDo> lists,
							   Context context, Handler handler) {
		// TODO Auto-generated constructor stub
		this.lists = lists;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.train_order_item,
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
		holder.v1.setText(lists.get(arg0).getFromStation());
		holder.v2.setText(lists.get(arg0).getStartTime());
		holder.v3.setText(lists.get(arg0).getToStation());
		holder.v4.setText(lists.get(arg0).getArriveTime());
		if (lists.get(arg0).getOrderTag().equals("1")) {
			holder.v5.setText("继续付款");
			holder.v5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.what = 1;
					msg.obj = lists.get(arg0).getOrderNum();
					handler.sendMessage(msg);
				}
			});
		} else if (lists.get(arg0).getOrderTag().equals("2")) {
			holder.v5.setText("支付成功");
		} else if (lists.get(arg0).getOrderTag().equals("3")) {
			holder.v5.setText("已出票");

		} else if (lists.get(arg0).getOrderTag().equals("4")) {
			holder.v5.setText("已退款");

		} else if (lists.get(arg0).getOrderTag().equals("5")) {
			holder.v5.setText("订单失效");

		}
		holder.v6.setText(lists.get(arg0).getCheci());
		holder.v7.setText(lists.get(arg0).getOrderTime());
		holder.v8.setText("￥"+lists.get(arg0).getPrice());
		return view;
	}

	public class ViewHolder {

		private TextView v1, v2, v3, v4, v5,v6,v7,v8;

	}

}
