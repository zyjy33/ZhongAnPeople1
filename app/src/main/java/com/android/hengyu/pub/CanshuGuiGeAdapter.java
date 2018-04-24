package com.android.hengyu.pub;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class CanshuGuiGeAdapter extends BaseAdapter {

	private ArrayList<GuigeData> lists;
	private ArrayList<GuigeData> lists_lb;
	private ArrayList<shangpingListData> lists_ll;
	private ArrayList<shangpingListData> list_2 = null;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;
	private SharedPreferences spPreferences;

	public CanshuGuiGeAdapter(ArrayList<GuigeData> list_ll,ArrayList<GuigeData> lists_lb,Context context) {//ArrayList<shangpingListData> lists_ll,

		try {
			this.context = context;
			this.lists = list_ll;
			this.lists_lb = lists_lb;
			this.inflater = LayoutInflater.from(context);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	//	public void putData(ArrayList<GoodsListData> lists) {
	//		this.lists = lists;
	//		notifyDataSetChanged();
	//	}

	@Override
	public int getCount() {

		return lists.size();
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// *******************************************************************************
		// 第一种方法
		View inflate = inflater.inflate(R.layout.guige_item, null);//guige_item
		//		ListView listView = (ListView) inflate.findViewById(R.id.expandedListView1);
		GridView gridview = (GridView) inflate.findViewById(R.id.gridView);
		try {

			try {

				TextView tv_name = (TextView) inflate.findViewById(R.id.tv_zhuti);
				ImageView img_ware = (ImageView) inflate.findViewById(R.id.img_ware);
				tv_name.setText(lists.get(position).title);
				//				loader.displayImage(RealmName.REALM_NAME_HTTP+ lists.get(position).img_url, img_ware);
				System.out
						.println("=====================位置1" + position);

			} catch (Exception e) {

				e.printStackTrace();
			}

			//			  gridview.setOnItemClickListener(new OnItemClickListener() {
			//		            @Override
			//		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			//
			//		            	Intent intent= new Intent(context,WareInformationActivityll.class);
			//						intent.putExtra("list_dz", lists_ll.get(arg2).id);
			//						context.startActivity(intent);
			//		            }
			//		        });

			//			if (lists_lb.get(position).title.equals("")) {
			System.out.println("=====================位置11" + lists_lb.size());
			//			gridview.setAdapter(new BaseAdapter() {
			//
			//				@Override
			//				public View getView(int position, View convertView,ViewGroup parent) {
			//
			//					View inflate = inflater.inflate(R.layout.gridview_item0, null);//
			//					try {
			//
			//						TextView tv_name = (TextView) inflate.findViewById(R.id.btn_aaa1);
			//
			//
			//						tv_name.setText(lists_lb.get(position).title);
			//
			//						System.out.println("=====================位置2" + position);
			//
			////						 ImageLoader imageLoader=ImageLoader.getInstance();
			////					        imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ lists_ll.get(position).img_url,iv);
			//
			//					} catch (Exception e) {
			//
			//						e.printStackTrace();
			//					}
			//					return inflate;
			//				}
			//
			//				@Override
			//				public long getItemId(int position) {
			//
			//					return position;
			//				}
			//
			//				@Override
			//				public Object getItem(int position) {
			//
			//					return position;
			//				}
			//
			//				@Override
			//				public int getCount() {
			//
			//					return lists_lb.size();
			//				}
			//			});
			//			}else {
			//
			//			}


		} catch (Exception e) {

			e.printStackTrace();
		}
		//YangHuzsAdapter
		// Utility.setListViewHeightBasedOnChildren(listView);
		return inflate;

	}

}
