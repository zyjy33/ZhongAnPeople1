package com.hengyushop.demo.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ctrip.openapi.java.utils.Validator;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.XiaDanActivity;
import com.hengyushop.demo.home.jsondata;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.ShopCarts;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
/**
 * 加入购物车 、下单
 * @author Administrator
 *
 */
public class GouWuCheActivity extends BaseActivity {

	private ArrayList<ShopCartData> list;
	private DialogProgress progress;
	private ListView listView;
	private TextView tv_jiaguo;
	XinShouGongyeLieAdapter adapter;
	private EditText et_username;
	ShopCartData data;
	int len;
	public static AQuery mAq;
	String content;
	private Button btn_add_shop_cart;
	public static String user_name, user_id,mobile,login_sign;
	ArrayList<jsondata> users3;
	public static boolean zhuangtai = false;
	String haoma;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiadan_haoma);
		progress = new DialogProgress(GouWuCheActivity.this);
		SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_id = spPreferences.getString("user_id", "");
		user_name = spPreferences.getString("user", "");
//		mobile = spPreferences.getString("mobile", "");
		login_sign = spPreferences.getString("login_sign", "");
		zhuangtai = true;
		String gouwuche = getIntent().getStringExtra("gouwuche");
		System.out.println("gouwuche================"+gouwuche);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		if (gouwuche != null) {
			textView1.setText(gouwuche);
		}
		initdata();
	}
	
	
	private void initdata() {
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		btn_add_shop_cart = (Button) findViewById(R.id.btn_add_shop_cart);
		et_username = (EditText) findViewById(R.id.et_user_name);
		btn_add_shop_cart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				haoma = et_username.getText().toString().trim();
				if (haoma.equals("")) {
					Toast.makeText(GouWuCheActivity.this, "手机号码不能为空", 100).show();
				}else {
					
					if(Validator.isMobile(haoma)){
//					String article_id = getIntent().getStringExtra("article_id");
//					System.out.println("goods_id================"+goods_id);
			        
					ArrayList<jsondata>  users3 = new ArrayList<jsondata>();
			        System.out.println("users3================"+users3.size());
			        jsondata jsondata1 = new jsondata();
			        users3.add(jsondata1);
			        
			        
//			        ShopCartData bean = (ShopCartData) getIntent().getSerializableExtra("bean");
//					String zhou = bean.getTitle();
//					System.out.println("zhou================"+zhou);
			        
					List<jsondata> list_ll = XiaDanActivity.list_ll;
					System.out.println("list_ll.size()---------------"+list_ll.size());
			        String jsondata_zhi = JSON.toJSONString(list_ll);  
			        
//			        String jsondata_zhi = getIntent().getStringExtra("jsondata_zhi");
//			        System.out.println("jsondata_zhi---------------"+jsondata_zhi);
			        
			        
			        load_dingdan_ll(GouWuCheActivity.this,jsondata_zhi);
//			        load_dingdan(jsondata_zhi);
//			        loadCart(jsondata_zhi);
					}else {
						Toast.makeText(GouWuCheActivity.this, "手机号码不正确", 200).show();
					}
				}
			}
		});
	
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
        
	
//	private void loadCart(String jsonString2) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("sale_id", user_id);
//		params.put("sale_name", user_name);
//		params.put("mobile", haoma);
//		params.put("jsondata", jsonString2);
//		params.put("sign", login_sign);
//		
//		AsyncHttp.post_1(RealmName.REALM_NAME_LL+ "/order_goods_save", params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//						super.onSuccess(arg0, arg1);
//						try {
//							JSONObject jsonObject = new JSONObject(arg1);
//							String status = jsonObject.getString("status");
//							String info = jsonObject.getString("info");
//							if (status.equals("y")) {
//								Toast.makeText(GouWuCheActivity.this,info, 200).show();
//								if (XiaDanActivity.list.size() > 0) {
//									XiaDanActivity.list.clear();
//						 	    }
//								
//								if (XiaDanActivity.list_ll.size() > 0) {
//									XiaDanActivity.list_ll.clear();
//						 	    }
//								btn_add_shop_cart.setVisibility(View.GONE);
//								
//								GouWuCheActivity.zhuangtai = false;
//								System.out.println("url------1---------------");
//								finish();
//								XiaDanActivity.handler1.sendEmptyMessage(0);
//							} else {
//								Toast.makeText(GouWuCheActivity.this,info, 200).show();
//							}
//							
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//				});
//	}
	
	/**
	 * 
	 * 
	 * @param mContext
	 * @param jsonString2 
	 * @param mobile
	 * @param pwd
	 * @param url
	 */
	public void load_dingdan_ll(final Activity mContext, String jsonString2) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sale_id", user_id);
		params.put("sale_name", user_name);
		params.put("mobile", haoma);
