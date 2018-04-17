package com.hengyushop.demo.airplane;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

import com.zams.www.R;
/**
 * 机票
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class AirPlaneHomeActivity extends TabActivity {

	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplane_home);
		tabHost = this.getTabHost();
		createBargainPrice();
		createSelect();
		createOnLine();
	}

	private void createSelect() {
		TabHost.TabSpec localTabSpec = this.tabHost.newTabSpec("1");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("机票查询");
		Intent localIntent = new Intent(AirPlaneHomeActivity.this,
				AirPlaneSelectActivity.class);
		localTabSpec.setIndicator(localView).setContent(localIntent);
		this.tabHost.addTab(localTabSpec);
	}

	private void createBargainPrice() {
		TabHost.TabSpec localTabSpec1 = this.tabHost.newTabSpec("0");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("特价机票");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(AirPlaneHomeActivity.this,
				AirPlaneBargainActivity.class);
		localTabSpec2.setContent(localIntent);
		this.tabHost.addTab(localTabSpec1);
	}

	private void createOnLine() {
		TabHost.TabSpec localTabSpec1 = this.tabHost.newTabSpec("2");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("在线值机");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(AirPlaneHomeActivity.this,
				AirPlaneOnLineActivity.class);
		localTabSpec2.setContent(localIntent);
		this.tabHost.addTab(localTabSpec1);

	}

}
