package com.zams.www;

import android.widget.ListView;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class OneListActivity extends BaseActivity{
	private ListView list_view;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_list_activity);
		list_view = (ListView) findViewById(R.id.one_list);
		
	};
}
