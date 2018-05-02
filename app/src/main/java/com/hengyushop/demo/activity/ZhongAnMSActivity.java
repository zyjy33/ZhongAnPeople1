package com.hengyushop.demo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.androiddevelop.cycleviewpager.lib.CycleViewPager;
import cn.androiddevelop.cycleviewpager.lib.CycleViewPager.ImageCycleViewListener;

import com.android.hengyu.pub.GouWuCheAGoodsAdaper;
import com.android.hengyu.pub.HuoDongListAdapter;
import com.android.hengyu.pub.ZamsHuoDong1Adapter;
import com.android.hengyu.pub.ZamsHuoDong2Adapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.baidu.location.v;
import com.ctrip.openapi.java.utils.CustomScrollView;
import com.example.taobaohead.headview.ScrollTopView;
import com.hengyushop.airplane.adapter.GoodsMyGridViewAdaper;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.MyPoster_activity_View;
import com.lglottery.www.widget.MyPoster_activity_View_1;
import com.lglottery.www.widget.PagerScrollView;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.stevenhu.android.phone.bean.ADInfo;
import com.stevenhu.android.phone.utils.ViewFactory;
import com.zams.www.HomeActivity;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.UserLoginWayActivity;

/**
 * 活动
 * 
 * @author Administrator
 * 
 */
public class ZhongAnMSActivity extends Fragment implements OnClickListener {
	private WareDao wareDao;
	private ImageView img_user;
	private ImageView img_shared;
	private Thread thread;
	private Handler handler3;
	private Context context;
	private TextView tv_activity_1, tv_activity_2, tv_activity_3, tv_activity_4;
	private PagerScrollView home_main_scrool;
	// private RelativeLayout home_title_layout;
	private LinearLayout home_title_layout;
	private EditText tv1;
	private LayoutInflater inflater;
	protected PopupWindow pop;
	private LinearLayout ll_jutoutiao, ll_sousuo;
	ScrollTopView mytaobao;
	private MyPosterView advPager_1 = null;
	private MyPosterView advPager_2 = null;
	private ImageView iv_xsgl;
	private MyPosterView posterView;
	private SharedPreferences spPreferences;
	String article_id, goods_id;
	String id;
	String group_id, city;
	private ImageView iv_zhuti_tp, iv_zhuti_tp_2,iv_imagr2, iv_imagr3, iv_imagr4;
	String user_id;
	ArrayList<AdvertDao1> datas;
	public static AQuery mAq;
	boolean panduan = false;
	private ListView new_list;
	private DialogProgress progress;
	public static boolean type = false;
	public static boolean zt_type_1 = false;
	public static boolean zt_type_2 = false;
	private PullToRefreshView refresh;
	private ArrayList<JuTuanGouData> list_ll = null;
	int len;
	ZamsHuoDong1Adapter juJingCaiAdapter;
	ZamsHuoDong2Adapter Activity2Adapter;
	HuoDongListAdapter adapter;
	private CustomScrollView scrollView;
	private LinearLayout index_item0, index_item1, index_item2, index_item3;
	public static int datatype_id = 6;//报名活动
	private int datatype_id_2 = 8;//签到活动
	public static int list_id = 6;
	SharedPreferences spPreferences_login;
	public ZhongAnMSActivity() {

	}

	private ImageLoader imageLoader;
	private String key;
	private String yth;

	public ZhongAnMSActivity(ImageLoader imageLoader, Handler handler3,
			Context context, String key, String yth) {
		this.imageLoader = imageLoader;
		this.handler3 = handler3;
		this.context = context;
		this.key = key;
		this.yth = yth;
	}

	View layout; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		try {
		layout = inflater.inflate(R.layout.activity_zams_huodong, null);//
		spPreferences = getActivity().getSharedPreferences("longuserset",Context.MODE_PRIVATE);
			progress = new DialogProgress(getActivity());
			mAq = new AQuery(getActivity());

			new_list = (ListView) layout.findViewById(R.id.new_list);
			new_list.setFocusable(false);
			iv_zhuti_tp = (ImageView) layout.findViewById(R.id.iv_zhuti_tp);
			iv_zhuti_tp_2 = (ImageView) layout.findViewById(R.id.iv_zhuti_tp_2);
			// iv_zhuti_tp.setBackgroundResource(R.drawable.hd_zhuti_tp);

			initLayout(layout);
			load_baoming_1(datatype_id);
			load_qiandao_2(datatype_id_2);
			load_list(datatype_id);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return layout;

	}
	
	
	
