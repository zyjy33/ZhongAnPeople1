package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.hengyushop.demo.home.JuJingCaiXqActivity;
import com.hengyushop.demo.home.JuTuanGouXqActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

import java.util.ArrayList;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class GouwucheAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList datatb1;
	private int clickTemp = -1;
	private ImageLoader imageLoader;
	TextView tv;

	public GouwucheAdapter(ImageLoader imageLoader) {

		this.imageLoader = imageLoader;
	}

	public GouwucheAdapter(ArrayList datatb1, Context mContext) {
		super();
		this.datatb1 = datatb1;
		this.mContext = mContext;
		Log.i("datatb1", datatb1 + "");
	}

	@Override
	public int getCount() {

		return datatb1.size();
	}

	@Override
	public Object getItem(int position) {

		return datatb1.get(position);
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
		try {


			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gridview_item0, parent, false);
			}

			tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);
			tv.setText((String) datatb1.get(position));

			System.out.println("WareInformationActivity--------------------"
					+ WareInformationActivity.spec_text_list);
			System.out.println("JuJingCaiXqActivity--------------------"
					+ JuJingCaiXqActivity.spec_text_list);
			System.out.println("JuTuanGouXqActivity--------------------"
					+ JuTuanGouXqActivity.spec_text_list);
			if (WareInformationActivity.spec_text_list == 1) {
				if (WareInformationActivity.spec_text.contains((String) datatb1
						.get(position))) {
					tv.setTextColor(Color.RED);
					tv.setBackgroundResource(R.drawable.juyunshang_bk);
				}
			} else if (JuJingCaiXqActivity.spec_text_list == 2) {
				if (JuJingCaiXqActivity.spec_text.contains((String) datatb1
						.get(position))) {
					tv.setTextColor(Color.RED);
					tv.setBackgroundResource(R.drawable.juyunshang_bk);
				}
			} else if (JuTuanGouXqActivity.spec_text_list == 3) {
				if (JuTuanGouXqActivity.spec_text.contains((String) datatb1
						.get(position))) {
					tv.setTextColor(Color.RED);
					tv.setBackgroundResource(R.drawable.juyunshang_bk);
				}
			} else {
				if (clickTemp == position) {
					// convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang
					// zangfutiaoli
					// convertView =
					// LayoutInflater.from(mContext).inflate(R.layout.gridview_item1,
					// parent, false);
					// TextView tv_zhuti = BaseViewHolder.get(convertView,
					// R.id.btn_aaa1);
					// tv.setText((String) datatb1.get(position));
					tv.setTextColor(Color.RED);
					tv.setBackgroundResource(R.drawable.juyunshang_bk);
				} else {
					// convertView =
					// LayoutInflater.from(mContext).inflate(R.layout.gridview_item0,
					// parent, false);
					// TextView tv1 = BaseViewHolder.get(convertView,
					// R.id.btn_aaa1);
					// tv.setText((String) datatb1.get(position));
					tv.setTextColor(Color.GRAY);
					tv.setBackgroundResource(R.drawable.zangfutiaoli);
				}
			}

			tv.setText((String) datatb1.get(position));
		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

}
