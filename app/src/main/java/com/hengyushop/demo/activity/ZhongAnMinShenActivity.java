package com.hengyushop.demo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.HuoDongListAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.pub.ZamsHuoDong1Adapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ctrip.openapi.java.utils.CustomScrollView;
import com.ctrip.openapi.java.utils.CustomScrollView.OnScrollChangedListener;
import com.droid.Activity01;
import com.example.taobaohead.BeanVo;
import com.example.taobaohead.headview.ScrollTopView;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.FenXiangActivity;
import com.hengyushop.demo.home.XinshouGyActivity;
import com.hengyushop.demo.home.ZhiFuOKActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.WareInformationData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PagerScrollView;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.HomeActivity;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.UserLoginWayActivity;
import com.zams.www.weiget.PermissionSetting;
import com.zxing.android.CaptureActivity;

/**
 * 活动
 * 
 * @author Administrator
 * 
 */
public class ZhongAnMinShenActivity extends Fragment implements OnClickListener {
	private LinearLayout index_item0, index_item1, index_item2;
	private WareDao wareDao;
	private ImageView img_user;
	private ImageView img_shared;
	private Thread thread;
	private Handler handler3;
	private Context context;
	private TextView img_demo3_1, img_demo3_0, img_demo2_0, tv_city;
	private PagerScrollView home_main_scrool;
	// private RelativeLayout home_title_layout;
	private LinearLayout home_title_layout;
	private EditText tv1;
	private LayoutInflater inflater;
	protected PopupWindow pop;
	private LinearLayout ll_jutoutiao, ll_sousuo;
	ScrollTopView mytaobao;
	private MyPosterView advPager = null;
	private ImageView iv_xsgl;
	private MyPosterView posterView;
	private SharedPreferences spPreferences;
	String article_id, goods_id;
	int list_id;
	String id;
	String group_id, city;
	private ImageView iv_zhuti_tp, iv_imagr2, iv_imagr3, iv_imagr4;
	String user_id;
	ArrayList<AdvertDao1> datas;
	public static AQuery mAq;
	boolean panduan = false;
	private ListView new_list;
	private DialogProgress progress;
	public static boolean type = false;
	private PullToRefreshView refresh;
	private ArrayList<JuTuanGouData> list_ll = null;
	int len;
	HuoDongListAdapter adapter;
	private CustomScrollView scrollView;
	private int fadingHeight = 200; // 当ScrollView滑动到什么位置时渐变消失（根据需要进行调整）

	public ZhongAnMinShenActivity() {

	}

	private ImageLoader imageLoader;
	private String key;
	private String yth;

