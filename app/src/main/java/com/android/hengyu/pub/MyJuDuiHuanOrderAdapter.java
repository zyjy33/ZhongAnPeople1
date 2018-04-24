package com.android.hengyu.pub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.hengyushop.demo.home.DianPingActivity;
import com.hengyushop.demo.my.MyJuDuiHuanXqActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.hengyushop.entity.MyOrderData;
import com.zams.www.R;
import com.zams.www.R.id;
import com.zams.www.R.layout;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 *
 * @author Administrator
 *
 */
public class MyJuDuiHuanOrderAdapter extends BaseAdapter {
	private Context context;
	private Intent intent;
	private List<MyOrderData> list;
	private LayoutInflater inflater;
	private Activity act;
	private Handler handler;
	private String payment_status, express_status, status;
	int zhuangtai, yunfei1;
	public static String total_cll, heji_zongjia;
	double dzongjia, yunfei;
	String user_name, user_id, login_sign, order_no;
	TextView tv_heji;
	int p;
	public static boolean type = false;
	public static AQuery mAq;
	public static String recharge_no, notify_url;

	// public static List<Double> list_monney = new ArrayList<Double>();
	public MyJuDuiHuanOrderAdapter(List<MyOrderData> list, Context context,
								   Handler handler) {
		this.list = list;
		this.context = context;
		this.handler = handler;
		// this.payment_status = payment_status;
		mAq = new AQuery(context);
		this.inflater = LayoutInflater.from(context);
	}

	// @Override
	// public int getCount() {
	// if (list.size() < 1) {
	//
	// return 0;
	// } else {
	//
	// return list.size();
	// }
	// }

