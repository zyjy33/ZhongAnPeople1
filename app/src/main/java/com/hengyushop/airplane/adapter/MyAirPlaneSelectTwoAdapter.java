package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengyushop.airplane.data.AirPlaneSelectFlightData;
import com.hengyushop.airplane.data.AirPlaneSelectMoneyData;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class MyAirPlaneSelectTwoAdapter extends BaseExpandableListAdapter {

	private List<AirPlaneSelectFlightData> flightDatas;
	private List<ArrayList<AirPlaneSelectMoneyData>> moneyDatas;
	private AirPlaneSelectFlightData fDatas;
	private AirPlaneSelectMoneyData mDatas;
	private LayoutInflater mInflater = null;
	private View tempView;
	private ChildViewHolder childHolder;
	private GroupViewHolder groupHolder;
	private OnClickListener clickListener;

	public MyAirPlaneSelectTwoAdapter(Context ct,
									  List<AirPlaneSelectFlightData> flightDatas,
									  ArrayList<ArrayList<AirPlaneSelectMoneyData>> moneyDatas,
									  OnClickListener onclick) {
		// TODO Auto-generated constructor stub
		this.flightDatas = flightDatas;
		this.moneyDatas = moneyDatas;
		mInflater = LayoutInflater.from(ct);
		clickListener = onclick;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (moneyDatas != null) // /
		{
			if (moneyDatas.size() > groupPosition
					&& moneyDatas.get(groupPosition) != null) // /
			{
				moneyDatas.get(groupPosition).size(); // //
			} else {
				return 0;
			}
		} else {
			return 0;
		}
		return (moneyDatas != null) ? moneyDatas.get(groupPosition).size() : 0; // /
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return (flightDatas != null) ? flightDatas.get(groupPosition) : null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return (flightDatas != null) ? flightDatas.size() : 0;
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null) {
			tempView = convertView;
		} else {
			tempView = mInflater.inflate(R.layout.airplane_listitem_select_two,
					parent, false);
		}
		groupHolder = (GroupViewHolder) tempView.getTag();
		if (groupHolder == null) {
			groupHolder = new GroupViewHolder();
			groupHolder.nameTextView = (TextView) tempView
					.findViewById(R.id.tv_plane_name);
			tempView.setTag(groupHolder);
		}
		fDatas = flightDatas.get(groupPosition);
		groupHolder.nameTextView.setText(fDatas.FlightName);
		return tempView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView != null) {
			tempView = convertView;
		} else {
			tempView = mInflater.inflate(
					R.layout.airplane_listiten_select_three, parent, false);
		}
		childHolder = (ChildViewHolder) tempView.getTag();
		if (childHolder == null) {
			childHolder = new ChildViewHolder();
			childHolder.nameTextView = (TextView) tempView
					.findViewById(R.id.tv_name);
			childHolder.rmdBtn = (Button) tempView.findViewById(R.id.btn_order);
			childHolder.rmdBtn.setOnClickListener(clickListener);
		}
		mDatas = moneyDatas.get(groupPosition).get(childPosition);
		childHolder.nameTextView.setText(mDatas.name);
		childHolder.rmdBtn.setTag(groupPosition + "," + childPosition); // 设置标签
		tempView.setTag(childHolder);
		return tempView;
	}

	public class GroupViewHolder {
		private TextView nameTextView;
	}

	public class ChildViewHolder {
		private ImageView imageView;
		private TextView nameTextView;
		private TextView briefTextView;
		public Button rmdBtn;
		private RelativeLayout bookBoxLayout;
	}

}
