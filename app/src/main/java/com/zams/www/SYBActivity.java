package com.zams.www;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.GouwucheAdapter;
import com.hengyushop.airplane.adapter.GuigeListAdapter;
import com.hengyushop.airplane.adapter.GuigeListlAdapter;
import com.hengyushop.airplane.adapter.GuiGeListviewAdapter;
import com.hengyushop.airplane.adapter.GuiGeListviewAdapter.ViewHolder1;
import com.hengyushop.airplane.adapter.GuiGeListviewAdapter.ViewHolder2;
import com.hengyushop.airplane.adapter.GuiGeListviewAdapter.ViewHolder3;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.XiangqingData;
import com.lglottery.www.common.Config;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import android.R.id;
import android.R.integer;
import android.R.interpolator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
/**
 * 规格
 * @author Administrator
 *
 */
public class SYBActivity extends BaseActivity{
	private ListView listview_01;
	private GuiGeListviewAdapter adapter;
	//	private GuigeListAdapter adapter;
	private GuigeListlAdapter adapter1;
	private TextView market_information_sep_price;
	private String retailPrice;
	public String id,id1,id2;
	public String title,title1,title2;
	public int gk_id = 0;
	public int gk_id1 = 0;
	public int gk_id2 = 0;
	LinearLayout no_data_no;
	GuigeData md;
	GuigeBean mb;
	private DialogProgress progress;
	TextView market_information_seps_num;
	private SharedPreferences spPreferences;
	public static String user_name,user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.market_information_sep_pop_ll);
		listview_01 = (ListView) findViewById(R.id.listview_01);
		progress = new DialogProgress(SYBActivity.this);
		//		progress.CreateProgress();
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		loadWeatherll();
		inter();
	}


	//	@Override
	//	protected void onResume() {
	//		// TODO Auto-generated method stub
	//		super.onResume();
	//	}
	public void inter() {
		no_data_no = (LinearLayout) findViewById(R.id.no_data_no);
		ImageView market_information_sep_ico = (ImageView) findViewById(R.id.market_information_sep_ico);
		//		Button market_information_sep_close = (Button) findViewById(R.id.market_information_sep_close);
		market_information_sep_price = (TextView) findViewById(R.id.market_information_sep_price);
		TextView market_information_sep_name = (TextView) findViewById(R.id.market_information_sep_name);
		//		LinearLayout market_information_sep_content = (LinearLayout) findViewById(R.id.market_information_sep_content);
		Button market_information_pop_shopcart = (Button) findViewById(R.id.market_information_pop_shopcart);
		Button market_information_pop_buy = (Button) findViewById(R.id.market_information_pop_buy);
		//		LinearLayout pop_bottom1 = (LinearLayout) findViewById(R.id.pop_bottom1);
		//		LinearLayout pop_bottom0 = (LinearLayout) findViewById(R.id.pop_bottom0);
		// 关于数量
		TextView market_information_seps_add = (TextView) findViewById(R.id.market_information_seps_add);
		TextView market_information_seps_del = (TextView) findViewById(R.id.market_information_seps_del);
		market_information_seps_num = (TextView) findViewById(R.id.market_information_seps_num);

		market_information_seps_num.setText("1");

		Button market_information_pop_sure = (Button)findViewById(R.id.market_information_pop_sure);
		String proFaceImg = getIntent().getStringExtra("proFaceImg");
		imageLoader.displayImage(RealmName.REALM_NAME_HTTP + proFaceImg,market_information_sep_ico);
		String proName = getIntent().getStringExtra("proName");
		market_information_sep_name.setText(proName);
		retailPrice = getIntent().getStringExtra("retailPrice");
		market_information_sep_price.setText("￥" + retailPrice);


		try {
			market_information_seps_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int num = Integer.parseInt(market_information_seps_num
							.getText().toString());
					market_information_seps_num.setText(String.valueOf(num + 1));
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			//产品减少
			market_information_seps_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int num = Integer.parseInt(market_information_seps_num
							.getText().toString());
					if (num != 1) {
						market_information_seps_num.setText(String.valueOf(num - 1));
					} else {
						Toast.makeText(getApplicationContext(), "不能再减了", 200)
								.show();
					}
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


		//加入购物车
		market_information_pop_shopcart
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						try {

							if (id != null) {
								if (gk_id == 0) {
									Toast.makeText(getApplicationContext(), "请选择商品的属性", 200).show();
								}else {
									if (!user_name.equals("")) {
										oadWeather_gouwuche();
									} else {
										Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
										startActivity(intent);
									}
								}
							}else if (id1 != null){
								if (gk_id1 == 0) {
									Toast.makeText(getApplicationContext(), "请选择商品的属性", 200).show();
								}else {
									if (!user_name.equals("")) {
										oadWeather_gouwuche();
									} else {
										Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
										startActivity(intent);
									}
								}
							}else if (id2 != null){
								if (gk_id2 == 0) {
									Toast.makeText(getApplicationContext(), "请选择商品的属性", 200).show();
								}else {
									if (!user_name.equals("")) {
										oadWeather_gouwuche();
									} else {
										Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
										startActivity(intent);
									}
								}
							}else {

								//						if (gk_id == 0) {
								//							Toast.makeText(getApplicationContext(), "请选择商品的属性", 200).show();
								//						}else if (gk_id1 == 0){
								//							Toast.makeText(getApplicationContext(), "请选择商品的颜色", 200).show();
								//						}else if (gk_id2 == 0){
								//							Toast.makeText(getApplicationContext(), "请选择商品的属性", 200).show();
								//						}else {

								if (!user_name.equals("")) {
									oadWeather_gouwuche();
									//							} else if (UserLoginActivity.id != null) {
									//								Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
									//								startActivity(intent);
								} else {
									Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
									startActivity(intent);
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});
		//取消
		market_information_pop_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	/**
	 * 加入购物车数据
	 */
	private void oadWeather_gouwuche() {
		progress.CreateProgress();
		//		String id = UserLoginActivity.id;
		//		String user_name = UserLoginActivity.user_name;
		System.out.println("1================"+user_id);
		System.out.println("2================"+user_name);
		String goods_id = getIntent().getStringExtra("goods_id");
		String article_id = getIntent().getStringExtra("article_id");
		String geshu = market_information_seps_num.getText().toString().trim();
		System.out.println("结果呢1=============="+geshu);
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_cart?user_id="+user_id+"&user_name="+user_name+
				"&article_id="+article_id+"&goods_id="+goods_id+"&quantity="+geshu+"",new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0,String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					System.out.println("1================"+arg1);
					progress.CloseProgress();
					String info = jsonObject.getString("info");
					Toast.makeText(getApplicationContext(), info, 200).show();
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("==========================访问接口失败！");
				System.out.println("========================="+arg0);
				System.out.println("=========================="+arg1);
				super.onFailure(arg0, arg1);
			}

		}, null);

	}

	/**
	 * 解析规格列表数据
	 */
	private void loadWeatherll() {
		progress.CreateProgress();
		String article_id = getIntent().getStringExtra("article_id");
		System.out.println("article_id=========================="+article_id);
		//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_spec_list?" +"channel_name=goods",
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_spec_list?" +"article_id="+article_id+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====规格数据====================="+arg1);
						Message msg = new Message();
						msg.what = 0;
						msg.obj = arg1;
						handler.sendMessage(msg);
					}
				}, null);
	}
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					formatWeatherll((String) msg.obj);
					break;
				case 1:
					//				Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
					//				startActivity(intent);
					break;
				case 5:
					ArrayList data_shuzu = WareInformationActivity.data_shuzu;
					ArrayList data_mrz = WareInformationActivity.data_mrz;
					ArrayList data_monney = WareInformationActivity.data_monney;
					System.out.println("值是1============="+data_mrz.size());
					adapter = new GuiGeListviewAdapter(SYBActivity.this,list,data,data1,data2,data_id,data_id1,data_id2,
							data_shuzu,data_mrz,data_monney);
					//				adapter = new GuigeListAdapter(SYBActivity.this,list,data,data1,data2);
					listview_01.setAdapter(adapter);
					setListViewHeightBasedOnChildren(listview_01);
					//				adapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		};
	};

	ArrayList data_tv,data,data1,data2,data_id,data_id1,data_id2;
	List<GuigeData> list = new ArrayList<GuigeData>();
	private void formatWeatherll(String result) {
		data_tv = new ArrayList();
		data = new ArrayList();
		data1 = new ArrayList();
		data2 = new ArrayList();
		data_id = new ArrayList();
		data_id1 = new ArrayList();
		data_id2 = new ArrayList();
		//		List<GuigeData> list = new ArrayList<GuigeData>();
		try {
			System.out.println("=====规格数据11====================="+result);
			JSONObject object = new JSONObject(result);
			JSONArray jobt = object.getJSONArray("data");
			int lenth = jobt.length();
			for (int i = 0; i < jobt.length(); i++) {
				JSONObject obj= jobt.getJSONObject(i);
				md = new GuigeData();
				md.setTitle(obj.getString("title"));
				String title_1 = obj.getString("title");
				data_tv.add(title_1);

				String cars = obj.getString("child");
				JSONArray jaArray = obj.getJSONArray("child");
				int len = jaArray.length();
				System.out.println("===============1================+"+len);
				if (len == 0) {

				}
				switch (i) {
					case 0:
						for (int j = 0; j < jaArray.length(); j++) {
							JSONObject objc= jaArray.getJSONObject(j);
							title = objc.getString("title");
							id = objc.getString("id");
							data_id.add(id);
							System.out.println("=====值====================="+title);
							data.add(title);
							//	    		adapter1 = new GuigeListlAdapter(SYBActivity.this,data,data_tv);
							//				listview_01.setAdapter(adapter1);

						}
						break;
					case 1:
						for (int j = 0; j < jaArray.length(); j++) {
							JSONObject objc= jaArray.getJSONObject(j);
							title1 = objc.getString("title");
							id1 = objc.getString("id");
							data_id1.add(id1);
							System.out.println("=====值1====================="+title1);
							data1.add(title1);
						}
						break;
					case 2:
						for (int j = 0; j < jaArray.length(); j++) {
							JSONObject objc= jaArray.getJSONObject(j);
							title2 = objc.getString("title");
							id2 = objc.getString("id");
							data_id2.add(id2);
							System.out.println("=====值2====================="+title2);
							data2.add(title2);
						}
						break;

					default:
						break;
				}
				//			md.setList(new ArrayList<GuigeBean>());
				//    		for (int j = 0; j < jaArray.length(); j++) {
				//    		JSONObject objc= jaArray.getJSONObject(j);
				//    		mb = new GuigeBean();
				//    		mb.setTitle(objc.getString("title"));
				//    		String title = objc.getString("title");
				//    		System.out.println("=====值====================="+title);
				//    		data.add(title);
				//    		md.getList().add(mb);
				//    		}
				list.add(md);
			}

			if (title == null) {
				progress.CloseProgress();
				no_data_no.setVisibility(View.VISIBLE);
				listview_01.setVisibility(View.GONE);
			}else if (title1 == null) {
				progress.CloseProgress();
				no_data_no.setVisibility(View.VISIBLE);
				listview_01.setVisibility(View.GONE);
			}else if (title2 == null) {
				progress.CloseProgress();
				no_data_no.setVisibility(View.VISIBLE);
				listview_01.setVisibility(View.GONE);
			}else{
				progress.CloseProgress();
				handler.sendEmptyMessage(5);
			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	public class GuiGeListviewAdapter  extends BaseAdapter {
		private List<GuigeData> list;
		private ArrayList data;
		private ArrayList data1;
		private ArrayList data2;
		private ArrayList data_id,data_id1,data_id2,data_shuzu,data_mrz,data_monney;
		private Context context;
		LayoutInflater inflater;
		GouwucheAdapter MyAdapter;
		GouwucheAdapter MyAdapter1;
		GouwucheAdapter MyAdapter2;
		String sell_price;
		final int TYPE_1 = 0;
		final int TYPE_2 = 1;
		final int TYPE_3 = 2;
		private ImageLoader imageLoader;

		public GuiGeListviewAdapter (ImageLoader imageLoader) {
			// TODO Auto-generated constructor stub
			this.imageLoader = imageLoader;
		}

		public GuiGeListviewAdapter (Context context,List<GuigeData> list,ArrayList data,ArrayList data1,ArrayList data2
				,ArrayList data_id,ArrayList data_id1,ArrayList data_id2,ArrayList data_shuzu,ArrayList data_mrz
				,ArrayList data_monney) {
			try {

				this.list = list;
				this.data = data;
				this.data1 = data1;
				this.data2 = data2;
				this.data_id = data_id;
				this.data_id1 = data_id1;
				this.data_id2 = data_id2;
				this.data_shuzu = data_shuzu;
				this.data_monney = data_monney;
				this.data_mrz = data_mrz;
				this.context = context;
				Log.i("data1", data1+"");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		public int getCount() {
			//			Log.i("data", "=======1======");
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
			else if(p == 1)
				return TYPE_2;
			else if(p == 2)
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
							convertView = inflater.inflate(R.layout.guige_item,   parent, false);
							holder1 = new ViewHolder1();
							holder1.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);
							holder1.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
							convertView.setTag(holder1);

							break;
						case TYPE_2:
							convertView = inflater.inflate(R.layout.guige_item,   parent, false);
							holder2 = new ViewHolder2();
							holder2.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);
							holder2.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
							convertView.setTag(holder2);

							break;
						case TYPE_3:
							convertView = inflater.inflate(R.layout.guige_item,   parent, false);
							holder3 = new ViewHolder3();
							holder3.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);
							holder3.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
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
						MyAdapter=new GouwucheAdapter(data,context);
						holder1.gridView.setAdapter(MyAdapter);

						holder1.gridView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								try {
									MyAdapter.setSeclection(arg2);
									MyAdapter.notifyDataSetChanged();
									id = (String)data_id.get(arg2);
									gk_id = Integer.parseInt(id);
									//		            	String zhi = (String)data_mrz.get(arg2);
									//		            	System.out.println("默认值====="+zhi);
									System.out.println("id值是====="+id);
									System.out.println("值========="+data_shuzu.get(arg2));
									String monney1 = (String)data_monney.get(arg2);
									System.out.println("价格========="+monney1);
									sell_price  = ","+id+","+id1+","+id2+",";
									System.out.println("拼接的值========="+sell_price);
									if (sell_price.equals(data_shuzu.get(arg2))) {
										String monney = (String)data_monney.get(arg2);
										System.out.println("价格0========="+monney);
										market_information_sep_price.setText("￥" + monney);
									}
									System.out.println("11/////////////////////////////////////////////////");
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
						});

						break;
					case TYPE_2:
						try {

							holder2.tv_yhwenzi.setText(list.get(position).getTitle());
							MyAdapter1=new GouwucheAdapter(data1,context);
							holder2.gridView.setAdapter(MyAdapter1);

							holder2.gridView.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
									try {
										MyAdapter1.setSeclection(arg2);
										MyAdapter1.notifyDataSetChanged();
										id1 = (String)data_id1.get(arg2);
										gk_id1 = Integer.parseInt(id1);
										//		            	String zhi = (String)data_mrz.get(arg2);
										//		            	System.out.println("默认值====="+zhi);
										System.out.println("id值是1====="+id1);
										System.out.println("值1========="+data_shuzu.get(arg2));
										String monney1 = (String)data_monney.get(arg2);
										System.out.println("价格========="+monney1);
										sell_price  = ","+id+","+id1+","+id2+",";
										System.out.println("拼接的值========="+sell_price);
										if (sell_price.equals(data_shuzu.get(arg2))) {
											String monney = (String)data_monney.get(arg2);
											System.out.println("价格1========="+monney);
											market_information_sep_price.setText("￥" + monney);
										}
										System.out.println("22/////////////////////////////////////////////////");
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
						MyAdapter2=new GouwucheAdapter(data2,context);
						holder3.gridView.setAdapter(MyAdapter2);

						holder3.gridView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								try {
									MyAdapter2.setSeclection(arg2);
									MyAdapter2.notifyDataSetChanged();
									id2 = (String)data_id2.get(arg2);
									//		            	String zhi = (String)data_mrz.get(arg2);
									gk_id2 = Integer.parseInt(id2);
									//		            	System.out.println("默认值====="+zhi);
									System.out.println("id值是2====="+id2);
									sell_price  = ","+id+","+id1+","+id2+",";
									System.out.println("拼接的值========="+sell_price);
									System.out.println("值2========="+data_shuzu.get(arg2));
									String monney1 = (String)data_monney.get(arg2);
									System.out.println("价格========="+monney1);
									//		                if (mrz == 1) {
									//		                	System.out.println("1=========");
									//							String monney = (String)data_monney.get(arg2);
									//							 System.out.println("价格1========="+monney);
									//							 market_information_sep_price.setText("￥:" + monney);
									////							 notifyDataSetChanged();
									//						}else {

									//						market_information_sep_price.setText("￥" + monney1);
									if (sell_price.equals(data_shuzu.get(arg2))) {
										String monney = (String)data_monney.get(arg2);
										System.out.println("价格2========="+monney);
										market_information_sep_price.setText("￥:" + (String)data_monney.get(arg2));
									}

									//						}
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}

						});
						break;

				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return convertView;
		}

	}


}
