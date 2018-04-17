//package com.example.taobaohead.headview;
//
//import com.zams.www.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//public class TopIntent extends Activity {
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.jieshou);
//		TextView name = (TextView) findViewById(R.id.name);
//		TextView age = (TextView) findViewById(R.id.age);
//		if (getIntent() != null) {
//			String nameVal = getIntent().getStringExtra("name");
//			String ageVal = getIntent().getStringExtra("age");
//			name.setText(nameVal);
//			age.setText(ageVal);
//		} else {
//			Toast.makeText(this, "请检查传递", 0).show();
//		}
//	}
// }