	public void putData(ArrayList<MyOrderData> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	public int getCount() {

		return list.size();
	}

	// @Override
	// public Object getItem(int position) {
	//
	// return list.get(position);
	// }

	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	private final class ViewHolder {
		TextView tv_goods_title;
		TextView tv_market_price;
		TextView sell_price;
		TextView quantity;
		ImageView tupian;//
		TextView lv_jijian;
		TextView tv_kukuang;
		TextView tv_quxiao;//
		TextView tv_zhuangtai;//
		TextView jijian;//
		TextView tv_heji, tv_yunfei;//
		TextView shanchu;// 删除
		TextView tv_zongjia;
		TextView tv_queren_fukuan;
		TextView tv_pingjia, tv_company_name, tv_hongbao;
		LinearLayout lv_dingdanxq;
		LinearLayout ll_anliu, ll_hongbao;
		private long timeGetTime;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {

		// ViewHolder holder = null;
		// holder = new ViewHolder();
		System.out
				.println("position==========================================================="
						+ position);
		// if (convertView == null) {
		// holder = new ViewHolder();

		// convertView = inflater.inflate(R.layout.itme_my_order, null);
		if (convertView == null) {
			convertView = LinearLayout.inflate(context, R.layout.itme_my_order,
					null);
		}

		LinearLayout addview = (LinearLayout) convertView
				.findViewById(id.gwc_addview);
		try {
			LinearLayout ll_hongbao = (LinearLayout) convertView
					.findViewById(R.id.ll_hongbao);//
			LinearLayout ll_anliu = (LinearLayout) convertView
					.findViewById(R.id.ll_anliu);//
			TextView lv_jijian = (TextView) convertView
					.findViewById(R.id.lv_jijian);//
			TextView tv_kukuang = (TextView) convertView
					.findViewById(R.id.tv_kukuang);
			TextView tv_quxiao = (TextView) convertView
					.findViewById(R.id.tv_quxiao);//
			TextView tv_zhuangtai = (TextView) convertView
					.findViewById(R.id.tv_zhuangtai);//
			TextView tv_yunfei = (TextView) convertView
					.findViewById(R.id.tv_yunfei);//
			TextView shanchu = (TextView) convertView
					.findViewById(R.id.tv_shanche);//
			TextView tv_queren_fukuan = (TextView) convertView
					.findViewById(R.id.tv_queren_fukuan);//
			TextView tv_pingjia = (TextView) convertView
					.findViewById(R.id.tv_pingjia);//
			TextView tv_hongbao = (TextView) convertView
					.findViewById(R.id.tv_hongbao);//
			TextView tv_company_name = (TextView) convertView
					.findViewById(R.id.tv_company_name);//
			TextView tv_heji = (TextView) convertView
					.findViewById(R.id.tv_heji);//

			if (list.get(position).getExchange_point_total().equals("0")) {
				tv_heji.setText("￥" + list.get(position).getPayable_amount());
			} else {
				tv_heji.setText("聚币"
						+ list.get(position).getExchange_point_total() + "+"
						+ "￥" + list.get(position).getPayable_amount());
			}

			tv_company_name.setText(list.get(position).getCompany_name());
			payment_status = list.get(position).getPayment_status();
			// System.out.println("payment_status============="+payment_status);
			express_status = list.get(position).getExpress_status();
			// System.out.println("express_status============="+express_status);
			status = list.get(position).getStatus();
			// System.out.println("status============="+status);

			String yunfei = list.get(position).getExpress_fee();
			// System.out.println("yunfei1============="+yunfei);
			if (yunfei.equals("0.0")) {
				// if (yunfei == 0) {
				tv_yunfei.setVisibility(View.GONE);
			} else {
				tv_yunfei.setText("(含运费￥" + list.get(position).getExpress_fee()
						+ ")");
			}

			String kedi_honbao = list.get(position).getCashing_packet();
			// System.out.println("kedi_honbao============="+kedi_honbao);
			if (kedi_honbao.equals("0.0")) {
				ll_hongbao.setVisibility(View.GONE);
			} else {
				tv_hongbao.setText("可抵红包:-￥" + kedi_honbao);
			}

			if (payment_status.equals("1")) {
				System.out.println("待付款=============");
				tv_zhuangtai.setText("等待付款");
				ll_anliu.setVisibility(View.VISIBLE);
				tv_kukuang.setVisibility(View.VISIBLE);
				tv_pingjia.setVisibility(View.GONE);
				tv_queren_fukuan.setVisibility(View.GONE);
				shanchu.setVisibility(View.VISIBLE);
				tv_kukuang.setText("去付款");
				zhuangtai = 2;
			} else if (payment_status.equals("2") && express_status.equals("1")) {
				System.out.println("待发货=============");
				tv_zhuangtai.setText("已付款");
				ll_anliu.setVisibility(View.GONE);
				zhuangtai = 3;
			} else if (payment_status.equals("2") && express_status.equals("2")
					&& status.equals("2")) {
				System.out.println("待收货=============");
				tv_zhuangtai.setText("已发货");
				ll_anliu.setVisibility(View.VISIBLE);
				tv_kukuang.setVisibility(View.GONE);
				tv_pingjia.setVisibility(View.GONE);
				tv_queren_fukuan.setVisibility(View.VISIBLE);
				tv_queren_fukuan.setText("确认收货");
				zhuangtai = 4;
			} else if (payment_status.equals("2") && express_status.equals("2")
					&& status.equals("3")) {
				System.out.println("已完成=============");
				tv_zhuangtai.setText("交易完成");
				ll_anliu.setVisibility(View.VISIBLE);
				tv_queren_fukuan.setVisibility(View.GONE);
				tv_kukuang.setVisibility(View.GONE);
				shanchu.setVisibility(View.VISIBLE);
				tv_pingjia.setVisibility(View.VISIBLE);
				tv_pingjia.setText("评价");
				zhuangtai = 5;
			}

			/**
			 * 确认付款
			 */
			tv_kukuang.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						double a = 0;

						String yunfei = list.get(position).getExpress_fee();
						// System.out.println("yunfei================"+yunfei);
						BigDecimal c = new BigDecimal(Double.parseDouble(list
								.get(position).getPayable_amount())
								+ Double.parseDouble(list.get(position)
								.getExpress_fee()));
						String total_cll = Double.toString(c.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue());
						// System.out.println("total_cll================"+total_cll);
						String order_no = list.get(position).getTrade_no();
						Intent intent = new Intent(context,
								MyOrderZFActivity.class);
						intent.putExtra("order_no", order_no);
						intent.putExtra("total_c", list.get(position)
								.getPayable_amount());
						context.startActivity(intent);

						Message msg = new Message();
						msg.what = 4;
						msg.obj = order_no;
						handler.sendMessage(msg);

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});

			/**
			 * 确认收货
			 */
			tv_queren_fukuan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(context,
								TishiCarArchivesActivity.class);
						intent.putExtra("order_no", list.get(position)
								.getOrder_no());
						context.startActivity(intent);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});

