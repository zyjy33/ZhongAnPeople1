package com.hengyushop.demo.shopcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GouWuCheAGoodsAdaper;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.MyShopingCartAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.MyShopPingCarActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.DataBean;
import com.hengyushop.entity.ShopCartBean;
import com.hengyushop.entity.SpListData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.WareInformationActivity;

public class MyShopCarActivity extends BaseActivity implements OnClickListener {

	private static final int INITIALIZE = 0;
	private SharedPreferences spPreferences;
	private static ListView mListView;// 列表
	public static String user_name, user_id;
	private ListAdapter mListAdapter;// adapter
	private LinearLayout adv_pager;
	private LinearLayout list_shops, list_none,ll_xianshi,ll_tjsp;
	private List<DataBean> mListData = new ArrayList<DataBean>();// 数据

	private boolean isBatchModel;// 是否可删除模式

	private RelativeLayout mBottonLayout;
	private CheckBox mCheckAll; // 全选 全不选
	private TextView mEdit; // 切换到删除模式

	private TextView mPriceAll; // 商品总价

	private TextView mSelectNum; // 选中数量

	private TextView mFavorite; // 移到收藏夹,分享

	private TextView mDelete; // 删除 结算

	private TextView subtitle;

	private double totalPrice = 0; // 商品总价
	public static double dzongjia = 0;
	/** 批量模式下，用来记录当前选中状态 */
	private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
	private ImageView back;
	private boolean flag = true; // 全选或全取消
	List<DataBean> result;
	private DialogProgress progress;
	private AQuery query;
	private static List<String> list_id = new ArrayList<String>();
	private static List<String> list_size = new ArrayList<String>();
	String num = "1";
	public static StringBuffer str;
	boolean zhuangtai = true;
	private Button btn_register;
	private MyGridView myGridView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gouwuche);
		progress = new DialogProgress(MyShopCarActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user_name", "");
		user_id = spPreferences.getString("user_id", "");
		query = new AQuery(this);
		initView();
		initListener();
		load_list();
	}

	@Override
	public void onResume() {

		super.onResume();
		MyShopPingCarActivity.str = null;
		WareInformationActivity.jdh_type = "";// 聚兑换判断为空
		if (list_id.size() > 0) {
			list_id.clear();
		}

		// 计算个数清空
		if (list_size.size() > 0) {
			list_size.clear();
			mDelete.setText(getResources().getString(R.string.menu_sett) + "("
					+ list_size.size() + ")");
		}
		getgouwuche();
	}

	private void getgouwuche() {

		// progress.CreateProgress();
		result = new ArrayList<DataBean>();
		user_id = spPreferences.getString("user_id", "");
		if (!user_id.equals("")) {

			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/get_shopping_cart?pageSize=500&pageIndex=1&user_id="
					+ user_id + "", new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {

					super.onSuccess(arg0, arg1);
					try {
						JSONObject jsonObject = new JSONObject(arg1);
						System.out.println("1================" + arg1);
						JSONArray jsot = jsonObject.getJSONArray("data");
						System.out.println("jsot================"
								+ jsot.length());
						if (jsot.length() > 0) {
							for (int i = 0; i < jsot.length(); i++) {
								JSONObject object = jsot.getJSONObject(i);
								DataBean dm = new DataBean();
								dm.setId(object.getInt("id"));
								dm.setTitle(object.getString("title"));
								dm.setMarket_price(object
										.getString("market_price"));
								dm.setSell_price(object.getDouble("sell_price"));
								dm.setImg_url(object.getString("img_url"));
								dm.setQuantity(object.getInt("quantity"));
								dm.setArticle_id(object.getString("article_id"));
								dm.setGoods_id(object.getString("goods_id"));
								result.add(dm);
							}
							progress.CloseProgress();
							adv_pager.setVisibility(View.GONE);
							subtitle.setVisibility(View.VISIBLE);
							mListView.setVisibility(View.VISIBLE);
							ll_xianshi.setVisibility(View.VISIBLE);
							System.out.println("1================");
						} else {
							progress.CloseProgress();
							System.out.println("2================");
							adv_pager.setVisibility(View.VISIBLE);
							subtitle.setVisibility(View.GONE);
							mListView.setVisibility(View.GONE);
							// mPriceAll.setText("￥"+0.00);
							ll_xianshi.setVisibility(View.GONE);
						}

						// refreshListView();
						progress.CloseProgress();
					} catch (JSONException e) {

						e.printStackTrace();
					}

				}

			}, null);

		} else {
			progress.CloseProgress();
			// adv_pager.setVisibility(View.VISIBLE);
		}
		totalPrice = 0;
		mPriceAll.setText("￥" + totalPrice);
		mCheckAll.setChecked(false);
		loadData();
		System.out.println("result22-------------" + result.size());
	}

	/**
	 * 热销专区
	 */
	SpListData spList;
	private ArrayList<SpListData> lists;
	private void load_list() {
		lists = new ArrayList<SpListData>();
		try {
			AsyncHttp.get(RealmName.REALM_NAME_LL+
							//				"/get_article_page_size_list?channel_name=life&category_id=687&page_size=20&page_index=1&strwhere=&orderby=",
							"/get_article_top_list?channel_name=goods&top=5&strwhere=status=0%20and%20is_top=1",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {

							super.onSuccess(arg0, arg1);
							System.out.println("热销专区====================="+arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									JSONArray jsonArray = jsonObject.getJSONArray("data");
									//									 len = jsonArray.length();
									for(int i=1;i<jsonArray.length();i++){
										JSONObject object = jsonArray.getJSONObject(i);
										spList = new SpListData();
										spList.id = object.getString("id");
										spList.img_url = object.getString("img_url");
										spList.title = object.getString("title");
										spList.market_price = object.getString("market_price");
										spList.sell_price = object.getString("sell_price");
										lists.add(spList);
									}
									spList = null;
								}else {
									//										progress.CloseProgress();
									Toast.makeText(MyShopCarActivity.this, info, 200).show();
								}
								System.out.println("lists.size()====================="+lists.size());
								GouWuCheAGoodsAdaper jdhadapter = new GouWuCheAGoodsAdaper(lists, MyShopCarActivity.this);
								myGridView.setAdapter(jdhadapter);
								//									GouWuCheAGoodsAdaper.mAq.clear();
								//									setListViewHeightBasedOnChildren(myGridView);
								myGridView.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
										System.out.println("====================="+lists.get(arg2).id);
										Intent intent = new Intent(MyShopCarActivity.this,WareInformationActivity.class);
										intent.putExtra("id", lists.get(arg2).id);
										startActivity(intent);
									}
								});
								//									progress.CloseProgress();
							} catch (JSONException e) {

								e.printStackTrace();
							}
						}
					}, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initView() {
		myGridView = (MyGridView) findViewById(R.id.gridView);
		back = (ImageView) findViewById(R.id.back);
		adv_pager = (LinearLayout) findViewById(R.id.adv_pager);
		ll_xianshi = (LinearLayout) findViewById(R.id.ll_xianshi);
		mBottonLayout = (RelativeLayout) findViewById(R.id.cart_rl_allprie_total);
		mCheckAll = (CheckBox) findViewById(R.id.check_box_all);
		mEdit = (TextView) findViewById(R.id.subtitle);
		mPriceAll = (TextView) findViewById(R.id.tv_cart_total);
		// mSelectNum = (TextView) findViewById(R.id.tv_cart_select_num);
		mFavorite = (TextView) findViewById(R.id.tv_cart_move_favorite);
		mDelete = (TextView) findViewById(R.id.tv_cart_buy_or_del);
		subtitle = (TextView) findViewById(R.id.subtitle);
		mListView = (ListView) findViewById(R.id.listview);
		// mListView.setSelector(R.drawable.list_selector);
		btn_register = (Button) findViewById(R.id.btn_register);

		ll_tjsp = (LinearLayout)findViewById(R.id.ll_tjsp);
		ll_tjsp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(MyShopCarActivity.this,TuiJianSpListActivity.class);
				startActivity(intent);
			}
		});

		// 购物车无商品去逛逛
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				user_id = spPreferences.getString("user_id", "");
				if (user_id.equals("")) {
					Intent intentll = new Intent(MyShopCarActivity.this,
							UserLoginActivity.class);
					startActivity(intentll);
				} else {
					Intent intentll = new Intent(MyShopCarActivity.this,
							NewWare.class);
					startActivity(intentll);
				}
			}
		});

	}

	private void initListener() {
		mEdit.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		mCheckAll.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	private void loadData() {
		new LoadDataTask().execute(new Params(INITIALIZE));
	}

	private void refreshListView() {
		if (mListAdapter == null) {
			mListAdapter = new ListAdapter();
			mListView.setAdapter(mListAdapter);
			mListView.setOnItemClickListener(mListAdapter);
			setListViewHeightBasedOnChildren(mListView);
		} else {
			mListAdapter.notifyDataSetChanged();
			setListViewHeightBasedOnChildren(mListView);
		}
	}

	private List<DataBean> getData() {
		System.out.println("result11-------------" + result.size());
		return result;
	}

	class Params {
		int op;

		public Params(int op) {
			this.op = op;
			System.out.println("result1-------------");
		}

	}

	class Result {
		int op;
		List<DataBean> list;

	}

	private class LoadDataTask extends AsyncTask<Params, Void, Result> {
		@Override
		protected Result doInBackground(Params... params) {
			Params p = params[0];
			Result result = new Result();
			result.op = p.op;
			try {// 模拟耗时
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result.list = getData();
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
			if (result.op == INITIALIZE) {
				mListData = result.list;
				query.clear();
				System.out.println("clear-------------清除内存");
				System.out.println("result2-------------");
			} else {
				System.out.println("result3-------------");
				mListData.addAll(result.list);
				Toast.makeText(MyShopCarActivity.this, "添加成功！",
						Toast.LENGTH_SHORT).show();
			}

			refreshListView();
		}

	}

	boolean isSelect = false;

	private class ListAdapter extends BaseAdapter implements
			OnItemClickListener {
		@Override
		public int getCount() {
			return mListData.size();
		}

		@Override
		public Object getItem(int position) {
			return mListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder = null;

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				try {

					holder = new ViewHolder();
					view = LayoutInflater.from(MyShopCarActivity.this).inflate(
							R.layout.cart_list_item, null);
					holder.checkBox = (CheckBox) view
							.findViewById(R.id.check_box);
					holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
					holder.image = (ImageView) view
							.findViewById(R.id.iv_adapter_list_pic);
					holder.content = (TextView) view
							.findViewById(R.id.tv_intro);
					holder.carNum = (TextView) view.findViewById(R.id.tv_num);
					holder.price = (TextView) view.findViewById(R.id.tv_price);
					holder.add = (TextView) view.findViewById(R.id.tv_add);
					holder.red = (TextView) view.findViewById(R.id.tv_reduce);
					holder.frontView = view.findViewById(R.id.item_left);
				} catch (Exception e) {

					e.printStackTrace();
				}
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			final DataBean data = mListData.get(position);
			bindListItem(holder, data);

			// mListData.get(position).setChoose(true);
			if (data != null) {
				// 判断是否选择
				if (data.isChoose()) {
					holder.checkBox.setChecked(true);
				} else {
					holder.checkBox.setChecked(false);
				}

				// 选中操作
				// holder.checkBox.setOnClickListener(new
				// CheckBoxOnClick(data));
				// 减少操作
				holder.red.setOnClickListener(new ReduceOnClick(data,
						holder.carNum));

				// 增加操作
				holder.add.setOnClickListener(new AddOnclick(data,
						holder.carNum));

			}
			return view;
		}

		class CheckBoxOnClick implements OnClickListener {
			DataBean shopcartEntity;

			public CheckBoxOnClick(DataBean shopcartEntity) {
				this.shopcartEntity = shopcartEntity;
			}

			@Override
			public void onClick(View view) {
				CheckBox cb = (CheckBox) view;
				if (cb.isChecked()) {
					shopcartEntity.setChoose(true);
					count();
				} else {
					shopcartEntity.setChoose(false);
				}
				select();

			}

		}

		private class AddOnclick implements OnClickListener {
			DataBean shopcartEntity;
			TextView shopcart_number_btn;

			private AddOnclick(DataBean shopcartEntity,
							   TextView shopcart_number_btn) {
				this.shopcartEntity = shopcartEntity;
				this.shopcart_number_btn = shopcart_number_btn;

			}

			@Override
			public void onClick(View arg0) {
				shopcartEntity.setChoose(true);
				String numberStr = shopcart_number_btn.getText().toString();
				if (!TextUtils.isEmpty(numberStr)) {
					int number = Integer.parseInt(numberStr);

					int currentNum = number + 1;
					// 设置列表
					shopcartEntity.setQuantity(currentNum);
					holder.carNum.setText("" + currentNum);
					int cart_id = shopcartEntity.getId();
					System.out.println("============cart_id=============="
							+ cart_id);
					AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/cart_goods_update?cart_id=" + cart_id
							+ "&user_id=" + user_id + "&quantity=" + currentNum
							+ "", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {

							System.out
									.println("==========================访问接口成功！"
											+ arg1);
							super.onSuccess(arg0, arg1);
						}

					}, MyShopCarActivity.this);
					notifyDataSetChanged();
				}
				count();
			}

		}

		private class ReduceOnClick implements OnClickListener {
			DataBean shopcartEntity;
			TextView shopcart_number_btn;

			private ReduceOnClick(DataBean shopcartEntity,
								  TextView shopcart_number_btn) {
				this.shopcartEntity = shopcartEntity;
				this.shopcart_number_btn = shopcart_number_btn;
			}

			@Override
			public void onClick(View arg0) {
				shopcartEntity.setChoose(true);
				String numberStr = shopcart_number_btn.getText().toString();
				if (!TextUtils.isEmpty(numberStr)) {
					int number = Integer.parseInt(numberStr);
					if (number == 1) {
						Toast.makeText(MyShopCarActivity.this, "不能往下减少了", 200)
								.show();
					} else {
						int currentNum = number - 1;
						// 设置列表
						shopcartEntity.setQuantity(currentNum);

						holder.carNum.setText("" + currentNum);
						int cart_id = shopcartEntity.getId();
						System.out.println("============cart_id=============="
								+ cart_id);
						AsyncHttp.get(RealmName.REALM_NAME_LL
										+ "/cart_goods_update?cart_id=" + cart_id
										+ "&user_id=" + user_id + "&quantity="
										+ currentNum + "",
								new AsyncHttpResponseHandler() {
									@Override
									public void onSuccess(int arg0, String arg1) {

										System.out
												.println("==========================2访问接口成功！"
														+ arg1);
										super.onSuccess(arg0, arg1);
									}

								}, MyShopCarActivity.this);
						notifyDataSetChanged();

					}

				}
				count();
			}

		}

		private void bindListItem(ViewHolder holder, DataBean data) {

			// holder.shopName.setText(data.getShopName());
			holder.content.setText(data.getTitle());
			holder.price.setText("￥" + data.getSell_price());
			holder.carNum.setText(data.getQuantity() + "");
			holder.tv_size.setText("￥" + data.getMarket_price());
			holder.tv_size.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

			query.id(holder.image).image(
					RealmName.REALM_NAME_HTTP + data.getImg_url());
			int _id = data.getId();
			boolean selected = mSelectState.get(_id, false);
			// System.out.println("selected-------------"+selected);
			holder.checkBox.setChecked(selected);

		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			DataBean bean = mListData.get(position);

			ViewHolder holder = (ViewHolder) view.getTag();
			int _id = (int) bean.getId();

			boolean selected = !mSelectState.get(_id, false);
			holder.checkBox.toggle();

			// 将CheckBox的选中状况记录下来
			mListData.get(position).setChoose(holder.checkBox.isChecked());
			// 调整选定条目
			if (holder.checkBox.isChecked() == true) {
				list_size.add(num);
				if (zhuangtai == true) {
					mDelete.setText(getResources()
							.getString(R.string.menu_sett)
							+ "("
							+ list_size.size() + ")");
				} else {
					mDelete.setText(getResources().getString(R.string.menu_del)
							+ "(" + list_size.size() + ")");
				}
				totalPrice += bean.getQuantity() * bean.getSell_price();
			} else {
				list_size.remove(num);
				if (zhuangtai == true) {
					mDelete.setText(getResources()
							.getString(R.string.menu_sett)
							+ "("
							+ list_size.size() + ")");
				} else {
					mDelete.setText(getResources().getString(R.string.menu_del)
							+ "(" + list_size.size() + ")");
				}
				mSelectState.delete(position);
				totalPrice -= bean.getQuantity() * bean.getSell_price();
			}
			BigDecimal c = new BigDecimal(totalPrice);
			dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			mPriceAll.setText("￥" + dzongjia + "");
			if (mSelectState.size() == mListData.size()) {
				mCheckAll.setChecked(true);
			} else {
				mCheckAll.setChecked(false);
			}

		}

	}

	class ViewHolder {
		CheckBox checkBox;

		ImageView image;
		TextView shopName;
		TextView content, tv_size;
		TextView carNum;
		TextView price;
		TextView add;
		TextView red;
		Button button; // 用于执行删除的button
		View frontView;
		LinearLayout item_right, item_left;

	}
	List<Integer> list_num;
	List<Integer> list_num2;
	int i;
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.subtitle:
				isBatchModel = !isBatchModel;
				if (isBatchModel) {
					mEdit.setText(getResources().getString(R.string.menu_enter));
					mDelete.setText(getResources().getString(R.string.menu_del));
					mBottonLayout.setVisibility(View.VISIBLE);
					mFavorite.setVisibility(View.GONE);
					BigDecimal c = new BigDecimal(totalPrice);
					dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					mPriceAll.setText("￥" + dzongjia + "");
					System.out.println("totalPrice1-------------" + totalPrice);
					mDelete.setText(getResources().getString(R.string.menu_del)
							+ "(" + list_size.size() + ")");
					zhuangtai = false;

				} else {
					mEdit.setText(getResources().getString(R.string.menu_edit));

					mFavorite.setVisibility(View.GONE);
					mBottonLayout.setVisibility(View.VISIBLE);
					mDelete.setText(getResources().getString(R.string.menu_sett));
					// totalPrice=0;
					BigDecimal c = new BigDecimal(totalPrice);
					dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					mPriceAll.setText("￥" + dzongjia + "");
					System.out.println("totalPrice2-------------" + totalPrice);
					mDelete.setText(getResources().getString(R.string.menu_sett)
							+ "(" + list_size.size() + ")");
					zhuangtai = true;
				}

				break;

			case R.id.check_box_all:
				totalPrice = 0;
				if (mCheckAll.isChecked()) {
					list_size.clear();
					for (int i = 0; i < mListData.size(); i++) {
						mListData.get(i).setChoose(true);
						// 如果为选中
						if (mListData.get(i).isChoose()) {
							totalPrice = totalPrice
									+ mListData.get(i).getQuantity()
									* mListData.get(i).getSell_price();
							list_size.add(num);
							if (zhuangtai == true) {
								mDelete.setText(getResources().getString(
										R.string.menu_sett)
										+ "(" + list_size.size() + ")");
							} else {
								mDelete.setText(getResources().getString(
										R.string.menu_del)
										+ "(" + list_size.size() + ")");
							}
						}
					}

					// 刷新
					mListAdapter.notifyDataSetChanged();
					// 显示
					BigDecimal c = new BigDecimal(totalPrice);
					dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					mPriceAll.setText("￥" + dzongjia + "");
				} else {
					for (int i = 0; i < mListData.size(); i++) {
						mListData.get(i).setChoose(false);
						list_size.clear();
						if (zhuangtai == true) {
							mDelete.setText(getResources().getString(
									R.string.menu_sett)
									+ "(" + list_size.size() + ")");
						} else {
							mDelete.setText(getResources().getString(
									R.string.menu_del)
									+ "(" + list_size.size() + ")");
						}
						// // 刷新
						mListAdapter.notifyDataSetChanged();
					}
					BigDecimal c = new BigDecimal(totalPrice);
					dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					mPriceAll.setText("￥" + dzongjia + "");
				}
				break;

			case R.id.tv_cart_buy_or_del:
				list_num = new ArrayList<Integer>();
				list_num2 = new ArrayList<Integer>();
				System.out.println("isBatchModel-------------" + isBatchModel);
				if (isBatchModel) {

					// Iterator it = mListData.iterator();
					// while (it.hasNext()) {
					// // 得到对应集合元素
					// DataBean g = (DataBean) it.next();
					// // 判断
					// if (g.isChoose()) {
					// // 从集合中删除上一次next方法返回的元素
					// it.remove();
					// }
					// }

					String str1 = "";
					for (i = 0; i < mListData.size(); i++) {
						if (mListData.get(i).isChoose()) {
							System.out.println("i=========================="+i);
							list_num.add(i);
							String strUrl = RealmName.REALM_NAME_LL
									+ "/cart_goods_delete?" + "clear=0&user_id="
									+ user_id + "&cart_id="
									+ mListData.get(i).getId();
							AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {

									System.out.println("==========================访问接口成功！"+ arg1);
									super.onSuccess(arg0, arg1);
									try {

										JSONObject object = new JSONObject(arg1);
										String status = object.getString("status");
										String info = object.getString("info");
										if (status.equals("y")) {
											Toast.makeText(MyShopCarActivity.this,info, 200).show();
											System.out.println("mListData.size()=========================="+ mListData.size());
											// ptye = true;
											list_num2.add(i);
											System.out.println("list_size.size()=========================="+ list_size.size());

											System.out.println("list_num.size()=========================="+list_num.size());
											System.out.println("list_num2.size()=========================="+list_num2.size());
											if (list_num.size() == list_num2.size()) {
												getgouwuche();
												list_num.clear();
												list_num2.clear();
											}
										} else {
											Toast.makeText(MyShopCarActivity.this,info, 200).show();
										}
									} catch (Exception e) {

										e.printStackTrace();
									}
								}

							}, MyShopCarActivity.this);

						}

					}

					// 刷新
					//				getgouwuche();

					// 计算个数清空
					if (list_size.size() > 0) {
						list_size.clear();
						mDelete.setText(getResources()
								.getString(R.string.menu_sett)
								+ "("
								+ list_size.size() + ")");
					}

					// String str1="";
					// for(int i=0;i<mListData.size();i++){
					// if (mListData.get(i).isChoose()) {
					// String fegefu = str1.length()>0?",":"";
					// str1 = str1+fegefu+String.valueOf(mListData.get(i).getId());
					// }
					// }
					// String strUrl = RealmName.REALM_NAME_LL +
					// "/cart_goods_delete?"+ "clear=0&user_id=" + user_id+
					// "&cart_id=" + str1;
					// AsyncHttp.get(strUrl,new AsyncHttpResponseHandler() {
					// @Override
					// public void onSuccess(int arg0, String arg1) {
					//
					// System.out.println("==========================访问接口成功！"+arg1);
					// super.onSuccess(arg0, arg1);
					// try {
					//
					// JSONObject object = new JSONObject(arg1);
					// String status = object.getString("status");
					// String info = object.getString("info");
					// if (status.equals("y")) {
					// Toast.makeText(MyShopCarActivity.this, info, 200).show();
					// // 刷新
					// getgouwuche();
					// // mListAdapter.notifyDataSetChanged();
					// }else {
					// Toast.makeText(MyShopCarActivity.this, info, 200).show();
					// }
					// } catch (Exception e) {
					//
					// e.printStackTrace();
					// }
					// }
					//
					// }, MyShopCarActivity.this);

				} else {
					String str1 = "";
					String str2 = "";
					String str3 = "";
					System.out.println("totalPrice-------------" + totalPrice);
					if (totalPrice != 0) {
						for (int i = 0; i < mListData.size(); i++) {
							if (mListData.get(i).isChoose()) {
								// str1 = "";//先清空
								// str2 = "";//先清空
								// str3 = "";//先清空
								String fegefu = str1.length() > 0 ? "," : "";
								str1 = str1
										+ fegefu
										+ String.valueOf(mListData.get(i)
										.getArticle_id());
								str2 = str2
										+ fegefu
										+ String.valueOf(mListData.get(i)
										.getGoods_id());
								str3 = str3
										+ fegefu
										+ String.valueOf(mListData.get(i)
										.getQuantity());

							}
						}
						System.out.println("str1-------------" + str1);
						String zhou = str1 + "/" + str2 + "/" + str3;
						// Toast.makeText(MyShopCarActivity.this, zhou, 200).show();
						if (str1.equals("")) {
							Toast.makeText(MyShopCarActivity.this, "请选择要支付的商品", 200)
									.show();
							// mListAdapter.notifyDataSetChanged();
						} else {
							loadgouwuche(str1, str2, str3);
						}

					} else {
						Toast.makeText(MyShopCarActivity.this, "请选择要支付的商品", 200)
								.show();
						mListAdapter.notifyDataSetChanged();
						return;
					}
				}

				break;
			case R.id.back:
				// onBackPressed();
				break;
			default:
				break;
		}
	}

	/**
	 * 计算价格
	 */
	public void count() {

		totalPrice = 0;// 人民币
		if (mListData != null && mListData.size() > 0) {
			for (int i = 0; i < mListData.size(); i++) {
				if (mListData.get(i).isChoose()) {

					totalPrice = totalPrice + mListData.get(i).getQuantity()
							* mListData.get(i).getSell_price();

				}
			}
			BigDecimal c = new BigDecimal(totalPrice);
			dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			mPriceAll.setText("￥" + dzongjia + "");
		}

	}

	public void select() {
		int count = 0;
		for (int i = 0; i < mListData.size(); i++) {
			if (mListData.get(i).isChoose()) {
				count++;
			}
		}
		if (count == mListData.size()) {
			mCheckAll.setChecked(true);
		} else {
			isSelect = true;
			mCheckAll.setChecked(false);
		}

	}

	private void loadgouwuche(String str1, String str2, String str3) {
		try {
			progress.CreateProgress();
			String login_sign = spPreferences.getString("login_sign", "");
			// AsyncHttp.get(RealmName.REALM_NAME_LL+
			// "/add_shopping_buys?user_id="+user_id+"&user_name="+user_name+
			// "&article_id="+str1+"&goods_id="+str2+"&quantity="+str3+"",
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/add_shopping_buys?user_id=" + user_id + "&user_name="
							+ user_name + "&user_sign=" + login_sign + "&article_id="
							+ str1 + "&goods_id=" + str2 + "&quantity=" + str3 + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {

							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								System.out.println("购物清单================"
										+ arg1);
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									JSONObject obj = jsonObject
											.getJSONObject("data");
									String buy_no = obj.getString("buy_no");
									String count = obj.getString("count");

									// JSONArray jsot =
									// jsonObject.getJSONArray("data");
									// ShopCartBean bean = new ShopCartBean();
									// for (int i = 0; i < jsot.length(); i++) {
									// JSONObject obj = jsot.getJSONObject(i);
									// bean.setId(obj.getString("id"));
									// String id = obj.getString("id");
									// list_id.add(id);
									// }
									// str = new StringBuffer();
									// for(String s:list_id){
									// str.append(s+",");
									// }
									// str.delete(str.lastIndexOf(","),str.length());
									// System.out.println("id拼接之后---------------"+str);

									// Toast.makeText(MyShopCarActivity.this, info,
									// 200).show();
									Intent intent = new Intent(
											MyShopCarActivity.this,
											MyOrderConfrimActivity.class);
									intent.putExtra("buy_no", buy_no);
									startActivity(intent);
								} else {
									Toast.makeText(MyShopCarActivity.this,
											info, 200).show();
								}
								progress.CloseProgress();
							} catch (JSONException e) {

								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							System.out
									.println("==========================访问接口失败！");
							System.out.println("========================="
									+ arg0);
							System.out.println("=========================="
									+ arg1);
							super.onFailure(arg0, arg1);
						}

					}, MyShopCarActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = (ListAdapter) mListView.getAdapter();
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
}
