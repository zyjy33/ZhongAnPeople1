package com.hengyushop.demo.airplane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AirPlaneOnLineActivity extends BaseActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplane_on_line);

		listView = (ListView) findViewById(R.id.list_on_line);
		listdata();
	}

	private void listdata() {
		String[] type = new String[] { "南方航空在线值机", "海南航空在线值机" };
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> lines = null;
		for (int i = 0; i < type.length; i++) {
			lines = new HashMap<String, String>();
			lines.put("text", type[i]);
			list.add(lines);
		}
		SimpleAdapter sa = new SimpleAdapter(AirPlaneOnLineActivity.this, list,
				R.layout.airplane_listitem_on_line, new String[] { "text" },
				new int[] { R.id.tv_name });
		listView.setAdapter(sa);
	}

}
