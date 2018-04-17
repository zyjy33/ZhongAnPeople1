package com.android.hengyu.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.hengyushop.dao.WorkDB;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import java.util.ArrayList;

public class PostQiuActivity extends BaseActivity {

	private GridView work_cate;
	private PostWorkIndexAdapter indexAdapter;
	private ArrayList<WorkIndexDo> woDos;
	private Button post_jydt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_qiu_index);
		init();
	}

	private void init() {
		post_jydt_back = (Button) findViewById(R.id.post_jydt_back);
		post_jydt_back.setOnClickListener(clickListener);
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
				Intent intent = new Intent(PostQiuActivity.this,
						PostQiuListActivity.class);
				intent.putExtra("obj", woDos.get(arg2));
				System.out.println( woDos.get(arg2).getId());
				startActivity(intent);

			}
		});

	}
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

}