	private void initLayout(View layout) {
		try {
		// 广告滚动
		advPager_1 = (MyPosterView) layout.findViewById(R.id.adv_pagerll);
		advPager_2 = (MyPosterView) layout.findViewById(R.id.adv_pagerll_2);
		index_item0 = (LinearLayout) layout.findViewById(R.id.index_item0);
		index_item1 = (LinearLayout) layout.findViewById(R.id.index_item1);
		index_item2 = (LinearLayout) layout.findViewById(R.id.index_item2);
		index_item3 = (LinearLayout) layout.findViewById(R.id.index_item3);
		tv_activity_1 = (TextView) layout.findViewById(R.id.tv_activity_1); 
		tv_activity_2 = (TextView) layout.findViewById(R.id.tv_activity_2); 
		tv_activity_3 = (TextView) layout.findViewById(R.id.tv_activity_3); 
		tv_activity_4 = (TextView) layout.findViewById(R.id.tv_activity_4); 
		index_item0.setOnClickListener(this);
		index_item1.setOnClickListener(this);
		index_item2.setOnClickListener(this);
		index_item3.setOnClickListener(this);
//		advPager_2.setVisibility(View.GONE);
		iv_zhuti_tp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				String article_id = datas.get(0).getId();
				System.out.println("article_id==============="+ article_id);
				if (!nickname.equals("")) {
					if (!user_name.equals("")) {
						Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
						intent.putExtra("id", article_id);
						intent.putExtra("datatype_id", String.valueOf(datatype_id));
						startActivity(intent);
					} else {
						Intent intent = new Intent(getActivity(),TishiWxBangDingActivity.class);
						startActivity(intent);
					}
				} else {
					if (user_name.equals("")) {
						Intent intent = new Intent(getActivity(),UserLoginActivity.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
						intent.putExtra("id", article_id);
						intent.putExtra("datatype_id", String.valueOf(datatype_id));
						startActivity(intent);
					}
				}
			}
		});
		
        iv_zhuti_tp_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				String article_id = datas_2.get(0).getId();
				System.out.println("article_id==============="+ article_id);
				if (!nickname.equals("")) {
					if (!user_name.equals("")) {
						Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
						intent.putExtra("id", article_id);
						intent.putExtra("datatype_id", String.valueOf(datatype_id));
						startActivity(intent);
					} else {
						Intent intent = new Intent(getActivity(),TishiWxBangDingActivity.class);
						startActivity(intent);
					}
				} else {
					if (user_name.equals("")) {
						Intent intent = new Intent(getActivity(),UserLoginActivity.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
						intent.putExtra("id", article_id);
						intent.putExtra("datatype_id", String.valueOf(datatype_id));
						startActivity(intent);
					}
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
							Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
							intent.putExtra("id", list_ll.get(arg2).id);
							intent.putExtra("datatype_id", String.valueOf(datatype_id));
							startActivity(intent);
						} else {
							Intent intent = new Intent(getActivity(),TishiWxBangDingActivity.class);
							startActivity(intent);
						}
					} else {
						if (user_name.equals("")) {
							Intent intent = new Intent(getActivity(),UserLoginActivity.class);
							startActivity(intent);
						} else {
							System.out.println("datatype_id-------------------------------"+datatype_id);
							Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
							intent.putExtra("id", list_ll.get(arg2).id);
							intent.putExtra("datatype_id", String.valueOf(datatype_id));
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
	}
	
	//活动签到，活动报名，赛事活动，活动投票
	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.index_item0:
			try {
			new_list.setVisibility(View.GONE);	
			tv_activity_1.setTextColor(this.getResources().getColor(R.color.juhongse));
			tv_activity_2.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_3.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_4.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			datatype_id = 6;
			System.out.println("zt_type_1--------------------------"+zt_type_1);
			if (zt_type_1 == false) {
				System.out.println("1--------------------------"+datas.size());
			if (datas.size() == 1) {
				iv_zhuti_tp.setVisibility(View.VISIBLE);
				iv_zhuti_tp_2.setVisibility(View.GONE);
				advPager_2.setVisibility(View.GONE);
			}else {
				advPager_1.setVisibility(View.VISIBLE);
				advPager_2.setVisibility(View.GONE);
				iv_zhuti_tp_2.setVisibility(View.GONE);
			}
			}else {
				iv_zhuti_tp_2.setVisibility(View.GONE);
				advPager_2.setVisibility(View.GONE);
			}
//			load_P(datatype_id);
			load_list(datatype_id);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;
		case R.id.index_item1:
			try {
			new_list.setVisibility(View.GONE);	
			tv_activity_1.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_2.setTextColor(this.getResources().getColor(R.color.juhongse));
			tv_activity_3.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_4.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			datatype_id = 8;
			System.out.println("zt_type_2--------------------------"+zt_type_2);
			if (zt_type_2 == false) {
				System.out.println("2---------------------------"+datas_2.size());
			if (datas_2.size() == 1) {
				iv_zhuti_tp.setVisibility(View.GONE);
				iv_zhuti_tp_2.setVisibility(View.VISIBLE);
				advPager_1.setVisibility(View.GONE);
				mAq.id(iv_zhuti_tp_2).image(datas_2.get(0).getImg_url());
			}else {
			    advPager_1.setVisibility(View.GONE);
			    advPager_2.setVisibility(View.VISIBLE);
			    iv_zhuti_tp.setVisibility(View.GONE);
			}
            }else {
            	iv_zhuti_tp.setVisibility(View.GONE);
            	advPager_1.setVisibility(View.GONE);
			}
//			load_qiandao_2(datatype_id_2);
			load_list(datatype_id);
			} catch (Exception e) {

				e.printStackTrace();
			}
			break;
		case R.id.index_item2:
			tv_activity_1.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_2.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_3.setTextColor(this.getResources().getColor(R.color.juhongse));
			tv_activity_4.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			datatype_id = 5;
			load_list(datatype_id);
			break;
		case R.id.index_item3:
			tv_activity_1.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_2.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_3.setTextColor(this.getResources().getColor(R.color.zams_zt_ys));
			tv_activity_4.setTextColor(this.getResources().getColor(R.color.juhongse));
			datatype_id = 4;
			load_list(datatype_id);
			break;
		default:
			break;
		}

	}

