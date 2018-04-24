package com.android.hengyu.pub;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.MyOrderData;
import com.zams.www.R;
import com.zams.www.R.id;
import com.zams.www.R.layout;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的订单
 *
 * @author Administrator
 *
 */
public class MyJuDuiHuanXqAdapter extends BaseAdapter {
	private Context context;
	private Intent intent;
	private List<MyOrderData> list;
	private LayoutInflater inflater;
	private Activity act;
	private Handler handler;
	public AQuery mAq;
	public static String total_c,heji_zongjia;
	public static List<Double> list_monney = new ArrayList<Double>();
	public MyJuDuiHuanXqAdapter(Context context, List<MyOrderData> list, Handler handler) {
		this.list = list;
		this.context = context;
		this.handler = handler;
		this.inflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	@Override
	public int getCount() {
		if (list.size() < 1) {

			return 0;
		} else {

			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	private final class ViewHolder {
		TextView tv_goods_title;
		TextView tv_market_price;
		TextView real_price;
		TextView quantity;
		ImageView tupian;//
		TextView lv_jijian,tv_sj_name;
		TextView tv_kukuang,tv_haoma;
		TextView tv_quxiao;//
		TextView tv_name;//
		TextView tv_addview;//
		TextView tv_order_bh;//
		TextView shanchu;//删除
		TextView tv_order_cjsj;
		TextView tv_order_fksj,tv_order_chengjiao_time;
		TextView tv_heji;
		TextView tv_zongjia,tv_yunfei,tv_hongbao;
		LinearLayout lv_dingdanxq;

		private long timeGetTime;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {

		ViewHolder holder = null;
		holder = new ViewHolder();
		if (convertView == null) {
			try {
				holder = new ViewHolder();


				convertView = inflater.inflate(R.layout.time_order_xq, null);
				LinearLayout addview = (LinearLayout) convertView.findViewById(id.gwc_addview);

				holder.lv_jijian = (TextView) convertView.findViewById(R.id.lv_jijian);//
				holder.tv_kukuang = (TextView) convertView.findViewById(R.id.tv_kukuang);
				holder.tv_sj_name = (TextView) convertView.findViewById(R.id.tv_sj_name);//
				holder.tv_haoma = (TextView) convertView.findViewById(R.id.tv_haoma);//
				//		holder.tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);//
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);//
				holder.tv_addview = (TextView) convertView.findViewById(R.id.tv_dizhi);//
				holder.tv_order_bh = (TextView) convertView.findViewById(R.id.tv_order_bh);//
				holder.tv_order_cjsj = (TextView) convertView.findViewById(R.id.tv_order_cjsj);//
				holder.tv_order_fksj = (TextView) convertView.findViewById(R.id.tv_order_fksj);//
				holder.tv_order_chengjiao_time = (TextView) convertView.findViewById(R.id.tv_order_chengjiao_time);//
				holder.shanchu = (TextView) convertView.findViewById(R.id.tv_shanche);// 删除
				holder.tv_yunfei = (TextView) convertView.findViewById(R.id.tv_yunfei);//
				holder.tv_name.setText("收货人: "+list.get(position).getAccept_name());//
				holder.tv_haoma.setText("电话号码: "+list.get(position).getMobile());//
				holder.tv_addview.setText("收货地址: "+list.get(position).getProvince()+"、"+list.get(position).getCity()+"、 "+
						list.get(position).getArea()+"、"+list.get(position).getAddress());
				holder.tv_sj_name.setText(list.get(position).getCompany_name());
				holder.tv_order_bh.setText(list.get(position).getOrder_no());//
				holder.tv_order_cjsj.setText(list.get(position).getAdd_time());

				String yunfei = list.get(position).getExpress_fee();
				System.out.println("yunfei1============="+yunfei);
				if (yunfei.equals("0.0")) {
					holder.tv_yunfei.setVisibility(View.GONE);
				}else {
					holder.tv_yunfei.setText("(含运费￥"+list.get(position).getExpress_fee()+")");
				}

				try {
					String status = list.get(position).getPayment_status();
					//				System.out.println("订单详情status============="+status);
					if (status != null) {
						//		if (status.equals("1")) {
						////			holder.tv_kukuang.setText("确认付款");
						//			holder.tv_kukuang.setVisibility(View.VISIBLE);
						//			holder.shanchu.setVisibility(View.VISIBLE);
						////			holder.tv_zhuangtai.setText("买家未付款");
						//		}

						if (status.equals("2")) {
							if (list.get(position).getPayment_time() != null) {
								holder.tv_order_fksj.setText(list.get(position).getPayment_time());//
								//				holder.tv_kukuang.setVisibility(View.VISIBLE);
							}
						}

						//		if (status.equals("3")) {
						if (!list.get(position).getRebate_time().equals("null")) {
							holder.tv_order_chengjiao_time.setText(list.get(position).getRebate_time());
							//				holder.tv_kukuang.setVisibility(View.VISIBLE);
						}
						//		}

					}

				} catch (Exception e) {

					e.printStackTrace();
				}

				addview.removeAllViews();

				for (int i = 0; i < list.get(position).getList().size(); i++) {
					holder = new ViewHolder();
					View vi = LayoutInflater.from(context).inflate(layout.itme_my_order_xq, null);
					holder.tv_goods_title = (TextView) vi.findViewById(R.id.tv_goods_title);//
					holder.tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
					holder.tv_market_price = (TextView) vi.findViewById(R.id.tv_market_price);
					holder.real_price = (TextView) vi.findViewById(R.id.tv_real_price);
					holder.quantity = (TextView) vi.findViewById(R.id.tv_quantity);
					holder.tv_zongjia = (TextView) vi.findViewById(R.id.tv_zongjia);
					holder.tv_hongbao = (TextView) vi.findViewById(R.id.tv_hongbao);
					holder.lv_dingdanxq = (LinearLayout) vi.findViewById(R.id.lv_dingdanxq);

					holder.tv_goods_title.setText(list.get(position).getList().get(i).getPoint_title());
					holder.tv_market_price.setText("价格:￥"+list.get(position).getExchange_price_total());
					holder.real_price.setText("聚币:￥"+list.get(position).getExchange_point_total());

					//			holder.quantity.setText("x"+list.get(position).getList().get(i).getQuantity());
					//			holder.tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
					//			ImageLoader imageLoader=ImageLoader.getInstance();
					//			imageLoader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getList().get(i).getImg_url(), holder.tupian);
					mAq.id(holder.tupian).image(RealmName.REALM_NAME_HTTP+list.get(position).getList().get(i).getImg_url());

					try {


						//				int number = list.get(position).getList().get(i).getQuantity();
						//				BigDecimal   c   =   new   BigDecimal(Double.parseDouble(list.get(position).getList().get(i).getGoods_price())/number);
						////				//保留2位小数
						//				double   sell_price   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						//				holder.real_price.setText("价格:￥"+sell_price);


						holder.tv_zongjia.setText("￥"+list.get(position).getExchange_price_total());

					} catch (Exception e) {

						e.printStackTrace();
					}

					addview.addView(vi);
					convertView.setTag(holder);




				}

				String kedi_honbao = list.get(position).getCashing_packet();
				System.out.println("kedi_honbao============="+kedi_honbao);
				if (kedi_honbao.equals("0.0")) {
				}else {
					holder.tv_hongbao.setText("-￥"+kedi_honbao);
				}


				//		Double sum1 = 0d;
				//		for(Double a:list_monney)//个数数组
				//		{
				//		    sum1 += a;
				////		    System.out.println("sum1============="+sum1);
				//		}
				//		  String total_c = Double.toString(sum1);
				System.out.println("yunfei================"+yunfei);

				//			BigDecimal c = new BigDecimal(Double.parseDouble(list.get(position).getPayable_amount())+Double.parseDouble(list.get(position).getExpress_fee()));
				//			heji_zongjia = Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				heji_zongjia = list.get(position).getPayable_amount();
				System.out.println("heji_zongjia============="+heji_zongjia);
				holder.tv_heji = (TextView) convertView.findViewById(R.id.tv_heji);
				if (list.get(position).getExchange_point_total().equals("0")) {
					holder.tv_heji.setText("合计:￥"+heji_zongjia);
				}else {
					holder.tv_heji.setText("合计:"+"聚币"+list.get(position).getExchange_point_total()+"+"+"￥"+heji_zongjia);
				}


			} catch (Exception e) {

				e.printStackTrace();
			}


		}else {
			holder = (ViewHolder) convertView.getTag();
		}


		return convertView;

	}
}