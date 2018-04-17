package com.zams.www.health;

import java.util.List;

import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectAdapter extends BaseAdapter{
	private Context context;
	private List<Object> list;
	

	public ProjectAdapter(Context context, List<Object> list) {
		super();
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
			
			conterView = View.inflate(context, R.layout.item_health_project, null);
			viewHolder = new ViewHolder(conterView);
			conterView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) conterView.getTag();
		}
		/*
		 *
		 */
		return conterView;
	}
	
	static class ViewHolder{
		ImageView pic,add_order;
		TextView name,content;
		public ViewHolder(View view) {
			// TODO Auto-generated constructor stub
			pic = (ImageView) view.findViewById(R.id.pic);
			add_order = (ImageView) view.findViewById(R.id.add_order);
			name = (TextView) view.findViewById(R.id.name);
			content = (TextView) view.findViewById(R.id.content);

		}
	}

}
