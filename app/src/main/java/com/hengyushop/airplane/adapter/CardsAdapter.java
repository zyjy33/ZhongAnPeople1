package com.hengyushop.airplane.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CardsAdapter extends BaseAdapter {
	private ArrayList<CardDao> lists;
	private Context context;

	public CardsAdapter(ArrayList<CardDao> lists, Context context) {
		// TODO Auto-generated constructor stub
		this.lists = lists;
		this.context = context;
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
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView view = new TextView(context);
		view.setText(lists.get(arg0).getCard());
		return view;
	}

}
