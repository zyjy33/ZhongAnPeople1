package com.lglottery.www.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lglottery.www.common.U;
import com.lglottery.www.domain.Lglottery_Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class LglotteryLogAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Lglottery_Log> lists;
	private ImageLoader imageLoader;

	public LglotteryLogAdapter(Context context, ArrayList<Lglottery_Log> lists,
							   ImageLoader imageLoader) {

		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;

	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void putList(ArrayList<Lglottery_Log> lists) {
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
			view = LinearLayout.inflate(context, R.layout.lglottery_log_item,
					null);
			holder.lglottery_id = (TextView) view
					.findViewById(R.id.lglottery_id);
			holder.lglottery_name = (TextView) view
					.findViewById(R.id.lglottery_name);
			holder.lglottery_time = (TextView) view
					.findViewById(R.id.lglottery_time);
			holder.lglottery_class = (TextView) view
					.findViewById(R.id.lglottery_class);
			holder.lglottery_img = (ImageView) view
					.findViewById(R.id.lglottery_img);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.lglottery_id.setText(context.getString(R.string.lglottery_lg_id,
				lists.get(position).getId()));
		String status = lists.get(position).getStatus();
		switch (Integer.parseInt(status)) {
			case 0:
				holder.lglottery_class.setText("未结束");
				break;
			case 1:
				holder.lglottery_name.setText(lists.get(position).getName());
				imageLoader.displayImage(U.IP + lists.get(position).getImg(),
						holder.lglottery_img);
				holder.lglottery_class.setText("已结束");
				break;
			default:
				break;
		}

		holder.lglottery_time.setText(lists.get(position).getTime());

		return view;
	}

	public class ViewHolder {
		ImageView lglottery_img;
		TextView lglottery_id, lglottery_name, lglottery_time, lglottery_class;
	}

}
