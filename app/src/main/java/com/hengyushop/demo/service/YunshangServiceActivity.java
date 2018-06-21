package com.hengyushop.demo.service;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.airplane.adapter.ServiceListAdaper;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.WareInformationData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.UserLoginWayActivity;
import com.zams.www.health.HealthActivity;
import com.zams.www.phone.PhoneServiceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YunshangServiceActivity extends Fragment implements OnClickListener {
    private DialogProgress progress;
    String user_id;
    private SharedPreferences spPreferences;
    private ImageView iv_biaoti;
    private ImageView zams_fw_1, zams_fw_2, zams_fw_3, zams_fw_4, zams_fw_5, zams_fw_6, zams_fw_7,
            zams_fw_8, zams_fw_9;
    ArrayList<WareInformationData> datas;
    private MyGridView myGridView;
    private MyPosterView advPager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.activity_zams_service, null);
        progress = new DialogProgress(getActivity());
        spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
        //		user_name = spPreferences.getString("user", "");
        //		nickname = spPreferences.getString("nickname", "");
        ininate(layout);
        //		loadWeather();
        //		get_service_list();
        return layout;
    }

    String weixin = "";
    String qq = "";
    String nickname = "";
    String user_name = "";
    String user_name_phone = "";
    String oauth_name;
    String datall;

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
        nickname = spPreferences_login.getString("nickname", "");
        user_name_phone = spPreferences.getString("user", "");
        System.out.println("nickname=================" + nickname);
        if (!nickname.equals("")) {
            getjianche();//后台检测是否绑定手机
        } else {
            getuserxinxi();
        }

    }


    private void getjianche() {

        SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
        nickname = spPreferences_login.getString("nickname", "");
        String headimgurl = spPreferences_login.getString("headimgurl", "");
        String unionid = spPreferences_login.getString("unionid", "");
        String access_token = spPreferences_login.getString("access_token", "");
        String sex = spPreferences_login.getString("sex", "");

        System.out.println("UserLoginActivity=====================" + UserLoginActivity.oauth_name);
        System.out.println("UserLoginWayActivity=====================" + UserLoginWayActivity.oauth_name);

        if (UserLoginActivity.oauth_name.equals("weixin")) {
            oauth_name = "weixin";
        } else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
            oauth_name = "qq";
            unionid = "";
        }

        System.out.println("nickname-----1-----" + nickname);
        String nick_name = nickname.replaceAll("\\s*", "");
        System.out.println("nick_name-----2-----" + nick_name);

        //				String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
        //				"&province=&city=&country=&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";

        String oauth_openid = spPreferences_login.getString("oauth_openid", "");
        String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0217?nick_name=" + nick_name + "&sex=" + sex + "&avatar=" + headimgurl + "" +
                "&province=&city=&country=&oauth_name=" + oauth_name + "&oauth_unionid=" + unionid + "" +
                "&oauth_openid=" + oauth_openid + "";

        System.out.println("我的======11======1=======" + strUrlone);
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                System.out.println("我的======输出=====1========" + arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    //							if (status.equals("y")) {
                    datall = object.getString("data");
                    //								JSONObject obj = object.getJSONObject("data");
                    //								data.id = obj.getString("id");
                    //								data.user_name = obj.getString("user_name");
                    //								province = obj.getString("province");
                    //								city = obj.getString("city");
                    //								area = obj.getString("area");

                    System.out.println("datall==============" + datall);
                    if (datall.equals("null")) {

                        SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
                        weixin = spPreferences_tishi.getString("weixin", "");
                        qq = spPreferences_tishi.getString("qq", "");
                        System.out.println("=================weixin==" + weixin);
                        System.out.println("=================qq==" + qq);

                        System.out.println("UserLoginActivity.panduan====1==" + UserLoginActivity.panduan_tishi);
                        System.out.println("UserLoginWayActivity.panduan====2==" + UserLoginWayActivity.panduan_tishi);
                        if (!nickname.equals("")) {

                            if (UserLoginActivity.panduan_tishi == true) {
                                if (weixin.equals("weixin")) {
                                } else {
                                    Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                                    startActivity(intent1);
                                    UserLoginActivity.panduan_tishi = false;
                                }

                            } else if (UserLoginWayActivity.panduan_tishi == true) {
                                if (qq.equals("qq")) {
                                } else {
                                    Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
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
                        System.out.println("---data.user_name-------------------" + data.user_name);
                        System.out.println("---user_id-------------------" + user_id);
                        if (data.user_name.equals("匿名")) {
                            //										if (data.id.equals("0")) {
                            System.out.println("---微信还未绑定-------------------");
                            Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                            startActivity(intent1);
                        } else {
                            SharedPreferences spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
                            String user = spPreferences.getString("user", "");
                            System.out.println("---1-------------------" + user);
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

        spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");
        System.out.println("user_name_phone=================" + user_name);
        System.out.println("user_id=================" + user_id);
        //接口调用user_name的参数值
        System.out.println("user_name================" + user_name);
    }

    /**
     * 初始化控件类别
     */
    private void ininate(View layout) {
        try {
            iv_biaoti = (ImageView) layout.findViewById(R.id.iv_biaoti);
            iv_biaoti.setImageResource(R.drawable.zams_fuwu);
            zams_fw_1 = (ImageView) layout.findViewById(R.id.zams_fw_1);
            zams_fw_2 = (ImageView) layout.findViewById(R.id.zams_fw_2);
            zams_fw_3 = (ImageView) layout.findViewById(R.id.zams_fw_3);
            zams_fw_4 = (ImageView) layout.findViewById(R.id.zams_fw_4);
            zams_fw_5 = (ImageView) layout.findViewById(R.id.zams_fw_5);
            zams_fw_6 = (ImageView) layout.findViewById(R.id.zams_fw_6);
            zams_fw_7 = (ImageView) layout.findViewById(R.id.zams_fw_7);
            zams_fw_8 = (ImageView) layout.findViewById(R.id.zams_fw_8);
            zams_fw_9 = (ImageView) layout.findViewById(R.id.zams_fw_9);
            zams_fw_1.setImageResource(R.drawable.fw_tp1);
            zams_fw_2.setImageResource(R.drawable.fw_tp2);
            zams_fw_3.setImageResource(R.drawable.fw_tp3);
            zams_fw_4.setImageResource(R.drawable.fw_tp4);
            zams_fw_5.setImageResource(R.drawable.fw_tp5);
            zams_fw_6.setImageResource(R.drawable.fw_tp6);
            zams_fw_7.setImageResource(R.drawable.fw_tp7);
            zams_fw_8.setImageResource(R.drawable.fw_tp8);
            zams_fw_9.setImageResource(R.drawable.fw_tp9);


            zams_fw_5.setOnClickListener(this);
            zams_fw_1.setOnClickListener(this);
            zams_fw_2.setOnClickListener(this);
            zams_fw_3.setOnClickListener(this);
            zams_fw_4.setOnClickListener(this);
            zams_fw_5.setOnClickListener(this);
            zams_fw_6.setOnClickListener(this);
            zams_fw_7.setOnClickListener(this);
            zams_fw_8.setOnClickListener(this);
            zams_fw_9.setOnClickListener(this);

            myGridView = (MyGridView) layout.findViewById(R.id.gridView);
            myGridView.setFocusable(false);
            advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);

            myGridView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    System.out.println("=====================" + datas.get(arg2).id);
                    if (!nickname.equals("")) {
                        if (!user_name.equals("")) {
                            Intent intent13 = new Intent(getActivity(), Webview1.class);
                            intent13.putExtra("web_id", datas.get(arg2).id);
                            startActivity(intent13);
                        } else {
                            Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                            startActivity(intent2);
                        }
                    } else {
                        if (user_name.equals("")) {
                            Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
                            startActivity(intent48);
                        } else {
                            Intent intent13 = new Intent(getActivity(), Webview1.class);
                            intent13.putExtra("web_id", datas.get(arg2).id);
                            startActivity(intent13);
                        }
                    }
                }
            });

            //		btn_register = (Button) layout.findViewById(R.id.btn_register);
            //		list_shops = (LinearLayout)layout.findViewById(R.id.list_shops);
            //		list_shop_cart = (ListView)layout.findViewById(R.id.list_shop_cart);
            //		tv_amount_jf = (EditText)layout.findViewById(R.id.tv_amount_jf);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                default:
                    break;
            }
            super.dispatchMessage(msg);
        }
    };

    /**
     * 广告滚动
     */
    private void loadWeather() {

        AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_adbanner_list?advert_id=21",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            System.out.println("广告滚动-----------" + arg1);
                            JSONObject object = new JSONObject(arg1);
                            JSONArray array = object.getJSONArray("data");
                            int len = array.length();
                            ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
                            for (int i = 0; i < len; i++) {
                                AdvertDao1 ada = new AdvertDao1();
                                JSONObject json = array.getJSONObject(i);
                                ada.setId(json.getString("id"));
                                ada.setAd_url(json.getString("ad_url"));
                                ada.setLink_url(json.getString("link_url"));
                                //	 String ad_url = ada.getAd_url();
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
                }, getActivity());
    }

    /**
     * 详情
     *
     * @param
     */
    private void get_service_list() {
        //		progress.CreateProgress();
        AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_top_list?channel_name=content&top=0&strwhere=status=0%20and%20category_id=2946"
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        try {
                            System.out.println("详情====================" + arg1);
                            JSONObject jsonObject = new JSONObject(arg1);
                            String status = jsonObject.getString("status");
                            String info = jsonObject.getString("info");
                            if (status.equals("y")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                datas = new ArrayList<WareInformationData>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    WareInformationData data = new WareInformationData();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    data.id = object.getInt("id");
                                    data.img_url = object.getString("img_url");
                                    data.title = object.getString("title");
                                    //								data.sell_price = object.getString("sell_price");
                                    //								data.marketPrice = object.getString("market_price");
                                    //								data.article_id = object.getString("article_id");
                                    //								data.goods_id = object.getString("goods_id");
                                    datas.add(data);
                                }
                                //							handler.sendEmptyMessage(110);
                            } else {
                                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                            }
                            ServiceListAdaper jdhadapter = new ServiceListAdaper(datas, getActivity());
                            myGridView.setAdapter(jdhadapter);
                            progress.CloseProgress();

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {

                        super.onFailure(arg0, arg1);
                        try {
                            System.out.println("==============================" + arg1);
                            progress.CloseProgress();
                            Toast.makeText(getActivity(), "异常", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                }, getActivity());

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
                    tempss = (ArrayList<AdvertDao1>) msg.obj;
                    ArrayList<String> urls = new ArrayList<String>();
                    for (int i = 0; i < tempss.size(); i++) {
                        urls.add(tempss.get(i).getAd_url());
                    }
                    // addvie(context, tempss,urls);
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    advPager.setData(urls, new MyPosterOnClick() {
                        @Override
                        public void onMyclick(int position) {

                            if (!nickname.equals("")) {
                                if (!user_name.equals("")) {
                                    //								String web_id = tempss.get(position).getId();
                                    //								System.out.println("web_id=============" + web_id);
                                    String link_url = tempss.get(position).getLink_url();
                                    System.out.println("link_url=============" + link_url);
                                    //								if (link_url.contains("goods")) {
                                    //									String id = link_url.substring(33, 37);
                                    //									System.out.println("id=============" + id);
                                    //									Intent intent = new Intent(getActivity(), WareInformationActivity.class);
                                    //									intent.putExtra("id", id);
                                    //									startActivity(intent);
                                    //								}else {
                                    Intent intent13 = new Intent(getActivity(), Webview1.class);
                                    //								intent13.putExtra("web_id", web_id);
                                    intent13.putExtra("link_url", link_url);
                                    startActivity(intent13);
                                    //								}
                                } else {
                                    Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                                    startActivity(intent2);
                                }
                            } else {
                                if (user_name.equals("")) {
                                    Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
                                    startActivity(intent48);
                                } else {
                                    //							String web_id = tempss.get(position).getId();
                                    //							System.out.println("web_id=============" + web_id);
                                    String link_url = tempss.get(position).getLink_url();
                                    System.out.println("link_url=============" + link_url);
                                    //							if (link_url.contains("goods")) {
                                    //								String id = link_url.substring(33, 37);
                                    //								System.out.println("id=============" + id);
                                    //								Intent intent = new Intent(getActivity(), WareInformationActivity.class);
                                    //								intent.putExtra("id", id);
                                    //								startActivity(intent);
                                    //							}else {
                                    Intent intent13 = new Intent(getActivity(), Webview1.class);
                                    //							intent13.putExtra("web_id", web_id);
                                    intent13.putExtra("link_url", link_url);
                                    startActivity(intent13);
                                    //							}
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zams_fw_1:
                Intent intent = new Intent(getActivity(), PlatformhotlineActivity.class);
                startActivity(intent);
                break;
            case R.id.zams_fw_2:
            case R.id.zams_fw_4:
            case R.id.zams_fw_6:
            case R.id.zams_fw_7:
            case R.id.zams_fw_8:
            case R.id.zams_fw_9:
                Toast.makeText(getActivity(), "功能开放中，尽请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.zams_fw_5:
                showPhoneServiceActivity("法律援助", "010-57436263", "中安民生为弱势群体提供的专业法律维权渠道，合作专业法律相关从业人员或团队，提供一对一法律咨询与求助。为经济困难的会员给予法律保障。", R.drawable.legal_aid);
                break;
            case R.id.zams_fw_3:
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent healthIntent = new Intent(getActivity(),
                                HealthActivity.class);
                        startActivity(healthIntent);
                    } else {
                        Intent intent3 = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent3);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent healthIntent = new Intent(getActivity(),
                                HealthActivity.class);
                        startActivity(healthIntent);
                    }
                }
                break;
        }
    }

    /**
     * 去咨询页面
     *
     * @param title
     * @param phoneNumber
     * @param content
     * @param resId
     */
    public void showPhoneServiceActivity(String title, String phoneNumber, String content, int resId) {
        Intent intent = new Intent(getActivity(), PhoneServiceActivity.class);
        intent.putExtra(PhoneServiceActivity.TITLE, title);
        intent.putExtra(PhoneServiceActivity.CONTENT, content);
        intent.putExtra(PhoneServiceActivity.INM_RES_ID, resId);
        intent.putExtra(PhoneServiceActivity.PHONE_NUM, phoneNumber);
        startActivity(intent);
    }

}