package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class AdvertSecond extends BaseActivity {
	private ImageView img;
	private TextView btn1;
	private Button btn2;
	private AdvertDao1 dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advert_second);
		img = (ImageView) findViewById(R.id.image);
		btn1 = (TextView) findViewById(R.id.tv1);
		btn2 = (Button) findViewById(R.id.infromation_share);
		dao = (AdvertDao1) getIntent().getSerializableExtra("dao");
		imageLoader.displayImage(dao.getDetail(), img);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AppManager.getAppManager().finishActivity();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent13 = new Intent(AdvertSecond.this,
						WareInformationActivity.class);
				intent13.putExtra("id", Integer.parseInt(dao.getId()));
				intent13.putExtra("seri", dao.getSeri());
				startActivity(intent13);
			}
		});

	}
}
