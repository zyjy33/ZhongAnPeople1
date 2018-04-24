package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GouWuCheAGoodsAdaper;
import com.android.hengyu.pub.HealthPavilionAdapter;
import com.android.hengyu.pub.JianKangMalllAdapter;
import com.android.hengyu.pub.MyGridllAdapter;
import com.android.hengyu.pub.WideChildAdapter;
import com.android.hengyu.pub.ZhongAnYlAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.ZaylListAdapter;
import com.hengyushop.airplane.adapter.zaylAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.demo.wec.MyScrollView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.GuigellBean;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.HomeActivity;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

/**
 *
 * 健康馆
 *
 * @author Administrator
 *
 */
public class HealthGunaActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private LinearLayout index_item0, index_item1,index_item2, ll_buju1;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket;
	private ArrayList<GuigeBean> list_jkg;
	private ArrayList<MyAssetsBean> list_lb;
	String user_name, user_id;
	int len;
	String fund_id = "0";
	private MyGridView gridView2;
	private ListView new_list;
	GuigeData md;
	GuigeBean mb;
	GuigellBean mbll;
	GuigeBean data_ll;
	//	MyAssetsBean data;
	private DialogProgress progress;
	ArrayList<GuigeBean> list_l;
	ArrayList<GuigeData> list_ll = new ArrayList<GuigeData>();
	private ArrayList<ZhongAnYlBean> list_llsc = null;
	private ArrayList<ZhongAnYlData> list_sc1 = null;
	//	private ArrayList<ZhongAnYlBean> list_sc2 = null;
	ZhongAnYlData data_sc1;
	ZhongAnYlBean bean_sc2;
	private GridView gridview;
	//	ZhongAnYlBean bean;
	MyScrollView myScrollView;
	private ListView listview_01;
	private ArrayList<ZhongAnYlData> list = null;
	ZhongAnYlData data;
	ZhongAnYlBean bean;
	ScrollView scrollview;
	MyGridllAdapter MyAdapter;
	JianKangMalllAdapter adapter;
	HealthPavilionAdapter adapter_2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_health_pavilion);
		progress = new DialogProgress(this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		Initialize();
//		load_gridview();
		loadzhonganyl();

	}

	public void onDestroy() {
		super.onDestroy();
		try {

			if (list.size() > 0) {
				list.clear();
				list = null;
			}

			if (list_jkg.size() > 0) {
				list_jkg.clear();
				list_jkg = null;
			}
			if (MyGridllAdapter.type == true) {
				MyGridllAdapter.aQuery.clear();
				MyGridllAdapter.type = false;
			}

			if (zaylAdapter.type == true) {
				zaylAdapter.aQuery.clear();
				zaylAdapter.type = false;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	};
	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
//			myScrollView = (MyScrollView) findViewById(R.id.scrollView);
			scrollview = (ScrollView) findViewById(R.id.ss_buju);
			gridView2=(MyGridView)findViewById(R.id.gridview2);
//			gridview = (GridView) findViewById(R.id.gridView);
			new_list = (ListView) findViewById(R.id.new_list);
			listview_01=(ListView) findViewById(R.id.listview_01);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			ll_buju1 = (LinearLayout) findViewById(R.id.ll_buju1);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			cursor3 = (ImageView) findViewById(R.id.cursor3);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.index_item0:
				cursor1.setVisibility(View.VISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				scrollview.setVisibility(View.GONE);
//			myScrollView.setVisibility(View.VISIBLE);
				listview_01.setVisibility(View.VISIBLE);
				break;
			case R.id.index_item1:
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.VISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				scrollview.setVisibility(View.VISIBLE);
//			myScrollView.setVisibility(View.GONE);
				listview_01.setVisibility(View.GONE);
				break;
			case R.id.index_item2:
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.VISIBLE);
				scrollview.setVisibility(View.GONE);
//			myScrollView.setVisibility(View.GONE);
				listview_01.setVisibility(View.GONE);
				Toast.makeText(HealthGunaActivity.this, "暂无日常调理", 200).show();
//			loadWeather();
//			load_gridview();
				break;
			default:
				break;
		}
	}


	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					MyAdapter = new MyGridllAdapter(list_jkg,getApplicationContext());
					gridView2.setAdapter(MyAdapter);
					if (list_jkg.size() > 0) {
						MyGridllAdapter.aQuery.clear();//清除内存
						list_jkg = null;
					}
					gridView2.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							Intent intent= new Intent(HealthGunaActivity.this,SymptomsJieShaoActivity.class);
							intent.putExtra("summary", list_jkg.get(arg2).summary);
							intent.putExtra("proposal", list_jkg.get(arg2).proposal);
							intent.putExtra("cause", list_jkg.get(arg2).cause);
							intent.putExtra("doctor", list_jkg.get(arg2).doctor);
							intent.putExtra("title", list_jkg.get(arg2).title);
							intent.putExtra("num", "2");
