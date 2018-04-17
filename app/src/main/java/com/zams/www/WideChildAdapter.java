package com.zams.www;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.dao.WideMarketDo;

import java.util.ArrayList;

public class WideChildAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<WideMarketDo> items;
	private Handler handler;

	public WideChildAdapter(ArrayList<WideMarketDo> items, Context context,
							Handler handler) {
		this.items = items;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void setContact() {

		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// System.out.println(users.size()+"頭像"+users.get(arg0).getImg());
		final ViewHolder holder;
		if (view == null || view.getTag(R.drawable.ic_launcher + arg0) == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.category_textview,
					null);
			holder.category_textview = (TextView) view
					.findViewById(R.id.category_textview);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.ic_launcher + arg0);
		}
		holder.category_textview.setText(items.get(arg0).getName());
		holder.category_textview.setTag(items.get(arg0).getId());
		holder.category_textview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				msg.obj = holder.category_textview;
				handler.sendMessage(msg);
			}
		});
		return view;
	}

	public class ViewHolder {
		private TextView category_textview;
	}
}
