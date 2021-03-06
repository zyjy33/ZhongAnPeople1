package com.hengyushop.demo.service;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.DialogProgress;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.MainFragment;
import com.zams.www.R;
import com.zams.www.weiget.PermissionSetting;

import java.util.List;

/**
 * 平台热线
 *
 * @author
 */
public class PlatformhotlineActivity extends Activity implements OnClickListener {
    public static final String PHONE_ONE = "phone_number_one";
    public static final String PHONE_TWO = "phone_number_two";
    public Activity mContext;
    public static String give_pension, article_id;
    TextView tv_dianhua1, tv_dianhua2;
    ImageView iv_guanxi;
    private String mTwoNumber;
    private String mOneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingtai_rexian);
        initUI();
    }

    protected void initUI() {
        Intent intent = getIntent();
        mOneNumber = intent.getStringExtra(PHONE_ONE);
        mTwoNumber = intent.getStringExtra(PHONE_TWO);
        tv_dianhua1 = (TextView) findViewById(R.id.tv_dianhua1);
        tv_dianhua2 = (TextView) findViewById(R.id.tv_dianhua2);

        if (!TextUtils.isEmpty(mOneNumber)) {
            tv_dianhua1.setVisibility(View.VISIBLE);
            tv_dianhua1.setOnClickListener(this);
            tv_dianhua1.setText(mOneNumber);
        }
        if (!TextUtils.isEmpty(mTwoNumber)) {
            tv_dianhua2.setVisibility(View.VISIBLE);
            tv_dianhua2.setOnClickListener(this);
            tv_dianhua2.setText(mTwoNumber);
        }
        iv_guanxi = (ImageView) findViewById(R.id.iv_guanxi);
        iv_guanxi.setImageResource(R.drawable.limitbuy_esoterica_close);
        iv_guanxi.setOnClickListener(this);
    }

    /**
     * 点击触发事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_guanxi:// 取消
                iv_guanxi.setVisibility(View.INVISIBLE);
                finish();
                break;
            case R.id.tv_dianhua1://
                AndPermission.with(this)
                        .permission(Permission.CALL_PHONE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
                                intent.setData(Uri.parse("tel:" + mOneNumber));
//                                intent.setData(Uri.parse("tel:" + "400-606-1201"));
                                startActivity(intent);
                                finish();
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                new PermissionSetting(PlatformhotlineActivity.this).showSetting(permissions);
                            }
                        }).start();
                break;
            case R.id.tv_dianhua2://

                AndPermission.with(this)
                        .permission(Permission.CALL_PHONE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent1 = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
                                intent1.setData(Uri.parse("tel:" + mTwoNumber));
//                                intent1.setData(Uri.parse("tel:" + "010-62575060"));
                                startActivity(intent1);
                                finish();
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                new PermissionSetting(PlatformhotlineActivity.this).showSetting(permissions);
                            }
                        }).start();
                break;
            default:
                break;
        }
    }

}