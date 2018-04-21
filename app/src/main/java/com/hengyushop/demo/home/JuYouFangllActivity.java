package com.hengyushop.demo.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.pub.JegGoodsListAdapter;
import com.android.hengyu.pub.MyAdapter;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.droid.Activity01;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.shangpingListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JuYouFangllActivity extends BaseActivity {
	private List<EnterpriseData> list = null;
	private int listll;
	private ArrayList<GoodsListData> lists  = null;
	private ArrayList<shangpingListData> list_ll = null;
	private MyGridView myGridView;
	private MyAdapter adapter;
	//	private int INDX = -1;
	//	private int quanbu_id = 0;
	private int INDX = 0;
	private GoodsListAdapter myadapter;
	private ListView new_list,listview;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	int useridString;
	private int id = 0;
	private ImageView iv_ditu;
	LinearLayout main_item0,main_item1,main_item2;
	private TextView tv_city;
	String city;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juyoufang_home);
		progress = new DialogProgress(this);
		Initialize();
		loadCate();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences spPreferences = getSharedPreferences("longuserset_city", MODE_PRIVATE);
		city = spPreferences.getString("city", "");
		System.out.println("city=================" + city);
		if (city.equals("")) {
			tv_city.setText("未定位");
		} else {
			tv_city.setText(city + "市");
		}
	}

	private void Initialize() {
		iv_ditu = (ImageView) findViewById(R.id.iv_ditu);
		iv_ditu.setBackgroundResource(R.drawable.ditu);
		myGridView = (MyGridView) findViewById(R.id.mGv);
		new_list = (ListView) findViewById(R.id.new_list);
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		//		refresh.setOnFooterRefreshListener(listFootListener);
		listview = (ListView)findViewById(R.id.listview);
		tv_city = (TextView) findViewById(R.id.tv_city);
		main_item0 = (LinearLayout) findViewById(R.id.main_item0);
		main_item1 = (LinearLayout) findViewById(R.id.main_item1);

		// 切换城市
		main_item0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(JuYouFangllActivity.this, Activity01.class);
				// String strwhere_zhi = tv1.getText().toString().trim();
				// intent.putExtra("strwhere_zhi", strwhere_zhi);
				startActivity(intent);
			}
		});
		// 热门城市
		main_item1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(JuYouFangllActivity.this, Activity01.class);
				startActivity(intent);
			}
		});
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					//				myadapter.putData(lists);
					System.out.println("========走了几篇=============这里"+list_ll.size());
					System.out.println("=====================这里"+lists.size());

					JegGoodsListAdapter myadapter = new JegGoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
					new_list.setAdapter(myadapter);
					myadapter.notifyDataSetChanged();


					//				myadapter = new GoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
					//				new_list.setAdapter(myadapter);

					//				SpListDataAdapter sAdapter = new SpListDataAdapter(list_ll, getApplicationContext(), imageLoader);
					//				listview.setAdapter(sAdapter);
					System.out.println("1====================");

					break;
				case 1:
					listll = list.size()+1;
					System.out.println("个数是多少===================="+list.size());

					adapter = new MyAdapter(getApplicationContext(), list);
					myGridView.setAdapter(adapter);

					//				myGridView.setOnItemClickListener(new OnItemClickListener() {
					//
					//		            @Override
					//		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					////		            	 INDX =  arg2;
					//
					//		            	 quanbu_id = list.get(arg2).id;
					//		            	 System.out.println("=====第二层数据1====================="+quanbu_id);
					//		            	 load_list(quanbu_id, true);
					//
					//		            	 adapter.setSeclection(arg2);
					//		            	 adapter.notifyDataSetChanged();
					//		            }
					//		        });

					//				if (list.size()>0) {
					//					load_list(id, true);
					//				}
					//				if (quanbu_id == 0) {
					//					System.out.println("1全部ID=========="+quanbu_id);
					////					for (int i = 0; i < list.size(); i++) {
					////					}
					//					int id = 0;
					//					load_list(id, true);
					//				}
					break;
				case 2:
					break;


				default:
					break;
			}
		};
	};
	//商家列表
	private void loadCate(){
		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_trade_list?" +
				//				"trade_id=0&page_size=10&page_index=1&strwhere=&orderby=", new AsyncHttpResponseHandler(){
				"channel_name=trade&parent_id=273", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		}, null);
	}

	//	ArrayList<ArrayList<WareData>> lists = null;
	public void parse(String st) {
		try {
			//			System.out.println("类别列表=========="+st);
			list = new ArrayList<EnterpriseData>();
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			System.out.println("jsonArray"+jsonArray.length());
			int len = jsonArray.length();
			//			System.out.println("值1=========="+len);
			int lenth = len+1;
			//			System.out.println("值2=========="+lenth);
			for (int i = 0; i < len; i++) {
				EnterpriseData data = new EnterpriseData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.title = object.getString("title");
				data.icon_url = object.getString("icon_url");
				Log.v("data1", data.id + "");
				list.add(data);
				int id = data.id;
				//				if (quanbu_id == 1000) {
				load_list(id, true);
				//				}
			}
			inter();
			handler.sendEmptyMessage(1);

			//			myGridView.setOnItemClickListener(new OnItemClickListener() {
			//
			//	            @Override
			//	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			////	            	 int  quanbu_id = list.get(arg2).id;
			//	            	 INDX =  arg2;
			//	            	 System.out.println("=====第二层数据1====================="+INDX);
			//	            	 load_list(INDX, true);
			//
			//	            	 adapter.setSeclection(arg2);
			//	            	 adapter.notifyDataSetChanged();
			//	            }
			//	        });

			progress.CloseProgress();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 第二个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(final int INDX,boolean flag) {
		RUN_METHOD = 1;
		lists = new ArrayList<GoodsListData>();
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 0;
			lists = new ArrayList<GoodsListData>();
			System.out.println("=====================6--");
		}
		System.out.println("=====================001--"+INDX);
		String  seturl = RealmName.REALM_NAME_LL+"/get_user_commpany?trade_id="+INDX+"" +
				"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=";
		System.out.println("=====================002--"+seturl);

		AsyncHttp.get(seturl, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("商家列表=====================二级值1"+arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					System.out.println("商家列表de值====================="+status);
					if (status.equals("y")) {

						JSONArray jsonArray = jsonObject.getJSONArray("data");
						int len = jsonArray.length();

						for(int i=0;i<jsonArray.length();i++){
							JSONObject object = jsonArray.getJSONObject(i);
							GoodsListData spList = new GoodsListData();
							spList.user_id = object.getString("user_id");
							spList.trade_title = object.getString("trade_title");
							spList.img_url = object.getString("img_url");
							spList.name = object.getString("name");
							lists.add(spList);
							//										list_id = spList.user_id ;//
							String user_id = spList.user_id ;//
							//										int user_id =  Integer.parseInt(id);
							System.out.println("二级值2====================="+user_id);
							loadCatell(user_id,true);

						}
						//									int id = lists.get(arg0).user_id;
						//									System.out.println("二级值的id继承====================="+list_id);
						//									int gd_id = 1;
						//									loadCatell(gd_id);
						if(len!=0){
							CURRENT_NUM =CURRENT_NUM+VIEW_NUM;
						}
						//									 System.out.println("1商家列表调用适配器==========");
						//									handler.sendEmptyMessage(0);
					}else {
						handler.sendEmptyMessage(0);
						System.out.println("2商家列表没有调用适配器==========");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, getApplicationContext());
	}



	//商品列表
	public void loadCatell(String user_id,boolean flag){
		try {
			System.out.println("二级值3====================="+user_id);
			//			useridString = user_id;
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
					"channel_name=goods&top=3&strwhere=user_id="+user_id+"", new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					try {
						//					System.out.println("二级值32====================="+useridString);
						System.out.println("商品列表2=========="+arg1);
						list_ll = new ArrayList<shangpingListData>();
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						String datall = jsonObject.getString("data");
						//						System.out.println("商家列表de值====================="+datall);
						//						if (status.equals("y")) {
						if (datall.equals("[]")) {
							System.out.println("2空值==========");

							//						String datall = jsonObject.getString("data");
							//						System.out.println("商品列表de值长度====================="+datall.length());
							//						String zhou = "0";
							//						String de = zhou;
							//						System.out.println("商品列表222====================="+de);
							//						int hehe = 1;
							//						if (hehe > 2) {
							//						if (useridString == 75) {
							//							handler.sendEmptyMessage(0);
							//						}
							//							 handler.sendEmptyMessage(0);
							System.out.println("1没有值调用适配器==========");
						}else {
							//						  System.out.println("2空值==========");

							JSONArray jsonArray = jsonObject.getJSONArray("data");
							System.out.println("jsonArray==="+jsonArray.length());
							for (int i = 0; i < jsonArray.length(); i++) {
								shangpingListData data = new shangpingListData();
								JSONObject object = jsonArray.getJSONObject(i);
								data.id = object.getString("id");
								data.title = object.getString("title");
								data.img_url = object.getString("img_url");
								data.category_title = object.getString("category_title");
								Log.v("data1", data.id + "");
								String id = data.id;
								System.out.println("第三级的id========="+id);
								list_ll.add(data);
							}
							handler.sendEmptyMessage(0);
							System.out.println("2有值调用适配器==========");
						}
						//					handler.sendEmptyMessage(2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 第一个列表数据解析
	 */
	//	private void load_list2(final int INDX,boolean flag) {
	//		lists = new ArrayList<GoodsListData>();
	//		if(flag){
	//			//计数和容器清零
	//			CURRENT_NUM = 0;
	//			lists = new ArrayList<GoodsListData>();
	//		}
	//		System.out.println("=====================002--"+INDX);
	//			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_user_commpany?trade_id="+INDX+"" +
	//							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
	//							 new AsyncHttpResponseHandler(){
	//							@Override
	//							public void onSuccess(int arg0, String arg1) {
	//								// TODO Auto-generated method stub
	//								super.onSuccess(arg0, arg1);
	//								System.out.println("商家列表=====================二级值2="+arg1);
	//								try {
	//									JSONObject jsonObject = new JSONObject(arg1);
	//									JSONArray jsonArray = jsonObject.getJSONArray("data");
	//									 int len = jsonArray.length();
	//									for(int i=0;i<jsonArray.length();i++){
	//										JSONObject object = jsonArray.getJSONObject(i);
	//										GoodsListData spList = new GoodsListData();
	//										spList.user_id = object.getString("user_id");
	//										spList.trade_title = object.getString("trade_title");
	//										spList.img_url = object.getString("img_url");
	//										spList.name = object.getString("name");
	//										lists.add(spList);
	////											myadapter = new GoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
	////											new_list.setAdapter(myadapter);
	////											loadCatell(user_id,true);
	//									}
	//
	//								} catch (JSONException e) {
	//									// TODO Auto-generated catch block
	//									e.printStackTrace();
	//								}
	//							}
	//						}, null);
	//	}
	//商品列表
	//	private void loadCatell(int user_id){
	//		try {
	////			System.out.println("二级值3====================="+user_id);
	//		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
	//                "channel_name=goods&top=3&strwhere=user_id="+user_id+"", new AsyncHttpResponseHandler(){
	//			@Override
	//			public void onSuccess(int arg0, String arg1) {
	//				// TODO Auto-generated method stub
	//				super.onSuccess(arg0, arg1);
	//				try {
	//					System.out.println("（商品列表=========="+arg1);
	//					if (quanbu_id == 0) {
	////						System.out.println("1全部ID=========="+quanbu_id);
	////						load_list(quanbu_id, true);
	//					list_ll = new ArrayList<shangpingListData>();
	//					JSONObject jsonObject = new JSONObject(arg1);
	//					JSONArray jsonArray = jsonObject.getJSONArray("data");
	//					System.out.println("jsonArray"+jsonArray.length());
	//					for (int i = 0; i < jsonArray.length(); i++) {
	//						shangpingListData data = new shangpingListData();
	//						JSONObject object = jsonArray.getJSONObject(i);
	//						data.id = object.getString("id");
	//						data.title = object.getString("title");
	//						data.img_url = object.getString("img_url");
	//						data.category_title = object.getString("category_title");
	//						Log.v("data1", data.id + "");
	//						list_ll.add(data);
	////						if (quanbu_id == 0) {
	////							JegGoodsListAdapter myadapter = new JegGoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
	////							new_list.setAdapter(myadapter);
	//							System.out.println("2全部ID=========="+quanbu_id);
	////						}
	//					}
	//					handler.sendEmptyMessage(0);
	//					}else {
	//						list_ll = new ArrayList<shangpingListData>();
	//						JSONObject jsonObject = new JSONObject(arg1);
	//						JSONArray jsonArray = jsonObject.getJSONArray("data");
	//						System.out.println("jsonArray"+jsonArray.length());
	//						for (int i = 0; i < jsonArray.length(); i++) {
	//							shangpingListData data = new shangpingListData();
	//							JSONObject object = jsonArray.getJSONObject(i);
	//							data.id = object.getString("id");
	//							data.title = object.getString("title");
	//							data.img_url = object.getString("img_url");
	//							data.category_title = object.getString("category_title");
	//							Log.v("data1", data.id + "");
	//							String id = data.id;
	//							System.out.println("第三级的id========="+id);
	//							list_ll.add(data);
	//					  }
	//						handler.sendEmptyMessage(0);
	//					}
	////					handler.sendEmptyMessage(2);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		}, null);
	//
	//		} catch (Exception e) {
	//			// TODO: handle exception
	//			e.printStackTrace();
	//		}
	//
	//    }

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};

	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {

						if(RUN_METHOD==0){
							System.out.println("=======3=="+RUN_METHOD);
							load_list(INDX, true);
						}else {
							System.out.println("=======4=="+INDX);
							load_list(INDX, false);
						}
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};


	private void inter(){

		int size = list.size();//数据总长度

		//获得屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int windowWidth = metrics.widthPixels;
		int itemWidth = windowWidth/4;

		//获得屏幕宽度也可以这样写
		//int itemWidth = getWindowManager().getDefaultDisplay().getWidth() / 5;//屏幕显示默认数量

		int gridViewWidth = (int)(size * itemWidth);//linearLayout的总宽度
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth,LinearLayout.LayoutParams.MATCH_PARENT);
		myGridView.setLayoutParams(params);//设置GridView布局参数
		myGridView.setNumColumns(size);//动态设置GridView列数
	}

	//	public class MyAdapter extends BaseAdapter {
	//
	//		private Context mContext;
	//		private List<EnterpriseData> List;
	//		private LayoutInflater mInflater;
	//		private int clickTemp = 0;
	////		private int clickTemp = -1;
	//
	//		public MyAdapter(Context context, List<EnterpriseData> list){
	//			this.List = list;
	//			this.mContext = context;
	//			mInflater = LayoutInflater.from(context);
	////			System.out.println("============="+list.size()+1);
	//		}
	//
	//		@Override
	//		public int getCount() {
	//			if (list.size()<1) {
	//				return 0;
	//			}else{
	//				return list.size();
	//
	//			}
	//		}
	//
	//		public void setSeclection(int position) {
	//			clickTemp = position;
	//		}
	//
	//		@Override
	//		public Object getItem(int position) {
	//			// TODO Auto-generated method stub
	//			return list.get(position);
	//		}
	//
	//		@Override
	//		public long getItemId(int position) {
	//			return position;
	//		}
	//
	//		@Override
	//		public View getView(final int position, View convertView, ViewGroup parent) {
	//			final ViewHolder holder;
	//			if (convertView == null) {
	//				holder =  new ViewHolder();
	//				convertView = mInflater.inflate(R.layout.leibie_item, null);
	//				holder.img = (ImageView) convertView.findViewById(R.id.img);
	//				holder.text = (TextView) convertView.findViewById(R.id.tv);
	////				holder.radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
	//				convertView.setTag(holder);
	//			}else {
	//				holder = (ViewHolder) convertView.getTag();
	//			}
	//
	////			holder.img.setImageResource(dataList.get(position));
	////			holder.text.setText("第" + position + "项");
	////			clickTempll = position;
	//
	//			 try {
	//
	//			 System.out.println("个数1======================"+position);
	////			if (position == 0) {
	//////				RadioButton btn = (RadioButton) RadioGroup.inflate(getApplicationContext(), R.layout.common_btn, null);
	//////				btn.setText("全部");
	////				holder.text.setText("全部");
	////				holder.img.setImageResource(R.drawable.quanbu);
	//////				image.setImageDrawable(getResources().getDrawable(R.drawable.yourimage);
	////			}
	//
	////			if (position > 0) {
	//			holder.text.setText(list.get(position).title);
	//	        ImageLoader imageLoader=ImageLoader.getInstance();
	//	        imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+list.get(position).icon_url,holder.img);
	////	        System.out.println("值是多少2=========="+clickTemp);
	////			}
	//
	//			 } catch (Exception e) {
	//					// TODO: handle exception
	//				 e.printStackTrace();
	//				}
	//			if (clickTemp == position) {
	////				convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang
	//				holder.text.setTextColor(Color.RED);
	//			} else {
	////				convertView.setBackgroundColor(Color.TRANSPARENT);
	////				convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang
	//				holder.text.setTextColor(Color.GRAY);
	//		    }
	//			return convertView;
	//		}
	//
	//
	//		class ViewHolder{
	//			ImageView img;
	//			TextView text;
	//			RadioButton radioButton;
	//		}
	//	}

}
