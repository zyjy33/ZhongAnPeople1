package com.zams.www;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GouWuCheAGoodsAdaper;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.BitmapUtils;
import com.example.listviewitemslidedeletebtnshow.CollectionActivity;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.demo.activity.QianDaoBaoMingActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.DBFengXiangActivity;
import com.hengyushop.demo.home.FenXiangActivity;
import com.hengyushop.demo.home.MyShopPingCarActivity;
import com.hengyushop.demo.home.XiaDanActivity;
import com.hengyushop.demo.my.ChuangKeActivity;
import com.hengyushop.demo.my.ChuangKeUserActivity;
import com.hengyushop.demo.my.DaiRegisterActivity;
import com.hengyushop.demo.my.JiaZhiMxActivity;
import com.hengyushop.demo.my.MyAssetsActivity;
import com.hengyushop.demo.my.MyJuDuiHuanActivity;
import com.hengyushop.demo.my.MyJuTuanActivity;
import com.hengyushop.demo.my.MyOrderActivity;
import com.hengyushop.demo.my.MyQianBaoActivity;
import com.hengyushop.demo.my.QuHuoHaomaActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.entity.MyOrderData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.http.Util;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.health.HealthActivity;
import com.zams.www.weiget.PermissionSetting;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class IndividualCenterActivity extends Fragment implements
        OnClickListener {
    private LinearLayout img_address, img_financemanage, img_btn_chongzhi,
            img_btn_collect_ware, img_btn_health_manage, img_btn_order, caidan, zhuxiao, ll_quhuo,
            img_btn_daizhuce, img_btn_shared;
    private TextView tv_unpay, tv_delivered, tv_received, tv_payed, tv_name,
            tv_weixin_name, tv_usernum, tv_usertag, tv_ticket, tv_shop_ticket,
            tv_jifen_ticket, tv_djjifen_ticket, is_vip, tv_chuangke,
            tv_jiazhibi, tv_quanbu;
    private ImageView img_head;
    private LinearLayout ll_quanbu, ll_unpay, ll_delivered, ll_received,
            ll_payed, ll_user, ll_chuangke, ll_user_weixin, ll_saoyisao,
            ll_saoyisao_qd, ll_huiyuan;
    View iv_buju, iv_buju_dzc;
    private MyPopupWindowMenu popupWindowMenu;
    private Context context;
    private Button person_login_un_btn;
    private LinearLayout lau0;
    private RelativeLayout lau1;
    private IWXAPI api;
    Editor editor;
    private SharedPreferences spPreferences;
    private SharedPreferences spPreferences_login;
    private SharedPreferences jdh_spPreferences;
    String strUrlone;
    private String group_id;
    String user_id, headimgurl, access_token, sex, unionid;
    ArrayList<MyOrderData> list1;
    private ImageLoader mImageLoader;
    private DialogProgress progress;
    public static String yth, login_sign, agencygroupid, storegroupid,
            shopsgroupid, salesgroupid;
    RoundImageView networkImage;
    private LinearLayout index_item0, index_item1, index_item2, index_item3;
    private LinearLayout ll_my_1, ll_my_2, ll_my_3, ll_my_4, ll_my_5;
    UserRegisterllData data;
    protected static Uri tempUri;
    public static String path, time, area, lng, lat, imagePath, tupian;
    Bitmap photo;
    String nickname = "";
    String weixin = "";
    String qq = "";
    String headimgurl2 = "";
    String user_name = "";
    String user_name_phone = "";
    String user_name_key = "";
    String oauth_name;
    String province = "";
    String city = "";
    String country = "";
    String datall;
    boolean tp_type = false;
    private static final String TAG = "ActivityDemo";
    View layout;
    private String mMobile = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.individual_center, null);
        popupWindowMenu = new MyPopupWindowMenu(getActivity());
        progress = new DialogProgress(getActivity());
        tp_type = false;
        example();
        networkImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.zams_log));
        return layout;
    }

    public void onResume() {
        super.onResume();
        try {

            lau0.setBackgroundDrawable(getResources().getDrawable(R.drawable.denglu_beijing));
            lau1.setBackgroundDrawable(getResources().getDrawable(R.drawable.denglu_beijing));

            spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
            nickname = spPreferences_login.getString("nickname", "");
            mMobile = spPreferences_login.getString("mobile", "");
            headimgurl = spPreferences_login.getString("headimgurl", "");
            headimgurl2 = spPreferences_login.getString("headimgurl2", "");

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

        spPreferences_login = getActivity().getSharedPreferences(
                "longuserset_login", Context.MODE_PRIVATE);

        nickname = spPreferences_login.getString("nickname", "");
        mMobile = spPreferences_login.getString("mobile", "");
        headimgurl = spPreferences_login.getString("headimgurl", "");
        unionid = spPreferences_login.getString("unionid", "");
        access_token = spPreferences_login.getString("access_token", "");
        sex = spPreferences_login.getString("sex", "");
        String oauth_openid = spPreferences_login.getString("oauth_openid", "");

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

        String strUrlone = RealmName.REALM_NAME_LL
                + "/user_oauth_register_0217?nick_name=" + nick_name + "&sex="
                + sex + "&avatar=" + headimgurl + "" + "&province=" + province
                + "&city=" + city + "&country=" + country + "&oauth_name="
                + oauth_name + "&oauth_unionid=" + unionid + ""
                + "&oauth_openid=" + oauth_openid + "";
        System.out.println("我的======11======1=======" + strUrlone);
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                System.out.println("我的======输出=====1========" + arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    object.getString("status");
                    object.getString("info");
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

                            if (UserLoginActivity.panduan_tishi) {
                                if (weixin.equals("weixin")) {
                                } else {
                                    Intent intent1 = new Intent(getActivity(),
                                            TishiWxBangDingActivity.class);
                                    startActivity(intent1);
                                    UserLoginActivity.panduan_tishi = false;
                                }

                            } else if (UserLoginWayActivity.panduan_tishi) {
                                if (qq.equals("qq")) {
                                } else {
                                    Intent intent2 = new Intent(getActivity(),
                                            TishiWxBangDingActivity.class);
                                    startActivity(intent2);
                                    UserLoginWayActivity.panduan_tishi = false;
                                }
                            } else {
                            }
                        }

                    } else {
                        UserRegisterllData data = new UserRegisterllData();
                        JSONObject obj = object.getJSONObject("data");
                        data.id = obj.getString("id");
                        data.user_name = obj.getString("user_name");
                        // data.login_sign = obj.getString("login_sign");
                        user_id = data.id;
                        System.out
                                .println("---data.user_name-------------------"
                                        + data.user_name);
                        System.out.println("---user_id-------------------"
                                + user_id);
                        if (data.user_name.equals("匿名")) {
                            // if (data.id.equals("0")) {
                            System.out.println("---微信还未绑定-------------------");
                            Intent intent1 = new Intent(getActivity(),
                                    TishiWxBangDingActivity.class);
                            startActivity(intent1);
                        } else {
                            SharedPreferences spPreferences = getActivity()
                                    .getSharedPreferences("longuserset",
                                            context.MODE_PRIVATE);
                            String user = spPreferences.getString("user", "");
                            System.out
                                    .println("---1-------------------" + user);
                            data.login_sign = obj.getString("login_sign");
                            data.point = obj.getString("point");
                            Editor editor = spPreferences.edit();
                            editor.putString("login_sign", data.login_sign);
                            editor.putString("user", data.user_name);
                            editor.putString("user_id", data.id);
                            editor.putString("point", data.point);
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

            }

            ;
        }, getActivity());

    }

    private void getuserxinxi() {


        try {

            spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
            user_name_phone = spPreferences.getString("user", "");
            System.out.println("user_name_phone================="
                    + user_name_phone);


            if (!user_name_phone.equals("")) {
                user_id = spPreferences.getString("user_id", "");
                user_name_key = user_name_phone;
            }

            if (!nickname.equals("")) {
                System.out.println("==00====");
                lau0.setVisibility(View.GONE);
                lau1.setVisibility(View.VISIBLE);
                ll_user_weixin.setVisibility(View.GONE);
                tv_weixin_name.setVisibility(View.VISIBLE);
                System.out.println("==11==user_name_phone==" + user_name_phone);
                if (!user_name_phone.equals("")) {
                    System.out.println("==11====");
                    ll_user_weixin.setVisibility(View.VISIBLE);
                    tv_weixin_name.setVisibility(View.GONE);
                    handler.sendEmptyMessage(-11);
                    load_list();
                } else {
                    try {
                        System.out.println("==22====");
                        setinten();// 数据清空

                        lau0.setVisibility(View.GONE);
                        lau1.setVisibility(View.VISIBLE);
                        if (!headimgurl2.equals("")) {
                            img_head.setVisibility(View.VISIBLE);
                            networkImage.setVisibility(View.GONE);
                            tv_weixin_name.setVisibility(View.VISIBLE);
                            Bitmap bitmap = BitUtil.stringtoBitmap(headimgurl2);
                            // Bitmap bitmap = UserLoginActivity.bitmap;
                            bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
                            img_head.setImageBitmap(bitmap);
                            if (TextUtils.isEmpty(mMobile)) {
                                tv_weixin_name.setText(nickname);
                            } else {
                                tv_weixin_name.setText(mMobile);
                            }
                        } else {
                            img_head.setVisibility(View.GONE);
                            networkImage.setVisibility(View.VISIBLE);
                            tv_weixin_name.setVisibility(View.VISIBLE);
                            mImageLoader = initImageLoader(getActivity(),
                                    mImageLoader, "test");
                            mImageLoader.displayImage(headimgurl, networkImage);
                            if (TextUtils.isEmpty(mMobile)) {
                                tv_weixin_name.setText(nickname);

                            } else {
                                tv_weixin_name.setText(mMobile);
                            }
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("==33======================="
                        + user_name_phone);

                if (!user_name_phone.equals("")) {
                    ll_user_weixin.setVisibility(View.VISIBLE);
                    lau0.setVisibility(View.GONE);
                    lau1.setVisibility(View.VISIBLE);
                    tv_weixin_name.setVisibility(View.GONE);
                    handler.sendEmptyMessage(-11);
                    load_list();
                } else {
                    lau0.setVisibility(View.VISIBLE);
                    lau1.setVisibility(View.GONE);
                    ll_user_weixin.setVisibility(View.GONE);
                    tv_weixin_name.setVisibility(View.GONE);
                    handler.sendEmptyMessage(-22);

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 提示是否注销登录
     */
    protected void dialoglogin() {
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage("是否注销登录?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                setinten();
                //				handler.sendEmptyMessage(-11);
                iv_buju.setVisibility(View.GONE);
                ll_saoyisao_qd.setVisibility(View.GONE);

                // 清空SharedPreferences保存数据
                jdh_spPreferences = getActivity().getSharedPreferences(
                        "user_juduihuan", Context.MODE_PRIVATE);
                spPreferences = getActivity().getSharedPreferences(
                        "longuserset", Context.MODE_PRIVATE);

                if (UserLoginActivity.panduan) {
                    // if (!user_name_weixin.equals("")) {
                    spPreferences_login.edit().clear().commit();
                    spPreferences.edit().clear().commit();
                    jdh_spPreferences.edit().clear().commit();// 积兑换保存福利清除
                    String nickname = spPreferences_login.getString("nickname",
                            "");
                    System.out.println("1======" + nickname);
                    // Toast.makeText(getActivity(), "微信名/"+nickname,
                    // Toast.LENGTH_SHORT).show();
                    UserLoginActivity.panduan = false;
                    Intent intent4 = new Intent(context,
                            UserLoginActivity.class);
                    startActivity(intent4);
                } else if (UserLoginWayActivity.panduan == true) {
                    // if (!user_name_qq.equals("")) {
                    spPreferences_login.edit().clear().commit();
                    spPreferences.edit().clear().commit();
                    jdh_spPreferences.edit().clear().commit();// 积兑换保存福利清除
                    String nickname = spPreferences_login.getString("nickname",
                            "");
                    // Toast.makeText(getActivity(), "QQ名/"+nickname,
                    // Toast.LENGTH_SHORT).show();
                    System.out.println("1======" + nickname);
                    UserLoginWayActivity.panduan = false;
                    Intent intent4 = new Intent(context,
                            UserLoginActivity.class);
                    startActivity(intent4);
                } else if (PhoneLoginActivity.panduan == true) {
                    // if (!user_name.equals("")) {
                    spPreferences.edit().clear().commit();
                    spPreferences_login.edit().clear().commit();
                    jdh_spPreferences.edit().clear().commit();// 积兑换保存福利清除
                    String user_name = spPreferences.getString("user", "");
                    System.out.println("2======" + user_name);
                    // Toast.makeText(getActivity(), "用户名/"+user_name,
                    // Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(context,
                            UserLoginActivity.class);
                    // intent4.putExtra("login", index);
                    PhoneLoginActivity.panduan = false;
                    startActivity(intent4);
                } else {
                    spPreferences.edit().clear().commit();
                    spPreferences_login.edit().clear().commit();
                    jdh_spPreferences.edit().clear().commit();// 积兑换保存福利清除
                    // Toast.makeText(getActivity(), "空值", Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(context,
                            UserLoginActivity.class);
                    startActivity(intent4);
                }
                SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
                spPreferences_tishi.edit().clear().commit();// 第三方授权登录提示绑定手机号信息清空
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

    /**
     * 数据清空
     */
    private void setinten() {

        tv_ticket.setText("0");
        tv_shop_ticket.setText("0");
        tv_jifen_ticket.setText("0");
        tv_djjifen_ticket.setText("0");
        tv_jiazhibi.setText("0");

        tv_quanbu.setText("0");
        tv_unpay.setText("0");
        tv_delivered.setText("0");
        tv_received.setText("0");
        tv_payed.setText("0");
    }

    public IndividualCenterActivity(ImageLoader imageLoader, Handler handler2,
                                    Context context) {
        this.context = context;
    }

    public IndividualCenterActivity() {

    }

    private void softshareWxChat(String text) {
        String temp[] = text.split("http");
        api = WXAPIFactory.createWXAPI(context, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http" + temp[1];
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "我发你一个软件,看看呗!";
        msg.description = temp[0];
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        boolean flag = api.sendReq(req);
        if (thumb != null && !thumb.isRecycled()) {
            thumb.recycle();
            thumb = null;
        }
        System.out.println("微信註冊" + flag + "-->" + msg.thumbData);
    }

    private void softshareWxFriend(String text) {
        String temp[] = text.split("http");
        api = WXAPIFactory.createWXAPI(context, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http" + temp[1];
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "我发你一个软件,看看呗!";
        // msg.title = "ni"+"我发你一个软件,看看呗!";
        msg.description = temp[0];
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icon);
        msg.thumbData = Util.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        boolean flag = api.sendReq(req);
        System.out.println(flag + "-->" + msg.thumbData);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    JSONObject obj;

    double exp, exp_weal, exp_invest, exp_action, exp_time;
    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case -22:
                    lau0.setVisibility(View.VISIBLE);
                    lau1.setVisibility(View.GONE);
                    break;
                case -11:
                    lau0.setVisibility(View.GONE);
                    lau1.setVisibility(View.VISIBLE);
                    try {
                        System.out.println("======user_name======1=======" + user_name);
                        System.out.println("======user_name_key======2=======" + user_name_key);
                        strUrlone = RealmName.REALM_NAME_LL
                                + "/get_user_model?username=" + user_name_key + "";
                        System.out.println("======11=============" + strUrlone);
                        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                            public void onSuccess(int arg0, String arg1) {
                                try {
                                    System.out.println("======输出用户资料============="
                                            + arg1);
                                    JSONObject object = new JSONObject(arg1);
                                    String status = object.getString("status");
                                    if (status.equals("y")) {
                                        try {
                                            obj = object.getJSONObject("data");
                                            data = new UserRegisterllData();
                                            data.user_name = obj.getString("user_name");
                                            data.user_code = obj.getString("user_code");
                                            data.agency_id = obj.getInt("agency_id");
                                            data.amount = obj.getString("amount");
                                            data.pension = obj.getString("pension");
                                            data.packet = obj.getString("packet");
                                            data.point = obj.getString("point");
                                            data.group_id = obj.getString("group_id");

                                            data.login_sign = obj
                                                    .getString("login_sign");
                                            data.agency_name = obj
                                                    .getString("agency_name");
                                            data.group_name = obj
                                                    .getString("group_name");
                                            data.avatar = obj.getString("avatar");
                                            data.mobile = obj.getString("mobile");
                                            data.vip_card = obj.getString("vip_card");
                                            //									exp+exp_weal+exp_invest+exp_action+exp_time

                                            exp = obj.getDouble("exp");
                                            exp_weal = obj.getDouble("exp_weal");
                                            exp_invest = obj.getDouble("exp_invest");
                                            exp_action = obj.getDouble("exp_action");
                                            exp_time = obj.getDouble("exp_time");

                                            double dzongjia = exp + exp_weal + exp_invest + exp_action + exp_time;
                                            BigDecimal w = new BigDecimal(dzongjia);
                                            double zong_jz = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                            System.out.println("zong_jz===============" + zong_jz);

                                            tv_jiazhibi.setText(String.valueOf(zong_jz));//价值
                                            System.out.println("avatar===============" + data.avatar);

                                            spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
                                            editor = spPreferences.edit();
                                            editor.putString("mobile", data.mobile);
                                            editor.putString("avatar", data.avatar);
                                            editor.putString("group_id", data.group_id);
                                            editor.putString("group_name", data.group_name);
                                            editor.commit();

                                            if (!TextUtils.isEmpty(data.vip_card)) {
                                                tv_usertag.setText("服务金卡:" + data.vip_card);
                                                tv_usertag.setVisibility(View.VISIBLE);
                                            } else {
                                                tv_usertag.setText("健康卡号:" + data.user_code);
                                                tv_usertag.setVisibility(View.GONE);
                                            }
                                            System.out.println("group_name=======企业职员========" + data.group_name);
                                            if (data.group_name.contains("企业职员")) {
                                                iv_buju.setVisibility(View.VISIBLE);
                                                ll_saoyisao_qd.setVisibility(View.VISIBLE);
                                            } else {
                                                iv_buju_dzc.setVisibility(View.VISIBLE);
                                                img_btn_daizhuce.setVisibility(View.VISIBLE);
                                            }

                                            data.exp = obj.getString("exp");
                                            login_sign = data.login_sign;
                                            tv_usernum.setText(data.group_name);
                                            group_id = data.group_id;
                                            tv_name.setText(data.mobile);
                                            yth = data.user_code;
                                            tv_ticket.setText(data.amount);
                                            tv_shop_ticket.setText(data.pension);
                                            tv_jifen_ticket.setText(data.packet);
                                            tv_djjifen_ticket.setText(data.point);// data.packet
                                            // point

                                            ll_huiyuan.setVisibility(View.VISIBLE);

                                            System.out.println("tp_type===============" + tp_type);
                                            System.out.println("data.avatar===============" + data.avatar);
                                            if (tp_type == false) {
                                                tp_type = false;
                                                mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
                                                if (!data.avatar.equals("")) {
                                                    mImageLoader.displayImage(RealmName.REALM_NAME_FTP + data.avatar, networkImage);
                                                } else {
                                                    if (data.avatar.equals("")) {
                                                        networkImage.setImageDrawable(getResources().getDrawable(R.drawable.app_zams));
                                                    } else {
                                                        if (!headimgurl.equals("")) {
                                                            img_head.setVisibility(View.GONE);
                                                            networkImage.setVisibility(View.VISIBLE);
                                                            mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
                                                            mImageLoader.displayImage(headimgurl, networkImage);
                                                        } else {
                                                            if (!headimgurl2.equals("")) {
                                                                img_head.setVisibility(View.VISIBLE);
                                                                networkImage.setVisibility(View.GONE);
                                                                Bitmap bitmap = BitUtil.stringtoBitmap(headimgurl2);
                                                                bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
                                                                img_head.setImageBitmap(bitmap);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            userpanduan(data.login_sign);
                                        } catch (Exception e) {

                                            e.printStackTrace();
                                        }
                                        data = null;
                                        obj = null;
                                    } else {

                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }

                            ;

                            @Override
                            public void onFailure(Throwable arg0, String arg1) {

                                super.onFailure(arg0, arg1);
                                NewDataToast.makeText(context, "连接超时", false, 0).show();
                                //							Toast.makeText(context,"连接超时, Toast.LENGTH_SHORT).show();
                            }
                        }, context);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    String strobj = (String) msg.obj;
                    Toast.makeText(getActivity(), strobj, Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    String text1 = (String) msg.obj;
                    softshareWxChat(text1);
                    break;
                case 3:
                    String text2 = (String) msg.obj;
                    softshareWxFriend(text2);
                    break;
                case 4:
                    String text = (String) msg.obj;
                    Uri uri = Uri.parse("smsto:");
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", text);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 初始化图片下载器，图片缓存地址<i>("/Android/data/[app_package_name]/cache/dirName")</i>
     */
    public ImageLoader initImageLoader(Context context,
                                       ImageLoader imageLoader, String dirName) {
        imageLoader = ImageLoader.getInstance();
        if (imageLoader.isInited()) {
            // 重新初始化ImageLoader时,需要释放资源.
            imageLoader.destroy();
        }
        imageLoader.init(initImageLoaderConfig(context, dirName));
        return imageLoader;
    }

    /**
     * 配置图片下载器
     *
     * @param dirName 文件名
     */
    private ImageLoaderConfiguration initImageLoaderConfig(Context context,
                                                           String dirName) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCache(new UnlimitedDiscCache(new File(dirName)))
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        return config;
    }

    private int getMemoryCacheSize(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        return memoryCacheSize;
    }

    /**
     * 判断是否升级
     *
     * @param login_sign
     */
    public void userpanduan(String login_sign) {
        try {
            String user_name = spPreferences.getString("user", "");
            String strUrlone = RealmName.REALM_NAME_LL
                    + "/get_user_config?username=" + user_name + "&sign="
                    + login_sign + "";

            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        //System.out.println("======11=======arg1======"+arg1);
                        String status = object.getString("status");
                        if (status.equals("y")) {
                            JSONObject obj = object.getJSONObject("data");
                            agencygroupid = obj.getString("agencygroupid");
                            storegroupid = obj.getString("storegroupid");
                            shopsgroupid = obj.getString("shopsgroupid");
                            salesgroupid = obj.getString("salesgroupid");

                            System.out.println("======agencygroupid============" + agencygroupid);
                            System.out.println("======group_id============" + group_id);
                            if (agencygroupid.contains(group_id)) {
                                tv_chuangke.setText("我是服务顾问");
                            } else if (storegroupid.contains(group_id)) {
                                tv_chuangke.setText("我是服务顾问");
                            } else if (shopsgroupid.contains(group_id)) {
                                tv_chuangke.setText("我是服务顾问");
                            } else if (salesgroupid.contains(group_id)) {
                                tv_chuangke.setText("我是服务顾问");
                            } else {
                                tv_chuangke.setText("服务顾问认证");
                            }

                            // agencygroupid 代理
                            // storegroupid 仓超
                            // shopsgroupid 门店
                            // salesgroupid 业务员
                        } else {

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, context);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void example() {

        try {

            index_item0 = (LinearLayout) layout.findViewById(R.id.index_item0);
            index_item1 = (LinearLayout) layout.findViewById(R.id.index_item1);
            index_item2 = (LinearLayout) layout.findViewById(R.id.index_item2);
            index_item3 = (LinearLayout) layout.findViewById(R.id.index_item3);
            index_item0.setOnClickListener(this);
            index_item1.setOnClickListener(this);
            index_item2.setOnClickListener(this);
            index_item3.setOnClickListener(this);
            ll_my_1 = (LinearLayout) layout.findViewById(R.id.ll_my_1);
            ll_my_2 = (LinearLayout) layout.findViewById(R.id.ll_my_2);
            ll_my_3 = (LinearLayout) layout.findViewById(R.id.ll_my_3);
            ll_my_4 = (LinearLayout) layout.findViewById(R.id.ll_my_4);
            ll_my_5 = (LinearLayout) layout.findViewById(R.id.ll_my_5);
            ll_my_1.setOnClickListener(this);
            ll_my_2.setOnClickListener(this);
            ll_my_3.setOnClickListener(this);
            ll_my_4.setOnClickListener(this);
            ll_my_5.setOnClickListener(this);
            ImageView iv_my_tp1 = (ImageView) layout.findViewById(R.id.iv_my_tp1);
            ImageView iv_my_tp2 = (ImageView) layout.findViewById(R.id.iv_my_tp2);
            ImageView iv_my_tp3 = (ImageView) layout.findViewById(R.id.iv_my_tp3);
            ImageView iv_my_tp4 = (ImageView) layout.findViewById(R.id.iv_my_tp4);
            iv_my_tp1.setBackgroundResource(R.drawable.wdyhj);
            iv_my_tp2.setBackgroundResource(R.drawable.wdjdh);
            iv_my_tp3.setBackgroundResource(R.drawable.wdkt);
            iv_my_tp4.setBackgroundResource(R.drawable.wdcj);

            ImageView iv_my_1 = (ImageView) layout.findViewById(R.id.iv_my_1);
            ImageView iv_my_2 = (ImageView) layout.findViewById(R.id.iv_my_2);
            ImageView iv_my_3 = (ImageView) layout.findViewById(R.id.iv_my_3);
            ImageView iv_my_4 = (ImageView) layout.findViewById(R.id.iv_my_4);
            ImageView iv_my_5 = (ImageView) layout.findViewById(R.id.iv_my_5);
            ImageView iv_my_6 = (ImageView) layout.findViewById(R.id.iv_my_6);
            ImageView iv_my_7 = (ImageView) layout.findViewById(R.id.iv_my_7);
            ImageView iv_my_8 = (ImageView) layout.findViewById(R.id.iv_my_8);
            ImageView iv_my_9 = (ImageView) layout.findViewById(R.id.iv_my_9);
            ImageView iv_my_10 = (ImageView) layout.findViewById(R.id.iv_my_10);
            ImageView iv_my_11 = (ImageView) layout.findViewById(R.id.iv_my_11);
            ImageView iv_my_sys_xd = (ImageView) layout.findViewById(R.id.iv_my_sys_xd);
            ImageView iv_my_sys_qd = (ImageView) layout.findViewById(R.id.iv_my_sys_qd);
            iv_my_1.setBackgroundResource(R.drawable.my_ddgl);
            iv_my_2.setBackgroundResource(R.drawable.my_zhgl);
            iv_my_3.setBackgroundResource(R.drawable.my_ck);
            iv_my_4.setBackgroundResource(R.drawable.my_sc);
            iv_my_5.setBackgroundResource(R.drawable.my_qbcz);
            iv_my_6.setBackgroundResource(R.drawable.my_dzc);
            iv_my_7.setBackgroundResource(R.drawable.my_qzyh);
            iv_my_8.setBackgroundResource(R.drawable.my_caiwu);
            iv_my_9.setBackgroundResource(R.drawable.my_fx);
            iv_my_10.setBackgroundResource(R.drawable.my_bangzhu);
            iv_my_11.setBackgroundResource(R.drawable.my_zx);
            iv_my_sys_xd.setBackgroundResource(R.drawable.my_saoyisao);
            iv_my_sys_qd.setBackgroundResource(R.drawable.my_saoyisao);

            networkImage = (RoundImageView) layout.findViewById(R.id.roundImage_network);
            person_login_un_btn = (Button) layout.findViewById(R.id.person_login_un_btn);
            person_login_un_btn.setOnClickListener(this);
            lau0 = (LinearLayout) layout.findViewById(R.id.lau0);
            lau1 = (RelativeLayout) layout.findViewById(R.id.lau1);
            //		is_vip = (TextView) layout.findViewById(R.id.is_vip);
            img_btn_shared = (LinearLayout) layout
                    .findViewById(R.id.img_btn_shared);
            img_btn_daizhuce = (LinearLayout) layout
                    .findViewById(R.id.img_btn_daizhuce);
            tv_chuangke = (TextView) layout.findViewById(R.id.tv_chuangke);
            tv_jiazhibi = (TextView) layout.findViewById(R.id.tv_jiazhibi);
            tv_usernum = (TextView) layout.findViewById(R.id.tv_usernum);
            // agency_name = (TextView) layout.findViewById(R.id.tv_agency_name);
            tv_usertag = (TextView) layout.findViewById(R.id.tv_usertag);
            img_address = (LinearLayout) layout.findViewById(R.id.img_btn_address);
            zhuxiao = (LinearLayout) layout.findViewById(R.id.zhuxiao);
            ll_huiyuan = (LinearLayout) layout.findViewById(R.id.ll_huiyuan);
            zhuxiao.setOnClickListener(this);
            img_financemanage = (LinearLayout) layout
                    .findViewById(R.id.img_btn_financemanage);
            img_btn_chongzhi = (LinearLayout) layout
                    .findViewById(R.id.img_btn_chongzhi);
            ll_quhuo = (LinearLayout) layout.findViewById(R.id.ll_quhuo);
            img_btn_collect_ware = (LinearLayout) layout.findViewById(R.id.img_btn_collect_ware);
            img_btn_health_manage = (LinearLayout) layout.findViewById(R.id.img_btn_health_manage);
            img_head = (ImageView) layout.findViewById(R.id.ib_user_photo);
            caidan = (LinearLayout) layout.findViewById(R.id.caidan);
            caidan.setOnClickListener(this);
            tv_quanbu = (TextView) layout.findViewById(R.id.tv_quanbu);
            tv_unpay = (TextView) layout.findViewById(R.id.tv_unpay);
            tv_delivered = (TextView) layout.findViewById(R.id.tv_delivered);
            tv_received = (TextView) layout.findViewById(R.id.tv_received);
            tv_payed = (TextView) layout.findViewById(R.id.tv_payed);

            tv_name = (TextView) layout.findViewById(R.id.tv_username);
            tv_weixin_name = (TextView) layout.findViewById(R.id.tv_weixin_name);
            tv_ticket = (TextView) layout.findViewById(R.id.tv_ticket);
            tv_shop_ticket = (TextView) layout.findViewById(R.id.tv_shop_ticket);
            tv_jifen_ticket = (TextView) layout.findViewById(R.id.tv_jifen_ticket);
            tv_djjifen_ticket = (TextView) layout
                    .findViewById(R.id.tv_djjifen_ticket);
            ll_quanbu = (LinearLayout) layout.findViewById(R.id.ll_quanbu);
            ll_unpay = (LinearLayout) layout.findViewById(R.id.ll_unpay);
            ll_delivered = (LinearLayout) layout.findViewById(R.id.ll_delivered);
            ll_received = (LinearLayout) layout.findViewById(R.id.ll_received);
            ll_payed = (LinearLayout) layout.findViewById(R.id.ll_payed);
            img_btn_order = (LinearLayout) layout.findViewById(R.id.img_btn_order);
            ll_user = (LinearLayout) layout.findViewById(R.id.ll_user);
            ll_chuangke = (LinearLayout) layout.findViewById(R.id.ll_chuangke);
            ll_user_weixin = (LinearLayout) layout
                    .findViewById(R.id.ll_user_weixin);
            ll_saoyisao = (LinearLayout) layout.findViewById(R.id.ll_saoyisao);
            ll_saoyisao_qd = (LinearLayout) layout
                    .findViewById(R.id.ll_saoyisao_qd);
            iv_buju = (View) layout.findViewById(R.id.iv_buju);
            iv_buju_dzc = (View) layout.findViewById(R.id.iv_buju_dzc);
            ll_chuangke.setOnClickListener(this);
            ll_quhuo.setOnClickListener(this);
            img_address.setOnClickListener(this);
            img_financemanage.setOnClickListener(this);
            img_btn_collect_ware.setOnClickListener(this);
            img_btn_health_manage.setOnClickListener(this);
            img_head.setOnClickListener(this);
            //		networkImage.setOnClickListener(this);
            ll_quanbu.setOnClickListener(this);
            ll_unpay.setOnClickListener(this);
            ll_delivered.setOnClickListener(this);
            ll_received.setOnClickListener(this);
            ll_payed.setOnClickListener(this);
            img_btn_order.setOnClickListener(this);
            ll_user.setOnClickListener(this);
            img_btn_shared.setOnClickListener(this);
            img_btn_chongzhi.setOnClickListener(this);
            ll_saoyisao.setOnClickListener(this);
            ll_saoyisao_qd.setOnClickListener(this);
            img_btn_daizhuce.setOnClickListener(this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_1://钱包
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "1");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "1");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_my_2://养老金
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "2");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "2");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_my_3://福利
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "3");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "3");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_my_4://红包
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "4");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        intent = new Intent(getActivity(), MyAssetsActivity.class);
                        intent.putExtra("status", "4");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_my_5://价值
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent = new Intent(getActivity(), JiaZhiMxActivity.class);
                        startActivity(Intent);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent3 = new Intent(getActivity(), JiaZhiMxActivity.class);
                        startActivity(intent3);
                    }
                }
                break;
            case R.id.img_btn_address:// 账户管理
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent = new Intent(getActivity(),
                                PersonCenterActivity.class);
                        startActivity(Intent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    try {

                        if (user_name_phone.equals("")) {
                            Intent intent9 = new Intent(getActivity(),
                                    UserLoginActivity.class);
                            startActivity(intent9);
                        } else {

                            Intent intent3 = new Intent(getActivity(),
                                    PersonCenterActivity.class);
                            startActivity(intent3);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
                break;
            case R.id.img_btn_chongzhi:// 钱包充值
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent = new Intent(getActivity(),
                                MyQianBaoActivity.class);
                        startActivity(Intent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    try {
                        if (user_name_phone.equals("")) {
                            Intent intent9 = new Intent(getActivity(),
                                    UserLoginActivity.class);
                            startActivity(intent9);
                        } else {
                            Intent intent = new Intent(getActivity(),
                                    MyQianBaoActivity.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                break;
            case R.id.img_btn_daizhuce:// 代注册
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent = new Intent(getActivity(),
                                DaiRegisterActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent = new Intent(context,
                                DaiRegisterActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.img_btn_shared:
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent = new Intent(getActivity(),
                                FenXiangActivity.class);
                        startActivity(Intent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
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
            case R.id.person_login_un_btn:
                Intent intent0 = new Intent(getActivity(), UserLoginActivity.class);
                intent0.putExtra("login", 0);
                startActivity(intent0);
                break;
            case R.id.img_btn_financemanage:// 财务管理
                // Intent intent2 = new
                // Intent(getActivity(),FinanceManageActivity.class);
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent = new Intent(getActivity(),
                                MyAssetsActivity.class);
                        startActivity(Intent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent2 = new Intent(getActivity(),
                                MyAssetsActivity.class);
                        startActivity(intent2);
                    }
                }
                break;
            case R.id.ll_quanbu:// 全部
                // int index0 = 0;
                // Intent intent5 = new
                // Intent(getActivity(),OrderInfromationActivity.class);//MyOrderActivity
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "0");
                        startActivity(intent5);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "0");
                        startActivity(intent5);
                    }
                }
                break;
            case R.id.ll_unpay:// 待付款
                // int index0 = 0;
                // Intent intent5 = new
                // Intent(getActivity(),OrderInfromationActivity.class);//MyOrderActivity
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "1");
                        startActivity(intent5);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "1");
                        startActivity(intent5);
                    }
                }
                break;
            case R.id.ll_delivered:// 已付款
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "2");
                        startActivity(intent5);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent6 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent6.putExtra("status", "2");
                        startActivity(intent6);
                    }
                }
                break;
            case R.id.ll_received:// 待发货
                // int index2 = 2;
                // Intent intent7 = new
                // Intent(getActivity(),OrderInfromationActivity.class);
                // intent7.putExtra("num", index2);
                // startActivity(intent7);
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "3");
                        startActivity(intent5);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent7 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent7.putExtra("status", "3");
                        startActivity(intent7);
                    }
                }
                break;
            case R.id.ll_payed:// 待收货
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent5 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent5.putExtra("status", "4");
                        startActivity(intent5);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent8 = new Intent(getActivity(),
                                MyOrderActivity.class);
                        intent8.putExtra("status", "4");
                        startActivity(intent8);
                    }
                }
                break;
            case R.id.ll_chuangke:// 服务顾问
                if (!nickname.equals("")) {
                    if (user_name_phone.equals("")) {
                        Intent intent6 = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent6);
                    } else {
                        if (agencygroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else if (storegroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else if (shopsgroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else if (salesgroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeActivity.class);
                            startActivity(intent_ck);
                        }
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent6 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent6);
                    } else {
                        if (agencygroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else if (storegroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else if (shopsgroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else if (salesgroupid.contains(group_id)) {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeUserActivity.class);
                            startActivity(intent_ck);
                        } else {
                            Intent intent_ck = new Intent(getActivity(),
                                    ChuangKeActivity.class);
                            startActivity(intent_ck);
                        }
                    }
                }
                break;
            case R.id.img_btn_collect_ware:// 我的收藏
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent intent5 = new Intent(getActivity(),
                                CollectionActivity.class);
                        startActivity(intent5);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent intent9 = new Intent(getActivity(),
                                CollectionActivity.class);
                        startActivity(intent9);
                    }
                }
                break;
            case R.id.img_btn_health_manage:// 健康管理
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent healthIntent = new Intent(getActivity(),
                                HealthActivity.class);
                        startActivity(healthIntent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
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
            case R.id.roundImage_network:
                // dialog();
                // showChoosePicDialog();
                CharSequence[] items = {"手机相册", "手机拍照"};// "查看头像",
                new AlertDialog.Builder(getActivity()).setTitle("上传照片")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == SELECT_PICTURE) {
                                    Intent intent = new Intent(
                                            "android.media.action.IMAGE_CAPTURE");
                                    // 判断存储卡是否可以用，可用进行存储
                                    if (hasSdcard()) {
                                        intent.putExtra(
                                                MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(new File(
                                                        Environment
                                                                .getExternalStorageDirectory(),
                                                        PHOTO_FILE_NAME)));
                                    }
                                    startActivityForResult(intent,
                                            PHOTO_REQUEST_CAMERA);
                                    System.out
                                            .println("SELECT_PICTURE------------1-------相册--------"
                                                    + SELECT_PICTURE);
                                } else if (which == SELECT_CAMERA) {
                                    System.out
                                            .println("SELECT_CAMERA-------------2----------拍照----"
                                                    + SELECT_CAMERA);
                                } else if (which == SELECT_SCAN) {
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(intent,
                                            PHOTO_REQUEST_GALLERY);
                                    System.out
                                            .println("SELECT_SCAN----------------4-----查看头像------"
                                                    + SELECT_SCAN);
                                }

                            }
                        }).create().show();
                break;
            case R.id.caidan:

                Intent intent4 = new Intent(getActivity(), Webview1.class);
                intent4.putExtra("web_id", "103");
                startActivity(intent4);
                break;
            case R.id.img_btn_order:// 订单管理
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent personIntent = new Intent(getActivity(),
                                MyOrderActivity.class);
                        startActivity(personIntent);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent personIntent = new Intent(getActivity(),
                                MyOrderActivity.class);
                        startActivity(personIntent);
                    }
                }
                break;
            case R.id.index_item0:// 我的优惠劵
                Toast.makeText(getActivity(), "功能还未开发，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.index_item1:// 我的聚兑换
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent1 = new Intent(getActivity(),
                                MyJuDuiHuanActivity.class);
                        Intent1.putExtra("num", "2");
                        startActivity(Intent1);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent Intent1 = new Intent(getActivity(),
                                MyJuDuiHuanActivity.class);
                        Intent1.putExtra("num", "2");
                        startActivity(Intent1);
                    }
                }
                break;
            case R.id.index_item2:// 我的聚团
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent2 = new Intent(getActivity(),
                                MyJuTuanActivity.class);
                        Intent2.putExtra("num", "3");
                        startActivity(Intent2);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        Intent Intent2 = new Intent(getActivity(),
                                MyJuTuanActivity.class);
                        Intent2.putExtra("num", "3");
                        startActivity(Intent2);
                    }
                }
                break;
            case R.id.ll_saoyisao:// 扫一扫下单
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        if (XiaDanActivity.list.size() > 0) {
                            XiaDanActivity.list.clear();
                        }
                        if (XiaDanActivity.list_ll.size() > 0) {
                            XiaDanActivity.list_ll.clear();
                        }
                        System.out.println("list_ll.size()============1========="
                                + XiaDanActivity.list_ll.size());
                        System.out.println("list.size()==============1==========="
                                + XiaDanActivity.list.size());
                        AndPermission.with(this)
                                .permission(Permission.CAMERA)
                                .onGranted(new Action() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        Intent Intent2 = new Intent(getActivity(), CaptureActivity.class);
                                        Intent2.putExtra("sp_sys", "1");
                                        startActivity(Intent2);
                                    }
                                })
                                .onDenied(new Action() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        if (AndPermission.hasAlwaysDeniedPermission(getActivity(), permissions)) {
                                            new PermissionSetting(getActivity()).showSetting(permissions);
                                        }
                                    }
                                })
                                .start();
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        if (XiaDanActivity.list.size() > 0) {
                            XiaDanActivity.list.clear();
                        }
                        if (XiaDanActivity.list_ll.size() > 0) {
                            XiaDanActivity.list_ll.clear();
                        }
                        System.out.println("list_ll.size()============1========="
                                + XiaDanActivity.list_ll.size());
                        System.out.println("list.size()==============1==========="
                                + XiaDanActivity.list.size());
