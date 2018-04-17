package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hengyushop.demo.at.BaseActivity;

public class ComboMainActivity extends BaseActivity {
	private LinearLayout item0, item1, item2, item3, item4, item5, item6,
			item7;
	private ImageView demo;

	private void init() {
		demo = (ImageView) findViewById(R.id.demo);
		item0 = (LinearLayout) findViewById(R.id.main_item0);
		item1 = (LinearLayout) findViewById(R.id.main_item1);
		item2 = (LinearLayout) findViewById(R.id.main_item2);
		item3 = (LinearLayout) findViewById(R.id.main_item3);
		item4 = (LinearLayout) findViewById(R.id.main_item4);
		item5 = (LinearLayout) findViewById(R.id.main_item5);
		item6 = (LinearLayout) findViewById(R.id.main_item6);
		item7 = (LinearLayout) findViewById(R.id.main_item7);
		item0.setOnClickListener(listener);
		item1.setOnClickListener(listener);
		item2.setOnClickListener(listener);
		item3.setOnClickListener(listener);
		item4.setOnClickListener(listener);
		item5.setOnClickListener(listener);
		item6.setOnClickListener(listener);
		item7.setOnClickListener(listener);
	}

	/**
	 * 点击事件
	 */
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
				case R.id.main_item0:
					Intent intent = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					startActivity(intent);
					break;
				case R.id.main_item1:
					Intent intent1 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent1.putExtra("tag", "美食");
					startActivity(intent1);
					break;
				case R.id.main_item2:
					Intent intent2 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent2.putExtra("tag", "电影");
					startActivity(intent2);
					break;
				case R.id.main_item3:
					Intent intent3 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent3.putExtra("tag", "美食");
					startActivity(intent3);
					break;
				case R.id.main_item4:
					Intent intent4 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent4.putExtra("tag", "酒店");
					startActivity(intent4);
					break;
				case R.id.main_item5:
					Intent intent5 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent5.putExtra("tag", "KTV");
					startActivity(intent5);
					break;
				case R.id.main_item6:
					Intent intent6 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent6.putExtra("tag", "丽人");
					startActivity(intent6);
					break;
				case R.id.main_item7:
					Intent intent7 = new Intent(ComboMainActivity.this,
							ComboListActivity.class);
					intent7.putExtra("tag", "景点门票");
					startActivity(intent7);
					break;
				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		init();
		imageLoader.displayImage("drawable://" + R.drawable.demo6, demo);
	}
}
