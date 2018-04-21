package com.hengyushop.demo.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hengyushop.movie.adapter.MovieHomeAdapter;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieHomeActivity extends Activity {

	private ListView list_home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_home);

		list_home = (ListView) findViewById(R.id.list_movie_home);
		list_home.setCacheColorHint(0);
		listData();

		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						MovieDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	private void listData() {
		String[] type = new String[] { "如何注册", "如何充值", "如何提现", "网购安全", "关于我们" };
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> lines = null;
		for (int i = 0; i < type.length; i++) {
			lines = new HashMap<String, String>();
			lines.put("text", type[i]);
			list.add(lines);
		}
		MovieHomeAdapter homeAdapter = new MovieHomeAdapter(list,
				MovieHomeActivity.this);
		list_home.setAdapter(homeAdapter);
	}

}
