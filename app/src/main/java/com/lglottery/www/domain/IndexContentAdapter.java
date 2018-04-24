package com.lglottery.www.domain;

import java.util.ArrayList;

import com.android.hengyu.web.RealmName;
import com.hengyushop.json.LingjiaDo;
import com.lglottery.www.common.U;
import com.lglottery.www.domain.Lglottery_Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IndexContentAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<IndexDomain> lists;
	private ImageLoader imageLoader;

	public IndexContentAdapter(Context context, ArrayList<IndexDomain> lists,
			ImageLoader imageLoader) {

		this.context = context;
		this.lists = lists;
		this.imageLoader = imageLoader;

	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void putList(ArrayList<IndexDomain> lists) {
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
			holder.item1 = (ImageView) view.findViewById(R.id.img);
			holder.item2 = (TextView) view.findViewById(R.id.text1);
			holder.item3 = (TextView) view.findViewById(R.id.text2);
			holder.item4 = (TextView) view.findViewById(R.id.text3);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		imageLoader.displayImage(RealmName.REALM_NAME+"/"+lists.get(position).getImg(), holder.item1);
		holder.item2.setText(lists.get(position).getName());
		holder.item3.setText(lists.get(position).getCurrent());
		holder.item4.setText(lists.get(position).getPrice());
		 
		return view;
	}

	public class ViewHolder {
		ImageView item1;
		TextView item2, item3, item4 ;
	}

}
