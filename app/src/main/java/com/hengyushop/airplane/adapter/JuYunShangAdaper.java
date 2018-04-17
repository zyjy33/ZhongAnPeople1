package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.shangpingListData;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

import java.util.ArrayList;

public class JuYunShangAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<shangpingListData> list;
	private LayoutInflater mInflater;
	public static AQuery mAq;

	public JuYunShangAdaper(ArrayList<shangpingListData> list, Context context) {
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	@Override
	public int getCount() {
		if (list.size() < 1) {

			return 0;
		} else {

			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		try {

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.juyunshang_item, null);
				holder.img = (ImageView) convertView
						.findViewById(R.id.iv_imagr);
				holder.tv_biaoti = (TextView) convertView
						.findViewById(R.id.tv_biaoti);
				holder.tv_jifengduihuan = (TextView) convertView
						.findViewById(R.id.tv_jifengduihuan);
				holder.tv_shichangjia = (TextView) convertView
						.findViewById(R.id.tv_shichangjia);
				LinearLayout ll_shangjia = (LinearLayout) convertView
						.findViewById(R.id.ll_shangjia);

				ll_shangjia.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mContext,
								WareInformationActivity.class);
						intent.putExtra("id", list.get(position).id);
						intent.putExtra("hongbao", "hongbao");
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startActivity(intent);
					}
				});

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_biaoti.setText(list.get(position).title);
			holder.tv_jifengduihuan.setText("价格:￥"
					+ list.get(position).sell_price);
			holder.tv_shichangjia.setText("市场价:￥"
					+ list.get(position).market_price);
			holder.tv_shichangjia.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			// ImageLoader imageLoader=ImageLoader.getInstance();
			// imageLoader.displayImage((String)
			// RealmName.REALM_NAME_HTTP+list.get(position).img_url,holder.img);
			mAq.id(holder.img).image(
					RealmName.REALM_NAME_HTTP + list.get(position).img_url);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView tv_biaoti;
		TextView tv_jifengduihuan;
		TextView tv_shichangjia;
		RadioButton radioButton;
	}
}