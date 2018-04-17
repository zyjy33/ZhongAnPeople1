package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.CategoryLayoutAdapter;
import com.hengyushop.airplane.adapter.CityLayoutAdapter;
import com.hengyushop.airplane.adapter.ComboListAdapter;
import com.hengyushop.db.ComboDB;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.domain.CategoryDomain;
import com.lglottery.www.domain.ComboDomain;
import com.lglottery.www.domain.ComboListDomain;
import com.lglottery.www.domain.DistrictsDomain;
import com.lglottery.www.widget.XListView;
import com.lglottery.www.widget.XListView.IXListViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComboListActivity extends BaseActivity implements
		IXListViewListener {
	private ComboListAdapter listAdapter;
	private XListView combo_list_layout;
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private RelativeLayout spinner_item1, spinner_item2, spinner_item3;
	private ArrayList<CategoryDomain> categoryList1, categoryList2;
	private ArrayList<DistrictsDomain> districtsDomains;
	private String PARENTLINE;
	private CategoryLayoutAdapter adapter2;
	private CityLayoutAdapter adapter3;
	private int CURRENT_NUM = 0;
	private final int VIEW_NUM = 8;
	private ArrayList<ComboListDomain> listDomains;
	private TextView combo_top_add;
	private SharedUtils utils;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					listAdapter.putLists(listDomains);
					onLoad();
					break;
				case 1:
					String deal_id = (String) msg.obj;
					Intent intent = new Intent(ComboListActivity.this,
							ComboDetalActivity.class);
					intent.putExtra("deal_id", deal_id);
					startActivity(intent);
					break;
				default:
					break;
			}
		};
	};

	/**
	 * 初始化数据
	 */
	private void init() {
		utils = new SharedUtils(getApplicationContext(), "locals");
		combo_top_add = (TextView) findViewById(R.id.combo_top_add);
		combo_top_add.setText(utils.getStringValue("address"));
		combo_list_layout = (XListView) findViewById(R.id.combo_list_layout);
		combo_list_layout.setPullLoadEnable(true);
		combo_list_layout.setXListViewListener(this);
		combo_list_layout.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				Intent intent = new Intent(ComboListActivity.this,
						ComboDetalActivity.class);
				startActivity(intent);
			}
		});
		/**
		 * 创建
		 */
		spinner_item1 = (RelativeLayout) findViewById(R.id.spinner_item1);
		spinner_item2 = (RelativeLayout) findViewById(R.id.spinner_item2);
		spinner_item3 = (RelativeLayout) findViewById(R.id.spinner_item3);
		spinner_item1.setOnClickListener(clickListener);
		spinner_item2.setOnClickListener(clickListener);
		spinner_item3.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.spinner_item1:
					initPopupWindow();
					showPopupWindow(spinner_item1);
					break;
				case R.id.spinner_item2:
					initPopupWindowCity();
					showPopupWindowCity(spinner_item2);
					break;
				case R.id.spinner_item3:

					break;
				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.combo_layout);
		listDomains = new ArrayList<ComboListDomain>();
		init();
		listAdapter = new ComboListAdapter(getApplicationContext(),
				listDomains, imageLoader, handler);
		combo_list_layout.setAdapter(listAdapter);
	}

	/**
	 * 关于弹出窗口
	 */
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.category_layout1, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.title_tip));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		ListView category_p = (ListView) popView.findViewById(R.id.category_p);
		ListView category_c = (ListView) popView.findViewById(R.id.category_c);
		ComboDB category1 = new ComboDB(getApplicationContext());
		categoryList1 = category1.getCategoryDomains();
		CategoryLayoutAdapter adapter = new CategoryLayoutAdapter(
				getApplicationContext(), categoryList1);
		category_p.setAdapter(adapter);
		if (categoryList1.size() > 0) {
			PARENTLINE = categoryList1.get(0).getCategoryId();
		}
		ComboDB category2 = new ComboDB(getApplicationContext());
		adapter2 = new CategoryLayoutAdapter(getApplicationContext(),
				category2.getCategoryDomainsChilds(PARENTLINE));
		category_c.setAdapter(adapter2);
		category_p.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
				if (categoryList1.size() > 0) {
					PARENTLINE = categoryList1.get(position).getCategoryId();
					ComboDB category2 = new ComboDB(getApplicationContext());
					adapter2.putLists(category2
							.getCategoryDomainsChilds(PARENTLINE));
				}
			}
		});
		// 子类
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAsDropDown(spinner_item1);
		}
	}

	/**
	 * 关于弹出窗口
	 */
	private void initPopupWindowCity() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.category_layout1, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.title_tip));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		ListView category_p = (ListView) popView.findViewById(R.id.category_p);
		ListView category_c = (ListView) popView.findViewById(R.id.category_c);
		ComboDB category1 = new ComboDB(getApplicationContext());
		districtsDomains = category1.getDistrictsDomains("8");
		CityLayoutAdapter adapter = new CityLayoutAdapter(
				getApplicationContext(), districtsDomains);
		category_p.setAdapter(adapter);
		if (districtsDomains.size() > 0) {
			PARENTLINE = districtsDomains.get(0).getId();
		}
		ComboDB category2 = new ComboDB(getApplicationContext());
		adapter3 = new CityLayoutAdapter(getApplicationContext(),
				category2.getDistrictsDomainsChilds(PARENTLINE));
		category_c.setAdapter(adapter3);
		category_p.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
				if (districtsDomains.size() > 0) {
					PARENTLINE = districtsDomains.get(position).getId();
					ComboDB category2 = new ComboDB(getApplicationContext());
					adapter3.putLists(category2
							.getDistrictsDomainsChilds(PARENTLINE));
				}
			}
		});
		// 子类
	}

	/**
	 * 连接网路加载数据
	 */
	private void loadData() {
		RequestParams params = new RequestParams();
		/*
		 * &category_name=%E7%BE%8E%E9%A3%9F &city=%E6%B7%B1%E5%9C%B3
		 * &StartIndex=0 &Count=50 &latitude=22.515314 &longitude=113.932174
		 */
		params.put("category_name", getIntent().getStringExtra("tag"));
		params.put("city", "佛山");
		params.put("StartIndex", String.valueOf(CURRENT_NUM));
		params.put("Count", String.valueOf(VIEW_NUM));
		params.put("latitude", utils.getStringValue("lat"));
		params.put("longitude", utils.getStringValue("log"));
		AsyncHttp.post(RealmName.REALM_NAME
						+ "/mi/DianPing_Handler.ashx?act=GetDealsList_ForMobile",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String result) {
						parseListData(result);
						CURRENT_NUM = CURRENT_NUM + VIEW_NUM - 1;
					}
				}, getApplicationContext());
	}

	/**
	 * 解析列表文字数据
	 *
	 * @param result
	 * @return
	 */
	private void parseListData(String result) {
		// Toast.makeText(getApplicationContext(), "请求网络->开始位置:"+CURRENT_NUM,
		// 200).show();
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("Data");
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ComboListDomain domain = new ComboListDomain();
				domain.setBranch_name(object.getString("branch_name"));
				domain.setBussiness_id(object.getString("business_id"));
				domain.setDistance(object.getString("Distance_tmp"));
				domain.setFirst_region(object.getString("FirstRegion"));
				domain.setName(object.getString("name"));
				domain.setSecond_region(object.getString("SecondRegion"));
				JSONArray array = object.getJSONArray("DealsList");
				int jen = array.length();
				ArrayList<ComboDomain> childs = new ArrayList<ComboDomain>();
				for (int j = 0; j < jen; j++) {
					JSONObject childObject = array.getJSONObject(j);
					ComboDomain child = new ComboDomain();
					child.setImage_url(childObject.getString("image_url"));
					child.setList_price(childObject.getString("list_price"));
					child.setCurrent_price(childObject
							.getString("current_price"));
					child.setDeal_id(childObject.getString("deal_id"));
					child.setRequired(childObject
							.getString("is_reservation_required"));
					child.setTitle(childObject.getString("title"));
					child.setPublish_data(childObject.getString("publish_date"));
					childs.add(child);
				}
				domain.setDomains(childs);
				listDomains.add(domain);
			}
			// Message msg = new Message();
			handler.sendEmptyMessage(0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void showPopupWindowCity(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAsDropDown(spinner_item1);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}

	private void onLoad() {
		combo_list_layout.stopRefresh();
		combo_list_layout.stopLoadMore();
		combo_list_layout.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onLoadMore() {
		loadData();
	}
}
