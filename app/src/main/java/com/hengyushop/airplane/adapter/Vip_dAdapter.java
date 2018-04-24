package com.hengyushop.airplane.adapter;

import java.util.ArrayList;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dot.data.VipDomain;
import com.lglottery.www.domain.CategoryDomain;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Vip_dAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<VipDomain> list;
	private ImageLoader imageLoader;
	private double  screenWidth;
	public Vip_dAdapter(Context context, ArrayList<VipDomain> list,ImageLoader imageLoader,double  screenWidth) {

		this.context = context;
		this.list = list;
		this.imageLoader = imageLoader;
		this.screenWidth = screenWidth;
	}

	public void putLists(ArrayList<VipDomain> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {

		final ViewHolder holder;
		if (view == null || view.getTag(R.drawable.icon + index) == null) {
			holder = new ViewHolder();

			view = LinearLayout.inflate(context,
					R.layout.vip_test, null);
			holder.view1 = (TextView) view.findViewById(R.id.view1);
			holder.view2 = (TextView) view.findViewById(R.id.view2);
			holder.view3 = (ImageView) view.findViewById(R.id.view3);
			holder.view4 = (ImageView) view.findViewById(R.id.view4);
			holder.view5 = (ImageView) view.findViewById(R.id.view5);
			holder.lay  = (LinearLayout) view.findViewById(R.id.lay);
			holder.des = (TextView) view.findViewById(R.id.des);
			holder.jiage = (TextView) view.findViewById(R.id.jiage);
			holder.hongbao = (TextView) view.findViewById(R.id.hongbao);
			double scale_1 = (double)271/427;
			holder.lay.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) (scale_1*screenWidth)));
			view.setTag(R.drawable.icon + index);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.icon + index);
		}
		VipDomain d=list.get(index);
		holder.view1.setText(d.getName());
		holder.des.setText(d.getDes());
		holder.view2.setText("");
		holder.jiage.setText(" ￥"+d.getPrice());
		String str = "送"+d.getBao()+"红包";
		SpannableStringBuilder style=new SpannableStringBuilder(str);
		style.setSpan(new ForegroundColorSpan(Color.RED),0,1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.hongbao.setText(style);
		imageLoader.displayImage(RealmName.REALM_NAME+"/admin/"+d.getImg(), holder.view3);
		imageLoader.displayImage(RealmName.REALM_NAME+"/admin/"+d.getImg1(), holder.view4);
		imageLoader.displayImage(RealmName.REALM_NAME+"/admin/"+d.getImg2(), holder.view5);
		return view;
	}

	public class ViewHolder {
		TextView view1,view2,des,jiage,hongbao;
		ImageView view3,view4,view5;
		LinearLayout lay;
	}
}
