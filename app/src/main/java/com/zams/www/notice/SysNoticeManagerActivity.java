package com.zams.www.notice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.android.hengyu.web.Constant;
import com.zams.www.R;
import com.zams.www.notice.fragment.OneNoticeFragment;
import com.zams.www.notice.fragment.OtherNoticeFragment;
import com.zams.www.notice.fragment.TwoNoticeFragment;

/**
 * Created by Administrator on 2018/6/13.
 */

public class SysNoticeManagerActivity extends FragmentActivity implements View.OnClickListener {

    public static final String OPEN_TYPE_KEY = "open_type_key";
    public static final String REQUEST_TYPE_KEY = "request_type_key";
    public static final String TITLE_KEY = "title_key";

    private TextView titleTv;
    private String mTitle;
    private int mOpenType;
    private String mRequestType;

    private Fragment mFragment;
    private String mUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_notice_one);
        initView();
        initData();
        initListener();
        requestData();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.title_tv);
        findViewById(R.id.back_img).setOnClickListener(this);
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(TITLE_KEY);
            mOpenType = intent.getIntExtra(OPEN_TYPE_KEY, 0);
            mRequestType = intent.getStringExtra(REQUEST_TYPE_KEY);
        }
        titleTv.setText(mTitle);
//        switch (mOpenType) {
//            case 1:
//            case 4:
//            case 5:
//                mFragment = new OneNoticeFragment();
//                break;
//            case 2:
//            case 3:
//                mFragment = new TwoNoticeFragment();
//                break;
//            case 6:
//                break;
//            default:
//                mFragment = new OneNoticeFragment();
//        }
        mFragment = new OtherNoticeFragment();
        Bundle args = new Bundle();
        args.putString(Constant.USER_ID, mUserId);
        args.putString(REQUEST_TYPE_KEY, mRequestType);
        mFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout_control, mFragment);
        transaction.commit();
    }

    private void initListener() {

    }

    private void requestData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_img) {
            finish();
        }
    }
}
