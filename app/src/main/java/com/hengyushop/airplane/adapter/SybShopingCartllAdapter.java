package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
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
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.ShopCartData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SybShopingCartllAdapter extends BaseAdapter {
	private ArrayList<ShopCartData> list;
	private static HashMap<Integer, Boolean> isSelected;
	// 用来导入布局
	private LayoutInflater inflater = null;
	private Context context;
	private ImageLoader loader;
	private WareDao wareDao;
	private ShopCartData cartData;
	private Handler handler;
	public static String total_c, total_c_jian, total_c_jia;
	String quantity;
	boolean statuo;
	public static String cart_id;
	// private List list_id = new ArrayList();
	private List<String> list_id = new ArrayList<String>();
	// 用来控制CheckBox的选中状况
	public static StringBuffer sb;
	public static int id;

	// 构造器
	public SybShopingCartllAdapter(ArrayList<ShopCartData> list,
								   Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
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

	public static class ViewHolder {
		TextView tv;
		public CheckBox cb;

		ImageButton btn_order_cancle;
		ImageView img_ware;
		TextView tv_warename;
		TextView tv_color;
		TextView tv_1;
		TextView tv_2;
		TextView tv_size;
		ImageButton btn_reduce;
		EditText et_number;
		ImageButton btn_add;
		TextView tv_money;
		TextView market_information_seps_add;
		TextView market_information_seps_del;
		TextView market_information_seps_num;
		CheckBox shopcart_item_check;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			ViewHolder holder = null;
			if (convertView == null) {

				System.out.println("6================");
				holder = new ViewHolder();
				convertView = RelativeLayout.inflate(context,
						R.layout.listitem_shopping_cart, null);
				// convertView = inflater.inflate(R.layout.listviewitem, null);
				holder.btn_order_cancle = (ImageButton) convertView
						.findViewById(R.id.cb_style);
				holder.img_ware = (ImageView) convertView
						.findViewById(R.id.img_ware);
				holder.tv_warename = (TextView) convertView
						.findViewById(R.id.tv_ware_name);
				holder.tv_color = (TextView) convertView
						.findViewById(R.id.tv_color);
				holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
				holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
				holder.tv_size = (TextView) convertView
						.findViewById(R.id.tv_size);
				holder.btn_reduce = (ImageButton) convertView
						.findViewById(R.id.img_btn_reduce);
				holder.et_number = (EditText) convertView
						.findViewById(R.id.et_number);
				holder.btn_add = (ImageButton) convertView
						.findViewById(R.id.img_btn_add);
				holder.tv_money = (TextView) convertView
						.findViewById(R.id.tv_money);
				holder.btn_order_cancle = (ImageButton) convertView
						.findViewById(R.id.cb_style);
				// holder.cb = (CheckBox)
				// convertView.findViewById(R.id.item_cb);
				// 关于数量
				holder.market_information_seps_add = (TextView) convertView
						.findViewById(R.id.market_information_seps_add);// 增加
				holder.market_information_seps_del = (TextView) convertView
						.findViewById(R.id.market_information_seps_del);// 减少
				holder.market_information_seps_num = (TextView) convertView
						.findViewById(R.id.market_information_seps_num);// 个数
				// holder.market_information_seps_num.setText("1");
				convertView.setTag(holder);

			} else {
				// 取出holder
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_warename.setText(list.get(position).getTitle());
			holder.tv_color.setText("￥" + list.get(position).getSell_price());
			holder.tv_size.setText("￥" + list.get(position).getMarket_price());
			holder.et_number.setText(list.get(position).getQuantity() + "");
			ImageLoader imageLoaderll = ImageLoader.getInstance();
			imageLoaderll
					.displayImage(RealmName.REALM_NAME_HTTP
							+ list.get(position).getImg_url(), holder.img_ware);
			holder.tv_size.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

			double a = 0;
			for (int i = 0; i < list.size(); i++) {
				String price = list.get(i).sell_price;
				int number = list.get(i).getQuantity();
				BigDecimal c = new BigDecimal(Double.parseDouble(price)
						* number);
				// 保留2位小数
				double total_c_ll = c.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				// System.out.println("价格是多少0============="+total_c);
				a += total_c_ll;
			}
			try {
				Message message = new Message();
				message.what = 1;
				// message2.obj = total_c;
				handler.sendMessage(message);
				total_c = Double.toString(a);
				// System.out.println("===总计======="+a);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			holder.btn_order_cancle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int orderid = Integer.parseInt(list.get(position).getId());
					Message message = new Message();
					message.what = 400;
					message.obj = position;
					message.arg1 = orderid;
					handler.sendMessage(message);

				}
			});

			holder.btn_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						changeAdd(position);

						// 商品数量或者类型改变 刷新计数页面
						// cartData = wareDao.findResult();
						Message message2 = new Message();
						message2.what = 200;
						message2.obj = total_c;
						handler.sendMessage(message2);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});

			holder.btn_reduce.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						changeReduce(position);

						// 商品数量或者类型改变 刷新计数页面
						// cartData = wareDao.findResult();
						Message message2 = new Message();
						message2.what = 200;
						message2.obj = total_c;
						handler.sendMessage(message2);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});
			// // 监听checkBox并根据原来的状态来设置新的状态
			// holder.cb.setOnClickListener(new View.OnClickListener() {
			//
			// public void onClick(View v) {
			//
			// if (isSelected.get(position)) {
			// isSelected.put(position, false);
			// setIsSelected(isSelected);
			// String cart_id = list.get(position).getId();
			// list_id.remove(cart_id);
			// System.out.println("1111================");
			// id = list_id.size();
			// // Message message2 = new Message();
			// // message2.what = 400;
			// //// message2.obj = id;
			// // handler.sendMessage(message2);
			// Message message = new Message();
			// message.what = 2;
			// // message.obj = id;
			// handler.sendMessage(message);
			// } else {
			// try {
			//
			// isSelected.put(position, true);
			// setIsSelected(isSelected);
			// String cart_id = list.get(position).getId();
			// list_id.add(cart_id);
			// System.out.println("2222================"+list_id.size());
			// id = list_id.size();
			// System.out.println("2222111111================"+id);
			// // Message message2 = new Message();
			// // message2.what = 400;
			// //// message2.obj = id;
			// // handler.sendMessage(message2);
			// Message message = new Message();
			// message.what = 2;
			// handler.sendMessage(message);
			//
			// } catch (Exception e) {
			// // TODO: handle exception
			// e.printStackTrace();
			// }
			// }
			// for(int i=0;i<list_id.size();i++){
			// System.out.println("=====测试答案1======================="+list_id.get(i));
			// sb = new StringBuffer();
			// sb.append(list_id.get(i));
			// System.out.println("===================="+sb);
			// // String zhou= EspUtils.EncodeBase64(sb.toString());
			// }
			//
			// // List<String> listll = new ArrayList<String>();
			// // //list中添对象
			// // listll.add("a");
			// // listll.add("b");
			// // listll.add("c");
			// // StringBuffer sb = new StringBuffer();
			// // for(String s:listll){
			// // sb.append(s+",");
			// // }
			// // System.out.println(""+sb.toString());
			// //
			// // List<String> list = new ArrayList<String>();
			// // list.add("a1");
			// // list.add("a2");
			// // String[] toBeStoredll = list_id.toArray(new
			// String[list_id.size()]);
			// // for(String s : toBeStoredll) {
			// // System.out.println("转数组2============"+s);
			// // }
			// //
			// String[] str=new String[list_id.size()];
			// int i=0;
			// for(Object obj:list_id){ //jdk5.0的高级循环
			// str[i++]=obj.toString();
			// }
			// System.out.println("转数组22======================="+str+",");
			//
			// String[] toBeStored = list_id.toArray(new
			// String[list_id.size()]);
			// for(String s : toBeStored) {
			// System.out.println("转数组3======================="+s);
			// }
			//
			// // Collections.sort(list_id,new Comparator() {
			// //
			// // @Override
			// // public int compare(Object arg0, Object arg1) {
			// // // TODO Auto-generated method stub
			// //
			// // return 0;
			// // }
			// //
			// // });
			//
			// // Collections.sort(list, new PriceComparator()){
			// // public int compare(Object object1, Object object2) {//
			// 实现接口中的方法
			// // Book p1 = (Book) object1; // 强制转换
			// // Book p2 = (Book) object2;
			// // return new Double(p2.price).compareTo( new Double(p1.price));
			// // }
			// // });
			// // Collections.sort(list,new Comparator(){
			// // public int compare(Object obj1,Object obj2){
			// // Person a=(Person)obj1;
			// // Person b=(Person)obj2;
			// // int age1=a.getAge();
			// // int age2=b.getAge();
			// // if(age1==age2){return 0;}
			// // if(age1>age2){return 1;}
			// // return -1;
			// // }
			// // });
			//
			// // List<String>listl = new ArrayList<String>();
			// // listl.add("1");
			// // listl.add("2");
			// // listl.add("3");
			// // listl.add("4");
			//
			// // Collections.sort(listl,new Comparator<String>(){
			// // @Override
			// // public int compare(String arg0, String arg1) {
			// // // TODO Auto-generated method stub
			// // return arg0.geto.compareTo(arg1.getOrder());;
			// // }
			// // });
			//
			// // for(String u : listll){
			// // System.out.println(u.getName());
			// // }
			//
			// // System.out.println("转数组3======================="+toBeStored);
			// // notifyDataSetChanged();
			// // Message message = new Message();
			// // message.what = 3;
			// // message.obj = toBeStoredll;
			// // handler.sendMessage(message);
			//
			// }
			// });
			// 根据isSelected来设置checkbox的选中状况
			// holder.cb.setChecked(getIsSelected().get(position));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		SybShopingCartllAdapter.isSelected = isSelected;
	}

	public void deleteData(int index) {
		String orderid = list.get(index).orderid + "";
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
			BigDecimal c = new BigDecimal(Double.parseDouble(price) * number);
			// 保留2位小数
			double total_c_ll = c.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			total_c = Double.toString(total_c_ll);
			System.out.println("价格是多少=============" + total_c);
			String cart_id = list.get(index).getId();
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/cart_goods_update?cart_id=" + cart_id + "&user_id="
							+ 19 + "&quantity=" + number + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							// System.out.println("==========================访问接口成功！"+arg1);
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
				list.get(index)
						.setQuantity((list.get(index).getQuantity()) - 1);
				notifyDataSetChanged();

				String price = list.get(index).sell_price;
				int number = list.get(index).getQuantity();
				BigDecimal c = new BigDecimal(Double.parseDouble(price)
						* number);
				// 保留2位小数
				double total_c_ll = c.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				total_c = Double.toString(total_c_ll);
				System.out.println("价格是多少1=============" + total_c);
				String cart_id = list.get(index).getId();
				AsyncHttp.get(RealmName.REALM_NAME_LL
								+ "/cart_goods_update?cart_id=" + cart_id + "&user_id="
								+ 19 + "&quantity=" + number + "",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								// System.out.println("==========================2访问接口成功！"+arg1);
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

}
