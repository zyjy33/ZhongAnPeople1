package com.zams.www.health;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zams.www.R;

import java.util.List;

public class ProjectEvaluateAdapter extends BaseAdapter{
	private Context context;
	private List<Object> list;


	public ProjectEvaluateAdapter(Context context, List<Object> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View conterView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (conterView == null) {

			conterView = View.inflate(context, R.layout.item_health_project_evalute, null);
			viewHolder = new ViewHolder(conterView);
			conterView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) conterView.getTag();
		}
		/*
		 * 加载数据/
		 */
		return conterView;
	}

	static class ViewHolder{
		ImageView pic,add_order;
		TextView name,content,phone;
		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub
			pic = (ImageView) view.findViewById(R.id.pic);
			add_order = (ImageView) view.findViewById(R.id.add_order);
			name = (TextView) view.findViewById(R.id.name);
			content = (TextView) view.findViewById(R.id.content);
			phone = (TextView) view.findViewById(R.id.phone);

		}
	}

}
