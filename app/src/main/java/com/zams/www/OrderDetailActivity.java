package com.zams.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.hengyu.pub.MyOrderDetailAdapter;
import com.android.hengyu.ui.ArrayWheelAdapterll;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.ui.WheelViewll;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.OrderDetailData;
import com.hengyushop.entity.OrderInfromationData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class OrderDetailActivity extends BaseActivity implements
		OnClickListener {

	private ListView listView;
	private TextView tv_total, tv_jf;
	private Button btn_continue_pay, btn_delete_order;
	private String strUrl, strUrl2;
	private WareDao wareDao;
	private DialogProgress progress;
	private String yth, key;
	private ArrayList<OrderDetailData> list;
	private MyPopupWindowMenu popupWindowMenu;
	private TextView v1, v2, v3, v4;
	private OrderInfromationData data;
	private Bundle bundle;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					list = (ArrayList<OrderDetailData>) msg.obj;
					MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(list,
							OrderDetailActivity.this, imageLoader);
					listView.setAdapter(adapter);
					progress.CloseProgress();
					break;
				case 1:
					String str = (String) msg.obj;
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), str, 200).show();
					Intent intent = new Intent(OrderDetailActivity.this,
							IndividualCenterActivity.class);
					startActivity(intent);
					finish();
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_detail);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(OrderDetailActivity.this);
		listView = (ListView) findViewById(R.id.list_order_detail);
		listView.setCacheColorHint(0);
		tv_total = (TextView) findViewById(R.id.tv_total);
		tv_jf = (TextView) findViewById(R.id.tv_jf);
		btn_continue_pay = (Button) findViewById(R.id.btn_continue_pay);
		btn_delete_order = (Button) findViewById(R.id.btn_delete_order);
		v1 = (TextView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		v3 = (TextView) findViewById(R.id.v3);
		v4 = (TextView) findViewById(R.id.v4);

		btn_continue_pay.setOnClickListener(this);
		btn_delete_order.setOnClickListener(this);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		data = (OrderInfromationData) bundle.getSerializable("obj");
		// code = (String) bundle.get("code");
		// total = (String) bundle.get("total");
		// reno = (String) bundle.get("trno");
		/*
		 * String total = list.get(arg2).Price; // String code =
		 * list.get(arg2).SerialNumber; // String reno = list.get(arg2).trno;
		 */
		tv_jf.setText("消费福利:" + data.jf);
		tv_total.setText("订单总价:  ￥" + data.Price);
		v1.setText(data.Address);
		v2.setText(data.UserName);
		v3.setText(data.SerialNumber);
		v4.setText(data.Time);
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();

		strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "orderdetail");
		params.put("orderid", data.SerialNumber);
		params.put("key", key);
		params.put("yth", yth);

		progress.CreateProgress();
		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<OrderDetailData> list = new ArrayList<OrderDetailData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				OrderDetailData data = new OrderDetailData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.Code = object.getString("proCode");
				data.Name = object.getString("proName");
				data.Price = object.getString("totalProductPrice");
				data.FanLi = object.getString("GetFanLi");
				data.Count = object.getString("productCount");
				data.proFaceImg = object.getString("proFaceImg");
				list.add(data);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = list;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

	/**
	 * 在线支付
	 */
	private void onlinePay() {

		String trade_no = data.trno;
		String[] bankNames = bundle.getStringArray("bank_names");
		ArrayList<CardItem> banks = (ArrayList<CardItem>) bundle
				.getSerializable("bank_objs");
		// 替换为支付控件
		// initPopupWindow();
		// showPopupWindow(btn_continue_pay);
		if (banks != null && banks.size() != 0) {
			// 表示是第二次支付
			System.out.println("写第二次支付");
			// initPopupWindow1();
			// showPopupWindow1(btn_OK);
			Intent intent = new Intent(OrderDetailActivity.this,
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
			Intent intent = new Intent(OrderDetailActivity.this,
					PayActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("tag", 0);
			bundle.putSerializable("trade_no", trade_no);
			intent.putExtras(bundle);
			startActivity(intent);
			// initPopupWindow();
			// showPopupWindow(btn_OK);
		}
		/*
		 * UmpPayInfoBean infoBean = new UmpPayInfoBean();
		 * UmpayQuickPay.requestPayWithBind(OrderDetailActivity.this, reno,
		 * "9058", "1", "", infoBean, 10000);
		 */

	}

	private void tongquan() {
		int index = 2;
		Intent intent = new Intent(OrderDetailActivity.this,
				WareShopCartPayActivity.class);
		intent.putExtra("id", index);
		intent.putExtra("orderSerialNumber", data.SerialNumber);
		intent.putExtra("money", data.Price);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_continue_pay:
			/*
			 * if (data.trno.length() == 0) { int index = 2; Intent intent = new
			 * Intent(OrderDetailActivity.this, WareShopCartPayActivity.class);
			 * intent.putExtra("id", index);
			 * intent.putExtra("orderSerialNumber", data.SerialNumber);
			 * intent.putExtra("money", data.Price); startActivity(intent); }
			 * else { String trade_no = data.trno; String[] bankNames =
			 * bundle.getStringArray("bank_names"); ArrayList<CardItem> banks =
			 * (ArrayList<CardItem>) bundle .getSerializable("bank_objs"); //
			 * 替换为支付控件 // initPopupWindow(); //
			 * showPopupWindow(btn_continue_pay); if (banks != null &&
			 * banks.size() != 0) { // 表示是第二次支付 System.out.println("写第二次支付"); //
			 * initPopupWindow1(); // showPopupWindow1(btn_OK); Intent intent =
			 * new Intent(OrderDetailActivity.this, PayActivity.class); Bundle
			 * bundle = new Bundle(); bundle.putInt("tag", 1);
			 * bundle.putSerializable("trade_no", trade_no);
			 * bundle.putStringArray("bank_names", bankNames);
			 * bundle.putSerializable("bank_objs", banks);
			 * intent.putExtras(bundle); startActivity(intent); } else { //
			 * 表示首次支付 Intent intent = new Intent(OrderDetailActivity.this,
			 * PayActivity.class); Bundle bundle = new Bundle();
			 * bundle.putInt("tag", 0); bundle.putSerializable("trade_no",
			 * trade_no); intent.putExtras(bundle); startActivity(intent); //
			 * initPopupWindow(); // showPopupWindow(btn_OK); }
			 *
			 *
			 * }
			 */
				initPopupWindow();
				showPopupWindow(listView);
				break;
			case R.id.btn_delete_order:
				dialog();
				break;

			default:
				break;
		}
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
		String[] names = new String[] { "在线支付", "通券支付" };
		ArrayWheelAdapterll<String> bankAdapter = new ArrayWheelAdapterll<String>(
				names);
		bank_item.setAdapter(bankAdapter);

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dissPop();
			}
		});
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int index = bank_item.getCurrentItem();
				if (index == 0) {
					onlinePay();
				} else {
					tongquan();
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

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认删除这个商品吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				strUrl2 = RealmName.REALM_NAME
						+ "/mi/receiveOrderInfo.ashx?act=UpdateProductOrderStatusId&key="
						+ key + "&yth=" + yth + "&orderSerialNumber="
						+ data.SerialNumber;
				progress.CreateProgress();
				AsyncHttp.get(strUrl2, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String msg = jsonObject.getString("msg");
							Message message = new Message();
							message.what = 1;
							message.obj = msg;
							handler.sendMessage(message);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, getApplicationContext());
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
}
