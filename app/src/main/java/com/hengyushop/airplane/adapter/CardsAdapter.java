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

		this.lists = lists;
		this.context = context;
	}

	@Override
	public int getCount() {

		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {

		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		TextView view = new TextView(context);
		view.setText(lists.get(arg0).getCard());
		return view;
	}

}
