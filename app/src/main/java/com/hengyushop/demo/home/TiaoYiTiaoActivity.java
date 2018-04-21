package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.XsgyListData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TiaoYiTiaoActivity extends Activity {
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tvStart, tv_guize;
	private TextView tv5;
	private TextView tv6;
	private TextView tv7;
	private TextView tv8;
	private TextView tvNotice;
	private ArrayList<XsgyListData> list;
	private List<TextView> views = new LinkedList<TextView>();// 所有的视图
	private int timeC = 100;// 变色时间间隔
	private int lightPosition = 0;// 当前亮灯位置,从0开始
	private int runCount = 10;// 需要转多少圈
	private int lunckyPosition = 4;// 中奖的幸运位置,从0开始
	String id, drawn, login_sign, ysj_point;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tiaoyitiao);
		init();
		userloginqm();
		getjiangxiangxq();
	}

	private void init() {
		try {
			LinearLayout ll_buju_tp = (LinearLayout) findViewById(R.id.ll_buju_tp);
			ll_buju_tp.setBackgroundResource(R.drawable.tyt_banren);
			LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
			ll_buju.setBackgroundResource(R.drawable.zp_bj);
			LinearLayout ll_buju_list = (LinearLayout) findViewById(R.id.ll_buju_list);
			ll_buju_list.setBackgroundResource(R.drawable.zhongjiang);

			tv1 = (TextView) findViewById(R.id.tv1);
			tv2 = (TextView) findViewById(R.id.tv2);
			tv3 = (TextView) findViewById(R.id.tv3);
			tv4 = (TextView) findViewById(R.id.tv4);
			tv5 = (TextView) findViewById(R.id.tv5);
			tv6 = (TextView) findViewById(R.id.tv6);
			tv7 = (TextView) findViewById(R.id.tv7);
			tv8 = (TextView) findViewById(R.id.tv8);
			tvStart = (TextView) findViewById(R.id.tvStart);
			tv_guize = (TextView) findViewById(R.id.tv_guize);
			tvNotice = (TextView) findViewById(R.id.tv_notice);
			views.add(tv1);
			views.add(tv2);
			views.add(tv3);
			views.add(tv4);
			views.add(tv5);
			views.add(tv6);
			views.add(tv7);
			views.add(tv8);
			tv1.setBackgroundResource(R.drawable.tyt_1);
			tv2.setBackgroundResource(R.drawable.tyt_2);
			tv3.setBackgroundResource(R.drawable.tyt_3);
			tv4.setBackgroundResource(R.drawable.tyt_4);
			tv5.setBackgroundResource(R.drawable.tyt_5);
			tv6.setBackgroundResource(R.drawable.tyt_6);
			tv7.setBackgroundResource(R.drawable.tyt_7);
			tv8.setBackgroundResource(R.drawable.tyt_8);
			tvStart.setBackgroundResource(R.drawable.ljcj);
			try {
				tvStart.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getjiangxiang(login_sign);
						// //
						// tvStart.setBackgroundResource(R.drawable.ic_await_award);
						// tvStart.getBackground().setAlpha(100);
						// // tvStart.setBackgroundResource(R.drawable.ljcj);
						// tvStart.setClickable(false);
						// tvStart.setEnabled(false);
						// tvNotice.setText("");
						// runCount = 10;
						// timeC = 100;
						// views.get(lunckyPosition).setBackgroundColor(Color.TRANSPARENT);
						// lunckyPosition = randomNum(0,7);
						// System.out.println("lunckyPosition==============11111111111111================"+lunckyPosition);
						// new TimeCount(timeC*9,timeC).start();

					}
				});

				ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
				iv_fanhui.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 生成随机数
	 *
	 * @param minNum
	 * @param maxNum
	 * @return
	 */
	private int randomNum(int minNum, int maxNum) {
		int max = maxNum;
		int min = minNum;
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			lightPosition = 0;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			Log.i(">>>", "1-----------------" + runCount);
			Log.i(">>>", "2-----------------" + lightPosition);
			// 如果是最后一次滚动
			if (runCount > 0) {
				if (lightPosition > 0) {
					System.out.println("1==============================");
					views.get(lightPosition - 1).setBackgroundColor(
							Color.TRANSPARENT);
					// views.get(lightPosition).setBackgroundResource(R.drawable.ic_await_award);
				}
				System.out
						.println("lightPosition=============================="
								+ lightPosition);
				// if (lightPosition<8){
				// System.out.println("2==============================");
				// // views.get(lightPosition).setBackgroundColor(Color.RED);
				// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_1);
				// }
				if (lightPosition == 0) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_11);
				}
				if (lightPosition == 1) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_22);
				}
				if (lightPosition == 2) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_33);
				}
				if (lightPosition == 3) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_44);
				}
				if (lightPosition == 4) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_55);
				}
				if (lightPosition == 5) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_66);
				}
				if (lightPosition == 6) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_77);
				}
				if (lightPosition == 7) {
					tv1.setBackgroundResource(R.drawable.tyt_1);
					tv2.setBackgroundResource(R.drawable.tyt_2);
					tv3.setBackgroundResource(R.drawable.tyt_3);
					tv4.setBackgroundResource(R.drawable.tyt_4);
					tv5.setBackgroundResource(R.drawable.tyt_5);
					tv6.setBackgroundResource(R.drawable.tyt_6);
					tv7.setBackgroundResource(R.drawable.tyt_7);
					tv8.setBackgroundResource(R.drawable.tyt_8);
					views.get(lightPosition).setBackgroundResource(
							R.drawable.tyt_88);
				}

			} else if (runCount == 0) {
				System.out
						.println("lightPosition=============================="
								+ lightPosition);
				System.out
						.println("lunckyPosition=============================="
								+ lunckyPosition);
				if (lightPosition <= lunckyPosition) {
					if (lightPosition > 0) {
						System.out.println("3==============================");
						// views.get(lightPosition-1).setBackgroundColor(Color.TRANSPARENT);
						// if (lightPosition == 0){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_11);
						// }
						// if (lightPosition == 1){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_22);
						// }
						// if (lightPosition == 3){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_33);
						// }
						// if (lightPosition == 4){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_44);
						// }
						// if (lightPosition == 5){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_55);
						// }
						// if (lightPosition == 6){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_66);
						// }
						// if (lightPosition == 7){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_77);
						// }
						// if (lightPosition == 8){
						// views.get(lightPosition).setBackgroundResource(R.drawable.tyt_88);
						// }
						// views.get(lightPosition).setBackgroundResource(R.drawable.ic_default_award);
					}
					// if (lightPosition<8){
					// System.out.println("4==============================");
					// views.get(lightPosition).setBackgroundColor(Color.RED);
					// //
					// views.get(lightPosition).setBackgroundResource(R.drawable.ic_default_award);
					// }
					if (lightPosition == 0) {
						System.out.println("4==============================");
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_11);
					}
					if (lightPosition == 1) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_22);
					}
					if (lightPosition == 2) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_33);
					}
					if (lightPosition == 3) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_44);
					}
					if (lightPosition == 4) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_55);
					}
					if (lightPosition == 5) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_66);
					}
					if (lightPosition == 6) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_77);
					}
					if (lightPosition == 7) {
						tv1.setBackgroundResource(R.drawable.tyt_1);
						tv2.setBackgroundResource(R.drawable.tyt_2);
						tv3.setBackgroundResource(R.drawable.tyt_3);
						tv4.setBackgroundResource(R.drawable.tyt_4);
						tv5.setBackgroundResource(R.drawable.tyt_5);
						tv6.setBackgroundResource(R.drawable.tyt_6);
						tv7.setBackgroundResource(R.drawable.tyt_7);
						tv8.setBackgroundResource(R.drawable.tyt_8);
						views.get(lightPosition).setBackgroundResource(
								R.drawable.tyt_88);
					}
				}
			}

			lightPosition++;
		}

		@Override
		public void onFinish() {
			Log.i(">>>", "onFinish=================" + runCount);
			// 如果不是最后一圈，需要还原最后一块的颜色
			TextView tvLast = views.get(7);
			if (runCount != 0) {
				tvLast.setBackgroundColor(Color.TRANSPARENT);
				// tvLast.setBackgroundColor(R.drawable.ic_launcher);
				// 最后几转速度变慢
				if (runCount < 3)
					timeC += 200;
				new TimeCount(timeC * 9, timeC).start();
				runCount--;
			}
			// 如果是最后一圈且计时也已经结束
			if (runCount == 0 && lightPosition == 8) {
				tvStart.setBackgroundResource(R.drawable.ljcj);
				tvStart.getBackground().setAlpha(200);
				System.out
						.println("已经结束----------------------------------------------------");
				tvStart.setClickable(true);
				tvStart.setEnabled(true);
				// tvNotice.setText("恭喜你抽中: "+views.get(lunckyPosition).getText().toString());
				// tvNotice.setText(list.get(lunckyPosition).drawn);
				// Toast.makeText(TiaoYiTiaoActivity.this,
				// list.get(lunckyPosition).drawn, 200).show();

				// Toast.makeText(TiaoYiTiaoActivity.this, "恭喜你抽中500万",
				// 200).show();
				// if (lunckyPosition!=views.size())
				// tvLast.setBackgroundColor(Color.TRANSPARENT);

				Intent intent = new Intent(TiaoYiTiaoActivity.this,
						ZyZTiShiActivity.class);
				intent.putExtra("drawn", drawn);//
				intent.putExtra("id", id);//
				intent.putExtra("quxiao", "取消");
				startActivity(intent);
			}

		}
	}

	/**
	 * 输出抽奖幸奖项-转一转
	 */
	private void getjiangxiang(String login_sign) {
		SharedPreferences spPreferences = getSharedPreferences("longuserset",
				MODE_PRIVATE);
		String user_id = spPreferences.getString("user_id", "");
		String user_name = spPreferences.getString("user", "");
		// String login_sign = spPreferences.getString("login_sign", "");
		String strUrlone = RealmName.REALM_NAME_LL
				+ "/get_article_activity_award?user_id=" + user_id
				+ "&user_name=" + user_name + "&article_id=7826&sign="
				+ login_sign + "";
		System.out.println("======输出抽奖幸奖项=============" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======输出抽奖幸奖项=============" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					if (status.equals("y")) {
						// Toast.makeText(ZhuanYiZhuanActivity.this, info,
						// 200).show();
						JSONObject obct = object.getJSONObject("data");
						id = obct.getString("id");
						// String title = obct.getString("title");
						// String drawn = obct.getString("drawn");

						tvStart.getBackground().setAlpha(100);
						tvStart.setClickable(false);
						tvStart.setEnabled(false);
						tvNotice.setText("");
						runCount = 10;
						timeC = 100;
						views.get(lunckyPosition).setBackgroundColor(
								Color.TRANSPARENT);
						lunckyPosition = randomNum(0, 7);
						System.out
								.println("lunckyPosition==============11111111111111================"
										+ lunckyPosition);
						new TimeCount(timeC * 9, timeC).start();

						System.out
								.println("开始-----------------------------------");

						System.out
								.println("id-----------------------------------"
										+ id);
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).id.equals(id)) {
								drawn = list.get(i).drawn;
							}
						}
						System.out
								.println("drawn-----------------------------------"
										+ drawn);
					} else {
						Toast.makeText(TiaoYiTiaoActivity.this, info, 200)
								.show();
					}

					System.out.println("======输出抽奖幸奖项=======id======" + id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				System.out.println("======访问接口失败=============" + arg1);
				// Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败",
				// 200).show();
			}
		}, TiaoYiTiaoActivity.this);

	}

	/**
	 * 输出抽奖详情
	 */
	private void getjiangxiangxq() {
		list = new ArrayList<XsgyListData>();
		// String strUrlone = RealmName.REALM_NAME_LL +
		// "/get_lottery_model?lottery_id=17";
		String strUrlone = RealmName.REALM_NAME_LL
				+ "/get_article_model?id=7826";
		// System.out.println("======输出抽奖详情============="+strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======输出抽奖详情=============" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					if (status.equals("y")) {
						// Toast.makeText(ZhuanYiZhuanActivity.this, info,
						// 200).show();
						JSONObject obct = object.getJSONObject("data");
						org.json.JSONArray jsonArray = obct
								.getJSONArray("activity_award");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jobject = jsonArray.getJSONObject(i);
							XsgyListData spList = new XsgyListData();
							spList.id = jobject.getString("id");
							spList.title = jobject.getString("title");
							spList.drawn = jobject.getString("drawn");
							list.add(spList);
						}
					} else {
						// Toast.makeText(TiaoYiTiaoActivity.this, info,
						// 200).show();
					}
					System.out.println("======list.size()============="
							+ list.size());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				System.out.println("======访问接口失败===============");
				// Toast.makeText(TiaoYiTiaoActivity.this, "访问接口失败",
				// 200).show();
			}
		}, TiaoYiTiaoActivity.this);

	}

	/**
	 * 获取登录签名
	 *
	 * @param order_no
	 */
	private void userloginqm() {
		SharedPreferences spPreferences = getSharedPreferences("longuserset",
				MODE_PRIVATE);
		String user_name = spPreferences.getString("user", "");
		String strUrlone = RealmName.REALM_NAME_LL
				+ "/get_user_model?username=" + user_name + "";
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					// System.out.println("登录签名==================="+arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						login_sign = obj.getString("login_sign");
						ysj_point = obj.getString("point");
					} else {
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, null);
	}

}