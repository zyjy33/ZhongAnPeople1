package com.hengyushop.demo.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zams.www.R;
import com.niceapp.lib.tagview.widget.Tag;
import com.niceapp.lib.tagview.widget.TagListView;
import com.niceapp.lib.tagview.widget.TagListView.OnTagClickListener;
import com.niceapp.lib.tagview.widget.TagView;

public class MerchantsLabelActivity extends Activity implements OnClickListener {

	private TagListView mTagListView, mTagListView1;
	private final List<Tag> mTags = new ArrayList<Tag>();
	private final List<Tag> mTags2 = new ArrayList<Tag>();
	private final List<String> list = new ArrayList<String>();
	private final String[] titles = { "汽车", "餐饮", "开网店", "摄影", "服装搭配", "手工制作",
			"商城", "减肥", "养老服务", "按摩", "炒股", "电子商务" };
	public static String str = "";
	int dark = 0xffffff;
	Button button;
	TextView tv_queren, tag_shanchu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_tag_activity);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		mTagListView = (TagListView) findViewById(R.id.tagview);
		mTagListView1 = (TagListView) findViewById(R.id.tagview1);
		// button = (Button) findViewById(R.id.button1);
		tv_queren = (TextView) findViewById(R.id.tv_queren);
		tag_shanchu = (TextView) findViewById(R.id.tag_shanchu);
		setUpData();
		mTagListView.setTags(mTags);
		final boolean re = false;

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		mTagListView.setOnTagClickListener(new OnTagClickListener() {

			@Override
			public void onTagClick(TagView tagView, Tag tag) {
				// if(tag.getOr()==true){
				// tag.setOr(false);
				// //
				// tagView.setBackgroundResource(R.drawable.tag_checked_normal);
				// Toast.makeText(getApplicationContext(),"您取消了"+tagView.getText().toString(),
				// 2000).show();
				// }else{

				if (mTags2.size() >= 3) {
					Toast.makeText(getApplicationContext(), "最多选三个", 2000)
							.show();
					// mTags2.clear();
				} else {
					tag_shanchu.setVisibility(View.VISIBLE);
					mTags2.add(tag);
					mTags.remove(tag);
					System.out.println("mTags2------00-----" + mTags2.size());

					tag.setOr(true);
					// Toast.makeText(getApplicationContext(),tagView.getText().toString()+"id"+tag.getId(),
					// 2000).show();
					// tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
					// tagView.setChecked(true);
					mTagListView.setTags(mTags);
					mTagListView1.setTags(mTags2);

				}
				// }
			}
		});

		mTagListView1.setOnTagClickListener(new OnTagClickListener() {

			@Override
			public void onTagClick(TagView tagView, Tag tag) {
				System.out.println("mTags2-----11-----" + mTags2.size());
				mTags2.remove(tag);
				mTags.add(tag);
				// tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
				mTagListView1.setTags(mTags2);
				mTagListView.setTags(mTags);
			}
		});

		tv_queren.setOnClickListener(new OnClickListener() {
			// tag的名字字符串
			// String str="";
			String strId = "";

			@Override
			public void onClick(View arg0) {
				if (mTags2.size() == 0) {
					Toast.makeText(getApplicationContext(), "请选择商家标签", 2000)
							.show();
				} else {
					for (int i = 0; i < mTags2.size(); i++) {
						Tag tag = mTags2.get(i);
						if (tag.getOr() == true) {
							// System.out.println("str.length-----------"+str.length());
							if (str.length() < 2) {
								str = tag.getTitle();
								strId = String.valueOf(tag.getId());
								list.add(str);
								// mTags2.add(tag);
								System.out.println("mTags2------11-----");
							} else {
								str = str + "," + tag.getTitle();
								strId = strId + ","
										+ String.valueOf(tag.getId());
								// list.add(str);
								// mTags2.add(tag);
							}
						}
					}
					System.out.println("mTags2-------22----" + mTags2.size());
					// Toast.makeText(getApplicationContext(),str+"---它们的id是"+strId,
					// 2000).show();
					// Toast.makeText(getApplicationContext(),strId,
					// 2000).show();

					// str="";
					// strId="";
					finish();
				}
			}
		});

	}

	private void setUpData() {
		for (int i = 0; i < 10; i++) {
			Tag tag = new Tag();
			tag.setId(i);
			tag.setChecked(true);
			tag.setTitle(titles[i]);
			mTags.add(tag);
		}
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(this, "点了", 2000).show();
	}
}
