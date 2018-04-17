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

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class YsjGoodsListAdapter extends BaseAdapter {

	private ArrayList<GoodsListData> lists;
	private ArrayList<shangpingListData> lists_ll;
	private ArrayList<shangpingListData> list_2 = null;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;
	private SharedPreferences spPreferences;

	public YsjGoodsListAdapter(ArrayList<GoodsListData> lists,ArrayList<shangpingListData> lists_ll,
							   Context context,ImageLoader loader) {//ArrayList<shangpingListData> lists_ll,
		// TODO Auto-generated constructor stub
		try {
			this.context = context;
			this.lists = lists;
			//			this.lists_ll = lists_ll;
			this.loader = loader;
			this.inflater = LayoutInflater.from(context);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void putData(ArrayList<GoodsListData> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// *******************************************************************************

		// 第一种方法
		//		View inflate = inflater.inflate(R.layout.goods_list_item, null);//
		View inflate = inflater.inflate(R.layout.tuijian_yunshangju_time, null);//
		//		ListView listView = (ListView) inflate.findViewById(R.id.expandedListView1);
		GridView gridview = (GridView) inflate.findViewById(R.id.gridview);
		try {

			try {
				//				TextView tv_zhuti = (TextView) inflate.findViewById(R.id.tv_zhuti);
				//				tv_zhuti.setText(lists.get(position).name);

				TextView tv_name = (TextView) inflate.findViewById(R.id.tv_ware_name);
				ImageView img_ware = (ImageView) inflate.findViewById(R.id.img_ware);
				tv_name.setText(lists.get(position).name);
				loader.displayImage(RealmName.REALM_NAME_HTTP+ lists.get(position).img_url, img_ware);


			} catch (Exception e) {
				// TODO: handle exception
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

			//			int zhouString = lists_ll.size() ;
			//			System.out
			//			.println("=====================lists_ll" + zhouString);

			//			if (lists_ll.equals("")) {
			//
			//			}else {
			gridview.setAdapter(new BaseAdapter() {

				@Override
				public View getView(int position, View convertView,
									ViewGroup parent) {

					View inflate = inflater.inflate(R.layout.yhzstp_item, null);//
					//					View inflate = inflater.inflate(R.layout.tuijian_yunshangju_time, null);//tuijian_yunshangju_time
					try {
						ImageView iv = (ImageView) inflate.findViewById(R.id.iv_item);
						ImageView iv1 = (ImageView) inflate.findViewById(R.id.iv_item1);

						TextView tv_name = (TextView) inflate.findViewById(R.id.tv_ware_name);
						ImageView img_ware = (ImageView) inflate
								.findViewById(R.id.img_ware);

						ImageView iv_biaoti1 = (ImageView) inflate
								.findViewById(R.id.iv_biaoti1);
						ImageView iv_biaoti2 = (ImageView) inflate
								.findViewById(R.id.iv_biaoti2);
						ImageView iv_biaoti3 = (ImageView) inflate
								.findViewById(R.id.iv_biaoti3);

						//						tv_name.setText(lists_ll.get(position).title);

						System.out
								.println("=====================位置" + position);

						//						loader.displayImage(
						//								RealmName.REALM_NAME_HTTP
						//										+ lists.get(position).img_url, img_ware);

						//						loader.displayImage(RealmName.REALM_NAME_HTTP+ lists_ll.get(position).img_url, iv);
						String  tupian =lists_ll.get(position).img_url;
						System.out.println("=====================tupian" + tupian);

						//						Editor editor = spPreferences.edit();
						//						editor.putString("tupian", tupian);
						//						editor.commit();

						ImageLoader imageLoader=ImageLoader.getInstance();
						imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ lists_ll.get(position).img_url,iv);

						//						 if (position == 0) {
						//							  imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ lists_ll.get(position).img_url,iv);
						//							  iv1.setVisibility(View.GONE);
						//						}else {
						//							 if (position == 1) {
						//								 iv1.setVisibility(View.VISIBLE);
						//								 imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ lists_ll.get(position).img_url,iv1);
						//							}
						//						}

						//						if (position == 0) {
						//							loader.displayImage(
						//									RealmName.REALM_NAME_HTTP
						//											+ lists_ll.get(position).img_url, iv_biaoti1);
						//
						////							iv_biaoti2.setVisibility(View.GONE);
						////							iv_biaoti3.setVisibility(View.GONE);
						//						}
						//						if (position == 1) {
						//							iv_biaoti1.setVisibility(View.GONE);
						//							iv_biaoti3.setVisibility(View.VISIBLE);
						//							loader.displayImage(
						//									RealmName.REALM_NAME_HTTP
						//											+ lists_ll.get(position).img_url, iv_biaoti2);
						//						}
						//						if (position == 2) {
						//							iv_biaoti1.setVisibility(View.GONE);
						//							iv_biaoti2.setVisibility(View.VISIBLE);
						//							loader.displayImage(
						//									RealmName.REALM_NAME_HTTP
						//											+ lists_ll.get(position).img_url, iv_biaoti3);
						//						}


					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					return inflate;
				}

				@Override
				public long getItemId(int position) {
					// TODO Auto-generated method stub
					return position;
				}

				@Override
				public Object getItem(int position) {
					// TODO Auto-generated method stub
					return position;
				}

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return lists_ll.size();
				}
			});

			//			}

			//			listView.setAdapter(new BaseAdapter() {
			//
			//				@Override
			//				public View getView(int position, View convertView,
			//						ViewGroup parent) {
			//
			//					View inflate = inflater.inflate(R.layout.tuijian_yunshangju_time, null);
			//					try {
			//
			//						// TextView tv_name = (TextView)
			//						// inflate.findViewById(R.id.tv_wzhuti_name);//category_title
			//						// TextView tv_category_title = (TextView)
			//						// inflate.findViewById(R.id.tv_ware_name);//category_title
			//						// ImageView iv_img = (ImageView)
			//						// inflate.findViewById(R.id.iv_img);
			//
			//						TextView tv_name = (TextView) inflate
			//								.findViewById(R.id.tv_ware_name);
			//						ImageView img_ware = (ImageView) inflate
			//								.findViewById(R.id.img_ware);
			//
			//						ImageView iv_biaoti1 = (ImageView) inflate
			//								.findViewById(R.id.iv_biaoti1);
			//						ImageView iv_biaoti2 = (ImageView) inflate
			//								.findViewById(R.id.iv_biaoti2);
			//						ImageView iv_biaoti3 = (ImageView) inflate
			//								.findViewById(R.id.iv_biaoti3);
			//
			//						tv_name.setText(lists_ll.get(position).title);
			//						System.out
			//								.println("=====================位置" + position);
			//
			//						loader.displayImage(
			//								RealmName.REALM_NAME_HTTP
			//										+ lists.get(position).img_url, img_ware);
			//
			//						if (position == 0) {
			//							loader.displayImage(
			//									RealmName.REALM_NAME_HTTP
			//											+ lists_ll.get(position).img_url, iv_biaoti1);
			//						}
			//						if (position == 1) {
			//							loader.displayImage(
			//									RealmName.REALM_NAME_HTTP
			//											+ lists_ll.get(position).img_url, iv_biaoti2);
			//						}
			//						if (position == 2) {
			//							loader.displayImage(
			//									RealmName.REALM_NAME_HTTP
			//											+ lists_ll.get(position).img_url, iv_biaoti3);
			//						}
			//					} catch (Exception e) {
			//						// TODO: handle exception
			//						e.printStackTrace();
			//					}
			//					return inflate;
			//				}
			//
			//				@Override
			//				public long getItemId(int position) {
			//					// TODO Auto-generated method stub
			//					return position;
			//				}
			//
			//				@Override
			//				public Object getItem(int position) {
			//					// TODO Auto-generated method stub
			//					return position;
			//				}
			//
			//				@Override
			//				public int getCount() {
			//					// TODO Auto-generated method stub
			//					return lists_ll.size();
			//				}
			//			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//YangHuzsAdapter
		// Utility.setListViewHeightBasedOnChildren(listView);
		return inflate;

	}

}
