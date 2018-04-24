package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class ManagerOrderActivity extends BaseActivity {
	private Button btn1, btn2, btn3, btn4;

	private void init() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_order_layout);
		init();
		btn1.setOnClickListener(clickListener);
		btn3.setOnClickListener(clickListener);
		btn2.setOnClickListener(clickListener);
		btn4.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {

			switch (view.getId()) {
			case R.id.btn1:
				int index0 = 0;
				Intent intent5 = new Intent(ManagerOrderActivity.this,
						OrderInfromationActivity.class);
				intent5.putExtra("num", index0);
				startActivity(intent5);
				break;
			case R.id.btn2:
				Intent intent2 = new Intent(ManagerOrderActivity.this,
						MobileManagerActivity.class);
				startActivity(intent2);

				break;
			case R.id.btn3:
				Intent intent3 = new Intent(ManagerOrderActivity.this,
						ManagerTrainActivity.class);
				startActivity(intent3);
				break;
			case R.id.btn4:
				Intent intent4 = new Intent(ManagerOrderActivity.this,
						AirManagerActivity.class);
				startActivity(intent4);
				break;

			default:
				break;
			}
		}
	};
}