			/**
			 * 取消订单
			 */
			tv_quxiao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						String order_no = list.get(position).getOrder_no();
						Message msg = new Message();
						msg.what = 2;
						msg.obj = order_no;
						handler.sendMessage(msg);
					} catch (Exception e) {

						e.printStackTrace();
					}

				}
			});

			/**
			 * 删除
			 */
			shanchu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						String order_no = list.get(position).getOrder_no();
						Message msg = new Message();
						msg.what = 3;
						msg.obj = order_no;
						handler.sendMessage(msg);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});
			addview.removeAllViews();

			for (int i = 0; i < list.get(position).getList().size(); i++) {
				// ViewHolder holder = null;
				p = i;
				// holder = new ViewHolder();
				View vi = LayoutInflater.from(context).inflate(
						layout.itme_my_order_zhuti, null);

				TextView tv_goods_title = (TextView) vi
						.findViewById(R.id.tv_goods_title);//
				ImageView tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
				TextView tv_market_price = (TextView) vi
						.findViewById(R.id.tv_market_price);
				TextView sell_price = (TextView) vi
						.findViewById(R.id.tv_real_price);
				TextView tv_market_price_title = (TextView) vi
						.findViewById(R.id.tv_market_price_title);
				TextView tv_jubi = (TextView) vi.findViewById(R.id.tv_jubi);
				TextView quantity = (TextView) vi
						.findViewById(R.id.tv_quantity);
				LinearLayout lv_dingdanxq = (LinearLayout) vi
						.findViewById(R.id.lv_dingdanxq);
				System.out.println("getGoods_title============="
						+ list.get(position).getList().get(i).getPoint_title());
				System.out.println("getPoint_value============="
						+ list.get(position).getList().get(i).getPoint_value());
				System.out.println("getPoint_price============="
						+ list.get(position).getList().get(i).getPoint_price());
				tv_goods_title.setText(list.get(position).getList().get(i)
						.getPoint_title());
				tv_jubi.setText("聚币:");
				sell_price
						.setText(list.get(position).getExchange_point_total());
				tv_market_price.setText("价格:￥"
						+ list.get(position).getExchange_price_total());

				// holder.sell_price.setText("￥"+list.get(position).getList().get(i).getSell_price());
				// quantity.setText("x"+list.get(position).getList().get(i).getQuantity());
				tv_market_price_title.setVisibility(View.GONE);
				// tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG
				// | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
				mAq.id(tupian).image(
						RealmName.REALM_NAME_HTTP
								+ list.get(position).getList().get(i)
								.getImg_url());
				type = true;
				// int number =
				// list.get(position).getList().get(i).getQuantity();
				// if (number >= 1) {
				// BigDecimal c = new
				// BigDecimal(Double.parseDouble(list.get(position).getList().get(i).getGoods_price())/number);
				// // //保留2位小数
				// double sell_price_zhi =
				// c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				//
				// sell_price.setText("1￥"+sell_price_zhi);//价格
				// }else {
				// sell_price.setText("2￥"+list.get(position).getList().get(i).getGoods_price());//价格
				// }

				addview.addView(vi);
				// convertView.setTag(holder);

				/**
				 * 订单详情
				 */
				lv_dingdanxq.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						try {

							Intent intent = new Intent(context,
									MyJuDuiHuanXqActivity.class);
							MyOrderData bean = list.get(position);
							Bundle bundle = new Bundle();
							bundle.putSerializable("bean", (Serializable) bean);
							intent.putExtras(bundle);
							intent.putExtra("payment_status", payment_status);
							intent.putExtra("express_status", express_status);
							intent.putExtra("status", status);
							context.startActivity(intent);

						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				});
			}

			/**
			 * 评价
			 */
			// TextView tv_pingjia = (TextView)
			// convertView.findViewById(R.id.tv_pingjia);//
			tv_pingjia.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(context,
								DianPingActivity.class);
						intent.putExtra("article_id", list.get(position)
								.getList().get(p).getArticle_id());
						context.startActivity(intent);

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

		// }else {
		// holder = (ViewHolder) convertView.getTag();
		// }
		return convertView;

	}

}