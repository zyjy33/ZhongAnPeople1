package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.JuyouFanglistAdapter;
import com.android.hengyu.pub.MyAdapter;
import com.android.hengyu.pub.MyAdapter_new;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.droid.Activity01;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListBean;
import com.hengyushop.entity.GoodsListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class JuYouFangActivity extends BaseActivity{
	private List<EnterpriseData> list = null;
//	private ArrayList<shangpingListData> list_ll = null;
	private MyGridView myGridView;
	private MyAdapter_new adapter;
	private int INDX = -1;
	private int quanbu_id = 0;
	private ImageView iv_ditu;
	private JuyouFanglistAdapter myadapter;
	private ListView new_list;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	LinearLayout main_item0,main_item1,main_item2;
	int useridString;
	private int id = 0;
	int len;
	EnterpriseData data_ll;
	GoodsListData data;
	GoodsListBean bean;
	private TextView tv_city;
	String city;
	private ArrayList<GoodsListData> lists;
	 private static final String TAG = "ActivityDemo";  
	//当第一次调用一个Activity就会执行onCreate方法
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juyoufang_home);
		Log.e(TAG, "start onCreate~~~");  
		 //在此调用下面方法，才能捕获到线程中的异常
//        Thread.setDefaultUncaughtExceptionHandler(this);
		progress = new DialogProgress(this);
		Initialize();
		lists = new ArrayList<GoodsListData>();
		myadapter = new JuyouFanglistAdapter(lists,JuYouFangActivity.this, imageLoader);
		new_list.setAdapter(myadapter);
		loadCate();
	}
	//当Activity可以得到用户焦点的时候就会调用onResume方法
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "start onResume~~~");  
		SharedPreferences spPreferences = getSharedPreferences("longuserset_city", MODE_PRIVATE);
		city = spPreferences.getString("city", "");
		System.out.println("city=================" + city);
		if (city.equals("")) {
			tv_city.setText("未定位");
		} else {
			tv_city.setText(city + "市");
		}
	}
	//当Activity处于可见状态的时候就会调用onStart方法
    @Override  
    protected void onStart() {  
        super.onStart();  
        Log.e(TAG, "start onStart~~~");  
    }  
    //当Activity没有被销毁的时候重新调用这个Activity就会调用onRestart方法
    @Override  
    protected void onRestart() {  
        super.onRestart();  
        Log.e(TAG, "start onRestart~~~");  
    }   
//    @Override  
//    protected void onResume() {  
//        super.onResume();  
//        Log.e(TAG, "start onResume~~~");  
//    }  
    
    //当Activity被遮挡住的时候就会调用onPause方法
    @Override  
    protected void onPause() {  
        super.onPause();  
        Log.e(TAG, "start onPause~~~");  
    }   
    //当Activity处于不可见状态的时候就会调用onStop方法
    @Override  
    protected void onStop() {  
        super.onStop();  
        Log.e(TAG, "start onStop~~~");  
    }  
      
    //当Activity被销毁时会调用onDestory方法
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        Log.e(TAG, "start onDestroy~~~");  
		BitmapDrawable bd1 = (BitmapDrawable)iv_ditu.getBackground();
		iv_ditu.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
		bd1.setCallback(null);
		bd1.getBitmap().recycle();
		if (list.size() > 0) {
			list.clear();
    		list = null;
		}
    } 
    
//	public void uncaughtException(Thread arg0, Throwable arg1) {
//		// TODO Auto-generated method stub
//		 //在此处理异常， arg1即为捕获到的异常
//        Log.i("AAA", "uncaughtException   " + arg1);
//	}
	
     /**
      * 结束指定的Activity
      */
