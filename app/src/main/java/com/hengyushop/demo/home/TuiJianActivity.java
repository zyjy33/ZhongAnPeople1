package com.hengyushop.demo.home;

import android.os.Bundle;
import android.view.Window;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

/**
 * 推荐
 *
 * @author Administrator
 *
 */
public class TuiJianActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuijian_yunshangju);

	}

}
