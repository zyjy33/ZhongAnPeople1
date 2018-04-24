package com.hengyushop.demo.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//import com.hengyushop.airplane.adapter.MyShopingCartllAdapter.ViewHolder;
/**\
 *
 * 云社群
 * @author Administrator
 *
 */
public class YunCommunityActivity extends Fragment {
	private ListView list_shop_cart;
	private Button btn_sittle_account;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf,tv_shanchu;
	private LinearLayout list_shops, list_none;
	private WareDao wareDao;
	private MyShopingCartllAdapter adapter;
	private MyAdapter madapter;
	private ShopCartData dm;
	private ShopCartData data;
	private DialogProgress progress;
	private String strUrl;
	private String yth;
	private MyPopupWindowMenu popupWindowMenu;
	private EditText tv_amount_jf;
	private UserRegisterData registerData;
	private CheckBox in_jf,shopcart_item_check;
	ArrayList<ShopCartData> list_ll;
	static StringBuffer sb;
	private static List list_id = new ArrayList();
	int shopping_id;
	private int ID;
	private int checkNum; // 记录选中的条目数量
	//    public static Handler handler;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.activity_yun_community, null);
		progress = new DialogProgress(getActivity());

		//		ininate(layout);
		//		loadWeather();
		//		setTotalCost();

		return layout;

	}

	// 刷新listview和TextView的显示
	private void dataChanged() {
		// 通知listView刷新
		adapter.notifyDataSetChanged();
		// TextView显示最新的选中数目
		btn_sittle_account.setText("已选中" + checkNum + "项");
	};
	public void setTotalCost(){
		//		tv_shanchu.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View arg0) {
		//
		//				ID = Integer.parseInt(list_ll.get(arg2).getId());
		//				dialog(ID);
		//			}
		//		});
	}

	/**
	 * 初始化控件类别
	 */
	private void ininate(View layout) {
		list_none = (LinearLayout)layout.findViewById(R.id.list_none);
		list_shops = (LinearLayout)layout.findViewById(R.id.list_shops);
		in_jf = (CheckBox)layout.findViewById(R.id.in_jf);
		shopcart_item_check = (CheckBox)layout.findViewById(R.id.shopcart_item_check);
		btn_sittle_account = (Button) layout.findViewById(R.id.btn_settle_accounts);
		list_shop_cart = (ListView)layout.findViewById(R.id.list_shop_cart);
		tv_endnumber = (TextView)layout.findViewById(R.id.tv_number);
		tv_shanchu = (TextView) layout.findViewById(R.id.tv_shanchu);
		tv_endmarketprice = (TextView)layout.findViewById(R.id.tv_original_price);
		tv_preferential = (TextView)layout.findViewById(R.id.tv_preferential);
		tv_endmoney = (TextView)layout.findViewById(R.id.tv_amount_payable);
		tv_amount_jf = (EditText)layout.findViewById(R.id.tv_amount_jf);
		jf = (TextView)layout.findViewById(R.id.jf);
		list_shop_cart.setCacheColorHint(0);

		//		 list_shop_cart.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		//			@Override
		//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		//					int arg2, long arg3) {
		//
		//				Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
		////				ID = Integer.parseInt(list_ll.get(arg2).getId());
		////				dialog(ID);
		//				return false;
		//			}
		//
		//		});

	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
				case 0:
					break;
				case 200:
					try {
						String total_c = MyShopingCartllAdapter.total_c;
						tv_endmoney.setText("￥" + total_c);
					} catch (Exception e) {

						e.printStackTrace();
					}
					break;
				case 2:
					try {
						final ArrayList<ShopCartData> carts = (ArrayList<ShopCartData>) msg.obj;
						shopping_id = MyShopingCartllAdapter.id;
						System.out.println("2222================"+shopping_id);
						if (shopping_id>0) {
							btn_sittle_account.setText("去结算(" + shopping_id + ")");
						}else {
							Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
						}
					} catch (Exception e) {

						e.printStackTrace();
					}

					break;
				case 1:
					try {
						String total_cll = MyShopingCartllAdapter.total_c;
						tv_endmoney.setText("￥" + total_cll);
					} catch (Exception e) {

						e.printStackTrace();
					}
					break;
				case 3:
					try {
						//				String[] orderid = (String[]) msg.obj;
						//				System.out.println("数组值11=================="+orderid);
					} catch (Exception e) {

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
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="+19+""
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("1================"+arg1);
							JSONArray jsot = jsonObject.getJSONArray("data");
							data = new ShopCartData();
							for (int i = 0; i < jsot.length(); i++) {
								JSONObject object = jsot.getJSONObject(i);
								dm = new ShopCartData();
								data.title = object.getString("title");
								data.market_price = object.getString("market_price");
								data.sell_price = object.getString("sell_price");
								data.id = object.getString("id");
								data.quantity = object.getInt("quantity");
								data.img_url = object.getString("img_url");

								dm.setTitle(object.getString("title"));
								dm.setMarket_price(object.getString("market_price"));
								dm.setSell_price(object.getString("sell_price"));
								dm.setId(object.getString("id"));
								dm.setImg_url(object.getString("img_url"));
								dm.setQuantity(object.getInt("quantity"));

								String zhou = dm.getSell_price();
								System.out.println("21================"+zhou);
								int geshu = object.getInt("sell_price");
								System.out.println("22================"+geshu);
								int sum = 0;
								sum +=geshu;
								//								for (int j = 0; j < geshu; j++) {
								//									sum +=geshu;
								//								}
								System.out.println("总额================"+sum);
								list_ll.add(dm);
								//								list_ll.add(data);
							}

							System.out.println("2================"+list_ll.size());
							String zhou = dm.getSell_price();
							//							tv_endmoney.setText("￥" + zhou);
							handler.sendEmptyMessage(0);
							progress.CloseProgress();
						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

				}, null);
	}


	protected void dialog(final int index, final int ID) {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("确认删除这个商品吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//				if (adapter != null) {
				////					adapter.deleteData(null, index);
				//				}
				//				if (UserLoginActivity.id != null) {
				strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?"
						+ "clear=0&user_id=" + 19
						+ "&cart_id=" + ID;
				AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(),getActivity());

				// 商品数量或者类型改变 刷新计数页面
				//					data = wareDao.findResult();
				//					Message message = new Message();
				//					message.what = 200;
				//					message.obj = data;
				//					handler.sendMessage(message);
				Toast.makeText(getActivity(), "删除成功", 200).show();
				dialog.dismiss();
				//					loadWeather();

				//				}else {
				//					Intent intent = new Intent(MyShoppingCartActivity.this, UserLoginActivity.class);
				//					startActivity(intent);
				//				}
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