	String user_name_weixin = "";
	String user_name_qq = "";
	String weixin = "";
	String qq = "";
	String nickname = "";
	public static String user_name = "";
	String user_name_phone = "";
	String user_name_3_wx = "";
	String user_name_3_qq = "";
	String user_name_3 = "";
	// String user_name_key = "";
	String oauth_name;
	String datall;

	boolean type_list= false;
	@Override
	public void onResume() {

		super.onResume();
		try {
			System.out.println("type_list=====================================================================" + type_list);
			
//			if (type_list == false) {
//				type_list = true;
//				load_list(datatype_id);
//			}
			
		spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");

		System.out.println("nickname=================" + nickname);
		if (!nickname.equals("")) {
			getjianche();// 后台检测是否绑定手机
		} else {
			getuserxinxi();
		}
		
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
    @Override  
    public void onPause() {  
        super.onPause();  
    }   
    @Override  
    public void onStop() {  
        super.onStop();  
    }  
    

	public void onDestroy() {
		 super.onDestroy(); 
		 try {
			System.out.println("MyPosterView.type=======1=========="+MyPosterView.type);
			System.out.println("MyPoster_activity_View.type=======1==============="+MyPoster_activity_View.type);
			System.out.println("MyPoster_activity_View_1.type=======1==============="+ MyPoster_activity_View_1.type);
			
			
			if (ZamsHuoDong1Adapter.type == true) {
				ZamsHuoDong1Adapter.mAq.clear();
				ZamsHuoDong1Adapter.mAq.recycle(new_list);
				ZamsHuoDong1Adapter.type = false;
			}
			
			if (ZamsHuoDong2Adapter.type == true) {
				ZamsHuoDong2Adapter.mAq.clear();
				ZamsHuoDong1Adapter.mAq.recycle(new_list);
				ZamsHuoDong2Adapter.type = false;
			}
			
			if (MyPosterView.type == true) {
				MyPosterView.mQuery.clear();
				MyPosterView.type = false;
				if (datatype_id == 6) {
					datas = null;
					tempss_1 = null;
					tempss_1 = null;
				}else if (datatype_id == 8) {
					datas_2 = null;
					tempss_2 = null;
					tempss_2 = null;
				}
			}
			
			if (HomeActivity.type == true) {
				HomeActivity.mAq.clear();
				HomeActivity.type = false;
			}
			
			
			if (list_ll.size() < 0) {
				list_ll.clear();
				list_ll = null;
			}
				
			datatype_id = 6;//刷新为报名活动
			
			System.out.println("HomeActivity.type=======2=========="+ HomeActivity.type);
			System.out.println("MyPosterView.type=======2==============="+ MyPosterView.type);
			System.out.println("GouWuCheAGoodsAdaper.type=======2==============="+ GouWuCheAGoodsAdaper.type);
		 } catch (Exception e) {

			 e.printStackTrace();
		 }
	};
	
	UserRegisterllData data_login;
	private void getjianche() {

		spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
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
						data = new JuTuanGouData();
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
			spPreferences = getActivity().getSharedPreferences("longuserset",Context.MODE_PRIVATE);
			user_name_phone = spPreferences.getString("user", "");
			System.out.println("user_name_phone================"+ user_name_phone);

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

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 13:
				break;
			default:
				break;
			}
		};
	};


