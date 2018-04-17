package com.hengyushop.demo.wec;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.MyWareFourAdapter1;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.GoodsMyGridViewAdaper;
import com.hengyushop.airplane.adapter.MyGridAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.LieBiaoActivity;
import com.hengyushop.demo.home.SouSuoSpActivity;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.WareData;
import com.hengyushop.entity.WareDatall;
import com.hengyushop.entity.WareInformationData;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class NewWareMallActivity extends BaseActivity implements
		OnClickListener {

	private String strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
	private String strUrl_ll = RealmName.REALM_NAME_LL
			+ "/get_category_child_list";
	private WareDao waredao;
	private WareDao waredaoll;
	private WareData ware;
	private ArrayList<WareData> list;
	private ArrayList<WareDatall> listll;
	// private ArrayList<WareDatall> listll = new ArrayList<WareDatall>();
	// private List<WareDatall> listll;
	private ArrayList<String> imgList = new ArrayList<String>();
	WareDatall dm;
	private ArrayList<WareInformationData> datas;
	private List<WareData> allid;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;
	private ListView new_list;
	private PullToRefreshView refresh;
	private ImageView new_image;
	private Button channel;
	private int INDX = -1;
	private int quanbu;
	private GridView gridView, myGridView;
	private MyGridView gridView_list;
	RadioButton btn;
	private ArrayList<SpListData> lists;
	private boolean flag = false;
	private int nobiaoti;
	private LinearLayout ll_xuanzhe;
	// SpListData spList;
	RadioButton[] btns;
	private RadioGroup radio_group;
	private MyWareFourAdapter1 adapter;
	private MySpListAdapter myadapter;
	private MyGridAdapter arrayadapter;
	private SharedUtils sharedUtils;
	private List<EnterpriseData> list_lb = null;
	private MyAdapter lbadapter;
	GoodsMyGridViewAdaper jdhadapter;
	int biaoti;
	int itemWidth;
	int len;
	View vi_xian;
	String quanbu_id, user_name;
	EditText tv1;
	int num = 0;
	private static SharedPreferences spPreferences;
	private boolean strue = false;
	private boolean pailie = false;
	ImageView img_shared;
	public String channel_name;

	// private int VIEW_NUM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_ware);
		// �ڴ˵������淽�������ܲ����߳��е��쳣
		// Thread.setDefaultUncaughtExceptionHandler(this);
		radio_group = (RadioGroup) findViewById(R.id.radio_group);
		new_list = (ListView) findViewById(R.id.new_list);
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		// new_image = (ImageView) findViewById(R.id.new_image);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		vi_xian = (View) findViewById(R.id.vi_xian);
		// channel = (Button) findViewById(R.id.channel);
		sharedUtils = new SharedUtils(getApplicationContext(), "shouyi");
		// gridView = (GridView) findViewById(R.id.gridView);
		myGridView = (MyGridView) findViewById(R.id.mGv);
		// gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		datas = new ArrayList<WareInformationData>();
		try {

			LinearLayout ll_sousuo = (LinearLayout) findViewById(R.id.ll_sousuo);
			ll_sousuo
					.setBackgroundColor(getResources().getColor(R.color.white));
			ImageView iv_sousuo = (ImageView) findViewById(R.id.iv_sousuo);
			tv1 = (EditText) findViewById(R.id.tv1);
			iv_sousuo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(NewWareMallActivity.this,
							SouSuoSpActivity.class);
					String strwhere_zhi = tv1.getText().toString().trim();
					intent.putExtra("strwhere_zhi", strwhere_zhi);
					startActivity(intent);
				}
			});

			tv1.setOnEditorActionListener(new TextView.OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView arg0, int arg1,
						KeyEvent arg2) {
					// TODO Auto-generated method stub
					if (arg1 == EditorInfo.IME_ACTION_SEARCH) {

						// Toast.makeText(NewWare.this,"�Ǻ�",Toast.LENGTH_SHORT).show();
						// search pressed and perform your functionality.
						Intent intent = new Intent(NewWareMallActivity.this,
								SouSuoSpActivity.class);
						String strwhere_zhi = tv1.getText().toString().trim();
						intent.putExtra("strwhere_zhi", strwhere_zhi);
						startActivity(intent);
					}
					return false;
				}

			});
			try {

				img_shared = (ImageView) findViewById(R.id.img_shared);
				img_shared.setBackgroundResource(R.drawable.liebiao_pl);
				gridView_list = (MyGridView) findViewById(R.id.gridView_list);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			img_shared.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// SharedPreferences spPreferences =
					// getSharedPreferences("longuserset", MODE_PRIVATE);
					// user_name = spPreferences.getString("user", "");
					// if (user_name.equals("")) {
					// Intent intentll = new
					// Intent(NewWare.this,UserLoginActivity.class);
					// startActivity(intentll);
					// } else {
					// Intent intentll = new
					// Intent(NewWare.this,FenXiangActivity.class);
					// startActivity(intentll);
					// }
					try {
						// gridView_list = (MyGridView)
						// findViewById(R.id.gridView_list);
						// gridView_list.setVisibility(View.VISIBLE);
						if (pailie == false) {
							try {
								pailie = true;
								// VIEW_NUM = 100;
								img_shared
										.setBackgroundResource(R.drawable.juzheng_pl);
								gridView_list.setVisibility(View.VISIBLE);
								refresh.setVisibility(View.GONE);
								jdhadapter = new GoodsMyGridViewAdaper(lists,
										getApplicationContext());
								gridView_list.setAdapter(jdhadapter);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else {
							try {
								pailie = false;
								// VIEW_NUM = 10;
								img_shared
										.setBackgroundResource(R.drawable.liebiao_pl);
								gridView_list.setVisibility(View.GONE);
								refresh.setVisibility(View.VISIBLE);
								myadapter = new MySpListAdapter(lists,
										NewWareMallActivity.this, imageLoader);
								new_list.setAdapter(myadapter);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
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
		lists = new ArrayList<SpListData>();

		myadapter = new MySpListAdapter(lists, NewWareMallActivity.this,
				imageLoader);
		new_list.setAdapter(myadapter);

		jdhadapter = new GoodsMyGridViewAdaper(lists, getApplicationContext());
		gridView_list.setAdapter(jdhadapter);

		System.out.println("=======����ֵ111==" + nobiaoti);

		loadWeather();
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(NewWareMallActivity.this);
		progress.CreateProgress();

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	// public void uncaughtException(Thread arg0, Throwable arg1) {
	// // TODO Auto-generated method stub
	// //�ڴ˴����쳣�� arg1��Ϊ���񵽵��쳣
	// Log.i("AAA", "uncaughtException   " + arg1);
	// }
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loadWeather();
		if (LieBiaoActivity.title_id != null) {
			INDX = Integer.parseInt(LieBiaoActivity.title_id);
			load_list(INDX, true);
		}

	}

    @Override  
    protected void onDestroy() {  
        super.onDestroy(); 
    	if (MySpListAdapter.type == true) {
    		MySpListAdapter.query.clear();
    		MySpListAdapter.query.recycle(new_list);
    		MySpListAdapter.type = false;
		}
    	if (GoodsMyGridViewAdaper.type == true) {
    		GoodsMyGridViewAdaper.mAq.clear();
    		GoodsMyGridViewAdaper.mAq.recycle(myGridView);
    		GoodsMyGridViewAdaper.type = false;
		}
    	
    	if (lists.size() > 0) {
    		lists.clear();
        	lists = null;
        	list_lb = null;
        	data1 = null;
    		data2 = null;
		}
    }
    
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				myadapter.putData(lists);
				progress.CloseProgress();
				 if (lists.size() > 0) {
					 MySpListAdapter.query.clear();
				}
//				myadapter.notifyDataSetChanged();
				// setListViewHeightBasedOnChildren(new_list);
				System.out.println("=====================����1==" + lists.size());

				new_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String id = lists.get(arg2).getId();
						System.out.println("=====================" + id);
						Intent intent = new Intent(NewWareMallActivity.this,WareInformationActivity.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				});
				break;
			case 1:
				jdhadapter.putData(lists);
				progress.CloseProgress();
				 if (lists.size() > 0) {
					 GoodsMyGridViewAdaper.mAq.clear();
				}
//				jdhadapter.notifyDataSetChanged();
				System.out.println("=====================����2==" + lists.size());

				gridView_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String id = lists.get(arg2).getId();
						System.out.println("=====================" + id);
						Intent intent = new Intent(NewWareMallActivity.this,
								WareInformationActivity.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				});
				break;
			case 2:
				formatWeather((String) msg.obj);
				break;
			case 3:
				try {

					lbadapter = new MyAdapter(getApplicationContext(), list_lb);
					myGridView.setAdapter(lbadapter);

					INDX = list_lb.get(0).id;
					quanbu = list_lb.get(0).id;

					System.out.println("=====1�ڶ���de����====================="
							+ INDX);
					AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_category_child_list?"
							+ "channel_name=mall&parent_id=" + INDX + "",
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {
									// TODO Auto-generated method stub
									super.onSuccess(arg0, arg1);
									formatWeatherll(arg1);

								}
							}, null);

					myGridView
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									try {
										INDX = list_lb.get(arg2).id;
										quanbu = list_lb.get(arg2).id;
										System.out
												.println("=====myGridView���0�ڶ���de����====================="
														+ list_lb.get(0).id);
										System.out
												.println("=====myGridView���1�ڶ���de����====================="
														+ list_lb.get(1).id);
										System.out
												.println("=====myGridView���2�ڶ���de����====================="
														+ INDX);

										lbadapter.setSeclection(arg2);
										lbadapter.notifyDataSetChanged();

										spPreferences = getSharedPreferences(
												"longuserset", MODE_PRIVATE);
										int id = spPreferences.getInt("id", 0);
										System.out
												.println("strue===================="
														+ strue);
										if (arg2 != id) {
											// ��һ�ε�����ѡ���һ�������˵�
											if (strue = false) {
												String parent_id = String
														.valueOf(list_lb
																.get(arg2).id);
												String quanbu_id = String
														.valueOf(quanbu);
												Intent intent = new Intent(
														NewWareMallActivity.this,
														LieBiaoActivity.class);
												intent.putExtra("id", parent_id);
												intent.putExtra("quanbu_id",
														quanbu_id);
												startActivity(intent);
												strue = true;
											} else {
												num = 0;
												System.out
														.println("λ��Ϊ0===================="
																+ INDX);
												AsyncHttp
														.get(RealmName.REALM_NAME_LL
																+ "/get_category_child_list?"
																+ "channel_name=mall&parent_id="
																+ INDX + "",
																new AsyncHttpResponseHandler() {
																	@Override
																	public void onSuccess(
																			int arg0,
																			String arg1) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub
																		super.onSuccess(
																				arg0,
																				arg1);
																		quanbu_id = arg1;
																		formatWeatherll(arg1);
																	}
																}, null);
											}

										} else {
											num = 1;
											System.out
													.println("λ��Ϊ1====================");
										}
										int geshu = list_lb.get(arg2).id;
										if (geshu != 0) {

											if (num == 1) {
												num = 0;
												// MyGridAdapter.clickTemp = 0;
												String parent_id = String
														.valueOf(list_lb
																.get(arg2).id);
												String quanbu_id = String
														.valueOf(quanbu);
												Intent intent = new Intent(
														NewWareMallActivity.this,
														LieBiaoActivity.class);
												intent.putExtra("id", parent_id);
												intent.putExtra("quanbu_id",
														quanbu_id);
												startActivity(intent);

											} else {
												num = 1;
												Editor editor = spPreferences
														.edit();
												editor.putInt("id", arg2);
												editor.commit();

											}
										} else {
											// num = 1;
											Toast.makeText(
													NewWareMallActivity.this,
													"����Ŀ¼", 200).show();
										}

									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 20:
				adapter.putData(datas);
				new_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(NewWareMallActivity.this,
								WareInformationActivity.class);
						intent.putExtra("id", datas.get(arg2).id);
						startActivity(intent);
					}
				});

				break;
			default:
				break;
			}

		};
	};

	private void loadWeather() {
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_category_child_list?channel_name=mall&parent_id=0",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						formatWeather(arg1);
					}
				}, null);
	}

	private int RUN_METHOD = -1;
	EnterpriseData data;
	private void formatWeather(String result) {
		try {
			System.out.println("=======�б�����==" + result);
			list_lb = new ArrayList<EnterpriseData>();
			JSONObject object = new JSONObject(result);
			JSONArray jsonArray = object.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				data = new EnterpriseData();
				data.id = obj.getInt("id");
				data.title = obj.getString("title");
				list_lb.add(data);
			}
			progress.CloseProgress();
			inter();
			vi_xian.setVisibility(View.VISIBLE);
			System.out.println("=======list_lb1==" + list_lb.get(0).id);
			System.out.println("=======list_lb2==" + list_lb.get(1).id);
			handler.sendEmptyMessage(3);
			// System.out.println("=======22==");
			// handler.sendEmptyMessage(100);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * �ڶ����˵�
	 */
	private ArrayList data1, data2;

	private void formatWeatherll(String result) {
		data1 = new ArrayList();
		data2 = new ArrayList();
		listll = new ArrayList<WareDatall>();
		try {
			System.out.println("=====�ڶ�������=====================" + result);
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			if (status.equals("y")) {
				JSONArray jsonArray = object.getJSONArray("data");

				listll.add(0, null);
				data1.add("001");
				data2.add("ȫ��");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					dm = new WareDatall();
					dm.setId(obj.getString("id"));
					dm.setTitle(obj.getString("title"));
					String title = obj.getString("title");
					String id = obj.getString("id");
					// INDX = Integer.parseInt(id);
					listll.add(dm);
					data1.add(id);
					data2.add(title);
				}

				System.out.println("=====data2====================="
						+ data2.size());
				// gridView.setVisibility(View.VISIBLE);
				MyGridAdapter.clickTemp = 0;
				// arrayadapter = new
				// MyGridAdapter(data1,data2,getApplicationContext());
				// gridView.setAdapter(arrayadapter);

				// INDX = Integer.parseInt((String) data1.get(1));
				System.out.println("=====INDX3=========yes============" + INDX);
				load_list(INDX, true);

				// gridView.setOnItemClickListener(new OnItemClickListener() {
				// @Override
				// public void onItemClick(AdapterView<?> arg0, View arg1, int
				// arg2, long arg3) {
				// // flag = false;
				// try{
				// String title_id = (String) data1.get(arg2);
				// INDX = Integer.parseInt(title_id);
				// System.out.println("=====�ڶ���title_id====================="+title_id);
				// //
				// System.out.println("=====quanbu====================="+quanbu);
				// if (title_id.equals("001")) {
				// load_list(quanbu, true);
				// } else {
				// load_list(INDX, true);
				// }
				//
				// // String id = listll.get(arg2).getId();
				// // INDX = Integer.parseInt(id);
				// //
				// System.out.println("=====�ڶ�������1====================="+INDX);
				// // load_list(INDX, true);
				// System.out.println("=====arg2===================="+arg2);
				// arrayadapter.setSeclection(arg2);
				// arrayadapter.notifyDataSetChanged();
				//
				// } catch (Exception e) {
				// // TODO: handle exception
				// }
				// }
				// });

			} else {
				// gridView.setVisibility(View.GONE);
				// INDX = Integer.parseInt((String) data1.get(1));
				System.out.println("=====INDX==========NO===========" + INDX);
				load_list(INDX, true);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
						System.out.println("=======4==" + INDX);
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

	/**
	 * 1�б����ݽ���
	 */

	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	JSONObject object;
	SpListData spList;
	private void load_list(int INDX, boolean flag) {
		 progress.CreateProgress();
		RUN_METHOD = 1;
		if (flag) {
			CURRENT_NUM = 1;
			lists = new ArrayList<SpListData>();
		}
		System.out.println("=====================002--" + INDX);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_article_page_size_list?channel_name=mall&category_id="
				+ INDX + "" + "&page_size=" + VIEW_NUM + "&page_index="
				+ CURRENT_NUM + "&strwhere=&orderby=",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						// System.out.println("=====================����ֵ"+arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								// if (jsonArray.length() > 0) {
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									object = jsonArray.getJSONObject(i);
									spList = new SpListData();
									spList.id = object.getString("id");
									spList.img_url = object
											.getString("img_url");
									spList.title = object.getString("title");
									spList.market_price = object
											.getString("market_price");
									spList.sell_price = object
											.getString("sell_price");
									spList.cashing_packet = object
											.getString("cashing_packet");
									// JSONArray jaArray =
									// object.getJSONArray("albums");
									// for (int j = 0; j < jaArray.length();
									// j++) {
									// JSONObject jact =
									// jaArray.getJSONObject(j);
									// }
									lists.add(spList);
								}
								object = null;
								spList = null;
							} else {
								progress.CloseProgress();
								Toast.makeText(NewWareMallActivity.this,
										"û����Ʒ��", 200).show();
							}
							if (len != 0) {
								CURRENT_NUM = CURRENT_NUM + 1;
							}
							LieBiaoActivity.title_id = null;
							if (pailie == false) {
								handler.sendEmptyMessage(0);
							} else {
								handler.sendEmptyMessage(1);
							}
							progress.CloseProgress();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	// ���򻬶�������
	public class MyAdapter extends BaseAdapter {

		private Context mContext;
		private List<EnterpriseData> List_lb;
		private LayoutInflater mInflater;
		private int clickTemp = 0;

		public MyAdapter(Context context, List<EnterpriseData> list) {
			this.List_lb = list;
			this.mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (List_lb.size() < 1) {

				return 0;
			} else {

				return List_lb.size();
			}
		}

		public void setSeclection(int position) {
			clickTemp = position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return List_lb.get(position);
		}

		// @Override
		// public Object getItem(int position) {
		// return list.getItem(position);
		// }

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			try {

				if (convertView == null) {
					holder = new ViewHolder();
					convertView = mInflater.inflate(R.layout.leibie_item, null);
					holder.img = (ImageView) convertView
							.findViewById(R.id.iv_img);
					holder.text = (TextView) convertView.findViewById(R.id.tv);
					holder.v_xiaxian = (View) convertView
							.findViewById(R.id.v_xiaxian);
					holder.v_xiaxian2 = (View) convertView
							.findViewById(R.id.v_xiaxian2);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				System.out.println("=====λ��=====================" + position);
				// holder.img.setImageResource(dataList.get(position));
				// holder.text.setText("��" + position + "��");
				holder.text.setText(List_lb.get(position).title);
				biaoti = list_lb.get(position).title.length();

				if (clickTemp == position) {
					// convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang
					holder.text.setTextColor(Color.RED);
					holder.v_xiaxian.setVisibility(View.VISIBLE);
					// holder.v_xiaxian2.setVisibility(View.INVISIBLE);
					holder.img.setBackgroundResource(R.drawable.xiabiao);

				} else {
					// convertView.setBackgroundColor(Color.TRANSPARENT);
					// convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang
					holder.text.setTextColor(Color.GRAY);
					holder.v_xiaxian.setVisibility(View.INVISIBLE);
					// holder.v_xiaxian2.setVisibility(View.VISIBLE);
					// holder.v_xiaxian.setBackgroundResource(Color.GRAY);
					holder.img.setBackgroundResource(R.drawable.xiabiao_huise);
					// iv_baby_collection.setImageResource(R.drawable.second_2_collection);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return convertView;

		}

		class ViewHolder {
			ImageView img;
			TextView text;
			View v_xiaxian;
			View v_xiaxian2;
			RadioButton radioButton;
		}
	}

	LayoutInflater mLayoutInflater;
	View popView;
	PopupWindow mPopupWindow;

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.detail_dianping, null);
		mPopupWindow = new PopupWindow(popView,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//��������background������ʧ
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				android.R.color.white));
		mPopupWindow.setOutsideTouchable(true);
		// �Զ��嶯��
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// ʹ��ϵͳ����
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);

		// mLayoutInflater = (LayoutInflater)
		// getSystemService(LAYOUT_INFLATER_SERVICE);
		// View popView =
		// mLayoutInflater.inflate(R.layout.market_information_sep_pop,null);
		// mPopupWindow = new PopupWindow(popView,
		// LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		// // mPopupWindow.setBackgroundDrawable(new
		// // BitmapDrawable());//��������background������ʧ
		// mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.ban_louming));
		// // �Զ��嶯��
		// // mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// // ʹ��ϵͳ����
		// mPopupWindow.setAnimationStyle(R.style.delete_pop_style);
		// mPopupWindow.update();
		// mPopupWindow.setTouchable(true);
		// mPopupWindow.setOutsideTouchable(true);
		// mPopupWindow.setFocusable(true);

	}

	private void showPopupWindow(View view) {

		if (!mPopupWindow.isShowing()) {
			try {
				// mPopupWindow.showAsDropDown(view,0,0);
				// ��һ������ָ��PopupWindow��ê��view�����������ĸ�view�ϡ�
				// �ڶ�������ָ����ʼ��Ϊparent�����½ǣ�����������������parent�����½�Ϊԭ�㣬�����ϸ�ƫ��10���ء�
				int[] location = new int[2];
				view.getLocationOnScreen(location);
				mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	// ���ú�����ʾ�ĸ���
	private void inter() {
		try {

			int size = list_lb.size();// �����ܳ���

			// �����Ļ���
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int windowWidth = metrics.widthPixels;

			if (biaoti > 4) {
				itemWidth = windowWidth / 2;
			} else {
				itemWidth = windowWidth / 3;
			}

			// �����Ļ���Ҳ��������д
			// int itemWidth = getWindowManager().getDefaultDisplay().getWidth()
			// / 5;//��Ļ��ʾĬ������

			int gridViewWidth = (int) (size * itemWidth);// linearLayout���ܿ��
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
			myGridView.setLayoutParams(params);// ����GridView���ֲ���
			myGridView.setNumColumns(size);// ��̬����GridView����

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// private int CURRENT_NUM = 0;
	// private final int VIEW_NUM = 10;
	private void load(int id, final boolean flag) {
		RUN_METHOD = 1;
		if (flag) {
			CURRENT_NUM = 0;
			datas = new ArrayList<WareInformationData>();
		}
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_article_page_size_list?channel_name=mall&category_id="
				+ INDX + "" + "&page_size=" + VIEW_NUM + "&page_index="
				+ CURRENT_NUM + "&strwhere=&orderby=",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						parse2(arg1);
					}
				}, null);
	}

	private void parse2(String arg1) {
		try {
			JSONObject jsonObject = new JSONObject(arg1);
			String status = jsonObject.getString("status");
			String info = jsonObject.getString("info");
			if (status.equals("y")) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				len = jsonArray.length();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					SpListData spList = new SpListData();
					spList.id = object.getString("id");
					spList.img_url = object.getString("img_url");
					spList.title = object.getString("title");
					spList.market_price = object.getString("market_price");
					spList.sell_price = object.getString("sell_price");
					lists.add(spList);
				}

				System.out.println("=====================�������б�");
			} else {
				progress.CloseProgress();
				Toast.makeText(NewWareMallActivity.this, info, 200).show();
			}
			progress.CloseProgress();
			handler.sendEmptyMessage(0);
			if (len != 0) {
				// CURRENT_NUM =CURRENT_NUM+1;
				CURRENT_NUM = CURRENT_NUM + VIEW_NUM;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// ��ȡListView��Ӧ��Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			// ��������View �Ŀ��
			listItem.measure(0, 0);
			// ͳ������������ܸ߶�
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
		listView.setLayoutParams(params);
	}
}
