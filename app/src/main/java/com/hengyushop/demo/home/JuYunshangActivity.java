package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.pub.JuyunshangAdapter;
import com.android.hengyu.pub.JuyunshangAdapterll;
import com.android.hengyu.pub.Juyunshanglist2Adapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.pub.YsjGoodsListAdapter;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListBean;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.XsgyListData;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.hengyushop.entity.shangpingListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class JuYunshangActivity extends BaseActivity {
	private List<EnterpriseData> list = null;
	private ArrayList<GoodsListData> lists;
	private ArrayList<shangpingListData> list_ll = null;
	private MyGridView myGridView;
	private MyAdapter adapter;
	public static int INDX = -1;
	GoodsListData data;
	GoodsListBean bean;
	private GoodsListAdapter myadapter;
	private Juyunshanglist2Adapter jysadapter;
//	private YsjGoodsListAdapter jysadapter;
	private ListView new_list,listview;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	private int id = 0;
	private GridView gridview;
	public AQuery mAq;
	int len;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juyunshang_home);
		progress = new DialogProgress(this);
		mAq = new AQuery(this);
		Initialize();
		lists = new ArrayList<GoodsListData>();
		jysadapter = new Juyunshanglist2Adapter(lists, getApplicationContext(), imageLoader);
		new_list.setAdapter(jysadapter);
		loadCate();
		
		
	}
	
	private void Initialize() {
		myGridView = (MyGridView) findViewById(R.id.mGv);
		new_list = (ListView) findViewById(R.id.new_list);
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		gridview = (GridView) findViewById(R.id.gridview);
		
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {
					
				jysadapter.putData(lists);
				Juyunshanglist2Adapter.mAq.clear();
				System.out.println("=====================2这里"+lists.size());
				new_list.setOnItemClickListener(new OnItemClickListener() {
	                
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String id = lists.get(arg2).getId();
						System.out.println("====================="+id);
						Intent intent = new Intent(JuYunshangActivity.this,WareInformationActivity.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				});

//				jysadapter = new JuyunshanglistAdapter(lists, getApplicationContext(), imageLoader);
//				new_list.setAdapter(jysadapter);
				
//				jysadapter = new YsjGoodsListAdapter(lists,list_ll,getApplicationContext(), imageLoader);
//				new_list.setAdapter(jysadapter);
				
//				jysadapter = new JuyunshangAdapter(lists,list_ll, getApplicationContext(), imageLoader);
//				new_list.setAdapter(jysadapter);
				
//				setListViewHeightBasedOnChildren(new_list);
				
//				JysimgerAdapter sAdapter = new JysimgerAdapter(list_ll, getApplicationContext(), imageLoader);
//				listview.setAdapter(sAdapter);
//				jysadapter.notifyDataSetChanged();
				
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					
				adapter = new MyAdapter(getApplicationContext(), list);
				myGridView.setAdapter(adapter);
				
				if (list.size()>0) {
					load_list(id, true);
				}
				
				myGridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		            	 INDX =  arg2;
		            	 System.out.println("=====第二层de数据====================="+INDX);
		            	 load_list(INDX, true);
		            	 
		            	 adapter.setSeclection(arg2);
		            	 adapter.notifyDataSetChanged();
		            }
		        });
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 2:
				try {
				JuyunshangAdapterll jysadapter = new JuyunshangAdapterll(list_ll, getApplicationContext(), imageLoader);
				gridview.setAdapter(jysadapter);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
				

			default:
				break;
			}
		};
	};
	
	//类别列表
	private void loadCate(){
		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_trade_list?" +
                "channel_name=trade&parent_id=278", new AsyncHttpResponseHandler(){
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
			System.out.println("类别列表=========="+st);
			list = new ArrayList<EnterpriseData>();
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			System.out.println("jsonArray"+jsonArray.length());
			list.add(0, null);
			for (int i = 0; i < jsonArray.length(); i++) {
				EnterpriseData data = new EnterpriseData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.title = object.getString("title");
				data.icon_url = object.getString("icon_url");
				Log.v("data1", data.id + "");
				list.add(data);
			}
			
			inter();
			handler.sendEmptyMessage(1);
			progress.CloseProgress();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 第1个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(final int INDX,boolean flag) {
		RUN_METHOD = 1;
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			lists = new ArrayList<GoodsListData>();
		}
		
		System.out.println("=====lists====================="+lists.size());
		System.out.println("=====================001--"+INDX);
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_user_commpany?trade_id="+INDX+"" +
							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=datatype='Supply'&orderby=",
							 new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("商家列表=====================二级值1"+arg1);
								try {
									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									String info = object.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = object.getJSONArray("data");
										len = jsonArray.length();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject object1 = jsonArray.getJSONObject(i);
											data = new GoodsListData();
											data.setId(object1.getString("id"));
											data.setName(object1.getString("name"));
											data.setImg_url(object1.getString("img_url"));
											data.setLogo_url(object1.getString("logo_url"));
											data.setUser_id(object1.getString("user_id"));
											String article = object1.getString("article");
											data.setList(new ArrayList<GoodsListBean>());
											JSONArray ja = new JSONArray(article);
									for (int k = 0; k < ja.length(); k++) {
										bean = new GoodsListBean();
										JSONObject obt = ja.getJSONObject(k);
										bean.setId(obt.getString("id"));
										bean.setTitle(obt.getString("title"));
										bean.setImg_url(obt.getString("img_url"));
										bean.setSell_price(obt.getString("sell_price"));
										bean.setMarket_price(obt.getString("market_price"));
										String img_url = bean.getImg_url();
//										System.out.println("=====内容img_url====================="+img_url);
										data.getList().add(bean);
									}
									System.out.println("=====lists11====================="+lists.size());
									lists.add(data);
									System.out.println("=====lists22====================="+lists.size());
//									loadCate(data.user_id);
									}
									}else {
										progress.CloseProgress();
										Toast.makeText(JuYunshangActivity.this, info, 200).show();
									}
									System.out.println("=====lists2====================="+lists.size());
									progress.CloseProgress();
									handler.sendEmptyMessage(0);
									
									if(len!=0){
										CURRENT_NUM =CURRENT_NUM+1;
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						}, null);
	}
	//商品列表
	ArrayList<shangpingListData> list_num;
	private void loadCate(String user_id){
	list_num = new ArrayList<shangpingListData>();
			System.out.println("走到商品列表了=========="+user_id);
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
                "channel_name=goods&top=100&strwhere=user_id="+user_id+"", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					System.out.println("（商品列表）=========="+arg1);
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					String info = jsonObject.getString("info");
					if (status.equals("y")) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						shangpingListData data = new shangpingListData();
						JSONObject object = jsonArray.getJSONObject(i);
						data.id = object.getString("id");
						list_num.add(data);
					}
                    }else {
                    	Toast.makeText(JuYunshangActivity.this, info, 200).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);
		
    }
	//商品列表
