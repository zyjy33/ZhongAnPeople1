package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.pub.JegGoodsListAdapter;
import com.android.hengyu.pub.MyAdapter;
import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.SpListDataAdapter;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.droid.Activity01;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.WareData;
import com.hengyushop.entity.shangpingListData;
import com.lglottery.www.common.Config;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

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
			tv_city.setText("δ��λ");
		} else {
			tv_city.setText(city + "��");
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
		
		       // �л�����
		       main_item0.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(JuYouFangllActivity.this, Activity01.class);
						// String strwhere_zhi = tv1.getText().toString().trim();
						// intent.putExtra("strwhere_zhi", strwhere_zhi);
						startActivity(intent);
					}
				});
				// ���ų���
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
				System.out.println("========���˼�ƪ=============����"+list_ll.size());
				System.out.println("=====================����"+lists.size());
				
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
				System.out.println("�����Ƕ���===================="+list.size());
				
				adapter = new MyAdapter(getApplicationContext(), list);
				myGridView.setAdapter(adapter);
				
//				myGridView.setOnItemClickListener(new OnItemClickListener() {
//
//		            @Override
//		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
////		            	 INDX =  arg2;
//		            	
//		            	 quanbu_id = list.get(arg2).id;
//		            	 System.out.println("=====�ڶ�������1====================="+quanbu_id);
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
//					System.out.println("1ȫ��ID=========="+quanbu_id);
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
	//�̼��б�
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
//			System.out.println("����б�=========="+st);
			list = new ArrayList<EnterpriseData>();
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			System.out.println("jsonArray"+jsonArray.length());
			int len = jsonArray.length();
//			System.out.println("ֵ1=========="+len);
			int lenth = len+1;
//			System.out.println("ֵ2=========="+lenth);
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
//	            	 System.out.println("=====�ڶ�������1====================="+INDX);
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
	 * �ڶ����б����ݽ���
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(final int INDX,boolean flag) {
		RUN_METHOD = 1;
		lists = new ArrayList<GoodsListData>();
		if(flag){
			//��������������
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
								System.out.println("�̼��б�=====================����ֵ1"+arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject.getString("status");
									System.out.println("�̼��б�deֵ====================="+status);
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
										System.out.println("����ֵ2====================="+user_id);
										loadCatell(user_id,true);
										
									}
//									int id = lists.get(arg0).user_id;
//									System.out.println("����ֵ��id�̳�====================="+list_id);
//									int gd_id = 1;
//									loadCatell(gd_id);
									if(len!=0){
										CURRENT_NUM =CURRENT_NUM+VIEW_NUM;
									}
//									 System.out.println("1�̼��б����������==========");
//									handler.sendEmptyMessage(0);
									}else {
                                    	handler.sendEmptyMessage(0);
										 System.out.println("2�̼��б�û�е���������==========");
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, getApplicationContext());
	}
	
	
	
	//��Ʒ�б�
	public void loadCatell(String user_id,boolean flag){
		try {
			System.out.println("����ֵ3====================="+user_id);
//			useridString = user_id;
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
                "channel_name=goods&top=3&strwhere=user_id="+user_id+"", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
//					System.out.println("����ֵ32====================="+useridString);
					System.out.println("��Ʒ�б�2=========="+arg1);
						list_ll = new ArrayList<shangpingListData>();
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						String datall = jsonObject.getString("data");
//						System.out.println("�̼��б�deֵ====================="+datall);
//						if (status.equals("y")) {
						if (datall.equals("[]")) {
							 System.out.println("2��ֵ==========");
							 
//						String datall = jsonObject.getString("data");
//						System.out.println("��Ʒ�б�deֵ����====================="+datall.length());
//						String zhou = "0";
//						String de = zhou;
//						System.out.println("��Ʒ�б�222====================="+de);
//						int hehe = 1;
//						if (hehe > 2) {
//						if (useridString == 75) {
//							handler.sendEmptyMessage(0);
//						}
//							 handler.sendEmptyMessage(0);
							 System.out.println("1û��ֵ����������==========");
					  }else {
//						  System.out.println("2��ֵ==========");
						  
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
								System.out.println("��������id========="+id);
								list_ll.add(data);
							}
							handler.sendEmptyMessage(0);
							System.out.println("2��ֵ����������==========");
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
	 * ��һ���б����ݽ���
	 */
//	private void load_list2(final int INDX,boolean flag) {
//		lists = new ArrayList<GoodsListData>();
//		if(flag){
//			//��������������
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
//								System.out.println("�̼��б�=====================����ֵ2="+arg1);
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
	//��Ʒ�б�
//	private void loadCatell(int user_id){
//		try {
////			System.out.println("����ֵ3====================="+user_id);
//		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_top_list?" +
//                "channel_name=goods&top=3&strwhere=user_id="+user_id+"", new AsyncHttpResponseHandler(){
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0, arg1);
//				try {
//					System.out.println("����Ʒ�б�=========="+arg1);
//					if (quanbu_id == 0) {
////						System.out.println("1ȫ��ID=========="+quanbu_id);
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
//							System.out.println("2ȫ��ID=========="+quanbu_id);
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
//							System.out.println("��������id========="+id);
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
	 * �����б�ˢ�¼���
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
	 * �����б�ˢ�¼���
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
		
		int size = list.size();//�����ܳ���

		//�����Ļ���
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int windowWidth = metrics.widthPixels;
		int itemWidth = windowWidth/4;

		//�����Ļ���Ҳ��������д
		//int itemWidth = getWindowManager().getDefaultDisplay().getWidth() / 5;//��Ļ��ʾĬ������

		int gridViewWidth = (int)(size * itemWidth);//linearLayout���ܿ��
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth,LinearLayout.LayoutParams.MATCH_PARENT);
		myGridView.setLayoutParams(params);//����GridView���ֲ���
		myGridView.setNumColumns(size);//��̬����GridView����
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
////			holder.text.setText("��" + position + "��");
////			clickTempll = position;
//			 
//			 try {
//				
//			 System.out.println("����1======================"+position);
////			if (position == 0) {
//////				RadioButton btn = (RadioButton) RadioGroup.inflate(getApplicationContext(), R.layout.common_btn, null);
//////				btn.setText("ȫ��");
////				holder.text.setText("ȫ��");
////				holder.img.setImageResource(R.drawable.quanbu);
//////				image.setImageDrawable(getResources().getDrawable(R.drawable.yourimage);
////			}
//			
////			if (position > 0) {
//			holder.text.setText(list.get(position).title);
//	        ImageLoader imageLoader=ImageLoader.getInstance();
//	        imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+list.get(position).icon_url,holder.img);
////	        System.out.println("ֵ�Ƕ���2=========="+clickTemp);
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
