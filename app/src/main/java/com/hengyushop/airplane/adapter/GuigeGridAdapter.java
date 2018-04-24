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
public class GuigeGridAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList data2;
	private int clickTemp = -1;
	// private ArrayList datatupian;
	private ImageLoader imageLoader;

	public GuigeGridAdapter(ImageLoader imageLoader) {

		this.imageLoader = imageLoader;
	}

	public GuigeGridAdapter(ArrayList data2, Context mContext) {
		super();
		this.data2 = data2;
		// this.datatupian = datatupian;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {

		return data2.size();
	}

	@Override
	public Object getItem(int position) {

		return data2.get(position);
	}

	public void setSeclection(int position) {
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

		TextView tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);
		// ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);

		tv.setText((String) data2.get(position));

		if (clickTemp == position) {
			// convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang
			tv.setTextColor(Color.RED);
		} else {
			// convertView.setBackgroundColor(Color.TRANSPARENT);
			// convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang
			tv.setTextColor(Color.GRAY);

		}

		// ImageLoader imageLoader=ImageLoader.getInstance();
		// imageLoader.displayImage((String)
		// Config.URL_IMG+datatupian.get(position),iv);
		return convertView;
	}

}
