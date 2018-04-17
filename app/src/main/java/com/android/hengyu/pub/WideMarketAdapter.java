package com.android.hengyu.pub;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.airplane.adapter.zaylAdapter;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.zams.www.R;

import java.util.ArrayList;

public class WideMarketAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ZhongAnYlData> liDos;
	private ArrayList<ZhongAnYlBean> lists_ll;
	private Handler handler;

	public WideMarketAdapter(ArrayList<ZhongAnYlData> liDos,
							 ArrayList<ZhongAnYlBean> list_ll, Context context, Handler handler) {
		this.liDos = liDos;
		this.lists_ll = list_ll;
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
		try {

			if (view == null
					|| view.getTag(R.drawable.ic_launcher + arg0) == null) {
				holder = new ViewHolder();
				view = LinearLayout.inflate(context, R.layout.zayl_item_telie,
						null);
				holder.name = (TextView) view.findViewById(R.id.tagname);
				holder.item_img = (GridView) view.findViewById(R.id.tagvalue);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view
						.getTag(R.drawable.ic_launcher + arg0);
			}
			System.out.println("arg0------------------------" + arg0);
			holder.name.setText(liDos.get(arg0).getTitle());
			try {

				// for (int i = 0; i < lists_ll.size(); i++) {
				//
				// }
				final ArrayList<ZhongAnYlBean> items = liDos.get(arg0)
						.getList();
				// ArrayList<ZhongAnYlBean> items =
				// lists_ll.get(arg0).getList();
				System.out.println("items------------------------"
						+ items.size());
				// String name = liDos.get(arg0).getList().get(arg0).getTitle();

				// String name = items.get(arg0).getTitle();
				// System.out.println("name------------------------"+name);
				// WideChildAdapter childAdapter = new WideChildAdapter(items,
				// context, handler);
				// holder.item_img.setAdapter(childAdapter);
				zaylAdapter MyAdapter2 = new zaylAdapter(items, context);
				holder.item_img.setAdapter(MyAdapter2);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return view;
	}

	public class ViewHolder {
		private TextView name;
		private GridView item_img;
	}
}
