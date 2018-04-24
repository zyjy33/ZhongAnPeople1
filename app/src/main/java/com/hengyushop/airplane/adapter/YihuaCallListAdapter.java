package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lglottery.www.domain.YiHuaCall;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class YihuaCallListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<YiHuaCall> listDomains;
	private ImageLoader imageLoader;
	private Handler handler;

	public YihuaCallListAdapter(Context context,
								ArrayList<YiHuaCall> listDomains, ImageLoader imageLoader,
								Handler handler) {

		this.context = context;
		this.listDomains = listDomains;
		this.imageLoader = imageLoader;
		this.handler = handler;
	}

	/**
	 * 装载数据
	 */
	public void putLists(ArrayList<YiHuaCall> listDomains) {
		this.listDomains = listDomains;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return listDomains.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listDomains.get(arg0);
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
			view = LinearLayout
					.inflate(context, R.layout.yihua_call_list, null);
			holder.item1 = (ImageView) view.findViewById(R.id.item1);
			holder.item2 = (TextView) view.findViewById(R.id.item2);
			holder.item3 = (TextView) view.findViewById(R.id.item3);
			holder.name = (TextView) view.findViewById(R.id.name);
			view.setTag(R.drawable.icon + index);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.icon + index);
		}
		if (listDomains.get(index).getType() != -1) {
			switch (listDomains.get(index).getType()) {
				case 0:
					imageLoader.displayImage("drawable://"
							+ R.drawable.yihua_call_bj, holder.item1);
					break;
				case 1:
					imageLoader.displayImage("drawable://"
							+ R.drawable.yihua_call_bj, holder.item1);
					break;
				case 2:
					imageLoader.displayImage("drawable://"
							+ R.drawable.yihua_call_b, holder.item1);
					break;
				case 3:
					imageLoader.displayImage("drawable://"
							+ R.drawable.yihua_call_j, holder.item1);
					break;

				default:
					break;
			}
		} else {
			holder.item1.setVisibility(View.INVISIBLE);
		}
		if (listDomains.get(index).getName() != null) {
			holder.name.setText(listDomains.get(index).getName());
		} else {
			holder.name.setText("");
		}

		if (listDomains.get(index).getTime() != null) {
			holder.item3.setText(listDomains.get(index).getTime());
		} else {
			holder.item3.setText("");
		}

		holder.item2.setText(listDomains.get(index).getPhone());

		return view;
	}

	public class ViewHolder {

		ImageView item1;
		TextView item2, item3, name;

	}

}
