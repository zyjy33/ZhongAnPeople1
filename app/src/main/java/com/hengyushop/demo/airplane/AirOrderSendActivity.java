package com.hengyushop.demo.airplane;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AirOrderSendActivity extends BaseActivity {
	private TextView fly_detiail_card_c, fly_detiail_card_l;
	private EditText fly_detiail_card_num, fly_detiail_card_name,
			fly_detiail_card_mob;
	private TextView fly_pop_1, fly_pop_2, fly_pop_3, fly_pop_4, fly_pop_5,
			fly_pop_6, fly_pop_2_p, fly_pop_1_p, fly_detail_cangname;
	// 乘机人的列表集合
	private ListView fly_detail_list;
	private Spinner advert_order_province, advert_order_city,
			advert_order_area;
	private ArrayList<String> province, city, area;
	private ArrayAdapter<String> provinceAdapter, cityAdapter, areaAdapter;
	private ArrayList<FlyDetailPop> detailPops = new ArrayList<FlyDetailPop>();
	private FlyDeatilItemAdapter ad;
	private RadioButton fly_detail_noneed, fly_detail_need;
	private RadioGroup fly_detail_isneed;
	private LinearLayout need;
	private Button fly_detail_send;
	private EditText fly_detail_name, fly_detail_bil;
	private Button fly_detail_add;
	private FlyResult flyResult;
	private int IsNeedVouchers = 0;
	private EditText fly_detail_p_name, fly_detail_p_mob, fly_detail_p_add;
	private RequestParams params = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.airorder_layout);
		if (getIntent().hasExtra("flyBean")) {
			flyResult = (FlyResult) getIntent().getExtras().getSerializable(
					"flyBean");
			init(flyResult);
		}
	}

	/**
	 * 构造票务信息
	 *
	 * @param params
	 */
	private void proceeList(RequestParams params) {
		String names = "";
		String types = "";
		String identityType = "";
		String identityNo = "";// 身份证
		for (int i = 0; i < detailPops.size(); i++) {
			if (i == detailPops.size() - 1) {
				types += detailPops.get(i).getTagL();
				identityType += detailPops.get(i).getTagC();
				identityNo += detailPops.get(i).getNum();
				names += detailPops.get(i).getName();
			} else {
				names += detailPops.get(i).getName() + ",";
				types += detailPops.get(i).getTagL() + ",";
				identityNo += detailPops.get(i).getNum() + ",";
				identityType += detailPops.get(i).getTagC() + ",";
			}

		}
		params.put("name", names);
		params.put("type", types);
		params.put("identityType", identityType);
		params.put("identityNo", identityNo);
	}

	private EditText login_account, login_password;

	private void initPopupWindowG(View view) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.login_dialog, null);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mPopupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.white));
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
		Button login_close_button = (Button) popView
				.findViewById(R.id.login_close_button);
		LinearLayout load1 = (LinearLayout) popView.findViewById(R.id.load1);
		RelativeLayout load2 = (RelativeLayout) popView
				.findViewById(R.id.load2);
		load2.setVisibility(View.VISIBLE);
		load1.setVisibility(View.GONE);
		login_account = (EditText) popView.findViewById(R.id.login_account);
		login_password = (EditText) popView.findViewById(R.id.login_password);
		Button login_btn_login = (Button) popView
				.findViewById(R.id.login_btn_login);
		login_close_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		login_btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				send(login_account.getText().toString(), login_password
						.getText().toString());
			}
		});

	}

	/**
	 * 发送消息
	 *
	 * @param userName
	 * @param password
	 */
	private void send(String userName, String password) {
		// 生成订单信息，发送到服务器
		final String linkMan = fly_detail_name.getText().toString();
		final String linkPhone = fly_detail_bil.getText().toString();
		params = new RequestParams();
		// Map<String, String> params = new HashMap<String, String>();
		// ---
		/*
		 * &bossUid=111 &buyUserName=111 &pwd=1
		 */
		WareDao dao = new WareDao(getApplicationContext());

		String bossUid = dao.getBossUid();
		// 待定
		if (bossUid.length() != 0 || detailPops.size() == 0) {
			params.put("bossUid", bossUid);
			params.put("buyUserName", userName);
			params.put("pwd", password);
			// -------
			params.put("linkPhone", linkPhone);
			params.put("linkMan", linkMan);
			params.put("imei", Common.IMEI);
			/*
			 * http://www.hengyushop.com/mi/FlightTicket.ashx?act=
			 * CreateOrderByPassengers http://www.hengyushop.com/mi/FlightTicket
			 * .ashx?imei=11&act =
			 * CreateOrderByPassengers&bossUid=111&buyUserName =jiajianjiao &
			 * pwd=0&linkMan=%E5%BC%A0%E5%B7%A5&linkPhone=11&parPrice=1& fuelTax
			 * =1&airportTax=1&flightNo=1&depCode=1&arrCode=1&seatClass =1
			 * &depDate=2009-10-10&depTime=0730&arrTime=0735&planeModel
			 * =1&name=%E5%BC%A0%E4%B8%89,%E6%9D%
			 * 8E%E5%9B%9B%E4%BA%BA&type=0,1&identityType=0,1&identityNo=4113811985555555551,
			 * 411381198555588888&IsNeedVouchers=1&consigneeUserName=1&
			 * consigneeProvinceCode =1&consigneeCityCode=1&consigneeAreaCode=1&
			 * consigneeAddressDetail=1&consigneeMobileTel=1
			 */
			params.put("SourceType", "5");
			params.put("parPrice", flyResult.getPrice());
			params.put("fuelTax", flyResult.getAudletFuelTax());
			params.put("airportTax", flyResult.getAudletAirportTax());
			params.put("flightNo", flyResult.getFlightNo());
			params.put("depCode", flyResult.getOrgCity());
			params.put("arrCode", flyResult.getDstCity());
			params.put("seatClass", flyResult.getSeatCode());
			params.put("depDate", flyResult.getDate());

			params.put("depTime", flyResult.getFirstTime().replace(":", ""));
			params.put("arrTime", flyResult.getEndTime().replace(":", ""));
			params.put("planeModel", flyResult.getPlaneType());
			params.put("Discount", flyResult.getDiscount());
			// 还缺少乘机人的数据构造
			proceeList(params);
			params.put("IsNeedVouchers", String.valueOf(IsNeedVouchers));
			if (IsNeedVouchers == 1) {
				params.put("consigneeUserName", fly_detail_p_name.getText()
						.toString());

				params.put("consigneeProvinceCode", new CityDB(
						getApplicationContext())
						.getName("select code from province where name='"
								+ province.get(advert_order_province
								.getSelectedItemPosition()) + "'"));
				params.put("consigneeCityCode", new CityDB(
						getApplicationContext())
						.getName("select code from city where name='"
								+ city.get(advert_order_city
								.getSelectedItemPosition()) + "'"));
				params.put("consigneeAreaCode", new CityDB(
						getApplicationContext())
						.getName("select code from area where name='"
								+ area.get(advert_order_area
								.getSelectedItemPosition()) + "'"));
				params.put("consigneeAddressDetail", fly_detail_p_add.getText()
						.toString());
				params.put("consigneeMobileTel", fly_detail_p_mob.getText()
						.toString());
			} else {
				params.put("consigneeUserName", "");
				params.put("consigneeProvinceCode", "");
				params.put("consigneeCityCode", "");
				params.put("consigneeAreaCode", "");
				params.put("consigneeAddressDetail", "");
				params.put("consigneeMobileTel", "");
			}

			/*
			 * Iterator<Entry<String, String>> iterator = params
			 * .entrySet().iterator(); while (iterator.hasNext()) {
			 * Entry<String, String> entry = iterator.next();
			 *
			 * System.out.print("&" + entry.getKey() + "=" + entry.getValue());
			 * }
			 */
			String url = "/mi/FlightTicket.ashx?act=CreateOrderByPassengers";
			AsyncHttp.post(RealmName.REALM_NAME + url, params,
					new AsyncHttpResponseHandler() {
						public void onStart() {

						};

						public void onSuccess(int arg0, String arg1) {
							System.out.println(arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								if (jsonObject.getString("status").equals("1")) {
									// 下单成功
									FlyOrderBean order = new FlyOrderBean();
									order.setLoginSession(login_account
											.getText().toString());
									order.setOrderNumber(jsonObject
											.getString("orderSerialNumber"));
									order.setAirNumber(flyResult.getFlightNo());
									order.setAirCompany(flyResult
											.getFlyCompany());
									order.setDetailPops(detailPops);
									order.setDate(flyResult.getDate());
									CityDB db12 = new CityDB(
											getApplicationContext());
									CityDB db23 = new CityDB(
											getApplicationContext());
									order.setEndAirNum(flyResult
											.getDstJetquay());
									order.setEndAirPort(db23
											.getJicBySam(flyResult.getDstCity()));
									order.setEndTime(flyResult.getEndTime());
									order.setPhoneMobile(linkPhone);
									order.setStartAirNum(flyResult
											.getOrgJetquay());
									order.setStartAirPort(db12
											.getJicBySam(flyResult.getOrgCity()));
									order.setStartTime(flyResult.getFirstTime());
									order.setPolicyOrderId(jsonObject
											.getString("PolicyOrderId"));
									order.setUserName(linkMan);
									order.setTrade_no(jsonObject
											.getString("trade_no"));
									JSONArray array = jsonObject
											.getJSONArray("items");
									int len = array.length();
									String[] bankNames;
									ArrayList<CardItem> banks;
									if (len != 0) {
										banks = new ArrayList<CardItem>();
										bankNames = new String[len + 1];
										for (int i = 0; i < len; i++) {
											JSONObject object2 = array
													.getJSONObject(i);
											CardItem item = new CardItem();
											item.setType(object2
													.getString("pay_type"));
											item.setBankName(object2
													.getString("gate_id"));
											item.setLastId(object2
													.getString("last_four_cardid"));
											item.setId(object2
													.getString("UserSignedBankID"));
											banks.add(item);
											bankNames[i] = ParseBank.parseBank(
													item.getBankName(),
													getApplicationContext())
													+ "("
													+ ParseBank.paseName(item
													.getType())
													+ ")"
													+ item.getLastId();
										}
										CardItem item = new CardItem();
										item.setBankName("-1");
										item.setId("-1");
										item.setLastId("-1");
										item.setType("-1");
										banks.add(item);
										bankNames[len] = "新支付方式";
										order.setBankNames(bankNames);
										order.setBanks(banks);
									}

									Message msg = new Message();
									msg.what = 1;
									msg.obj = order;
									handler.sendMessage(msg);
								} else {
									Toast.makeText(getApplicationContext(),
											"下单失败，请检查订单表", 0).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						};
					}, getApplicationContext());
		} else {
			Toast.makeText(getApplicationContext(), "设备未激活", 0).show();

		}
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {

				case 1:
					// 进入另一个页面
					Intent intent = new Intent(AirOrderSendActivity.this,
							FlyDetailOrderActivity.class);
					Bundle bundle = new Bundle();
					FlyOrderBean bean = (FlyOrderBean) msg.obj;
					bundle.putSerializable("fly_order", bean);
					intent.putExtras(bundle);
					startActivity(intent);
					break;
				default:
					break;
			}
		};
	};
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	DisplayMetrics dm = new DisplayMetrics();

	private void initPopupWindow(View view) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.card_category, null);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mPopupWindow = new PopupWindow(popView, view.getWidth(),
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
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

		fly_pop_1 = (TextView) popView.findViewById(R.id.fly_pop_1);
		fly_pop_2 = (TextView) popView.findViewById(R.id.fly_pop_2);
		fly_pop_3 = (TextView) popView.findViewById(R.id.fly_pop_3);
		fly_pop_4 = (TextView) popView.findViewById(R.id.fly_pop_4);
		fly_pop_5 = (TextView) popView.findViewById(R.id.fly_pop_5);
		fly_pop_6 = (TextView) popView.findViewById(R.id.fly_pop_6);
		fly_pop_1.setOnClickListener(clickListener);
		fly_pop_2.setOnClickListener(clickListener);
		fly_pop_3.setOnClickListener(clickListener);
		fly_pop_4.setOnClickListener(clickListener);
		fly_pop_5.setOnClickListener(clickListener);
		fly_pop_6.setOnClickListener(clickListener);

	}

	private void initPopupWindowL(View view) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.people_category, null);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mPopupWindow = new PopupWindow(popView, view.getWidth(),
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
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

		fly_pop_1_p = (TextView) popView.findViewById(R.id.fly_pop_1_p);
		fly_pop_2_p = (TextView) popView.findViewById(R.id.fly_pop_2_p);
		fly_pop_3 = (TextView) popView.findViewById(R.id.fly_pop_3);
		fly_pop_4 = (TextView) popView.findViewById(R.id.fly_pop_4);
		fly_pop_5 = (TextView) popView.findViewById(R.id.fly_pop_5);
		fly_pop_6 = (TextView) popView.findViewById(R.id.fly_pop_6);
		fly_pop_1_p.setOnClickListener(clickListener);
		fly_pop_2_p.setOnClickListener(clickListener);

	}

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

	/**
	 * 点击事件
	 */
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {

				case R.id.fly_detiail_card_c:
					initPopupWindow(fly_detiail_card_c);
					showPopupWindow(fly_detiail_card_c);
					break;
				case R.id.fly_detiail_card_l:
					initPopupWindowL(fly_detiail_card_l);
					showPopupWindow(fly_detiail_card_l);
					break;
				case R.id.fly_pop_1:
					fly_detiail_card_c.setText(fly_pop_1.getText().toString());
					fly_detiail_card_c.setTag(fly_pop_1.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_2:
					fly_detiail_card_c.setText(fly_pop_2.getText().toString());
					fly_detiail_card_c.setTag(fly_pop_2.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_3:
					fly_detiail_card_c.setText(fly_pop_3.getText().toString());
					fly_detiail_card_c.setTag(fly_pop_3.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_4:
					fly_detiail_card_c.setText(fly_pop_4.getText().toString());
					fly_detiail_card_c.setTag(fly_pop_4.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_5:
					fly_detiail_card_c.setText(fly_pop_5.getText().toString());
					fly_detiail_card_c.setTag(fly_pop_5.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_6:
					fly_detiail_card_c.setText(fly_pop_6.getText().toString());
					fly_detiail_card_c.setTag(fly_pop_6.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_2_p:
					fly_detiail_card_l.setText(fly_pop_2_p.getText().toString());
					fly_detiail_card_l.setTag(fly_pop_2_p.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_pop_1_p:
					fly_detiail_card_l.setText(fly_pop_1_p.getText().toString());
					fly_detiail_card_l.setTag(fly_pop_1_p.getTag().toString());
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}
					break;
				case R.id.fly_detail_send:
					initPopupWindowG(view);
					showPopupWindowPay(view);

					break;
				case R.id.fly_detail_add:
					String len1 = fly_detiail_card_name.getText().toString();
					String len2 = fly_detiail_card_num.getText().toString();
					String len3 = fly_detiail_card_mob.getText().toString();
					String type1 = fly_detiail_card_l.getTag().toString();
					String type2 = fly_detiail_card_c.getTag().toString();
					// 才会出现一个新的乘机人
					FlyDetailPop pop = new FlyDetailPop();
					pop.setTagL(type1);
					pop.setTagC(type2);
					pop.setName(len1);
					pop.setNum(len2);
					pop.setMob(len3);
					detailPops.add(pop);
					ad.putData(detailPops);
					break;
				default:
					break;
			}

		}
	};

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
			mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0],
					view.getHeight() + location[1]);
		}
	}

	/**
	 * 生成组件对象
	 */
	private void init(FlyResult bean) {
		fly_detail_name = (EditText) findViewById(R.id.fly_detail_name);
		fly_detail_bil = (EditText) findViewById(R.id.fly_detail_bil);

		fly_detail_list = (ListView) findViewById(R.id.fly_detail_list);
		fly_detiail_card_c = (TextView) findViewById(R.id.fly_detiail_card_c);
		fly_detiail_card_l = (TextView) findViewById(R.id.fly_detiail_card_l);
		fly_detiail_card_num = (EditText) findViewById(R.id.fly_detiail_card_num);
		fly_detiail_card_name = (EditText) findViewById(R.id.fly_detiail_card_name);
		fly_detiail_card_mob = (EditText) findViewById(R.id.fly_detiail_card_mob);

		fly_detail_p_name = (EditText) findViewById(R.id.fly_detail_p_name);
		fly_detail_p_mob = (EditText) findViewById(R.id.fly_detail_p_mob);
		fly_detail_p_add = (EditText) findViewById(R.id.fly_detail_p_add);
		fly_detail_add = (Button) findViewById(R.id.fly_detail_add);

		ad = new FlyDeatilItemAdapter(getApplicationContext(), detailPops);
		fly_detail_send = (Button) findViewById(R.id.fly_detail_send);
		fly_detail_send.setOnClickListener(clickListener);
		fly_detail_list.setAdapter(ad);
		fly_detiail_card_c.setOnClickListener(clickListener);
		fly_detiail_card_l.setOnClickListener(clickListener);
		fly_detail_add.setOnClickListener(clickListener);

		advert_order_province = (Spinner) findViewById(R.id.advert_order_province);
		advert_order_city = (Spinner) findViewById(R.id.advert_order_city);
		advert_order_area = (Spinner) findViewById(R.id.advert_order_area);
		province = new CityDB(getApplicationContext())
				.getProvince("select name from province");
		provinceAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, province);
		provinceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		advert_order_province.setAdapter(provinceAdapter);
		// 城市
		city = new CityDB(getApplicationContext())
				.getProvince("select name from city where provinceId =(select code from province where name='"
						+ province.get(0) + "')");
		cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, city);
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		advert_order_city.setAdapter(cityAdapter);
		// 地区
		area = new CityDB(getApplicationContext())
				.getProvince("select name from area where cityId =(select code from city where name='"
						+ city.get(0) + "')");
		areaAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, area);
		areaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		advert_order_area.setAdapter(areaAdapter);
		// 事件
		advert_order_province
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
											   int arg2, long arg3) {
						// TODO Auto-generated method stub
						city = new CityDB(getApplicationContext())
								.getProvince("select name from city where provinceId =(select code from province where name='"
										+ province.get(arg2) + "')");
						cityAdapter = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_spinner_item, city);
						cityAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						advert_order_city.setAdapter(cityAdapter);
						area = new CityDB(getApplicationContext())
								.getProvince("select name from area where cityId =(select code from city where name='"
										+ city.get(0) + "')");
						areaAdapter = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_spinner_item, area);
						areaAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						advert_order_area.setAdapter(areaAdapter);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		advert_order_city
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
											   int arg2, long arg3) {
						// TODO Auto-generated method stub
						area = new CityDB(getApplicationContext())
								.getProvince("select name from area where cityId =(select code from city where name='"
										+ city.get(arg2) + "')");
						areaAdapter = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_spinner_item, area);
						areaAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						advert_order_area.setAdapter(areaAdapter);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		need = (LinearLayout) findViewById(R.id.need);
		fly_detail_isneed = (RadioGroup) findViewById(R.id.fly_detail_isneed);
		fly_detail_need = (RadioButton) findViewById(R.id.fly_detail_need);
		fly_detail_noneed = (RadioButton) findViewById(R.id.fly_detail_noneed);
		fly_detail_noneed.setChecked(true);
		IsNeedVouchers = 0;
		fly_detail_isneed
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						switch (arg1) {
							case R.id.fly_detail_need:
								need.setVisibility(View.VISIBLE);
								IsNeedVouchers = 1;
								break;
							case R.id.fly_detail_noneed:
								IsNeedVouchers = 0;
								need.setVisibility(View.INVISIBLE);
								break;
							default:
								break;
						}
					}
				});

	}
}
