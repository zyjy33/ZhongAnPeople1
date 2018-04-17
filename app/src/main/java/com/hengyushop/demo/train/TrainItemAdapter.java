package com.hengyushop.demo.train;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ChePiaoData> list;
	private ImageLoader imageLoader;

	public TrainItemAdapter(Context context, ArrayList<ChePiaoData> list,
			ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.imageLoader = imageLoader;
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
		// TODO Auto-generated method stub
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub ViewHolder holder = null;
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context,
					R.layout.train_listitem_select, null);
			holder.start_time = (TextView) view.findViewById(R.id.start_time);
			holder.start_add = (TextView) view.findViewById(R.id.start_add);
			holder.arrive_time = (TextView) view.findViewById(R.id.arrive_time);
			holder.arrive_add = (TextView) view.findViewById(R.id.arrive_add);
			holder.total_time = (TextView) view.findViewById(R.id.total_time);
			holder.tv_train_number = (TextView) view
					.findViewById(R.id.tv_train_number);
			holder.daoda = (TextView) view.findViewById(R.id.daoda);
			holder.train_grade = (TextView) view.findViewById(R.id.train_grade);
			holder.image = (ImageView) view.findViewById(R.id.image);
			holder.a0 = (ImageView) view.findViewById(R.id.a0);
			holder.a1 = (ImageView) view.findViewById(R.id.a1);
			holder.v1 = (TextView) view.findViewById(R.id.v1);
			holder.v2 = (TextView) view.findViewById(R.id.v2);
			holder.v3 = (TextView) view.findViewById(R.id.v3);
			holder.v4 = (TextView) view.findViewById(R.id.v4);
			holder.v5 = (TextView) view.findViewById(R.id.v5);
			holder.v6 = (TextView) view.findViewById(R.id.v6);
			holder.v7 = (TextView) view.findViewById(R.id.v7);
			holder.v8 = (TextView) view.findViewById(R.id.v8);
			holder.v9 = (TextView) view.findViewById(R.id.v9);
			holder.v10 = (TextView) view.findViewById(R.id.v10);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		ChePiaoData data = list.get(index);
		holder.start_time.setText(data.getFromTime());
		holder.start_add.setText(data.getFromStation());
		holder.arrive_add.setText(data.getToStation());
		holder.arrive_time.setText(data.getToTime());
		holder.daoda.setText("");
		holder.tv_train_number.setText(data.getTrainCode());
		holder.total_time.setText(data.getTakeTime());

		if (data.getStartStationCode().equals(data.getFromStationCode())) {
			imageLoader.displayImage("drawable://" + R.drawable.shi, holder.a0);
		} else {
			imageLoader.displayImage("drawable://" + R.drawable.guo, holder.a0);
		}

		if (data.getToStationCode().equals(data.getEndStationCode())) {
			imageLoader.displayImage("drawable://" + R.drawable.zhong,
					holder.a1);
		} else {
			imageLoader.displayImage("drawable://" + R.drawable.guo, holder.a1);
		}

		String business = data.getBusiness();// 商务座
		String best_seat = data.getBest_seat();// 特等座
		String one_seat = data.getOne_seat();// 一等座
		String two_seat = data.getTwo_seat();// 二等座
		String vag_sleeper = data.getVag_sleeper();// 高级软座
		String soft_sleeper = data.getSoft_sleeper();// 软卧
		String hard_sleeper = data.getHard_sleeper();// 硬卧
		String soft_seat = data.getSoft_seat();// 软座
		String hard_seat = data.getHard_seat();// 硬座
		String none_seat = data.getNone_seat();// 无座
		if (isHere(business)) {
			holder.v1.setVisibility(View.VISIBLE);
			addTextViewValue("商务座", business, holder.v1);

		} else {
			holder.v1.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(best_seat)) {
			holder.v2.setVisibility(View.VISIBLE);
			addTextViewValue("特等座", best_seat, holder.v2);
		} else {
			holder.v2.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(one_seat)) {
			holder.v3.setVisibility(View.VISIBLE);
			addTextViewValue("一等座", one_seat, holder.v3);
		} else {
			holder.v3.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(two_seat)) {
			holder.v4.setVisibility(View.VISIBLE);
			addTextViewValue("二等座", two_seat, holder.v4);
		} else {
			holder.v4.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(vag_sleeper)) {
			holder.v5.setVisibility(View.VISIBLE);
			addTextViewValue("高级软卧", vag_sleeper, holder.v5);
		} else {
			holder.v5.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(soft_sleeper)) {
			holder.v6.setVisibility(View.VISIBLE);
			addTextViewValue("软卧", soft_sleeper, holder.v6);
		} else {
			holder.v6.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(hard_sleeper)) {
			holder.v7.setVisibility(View.VISIBLE);
			addTextViewValue("硬卧", hard_sleeper, holder.v7);
		} else {
			holder.v7.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(soft_seat)) {
			holder.v8.setVisibility(View.VISIBLE);
			addTextViewValue("软座", soft_seat, holder.v8);
		} else {
			holder.v8.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(hard_seat)) {
			holder.v9.setVisibility(View.VISIBLE);
			addTextViewValue("硬座", hard_seat, holder.v9);
		} else {
			holder.v9.setVisibility(View.GONE);
		}
		// ---------------------------------------
		if (isHere(none_seat)) {
			holder.v10.setVisibility(View.VISIBLE);
			addTextViewValue("无座", none_seat, holder.v10);
		} else {
			holder.v10.setVisibility(View.GONE);
		}
		// ---------------------------------------

		return view;
	}

	/**
	 * 
	 * @param tag
	 *            名称
	 * @param business
	 *            变量
	 * @param v
	 *            对象
	 */
	private void addTextViewValue(String tag, String business, TextView v) {
		if (!business.equals("无")) {
			String temp = tag + " <font color='green'>" + business + "</font>";
			v.setText(Html.fromHtml(temp));
		} else {
			v.setText(tag + " " + business);
		}
	}

	/**
	 * 是否存在
	 * 
	 * @param temp1
	 * @param temp2
	 * @return
	 */
	private boolean isHere(String param) {
		if (param.length() != 0 && !param.contains("_")) {
			return true;
		}
		return false;

	}

	public class ViewHolder {
		private TextView start_time, start_add, arrive_time, arrive_add,
				total_time, tv_train_number, daoda, train_grade;
		private ImageView image, a0, a1;
		private TextView v1, v2, v3, v4, v5, v6, v7, v8, v9, v10;

	}

}
