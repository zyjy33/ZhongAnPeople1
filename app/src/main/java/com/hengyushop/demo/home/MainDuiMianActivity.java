package com.hengyushop.demo.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.ctrip.openapi.java.utils.EncodingHandler;
import com.google.zxing.WriterException;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 面对面推广
 *
 * @author Administrator
 *
 */
public class MainDuiMianActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_fanhui, mImageView, mImageView1, mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private Button btn_data;
	private LinearLayout ll_erweima;
	private List<String> list;
	private ListView lv;
	private TextView tv_geshu;
	TestAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mianduimianll);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(MainDuiMianActivity.this);
		intren();

	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	// }
	public void intren() {
		try {
			RelativeLayout rl_tupian = (RelativeLayout) findViewById(R.id.rl_tupian);
			rl_tupian.setBackgroundResource(R.drawable.reweima_bjs);

			mImageView = (ImageView) findViewById(R.id.iv_qr_image);
			mImageView1 = (ImageView) findViewById(R.id.iv_qr_image1);
			mImageView2 = (ImageView) findViewById(R.id.iv_qr_image2);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			mEditText = (EditText) findViewById(R.id.et_haoma);
			btn_data = (Button) findViewById(R.id.btn_data);
			tv_geshu = (TextView) findViewById(R.id.tv_geshu);

			mImageView1.setBackgroundResource(R.drawable.rwm);
			mImageView2.setBackgroundResource(R.drawable.rwmhs);
			;

			iv_fanhui.setOnClickListener(this);
			btn_data.setOnClickListener(this);

			list = new ArrayList<String>();
			// list.add("我是小明");
			// list.add("我是小张");
			// list.add("我是小海");
			lv = (ListView) findViewById(R.id.myList);
			adapter = new TestAdapter(list, this);
			lv.setAdapter(adapter);
			setListViewHeightBasedOnChildren(lv);

			// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
			// adapter.notifyDataSetChanged();

			btn_data.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					String haoma = mEditText.getText().toString().trim();
					// http://183.62.138.31:1010/appinvite/109/13117711520_13117711521.html
					// https://www.pgyer.com/ZnX8

					if (TextUtils.isEmpty(haoma)) {
						Toast.makeText(MainDuiMianActivity.this, "请输入手机号", 200)
								.show();
					} else if (haoma.length() < 11) {
						Toast.makeText(MainDuiMianActivity.this, "手机号少于11位",
								200).show();
					} else {

						Bitmap _Bitmap;
						try {

							String id = spPreferences.getString("user_id", "");

							System.out.println("=============" + haoma);
							list.add(haoma); // 添加新数据 改变List集合
							adapter.list = list; // 将改变后的List集合赋给Adapter中的集合

							StringBuffer str = new StringBuffer();
							for (String s : list) {
								str.append(s + "_");
							}
							str.delete(str.lastIndexOf("_"), str.length());

							System.out.println("22---------------" + str);

							String str1 = "http://183.62.138.31:1011/appinvite/"
									+ id + "/" + str + ".html";

							System.out.println("str=============" + str1);

							mImageView1.setVisibility(View.GONE);
							mImageView2.setVisibility(View.GONE);
							mImageView.setVisibility(View.VISIBLE);
							_Bitmap = EncodingHandler.createQRCode(str1, 350);

							System.out.println("id111=============" + id);

							mImageView.setImageBitmap(_Bitmap);

							// zhou();
							try {

								if (list.size() > 0) {
									int gs = list.size();
									String geshu = String.valueOf(gs);
									tv_geshu.setText(geshu);
								}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
							mEditText.setText("");
							// setListViewHeightBasedOnChildren(lv);
							adapter.notifyDataSetChanged(); // 调用notifyDataSetChanged方法

						} catch (WriterException e) {
							// showToast("异常");
							Toast.makeText(MainDuiMianActivity.this, "异常", 200)
									.show();
						}
						// } else {
						// // showToast("不能为空");
						// Toast.makeText(MainDuiMianActivity.this, "请输入手机号",
						// 200).show();
					}

				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void zhou() {
		// TODO Auto-generated method stub
		try {

			TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
			if (list.size() > 0) {
				int gs = list.size();
				String geshu = String.valueOf(gs);
				tv_geshu.setText(geshu);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;

			default:
				break;
		}
	}

	public class TestAdapter extends BaseAdapter {
		public List<String> list;
		private Context context;
		private LayoutInflater mInflater;

		public TestAdapter(List<String> list, Context context) {
			super();
			this.list = list;
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = mInflater.inflate(R.layout.listitem, null);
				vh.tv = (TextView) convertView.findViewById(R.id.textView1);
				vh.tv2 = (ImageView) convertView.findViewById(R.id.textView2);

				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();

				vh.tv2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// list.clear();
						list.remove(position);
						System.out.println("=============" + list.size());
						try {
							int gs = list.size();
							String geshu = String.valueOf(gs);
							if (list.size() > 0) {
								tv_geshu.setText(geshu);
							} else {
								tv_geshu.setText(geshu);
								mImageView1.setVisibility(View.VISIBLE);
								mImageView2.setVisibility(View.VISIBLE);
								mImageView.setVisibility(View.GONE);
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						adapter.notifyDataSetChanged();

						// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
					}
				});

			}
			vh.tv.setText(list.get(position));
			return convertView;
		}

		public class ViewHolder {
			public TextView tv;
			public ImageView tv2;
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

}
