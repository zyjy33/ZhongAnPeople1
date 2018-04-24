package com.hengyushop.airplane.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeData;
import com.lglottery.www.domain.DistrictsDomain;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.R.layout;

/**
 * @Description:gridviewAdapter
 * @author http://blog.csdn.net/finddreams
 */
public class GuigeListlAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList data;
	private ArrayList data1;

	public GuigeListlAdapter(Context context, ArrayList data, ArrayList data1) {

		this.data = data;
		this.data1 = data1;
		this.mContext = context;
		this.inflater = LayoutInflater.from(context);
		// System.out.println("=====42====================="+list.size());
	}

	@Override
	public int getCount() {

		// Log.i("data", "=============" + list.size());
		return data.size();
	}

	@Override
	public Object getItem(int position) {

		return data.get(position);

	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup patent) {
		try {
			System.out.println("=====51=====================");

			convertView = inflater.inflate(R.layout.guige_item, null);
			LinearLayout addview = (LinearLayout) convertView
					.findViewById(R.id.addview);
			TextView tv_zhuti = (TextView) convertView
					.findViewById(R.id.tv_zhuti);
			tv_zhuti.setText((String) data.get(position));

			// System.out.println("=====6====================="+zhou);

			MyGridView gridView = (MyGridView) convertView
					.findViewById(R.id.gridView);
			GouwucheAdapter MyAdapter = new GouwucheAdapter(data1, mContext);
			gridView.setAdapter(MyAdapter);
			addview.removeAllViews();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

}