//	private void loadCatell(int user_id){
//		try {
//			System.out.println("走到商品列表了=========="+user_id);
//		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
//                "channel_name=goods&top=3&strwhere=user_id="+user_id+"", new AsyncHttpResponseHandler(){
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0, arg1);
//				try {
//					System.out.println("（商品列表）=========="+arg1);
//					list_ll = new ArrayList<shangpingListData>();
//					JSONObject jsonObject = new JSONObject(arg1);
//					String datall = jsonObject.getString("data");
//					System.out.println("商品列表de值====================="+datall);
//					if (!datall.equals("")) {
//					JSONArray jsonArray = jsonObject.getJSONArray("data");
//						
//					System.out.println("jsonArray"+jsonArray.length());
//					for (int i = 0; i < jsonArray.length(); i++) {
//						shangpingListData data = new shangpingListData();
//						JSONObject object = jsonArray.getJSONObject(i);
//						data.id = object.getString("id");
//						data.user_id = object.getString("user_id");
//						data.title = object.getString("title");
//						data.img_url = object.getString("img_url");
//						Log.v("data1", data.id + "");
//						list_ll.add(data);
//						String  zhoude = data.title;
////						jysadapter = new JuyunshangAdapter(lists,list_ll, getApplicationContext(), imageLoader);
////						new_list.setAdapter(jysadapter);
////						TextView zhouTextView = (TextView) findViewById(R.id.tv_zhou);
////						zhouTextView.setText(zhoude);
//					}
//					
//					handler.sendEmptyMessage(0);
//					System.out.println("1有值调用适配器==========");
//                    }else {
////                    	handler.sendEmptyMessage(2);
//                    	System.out.println("2空值调用适配器了==========");
//					}
////					System.out.println("调用适配器了==========");
////					handler.sendEmptyMessage(0);
//					
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
						
