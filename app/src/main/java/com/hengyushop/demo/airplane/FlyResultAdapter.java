package com.hengyushop.demo.airplane;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zams.www.R;

import java.util.ArrayList;

public class FlyResultAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FlyResult> results;
	private Handler handler;

	public FlyResultAdapter(Context context, ArrayList<FlyResult> results,
							Handler handler) {

		this.results = results;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {

		return results.size();
	}

	public void setData(ArrayList<FlyResult> results) {
		this.results = results;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int arg0) {
		return results.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int index, View view, ViewGroup arg2) {

		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context,
					R.layout.airplane_listitem_select_two, null);
			holder.textView1 = (TextView) view.findViewById(R.id.tv_plane_name);
			holder.textView2 = (TextView) view.findViewById(R.id.first_time);
			holder.textView3 = (TextView) view
					.findViewById(R.id.first_airplane);
			holder.textView4 = (TextView) view.findViewById(R.id.end_time);
			holder.textView5 = (TextView) view.findViewById(R.id.end_airplane);
			holder.textView6 = (TextView) view.findViewById(R.id.tv_money);
			holder.textView7 = (TextView) view.findViewById(R.id.tv_discount);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.textView1.setText(results.get(index).getFlyCompany()
				+ results.get(index).getFlightNo());
		holder.textView2.setText(results.get(index).getFirstTime());
		CityDB db1 = new CityDB(context);
		CityDB db2 = new CityDB(context);
		holder.textView3.setText(db1.getJicBySam(results.get(index)
				.getOrgCity()) + results.get(index).getOrgJetquay());
		holder.textView4.setText(results.get(index).getEndTime());
		holder.textView5.setText(db2.getJicBySam(results.get(index)
				.getDstCity()) + results.get(index).getDstJetquay());
		holder.textView6.setText("￥" + results.get(index).getPrice());// 价格
		holder.textView7.setText("(" + results.get(index).getDiscount() + "折)");// 折扣
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Message msg = new Message();
				msg.what = 2;
				msg.obj = results.get(index);
				handler.sendMessage(msg);
			}
		});
		return view;
	}

	public class ViewHolder {
		private TextView textView1, textView2, textView3, textView4, textView5,
				textView6, textView7;
	}
}
