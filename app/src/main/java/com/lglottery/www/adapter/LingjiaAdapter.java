package com.lglottery.www.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.json.LingjiaDo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class LingjiaAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<LingjiaDo> lists;
	private ImageLoader imageLoader;

	public LingjiaAdapter(Context context, ArrayList<LingjiaDo> lists,
						  ImageLoader imageLoader) {

		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;

	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void putList(ArrayList<LingjiaDo> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {

		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.lingjia_item, null);
			holder.img = (ImageView) view.findViewById(R.id.img);
			holder.text1 = (TextView) view.findViewById(R.id.text1);
			holder.text2 = (TextView) view.findViewById(R.id.text2);
			holder.text3 = (TextView) view.findViewById(R.id.text3);
			holder.text4 = (TextView) view.findViewById(R.id.text4);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		imageLoader.displayImage(
				RealmName.REALM_NAME + "/" + lists.get(position).getImg(),
				holder.img);
		holder.text1.setText(lists.get(position).getName());
		holder.text2.setText(lists.get(position).getPrice() + "元+"
				+ lists.get(position).getJifen() + "福利");
		holder.text3.setText(0 + "人已购买");
		holder.text4.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		holder.text4.setText("市场价:" + lists.get(position).getGoods());
		return view;
	}

	public class ViewHolder {
		ImageView img;
		TextView text1, text2, text3, text4;
	}

}
