package com.android.hengyu.pub;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.zams.www.R;

import java.util.ArrayList;

public class HealthPavilionAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GuigeData> liDos;
	private ArrayList<GuigeBean> list;
	private Handler handler;
	int p;

	public HealthPavilionAdapter(ArrayList<GuigeData> liDos,
								 ArrayList<GuigeBean> list, Context context, Handler handler) {
		this.liDos = liDos;
		this.list = list;
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
				view = LinearLayout.inflate(context,
						R.layout.jiangkang_guan_item, null);
				holder.name = (TextView) view.findViewById(R.id.tagname);
				holder.item_img = (GridView) view.findViewById(R.id.tagvalue);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view
						.getTag(R.drawable.ic_launcher + arg0);
			}

			holder.name.setText(liDos.get(arg0).getTitle());

			final ArrayList<GuigeBean> items = liDos.get(arg0).getList();

			WideChildAdapter childAdapter = new WideChildAdapter(items,
					context, handler);
			holder.item_img.setAdapter(childAdapter);

			for (int i = 0; i < liDos.get(arg0).getList().size(); i++) {
				p = i;
			}
			// holder.item_img.setOnItemClickListener(new OnItemClickListener()
			// {
			// @Override
			// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			// long arg3) {
			// System.out.println("=arg2============"+arg2);
			// //
			// System.out.println("============="+liDos.get(arg2).getList().get(p));
			// // Intent intent= new
			// Intent(context,SymptomsJieShaoActivity.class);
			// // intent.putExtra("summary",
			// liDos.get(arg2).getList().get(p).summary);
			// //
			// intent.putExtra("proposal",liDos.get(arg2).getList().get(p).proposal);
			// // intent.putExtra("cause",
			// liDos.get(arg2).getList().get(p).cause);
			// // intent.putExtra("doctor",
			// liDos.get(arg2).getList().get(p).doctor);
			// // intent.putExtra("title",
			// liDos.get(arg2).getList().get(p).title);
			// //
			// //// GuigeData bean= list_ll.get(arg2);
			// //// Bundle bundle = new Bundle();
			// //// bundle.putSerializable("bean", bean);
			// //// intent.putExtras(bundle);
			// // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// // context.startActivity(intent);
			//
			// // Message msg = new Message();
			// // msg.what = 1;
			// //// msg.obj = items;
			// // handler.sendMessage(msg);
			// }
			// });
			// }

		} catch (Exception e) {

			e.printStackTrace();
		}
		return view;
	}

	public class ViewHolder {
		private TextView name;
		private GridView item_img;
	}
}
