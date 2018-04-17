package com.hengyushop.demo.home;

import com.hengyushop.demo.airplane.AirPlaneBargainActivity;
import com.hengyushop.demo.airplane.AirPlaneOnLineActivity;
import com.hengyushop.demo.airplane.AirPlaneSelectActivity;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * È«²¿
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
