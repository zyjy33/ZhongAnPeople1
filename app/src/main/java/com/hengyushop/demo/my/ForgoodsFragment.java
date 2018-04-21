package com.hengyushop.demo.my;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 待收货
 *
 * @author Administrator
 *
 */
public class ForgoodsFragment extends Fragment implements OnClickListener {

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
	String user_name, user_id;
	private SharedPreferences spPreferences;
	private PullToRefreshView refresh;
	private int RUN_METHOD = -1;
	int len;

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
		// load_list(true);
	}

	public void initUI() {
		refresh = (PullToRefreshView) parentView.findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		my_list = (ListView) parentView.findViewById(R.id.new_list);
		no_data_no = (LinearLayout) parentView.findViewById(R.id.no_data_no);

	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					// madapter = new MyOrderllAdapter(getActivity(), list,handler);
					// my_list.setAdapter(madapter);
					break;

				default:
					break;
			}
		};
	};

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
									+ "&page_size=10&page_index=1&strwhere=payment_status = 2 and express_status = 2&orderby=",
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
								+ "&page_size=10&page_index=1&strwhere=payment_status = 2 and express_status = 2&orderby=",
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
											md.setPayment_status(obj
													.getString("payment_status"));
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

	private void loadWeather() {
		progress.CreateProgress();
		// System.out.println("=========1============"+id);//5897
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
								+ "/get_order_page_size_list?user_id="
								+ user_id
								+ ""
								+ "&page_size=10&page_index=1&strwhere=express_status=2&orderby=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=========待收货============"
										+ arg1);
								try {
									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									if (status.equals("y")) {
										JSONArray jsonArray = object
												.getJSONArray("data");
										for (int i = 0; i < jsonArray.length(); i++) {
											md = new MyOrderData();
											JSONObject obj = jsonArray
													.getJSONObject(i);
											md.setId(obj.getString("id"));
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
										handler.sendEmptyMessage(3);
									} else {
										progress.CloseProgress();
										no_data_no.setVisibility(View.VISIBLE);
									}

								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								System.out.println("========1===========");
							}

						}, null);
	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// }
	//
	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	// context = activity;
	//
	// }
	//
	//
	//
	//
	// private Intent getIntent() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			default:
				break;
		}

	}
}