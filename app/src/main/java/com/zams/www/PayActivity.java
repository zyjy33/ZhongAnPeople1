package com.zams.www;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.android.hengyu.ui.ArrayWheelAdapterll;
import com.android.hengyu.ui.WheelViewll;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umpay.quickpay.UmpPayInfoBean;
import com.umpay.quickpay.UmpayQuickPay;
import com.zams.www.R;

public class PayActivity extends BaseActivity {
	private LinearLayout layout2, layout1;
	// private Button pay1, pay2;
	private WareDao wareDao;
	private UserRegisterData registerData;
	private String yth;
	private String[] bankNames;
	private ArrayList<CardItem> bankObjs;
	private String dateValue = "";
	private String bankValue = "";
	private String birthDay = "";
	private LinearLayout xyk, czk;
	private Bundle bundle;
	private String indexType = "";

	private void init() {
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		xyk = (LinearLayout) findViewById(R.id.xyk);
		czk = (LinearLayout) findViewById(R.id.czk);
	}

	private void changeView(String indexType) {
		if (indexType.equals("CREDITCARD")) {
			xyk.setVisibility(View.VISIBLE);
			czk.setVisibility(View.GONE);
		} else if (indexType.equals("DEBITCARD")) {
			czk.setVisibility(View.VISIBLE);
			xyk.setVisibility(View.GONE);
		}
	}

	// private void initLayout0() {
	// pay1 = (Button) findViewById(R.id.pay1);
	// pay2 = (Button) findViewById(R.id.pay2);
	// }

