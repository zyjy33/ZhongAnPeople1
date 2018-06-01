package com.hengyushop.demo.my;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.R;
import com.zams.www.weiget.PermissionSetting;
import com.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务顾问
 *
 * @author Administrator
 */
public class ChuangKeActivity extends BaseActivity implements OnClickListener {
    private ImageView iv_fanhui, img_menu;
    private Button fanhui;
    public static Handler handler;
    String user_name, user_id, nickname;
    private SharedPreferences spPreferences;
    public static AQuery mAq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chuangke_title);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mAq = new AQuery(ChuangKeActivity.this);
        spPreferences = getSharedPreferences("longuserset",
                Context.MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        try {
            Button button = (Button) findViewById(R.id.btn_login);
            button.setOnClickListener(this);
            Button btn_chuangke = (Button) findViewById(R.id.btn_chuangke);
            btn_chuangke.setOnClickListener(this);
            fanhui = (Button) findViewById(R.id.fanhui);
            fanhui.setOnClickListener(this);
            img_menu = (ImageView) findViewById(R.id.img_menu);
            Button enter_shop = (Button) findViewById(R.id.enter_shop);
            enter_shop.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent4 = new Intent(ChuangKeActivity.this,
                            Webview1.class);
                    //					intent4.putExtra("ylsc_id", "5977");
                    intent4.putExtra("web_id", "5977");
                    startActivity(intent4);
                }
            });

            loadguanggao();

            handler = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 2:
                            finish();
                            break;
                    }
                }
            };

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.btn_login:
                Intent intent = new Intent(ChuangKeActivity.this,
                        ShengJiCkActivity.class);
                // Intent intent = new
                // Intent(ChuangKeActivity.this,ChongZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_chuangke:

                AndPermission.with(ChuangKeActivity.this)
                        .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent1 = new Intent(ChuangKeActivity.this,
                                        CaptureActivity.class);
                                startActivity(intent1);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                new PermissionSetting(ChuangKeActivity.this).showSetting(permissions);
                            }
                        }).start();
                break;
            default:
                break;
        }
    }

    // @Override
    // protected void onResume() {
    //
    // super.onResume();
    // String haoma = getIntent().getStringExtra("zhou");
    //
    // System.out.println("======haoma============"+haoma);
    // }

    private void loadguanggao() {
        try {

            // 广告滚动
            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/get_adbanner_list?advert_id=13",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                JSONArray array = object.getJSONArray("data");
                                int len = array.length();
                                ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
                                for (int i = 0; i < len; i++) {
                                    AdvertDao1 ada = new AdvertDao1();
                                    JSONObject json = array.getJSONObject(i);
                                    ada.setId(json.getString("id"));
                                    ada.setAd_url(json.getString("ad_url"));
                                    String ad_url = ada.getAd_url();
                                    //									ImageLoader imageLoader = ImageLoader.getInstance();
                                    //									imageLoader.displayImage(RealmName.REALM_NAME_HTTP + ad_url,img_menu);
                                    mAq.id(img_menu).image(RealmName.REALM_NAME_HTTP + ad_url);
                                    images.add(ada);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
