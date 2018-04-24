package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Color;
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
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList data1;
	private ArrayList data2;
	public static int clickTemp = 0;
	private ImageLoader imageLoader;

	// public MyGridAdapter(ImageLoader imageLoader) {
	//
	// this.imageLoader = imageLoader;
	// }

	public MyGridAdapter(ArrayList data1, ArrayList data2, Context mContext) {
		super();
		try {
			this.data1 = data1;
			this.data2 = data2;
			this.mContext = mContext;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {

		return data2.size();
	}

	@Override
	public Object getItem(int position) {

		return data2.get(position);
	}

	public static void setSeclection(int position) {
		System.out.println("=====clickTemp=====================" + clickTemp);
		clickTemp = position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gridview_item0, parent, false);
		}
		try {

			TextView tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);
			// ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
			// System.out.println("=====4====================="+position);

			// if (position == 0) {
			// tv.setText("全部");
			// }

			tv.setText((String) data2.get(position));

			if (clickTemp == position) {
				System.out.println("====红色=====================" + clickTemp);
				// convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang
				tv.setTextColor(Color.RED);
			} else {
				System.out.println("====灰色=====================" + clickTemp);
				// convertView.setBackgroundColor(Color.TRANSPARENT);
				// convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang
				tv.setTextColor(Color.GRAY);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		// ImageLoader imageLoader=ImageLoader.getInstance();
		// imageLoader.displayImage((String)
		// Config.URL_IMG+datatupian.get(position),iv);
		return convertView;
	}

}