	public ZhongAnMinShenActivity(ImageLoader imageLoader, Handler handler3,
			Context context, String key, String yth) {
		this.imageLoader = imageLoader;
		this.handler3 = handler3;
		this.context = context;
		this.key = key;
		this.yth = yth;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater
				.inflate(R.layout.activity_huodong_fragment, null);//
		// View layout = inflater.inflate(R.layout.hengyu_activity, null);//
		// hengyu_activity second_main_content_activity
		// View layout = inflater.inflate(R.layout.activity_huodong, null);//
		spPreferences = getActivity().getSharedPreferences("longuserset",
				Context.MODE_PRIVATE);
		try {
			progress = new DialogProgress(getActivity());
			mAq = new AQuery(getActivity());

			new_list = (ListView) layout.findViewById(R.id.new_list);
			new_list.setFocusable(false);
			iv_zhuti_tp = (ImageView) layout.findViewById(R.id.iv_zhuti_tp);
			// iv_zhuti_tp.setBackgroundResource(R.drawable.hd_zhuti_tp);

			tv1 = (EditText) layout.findViewById(R.id.tv1);
			// tv1.getBackground().setAlpha(50);
			tv_city = (TextView) layout.findViewById(R.id.tv_city);
			ll_sousuo = (LinearLayout) layout.findViewById(R.id.ll_sousuo);
			ll_sousuo.getBackground().setAlpha(70);
			img_shared = (ImageView) layout.findViewById(R.id.img_shared);
			img_user = (ImageView) layout.findViewById(R.id.iv_sys);
			// img_shared.setBackgroundResource(R.drawable.hd_jt);
			img_user.setBackgroundResource(R.drawable.saoyisao);
			// user_name = spPreferences.getString("user", "");

			initLayout(layout);
			// list_ll = new ArrayList<JuTuanGouData>();
			// adapter = new HuoDongListAdapter(list_ll,getActivity());
			// new_list.setAdapter(adapter);
			load_P();
			// load_list(true);
			load_list();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return layout;

	}

	private void initLayout(View layout) {
		// refresh = (PullToRefreshView) layout.findViewById(R.id.refresh);
		// refresh.setOnHeaderRefreshListener(listHeadListener);
		// refresh.setOnFooterRefreshListener(listFootListener);
		// img_shared.setOnClickListener(this);

		// home_main_scrool = (PagerScrollView)
		// layout.findViewById(R.id.home_main_scrool);
		// 广告滚动
		advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);
		home_title_layout = (LinearLayout) layout
				.findViewById(R.id.home_title_layout);

		// index_item0 = (LinearLayout) layout.findViewById(R.id.index_item0);
		// index_item1 = (LinearLayout) layout.findViewById(R.id.index_item1);
		// index_item2 = (LinearLayout) layout.findViewById(R.id.index_item2);
		// index_item0.setOnClickListener(this);
		// index_item1.setOnClickListener(this);
		// index_item2.setOnClickListener(this);

		// 切换城市
		tv_city.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), Activity01.class);
				// String strwhere_zhi = tv1.getText().toString().trim();
				// intent.putExtra("strwhere_zhi", strwhere_zhi);
				startActivity(intent);
			}
		});

		ImageView iv_sousuo = (ImageView) layout.findViewById(R.id.iv_sousuo);
		iv_sousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), SouSuoActivity.class);
				String strwhere_zhi = tv1.getText().toString().trim();
				intent.putExtra("strwhere_zhi", strwhere_zhi);
				intent.putExtra("channel_name", "signup");
				startActivity(intent);
			}
		});

		tv1.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
					// Toast.makeText(getActivity(),"呵呵",Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(),
							SouSuoActivity.class);
					String strwhere_zhi = tv1.getText().toString().trim();
					intent.putExtra("strwhere_zhi", strwhere_zhi);
					intent.putExtra("channel_name", "signup");
					startActivity(intent);
				}
				return false;
			}

		});
		//
		try {

			// 扫一扫
			img_user.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (!nickname.equals("")) {
						if (!user_name.equals("")) {

							AndPermission.with(getActivity())
									.permission( Permission.Group.CAMERA, Permission.Group.STORAGE)
									.onGranted(new Action() {
										@Override
										public void onAction(List<String> permissions) {
											Intent Intent2 = new Intent(getActivity(),
													CaptureActivity.class);
											startActivity(Intent2);
										}
									})
									.onDenied(new Action() {
										@Override
										public void onAction(List<String> permissions) {
											new PermissionSetting(getActivity()).showSetting(permissions);
										}
									}).start();
						} else {
							// getjianche();//后台检测是否绑定手机
							Intent intent2 = new Intent(getActivity(),
									TishiWxBangDingActivity.class);
							startActivity(intent2);
						}
					} else {
						if (user_name.equals("")) {
							Intent intent48 = new Intent(getActivity(),
									UserLoginActivity.class);
							startActivity(intent48);
						} else {
							String group_id = spPreferences.getString(
									"group_id", "");
							System.out.println("group_id======1========="
									+ group_id);
							if (group_id.equals("")) {
								Intent intent48 = new Intent(getActivity(),
										UserLoginActivity.class);
								startActivity(intent48);
							} else {

								AndPermission.with(getActivity())
										.permission( Permission.Group.CAMERA, Permission.Group.STORAGE)
										.onGranted(new Action() {
											@Override
											public void onAction(List<String> permissions) {
												Intent intent48 = new Intent(getActivity(),
														CaptureActivity.class);
												// intent48.putExtra("sp_sys", "3");
												startActivity(intent48);
											}
										})
										.onDenied(new Action() {
											@Override
											public void onAction(List<String> permissions) {
												new PermissionSetting(getActivity()).showSetting(permissions);
											}
										}).start();
							}
						}
					}

				}
			});

			iv_zhuti_tp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						System.out.println("list_id======1=========" + list_id);
						if (!nickname.equals("")) {
							if (!user_name.equals("")) {
								Intent intent = new Intent(getActivity(),
										ZhongAnMinShenXqActivity.class);
								// intent.putExtra("id", datas.get(0).id);
								intent.putExtra("id", article_id);
								startActivity(intent);
							} else {
								Intent intent = new Intent(getActivity(),
										TishiWxBangDingActivity.class);
								startActivity(intent);
							}
						} else {
							if (user_name.equals("")) {
								Intent intent = new Intent(getActivity(),
										UserLoginActivity.class);
								startActivity(intent);
							} else {
								Intent intent = new Intent(getActivity(),
										ZhongAnMinShenXqActivity.class);
								// intent.putExtra("id", datas.get(0).id);
								intent.putExtra("id", article_id);
								startActivity(intent);
							}
						}

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {

			e.printStackTrace();
		}
		// //广告滚动
		// advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);
		// AsyncHttp.get(RealmName.REALM_NAME_LL +
		// "/get_adbanner_list?advert_id=11",
		// new AsyncHttpResponseHandler() {
		// @Override
		// public void onSuccess(int arg0, String arg1) {
		// super.onSuccess(arg0, arg1);
		// try {
		// System.out.println("广告滚动-----------"+arg1);
		// JSONObject object = new JSONObject(arg1);
		// JSONArray array = object.getJSONArray("data");
		// int len = array.length();
		// ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
		// for (int i = 0; i < len; i++) {
		// AdvertDao1 ada = new AdvertDao1();
		// JSONObject json = array.getJSONObject(i);
		// ada.setId(json.getString("id"));
		// ada.setAd_url(json.getString("ad_url"));
		// ada.setLink_url(json.getString("link_url"));
		// // String ad_url = ada.getAd_url();
		// ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
		// images.add(ada);
		// }
		// // Message msg = new Message();
		// // msg.obj = images;
		// // msg.what = 0;
		// // childHandler.sendMessage(msg);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, context);

		// home_main_scrool.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View arg0, MotionEvent arg1) {
		//
		// switch (arg1.getAction()) {
		// case MotionEvent.ACTION_MOVE:
		// System.out.println("滑动" + arg0.getScrollY());
		// if (arg0.getScrollY() <= 1) {
		// img_user.setBackgroundResource(R.drawable.saoyisao);
		// // img_shared.setBackgroundResource(R.drawable.hd_jt);
		// home_title_layout.setBackgroundColor(getResources().getColor(R.color.no_color));
		// ll_sousuo.getBackground().setAlpha(70);
		// tv1.setBackgroundColor(getResources().getColor(R.color.no_color));
		// tv_city.setTextColor(Color.WHITE);
		// }else {
		// img_user.setBackgroundResource(R.drawable.sys_hs);
		// // img_shared.setBackgroundResource(R.drawable.hd_jt1);
		// home_title_layout.setBackgroundColor(getResources().getColor(R.color.white));
		// ll_sousuo.setBackgroundColor(getResources().getColor(R.color.baihuise));
		// tv1.setBackgroundColor(getResources().getColor(R.color.baihuise));
		// tv_city.setTextColor(Color.BLACK);
		// }
		// break;
		// }
		// return false;
		// }
		// });
		// home_main_scrool.setAlwaysDrawnWithCacheEnabled(true);

		scrollView = (CustomScrollView) layout.findViewById(R.id.myScrollView1);
		// 监听事件
		scrollView.setOnScrollChangedListener(new OnScrollChangedListener() {

			@Override
			public void onScrollChanged(ScrollView who, int x, int y, int oldx,
					int oldy) {
				if (y > fadingHeight) {
					y = fadingHeight; // 当滑动到指定位置之后设置颜色为纯色，之前的话要渐
				} else if (y < 0) {
					y = 0;
				}

				if (y >= fadingHeight) {
					img_user.setBackgroundResource(R.drawable.sys_hs);
					// img_shared.setBackgroundResource(R.drawable.hd_jt1);
					home_title_layout.setBackgroundColor(getResources()
							.getColor(R.color.white));
					ll_sousuo.setBackgroundColor(getResources().getColor(
							R.color.baihuise));
					tv1.setBackgroundColor(getResources().getColor(
							R.color.baihuise));
					tv_city.setTextColor(Color.BLACK);
				} else {
					img_user.setBackgroundResource(R.drawable.saoyisao);
					// img_shared.setBackgroundResource(R.drawable.hd_jt);
					home_title_layout.setBackgroundColor(getResources()
							.getColor(R.color.no_color));
					ll_sousuo.getBackground().setAlpha(70);
					tv1.setBackgroundColor(getResources().getColor(
							R.color.no_color));
					tv_city.setTextColor(Color.WHITE);
				}
			}
		});

		new_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				try {
					if (!nickname.equals("")) {
						if (!user_name.equals("")) {
							Intent intent = new Intent(getActivity(),
									ZhongAnMinShenXqActivity.class);
							intent.putExtra("id", list_ll.get(arg2).id);
							startActivity(intent);
						} else {
							Intent intent = new Intent(getActivity(),
									TishiWxBangDingActivity.class);
							startActivity(intent);
						}
					} else {
						if (user_name.equals("")) {
							Intent intent = new Intent(getActivity(),
									UserLoginActivity.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent(getActivity(),
									ZhongAnMinShenXqActivity.class);
							intent.putExtra("id", list_ll.get(arg2).id);
							startActivity(intent);
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	// /**
	// * 上拉列表刷新加载
	// */
	// private OnHeaderRefreshListener listHeadListener = new
	// OnHeaderRefreshListener() {
	//
	// @Override
	// public void onHeaderRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// refresh.onHeaderRefreshComplete();
	// }
	// }, 1000);
	// }
	// };
	//
	// /**
	// * 下拉列表刷新加载
	// */
	// private OnFooterRefreshListener listFootListener = new
	// OnFooterRefreshListener() {
	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// load_list(false);
	// refresh.onFooterRefreshComplete();
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	// }, 1000);
	// }
	// };

	// public void onPause() {
	// super.onPause();
	// if (posterView != null) {
	// posterView.puseExecutorService();
	// }
	// };

	String user_name_weixin = "";
	String user_name_qq = "";
	String weixin = "";
	String qq = "";
	String nickname = "";
	String user_name = "";
	String user_name_phone = "";
	String user_name_3_wx = "";
	String user_name_3_qq = "";
	String user_name_3 = "";
	// String user_name_key = "";
	String oauth_name;
	String datall;

	@Override
	public void onResume() {

		super.onResume();
		
		System.out.println("MyPosterView.type=======1=========="+MyPosterView.type);
		System.out.println("HomeActivity.type=======1==============="+HomeActivity.type);
		if (MyPosterView.type == true) {
			MyPosterView.mQuery.clear();
			MyPosterView.type = false;
		}
		if (HomeActivity.type == true) {
			HomeActivity.mAq.clear();
			HomeActivity.type = false;
		}
		
		System.out.println("HomeActivity.type=======2=========="+HomeActivity.type);
		System.out.println("MyPosterView.type=======2==============="+MyPosterView.type);
		

		SharedPreferences spPreferences = getActivity().getSharedPreferences(
				"longuserset_city", Context.MODE_PRIVATE);
		city = spPreferences.getString("city", "");
		System.out.println("city=================" + city);
		if (city.equals("")) {
			tv_city.setText("未定位");
		} else {
			tv_city.setText(city + "市");
		}

		SharedPreferences spPreferences_login = getActivity()
				.getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");

		System.out.println("nickname=================" + nickname);
		if (!nickname.equals("")) {
			getjianche();// 后台检测是否绑定手机
		} else {
			getuserxinxi();
		}
		System.out
				.println("mAq=========================================" + mAq);
		mAq = new AQuery(getActivity());
		mAq.clear();// 清除首页内存

	}

	private void getjianche() {

		SharedPreferences spPreferences_login = getActivity()
				.getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");
		String headimgurl = spPreferences_login.getString("headimgurl", "");
		String unionid = spPreferences_login.getString("unionid", "");
		String access_token = spPreferences_login.getString("access_token", "");
		String sex = spPreferences_login.getString("sex", "");

		System.out.println("UserLoginActivity====================="
				+ UserLoginActivity.oauth_name);
		System.out.println("UserLoginWayActivity====================="
				+ UserLoginWayActivity.oauth_name);

		if (UserLoginActivity.oauth_name.equals("weixin")) {
			oauth_name = "weixin";
		} else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
			oauth_name = "qq";
			unionid = "";
		}

		System.out.println("nickname-----1-----" + nickname);
		String nick_name = nickname.replaceAll("\\s*", "");
		System.out.println("nick_name-----2-----" + nick_name);

		// String strUrlone = RealmName.REALM_NAME_LL +
		// "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+""
		// +
		// "&province=&city=&country=&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";

		String oauth_openid = spPreferences_login.getString("oauth_openid", "");
		String strUrlone = RealmName.REALM_NAME_LL
				+ "/user_oauth_register_0217?nick_name=" + nick_name + "&sex="
				+ sex + "&avatar=" + headimgurl + ""
				+ "&province=&city=&country=&oauth_name=" + oauth_name
				+ "&oauth_unionid=" + unionid + "" + "&oauth_openid="
				+ oauth_openid + "";
		System.out.println("我的======11======1=======" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				System.out.println("我的======输出=====1========" + arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					// if (status.equals("y")) {
					datall = object.getString("data");
					System.out.println("datall==============" + datall);
					if (datall.equals("null")) {

						SharedPreferences spPreferences_tishi = getActivity()
								.getSharedPreferences("longuserset_tishi",
										Context.MODE_PRIVATE);
						weixin = spPreferences_tishi.getString("weixin", "");
						qq = spPreferences_tishi.getString("qq", "");
						System.out
								.println("=================weixin==" + weixin);
						System.out.println("=================qq==" + qq);

						System.out.println("UserLoginActivity.panduan====1=="
								+ UserLoginActivity.panduan_tishi);
						System.out
								.println("UserLoginWayActivity.panduan====2=="
										+ UserLoginWayActivity.panduan_tishi);
						if (!nickname.equals("")) {

							if (UserLoginActivity.panduan_tishi == true) {
								if (weixin.equals("weixin")) {
								} else {
									Intent intent1 = new Intent(getActivity(),
											TishiWxBangDingActivity.class);
									startActivity(intent1);
									UserLoginActivity.panduan_tishi = false;
								}

							} else if (UserLoginWayActivity.panduan_tishi == true) {
								if (qq.equals("qq")) {
								} else {
									Intent intent2 = new Intent(getActivity(),
											TishiWxBangDingActivity.class);
									startActivity(intent2);
									UserLoginWayActivity.panduan_tishi = false;
								}
							}
						}

					} else {
						UserRegisterllData data = new UserRegisterllData();
						JSONObject obj = object.getJSONObject("data");
						data.id = obj.getString("id");
						data.user_name = obj.getString("user_name");
						user_name = data.user_name;
						user_id = data.id;
						System.out
								.println("---data.user_name-------------------"
										+ data.user_name);
						System.out.println("---user_id-------------------"
								+ user_id);
						if (data.user_name.equals("匿名")) {
							System.out.println("---微信还未绑定-------------------");
							Intent intent1 = new Intent(getActivity(),
									TishiWxBangDingActivity.class);
							startActivity(intent1);
						} else {
							SharedPreferences spPreferences = getActivity()
									.getSharedPreferences("longuserset",
											Context.MODE_PRIVATE);
							String user = spPreferences.getString("user", "");
							System.out
									.println("---1-------------------" + user);
							data.login_sign = obj.getString("login_sign");

							Editor editor = spPreferences.edit();
							editor.putString("user", data.user_name);
							editor.putString("user_id", data.id);
							editor.putString("login_sign", data.login_sign);
							editor.commit();

							String user_name = spPreferences.getString("user",
									"");
							System.out.println("---2-------------------"
									+ user_name);
						}
					}
					getuserxinxi();
				} catch (JSONException e) {

					e.printStackTrace();
				}

			};
		}, getActivity());

	}

	private void getuserxinxi() {

		try {

			spPreferences = getActivity().getSharedPreferences("longuserset",
					Context.MODE_PRIVATE);
			user_name_phone = spPreferences.getString("user", "");
			System.out.println("user_name_phone================"
					+ user_name_phone);

			if (!user_name_phone.equals("")) {
				user_name = user_name_phone;
				user_id = spPreferences.getString("user_id", "");
			} else {
				user_name = "";
			}

			// spPreferences = getActivity().getSharedPreferences("longuserset",
			// Context.MODE_PRIVATE);
			// Editor editor = spPreferences.edit();
			// editor.putString("user", user_name);
			// editor.putString("user_id", user_id);
			// editor.commit();

			System.out.println("user_name================" + user_name);
			group_id = spPreferences.getString("group_id", "");
			System.out.println("======group_id======1=======" + group_id);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				System.out.println("=================5=" + list_ll.size());
				// list_ll = (ArrayList<JuTuanGouData>) msg.obj;
				// adapter.putData(list_ll);
				// HuoDongListAdapter.aQuery.clear();

				// new_list.setOnItemClickListener(new OnItemClickListener() {
				//
				// @Override
				// public void onItemClick(AdapterView<?> arg0, View arg1, int
				// arg2,
				// long arg3) {
				//
				// try {
				// if (!nickname.equals("")) {
				// if (!user_name.equals("")) {
				// Intent intent= new
				// Intent(getActivity(),ZhongAnMinShenHdXqActivity.class);
				// intent.putExtra("id", list_ll.get(arg2).id);
				// startActivity(intent);
				// } else {
				// Intent intent = new Intent(getActivity(),
				// TishiWxBangDingActivity.class);
				// startActivity(intent);
				// }
				// }else {
				// if (user_name.equals("")) {
				// Intent intent = new Intent(getActivity(),
				// UserLoginActivity.class);
				// startActivity(intent);
				// }else {
				// Intent intent= new
				// Intent(getActivity(),ZhongAnMinShenHdXqActivity.class);
				// intent.putExtra("id", list_ll.get(arg2).id);
				// startActivity(intent);
				// }
				// }
				//
				// } catch (Exception e) {
				//
				// e.printStackTrace();
				// }
				// }
				// });
				break;
			case 13:
				try {

					String id = (String) msg.obj;
					System.out.println("1111=============" + id);
					Intent intent13 = new Intent(getActivity(), Webview1.class);
					intent13.putExtra("gg_id", id);
					startActivity(intent13);

				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			case 15:
				if (!nickname.equals("")) {
					if (!user_name.equals("")) {
						Intent Intent2 = new Intent(getActivity(),
								FenXiangActivity.class);
						startActivity(Intent2);
					} else {
						Intent intent2 = new Intent(getActivity(),
								TishiWxBangDingActivity.class);
						startActivity(intent2);
					}
				} else {
					if (user_name.equals("")) {
						Intent intentll = new Intent(getActivity(),
								UserLoginActivity.class);
						startActivity(intentll);
					} else {
						try {
							// SoftWarePopuWindow(img_shared, context);
							Intent intentll = new Intent(getActivity(),
									FenXiangActivity.class);
							startActivity(intentll);
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.index_item0:
			// if (!nickname.equals("")) {
			// if (!user_name.equals("")) {
			// Intent Intent2 = new
			// Intent(getActivity(),ZhongAnMinShenXqActivity.class);
			// startActivity(Intent2);
			// } else {
			// Intent intent2 = new Intent(getActivity(),
			// TishiWxBangDingActivity.class);
			// startActivity(intent2);
			// }
			// }else {
			// if (user_name.equals("")) {
			// Intent intent48 = new Intent(getActivity(),
			// UserLoginActivity.class);
			// startActivity(intent48);
			// }else {
			// Intent intent48 = new Intent(getActivity(),
			// ZhongAnMinShenXqActivity.class);
			// startActivity(intent48);
			// }
			// }
			break;
		case R.id.index_item1:
			// handler.sendEmptyMessage(1);
			break;
		case R.id.index_item2:
			// handler.sendEmptyMessage(2);
			break;

		default:
			break;
		}

	}

	/**
	 * 第1个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag) {
		RUN_METHOD = 1;
		if (flag) {
			// 计数和容器清零
			CURRENT_NUM = 1;
			list_ll = new ArrayList<JuTuanGouData>();
		}
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
						+ "/get_article_page_size_list?channel_name=signup&category_id=0"
						+ "&page_size=" + VIEW_NUM + "&page_index="
						+ CURRENT_NUM + "&strwhere=&orderby=",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								System.out.println("活动列表====================="
										+ arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject
											.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = jsonObject
												.getJSONArray("data");
										len = jsonArray.length();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject obj = jsonArray
													.getJSONObject(i);
											JuTuanGouData data = new JuTuanGouData();
											data.setId(obj.getString("id"));
											data.setTitle(obj
													.getString("title"));
											data.setImg_url(obj
													.getString("img_url"));
											data.setCategory_title(obj
													.getString("category_title"));
											data.setSell_price(obj
													.getString("sell_price"));

											data.setAdd_time(obj
													.getString("add_time"));
											data.setUpdate_time(obj
													.getString("update_time"));
											// data.setEnd_time(obj.getString("end_time"));
											// data.setCategory_id(obj.getString("category_id"));
											System.out
													.println("1----------title-----------"
															+ data.getTitle());
											list_ll.add(data);
										}
										// Toast.makeText(JuTuanGouActivity.this,
										// info, 200).show();
										System.out
												.println("list_ll.size()--------1--------"
														+ list_ll.size());
										// ZamsHuoDongAdapter juJingCaiAdapter =
										// new ZamsHuoDongAdapter(getActivity(),
										// list_ll);
										// new_list.setAdapter(juJingCaiAdapter);
										// setListViewHeightBasedOnChildren(new_list);
										// ZamsHuoDongAdapter.mAq.clear();
									} else {
										Toast.makeText(getActivity(), info, 200)
												.show();
									}
									Message msg = new Message();
									msg.what = 0;
									msg.obj = list_ll;
									handler.sendMessage(msg);
									if (len != 0) {
										CURRENT_NUM = CURRENT_NUM + 1;
									}
								} catch (JSONException e) {

									e.printStackTrace();
								}
							}
						}, null);
	}

	/**
	 * 活动列表
	 */
	java.util.Date now_1;
	java.util.Date date_1;

	private void load_list() {
		try {
			list_ll = new ArrayList<JuTuanGouData>();
			// AsyncHttp.get(RealmName.REALM_NAME_LL +
			// "/get_article_page_size_list?page_size=" + 3 + "" +
			// "&page_index=" + 1 +
			// "&channel_name=groupon&category_id="+0+""//1703
			AsyncHttp
					.get(RealmName.REALM_NAME_LL
							+ "/get_article_page_size_list?channel_name=signup&category_id=0"
							+ "&page_size=10&page_index=1&strwhere=&orderby=",
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {

									super.onSuccess(arg0, arg1);
									System.out.println("输出聚精彩列表========="
											+ arg1);
									try {
										JSONObject object = new JSONObject(arg1);
										String status = object
												.getString("status");
										String info = object.getString("info");
										// JuTuanGouData data = new
										// JuTuanGouData();
										// datetime =
										// object.getString("datetime");
										// data.setDatetime(object.getString("datetime"));
										if (status.equals("y")) {

											JSONArray jsonArray = object
													.getJSONArray("data");
											for (int i = 0; i < jsonArray
													.length(); i++) {
												JSONObject obj = jsonArray
														.getJSONObject(i);
												JuTuanGouData data = new JuTuanGouData();
												data.setId(obj.getString("id"));
												data.setTitle(obj
														.getString("title"));
												data.setImg_url(obj
														.getString("img_url"));
												data.setCategory_title(obj
														.getString("category_title"));
												data.setSell_price(obj
														.getString("sell_price"));
												data.setAdd_time(obj
														.getString("add_time"));
												data.setUpdate_time(obj
														.getString("update_time"));
												data.setStart_time(obj
														.getString("start_time"));
												data.setEnd_time(obj
														.getString("end_time"));
												data.setAddress(obj
														.getString("address"));
												data.setCompany_name(obj
														.getString("company_name"));
												// data.setCategory_id(obj.getString("category_id"));
												System.out.println("1----------title-----------"
														+ data.getTitle());
												list_ll.add(data);
											}
											// Toast.makeText(JuTuanGouActivity.this,
											// info, 200).show();
											try {
												System.out
														.println("list_ll.size()--------1--------"
																+ list_ll
																		.size());
												ZamsHuoDong1Adapter juJingCaiAdapter = new ZamsHuoDong1Adapter(
														getActivity(), list_ll);
												new_list.setAdapter(juJingCaiAdapter);
												setListViewHeightBasedOnChildren(new_list);
												ZamsHuoDong1Adapter.mAq.clear();

											} catch (Exception e) {

												e.printStackTrace();
											}
										} else {
											// Toast.makeText(JuTuanGouActivity.this,
											// info, 200).show();
										}

									} catch (JSONException e) {

										e.printStackTrace();
									}
								}

							}, getActivity());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 活动置顶
	 * 
	 * @param
	 */
	private void load_P() {
		// progress.CreateProgress();
		AsyncHttp
				.get(RealmName.REALM_NAME_LL
						+ "/get_article_top_list?channel_name=signup&top=0&strwhere=status=0%20and%20is_top=1",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
								parse2(arg1);
							}

							@Override
							public void onFailure(Throwable arg0, String arg1) {

								super.onFailure(arg0, arg1);
								try {
									System.out
											.println("11================================="
													+ arg0);
									System.out
											.println("22================================="
													+ arg1);
									progress.CloseProgress();
									// Toast.makeText(getActivity(), "超时异常",
									// 200).show();
								} catch (Exception e) {

									e.printStackTrace();
								}
							}
						}, getActivity());
	}

	/**
	 * 活动置顶
	 * 
	 * @param st
	 */
	private void parse2(String st) {
		try {
			System.out.println("活动置顶====================" + st);
			JSONObject jsonObject = new JSONObject(st);
			String status = jsonObject.getString("status");
			String info = jsonObject.getString("info");
			if (status.equals("y")) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				datas = new ArrayList<AdvertDao1>();
				for (int i = 0; i < jsonArray.length(); i++) {
					AdvertDao1 data = new AdvertDao1();
					JSONObject object = jsonArray.getJSONObject(i);
					data.id = object.getString("id");
					article_id = object.getString("id");
					// list_id = data.id;
					// data.img_url = object.getString("img_url");
					data.setImg_url(RealmName.REALM_NAME_HTTP
							+ object.getString("img_url"));
					// data.title = object.getString("title");
					datas.add(data);
					// mAq.id(iv_zhuti_tp).image(RealmName.REALM_NAME_HTTP +
					// data.img_url);
					// JSONArray jst= object.getJSONArray("activity");
					// JSONObject jobject = object.getJSONObject("activity");
					// article_id = jobject.getString("article_id");
					// for (int k = 0; k < jst.length(); k++) {
					// JSONObject jobject = jst.getJSONObject(k);
					// article_id = jobject.getString("article_id");
					// }
				}
			} else {
				// iv_zhuti_tp.setVisibility(View.GONE);
				// Toast.makeText(getActivity(), info, 200).show();
			}
			progress.CloseProgress();

			Message msg = new Message();
			msg.obj = datas;
			msg.what = 0;
			childHandler.sendMessage(msg);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 广告
	 */
	ArrayList<AdvertDao1> tempss;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {
					tempss = (ArrayList<AdvertDao1>) msg.obj;
					ArrayList<String> urls = new ArrayList<String>();
					for (int i = 0; i < tempss.size(); i++) {
						urls.add(tempss.get(i).getImg_url());
					}
					ImageLoader imageLoader = ImageLoader.getInstance();
					advPager.setData(urls, new MyPosterOnClick() {
						@Override
						public void onMyclick(int position) {

							// String link_url =
							// tempss.get(position).getLink_url();
							// System.out.println("link_url=============" +
							// link_url);
							// Intent intent13 = new Intent(getActivity(),
							// Webview1.class);
							// intent13.putExtra("link_url", link_url);
							// startActivity(intent13);
							try {
								String article_id = tempss.get(position)
										.getId();
								System.out.println("article_id==============="
										+ article_id);
								if (!nickname.equals("")) {
									if (!user_name.equals("")) {
										Intent intent = new Intent(
												getActivity(),
												ZhongAnMinShenXqActivity.class);
										// intent.putExtra("id",
										// datas.get(0).id);
										intent.putExtra("id", article_id);
										startActivity(intent);
									} else {
										Intent intent = new Intent(
												getActivity(),
												TishiWxBangDingActivity.class);
										startActivity(intent);
									}
								} else {
									if (user_name.equals("")) {
										Intent intent = new Intent(
												getActivity(),
												UserLoginActivity.class);
										startActivity(intent);
									} else {
										Intent intent = new Intent(
												getActivity(),
												ZhongAnMinShenXqActivity.class);
										// intent.putExtra("id",
										// datas.get(0).id);
										intent.putExtra("id", article_id);
										startActivity(intent);
									}
								}

							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					}, true, imageLoader, true);
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
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

	public void setListViewHeightBasedOnChildren(GridView gridview2) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = gridview2.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridview2);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridview2.getLayoutParams();
		params.height = totalHeight
				+ (gridview2.getHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		gridview2.setLayoutParams(params);
	}

}
