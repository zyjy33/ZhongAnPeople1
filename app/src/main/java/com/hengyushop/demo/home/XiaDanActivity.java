package com.hengyushop.demo.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.pub.XiaDanListAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.GouWuCheActivity;
import com.hengyushop.demo.my.HaomaActivity;
import com.hengyushop.entity.ShopCartData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

/**
 * 下单
 * 
 * @author Administrator
 * 
 */
public class XiaDanActivity extends BaseActivity {

	public static List<ShopCartData> list = new ArrayList<ShopCartData>();
	public static List<jsondata> list_ll = new ArrayList<jsondata>();
	private DialogProgress progress;
	private ListView listView;
	private TextView tv_jiaguo;
	XinShouGongyeLieAdapter adapter;
	ShopCartData data;
	jsondata jsondata;
	int len;
	String content;
	private Button btn_add_shop_cart, btn_add_order;
	public static String user_name, user_id, mobile, login_sign;
	double di_hongbao = 0;
	public static Handler handler1;
	public static AQuery mAq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiadan_2);
		progress = new DialogProgress(XiaDanActivity.this);
		progress.CreateProgress();
		SharedPreferences spPreferences = getSharedPreferences("longuserset",
				MODE_PRIVATE);
		user_id = spPreferences.getString("user_id", "");
		user_name = spPreferences.getString("user", "");
		mobile = spPreferences.getString("mobile", "");
		login_sign = spPreferences.getString("login_sign", "");

		// initdata();

		handler1 = new Handler() {
			public void dispatchMessage(Message msg) {
				switch (msg.what) {
				case 0:
					finish();
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initdata();

		if (HaomaActivity.zhuangtai == true) {
			HaomaActivity.zhuangtai = false;
			System.out.println("====不加值=====================");

			// if (XiaDanActivity.list.size() > 0) {
			// XiaDanActivity.list.clear();
			// }
			// if (XiaDanActivity.list_ll.size() > 0) {
			// XiaDanActivity.list_ll.clear();
			// }
			// System.out.println("list_ll.size()============1========="+list_ll.size());
			// System.out.println("list.size()==============1==========="+list.size());

		} else {
			System.out.println("list_ll.size()====================="
					+ list_ll.size());
			System.out
					.println("list.size()=====================" + list.size());

			// String sp_sys = getIntent().getStringExtra("sp_sys");
			// System.out.println("sp_sys================"+sp_sys);

			System.out.println("CaptureActivity.ytpe================"
					+ CaptureActivity.ytpe);

			if (CaptureActivity.ytpe.equals("2")) {
				String content = CaptureActivity.bianma;
				System.out.println("content========11========" + content);
				CaptureActivity.zhuangtai = false;
				if (content != null) {
					load_list(content);
				}
			} else if (CaptureActivity.ytpe.equals("1")) {
				CaptureActivity.ytpe = "3";
				// content = "6920110106345";
				String content = getIntent().getStringExtra("bianma");
				System.out.println("content==========22======" + content);
				if (content != null) {
					load_list(content);
				}
			}
			// else {
			// content = "6920110106345";
			// System.out.println("content==========22======"+content);
			// if (content != null) {
			// load_list(content);
			// }
			// }

		}

	}

	/**
	 * 列表数据解析
	 * 
	 * @param content
	 */
	private void load_list(String content) {
		try {
			progress.CloseProgress();
			// list = new ArrayList<ShopCartData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/get_goods_content?goods_no=" + content + "",

			new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					System.out.println("=====================二级值11" + arg1);
					try {
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						String info = jsonObject.getString("info");
						if (status.equals("y")) {
							try {
								JSONObject jsonobt = jsonObject
										.getJSONObject("data");
								data = new ShopCartData();
								data.setId(jsonobt.getString("id"));
								data.setTitle(jsonobt.getString("title"));
								data.setImg_url(jsonobt.getString("img_url"));
								// data.quantity = jsonobt.getInt("quantity");
								String spec_item = jsonobt
										.getString("spec_item");
								JSONArray ja = new JSONArray(spec_item);
								for (int j = 0; j < ja.length(); j++) {
									JSONObject obct = ja.getJSONObject(j);
									data.goods_id = obct.getString("goods_id");
									data.article_id = obct
											.getString("article_id");
									data.market_price = obct
											.getString("market_price");
									data.sell_price = obct
											.getString("sell_price");
									jsondata = new jsondata();
									jsondata.setGoods_id(obct
											.getString("goods_id"));
									jsondata.setArticle_id(obct
											.getString("article_id"));

								}

								list.add(data);
								list_ll.add(jsondata);
								System.out
										.println("====11====================="
												+ list.size());

								CaptureActivity.bianma = null;
								// System.out.println("=====di_hongbao=========1============"+di_hongbao);
								// System.out.println("=====data.sell_price====================="+data.sell_price);
								// 金额
								BigDecimal e = new BigDecimal(data.sell_price);
								// 保留2位小数
								double total_c = e.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();
								di_hongbao += total_c;
								// System.out.println("=====di_hongbao==========2==========="+di_hongbao);
								String kedi_hongbao = String
										.valueOf(di_hongbao);
								// System.out.println("=====kedi_hongbao====================="+kedi_hongbao);
								tv_jiaguo.setText("合计:￥" + kedi_hongbao + "元");

								handler.sendEmptyMessage(0);

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else {
							progress.CloseProgress();
							Toast.makeText(XiaDanActivity.this, info, 200)
									.show();
						}

						System.out.println("=====22=====================");
						// Message msg = new Message();
						// msg.what = 0;
						// msg.obj = list;
						// handler.sendMessage(msg);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, XiaDanActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {

					System.out.println("=================5=" + list.size());
					XiaDanListAdapter adapter = new XiaDanListAdapter(list,
							XiaDanActivity.this, imageLoader);
					listView.setAdapter(adapter);
					progress.CloseProgress();

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				// listView.setOnItemClickListener(new OnItemClickListener() {
				//
				// @Override
				// public void onItemClick(AdapterView<?> arg0, View arg1,
				// int arg2, long arg3) {
				// // TODO Auto-generated method stub
				// try {
				//
				// System.out.println("=================1="
				// + list.size());
				// Intent intent = new Intent(
				// XiaDanActivity.this, Webview1.class);
				// intent.putExtra("list_xsgy", list.get(arg2).id);
				// startActivity(intent);
				//
				// } catch (Exception e) {
				// // TODO: handle exception
				// e.printStackTrace();
				// }
				// }
				// });
				break;

			default:
				break;
			}
		};
	};

	private void initdata() {
		listView = (ListView) findViewById(R.id.list_ware_collect);
		tv_jiaguo = (TextView) findViewById(R.id.tv_jiaguo);
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		btn_add_shop_cart = (Button) findViewById(R.id.btn_add_shop_cart);
		// btn_add_order = (Button) findViewById(R.id.btn_add_order);

		btn_add_shop_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("list_ll.size()============1========="
						+ list_ll.size());
				System.out.println("list.size()==============1==========="
						+ list.size());
				// String jsondata_zhi = JSON.toJSONString(list_ll);
				// load_dingdan_ll(XiaDanActivity.this,jsondata_zhi);
				Intent Intent2 = new Intent(XiaDanActivity.this,
						GouWuCheActivity.class);
				Intent2.putExtra("gouwuche", "加入购物车");
				startActivity(Intent2);
			}
		});

		// btn_add_order.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent Intent2 = new Intent(XiaDanActivity.this,HaomaActivity.class);
		// Intent2.putExtra("goods_id", data.goods_id);
		// Intent2.putExtra("article_id", data.article_id);
		// startActivity(Intent2);
		//
		// // Intent intent = new
		// Intent(XiaDanActivity.this,XiaDanActivity.class);
		// // ShopCartData bean= (ShopCartData)list;
		// // Bundle bundle = new Bundle();
		// // bundle.putSerializable("bean", bean);
		// // intent.putExtras(bundle);
		// // startActivity(intent);
		//
		// }
		// });

		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		TextView tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
		tv_xiabu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent Intent2 = new Intent(XiaDanActivity.this,
						CaptureActivity.class);
				// Intent2.putExtra("sp_sys", "2");
				startActivity(Intent2);
			}
		});

	}

	/**
	 * 
	 * 
	 * @param mContext
	 * @param jsonString2
	 * @param mobile
	 * @param pwd
	 * @param url
	 */
	// public void load_dingdan_ll(final Activity mContext, String jsonString2)
	// {
	//
	// HashMap<String, Object> params = new HashMap<String, Object>();
	// params.put("sale_id", user_id);
	// params.put("sale_name", user_name);
	// params.put("mobile", mobile);
	// // params.put("jsondata",
	// "[{article_id:"+6356+",goods_id:"+6325+",quantity:"+1+"}]");
	// params.put("jsondata", jsonString2);
	// params.put("sign", login_sign);
	//
	// mAq = new AQuery(mContext);
	//
	// String url = RealmName.REALM_NAME_LL+ "/order_goods_cart_save?";
	// // String url = RealmName.REALM_NAME_LL +
	// "/order_goods_save?sale_id="+user_id+"&sale_name="+user_name+"" +
	// // "&mobile="+mobile+"&jsondata="+article_id+"&sign="+login_sign+"";
	//
	// System.out.println("url---------------------"+url);
	// System.out.println("params---------------------"+params);
	// System.out.println("---------------------"+url+params);
	// mAq.ajax(url,params, JSONObject.class, new AjaxCallback<JSONObject>() {
	// @Override
	// public void callback(String url, JSONObject object, AjaxStatus status) {
	// Log.i("dzhi", " 取得的值-----" + object);
	// // System.out.println("===得到的数据结果是:" + object);
	// if (object != null) {
	// try {
	// String info = object.getString("info");
	// if (object.getString("status").equals("y")) {
	// // String data = object.optString("data");
	// // if (list.size() > 0) {
	// // list.clear();
	// // }
	// // if (list_ll.size() > 0) {
	// // list_ll.clear();
	// // }
	//
	// // finish();
	// Toast.makeText(XiaDanActivity.this,info, 200).show();
	// } else {
	// Toast.makeText(XiaDanActivity.this,info, 200).show();
	// }
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// } else {
	//
	// Toast.makeText(XiaDanActivity.this,"请求有异常！", 200).show();
	// }
	//
	//
	// super.callback(url, object, status);
	//
	// }
	// });
	// }

}
