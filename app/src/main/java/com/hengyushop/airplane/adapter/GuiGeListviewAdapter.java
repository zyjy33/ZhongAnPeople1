package com.hengyushop.airplane.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class GuiGeListviewAdapter extends BaseAdapter {
	private List<GuigeData> list;
	private ArrayList data;
	private ArrayList data1;
	private ArrayList data2;
	private ArrayList data_id, data_id1, data_id2, data_shuzu;
	private Context context;
	LayoutInflater inflater;
	GouwucheAdapter MyAdapter;
	GouwucheAdapter MyAdapter1;
	GouwucheAdapter MyAdapter2;
	public String id, id1, id2;
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;
	final int TYPE_3 = 2;
	private ImageLoader imageLoader;

	public GuiGeListviewAdapter(ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.imageLoader = imageLoader;
	}

	public GuiGeListviewAdapter(Context context, List<GuigeData> list,
								ArrayList data, ArrayList data1, ArrayList data2,
								ArrayList data_id, ArrayList data_id1, ArrayList data_id2,
								ArrayList data_shuzu) {
		try {

			this.list = list;
			this.data = data;
			this.data1 = data1;
			this.data2 = data2;
			this.data_id = data_id;
			this.data_id1 = data_id1;
			this.data_id2 = data_id2;
			this.data_shuzu = data_shuzu;
			this.context = context;
			Log.i("data1", data1 + "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// Log.i("data", "=======1======");
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		Log.i("data", "=======2======");
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.i("data", "=======3======");
		return 0;
	}

	// 每个convert view都会调用此方法，获得当前所需要的view样式
	@Override
	public int getItemViewType(int position) {
		int p = position;
		if (p == 0)
			return TYPE_1;
		else if (p == 1)
			return TYPE_2;
		else if (p == 2)
			return TYPE_3;
		Log.i("data", "=======4======");
		return p;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	public class ViewHolder1 {
		MyGridView gridView;
		TextView tv_yhwenzi;
	}

	public class ViewHolder2 {
		MyGridView gridView;
		TextView tv_yhwenzi;
	}

	public class ViewHolder3 {
		MyGridView gridView;
		TextView tv_yhwenzi;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {

			ViewHolder1 holder1 = null;
			ViewHolder2 holder2 = null;
			ViewHolder3 holder3 = null;
			int type = getItemViewType(position);

			if (convertView == null) {
				inflater = LayoutInflater.from(context);
				// 按当前所需的样式，确定new的布局
				switch (type) {
					case TYPE_1:
						convertView = inflater.inflate(R.layout.guige_item, parent,
								false);
						holder1 = new ViewHolder1();
						holder1.tv_yhwenzi = (TextView) convertView
								.findViewById(R.id.tv_zhuti);
						holder1.gridView = (MyGridView) convertView
								.findViewById(R.id.gridView);
						convertView.setTag(holder1);

						break;
					case TYPE_2:
						convertView = inflater.inflate(R.layout.guige_item, parent,
								false);
						holder2 = new ViewHolder2();
						holder2.tv_yhwenzi = (TextView) convertView
								.findViewById(R.id.tv_zhuti);
						holder2.gridView = (MyGridView) convertView
								.findViewById(R.id.gridView);
						convertView.setTag(holder2);

						break;
					case TYPE_3:
						convertView = inflater.inflate(R.layout.guige_item, parent,
								false);
						holder3 = new ViewHolder3();
						holder3.tv_yhwenzi = (TextView) convertView
								.findViewById(R.id.tv_zhuti);
						holder3.gridView = (MyGridView) convertView
								.findViewById(R.id.gridView);
						convertView.setTag(holder3);

						break;

					default:
						break;
				}

			} else {
				switch (type) {
					case TYPE_1:
						holder1 = (ViewHolder1) convertView.getTag();
						break;
					case TYPE_2:
						holder2 = (ViewHolder2) convertView.getTag();
						break;
					case TYPE_3:
						holder3 = (ViewHolder3) convertView.getTag();
						break;

				}
			}

			// 设置资源
			switch (type) {
				case TYPE_1:
					holder1.tv_yhwenzi.setText(list.get(position).getTitle());
					MyAdapter = new GouwucheAdapter(data, context);
					holder1.gridView.setAdapter(MyAdapter);

					holder1.gridView
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
														View arg1, int arg2, long arg3) {
									MyAdapter.setSeclection(arg2);
									MyAdapter.notifyDataSetChanged();
									id = (String) data_id.get(arg2);
									System.out.println("id值是=====" + id);
									System.out.println("id=====" + data_id1.size());
								}
							});

					break;
				case TYPE_2:
					try {

						holder2.tv_yhwenzi.setText(list.get(position).getTitle());
						MyAdapter1 = new GouwucheAdapter(data1, context);
						holder2.gridView.setAdapter(MyAdapter1);

						holder2.gridView
								.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> arg0,
															View arg1, int arg2, long arg3) {
										try {
											MyAdapter1.setSeclection(arg2);
											MyAdapter1.notifyDataSetChanged();
											id1 = (String) data_id1.get(arg2);
											System.out.println("id值是1=====" + id1);
											System.out.println("id====="
													+ data_id1.size());
										} catch (Exception e) {
											// TODO: handle exception
											e.printStackTrace();
										}
									}
								});
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;

				case TYPE_3:
					holder3.tv_yhwenzi.setText(list.get(position).getTitle());
					MyAdapter2 = new GouwucheAdapter(data2, context);
					holder3.gridView.setAdapter(MyAdapter2);

					holder3.gridView
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
														View arg1, int arg2, long arg3) {
									MyAdapter2.setSeclection(arg2);
									MyAdapter2.notifyDataSetChanged();
									id2 = (String) data_id2.get(arg2);
									System.out.println("id值是2=====" + id2);
									String sell_price = "," + id + "," + id1 + ","
											+ id2 + ",";
									System.out
											.println("拼接的值=========" + sell_price);
									if (sell_price == data_shuzu.get(arg2)) {

									}
								}
							});
					break;

			}
			// String zhouString = id+","+id1+","+id2;
			// System.out.println("拼接的值1========="+zhouString);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}

}
