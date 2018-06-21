package com.zams.www.phone;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengyushop.demo.at.BaseActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.R;
import com.zams.www.weiget.PermissionSetting;


import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class PhoneServiceActivity extends BaseActivity implements View.OnClickListener {
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String INM_RES_ID = "img_res_id";
    public static final String PHONE_NUM = "phone_number";
    private View backImg;
    private TextView centerTitle;
    private TextView phoneServiceTitle;
    private TextView phoneServiceContent;
    private ImageView phoneServiceImg;
    private Button startCall;
    private String mTitle;
    private String mContent;
    private int mImgResId;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_service);
        initView();
        initData();
    }


    private void initView() {
        backImg = findViewById(R.id.back_img);
        centerTitle = (TextView) findViewById(R.id.center_title);
        phoneServiceTitle = (TextView) findViewById(R.id.phone_service_title);
        phoneServiceContent = (TextView) findViewById(R.id.phone_service_content);
        phoneServiceImg = (ImageView) findViewById(R.id.phone_service_img);
        startCall = (Button) findViewById(R.id.start_call_btn);

    }

    private void initData() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE);
        mContent = intent.getStringExtra(CONTENT);
        mImgResId = intent.getIntExtra(INM_RES_ID, 1);
        mPhoneNumber = intent.getStringExtra(PHONE_NUM);
        if (mImgResId != 1) {
            phoneServiceImg.setImageResource(mImgResId);
        }
        centerTitle.setText(mTitle);
        phoneServiceTitle.setText(mTitle);
        phoneServiceContent.setText(mContent);
        startCall.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.start_call_btn:
                AndPermission.with(this)
                        .permission(Permission.CALL_PHONE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent1 = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
                                intent1.setData(Uri.parse("tel:" + mPhoneNumber));
                                startActivity(intent1);
                                finish();
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                new PermissionSetting(PhoneServiceActivity.this).showSetting(permissions);
                            }
                        }).start();
                break;
        }
    }
}