//		params.put("jsondata", "[{article_id:"+6356+",goods_id:"+6325+",quantity:"+1+"}]");
		params.put("jsondata", jsonString2);
		params.put("sign", login_sign);
		
		mAq = new AQuery(mContext);
		
		String url = RealmName.REALM_NAME_LL+ "/order_goods_cart_save?";
		
		String url_ll =  RealmName.REALM_NAME_LL + "/order_goods_cart_save?sale_id="+user_id+"&sale_name="+user_name+"" +
		"&mobile="+haoma+"&jsondata="+jsonString2+"&sign="+login_sign+"";
		System.out.println("url_ll---------------------"+url_ll);
		
		System.out.println("url---------------------"+url);
		System.out.println("params---------------------"+params);
		mAq.ajax(url,params, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				Log.i("dzhi", " 取得的值-----" + object);
//				System.out.println("===得到的数据结果是:" + object);
				if (object != null) {
					try {
						String info = object.getString("info");
					if (object.getString("status").equals("y")) {
//					    String data = object.optString("data");
						Toast.makeText(GouWuCheActivity.this,info, 200).show();
						
						if (XiaDanActivity.list.size() > 0) {
							XiaDanActivity.list.clear();
				 	    }
						
						if (XiaDanActivity.list_ll.size() > 0) {
							XiaDanActivity.list_ll.clear();
				 	    }
						btn_add_shop_cart.setVisibility(View.GONE);
						
						GouWuCheActivity.zhuangtai = false;
						System.out.println("url------1---------------");
						finish();
						XiaDanActivity.handler1.sendEmptyMessage(0);
					} else {
						Toast.makeText(GouWuCheActivity.this,info, 200).show();
					}
					
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					
					Toast.makeText(GouWuCheActivity.this,"请求有异常！", 200).show();
				}
				
				
				super.callback(url, object, status);

			}
		});
	}
	
	
	/**
	 * 下单
	 * @param jsonString2 
	 * @param content 
	 * @param goods_id 
	 */
//	private void load_dingdan(String jsonString2){
//		try {
//			progress.CloseProgress();
//		list = new ArrayList<ShopCartData>();
//		AsyncHttp.get(RealmName.REALM_NAME_LL + "/order_goods_save?sale_id="+user_id+"&sale_name="+user_name+"" +
//				"&mobile="+haoma+"&jsondata="+jsonString2+"&sign="+login_sign+"",
//				//[{article_id:"+article_id+",goods_id:"+goods_id+",quantity:"+1+"}]
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//						super.onSuccess(arg0, arg1);
//						System.out.println("=====================二级值11" + arg1);
//						try {
//							JSONObject jsonObject = new JSONObject(arg1);
//							String status = jsonObject.getString("status");
//							String info = jsonObject.getString("info");
//							if (status.equals("y")) {
////								try {
//								JSONObject jsonobt = jsonObject.getJSONObject("data");
////								data = new ShopCartData();
////								data.setId(jsonobt.getString("id"));
////								data.setTitle(jsonobt.getString("title"));
////								data.setImg_url(jsonobt.getString("img_url"));
////								// data.quantity = jsonobt.getInt("quantity");
//								
////								String spec_item = jsonobt.getString("spec_item");
////								JSONArray ja = new JSONArray(spec_item);
////								for (int j = 0; j < ja.length(); j++) {
////									JSONObject obct = ja.getJSONObject(j);
////									data.setMarket_price(obct.getString("market_price"));
////									data.setSell_price(obct.getString("sell_price"));
////								}
////								list.add(data);
////								System.out.println("====11=====================");
////								
////								} catch (Exception e) {
////									// TODO: handle exception
////									e.printStackTrace();
////								}
//								progress.CloseProgress();
//								Toast.makeText(GouWuCheActivity.this,info, 200).show();
////								finish();
//								if (XiaDanActivity.list.size() > 0) {
//									XiaDanActivity.list.clear();
//						 	    }
//								
//								if (XiaDanActivity.list_ll.size() > 0) {
//									XiaDanActivity.list_ll.clear();
//						 	    }
//								btn_add_shop_cart.setVisibility(View.GONE);
//								
//								GouWuCheActivity.zhuangtai = false;
//								System.out.println("url------1---------------");
//								finish();
//								XiaDanActivity.handler1.sendEmptyMessage(0);
//							} else {
//								progress.CloseProgress();
//								Toast.makeText(GouWuCheActivity.this,info, 200).show();
//							}
//							System.out.println("=====22=====================");
//							
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}, GouWuCheActivity.this);
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}

}