//     public void finishActivity(Activity activity) {
//         if (activity != null) {
//             activityStack.remove(activity);
//             activity.finish();
//             activity = null;
//         }
//    }
	private void Initialize() {
		try {
			
		iv_ditu = (ImageView) findViewById(R.id.iv_ditu);
//		iv_ditu.setBackgroundResource(R.drawable.ditu);
//		Bitmap bitmap = BitmapFactory.decodeResource(JuYouFangActivity.this.getResources(),R.drawable.ditu);
//		iv_ditu.setImageBitmap(bitmap);
        //回收并且置为null
//        bitmap.recycle();
//        bitmap = null;
	      // 先判断是否已经回收
//        if(bitmap != null && !bitmap.isRecycled()){
//            // 回收并且置为null
//            bitmap.recycle();
//            bitmap = null;
//      }
		
		Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ditu);
		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
		iv_ditu.setBackgroundDrawable(bd);
		
		//销毁的时候使用
//		BitmapDrawable bd1 = (BitmapDrawable)iv_ditu.getBackground();
//		iv_ditu.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
//		bd1.setCallback(null);
//		bd1.getBitmap().recycle();
		
	 } catch (OutOfMemoryError e) {
         // 捕获OutOfMemoryError，避免直接崩溃
     }

		myGridView = (MyGridView) findViewById(R.id.mGv);
		new_list = (ListView) findViewById(R.id.new_list);
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		tv_city = (TextView) findViewById(R.id.tv_city);
		main_item0 = (LinearLayout) findViewById(R.id.main_item0);
		main_item1 = (LinearLayout) findViewById(R.id.main_item1);
		
		       // 切换城市
		       main_item0.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(JuYouFangActivity.this, Activity01.class);
						// String strwhere_zhi = tv1.getText().toString().trim();
						// intent.putExtra("strwhere_zhi", strwhere_zhi);
						startActivity(intent);
					}
				});
				// 热门城市
		       main_item1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(JuYouFangActivity.this, Activity01.class);
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
	
	
	//商家列表
		private void loadCate(){
			progress.CreateProgress();
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_trade_list?" +
	                "channel_name=trade&parent_id=273", new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					parse(arg1);
				}
			}, null);
	    }
		
		public void parse(String st) {
			try {
				System.out.println("类别列表=========="+st);
				list = new ArrayList<EnterpriseData>();
				JSONObject jsonObject = new JSONObject(st);
				String status = jsonObject.getString("status");
				if (status.equals("y")) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				System.out.println("jsonArray"+jsonArray.length());
				int len = jsonArray.length();
				list.add(0, null);
				for (int i = 0; i < len; i++) {
					data_ll = new EnterpriseData();
					JSONObject object = jsonArray.getJSONObject(i);
					data_ll.id = object.getInt("id");
//					data.setId(object.getInt("id"));
					data_ll.title = object.getString("title");
					data_ll.icon_url = object.getString("icon_url");
					Log.v("data_ll", data_ll.id + "");
					list.add(data_ll);
				}
				inter();
				handler.sendEmptyMessage(1);
				data_ll = null;
				}else {
				}
				progress.CloseProgress();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				myadapter.putData(lists);
				System.out.println("=====================这里"+lists.size());
				
	            new_list.setOnItemClickListener(new OnItemClickListener() {
	                
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String id = lists.get(arg2).getId();
						System.out.println("====================="+id);
						Intent intent = new Intent(JuYouFangActivity.this,WareInformationActivity.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				});
	            
//				JegGoodsListAdapter myadapter = new JegGoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
//				new_list.setAdapter(myadapter);
//				myadapter = new GoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
//				new_list.setAdapter(myadapter);
//				SpListDataAdapter sAdapter = new SpListDataAdapter(list_ll, getApplicationContext(), imageLoader);
//				listview.setAdapter(sAdapter);
//				myadapter.notifyDataSetChanged();
				break;
			case 1:
				System.out.println("个数是多少===================="+list.size());
				adapter = new MyAdapter_new(getApplicationContext(),list);
				myGridView.setAdapter(adapter);
				

				if (list.size()>0) {
					try{
//					int id = list.get(1).getId();
//					int id = 273;
					load_list(id, true);
					
					 } catch (Exception e) {
							// TODO: handle exception
						 e.printStackTrace();
						}
				}
				
				myGridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		            	try {	 	
//		            		 INDX =  list.get(arg2).getId();
		            		 INDX =  arg2;
			            	 System.out.println("=====第二层de数据====================="+INDX);
			            	 load_list(INDX, true);
		            	 adapter.setSeclection(arg2);
		            	 adapter.notifyDataSetChanged();
		            } catch (Exception e) {
						// TODO: handle exception
					 e.printStackTrace();
					}
		            	 
		            }
		        });
				
				break;
			case 2:
			    break;
				

			default:
				break;
			}
		};
	};
	
	
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
		System.out.println("=====================001--"+INDX);
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_user_commpany?trade_id="+INDX+"" +
							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
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
										data.getList().add(bean);
									}
									lists.add(data);
									}
									}else {
										progress.CloseProgress();
										Toast.makeText(JuYouFangActivity.this, info, 200).show();
									}
									System.out.println("=====lists2====================="+lists.size());
									if(len!=0){
										CURRENT_NUM =CURRENT_NUM+1;
									}
									progress.CloseProgress();
									handler.sendEmptyMessage(0);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, null);
	}
	
	
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
						load_list(INDX, false);
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
		int itemWidth = windowWidth/5;

		//获得屏幕宽度也可以这样写
		//int itemWidth = getWindowManager().getDefaultDisplay().getWidth() / 5;//屏幕显示默认数量

		int gridViewWidth = (int)(size * itemWidth);//linearLayout的总宽度
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth,LinearLayout.LayoutParams.MATCH_PARENT);
		myGridView.setLayoutParams(params);//设置GridView布局参数
		myGridView.setNumColumns(size);//动态设置GridView列数
	}
	

}