//				            GuigeData  bean=  list_ll.get(arg2);
//						    Bundle bundle = new Bundle();
//						    bundle.putSerializable("bean", bean);
//						    intent.putExtras(bundle);
							startActivity(intent);
						}
					});
					break;
//			case 1:
//				try{
//					System.out.println("list_llsc个数是多少===================="+list_llsc.size());
//					ZaylListAdapter MyAdapter2 = new ZaylListAdapter(list_llsc, getApplicationContext());
//					gridview.setAdapter(MyAdapter2);
//					setListViewHeightBasedOnChildrenll(gridview);
//					gridview.setOnItemClickListener(new OnItemClickListener() {
//
//						@Override
//						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//								long arg3) {
//
//							try {
//							String id = list_llsc.get(arg2).getId();
//									 System.out.println("=====id====================="+id);
//							Intent intent = new Intent(HealthGunaActivity.this, WareInformationActivity.class);
//							intent.putExtra("id", id);
//							startActivity(intent);
//
//							} catch (Exception e) {
//
//								e.printStackTrace();
//							}
//						}
//					});
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//				break;

				case 2:
					try {

						System.out.println("list_sc1个数是多少===================="+list.size());
						adapter = new JianKangMalllAdapter(list,getApplicationContext());
						listview_01.setAdapter(adapter);

						if (list.size() > 0) {
							JianKangMalllAdapter.aQuery.clear();//清除内存
							list = null;
						}
//				setListViewHeightBasedOnChildren(listview_01);
//				adapter.notifyDataSetChanged();

//				listview_01.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//
//						System.out.println("arg2====================="+arg2);
//						String id = list.get(arg2).getId();
//						System.out.println("====================="+id);
//						if(arg2 == 0){
//						Intent intent = new Intent(HealthGunaActivity.this,HongBaoZqListActivity.class);
//						intent.putExtra("category_id", id);
//						intent.putExtra("title", list.get(arg2).getTitle());
//						intent.putExtra("type_zhi", "3");
//						startActivity(intent);
//						}else{
//							//ZhongAnYlListActivity
//							Intent intent = new Intent(HealthGunaActivity.this,JianKangScListActivity.class);
//							intent.putExtra("category_id", id);
//							intent.putExtra("title", list.get(arg2).getTitle());
//							startActivity(intent);
//						}
//
//
//					}
//				});

					} catch (Exception e) {

						e.printStackTrace();
					}
					break;

				default:
					break;
			}
		};
	};
	/**
	 * 健康商城
	 */
	private void loadzhonganyl() {
		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_loop_list?channel_name=healthy&top=3"
				, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
//				System.out.println("=======列表数据================================"+arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								list = new ArrayList<ZhongAnYlData>();
								JSONArray jsonArray = object.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									try {
										JSONObject object1 = jsonArray.getJSONObject(i);
										data = new ZhongAnYlData();
										data.setId(object1.getString("id"));
										data.setTitle(object1.getString("title"));
										String zhou1= data.getTitle();
//							System.out.println("=====标题====================="+zhou1);
										String article = object1.getString("article");
										data.setList(new ArrayList<ZhongAnYlBean>());
										JSONArray ja = new JSONArray(article);

										for(int k = 0; k < ja.length(); k++) {
											bean = new ZhongAnYlBean();
											JSONObject obt = ja.getJSONObject(k);
											bean.setId(obt.getString("id"));
											bean.setTitle(obt.getString("title"));
											bean.setImg_url(obt.getString("img_url"));
											bean.setSell_price(obt.getString("sell_price"));
											bean.setMarket_price(obt.getString("market_price"));
											String zhou = bean.getTitle();
//						System.out.println("=====内容====================="+zhou);

											data.getList().add(bean);
										}
										list.add(data);
									} catch (Exception e) {

										e.printStackTrace();
									}

								}
//						System.out.println("=====内容list====================="+list.size());
								scrollview.setVisibility(View.GONE);
								listview_01.setVisibility(View.VISIBLE);

								handler.sendEmptyMessage(2);
								progress.CloseProgress();
								data = null;
								bean = null;
//						Toast.makeText(ZhongAnYlActivity.this, info, 200).show();
							}else {
								progress.CloseProgress();
								Toast.makeText(HealthGunaActivity.this, info, 200).show();
							}
							load_gridview();
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}

				}, null);
	}
