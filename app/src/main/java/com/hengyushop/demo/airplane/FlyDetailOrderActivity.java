package com.hengyushop.demo.airplane;

import java.util.ArrayList;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.CardItem;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.train.TrainDetailActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;
import com.zams.www.PayActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class FlyDetailOrderActivity extends BaseActivity {
	private Button fly_order_send;
	private ListView fly_order_list;
	private TextView fly_order1, fly_order2, fly_order3, fly_order4,
			fly_order5, fly_order6;
	private FlyOrderItemAdapter adapter;
	private TextView fly_order_check;
	private EditText pass;

	private void init(FlyOrderBean bean) {
		fly_order1 = (TextView) findViewById(R.id.fly_order1);
		fly_order2 = (TextView) findViewById(R.id.fly_order2);
		fly_order3 = (TextView) findViewById(R.id.fly_order3);
		fly_order4 = (TextView) findViewById(R.id.fly_order4);
		fly_order5 = (TextView) findViewById(R.id.fly_order5);
		fly_order6 = (TextView) findViewById(R.id.fly_order6);
		fly_order1
				.setText(getString(R.string.fly_order1, bean.getOrderNumber()));
		fly_order2.setText(getString(R.string.fly_order2, bean.getAirCompany(),
				bean.getAirNumber()));
		fly_order3.setText(getString(R.string.fly_order3, bean.getDate(),
				bean.getStartTime(), bean.getEndTime()));
		fly_order4.setText(getString(R.string.fly_order4,
				bean.getStartAirPort(), bean.getStartAirNum(),
				bean.getEndAirPort(), bean.getEndAirNum()));

		fly_order5.setText(getString(R.string.fly_order5, bean.getUserName()));
		fly_order6
				.setText(getString(R.string.fly_order6, bean.getPhoneMobile()));
		fly_order_list = (ListView) findViewById(R.id.fly_order_list);
		adapter = new FlyOrderItemAdapter(getApplicationContext(),
				bean.getDetailPops());
		fly_order_list.setAdapter(adapter);
	}

	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private TextView fly_pop_1_p, fly_pop_2_p, fly_pop_3_p, fly_pop_4_p;
	DisplayMetrics dm = new DisplayMetrics();

	private void initPopupWindowL(View view) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.fly_category, null);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mPopupWindow = new PopupWindow(popView, view.getWidth(),
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);

		// createView(parentLayout, obj);
		/*
		 * mPopupWindow.setOnDismissListener(new OnDismissListener() {
		 *
		 * @Override public void onDismiss() { // TODO Auto-generated method
		 * stub
		 *
		 * } });
		 */
		fly_pop_1_p = (TextView) popView.findViewById(R.id.fly_pop_1_p);
		fly_pop_2_p = (TextView) popView.findViewById(R.id.fly_pop_2_p);
		fly_pop_3_p = (TextView) popView.findViewById(R.id.fly_pop_3_p);
		fly_pop_4_p = (TextView) popView.findViewById(R.id.fly_pop_4_p);
		fly_pop_1_p.setOnClickListener(clickListener);
		fly_pop_2_p.setOnClickListener(clickListener);
		fly_pop_3_p.setOnClickListener(clickListener);
		fly_pop_4_p.setOnClickListener(clickListener);
	}

	/**
	 * 创建支付的窗体
	 */
	private void initPopupWindowPay() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.pay_dialog, null);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mPopupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.blue));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		// createView(parentLayout, obj);
		/*
		 * mPopupWindow.setOnDismissListener(new OnDismissListener() {
		 *
		 * @Override public void onDismiss() { // TODO Auto-generated method
		 * stub
		 *
		 * } });
		 */
		TextView login_account = (TextView) popView
				.findViewById(R.id.login_account);
		login_account.setText(bean.getLoginSession());
		pass = (EditText) popView.findViewById(R.id.login_password);
		Button login_btn_login = (Button) popView
				.findViewById(R.id.login_btn_login);
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("logo", Activity.MODE_PRIVATE);
		final String bossUid = preferences.getString("bossUid", "");
		login_btn_login.setOnClickListener(new OnClickListener() {
			// mi/FlightTicket.ashx?
			/*
			 * imei=11&act=ConfirmPayPassTicket&bossUid=111&buyUserName=111&
			 * buyPwd=111&orderSerialNumber=111&paymentTypeId=3&PolicyOrderId=1
			 */
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				RequestParams params = new RequestParams();
				params.put("imei", Common.IMEI);
				params.put("PolicyOrderId", bean.getPolicyOrderId());
				params.put("paymentTypeId", "3");
				params.put("buyUserName", bean.getLoginSession());
				params.put("buyPwd", pass.getText().toString());
				params.put("bossUid", bossUid);
				params.put("orderSerialNumber", bean.getOrderNumber());
				String url = "/mi/FlightTicket.ashx?act=ConfirmPayPassTicket";
				AsyncHttp.post(RealmName.REALM_NAME + url, params,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								super.onSuccess(arg0, arg1);
								System.out.println(arg1);
							}
						}, getApplicationContext());
			}
		});
		Button login_close_button = (Button) popView
				.findViewById(R.id.login_close_button);
		login_close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
	}

	/**
	 * 弹出popWindow
	 *
	 * @param view
	 */
	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, location[0],
					view.getHeight() + location[1]);
		}
	}

	/**
	 * 支付弹出的对话框
	 *
	 * @param view
	 */
	private void showPopupWindowPay(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

	private FlyOrderBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fly_order_layout);
		fly_order_check = (TextView) findViewById(R.id.fly_order_check);
		fly_order_check.setOnClickListener(clickListener);
		fly_order_send = (Button) findViewById(R.id.fly_order_send);
		fly_order_send.setOnClickListener(clickListener);
		if (getIntent().hasExtra("fly_order")) {
			bean = (FlyOrderBean) getIntent().getSerializableExtra("fly_order");
			init(bean);
		}
	}

	/**
	 * 点击事件按钮
	 */
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {

				case R.id.fly_order_check:
					initPopupWindowL(fly_order_check);
					showPopupWindow(fly_order_check);
					break;
				case R.id.fly_pop_1_p:
					fly_order_check.setText(fly_pop_1_p.getText().toString());
					fly_order_check.setTag(fly_pop_1_p.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_2_p:
					fly_order_check.setText(fly_pop_2_p.getText().toString());
					fly_order_check.setTag(fly_pop_2_p.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_3_p:
					fly_order_check.setText(fly_pop_3_p.getText().toString());
					fly_order_check.setTag(fly_pop_3_p.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_4_p:
					fly_order_check.setText(fly_pop_4_p.getText().toString());
					fly_order_check.setTag(fly_pop_4_p.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;

				case R.id.fly_order_send:
					if (fly_order_check.getTag().toString().equals("3")) {
						if (mPopupWindow != null && mPopupWindow.isShowing()) {
							mPopupWindow.dismiss();
						}
						initPopupWindowPay();
						showPopupWindowPay(view);
					} else if (fly_order_check.getTag().toString().equals("2")) {
						String[] bankNames = bean.getBankNames();
						ArrayList<CardItem> banks = bean.getBanks();
						String trade_no = bean.getTrade_no();
						if (banks != null && banks.size() != 0) {
							// 表示是第二次支付
							System.out.println("写第二次支付");
							// initPopupWindow1();
							// showPopupWindow1(btn_OK);
							Intent intent = new Intent(FlyDetailOrderActivity.this,
									PayActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("tag", 1);
							bundle.putSerializable("trade_no", trade_no);
							bundle.putStringArray("bank_names", bankNames);
							bundle.putSerializable("bank_objs", banks);
							intent.putExtras(bundle);
							startActivity(intent);
						} else {
							// 表示首次支付
							Intent intent = new Intent(FlyDetailOrderActivity.this,
									PayActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("tag", 0);
							bundle.putSerializable("trade_no", trade_no);
							intent.putExtras(bundle);
							startActivity(intent);
							// initPopupWindow();
							// showPopupWindow(btn_OK);
						}
					} else {
						Toast.makeText(getApplicationContext(), "暂时不支持", 0).show();
					}
					break;

				default:
					break;
			}
		}
	};
}
// it is none of your business!