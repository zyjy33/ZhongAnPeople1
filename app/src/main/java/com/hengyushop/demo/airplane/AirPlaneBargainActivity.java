package com.hengyushop.demo.airplane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AirPlaneBargainActivity extends BaseActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplane_bargain_price);
		listView = (ListView) findViewById(R.id.lsit_bargain_price);
		listdata();
	}

	private void listdata() {
		String[] type = new String[] { "北京-上海", "北京-长沙", "北京-杭州", "长沙-上海",
				"深圳-上海", "海南-上海", "三亚-上海", "海南-北京", "北京-上海", "北京-长沙", "北京-杭州" };
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> lines = null;
		for (int i = 0; i < type.length; i++) {
			lines = new HashMap<String, String>();
			lines.put("text", type[i]);
			list.add(lines);
		}
		SimpleAdapter sa = new SimpleAdapter(AirPlaneBargainActivity.this,
				list, R.layout.airplane_listitem_bargain_price,
				new String[] { "text" }, new int[] { R.id.tv_name });
		listView.setAdapter(sa);
	}

}
