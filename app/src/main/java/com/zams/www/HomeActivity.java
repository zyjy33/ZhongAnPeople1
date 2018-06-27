package com.zams.www;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.QiYeJinMianAdaper;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.example.taobaohead.BeanVo;
import com.example.taobaohead.headview.ScrollTopView;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.EndowmentBankActivity;
import com.hengyushop.demo.home.FenXiangActivity;
import com.hengyushop.demo.home.HealthGunaActivity;
import com.hengyushop.demo.home.JuDuiHuanActivity;
import com.hengyushop.demo.home.JuTouTiaoActivity;
import com.hengyushop.demo.home.JuYouFangActivity;
import com.hengyushop.demo.home.JuYunshangActivity;
import com.hengyushop.demo.home.SouSuoSpActivity;
import com.hengyushop.demo.home.XinshouGyActivity;
import com.hengyushop.demo.home.ZhongAnYlActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.shopcart.TuiJianSpListActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.WareInformationData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PagerScrollView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.home.SignInActivity;
import com.zams.www.notice.SystemNoticeActivity;
import com.zams.www.utils.AccountUtils;
import com.zams.www.utils.UiUtils;
import com.zams.www.weiget.PermissionSetting;
import com.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Fragment implements OnClickListener {
    private ImageView yh0, yh3, yh6, yh10, yh16, yh14, yh19, yh22, yh25;
    ImageView iv_home_tp1, iv_home_tp2, iv_home_tp3, iv_home_tp4, iv_home_tp5, iv_home_tp6, iv_home_tp7, iv_home_tp8;
    private TextView yh1, yh2, yh4, yh5, yh7, yh8, yh11, yh12, yh17, yh18,
            yh141, yh142, yh20, yh21, yh23, yh24, yh26, yh27;
    private LinearLayout yh_0, yh_1, yh_2, yh_3, yh_4, yh_5, yh_6, yh_7, yh_8;

    private WareDao wareDao;
    private ImageView img_user;
    private ImageView img_shared;
    private Thread thread;
    private Handler handler3;
    private Context context;
    private TextView img_demo3_1, img_demo3_0, img_demo2_0, img_demo2_1;
    private PagerScrollView home_main_scrool;
    private RelativeLayout home_title_layout;
    private LinearLayout ll_buju;
    private EditText tv1;
    private LayoutInflater inflater;
    protected PopupWindow pop;
    private View view;
    private ImageButton btn_wechat;
    private View btn_sms;
    private View btn_wx_friend;
    private ImageButton img_btn_tencent;
    private IWXAPI api;
    private LinearLayout ll_jutoutiao, ll_sousuo;
    private ArrayList<BeanVo> list;
    private GridView gridview;
    ScrollTopView mytaobao;
    private MyPosterView advPager = null; //广告
    private LinearLayout vip0, vip1, second_main_l4, second_main_l3,
            second_main_l2, item0, item1, item2, item3, layout2, index_item4,
            index_item0, index_item1, index_item2, index_item3, index_item6,
            index_item7, index_item5, ll_rxzq, ll_tjsp;
    private ImageView iv_xsgl;
    private LinearLayout main_fragment_viewpager;
    private MyPosterView posterView;
    private int screenHeight;
    private SharedPreferences spPreferences;
    String article_id, goods_id;
    String id;
    String group_id;
    private MyGridView myGridView;
    private ImageView iv_imagr1, iv_imagr2, iv_imagr3, iv_imagr4;
    private ImageView iv_zhuangti1, iv_zhuangti2, iv_pt1, iv_pt2, iv_pt3,
            iv_pt4;
    private TextView tv_text1, tv_jiaguo1, tv_scj1, tv_goumai1, tv_text2,
            tv_jiaguo2, tv_scj2, tv_goumai2, tv_text3, tv_jiaguo3, tv_scj3,
            tv_goumai3, tv_text4, tv_jiaguo4, tv_scj4, tv_goumai4;
    private LinearLayout ll_sp1, ll_sp2, ll_sp3, ll_sp4, ll_sp5;
    String user_id;
    public static AQuery mAq;
    private ListView new_list;
    private DialogProgress progress;
    public static boolean type = false;
    private ArrayList<JuTuanGouData> list_ll = null;
    View layout;
    private ImageView redPackageImg;
    private View mRedPoint;
    private SharedPreferences mNoticeSp;


    public HomeActivity() {

    }

    private ImageLoader imageLoader;
    private String key;
    private String yth;

    public HomeActivity(ImageLoader imageLoader, Handler handler3,
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

        layout = inflater.inflate(R.layout.hengyu_home, null);
        progress = new DialogProgress(getActivity());
        tv1 = (EditText) layout.findViewById(R.id.tv1);
        // tv1.getBackground().setAlpha(50);
        ll_sousuo = (LinearLayout) layout.findViewById(R.id.ll_sousuo);
        ll_sousuo.getBackground().setAlpha(70);
        mAq = new AQuery(getActivity());
        new_list = (ListView) layout.findViewById(R.id.new_list);
        mRedPoint = layout.findViewById(R.id.red_point);
        ImageView iv_sousuo = (ImageView) layout.findViewById(R.id.iv_sousuo);
        iv_sousuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), SouSuoSpActivity.class);
                String strwhere_zhi = tv1.getText().toString().trim();
                intent.putExtra("strwhere_zhi", strwhere_zhi);
                intent.putExtra("home_sousuo", "home_sousuo");
                startActivity(intent);
            }
        });

        tv1.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1,
                                          KeyEvent arg2) {

                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(getActivity(),
                            SouSuoSpActivity.class);
                    String strwhere_zhi = tv1.getText().toString().trim();
                    intent.putExtra("strwhere_zhi", strwhere_zhi);
                    intent.putExtra("home_sousuo", "home_sousuo");
                    startActivity(intent);
                }
                return false;
            }

        });

        img_user = (ImageView) layout.findViewById(R.id.img_user);
        img_shared = (ImageView) layout.findViewById(R.id.img_shared);
        img_user.setBackgroundResource(R.drawable.saoyisao);
        img_shared.setBackgroundResource(R.drawable.message_icon_2);
        gridview = (GridView) layout.findViewById(R.id.gridView);
        redPackageImg = ((ImageView) layout.findViewById(R.id.red_package_img));
        redPackageImg.getLayoutParams().height = (int) (UiUtils.getScreenWidth() * 0.16);
        redPackageImg.setOnClickListener(this);
        spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
        mNoticeSp = getActivity().getSharedPreferences(Constant.SP_NOTICE, Context.MODE_PRIVATE);
        requestRedPackage();
        initLayout(layout);
        getguangao();
        loadWeather();
        load_P();// 商品详情
        return layout;

    }

    public void onPause() {
        super.onPause();
        if (posterView != null) {
            posterView.puseExecutorService();
        }
    }

    ;

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
    //	String user_name_key = "";
    String oauth_name;
    String datall;
    SharedPreferences spPreferences_login;

    @Override
    public void onResume() {

        super.onResume();
        boolean isShowRed = mNoticeSp.getBoolean(Constant.SHOW_RED_POINT, false);
        if (isShowRed && AccountUtils.hasBoundPhone()) {
            mRedPoint.setVisibility(View.VISIBLE);
        } else {
            mRedPoint.setVisibility(View.GONE);
        }
        try {

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
                    // JSONObject obj = object.getJSONObject("data");
                    // data.id = obj.getString("id");
                    // data.user_name = obj.getString("user_name");
                    // province = obj.getString("province");
                    // city = obj.getString("city");
                    // area = obj.getString("area");

                    System.out.println("datall==============" + datall);
                    if (datall.equals("null")) {

                        SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi",
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

                            String user_name = spPreferences.getString("user", "");
                            System.out.println("---2-------------------" + user_name);
                        }
                    }
                    getuserxinxi();
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            ;
        }, getActivity());

    }

    private void getuserxinxi() {

        try {

            spPreferences = getActivity().getSharedPreferences("longuserset",
                    Context.MODE_PRIVATE);
            user_name_phone = spPreferences.getString("user", "");
            System.out.println("user_name_phone================" + user_name_phone);


            // 接口调用user_name的参数值
            //			if (!user_name_phone.equals("")) {
            //				user_name_key = user_name_phone;
            //			}

            if (!user_name_phone.equals("")) {
                user_name = user_name_phone;
                user_id = spPreferences.getString("user_id", "");
            } else {
                user_name = "";
            }

            System.out.println("user_name================" + user_name);
            group_id = spPreferences.getString("group_id", "");
            System.out.println("======group_id======1=======" + group_id);

        } catch (Exception e) {

            e.printStackTrace();
        }
        // 扫一扫
        img_user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!nickname.equals("")) {
                    if (!user_name.equals("")) {
                        AndPermission.with(getActivity())
                                .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                                .onGranted(new Action() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        Intent Intent2 = new Intent(getActivity(), CaptureActivity.class);
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
                        // String group_id = spPreferences.getString("group_id",
                        // "");
                        System.out.println("group_id======1========="
                                + group_id);
                        if (group_id.equals("")) {
                            Intent intent48 = new Intent(getActivity(),
                                    UserLoginActivity.class);
                            startActivity(intent48);
                        } else {
                            AndPermission.with(getActivity())
                                    .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                                    .onGranted(new Action() {
                                        @Override
                                        public void onAction(List<String> permissions) {
                                            Intent Intent2 = new Intent(getActivity(), CaptureActivity.class);
                                            startActivity(Intent2);
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
    }


    /**
     * 红包专区
     */
    QiYeJinMianAdaper jdhadapter;
    private ArrayList<SpListData> lists;

    private void load_list() {
        lists = new ArrayList<SpListData>();
        try {
            AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_page_size_list?channel_name=goods&category_id=2955&page_size=3&page_index=1&strwhere=&orderby=",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            super.onSuccess(arg0, arg1);
                            System.out.println("热销专区=====================" + arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                String status = jsonObject.getString("status");
                                String info = jsonObject.getString("info");
                                if (status.equals("y")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    //									 len = jsonArray.length();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        SpListData spList = new SpListData();
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        spList.id = object.getString("id");
                                        spList.img_url = object.getString("img_url");
                                        spList.title = object.getString("title");
                                        spList.market_price = object.getString("market_price");
                                        spList.sell_price = object.getString("sell_price");
                                        spList.category_id = object.getString("category_id");
                                        lists.add(spList);
                                    }
                                    //									spList = null;
                                } else {
                                    //										progress.CloseProgress();
                                    Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                }
                                //									if(len!=0){
                                //										CURRENT_NUM =CURRENT_NUM+1;
                                //									}

                                System.out.println("lists.size()=====================" + lists.size());

                                //									jdhadapter = new QiYeJinMianAdaper(lists, getActivity());
                                //									myGridView.setAdapter(jdhadapter);

                                //									if (lists.size() > 0) {
                                //										QiYeJinMianAdaper.mAq.clear();
                                //									}
                                //									setListViewHeightBasedOnChildren(myGridView);
                                //									myGridView.setOnItemClickListener(new OnItemClickListener() {
                                //							            @Override
                                //							            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                //											System.out.println("====================="+lists.get(arg2).id);
                                //											Intent intent = new Intent(getActivity(),HongBaoZqListActivity.class);
                                //											intent.putExtra("category_id", lists.get(arg2).category_id);
                                //											intent.putExtra("type_zhi", String.valueOf(arg2));
                                //											startActivity(intent);
                                //							            }
                                //							        });
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    }, null);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    //	private void load_list() {
    //		try {
    //			list_ll = new ArrayList<JuTuanGouData>();
    //			AsyncHttp.get(RealmName.REALM_NAME_LL
    //					+ "/get_game_groupon_list?page_size=" + 2 + ""
    //					+ "&page_index=" + 1 + "&channel_name=groupon&category_id="
    //					+ 909 + "", new AsyncHttpResponseHandler() {
    //				@Override
    //				public void onSuccess(int arg0, String arg1) {
    //
    //					super.onSuccess(arg0, arg1);
    //					System.out.println("输出所有拼团活动列表=========" + arg1);
    //					try {
    //						JSONObject object = new JSONObject(arg1);
    //						String status = object.getString("status");
    //						String info = object.getString("info");
    //						if (status.equals("y")) {
    //							JSONArray jsonArray = object.getJSONArray("data");
    //							for (int i = 0; i < jsonArray.length(); i++) {
    //								JSONObject obj = jsonArray.getJSONObject(i);
    //								JuTuanGouData data = new JuTuanGouData();
    //								data.setId(obj.getString("id"));
    //								data.setTitle(obj.getString("title"));
    //								data.setImg_url(obj.getString("img_url"));
    //								data.setArticle_id(obj.getString("article_id"));
    //								data.setPrice(obj.getString("price"));
    //								data.setPeople(obj.getString("people"));
    //								data.setGroupon_price(obj
    //										.getString("groupon_price"));
    //								data.setAdd_time(obj.getString("add_time"));
    //								data.setUpdate_time(obj
    //										.getString("update_time"));
    //								data.setCategory_id(obj
    //										.getString("category_id"));
    //								list_ll.add(data);
    //							}
    //							System.out.println("---------------------"
    //									+ list_ll.size());
    //							// Toast.makeText(JuTuanGouActivity.this, info,
    //							//  Toast.LENGTH_SHORT).show();
    //						} else {
    //							// Toast.makeText(JuTuanGouActivity.this, info,
    //							//  Toast.LENGTH_SHORT).show();
    //						}
    //						JuJingCaiAdapter juJingCaiAdapter = new JuJingCaiAdapter(
    //								getActivity(), list_ll);
    //						new_list.setAdapter(juJingCaiAdapter);
    //						setListViewHeightBasedOnChildren(new_list);
    //					} catch (JSONException e) {
    //
    //						e.printStackTrace();
    //					}
    //				}
    //
    //			}, getActivity());
    //		} catch (Exception e) {
    //
    //			e.printStackTrace();
    //		}
    //	}

    // 聚头条
    private void loadWeather() {

        String strUrl = RealmName.REALM_NAME_LL
                + "/get_article_category_id_list?channel_name=news&category_id=3"
                + "&top=10&strwhere=";

        System.out.println("聚头条" + strUrl);
        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {
                super.onSuccess(arg0, arg1);
                parse1(arg1);
            }
        }, null);
    }

    //聚头条
    BeanVo data_1 = null;

    private void parse1(String st) {
        try {
            JSONObject jsonObject = new JSONObject(st);
            // System.out.println("聚头条====================" + st);
            String status = jsonObject.getString("status");
            if (status.equals("y")) {
                list = new ArrayList<BeanVo>();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                data_1 = new BeanVo();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    data_1.title = object.getString("title");
                    data_1.img_url = object.getString("img_url");
                    list.add(data_1);
                }
                data_1 = null;
                Message msg = new Message();
                msg.what = 8;
                msg.obj = list;
                handler.sendMessage(msg);
                // load_P();//商品详情
            } else {

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 商品详情
     *
     * @param
     */
    private void load_P() {
        // progress.CreateProgress();
        AsyncHttp
                .get(RealmName.REALM_NAME_LL
                                + "/get_article_top_list?channel_name=goods&top=6&strwhere=link_url=''%20and%20is_top=1&status=0",
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

                                    System.out.println("11=================================" + arg0);
                                    System.out.println("22=================================" + arg1);
                                    progress.CloseProgress();
                                    // Toast.makeText(getActivity(), "超时异常",
                                    //  Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }
                        }, context);

    }

    /**
     * 商品详情
     *
     * @param st
     */
    JSONObject object;
    WareInformationData data;
    ArrayList<WareInformationData> datas = null;

    private void parse2(String st) {
        try {

            System.out.println("商品详情====================" + st);
            JSONObject jsonObject = new JSONObject(st);
            String status = jsonObject.getString("status");
            String info = jsonObject.getString("info");
            if (status.equals("y")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                datas = new ArrayList<WareInformationData>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    data = new WareInformationData();
                    object = jsonArray.getJSONObject(i);
                    data.id = object.getInt("id");
                    data.img_url = object.getString("img_url");
                    data.title = object.getString("title");
                    data.sell_price = object.getString("sell_price");
                    data.marketPrice = object.getString("market_price");
                    // data.article_id = object.getString("article_id");
                    // data.goods_id = object.getString("goods_id");
                    // goods_id = data.goods_id;
                    // article_id = data.article_id;
                    datas.add(data);
                }

                //				data = null;
                //				datas = null;
                //				object = null;
                Message msg = new Message();
                msg.what = 110;
                msg.obj = datas;
                handler.sendMessage(msg);
            } else {
                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
            }

            progress.CloseProgress();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    private void initLayout(View layout) {

        iv_zhuangti1 = (ImageView) layout.findViewById(R.id.iv_zhuangti1);
        iv_zhuangti2 = (ImageView) layout.findViewById(R.id.iv_zhuangti2);
        iv_zhuangti1.setBackgroundResource(R.drawable.zt1);
        iv_zhuangti2.setBackgroundResource(R.drawable.zt2);
        myGridView = (MyGridView) layout.findViewById(R.id.gridView);
        iv_pt1 = (ImageView) layout.findViewById(R.id.iv_pt1);
        iv_pt2 = (ImageView) layout.findViewById(R.id.iv_pt2);
        iv_pt3 = (ImageView) layout.findViewById(R.id.iv_pt3);
        iv_pt4 = (ImageView) layout.findViewById(R.id.iv_pt4);
        iv_pt1.setBackgroundResource(R.drawable.pt1);
        iv_pt2.setBackgroundResource(R.drawable.pt2);
        iv_pt3.setBackgroundResource(R.drawable.pt3);
        iv_pt4.setBackgroundResource(R.drawable.pt4);

        iv_home_tp1 = (ImageView) layout.findViewById(R.id.iv_home_tp1);
        iv_home_tp2 = (ImageView) layout.findViewById(R.id.iv_home_tp2);
        iv_home_tp3 = (ImageView) layout.findViewById(R.id.iv_home_tp3);
        iv_home_tp4 = (ImageView) layout.findViewById(R.id.iv_home_tp4);
        iv_home_tp5 = (ImageView) layout.findViewById(R.id.iv_home_tp5);
        iv_home_tp6 = (ImageView) layout.findViewById(R.id.iv_home_tp6);
        iv_home_tp7 = (ImageView) layout.findViewById(R.id.iv_home_tp7);
        iv_home_tp8 = (ImageView) layout.findViewById(R.id.iv_home_tp8);

        Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_shg);
        BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
        iv_home_tp1.setBackgroundDrawable(bd1);
        Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_tcg);
        BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
        iv_home_tp2.setBackgroundDrawable(bd2);
        Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_jkg);
        BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
        iv_home_tp3.setBackgroundDrawable(bd3);
        Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_ppsj);
        BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
        iv_home_tp4.setBackgroundDrawable(bd4);
        Bitmap bm5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_zayl);
        BitmapDrawable bd5 = new BitmapDrawable(this.getResources(), bm5);
        iv_home_tp5.setBackgroundDrawable(bd5);
        Bitmap bm6 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_ylyh);
        BitmapDrawable bd6 = new BitmapDrawable(this.getResources(), bm6);
        iv_home_tp6.setBackgroundDrawable(bd6);
        Bitmap bm7 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_jfg);
        BitmapDrawable bd7 = new BitmapDrawable(this.getResources(), bm7);
        iv_home_tp7.setBackgroundDrawable(bd7);
        Bitmap bm8 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sy_ptg);
        BitmapDrawable bd8 = new BitmapDrawable(this.getResources(), bm8);
        iv_home_tp8.setBackgroundDrawable(bd8);

        ll_sp1 = (LinearLayout) layout.findViewById(R.id.ll_sp1);
        ll_sp2 = (LinearLayout) layout.findViewById(R.id.ll_sp2);
        ll_sp3 = (LinearLayout) layout.findViewById(R.id.ll_sp3);
        ll_sp4 = (LinearLayout) layout.findViewById(R.id.ll_sp4);
        iv_imagr1 = (ImageView) layout.findViewById(R.id.iv_imagr1);
        iv_imagr2 = (ImageView) layout.findViewById(R.id.iv_imagr2);
        iv_imagr3 = (ImageView) layout.findViewById(R.id.iv_imagr3);
        iv_imagr4 = (ImageView) layout.findViewById(R.id.iv_imagr4);
        tv_text1 = (TextView) layout.findViewById(R.id.tv_text1);
        tv_text2 = (TextView) layout.findViewById(R.id.tv_text2);
        tv_text3 = (TextView) layout.findViewById(R.id.tv_text3);
        tv_text4 = (TextView) layout.findViewById(R.id.tv_text4);
        tv_jiaguo1 = (TextView) layout.findViewById(R.id.tv_jiaguo1);
        tv_jiaguo2 = (TextView) layout.findViewById(R.id.tv_jiaguo2);
        tv_jiaguo3 = (TextView) layout.findViewById(R.id.tv_jiaguo3);
        tv_jiaguo4 = (TextView) layout.findViewById(R.id.tv_jiaguo4);
        tv_scj1 = (TextView) layout.findViewById(R.id.tv_scj1);
        tv_scj2 = (TextView) layout.findViewById(R.id.tv_scj2);
        tv_scj3 = (TextView) layout.findViewById(R.id.tv_scj3);
        tv_scj4 = (TextView) layout.findViewById(R.id.tv_scj4);
        tv_goumai1 = (TextView) layout.findViewById(R.id.tv_goumai1);
        tv_goumai2 = (TextView) layout.findViewById(R.id.tv_goumai2);
        tv_goumai3 = (TextView) layout.findViewById(R.id.tv_goumai3);
        tv_goumai4 = (TextView) layout.findViewById(R.id.tv_goumai4);

        home_main_scrool = (PagerScrollView) layout
                .findViewById(R.id.home_main_scrool);
        home_title_layout = (RelativeLayout) layout
                .findViewById(R.id.home_title_layout);

        // main_fragment_viewpager = (LinearLayout)
        // layout.findViewById(R.id.main_fragment_viewpager);
        // posterView = new MyPosterView(context, null);
        // main_fragment_viewpager.addView(posterView);

        index_item6 = (LinearLayout) layout.findViewById(R.id.index_item6);
        index_item4 = (LinearLayout) layout.findViewById(R.id.index_item4);
        index_item7 = (LinearLayout) layout.findViewById(R.id.index_item7);
        index_item5 = (LinearLayout) layout.findViewById(R.id.index_item5);

        index_item1 = (LinearLayout) layout.findViewById(R.id.index_item1);
        index_item2 = (LinearLayout) layout.findViewById(R.id.index_item2);
        index_item3 = (LinearLayout) layout.findViewById(R.id.index_item3);
        index_item0 = (LinearLayout) layout.findViewById(R.id.index_item0);
        ll_rxzq = (LinearLayout) layout.findViewById(R.id.ll_rxzq);
        ll_tjsp = (LinearLayout) layout.findViewById(R.id.ll_tjsp);
        //		ll_rxzq.setOnClickListener(this);
        // index_item4.setOnClickListener(this);
        // index_item7.setOnClickListener(this);
        // index_item6.setOnClickListener(this);
        // index_item5.setOnClickListener(this);
        // index_item1.setOnClickListener(this);
        // index_item2.setOnClickListener(this);
        // index_item3.setOnClickListener(this);
        // index_item0.setOnClickListener(this);
        img_shared.setOnClickListener(this);

        index_item4.setOnClickListener(this);
        index_item7.setOnClickListener(this);
        index_item6.setOnClickListener(this);
        index_item5.setOnClickListener(this);
        index_item1.setOnClickListener(this);
        index_item2.setOnClickListener(this);
        index_item3.setOnClickListener(this);
        index_item0.setOnClickListener(this);


        yh_0 = (LinearLayout) layout.findViewById(R.id.yh_0);
        yh_1 = (LinearLayout) layout.findViewById(R.id.yh_1);
        yh_2 = (LinearLayout) layout.findViewById(R.id.yh_2);
        yh_3 = (LinearLayout) layout.findViewById(R.id.yh_3);
        yh_4 = (LinearLayout) layout.findViewById(R.id.yh_4);
        yh_5 = (LinearLayout) layout.findViewById(R.id.yh_5);
        yh_6 = (LinearLayout) layout.findViewById(R.id.yh_6);
        yh_7 = (LinearLayout) layout.findViewById(R.id.yh_7);
        yh_8 = (LinearLayout) layout.findViewById(R.id.yh_8);

        yh_0.setOnClickListener(this);
        yh_1.setOnClickListener(this);
        yh_2.setOnClickListener(this);
        yh_3.setOnClickListener(this);
        yh_4.setOnClickListener(this);
        yh_5.setOnClickListener(this);
        yh_6.setOnClickListener(this);
        yh_7.setOnClickListener(this);
        yh_8.setOnClickListener(this);


        //		ll_rxzq.setOnClickListener(new OnClickListener() {
        //
        //			@Override
        //			public void onClick(View arg0) {
        //
        //				Intent intent = new Intent(getActivity(),HongBaoZqListActivity.class);
        //				intent.putExtra("channel_name", "life");
        //				startActivity(intent);
        //			}
        //		});

        //HongBaoZqListActivity

        ll_tjsp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TuiJianSpListActivity TuiJianGoodsListActivity
                Intent intent = new Intent(getActivity(), TuiJianSpListActivity.class);
                intent.putExtra("title", "每日劲爆品");
                startActivity(intent);
            }
        });


        yh_0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Message msg = new Message();
                // msg.what = 30;
                // msg.arg1 = datas.get(0).id;
                // handler.sendMessage(msg);
                String id = Integer.toString(datas.get(0).id);
                System.out.println("=====================" + id);
                Intent intent30 = new Intent(getActivity(),
                        WareInformationActivity.class);
                intent30.putExtra("id", id);
                startActivity(intent30);
            }
        });
        yh_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String id = Integer.toString(datas.get(1).id);
                System.out.println("=====================" + id);
                Intent intent30 = new Intent(getActivity(),
                        WareInformationActivity.class);
                intent30.putExtra("id", id);
                startActivity(intent30);
            }
        });
        //		yh_2.setOnClickListener(new OnClickListener() {
        //
        //			@Override
        //			public void onClick(View arg0) {
        //
        //				String id = Integer.toString(datas.get(3).id);
        //				System.out.println("=====================" + id);
        //				Intent intent30 = new Intent(getActivity(),
        //						WareInformationActivity.class);
        //				intent30.putExtra("id", id);
        //				startActivity(intent30);
        //			}
        //		});
        yh_3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String id = Integer.toString(datas.get(2).id);
                System.out.println("=====================" + id);
                Intent intent30 = new Intent(getActivity(),
                        WareInformationActivity.class);
                intent30.putExtra("id", id);
                startActivity(intent30);
            }
        });
        yh_4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String id = Integer.toString(datas.get(3).id);
                System.out.println("=====================" + id);
                Intent intent30 = new Intent(getActivity(),
                        WareInformationActivity.class);
                intent30.putExtra("id", id);
                startActivity(intent30);
            }
        });
        //		yh_5.setOnClickListener(new OnClickListener() {
        //			@Override
        //			public void onClick(View arg0) {
        //				String id = Integer.toString(datas.get(6).id);
        //				System.out.println("=====================" + id);
        //				Intent intent30 = new Intent(getActivity(),
        //						WareInformationActivity.class);
        //				intent30.putExtra("id", id);
        //				startActivity(intent30);
        //			}
        //		});
        yh_6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String id = Integer.toString(datas.get(4).id);
                System.out.println("=====================" + id);
                Intent intent30 = new Intent(getActivity(),
                        WareInformationActivity.class);
                intent30.putExtra("id", id);
                startActivity(intent30);
            }
        });
        yh_7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String id = Integer.toString(datas.get(5).id);
                System.out.println("=====================" + id);
                Intent intent30 = new Intent(getActivity(),
                        WareInformationActivity.class);
                intent30.putExtra("id", id);
                startActivity(intent30);
            }
        });


        yh0 = (ImageView) layout.findViewById(R.id.yh0);
        yh1 = (TextView) layout.findViewById(R.id.yh1);
        yh2 = (TextView) layout.findViewById(R.id.yh2);
        yh3 = (ImageView) layout.findViewById(R.id.yh3);
        yh4 = (TextView) layout.findViewById(R.id.yh4);
        yh5 = (TextView) layout.findViewById(R.id.yh5);
        yh6 = (ImageView) layout.findViewById(R.id.yh6);
        yh7 = (TextView) layout.findViewById(R.id.yh7);
        yh8 = (TextView) layout.findViewById(R.id.yh8);
        yh16 = (ImageView) layout.findViewById(R.id.yh16);
        yh17 = (TextView) layout.findViewById(R.id.yh17);
        yh18 = (TextView) layout.findViewById(R.id.yh18);
        yh14 = (ImageView) layout.findViewById(R.id.yh14);
        yh141 = (TextView) layout.findViewById(R.id.yh141);
        yh142 = (TextView) layout.findViewById(R.id.yh142);
        yh19 = (ImageView) layout.findViewById(R.id.yh19);
        yh20 = (TextView) layout.findViewById(R.id.yh20);
        yh21 = (TextView) layout.findViewById(R.id.yh21);
        yh22 = (ImageView) layout.findViewById(R.id.yh22);
        yh23 = (TextView) layout.findViewById(R.id.yh23);
        yh24 = (TextView) layout.findViewById(R.id.yh24);
        yh25 = (ImageView) layout.findViewById(R.id.yh25);
        yh26 = (TextView) layout.findViewById(R.id.yh26);
        yh27 = (TextView) layout.findViewById(R.id.yh27);

        yh10 = (ImageView) layout.findViewById(R.id.yh10);
        yh11 = (TextView) layout.findViewById(R.id.yh11);
        yh12 = (TextView) layout.findViewById(R.id.yh12);


        home_main_scrool.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("滑动" + arg0.getScrollY());
                        if (arg0.getScrollY() <= 1) {
                            img_user.setBackgroundResource(R.drawable.saoyisao);
                            img_shared.setBackgroundResource(R.drawable.message_icon_2);
                            home_title_layout.setBackgroundColor(getResources()
                                    .getColor(R.color.no_color));
                            // ll_sousuo.setBackgroundColor(getResources().getColor(R.color.no_color));
                            ll_sousuo.getBackground().setAlpha(70);
                            tv1.setBackgroundColor(getResources().getColor(
                                    R.color.no_color));
                        } else {
                            img_user.setBackgroundResource(R.drawable.sys_hs);
                            img_shared.setBackgroundResource(R.drawable.message_icon);
                            home_title_layout.setBackgroundColor(getResources()
                                    .getColor(R.color.white));
                            ll_sousuo.setBackgroundColor(getResources().getColor(
                                    R.color.baihuise));
                            tv1.setBackgroundColor(getResources().getColor(
                                    R.color.baihuise));
                        }
                        break;
                }
                return false;
            }
        });
        home_main_scrool.setAlwaysDrawnWithCacheEnabled(true);
        // format();

        // 聚头条
        mytaobao = (ScrollTopView) layout.findViewById(R.id.mytaobao);
        ll_jutoutiao = (LinearLayout) layout.findViewById(R.id.ll_jutoutiao);

        ll_jutoutiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getActivity(),
                        JuTouTiaoActivity.class);
                startActivity(intent);

            }
        });

        mytaobao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getActivity(),
                        JuTouTiaoActivity.class);
                startActivity(intent);

            }
        });

        // 新手攻略
        iv_xsgl = (ImageView) layout.findViewById(R.id.iv_xsgl);// get_article_page_size_list
        iv_xsgl.setBackgroundResource(R.drawable.xsgl);
        // Bitmap bitMap =
        // BitmapFactory.decodeResource(getResources(),R.drawable.xsgl);
        // iv_xsgl.setImageBitmap(bitMap);
        // bitMap.recycle(); //回收图片所占的内存

        iv_xsgl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String xinshougl = "zhi";
                Intent intent = new Intent(getActivity(),
                        XinshouGyActivity.class);
                // intent.putExtra("xsgl", xinshougl);
                startActivity(intent);
            }
        });
        // format();

    }

    /**
     * 获取红包专区数据
     */
    private void requestRedPackage() {
        AsyncHttp.get("http://mobile.zams.cn/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=22", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    String status = object.getString("status");
                    if ("y".equals(status)) {
                        JSONArray array = object.getJSONArray("data");
                        if (array.length() > 0) {
                            JSONObject jo = (JSONObject) array.get(0);
                            String ad_url = RealmName.REALM_NAME + jo.getString("ad_url");
                            Activity activity = getActivity();
                            if (activity != null) {
                                Glide.with(activity)
                                        .load(ad_url)
                                        .placeholder(getResources().getDrawable(R.drawable.red_package))
                                        .into(redPackageImg);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, getActivity());
    }


    ArrayList<AdvertDao1> images = null;

    private void getguangao() {

        // 广告滚动
        advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);
        AsyncHttp.get(RealmName.REALM_NAME_LL
                        + "/get_adbanner_list?advert_id=11",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            JSONArray array = object.getJSONArray("data");
                            int len = array.length();
                            images = new ArrayList<AdvertDao1>();
                            for (int i = 0; i < len; i++) {
                                AdvertDao1 ada = new AdvertDao1();
                                JSONObject json = array.getJSONObject(i);
                                ada.setId(json.getString("id"));
                                ada.setAd_url(json.getString("ad_url"));
                                ada.setLink_url(json.getString("link_url"));
                                ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
                                images.add(ada);
                            }
                            Message msg = new Message();
                            msg.obj = images;
                            msg.what = 0;
                            childHandler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, context);
    }


    /**
     * 广告
     */
    ArrayList<AdvertDao1> tempss;
    ArrayList<String> urls;
    private Handler childHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    tempss = (ArrayList<AdvertDao1>) msg.obj;
                    urls = new ArrayList<String>();
                    for (int i = 0; i < tempss.size(); i++) {
                        urls.add(tempss.get(i).getAd_url());
                    }
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    advPager.setData(urls, new MyPosterOnClick() {
                        @Override
                        public void onMyclick(int position) {
                            if (!TextUtils.isEmpty(nickname)) {
                                if (!TextUtils.isEmpty(user_name)) {
                                    String link_url = tempss.get(position).getLink_url();
                                    goWebOrInfoActivity(link_url);
                                } else {
                                    Intent intent2 = new Intent(getActivity(),
                                            TishiWxBangDingActivity.class);
                                    startActivity(intent2);
                                }
                            } else {
                                if (TextUtils.isEmpty(user_name)) {
                                    Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
                                    startActivity(intent48);
                                } else {
                                    String link_url = tempss.get(position).getLink_url();
                                    goWebOrInfoActivity(link_url);
                                }
                            }

                        }
                    }, true, imageLoader, true);

                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void goWebOrInfoActivity(String link_url) {
        if (link_url != null && link_url.contains("goods")) {
            int start = link_url.lastIndexOf("-") + 1;
            int end = link_url.lastIndexOf(".");
            String id = link_url.substring(start, end);
            System.out.println("id=============" + id);
            Intent intent = new Intent(getActivity(),
                    WareInformationActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else {
            Intent intent13 = new Intent(getActivity(),
                    Webview1.class);
            intent13.putExtra("link_url", link_url);
            startActivity(intent13);
        }
    }

    Handler handler = new Handler() {

        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0://
                    Intent intent = new Intent(getActivity(), NewWare.class);
                    intent.putExtra("channel_name", "life");
                    startActivity(intent);
                    break;
                case 1:
                    System.out.println("2=============");
                    Intent intent1 = new Intent(getActivity(), JuYouFangActivity.class);
                    startActivity(intent1);
                    break;
                case 2:
                    System.out.println("3=============");
                    // Toast.makeText(getActivity(), "功能正在完善",  Toast.LENGTH_SHORT).show();
                    // Intent intent2 = new Intent(getActivity(),
                    // JuYunshangActivity.class);
                    // startActivity(intent2);
                    Intent intent111 = new Intent(getActivity(),
                            HealthGunaActivity.class);
                    startActivity(intent111);
                    break;
                case 3:// 福利馆
                    System.out.println("4=============");
                    if (!nickname.equals("")) {
                        if (!user_name.equals("")) {
                            Intent Intent2 = new Intent(getActivity(), JuDuiHuanActivity.class);
                            startActivity(Intent2);
                        } else {
                            Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                            startActivity(intent2);
                        }
                    } else {
                        if (user_name.equals("")) {
                            Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
                            startActivity(intent48);
                        } else {
                            Intent intent481 = new Intent(getActivity(), JuDuiHuanActivity.class);
                            startActivity(intent481);
                        }
                    }
                    break;
                case 4:
                    Intent intent4 = new Intent(getActivity(),
                            ZhongAnYlActivity.class);
                    startActivity(intent4);
                    break;
                case 5:
                    System.out.println("6=============");
                    System.out.println("user_name================" + user_name);
                    System.out.println("user_name_3_wx=================" + user_name_3_wx);
                    if (!nickname.equals("")) {
                        if (!user_name.equals("")) {
                            Intent Intent2 = new Intent(getActivity(), EndowmentBankActivity.class);
                            startActivity(Intent2);
                        } else {
                            Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                            startActivity(intent2);
                        }
                    } else {
                        if (user_name.equals("")) {
                            Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
                            startActivity(intent48);
                        } else {
                            Intent intent12 = new Intent(getActivity(), EndowmentBankActivity.class);
                            startActivity(intent12);
                        }
                    }
                    break;
                case 6:
//                    System.out.println("7=============");
//                    // Toast.makeText(getActivity(), "功能正在完善",  Toast.LENGTH_SHORT).show();
//                    Intent intent6 = new Intent(getActivity(),
//                            JuYunshangActivity.class);
//                    startActivity(intent6);
                    Intent intent6 = new Intent(getActivity(), NewWare.class);
                    intent6.putExtra("channel_name", "feedback");
                    startActivity(intent6);
                    break;
                case 7: // TODO: 2018/6/22 zyjy
                    System.out.println("8=============");
                    if (!nickname.equals("")) {
                        if (!user_name.equals("")) {
                            Intent intent48 = new Intent(getActivity(),
                                    SignInActivity.class);
                            startActivity(intent48);
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
                            Intent intent48 = new Intent(getActivity(),
                                    SignInActivity.class);
                            startActivity(intent48);

                        }
                    }
                    // Toast.makeText(getActivity(), "功能正在完善",  Toast.LENGTH_SHORT).show();

                    break;
                case 8:
                    mytaobao.setData(list);
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
                                    SystemNoticeActivity.class);
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
                                        SystemNoticeActivity.class);
                                startActivity(intentll);
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 16:
                    // String text1 = (String) msg.obj;
                    // System.out.println("到这里了16---"+text1);
                    // softshareWxChat(text1);
                    break;
                case 110:
                    type = true;
                    int len = datas.size();
                    if (len > 0) {
                        try {
                            System.out.println("点击了=================");
                            mAq.id(yh0).image(RealmName.REALM_NAME_HTTP + datas.get(0).img_url);
                            yh1.setText(datas.get(0).title);
                            yh2.setText("￥" + datas.get(0).sell_price);

                            mAq.id(yh3).image(RealmName.REALM_NAME_HTTP + datas.get(1).img_url);
                            yh4.setText(datas.get(1).title);
                            yh5.setText("￥" + datas.get(1).sell_price);

                            mAq.id(yh16).image(RealmName.REALM_NAME_HTTP + datas.get(2).img_url);
                            yh17.setText(datas.get(2).title);
                            yh18.setText("￥" + datas.get(2).sell_price);

                            mAq.id(yh10).image(RealmName.REALM_NAME_HTTP + datas.get(3).img_url);
                            yh11.setText(datas.get(3).title);
                            yh12.setText("￥" + datas.get(3).sell_price);

                            mAq.id(yh19).image(RealmName.REALM_NAME_HTTP + datas.get(4).img_url);
                            yh20.setText(datas.get(4).title);
                            yh21.setText("￥" + datas.get(4).sell_price);

                            mAq.id(yh22).image(RealmName.REALM_NAME_HTTP + datas.get(5).img_url);
                            yh23.setText(datas.get(5).title);
                            yh24.setText("￥" + datas.get(5).sell_price);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }

                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 立即购买
     *
     * @param article_id
     * @param goods_id
     */
    private void loadgouwuche(String article_id, String goods_id) {
        try {
            // progress.CreateProgress();
            String user_name = spPreferences.getString("user", "");
            String user_id = spPreferences.getString("user_id", "");

            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/add_shopping_buy?user_id=" + user_id + "&user_name="
                            + user_name + "&article_id=" + article_id + "&goods_id="
                            + goods_id + "&quantity=" + 1 + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                String status = jsonObject.getString("status");
                                System.out.println("购物清单================"
                                        + arg1);
                                String info = jsonObject.getString("info");
                                if (status.equals("y")) {
                                    // progress.CloseProgress();
                                    JSONObject obj = jsonObject
                                            .getJSONObject("data");
                                    String id = obj.getString("id");
                                    String count = obj.getString("count");
                                    Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT)
                                            .show();
                                    Intent intent = new Intent(getActivity(),
                                            MyOrderConfrimActivity.class);
                                    intent.putExtra("shopping_ids", id);
                                    startActivity(intent);
                                    // finish();
                                } else {
                                    // progress.CloseProgress();
                                    Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT)
                                            .show();
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            System.out
                                    .println("==========================访问接口失败！");
                            System.out.println("========================="
                                    + arg0);
                            System.out.println("=========================="
                                    + arg1);
                            super.onFailure(arg0, arg1);
                        }

                    }, getActivity());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.index_item0:
                handler.sendEmptyMessage(0);
                break;
            case R.id.index_item1:
                handler.sendEmptyMessage(1);
                break;
            case R.id.index_item2:
                handler.sendEmptyMessage(2);
                break;
            case R.id.index_item3:
                handler.sendEmptyMessage(6);
                break;
            case R.id.index_item4:
                handler.sendEmptyMessage(4);
                break;
            case R.id.index_item5:
                handler.sendEmptyMessage(5);
                break;
            case R.id.index_item6:
                handler.sendEmptyMessage(3);
                break;
            case R.id.index_item7:
                handler.sendEmptyMessage(7);
                break;
            case R.id.img_shared:
                handler.sendEmptyMessage(15);
                break;
            case R.id.red_package_img:
                Intent intent = new Intent(getActivity(), NewWare.class);
                intent.putExtra("channel_name", "feedback");
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

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

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        System.out.println("MyPosterView.type=======1==========" + MyPosterView.type);
        System.out.println("HomeActivity.type=======1===============" + HomeActivity.type);
        try {
            if (MyPosterView.type == true) {
                try {
                    MyPosterView.mQuery.clear();
                    MyPosterView.type = false;
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
            if (HomeActivity.type == true) {
                HomeActivity.mAq.clear();
                HomeActivity.type = false;
            }

            if (QiYeJinMianAdaper.type == true) {
                QiYeJinMianAdaper.mAq.clear();
                QiYeJinMianAdaper.type = false;
            }

            if (lists.size() < 0) {
                lists.clear();
                lists = null;
            }

            if (datas.size() < 0) {
                datas.clear();
                datas = null;
            }

            BitmapDrawable bd1 = (BitmapDrawable) iv_home_tp1.getBackground();
            iv_home_tp1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd1.setCallback(null);
            bd1.getBitmap().recycle();
            BitmapDrawable bd2 = (BitmapDrawable) iv_home_tp2.getBackground();
            iv_home_tp2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd2.setCallback(null);
            bd2.getBitmap().recycle();
            BitmapDrawable bd3 = (BitmapDrawable) iv_home_tp3.getBackground();
            iv_home_tp3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd3.setCallback(null);
            bd3.getBitmap().recycle();
            BitmapDrawable bd4 = (BitmapDrawable) iv_home_tp4.getBackground();
            iv_home_tp4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd4.setCallback(null);
            bd4.getBitmap().recycle();
            BitmapDrawable bd5 = (BitmapDrawable) iv_home_tp5.getBackground();
            iv_home_tp5.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd5.setCallback(null);
            bd5.getBitmap().recycle();
            BitmapDrawable bd6 = (BitmapDrawable) iv_home_tp6.getBackground();
            iv_home_tp6.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd6.setCallback(null);
            bd6.getBitmap().recycle();
            BitmapDrawable bd7 = (BitmapDrawable) iv_home_tp7.getBackground();
            iv_home_tp7.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd7.setCallback(null);
            bd7.getBitmap().recycle();
            BitmapDrawable bd8 = (BitmapDrawable) iv_home_tp8.getBackground();
            iv_home_tp8.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd8.setCallback(null);
            bd8.getBitmap().recycle();
        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println("HomeActivity.type=======2==========" + HomeActivity.type);
        System.out.println("MyPosterView.type=======2===============" + MyPosterView.type);
    }

    ;
}
