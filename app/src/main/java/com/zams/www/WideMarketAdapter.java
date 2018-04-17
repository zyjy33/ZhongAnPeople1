package com.zams.www;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.dao.WideDo;
import com.hengyushop.dao.WideMarketDo;

import java.util.ArrayList;

public class WideMarketAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<WideDo> liDos;
	private Handler handler;

	public WideMarketAdapter(ArrayList<WideDo> liDos, Context context,
							 Handler handler) {
		this.liDos = liDos;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return liDos.size();
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
		ViewHolder holder;
		if (view == null || view.getTag(R.drawable.ic_launcher + arg0) == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.wide_market_item,
					null);
			holder.name = (TextView) view.findViewById(R.id.tagname);
			holder.item_img = (GridView) view.findViewById(R.id.tagvalue);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.ic_launcher + arg0);
		}

		holder.name.setText(liDos.get(arg0).getName());
		final ArrayList<WideMarketDo> items = liDos.get(arg0).getList();
		int len = items.size();
		WideChildAdapter childAdapter = new WideChildAdapter(items, context,
				handler);
		holder.item_img.setAdapter(childAdapter);
		int length = 0;
		if (len % 3 != 0) {
			length = 1 + (len / 3);
		} else {
			length = len / 3;
		}
		System.out.println("length:" + length);
		holder.item_img.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, length * 120));
		// holder.item_img.setBackgroundResource(R.drawable.found_category_item_normal);
		// holder.item_img.removeAllViews();
		// for (int i = 0; i < len; i++) {
		// final Button textView = (Button) LinearLayout.inflate(context,
		// R.layout.category_textview, null).findViewById(
		// R.id.category_textview);
		// textView.setText(items.get(i).getName());
		// textView.setSingleLine(true);
		// textView.setEllipsize(TruncateAt.END);
		// textView.setEms(6);
		// textView.setTextColor(R.color.grey);
		// textView.setBackgroundResource(R.drawable.clo);
		// textView.setLayoutParams(new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, 100));
		// // textView.setLayoutParams(LayoutParams.WRAP_CONTENT,50);
		// textView.setTag(items.get(i).getId());
		// holder.item_img.addView(textView);
		// textView.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// Message msg = new Message();
		// msg.what = 1;
		// msg.obj = textView;
		// handler.sendMessage(msg);
		// }
		// });
		// }
		/*
		 * int length = 0; if(len%3!=0){ length = 1+(len/3); }else { length =
		 * len/3; } holder.item_img.setLayoutParams(new
		 * LinearLayout.LayoutParams(198 * 3, 75 * ((len / 3) + 1)));
		 */
		// holder.select.setOnCheckedChangeListener(new
		// OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton cb, boolean arg1) {
		// /* Message msg = new Message();
		// msg.what = 110;*/
		// users.get(arg0).setCheck(arg1);
		// // msg.obj = users;
		// // handler.sendMessage(msg);
		// }
		// });
		return view;
	}

	public class ViewHolder {
		private TextView name;
		private GridView item_img;
	}
}