	/**
	 * 活动列表
	 */
	java.util.Date now_1;
	java.util.Date date_1;
	public static String datetime;
	JuTuanGouData data;
	private void load_list(int datatype_id_1) {
		datatype_id = datatype_id_1;
//		datas = new ArrayList<AdvertDao1>();
		try {
			progress.CreateProgress();
			list_ll = new ArrayList<JuTuanGouData>();
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_article_datatype_size_list?channel_name=signup&category_id=0&datatype_id="+datatype_id_1+""
							+ "&page_size=100&page_index=1&strwhere=&orderby=id%20desc",
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {

									super.onSuccess(arg0, arg1);
//									System.out.println("输出列表=============="+ arg1);
									try {
										JSONObject object = new JSONObject(arg1);
										String status = object.getString("status");
										String info = object.getString("info");
										datetime = object.getString("datetime");
										if (status.equals("y")) {
											new_list.setVisibility(View.VISIBLE);
											JSONArray jsonArray = object.getJSONArray("data");
											for (int i = 0; i < jsonArray.length(); i++) {
												data = new JuTuanGouData();
												JSONObject obj = jsonArray.getJSONObject(i);
//												AdvertDao1 data_l = new AdvertDao1();
												data.setId(obj.getString("id"));
												data.setTitle(obj.getString("title"));
												data.setImg_url(obj.getString("img_url"));
//												data_l.setImg_url(RealmName.REALM_NAME_HTTP+ obj.getString("img_url"));
												data.setCategory_title(obj.getString("category_title"));
//												data.setSell_price(obj.getString("sell_price"));
												data.setAdd_time(obj.getString("add_time"));
												data.setUpdate_time(obj.getString("update_time"));
												data.setStart_time(obj.getString("start_time"));
												data.setEnd_time(obj.getString("end_time"));
												data.setAddress(obj.getString("address"));
												data.setCompany_name(obj.getString("company_name"));
												// data.setCategory_id(obj.getString("category_id"));
												JSONObject jsot = obj.getJSONObject("default_spec_item");
												data.setSell_price(jsot.getString("sell_price"));
//												datas.add(data_l);
												list_ll.add(data);
											}
											data = null;
											// Toast.makeText(JuTuanGouActivity.this,
											// info, 200).show();
											try {
												System.out.println("list_ll.size()--------输出列表--------"+ list_ll.size());
												if (datatype_id == 6){
													Activity2Adapter = new ZamsHuoDong2Adapter(getActivity(), list_ll);
													new_list.setAdapter(Activity2Adapter);
												}else if (datatype_id == 8) {
													juJingCaiAdapter = new ZamsHuoDong1Adapter(getActivity(), list_ll);
													new_list.setAdapter(juJingCaiAdapter);
												}
												setListViewHeightBasedOnChildren(new_list);
//												ZamsHuoDongAdapter.mAq.clear();
												progress.CloseProgress();
											} catch (Exception e) {

												e.printStackTrace();
											}
											new_list.setVisibility(View.VISIBLE);	
										} else {
											 new_list.setVisibility(View.GONE);
//											 juJingCaiAdapter = new ZamsHuoDong1Adapter(getActivity(), list_ll);
//											 new_list.setAdapter(juJingCaiAdapter);
											 Toast.makeText(getActivity(),"暂无活动", Toast.LENGTH_SHORT).show();
											 progress.CloseProgress();
										}
									} catch (JSONException e) {

										e.printStackTrace();
									}
								}
								@Override
								public void onFailure(Throwable arg0,String arg1) {

									super.onFailure(arg0, arg1);
									System.out.println("arg1----------------"+arg1);
									Toast.makeText(getActivity(),"链接异常",  Toast.LENGTH_SHORT).show();
									progress.CloseProgress();
								}

							}, getActivity());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 活动置顶1
	 * @param datatype_id_1 
	 * 
	 * @param
	 */
	AdvertDao1 data_1;
	private void load_baoming_1(int datatype_id_1) {
		// progress.CreateProgress();
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_article_datatype_size_list?channel_name=signup&category_id=0&datatype_id="+datatype_id_1+""
				+ "&page_size=100&page_index=1&strwhere=is_top=1&orderby=id%20desc",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
//								parse2(arg1);
									try {
										System.out.println("活动置顶========1============" + arg1);
										JSONObject jsonObject = new JSONObject(arg1);
										String status = jsonObject.getString("status");
										String info = jsonObject.getString("info");
										if (status.equals("y")) {
											JSONArray jsonArray = jsonObject.getJSONArray("data");
											datas = new ArrayList<AdvertDao1>();
											for (int i = 0; i < jsonArray.length(); i++) {
//											for (int i = 0; i < 1; i++) {
												data_1 = new AdvertDao1();
												JSONObject object = jsonArray.getJSONObject(i);
												data_1.setId(object.getString("id"));
												// data.img_url = object.getString("img_url");
												data_1.setImg_url(RealmName.REALM_NAME_HTTP+ object.getString("img_url"));
												datas.add(data_1);
											}
											data_1 = null;
											System.out.println("datas.size()--------------------------------------"+datas.size());
											System.out.println("datatype_id------------------1--------------------"+datatype_id);
											if (datas.size() == 1) {
												iv_zhuti_tp.setVisibility(View.VISIBLE);
												advPager_1.setVisibility(View.GONE);
												mAq.id(iv_zhuti_tp).image(datas.get(0).getImg_url());
											}else {
											Message msg = new Message();
											msg.obj = datas;
											msg.what = 0;
											childHandler.sendMessage(msg);
											}
										} else {
											zt_type_1 = true;
											advPager_1.setVisibility(View.GONE);
											// Toast.makeText(getActivity(), info,  Toast.LENGTH_SHORT).show();
										}
										progress.CloseProgress();

//										if (datatype_id == 6) {
//											Message msg = new Message();
//											msg.obj = datas;
//											msg.what = 0;
//											childHandler.sendMessage(msg);
//										}
//											else 
//											if (datatype_id == 8) {
//											Message msg = new Message();
//											msg.obj = datas;
//											msg.what = 0;
//											childHandler1.sendMessage(msg);
//										}

									} catch (Exception e) {

										e.printStackTrace();
									}
								}
								