//					if(RUN_METHOD==0){
//						System.out.println("=======3=="+RUN_METHOD);
//						load_list2(INDX, true);
//					}else {
						System.out.println("=======4=="+INDX);
						load_list(INDX, false);
//					}
					refresh.onFooterRefreshComplete();
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};
	
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
//								System.out.println("=====================二级值2"+arg1);
//								try {
//									JSONObject jsonObject = new JSONObject(arg1);
//									JSONArray jsonArray = jsonObject.getJSONArray("data");
//									 int len = jsonArray.length();
//									for(int i=0;i<jsonArray.length();i++){
//										JSONObject object = jsonArray.getJSONObject(i);
//										GoodsListData spList = new GoodsListData();
//										spList.user_id = object.getInt("user_id");
//										spList.trade_title = object.getString("trade_title");
//										spList.img_url = object.getString("img_url");
//										spList.name = object.getString("name");
//										lists.add(spList);
//											int user_id = spList.user_id ;//
////											int user_id =  Integer.parseInt(id);
//											System.out.println("二级值2====================="+user_id);
////											loadCatell(user_id);
//									}
//									handler.sendEmptyMessage(0);
//								} catch (JSONException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}, null);
//	}
	
	
	private void inter(){
		
		int size = list.size();//数据总长度

		//获得屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int windowWidth = metrics.widthPixels;
		int itemWidth = windowWidth/5;

		//获得屏幕宽度也可以这样写
		//int itemWidth = getWindowManager().getDefaultDisplay().getWidth() / 5;//屏幕显示默认数量

		int gridViewWidth = (int)(size * itemWidth);//linearLayout的总宽度
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth,LinearLayout.LayoutParams.MATCH_PARENT);
		myGridView.setLayoutParams(params);//设置GridView布局参数
		myGridView.setNumColumns(size);//动态设置GridView列数
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
	
	public class MyAdapter extends BaseAdapter {

		private Context mContext;
		private List<EnterpriseData> List;
		private LayoutInflater mInflater;
		private int clickTemp = 0;

		public MyAdapter(Context context, List<EnterpriseData> list){
			this.List = list;
//			list.add(null);
			this.mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		
		@Override
		public int getCount() {
			if (list.size()<1) {
				return 0;
			}else{
				return list.size();
			}
			
		}
		
		public void setSeclection(int position) {
			clickTemp = position;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
//			position = clickTemp
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			try {
				
			final ViewHolder holder;
			if (convertView == null) {
				holder =  new ViewHolder();
				convertView = mInflater.inflate(R.layout.jys_leibie_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.text = (TextView) convertView.findViewById(R.id.tv);
//				holder.radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
	        System.out.println("值是多少========="+position);
	        
	        
			if (position == 0) {
				holder.text.setText("推荐");
				holder.img.setImageResource(R.drawable.tuijian);
//				image.setImageDrawable(getResources().getDrawable(R.drawable.yourimage);
			}
			
			if (position > 0) {
			holder.text.setText(list.get(position).title);
	        mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).icon_url);
			}
			
//			holder.text.setText(list.get(position).title);
//	        ImageLoader imageLoader=ImageLoader.getInstance();
//	        imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+list.get(position).icon_url,holder.img);
//	        System.out.println("值是多少2=========="+clickTemp);
			
			if (clickTemp == position) {
//				convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang 
				holder.text.setTextColor(Color.RED);
			} else {
//				convertView.setBackgroundColor(Color.TRANSPARENT);
//				convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang 
				holder.text.setTextColor(Color.GRAY);
		    }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return convertView;
		}


		class ViewHolder{
			ImageView img;
			TextView text;
			RadioButton radioButton;
		}
	}

}
