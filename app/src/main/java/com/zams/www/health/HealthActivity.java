package com.zams.www.health;

import com.lglottery.www.http.HttpUtils;
import com.zams.www.R;
import com.zams.www.health.fragment.HealthManagerFragment;
import com.zams.www.health.fragment.HealthOrderFragment;
import com.zams.www.health.fragment.NoEvaluatedFragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HealthActivity extends FragmentActivity implements OnClickListener {
    private TextView healthTv;
    private TextView orderTv;
    private ImageView backImg;
    private Drawable mSelectDrawable;
    private HealthManagerFragment mLeftFragment;
    private NoEvaluatedFragment mRightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_activity);
        initView();
        initData();
    }

    private void initView() {
        healthTv = (TextView) findViewById(R.id.health_manager_tv);
        orderTv = (TextView) findViewById(R.id.order_info_tv);
        backImg = (ImageView) findViewById(R.id.iv_back);

        mSelectDrawable = getResources().getDrawable(R.drawable.orange_bg);
        mSelectDrawable.setBounds(0, 0,
                HttpUtils.dip2px(this, 50f), HttpUtils.dip2px(this, 1f));
        healthTv.setCompoundDrawables(null, null, null, mSelectDrawable);

        healthTv.setSelected(true);
        healthTv.setOnClickListener(this);
        orderTv.setOnClickListener(this);
        backImg.setOnClickListener(this);

    }

    private void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mLeftFragment = new HealthManagerFragment();
        transaction.add(R.id.health_and_comment_layout, mLeftFragment);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.health_manager_tv:
                transaction.replace(R.id.health_and_comment_layout, mLeftFragment);
                healthTv.setSelected(true);
                orderTv.setSelected(false);
                healthTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                orderTv.setCompoundDrawables(null, null, null, null);
                break;
            case R.id.order_info_tv:
                if (mRightFragment == null) {
                    mRightFragment = new NoEvaluatedFragment();
                }
                transaction.replace(R.id.health_and_comment_layout, mRightFragment);
                healthTv.setSelected(false);
                orderTv.setSelected(true);
                orderTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                healthTv.setCompoundDrawables(null, null, null, null);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mRightFragment != null) {
            mRightFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void toHealthManagerFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.health_and_comment_layout, mLeftFragment);
        healthTv.setSelected(true);
        orderTv.setSelected(false);
        healthTv.setCompoundDrawables(null, null, null, mSelectDrawable);
        orderTv.setCompoundDrawables(null, null, null, null);
        transaction.commit();
    }
}