//                        if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)) {
//                            requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.CAMERA_REQUEST);
//
//                        } else {
                        AndPermission.with(this)
                                .permission(Permission.CAMERA)
                                .onGranted(new Action() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        Intent Intent2 = new Intent(getActivity(), CaptureActivity.class);
                                        Intent2.putExtra("sp_sys", "1");
                                        startActivity(Intent2);
                                    }
                                })
                                .onDenied(new Action() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        if (AndPermission.hasAlwaysDeniedPermission(getActivity(), permissions)) {
                                            new PermissionSetting(getActivity()).showSetting(permissions);
                                        }
                                    }
                                })
                                .start();
//                        }
                    }
                }
                break;
            case R.id.ll_saoyisao_qd:// 扫一扫签到
                //			Intent Intent = new Intent(getActivity(),QianDaoBaoMingActivity.class);
                ////			Intent.putExtra("sp_sys", "2");
                //			startActivity(Intent);

                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        //					Intent Intent2 = new Intent(getActivity(),CaptureActivity.class);
                        Intent Intent2 = new Intent(getActivity(), QianDaoBaoMingActivity.class);
                        startActivity(Intent2);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent9);
                    } else {
                        //					Intent Intent2 = new Intent(getActivity(),CaptureActivity.class);
                        Intent Intent2 = new Intent(getActivity(), QianDaoBaoMingActivity.class);
                        startActivity(Intent2);
                    }
                }

                break;
            case R.id.ll_quhuo:// 取货
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        Intent Intent2 = new Intent(getActivity(),
                                QuHuoHaomaActivity.class);
                        Intent2.putExtra("sp_sys", "3");
                        startActivity(Intent2);
                    } else {
                        Intent intent = new Intent(getActivity(),
                                TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name_phone.equals("")) {
                        Intent intent9 = new Intent(getActivity(),
                                UserLoginActivity.class);
                        startActivity(intent9);
                    } else {

                        Intent Intent2 = new Intent(getActivity(),
                                QuHuoHaomaActivity.class);
                        Intent2.putExtra("sp_sys", "3");
                        startActivity(Intent2);
                    }
                }
                break;
            case R.id.index_item3:// 我的抽奖
                Toast.makeText(getActivity(), "功能还未开发，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_user:
                // Intent intentu = new
                // Intent(getActivity(),PersonCenterActivity.class);
                // startActivity(intentu);
                break;
            case R.id.zhuxiao:
                dialoglogin();
                break;
            default:
                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == Constant.CAMERA_REQUEST && grantResults.length > 0) {
//            int grantResult = grantResults[0];
//            if (PackageManager.PERMISSION_GRANTED == grantResult) {
//                Intent Intent2 = new Intent(getActivity(), com.zxing.android.CaptureActivity.class);
//                startActivity(Intent2);
//            } else {
//                Toast.makeText(context, "照相机权限已被拒绝", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public final static int CONSULT_DOC_PICTURE = 1000;
    public final static int CONSULT_DOC_CAMERA = 1001;

    private static final int PHOTO_REQUEST_CAMERA = 10;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 11;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 12;// 裁剪

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private int SELECT_PICTURE = 1;
    private int SELECT_CAMERA = 2;
    private int SELECT_SCAN = 0;
    private File file;
    private ProgressDialog pd;
    private Bitmap bmp;

    /**
     * 是否有sd卡
     *
     * @return
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {// 图库
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                System.out.println("图片的值=========uri========" + uri);
                crop(uri);
            } else if (requestCode == PHOTO_REQUEST_CAMERA) {// 拍照

                if (hasSdcard()) {
                    file = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_FILE_NAME);
                    crop(Uri.fromFile(file));
                    System.out.println("图片的值=========file========" + file);
                } else {
                    // Toast.makeText(this, "未找到存储卡，无法存储照片！", 0).show();
                }
            } else if (requestCode == PHOTO_REQUEST_CUT) {// 裁剪
                try {
                    bmp = data.getParcelableExtra("data");
                    networkImage.setImageBitmap(bmp);
                    tp_type = true;
                    File tempFile = BitmapUtils.saveBitmapFile(bmp, PHOTO_FILE_NAME);
                    System.out.println("图片的值1=================" + bmp);
                    System.out.println("图片的值2=================" + tempFile);
                    // upload(tempFile);//上传到服务器
                    // pd = new ProgressDialog(this);
                    // pd.setMessage("头像正在上传中请稍后");
                    // pd.show();
                    try {
                        imagePath = Utils.savePhoto(bmp, Environment
                                .getExternalStorageDirectory()
                                .getAbsolutePath(), String.valueOf(System
                                .currentTimeMillis()));
                        System.out.println("imagePath======================="
                                + imagePath);
                        // SimpleDateFormat f = new
                        // SimpleDateFormat("yyyyMMddHHmmssSSS");
                        // time = f.format(new Date());
                        // System.out.println("time============1============"+time);
                        new Thread() {
                            public void run() {
                                try {
                                    FTPClient client = new FTPClient();
                                    // client.connect("183.62.138.31", 2021);
                                    // client.login("zams", "yunsen1230.");
                                    // IP:60.205.151.60
                                    // Point:2021
                                    // 用户：zams
                                    // 密码：zams1230.
                                    client.connect("60.205.151.160", 2021);
                                    client.login("zams", "zams1230.");
                                    SimpleDateFormat f = new SimpleDateFormat(
                                            "yyyyMMddHHmmssSSS");
                                    time = f.format(new Date());
                                    yth = IndividualCenterActivity.yth;
                                    String remotePathTmp = "phone/" + "" + yth
                                            + "";// 路径
                                    System.out
                                            .println("time===========2============="
                                                    + time);
                                    System.out
                                            .println("remotePathTmp========================"
                                                    + remotePathTmp);

                                    try {
                                        client.createDirectory(remotePathTmp);// 客户端创建目录
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        client.changeDirectory(remotePathTmp);

                                        File file = new File(imagePath);
                                        FileInputStream fis = new FileInputStream(
                                                file);
                                        try {
                                            client.upload(time + ".jpg", fis,
                                                    0, 0, null);
                                        } catch (FTPDataTransferException e) {

                                            e.printStackTrace();
                                        } catch (FTPAbortedException e) {

                                            e.printStackTrace();
                                        }
                                        fis.close();
                                        client.logout();// exit
                                    }

                                    tupian = "/upload/phone/" + yth + "/"
                                            + time + ".jpg";
                                    System.out
                                            .println("tupian1--------------------------"
                                                    + tupian);

                                } catch (IllegalStateException e) {
                                    e.printStackTrace();// 非法状态异常
                                } catch (FTPIllegalReplyException e) {
                                    e.printStackTrace();// 非法回复异常
                                } catch (FTPException e) {
                                    e.printStackTrace();// 异常
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                gettouxiang();
                            }

                            ;

                        }.start();

                        // new Thread() {
                        // public void run() {
                        // try {
                        // FTPClient client = new FTPClient();
                        // client.connect("183.62.138.31", 2021);
                        // client.login("zams", "yunsen1230.");
                        // SimpleDateFormat f = new
                        // SimpleDateFormat("yyyyMMddHHmmssSSS");
                        // time = f.format(new Date());
                        // yth = IndividualCenterActivity.yth;
                        // String remotePathTmp = "phone/" + "" + yth +"";//路径
                        // System.out.println("remotePathTmp========================"+remotePathTmp);
                        //
                        // try {
                        // client.createDirectory(remotePathTmp);//客户端创建目录
                        // } catch (Exception e) {
                        // e.printStackTrace();
                        // } finally {
                        // client.changeDirectory(remotePathTmp);
                        //
                        // File file = new File(imagePath);
                        // FileInputStream fis = new FileInputStream(file);
                        // try {
                        // client.upload(time + ".jpg", fis, 0, 0, null);
                        // } catch (FTPDataTransferException e) {
                        //
                        // e.printStackTrace();
                        // } catch (FTPAbortedException e) {
                        //
                        // e.printStackTrace();
                        // }
                        // fis.close();
                        // client.logout();//exit
                        // }
                        //
                        // tupian = "/upload/phone/" + yth + "/"+ time + ".jpg";
                        // System.out.println("tupian--------------------------"+tupian);
                        //
                        // } catch (IllegalStateException e) {
                        // e.printStackTrace();//非法状态异常
                        // }
                        // catch (FTPIllegalReplyException e) {
                        // e.printStackTrace();//非法回复异常
                        // } catch (FTPException e) {
                        // e.printStackTrace();//异常
                        // } catch (IOException e) {
                        // e.printStackTrace();
                        // }
                        //
                        // gettouxiang();
                        // };
                        //
                        // }.start();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 剪切图片
     *
     * @param uri
     * @function:
     * @author:yl
     * @date:2013-12-30
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void gettouxiang() {

        String imgUrl = "/upload/phone/" + yth + "/" + time + ".jpg";
        System.out.println("imgUrl--------------------------" + imgUrl);
        Editor editor = spPreferences.edit();
        editor.putString("avatar", imgUrl);
        editor.commit();
        String strUrl = RealmName.REALM_NAME_LL
                + "/user_avatar_save?user_name=" + user_name + "&user_id="
                + user_id + "&user_avatar=" + imgUrl + "&sign=" + login_sign
                + "";
        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    System.out.println("arg1--------------------------" + arg1);
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    object.getString("info");
                    // onResume();
                    if (status.equals("y")) {
                        Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                        System.out.println("上传成功--------------------------");
                    } else {
                        Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, getActivity());
    }

    // 创建删除方法：
    // public void clearData() {
    // // spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
    // spPreferences.edit().clear().commit();
    // }

    /**
     * 显示修改头像的对话框
     */
    // protected void showChoosePicDialog() {
    // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // builder.setTitle("设置头像");
    // String[] items = { "选择本地照片", "拍照" };
    // builder.setNegativeButton("取消", null);
    // builder.setItems(items, new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // switch (which) {
    // case CHOOSE_PICTURE: // 选择本地照片
    // Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
    // System.out.println("本地照片-----------------"+openAlbumIntent);
    // openAlbumIntent.setType("image/*");
    // startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    // break;
    // case TAKE_PICTURE: // 拍照
    // Intent openCameraIntent = new Intent(
    // MediaStore.ACTION_IMAGE_CAPTURE);
    // tempUri = Uri.fromFile(new
    // File(Environment.getExternalStorageDirectory(), "image.jpg"));
    // System.out.println("拍照================"+tempUri);
    // // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
    // openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
    // startActivityForResult(openCameraIntent, TAKE_PICTURE);
    //
    // break;
    // }
    // }
    //
    // });
    // builder.create().show();
    // }

    // @Override
    // public void onActivityResult(int requestCode, int resultCode, Intent
    // data) {
    // super.onActivityResult(requestCode, resultCode, data);
    // // if (resultCode == RESULT_OK) { // 如果返回码是可以用的
    // switch (requestCode) {
    // //拍照
    // case TAKE_PICTURE:
    // startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
    // break;
    // //上传图片
    // case CHOOSE_PICTURE:
    // startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
    // break;
    // case CROP_SMALL_PICTURE:
    // if (data != null) {
    // setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
    // }
    // break;
    // }
    // // }
    // }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    // protected void startPhotoZoom(Uri uri) {
    // if (uri == null) {
    // Log.i("tag", "The uri is not exist.");
    // }
    // tempUri = uri;
    // // System.out.println("裁剪图片方法实现================"+tempUri);
    // Intent intent = new Intent("com.android.camera.action.CROP");
    // intent.setDataAndType(uri, "image/*");
    // // 设置裁剪
    // intent.putExtra("crop", "true");
    // // aspectX aspectY 是宽高的比例
    // intent.putExtra("aspectX", 1);
    // intent.putExtra("aspectY", 1);
    // // outputX outputY 是裁剪图片宽高
    // intent.putExtra("outputX", 300);
    // intent.putExtra("outputY", 300);
    // intent.putExtra("return-data", true);
    // startActivityForResult(intent, CROP_SMALL_PICTURE);
    // }

    // public void saveBitmapFile(Bitmap bitmap){
    // File file=new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
    // try {
    // BufferedOutputStream bos = new BufferedOutputStream(new
    // FileOutputStream(file));
    // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    //
    // bos.flush();
    // bos.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        photo = extras.getParcelable("data");
        if (extras != null) {
            // photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            System.out.println("图片的值1=================" + photo);
            System.out.println("图片的值2=================" + tempUri);
            networkImage.setImageBitmap(photo);
            try {
                imagePath = Utils.savePhoto(photo, Environment
                                .getExternalStorageDirectory().getAbsolutePath(),
                        String.valueOf(System.currentTimeMillis()));
                System.out.println("imagePath======================="
                        + imagePath);

                new Thread() {
                    public void run() {
                        try {
                            FTPClient client = new FTPClient();
                            client.connect("183.62.138.31", 2021);
                            client.login("zams", "yunsen1230.");
                            SimpleDateFormat f = new SimpleDateFormat(
                                    "yyyyMMddHHmmssSSS");
                            time = f.format(new Date());
                            yth = IndividualCenterActivity.yth;
                            String remotePathTmp = "phone/" + "" + yth + "";// 路径
                            System.out.println("========================"
                                    + remotePathTmp);

                            try {
                                client.createDirectory(remotePathTmp);// 客户端创建目录
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                client.changeDirectory(remotePathTmp);

                                File file = new File(imagePath);
                                FileInputStream fis = new FileInputStream(file);
                                try {
                                    client.upload(time + ".jpg", fis, 0, 0,
                                            null);
                                } catch (FTPDataTransferException e) {

                                    e.printStackTrace();
                                } catch (FTPAbortedException e) {

                                    e.printStackTrace();
                                }
                                fis.close();
                                client.logout();// exit
                            }

                            tupian = "/upload/phone/" + yth + "/" + time
                                    + ".jpg";
                            System.out
                                    .println("tupian1--------------------------"
                                            + tupian);

                        } catch (IllegalStateException e) {
                            e.printStackTrace();// 非法状态异常
                        } catch (FTPIllegalReplyException e) {
                            e.printStackTrace();// 非法回复异常
                        } catch (FTPException e) {
                            e.printStackTrace();// 异常
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        gettouxiang();
                    }

                    ;

                }.start();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * 第0个列表数据解析
     */
    ArrayList<String> list_0;
    ArrayList<String> list_1;
    ArrayList<String> list_2;
    ArrayList<String> list_3;
    ArrayList<String> list_4;

    private void load_list() {
        list_0 = new ArrayList<String>();
        list_1 = new ArrayList<String>();
        list_2 = new ArrayList<String>();
        list_3 = new ArrayList<String>();
        list_4 = new ArrayList<String>();
        list1 = new ArrayList<MyOrderData>();
        System.out.println("=========user_id============" + user_id);
        AsyncHttp.get(RealmName.REALM_NAME_LL
                        + "/get_order_page_size_list?user_id=" + user_id + ""
                        + "&page_size=1000&page_index=1&strwhere=datatype=1&orderby=",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        // System.out.println("=========数据接口============"+arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status_1 = object.getString("status");
                            if (status_1.equals("y")) {
                                //								ArrayList<MyOrderData> list1 = new ArrayList<MyOrderData>();
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //									MyOrderData md = new MyOrderData();
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    //									md.setPayment_status(obj.getString("payment_status"));
                                    String payment_status = obj.getString("payment_status");
                                    String express_status = obj.getString("express_status");
                                    String status = obj.getString("status");
                                    // 订单状态
                                    if (payment_status.equals("1")) {
                                        System.out.println("待付款=============");
                                        list_1.add(payment_status);
                                        //									} else if (status.equals("4")) {
                                        //										list_2.add(status)
                                    } else if (payment_status.equals("2") && express_status.equals("1") && status.equals("2")) {
                                        System.out.println("待发货=============");
                                        list_2.add(payment_status);
                                    } else if (payment_status.equals("2") && express_status.equals("2") && status.equals("2")) {
                                        System.out.println("待收货=============");
                                        list_3.add(payment_status);
                                    } else if (payment_status.equals("2") && express_status.equals("2") && status.equals("3")) {
                                        System.out.println("已完成=============");
                                        list_4.add(payment_status);
                                    }

                                    list_0.add(payment_status);
                                    // tv_unpay,tv_delivered,tv_received,tv_payed
                                }
                                System.out.println("========list_0.size()======1=====" + list_0.size());
                                String num = String.valueOf(list_0.size());
                                tv_quanbu.setText(num);
                                String num1 = String.valueOf(list_1.size());
                                tv_unpay.setText(num1);
                                String num2 = String.valueOf(list_2.size());
                                tv_delivered.setText(num2);
                                String num3 = String.valueOf(list_3.size());
                                tv_received.setText(num3);
                                String num4 = String.valueOf(list_4.size());
                                tv_payed.setText(num4);

                                list_0 = null;
                                list_1 = null;
                                list_2 = null;
                                list_3 = null;
                                list_4 = null;
                            } else {
                                // progress.CloseProgress();
                            }
                            //							load_list1();
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }

                }, getActivity());
    }


    /**
     * 提示是否修改头像
     */
    // protected void dialog() {
    // AlertDialog.Builder builder = new Builder(getActivity());
    // builder.setMessage("确认要修改图片吗?");
    // builder.setTitle("提示");
    // builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // setImage();
    // }
    // });
    //
    // builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // }
    // });
    //
    // builder.create().show();
    // }
    //
    // private void setImage() {
    //
    // // 使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
    // Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
    // getAlbum.setType(IMAGE_TYPE);
    // startActivityForResult(getAlbum, IMAGE_CODE);
    // }

    // @Override
    // public void onActivityResult(int requestCode, int resultCode, Intent
    // data) {
    // try {
    //
    // if (resultCode != -1) { // 此处的 RESULT_OK 是系统自定义得一个常量
    // Log.e("TAG->onresult", "ActivityResult resultCode error");
    // return;
    // }
    // Bitmap bm = null;
    // // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
    // ContentResolver resolver = getActivity().getContentResolver();
    // // 此处的用于判断接收的Activity是不是你想要的那个
    // if (requestCode == IMAGE_CODE) {
    // try {
    // Uri originalUri = data.getData(); // 获得图片的uri
    // bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
    // // 显得到bitmap图片
    // System.out.println("bm-------------------------"+bm);
    // // img_head.setImageBitmap(bm);
    // // 这里开始的第二部分，获取图片的路径：
    // String[] proj = { MediaStore.Images.Media.DATA };
    // System.out.println("proj-------------------------"+proj);
    // // 好像是android多媒体数据库的封装接口，具体的看Android文档
    // Cursor cursor = getActivity().managedQuery(originalUri, proj,null, null,
    // null);
    // // 按我个人理解 这个是获得用户选择的图片的索引值
    // int column_index =
    // cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    // // 将光标移至开头 ，这个很重要，不小心很容易引起越界
    // cursor.moveToFirst();
    // // 最后根据索引值获取图片路径
    // path = cursor.getString(column_index);
    // // bm = Utils.toRoundBitmap(bm,originalUri);
    // System.out.println("path-------------------------"+path);
    // ///storage/emulated/0/DCIM/Camera/IMG_20161106_200658.jpg
    // new Thread() {
    // public void run() {
    // try {
    // /**
    // // * 操作FTP文件
    // // */
    // FTPClient client = new FTPClient();
    // client.connect("183.62.138.31", 2021);
    // client.login("zams", "yunsen1230.");
    //
    // SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    // time = f.format(new Date());
    //
    // String reTransmitFolderName = time + "_Folder";
    // System.out.println("========================"+reTransmitFolderName);
    // String remotePathTmp = "phone/" + "" + yth +"";//路径
    // System.out.println("========================"+remotePathTmp);
    //
    // try {
    // client.createDirectory(remotePathTmp);//客户端创建目录
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // client.changeDirectory(remotePathTmp);
    // File file = new File(path);
    // FileInputStream fis = new FileInputStream(file);
    //
    // try {
    // client.upload(time + ".jpg", fis, 0, 0, null);
    // } catch (FTPDataTransferException e) {
    //
    // e.printStackTrace();
    // } catch (FTPAbortedException e) {
    //
    // e.printStackTrace();
    // }
    //
    // fis.close();
    // client.logout();//exit
    // }
    //
    // //String imgUrl, String directoryname, String filename
    // } catch (IllegalStateException e) {
    // e.printStackTrace();//非法状态异常
    // }
    // catch (FTPIllegalReplyException e) {
    // e.printStackTrace();//非法回复异常
    // } catch (FTPException e) {
    // e.printStackTrace();//异常
    // // } catch (FTPDataTransferException e) {
    // // e.printStackTrace();//数据转移异常
    // // } catch (FTPAbortedException e) {
    // // e.printStackTrace();//失败异常
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // gettouxiang();
    // onResume();
    // };
    //
    // }.start();
    //
    // } catch (IOException e) {
    // Log.e("TAG-->Error", e.toString());
    // e.printStackTrace();
    // }
    // }
    //
    // } catch (Exception e) {
    //
    // e.printStackTrace();
    // }
    // }

}
