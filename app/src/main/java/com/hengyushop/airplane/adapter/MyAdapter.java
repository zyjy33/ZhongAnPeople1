package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.ShopCartData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {
	// 填充数据的list
	private ArrayList<ShopCartData> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
	// 上下文
	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;
	public static List list_id = new ArrayList();
	private Handler handler;

	// 构造器
	public MyAdapter(ArrayList<ShopCartData> list, Context context) {
		this.context = context;
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
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			// convertView = inflater.inflate(R.layout.listitem_shopping_cart,
			// null);//listviewitem
			convertView = inflater.inflate(R.layout.listviewitem, null);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			holder.img_ware = (ImageView) convertView
					.findViewById(R.id.img_ware);
			holder.tv_warename = (TextView) convertView
					.findViewById(R.id.tv_ware_name);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_money = (TextView) convertView
					.findViewById(R.id.tv_money2);

			// 关于数量
			holder.market_information_seps_add = (TextView) convertView
					.findViewById(R.id.market_information_seps_add);// 增加
			holder.market_information_seps_del = (TextView) convertView
					.findViewById(R.id.market_information_seps_del);// 减少
			holder.market_information_seps_num = (TextView) convertView
					.findViewById(R.id.market_information_seps_num);// 个数
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_warename.setText(list.get(position).getTitle());
		holder.tv_size.setText("￥" + list.get(position).getSell_price());
		holder.tv_money.setText("￥" + list.get(position).getMarket_price());
		holder.market_information_seps_num.setText(list.get(position)
				.getQuantity() + "");
		ImageLoader imageLoaderll = ImageLoader.getInstance();
		imageLoaderll.displayImage(
				RealmName.REALM_NAME_HTTP + list.get(position).getImg_url(),
				holder.img_ware);
		holder.tv_size.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

		// 根据isSelected来设置checkbox的选中状况
		holder.cb.setChecked(getIsSelected().get(position));

		// 设置list中TextView的显示
		// holder.tv.setText(list.get(position).getTitle());
		// 根据isSelected来设置checkbox的选中状况

		// 监听checkBox并根据原来的状态来设置新的状态
		holder.cb.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (isSelected.get(position)) {
					isSelected.put(position, false);
					setIsSelected(isSelected);
					String cart_id = list.get(position).getId();
					list_id.remove(cart_id);
					System.out.println("1111================");
				} else {
					isSelected.put(position, true);
					setIsSelected(isSelected);
					String cart_id = list.get(position).getId();
					list_id.add(cart_id);
					System.out.println("2222================");
				}
				for (int i = 0; i < list_id.size(); i++) {
					System.out.println("=====测试答案1======================="
							+ list_id.get(i));
				}
				// Message message2 = new Message();
				// message2.what = 400;
				// message2.obj = list_id;
				// handler.sendMessage(message2);

			}
		});

		holder.cb.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyAdapter.isSelected = isSelected;
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
}