//	private void loadzhonganyl() {
//		try {
//			list_sc1 = new ArrayList<ZhongAnYlData>();
//		progress.CreateProgress();
//		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_loop_list?channel_name=innovate&top=3"
//				, new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//
//				super.onSuccess(arg0, arg1);
//				System.out.println("=======列表数据================================"+arg1);
//				try {
//					JSONObject object = new JSONObject(arg1);
//					String status = object.getString("status");
//					String info = object.getString("info");
//					if (status.equals("y")) {
//						JSONArray jsonArray = object.getJSONArray("data");
//						for (int i = 0; i < jsonArray.length(); i++) {
//							try {
//							JSONObject object1 = jsonArray.getJSONObject(i);
//							data_sc1 = new ZhongAnYlData();
//							data_sc1.setId(object1.getString("id"));
//							data_sc1.setTitle(object1.getString("title"));
//							String zhou1= data_sc1.getTitle();
//							System.out.println("=====标题====================="+zhou1);
////							String article = object1.getString("article");
////							data_sc1.setList(new ArrayList<ZhongAnYlBean>());
////							JSONArray ja = new JSONArray(article);
//
////					for (int k = 0; k < ja.length(); k++) {
//////						try {
////						bean_sc2 = new ZhongAnYlBean();
////						JSONObject obt = ja.getJSONObject(k);
////						bean_sc2.setId(obt.getString("id"));
////						bean_sc2.setTitle(obt.getString("title"));
////						bean_sc2.setImg_url(obt.getString("img_url"));
////						bean_sc2.setSell_price(obt.getString("sell_price"));
////						bean_sc2.setMarket_price(obt.getString("market_price"));
////						String zhou = bean.getTitle();
////						System.out.println("=====内容====================="+zhou);
////
////						data_sc1.getList().add(bean_sc2);
//////						} catch (Exception e) {
//////
//////							e.printStackTrace();
//////						}
////					   }
//					list_sc1.add(data_sc1);
//
//					} catch (Exception e) {
//
//						e.printStackTrace();
//					}
//					}
//						System.out.println("=====内容list====================="+list.size());
//					progress.CloseProgress();
////					handler.sendEmptyMessage(2);
//
////					load_gridview();
////						Toast.makeText(ZhongAnYlActivity.this, info, 200).show();
//					}else {
//						progress.CloseProgress();
//						Toast.makeText(HealthGunaActivity.this, info, 200).show();
//					}
//				} catch (JSONException e) {
//
//					e.printStackTrace();
//				}
//			}
//
//		}, null);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//	}

	/**
	 * 列表数据解析
	 */

	private void load_gridview() {
		progress.CreateProgress();
		list_jkg = new ArrayList<GuigeBean>();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_test_solution_list?" +
						"top=8&where=is_hot=1",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("症状==================================" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray.getJSONObject(i);
									data_ll = new GuigeBean();
									data_ll.id = json.getString("id");
									data_ll.title = json.getString("title");
									data_ll.icon_url = json.getString("icon_url");
									data_ll.summary = json.getString("summary");//总结
									data_ll.proposal = json.getString("proposal");//生活建议
									data_ll.cause = json.getString("cause");//形成原因
									data_ll.doctor = json.getString("doctor");//何时就医
									list_jkg.add(data_ll);
								}
								Message msg = new Message();
								msg.what = 0;
								msg.obj = list_jkg;
								handler.sendMessage(msg);
								data_ll = null;
							} else {
								Toast.makeText(HealthGunaActivity.this, info, 200).show();
							}
							progress.CloseProgress();
