package com.zams.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hengyu.pub.MyAddressManagerAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 *
 * 修改地址
 *
 * @author Administrator
 *
 */
public class AddressManagerGlActivity extends BaseActivity {

	private ListView list_address;
	private Button btn_add_address;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private UserRegisterData registerData;
	private DialogProgress progress;
	private MyPopupWindowMenu popupWindowMenu;
	private SharedPreferences spPreferences;
	MyAddressManagerAdapter adapter;
	private int ID;
	private ArrayList<UserAddressData> list = new ArrayList<UserAddressData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_manager);
		try {

			popupWindowMenu = new MyPopupWindowMenu(this);
			wareDao = new WareDao(getApplicationContext());
			progress = new DialogProgress(AddressManagerGlActivity.this);
			list_address = (ListView) findViewById(R.id.list_address);
			// list_address.setCacheColorHint(0);
			btn_add_address = (Button) findViewById(R.id.btn_add_address);
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			// registerData = wareDao.findIsLoginHengyuCode();
			// if (wareDao != null) {
			// yth = registerData.getHengyuCode().toString();
			// key = registerData.getUserkey().toString();
			// }
			// DOparse();

			// getuseraddress();

		} catch (Exception e) {

			e.printStackTrace();
		}

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		btn_add_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				int index = 1;
				Intent intent = new Intent(AddressManagerGlActivity.this,
						AddUserAddressActivity.class);
				intent.putExtra("index", index);
				startActivityForResult(intent, 0);
			}
		});

		list_address.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
										   int arg2, long arg3) {

				ID = list.get(arg2).id;
				dialog(ID);
				return true;
			}
		});
	}

	@Override
	protected void onResume() {

		super.onResume();
		getuseraddress();
	}

	/**
	 * 输出用户默认收货地址
	 */
	private void getuseraddress() {
		progress.CreateProgress();
		String user_name = spPreferences.getString("user", "");
		System.out.println("结果呢1==============" + user_name);
		AsyncHttp.get(RealmName.REALM_NAME_LL
						+ "/get_user_shopping_address?user_name=" + user_name + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("1================" + arg1);
							list = new ArrayList<UserAddressData>();
							String status = jsonObject.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsot = jsonArray
											.getJSONObject(i);
									UserAddressData data = new UserAddressData();
									data.user_accept_name = jsot
											.getString("user_accept_name");
									data.province = jsot.getString("province");
									data.city = jsot.getString("city");
									data.user_area = jsot.getString("area");
									data.user_mobile = jsot
											.getString("user_mobile");
									data.user_address = jsot
											.getString("user_address");
									data.id = jsot.getInt("id");
									list.add(data);
								}
								progress.CloseProgress();

								Message message = new Message();
								message.what = 0;
								message.obj = list;
								handler.sendMessage(message);
							} else {
								progress.CloseProgress();
								Message message = new Message();
								message.what = 0;
								message.obj = list;
								handler.sendMessage(message);

							}

						} catch (JSONException e) {

							progress.CloseProgress();
							e.printStackTrace();
						}
					}

				}, getApplicationContext());

	}

	Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					try {

						progress.CloseProgress();
						list = (ArrayList<UserAddressData>) msg.obj;
						adapter = new MyAddressManagerAdapter(
								AddressManagerGlActivity.this, list);
						list_address.setAdapter(adapter);
						adapter.notifyDataSetChanged();

						list_address
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(AdapterView<?> arg0,
															View arg1, int arg2, long arg3) {

										if (getIntent().hasExtra("order_confrim")) {
											// 表示是来自订单确认
											// Intent intent = new Intent();
											Intent intent = new Intent(
													AddressManagerGlActivity.this,
													EditUserAddressActivity.class);
											// intent.putExtra("data",
											// list.get(arg2));
											// setResult(100, intent);
											UserAddressData bean = list.get(arg2);
											Bundle bundle = new Bundle();
											bundle.putSerializable("bean", bean);
											intent.putExtras(bundle);
											startActivity(intent);

											// Intent intent = new
											// Intent(SpraypaintActivity.this,GoodscarActivity.class);
											// WashcarBean bean=
											// lists.get(position);
											// Bundle bundle = new Bundle();
											// bundle.putSerializable("bean", bean);
											// intent.putExtras(bundle);
											// startActivity(intent);
										}

									}

								});

					} catch (Exception e) {

						e.printStackTrace();
					}
					break;
				case 1:
					Toast.makeText(getApplicationContext(), (String) msg.obj, 200)
							.show();
					break;
				default:
					break;
			}
		};
	};

	protected void dialog(final int ID) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认删除该地址？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				String user_id = spPreferences.getString("user_id", "");
				System.out.println("1111====================" + user_id);
				String strUrl = RealmName.REALM_NAME_LL
						+ "/delete_user_shopping_address?user_id=" + user_id
						+ "&id=" + ID + "";
				Log.v("data1", "删除:" + strUrl);
				progress.CreateProgress();
				AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						System.out.println("====================" + arg1);
						progress.CloseProgress();
						super.onSuccess(arg0, arg1);

						// adapter.notifyDataSetChanged();
						getuseraddress();

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {

						super.onFailure(arg0, arg1);
						System.out.println("1====================" + arg0);
						System.out.println("2====================" + arg1);
					}
				}, null);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

}
