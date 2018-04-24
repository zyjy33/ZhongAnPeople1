package com.hengyushop.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class OnePersonAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<One_Person_Bean> lists;
	private ImageLoader imageLoader;

	public OnePersonAdapter(Context context, ArrayList<One_Person_Bean> lists,
							ImageLoader imageLoader) {

		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;
	}

	public void putData(ArrayList<One_Person_Bean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}
	public static class ViewHolder {
		public ImageView item0;
		public TextView   item1, item2,item3,item5,item6,item7;
		public ProgressBar item4;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return lists.get(groupPosition).getChild().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {


		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.one_person_child, null);
		}
		TextView  count = (TextView) convertView.findViewById(R.id.count);
		count.setText(""+lists.get(groupPosition).getChild().size());
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.person_child_layout);
		layout.removeAllViews();
		for(int i=0;i<lists.get(groupPosition).getChild().size();i++){
			LinearLayout child = (LinearLayout) LayoutInflater.from(context).inflate(
					R.layout.one_person_child_item, null);
			TextView item0 = (TextView) child.findViewById(R.id.child0);
			TextView item1 = (TextView) child.findViewById(R.id.child1);
			TextView item2 = (TextView) child.findViewById(R.id.child2);
			item0.setText(lists.get(groupPosition).getChild().get(i).getTime());
			item1.setText(lists.get(groupPosition).getChild().get(i).getCount()+"人次");
			item2.setText(lists.get(groupPosition).getChild().get(i).getNum());
			layout.addView(child);
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		if(lists.get(groupPosition).getChild().size()!=0){
			return 1;
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return lists.get(groupPosition);
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
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.one_person_item, null);
			viewHolder = new ViewHolder();
			viewHolder.item0 = (ImageView) convertView.findViewById(R.id.item0);
			viewHolder.item1 = (TextView) convertView.findViewById(R.id.item1);
			viewHolder.item2 = (TextView) convertView.findViewById(R.id.item2);
			viewHolder.item3 = (TextView) convertView.findViewById(R.id.item3);
			viewHolder.item4 = (ProgressBar) convertView.findViewById(R.id.item4);
			viewHolder.item5 = (TextView) convertView.findViewById(R.id.item5);
			viewHolder.item6 = (TextView) convertView.findViewById(R.id.item6);
			viewHolder.item7 = (TextView) convertView.findViewById(R.id.item7);
			//添加到新的界面
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(RealmName.REALM_NAME+"/admin/"+lists.get(groupPosition).getProFaceImg(),  viewHolder.item0);
		if(lists.get(groupPosition).getCurrentGameDone().equals("0")){
			viewHolder.item1.setText("正在进行..");
		}else {
			viewHolder.item1.setText("已揭晓");
		}

		viewHolder.item2.setText(lists.get(groupPosition).getProName());
		viewHolder.item3.setText(lists.get(groupPosition).getHaveTatalJuGouMa());
		viewHolder.item4.setProgress((int)(Double.parseDouble(lists.get(groupPosition).getHasJoinedNum())/Double.parseDouble(lists.get(groupPosition).getNeedGameUserNum())*1000));
		viewHolder.item5.setText(lists.get(groupPosition).getHasJoinedNum());
		viewHolder.item6.setText(lists.get(groupPosition).getNeedGameUserNum());
		viewHolder.item7.setText(""+(Integer.parseInt(lists.get(groupPosition).getNeedGameUserNum())-Integer.parseInt(lists.get(groupPosition).getHasJoinedNum())));
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

}
