//package com.hengyushop.demo.my;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.android.hengyu.pub.MyOrderllAdapter;
//import com.android.hengyu.web.DialogProgress;
//import com.android.hengyu.web.RealmName;
//import com.hengyushop.demo.at.AsyncHttp;
//import com.hengyushop.entity.MyOrderData;
//import com.hengyushop.entity.OrderBean;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.zams.www.R;
///**
// * 全部
// * @author Administrator
// *
// */
//public class AllorderActivity extends Fragment implements OnClickListener{
//
//	private Context context;
//	View parentView;
//
//	private TextView textView_chakan;
//	private TextView textView_pingjia;
//	private int pre = 1;
//	private int comNO = 1;
//	public static String chexi;
//	public static String chexing;
//	public static String chekuan;
//	LinearLayout no_data_no;
//	private ListView my_list;
//	public static Handler handler;
//	private int NO = 1;
//	private MyOrderllAdapter madapter;
//	public static String car_user_id;
//	public static String order_id;
//	private List<MyOrderData> list = new ArrayList<MyOrderData>();
//	private List<OrderBean> lists = new ArrayList<OrderBean>();
//	MyOrderData md;
//	OrderBean mb;
//	private DialogProgress progress;
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		if (null != parentView) {
//			ViewGroup parent = (ViewGroup) parentView.getParent();
//			if (null != parent) {
//				parent.removeView(parentView);
//			}
//		} else {
//			parentView = inflater.inflate(R.layout.activity_my_order, container,false);
//			progress = new DialogProgress(getActivity());
//			 initUI();
//			 loadWeather();
//		}
//
//		return parentView;
//	}
//
//	private void loadWeather() {
//		progress.CreateProgress();
////		System.out.println("=========1============"+id);//5897
//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+56+"" +
//				"&page_size=10&page_index=1&strwhere=payment_status=1&orderby=",
//				new AsyncHttpResponseHandler() {
//							@Override
//							public void onSuccess(int arg0, String arg1) {
//
//								super.onSuccess(arg0, arg1);
//								System.out.println("=========解析数据============");
//								try {
//									
//								JSONObject object = new JSONObject(arg1);
//								String status = object.getString("status");
//								if (status.equals("y")) {
//								JSONArray jsonArray = object.getJSONArray("data");
//								for (int i = 0; i < jsonArray.length(); i++) {
//								md = new MyOrderData();
//								JSONObject obj= jsonArray.getJSONObject(i);
//								md.setId(obj.getString("id"));
//								String order_goods = obj.getString("order_goods");
//								md.setList(new ArrayList<OrderBean>());
//								JSONArray ja = new JSONArray(order_goods);
//								try {
//								for (int j = 0; j < ja.length(); j++) {
//									JSONObject jo = ja.getJSONObject(j);
//									mb = new OrderBean();
//									mb.setImg_url(jo.getString("img_url"));
//									mb.setGoods_title(jo.getString("goods_title"));
//									mb.setGoods_price(jo.getString("goods_price"));
//									mb.setReal_price(jo.getString("real_price"));
//									mb.setQuantity(jo.getString("quantity"));
//									String zhouString  = mb.getGoods_title();
//									System.out.println("============="+zhouString);
//									md.getList().add(mb);
//								}
//								} catch (Exception e) {
//
//									e.printStackTrace();
//								}
//								list.add(md); 
//								}
//								progress.CloseProgress();
//						    	handler.sendEmptyMessage(3);
//								}else {
//									progress.CloseProgress();
//									no_data_no.setVisibility(View.VISIBLE);
//								}
//								
//								} catch (Exception e) {
//
//									e.printStackTrace();
//								}
//								System.out.println("========1===========");
//							}
//							
//							
//						}, null);
//	}
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		context = activity;
//
//	}
//	public void initUI() {
//		my_list = (ListView)parentView.findViewById(R.id.my_list);
//		  no_data_no = (LinearLayout) parentView.findViewById(R.id.no_data_no);
//		handler = new Handler() {
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 2:
//					
//					break;
//				case 3:
//								madapter = new MyOrderllAdapter(getActivity(), list,handler);
//								my_list.setAdapter(madapter);
//					 break;
//				}
//			}
//		};
//		
//	}
//	
//
//
//	private Intent getIntent() {
//
//		return null;
//	}
//
//	
//	@Override
//	public void onClick(View v) {
//
//		switch (v.getId()) {
//			default:
//				break;
//	}
//	
//	}
// }