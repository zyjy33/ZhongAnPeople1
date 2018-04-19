package com.zams.www.health.business;

import java.util.List;

import com.bumptech.glide.Glide;
import com.zams.www.R;
import com.zams.www.weiget.YSBaseAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HallListAdapter extends YSBaseAdapter<MedicalItems> {
	private static final String TAG = "HallListAdapter";
	private ImageView hallImg;
	private TextView hallTitleTv;
	private TextView hallMoneyTv;
	private ImageView shopIv;

	public HallListAdapter(Context context, List<MedicalItems> list, int layout) {
		super(context, list, layout);

	}

	@Override
	public void convert(YSBaseAdapter.ViewHolder holder, MedicalItems data) {
		hallImg =  holder.findViewById(R.id.hall_item_img);
		hallTitleTv = holder.findViewById(R.id.hall_title_tv);
		hallMoneyTv =  holder.findViewById(R.id.hall_money_tv);
		shopIv = holder.findViewById(R.id.shop_iv);
		hallTitleTv.setText(data.getMedical_name());
		hallMoneyTv.setText(data.getMedical_price() + "元或" + data.getPoints()
				+ "积分");
		Glide.with(mContext)
				.load(data.getImg_url())
				.placeholder(R.drawable.sj_fw)//图片加载出来前，显示的图片
				.error(R.drawable.sj_fw)//图片加载失败后，显示的图片
				.into(hallImg);
		shopIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e(TAG, "onClick: 加入购物车");
			}
		});

	}


	public void upData(List<MedicalItems> datas) {
		if(datas != null){
			mList.clear();
			mList.addAll(datas);
			this.notifyDataSetChanged();
		}

	}

}
