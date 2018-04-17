package com.android.hengyu.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.hengyushop.json.HttpUtils;
import com.umpay.api.common.ReqData;
import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PostWorkListAdapter extends BaseAdapter {
	private ArrayList<WorkListDo> list;
	private Context context;

	public PostWorkListAdapter(ArrayList<WorkListDo> list, Context context) {
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
		holder.sd.setText(list.get(position).getAddress());
		holder.v1.setText(list.get(position).getTitle());
		holder.v2.setText(list.get(position).getCompany());
		holder.v3.setText(list.get(position).getSalary());

		try {
			holder.time.setText(HttpUtils.getSimpleTime(list.get(position)
					.getTime(), "MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, v3, time, sd;
	}
}
