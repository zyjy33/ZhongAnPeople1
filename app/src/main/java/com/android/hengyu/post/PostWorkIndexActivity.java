package com.android.hengyu.post;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.hengyushop.dao.WorkDB;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class PostWorkIndexActivity extends BaseActivity {
	private GridView work_cate;
	private PostWorkIndexAdapter indexAdapter;
	private ArrayList<WorkIndexDo> woDos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_work_index);
		init();
	}

	private void init() {
		work_cate = (GridView) findViewById(R.id.work_cate);
		WorkDB db = new WorkDB(getApplicationContext());
		woDos = db.getWorks();
		indexAdapter = new PostWorkIndexAdapter(woDos, getApplicationContext());
		work_cate.setAdapter(indexAdapter);
		work_cate.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PostWorkIndexActivity.this,
						PostWorkListActivity.class);
				intent.putExtra("obj", woDos.get(arg2));
				startActivity(intent);

			}
		});

	}
}
