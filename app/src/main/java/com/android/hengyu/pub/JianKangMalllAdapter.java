package com.android.hengyu.pub;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.SpcsAdapter;
import com.hengyushop.airplane.adapter.zaylAdapter;
import com.hengyushop.demo.home.HealthGunaActivity;
import com.hengyushop.demo.home.HongBaoZqListActivity;
import com.hengyushop.demo.home.JianKangScListActivity;
import com.hengyushop.demo.home.JiangKangMallListActivity;
import com.hengyushop.demo.home.ZhongAnYlActivity;
import com.hengyushop.demo.home.ZhongAnYlListActivity;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class JianKangMalllAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ZhongAnYlData> list;
	private LayoutInflater inflater;
	ArrayList<ZhongAnYlBean> items;
	GridView gridview;
	public static AQuery aQuery;

	public JianKangMalllAdapter(ArrayList<ZhongAnYlData> list, Context context) {
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		aQuery = new AQuery(context);
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
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {
		// TODO Auto-generated method stub
		try {
			convertView = inflater.inflate(R.layout.zayl_item_telie, null);
			TextView tv_letter = (TextView) convertView
					.findViewById(R.id.tagname);
			LinearLayout addview = (LinearLayout) convertView
					.findViewById(R.id.addview);
			ImageView iv_zhuti = (ImageView) convertView
					.findViewById(R.id.iv_zhuti);
			LinearLayout img_btn_address = (LinearLayout) convertView
					.findViewById(R.id.img_btn_address);
			String zhou = list.get(position).getTitle();
			System.out.println("--------------------" + zhou);
			tv_letter.setText(list.get(position).getTitle());

			addview.removeAllViews();

			if (position == 0) {
				iv_zhuti.setBackgroundColor(Color.RED);
				// tv_letter.setBackgroundColor(getResources().getColor(R.color.white));
			} else if (position == 1) {
				iv_zhuti.setBackgroundColor(Color.BLUE);
			} else if (position == 2) {
				iv_zhuti.setBackgroundColor(Color.CYAN);
			} else if (position == 3) {
				iv_zhuti.setBackgroundColor(Color.MAGENTA);
			} else if (position == 4) {
				iv_zhuti.setBackgroundColor(Color.YELLOW);
			} else if (position == 5) {
				iv_zhuti.setBackgroundColor(Color.CYAN);
			} else if (position == 6) {
				iv_zhuti.setBackgroundColor(Color.BLUE);
			} else if (position == 7) {
				iv_zhuti.setBackgroundColor(Color.GREEN);
			}

			img_btn_address.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					try {

						String id = list.get(position).getId();
//						String title = list.get(position).getTitle();
//						System.out.println("=====================" + id);
//						Intent intent = new Intent(context,JiangKangMallListActivity.class);
//						intent.putExtra("id", id);
//						intent.putExtra("title", title);
//						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						context.startActivity(intent);
						
						System.out.println("position====================="+position);
						System.out.println("id====================="+id);
						if(position == 0){
						Intent intent = new Intent(context,HongBaoZqListActivity.class);
						intent.putExtra("category_id", id);
						intent.putExtra("title", list.get(position).getTitle());
						intent.putExtra("type_zhi", "3");
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						}else{
							//ZhongAnYlListActivity
							Intent intent = new Intent(context,JianKangScListActivity.class);
							intent.putExtra("category_id", id);
							intent.putExtra("title", list.get(position).getTitle());
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});

			for (int i = 0; i < list.get(position).getList().size(); i++) {
				try {

					View vi = LayoutInflater.from(context).inflate(
							R.layout.zayl_telie, null);// zayl_item

					// System.out.println("position------------------------"+list.get(position).getList().size());
					items = list.get(position).getList();
					// System.out.println("items------------------------"+items.size());
					gridview = (GridView) vi.findViewById(R.id.gridView);
					// zaylAdapter MyAdapter2 = new zaylAdapter(items, context);
					// gridview.setAdapter(MyAdapter2);
					gridview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							try {
								String id = list.get(position).getList().get(arg2).getId();
								System.out.println("=====id====================="+ id);
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

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			zaylAdapter MyAdapter2 = new zaylAdapter(items, context);
			gridview.setAdapter(MyAdapter2);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}
}