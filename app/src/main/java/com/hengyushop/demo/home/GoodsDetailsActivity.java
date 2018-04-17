package com.hengyushop.demo.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

/**
 * 商品详情
 *
 * @author Administrator
 *
 */
public class GoodsDetailsActivity extends BaseActivity implements
		OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_ware_infromation);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
