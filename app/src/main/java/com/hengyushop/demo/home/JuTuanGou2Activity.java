package com.hengyushop.demo.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.pub.JuTuanGouAdapter;
import com.android.hengyu.pub.MyAdapter2;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.example.taobaohead.BeanVo;
import com.example.taobaohead.headview.ScrollTopViewll;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.zams.www.UserLoginActivity;
import com.zams.www.R;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class JuTuanGou2Activity extends BaseActivity implements OnClickListener {
	private List<JuTuanGouData> list = null;
	private List<JuTuanGouData> list_1;
	private List<JuTuanGouData> list_2;
	private List<JuTuanGouData> list_3;
	private List<JuTuanGouData> list_4 = null;
	private MyGridView myGridView;
	private MyAdapter2 adapter;
	private JuTuanGouAdapter Jutuangouadapter;
	// private int INDX = -1;
	private int INDX = 0;
	private GoodsListAdapter myadapter;
	private ListView new_list;
	private DialogProgress progress;
	private int list_id = 0;
	private int id = 0;
	private ImageView img_menu;
	public static boolean type = false;
	ScrollTopViewll mytaobao;
	private ArrayList<BeanVo> list_ju;
	private ArrayList<String> list2 = new ArrayList<String>();
	private LinearLayout index_item0, index_item1, index_item2, index_item3,ll_buju_tp;
	private LinearLayout ll_tuangou1, ll_tuangou2, ll_tuangou3, ll_tuangou4;
	String category_id = "";
	private TextView tv_time1, tv_titel1, tv_price1, tv_groupon_price1,
			tv_tuan1, tv_anniu1;
	private TextView tv_time2, tv_titel2, tv_price2, tv_groupon_price2,
			tv_tuan2, tv_anniu2;
	private TextView tv_time3, tv_titel3, tv_price3, tv_groupon_price3,
			tv_tuan3, tv_anniu3;
	private TextView tv_time4, tv_titel4, tv_price4, tv_groupon_price4,
			tv_tuan4, tv_anniu4;
	private ImageView ll_tupian1, ll_tupian2, ll_tupian3, ll_tupian4;
	public AQuery mAq;
	public String datetime;
	JuTuanGouData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jutuangou_tetle);
		// Thread.setDefaultUncaughtExceptionHandler(this);
		mAq = new AQuery(this);
		progress = new DialogProgress(this);
		// progress.CreateProgress();
		Initialize();
