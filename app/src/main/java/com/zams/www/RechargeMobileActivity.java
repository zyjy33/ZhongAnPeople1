package com.zams.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.PhoneAndEmailProving;
import com.android.hengyu.web.RealmName;
import com.android.pliay.SignUtils;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.RechargeMobileData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umpay.quickpay.UmpPayInfoBean;
import com.umpay.quickpay.UmpayQuickPay;
import com.zams.www.R;
import com.zams.www.MyLetterListView.OnTouchingLetterChangedListener;

public class RechargeMobileActivity extends BaseActivity {
	private EditText et_number;
	private TextView tv_information, tv_name, tv_price, tv_mobilenumber;
	private Spinner sp_mobile_money;
	private LinearLayout ll_information;
	private List<String> sp_money;
	private String mobile_number;
	private String mobile_money, money;
	private String yth;
	private int status;
	private MyHandler handler;
	private RechargeMobileData data;
	private WareDao wareDao;
	private DialogProgress progress;
	private String StrUrl;
	private List<UserRegisterData> list;
	private MyPopupWindowMenu popupWindowMenu;
	private ArrayList<CardItem> banks = null;
	private String bankNames[] = null;



	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case 10:
					sp_mobile_money.setSelection(0);
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), "充值失败,请检查充值信息.", Toast.LENGTH_SHORT)
							.show();
					break;
				case 20:
					ll_information.setVisibility(View.VISIBLE);
					data = (RechargeMobileData) msg.obj;
					tv_information.setText(data.location);
					tv_name.setText(data.BaoBeiMingCheng);
					tv_price.setText(data.telFee1);
					tv_mobilenumber.setText(data.phonenum);
					progress.CloseProgress();
					break;
				case 0:
					if (banks != null && banks.size() != 0) {
						// 表示是第二次支付
						System.out.println("写第二次支付");
						// initPopupWindow1();
						// showPopupWindow1(btn_OK);
						Intent intent = new Intent(RechargeMobileActivity.this,
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
						Intent intent = new Intent(RechargeMobileActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 0);
						bundle.putSerializable("trade_no", trade_no);
						intent.putExtras(bundle);
						startActivity(intent);
						// initPopupWindow();
						// showPopupWindow(btn_OK);
					}
					// 在线支付
					// initPopupWindow();
					// showPopupWindow(aliays);
					break;
				case 1:
					ali_pay();
					break;
				case 5:
					System.out.println("微信支付");
					//微信支付
					boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
					if(isPaySupported){
						PayReq req = new PayReq();
						req.appId			= Constant.APP_ID;
						req.partnerId		= Constant.MCH_ID;
						req.prepayId		= prepayid;
						req.nonceStr		= noncestr;
						req.timeStamp		= timestamp;
						req.packageValue	= package_;
						req.sign			= sign;
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
						api.registerApp(Constant.APP_ID);
						boolean flag = api.sendReq(req);
						System.out.println("支付"+flag);
					}else {

					}



					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	}
	public String sign(String content) {
		return SignUtils.sign(content, Common.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	public String getOrderInfo(String subject, String body, String dingdan) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		//		orderInfo += "&total_fee=" + "\"" + mobile_money + "\"";
		orderInfo += "&total_fee=" + "\"" + mobile_money + "\"";
		// 服务器异步通知页面路径
		//		orderInfo += "&notify_url=" + "\"" + RealmName.REALM_NAME
		//				+ "/taobao/alipay_notify_url.aspx" + "\"";
		orderInfo += "&notify_url=" + "\"" +  "http://183.62.138.31:1636/taobao/alipay_notify_url.aspx" + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	private void ali_pay() {
		//
		String orderInfo = getOrderInfo("云商聚", "话费充值", trade_no);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(RechargeMobileActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = 5;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	private String trade_no;
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private static final int REQUESTCODE = 10000;

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.chose_payment, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.no_color));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		Button pay1 = (Button) popView.findViewById(R.id.pay1);
		Button pay2 = (Button) popView.findViewById(R.id.pay2);
		pay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmpPayInfoBean infoBean = new UmpPayInfoBean();
				UmpayQuickPay.requestPayWithBind(RechargeMobileActivity.this,
						trade_no, "9058", "0", "", infoBean, REQUESTCODE);
				dissPop();
			}
		});
		pay2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmpPayInfoBean infoBean = new UmpPayInfoBean();
				UmpayQuickPay.requestPayWithBind(RechargeMobileActivity.this,
						trade_no, "9058", "1", "", infoBean, REQUESTCODE);
				dissPop();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RechargeMobileActivity.REQUESTCODE) {

		}
	}

	// 01 07 13 17 22 29
	// 蓝球：03
	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

	private void dissPop() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}
	private Button tongxunlu;
	/**
	 * 通讯录的信息回调
	 */
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
				case R.id.tongxunlu:

					// show tongxunlu view

					initPopupWindow1();
					showPopupWindow1(tongxunlu);

					break;

				default:
					break;
			}
		}
	};

	private void initPopupWindow1() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.yihua_con, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.white));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		LinearLayout lay_t = (LinearLayout) popView.findViewById(R.id.lay_t);
		lay_t.setVisibility(View.GONE);
		personList = (ListView) popView.findViewById(R.id.list_view);
		MyLetterListView list_view = (MyLetterListView) popView
				.findViewById(R.id.MyLetterListView01);
		start();
		list_view
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
		alphaIndexer = new HashMap<String, Integer>();
		handler2 = new Handler();
		overlayThread = new OverlayThread();
		initOverlay();
		personList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {

				et_number.setText(listPerson.get(arg2).getAsString(NUMBER).replaceAll(" ", ""));
				mPopupWindow.dismiss();

			}
		});
	}
	// 查询联系人
	private ArrayList<ContentValues> listPerson;
	private class MyAsyncQueryHandler extends AsyncQueryHandler {



		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);

		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {
				listPerson = new ArrayList<ContentValues>();
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					ContentValues cv = new ContentValues();
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					if (number.startsWith("+86")) {
						cv.put(NAME, name);
						cv.put(NUMBER, number.substring(3)); // 去掉+86
						cv.put(SORT_KEY, sortKey);
					} else {
						cv.put(NAME, name);
						cv.put(NUMBER, number);
						cv.put(SORT_KEY, sortKey);
					}
					listPerson.add(cv);
				}
				if (list.size() > 0) {
					setAdapter(listPerson);
				}
			}
		}

	}
	private void setAdapter(List<ContentValues> list) {
		adapter = new ListAdapter(this, list);
		personList.setAdapter(adapter);

	}
	private void start() {
		// 点击到通讯录的时候就开始查询公共类目下面的信息
		Uri uri = Uri.parse("content://com.android.contacts/data/phones");
		String[] projection = { "_id", "display_name", "data1", "sort_key" };
		asyncQuery = new MyAsyncQueryHandler(getContentResolver());
		asyncQuery.startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc");
	}
	private BaseAdapter adapter;
	private TextView overlay;
	private ListView personList;
	private MyLetterListView letterListView;
	private AsyncQueryHandler asyncQuery;
	private static final String NAME = "name", NUMBER = "number",
			SORT_KEY = "sort_key";
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler2;
	private OverlayThread overlayThread;
	private IWXAPI api;
	// 初始化汉语拼音首字母弹出提示框
	// 控制手机和显示的情况
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.yihua_con_overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}
	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				personList.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler2.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler2.postDelayed(overlayThread, 1500);
			}
		}

	}
	private class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<ContentValues> list;

		public ListAdapter(Context context, List<ContentValues> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				String currentStr = getAlpha(list.get(i).getAsString(SORT_KEY));
				// 上一个汉语拼音首字母，如果不存在为“ ”
				String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
						.getAsString(SORT_KEY)) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = getAlpha(list.get(i).getAsString(SORT_KEY));
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.yihua_con_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.number = (TextView) convertView
						.findViewById(R.id.number);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ContentValues cv = list.get(position);
			holder.name.setText(cv.getAsString(NAME));
			holder.number.setText(cv.getAsString(NUMBER));
			String currentStr = getAlpha(list.get(position).getAsString(
					SORT_KEY));
			String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
					position - 1).getAsString(SORT_KEY)) : " ";
			if (!previewStr.equals(currentStr)) {
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha;
			TextView name;
			TextView number;
		}

	}
	// 设置overlay不可见
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	// 获得汉语拼音首字母
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}

		if (str.trim().length() == 0) {
			return "#";
		}

		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}

	private void showPopupWindow1(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge_mobile_calls);
		api = WXAPIFactory.createWXAPI(RechargeMobileActivity.this,null);
		api.registerApp(Constant.APP_ID);
		tongxunlu = (Button) findViewById(R.id.tongxunlu);
		tongxunlu.setOnClickListener(clickListener);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(RechargeMobileActivity.this);
		innidata();
		SpinnerData();
		handler = new MyHandler();
		list = wareDao.findisLogin();
		if (list.size() != 0) {
			UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
			yth = registerData.getHengyuCode();
		}
	}

	String type = "-1";

	private RelativeLayout pay_click0, pay_click1, pay_click2, pay_click3;
	private void innidata() {
		pay_click0 = (RelativeLayout) findViewById(R.id.pay_click0);
		pay_click1 = (RelativeLayout) findViewById(R.id.pay_click1);
		pay_click2 = (RelativeLayout) findViewById(R.id.pay_click2);
		pay_click3 = (RelativeLayout) findViewById(R.id.pay_click3);
		pay_click1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				type = "1";
				warn();
			}
		});
		pay_click0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				type = "";
				warn();
			}
		});
		pay_click2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				type = "2";
				warn();
			}
		});
		pay_click3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				type="5";
				//				warn();
				Toast.makeText(getApplicationContext(), "待接入", Toast.LENGTH_SHORT).show();
			}
		});


		et_number = (EditText) findViewById(R.id.et_mobile);
		tv_information = (TextView) findViewById(R.id.tv_information);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_mobilenumber = (TextView) findViewById(R.id.tv_mobilenumber);
		sp_mobile_money = (Spinner) findViewById(R.id.sp_mobile_money);
		sp_mobile_money.setPrompt("请选择充值金额");
		ll_information = (LinearLayout) findViewById(R.id.ll_information);
		ll_information.setVisibility(View.GONE);
		// 得到输入的电话号码文本框里面的内容
		et_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				mobile_number = et_number.getText().toString();
			}
		});


	}

	private void SpinnerData() {
		String[] str2 = new String[] { "请选择充值金额", "30元", "50元", "100元" };
		sp_money = new ArrayList<String>();
		for (int i = 0; i < str2.length; i++) {
			sp_money.add(str2[i]);
		}
		ArrayAdapter aa2 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, sp_money);
		aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_mobile_money.setAdapter(aa2);
		sp_mobile_money.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				money = sp_money.get(arg2);
				if (!money.equals("请选择充值金额")) {
					mobile_number = et_number.getText().toString();
					if ("".equals(mobile_number)
							) {
						// 给出错误提示
						et_number.setError("您输入的手机号码（段）有误!");
						et_number.requestFocus();
						et_number.setText("");
						sp_mobile_money.setSelection(0);
						return;
					} else {
						list = wareDao.findisLogin();
						if (list.size() != 0) {

							mobile_money = money.substring(0,
									money.length() - 1);
							progress.CreateProgress();

							StrUrl = RealmName.REALM_NAME
									+ "/mi/getdata.ashx";
							Log.v("data1", StrUrl);
							Map<String, String> params = new HashMap<String, String>();
							params.put("act", "MobileRechargeCalls");
							params.put("key", "");
							params.put("yth", yth);
							params.put("mobile", mobile_number);
							params.put("fee", mobile_money);
							AsyncHttp.post_1(StrUrl, params,new AsyncHttpResponseHandler(){
								public void onSuccess(int arg0, String arg1) {

									try {
										JSONObject jsonObject = new JSONObject(arg1);
										status = jsonObject.getInt("status");


										if (status == 1) {
											RechargeMobileData data = new RechargeMobileData();
											data.telFee1 = jsonObject
													.getString("telFee1");
											data.phonenum = jsonObject
													.getString("phonenum");
											data.location = jsonObject
													.getString("location");
											data.BaoBeiMingCheng = jsonObject
													.getString("BaoBeiMingCheng");
											data.PassTicket = jsonObject
													.getString("PassTicket");
											Message message = new Message();
											message.what = 20;
											message.obj = data;
											handler.sendMessage(message);
										} else {
											Message message = new Message();
											message.what = 10;
											handler.sendMessage(message);
										}
									} catch (JSONException e) {

										e.printStackTrace();
									}

								};
							} );

						} else {
							int index = 1;
							Intent intent = new Intent(
									RechargeMobileActivity.this,
									UserLoginActivity.class);
							intent.putExtra("login", index);
							startActivity(intent);
							finish();
						}
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {


			}
		});
	}
	private String partner_id,prepayid,noncestr,timestamp,package_,sign;
	private void warn() {
		if (mobile_number == null || money.equals("请选择充值金额")) {

			Toast.makeText(RechargeMobileActivity.this, "请填写您所需充值的信息哟!",
					Toast.LENGTH_LONG).show();
		} else {
			if (mobile_number.length() == 0) {
				Toast.makeText(RechargeMobileActivity.this, "请填写您所需充值的信息哟!",
						Toast.LENGTH_LONG).show();
			} else {
				if ("".equals(mobile_number) || mobile_number.length() < 11) {
					// 给出错误提示
					et_number.setError("您输入的手机号码（段）有误！");
					et_number.requestFocus();
					// 将显示查询结果的TextView清空
					et_number.setText("");
					return;
				} else {
					if (type.equals("-1")) {
						Toast.makeText(getApplicationContext(), "请完善必要信息!", Toast.LENGTH_SHORT)
								.show();
					} else if (type.equals("2")) {
						//支付宝

						// 在线支付
						RequestParams params = new RequestParams();
						params.put("mobile", mobile_number);
						params.put("fee", mobile_money);
						params.put("yth", yth);
						System.out.println("话费");
						//						http://www.ju918.com/mi/umphandler.ashx?act=payReqShortCut_HuaFei&mobile=13316989009&fee=30&yth=114514799
						AsyncHttp
								.post(RealmName.REALM_NAME
												+ "/mi/umphandler.ashx?act=payReqShortCut_HuaFei",
										params, new AsyncHttpResponseHandler() {
											@Override
											public void onSuccess(int arg0,
																  String arg1) {
												// stub
												super.onSuccess(arg0, arg1);
												System.out.println(arg1);
												try {
													JSONObject object = new JSONObject(
															arg1);
													trade_no = object
															.getString("trade_no");

													handler.sendEmptyMessage(1);
												} catch (JSONException e) {

													// block
													e.printStackTrace();
												}
											}
										}, getApplicationContext());

						// AsyncHttp.post(url, params, handler, con);

					}else if (type.equals("1")) {
						// 在线支付
						RequestParams params = new RequestParams();
						params.put("mobile", mobile_number);
						params.put("fee", mobile_money);
						params.put("yth", yth);
						System.out.println("话费");
						//						http://www.ju918.com/mi/umphandler.ashx?act=payReqShortCut_HuaFei&mobile=13316989009&fee=30&yth=114514799
						AsyncHttp
								.post(RealmName.REALM_NAME
												+ "/mi/umphandler.ashx?act=payReqShortCut_HuaFei",
										params, new AsyncHttpResponseHandler() {
											@Override
											public void onSuccess(int arg0,
																  String arg1) {
												// stub
												super.onSuccess(arg0, arg1);
												System.out.println(arg1);
												try {
													JSONObject object = new JSONObject(
															arg1);
													trade_no = object
															.getString("trade_no");
													JSONArray array = object
															.getJSONArray("items");
													int len = array.length();
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
																	+ ParseBank
																	.paseName(item
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
													}
													handler.sendEmptyMessage(0);
												} catch (JSONException e) {
													// block
													e.printStackTrace();
												}
											}
										}, getApplicationContext());

						// AsyncHttp.post(url, params, handler, con);
					} else if(TextUtils.equals("5", type)){
						//支付宝

						// 在线支付
						RequestParams params = new RequestParams();
						params.put("mobile", mobile_number);
						params.put("fee", mobile_money);
						params.put("yth", yth);
						System.out.println("话费");
						//						http://www.ju918.com/mi/umphandler.ashx?act=payReqShortCut_HuaFei&mobile=13316989009&fee=30&yth=114514799
						AsyncHttp
								.post(RealmName.REALM_NAME
												+ "/mi/umphandler.ashx?act=payReqShortCut_HuaFei",
										params, new AsyncHttpResponseHandler() {
											@Override
											public void onSuccess(int arg0,
																  String arg1) {
												// stub
												super.onSuccess(arg0, arg1);
												System.out.println(arg1);
												try {
													JSONObject object = new JSONObject(
															arg1);

													//微信处理
													//													  appid = object.getString("appid");
													partner_id = object.getString("mch_id");
													prepayid = object.getString("prepay_id");
													noncestr= object.getString("nonce_str");
													timestamp= object.getString("timeStamp");
													package_="Sign=WXPay";
													sign= object.getString("sign");

													handler.sendEmptyMessage(5);
												} catch (JSONException e) {
													// block
													e.printStackTrace();
												}
											}
										}, getApplicationContext());

						// AsyncHttp.post(url, params, handler, con);

					}else {
						if (!"".equals(status) && status == 1) {
							if (list.size() != 0) {
								Intent intent = new Intent(
										RechargeMobileActivity.this,
										RechargePayMobileActivity.class);
								intent.putExtra("infromation", data.phonenum);
								intent.putExtra("money", data.telFee1);
								intent.putExtra("spmoney", mobile_money);
								intent.putExtra("PassTicket", data.PassTicket);
								startActivity(intent);
							} else {
								int index = 1;
								Intent intent = new Intent(
										RechargeMobileActivity.this,
										UserLoginActivity.class);
								intent.putExtra("login", index);
								startActivity(intent);
								finish();
							}
						}
					}

				}
			}
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
			popupWindowMenu.showAtLocation(
					findViewById(R.id.recharge_mobile_calls), Gravity.BOTTOM,
					0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}
}
