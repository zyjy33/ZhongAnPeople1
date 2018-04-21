package com.hengyushop.demo.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyOrderllAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.MyOrderData;
import com.hengyushop.entity.OrderBean;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 待付款
 *
 * @author Administrator
 *
 */
public class CompleteyesFragment extends Fragment implements OnClickListener {

	private Context context;
	View parentView;

	private TextView textView_chakan;
	private TextView textView_pingjia;
	private int pre = 1;
	private int comNO = 1;
	public static String chexi;
	public static String chexing;
	public static String chekuan;
	LinearLayout no_data_no;
	private ListView my_list;
	private int NO = 1;
	private MyOrderllAdapter madapter;
	public static String car_user_id;
	public static String order_id;
	private List<MyOrderData> list = new ArrayList<MyOrderData>();
	private List<OrderBean> lists = new ArrayList<OrderBean>();
	MyOrderData md;
	OrderBean mb;
	private DialogProgress progress;
	String user_name, user_id, login_sign, order_no;
	private SharedPreferences spPreferences;
	private PullToRefreshView refresh;
	private int RUN_METHOD = -1;
	String recharge_no, total_c;
	int len;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;

	}

	// private Intent getIntent() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (null != parentView) {
			ViewGroup parent = (ViewGroup) parentView.getParent();
			if (null != parent) {
				parent.removeView(parentView);
			}
		} else {
			parentView = inflater.inflate(R.layout.activity_my_order,
					container, false);
			progress = new DialogProgress(getActivity());
			spPreferences = getActivity().getSharedPreferences("longuserset",
					Context.MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			user_id = spPreferences.getString("user_id", "");
			initUI();
			// loadWeather();

		}

		return parentView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		load_list(true);
	}

	public void initUI() {

		refresh = (PullToRefreshView) parentView.findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		my_list = (ListView) parentView.findViewById(R.id.new_list);
		no_data_no = (LinearLayout) parentView.findViewById(R.id.no_data_no);

	}

	OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
		}
	};

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					// madapter = new MyOrderllAdapter(getActivity(), list,handler);
					// my_list.setAdapter(madapter);

					my_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							// TODO Auto-generated method stub
							String id = lists.get(arg2).getId();
							System.out.println("=====================" + id);
							Intent intent = new Intent(getActivity(),
									MyOrderXqActivity.class);
							intent.putExtra("id", id);
							startActivity(intent);
						}
					});
					break;
				case 1:
					order_no = (String) msg.obj;
					dialog();
					break;
				case 2:
					order_no = (String) msg.obj;
					dialog2();
					break;
				// case 3:
				// System.out.println("======dialog3===============");
				// order_no = (String) msg.obj;
				// // dialog3();
				// break;

				default:
					break;
			}
		};
	};

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("是否要确认付款?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok(order_no);
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

	protected void dialog2() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("是否确定取消订单?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok(order_no);
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

	// protected void dialog3() {
	// AlertDialog.Builder builder = new Builder(getActivity());
	// builder.setMessage("是否确定删除订单?");
	// builder.setTitle("提示");
	// builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// fukuanok3(order_no);
	// }
	// });
	//
	// builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	// });
	//
	// builder.create().show();
	// }

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};

	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						if (RUN_METHOD == 0) {
							System.out.println("RUN_METHOD1========="
									+ RUN_METHOD);
							load_list2(false);
						} else {
							System.out.println("RUN_METHOD2========="
									+ RUN_METHOD);
							load_list(false);
						}
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	/**
	 * 第1个列表数据解析
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag) {

		try {

			RUN_METHOD = 1;
			list = new ArrayList<MyOrderData>();
			if (flag) {
				// 计数和容器清零
				CURRENT_NUM = 0;
				list = new ArrayList<MyOrderData>();
			}
			progress.CreateProgress();
			// System.out.println("=========1============"+id);//5897
			AsyncHttp
					.get(RealmName.REALM_NAME_LL
									+ "/get_order_page_size_list?user_id="
									+ user_id
									+ ""
									+ "&page_size=10&page_index=1&strwhere=payment_status=1&orderby=",
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {
									// TODO Auto-generated method stub
									super.onSuccess(arg0, arg1);
									System.out
											.println("=========全部============"
													+ arg1);
									try {

										JSONObject object = new JSONObject(arg1);
										String status = object
												.getString("status");
										if (status.equals("y")) {
											JSONArray jsonArray = object
													.getJSONArray("data");
											len = jsonArray.length();
											for (int i = 0; i < jsonArray
													.length(); i++) {
												md = new MyOrderData();
												JSONObject obj = jsonArray
														.getJSONObject(i);
												md.setId(obj.getString("id"));
												md.setOrder_no(obj
														.getString("order_no"));
												md.setPayment_status(obj
														.getString("payment_status"));
												String order_goods = obj
														.getString("order_goods");
												md.setList(new ArrayList<OrderBean>());
												JSONArray ja = new JSONArray(
														order_goods);
												try {
													for (int j = 0; j < ja
															.length(); j++) {
														JSONObject jo = ja
																.getJSONObject(j);
														mb = new OrderBean();
														mb.setImg_url(jo
																.getString("img_url"));
														mb.setGoods_title(jo
																.getString("goods_title"));
														mb.setSell_price(jo
																.getString("sell_price"));
														mb.setMarket_price(jo
																.getString("market_price"));
														mb.setReal_price(jo
																.getString("real_price"));
														mb.setQuantity(jo
																.getInt("quantity"));
														String zhouString = mb
																.getGoods_title();
														System.out
																.println("============="
																		+ zhouString);
														md.getList().add(mb);
													}
												} catch (Exception e) {
													// TODO: handle exception
													e.printStackTrace();
												}
												list.add(md);
											}
											progress.CloseProgress();
											refresh.setVisibility(View.VISIBLE);
										} else {
											refresh.setVisibility(View.GONE);
											progress.CloseProgress();
											no_data_no
													.setVisibility(View.VISIBLE);
										}
										Message msg = new Message();
										msg.what = 0;
										msg.obj = list;
										handler.sendMessage(msg);

										if (len != 0) {
											// CURRENT_NUM = CURRENT_NUM +
											// VIEW_NUM;
											CURRENT_NUM = 1;
										}

									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
									System.out.println("========1===========");
								}

							}, getActivity());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 第2个列表数据解析
	 */
	private void load_list2(boolean flag) {
		progress.CreateProgress();
		list = new ArrayList<MyOrderData>();
		if (flag) {
			// 计数和容器清零
			CURRENT_NUM = 0;
			list = new ArrayList<MyOrderData>();
		}
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
								+ "/get_order_page_size_list?user_id="
								+ user_id
								+ ""
								+ "&page_size=10&page_index=1&strwhere=payment_status=1&orderby=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=========全部============"
										+ arg1);
								try {

									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									if (status.equals("y")) {
										JSONArray jsonArray = object
												.getJSONArray("data");
										len = jsonArray.length();
										for (int i = 0; i < jsonArray.length(); i++) {
											md = new MyOrderData();
											JSONObject obj = jsonArray
													.getJSONObject(i);
											md.setId(obj.getString("id"));
											md.setOrder_no(obj
													.getString("order_no"));
											String order_goods = obj
													.getString("order_goods");
											md.setList(new ArrayList<OrderBean>());
											JSONArray ja = new JSONArray(
													order_goods);
											try {
												for (int j = 0; j < ja.length(); j++) {
													JSONObject jo = ja
															.getJSONObject(j);
													mb = new OrderBean();
													mb.setImg_url(jo
															.getString("img_url"));
													mb.setGoods_title(jo
															.getString("goods_title"));
													mb.setSell_price(jo
															.getString("sell_price"));
													mb.setMarket_price(jo
															.getString("market_price"));
													mb.setReal_price(jo
															.getString("real_price"));
													mb.setQuantity(jo
															.getInt("quantity"));
													String zhouString = mb
															.getGoods_title();
													System.out
															.println("============="
																	+ zhouString);
													md.getList().add(mb);
												}
											} catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
											}
											list.add(md);
										}
										progress.CloseProgress();
										// handler.sendEmptyMessage(3);
										refresh.setVisibility(View.VISIBLE);
									} else {
										refresh.setVisibility(View.GONE);
										progress.CloseProgress();
										no_data_no.setVisibility(View.VISIBLE);
									}
									Message msg = new Message();
									msg.what = 0;
									msg.obj = list;
									handler.sendMessage(msg);
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}

						}, getActivity());
	}

	/**
	 * 确认付款
	 *
	 * @param order_no
	 * @param payment_id
	 */
	public void fukuanok(String order_no2) {
		progress.CreateProgress();
		order_no = order_no2;
		System.out.println("order_no================================="
				+ order_no);
		String login_sign = spPreferences.getString("login_sign", "");
		System.out.println("login_sign================================="
				+ login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_order_payment?user_id=" + user_id + "&user_name="
				+ user_name + "" + "&order_no=" + order_no + "&sign="
				+ login_sign + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					System.out.println("确认付款================================="
							+ arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					if (status.equals("y")) {
						// JSONObject obj = object.getJSONObject("data");
						// System.out.println("0================================="+data.recharge_no
						// );
						progress.CloseProgress();
						Toast.makeText(getActivity(), info, 200).show();
						// Intent intent = new Intent(getActivity(),
						// MyOrderConfrimActivity.class);
						// intent.putExtra("order_no",order_no);
						// intent.putExtra("id","110");
						// startActivity(intent);
						// zhifu();
					} else {
						progress.CloseProgress();
						Toast.makeText(getActivity(), info, 200).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}, getActivity());

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
						+ user_id + "&user_name=" + user_name + "" + "&order_no="
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
								Toast.makeText(getActivity(), info, 200).show();
								// Intent intent = new Intent(getActivity(),
								// MyOrderConfrimActivity.class);
								// intent.putExtra("order_no",order_no);
								// intent.putExtra("id","1");
								// startActivity(intent);
							} else {
								progress.CloseProgress();
								Toast.makeText(getActivity(), info, 200).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, getActivity());

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
						+ user_id + "&user_name=" + user_name + "" + "&order_no="
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
								Toast.makeText(getActivity(), info, 200).show();
								// Intent intent = new Intent(getActivity(),
								// MyOrderConfrimActivity.class);
								// intent.putExtra("order_no",order_no);
								// intent.putExtra("id","1");
								// startActivity(intent);
							} else {
								progress.CloseProgress();
								Toast.makeText(getActivity(), info, 200).show();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, getActivity());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			default:
				break;
		}

	}

}