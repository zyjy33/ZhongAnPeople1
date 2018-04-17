package com.hengyushop.movie.adapter;

import java.util.ArrayList;

import com.android.hengyu.web.RealmName;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OneNewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<OneNewBean> lists;
	private ImageLoader imageLoader;

	public OneNewAdapter(Context context, ArrayList<OneNewBean> lists,
			ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;
	}

	public void putData(ArrayList<OneNewBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
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
	public View getView(final int arg0, View convertView, ViewGroup arg2) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.one_new_item, null);
			viewHolder = new ViewHolder();
			viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
			viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
			viewHolder.item0 = (ImageView) convertView.findViewById(R.id.item0);
			viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
			viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(
				RealmName.REALM_NAME + "/admin/" + lists.get(arg0).getImg(),
				viewHolder.item0);
		viewHolder.item1.setText(lists.get(arg0).getUsername());
		viewHolder.item2.setText(lists.get(arg0).getCount());
		viewHolder.item3.setText(lists.get(arg0).getNumber());
		viewHolder.item4.setText(lists.get(arg0).getTime());
		return convertView;
	}

	public static class ViewHolder {
		public TextView item3, item1, item2, item4;
		public ImageView item0;
	}

}
