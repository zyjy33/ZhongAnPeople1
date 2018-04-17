package com.hengyushop.demo.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hengyushop.demo.airplane.AirPlaneBargainActivity;
import com.zams.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MovieCinemaSelectNearbyActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_cinema_select_nearby);

		listView = (ListView) findViewById(R.id.list_nearby);
		listdata();
	}

	private void listdata() {
		String[] type = new String[] { "�����ֵ�����ӰԺ", "������ͼӰԺ  �߿ڵ�", "���ں���ӰԺ",
				"������ͼӰԺ", "�������ڽ�����", "�����ֵ�����ӰԺ", "������ͼӰԺ  �߿ڵ�", "���ں���ӰԺ",
				"������ͼӰԺ", "�������ڽ�����" };
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
