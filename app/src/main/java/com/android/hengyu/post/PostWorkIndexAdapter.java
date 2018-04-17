package com.android.hengyu.post;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PostWorkIndexAdapter extends BaseAdapter {
	private ArrayList<WorkIndexDo> list;
	private Context context;

	public PostWorkIndexAdapter(ArrayList<WorkIndexDo> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		TextView view2 = new TextView(context);
		view2.setHeight(90);
		view2.setBackgroundResource(R.color.post_work_item);
		view2.setPadding(4, 2, 4, 2);
		view2.setText(list.get(position).getName());
		view2.setGravity(Gravity.CENTER_VERTICAL);
		view2.setTextColor(R.color.white);
		return view2;
	}

}
