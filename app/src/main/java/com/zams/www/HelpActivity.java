package com.zams.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends BaseActivity {

	private ListView list_help;
	private String list_type;
	private List<Map<String, String>> list;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hengyu_help);
		list_help = (ListView) findViewById(R.id.list_help);
		list_help.setCacheColorHint(0);
		getData();
		list_help.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				list_type = list.get(arg2).get("txt");
				Toast.makeText(getApplicationContext(), list_type, 200).show();
				if (list_type.equals("意见反馈")) {
					Intent intent = new Intent(HelpActivity.this,
							HelpSuggestionActivity.class);
					startActivity(intent);
				} else {
					Dialog();
				}
			}
		});
	}

	private void getData() {
		String[] strType = new String[] { "帮助中心&关于我们", "版权申明与免责条款", "意见反馈" };
		list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < strType.length; i++) {
			Map<String, String> lines = new HashMap<String, String>();
			lines.put("txt", strType[i]);
			list.add(lines);
		}

		SimpleAdapter sa = new SimpleAdapter(HelpActivity.this, list,
				R.layout.listitem_help, new String[] { "txt" },
				new int[] { R.id.tv_help_type });
		list_help.setAdapter(sa);
	}

	private void Dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(HelpActivity.this);
		View loginView = null;
		loginView = LayoutInflater.from(this).inflate(R.layout.help_dialog,
				null);
		builder.setView(loginView);

		tv = (TextView) loginView.findViewById(R.id.tv_help_content);
		if (list_type.equals("帮助中心&关于我们")) {
			tv.setText("帮助中心&关于我们");
		} else if (list_type.equals("版权申明与免责条款")) {
			tv.setText("版权申明与免责条款");
		}
		builder.create().show();
	}
}
