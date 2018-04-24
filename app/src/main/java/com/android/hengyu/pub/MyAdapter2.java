package com.android.hengyu.pub;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hengyushop.entity.JuTuanGouData;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends BaseAdapter {

	private Context mContext;
	private List<JuTuanGouData> List;
	private ArrayList<String> list2;
	private LayoutInflater mInflater;
	private int clickTemp = 0;
	//	private int clickTemp = -1;

	public MyAdapter2(Context context, List<JuTuanGouData> list, ArrayList<String> list2){
		this.List = list;
		this.list2 = list2;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		//		System.out.println("============="+list.size()+1);
	}

	@Override
	public int getCount() {
		if (List.size()<1) {
			return 0;
		}else{
			return List.size();

		}
	}

	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public Object getItem(int position) {

		return List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder =  new ViewHolder();
			convertView = mInflater.inflate(R.layout.jys_leibie_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.text = (TextView) convertView.findViewById(R.id.tv);
			//			holder.radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		//		holder.img.setImageResource(dataList.get(position));
		//		holder.text.setText("第" + position + "项");
		//		clickTempll = position;

		try {
			System.out.println("List======================"+List.size());



			System.out.println("个数1======================"+position);
			//		 String weizhi = list2.get(position);
			if (position == 0) {
				holder.img.setImageResource(R.drawable.jujc);
				holder.text.setText(List.get(position).title);
			}else if (position == 1) {
				holder.img.setImageResource(R.drawable.jutg);
				holder.text.setText(List.get(position).title);
			}else if (position == 2) {
				holder.img.setImageResource(R.drawable.yust);
				holder.text.setText(List.get(position).title);
			}
			if (position == 3) {
				holder.img.setImageResource(R.drawable.yyjb2);
				holder.text.setText("一元聚币");
			}

			//        ImageLoader imageLoader=ImageLoader.getInstance();
			//        imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+List.get(position).icon_url,holder.img);


		} catch (Exception e) {

			e.printStackTrace();
		}
		if (clickTemp == position) {
			holder.text.setTextColor(Color.RED);
		} else {
			holder.text.setTextColor(Color.GRAY);
		}
		return convertView;
	}


	class ViewHolder{
		ImageView img;
		TextView text;
		RadioButton radioButton;
	}
}
