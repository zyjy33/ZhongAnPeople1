package com.zams.www;

import android.os.Bundle;
import android.view.Window;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class VipItemActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vip_item_layout);
	}
}
