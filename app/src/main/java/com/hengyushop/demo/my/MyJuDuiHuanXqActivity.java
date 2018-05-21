package com.hengyushop.demo.my;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyJuDuiHuanXqAdapter;
import com.android.hengyu.pub.MyJuTuanOrderXqAdapter;
import com.android.hengyu.pub.MyOrderXqAdapter;
import com.android.hengyu.pub.MyOrderllAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.DianPingActivity;
import com.hengyushop.entity.MyOrderData;
import com.hengyushop.entity.OrderBean;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.zams.www.R;

/**
 *
 * 订单详情
 *
 * @author Administrator
 *
 */
public class MyJuDuiHuanXqActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_fanhui, mImageView, mImageView1, mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private LinearLayout img_btn_order;
	String user_name, user_id;
	private Button btn_settle_accounts;
	LayoutInflater mLayoutInflater;
	private View btn_sms;
	private View btn_wx_friend;
	private ImageButton img_btn_tencent;
	private IWXAPI api;
	private ListView my_list;
	private PullToRefreshView refresh;
	String login_sign, order_no;
	private TextView tv_shanche, tv_fukuan, tv_queren_fukuan, tv_pingjia;
	LinearLayout ll_anliu;
	private String payment_status, express_status, status;
	int zhuangtai;
	// private List<MyOrderData> list = new ArrayList<MyOrderData>();
	// private List<OrderBean> lists = new ArrayList<OrderBean>();
	MyOrderData md;
	OrderBean mb;
	private MyJuDuiHuanXqAdapter mybAdapter;
	private int RUN_METHOD = -1;
	int len;
	private List<MyOrderData> list;
	List<OrderBean> lists;
	public static Handler handler;
	public static boolean teby = false;
	MyOrderData bean;
	@Override
	protected void onResume() {

		super.onResume();
		// TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
		// if (list.size() > 0) {
		// tv_geshu.setText(list.size());
		// }
		System.out.println("状态==============" + teby);
		// 余额支付更新
		if (teby) {
			userloginqm();
			finish();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_xq);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(MyJuDuiHuanXqActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		initUI();
		try {
			my_list = (ListView) findViewById(R.id.new_list);
			tv_shanche = (TextView) findViewById(R.id.tv_shanche);
			tv_fukuan = (TextView) findViewById(R.id.tv_fukuang);
			tv_queren_fukuan = (TextView) findViewById(R.id.tv_queren_fukuan);//
			tv_pingjia = (TextView) findViewById(R.id.tv_pingjia);//
			ll_anliu = (LinearLayout) findViewById(R.id.ll_anliu);//
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			tv_shanche.setOnClickListener(this);
			tv_fukuan.setOnClickListener(this);
			tv_queren_fukuan.setOnClickListener(this);
			tv_pingjia.setOnClickListener(this);

			System.out.println("zhou1----------");
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

			bean = (MyOrderData) getIntent().getSerializableExtra(
					"bean");
			list = new ArrayList<MyOrderData>();
			list.add(bean);
			order_no = list.get(0).getOrder_no();

			mybAdapter = new MyJuDuiHuanXqAdapter(MyJuDuiHuanXqActivity.this,
					list, handler);
			my_list.setAdapter(mybAdapter);
			payment_status = list.get(0).getPayment_status();
			System.out.println("payment_status=============" + payment_status);
			express_status = list.get(0).getExpress_status();
			System.out.println("express_status=============" + express_status);
			status = list.get(0).getStatus();
			System.out.println("status=============" + status);

			System.out.println("zhou3----------");
			if (payment_status.equals("1")) {
				System.out.println("待付款=============");
				ll_anliu.setVisibility(View.VISIBLE);
				tv_queren_fukuan.setVisibility(View.GONE);
				tv_pingjia.setVisibility(View.GONE);
				// tv_fukuan.setVisibility(View.VISIBLE);
				tv_shanche.setVisibility(View.VISIBLE);
				// tv_fukuan.setText("确认付款");
				zhuangtai = 2;
			} else if (payment_status.equals("2") && express_status.equals("1")) {
				System.out.println("待发货=============");
				ll_anliu.setVisibility(View.GONE);
				zhuangtai = 3;
			} else if (payment_status.equals("2") && express_status.equals("2")
					&& status.equals("2")) {
				ll_anliu.setVisibility(View.VISIBLE);
				tv_fukuan.setVisibility(View.GONE);
				tv_queren_fukuan.setVisibility(View.VISIBLE);
				tv_pingjia.setVisibility(View.GONE);
				tv_queren_fukuan.setText("确认收货");
				zhuangtai = 4;
			} else if (payment_status.equals("2") && express_status.equals("2")
					&& status.equals("3")) {
				System.out.println("已完成=============");
				ll_anliu.setVisibility(View.VISIBLE);
				tv_fukuan.setVisibility(View.GONE);
				tv_queren_fukuan.setVisibility(View.GONE);
				// tv_pingjia.setVisibility(View.VISIBLE);
				tv_shanche.setVisibility(View.VISIBLE);
				// tv_pingjia.setText("评价");
				zhuangtai = 5;
			}

			handler = new Handler() {
				public void dispatchMessage(Message msg) {
					switch (msg.what) {
						case 0:
							break;
						case 1:
							// System.out.println("======dialog1===============");
							// order_no = (String) msg.obj;
							// dialog();
							finish();
							break;
						// case 2:
						// System.out.println("======dialog2===============");
						// order_no = (String) msg.obj;
						// dialog2();
						// break;
						// case 3:
						// System.out.println("======dialog3===============");
						// order_no = (String) msg.obj;
						// // dialog3();
						// break;

						default:
							break;
					}
				}
			};

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void initUI() {

	}

	// handler = new Handler() {
	// public void dispatchMessage(Message msg) {
	// switch (msg.what) {
	// case 0:
	// break;
	// // case 1:
	// // System.out.println("======dialog1===============");
	// // order_no = (String) msg.obj;
	// // dialog();
	// // break;
	// // case 2:
	// // System.out.println("======dialog2===============");
	// // order_no = (String) msg.obj;
	// // dialog2();
	// // break;
	// // case 3:
	// // System.out.println("======dialog3===============");
	// // order_no = (String) msg.obj;
	// //// dialog3();
	// // break;
	//
	// default:
	// break;
	// }
	// };
	// };

	protected void dialog2() {
		AlertDialog.Builder builder = new Builder(MyJuDuiHuanXqActivity.this);
		builder.setMessage("是否确定取消订单?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok2(order_no);
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

	protected void dialog3() {
		AlertDialog.Builder builder = new Builder(MyJuDuiHuanXqActivity.this);
		builder.setMessage("是否确定删除订单?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok3(order_no);
				finish();
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

	/**
	 * 删除订单
	 *
	 * @param order_no
	 * @param payment_id
	 */
	public void fukuanok3(String order_no2) {
		progress.CreateProgress();
		order_no = order_no2;
		System.out.println("order_no================================="
				+ order_no);
		String login_sign = spPreferences.getString("login_sign", "");
		System.out.println("login_sign================================="
				+ login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/delete_order?user_id="
						+ user_id + "&user_name=" + user_name + "" + "&trade_no="
						+ order_no + "&sign=" + login_sign + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out
									.println("取消订单================================="
											+ arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanXqActivity.this,
										info, 200).show();
								// Intent intent = new Intent(getActivity(),
								// MyOrderConfrimActivity.class);
								// intent.putExtra("order_no",order_no);
								// intent.putExtra("id","1");
								// startActivity(intent);
							} else {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanXqActivity.this,
										info, 200).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, MyJuDuiHuanXqActivity.this);

	}

	/**
	 * 取消订单
	 *
	 * @param order_no
	 * @param payment_id
	 */
	public void fukuanok2(String order_no2) {
		progress.CreateProgress();
		order_no = order_no2;
		System.out.println("order_no================================="
				+ order_no);
		String login_sign = spPreferences.getString("login_sign", "");
		System.out.println("login_sign================================="
				+ login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/update_order_cancel?user_id="
						+ user_id + "&user_name=" + user_name + "" + "&trade_no="
						+ order_no + "&sign=" + login_sign + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out
									.println("取消订单================================="
											+ arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanXqActivity.this,
										info, 200).show();
							} else {
								progress.CloseProgress();
								Toast.makeText(MyJuDuiHuanXqActivity.this,
										info, 200).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, MyJuDuiHuanXqActivity.this);

	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.tv_fukuang:
				String total_c = MyOrderXqAdapter.heji_zongjia;
				Intent intent = new Intent(MyJuDuiHuanXqActivity.this,
						MyOrderZFActivity.class);
				intent.putExtra("order_no", order_no);
				intent.putExtra("total_c", total_c);
				intent.putExtra("5", "5");
				startActivity(intent);
				// finish();
				break;
			case R.id.tv_queren_fukuan:
				Intent intent1 = new Intent(MyJuDuiHuanXqActivity.this,
						TishiCarArchivesActivity.class);
				intent1.putExtra("order_no", order_no);
				// intent1.putExtra("order_yue","order_yue");
				// intent1.putExtra("orderxq","orderxq");
				startActivity(intent1);
				break;
			case R.id.tv_pingjia:
				Intent intent2 = new Intent(MyJuDuiHuanXqActivity.this,
						DianPingActivity.class);
				// intent2.putExtra("article_id",list.get(position).getList().get(p).getArticle_id());
				startActivity(intent2);
				break;
			case R.id.tv_shanche:
				dialog3();
				break;
			default:
				break;
		}
	}

	/**
	 * 获取登录签名
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
							loadguanggaoll(order_no, login_sign);
						} else {
						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, MyJuDuiHuanXqActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 更新订单
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
										.println("更新订单================================="
												+ arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									teby = false;
									finish();
									Toast.makeText(MyJuDuiHuanXqActivity.this, info,
											200).show();
								} else {
									progress.CloseProgress();
									teby = false;
									Toast.makeText(MyJuDuiHuanXqActivity.this, info,
											200).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							super.onFailure(arg0, arg1);
							System.out.println("11================================="
									+ arg0);
							System.out.println("22================================="
									+ arg1);
							Toast.makeText(MyJuDuiHuanXqActivity.this, "网络超时异常", 200)
									.show();
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
