package com.lglottery.www.adapter;

import java.util.ArrayList;

import com.lglottery.www.domain.Lglottery_Item;
import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LglotteryListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Lglottery_Item> list;
	private Handler handler;

	public LglotteryListAdapter(Context context,
			ArrayList<Lglottery_Item> list, Handler handler) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.handler = handler;
		this.context = context;
	}

	public void putList(ArrayList<Lglottery_Item> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.lglottery_list_item,
					null);
			holder.button = (Button) view.findViewById(R.id.lglottery_play);
			holder.textView = (TextView) view.findViewById(R.id.lglottery_view);
			// lottery_pay
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.button.setText(list.get(position).getPlayName());
		holder.textView.setText(context.getString(R.string.lottery_pay, list
				.get(position).getJinbi()));
		holder.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.obj = list.get(position);
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});
		return view;
	}

	public class ViewHolder {
		public Button button;
		public TextView textView;
	}

}
