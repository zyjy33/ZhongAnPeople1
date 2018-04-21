package com.hengyushop.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class OneBuyListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<OneBuyBean> lists;
	private ImageLoader imageLoader;

	public OneBuyListAdapter(Context context, ArrayList<OneBuyBean> lists,
							 ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;
	}

	public void putData(ArrayList<OneBuyBean> lists) {
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
					R.layout.one_all_list, null);
			viewHolder = new ViewHolder();
			viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
			viewHolder.item2 = (ProgressBar) convertView
					.findViewById(R.id.item2);
			viewHolder.item0 = (ImageView) convertView.findViewById(R.id.item0);
			viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
			viewHolder.item4 = (TextView) convertView.findViewById(R.id.item4);
			viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.item1.setText(lists.get(arg0).getName() + "\t" + "价值 ￥:"
				+ lists.get(arg0).getMarket());
		viewHolder.item2
				.setProgress((int) (Double.parseDouble(lists.get(arg0)
						.getJoinNum())
						/ Double.parseDouble(lists.get(arg0).getNum()) * 1000));
		imageLoader.displayImage(
				RealmName.REALM_NAME + "/admin/" + lists.get(arg0).getImg(),
				viewHolder.item0);
		viewHolder.item3.setText(lists.get(arg0).getJoinNum());
		viewHolder.item4.setText(lists.get(arg0).getNum());
		viewHolder.item5.setText(String.valueOf(Integer.parseInt(lists
				.get(arg0).getNum())
				- Integer.parseInt(lists.get(arg0).getJoinNum())));
		return convertView;
	}

	public static class ViewHolder {
		public TextView item1, item3, item4, item5;
		public ProgressBar item2;
		public ImageView item0;
	}

}
