package com.hengyushop.demo.home;

import android.os.Bundle;
import android.view.Window;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

/**
 * 全部
 *
 * @author Administrator
 *
 */
public class QuanBuActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_pay_pop_leibie);

		// LinearLayout ll_quanbu = (LinearLayout) findViewById(R.id.ll_quanbu);
		// ll_quanbu.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// finish();
		// }
		// });

	}

}
