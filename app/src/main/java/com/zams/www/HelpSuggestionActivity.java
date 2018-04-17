package com.zams.www;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class HelpSuggestionActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestion_feedback);
	}

}
