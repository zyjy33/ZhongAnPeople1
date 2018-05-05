package com.zams.www.health;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lglottery.www.http.HttpUtils;
import com.zams.www.R;
import com.zams.www.health.fragment.AllOrderFragment;
import com.zams.www.health.fragment.NoEvaluatedFragment;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NoEvaluatedOrderActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView backImg;
    private TextView leftTitle;
    private TextView rightTitle;
    private Drawable mSelectDrawable;
    private AllOrderFragment mLeftFragment;
    private NoEvaluatedFragment mRightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_evaluated_order);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.iv_back);
        leftTitle = (TextView) findViewById(R.id.left_title_tv);
        rightTitle = (TextView) findViewById(R.id.right_title_tv);
    }

    private void initData() {
        mSelectDrawable = getResources().getDrawable(R.drawable.orange_bg);
        mSelectDrawable.setBounds(0, 0,
                HttpUtils.dip2px(this, 50f), HttpUtils.dip2px(this, 1f));
        leftTitle.setCompoundDrawables(null, null, null, mSelectDrawable);
        leftTitle.setSelected(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mLeftFragment = new AllOrderFragment();
        transaction.add(R.id.no_evaluated_layout, mLeftFragment);

    }

    private void initListener() {
        backImg.setOnClickListener(this);
        leftTitle.setOnClickListener(this);
        rightTitle.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.left_title_tv:
                transaction.replace(R.id.no_evaluated_layout, mLeftFragment);
                leftTitle.setSelected(true);
                rightTitle.setSelected(false);
                leftTitle.setCompoundDrawables(null, null, null, mSelectDrawable);
                rightTitle.setCompoundDrawables(null, null, null, null);
                break;
            case R.id.right_title_tv:
                if (mRightFragment == null) {
                    mRightFragment = new NoEvaluatedFragment();
                }
                transaction.replace(R.id.no_evaluated_layout, mRightFragment);
                leftTitle.setSelected(false);
                rightTitle.setSelected(true);
                leftTitle.setCompoundDrawables(null, null, null, null);
                rightTitle.setCompoundDrawables(null, null, null, mSelectDrawable);
                break;
        }

        transaction.commit();
    }
}
