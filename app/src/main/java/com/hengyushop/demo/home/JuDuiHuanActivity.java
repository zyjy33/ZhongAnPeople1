package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.pub.QiYeJinMianAdaper;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.GoodsMyGridViewAdaper;
import com.hengyushop.airplane.adapter.JuduihuanAdaper;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

/**
 * 福利馆
 *
 * @author Administrator
 *
 */
public class JuDuiHuanActivity extends BaseActivity implements OnClickListener {
	private ArrayList<JuTuanGouData> lists = null;
	private ArrayList<JuTuanGouData> lists_ll = null;
	private MyGridView myGridView, myGridView2;
	private int INDX = 0;
	private GoodsListAdapter myadapter;
	private JuduihuanAdaper jdhadapter;
	private ListView new_list, listview;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	JuTuanGouData data;
	// private int id = 0;
	private MyPosterView advPager = null;
	private LinearLayout index_item0, index_item1, index_item2, index_item3;
	private LinearLayout yh, yh_0, yh_1, yh_2, yh_ll;
	private ImageView img, img_0, img_1, img_2;
	private TextView tv_biaoti, tv_biaoti_0, tv_biaoti_1, tv_biaoti_2,
			tv_qiandao, tv_jifen;
	private TextView tv_jifengduihuan, tv_jifengduihuan_0, tv_jifengduihuan_1,
			tv_jifengduihuan_2;
	private TextView tv_shichangjia, tv_shichangjia_0, tv_shichangjia_1,
			tv_shichangjia_2;
	public static String id, login_sign, point;
	public static AQuery aQuery;
	public static String drawn = "";
	public static String id_ll = "";
	public static String drawn_ll = "";
	private ArrayList<XsgyListData> list;
	LinearLayout ll_buju_tp;
	private ImageView zams_fw_1,zams_fw_2,zams_fw_3,zams_fw_4,zams_fw_5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jifenguan);// activity_juhuihuan_tetle
		// activity_jifenguan
		progress = new DialogProgress(this);
		aQuery = new AQuery(JuDuiHuanActivity.this);
		// progress.CreateProgress();
		Initialize();
		getzhiding();// 获取置顶商品
		// userloginqm();
		// getjiangxiangxq();
		load_list(INDX, true);
	}

	@Override
	protected void onResume() {

		super.onResume();
		jianceqiandao();
	}

	public void onDestroy() {
		super.onDestroy();
		try {

			if (lists.size() > 0) {
				lists.clear();
				lists = null;
			}
			if (lists_ll.size() > 0) {
				lists_ll.clear();
				lists_ll = null;
				aQuery.clear();
			}
			if (JuduihuanAdaper.type == true) {
				JuduihuanAdaper.mAq.clear();
				JuduihuanAdaper.type = false;
			}
			BitmapDrawable bd = (BitmapDrawable)ll_buju_tp.getBackground();
			ll_buju_tp.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd.setCallback(null);
			bd.getBitmap().recycle();

			BitmapDrawable bd1 = (BitmapDrawable)zams_fw_1.getBackground();
			zams_fw_1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)zams_fw_2.getBackground();
			zams_fw_2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
			BitmapDrawable bd3 = (BitmapDrawable)zams_fw_3.getBackground();
			zams_fw_3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd3.setCallback(null);
			bd3.getBitmap().recycle();
			BitmapDrawable bd4 = (BitmapDrawable)zams_fw_4.getBackground();
			zams_fw_4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd4.setCallback(null);
			bd4.getBitmap().recycle();
			BitmapDrawable bd5 = (BitmapDrawable)zams_fw_5.getBackground();
			zams_fw_5.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd5.setCallback(null);
			bd5.getBitmap().recycle();
		} catch (Exception e) {

			e.printStackTrace();
		}
	};
	private void Initialize() {
		try {
			ll_buju_tp = (LinearLayout) findViewById(R.id.ll_buju_tp);
//			ll_buju_tp.setBackgroundResource(R.drawable.zams_jfg);
			Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.zams_jfg);
			BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
			ll_buju_tp.setBackgroundDrawable(bd);
			zams_fw_1 = (ImageView) findViewById(R.id.zams_fw_1);
			zams_fw_2 = (ImageView) findViewById(R.id.zams_fw_2);
			zams_fw_3 = (ImageView) findViewById(R.id.zams_fw_3);
			zams_fw_4 = (ImageView) findViewById(R.id.zams_fw_4);
			zams_fw_5 = (ImageView) findViewById(R.id.zams_fw_5);

			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jfg_jfrw);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			zams_fw_1.setBackgroundDrawable(bd1);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jfg_jfcj);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			zams_fw_2.setBackgroundDrawable(bd2);
			Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jfg_gyg);
			BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
			zams_fw_3.setBackgroundDrawable(bd3);
			Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jfg_dtqd);
			BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
			zams_fw_4.setBackgroundDrawable(bd4);
			Bitmap bm5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.zxdq);
			BitmapDrawable bd5 = new BitmapDrawable(this.getResources(), bm5);
			zams_fw_5.setBackgroundDrawable(bd5);

			yh = (LinearLayout) findViewById(R.id.yh);
			yh_0 = (LinearLayout) findViewById(R.id.yh_0);
			yh_1 = (LinearLayout) findViewById(R.id.yh_1);
			yh_2 = (LinearLayout) findViewById(R.id.yh_2);
			yh_ll = (LinearLayout) findViewById(R.id.yh_ll);
			yh.setOnClickListener(this);
			yh_0.setOnClickListener(this);
			yh_1.setOnClickListener(this);
			yh_2.setOnClickListener(this);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			index_item3 = (LinearLayout) findViewById(R.id.index_item3);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			index_item3.setOnClickListener(this);
			myGridView = (MyGridView) findViewById(R.id.gridView);
			myGridView.setFocusable(false);
			tv_qiandao = (TextView) findViewById(R.id.tv_qiandao);
			tv_jifen = (TextView) findViewById(R.id.tv_jifen);
			tv_qiandao.setOnClickListener(this);

			img = (ImageView) findViewById(R.id.img);
			img_0 = (ImageView) findViewById(R.id.img_0);
			img_1 = (ImageView) findViewById(R.id.img_1);
			img_2 = (ImageView) findViewById(R.id.img_2);
			tv_biaoti = (TextView) findViewById(R.id.tv_biaoti);
			tv_biaoti_0 = (TextView) findViewById(R.id.tv_biaoti_0);
			tv_biaoti_1 = (TextView) findViewById(R.id.tv_biaoti_1);
			tv_biaoti_2 = (TextView) findViewById(R.id.tv_biaoti_2);
			tv_jifengduihuan = (TextView) findViewById(R.id.tv_jifengduihuan);
			tv_jifengduihuan_0 = (TextView) findViewById(R.id.tv_jifengduihuan_0);
			tv_jifengduihuan_1 = (TextView) findViewById(R.id.tv_jifengduihuan_1);
			tv_jifengduihuan_2 = (TextView) findViewById(R.id.tv_jifengduihuan_2);
			tv_shichangjia = (TextView) findViewById(R.id.tv_shichangjia);
			tv_shichangjia_0 = (TextView) findViewById(R.id.tv_shichangjia_0);
			tv_shichangjia_1 = (TextView) findViewById(R.id.tv_shichangjia_1);
			tv_shichangjia_2 = (TextView) findViewById(R.id.tv_shichangjia_2);

			// ling_tip = (ImageView) findViewById(R.id.ling_tip);
			// refresh = (PullToRefreshView) findViewById(R.id.refresh);
			// refresh.setOnHeaderRefreshListener(listHeadListener);
			// refresh.setOnFooterRefreshListener(listFootListener);
			// new_list = (ListView) findViewById(R.id.new_list);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

			myGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
					// System.out.println("====================="+lists.get(arg2).id);
					Intent intent = new Intent(JuDuiHuanActivity.this,
							WareInformationActivity.class);
					intent.putExtra("jdh_id", lists.get(arg2).id);
					intent.putExtra("jdh_type", "1");
					startActivity(intent);
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 获取置顶商品
	 */
	private void getzhiding() {
		progress.CreateProgress();
		lists_ll = new ArrayList<JuTuanGouData>();
		// AsyncHttp.get(RealmName.REALM_NAME_LL +
		// "/get_game_point_top?channel_name=point&category_id=0&top=4&where=",
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
								+ "/get_article_top_list_2017?channel_name=point&top=4&strwhere=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								System.out
										.println("获取置顶商品======================="
												+ arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject
											.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = jsonObject
												.getJSONArray("data");
										// int len = jsonArray.length();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject object = jsonArray
													.getJSONObject(i);
											// GoodsListData data = new
											// GoodsListData();
											data = new JuTuanGouData();
											data.id = object.getString("id");
											data.title = object
													.getString("title");
											data.img_url = object
													.getString("img_url");
											// spList.point =
											// object.getString("exchange_point");
											// spList.price =
											// object.getString("exchange_price");
											// spList.goods_price =
											// object.getString("goods_price");

											JSONObject jsot = object
													.getJSONObject("default_spec_item");
											data.setGoods_id(jsot
													.getString("goods_id"));
											data.setArticle_id(jsot
													.getString("article_id"));
											data.setMarket_price(jsot
													.getString("market_price"));
											data.setCashing_point(jsot
													.getString("cashing_point"));
											data.setExchange_point(jsot
													.getString("exchange_point"));
											data.setExchange_price(jsot
													.getString("exchange_price"));
											lists_ll.add(data);
										}
										data = null;
									} else {
										progress.CloseProgress();
										yh.setVisibility(View.GONE);
										yh_ll.setVisibility(View.GONE);
										yh_1.setVisibility(View.GONE);
										yh_2.setVisibility(View.GONE);
									}
									progress.CloseProgress();
									try {
										System.out
												.println("lists_ll.size()========================="
														+ lists_ll.size());
										if (!lists_ll.get(0).title.equals("")) {
											aQuery.id(img)
													.image(RealmName.REALM_NAME_HTTP
															+ lists_ll.get(0).img_url);
											tv_biaoti.setText(lists_ll.get(0).title);
											System.out
													.println("1========================="
															+ lists_ll.get(0).exchange_point);
											tv_jifengduihuan.setText("福利兑换："
													+ lists_ll.get(0).exchange_point
													+ "福利"
													+ "+"
													+ lists_ll.get(0).exchange_price
													+ "元");
											tv_shichangjia
													.getPaint()
													.setFlags(
															Paint.STRIKE_THRU_TEXT_FLAG
																	| Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
											tv_shichangjia.setText("市场价:￥"
													+ lists_ll.get(0).market_price);
											yh.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View arg0) {

													// method stub
													Intent intent = new Intent(
															JuDuiHuanActivity.this,
															WareInformationActivity.class);
													intent.putExtra("jdh_id",
															lists_ll.get(0).id);
													intent.putExtra("jdh_type",
															"1");
													startActivity(intent);
												}
											});
										} else {
											yh.setVisibility(View.GONE);
										}

										if (!lists_ll.get(1).title.equals("")) {
											aQuery.id(img_0)
													.image(RealmName.REALM_NAME_HTTP
															+ lists_ll.get(1).img_url);
											tv_biaoti_0.setText(lists_ll.get(1).title);
											System.out
													.println("2========================="
															+ lists_ll.get(1).exchange_point);
											tv_jifengduihuan_0.setText("福利兑换:"
													+ lists_ll.get(1).exchange_point
													+ "福利"
													+ "+"
													+ lists_ll.get(1).exchange_price
													+ "元");
											tv_shichangjia_0
													.getPaint()
													.setFlags(
															Paint.STRIKE_THRU_TEXT_FLAG
																	| Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
											tv_shichangjia_0.setText("市场价:￥"
													+ lists_ll.get(1).market_price);

											yh_0.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {

													// method stub
													Intent intent = new Intent(
															JuDuiHuanActivity.this,
															WareInformationActivity.class);
													intent.putExtra("jdh_id",
															lists_ll.get(1).id);
													intent.putExtra("jdh_type",
															"1");
													startActivity(intent);
												}
											});
										} else {
											// yh_0.setVisibility(View.GONE);
											yh_ll.setVisibility(View.GONE);
										}

										if (!lists_ll.get(2).title.equals("")) {
											aQuery.id(img_1)
													.image(RealmName.REALM_NAME_HTTP
															+ lists_ll.get(2).img_url);
											tv_biaoti_1.setText(lists_ll.get(2).title);
											System.out
													.println("3========================="
															+ lists_ll.get(2).exchange_point);
											tv_jifengduihuan_1.setText("福利兑换:"
													+ lists_ll.get(2).exchange_point
													+ "福利"
													+ "+"
													+ lists_ll.get(2).exchange_price
													+ "元");
											tv_shichangjia_1
													.getPaint()
													.setFlags(
															Paint.STRIKE_THRU_TEXT_FLAG
																	| Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
											tv_shichangjia_1.setText("市场价:￥"
													+ lists_ll.get(2).market_price);
											yh_1.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {

													// method stub
													Intent intent = new Intent(
															JuDuiHuanActivity.this,
															WareInformationActivity.class);
													intent.putExtra("jdh_id",
															lists_ll.get(2).id);
													intent.putExtra("jdh_type",
															"1");
													startActivity(intent);
												}
											});
										} else {
											yh_1.setVisibility(View.GONE);
										}

										if (!lists_ll.get(3).title.equals("")) {
											aQuery.id(img_2)
													.image(RealmName.REALM_NAME_HTTP
															+ lists_ll.get(3).img_url);
											tv_biaoti_2.setText(lists_ll.get(3).title);
											tv_jifengduihuan_2.setText("福利兑换:"
													+ lists_ll.get(3).exchange_point
													+ "福利"
													+ "+"
													+ lists_ll.get(3).exchange_price
													+ "元");
											tv_shichangjia_2
													.getPaint()
													.setFlags(
															Paint.STRIKE_THRU_TEXT_FLAG
																	| Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
											tv_shichangjia_2.setText("市场价:￥"
													+ lists_ll.get(3).market_price);
											yh_2.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {

													// method stub
													Intent intent = new Intent(
															JuDuiHuanActivity.this,
															WareInformationActivity.class);
													intent.putExtra("jdh_id",
															lists_ll.get(3).id);
													intent.putExtra("jdh_type",
															"1");
													startActivity(intent);
												}
											});
										} else {
											yh_2.setVisibility(View.GONE);
										}
										// progress.CloseProgress();
									} catch (Exception e) {

										e.printStackTrace();
									}
								} catch (JSONException e) {

									e.printStackTrace();
								}
							}

							@Override
							public void onFailure(Throwable arg0, String arg1) {

								progress.CloseProgress();
								System.out
										.println("==========================访问接口失败！");
								System.out.println("========================="
										+ arg0);
								System.out.println("=========================="
										+ arg1);
								// Toast.makeText(JuDuiHuanActivity.this, "异常",
								// 200).show();
								yh.setVisibility(View.GONE);
								yh_ll.setVisibility(View.GONE);
								yh_1.setVisibility(View.GONE);
								yh_2.setVisibility(View.GONE);
								super.onFailure(arg0, arg1);
							}
						}, null);
	}

	/**
	 * 第一个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(final int INDX, boolean flag) {
		// progress.CreateProgress();
		RUN_METHOD = 1;
		lists = new ArrayList<JuTuanGouData>();
		// if(flag){
		// //计数和容器清零
		// CURRENT_NUM = 0;
		// lists = new ArrayList<JuTuanGouData>();
		// }
		System.out.println("=====================001--" + INDX);
		// AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_game_point_page_size?channel_name=point&category_id="+INDX+""
		// +
		// "&page_size="+100+"&page_index="+1+"&strwhere=&orderby=",
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
								+ "/get_article_page_size_list_2017?channel_name=point&category_id="
								+ INDX + "" + "&page_size=" + 100 + "&page_index=" + 1
								+ "&strwhere=&orderby=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								System.out.println("=====================二级值=="
										+ arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject
											.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = jsonObject
												.getJSONArray("data");
										// int len = jsonArray.length();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject object = jsonArray
													.getJSONObject(i);
											// GoodsListData spList = new
											// GoodsListData();
											// spList.id =
											// object.getString("id");
											// spList.title =
											// object.getString("title");
											// spList.img_url =
											// object.getString("img_url");
											// spList.point =
											// object.getString("exchange_point");
											// spList.price =
											// object.getString("exchange_price");
											// spList.goods_price =
											// object.getString("goods_price");//goods_price

											JuTuanGouData data = new JuTuanGouData();
											data.id = object.getString("id");
											data.title = object
													.getString("title");
											data.img_url = object
													.getString("img_url");
											// spList.point =
											// object.getString("exchange_point");
											// spList.price =
											// object.getString("exchange_price");
											// spList.goods_price =
											// object.getString("goods_price");
											System.out
													.println("data.title ======================"
															+ data.title);
											JSONObject jsot = object
													.getJSONObject("default_spec_item");
											data.setGoods_id(jsot
													.getString("goods_id"));
											data.setArticle_id(jsot
													.getString("article_id"));
											data.setMarket_price(jsot
													.getString("market_price"));
											// data.setCashing_point(jsot.getString("cashing_point"));
											data.setExchange_point(jsot
													.getString("exchange_point"));
											data.setExchange_price(jsot
													.getString("exchange_price"));
											System.out
													.println("data.exchange_point ======================"
															+ data.exchange_point);
											lists.add(data);
										}

									} else {
										// progress.CloseProgress();
									}
									handler.sendEmptyMessage(0);
									progress.CloseProgress();
									// if(len!=0){
									// CURRENT_NUM =CURRENT_NUM+VIEW_NUM;
									// }
								} catch (JSONException e) {

									e.printStackTrace();
								}
							}
						}, null);
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					// myadapter.putData(lists);
					System.out.println("=====================2这里" + lists.size());
					jdhadapter = new JuduihuanAdaper(lists, getApplicationContext());
					myGridView.setAdapter(jdhadapter);
					if (lists.size() > 0) {
						JuduihuanAdaper.mAq.clear();
					}
					progress.CloseProgress();
					break;
				case 1:
					break;
				case 13:
					try {
						String id = (String) msg.obj;
						System.out.println("1111=============" + id);
						Intent intent13 = new Intent(JuDuiHuanActivity.this,
								Webview1.class);
						intent13.putExtra("gg_id", id);
						startActivity(intent13);
					} catch (Exception e) {

						e.printStackTrace();
					}
					break;

				default:
					break;
			}
		};
	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.tv_qiandao:
				// SharedPreferences spPreferences =
				// getSharedPreferences("user_juduihuan", MODE_PRIVATE);
				// String point = spPreferences.getString("point", "");
				userqiandao();
				break;
			case R.id.index_item0:
				Intent intent = new Intent(JuDuiHuanActivity.this,
						JiFenRenWuActivity.class);
				startActivity(intent);
				break;
			case R.id.index_item1:
				Intent intent1 = new Intent(JuDuiHuanActivity.this,
						TiaoYiTiaoActivity.class);
				startActivity(intent1);
				break;
			case R.id.index_item2:
				// getjiangxiang(login_sign);

				// System.out.println("id-----------------------------------"+id);
				// for (int i = 0; i < list.size(); i++) {
				// if (list.get(i).id.equals(id)) {
				// drawn = list.get(i).drawn;
				// }
				// }
				// System.out.println("drawn-----------------------------------"+drawn);
				Intent intent3 = new Intent(JuDuiHuanActivity.this,
						GuaYiGuaActivity.class);
				startActivity(intent3);
				break;
			case R.id.index_item3:
				break;

			default:
				break;
		}
	}

	/**
	 * 签到获取福利
	 */
	private void userqiandao() {
		try {
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			String user_id = spPreferences.getString("user_id", "");
			String user_name = spPreferences.getString("user", "");
			String login_sign = spPreferences.getString("login_sign", "");
			String strUrlone = RealmName.REALM_NAME_LL
					+ "/comment_sign_in?user_id=" + user_id + "&user_name="
					+ user_name + "&login_sign=" + login_sign + "";
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======签到获取福利=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							// String webtel = obj.getString("webtel");
							Toast.makeText(JuDuiHuanActivity.this, info, 200)
									.show();
							tv_qiandao.setText("已签到");
							userloginqm2();
						} else {
							// tv_qiandao.setText("已签到");
							Toast.makeText(JuDuiHuanActivity.this, info, 200)
									.show();
							// userloginqm2();
						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};

				@Override
				public void onFailure(Throwable arg0, String arg1) {

					super.onFailure(arg0, arg1);
					Toast.makeText(JuDuiHuanActivity.this, "访问接口失败", 200)
							.show();
				}
			}, JuDuiHuanActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 检测用户是否已经签到
	 */
	private void jianceqiandao() {
		try {
			// progress.CreateProgress();
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			String user_id = spPreferences.getString("user_id", "");
			String user_name = spPreferences.getString("user", "");
			String login_sign = spPreferences.getString("login_sign", "");
			// String user_point = spPreferences.getString("point", "");
			// tv_jifen.setText(user_point);
			String strUrlone = RealmName.REALM_NAME_LL
					+ "/comment_sign_exist?user_id=" + user_id + "&user_name="
					+ user_name + "&login_sign=" + login_sign + "";
			System.out.println("======11=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======arg1=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							// JSONObject obj = object.getJSONObject("data");
							// Toast.makeText(QianDaoActivity.this, info,
							// 200).show();
							progress.CloseProgress();
						} else {
							// Toast.makeText(QianDaoActivity.this, info,
							// 200).show();
							tv_qiandao.setText("已签到");
							progress.CloseProgress();
						}
						userloginqm2();
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};

				@Override
				public void onFailure(Throwable arg0, String arg1) {

					super.onFailure(arg0, arg1);
					Toast.makeText(JuDuiHuanActivity.this, "访问接口失败", 200)
							.show();
				}
			}, JuDuiHuanActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 获取登录签名、获取用户福利 1
	 *
	 * @param order_no
	 */
	// private void userloginqm1() {
	// try{
	// SharedPreferences spPreferences = getSharedPreferences("longuserset",
	// MODE_PRIVATE);
	// String user_name = spPreferences.getString("user", "");
	// String strUrlone = RealmName.REALM_NAME_LL +
	// "/get_user_model?username="+user_name+"";
	// System.out.println("======获取用户福利============="+strUrlone);
	// AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
	// public void onSuccess(int arg0, String arg1) {
	// try {
	// JSONObject object = new JSONObject(arg1);
	// String status = object.getString("status");
	// if (status.equals("y")) {
	// JSONObject obj = object.getJSONObject("data");
	// UserRegisterllData data = new UserRegisterllData();
	// data.login_sign = obj.getString("login_sign");
	// point = obj.getString("point");
	// System.out.println("======point============="+point);
	// login_sign = data.login_sign;
	// System.out.println("======login_sign============="+login_sign);
	//
	// try {
	// SharedPreferences spPreferences = getSharedPreferences("user_juduihuan",
	// MODE_PRIVATE);
	// String jdh_point = spPreferences.getString("jdh_point", "");
	// System.out.println("======jdh_point============="+jdh_point);
	// int point_ll = Integer.parseInt(point);
	// int jdh_point_ll = Integer.parseInt(jdh_point);
	// System.out.println("======point_ll============="+point_ll);
	// System.out.println("======jdh_point_ll============="+jdh_point_ll);
	// if (jdh_point_ll >= point_ll) {
	// tv_qiandao.setText("已签到");
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }

	// tv_jifen.setText(point);
	// }else{
	// }
	// // getjiangxiang(login_sign);
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	// }
	// };
	// }, null);
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	/**
	 * 获取登录签名、获取用户福利
	 *
	 * @param order_no
	 */
	private void userloginqm2() {
		try {
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL+ "/get_user_model?username=" + user_name + "";
			System.out.println("======获取用户福利=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							point = obj.getString("point");
							System.out.println("======point============="
									+ point);
							login_sign = data.login_sign;
							System.out.println("======login_sign============="
									+ login_sign);
							// SharedPreferences spPreferences =
							// getSharedPreferences("user_juduihuan",
							// MODE_PRIVATE);
							// Editor editor = spPreferences.edit();
							// editor.putString("jdh_point", point);
							// editor.commit();
							tv_jifen.setText(point);
						} else {
						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 广告滚动
	 */
	private void loadguanggao() {
		try {
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_adbanner_list?advert_id=12",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									AdvertDao1 ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									String ad_url = ada.getAd_url();
									// ada.setAd_url(RealmName.REALM_NAME_HTTP +
									// json.getString("ad_url"));
									ImageLoader imageLoader = ImageLoader
											.getInstance();
									// imageLoader.displayImage(RealmName.REALM_NAME_HTTP
									// + ad_url, ling_tip);
									images.add(ada);
								}
								// Message msg = new Message();
								// msg.obj = images;
								// msg.what = 0;
								// childHandler.sendMessage(msg);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);

		} catch (Exception e) {

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
					for (int i = 0; i < tempss.size(); i++) {
						urls.add(tempss.get(i).getAd_url());
					}
					advPager.setData(urls, new MyPosterOnClick() {
						@Override
						public void onMyclick(int position) {

							Message msg = new Message();
							msg.what = 13;
							msg.obj = tempss.get(position).getId();
							handler.sendMessage(msg);
						}
					}, true, imageLoader, true);
					break;
				default:
					break;
			}
		};
	};

	/**
	 * 上拉列表刷新加载
	 */
	// private OnHeaderRefreshListener listHeadListener = new
	// OnHeaderRefreshListener() {
	//
	// @Override
	// public void onHeaderRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// refresh.onHeaderRefreshComplete();
	// }
	// }, 1000);
	// }
	// };

	/**
	 * 下拉列表刷新加载
	 */
	// private OnFooterRefreshListener listFootListener = new
	// OnFooterRefreshListener() {
	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// System.out.println("=======4=="+INDX);
	// load_list(INDX, false);
	// refresh.onFooterRefreshComplete();
	// }
	// }, 1000);
	// }
	// };

	/**
	 * 输出刮一刮奖
	 *
	 * @param login_sign
	 */
	// private void getjiangxiang(String login_sign) {
	// SharedPreferences spPreferences = getSharedPreferences("longuserset",
	// MODE_PRIVATE);
	// String user_id = spPreferences.getString("user_id", "");
	// String user_name = spPreferences.getString("user", "");
	// // String login_sign = spPreferences.getString("login_sign", "");
	// // String login_sign = getIntent().getStringExtra("login_sign");
	// String strUrlone = RealmName.REALM_NAME_LL +
	// "/get_lottery_award?user_id="+user_id+"&user_name="+user_name+"&lottery_id=16&sign="+login_sign+"";
	// System.out.println("======输出抽奖幸奖项============="+strUrlone);
	// AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
	// public void onSuccess(int arg0, String arg1) {
	// try {
	// System.out.println("======输出抽奖幸奖项============="+arg1);
	// JSONObject object = new JSONObject(arg1);
	// String status = object.getString("status");
	// String info = object.getString("info");
	// if (status.equals("y")) {
	// // Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
	// JSONObject obct = object.getJSONObject("data");
	// id = obct.getString("id");
	// String title = obct.getString("title");
	// String drawn = obct.getString("drawn");
	// }else{
	// Toast.makeText(JuDuiHuanActivity.this, info, 200).show();
	// }
	// System.out.println("======输出抽奖幸奖项=======id======"+id);
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	// }
	// };
	//
	// @Override
	// public void onFailure(Throwable arg0, String arg1) {
	//
	// super.onFailure(arg0, arg1);
	// System.out.println("======访问接口失败============="+arg1);
	// // Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
	// }
	// }, JuDuiHuanActivity.this);
	//
	// }

	/**
	 * 输出刮一刮奖
	 *
	 * @param login_sign
	 */
	// private void getjiangxiang(String login_sign) {
	// SharedPreferences spPreferences = getSharedPreferences("longuserset",
	// MODE_PRIVATE);
	// String user_id = spPreferences.getString("user_id", "");
	// String user_name = spPreferences.getString("user", "");
	// // String login_sign = spPreferences.getString("login_sign", "");
	// // String login_sign = getIntent().getStringExtra("login_sign");
	// String strUrlone = RealmName.REALM_NAME_LL +
	// "/get_article_activity_award?user_id="+user_id+"&user_name="+user_name+"&article_id=7825&sign="+login_sign+"";
	// // System.out.println("======输出抽奖幸奖项============="+strUrlone);
	// AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
	// public void onSuccess(int arg0, String arg1) {
	// try {
	// System.out.println("======输出抽奖幸奖项============="+arg1);
	// JSONObject object = new JSONObject(arg1);
	// String status = object.getString("status");
	// String info = object.getString("info");
	// if (status.equals("y")) {
	// // Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
	// JSONObject obct = object.getJSONObject("data");
	// id = obct.getString("id");
	// // String title = obct.getString("title");
	// // String drawn = obct.getString("drawn");
	// System.out.println("id-----------------------------------"+id);
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).id.equals(id)) {
	// drawn = list.get(i).drawn;
	// }
	// }
	//
	// System.out.println("drawn-----------------------------------"+drawn);
	// Intent intent1 = new Intent(JuDuiHuanActivity.this,
	// GuaYiGuaActivity.class);
	// startActivity(intent1);
	// }else{
	// Toast.makeText(JuDuiHuanActivity.this, info, 200).show();
	// }
	// System.out.println("======输出抽奖幸奖项=======id======"+id);
	//
	//
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	// }
	// };
	//
	// @Override
	// public void onFailure(Throwable arg0, String arg1) {
	//
	// super.onFailure(arg0, arg1);
	// System.out.println("======访问接口失败============="+arg1);
	// // Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
	// }
	// }, JuDuiHuanActivity.this);
	//
	// }

	/**
	 * 输出刮一刮奖详情
	 */
	// private void getjiangxiangxq() {
	// list = new ArrayList<XsgyListData>();
	// // String strUrlone = RealmName.REALM_NAME_LL +
	// "/get_lottery_model?lottery_id=16";
	// String strUrlone = RealmName.REALM_NAME_LL +
	// "/get_article_model?id=7825";
	// // System.out.println("======输出抽奖详情============="+strUrlone);
	// AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
	// public void onSuccess(int arg0, String arg1) {
	// try {
	// // System.out.println("======输出抽奖详情============="+arg1);
	// JSONObject object = new JSONObject(arg1);
	// String status = object.getString("status");
	// String info = object.getString("info");
	// if (status.equals("y")) {
	// // Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
	// JSONObject obct = object.getJSONObject("data");
	// org.json.JSONArray jsonArray = obct.getJSONArray("activity_award");
	// for(int i=0;i<jsonArray.length();i++){
	// JSONObject jobject = jsonArray.getJSONObject(i);
	// XsgyListData spList = new XsgyListData();
	// spList.id = jobject.getString("id");
	// spList.title = jobject.getString("title");
	// spList.drawn = jobject.getString("drawn");
	// list.add(spList);
	// }
	// }else{
	// Toast.makeText(JuDuiHuanActivity.this, info, 200).show();
	// }
	// System.out.println("======list.size()============="+list.size());
	//
	// // System.out.println("id-----------------------------------"+id);
	// // for (int i = 0; i < list.size(); i++) {
	// // if (list.get(i).id.equals(id)) {
	// // drawn = list.get(i).drawn;
	// // }
	// // }
	// // System.out.println("drawn-----------------------------------"+drawn);
	//
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	// }
	// };
	//
	//
	// @Override
	// public void onFailure(Throwable arg0, String arg1) {
	//
	// super.onFailure(arg0, arg1);
	// System.out.println("======访问接口失败============="+arg1);
	// // Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
	// }
	// }, JuDuiHuanActivity.this);
	//
	// }
}