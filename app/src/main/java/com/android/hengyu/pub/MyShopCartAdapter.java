package com.android.hengyu.pub;

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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.ShopCarts;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class MyShopCartAdapter extends BaseAdapter {

	private ArrayList<ShopCarts> list = null;
	private Context context;
	private ImageLoader loader;
	private WareDao wareDao;
	private ShopCartData cartData;
	private Handler handler;

	public MyShopCartAdapter(ArrayList<ShopCarts> list, Context context,
							 Handler handler, ImageLoader loader) {
		this.context = context;
		this.list = list;
		this.handler = handler;
		this.loader = loader;
		wareDao = new WareDao(context);
	}

	public void putData(ArrayList<ShopCarts> list) {
		this.list = list;
		notifyDataSetInvalidated();
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public class ViewHolder {
		ImageView shopcart_item_ico, shopcart_item_clear;
		TextView shopcart_item_text, shopcart_item_market, item_type,
				shopcart_item_num_tip, shopcart_item_price, shopcart_item_del,
				shopcart_item_add;
		LinearLayout shop_check_layout, shop_con, shopcart_item_cag;
		CheckBox shopcart_item_check;
		TextView shopcart_item_status;
		EditText shopcart_item_num;
		RelativeLayout view0, view1;
	}

	private void addView(String param, LinearLayout layout, boolean flag,
						 boolean isEnd) {
		if (param != null) {
			if (param.length() != 0) {
				TextView textView = new TextView(context);
				if (flag) {
					textView.setText(param + "/");
				} else {
					textView.setText(param + ":");
					if (isEnd) {
						textView.setText(param);

					}

				}
				textView.setTextSize(10);
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.leftMargin = 1;
				params.rightMargin = 1;
				textView.setLayoutParams(params);
				layout.addView(textView);
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;
		if (convertView == null
				|| convertView.getTag(R.drawable.icon + position) == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.shopcart_view, null);
			viewHolder = new ViewHolder();
			viewHolder.shop_con = (LinearLayout) convertView
					.findViewById(R.id.shop_con);
			viewHolder.item_type = (TextView) convertView
					.findViewById(R.id.item_type);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag(R.drawable.icon
					+ position);
		}

		viewHolder.item_type.setText(list.get(position).getName());
		final ArrayList<ShopCartData> datas = list.get(position).getList();
		int len = datas.size();
		for (int i = 0; i < len; i++) {
			LinearLayout tempView = (LinearLayout) LinearLayout.inflate(
					context, R.layout.shopcart_content_item, null);
			viewHolder.shop_con.addView(tempView);
			viewHolder.shopcart_item_ico = (ImageView) tempView
					.findViewById(R.id.shopcart_item_ico);
			viewHolder.shopcart_item_market = (TextView) tempView
					.findViewById(R.id.shopcart_item_market);
			viewHolder.shopcart_item_price = (TextView) tempView
					.findViewById(R.id.shopcart_item_price);
			viewHolder.shopcart_item_text = (TextView) tempView
					.findViewById(R.id.shopcart_item_text);
			viewHolder.shop_check_layout = (LinearLayout) tempView
					.findViewById(R.id.shop_check_layout);
			viewHolder.shopcart_item_check = (CheckBox) tempView
					.findViewById(R.id.shopcart_item_check);
			viewHolder.shopcart_item_status = (TextView) tempView
					.findViewById(R.id.shopcart_item_status);

			viewHolder.shopcart_item_del = (TextView) tempView
					.findViewById(R.id.shopcart_item_del);
			viewHolder.shopcart_item_add = (TextView) tempView
					.findViewById(R.id.shopcart_item_add);
			viewHolder.shopcart_item_num = (EditText) tempView
					.findViewById(R.id.shopcart_item_num);
			viewHolder.shopcart_item_clear = (ImageView) tempView
					.findViewById(R.id.shopcart_item_clear);
			viewHolder.shopcart_item_num_tip = (TextView) tempView
					.findViewById(R.id.shopcart_item_num_tip);
			viewHolder.view0 = (RelativeLayout) tempView
					.findViewById(R.id.view0);
			viewHolder.view1 = (RelativeLayout) tempView
					.findViewById(R.id.view1);
			viewHolder.shopcart_item_cag = (LinearLayout) tempView
					.findViewById(R.id.shopcart_item_cag);
			viewHolder.shopcart_item_market.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			final int index = i;
			viewHolder.shopcart_item_text.setText(datas.get(index).warename);
			// holder.tv_1.setText(list.get(position).stylenameone);
			// holder.tv_2.setText(list.get(position).stylenametwo);
			// holder.tv_color.setText(list.get(position).stylenatureone);
			// holder.tv_size.setText(list.get(position).stylenaturetwo);
			viewHolder.shopcart_item_market.setText("￥"
					+ datas.get(index).marketprice);
			viewHolder.shopcart_item_price.setText("￥"
					+ datas.get(index).retailprice);
			// holder.jifen.setText("聚红包:"+list.get(position).jf);
			viewHolder.shopcart_item_num_tip.setText("X"
					+ datas.get(index).number + "");
			loader.displayImage(
					RealmName.REALM_NAME + "/admin/" + datas.get(index).imgurl,
					viewHolder.shopcart_item_ico);
			addView(datas.get(index).stylenameone,
					viewHolder.shopcart_item_cag, false, false);
			addView(datas.get(index).stylenametwo,
					viewHolder.shopcart_item_cag, true, true);
			addView(datas.get(index).stylenatureone,
					viewHolder.shopcart_item_cag, false, false);
			addView(datas.get(index).stylenaturetwo,
					viewHolder.shopcart_item_cag, false, true);
			if (handler == null) {
				viewHolder.shopcart_item_check.setVisibility(View.GONE);
				viewHolder.shop_check_layout.setVisibility(View.GONE);
			} else {
				if (datas.get(index).uploadStatus) {
					viewHolder.shopcart_item_check.setVisibility(View.VISIBLE);
					viewHolder.shop_check_layout.setVisibility(View.VISIBLE);
					viewHolder.shopcart_item_status.setVisibility(View.GONE);
				} else {
					viewHolder.shopcart_item_check.setVisibility(View.VISIBLE);
					viewHolder.shop_check_layout.setVisibility(View.GONE);
					viewHolder.shopcart_item_status.setVisibility(View.VISIBLE);
					viewHolder.shopcart_item_status.setText("已下架");
					if (datas.get(index).IsDeleted) {
						// viewHolder.shopcart_item_check.setVisibility(View.VISIBLE);
						// viewHolder.shop_check_layout.setVisibility(View.GONE);
						// viewHolder.shopcart_item_status.setVisibility(View.VISIBLE);
						viewHolder.shopcart_item_status.setText("已失效");
					}
				}

			}

			viewHolder.shopcart_item_check.setChecked(datas.get(index).isCheck);
			viewHolder.shopcart_item_check
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton arg0,
													 boolean arg1) {
							datas.get(index).setCheck(arg1);
							notifyDataSetChanged();
							Message msg = new Message();
							msg.what = -2;
							msg.obj = list;
							handler.sendMessage(msg);
						}
					});

			viewHolder.shopcart_item_clear
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							int orderid = datas.get(index).orderid;
							Message message = new Message();
							message.what = 400;
							message.obj = index;
							message.arg1 = orderid;
							handler.sendMessage(message);

						}
					});

			viewHolder.shopcart_item_add
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							changeAdd(datas, position);

							// 商品数量或者类型改变 刷新计数页面
							cartData = wareDao.findResult();
							Message message2 = new Message();
							message2.what = 200;
							message2.obj = cartData;
							handler.sendMessage(message2);
						}
					});

			viewHolder.shopcart_item_del
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							changeReduce(datas, position);

							// 商品数量或者类型改变 刷新计数页面
							cartData = wareDao.findResult();
							Message message2 = new Message();
							message2.what = 200;
							message2.obj = cartData;
							handler.sendMessage(message2);
						}
					});
		}

		return convertView;
	}

	public void deleteData(ArrayList<ShopCartData> list, int index) {
		String orderid = list.get(index).orderid + "";
		wareDao.deleteByOrderid(orderid);
		wareDao.findShopCart();
		list.remove(index);
		notifyDataSetChanged();
	}

	private synchronized void changeAdd(ArrayList<ShopCartData> list, int index) {
		list.get(index).setNumber((list.get(index).number) + 1);
		notifyDataSetChanged();
		String orderid = list.get(index).orderid + "";
		cartData = new ShopCartData();
		cartData.setNumber(list.get(index).number);
		wareDao.updateByOrderid(orderid, cartData);

		UserRegisterData data = wareDao.findIsLoginHengyuCode();
		String yth = data.getHengyuCode();
		String price = list.get(index).retailprice;
		int number = list.get(index).number;

		String str = RealmName.REALM_NAME + "/mi/receiveOrderInfo.ashx?"
				+ "act=UpdateCartInfoNum&yth=" + yth + "&ProductOrderItemId="
				+ orderid + "&productCount=" + number + "&totalProductPrice="
				+ price;
		try {
			// httpToServer.getJsonString(str);
			AsyncHttp.get(str, new AsyncHttpResponseHandler() {

			}, context);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void changeReduce(ArrayList<ShopCartData> list, int index) {
		if (list.get(index).number != 0) {
			list.get(index).setNumber((list.get(index).number) - 1);
		}
		notifyDataSetChanged();
		String orderid = list.get(index).orderid + "";
		cartData = new ShopCartData();
		cartData.setNumber(list.get(index).number);
		wareDao.updateByOrderid(orderid, cartData);

		UserRegisterData data = wareDao.findIsLoginHengyuCode();
		String yth = data.getHengyuCode();
		String price = list.get(index).retailprice;
		int number = list.get(index).number;

		String str = RealmName.REALM_NAME + "/mi/receiveOrderInfo.ashx?"
				+ "act=UpdateCartInfoNum&yth=" + yth + "&ProductOrderItemId="
				+ orderid + "&productCount=" + number + "&totalProductPrice="
				+ price;
		try {
			AsyncHttp.get(str, new AsyncHttpResponseHandler(), context);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 增益
	 *
	 * @param index
	 */
	// private void changeAdd(int index) {
	// if (alldatas.get(index).containsKey("number")) {
	//
	// alldatas.get(index).put(
	// "number",
	// String.valueOf(Integer.parseInt(alldatas.get(index)
	// .get("number").toString()) + 1));
	// notifyDataSetChanged();
	// }
	// }
	//
	// private void changeReduce(int index) {
	// if (alldatas.get(index).containsKey("number")) {
	//
	// if (Integer.parseInt(alldatas.get(index).get("number").toString()) != 0)
	// {
	// alldatas.get(index).put(
	// "number",
	// String.valueOf(Integer.parseInt(alldatas.get(index)
	// .get("number").toString()) - 1));
	// }
	//
	// notifyDataSetChanged();
	// }
	// }

}
