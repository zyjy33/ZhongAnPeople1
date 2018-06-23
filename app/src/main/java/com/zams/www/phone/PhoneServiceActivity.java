package com.zams.www.phone;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.service.PlatformhotlineActivity;
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
    public static final String PHONE_NUM_2 = "phone_number_2";

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
    private String mPhoneNumber2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        mPhoneNumber2 = intent.getStringExtra(PHONE_NUM_2);
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
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    Toast.makeText(this, "此功能暂未开放", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, PlatformhotlineActivity.class);
                    intent.putExtra(PlatformhotlineActivity.PHONE_ONE, mPhoneNumber);
                    if (mPhoneNumber2 == null) {
                        mPhoneNumber2 = "";
                    }
                    intent.putExtra(PlatformhotlineActivity.PHONE_TWO, mPhoneNumber2);
                    startActivity(intent);
                }
                break;
        }
    }
}
