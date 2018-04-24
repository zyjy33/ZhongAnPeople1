package com.android.hengyu.pub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

import java.util.ArrayList;

public class JysGoodsListAdapter extends BaseAdapter {

	private ArrayList<GoodsListData> lists;
	private ArrayList<shangpingListData> lists_ll;
	private ArrayList<shangpingListData> list_2 = null;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;

	public JysGoodsListAdapter(ArrayList<GoodsListData> lists,
							   ArrayList<shangpingListData> lists_ll, Context context,
							   ImageLoader loader) {

		try {
			this.context = context;
			this.lists = lists;
			this.lists_ll = lists_ll;
			this.loader = loader;
			this.inflater = LayoutInflater.from(context);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

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
		View inflate = inflater.inflate(R.layout.tuijian_yunshangju_time, null);
		ListView listView = (ListView) inflate
				.findViewById(R.id.expandedListView1);
		try {

			try {

				TextView tv_zhuti = (TextView) inflate
						.findViewById(R.id.tv_zhuti);
				tv_zhuti.setText(lists.get(position).name);

				// System.out.println("ID值====================="+zhou);
				// loadCatell(zhou);

			} catch (Exception e) {

				e.printStackTrace();
			}

			// listView.setOnItemLongClickListener(new OnItemLongClickListener()
			// {
			// @Override
			// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int
			// arg2, long arg3) {
			//  -
			// // System.out.println("=====================1");
			// Intent intent = new
			// Intent(context,WareInformationActivity.class);
			// intent.putExtra("id", lists_ll.get(arg2).id);
			// context.startActivity(intent);
			// return true;
			// }
			//
			// });

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {

					try {

						Intent intent = new Intent(context,
								WareInformationActivity.class);
						intent.putExtra("id", lists_ll.get(arg2).id);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});

			listView.setAdapter(new BaseAdapter() {

				@Override
				public View getView(int position, View convertView,
									ViewGroup parent) {
					View inflate = inflater.inflate(R.layout.goods_list_ll,
							null);

					TextView tv_name = (TextView) inflate
							.findViewById(R.id.tv_wzhuti_name);// category_title
					TextView tv_category_title = (TextView) inflate
							.findViewById(R.id.tv_ware_name);// category_title
					ImageView iv_img = (ImageView) inflate
							.findViewById(R.id.iv_img);
					ImageView iv_biaoti1 = (ImageView) inflate
							.findViewById(R.id.iv_biaoti1);
					ImageView iv_biaoti2 = (ImageView) inflate
							.findViewById(R.id.iv_biaoti2);
					ImageView iv_biaoti3 = (ImageView) inflate
							.findViewById(R.id.iv_biaoti3);

					tv_name.setText(lists_ll.get(position).title);
					tv_category_title.setText(lists_ll.get(position).category_title);
					System.out.println("=====================位置" + position);
					// if (position == 0) {
					loader.displayImage(
							RealmName.REALM_NAME_HTTP
									+ lists_ll.get(position).img_url,
							iv_biaoti1);
					// }else if (position == 1) {
					loader.displayImage(
							RealmName.REALM_NAME_HTTP
									+ lists_ll.get(position).img_url,
							iv_biaoti2);
					// }else if (position == 2) {
					loader.displayImage(
							RealmName.REALM_NAME_HTTP
									+ lists_ll.get(position).img_url,
							iv_biaoti3);
					// }

					loader.displayImage(
							RealmName.REALM_NAME_HTTP
									+ lists_ll.get(position).img_url, iv_img);
					return inflate;
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
				public int getCount() {

					return lists_ll.size();
				}
			});
		} catch (Exception e) {

			e.printStackTrace();
		}

		// Utility.setListViewHeightBasedOnChildren(listView);
		return inflate;

	}

	// 商品列表
	// private void loadCatell(int user_id){
	// try {
	// System.out.println("二级值333333====================="+user_id);
	// AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
	// "channel_name=goods&top=3&strwhere=user_id="+user_id+"", new
	// AsyncHttpResponseHandler(){
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	//
	// super.onSuccess(arg0, arg1);
	// try {
	// System.out.println("值2=========="+arg1);
	// list_2 = new ArrayList<shangpingListData>();
	// JSONObject jsonObject = new JSONObject(arg1);
	// JSONArray jsonArray = jsonObject.getJSONArray("data");
	// System.out.println("jsonArray"+jsonArray.length());
	// for (int i = 0; i < jsonArray.length(); i++) {
	// shangpingListData data = new shangpingListData();
	// JSONObject object = jsonArray.getJSONObject(i);
	// data.id = object.getString("id");
	// data.title = object.getString("title");
	// data.img_url = object.getString("img_url");
	// data.category_title = object.getString("category_title");
	// Log.v("data1", data.id + "");
	// list_2.add(data);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }, null);
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

}
