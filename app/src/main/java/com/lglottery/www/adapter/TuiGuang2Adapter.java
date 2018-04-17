package com.lglottery.www.adapter;

import java.util.ArrayList;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.lglottery.www.domain.TuiGuangBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TuiGuang2Adapter extends BaseAdapter {
	private Context context;
	private ArrayList<TuiGuangBean> lists;
	private ImageLoader imageLoader;
	private Handler handler;
	public static AQuery aQuery;

	public TuiGuang2Adapter(Context context, ArrayList<TuiGuangBean> lists,
			ImageLoader imageLoader, Handler handler) {
		this.context = context;
		this.lists = lists;
		this.handler = handler;
		this.imageLoader = imageLoader;
		aQuery = new AQuery(context);
	}

	private class ViewHolder {
		TextView title;
		TextView content, tv_time;
		ImageView tag;
		LinearLayout click;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int groupPosition, View convertView,
			ViewGroup arg2) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// convertView =
			// LinearLayout.inflate(context,R.layout.tuiguang_parent_item2,
			// null);
			convertView = LinearLayout.inflate(context,
					R.layout.nilaida_wolaisong_item, null);
			holder.tag = (ImageView) convertView.findViewById(R.id.tag);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.click = (LinearLayout) convertView.findViewById(R.id.click);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			holder.title.setText(lists.get(groupPosition).getTitle());
			holder.content.setText(lists.get(groupPosition).getSubtitle());
			holder.tv_time.setText(lists.get(groupPosition).getAdd_time());
			aQuery.id(holder.tag).image(
					RealmName.REALM_NAME_HTTP
							+ lists.get(groupPosition).getImg_url());
			holder.click.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = lists.get(groupPosition).id;
					handler.sendMessage(msg);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;

	}

}