//							loadzhengzhuang();
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				}, null);
	}


	/**
	 * 解析症状列表数据
	 */
	private void loadzhengzhuang() {
		list_l = new ArrayList<GuigeBean>();
		list_lb  = new ArrayList<MyAssetsBean>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_test_lesson_solustion_list?channel_name=test&parent_id=0",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("=====规格数据====================="+arg1);

						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jobt = object.getJSONArray("data");
								for (int i = 0; i < jobt.length(); i++) {
									JSONObject obj= jobt.getJSONObject(i);
									String child = obj.getString("child");
									System.out.println("=====1值====================="+child);
									if (child.length() > 0) {
//								JSONArray jaArray = obj.getJSONArray(child);
										JSONArray jaArray = new JSONArray(child);
										for (int j = 0; j < jaArray.length(); j++){
											JSONObject objc= jaArray.getJSONObject(j);
											md = new GuigeData();
											md.setTitle(objc.getString("title"));

//								JSONArray jay = objc.getJSONArray("solution");
//								if (jay.length() > 0) {
//								md.setList(new ArrayList<GuigeBean>());
//								for (int k = 0; k < jay.length(); k++) {
//						    	JSONObject obt= jay.getJSONObject(k);
//					    		mb = new GuigeBean();
//					    		mb.setTitle(obt.getString("title"));
//					    		mb.setIcon_url(obt.getString("icon_url"));
//					    		mb.summary = obt.getString("summary");
//					    		mb.proposal = obt.getString("proposal");//生活建议
//					    		mb.cause = obt.getString("cause");//形成原因
//					    		mb.doctor = obt.getString("doctor");//何时就医
////					    		list_l.add(mb);
//					    		md.getList().add(mb);
//					    		JSONArray ja = obt.getJSONArray("articles");
//					    		mb.setList(new ArrayList<GuigellBean>());
//								  for (int y = 0; y < ja.length(); y++) {
//						    	JSONObject joct= ja.getJSONObject(y);
////						    	data = new MyAssetsBean();
////						    	data.id = joct.getString("id");
////						    	data.title = joct.getString("title");
////						    	data.img_url = joct.getString("img_url");
////						    	list_lb.add(data);
//						    	mbll = new GuigellBean();
//						    	mbll.article_id = joct.getString("article_id");
//						    	mbll.title = joct.getString("title");
//						    	mbll.img_url = joct.getString("img_url");
//						    	mbll.item_id = joct.getString("item_id");
//						    	mb.getList().add(mbll);
//								  }
//								}
//								}

											list_ll.add(md);
										}
									}else {
										System.out.println("=====22=====================");
									}
								}
//							md = null;
//				    		mbll = null;
//						    adapter_2 = new HealthPavilionAdapter(list_ll,list_l,getApplicationContext(), handler);
//							new_list.setAdapter(adapter);
//							setListViewHeightBasedOnChildren(new_list);
//							if (list_ll.size() > 0) {
//								WideChildAdapter.aQuery.clear();//清除内存
//								list_ll = null;
//								list_l = null;
//							}
								progress.CloseProgress();
							} else {
								Toast.makeText(HealthGunaActivity.this, info, 200).show();
							}

						} catch (JSONException e) {

							e.printStackTrace();
						}

//						    adapter_2 = new HealthPavilionAdapter(list_ll,list_l,getApplicationContext(), handler);
//							new_list.setAdapter(adapter);
//							setListViewHeightBasedOnChildren(new_list);
//							if (list_ll.size() > 0) {
//								WideChildAdapter.aQuery.clear();//清除内存
//								list_ll = null;
//								list_l = null;
//							}
//							progress.CloseProgress();

//							adapter.notifyDataSetChanged();

//							loadWeather();
					}
				}, HealthGunaActivity.this);
	}

//	private void loadWeather() {
////		progress.CreateProgress();
//		list_llsc = new ArrayList<ZhongAnYlBean>();
//		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_page_size_list?channel_name=innovate&category_id="+0+"" +
//				"&page_size="+100+"&page_index="+1+"&strwhere=&orderby=",
//				new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//
//				super.onSuccess(arg0, arg1);
//				System.out.println("=======列表数据================================");
//				try {
//					JSONObject object = new JSONObject(arg1);
//					String status = object.getString("status");
//					String info = object.getString("info");
//					if (status.equals("y")) {
//						JSONArray jsonArray = object.getJSONArray("data");
//						int len = jsonArray.length();
//					for (int i = 0; i < len; i++) {
//						bean = new ZhongAnYlBean();
//						JSONObject obt = jsonArray.getJSONObject(i);
//						bean.setId(obt.getString("id"));
//						bean.setTitle(obt.getString("title"));
//						bean.setImg_url(obt.getString("img_url"));
//						bean.setSell_price(obt.getString("sell_price"));
//						bean.setMarket_price(obt.getString("market_price"));
//						String zhou = bean.getTitle();
//						System.out.println("=====内容====================="+zhou);
//						list_llsc.add(bean);
//					}
//					}else {
//						Toast.makeText(HealthGunaActivity.this, info, 200).show();
//					}
//					progress.CloseProgress();
//					handler.sendEmptyMessage(1);
//
//				} catch (JSONException e) {
//
//					e.printStackTrace();
//				}
//			}
//
//		}, null);
//	}

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

	public void setListViewHeightBasedOnChildrenll(GridView gridview2) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = gridview2.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridview2);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridview2.getLayoutParams();
		params.height = totalHeight+ (gridview2.getHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		gridview2.setLayoutParams(params);
	}
}
