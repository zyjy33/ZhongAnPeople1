package com.hengyushop.demo.home;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.ZhongAnYlAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.ZaylListAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.WareInformationActivity;

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
public class ZhongAnYlListActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private GridView gridview;
	private ArrayList<ZhongAnYlBean> list = null;
	private ZhongAnYlAdapter zaylaAdapter;
	ZhongAnYlData data;
	ZhongAnYlBean bean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhonganyanglao_time);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(ZhongAnYlListActivity.this);
		intren();
		String id = getIntent().getStringExtra("id");
		loadWeather(id);
	}

	//当Activity被销毁时会调用onDestory方法
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (ZaylListAdapter.type == true) {
			ZaylListAdapter.aQuery.clear();
			ZaylListAdapter.type = false;
		}
	}
	public void intren() {
		try {
			gridview = (GridView) findViewById(R.id.gridView);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
			iv_fanhui.setOnClickListener(this);

			TextView tv_titel = (TextView) findViewById(R.id.tv_titel);
			String title = getIntent().getStringExtra("title");
			tv_titel.setText(title);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					break;
				case 1:

					try {
						System.out.println("list个数是多少===================="+list.size());
						ZaylListAdapter MyAdapter2 = new ZaylListAdapter(list, getApplicationContext());
						gridview.setAdapter(MyAdapter2);
						setListViewHeightBasedOnChildren(gridview);
						gridview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
													long arg3) {

								try {
									String id = list.get(arg2).getId();
									System.out.println("=====id====================="+id);
									Intent intent = new Intent(ZhongAnYlListActivity.this, WareInformationActivity.class);
									intent.putExtra("id", id);
									startActivity(intent);

								} catch (Exception e) {

									e.printStackTrace();
								}
							}
						});

					} catch (Exception e) {

						e.printStackTrace();
					}
					break;


				default:
					break;
			}
		};
	};

	private void loadWeather(String id) {
		progress.CreateProgress();
		list = new ArrayList<ZhongAnYlBean>();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_page_size_list?channel_name=innovate&category_id="+id+"" +
						"&page_size="+50+"&page_index="+1+"&strwhere=&orderby=",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("=======列表数据================================");
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								int len = jsonArray.length();
								for (int i = 0; i < len; i++) {
									bean = new ZhongAnYlBean();
									JSONObject obt = jsonArray.getJSONObject(i);
									bean.setId(obt.getString("id"));
									bean.setTitle(obt.getString("title"));
									bean.setImg_url(obt.getString("img_url"));
									bean.setSell_price(obt.getString("sell_price"));
									bean.setMarket_price(obt.getString("market_price"));
									String zhou = bean.getTitle();
									System.out.println("=====内容====================="+zhou);
									list.add(bean);
								}
								progress.CloseProgress();
								handler.sendEmptyMessage(1);
							}else {
								progress.CloseProgress();
								Toast.makeText(ZhongAnYlListActivity.this, info, 200).show();
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {

						super.onFailure(arg0, arg1);
						progress.CloseProgress();
						Toast.makeText(ZhongAnYlListActivity.this, "接口异常", 200).show();
						System.out.println("======输出112============="+arg0);
						System.out.println("======输出113============="+arg1);
					}

				}, null);
	}
	public void setListViewHeightBasedOnChildren(GridView gridview2) {
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

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.ra5:
				//			loadWeather();
				break;

			default:
				break;
		}
	}
}
