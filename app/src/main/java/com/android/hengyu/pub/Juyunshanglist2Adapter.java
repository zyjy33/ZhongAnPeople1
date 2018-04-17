package com.android.hengyu.pub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.home.JuYunshangXqActivity;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.ZhongAnYlBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

import java.util.ArrayList;
import java.util.List;

public class Juyunshanglist2Adapter extends BaseAdapter {
	private Context context;
	private ArrayList<GoodsListData> list;
	private LayoutInflater inflater;
	ArrayList<ZhongAnYlBean> items;
	public static List<String> list_zhi = new ArrayList<String>();
	public static List<String> list_zhi1 = new ArrayList<String>();
	public static List<String> list_zhi2 = new ArrayList<String>();
	private ArrayList<GoodsListData> list_ll = new ArrayList<GoodsListData>();;
	private ImageLoader loader;
	GridView gridview;
	public static AQuery mAq;
	public Juyunshanglist2Adapter(ArrayList<GoodsListData> list,Context context, ImageLoader imageLoader) {
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
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {
		// TODO Auto-generated method stub
		try {

			convertView = inflater.inflate(R.layout.tuijian_yunshangju_time, null);
			TextView tv_letter = (TextView) convertView.findViewById(R.id.tv_ware_name);
			LinearLayout addview= (LinearLayout) convertView.findViewById(R.id.addview);
			LinearLayout ll_sp_name = (LinearLayout) convertView.findViewById(R.id.ll_sp_name);
			ImageView img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
			System.out.println("-----getName---------"+list.get(position).getName());

			if (list.get(position).getName().equals("null")) {
				tv_letter.setText("");
			}else {
				tv_letter.setText(list.get(position).getName());
			}

			ll_sp_name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					try {
						//					String id = String.valueOf(JuYunshangActivity.INDX);
						String id = list.get(position).getUser_id();
						System.out.println("=====id================"+id);
						Intent intent = new Intent(context,JuYunshangXqActivity.class);
						intent.putExtra("id", id);
						intent.putExtra("name", list.get(position).getName());
						intent.putExtra("img_url", list.get(position).getImg_url());
						intent.putExtra("logo_url", list.get(position).getLogo_url());
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});

			//		ImageLoader imageLoader = ImageLoader.getInstance();
			//	    imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ list.get(position).getLogo_url(),img_ware);
			if (list.get(position).getLogo_url().equals("")) {
				img_ware.setBackgroundResource(R.drawable.zams_tb);
			}else {
				mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP+list.get(position).getLogo_url());
			}

			addview.removeAllViews();


			for (int i = 0; i <list.get(position).getList().size() ; i++) {
				final int p = i;
				View vi = LayoutInflater.from(context).inflate(R.layout.listitem_menu_sp_time,null);
				TextView tv_name = (TextView) vi.findViewById(R.id.tv_ware_name);
				TextView tv_rePrice = (TextView) vi.findViewById(R.id.tv_hengyu_money);
				TextView tv_maPrice = (TextView) vi.findViewById(R.id.tv_market_money);
				ImageView img_ware2 = (ImageView) vi.findViewById(R.id.img_ware);
				RelativeLayout rl_list = (RelativeLayout) vi.findViewById(R.id.rl_list);

				tv_name.setText(list.get(position).getList().get(i).title);
				tv_rePrice.setText("价格:￥" + list.get(position).getList().get(i).getSell_price());
				//			System.out.println("=====list.get(position).getList().get(i).getMarket_price(================"+list.get(position).getList().get(i).getSell_price());
				tv_maPrice.setText("市场价:￥" + list.get(position).getList().get(i).getMarket_price());
				tv_maPrice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
				System.out.println("--------------"+list.get(position).getList().get(i).getImg_url());
				//			imageLoader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getList().get(i).img_url, img_ware2);
				mAq.id(img_ware2).image(RealmName.REALM_NAME_HTTP+list.get(position).getList().get(i).img_url);
				rl_list.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						try {
							String id = list.get(position).getList().get(p).getId();
							System.out.println("=====id================"+id);
							Intent intent = new Intent(context,WareInformationActivity.class);
							intent.putExtra("id", id);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});

				addview.addView(vi);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}
}