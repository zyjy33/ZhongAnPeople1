package com.hengyushop.demo.train;

import java.util.ArrayList;

import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TraindetailPersonItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<TrainPersonItem> list;
	private Handler handler;
	public TraindetailPersonItemAdapter(Context context,
			ArrayList<TrainPersonItem> list,Handler handler) {
		this.context = context;
		this.list = list;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void putData() {
		notifyDataSetChanged();
	}

	public void remove(int index) {
		notifyDataSetChanged();
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
	public View getView(final int index, View view, ViewGroup arg2) {
		  ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.train_person_item,
					null);
			holder.v1 = (TextView) view.findViewById(R.id.v1);
			holder.v2 = (TextView) view.findViewById(R.id.v2);
			holder.v3 = (TextView) view.findViewById(R.id.v3);
			holder.checkedMap = (CheckBox) view.findViewById(R.id.ck);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.v1.setText(list.get(index).getContactUserName());
		holder.v2.setText(list.get(index).getTempPiao());
		holder.v3.setText(list.get(index).getDocumentNumber());
		holder.v2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Message msg = new Message();
				msg.what = 4;
				msg.arg1 = index;
				handler.sendMessage(msg);
			}
		});
		holder.checkedMap.setVisibility(View.GONE);

		return view;
	}

	public class ViewHolder {
		private TextView v1, v2, v3;
		private CheckBox checkedMap;

	}

}
