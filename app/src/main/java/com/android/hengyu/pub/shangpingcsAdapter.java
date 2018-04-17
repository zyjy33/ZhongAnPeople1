package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.airplane.adapter.SpcsAdapter;
import com.hengyushop.entity.GuigeData;
import com.zams.www.R;

public class shangpingcsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GuigeData> list;
	private LayoutInflater inflater;
	private ArrayList data;

	public shangpingcsAdapter(ArrayList<GuigeData> list, ArrayList data,
			Context context) {
		this.list = list;
		this.data = data;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list.size() < 1) {

			return 0;
		} else {

			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.guige_item, null);
		TextView tv_letter = (TextView) convertView.findViewById(R.id.tv_zhuti);
		LinearLayout addview = (LinearLayout) convertView
				.findViewById(R.id.addview);
		tv_letter.setText(list.get(position).getTitle());

		addview.removeAllViews();

		for (int i = 0; i < list.get(position).getList().size(); i++) {// item_driver_evaluater_list_item
			try {

				View vi = LayoutInflater.from(context).inflate(
						R.layout.guige_item, null);

				GridView gridview = (GridView) vi.findViewById(R.id.gridView);
				SpcsAdapter MyAdapter2 = new SpcsAdapter(data, context);
				gridview.setAdapter(MyAdapter2);

				addview.addView(vi);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return convertView;
	}
}