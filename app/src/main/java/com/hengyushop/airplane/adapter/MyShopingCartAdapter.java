package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.ShopCartViewHolder;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.ShopCartData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyShopingCartAdapter extends BaseAdapter {
	public static HashMap<Integer, Boolean> isSelected;
	private ArrayList<ShopCartData> list = null;
	private ShopCartViewHolder holder;
	private Context context;
	private ImageLoader loader;
	private WareDao wareDao;
	private ShopCartData cartData;
	private Handler handler;
	public static String total_c,total_c_jian,total_c_jia;
	String quantity;
	boolean statuo;
	public static String cart_id ;
	private List list_id = new ArrayList();


	public MyShopingCartAdapter(ArrayList<ShopCartData> list, Context context,
								Handler handler) {
		System.out.println("4================");
		this.context = context;
		this.list = list;
		this.handler = handler;
		loader = ImageLoader.getInstance();
		wareDao = new WareDao(context);
		isSelected = new HashMap<Integer, Boolean>();
		init();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 初始化 设置所有checkbox都为未选择
	public void init() {
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			System.out.println("6================");
			holder = new ShopCartViewHolder();
			convertView = RelativeLayout.inflate(context,R.layout.listitem_shopping_cart, null);
			holder.img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
			holder.btn_order_cancle = (ImageButton) convertView.findViewById(R.id.cb_style);
			holder.tv_warename = (TextView) convertView.findViewById(R.id.tv_ware_name);
			holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
			holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
			holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.btn_reduce = (ImageButton) convertView.findViewById(R.id.img_btn_reduce);
			holder.et_number = (EditText) convertView.findViewById(R.id.et_number);
			holder.btn_add = (ImageButton) convertView.findViewById(R.id.img_btn_add);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.shopcart_item_check = (CheckBox) convertView.findViewById(R.id.shopcart_item_check);
			// 关于数量
			holder.market_information_seps_add = (TextView) convertView.findViewById(R.id.market_information_seps_add);//增加
			holder.market_information_seps_del = (TextView) convertView.findViewById(R.id.market_information_seps_del);//减少
			holder.market_information_seps_num = (TextView) convertView.findViewById(R.id.market_information_seps_num);//个数
			//			holder.market_information_seps_num.setText("1");
			convertView.setTag(holder);
		} else {
			holder = (ShopCartViewHolder) convertView.getTag();
		}

		System.out.println("是什么111111111111111111111111111==========="+position);
		final boolean ai = list.get(position).isCheck();
		//		System.out.println("是什么000==========="+ai.isCheck());
		System.out.println("是什么00022==========="+list.get(position).isCheck);
		ShopCartData zhou = list.get(position);
		System.out.println("是什么==========="+list.size());
		//		holder.tv_warename.setText(ai.getTitle());
		//		holder.tv_color.setText("￥" + ai.getSell_price());
		holder.tv_warename.setText(list.get(position).getTitle());
		holder.tv_color.setText("￥" + list.get(position).getSell_price());
		holder.tv_size.setText("￥" + list.get(position).getMarket_price());
		holder.et_number.setText(list.get(position).getQuantity()+ "");
		System.out.println("7================"+ list.get(position).sell_price);
		System.out.println("8================"+ list.get(position).quantity);
		loader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getImg_url(),holder.img_ware);
		holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

		double a= 0;
		for (int i = 0; i < list.size(); i++) {
			String price = list.get(i).sell_price;
			int number = list.get(i).getQuantity();
			BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
			//保留2位小数
			double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out.println("价格是多少0============="+total_c);
			a += total_c_ll;
		}

		Message message = new Message();
		message.what = 1;
		//		message2.obj = total_c;
		handler.sendMessage(message);
		total_c = Double.toString(a);
		System.out.println("===总计======="+a);

		//		holder.btn_order_cancle.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				try {
		//
		//				int orderid = Integer.parseInt(list.get(position).id);
		//				Message message = new Message();
		//				message.what = 400;
		//				message.obj = position;
		//				message.arg1 = orderid;
		//				handler.sendMessage(message);
		//				} catch (Exception e) {
		//					// TODO: handle exception
		//					e.printStackTrace();
		//				}
		//
		//			}
		//		});

		//		if (ai.isCheck()) {
		//			System.out.println("是什么1===========");
		//			holder.shopcart_item_check.setChecked(true);
		//		} else {
		//			holder.shopcart_item_check.setChecked(false);
		//			System.out.println("是什么2===========");
		//		}

		//		holder.shopcart_item_check.setChecked(isSelected.get(position));


		holder.shopcart_item_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//				final ShopCartData ai = list.get(position);
				//				ShopCartData zhou = list.get(position);
				//				System.out.println("是什么==========="+zhou);
				System.out.println("是什么111111111111111111111111111==========="+position);
				boolean isCheck = ai;
				//				System.out.println("是什么0==========="+isCheck);
				//				holder.shopcart_item_check.setChecked(true);

				System.out.println("是什么1==========="+holder.shopcart_item_check.isChecked());

				//				ai.setCheck(!isCheck);
				if (holder.shopcart_item_check.isChecked() == false) {
					//				if (isCheck == false) {
					//					holder.shopcart_item_check.setChecked(true);

					cart_id = list.get(position).getId();
					//					int id = Integer.parseInt(cart_id);
					System.out.println("是什么1==========="+holder.shopcart_item_check.isChecked());
					System.out.println("是什么11==========="+cart_id);
					//					list_id.add(cart_id);
					list_id.remove(cart_id);
					//				}else  if(isCheck == true) {
				}else if (holder.shopcart_item_check.isChecked() == true){

					cart_id = list.get(position).getId();
					System.out.println("是什么2==========="+holder.shopcart_item_check.isChecked());
					System.out.println("是什么22==========="+cart_id);
					list_id.add(cart_id);
				}
				//				 System.out.println("是什么1==========="+list_id.size());
				for(int i=0;i<list_id.size();i++){
					System.out.println("=====测试答案1======================="+list_id.get(i));
				}

			}
		});


		//		holder.market_information_seps_num
		//		holder.shopcart_item_check.setChecked(datas.get(index).isCheck);
		//		holder.shopcart_item_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		//			@Override
		//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		//				list.get(position).setCheck(arg1);
		//
		//				notifyDataSetChanged();
		//				cart_id = list.get(position).getId();
		//				for(int k=0;k<1;k++){
		////			    list.get(index)
		//			    list_ll.add(k);
		//		        }
		//				cart_id = list.get(position).getId();
		//				int daan_id1 = Integer.parseInt(cart_id);
		//				for (int j = daan_id1; j < daan_id1++; j++) {
		//					daan_id1 = j+1;
		//				}
		//				list_id.add(daan_id1);
		//				System.out.println("111========="+list_ll.size());
		//
		////				if (statuo == true) {
		////
		////				}else if (statuo == false){
		////					statuo = true;
		////				}
		//				Toast.makeText(context, "点击了", 100).show();
		//
		////				Message msg = new Message();
		////				msg.what = 2;
		////				msg.obj = list_ll;
		////				handler.sendMessage(msg);
		//			}
		//		});


		//		double total=0;
		//		if(null!=list){
		//			for (ShopCartData spSMF : list) {
		//				if(null!=spSMF.getSell_price()&&spSMF.getQuantity()>0){
		//					total +=Double.parseDouble(spSMF.getSell_price())*spSMF.getQuantity();
		//				}
		//			}
		//		}
		//		tv_total.setText(String.format(getString(R.string.str_total_format), String.valueOf(total)));





		holder.btn_add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				changeAdd(position);

				// 商品数量或者类型改变 刷新计数页面
				cartData = wareDao.findResult();
				Message message2 = new Message();
				message2.what = 200;
				message2.obj = total_c;
				handler.sendMessage(message2);
			}
		});

		holder.btn_reduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeReduce(position);

				// 商品数量或者类型改变 刷新计数页面
				cartData = wareDao.findResult();
				Message message2 = new Message();
				message2.what = 200;
				message2.obj = total_c;
				handler.sendMessage(message2);
			}
		});


		return convertView;
	}

	public void deleteData(int index) {
		String orderid = list.get(index).id;
		wareDao.deleteByOrderid(orderid);
		wareDao.findShopCart();
		list.remove(index);
		notifyDataSetChanged();
	}

	private void changeAdd(int index) {
		try {

			list.get(index).setQuantity((list.get(index).getQuantity()) + 1);
			notifyDataSetChanged();

			String price = list.get(index).sell_price;
			int number = list.get(index).getQuantity();
			BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
			//保留2位小数
			double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			total_c = Double.toString(total_c_ll);
			System.out.println("价格是多少============="+total_c);
			String cart_id = list.get(index).getId();
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/cart_goods_update?cart_id="+cart_id+"&user_id="+19+"&quantity="+number+"",new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					//				System.out.println("==========================访问接口成功！"+arg1);
					super.onSuccess(arg0, arg1);
				}

			}, context);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void changeReduce(int index) {
		try {
			if (list.get(index).getQuantity() != 1) {
				list.get(index).setQuantity((list.get(index).getQuantity()) - 1);
				notifyDataSetChanged();

				String price = list.get(index).sell_price;
				int number = list.get(index).getQuantity();
				BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
				//保留2位小数
				double   total_c_ll   =   c.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
				total_c = Double.toString(total_c_ll);
				System.out.println("价格是多少1============="+total_c);
				String cart_id = list.get(index).getId();
				AsyncHttp.get(RealmName.REALM_NAME_LL+ "/cart_goods_update?cart_id="+cart_id+"&user_id="+19+"&quantity="+number+"",new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						//					System.out.println("==========================2访问接口成功！"+arg1);
						super.onSuccess(arg0, arg1);
					}

				}, context);
			} else {
				Toast.makeText(context, "不能再减了", 200).show();
			}


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//	holder.market_information_seps_add.setOnClickListener(new View.OnClickListener() {
	//
	//			@Override
	//			public void onClick(View arg0) {
	//				// TODO Auto-generated method stub
	//				try {
	//					int num = Integer.parseInt(holder.market_information_seps_num.getText().toString());
	////					int add = Integer.parseInt(list.get(position).getQuantity());
	//					holder.market_information_seps_num.setText(String.valueOf(num + 1));
	//
	////					String quantity = list.get(position).getQuantity()+1;
	////					int numll = Integer.parseInt(quantity);
	//					int numll = num+1;
	//					System.out.println("数字是多少1==="+numll);
	//
	//					String price = list.get(position).getSell_price();
	//					System.out.println("价格是多少11============="+price);
	//					BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*numll);
	//					System.out.println("价格是多少12============="+c);
	//					//保留2位小数
	//					double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	//					String total_c = Double.toString(total_c_ll);
	//					System.out.println("价格是多少============="+total_c);
	//
	//				} catch (Exception e) {
	//					// TODO: handle exception
	//					e.printStackTrace();
	//				}
	//			}
	//		});

	//产品减少
	//	holder.market_information_seps_del.setOnClickListener(new View.OnClickListener() {
	//
	//			@Override
	//			public void onClick(View arg0) {
	//				// TODO Auto-generated method stub
	//				try {
	//				int num = Integer.parseInt(holder.market_information_seps_num.getText().toString());
	////					int del = Integer.parseInt(list.get(position).getQuantity());
	//				if (num != 1) {
	//					holder.market_information_seps_num.setText(String.valueOf(num - 1));
	//					int numll = num-1;
	//					System.out.println("数字是多少2==="+numll);
	//					String price = list.get(position).getSell_price();
	//					System.out.println("价格是多少21============="+price);
	//					BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*numll);
	//					System.out.println("价格是多少22============="+c);
	//					//保留2位小数
	//					double   total_c_ll   =   c.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
	//					String total_c = Double.toString(total_c_ll);
	//					System.out.println("价格是多少============="+total_c);
	//				} else {
	//					Toast.makeText(context, "不能再减了", 200).show();
	//				}
	//				} catch (Exception e) {
	//					// TODO: handle exception
	//					e.printStackTrace();
	//				}
	//			}
	//		});

}
