package com.lglottery.www.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class ShouYiAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ContentValues> lists;
	private ImageLoader imageLoader;
	private String type;

	public ShouYiAdapter(Context context, ArrayList<ContentValues> lists,
						 ImageLoader imageLoader, String type) {

		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;
		this.type = type;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void putList(ArrayList<ContentValues> lists) {
		this.lists = lists;
		notifyDataSetChanged();
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
	public View getView(int position, View view, ViewGroup arg2) {

		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.shouyi_item, null);
			holder.img = (ImageView) view.findViewById(R.id.ico);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.price = (TextView) view.findViewById(R.id.shouyi);
			holder.count = (TextView) view.findViewById(R.id.count);
			holder.tag = (TextView) view.findViewById(R.id.tag);
			holder.tag_b = (TextView) view.findViewById(R.id.tag_b);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}

		if (type.equals("1")) {
			holder.tag.setText("收益");
		} else if (type.equals("2")) {
			holder.tag.setText("福利");
		}
		imageLoader.displayImage(RealmName.REALM_NAME
				+ lists.get(position).getAsString("url"), holder.img);

		holder.name.setText(lists.get(position).getAsString("name"));
		holder.count.setText(lists.get(position).getAsString("count"));
		holder.price.setText(lists.get(position).getAsString("price"));
		holder.tag_b.setText(String.valueOf(position + 1));
		return view;
	}

	public class ViewHolder {
		ImageView img;
		TextView name, price, count, tag, tag_b;
	}

}
