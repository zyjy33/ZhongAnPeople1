package com.android.hengyu.pub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.WareInformationData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class homeListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<WareInformationData> items;
	private ImageLoader imageLoader;
	AQuery aQuery;

	public homeListAdapter(ArrayList<WareInformationData> items2,
						   Context mContext) {
		super();
		this.items = items2;
		this.mContext = mContext;
		aQuery = new AQuery(mContext);
	}

	@Override
	public int getCount() {

		return items.size();
	}

	@Override
	public Object getItem(int position) {

		return items.get(position);
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
						R.layout.lottery_help, parent, false);
			}
			TextView tv = BaseViewHolder.get(convertView, R.id.tv_zhuti);
			// TextView tv_market_price = BaseViewHolder.get(convertView,
			// R.id.tv_market_price);
			TextView tv_sell_price = BaseViewHolder.get(convertView,
					R.id.tv_sell_price);
			ImageView img_ware = BaseViewHolder.get(convertView, R.id.img_ware);
			// tv_market_price.setText("市场价:￥"+items.get(position).getMarket_price());
			// tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |
			// Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

			if (!items.get(position).img_url.equals("")) {
				tv.setText(items.get(position).title);
				tv_sell_price.setText("价格:￥" + items.get(position).sell_price);
				aQuery.id(img_ware)
						.image(RealmName.REALM_NAME_HTTP
								+ items.get(position).img_url);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

}
