package com.zams.www;

//package com.zams.www;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Fragment;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.hengyu.ui.MyPopupWindowMenu;
//import com.android.hengyu.web.DialogProgress;
//import com.android.hengyu.web.RealmName;
//import com.hengyushop.airplane.adapter.MyAdapter;
//import com.hengyushop.airplane.adapter.MyShopingCartllAdapter;
//import com.hengyushop.airplane.adapter.SybShopingCartllAdapter;
//import com.hengyushop.dao.WareDao;
//import com.hengyushop.demo.at.AsyncHttp;
//import com.hengyushop.entity.ShopCartData;
//import com.hengyushop.entity.UserRegisterData;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//
//public class SybShoppingCartActivity extends Fragment {
//	private ListView list_shop_cart;
//	private Button btn_sittle_account;
//	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
//			tv_endmoney, jf,tv_shanchu;
//	private LinearLayout list_shops, list_none;
//	private WareDao wareDao;
//	private SybShopingCartllAdapter adapter;
//	private MyAdapter madapter;
//	private ShopCartData dm;
//	private ShopCartData data;
//	private DialogProgress progress;
//	private String strUrl;
//	private String yth;
//	private MyPopupWindowMenu popupWindowMenu;
//	private EditText tv_amount_jf;
//	private UserRegisterData registerData;
//	private CheckBox in_jf,shopcart_item_check;
//	ArrayList<ShopCartData> list_ll;
//	static StringBuffer sb;
//	private static List list_id = new ArrayList();
//	int shopping_id;
//	String id;
//	private int ID;
//	private int checkNum; // 记录选中的条目数量
//	private List<ShopCartData> mListData = new ArrayList<ShopCartData>();// 数据
//	private static final int INITIALIZE = 0;
//
////    public static Handler handler;
//
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View layout = inflater.inflate(R.layout.shopping_cart, null);
//		progress = new DialogProgress(getActivity());
//
//		ininate(layout);
//		loadWeather();
//		setTotalCost();
//		loadData();
//		return layout;
//
//	}
//
//	//载入数据
//		private void loadData()
//		{
//			new LoadDataTask().execute(new Params(INITIALIZE));
//		}
//
//		class Params
//		{
//			int op;
//
//			public Params(int op)
//			{
//				this.op = op;
//			}
//
//		}
//
//		private class LoadDataTask extends AsyncTask<Params, Void, Result>
//		{
//			@Override
//			protected Result doInBackground(Params... params)
//			{
//				Params p = params[0];
//				Result result = new Result();
//				result.op = p.op;
//				try
//				{// 模拟耗时
//					Thread.sleep(500L);
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//				result.list = getData();
//				return result;
//			}
//
//			@Override
//			protected void onPostExecute(Result result)
//			{
//				super.onPostExecute(result);
//				if (result.op == INITIALIZE)
//				{
//					mListData = result.list;
//				} else
//				{
//					mListData.addAll(result.list);
////					Toast.makeText(Context, "添加成功！", Toast.LENGTH_SHORT).show();
//				}
//
//				refreshListView();
//			}
//
//		}
//
//		private void refreshListView()
//		{
//			if (adapter == null)
//			{
//				adapter = new SybShopingCartllAdapter();
//				mListView.setAdapter(adapter);
//				mListView.setOnItemClickListener(adapter);
//
//			} else
//			{
//				adapter.notifyDataSetChanged();
//
//			}
//		}
//
//		class Result
//		{
//			int op;
//			List<ShopCartData> list;
//		}
//	// 刷新listview和TextView的显示
//		private void dataChanged() {
//			// 通知listView刷新
//			adapter.notifyDataSetChanged();
//			// TextView显示最新的选中数目
//			btn_sittle_account.setText("已选中" + checkNum + "项");
//		};
//	public void setTotalCost(){
////		tv_shanchu.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View arg0) {
////				// TODO Auto-generated method stub
////				ID = Integer.parseInt(list_ll.get(arg2).getId());
////				dialog(ID);
////			}
////		});
//	}
//
//	/**
//	 * 初始化控件类别
//	 */
//	private void ininate(View layout) {
//		list_none = (LinearLayout)layout.findViewById(R.id.list_none);
//		list_shops = (LinearLayout)layout.findViewById(R.id.list_shops);
//		in_jf = (CheckBox)layout.findViewById(R.id.in_jf);
//		shopcart_item_check = (CheckBox)layout.findViewById(R.id.shopcart_item_check);
//		btn_sittle_account = (Button) layout.findViewById(R.id.btn_settle_accounts);
//		list_shop_cart = (ListView)layout.findViewById(R.id.list_shop_cart);
//		tv_endnumber = (TextView)layout.findViewById(R.id.tv_number);
//		tv_shanchu = (TextView) layout.findViewById(R.id.tv_shanchu);
//		tv_endmarketprice = (TextView)layout.findViewById(R.id.tv_original_price);
//		tv_preferential = (TextView)layout.findViewById(R.id.tv_preferential);
//		tv_endmoney = (TextView)layout.findViewById(R.id.tv_amount_payable);
//		tv_amount_jf = (EditText)layout.findViewById(R.id.tv_amount_jf);
//		jf = (TextView)layout.findViewById(R.id.jf);
//		list_shop_cart.setCacheColorHint(0);
//
//		btn_sittle_account.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				String total_cll = MyShopingCartllAdapter.total_c;
//				Intent intent = new Intent(getActivity(),MyOrderConfrimActivity.class);
////				String rsu = tv_amount_jf.getText().toString();
////				intent.putExtra("jf",rsu.length() == 0 ? "0": rsu);
//				intent.putExtra("total_cll", total_cll);
//				startActivity(intent);
//			}
//		});
//
//	}
//
//	Handler handler = new Handler() {
//		@SuppressWarnings("unchecked")
//		@Override
//		public void dispatchMessage(Message msg) {
//
//			switch (msg.what) {
//			case 0:
//				try {
//
//				System.out.println("3================"+list_ll.size());
//				adapter = new SybShopingCartllAdapter(list_ll, getActivity(), handler);
//				list_shop_cart.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
//				list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//				System.out.println("111111================"+list_id.size());
//				 if (list_ll.size() > 0) {
//						btn_sittle_account.setText("去结算(" + list_ll.size() + ")");
//					}else {
//						btn_sittle_account.setText("去结算");
//					}
//
//
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//				break;
//			case 200:
//				try {
//				String total_c = MyShopingCartllAdapter.total_c;
//				tv_endmoney.setText("￥" + total_c);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//				break;
//			case 2:
//				try {
//					final ArrayList<ShopCartData> carts = (ArrayList<ShopCartData>) msg.obj;
//					shopping_id = MyShopingCartllAdapter.id;
//					System.out.println("2222================"+shopping_id);
//					if (shopping_id>0) {
//						btn_sittle_account.setText("去结算(" + shopping_id + ")");
//					}else {
//						Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
//					}
//					} catch (Exception e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
//
//				break;
//			case 1:
//				try {
//				String total_cll = MyShopingCartllAdapter.total_c;
//				tv_endmoney.setText("￥" + total_cll);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//				break;
//			case 3:
//				try {
////				String[] orderid = (String[]) msg.obj;
////				System.out.println("数组值11=================="+orderid);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//				break;
////			case 400:
////				int position = (Integer) msg.obj;
////				int orderid = msg.arg1;
////				dialog(position, orderid);
////				break;
//			default:
//				break;
//			}
//			super.dispatchMessage(msg);
//		}
//	};
//
//	/**
//	 * 获取购物车列表数据
//	 */
//	private void loadWeather() {
//		list_ll = new ArrayList<ShopCartData>();
//		progress.CreateProgress();
////		String id = UserLoginActivity.id;
//
//		String user_id = MainFragment.user_id;
//		String user_idll = UserLoginActivity.user_id;
//		System.out.println("1==================" + user_id);
////		System.out.println("2==================" + name);
////		if (name != null) {
////			id = name;
////		} else
//		if (!user_id.equals("")) {
//			id = user_id;
//		}else if (user_idll != null){
//			id = user_idll;
//		}
//
//		System.out.println("结果呢1=============="+id);
////		if (id!=null) {
//
//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="+id+""
//				,new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0,String arg1) {
//						// TODO Auto-generated method stub
//						super.onSuccess(arg0, arg1);
//						try {
//							JSONObject jsonObject = new JSONObject(arg1);
//							System.out.println("1================"+arg1);
//							JSONArray jsot = jsonObject.getJSONArray("data");
//							if (jsot.length() > 0) {
//							data = new ShopCartData();
//							for (int i = 0; i < jsot.length(); i++) {
//								JSONObject object = jsot.getJSONObject(i);
//								dm = new ShopCartData();
//								data.title = object.getString("title");
//								data.market_price = object.getString("market_price");
//								data.sell_price = object.getString("sell_price");
//								data.id = object.getString("id");
//								data.quantity = object.getInt("quantity");
//								data.img_url = object.getString("img_url");
//
//								dm.setTitle(object.getString("title"));
//								dm.setMarket_price(object.getString("market_price"));
//								dm.setSell_price(object.getString("sell_price"));
//								dm.setId(object.getString("id"));
//								dm.setImg_url(object.getString("img_url"));
//								dm.setQuantity(object.getInt("quantity"));
//
//								String zhou = dm.getSell_price();
//								System.out.println("21================"+zhou);
//
//								list_ll.add(dm);
////								list_ll.add(data);
//							}
//							progress.CloseProgress();
//							System.out.println("2================"+list_ll.size());
//							handler.sendEmptyMessage(0);
//							}else {
//								progress.CloseProgress();
//								adapter = new SybShopingCartllAdapter(list_ll, getActivity(), handler);
//								list_shop_cart.setAdapter(adapter);
//								adapter.notifyDataSetChanged();
//								Toast.makeText(getActivity(), "购物车暂无数据", 200).show();
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//
//				}, null);
////		}else {
////			progress.CloseProgress();
////			Toast.makeText(getActivity(), "购物车暂无数据", 200).show();
////		}
//	}
//
//
//
//
// }
