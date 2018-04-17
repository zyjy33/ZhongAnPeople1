package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.hengyushop.entity.JuTuanGouData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

/**
 * Adapter
 */
public class CanTuanAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<JuTuanGouData> list;
	private Handler handler;
	private ImageLoader imageLoader;
	public static String tuangoujia, tuanshu;

	public CanTuanAdapter(ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.imageLoader = imageLoader;
	}

	public CanTuanAdapter(ArrayList<JuTuanGouData> list, Context mContext,
						  Handler handler) {
		super();
		this.list = list;
		this.handler = handler;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.cantuanjia_item, parent, false);
		}
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_renshu = (TextView) convertView
				.findViewById(R.id.tv_renshu);

		// TextView tv_name = BaseViewHolder.get(convertView, R.id.tv_name);
		// TextView tv_renshu = BaseViewHolder.get(convertView, R.id.tv_renshu);
		TextView tv_city = BaseViewHolder.get(convertView, R.id.tv_city);
		TextView tv_time = BaseViewHolder.get(convertView, R.id.tv_time);
		TextView tv_qucantuan = BaseViewHolder.get(convertView,
				R.id.tv_qucantuan);

		// ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);

		System.out.println("position====================" + position);
		tv_renshu.setText("还差" + list.get(position).getQuantity() + "人成团");
		System.out.println("position===================="
				+ list.get(position).getQuantity());
		System.out.println("position===================="
				+ list.get(position).getUser_name());
		String user_name = list.get(position).getUser_name();
		System.out.println("user_name====================" + user_name);
		tv_name.setText(user_name);
		// ImageLoader imageLoader=ImageLoader.getInstance();
		// imageLoader.displayImage((String)
		// Config.URL_IMG+datatupian.get(position),iv);

		tv_qucantuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		return convertView;
	}

}
