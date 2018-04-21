package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.JuYunShangAdaper;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GoodsListBean;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.shangpingListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

public class JuYunshangXqActivity extends BaseActivity implements OnClickListener{
	//	private ArrayList<GoodsListData> lists;
	private ArrayList<shangpingListData> list_ll;
	private MyGridView myGridView;
	private int INDX = -1;
	GoodsListData data;
	GoodsListBean bean;
	private JuYunShangAdaper jysadapter;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	private int id = 0;
	TextView tv_shangping;
	int len;
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private LinearLayout index_item0, index_item1, index_item2, index_item3,
			ll_sjjs;
	public AQuery mAq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juyunshang_xq);
		progress = new DialogProgress(this);
		mAq = new AQuery(this);
		Initialize();
		//		lists = new ArrayList<GoodsListData>();
		//		new_list.setAdapter(jysadapter);
		//		loadCate();
		int trade_id = Integer.parseInt(getIntent().getStringExtra("id"));
		System.out.println("=====================trade_id"+trade_id);
		loadCate(trade_id);
	}

	private void Initialize() {
		try {
			tv_shangping = (TextView) findViewById(R.id.tv_shangping);
			myGridView = (MyGridView) findViewById(R.id.gridView);
			ll_sjjs = (LinearLayout) findViewById(R.id.ll_sjjs);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			index_item3 = (LinearLayout) findViewById(R.id.index_item3);

			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			cursor3 = (ImageView) findViewById(R.id.cursor3);
			cursor4 = (ImageView) findViewById(R.id.cursor4);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			index_item3.setOnClickListener(this);

			TextView tv_letter = (TextView) findViewById(R.id.tv_ware_name);
			ImageView img_ware = (ImageView) findViewById(R.id.img_ware);
			ImageView iv_img_url = (ImageView) findViewById(R.id.iv_img_url);
			String name = getIntent().getStringExtra("name");
			String img_url = getIntent().getStringExtra("img_url");
			String logo_url = getIntent().getStringExtra("logo_url");
			if (name.equals("null")) {
				tv_letter.setText("");
			}else {
				tv_letter.setText(name);
			}

			//		ImageLoader imageLoader = ImageLoader.getInstance();
			//	    imageLoader.displayImage(RealmName.REALM_NAME_HTTP + logo_url,img_ware);
			//	    imageLoader.displayImage(RealmName.REALM_NAME_HTTP + img_url,iv_img_url);
			System.out.println("logo_url-------------------------"+logo_url);
			if (logo_url.equals("")) {
				img_ware.setBackgroundResource(R.drawable.zams_tb);
			}else {
				mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + logo_url);
			}
			mAq.id(iv_img_url).image(RealmName.REALM_NAME_HTTP + img_url);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}



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
						//				jysadapter.putData(lists);
						System.out.println("=====================这里"+list_ll.size());
						jysadapter = new JuYunShangAdaper(list_ll, getApplicationContext());
						myGridView.setAdapter(jysadapter);
						JuYunShangAdaper.mAq.clear();
						myGridView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								System.out.println("=====================这里"+list_ll.size());
								Intent intent = new Intent(JuYunshangXqActivity.this,WareInformationActivity.class);
								intent.putExtra("id", list_ll.get(arg2).id);
								intent.putExtra("hongbao", "hongbao");
								startActivity(intent);
							}
						});
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 1:
					break;
				case 2:
					break;


				default:
					break;
			}
		};
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.index_item0:
				cursor1.setVisibility(View.VISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				myGridView.setVisibility(View.GONE);
				ll_sjjs.setVisibility(View.VISIBLE);
				//			fund_id = "1";
				//			load_list(true, fund_id);
				break;
			case R.id.index_item1:
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.VISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				myGridView.setVisibility(View.VISIBLE);
				ll_sjjs.setVisibility(View.GONE);
				//			fund_id = "4";
				//			int trade_id = Integer.parseInt(getIntent().getStringExtra("id"));
				//			System.out.println("=====================trade_id"+trade_id);
				//			loadCate(trade_id);

				//			load_list(trade_id, true);
				break;
			case R.id.index_item2:
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.VISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				myGridView.setVisibility(View.GONE);
				ll_sjjs.setVisibility(View.GONE);
				break;
			case R.id.index_item3:
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.VISIBLE);
				myGridView.setVisibility(View.GONE);
				ll_sjjs.setVisibility(View.GONE);
				break;

			default:
				break;
		}
	}

	//商品列表
	private void loadCate(int user_id){
		list_ll = new ArrayList<shangpingListData>();
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
							data.title = object.getString("title");
							data.setTitle(object.getString("title"));
							data.img_url = object.getString("img_url");
							data.sell_price = object.getString("sell_price");
							data.market_price = object.getString("market_price");
							Log.v("data1", data.id + "");
							list_ll.add(data);
						}
						//						String zhou = list_ll.get(0).title;
						//						System.out.println("================"+zhou);
						tv_shangping.setText(String.valueOf(list_ll.size()));
						handler.sendEmptyMessage(0);
					}else {
						Toast.makeText(JuYunshangXqActivity.this, info, 200).show();
					}

				} catch (Exception e) {
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
					//						load_list(INDX, false);
					refresh.onFooterRefreshComplete();
				}
			}, 1000);
		}
	};




}
