package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class SpcsAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList datatb1;
	private ImageLoader imageLoader;

	public SpcsAdapter(ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.imageLoader = imageLoader;
	}

	public SpcsAdapter(ArrayList datatb1, Context mContext) {
		super();
		this.datatb1 = datatb1;
		this.mContext = mContext;
		Log.i("datatb1", datatb1 + "");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datatb1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datatb1.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {

			// TODO: handle exception
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gridview_item0, parent, false);
			}

			TextView tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);

			tv.setText((String) datatb1.get(position));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}

}
