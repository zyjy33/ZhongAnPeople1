package com.android.hengyu.pub;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.activity.ZhongAnMSActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.JuTuanGouData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ZamsHuoDong2Adapter extends BaseAdapter {

	private Context mContext;
	private List<JuTuanGouData> List;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	public static boolean type = false;
	java.util.Date now_1;
	java.util.Date date_1;
	String strUrlone, user_name;
	public ZamsHuoDong2Adapter(Context context, List<JuTuanGouData> list) {
		try {

			this.List = list;
			this.mContext = context;
			mInflater = LayoutInflater.from(context);
			mAq = new AQuery(context);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void putData(ArrayList<JuTuanGouData> List) {
		this.List = List;
		//		this.notifyDataSetChanged();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return List.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return List.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.activity_huodong_item, null);//activity_zams_item
				holder.img = (ImageView) convertView.findViewById(R.id.ll_tupian);
				holder.tv_titel = (TextView) convertView
						.findViewById(R.id.tv_titel);
				holder.tv_category_title = (TextView) convertView
						.findViewById(R.id.tv_category_title);
				// holder.tv_price = (TextView)
				// convertView.findViewById(R.id.tv_price);
				holder.tv_groupon_price = (TextView) convertView
						.findViewById(R.id.tv_groupon_price);
				holder.tv_dizhi = (TextView) convertView
						.findViewById(R.id.tv_dizhi);
				holder.tv_dizhi_2 = (TextView) convertView.findViewById(R.id.tv_dizhi_2);
				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				holder.tv_ent = (TextView) convertView.findViewById(R.id.tv_ent);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			/**
			 * 类别选项
			 */
			// System.out.println("List======================"+List.size());

			System.out.println("个数1======================" + position);
			holder.tv_titel.setText(List.get(position).getCompany_name());
			holder.tv_category_title.setText(List.get(position).getTitle());
			holder.tv_time.setText(List.get(position).getStart_time().subSequence(0, 16)
					+ "—"
					+ List.get(position).getEnd_time().subSequence(0, 16));
			System.out.println("List.get(position).getCategory_title()======================"+ List.get(position).getCategory_title());
			if (List.get(position).getCategory_title() != null) {
				holder.tv_dizhi.setText(List.get(position).getCategory_title());
			} else {
				holder.tv_dizhi.setVisibility(View.GONE);
			}
			mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+ List.get(position).getImg_url());
			type = true;
			System.out.println("Sell_price======================" + List.get(position).getSell_price());
			if (!List.get(position).getSell_price().equals("0.0")) {
				holder.tv_groupon_price.setText("￥"+ List.get(position).getSell_price());
				holder.tv_dizhi.setVisibility(View.GONE);
				holder.tv_dizhi_2.setVisibility(View.VISIBLE);
				//				holder.tv_dizhi.setBackgroundResource(R.drawable.bg_red_3_5_huodong);
				//				holder.tv_dizhi.setTextColor(Color.WHITE);
				//				holder.tv_dizhi.setText("节日活动");
			}else {
				//				if (position == 0) {
				//					holder.tv_groupon_price.setText("￥"+ List.get(position).getSell_price());
				//					holder.tv_dizhi.setVisibility(View.GONE);
				//					holder.tv_dizhi_2.setVisibility(View.VISIBLE);
				//				}else {
				holder.tv_dizhi.setVisibility(View.VISIBLE);
				holder.tv_dizhi_2.setVisibility(View.GONE);
				//				}
			}

			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					now_1 = df.parse(List.get(position).getEnd_time());
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					date_1 = df.parse(ZhongAnMSActivity.datetime);
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				long end_time = now_1.getTime();
				long time = date_1.getTime();
				System.out.println("end_time-------------" + end_time);
				System.out.println("time-------------" + time);
				if (end_time > time) {
					//					holder.tv_ent.setVisibility(View.GONE);
					SharedPreferences spPreferences = mContext.getSharedPreferences("longuserset", mContext.MODE_PRIVATE);
					user_name = spPreferences.getString("user", "");
					if (!user_name.equals("")) {
						AsyncHttp.get(RealmName.REALM_NAME_LL + "/check_order_signin?mobile="
										+ user_name + "&article_id=" + List.get(position).getId() + "",
								new AsyncHttpResponseHandler() {
									@Override
									public void onSuccess(int arg0, String arg1) {
										// TODO Auto-generated method stub
										super.onSuccess(arg0, arg1);
										try {
											JSONObject jsonObject = new JSONObject(arg1);
											String status = jsonObject.getString("status");
											System.out.println("检测报名活动是否报名================"+ arg1);
											String info = jsonObject.getString("info");
											if (status.equals("y")) {
												//未签到未报名
												holder.tv_ent.setText("签到中");
											} else {
												//已签到
												holder.tv_ent.setText("报名中");
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}

									@Override
									public void onFailure(Throwable arg0, String arg1) {
										// TODO Auto-generated method stub
										System.out.println("访问接口失败！==========================" + arg1);
										super.onFailure(arg0, arg1);
									}
								}, null);
					}else {
						//						holder.tv_ent.setVisibility(View.GONE);
						holder.tv_ent.setText("进行中");
					}
					System.out.println("1-------立即参与------");
				} else {
					//					holder.tv_ent.setVisibility(View.VISIBLE);
					holder.tv_ent.setText("已结束");
					System.out.println("2-----已结束--------");
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |
			// Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			// holder.tv_tuan.setText(List.get(position).getPeople() + "人团");
			// holder.tv_anniu.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			// try {
			// Intent intent = new Intent(mContext,
			// JuTuanGouXqActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// intent.putExtra("id", List.get(position).getId());
			// intent.putExtra("choujiang", "110");
			// mContext.startActivity(intent);
			// } catch (Exception e) {
			// // TODO: handle exception
			// e.printStackTrace();
			// }
			// }
			// });

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img, ll_tupian;
		TextView tv_titel, tv_price, tv_dizhi, tv_dizhi_2,tv_groupon_price, tv_anniu;
		TextView tv_category_title, tv_tuan2, tv_groupon_price2, tv_time,tv_ent;
		TextView tv_price3, tv_groupon_price3, tv_anniu3;
		LinearLayout ll_jutuan, ll_yushoutuan, ll_yiyuanjutou;
	}
}
