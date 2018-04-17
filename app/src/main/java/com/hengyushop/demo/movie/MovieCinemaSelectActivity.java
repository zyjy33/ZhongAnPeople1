package com.hengyushop.demo.movie;

import com.zams.www.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class MovieCinemaSelectActivity extends TabActivity {

	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_cinema_select);

		tabHost = this.getTabHost();
		createNearby();
		createCity();
	}

	private void createNearby() {
		TabHost.TabSpec localTabSpec = this.tabHost.newTabSpec("0");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("¸½½ü");
		Intent localIntent = new Intent(MovieCinemaSelectActivity.this,
				MovieCinemaSelectNearbyActivity.class);
		localTabSpec.setIndicator(localView).setContent(localIntent);
		this.tabHost.addTab(localTabSpec);
	}

	private void createCity() {
		TabHost.TabSpec localTabSpec1 = this.tabHost.newTabSpec("1");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator,
				null);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localTextView.setText("³ÇÇø");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(MovieCinemaSelectActivity.this,
				MovieCinemaSelectCityActivity.class);
		localTabSpec2.setContent(localIntent);
		this.tabHost.addTab(localTabSpec1);

	}

}
