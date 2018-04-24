package com.android.hengyu.pub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class JuyunshangAdapter extends BaseAdapter {

	private ArrayList<GoodsListData> lists;
	private ArrayList<shangpingListData> lists_ll;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;

	public JuyunshangAdapter(ArrayList<GoodsListData> lists,
							 ArrayList<shangpingListData> lists_ll, Context context,
							 ImageLoader loader) {

		try {

			this.context = context;
			this.lists = lists;
			this.lists_ll = lists_ll;
			this.loader = loader;
			this.inflater = LayoutInflater.from(context);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	public int getCount() {

		// return list.size();
		return lists.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LinearLayout.inflate(context,R.layout.tuijian_yunshangju_time, null);
		}

		try {
			LinearLayout ll_shangping = (LinearLayout) convertView.findViewById(R.id.ll_shangping);
			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
			// TextView tv_rePrice = (TextView)
			// convertView.findViewById(R.id.tv_hengyu_money);
			// TextView tv_maPrice = (TextView)
			// convertView.findViewById(R.id.tv_market_money);
			ImageView img_ware = (ImageView) convertView
					.findViewById(R.id.img_ware);

			ImageView iv_biaoti1 = (ImageView) convertView
					.findViewById(R.id.iv_biaoti1);
			ImageView iv_biaoti2 = (ImageView) convertView
					.findViewById(R.id.iv_biaoti2);
			ImageView iv_biaoti3 = (ImageView) convertView
					.findViewById(R.id.iv_biaoti3);
			// TextView tv_id = (TextView)
			// convertView.findViewById(R.id.tv_ware_id);

			tv_name.setText(lists.get(position).name);
			// tv_name.setText(lists.get(position).title);
			// tv_rePrice.setText("￥" + lists.get(position).getSell_price());
			// tv_maPrice.setText("￥" + lists.get(position).getMarket_price());
			// tv_rePrice.setText("￥" + lists.get(position).sell_price);
			// tv_maPrice.setText("￥" + lists.get(position).market_price);
			// tv_id.setText(list.get(position).id + "");

			loader.displayImage(RealmName.REALM_NAME_HTTP+ lists.get(position).img_url, img_ware);

			System.out.println("======================="+position);
			//			if (position == 0) {
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti1);
			////				loader.displayImage(
			////						RealmName.REALM_NAME_HTTP
			////								+ lists_ll.get(position).img_url, iv_biaoti2);
			//				System.out.println("11======================="+position);
			//			}
			//
			//			if (position == 1) {
			//				loader.displayImage(
			//				RealmName.REALM_NAME_HTTP
			//						+ lists_ll.get(position).img_url, iv_biaoti2);
			//				System.out.println("22======================="+position);
			//			}

			//          String zhouString = lists_ll.get(position).si;
			//			if (!lists_ll.get(position).equals("")) {

			//			for (int i = 0; i < lists_ll.size(); i++) {
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti1);
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti2);
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti3);
			//			}

			//			int zhou = lists_ll.size();

			//			if (lists_ll.size() > 0) {
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti1);
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti2);
			//				loader.displayImage(
			//						RealmName.REALM_NAME_HTTP
			//								+ lists_ll.get(position).img_url, iv_biaoti3);
			//			} else {
			//				ll_shangping.setVisibility(View.GONE);
			//			}

			//			iv_biaoti1.setOnClickListener(new OnClickListener() {
			//
			//				@Override
			//				public void onClick(View arg0) {
			//
			//					Intent intent = new Intent(context,
			//							WareInformationActivity.class);
			//					intent.putExtra("id", lists_ll.get(position).id);
			//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//					context.startActivity(intent);
			//				}
			//			});
			//			iv_biaoti2.setOnClickListener(new OnClickListener() {
			//
			//				@Override
			//				public void onClick(View arg0) {
			//
			//					Intent intent = new Intent(context,
			//							WareInformationActivity.class);
			//					intent.putExtra("id", lists_ll.get(position).id);
			//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//					context.startActivity(intent);
			//				}
			//			});
			//			iv_biaoti3.setOnClickListener(new OnClickListener() {
			//
			//				@Override
			//				public void onClick(View arg0) {
			//
			//					Intent intent = new Intent(context,
			//							WareInformationActivity.class);
			//					intent.putExtra("id", lists_ll.get(position).id);
			//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//					context.startActivity(intent);
			//				}
			//			});

		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

	private final class ViewHolder {
		TextView tv_zhuti;//
		TextView tv_item;//
		ImageView tupian;//
		ImageView iv_item;//
		ListView listview;
		LinearLayout lv_dingdanxq;
	}

}
