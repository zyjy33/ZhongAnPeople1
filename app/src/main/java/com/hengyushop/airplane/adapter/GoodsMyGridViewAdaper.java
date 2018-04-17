package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.SpListData;
import com.zams.www.R;

import java.util.ArrayList;

public class GoodsMyGridViewAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<SpListData> list;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	public static boolean type = false;
	public GoodsMyGridViewAdaper(ArrayList<SpListData> list,Context context){
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	public void putData(ArrayList<SpListData> lists){
		this.list = lists;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list.size()<1) {

			return 0;
		}else{

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
				holder =  new ViewHolder();
				convertView = mInflater.inflate(R.layout.gridview_goods_item, null);
				holder.ll_kedikou = (LinearLayout) convertView.findViewById(R.id.ll_kedikou);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
				holder.tv_hengyu_money = (TextView) convertView.findViewById(R.id.tv_hengyu_money);
				holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
				holder.tv_hongbao = (TextView) convertView.findViewById(R.id.tv_hongbao);
				ImageView img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
				img_ware.setBackgroundResource(R.drawable.kd_honbao);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_biaoti.setText(list.get(position).getTitle());
			holder.tv_hengyu_money.setText("￥"+list.get(position).getSell_price());
			holder.tv_shichangjia.setText("￥"+list.get(position).getMarket_price());
			holder.tv_hongbao.setText("￥"+list.get(position).getCashing_packet());
			if (list.get(position).getCashing_packet().equals("0.0")) {
				holder.ll_kedikou.setVisibility(View.INVISIBLE);
			}else {
				holder.ll_kedikou.setVisibility(View.VISIBLE);
			}
			holder.tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).getImg_url());
			//        BitmapFactory.Options options = new BitmapFactory.Options();
			//        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
			//        Bitmap btimapObject = BitmapFactory.decodeFile(myImage.getAbsolutePath(), options);

			//        BitmapFactory.Options options = new BitmapFactory.Options();
			//        options.inJustDecodeBounds = true;     // 不把图片加载到内存中
			//        Bitmap btimapObject = BitmapFactory.decodeFile(myImage.getAbsolutePath(), options);
			type = true;
			//        mAq.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}


	class ViewHolder{
		ImageView img;
		TextView tv_biaoti;
		TextView tv_hengyu_money;
		TextView tv_shichangjia,tv_hongbao;
		LinearLayout ll_kedikou;
		RadioButton radioButton;
	}
}