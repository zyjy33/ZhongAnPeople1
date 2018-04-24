package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class VipQuanActivity extends BaseActivity {
	private RelativeLayout item1, item2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vip_quan_layout);
		item1 = (RelativeLayout) findViewById(R.id.item1);
		item2 = (RelativeLayout) findViewById(R.id.item2);
		item2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(VipQuanActivity.this,
						SoftUpdataActivity.class);
				startActivity(intent);
			}
		});
		item1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(VipQuanActivity.this,
						VipItemActivity.class);
				startActivity(intent);
			}
		});

	}
}
