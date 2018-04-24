package com.android.hengyu.pub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.home.JuJingCaiXqActivity;
import com.hengyushop.demo.home.JuTuanGouActivity;
import com.hengyushop.demo.home.JuTuanGouXqActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.zams.www.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JuTuanGouAdapter extends BaseAdapter {

	private Context mContext;
	private List<JuTuanGouData> List;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	java.util.Date now_1;
	java.util.Date date_1;

	public JuTuanGouAdapter(Context context, List<JuTuanGouData> list) {
		this.List = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
		// System.out.println("============="+list.size()+1);
	}

	public void putData(ArrayList<JuTuanGouData> List) {
		this.List = List;
		this.notifyDataSetChanged();
	}

	public int getCount() {

		// return list.size();
		return List.size();
	}

	// @Override
	// public int getCount() {
	// if (List.size() < 1) {
	// return 0;
	// } else {
	// return List.size();
	//
	// }
	// }
	@Override
	public Object getItem(int position) {

		return List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.jutuangou_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.tv_titel = (TextView) convertView
					.findViewById(R.id.tv_titel);

			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_groupon_price = (TextView) convertView
					.findViewById(R.id.tv_groupon_price);
			holder.tv_tuan = (TextView) convertView.findViewById(R.id.tv_tuan);
			holder.tv_anniu = (TextView) convertView
					.findViewById(R.id.tv_anniu);

			holder.tv_price2 = (TextView) convertView
					.findViewById(R.id.tv_price2);
			holder.tv_groupon_price2 = (TextView) convertView
					.findViewById(R.id.tv_groupon_price2);
			holder.tv_tuan2 = (TextView) convertView
					.findViewById(R.id.tv_tuan2);
			holder.tv_anniu2 = (TextView) convertView
					.findViewById(R.id.tv_anniu2);

			holder.tv_price3 = (TextView) convertView
					.findViewById(R.id.tv_price3);
			holder.tv_groupon_price3 = (TextView) convertView
					.findViewById(R.id.tv_groupon_price3);
			holder.tv_anniu3 = (TextView) convertView
					.findViewById(R.id.tv_anniu3);

			holder.ll_tupian = (ImageView) convertView
					.findViewById(R.id.ll_tupian);

			holder.ll_jutuan = (LinearLayout) convertView
					.findViewById(R.id.ll_jutuan);
			holder.ll_yushoutuan = (LinearLayout) convertView
					.findViewById(R.id.ll_yushoutuan);
			holder.ll_yiyuanjutou = (LinearLayout) convertView
					.findViewById(R.id.ll_yiyuanjutou);
			// tv_titel,tv_price,tv_tuan,tv_groupon_price,tv_anniu;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/**
		 * 类别选项
		 */
		try {
			// System.out.println("List======================"+List.size());

			System.out.println("个数1======================" + position);
			String category_id = List.get(position).getCategory_id();
			System.out.println("category_id======================"
					+ category_id);

			// 预售团
			// if (category_id.equals("1704")) {
			if (category_id.equals("2927")) {
				// Toast.makeText(mContext, "预售团", 200).show();
				// System.out.println("List.get(position).getPrice()======================"+
				// List.get(position).getPrice());
				holder.ll_jutuan.setVisibility(View.GONE);
				holder.ll_yushoutuan.setVisibility(View.VISIBLE);
				holder.ll_yiyuanjutou.setVisibility(View.GONE);
				holder.tv_groupon_price2.setText("￥"
						+ List.get(position).getPrice());
				holder.tv_price2.setText("￥"
						+ List.get(position).getSell_price());
				System.out
						.println("List.get(position).getPeople()======================"
								+ List.get(position).getPeople());
				holder.tv_price.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
				holder.tv_tuan2.setText(List.get(position).getPeople() + "人团");

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					now_1 = df.parse(List.get(position).getEnd_time());
				} catch (java.text.ParseException e1) {

					e1.printStackTrace();
				}

				try {
					date_1 = df.parse(JuTuanGouActivity.datetime);
				} catch (java.text.ParseException e1) {

					e1.printStackTrace();
				}
				long end_time = now_1.getTime();
				long time = date_1.getTime();
				System.out.println("end_time-------------" + end_time);
				System.out.println("time-------------" + time);
				if (end_time > time) {
					System.out.println("1-------立即参与------");
					holder.tv_anniu2.setText("去开团");
					holder.tv_anniu2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							// MyOrderConfrimActivity.fanhui_type = false;
							// DBFengXiangActivity.fanhui_type = false;
							// UserLoginActivity.fanhui_type = false;
							Intent intent = new Intent(mContext,
									JuTuanGouXqActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("id", List.get(position).getId());
							intent.putExtra("datatype", "7");
							intent.putExtra("jiekou", "3");
							intent.putExtra("fx_shuzi", "ladder");
							mContext.startActivity(intent);
						}
					});
				} else {
					System.out.println("2-----已结束--------");
					holder.tv_anniu2.setText("已经结束");
				}

				// 聚精彩
				// } else if (category_id.equals("1703")) {
			} else if (category_id.equals("909")) {
				// Toast.makeText(mContext, "聚精彩", 200).show();
				// holder.ll_tupian.setImageResource(R.drawable.jujingcai);
				holder.ll_jutuan.setVisibility(View.VISIBLE);
				holder.ll_yushoutuan.setVisibility(View.GONE);
				holder.ll_yiyuanjutou.setVisibility(View.GONE);
				holder.tv_price.setText("￥"
						+ List.get(position).getSell_price());
				holder.tv_groupon_price.setText("￥"
						+ List.get(position).getPrice());
				holder.tv_price.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
				holder.tv_tuan.setText(List.get(position).getPeople() + "人团");

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					now_1 = df.parse(List.get(position).getEnd_time());
				} catch (java.text.ParseException e1) {

					e1.printStackTrace();
				}

				try {
					date_1 = df.parse(JuTuanGouActivity.datetime);
				} catch (java.text.ParseException e1) {

					e1.printStackTrace();
				}
				long end_time = now_1.getTime();
				long time = date_1.getTime();
				System.out.println("end_time-------------" + end_time);
				System.out.println("time-------------" + time);
				if (end_time > time) {
					System.out.println("1-------立即参与------");
					holder.tv_anniu.setText("去开团");
					holder.tv_anniu.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							try {
								// JuTuanConfrimActivity.fanhui_type = false;
								// DBFengXiangActivity.fanhui_type = false;
								Intent intent = new Intent(mContext,
										JuJingCaiXqActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.putExtra("id", List.get(position)
										.getId());
								// intent.putExtra("choujiang", "110");
								intent.putExtra("fx_shuzi", "groupon");
								// intent.putExtra("type","1");

								mContext.startActivity(intent);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else {
					System.out.println("2-----已结束--------");
					holder.tv_anniu.setText("已经结束");
				}

				// 聚团
				// } else if (category_id.equals("1702")) {
			} else if (category_id.equals("910")) {
				// Toast.makeText(mContext, "聚团", 200).show();
				holder.ll_jutuan.setVisibility(View.GONE);
				holder.ll_yiyuanjutou.setVisibility(View.GONE);
				holder.ll_yushoutuan.setVisibility(View.VISIBLE);
				holder.tv_price2.setText("￥" + List.get(position).getPrice());
				holder.tv_groupon_price2.setText("￥"
						+ List.get(position).getSell_price());
				holder.tv_price2.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
				System.out
						.println("List.get(position).getPeople()-----------------------------"
								+ List.get(position).getPeople());
				holder.tv_tuan2.setText(List.get(position).getPeople() + "人团");
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					now_1 = df.parse(List.get(position).getEnd_time());
				} catch (java.text.ParseException e1) {

					e1.printStackTrace();
				}

				try {
					date_1 = df.parse(JuTuanGouActivity.datetime);
				} catch (java.text.ParseException e1) {

					e1.printStackTrace();
				}
				long end_time = now_1.getTime();
				long time = date_1.getTime();
				System.out.println("end_time-------------" + end_time);
				System.out.println("time-------------" + time);
				if (end_time > time) {
					System.out.println("1-------立即参与------");
					holder.tv_anniu2.setText("去开团");
					holder.tv_anniu2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							try {
								// MyOrderConfrimActivity.fanhui_type = false;
								// DBFengXiangActivity.fanhui_type = false;
								// UserLoginActivity.fanhui_type = false;
								Intent intent = new Intent(mContext,
										JuTuanGouXqActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.putExtra("id", List.get(position)
										.getId());
								intent.putExtra("datatype", "4");
								intent.putExtra("jiekou", "2");
								intent.putExtra("fx_shuzi", "group");
								mContext.startActivity(intent);

							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else {
					System.out.println("2-----已结束--------");
					holder.tv_anniu2.setText("已经结束");
				}

				// 一元聚宝
			}
			// else if (category_id.equals("1728")) {
			// // Toast.makeText(mContext, "一元聚宝", 200).show();
			// // holder.ll_tupian.setImageResource(R.drawable.jutuan);
			// holder.ll_jutuan.setVisibility(View.GONE);
			// holder.ll_yushoutuan.setVisibility(View.GONE);
			// holder.ll_yiyuanjutou.setVisibility(View.VISIBLE);
			// // ImageLoader imageLoader = ImageLoader.getInstance();
			// // imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP
			// // + List.get(position).getImg_url(), holder.img);
			// // holder.tv_titel.setText(List.get(position).getTitle());
			// holder.tv_price3.setText("￥" + List.get(position).getPrice());
			// holder.tv_groupon_price3.setText("￥"
			// + List.get(position).getGroupon_price());
			// holder.tv_price3.getPaint().setFlags(
			// Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //
			// 设置市场价文字的中划线
			// holder.tv_anniu3.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			//
			// // MyOrderConfrimActivity.fanhui_type = false;
			// // DBFengXiangActivity.fanhui_type = false;
			// // UserLoginActivity.fanhui_type = false;
			// String shuzi = "110";
			// System.out.println("-----------"+shuzi);
			// Intent intent = new Intent(mContext, JuTuanGouXqActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// intent.putExtra("id", List.get(position).getId());
			// mContext.startActivity(intent);
			// }
			// });
			// }

			// ImageLoader imageLoader=ImageLoader.getInstance();
			// imageLoader.displayImage((String)RealmName.REALM_NAME_HTTP+List.get(position).getImg_url(),holder.img);
			mAq.id(holder.img)
					.image(RealmName.REALM_NAME_HTTP
							+ List.get(position).getImg_url());
			holder.tv_titel.setText(List.get(position).getTitle());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img, ll_tupian;
		TextView tv_titel, tv_price, tv_tuan, tv_groupon_price, tv_anniu;
		TextView tv_price2, tv_tuan2, tv_groupon_price2, tv_anniu2, tv_time;
		TextView tv_price3, tv_groupon_price3, tv_anniu3;
		LinearLayout ll_jutuan, ll_yushoutuan, ll_yiyuanjutou;
	}
}
