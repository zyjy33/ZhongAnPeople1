package com.hengyushop.demo.movie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zams.www.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieCinemaSelectNearbyActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_cinema_select_nearby);

		listView = (ListView) findViewById(R.id.list_nearby);
		listdata();
	}

	private void listdata() {
		String[] type = new String[] { "华谊兄弟深圳影院", "深圳雅图影院  蛇口点", "深圳海岸影院",
				"深圳雅图影院", "金逸深圳建安店", "华谊兄弟深圳影院", "深圳雅图影院  蛇口点", "深圳海岸影院",
				"深圳雅图影院", "金逸深圳建安店" };
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> lines = null;
		for (int i = 0; i < type.length; i++) {
			lines = new HashMap<String, String>();
			lines.put("text", type[i]);
			list.add(lines);
		}
		SimpleAdapter sa = new SimpleAdapter(
				MovieCinemaSelectNearbyActivity.this, list,
				R.layout.movie_listitem_nearby, new String[] { "text" },
				new int[] { R.id.tv_area_name });
		listView.setAdapter(sa);
	}

}
