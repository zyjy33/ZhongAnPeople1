package com.zams.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.hengyushop.airplane.adapter.ShopingCartOrderAdapter;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.ShopCarts;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.CommomConfrim;
import com.lglottery.www.widget.CommomConfrim.onDeleteSelect;
import com.lglottery.www.widget.InScrollListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
/**
 * 中安民生订单确认
 * @author Administrator
 *
 */
public class MyOrderConfrimActivityll extends BaseActivity {
	private ListView list_address_a;
	private String yth, key, phone, imei, tel, endmoney, pwd, username,
			PassTicket, shopPassTicket, orderSerialNumber;
	private int addressid;
	private WareDao wareDao;
	private UserRegisterData registerData;
	private String strUrl;
	private ArrayList<UserAddressData> list;
	private DialogProgress progress;
	private int checkedAddressId;
	private StringBuilder orderid;
	private MyPopupWindowMenu popupWindowMenu;
	private String trade_no;
	private ArrayList<CardItem> banks = null;
	private String bankNames[] = null;
	private static final int REQUESTCODE = 10000;
	private int jf;
	private ArrayList<ShopCarts> carts;
	private TextView tv_user_name, tv_user_address, tv_user_phone;
	private SharedPreferences spPreferences;
	public static String user_name, user_id;
	private ImageButton btn_add_address;
	private ShopingCartOrderAdapter adapter;
	private InScrollListView list_shop_cart;
	ArrayList<ShopCartData> list_ll;
	private ShopCartData data;
	private ShopCartData dm;
	private LinearLayout layout0, ll_ljgm, layout2;
	private RelativeLayout layout1;
	private TextView heji;
	private Button confrim_btn;
	ImageView img_ware;
	TextView tv_warename;
	TextView tv_color;
	TextView tv_size;
	String name = "";
	String retailPrice;
	String type;
	String order_str;
	String recharge_no;
	private IWXAPI api;
	private String partner_id,prepayid,noncestr,timestamp,package_,sign;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(MyOrderConfrimActivityll.this);
		api = WXAPIFactory.createWXAPI(MyOrderConfrimActivityll.this,null);
		api.registerApp(Constant.APP_ID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confrim);
		progress.CreateProgress();
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");

