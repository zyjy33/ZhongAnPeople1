package com.android.hengyu.pub;

import java.util.ArrayList;

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

public class JuyunshangAdapterll extends BaseAdapter {

	private ArrayList<shangpingListData> lists_ll;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;

	public JuyunshangAdapterll(ArrayList<shangpingListData> lists_ll,
			Context context, ImageLoader loader) {
		// TODO Auto-generated constructor stub
		try {

			this.context = context;
			this.lists_ll = lists_ll;
			this.loader = loader;
			this.inflater = LayoutInflater.from(context);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return list.size();
		return lists_ll.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context, R.layout.yhzstp_item,
					null);
		}
		try {
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item);

			loader.displayImage(
					RealmName.REALM_NAME_HTTP + lists_ll.get(position).img_url,
					iv);

			System.out.println("=======================" + position);

		} catch (Exception e) {
			// TODO: handle exception
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
