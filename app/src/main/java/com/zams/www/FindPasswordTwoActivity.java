package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.hengyushop.demo.at.BaseActivity;

public class FindPasswordTwoActivity extends BaseActivity implements
		OnClickListener {

	private Button btn_find_pwd_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏

		setContentView(R.layout.find_password2);
		initdata();

	}

	private void initdata() {
		btn_find_pwd_next = (Button) findViewById(R.id.btn_find_pwd_next);
		btn_find_pwd_next.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_find_pwd_next:
				Intent intent = new Intent(FindPasswordTwoActivity.this,
						FindPasswordThreeActivity.class);
				startActivity(intent);
				break;

			default:
				break;
		}
	}

}
