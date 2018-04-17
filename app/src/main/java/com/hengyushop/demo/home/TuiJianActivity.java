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
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * ÍÆ¼ö
 * 
 * @author Administrator
 * 
 */
public class TuiJianActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuijian_yunshangju);

	}

}
