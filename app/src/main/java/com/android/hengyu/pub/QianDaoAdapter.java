package com.android.hengyu.pub;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hengyushop.entity.XsgyListData;
import com.zams.www.R;

import java.util.ArrayList;

public class QianDaoAdapter extends BaseAdapter {

	private ArrayList<XsgyListData> list;
	private Context context;
	public static AQuery aQuery;
	private Handler handler;
	Button tv_total;
	public static String trade_no,article_id;
	public QianDaoAdapter(ArrayList<XsgyListData> list,
						  Context context, Handler handler) {

		this.context = context;
		this.handler = handler;
		this.list = list;
		aQuery = new AQuery(context);
	}

	//	public void putData(ArrayList<XsgyListData> list) {
	//		this.list = list;
	//		this.notifyDataSetChanged();
	//	}

	public int getCount() {

		return list.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			// convertView =
			// LinearLayout.inflate(context,R.layout.listitem_xsgy, null);
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_qiandao, null);
		}

		//		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_activity_name);
		TextView tv_ware_time = (TextView) convertView.findViewById(R.id.tv_zbf_name);
		TextView tv_price = (TextView) convertView.findViewById(R.id.tv_bm_time);
		tv_total = (Button) convertView.findViewById(R.id.btn_holdr);

		tv_name.setText(list.get(position).company_name);
		tv_ware_time.setText(list.get(position).article_title);
		tv_price.setText(list.get(position).payment_time);
		//		 loadzhifuweix(list.get(position).article_id);
		tv_total.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				System.out.println("list.get(position).trade_no================================="+list.get(position).trade_no);
				//					baomingqueren(list.get(position).trade_no);
				//			    	Intent intent = new Intent(context,TishiBaoMinQianDaoActivity.class);
				//			    	intent.putExtra("bianma",list.get(position).trade_no);
				//			    	intent.putExtra("qiandao","qiandao");
				////			    	intent.putExtra("sp_sys", getIntent().getStringExtra("sp_sys"));
				//			    	context.startActivity(intent);
				trade_no = list.get(position).trade_no;
				article_id = list.get(position).article_id;
				Message msg = new Message();
				msg.what = 1;
				//					msg.obj = list.get(position).trade_no;
				handler.sendMessage(msg);
			}
		});

		//		aQuery.id(image).image(
		//				RealmName.REALM_NAME_HTTP + list.get(position).img_url);

		return convertView;
	}

	/**
	 * 签到查询
	 * @param article_id
	 */
	//	private void loadzhifuweix(String article_id) {
	//		try {
	////			String id = getIntent().getStringExtra("id");
	////			String mobile = getIntent().getStringExtra("mobile");
	//		   AsyncHttp.get(RealmName.REALM_NAME_LL
	//				+ "/exists_order_signup?mobile="+QianDaoListActivity.shoujihao+"&article_id="+article_id+"",
	//				new AsyncHttpResponseHandler() {
	//					@Override
	//					public void onSuccess(int arg0, String arg1) {
	//						super.onSuccess(arg0, arg1);
	//						try {
	//							JSONObject object = new JSONObject(arg1);
	//							System.out.println("签到查询================================="+arg1);
	//							  String status = object.getString("status");
	////							    String info = object.getString("info");
	//							    if (status.equals("y")) {
	//							    	 tv_total.setText("签到成功");
	//							    	 tv_total.setVisibility(View.VISIBLE);
	////							    	   Toast.makeText(DianZiPiaoActivity.this, info, 200).show();
	//							    }else {
	//							    	 tv_total.setText("立即签到");
	//							    	 tv_total.setVisibility(View.VISIBLE);
	////									Toast.makeText(DianZiPiaoActivity.this, info, 200).show();
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
	//						System.out.println("异常================================="+arg1);
	////						Toast.makeText(DianZiPiaoActivity.this, "异常", 200).show();
	//					}
	//				}, null);
	//
	//		} catch (Exception e) {
	//
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 报名确认
	 * @param trade_no
	 */
	//	private void baomingqueren(String trade_no) {
	//		try {
	//		   AsyncHttp.get(RealmName.REALM_NAME_LL
	//				+ "/signup_award_confirm?trade_no="+trade_no+"&express_status="+2+"",
	//				new AsyncHttpResponseHandler() {
	//					@Override
	//					public void onSuccess(int arg0, String arg1) {
	//						super.onSuccess(arg0, arg1);
	//						try {
	//							JSONObject object = new JSONObject(arg1);
	//							System.out.println("报名确认================================="+arg1);
	//							  String status = object.getString("status");
	//							    String info = object.getString("info");
	//							    if (status.equals("y")) {
	//							    	   Toast.makeText(context, "签到成功", 200).show();
	//							    	   tv_total.setText("签到成功");
	//
	//							    }else {
	//									Toast.makeText(context, info, 200).show();
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
	//						System.out.println("异常================================="+arg1);
	////						Toast.makeText(DianZiPiaoActivity.this, "网络超时异常", 200).show();
	//					}
	//				}, null);
	//
	//		} catch (Exception e) {
	//
	//			e.printStackTrace();
	//		}
	//	}
}
