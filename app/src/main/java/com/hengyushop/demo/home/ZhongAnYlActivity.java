package com.hengyushop.demo.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.ZhongAnYlAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 中安养老
 *
 * @author Administrator
 *
 */
public class ZhongAnYlActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_fanhui, iv_biaoti1, iv_biaoti2, iv_biaoti3,
			iv_biaoti4;
	private TextView tv_xiabu;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private ListView listview_01;
	private ArrayList<ZhongAnYlData> list = null;
	private ArrayList<ZhongAnYlBean> list_ll = null;
	private ZhongAnYlAdapter zaylaAdapter;
	ZhongAnYlData data;
	ZhongAnYlBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhonganyanglao);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(ZhongAnYlActivity.this);
		intren();
		// loadCate();
		loadzhonganyl();
	}
	public void onDestroy() {
		super.onDestroy();
		try {
			BitmapDrawable bd1 = (BitmapDrawable)iv_biaoti1.getBackground();
			iv_biaoti1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)iv_biaoti2.getBackground();
			iv_biaoti2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
			BitmapDrawable bd3 = (BitmapDrawable)iv_biaoti3.getBackground();
			iv_biaoti3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd3.setCallback(null);
			bd3.getBitmap().recycle();
			BitmapDrawable bd4 = (BitmapDrawable)iv_biaoti4.getBackground();
			iv_biaoti4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd4.setCallback(null);
			bd4.getBitmap().recycle();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	};
	public void intren() {
		try {
			iv_biaoti1 = (ImageView) findViewById(R.id.iv_biaoti1);
			iv_biaoti2 = (ImageView) findViewById(R.id.iv_biaoti2);
			iv_biaoti3 = (ImageView) findViewById(R.id.iv_biaoti3);
			iv_biaoti4 = (ImageView) findViewById(R.id.iv_biaoti4);
			//			iv_biaoti1.setBackgroundResource(R.drawable.scyanglao);
			//			iv_biaoti2.setBackgroundResource(R.drawable.sqyanglao);
			//			iv_biaoti3.setBackgroundResource(R.drawable.jyyanglao);
			//			iv_biaoti4.setBackgroundResource(R.drawable.cxyl);
			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.scyanglao);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			iv_biaoti1.setBackgroundDrawable(bd1);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sqyanglao);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			iv_biaoti2.setBackgroundDrawable(bd2);
			Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jyyanglao);
			BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
			iv_biaoti3.setBackgroundDrawable(bd3);
			Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.cxyl);
			BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
			iv_biaoti4.setBackgroundDrawable(bd4);

			listview_01 = (ListView) findViewById(R.id.listview_01);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
			iv_fanhui.setOnClickListener(this);
			listview_01.setFocusable(false);

			iv_biaoti1.setOnClickListener(this);
			// setListViewHeightBasedOnChildren(listview_01);
			// listview_01.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> arg0, View arg1,int arg2,
			// long arg3) {
			// // TODO Auto-generated method stub
			// Intent intent=new Intent(context, Webview1.class);
			// intent.putExtra("yanghu_list",data0.get(arg2)+"");
			// startActivity(intent);
			// }
			// });

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					break;
				case 1:

					try {

						System.out.println("list个数是多少===================="
								+ list.size());
						ZhongAnYlAdapter adapter = new ZhongAnYlAdapter(list,
								getApplicationContext());
						listview_01.setAdapter(adapter);

						// WideMarketAdapter adapter = new
						// WideMarketAdapter(list,list_ll,getApplicationContext(),
						// handler);
						// listview_01.setAdapter(adapter);

						// ZhongAnYangLaoAdapter adapter = new
						// ZhongAnYangLaoAdapter(list,list_ll,getApplicationContext(),
						// imageLoader);
						// listview_01.setAdapter(adapter);
						setListViewHeightBasedOnChildren(listview_01);
						adapter.notifyDataSetChanged();

						listview_01
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(AdapterView<?> arg0,
															View arg1, int arg2, long arg3) {
										// TODO Auto-generated method stub
										String id = list.get(arg2).getId();
										System.out.println("====================="
												+ id);
										Intent intent = new Intent(
												ZhongAnYlActivity.this,
												ZhongAnYlListActivity.class);
										intent.putExtra("id", id);
										intent.putExtra("title", list.get(arg2)
												.getTitle());
										startActivity(intent);
									}
								});

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

	private void loadzhonganyl() {
		progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL
						+ "/get_article_loop_list?channel_name=innovate&top=3",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out
								.println("=======列表数据================================"
										+ arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								list = new ArrayList<ZhongAnYlData>();
								JSONArray jsonArray = object
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									try {
										JSONObject object1 = jsonArray
												.getJSONObject(i);
										data = new ZhongAnYlData();
										data.setId(object1.getString("id"));
										data.setTitle(object1
												.getString("title"));
										String zhou1 = data.getTitle();
										System.out
												.println("=====标题====================="
														+ zhou1);
										String article = object1
												.getString("article");
										data.setList(new ArrayList<ZhongAnYlBean>());
										JSONArray ja = new JSONArray(article);

										for (int k = 0; k < ja.length(); k++) {
											// try {
											bean = new ZhongAnYlBean();
											JSONObject obt = ja
													.getJSONObject(k);
											bean.setId(obt.getString("id"));
											bean.setTitle(obt
													.getString("title"));
											bean.setImg_url(obt
													.getString("img_url"));
											bean.setSell_price(obt
													.getString("sell_price"));
											bean.setMarket_price(obt
													.getString("market_price"));
											String zhou = bean.getTitle();
											System.out
													.println("=====内容====================="
															+ zhou);

											data.getList().add(bean);
											// } catch (Exception e) {
											// // TODO: handle exception
											// e.printStackTrace();
											// }
										}
										list.add(data);

									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}
								System.out
										.println("=====内容list====================="
												+ list.size());
								progress.CloseProgress();
								handler.sendEmptyMessage(1);
								// Toast.makeText(ZhongAnYlActivity.this, info,
								// 200).show();
							} else {
								progress.CloseProgress();
								Toast.makeText(ZhongAnYlActivity.this, info,
										200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}, null);
	}

	// 商家列表
	// private void loadCate(){
	// progress.CreateProgress();
	// AsyncHttp.get(RealmName.REALM_NAME_LL +
	// "/get_category_child_list?channel_name=innovate&parent_id=0"
	// , new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	// // TODO Auto-generated method stub
	// super.onSuccess(arg0, arg1);
	// parse(arg1);
	// }
	// }, null);
	// }
	//
	// public void parse(String st) {
	// try {
	// list = new ArrayList<ZhongAnYlData>();
	// System.out.println("类别=======================");
	// JSONObject jsonObject = new JSONObject(st);
	// String status = jsonObject.getString("status");
	// String info = jsonObject.getString("info");
	// if (status.equals("y")) {
	// JSONArray jsonArray = jsonObject.getJSONArray("data");
	// int len = jsonArray.length();
	// for (int i = 0; i < len; i++) {
	// data = new ZhongAnYlData();
	// JSONObject object = jsonArray.getJSONObject(i);
	// data.id = object.getString("id");
	// data.title = object.getString("title");
	// System.out.println("标题======================="+data.title);
	// String id = data.id;
	// int category_id = Integer.parseInt(id);
	// loadWeather(category_id);
	// list.add(data);
	// }
	// String zhou = "641";
	// // loadWeather(zhou);
	// progress.CloseProgress();
	// handler.sendEmptyMessage(1);
	// }else {
	// progress.CloseProgress();
	// Toast.makeText(ZhongAnYlActivity.this, info, 200).show();
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// private void loadWeather(int category_id) {
	// list_ll = new ArrayList<ZhongAnYlBean>();
	// AsyncHttp.get(RealmName.REALM_NAME_LL +
	// "/get_article_page_size_list?channel_name=innovate&category_id="+category_id+""
	// +
	// "&page_size="+10+"&page_index="+1+"&strwhere=&orderby=",
	// new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int arg0, String arg1) {
	// // TODO Auto-generated method stub
	// super.onSuccess(arg0, arg1);
	// // System.out.println("=======详情数据=="+arg1);
	// System.out.println("=======列表数据================================");
	// try {
	// progress.CloseProgress();
	// JSONObject object = new JSONObject(arg1);
	// String status = object.getString("status");
	// String info = object.getString("info");
	// if (status.equals("y")) {
	// JSONArray jsonArray = object.getJSONArray("data");
	// int len = jsonArray.length();
	// // data.setList(new ArrayList<GuigeBean>());
	// data.setList(new ArrayList<ZhongAnYlBean>());
	// for (int i = 0; i < len; i++) {
	// bean = new ZhongAnYlBean();
	// JSONObject obt = jsonArray.getJSONObject(i);
	// bean.id = obt.getString("id");
	// bean.title = obt.getString("title");
	// bean.img_url = obt.getString("img_url");
	// bean.sell_price = obt.getString("sell_price");
	// bean.market_price = obt.getString("market_price");
	// list_ll.add(bean);
	// try {
	// String zhou = bean.getTitle();
	// System.out.println("=====内容====================="+zhou);
	// data.getList().add(bean);
	// // String items = data.getList().get(0).getTitle();
	// progress.CloseProgress();
	// // handler.sendEmptyMessage(1);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// }
	// // list.add(data);
	// progress.CloseProgress();
	// // handler.sendEmptyMessage(1);
	// // Toast.makeText(ZhongAnYlActivity.this, info, 200).show();
	// }else {
	// Toast.makeText(ZhongAnYlActivity.this, info, 200).show();
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// }, null);
	// }

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
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.ra5:
				// loadWeather();
				break;
			case R.id.iv_biaoti1:
				Intent intent = new Intent(ZhongAnYlActivity.this, NewWare.class);
				intent.putExtra("channel_name", "mall");
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
