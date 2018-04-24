package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.QiYeJinMianAdaper;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.GoodsMyGridViewAdaper;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.SpListData;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.HomeActivity;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class HongBaoZqListActivity extends BaseActivity {

	private ArrayList<SpListData> lists = null;
	private DialogProgress progress;
	private int ID;
	private PullToRefreshView refresh;
	GoodsMyGridViewAdaper jdhadapter;
	private MyGridView gridView_list;
	int len;
	String type_zhi = "";
	private TextView textView1;
	GridView gridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hongbao_list);
		progress = new DialogProgress(HongBaoZqListActivity.this);
		textView1 = (TextView) findViewById(R.id.textView1);
		type_zhi = getIntent().getStringExtra("type_zhi");
		if (type_zhi.equals("0")) {
			textView1.setText("2017价值回馈A区");
		}else if (type_zhi.equals("1")) {
			textView1.setText("2017价值回馈B区");
		}else if (type_zhi.equals("2")) {
			textView1.setText("2017价值回馈C区");
		}else if (type_zhi.equals("3")) {
			textView1.setText(getIntent().getStringExtra("title"));
		}
		initdata();

		lists = new ArrayList<SpListData>();
		jdhadapter = new GoodsMyGridViewAdaper(lists, getApplicationContext());
		gridView.setAdapter(jdhadapter);

		load_list(true);
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}

	public void onDestroy() {
		super.onDestroy();
		System.out.println("GoodsMyGridViewAdaper.type=======1=========="+GoodsMyGridViewAdaper.type);
		try {

			if (GoodsMyGridViewAdaper.type == true) {
				GoodsMyGridViewAdaper.mAq.clear();
				GoodsMyGridViewAdaper.mAq.recycle(gridView);
				GoodsMyGridViewAdaper.type = false;
			}

			if (lists.size() > 0) {
				lists.clear();
				lists = null;
			}

			System.out.println("GoodsMyGridViewAdaper.type=======1=========="+GoodsMyGridViewAdaper.type);
		} catch (Exception e) {

			e.printStackTrace();
		}
	};


	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					System.out.println("=====================这里2=="+lists.size());
					jdhadapter.putData(lists);
					progress.CloseProgress();
//				jdhadapter.notifyDataSetChanged();
					gridView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {

							String id = lists.get(arg2).getId();
							System.out.println("====================="+id);
							Intent intent = new Intent(HongBaoZqListActivity.this,WareInformationActivity.class);
							intent.putExtra("id", id);
							startActivity(intent);
						}
					});
					break;

				default:
					break;
			}
		};
	};
	private void initdata() {
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		gridView = (GridView) findViewById(R.id.gridView);
		gridView_list = (MyGridView) findViewById(R.id.gridView_list);
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	}

//	  <GridView
//      android:id="@+id/gridView"
//      android:layout_width="match_parent"
//      android:layout_height="match_parent"
//      android:background="@color/no_color"
//      android:cacheColorHint="@color/no_color"
//      android:divider="@color/list_diver"
//      android:numColumns="2"
//      android:dividerHeight="1dp">

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {

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

			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						load_list(false);
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	/**
	 * 商品列表数据解析
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	SpListData spList;
	JSONObject object;
	private void load_list(boolean flag) {
		progress.CreateProgress();
		if(flag){
			CURRENT_NUM = 1;
			lists = new ArrayList<SpListData>();
		}
		String category_id = getIntent().getStringExtra("category_id");
//			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?channel_name=goods&category_id="+category_id+"
//			&page_size=500&page_index=1&strwhere=&orderby=",
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?channel_name=goods&category_id="+category_id+"" +
						"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
				new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
//								System.out.println("=====================三级值"+arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								len = jsonArray.length();
								for(int i=0;i<jsonArray.length();i++){
									spList = new SpListData();
									object = jsonArray.getJSONObject(i);
									spList.id = object.getString("id");
									spList.img_url = object.getString("img_url");
									spList.title = object.getString("title");
									spList.market_price = object.getString("market_price");
									spList.sell_price = object.getString("sell_price");
									spList.cashing_packet = object.getString("cashing_packet");
//										JSONArray jaArray = object.getJSONArray("albums");
//										for (int j = 0; j < jaArray.length(); j++) {
//											JSONObject jact = jaArray.getJSONObject(j);
//										}
									lists.add(spList);
								}
								object = null;
								spList = null;
							}else {
								progress.CloseProgress();
								Toast.makeText(HongBaoZqListActivity.this, "没有商品了", 200).show();
							}
							handler.sendEmptyMessage(1);

							if (lists.size() > 0) {
								GoodsMyGridViewAdaper.mAq.clear();
							}
							progress.CloseProgress();

							if(len!=0){
								CURRENT_NUM =CURRENT_NUM+1;
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0,String arg1) {

						super.onFailure(arg0, arg1);
						progress.CloseProgress();
						System.out.println("arg1====================="+arg1);
						Toast.makeText(HongBaoZqListActivity.this, "链接异常", Toast.LENGTH_SHORT).show();
					}
				}, HongBaoZqListActivity.this);
	}



}
