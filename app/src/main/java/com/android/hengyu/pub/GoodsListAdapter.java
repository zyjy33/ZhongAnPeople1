package com.android.hengyu.pub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.shangpingListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;

public class GoodsListAdapter extends BaseAdapter {

	private ArrayList<GoodsListData> lists;
	private ArrayList<shangpingListData> lists_ll;
	private Context context;
	private ImageLoader loader;
	private LayoutInflater inflater;
	public GoodsListAdapter(ArrayList<GoodsListData> lists,ArrayList<shangpingListData> lists_ll,
							Context context,ImageLoader loader) {

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
	public void putData(ArrayList<GoodsListData> lists){
		this.lists = lists;
		this.notifyDataSetChanged();
	}

	public int getCount() {

		// return list.size();
		return lists.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View convertView, ViewGroup patent) {

		try {
			ViewHolder holder = null;
			holder = new ViewHolder();
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.goods_list_item, null);
				LinearLayout addview = (LinearLayout) convertView.findViewById(R.id.addview);
				TextView tv_zhuti = (TextView) convertView.findViewById(R.id.tv_zhuti);//
				//		tv_zhuti.setText((String) data_tm.get(position));
				tv_zhuti.setText(lists.get(position).name);
				System.out.println("222=====================");
				addview.removeAllViews();

				//		for (int i = 0; i < lists_ll.size(); i++) {
				//			holder = new ViewHolder();
				//			View vi = LayoutInflater.from(context).inflate(layout.goods_list, null);
				//			holder.listview = (ListView) vi.findViewById(R.id.listview_01);
				////			holder.tv_item = (TextView) vi.findViewById(R.id.tv_neirong);// 购买时间
				////			holder.tv_item.setText((String)data_tm.get(position));
				//
				//			SpListDataAdapter sAdapter = new SpListDataAdapter(lists_ll, context, loader);
				//			holder.listview.setAdapter(sAdapter);
				//
				//			holder.listview.setOnItemClickListener(new OnItemClickListener() {
				//
				//			@Override
				//			public void onItemClick(AdapterView<?> arg0, View arg1,
				//					int arg2, long arg3) {
				//
				////				myadapter.setSeclection(arg2);
				////				myadapter.notifyDataSetChanged();
				////				String id = lists.get(arg2).getId();
				//				System.out.println("====================="+id);
				//				try {
				//
				//				Intent intent = new Intent(context,WareInformationActivity.class);
				//				intent.putExtra("id", lists_ll.get(arg2).id);
				//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//				context.startActivity(intent);
				//
				//				} catch (Exception e) {
				//
				//					e.printStackTrace();
				//				}
				//
				//			}
				//		});
				//			addview.addView(vi);
				//			convertView.setTag(holder);
				//		}

			}else {
				holder = (ViewHolder) convertView.getTag();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return convertView;
	}
	private final class ViewHolder {
		TextView tv_zhuti;//
		TextView tv_item;//
		ImageView tupian;//
		ImageView iv_item;//
		ListView listview;
		LinearLayout lv_dingdanxq;
	}

}
