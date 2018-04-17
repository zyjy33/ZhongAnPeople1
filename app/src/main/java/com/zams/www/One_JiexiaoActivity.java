package com.zams.www;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class One_JiexiaoActivity extends BaseActivity {
	private LinearLayout old_one;
	private ImageView item0;
	private TextView itme1, itme2, itme3, itme4, itme5;
	private Button itme6;
	private MyPosterView market_information_images;
	private TextView tv_ware_market_money, market_information_title, item7,
			item8, item9;
	private LinearLayout img_layout;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_new_jiexiao);
		item10 = (ProgressBar) findViewById(R.id.item10);
		old_one = (LinearLayout) findViewById(R.id.old_one);
		itme1 = (TextView) findViewById(R.id.item1);
		itme2 = (TextView) findViewById(R.id.item2);
		itme3 = (TextView) findViewById(R.id.item3);
		itme4 = (TextView) findViewById(R.id.item4);
		itme5 = (TextView) findViewById(R.id.item5);
		itme6 = (Button) findViewById(R.id.item6);
		img_layout = (LinearLayout) findViewById(R.id.img_layout);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;// 宽度height = dm.heightPixels ;
		market_information_images = (MyPosterView) findViewById(R.id.market_information_images);
		market_information_title = (TextView) findViewById(R.id.market_information_title);
		tv_ware_market_money = (TextView) findViewById(R.id.tv_ware_market_money);
		item7 = (TextView) findViewById(R.id.item7);
		item8 = (TextView) findViewById(R.id.item8);
		item9 = (TextView) findViewById(R.id.item9);
		// 计算期数
		old_one.removeAllViews();
		int old_Number = Integer.parseInt(getIntent().getStringExtra(
				"LuckDrawBatchOrderNumber"));
		for (int i = old_Number; i > 0; i--) {
			TextView textView = new TextView(getApplicationContext());
			textView.setText("第" + i + "期");
			textView.setPadding(7, 5, 7, 5);
			textView.setTextSize(17);
			old_one.addView(textView);
			final int idex = i;
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onloadJie(String.valueOf(idex));
				}
			});
		}
		img_layout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels,
				widthPixels));
		itme6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(One_JiexiaoActivity.this,
						OneResultActivity.class);
				intent.putExtra("id", getIntent().getStringExtra("id"));
				intent.putExtra("idex", getIntent().getStringExtra("idex"));
				startActivity(intent);
			}
		});
		onloadJie(getIntent().getStringExtra("idex"));
		onloadInfo();
	};

	private String AllJuGouCount;
	private String ActualLuckNumber;
	private String HengYuCode;
	private String username;
	private String LuckDrawBeginTime;
	private String AnnouncedTime;
	private Object proFaceImg;
	private Object proInverseImg;
	private Object proDoDetailImg;
	private Object proDesignImg;
	private Object proSupplementImg;
	private String marketPrice;
	private String proName;
	private String NeedGameUserNum;
	private String HasJoinedNum;
	private ProgressBar item10;
	private String LuckDrawBatchOrderNumber;

	private void onloadJie(String idex) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetLuckYiYuanJuGouAnnounceRecords");
		params.put("yth", "");
		params.put("productItemId", getIntent().getStringExtra("id"));
		params.put("luckDrawBatchOrderNumber", idex);
		/*
		 * params.put("act", "GetLuckReleaseRecords"); params.put("yth", "");
		 * params.put("productItemId", getIntent().getStringExtra("id"));
		 * params.put("luckDrawBatchOrderNumber", "1");
		 * //mi/getdata.ashx?act=GetLuckYiYuanJuGouAnnounceRecords
		 * &yth=test或为空&ProductItemId=1
		 */
		// mi/getdata.ashx?act=GetLuckReleaseRecords&yth=test或为空&ProductItemId=1&LuckDrawBatchOrderNumber=已结束的抽奖
		// mi/getdata.ashx?act=GetLuckYiYuanJuGouAnnounceRecords&yth=test或为空&ProductItemId=1
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							if (status.equals("1")) {
								JSONArray array = jsonObject
										.getJSONArray("items");
								int len = array.length();
								for (int i = 0; i < len; i++) {
									JSONObject oj = array.getJSONObject(i);
									if (oj.getString("LuckDrawBatchOrderNumber")
											.equals(getIntent().getStringExtra(
													"idex"))) {
										AllJuGouCount = oj
												.getString("AllJuGouCount");
										AnnouncedTime = oj
												.getString("AnnouncedTime");
										ActualLuckNumber = oj
												.getString("ActualLuckNumber");
										HengYuCode = oj.getString("HengYuCode");
										username = oj.getString("username");
										LuckDrawBeginTime = oj
												.getString("LuckDrawBeginTime");
									}
								}
								Message msg = new Message();
								msg.what = 1;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void onloadInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "OneProductItemInfo");
		params.put("yth", "test");
		params.put("key", "test");
		params.put("productItemType", "6");
		params.put("productItemId", getIntent().getStringExtra("id"));
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {

					public void onSuccess(int arg0, String arg1) {
						parse(arg1);

					};
				});
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				proName = object.getString("proName");
				marketPrice = object.getString("marketPrice");
				proFaceImg = object.getString("proFaceImg");
				System.out.println("图片地址:" + proFaceImg);
				proInverseImg = object.getString("proInverseImg");
				proDoDetailImg = object.getString("proDoDetailImg");
				proDesignImg = object.getString("proDesignImg");
				proSupplementImg = object.getString("proSupplementImg");
				NeedGameUserNum = object.getString("NeedGameUserNum");
				HasJoinedNum = object.getString("HasJoinedNum");
				LuckDrawBatchOrderNumber = object
						.getString("LuckDrawBatchOrderNumber");
			}

			handler.sendEmptyMessage(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<String> getData() {
		ArrayList<String> list = new ArrayList<String>();
		if (!"".equals(proFaceImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proFaceImg);
		}
		if (!"".equals(proInverseImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proInverseImg);
		}
		if (!"".equals(proDoDetailImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proDoDetailImg);
		}
		if (!"".equals(proDesignImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proDesignImg);
		}
		if (!"".equals(proSupplementImg)) {
			list.add(RealmName.REALM_NAME + "/admin/" + proSupplementImg);
		}
		return list;
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					itme1.setText(HengYuCode);
					itme2.setText(AllJuGouCount);
					itme3.setText(AnnouncedTime);
					itme4.setText(LuckDrawBeginTime);
					itme5.setText(ActualLuckNumber);
					break;
				case 2:
					market_information_title.setText(proName + "");
					market_information_images.setData(getData(), imageLoader);
					item7.setText(HasJoinedNum);
					item8.setText(NeedGameUserNum);
					item10.setProgress((int) (Double.parseDouble(HasJoinedNum)
							/ Double.parseDouble(NeedGameUserNum) * 100));
					item9.setText(""
							+ (Integer.parseInt(NeedGameUserNum) - Integer
							.parseInt(HasJoinedNum)));
					tv_ware_market_money.setText("￥" + marketPrice);

					break;
				default:
					break;
			}
		};
	};
}
