package com.android.hengyu.post;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

public class PostCyDetail extends BaseActivity {
	private TextView view;
	private ImageView image;
	private TextView title;
	private TextView time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_stand_detail);
		PostCyDo sd = (PostCyDo) getIntent().getExtras().get("ob");
		view = (TextView) findViewById(R.id.text);
		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.title);
		time = (TextView) findViewById(R.id.time);

		title.setText(sd.getTitle());
		time.setText(sd.getTime());
		view.setText(sd.getInfo());
		System.out.println(RealmName.REALM_NAME + sd.getImg());
		imageLoader.displayImage(sd.getImg(), image);
	}
}