		initdata();
		// loadWeather();//获取购物车列表数据
		getuseraddress();
		try {

			// 立即购买
			img_ware = (ImageView) findViewById(R.id.img_ware);
			tv_warename = (TextView) findViewById(R.id.tv_ware_name);
			tv_color = (TextView) findViewById(R.id.tv_color);
			tv_size = (TextView) findViewById(R.id.tv_size);
			ll_ljgm = (LinearLayout) findViewById(R.id.ll_ljgm);

			String proName = getIntent().getStringExtra("proName");
			if (proName != null) {
				ll_ljgm.setVisibility(View.VISIBLE);
				System.out.println("1================");
				String proFaceImg = getIntent().getStringExtra("proFaceImg");
				retailPrice = getIntent().getStringExtra("retailPrice");
				String marketPrice = getIntent().getStringExtra("marketPrice");
				// intent.putExtra("goods_id",goods_id);
				// intent.putExtra("article_id",article_id);
				ImageLoader imageLoaderll = ImageLoader.getInstance();
				imageLoaderll.displayImage(RealmName.REALM_NAME_HTTP
						+ proFaceImg, img_ware);
				tv_color.setText(retailPrice);
				tv_size.setText(marketPrice);
				tv_warename.setText(proName);
				heji.setText("合计:" + retailPrice);
			} else {
				loadWeather();// 获取购物车列表数据
				System.out.println("2================");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initdata() {
		try {

			confrim_btn = (Button) findViewById(R.id.confrim_btn);
			list_shop_cart = (InScrollListView) findViewById(R.id.list_shop_cart);
			btn_add_address = (ImageButton) findViewById(R.id.img_btn_add_address);
			layout0 = (LinearLayout) findViewById(R.id.layout0);
			layout1 = (RelativeLayout) findViewById(R.id.layout1);
			layout2 = (LinearLayout) findViewById(R.id.layout2);
			tv_user_name = (TextView) findViewById(R.id.tv_user_name);
			tv_user_address = (TextView) findViewById(R.id.tv_user_address);
			tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
			heji = (TextView) findViewById(R.id.heji);
			retailPrice = getIntent().getStringExtra("total_cll");
			heji.setText("合计:" + retailPrice);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

			/**
			 * 收货地址列表
			 */
			layout0.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(MyOrderConfrimActivityll.this,
							AddressManagerActivity.class);
					intent.putExtra("order_confrim", "order_confrim");// 标示
					startActivityForResult(intent, 100);
				}
			});

			/**
			 * 添加收货地址
			 */
			layout1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = 0;
					Intent intent = new Intent(MyOrderConfrimActivityll.this,
							AddUserAddressActivity.class);
					intent.putExtra("index", index);
					startActivityForResult(intent, 0);
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

		confrim_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {


				CommomConfrim.showSheet(MyOrderConfrimActivityll.this,
						new onDeleteSelect() {

							@Override
							public void onClick(int resID) {

								switch (resID) {
									case R.id.item0:
										// 余额支付
										Toast.makeText(MyOrderConfrimActivityll.this, "功能正在完善",Toast.LENGTH_SHORT).show();
										break;
									case R.id.item1:
										break;
									case R.id.item2:// 支付宝
										type = "3";
										loadguanggao(type);
										break;
									case R.id.item3:
										type = "5";
										loadweixinzf1(type);

										break;
									case R.id.item4:

										break;
									default:
										break;
								}
							}

						}, cancelListener, null);
			}
		});
	}

	//	@Override
	//	protected void onResume() {
	//
	//		super.onResume();
	//		getuseraddress();
	//	}

	/**
	 * 获取购物车列表数据
	 */
	private void loadWeather() {
		list_ll = new ArrayList<ShopCartData>();
		// progress.CreateProgress();
		// String id = UserLoginActivity.id;
		user_id = spPreferences.getString("user_id", "");
		System.out.println("结果呢1==============" + user_id);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="
				+ user_id + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					System.out.println("1================" + arg1);
					JSONArray jsot = jsonObject.getJSONArray("data");
					data = new ShopCartData();
					for (int i = 0; i < jsot.length(); i++) {
						JSONObject object = jsot.getJSONObject(i);
						dm = new ShopCartData();
						data.title = object.getString("title");
						data.market_price = object.getString("market_price");
						data.sell_price = object.getString("sell_price");
						data.id = object.getString("id");
						data.quantity = object.getInt("quantity");
						data.img_url = object.getString("img_url");

						dm.setTitle(object.getString("title"));
						dm.setMarket_price(object.getString("market_price"));
						dm.setSell_price(object.getString("sell_price"));
						dm.setId(object.getString("id"));
						dm.setImg_url(object.getString("img_url"));
						dm.setQuantity(object.getInt("quantity"));

						name = dm.getTitle();
						System.out.println("21================" + name);
						list_ll.add(dm);
					}
					System.out.println("2================" + list_ll.size());
					// progress.CloseProgress();
					handler.sendEmptyMessage(0);
				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

		}, getApplicationContext());
	}

	/**
	 * 输出用户默认收货地址
	 */
	private void getuseraddress() {
		list_ll = new ArrayList<ShopCartData>();
		// progress.CreateProgress();
		// String id = UserLoginActivity.id;
		// String user_name = UserLoginActivity.user_name;
		user_name = spPreferences.getString("user", "");
		// user_id = spPreferences.getString("user_id", "");

		System.out.println("结果呢1==============" + user_name);
		System.out.println("结果呢2==============" + user_id);
		// AsyncHttp.get(RealmName.REALM_NAME_LL+
		// "/get_user_shopping_address_default?user_name=13714758507"
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_user_shopping_address_default?user_name=" + user_name
				+ "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					System.out.println("1================" + arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
						JSONObject jsot = jsonObject.getJSONObject("data");
						UserAddressData data = new UserAddressData();
						String name = jsot.getString("user_accept_name");
						String user_area = jsot.getString("area");
						String user_mobile = jsot.getString("user_mobile");
						String user_address = jsot.getString("user_address");
						tv_user_name.setText("收货人：" + name);
						tv_user_address.setText(user_area + "、" + user_address);
						tv_user_phone.setText(user_mobile);

						// jsot.getString("user_accept_name");
						// data.user_area = jsot.getString("area");
						// data.user_mobile = jsot.getString("user_mobile");
						// data.user_address = jsot.getString("user_address");
						// System.out.println("21================"+name);
						// System.out.println("21================"+user_area);
						// System.out.println("21================"+user_mobile);
						// System.out.println("21================"+user_address);
						// list.add(data);
						layout1.setVisibility(View.GONE);
						// layout2.setVisibility(View.GONE);
						progress.CloseProgress();
						layout0.setVisibility(View.VISIBLE);
						// handler.sendEmptyMessage(2);
					} else {
						progress.CloseProgress();
						layout1.setVisibility(View.VISIBLE);
						// layout2.setVisibility(View.VISIBLE);
						layout0.setVisibility(View.GONE);

					}

				} catch (JSONException e) {

					progress.CloseProgress();
					e.printStackTrace();
				}
			}

		}, getApplicationContext());

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 在这里进行查询地址的操作
		// Toast.makeText(getApplicationContext(), "查询地址联网操作",200).show();
		// handler.sendEmptyMessage(4);
		if (resultCode == 100) {
			layout0.setVisibility(View.VISIBLE);
			layout1.setVisibility(View.GONE);

			UserAddressData dt = (UserAddressData) data.getSerializableExtra("data");
			checkedAddressId = dt.consigneeAddressId;
			String name = dt.user_accept_name;
			String user_area = dt.user_area;
			String user_mobile = dt.user_mobile;
			String user_address = dt.user_address;
			System.out.println("checkedAddressId==================" + name);

			tv_user_name.setText("收货人:"+name);
			tv_user_address.setText(user_area+" "+user_address);
			tv_user_phone.setText(user_mobile);
		}
	}


	OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			/*
			 * http://www.ju918.com/mi/getdata.ashx?act=UserCartInfo&appkey=
			 * 0762222540
			 * &key=QUPgWi93j719&sign=AAE3474591B6B22950AD09A11082D4D751DDABC9
			 * &yth=112967999
			 */
		}
	};
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
				case 0:
					try {

						System.out.println("3================"+list_ll.size());
						adapter = new ShopingCartOrderAdapter(list_ll, getApplicationContext(), handler);
						list_shop_cart.setAdapter(adapter);
						//				list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

					} catch (Exception e) {

						e.printStackTrace();
					}
					break;
				case 1:
					ali_pay();
					break;
				case 2://微信支付
					try {
						boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
						if(isPaySupported){
							try {
								PayReq req = new PayReq();
								req.appId			= Constant.APP_ID;
								req.partnerId		= Constant.MCH_ID;
								req.prepayId		= prepayid;//7
								req.nonceStr		= noncestr;//3
								req.timeStamp		= timestamp;//-1
								req.packageValue	= package_;
								req.sign			= sign;//-3
								// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
								api.registerApp(Constant.APP_ID);
								boolean flag = api.sendReq(req);
								System.out.println("支付"+flag);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}else {

						}
					} catch (Exception e) {

						e.printStackTrace();
					}

					break;
				case 5://支付宝
					PayResult payResult = new PayResult((String) msg.obj);

					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();
					System.out.println(resultInfo + "---" + resultStatus);
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(MyOrderConfrimActivityll.this, "支付成功",
								Toast.LENGTH_SHORT).show();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(MyOrderConfrimActivityll.this, "支付结果确认中",
									Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(MyOrderConfrimActivityll.this, "支付失败",
									Toast.LENGTH_SHORT).show();

						}
					}
					break;
				default:
					break;
			}
		}
	};


	/**
	 * 用户在线充值    支付宝1
	 * @param payment_id
	 */
	private void loadguanggao(String fund_id) {
		try {
			progress.CreateProgress();
			System.out.println("==============="+retailPrice);

			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/update_order_payment?user_id="+user_id+"&user_name="+user_name+"" +
							"&amount="+retailPrice+"&fund_id="+fund_id+"&payment_id=3",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("1================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									JSONObject obj = object.getJSONObject("data");
									AdvertDao1 data = new AdvertDao1();
									data.recharge_no = obj.getString("recharge_no");
									recharge_no = data.recharge_no;
									System.out.println("11================================="+data.recharge_no );
									progress.CloseProgress();
									//										loadguanggaoll(recharge_no);
									loadzhidu(recharge_no);
								}else {
									progress.CloseProgress();
									Toast.makeText(MyOrderConfrimActivityll.this, info, 200).show();
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, getApplicationContext());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 用户在线充值 支付宝2
	 * @param payment_id
	 */
	//	private void loadguanggaoll(String recharge_noll) {
	//		try {
	//			recharge_no = recharge_noll;
	//			System.out.println("recharge_no================================="+recharge_no);
	//			String login_sign = spPreferences.getString("login_sign", "");
	////			String login_sign = UserLoginActivity.login_sign;;
	//			System.out.println("login_sign================================="+login_sign);
	//
	//		AsyncHttp.get(RealmName.REALM_NAME_LL
	//				+ "/update_amount_reserve?user_id="+user_id+"&user_name="+user_name+"" +
	//						"&recharge_no="+recharge_noll+"&sign="+login_sign+"",
	//
	//				new AsyncHttpResponseHandler() {
	//					@Override
	//					public void onSuccess(int arg0, String arg1) {
	//						super.onSuccess(arg0, arg1);
	//						try {
	//							JSONObject object = new JSONObject(arg1);
	//							System.out.println("1================================="+arg1);
	//							  String status = object.getString("status");
	//							    String info = object.getString("info");
	//							    if (status.equals("y")) {
	//							    	   progress.CloseProgress();
	//										loadzhidu(recharge_no);
	//							    }else {
	//							    	progress.CloseProgress();
	//									Toast.makeText(MyOrderConfrimActivity.this, info, 200).show();
	//								}
	//						} catch (JSONException e) {
	//							e.printStackTrace();
	//						}
	//					}
	//
	//					@Override
	//					public void onFailure(Throwable arg0, String arg1) {
	//
	//						super.onFailure(arg0, arg1);
	//						System.out.println("11================================="+arg0);
	//						System.out.println("22================================="+arg1);
	////						Toast.makeText(MyOrderConfrimActivity.this, "网络超时异常", 200).show();
	//					}
	//
	//				}, null);
	//
	//		} catch (Exception e) {
	//
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 用户在线充值  支付宝3
	 * @param payment_id
	 */
	private void loadzhidu(String recharge_no) {
		try {

			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/payment_sign?user_id="+user_id+"&user_name="+user_name+"" +
							"&total_fee="+retailPrice+"&out_trade_no="+recharge_no+"&payment_type=alipay",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("2================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									handler.sendEmptyMessage(1);
								}else {
									progress.CloseProgress();
									Toast.makeText(MyOrderConfrimActivityll.this, info, 200).show();
								}
								//							String info = object.getString("info");
								//							Toast.makeText(getApplicationContext(), info, 200).show();
								//							System.out.println("==========================11=="+recharge_no);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	/**
	 * 用户在线充值    微信支付1
	 * @param payment_id
	 */
	private void loadweixinzf1(String fund_id) {
		try {
			progress.CreateProgress();

			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/add_amount_recharge?user_id="+user_id+"&user_name="+user_name+"" +
							"&amount="+1+"&fund_id="+fund_id+"&payment_id="+5+"",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("0================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									JSONObject obj = object.getJSONObject("data");
									AdvertDao1 data = new AdvertDao1();
									data.recharge_no = obj.getString("recharge_no");
									recharge_no = data.recharge_no;
									System.out.println("0================================="+data.recharge_no );
									progress.CloseProgress();
									//										loadweixinzf2(recharge_no);
									loadweixinzf3(recharge_no);
								}else {
									progress.CloseProgress();
									Toast.makeText(MyOrderConfrimActivityll.this, info, 200).show();
								}


							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, getApplicationContext());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 用户在线充值 微信支付2
	 * @param payment_id
	 */
	//	private void loadweixinzf2(String recharge_noll) {
	//		try {
	//			recharge_no = recharge_noll;
	//			String login_sign = spPreferences.getString("login_sign", "");
	////			String login_sign = UserLoginActivity.login_sign;
	//			System.out.println("login_sign================================="+login_sign);
	//
	//		AsyncHttp.get(RealmName.REALM_NAME_LL
	//				+ "/update_amount_reserve?user_id="+user_id+"&user_name="+user_name+"" +
	//						"&recharge_no="+recharge_no+"&sign="+login_sign+"",
	//
	//				new AsyncHttpResponseHandler() {
	//					@Override
	//					public void onSuccess(int arg0, String arg1) {
	//						super.onSuccess(arg0, arg1);
	//						try {
	//							System.out.println("1================================="+arg1);
	//							JSONObject object = new JSONObject(arg1);
	//						    String status = object.getString("status");
	//						    String info = object.getString("info");
	//						    if (status.equals("y")) {
	//						    	  progress.CloseProgress();
	//									loadweixinzf3(recharge_no);
	//						    }else {
	//						    	progress.CloseProgress();
	//								Toast.makeText(MyOrderConfrimActivity.this, info, 200).show();
	//							}
	//						} catch (JSONException e) {
	//							e.printStackTrace();
	//						}
	//					}
	//
	//					@Override
	//					public void onFailure(Throwable arg0, String arg1) {
	//
	//						super.onFailure(arg0, arg1);
	//						System.out.println("11================================="+arg0);
	//						System.out.println("22================================="+arg1);
	//					}
	//
	//
	//				}, null);
	//
	//		} catch (Exception e) {
	//
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 用户在线充值    微信支付3
	 * @param payment_id
	 */
	private void loadweixinzf3(String recharge_no) {
		try {

			System.out.println("总金额==================="+retailPrice);

			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/payment_sign?user_id="+user_id+"&user_name="+user_name+"" +
							"&total_fee="+1+"&out_trade_no="+recharge_no+"&payment_type=weixin",

					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {

								JSONObject object = new JSONObject(arg1);
								System.out.println("weixin================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if(status.equals("y")){
									JSONObject jsonObject = object.getJSONObject("data");
									partner_id = jsonObject.getString("mch_id");
									prepayid = jsonObject.getString("prepay_id");
									noncestr= jsonObject.getString("nonce_str");
									timestamp = jsonObject.getString("timestamp");
									package_="Sign=WXPay";
									sign= jsonObject.getString("sign");
									System.out.println("weixin================================="+package_);
									progress.CloseProgress();
									handler.sendEmptyMessage(2);
								}else {
									progress.CloseProgress();
									Toast.makeText(MyOrderConfrimActivityll.this, info, 200).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void ali_pay() {
		try {

			//
			String orderInfo = getOrderInfo("中安民生商品", "商品描述", recharge_no);

			// 对订单做RSA 签名
			String sign = sign(orderInfo);
			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"+ getSignType();

			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(MyOrderConfrimActivityll.this);
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

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content
	 *            待签名订单信息
	 */
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
	/**
	 * create the order info. 创建订单信息
	 *
	 */
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
		//		orderInfo += "&total_fee=" + "\"" + chongzhi_edit.getText().toString() + "\"";
		orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";

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
		System.out.println(orderInfo);
		return orderInfo;
	}
}
