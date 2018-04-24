package com.android.hengyu.pub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.ZhongAnYlBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class JuyouFanglistAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GoodsListData> list;
	private LayoutInflater inflater;
	ArrayList<ZhongAnYlBean> items;
	private ImageLoader loader;
	GridView gridview;
	private AQuery mAq;
	public JuyouFanglistAdapter(ArrayList<GoodsListData> list,Context context, ImageLoader imageLoader) {
		this.list = list;
		this.context = context;
		this.loader = loader;
		mAq = new AQuery(context);
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list.size()<1) {

			return 0;
		}else{

			return list.size();
		}
	}

	public void putData(ArrayList<GoodsListData> list){
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {

		try {

			convertView = inflater.inflate(R.layout.goods_list_item, null);
			TextView tv_letter = (TextView) convertView.findViewById(R.id.tv_zhuti);
			LinearLayout addview= (LinearLayout) convertView.findViewById(R.id.addview);
			//		LinearLayout ll_sp_name = (LinearLayout) convertView.findViewById(R.id.ll_sp_name);
			//		ImageView img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
			System.out.println("-----getName---------"+list.get(position).getName());

			if (list.get(position).getName().equals("null")) {
				tv_letter.setText("");
			}else {
				tv_letter.setText(list.get(position).getName());
			}
			//		mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP+list.get(position).getImg_url());

			//		ll_sp_name.setOnClickListener(new OnClickListener() {
			//
			//			@Override
			//			public void onClick(View arg0) {
			//
			//				try {
			//					String id = String.valueOf(JuYunshangActivity.INDX);
			//					System.out.println("=====id================"+id);
			//					Intent intent = new Intent(context,JuYunshangXqActivity.class);
			//					intent.putExtra("id", id);
			//					intent.putExtra("name", list.get(position).getName());
			//					intent.putExtra("img_url", list.get(position).getImg_url());
			//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//					context.startActivity(intent);
			//				} catch (Exception e) {
			//
			//					e.printStackTrace();
			//				}
			//			}
			//		});

			addview.removeAllViews();

			for (int i = 0; i <list.get(position).getList().size() ; i++) {
				final int p = i;
				//			View vi = LayoutInflater.from(context).inflate(R.layout.listitem_menu_sp_time,null);
				//			TextView tv_name = (TextView) vi.findViewById(R.id.tv_ware_name);
				//			TextView tv_rePrice = (TextView) vi.findViewById(R.id.tv_hengyu_money);
				//			TextView tv_maPrice = (TextView) vi.findViewById(R.id.tv_market_money);
				//			ImageView img_ware2 = (ImageView) vi.findViewById(R.id.img_ware);
				//			RelativeLayout rl_list = (RelativeLayout) vi.findViewById(R.id.rl_list);


				//			tv_name.setText(list.get(position).getList().get(i).getTitle());
				//			tv_rePrice.setText("￥" + list.get(position).getList().get(i).getSell_price());
				//			tv_maPrice.setText("￥" + list.get(position).getList().get(i).getMarket_price());
				//			tv_maPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
				//			System.out.println("--------------"+list.get(position).getList().get(i).getImg_url());
				//			 mAq.id(img_ware2).image(RealmName.REALM_NAME_HTTP+list.get(position).getList().get(i).img_url);

				//			rl_list.setOnClickListener(new OnClickListener() {
				//
				//			@Override
				//			public void onClick(View arg0) {
				//
				//				try {
				//					String id = list.get(position).getList().get(p).getId();
				//					System.out.println("=====id================"+id);
				//					Intent intent = new Intent(context,WareInformationActivity.class);
				//					intent.putExtra("id", id);
				//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//					context.startActivity(intent);
				//				} catch (Exception e) {
				//
				//					e.printStackTrace();
				//				}
				//			}
				//		});
				View vi = inflater.inflate(R.layout.goods_list_ll, null);

				TextView tv_name = (TextView) vi.findViewById(R.id.tv_wzhuti_name);//category_title
				TextView tv_category_title = (TextView) vi.findViewById(R.id.tv_ware_name);//category_title
				ImageView iv_img = (ImageView) vi.findViewById(R.id.iv_img);

				addview.addView(vi);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return convertView;
	}
}