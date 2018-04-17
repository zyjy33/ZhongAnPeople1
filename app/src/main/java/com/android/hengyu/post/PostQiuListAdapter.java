package com.android.hengyu.post;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.json.HttpUtils;
import com.zams.www.R;

import java.text.ParseException;
import java.util.ArrayList;

public class PostQiuListAdapter extends BaseAdapter {
	private ArrayList<QiuListDo> list;
	private Context context;

	public PostQiuListAdapter(ArrayList<QiuListDo> list, Context context) {
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
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.post_work_item, null);
			holder.v1 = (TextView) view.findViewById(R.id.name);
			holder.v2 = (TextView) view.findViewById(R.id.add);
			holder.v3 = (TextView) view.findViewById(R.id.pri);
			holder.sd = (TextView) view.findViewById(R.id.sd);
			holder.time = (TextView) view.findViewById(R.id.time);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.sd.setText(list.get(position).getAge() + "岁");
		holder.v1.setText(list.get(position).getUsername());
		holder.v2.setText(list.get(position).getResumeTitle());
		holder.v3.setText(list.get(position).getEducation_tmp());
		try {
			holder.time.setText(HttpUtils.getSimpleTime(list.get(position)
					.getCreateTime(), "MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, v3, time, sd;
	}
}
