package com.zams.www;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyAdapter;
import com.hengyushop.airplane.adapter.MyShopingCartllAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.ShopCartBean;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;

//import com.hengyushop.airplane.adapter.MyShopingCartllAdapter.ViewHolder;
/**
 * 云商聚购物车
 *
 * @author Administrator
 *
 */
public class MyShoppingCartllActivity extends BaseActivity {
	private ListView list_shop_cart;
	private Button btn_sittle_account;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf, tv_shanchu;
	private LinearLayout list_shops, list_none;
	private WareDao wareDao;
	private MyShopingCartllAdapter adapter;
	private MyAdapter madapter;
	private ShopCartData dm;
	private ShopCartData data;
	private ShopCartBean bean;
	private DialogProgress progress;
	private String strUrl;
	private String yth;
	private MyPopupWindowMenu popupWindowMenu;
	private EditText tv_amount_jf;
	private UserRegisterData registerData;
	private CheckBox in_jf, shopcart_item_check;
	ArrayList<ShopCartData> list_ll = new ArrayList<ShopCartData>();
	static StringBuffer sb;
	public static StringBuffer str, str1, str2, str3;
	int shopping_id;
	String id;
	private int ID;
	private int checkNum; // 记录选中的条目数量
	ArrayList<ShopCartBean> list;
	private SharedPreferences spPreferences;
	private static String user_name, user_id;
	private static List<String> list_id = new ArrayList<String>();
	public static String total_cll;
	public static List<String> list_cart_id = new ArrayList<String>();
	public static List<String> list_goods_id = new ArrayList<String>();
	public static List<Integer> list_quantity = new ArrayList<Integer>();
	private LinearLayout adv_pager;
	private Button btn_register;

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// View layout = inflater.inflate(R.layout.shopping_cart, null);
	// progress = new DialogProgress(MyShoppingCartllActivity.this);
	// spPreferences =
	// MyShoppingCartllActivity.this.getSharedPreferences("longuserset",
	// Context.MODE_PRIVATE);
	// user_name = spPreferences.getString("user_name", "");
	// user_id = spPreferences.getString("user_id", "");
	// ininate(layout);
	// return layout;
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
		progress = new DialogProgress(MyShoppingCartllActivity.this);
		spPreferences = MyShoppingCartllActivity.this.getSharedPreferences(
				"longuserset", Context.MODE_PRIVATE);
		user_name = spPreferences.getString("user_name", "");
		user_id = spPreferences.getString("user_id", "");
		ininate();

