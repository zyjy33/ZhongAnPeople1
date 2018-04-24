package com.android.hengyu.pub;

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
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.home.JuTuanGouXqActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class JuJingCaiAdapter extends BaseAdapter {

	private Context mContext;
	private List<JuTuanGouData> List;
	private LayoutInflater mInflater;
	public AQuery mAq;

	public JuJingCaiAdapter(Context context, List<JuTuanGouData> list) {
		this.List = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	public void putData(ArrayList<JuTuanGouData> List) {
		this.List = List;
		this.notifyDataSetChanged();
	}

	public int getCount() {

		return List.size();
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
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.jujingcai_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.tv_titel = (TextView) convertView
					.findViewById(R.id.tv_titel);

			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_groupon_price = (TextView) convertView
					.findViewById(R.id.tv_groupon_price);
			holder.tv_tuan = (TextView) convertView.findViewById(R.id.tv_tuan);
			holder.tv_anniu = (TextView) convertView
					.findViewById(R.id.tv_anniu);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/**
		 * 类别选项
		 */
		try {
			// System.out.println("List======================"+List.size());

			System.out.println("个数1======================" + position);
			holder.tv_titel.setText(List.get(position).getTitle());
			holder.tv_price.setText("￥" + List.get(position).getPrice());
			holder.tv_groupon_price.setText("￥"
					+ List.get(position).getGroupon_price());
			holder.tv_price.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			holder.tv_tuan.setText(List.get(position).getPeople() + "人团");
			holder.tv_anniu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					try {
						Intent intent = new Intent(mContext,
								JuTuanGouXqActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("id", List.get(position).getId());
						intent.putExtra("choujiang", "110");
						mContext.startActivity(intent);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});

			mAq.id(holder.img)
					.image(RealmName.REALM_NAME_HTTP
							+ List.get(position).getImg_url());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img, ll_tupian;
		TextView tv_titel, tv_price, tv_tuan, tv_groupon_price, tv_anniu;
		TextView tv_price2, tv_tuan2, tv_groupon_price2, tv_anniu2, tv_time;
		TextView tv_price3, tv_groupon_price3, tv_anniu3;
		LinearLayout ll_jutuan, ll_yushoutuan, ll_yiyuanjutou;
	}
}
