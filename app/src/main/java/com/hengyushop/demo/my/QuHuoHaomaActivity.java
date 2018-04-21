package com.hengyushop.demo.my;

import java.util.ArrayList;
import java.util.List;

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
import com.hengyushop.demo.home.User;
import com.hengyushop.demo.home.UserGroup;
import com.hengyushop.demo.home.XiaDanActivity;
import com.hengyushop.demo.home.jsondata;
import com.hengyushop.entity.ShopCartData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.UserRegisterActivity;
import com.zijunlin.Zxing.Demo.CaptureActivity;

/**
 * 取货
 *
 * @author Administrator
 *
 */
public class QuHuoHaomaActivity extends BaseActivity {

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
	public static String user_name, user_id, mobile, login_sign;
	public static Handler handler1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quhuo_yanzheng);
		progress = new DialogProgress(QuHuoHaomaActivity.this);
		SharedPreferences spPreferences = getSharedPreferences("longuserset",
				MODE_PRIVATE);
		user_id = spPreferences.getString("user_id", "");
		user_name = spPreferences.getString("user", "");
		mobile = spPreferences.getString("mobile", "");
		login_sign = spPreferences.getString("login_sign", "");
		initdata();

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

	private void initdata() {
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		btn_add_shop_cart = (Button) findViewById(R.id.btn_add_shop_cart);
		et_username = (EditText) findViewById(R.id.et_user_name);

		btn_add_shop_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String haoma = et_username.getText().toString().trim();
				if (haoma.equals("")) {
					Toast.makeText(QuHuoHaomaActivity.this, "请输入您的验货码", 100)
							.show();
				} else {
					// String accept_no = "30150023";
					// load_dingdan(accept_no);

					// if(Validator.isMobile(haoma)){
					Intent Intent2 = new Intent(QuHuoHaomaActivity.this,
							QuHuoListActivity.class);
					Intent2.putExtra("yanhuo_haoma", haoma);
					startActivity(Intent2);
					// }else {
					// Toast.makeText(QuHuoHaomaActivity.this, "手机号码不正确",
					// 200).show();
					// }
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

	/**
	 * 列表数据解析
	 *
	 * @param jsonString2
	 * @param content
	 * @param goods_id
	 */
	private void load_dingdan(String accept_no) {
		try {
			progress.CloseProgress();
			list = new ArrayList<ShopCartData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/order_goods_accept?accept_no=" + accept_no + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println("=====================二级值11"
									+ arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									// try {
									// JSONObject jsonobt =
									// jsonObject.getJSONObject("data");
									// data = new ShopCartData();
									// data.setId(jsonobt.getString("id"));
									// data.setTitle(jsonobt.getString("title"));
									// data.setImg_url(jsonobt.getString("img_url"));
									// // data.quantity =
									// jsonobt.getInt("quantity");
									// String spec_item =
									// jsonobt.getString("spec_item");
									// JSONArray ja = new JSONArray(spec_item);
									// for (int j = 0; j < ja.length(); j++) {
									// JSONObject obct = ja.getJSONObject(j);
									// data.setMarket_price(obct.getString("market_price"));
									// data.setSell_price(obct.getString("sell_price"));
									// }
									// list.add(data);
									// System.out.println("====11=====================");
									//
									// } catch (Exception e) {
									// // TODO: handle exception
									// e.printStackTrace();
									// }
									progress.CloseProgress();
									Toast.makeText(QuHuoHaomaActivity.this,
											info, 200).show();
									Intent Intent2 = new Intent(
											QuHuoHaomaActivity.this,
											QuHuoListActivity.class);
									Intent2.putExtra("bianma", content);
									startActivity(Intent2);
									// finish();
								} else {
									progress.CloseProgress();
									Toast.makeText(QuHuoHaomaActivity.this,
											info, 200).show();
								}
								System.out
										.println("=====22=====================");

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, QuHuoHaomaActivity.this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
