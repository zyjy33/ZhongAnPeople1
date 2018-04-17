package com.hengyushop.demo.my;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.hengyushop.demo.activity.BaoMinOKActivity;
import com.hengyushop.demo.activity.BaoMinTiShiActivity;
import com.hengyushop.demo.activity.ZhongAnMinShenXqActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.home.ZhiFuOKActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zams.www.R;

/**
 * �а�����
 * 
 * @author Administrator
 * 
 */
public class MyOrderZFActivity extends BaseActivity implements OnClickListener {
	String total_c;
	private DialogProgress progress;
	String user_name, user_id, login_sign, order_no;
	private SharedPreferences spPreferences;
	boolean zhuangtai = false;
	private String partner_id, prepayid, noncestr, timestamp, package_, sign;
	private IWXAPI api;
	boolean flag;
	String xq = "0";
	String orderxq;
	public static String order_type = "0";
	public static String recharge_no, notify_url;
	public static String province, city, area, user_address, accept_name,
			user_mobile;
	public static String datetime, sell_price, give_pension, article_id;
	LinearLayout ll_zhifu_buju;
	public static String huodong_type = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_delete_pop);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(MyOrderZFActivity.this);
		api = WXAPIFactory.createWXAPI(MyOrderZFActivity.this, null);
		api.registerApp(Constant.APP_ID);
		setUpViews();
		recharge_no = getIntent().getStringExtra("order_no");
		System.err.println("recharge_no==============" + recharge_no);
		total_c = getIntent().getStringExtra("total_c");
		System.err.println("total_c==============" + total_c);
	}

	private void setUpViews() {
		TextView item0 = (TextView) findViewById(R.id.item0);
		TextView item1 = (TextView) findViewById(R.id.item1);
		TextView item2 = (TextView) findViewById(R.id.item2);
		TextView item3 = (TextView) findViewById(R.id.item3);
		TextView item4 = (TextView) findViewById(R.id.item4);
		ll_zhifu_buju = (LinearLayout) findViewById(R.id.ll_zhifu_buju);
		item0.setOnClickListener(this);
		// item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		System.out.println("flag״̬==============" + flag);
		if (flag == true) {
			// flag = false;
			userloginqm();
			// finish();
			orderxq = getIntent().getStringExtra("5");
			System.out.println("---------------xq-" + orderxq);
			// if (orderxq != null) {
			// if (orderxq.equals("5")) {
			// MyOrderXqActivity.handler.sendEmptyMessage(1);
			// }
			// }
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.item0:
			// ���֧��
			orderxq = getIntent().getStringExtra("5");
			System.out.println("---------------xq-" + orderxq);
			// order_type = getIntent().getStringExtra("order_type");
			// System.out.println("order_type----------------"+order_type);

			Intent intent = new Intent(MyOrderZFActivity.this,
					TishiCarArchivesActivity.class);
			// intent.putExtra("order_type",order_type);
			intent.putExtra("order_no", recharge_no);
			intent.putExtra("order_yue", "order_yue");
			intent.putExtra("orderxq", orderxq);
			intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
			intent.putExtra("hd_title", getIntent().getStringExtra("title"));
			intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
			intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
			intent.putExtra("address", getIntent().getStringExtra("address"));
			intent.putExtra("id", getIntent().getStringExtra("id"));
			  intent.putExtra("real_name",getIntent().getStringExtra("real_name"));
			  intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
			startActivity(intent);
			finish();
			// Toast.makeText(MyOrderZFActivity.this,
			// "������������",Toast.LENGTH_SHORT).show();
			break;
		case R.id.item1:
			break;
		case R.id.item2:// ֧����
			// type = "3";
			loadzhidu(recharge_no);
			break;
		case R.id.item3:// ΢��
			// type = "5";
			loadweixinzf2(recharge_no);
			break;
		case R.id.item4:
			finish();
			break;

		default:
			break;
		}
	}

	private void getzhou() {
		// TODO Auto-generated method stub
		try {
			// recharge_no = recharge_no3;
			System.out.println("����===================" + recharge_no);
			System.out.println("����total_c===================" + total_c);
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/add_order_signup?user_id=" + user_id + "&user_name="
					+ user_name + "" + "&total_fee=" + total_c
					+ "&out_trade_no=" + recharge_no + "&payment_type=alipay",
			// add_order_signup?user_id=string&user_name=string&user_sign=string&buy_no=string&payment_id=string&is_invoice=string
			// &invoice_title=string
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								System.out
										.println("2================================="
												+ arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									JSONObject obj = object
											.getJSONObject("data");
									notify_url = obj.getString("notify_url");
									progress.CloseProgress();
									handler.sendEmptyMessage(1);
									// zhuangtai = true;
									finish();
								} else {
									progress.CloseProgress();
									Toast.makeText(MyOrderZFActivity.this,
											info, 200).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0:
				break;
			case 1:
				ali_pay();
				break;
			case 2:// ΢��֧��
				try {
					boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
					// System.err.println("isPaySupported=============="+isPaySupported);
					// Toast.makeText(MyOrderConfrimActivity.this, "��ȡ������...",
					// Toast.LENGTH_SHORT).show();
					String zhou = String.valueOf(isPaySupported);
					// Toast.makeText(MyOrderConfrimActivity.this, zhou,
					// Toast.LENGTH_SHORT).show();
					if (isPaySupported) {
						try {
							PayReq req = new PayReq();
							req.appId = Constant.APP_ID;
							req.partnerId = Constant.MCH_ID;
							req.prepayId = prepayid;// 7
							req.nonceStr = noncestr;// 3
							req.timeStamp = timestamp;// -1
							req.packageValue = package_;
							req.sign = sign;// -3

							// ��֧��֮ǰ�����Ӧ��û��ע�ᵽ΢�ţ�Ӧ���ȵ���IWXMsg.registerApp��Ӧ��ע�ᵽ΢��
							api.registerApp(Constant.APP_ID);
							flag = api.sendReq(req);
							System.out.println("֧��" + flag);
							// Toast.makeText(MyOrderConfrimActivity.this,
							// "֧��true", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					} else {
						// Toast.makeText(MyOrderConfrimActivity.this, "֧��NO",
						// Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				break;
			case 5:// ֧����
				PayResult payResult = new PayResult((String) msg.obj);

				// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(MyOrderZFActivity.this, "֧���ɹ�",
							Toast.LENGTH_SHORT).show();
					userloginqm();
					finish();
					String xq = getIntent().getStringExtra("5");
					System.out.println("---------------xq-" + xq);
					// if (xq != null) {
					// if (xq.equals("5")) {
					// MyOrderXqActivity.handler.sendEmptyMessage(1);
					// }
					// }
				} else {
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(MyOrderZFActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();
						finish();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(MyOrderZFActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();
						finish();

					}
				}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ֧����
	 * 
	 * @param payment_id
	 */
	private void loadzhidu(String recharge_no) {
		try {
			// recharge_no = recharge_no3;
			System.out.println("����===================" + recharge_no);
			System.out.println("����total_c===================" + total_c);
			AsyncHttp.get(RealmName.REALM_NAME_LL + "/payment_sign?user_id="
					+ user_id + "&user_name=" + user_name + "" + "&total_fee="
					+ total_c + "&out_trade_no=" + recharge_no
					+ "&payment_type=alipay", new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					super.onSuccess(arg0, arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						System.out.println("2================================="
								+ arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							notify_url = obj.getString("notify_url");
							progress.CloseProgress();
							handler.sendEmptyMessage(1);
							// zhuangtai = true;
							finish();
						} else {
							progress.CloseProgress();
							Toast.makeText(MyOrderZFActivity.this, info, 200)
									.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * ΢��֧��
	 * 
	 * @param payment_id
	 */
	private void loadweixinzf2(String recharge_no) {
		try {
			// recharge_no = recharge_no2;
			System.out.println("����===================" + recharge_no);
			String monney = String.valueOf(Double.parseDouble(total_c) * 100);
			// BigDecimal b = new BigDecimal(monney);
			// double monney_ll = b.setScale(1,
			// BigDecimal.ROUND_HALF_UP).doubleValue();
			// double monney_l = b.setScale(2,
			// BigDecimal.ROUND_HALF_UP).doubleValue();
			// System.out.println("����======monney_ll============="+monney_ll);
			System.out.println("����======monney=============" + monney);
			AsyncHttp.get(RealmName.REALM_NAME_LL + "/payment_sign?user_id="
					+ user_id + "&user_name=" + user_name + "" + "&total_fee="
					+ monney + "&out_trade_no=" + recharge_no
					+ "&payment_type=weixin",

			new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					super.onSuccess(arg0, arg1);
					try {

						JSONObject object = new JSONObject(arg1);
						System.out
								.println("weixin================================="
										+ arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							JSONObject jsonObject = object
									.getJSONObject("data");
							partner_id = jsonObject.getString("mch_id");
							prepayid = jsonObject.getString("prepay_id");
							noncestr = jsonObject.getString("nonce_str");
							timestamp = jsonObject.getString("timestamp");
							package_ = "Sign=WXPay";
							sign = jsonObject.getString("sign");
							System.out
									.println("weixin================================="
											+ package_);
							progress.CloseProgress();
							handler.sendEmptyMessage(2);
							// loadweixinzf3(recharge_no);
							zhuangtai = true;
							// finish();
							ll_zhifu_buju.setVisibility(View.INVISIBLE);
						} else {
							progress.CloseProgress();
							Toast.makeText(MyOrderZFActivity.this, info, 200)
									.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��¼ǩ��
	 */
	private void userloginqm() {
		try {
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			System.out.println("======11=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							login_sign = data.login_sign;
							loadguanggaoll(recharge_no, login_sign);
						} else {

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, MyOrderZFActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * ȷ�ϸ��� ���¶���
	 * 
	 * @param login_sign
	 * @param payment_id
	 */
	private void loadguanggaoll(String recharge_noll, String login_sign) {
		try {
			// recharge_no = recharge_noll;
			System.out.println("recharge_no================================="
					+ recharge_noll);
			System.out.println("login_sign================================="
					+ login_sign);
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/update_order_payment?user_id=" + user_id
					+ "&user_name=" + user_name + "" + "&trade_no="
					+ recharge_noll + "&sign=" + login_sign + "",

			new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					super.onSuccess(arg0, arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						System.out
								.println("���¶���================================="
										+ arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							progress.CloseProgress();
							// JSONObject jsonObject =
							// object.getJSONObject("data");
							// JSONArray jay =
							// jsonObject.getJSONArray("orders");
							// for (int j = 0; j < jay.length(); j++){
							// JSONObject objc= jay.getJSONObject(j);
							// // UserAddressData data = new UserAddressData();
							// // data.accept_name =
							// objc.getString("accept_name");
							// // data.province = objc.getString("province");
							// // data.city = objc.getString("city");
							// // data.area = objc.getString("area");
							// // data.user_mobile = objc.getString("mobile");
							// // data.user_address = objc.getString("address");
							// // data.order_no = objc.getString("order_no");
							// // data.add_time = objc.getString("add_time");
							// accept_name = objc.getString("accept_name");
							// province = objc.getString("province");
							// city = objc.getString("city");
							// area = objc.getString("area");
							// user_mobile = objc.getString("mobile");
							// user_address = objc.getString("address");
							// recharge_no = objc.getString("order_no");
							// datetime = objc.getString("add_time");
							// sell_price = objc.getString("payable_amount");
							// JSONArray jsonArray =
							// objc.getJSONArray("order_goods");
							// for (int i = 0; i < jsonArray.length(); i++) {
							// JSONObject json = jsonArray.getJSONObject(i);
							// article_id = json.getString("article_id");
							// // sell_price = json.getString("sell_price");
							// give_pension = json.getString("give_pension");
							// }
							// }

							// System.out.println("datetime================================="+datetime);
							// System.out.println("give_pension================================="+give_pension);

							// order_type =
							// getIntent().getStringExtra("order_type");
							// order_type = "1";//֧��״̬
							// System.out.println("order_type==============1==================="+order_type);
							
//							Toast.makeText(MyOrderZFActivity.this, info, 200).show();

							// �֧���ɹ�����ʾ����
							if (BaoMinTiShiActivity.huodong_zf_type.equals("1")) {
								BaoMinTiShiActivity.huodong_zf_type = "0";
								// huodong_type = "1";//�֧���ɹ�֮�����ò��ܼ�������
								Intent intent = new Intent(MyOrderZFActivity.this,BaoMinOKActivity.class);
								intent.putExtra("img_url", getIntent()
										.getStringExtra("img_url"));
								intent.putExtra("hd_title", getIntent()
										.getStringExtra("title"));
								intent.putExtra("start_time", getIntent()
										.getStringExtra("start_time"));
								intent.putExtra("end_time", getIntent()
										.getStringExtra("end_time"));
								intent.putExtra("address", getIntent()
										.getStringExtra("address"));
								intent.putExtra("trade_no", getIntent()
										.getStringExtra("order_no"));
								intent.putExtra("id", getIntent().getStringExtra("id"));
								  intent.putExtra("real_name",getIntent().getStringExtra("real_name"));
								  intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
								startActivity(intent);
								finish();
							} else {
								// Intent intent = new Intent(MyOrderZFActivity.this,ZhiFuOKActivity.class);
								// startActivity(intent);
								finish();
							}
						} else {
							progress.CloseProgress();
							finish();
							Toast.makeText(MyOrderZFActivity.this, info, 200).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("֧���쳣================================="
							+ arg0);
					System.out.println("֧���쳣================================="
							+ arg1);
					// Toast.makeText(MyOrderZFActivity.this, "֧���쳣",
					// 200).show();
					finish();
				}

			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void ali_pay() {
		try {

			//
			String orderInfo = getOrderInfo("�а�������Ʒ", "��Ʒ����", recharge_no);

			// �Զ�����RSA ǩ��
			String sign = sign(orderInfo);
			try {
				// �����sign ��URL����
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// �����ķ���֧���������淶�Ķ�����Ϣ
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
					+ getSignType();

			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					// ����PayTask ����
					PayTask alipay = new PayTask(MyOrderZFActivity.this);
					// ����֧���ӿڣ���ȡ֧�����
					String result = alipay.pay(payInfo);
					Message msg = new Message();
					msg.what = 5;
					msg.obj = result;
					handler.sendMessage(msg);
				}
			};

			// �����첽����
			Thread payThread = new Thread(payRunnable);
			payThread.start();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
	}

	/**
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Common.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	public String getOrderInfo(String subject, String body, String dingdan) {
		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + total_c + "\"";
		// orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";

		// �������첽֪ͨҳ��·��
		// orderInfo += "&notify_url=" + "\"" +
		// "http://183.62.138.31:1636/taobao/alipay_notify_url.aspx" + "\"";
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
		System.out.println("======notify_url=============" + notify_url);

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";
		System.out.println(orderInfo);
		return orderInfo;
	}

}
