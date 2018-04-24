package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.R.layout;
import com.zams.www.WareInformationActivity;

public class JysimgerAdapter extends BaseAdapter {

	private ArrayList<shangpingListData> lists_ll;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;

	public JysimgerAdapter(ArrayList<shangpingListData> lists_ll, Context context,
			ImageLoader loader) {

		try {

			this.context = context;
			this.lists_ll = lists_ll;
			this.loader = loader;
			this.inflater = LayoutInflater.from(context);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void putData(ArrayList<GoodsListData> lists) {
		this.lists_ll = lists_ll;
		this.notifyDataSetChanged();
	}

	public int getCount() {

		// return list.size();
		return lists_ll.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LinearLayout.inflate(context, layout.tuijian_yunshangju_time, null);
		}

		try {
			LinearLayout ll_shangping = (LinearLayout) convertView.findViewById(R.id.ll_shangping);
			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
			ImageView img_ware = (ImageView) convertView
					.findViewById(R.id.img_ware);
			ImageView iv_biaoti1 = (ImageView) convertView
					.findViewById(R.id.iv_biaoti1);
			ImageView iv_biaoti2 = (ImageView) convertView
					.findViewById(R.id.iv_biaoti2);
			ImageView iv_biaoti3 = (ImageView) convertView
					.findViewById(R.id.iv_biaoti3);

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
			String zhou = lists_ll.get(position).user_id;
			System.out.println("1===================="+zhou);
//			if (zhou == 1) {
//				loader.displayImage(
//						RealmName.REALM_NAME_HTTP
//								+ lists_ll.get(position).img_url, iv_biaoti1);
//				System.out.println("2===================="+lists_ll.get(position));
//	 		}
			
			if (lists_ll.get(position).id.equals(1)) {
				loader.displayImage(
						RealmName.REALM_NAME_HTTP
								+ lists_ll.get(position).img_url, iv_biaoti1);
				System.out.println("22===================="+lists_ll.get(position));
			}
			
			if (lists_ll.get(position).equals(1)) {
				loader.displayImage(
						RealmName.REALM_NAME_HTTP
								+ lists_ll.get(position).img_url, iv_biaoti1);
				System.out.println("222===================="+lists_ll.get(position));
			}
			
//			if (lists_ll.size() == 2) {
//				loader.displayImage(
//						RealmName.REALM_NAME_HTTP
//								+ lists_ll.get(position).img_url, iv_biaoti2);
//			}
//			if (lists_ll.size() == 3) {
//				loader.displayImage(
//						RealmName.REALM_NAME_HTTP
//								+ lists_ll.get(position).img_url, iv_biaoti3);
//			}
			
//			int zhou = lists_ll.size();
//			if (!lists_ll.get(position).equals("")) {
				loader.displayImage(
						RealmName.REALM_NAME_HTTP
								+ lists_ll.get(position).img_url, iv_biaoti1);
				loader.displayImage(
						RealmName.REALM_NAME_HTTP
								+ lists_ll.get(position).img_url, iv_biaoti2);
				loader.displayImage(
						RealmName.REALM_NAME_HTTP
								+ lists_ll.get(position).img_url, iv_biaoti3);
//			} else {
//				ll_shangping.setVisibility(View.GONE);
//			}

			iv_biaoti1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(context,
							WareInformationActivity.class);
					intent.putExtra("id", lists_ll.get(position).user_id);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			});
			iv_biaoti2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(context,
							WareInformationActivity.class);
					intent.putExtra("id", lists_ll.get(position).user_id);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			});
			iv_biaoti3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(context,
							WareInformationActivity.class);
					intent.putExtra("id", lists_ll.get(position).user_id);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			});

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