	private void firstPay(final Bundle bundle) {
		// initLayout0();
		layout2.setVisibility(View.VISIBLE);
		layout1.setVisibility(View.INVISIBLE);
		Button pay1 = (Button) findViewById(R.id.pay1);
		Button pay2 = (Button) findViewById(R.id.pay2);
		pay1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				String trade_no = bundle.getString("trade_no");
				UmpPayInfoBean infoBean = new UmpPayInfoBean();
				boolean flag = UmpayQuickPay.requestPayWithBind(
						PayActivity.this, trade_no, yth, "0", "", infoBean,
						1000);
				if (flag)
					AppManager.getAppManager().finishActivity();
			}
		});
		pay2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String trade_no = bundle.getString("trade_no");
				UmpPayInfoBean infoBean = new UmpPayInfoBean();
				boolean flag = UmpayQuickPay.requestPayWithBind(
						PayActivity.this, trade_no, yth, "1", "", infoBean,
						1000);
				if (flag)
					AppManager.getAppManager().finishActivity();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_pay);
		wareDao = new WareDao(getApplicationContext());
		registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode().toString();

		bundle = getIntent().getExtras();
		init();
		int tag = bundle.getInt("tag");
		switch (tag) {
			case 0:
				firstPay(bundle);
				break;
			case 1:
				// 表示第二次支付
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.INVISIBLE);
				bankLayout = (LinearLayout) findViewById(R.id.change_bank);
				bank_name = (TextView) findViewById(R.id.bank_name);
				datePicker = (TextView) findViewById(R.id.select_date);
				an_ma = (EditText) findViewById(R.id.an_ma);
				yanma = (EditText) findViewById(R.id.yanma);
				pay_ok = (Button) findViewById(R.id.pay_ok);
				huode = (Button) findViewById(R.id.huode);
				select_date = (TextView) findViewById(R.id.select_bir);
				String trade_no = bundle.getString("trade_no");

				bankNames = bundle.getStringArray("bank_names");
				bankObjs = (ArrayList<CardItem>) bundle
						.getSerializable("bank_objs");
				indexType = bankObjs.get(0).getType();
				bankValue = bankObjs.get(0).getId();
				bank_name.setText(bankNames[0]);
				changeView(indexType);
				initLayout1(trade_no);
				break;
			default:
				break;
		}
	}

	private LinearLayout bankLayout;
	private TextView bank_name, datePicker, select_date;
	private EditText an_ma;
	private EditText yanma;
	private Button pay_ok;
	private Button huode;

	private void views(String arg1) {
		try {
			JSONObject jsonObject = new JSONObject(arg1);
			String status = jsonObject.getString("status");
			if (status.equals("1")) {
				AppManager.getAppManager().finishActivity();
				Toast.makeText(getApplicationContext(), "支付成功", 200).show();
			} else {
				Toast.makeText(getApplicationContext(), "支付失败", 200).show();
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	/*
	 * 取值范围： CREDITCARD（信用卡） DEBITCARD(借记卡)
	 */
	private void initLayout1(final String trade_no) {

		select_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showDialog(1);
			}
		});
		huode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RequestParams params = new RequestParams();
				params.put("yth", yth);
				params.put("UserSignedBankID", bankValue);
				params.put("trade_no", trade_no);
				AsyncHttp.post(RealmName.REALM_NAME
								+ "/mi/UmpHandler.ashx?act=Req_smsverify_shortcut",
						params, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(String arg0) {

								super.onSuccess(arg0);
								System.out.println(arg0);
							}
						}, getApplicationContext());
			}
		});
		pay_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (indexType.equals("CREDITCARD")) {

					RequestParams params = new RequestParams();
					params.put("yth", yth);
					params.put("trade_no", trade_no);
					params.put("UserSignedBankID", bankValue);
					params.put("valid_date", dateValue);
					System.out.println(dateValue);
					params.put("verify_code", yanma.getText().toString());
					params.put("cvv2", an_ma.getText().toString());
					params.put("birthday", "");
					AsyncHttp
							.post(RealmName.REALM_NAME
											+ "/mi/UmpHandler.ashx?act=Agreement_pay_confirm_shortcut",
									params, new AsyncHttpResponseHandler() {
										@Override
										public void onSuccess(int arg0,
															  String arg1) {
											super.onSuccess(arg0, arg1);
											System.out.println(arg1);
											views(arg1);
										}
									}, getApplicationContext());

				} else if (indexType.equals("DEBITCARD")) {

					AsyncHttp
							.get(RealmName.REALM_NAME
									+ "/mi/UmpHandler.ashx?act=Agreement_pay_confirm_shortcut?birthday="
									+ birthDay, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {
									super.onSuccess(arg0, arg1);
									System.out.println(arg1);
									views(arg1);
								}
							}, getApplicationContext());

				}

			}
		});
		datePicker.setOnClickListener(new OnClickListener() {

			@SuppressWarnings({ "static-access", "deprecation" })
			@Override
			public void onClick(View arg0) {
				showDialog(0);
				int SDKVersion = PayActivity.this.getSDKVersionNumber();// 获取系统版本
				System.out.println("SDKVersion = " + SDKVersion);
				DatePicker dp = findDatePicker((ViewGroup) mdialog.getWindow()
						.getDecorView());// 设置弹出年月日
				if (dp != null) {
					if (SDKVersion < 11) {
						((ViewGroup) dp.getChildAt(0)).getChildAt(1)
								.setVisibility(View.GONE);
					} else if (SDKVersion > 14) {
						((ViewGroup) ((ViewGroup) dp.getChildAt(0))
								.getChildAt(0)).getChildAt(2).setVisibility(
								View.GONE);
					}
				}

			}
		});
		bankLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				initPopupWindow();
				showPopupWindow(layout1);
			}
		});
	}

	private Dialog mdialog;
	private Calendar calendar = null;

	@Override
	protected Dialog onCreateDialog(int id) { // 对应上面的showDialog(0);//日期弹出框
		mdialog = null;
		switch (id) {
			case 0:
				calendar = Calendar.getInstance();
				mdialog = new DatePickerDialog(this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {
								datePicker.setText(year + "-" + (monthOfYear + 1));
								String monthValue = "";
								int month = (monthOfYear + 1);
								if (month < 10) {
									monthValue = "0" + month;
								}
								dateValue = String.valueOf(year).substring(2)
										+ monthValue;
							}
						}, calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH));
				break;
			case 1:
				calendar = Calendar.getInstance();
				mdialog = new DatePickerDialog(this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {
								select_date.setText(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
								String monthValue = "";
								int month = (monthOfYear + 1);
								if (month < 10) {
									monthValue = "0" + month;
								}
								String dayValue = "";
								int day = dayOfMonth;
								if (day < 10) {
									dayValue = "0" + day;
								}
								birthDay = String.valueOf(year).substring(2)
										+ monthValue + dayValue;

							}
						}, calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH));
				break;
		}
		return mdialog;
	}

	/**
	 * 从当前Dialog中查找DatePicker子控件
	 *
	 * @param group
	 * @return
	 */
	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

	/**
	 * 获取系统SDK版本
	 *
	 * @return
	 */
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}

	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private WheelViewll bank_item;

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.chose_payment, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.grey));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		bank_item = (WheelViewll) popView.findViewById(R.id.bank_item);
		Button cancle = (Button) popView.findViewById(R.id.cancle);
		Button sure = (Button) popView.findViewById(R.id.sure);

		ArrayWheelAdapterll<String> bankAdapter = new ArrayWheelAdapterll<String>(
				bankNames);
		bank_item.setAdapter(bankAdapter);

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dissPop();
			}
		});
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int index = bank_item.getCurrentItem();
				bankValue = bankObjs.get(index).getId();
				bank_name.setText(bankNames[index]);
				String indexType = bankObjs.get(index).getType();
				if (indexType.equals("-1")) {
					layout2.setVisibility(View.VISIBLE);
					layout1.setVisibility(View.INVISIBLE);
					firstPay(bundle);
				} else {
					changeView(indexType);
				}

				dissPop();
			}
		});
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		}
	}

	private void dissPop() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

}
