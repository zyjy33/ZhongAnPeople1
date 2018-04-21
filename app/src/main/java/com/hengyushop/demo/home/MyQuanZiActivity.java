package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyJuFenMxAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.MyJuFenData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

/**
 *
 * 我的粉丝
 *
 * @author Administrator
 *
 */
public class MyQuanZiActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private LinearLayout index_item0, index_item1, ll_buju1, index_item3;
	private SharedPreferences spPreferences;
	private TextView tv_djjifen_ticket, tv_ticket, tv_quanzi, tv_quanzi_dan;
	private ArrayList<MyAssetsBean> list_lb;
	private ArrayList<MyJuFenData> list = null;
	private List<MyJuFenData> list_2;
	String user_name, user_id;
	int len;
	String fund_id = "0";
	private ListView new_list;
	GuigeData md;
	GuigeBean mb;
	GuigeBean data_ll;
	MyAssetsBean data;
	ArrayList<GuigeBean> list_l;
	private DialogProgress progress;
	ArrayList<GuigeData> list_ll = new ArrayList<GuigeData>();
	MyJuFenMxAdapter adapter;
	String img_url = "";
	String user_group_id = "13";
	private static final String TAG = "ActivityDemo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_quanzi);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(this);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		Initialize();
		load_list();
	}

	// @Override
	// protected void onStart() {
	// super.onStart();
	// Log.e(TAG, "start onStart~~~");
	// }
	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// Log.e(TAG, "start onRestart~~~");
	// }
	// @Override
	// protected void onResume() {
	// super.onResume();
	// Log.e(TAG, "start onResume~~~");
	// }
	// @Override
	// protected void onPause() {
	// super.onPause();
	// Log.e(TAG, "start onPause~~~");
	// }
	// @Override
	// protected void onStop() {
	// super.onStop();
	// Log.e(TAG, "start onStop~~~");
	// }
	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// Log.e(TAG, "start onDestroy~~~");
	// }

	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
			tv_ticket = (TextView) findViewById(R.id.tv_ticket);
			tv_quanzi = (TextView) findViewById(R.id.tv_quanzi);
			tv_quanzi_dan = (TextView) findViewById(R.id.tv_quanzi_dan);
			// tv_jifen_ticket = (TextView) findViewById(R.id.tv_jifen_ticket);
			tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);

			new_list = (ListView) findViewById(R.id.new_list);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			ll_buju1 = (LinearLayout) findViewById(R.id.ll_buju1);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.index_item0:
				cursor1.setVisibility(View.VISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				// tv_quanzi.setText("个数");
				// tv_quanzi_dan.setText("名单");
				user_group_id = "13";// 普通会员
				load_list();
				break;
			case R.id.index_item1:
				try {

					cursor1.setVisibility(View.INVISIBLE);
					cursor2.setVisibility(View.VISIBLE);
					// tv_quanzi.setText("个数");
					// tv_quanzi_dan.setText("名单");
					user_group_id = "12";// 价值会员
					load_list2();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;

			default:
				break;
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					try {

						System.out.println("-------------------------"
								+ list.size());
						if (list.size() > 0) {
							String num = String.valueOf(list.size());
							tv_ticket.setText(num + "个");
						} else {
							tv_ticket.setText("0个");
						}

						adapter = new MyJuFenMxAdapter(list, list_avatar1,
								MyQuanZiActivity.this, imageLoader);
						new_list.setAdapter(adapter);
						progress.CloseProgress();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;
				case 1:
					try {
						System.out.println("-------------------------"
								+ list_2.size());
						if (list_2.size() > 0) {
							String num = String.valueOf(list_2.size());
							tv_ticket.setText(num + "个");
						} else {
							tv_ticket.setText("0个");
						}

						adapter = new MyJuFenMxAdapter(list_2, list_avatar2,
								MyQuanZiActivity.this, imageLoader);
						new_list.setAdapter(adapter);
						// MyQuanZiAdapter adapter = new
						// MyQuanZiAdapter(MyQuanZiActivity.this,list_2);
						// new_list.setAdapter(adapter);
						progress.CloseProgress();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					break;

				default:
					break;
			}
		};
	};

	/**
	 * 第1个列表数据解析
	 */
	ArrayList list_avatar1;

	private void load_list() {
		progress.CreateProgress();
		list_avatar1 = new ArrayList();
		list = new ArrayList<MyJuFenData>();

		AsyncHttp.get(RealmName.REALM_NAME_LL
						+ "/get_user_child_list_2017?user_id=" + user_id
						+ "&user_name=" + user_name + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1" + arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								int len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject object = jsonArray
											.getJSONObject(i);
									MyJuFenData data = new MyJuFenData();
									data.mobile = object.getString("mobile");
									data.login_sign = object
											.getString("login_sign");
									data.avatar = object.getString("avatar");
									data.real_name = object
											.getString("real_name");
									// data.user_name =
									// object.getString("user_name");
									data.nick_name = object
											.getString("nick_name");
									data.reg_time = object.getString("reg_time");
									String group_id = object
											.getString("group_id");
									String avatar = data.avatar;//
									System.out
											.println("二级值1====================="
													+ avatar);

									System.out
											.println("user_group_id==============1=================="
													+ user_group_id);
									if (group_id.equals(user_group_id)) {
										list.add(data);

										if (avatar.contains("http")) {
											System.out
													.println("================================http");
											list_avatar1.add(avatar);
										} else if (avatar.contains("upload")) {
											System.out
													.println("================================upload");
											String img_url = RealmName.REALM_NAME_HTTP
													+ avatar;
											list_avatar1.add(img_url);
										} else {
											System.out
													.println("================================空值");
											list_avatar1.add(avatar);
										}
										System.out
												.println("list_avatar1.size()===========1====================="
														+ list_avatar1.size());
									}

								}
								System.out
										.println("list.size()================1================"
												+ list.size());
								System.out
										.println("list_avatar.size()================================"
												+ list_avatar1.size());

								new_list.setVisibility(View.VISIBLE);
								handler.sendEmptyMessage(0);
							} else {
								progress.CloseProgress();
								tv_ticket.setText("0个");
								new_list.setVisibility(View.GONE);
								Toast.makeText(MyQuanZiActivity.this, info, 200)
										.show();
							}

							// if (list.size() > 0) {
							// String num = String.valueOf(list.size());
							// tv_ticket.setText(num+"个");
							// }
							progress.CloseProgress();
							load_yanglaojin();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}

	/**
	 * 第2个列表数据解析
	 */
	ArrayList list_avatar2;

	private void load_list2() {
		progress.CreateProgress();
		list_avatar2 = new ArrayList();
		try {
			// System.out.println("list_2.size()====================="+list_2.size());
			list_2 = new ArrayList<MyJuFenData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_user_child_list_2017?user_id=" + user_id
							+ "&user_name=" + user_name + "",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println("=====================二级值1"
									+ arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");
									int len = jsonArray.length();
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject object = jsonArray
												.getJSONObject(i);
										MyJuFenData data = new MyJuFenData();
										data.mobile = object
												.getString("mobile");
										data.login_sign = object
												.getString("login_sign");
										data.avatar = object
												.getString("avatar");
										data.real_name = object
												.getString("real_name");
										// data.user_name =
										// object.getString("user_name");
										data.nick_name = object
												.getString("nick_name");
										data.audit_time = object.getString("audit_time");
										String group_id = object
												.getString("group_id");
										String avatar = data.avatar;//
										System.out
												.println("二级值2====================="
														+ avatar);

										System.out
												.println("user_group_id==============2=================="
														+ user_group_id);
										if (group_id.equals(user_group_id)) {
											list_2.add(data);
											if (avatar.contains("http")) {
												System.out
														.println("================================http");
												list_avatar2.add(avatar);
											} else if (avatar
													.contains("upload")) {
												System.out
														.println("================================upload");
												String img_url = RealmName.REALM_NAME_HTTP
														+ avatar;
												list_avatar2.add(img_url);
											} else {
												System.out
														.println("================================空值");
												list_avatar2.add(avatar);
											}
											System.out
													.println("list_avatar.size()=================2==============="
															+ list_avatar2
															.size());
										}

									}
									System.out
											.println("list_2.size()==============2=================="
													+ list_2.size());
									System.out
											.println("list_avatar2.size()================================"
													+ list_avatar2.size());
									new_list.setVisibility(View.VISIBLE);
									handler.sendEmptyMessage(1);

								} else {
									try {
										progress.CloseProgress();
										new_list.setVisibility(View.GONE);
										tv_ticket.setText("0个");
										Toast.makeText(MyQuanZiActivity.this,
												info, 200).show();
									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}

								// if (list_2.size() > 0) {
								// String num = String.valueOf(list.size());
								// tv_ticket.setText(num+"个");
								// }

								// load_yanglaojin();
								progress.CloseProgress();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void load_yanglaojin() {
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_income_from_child_sum?to_user_id=" + user_id
				+ "&fund_id=2&expenses_id=6", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("=====================二级值" + arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					String info = jsonObject.getString("info");
					if (status.equals("y")) {
						JSONObject jobt = jsonObject.getJSONObject("data");
						// String num = jobt.getString("sum");
						double num = jobt.getDouble("sum");
						String num_ll = String.valueOf(num);
						tv_djjifen_ticket.setText(num_ll);
					} else {
						progress.CloseProgress();
					}
					progress.CloseProgress();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, null);
	}
}