		// loadWeather();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("2================");
		ShoppingCartActivity.str = null;
		// List<String> list_cart_id = MyShopingCartllAdapter.list_cart_id;
		// List<String> list_goods_id = MyShopingCartllAdapter.list_goods_id;
		// List<Integer> list_quantity = MyShopingCartllAdapter.list_quantity;
		System.out
				.println("list_cart_id================" + list_cart_id.size());
		if (list_cart_id.size() > 0) {
			list_cart_id.clear();
			list_goods_id.clear();
			list_quantity.clear();
			list_id.clear();
			// list_ll.clear();
			loadWeather();
			System.out.println("list_cart_id2================"
					+ list_cart_id.size());
		} else {
			list_id.clear();
			// list_ll.clear();
			loadWeather();
		}
	}

	/**
	 * 初始化控件类别
	 */
	private void ininate() {
		adv_pager = (LinearLayout) findViewById(R.id.adv_pager);
		list_none = (LinearLayout) findViewById(R.id.list_none);
		list_shops = (LinearLayout) findViewById(R.id.list_shops);
		in_jf = (CheckBox) findViewById(R.id.in_jf);
		shopcart_item_check = (CheckBox) findViewById(R.id.shopcart_item_check);
		btn_sittle_account = (Button) findViewById(R.id.btn_settle_accounts);
		list_shop_cart = (ListView) findViewById(R.id.list_shop_cart);
		tv_endnumber = (TextView) findViewById(R.id.tv_number);
		tv_shanchu = (TextView) findViewById(R.id.tv_shanchu);
		tv_endmarketprice = (TextView) findViewById(R.id.tv_original_price);
		tv_preferential = (TextView) findViewById(R.id.tv_preferential);
		tv_endmoney = (TextView) findViewById(R.id.tv_amount_payable);
		tv_amount_jf = (EditText) findViewById(R.id.tv_amount_jf);
		jf = (TextView) findViewById(R.id.jf);
		list_shop_cart.setCacheColorHint(0);
		btn_register = (Button) findViewById(R.id.btn_register);

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setVisibility(View.VISIBLE);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 购物车无商品去逛逛
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				user_id = spPreferences.getString("user_id", "");
				if (user_id.equals("")) {
					Intent intentll = new Intent(MyShoppingCartllActivity.this,
							UserLoginActivity.class);
					startActivity(intentll);
				} else {
					Intent intentll = new Intent(MyShoppingCartllActivity.this,
							NewWare.class);
					startActivity(intentll);
				}
			}
		});

		btn_sittle_account.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// String total_cll = MyShopingCartllAdapter.total_c;
				// Intent intent = new
				// Intent(getActivity(),MyOrderConfrimActivity.class);
				// intent.putExtra("total_cll", total_cll);
				// startActivity(intent);
				// if (list_cart_id.size() > 0) {
				loadgouwuche();
				// }else {
				// Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
				// }
			}
		});

	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
				case 0:
					try {

						System.out.println("3================" + list_ll.size());
						adapter = new MyShopingCartllAdapter(list_ll,
								MyShoppingCartllActivity.this, handler);
						list_shop_cart.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
						if (list_ll.size() > 0) {
							btn_sittle_account.setText("去结算(" + list_ll.size()
									+ ")");
						} else {
							btn_sittle_account.setText("去结算");
						}

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 200:
					try {
						String total_c = MyShopingCartllAdapter.total_c;
						tv_endmoney.setText("￥" + total_c);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						final ArrayList<ShopCartData> carts = (ArrayList<ShopCartData>) msg.obj;
						shopping_id = MyShopingCartllAdapter.id;
						System.out.println("2222================" + shopping_id);
						if (shopping_id > 0) {
							btn_sittle_account.setText("去结算(" + shopping_id + ")");
						} else {
							Toast.makeText(MyShoppingCartllActivity.this,
									"请勾选要下单的商品", 200).show();
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					break;
				case 1:
					try {
						total_cll = MyShopingCartllAdapter.total_c;
						tv_endmoney.setText("￥" + total_cll);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						// String[] orderid = (String[]) msg.obj;
						// System.out.println("数组值11=================="+orderid);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 400:
					int position = (Integer) msg.obj;
					int orderid = msg.arg1;
					dialog(position, orderid);
					break;
				default:
					break;
			}
			super.dispatchMessage(msg);
		}
	};

	/**
	 * 获取购物车列表数据
	 */
	private void loadWeather() {
		list_ll = new ArrayList<ShopCartData>();
		progress.CreateProgress();
		String user_id = spPreferences.getString("user_id", "");
		System.out.println("1==================" + user_id);
		if (!user_id.equals("")) {
			System.out.println("结果呢1==============" + id);

			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="
					+ user_id + "", new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					try {
						JSONObject jsonObject = new JSONObject(arg1);
						System.out.println("1================" + arg1);
						JSONArray jsot = jsonObject.getJSONArray("data");
						if (jsot.length() > 0) {
							data = new ShopCartData();
							for (int i = 0; i < jsot.length(); i++) {
								JSONObject object = jsot.getJSONObject(i);
								dm = new ShopCartData();
								data.title = object.getString("title");
								data.market_price = object
										.getString("market_price");
								data.sell_price = object
										.getString("sell_price");
								data.id = object.getString("id");
								data.quantity = object.getInt("quantity");
								data.img_url = object.getString("img_url");

								dm.setTitle(object.getString("title"));
								dm.setMarket_price(object
										.getString("market_price"));
								dm.setSell_price(object.getString("sell_price"));
								dm.setId(object.getString("id"));
								dm.setImg_url(object.getString("img_url"));
								dm.setQuantity(object.getInt("quantity"));
								dm.setArticle_id(object.getString("article_id"));
								dm.setGoods_id(object.getString("goods_id"));

								String zhou = dm.getSell_price();
								System.out.println("21================" + zhou);

								list_ll.add(dm);
								// list_ll.add(data);
							}
							progress.CloseProgress();
							handler.sendEmptyMessage(0);
							adv_pager.setVisibility(View.GONE);
							// Toast.makeText(getActivity(), "购物车商品",
							// 200).show();
						} else {
							progress.CloseProgress();
							adapter = new MyShopingCartllAdapter(list_ll,
									MyShoppingCartllActivity.this, handler);
							list_shop_cart.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							adv_pager.setVisibility(View.VISIBLE);
							// Toast.makeText(getActivity(), "购物车暂无商品",
							// 200).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}, null);

		} else {
			progress.CloseProgress();
			adv_pager.setVisibility(View.VISIBLE);
			// Toast.makeText(getActivity(), "请先登录", 200).show();
		}
	}

	private void loadgouwuche() {
		try {
			progress.CreateProgress();
			list = new ArrayList<ShopCartBean>();
			// str1 = MyShopingCartllAdapter.str1;
			// str2 = MyShopingCartllAdapter.str2;
			// str3 = MyShopingCartllAdapter.str3;
			for (int i = 0; i < list_ll.size(); i++) {
				String cart_id = list_ll.get(i).getArticle_id();
				String goods_id = list_ll.get(i).getGoods_id();
				int quantity = list_ll.get(i).getQuantity();

				list_cart_id.add(cart_id);
				list_goods_id.add(goods_id);
				list_quantity.add(quantity);
			}

			// article_id 拼接
			str1 = new StringBuffer();
			for (String s : list_cart_id) {
				str1.append(s + ",");
			}
			str1.delete(str1.lastIndexOf(","), str1.length());
			System.out.println("1拼接之后---------------" + str1);

			// goods_id 拼接
			str2 = new StringBuffer();
			for (String s : list_goods_id) {
				str2.append(s + ",");
			}
			str2.delete(str2.lastIndexOf(","), str2.length());

			System.out.println("2拼接之后---------------" + str2);

			// quantity 拼接
			str3 = new StringBuffer();
			for (int s : list_quantity) {
				str3.append(s + ",");
			}
			str3.delete(str3.lastIndexOf(","), str3.length());

			System.out.println("3拼接之后---------------" + str3);
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/add_shopping_buys?user_id=" + user_id + "&user_name="
					+ user_name + "&article_id=" + str1 + "&goods_id=" + str2
					+ "&quantity=" + str3 + "", new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					try {
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						System.out.println("购物清单================" + arg1);
						String info = jsonObject.getString("info");
						// status1 = true;
						if (status.equals("y")) {
							progress.CloseProgress();
							JSONArray jsot = jsonObject.getJSONArray("data");
							bean = new ShopCartBean();
							for (int i = 0; i < jsot.length(); i++) {
								JSONObject obj = jsot.getJSONObject(i);
								bean.setId(obj.getString("id"));
								bean.id = obj.getString("id");
								String id = obj.getString("id");
								list.add(bean);
								list_id.add(id);
							}
							str = new StringBuffer();
							for (String s : list_id) {
								str.append(s + ",");
							}
							str.delete(str.lastIndexOf(","), str.length());
							System.out.println("id拼接之后---------------" + str);

							// Toast.makeText(getActivity(), info, 200).show();
							System.out.println("拼接之后total_cll---------------"
									+ total_cll);
							Intent intent = new Intent(
									MyShoppingCartllActivity.this,
									MyOrderConfrimActivity.class);
							intent.putExtra("total_cll", total_cll);
							startActivity(intent);
							// finish();
						} else {
							progress.CloseProgress();
							Toast.makeText(MyShoppingCartllActivity.this, info,
									200).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					System.out.println("==========================访问接口失败！");
					System.out.println("=========================" + arg0);
					System.out.println("==========================" + arg1);
					super.onFailure(arg0, arg1);
				}

			}, MyShoppingCartllActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	protected void dialog(final int index, final int ID) {
		AlertDialog.Builder builder = new Builder(MyShoppingCartllActivity.this);
		builder.setMessage("确认删除这个商品吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// if (adapter != null) {
				// // adapter.deleteData(null, index);
				// }
				// if (UserLoginActivity.id != null) {
				strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?"
						+ "clear=0&user_id=" + 19 + "&cart_id=" + ID;
				AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(),
						MyShoppingCartllActivity.this);

				// 商品数量或者类型改变 刷新计数页面
				// data = wareDao.findResult();
				// Message message = new Message();
				// message.what = 200;
				// message.obj = data;
				// handler.sendMessage(message);
				Toast.makeText(MyShoppingCartllActivity.this, "删除成功", 200)
						.show();
				// List<String> list_cart_id =
				// MyShopingCartllAdapter.list_cart_id;
				// List<String> list_goods_id =
				// MyShopingCartllAdapter.list_goods_id;
				// List<Integer> list_quantity =
				// MyShopingCartllAdapter.list_quantity;
				System.out.println("list_cart_id================"
						+ list_cart_id.size());
				if (list_cart_id.size() > 0) {
					list_cart_id.clear();
					list_goods_id.clear();
					list_quantity.clear();
				}

				dialog.dismiss();
				// adapter.notifyDataSetChanged();
				loadWeather();

				// }else {
				// Intent intent = new Intent(MyShoppingCartActivity.this,
				// UserLoginActivity.class);
				// startActivity(intent);
				// }
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
