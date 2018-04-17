package com.android.hengyu.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import java.util.ArrayList;

public class PostJYDT_Activity extends BaseActivity {
	/**
	 * 声明组件对象
	 */
	private ListView post_list;
	private Button post_jydt_back;
	private PostStandAdapter adapter;
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.post_jydt_back:
					AppManager.getAppManager().finishActivity();
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 组件创建
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		post_jydt_back = (Button) findViewById(R.id.post_jydt_back);
		post_jydt_back.setOnClickListener(clickListener);
		post_list = (ListView) findViewById(R.id.post_list);
		final ArrayList<PostStandDo> list = (ArrayList<PostStandDo>) getIntent()
				.getExtras().get("list");
		adapter = new PostStandAdapter(list, getApplicationContext());
		post_list.setAdapter(adapter);
		post_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PostJYDT_Activity.this,
						PostStandDetail.class);
				intent.putExtra("ob", list.get(arg2));
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_jydt_layout);
		init();
	}
}
