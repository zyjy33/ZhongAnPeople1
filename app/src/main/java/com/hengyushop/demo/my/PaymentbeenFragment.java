package com.hengyushop.demo.my;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.R.id;
import com.zams.www.R.layout;
/**
 * 已付款
 * @author Administrator
 *
 */
public class PaymentbeenFragment extends Fragment implements OnClickListener{

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
	String user_name, user_id,login_sign,order_no;
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
			parentView = inflater.inflate(R.layout.activity_my_order, container,false);
			progress = new DialogProgress(getActivity());
			spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			user_id = spPreferences.getString("user_id", "");
			login_sign = spPreferences.getString("login_sign", "");
			 initUI();
//			 loadWeather();
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
		my_list = (ListView)parentView.findViewById(R.id.new_list);
		  no_data_no = (LinearLayout) parentView.findViewById(R.id.no_data_no);
		
	}
	
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
//			madapter = new MyOrderllAdapter(getActivity(), list,handler);
//			my_list.setAdapter(madapter);
            break;
			case 3:
				order_no = (String) msg.obj;
				dialog3();
				break;
			default:
				break;
			}
		};
	};
	
	protected void dialog3() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("是否确定删除订单?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				fukuanok3(order_no);
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
							System.out.println("RUN_METHOD1========="+ RUN_METHOD);
							load_list2(false);
						} else {
							System.out.println("RUN_METHOD2========="+ RUN_METHOD);
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
//		System.out.println("=========1============"+id);//5897
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=10&page_index=1&strwhere=payment_status=2&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=========全部============"+arg1);
								try {
									
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
								md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								md.setOrder_no(obj.getString("order_no"));
								md.setPayment_status(obj.getString("payment_status"));
								String order_goods = obj.getString("order_goods");
								md.setList(new ArrayList<OrderBean>());
								JSONArray ja = new JSONArray(order_goods);
								try {
								for (int j = 0; j < ja.length(); j++) {
									JSONObject jo = ja.getJSONObject(j);
									mb = new OrderBean();
									mb.setImg_url(jo.getString("img_url"));
									mb.setGoods_title(jo.getString("goods_title"));
									mb.setSell_price(jo.getString("sell_price"));
									mb.setMarket_price(jo.getString("market_price"));
									mb.setReal_price(jo.getString("real_price"));
									mb.setQuantity(jo.getInt("quantity"));
									String zhouString  = mb.getGoods_title();
									System.out.println("============="+zhouString);
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
								}else {
									refresh.setVisibility(View.GONE);
									progress.CloseProgress();
									no_data_no.setVisibility(View.VISIBLE);
								}
								Message msg = new Message();
								msg.what = 0;
								msg.obj = list;
								handler.sendMessage(msg);
								if (len != 0) {
//									CURRENT_NUM = CURRENT_NUM + VIEW_NUM;
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
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=10&page_index=1&strwhere=payment_status=2&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=========全部============"+arg1);
								try {
									
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
								md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								md.setOrder_no(obj.getString("order_no"));
								md.setPayment_status(obj.getString("payment_status"));
								String order_goods = obj.getString("order_goods");
								md.setList(new ArrayList<OrderBean>());
								JSONArray ja = new JSONArray(order_goods);
								try {
								for (int j = 0; j < ja.length(); j++) {
									JSONObject jo = ja.getJSONObject(j);
									mb = new OrderBean();
									mb.setImg_url(jo.getString("img_url"));
									mb.setGoods_title(jo.getString("goods_title"));
									mb.setSell_price(jo.getString("sell_price"));
									mb.setMarket_price(jo.getString("market_price"));
									mb.setReal_price(jo.getString("real_price"));
									mb.setQuantity(jo.getInt("quantity"));
									String zhouString  = mb.getGoods_title();
									System.out.println("============="+zhouString);
									md.getList().add(mb);
								}
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								list.add(md); 
								}
								progress.CloseProgress();
//						    	handler.sendEmptyMessage(3);
						    	refresh.setVisibility(View.VISIBLE);
								}else {
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
	 * @param payment_id 
	 */
	public void fukuanok() {
			progress.CreateProgress();	
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_order_payment?user_id="+user_id+"&user_name="+user_name+"" +
				"&recharge_no="+order_no+"&sign="+login_sign+"",
						
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("确认付款================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
//							    	JSONObject obj = object.getJSONObject("data");
//									System.out.println("0================================="+data.recharge_no );
									  progress.CloseProgress();
									  Toast.makeText(getActivity(), info, 200).show();
							    }else {
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
	 * @param order_no 
	 * @param payment_id 
	 */
	public void fukuanok3(String order_no2) {
			progress.CreateProgress();	
			order_no = order_no2;
			System.out.println("order_no================================="+order_no);
			String login_sign = spPreferences.getString("login_sign", "");
			System.out.println("login_sign================================="+login_sign);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/delete_order?user_id="+user_id+"&user_name="+user_name+"" +
				"&order_no="+order_no+"&sign="+login_sign+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("取消订单================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
									  progress.CloseProgress();
									  Toast.makeText(getActivity(), info, 200).show();
//									  Intent intent = new Intent(getActivity(), MyOrderConfrimActivity.class);
//									  intent.putExtra("order_no",order_no);
//									  intent.putExtra("id","1");
//									  startActivity(intent);
							    }else {
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;

	}
	


	private Intent getIntent() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			default:
				break;
	}
	
	}
	
	/**
	 * 我的订单
	 * 
	 * @author Administrator
	 * 
	 */
//	public class MyOrderllAdapter extends BaseAdapter {
//		private Context context;
//		private Intent intent;
//		private List<MyOrderData> list;
//		private LayoutInflater inflater;
//		private Activity act;
//		private Handler handler;
//
//		public MyOrderllAdapter(Context context, List<MyOrderData> list, Handler handler) {
//			this.list = list;
//			this.context = context;
//			this.handler = handler;
//			this.inflater = LayoutInflater.from(context);
//		}
//
//		@Override
//		public int getCount() {
//			if (list.size() < 1) {
//
//				return 0;
//			} else {
//
//				return list.size();
//			}
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		private final class ViewHolder {
//			TextView tv_goods_title;
//			TextView tv_market_price;
//			TextView real_price;
//			TextView quantity;
//			ImageView tupian;//
//			TextView lv_jijian;
//			TextView tv_kukuang;
//			TextView tv_quxiao;//
//			TextView shanchu;//删除
//			LinearLayout lv_dingdanxq;
//		}
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup patent) {
//			// TODO Auto-generated method stub
//			ViewHolder holder = null;
//			holder = new ViewHolder();
//			//ViewGroup zhouGroup = null;
//			if (convertView == null) {
//			holder = new ViewHolder();
//			
//			convertView = inflater.inflate(R.layout.itme_my_order, null);
//			LinearLayout addview = (LinearLayout) convertView.findViewById(id.gwc_addview);
//			
//			holder.lv_jijian = (TextView) convertView.findViewById(R.id.lv_jijian);//
//			holder.tv_kukuang = (TextView) convertView.findViewById(R.id.tv_kukuang);
//			holder.tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);//
////			holder.chekuan = (TextView) convertView.findViewById(R.id.tv_chekuan);//
////			holder.jijian = (TextView) convertView.findViewById(R.id.tv_jijian);//
////			holder.hejijiaguo = (TextView) convertView.findViewById(R.id.tv_hejijiaguo);//
//			
//			holder.shanchu = (TextView) convertView.findViewById(R.id.tv_shanche);// 删除
//
////			holder.chepaihao.setText(list.get(position).getPlate_no());// 
//			
//			
//			/**
//			 * 删除
//			 */
//			holder.shanchu.setOnClickListener(new OnClickListener() {
//	
//				@Override
//				public void onClick(View v) {
//					
//				}
//			});
//			
//			addview.removeAllViews();
//
//			for (int i = 0; i < list.get(position).getList().size(); i++) {
//				// ViewHolder holder = null;
//				holder = new ViewHolder();
//				View vi = LayoutInflater.from(context).inflate(layout.itme_my_order_zhuti, null);
//				
//				holder.tv_goods_title = (TextView) vi.findViewById(R.id.tv_goods_title);//
//				holder.tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
//				holder.tv_market_price = (TextView) vi.findViewById(R.id.tv_market_price);
//				holder.real_price = (TextView) vi.findViewById(R.id.tv_real_price);
//				holder.quantity = (TextView) vi.findViewById(R.id.tv_quantity);
//				holder.lv_dingdanxq = (LinearLayout) vi.findViewById(R.id.lv_dingdanxq);
//				//holder.shanchu = (TextView) vi.findViewById(R.id.tv_shanche);// 删除
//				
////				holder.tv_goods_title.setText(list.get(position).getList().get(i).getSell_price());
//				holder.tv_market_price.setText(list.get(position).getList().get(i).getMarket_price());
//				holder.real_price.setText(list.get(position).getList().get(i).getSell_price());
//				holder.quantity.setText("X"+list.get(position).getList().get(i).getQuantity());
//				holder.real_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
//				ImageLoader imageLoader=ImageLoader.getInstance();
//				imageLoader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getList().get(i).getImg_url(), holder.tupian);
//				
//				double a= 0;
//				for (int w = 0; w < list.size(); w++) {
//					String price = list.get(w).getList().get(i).getSell_price();
//					int number = list.get(w).getList().get(i).getQuantity();
//					BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
//					//保留2位小数
//					double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
////					System.out.println("价格是多少0============="+total_c);
//					a += total_c_ll;
//				}
//				String total_c = Double.toString(a);
//				
//				Message message = new Message();
//				message.what = 3;
//				message.obj = total_c;
//				handler.sendMessage(message);
//				
//				
//				addview.addView(vi);
//				convertView.setTag(holder);  
//				
//				
//				
//				/**
//				 * 订单详情
//				 */
//				holder.lv_dingdanxq.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(context,MyOrderXqActivity.class);
//						intent.putExtra("id", list.get(position).getId());
////						intent.putExtra("list", list.get(position).getList().get(i));
//						context.startActivity(intent);
//					}
//				});
//				
//			}
//			}else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			
//			
//			return convertView;
//			
//		}
//	}
}