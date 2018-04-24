package com.hengyushop.airplane.adapter;

import java.util.ArrayList;

import com.lglottery.www.domain.CategoryDomain;
import com.zams.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryLayoutAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<CategoryDomain> list;

	public CategoryLayoutAdapter(Context context, ArrayList<CategoryDomain> list) {

		this.context = context;
		this.list = list;
	}

	public void putLists(ArrayList<CategoryDomain> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {

		final ViewHolder holder;
		if (view == null || view.getTag(R.drawable.icon + index) == null) {
			holder = new ViewHolder();

			view = LinearLayout.inflate(context,
					R.layout.category_layout1_item, null);
			holder.categoryName = (TextView) view.findViewById(R.id.layoutp1);
			view.setTag(R.drawable.icon + index);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.icon + index);
		}
		holder.categoryName.setText(list.get(index).getCategoryName());

		return view;
	}

	public class ViewHolder {
		TextView categoryName;
	}
}
