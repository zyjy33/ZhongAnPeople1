package com.android.hengyu.pub;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.home.SymptomsJieShaoActivity;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigellBean;
import com.zams.www.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WideChildAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GuigeBean> items;
	private Handler handler;
	public static AQuery aQuery;

	public WideChildAdapter(ArrayList<GuigeBean> items, Context context,
							Handler handler) {
		this.items = items;
		this.context = context;
		this.handler = handler;
		aQuery = new AQuery(context);
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
	public View getView(final int position, View view, ViewGroup arg2) {
		// System.out.println(users.size()+"頭像"+users.get(arg0).getImg());
		final ViewHolder holder;
		if (view == null
				|| view.getTag(R.drawable.ic_launcher + position) == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.grid_item_ll, null);
			TextView tv = (TextView) view.findViewById(R.id.tv_item);
			ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
			RelativeLayout el_quanbu = (RelativeLayout) view
					.findViewById(R.id.el_quanbu);
			// TextView tv = BaseViewHolder.get(view, R.id.tv_item);
			// ImageView iv = BaseViewHolder.get(view, R.id.iv_item);
			// RelativeLayout el_quanbu = BaseViewHolder.get(view,
			// R.id.el_quanbu);
			tv.setText(items.get(position).getTitle());
			aQuery.id(iv).image(
					RealmName.REALM_NAME_HTTP
							+ items.get(position).getIcon_url());
			el_quanbu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					try {
						System.out.println("=arg2============" + position);
						System.out.println("=getTitle============"
								+ items.get(position).getTitle());

						Intent intent = new Intent(context,
								SymptomsJieShaoActivity.class);
						// ArrayList<GuigellBean> bean =
						// items.get(position).getList();
						List<GuigellBean> list = new ArrayList<GuigellBean>();
						list = items.get(position).getList();
						intent.putExtra("list", (Serializable) list);
						// ArrayList<String> beanll = new ArrayList<String>();
						// intent.putStringArrayListExtra(name, value);

						// GuigeBean bean = items.get(position);
						// Bundle bundle = new Bundle();
						// bundle.putSerializable("bean",(Serializable) list);
						// intent.putExtras(bundle);
						intent.putExtra("summary", items.get(position).summary);
						intent.putExtra("proposal",
								items.get(position).proposal);
						intent.putExtra("cause", items.get(position).cause);
						intent.putExtra("doctor", items.get(position).doctor);
						intent.putExtra("title", items.get(position).title);
						intent.putExtra("num", "1");
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						// Message msg = new Message();
						// msg.what = 1;
						// msg.obj = items;
						// handler.sendMessage(msg);
					} catch (Exception e) {

						e.printStackTrace();
					}

				}
			});
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view
					.getTag(R.drawable.ic_launcher + position);
		}
		// holder.category_textview.setText(items.get(arg0).getTitle());
		return view;
	}

	public class ViewHolder {
		private TextView category_textview;
	}
}