//		loadguanggao();//广告
		getTupian();
		loadWeather();
		category_id = "1703";
		loadjutoutiao();
		// loadWeather21();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			if (MyPosterView.type == true) {
				MyPosterView.mQuery.clear();
				MyPosterView.type = false;
			}
			mAq.clear();
			
			if (list_1.size() > 0) {
				list_1 = null;
			}
			if (list_2.size() > 0) {
				list_2 = null;
			}
			if (list_3.size() > 0) {
				list_3 = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// public void uncaughtException(Thread arg0, Throwable arg1) {
	// // TODO Auto-generated method stub
	// //在此处理异常， arg1即为捕获到的异常
	// Log.i("AAA", "uncaughtException   " + arg1);
	// }
	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// // loadCate();
	// }

	// 中奖
	private void loadjutoutiao() {
		// progress.CreateProgress();
		String strUrl = RealmName.REALM_NAME_LL
				+ "/get_order_groupon_award_list?top=10&orderby=";
		// System.out.println("中奖"+strUrl);
		AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse1(arg1);
			}
		}, null);
	}

	// 中奖
	BeanVo data_zj;
	private void parse1(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			String status = jsonObject.getString("status");
			if (status.equals("y")) {
				System.out.println("中奖====================" + st);
				list_ju = new ArrayList<BeanVo>();
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					data_zj = new BeanVo();
					JSONObject object = jsonArray.getJSONObject(i);
					data_zj.user_name = object.getString("user_name");
					String order_groupon = object.getString("order_groupon");
					// JSONArray json = new JSONArray(order_groupon);
					JSONObject obt = new JSONObject(order_groupon);
					// for (int k = 0; k < json.length(); k++) {
					// JSONObject jsont = json.getJSONObject(k);
					// data.groupon_title =
					// "恭喜"+data.user_name+"抢到"+obt.getString("groupon_title");
					data_zj.groupon_title = obt.getString("groupon_title");
					String nick_name = data_zj.user_name.replaceAll("\\s*", "");
					System.out.println("nick_name-----2-----" + nick_name);

					System.out.println("data.groupon_title===================="
							+ data.user_name);
					// }
					list_ju.add(data_zj);
					type = true;
				}
				data_zj = null;
				System.out.println("list_ju===================="+ list_ju.size());
			} else {
			}
			System.out.println("type====================" + type);
			
			mytaobao.setData(list_ju);
			// progress.CloseProgress();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void Initialize() {
		try {
			// 聚头条
			mytaobao = (ScrollTopViewll) findViewById(R.id.mytaobao);

			mytaobao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(JuTuanGou2Activity.this,
							JuTouTiaoActivity.class);
					startActivity(intent);
				}
			});

			img_menu = (ImageView) findViewById(R.id.img_menu);
			ll_buju_tp = (LinearLayout) findViewById(R.id.ll_buju_tp);
			// img_menu.setBackgroundResource(R.drawable.banner);
			ll_tupian1 = (ImageView) findViewById(R.id.ll_tupian1);
			tv_time1 = (TextView) findViewById(R.id.tv_time1);
			tv_titel1 = (TextView) findViewById(R.id.tv_titel1);
			tv_price1 = (TextView) findViewById(R.id.tv_price1);
			tv_groupon_price1 = (TextView) findViewById(R.id.tv_groupon_price1);
			tv_tuan1 = (TextView) findViewById(R.id.tv_tuan1);
			tv_anniu1 = (TextView) findViewById(R.id.tv_anniu1);

			ll_tupian2 = (ImageView) findViewById(R.id.ll_tupian2);
			tv_time2 = (TextView) findViewById(R.id.tv_time2);
			tv_titel2 = (TextView) findViewById(R.id.tv_titel2);
			tv_price2 = (TextView) findViewById(R.id.tv_price2);
			tv_groupon_price2 = (TextView) findViewById(R.id.tv_groupon_price2);
			tv_tuan2 = (TextView) findViewById(R.id.tv_tuan2);
			tv_anniu2 = (TextView) findViewById(R.id.tv_anniu2);

			ll_tupian3 = (ImageView) findViewById(R.id.ll_tupian3);
			tv_time3 = (TextView) findViewById(R.id.tv_time3);
			tv_titel3 = (TextView) findViewById(R.id.tv_titel3);
			tv_price3 = (TextView) findViewById(R.id.tv_price3);
			tv_groupon_price3 = (TextView) findViewById(R.id.tv_groupon_price3);
			tv_tuan3 = (TextView) findViewById(R.id.tv_tuan3);
			tv_anniu3 = (TextView) findViewById(R.id.tv_anniu3);

			ll_tupian4 = (ImageView) findViewById(R.id.ll_tupian4);
			tv_time4 = (TextView) findViewById(R.id.tv_time4);
			tv_titel4 = (TextView) findViewById(R.id.tv_titel4);
			tv_price4 = (TextView) findViewById(R.id.tv_price4);
			tv_groupon_price4 = (TextView) findViewById(R.id.tv_groupon_price4);
			tv_anniu4 = (TextView) findViewById(R.id.tv_anniu4);

			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			// index_item3 = (LinearLayout) findViewById(R.id.index_item3);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			// index_item3.setOnClickListener(this);
			ll_tuangou1 = (LinearLayout) findViewById(R.id.ll_tuangou1);
			ll_tuangou2 = (LinearLayout) findViewById(R.id.ll_tuangou2);
			ll_tuangou3 = (LinearLayout) findViewById(R.id.ll_tuangou3);
			ll_tuangou4 = (LinearLayout) findViewById(R.id.ll_tuangou4);
			// ll_tuangou1.setOnClickListener(this);
			ll_tuangou2.setOnClickListener(this);
			ll_tuangou3.setOnClickListener(this);
			ll_tuangou4.setOnClickListener(this);

			// myGridView = (MyGridView) findViewById(R.id.mGv);
			new_list = (ListView) findViewById(R.id.new_list);
			Button iv_fanhui = (Button) findViewById(R.id.fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_item0:
			try {
				if (list_1.size() > 0) {
					Intent intent = new Intent(JuTuanGou2Activity.this,
							JuTuanGouActivity.class);
					intent.putExtra("title", "拼精彩");
					intent.putExtra("category_id", list_1.get(0)
							.getCategory_id());
					intent.putExtra("zhuangtai", "groupon");
					intent.putExtra("type", "1");
					startActivity(intent);
				} else {
					Toast.makeText(JuTuanGou2Activity.this, "数据为空", 200).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.index_item1:
			try {
				if (list_2.size() > 0) {
					Intent intent01 = new Intent(JuTuanGou2Activity.this,
							JuTuanGouActivity.class);
					intent01.putExtra("title", "拼团");
					intent01.putExtra("category_id", list_2.get(0)
							.getCategory_id());
					intent01.putExtra("zhuangtai", "group");
					intent01.putExtra("type", "2");
					// intent01.putExtra("type_xq", "1");
					startActivity(intent01);
				} else {
					Toast.makeText(JuTuanGou2Activity.this, "数据为空", 200).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.index_item2:
			try {
				if (list_3.size() > 0) {
					Intent intent02 = new Intent(JuTuanGou2Activity.this,
							JuTuanGouActivity.class);
					intent02.putExtra("title", "预售团");
					intent02.putExtra("category_id", list_3.get(0)
							.getCategory_id());
					intent02.putExtra("zhuangtai", "ladder");
					// intent02.putExtra("type_xq", "2");
					startActivity(intent02);
				} else {
					Toast.makeText(JuTuanGou2Activity.this, "数据为空", 200).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		// case R.id.index_item3:
		// if (list_4.size() > 0) {
		//
		// Intent intent03 = new Intent(JuTuanGou2Activity.this,
		// JuTuanGouActivity.class);
		// intent03.putExtra("title", "一元聚宝");
		// intent03.putExtra("category_id", list_4.get(0).getCategory_id());
		// intent03.putExtra("zhuangtai","4");
		// startActivity(intent03);
		// }else {
		// Toast.makeText(JuTuanGou2Activity.this, "数据为空", 200).show();
		// }
		// break;
		case R.id.ll_tuangou1:
			Intent intent0 = new Intent(JuTuanGou2Activity.this,
					JuJingCaiXqActivity.class);
			intent0.putExtra("id", list_1.get(0).getId());
			intent0.putExtra("choujiang", "110");
			intent0.putExtra("type", "1");
			intent0.putExtra("time_type", "time_type");
			intent0.putExtra("fx_shuzi", "groupon");
			intent0.putExtra("datatype", "5");
			startActivity(intent0);
			break;
		case R.id.ll_tuangou2:
			Intent intent1 = new Intent(JuTuanGou2Activity.this,
					JuTuanGouXqActivity.class);
			intent1.putExtra("id", list_2.get(0).getId());
			intent1.putExtra("type", "2");
			intent1.putExtra("jiekou", "2");// 接口状态
			intent1.putExtra("fx_shuzi", "group");
			// intent1.putExtra("type_xq", "1");
			intent1.putExtra("datatype", "4");
			startActivity(intent1);
			break;
		case R.id.ll_tuangou3:
			Intent intent2 = new Intent(JuTuanGou2Activity.this,
					JuTuanGouXqActivity.class);
			intent2.putExtra("id", list_3.get(0).getId());
			intent2.putExtra("jiekou", "3");// 接口状态
			// intent2.putExtra("type_xq", "2");
			intent2.putExtra("fx_shuzi", "ladder");
			intent2.putExtra("datatype", "7");
			startActivity(intent2);
			break;
		case R.id.ll_tuangou4:
			Intent intent3 = new Intent(JuTuanGou2Activity.this,
					JuTuanGouXqActivity.class);
			intent3.putExtra("id", list_4.get(0).getId());
			startActivity(intent3);
			break;

		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				System.out.println("个数是多少1====================" + list.size());
				adapter = new MyAdapter2(getApplicationContext(), list, list2);
				myGridView.setAdapter(adapter);
				break;
			case 2:
				System.out
						.println("个数是多少2====================" + list_1.size());
				Jutuangouadapter = new JuTuanGouAdapter(
						getApplicationContext(), list_1);
				new_list.setAdapter(Jutuangouadapter);
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 输出聚精彩列表
	 * 
	 * @param category_id
	 */
	java.util.Date now_1;
	java.util.Date date_1;
	private void loadWeather() {
		progress.CreateProgress();
		list_1 = new ArrayList<JuTuanGouData>();
		// AsyncHttp.get(RealmName.REALM_NAME_LL +
		// "/get_game_groupon_top?channel_name=groupon&category_id=0&top=1"
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
						+ "/get_article_top_list_2017?channel_name=groupon&top=1&strwhere=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out
										.println("输出所有拼团活动列表=========" + arg1);
								try {
									list_1 = new ArrayList<JuTuanGouData>();

									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									String info = object.getString("info");
									datetime = object.getString("datetime");
									if (status.equals("y")) {
										JSONArray jsonArray = object
												.getJSONArray("data");
										data = new JuTuanGouData();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject obj = jsonArray
													.getJSONObject(i);
											data.setId(obj.getString("id"));
											data.setTitle(obj
													.getString("title"));
											data.setImg_url(obj
													.getString("img_url"));
											// data.setArticle_id(obj.getString("article_id"));
											// data.setPrice(obj.getString("price"));
											// data.setPeople(obj.getString("people"));
											// data.setGroupon_price(obj.getString("groupon_price"));
											data.setAdd_time(obj
													.getString("add_time"));
											data.setUpdate_time(obj
													.getString("update_time"));
											data.setCategory_id(obj
													.getString("category_id"));
											data.setEnd_time(obj
													.getString("end_time"));

											JSONObject jsot = obj
													.getJSONObject("default_spec_item");
											data.setArticle_id(jsot
													.getString("article_id"));
											data.setSell_price(jsot
													.getString("sell_price"));

											JSONObject jsoct = jsot
													.getJSONObject("default_activity_price");
											data.setPeople(jsoct
													.getString("people"));
											data.setPrice(jsoct
													.getString("price"));
											list_1.add(data);
										}
										ll_tuangou1.setVisibility(View.VISIBLE);
										// String title =
										// list_1.get(0).getTitle();
										// System.out.println("title====1====="+title);

										tv_titel1.setText(list_1.get(0)
												.getTitle());
										tv_price1.setText(list_1.get(0)
												.getSell_price());
										tv_groupon_price1.setText("￥"
												+ list_1.get(0).getPrice());
										tv_tuan1.setText(list_1.get(0)
												.getPeople() + "人团");
										tv_time1.setText(list_1.get(0)
												.getUpdate_time());
										ImageLoader imageLoader = ImageLoader
												.getInstance();
										imageLoader
												.displayImage(
														(String) RealmName.REALM_NAME_HTTP
																+ list_1.get(0)
																		.getImg_url(),
														ll_tupian1);
										imageLoader.clearMemoryCache();
										// mAq.id(ll_tupian1).image(RealmName.REALM_NAME_HTTP+list_1.get(0).getImg_url());
										SimpleDateFormat df = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");

										try {
											now_1 = df.parse(list_1.get(0)
													.getEnd_time());
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										try {
											date_1 = df.parse(datetime);
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										long end_time = now_1.getTime();
										long time = date_1.getTime();
										System.out
												.println("end_time-------------"
														+ end_time);
										System.out.println("time-------------"
												+ time);
										if (end_time > time) {
											System.out
													.println("1-------立即参与------");
											getzhou();

										} else {
											System.out
													.println("2-----已结束--------");
											tv_anniu1.setText("已经结束");
										}

										System.out
												.println("1---------------------"
														+ list_1.size());
										data = null;
									} else {
										ll_tuangou1.setVisibility(View.GONE);
										// progress.CloseProgress();
										// Toast.makeText(JuTuanGou2Activity.this,
										// info, 200).show();
									}
									progress.CloseProgress();
									loadWeather2();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}, null);
	}

	private void getzhou() {
		// TODO Auto-generated method stub
		ll_tuangou1.setOnClickListener(this);
	}

	/**
	 * 输出聚团列表2
	 * 
	 * @param category_id
	 */
	java.util.Date now_2;
	java.util.Date date_2;
	JuTuanGouData data_11;
	private void loadWeather2() {
		list_2 = new ArrayList<JuTuanGouData>();
		// AsyncHttp.get(RealmName.REALM_NAME_LL +
		// "/get_game_groupon_top?channel_name=group&category_id=0&top=1"
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
						+ "/get_article_top_list_2017?channel_name=group&top=1&strwhere=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out
										.println("输出所有拼团活动列表=========" + arg1);
								try {
									list_2 = new ArrayList<JuTuanGouData>();
									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									String info = object.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = object
												.getJSONArray("data");
										data_11 = new JuTuanGouData();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject obj = jsonArray
													.getJSONObject(i);
											data_11.setId(obj.getString("id"));
											data_11.setTitle(obj
													.getString("title"));
											data_11.setImg_url(obj
													.getString("img_url"));
											// data.setArticle_id(obj.getString("article_id"));
											// data.setPrice(obj.getString("price"));
											// data.setPeople(obj.getString("people"));
											// data.setGroupon_price(obj.getString("groupon_price"));
											data_11.setAdd_time(obj
													.getString("add_time"));
											data_11.setUpdate_time(obj
													.getString("update_time"));
											data_11.setCategory_id(obj
													.getString("category_id"));
											data_11.setEnd_time(obj
													.getString("end_time"));

											JSONObject jsot = obj
													.getJSONObject("default_spec_item");
											data_11.setArticle_id(jsot
													.getString("article_id"));
											data_11.setSell_price(jsot
													.getString("sell_price"));

											JSONObject jsoct = jsot
													.getJSONObject("default_activity_price");
											data_11.setPeople(jsoct
													.getString("people"));
											data_11.setPrice(jsoct
													.getString("price"));
											list_2.add(data_11);
										}
										System.out
												.println("2---------------------"
														+ list_2.size());
										ll_tuangou2.setVisibility(View.VISIBLE);
										tv_titel2.setText(list_2.get(0)
												.getTitle());
										tv_price2.setText(list_2.get(0)
												.getSell_price());
										tv_groupon_price2.setText("￥"
												+ list_2.get(0).getPrice());
										tv_tuan2.setText(list_2.get(0)
												.getPeople() + "人团");
										tv_time2.setText(list_2.get(0)
												.getUpdate_time());
										ImageLoader imageLoader = ImageLoader
												.getInstance();
										imageLoader
												.displayImage(
														(String) RealmName.REALM_NAME_HTTP
																+ list_2.get(0)
																		.getImg_url(),
														ll_tupian2);
										imageLoader.clearMemoryCache();
										// mAq.id(ll_tupian2).image(RealmName.REALM_NAME_HTTP+list_2.get(0).getImg_url());

										SimpleDateFormat df = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");

										try {
											now_2 = df.parse(list_2.get(0)
													.getEnd_time());
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										try {
											date_2 = df.parse(datetime);
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										long end_time = now_2.getTime();
										long time = date_2.getTime();
										System.out
												.println("end_time------2-------"
														+ end_time);
										System.out.println("time--------2-----"
												+ time);
										if (end_time > time) {
											System.out
													.println("1----2---立即参与------");
											getzhou2();

										} else {
											System.out
													.println("2--2---已结束--------");
											tv_anniu2.setText("已经结束");
										}
										data_11 = null;
									} else {
										ll_tuangou2.setVisibility(View.GONE);
									}
									loadWeather3();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}, null);
	}

	private void getzhou2() {
		// TODO Auto-generated method stub
		ll_tuangou2.setOnClickListener(this);
	}

	/**
	 * 输出预售团列表3
	 * 
	 * @param category_id
	 */
	java.util.Date now_3;
	java.util.Date date_3;
	JuTuanGouData data_22;
	private void loadWeather3() {
		list_3 = new ArrayList<JuTuanGouData>();
		// AsyncHttp.get(RealmName.REALM_NAME_LL +
		// "/get_game_groupon_top?channel_name=ladder&category_id=0&top=1"
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
						+ "/get_article_top_list_2017?channel_name=ladder&top=1&strwhere=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out
										.println("输出所有拼团活动列表=========" + arg1);
								try {
									list_3 = new ArrayList<JuTuanGouData>();
									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									String info = object.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = object
												.getJSONArray("data");
										data_22 = new JuTuanGouData();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject obj = jsonArray.getJSONObject(i);
											data_22.setId(obj.getString("id"));
											data_22.setTitle(obj
													.getString("title"));
											data_22.setImg_url(obj
													.getString("img_url"));
											// data.setArticle_id(obj.getString("article_id"));
											// data.setPrice(obj.getString("price"));
											// data.setPeople(obj.getString("people"));
											// data.setGroupon_price(obj.getString("groupon_price"));
											data_22.setAdd_time(obj
													.getString("add_time"));
											data_22.setUpdate_time(obj
													.getString("update_time"));
											data_22.setCategory_id(obj
													.getString("category_id"));
											data_22.setEnd_time(obj
													.getString("end_time"));

											JSONObject jsot = obj
													.getJSONObject("default_spec_item");
											data_22.setArticle_id(jsot
													.getString("article_id"));
											data_22.setSell_price(jsot
													.getString("sell_price"));

											JSONObject jsoct = jsot
													.getJSONObject("default_activity_price");
											data_22.setPeople(jsoct
													.getString("people"));
											data_22.setPrice(jsoct
													.getString("price"));
											list_3.add(data_22);
										}
										ll_tuangou3.setVisibility(View.VISIBLE);
										System.out
												.println("3---------------------"
														+ list_3.size());
										tv_titel3.setText(list_3.get(0)
												.getTitle());
										tv_price3.setText(list_3.get(0)
												.getSell_price());
										tv_groupon_price3.setText("￥"
												+ list_3.get(0).getPrice());
										tv_tuan3.setText(list_3.get(0)
												.getPeople() + "人团");
										tv_time3.setText(list_3.get(0)
												.getUpdate_time());
										ImageLoader imageLoader = ImageLoader
												.getInstance();
										imageLoader
												.displayImage(
														(String) RealmName.REALM_NAME_HTTP
																+ list_3.get(0)
																		.getImg_url(),
														ll_tupian3);
										imageLoader.clearMemoryCache();
										// mAq.id(ll_tupian3).image(RealmName.REALM_NAME_HTTP+list_3.get(0).getImg_url());

										SimpleDateFormat df = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");

										try {
											now_3 = df.parse(list_3.get(0)
													.getEnd_time());
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										try {
											date_3 = df.parse(datetime);
										} catch (java.text.ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										long end_time = now_3.getTime();
										long time = date_3.getTime();
										System.out
												.println("end_time------3-------"
														+ end_time);
										System.out.println("time--------3-----"
												+ time);
										if (end_time > time) {
											System.out
													.println("1----3---立即参与------");
											getzhou3();
										} else {
											System.out
													.println("2--3---已结束--------");
											tv_anniu3.setText("已经结束");
										}
										data_22 = null;
									} else {
										ll_tuangou3.setVisibility(View.GONE);
									}
									progress.CloseProgress();
									// loadWeather4();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}, null);
	}

	private void getzhou3() {
		// TODO Auto-generated method stub
		ll_tuangou3.setOnClickListener(this);
	}


//	private void loadguanggao() {
//		try {
//
//			// 广告滚动
//			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_adbanner_list?advert_id=17",
//					new AsyncHttpResponseHandler() {
//						@Override
//						public void onSuccess(int arg0, String arg1) {
//							super.onSuccess(arg0, arg1);
//							System.out
//									.println("======输出33=============" + arg1);
//							try {
//								JSONObject object = new JSONObject(arg1);
//								String status = object.getString("status");
//								String info = object.getString("info");
//								if (status.equals("y")) {
//									JSONArray array = object.getJSONArray("data");
//									int len = array.length();
//									ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
//									for (int i = 0; i < len; i++) {
//										AdvertDao1 ada = new AdvertDao1();
//										JSONObject json = array
//												.getJSONObject(i);
//										ada.setId(json.getString("id"));
//										ada.setAd_url(json.getString("ad_url"));
//										String ad_url = ada.getAd_url();
//										ImageLoader imageLoader = ImageLoader.getInstance();
//										imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ ad_url, img_menu);
//										imageLoader.clearMemoryCache();// 清除内存缓存
//										images.add(ada);
//									}
//								} else {
//
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//
//						@Override
//						public void onFailure(Throwable arg0, String arg1) {
//							// TODO Auto-generated method stub
//							super.onFailure(arg0, arg1);
//							System.out.println("======输出112============="+ arg0);
//							System.out.println("======输出113============="+ arg1);
//						}
//
//					}, null);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 广告图片
	 */
	ArrayList<AdvertDao1> images;
	private MyPosterView advPager = null;
	AdvertDao1 ada;
	private void getTupian() {
		// TODO Auto-generated method stub
		try {
			//广告滚动	
			advPager = (MyPosterView) findViewById(R.id.adv_pagerll);
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/get_adbanner_list?advert_id=17",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								System.out.println("加载滚动广告================="+arg1);
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									ada.setLink_url(json.getString("link_url"));
//									ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
									images.add(ada);
								}
								ada = null;
								System.out.println("images.size()================="+images.size());
								Message msg = new Message();
								msg.obj = images;
								msg.what = 0;
								childHandler.sendMessage(msg);
								}else{
									
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, getApplicationContext());
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	ArrayList<AdvertDao1> tempss;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				tempss = (ArrayList<AdvertDao1>) msg.obj;
				ArrayList<String> urls = new ArrayList<String>();
				for(int i=0;i<tempss.size();i++){
					urls.add(RealmName.REALM_NAME_HTTP + tempss.get(i).getAd_url());
					System.out.println("tempss================="+tempss.get(i).getAd_url());
				}
				
				if (urls.size() == 1) {
					ll_buju_tp.setVisibility(View.GONE);
					img_menu.setVisibility(View.VISIBLE);
//					 mAq.id(ling_tip).image(RealmName.REALM_NAME_HTTP+proInverseImg);
					 mAq.id(img_menu).image(RealmName.REALM_NAME_HTTP+tempss.get(0).getAd_url());
				}else {
					ll_buju_tp.setVisibility(View.VISIBLE);
					img_menu.setVisibility(View.GONE);
					advPager.setData(urls, new MyPosterOnClick() {
					@Override
					public void onMyclick(int position) {
						// TODO Auto-generated method stub
//						link_url
//						Message msg = new Message();
//						msg.what = 13;
//						msg.obj = tempss.get(position).getId();
//						handler.sendMessage(msg);
					}
				}, true, imageLoader, true);
				}
//				MyPosterView.mQuery.clear();//清除内存
				break;
			default:
				break;
			}
		};
	};

	private void inter() {

		int size = list.size();// 数据总长度

		// 获得屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int windowWidth = metrics.widthPixels;
		int itemWidth = windowWidth / 4;

		// 获得屏幕宽度也可以这样写
		// int itemWidth = getWindowManager().getDefaultDisplay().getWidth() /
		// 5;//屏幕显示默认数量

		int gridViewWidth = (int) (size * itemWidth);// linearLayout的总宽度
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
		myGridView.setLayoutParams(params);// 设置GridView布局参数
		myGridView.setNumColumns(size);// 动态设置GridView列数
	}

}
