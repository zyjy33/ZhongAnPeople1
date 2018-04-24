package com.lglottery.www.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.lglottery.www.domain.TuiGuangBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class TuiGuangAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<TuiGuangBean> lists;
	private ImageLoader imageLoader;
	private Handler handler;

	public TuiGuangAdapter(Context context, ArrayList<TuiGuangBean> lists,
						   ImageLoader imageLoader, Handler handler) {
		this.context = context;
		this.lists = lists;
		this.handler = handler;
		this.imageLoader = imageLoader;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder holder = new ChildHolder();
		convertView = LinearLayout.inflate(context,
				R.layout.ware_infromation_share, null);
		holder.img_btn_sms = (ImageButton) convertView
				.findViewById(R.id.img_btn_sms);
		holder.img_btn_wechat = (ImageButton) convertView
				.findViewById(R.id.img_btn_wechat);
		holder.img_btn_wx_friend = (ImageButton) convertView
				.findViewById(R.id.img_btn_wx_friend);

		holder.img_btn_wechat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Message msg = handler.obtainMessage();
				msg.what = 2;
				msg.obj = lists.get(groupPosition);
				handler.sendMessage(msg);
			}
		});
		holder.img_btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Message msg = handler.obtainMessage();
				msg.what = 3;
				msg.obj = lists.get(groupPosition);
				handler.sendMessage(msg);
			}
		});
		holder.img_btn_sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Message msg = handler.obtainMessage();
				msg.what = 4;
				msg.obj = lists.get(groupPosition);
				handler.sendMessage(msg);
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return lists.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LinearLayout.inflate(context,
					R.layout.tuiguang_parent_item2, null);
			holder.tag = (ImageView) convertView.findViewById(R.id.tag);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.click = (LinearLayout) convertView.findViewById(R.id.click);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(RealmName.REALM_NAME + "/admin/weixin/"
				+ lists.get(groupPosition).getImage(), holder.tag);
		holder.title.setText(lists.get(groupPosition).getTitle());
		holder.content.setText(lists.get(groupPosition).getContent());
		holder.click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Message msg = handler.obtainMessage();
				msg.what = 0;
				msg.obj = lists.get(groupPosition);
				// 此处替换成详细信息的内容数据
				System.out.println("点击");
				handler.sendMessage(msg);
			}
		});
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private class ViewHolder {
		TextView title;
		TextView content;
		ImageView tag;
		LinearLayout click;

	}

	private class ChildHolder {
		ImageButton img_btn_wechat, img_btn_wx_friend, img_btn_sms;
	}
}