							@Override
							public void onFailure(Throwable arg0, String arg1) {

								super.onFailure(arg0, arg1);
									progress.CloseProgress();
									Toast.makeText(getActivity(), "超时异常", Toast.LENGTH_SHORT).show();
							}
						}, getActivity());
	}

	/**
	 * 活动置顶2
	 * 
	 * @param st
	 */
	AdvertDao1 data_2;
	ArrayList<AdvertDao1> datas_2;
	private void load_qiandao_2(int datatype_id_2) {
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_article_datatype_size_list?channel_name=signup&category_id=0&datatype_id="+datatype_id_2+""
				+ "&page_size=100&page_index=1&strwhere=is_top=1&orderby=id%20desc",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {

								super.onSuccess(arg0, arg1);
									try {
										System.out.println("活动置顶========2============" + arg1);
										JSONObject jsonObject = new JSONObject(arg1);
										String status = jsonObject.getString("status");
										String info = jsonObject.getString("info");
										if (status.equals("y")) {
											JSONArray jsonArray = jsonObject.getJSONArray("data");
											datas_2 = new ArrayList<AdvertDao1>();
											for (int i = 0; i < jsonArray.length(); i++) {
//											for (int i = 0; i < 1; i++) {
												data_2 = new AdvertDao1();
												JSONObject object = jsonArray.getJSONObject(i);
												data_2.setId(object.getString("id"));
												// data.img_url = object.getString("img_url");
												data_2.setImg_url(RealmName.REALM_NAME_HTTP+ object.getString("img_url"));
												datas_2.add(data_2);
											}
											data_2 = null;
											System.out.println("datatype_id------------------2--------------------"+datatype_id);
											if (datas_2.size() == 1) {
												System.out.println("datas_2.size()------------------2--------------------"+datas_2.size());
												iv_zhuti_tp_2.setVisibility(View.GONE);
//												advPager_2.setVisibility(View.GONE);
//												mAq.id(iv_zhuti_tp_2).image(datas_2.get(0).getImg_url());
											}else {
												Message msg = new Message();
												msg.obj = datas_2;
												msg.what = 0;
												childHandler1.sendMessage(msg);
											}
										} else {
											 zt_type_2 = true;
											 advPager_2.setVisibility(View.GONE);
//											 iv_zhuti_tp.setBackgroundResource(R.drawable.ic_launcher);
//											 Toast.makeText(getActivity(), info,  Toast.LENGTH_SHORT).show();
										}
										progress.CloseProgress();

									} catch (Exception e) {

										e.printStackTrace();
									}
								}
								
							@Override
							public void onFailure(Throwable arg0, String arg1) {

								super.onFailure(arg0, arg1);
									progress.CloseProgress();
									Toast.makeText(getActivity(), "超时异常", Toast.LENGTH_SHORT).show();
							}
						}, getActivity());
	}
	
	/**
	 * 广告
	 */
	ArrayList<AdvertDao1> tempss_1;
	ArrayList<String> urls_1;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {
					tempss_1 = (ArrayList<AdvertDao1>) msg.obj;
					urls_1 = new ArrayList<String>();
					for (int i = 0; i < tempss_1.size(); i++) {
						urls_1.add(tempss_1.get(i).getImg_url());
					}
					System.out.println("urls_1.size()---------1----------"+urls_1.size());
					ImageLoader imageLoader = ImageLoader.getInstance();
					advPager_1.setData(urls_1, new MyPosterOnClick() {
						@Override
						public void onMyclick(int position) {

							try {
								String article_id = tempss_1.get(position).getId();
								System.out.println("article_id==============="+ article_id);
								if (!nickname.equals("")) {
									if (!user_name.equals("")) {
										Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
										intent.putExtra("id", article_id);
										intent.putExtra("datatype_id", String.valueOf(datatype_id));
										startActivity(intent);
									} else {
										Intent intent = new Intent(getActivity(),TishiWxBangDingActivity.class);
										startActivity(intent);
									}
								} else {
									if (user_name.equals("")) {
										Intent intent = new Intent(getActivity(),UserLoginActivity.class);
										startActivity(intent);
									} else {
										Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
										intent.putExtra("id", article_id);
										intent.putExtra("datatype_id", String.valueOf(datatype_id));
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
	
	/**
	 * 广告
	 */
	ArrayList<AdvertDao1> tempss_2;
	ArrayList<String> urls_2;
	private Handler childHandler1 = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {
					tempss_2 = (ArrayList<AdvertDao1>) msg.obj;
					urls_2 = new ArrayList<String>();
					for (int i = 0; i < tempss_2.size(); i++) {
						urls_2.add(tempss_2.get(i).getImg_url());
					}
					System.out.println("urls_2.size()-----------2--------"+urls_2.size());
					ImageLoader imageLoader = ImageLoader.getInstance();
					advPager_2.setData(urls_2, new MyPosterOnClick() {
						@Override
						public void onMyclick(int position) {

							try {
								String article_id = tempss_2.get(position).getId();
								System.out.println("article_id==============="+ article_id);
								if (!nickname.equals("")) {
									if (!user_name.equals("")) {
										Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
										intent.putExtra("id", article_id);
										intent.putExtra("datatype_id", String.valueOf(datatype_id));
										startActivity(intent);
									} else {
										Intent intent = new Intent(getActivity(),TishiWxBangDingActivity.class);
										startActivity(intent);
									}
								} else {
									if (user_name.equals("")) {
										Intent intent = new Intent(getActivity(),UserLoginActivity.class);
										startActivity(intent);
									} else {
										Intent intent = new Intent(getActivity(),ZhongAnMinShenXqActivity.class);
										intent.putExtra("id", article_id);
										intent.putExtra("datatype_id", String.valueOf(datatype_id));
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
