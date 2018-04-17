package com.hengyushop.airplane.adapter;

import java.util.ArrayList;

import com.lglottery.www.domain.ComboDomain;
import com.lglottery.www.domain.ComboListDomain;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComboListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ComboListDomain> listDomains;
	private ImageLoader imageLoader;
	private Handler handler;

	public ComboListAdapter(Context context,
							ArrayList<ComboListDomain> listDomains, ImageLoader imageLoader,
							Handler handler) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listDomains = listDomains;
		this.imageLoader = imageLoader;
		this.handler = handler;
	}

	/**
	 * 装载数据
	 */
	public void putLists(ArrayList<ComboListDomain> listDomains) {
		this.listDomains = listDomains;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listDomains.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listDomains.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		int len = listDomains.get(index).getDomains().size();
		if (view == null || view.getTag(R.drawable.icon + index) == null) {
			holder = new ViewHolder();

			view = LinearLayout.inflate(context, R.layout.combo_item, null);

			holder.layout = (LinearLayout) view
					.findViewById(R.id.combo_content);
			holder.combo_title_item = (TextView) view
					.findViewById(R.id.combo_title_item);
			holder.item_district = (TextView) view
					.findViewById(R.id.item_district);
			holder.item_position = (TextView) view
					.findViewById(R.id.item_position);

			//
			view.setTag(R.drawable.icon + index);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.icon + index);
		}

		holder.item_position.setText(listDomains.get(index).getBranch_name());
		double distance = Double.parseDouble(listDomains.get(index)
				.getDistance());
		if (distance > 1000) {
			holder.item_district.setText(Math.floor(distance / 1000) + "km");
		} else {
			holder.item_district.setText(Math.floor(distance) + "m");
		}
		holder.combo_title_item.setText(listDomains.get(index).getName());
		ArrayList<ComboDomain> domains = listDomains.get(index).getDomains();
		for (int i = 0; i < len; i++) {

			LinearLayout tempView = (LinearLayout) LinearLayout.inflate(
					context, R.layout.combo_item_content, null);
			// tempView.setLayoutParams(new
			// LayoutParams(LayoutParams.MATCH_PARENT, 100));
			holder.layout.addView(tempView);

			holder.combo_image_item = (ImageView) tempView
					.findViewById(R.id.combo_image_item);
			holder.current_price = (TextView) tempView
					.findViewById(R.id.current_price);
			holder.list_price = (Button) tempView.findViewById(R.id.list_price);
			holder.item_vtag = (TextView) tempView.findViewById(R.id.item_vtag);
			holder.content_id = (LinearLayout) tempView
					.findViewById(R.id.content_id);
			// holder.item_vtag.setText(domains.get(index).getName());

			holder.current_price.setText(domains.get(i).getCurrent_price()
					+ "元");
			holder.list_price.setText(domains.get(i).getList_price() + "元");
			holder.item_vtag.setText(domains.get(i).getTitle());
			this.imageLoader.displayImage(domains.get(i).getImage_url(),
					holder.combo_image_item);
			final String posi = domains.get(i).getDeal_id();
			holder.content_id.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Message msg = new Message();
					msg.what = 1;
					System.out.println(posi);
					msg.obj = posi;
					handler.sendMessage(msg);
				}
			});
			if (i != len - 1) {
				View view2 = new View(context);
				view2.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, 1));
				view2.setBackgroundColor(R.color.list_item_line);
				holder.layout.addView(view2);
			}
		}
		// holder.layout.setLayoutParams(new LinearLayout.LayoutParams(800,
		// len * 100));
		/*
		 * holder.item_vtag.setText(listDomains.get(index).getName());
		 * holder.combo_title_item.setText(listDomains.get(index).getTitle());
		 * holder
		 * .current_price.setText(listDomains.get(index).getCurrent_price() +
		 * "元");
		 * holder.list_price.setText(listDomains.get(index).getList_price() +
		 * "元");
		 * 
		 * this.imageLoader.displayImage(listDomains.get(index).getImage_url(),
		 * holder.combo_image_item);
		 */

		return view;
	}

	public class ViewHolder {
		TextView combo_title_item, item_position, item_district, item_vtag,
				current_price;
		Button list_price;
		ImageView combo_image_item;
		LinearLayout layout, content_id;
	}
	/*
	 * holder.combo_image_item = (ImageView) view
	 * .findViewById(R.id.combo_image_item);
	 * 
	 * holder.current_price = (TextView) view .findViewById(R.id.current_price);
	 * holder.list_price = (TextView) view.findViewById(R.id.list_price);
	 * holder.item_vtag = (TextView) view.findViewById(R.id.item_vtag);
	 */
}
