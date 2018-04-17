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

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.EnterpriseData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

	private Context mContext;
	private List<EnterpriseData> List;
	private LayoutInflater mInflater;
	private int clickTemp = 0;
	//	private int clickTemp = -1;

	public MyAdapter(Context context, List<EnterpriseData> list){
		this.List = list;
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
		// TODO Auto-generated method stub
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

			System.out.println("个数1======================"+position);
			//		if (position == 0) {
			////			RadioButton btn = (RadioButton) RadioGroup.inflate(getApplicationContext(), R.layout.common_btn, null);
			////			btn.setText("全部");
			//			holder.text.setText("全部");
			//			holder.img.setImageResource(R.drawable.quanbu);
			////			image.setImageDrawable(getResources().getDrawable(R.drawable.yourimage);
			//		}

			//		if (position > 0) {
			holder.text.setText(List.get(position).title);
			ImageLoader imageLoader=ImageLoader.getInstance();
			imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+List.get(position).icon_url,holder.img);
			//        System.out.println("值是多少2=========="+clickTemp);
			//		}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (clickTemp == position) {
			//			convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang
			holder.text.setTextColor(Color.RED);
		} else {
			//			convertView.setBackgroundColor(Color.TRANSPARENT);
			//			convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang
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
