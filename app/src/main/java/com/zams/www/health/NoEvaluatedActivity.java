package com.zams.www.health;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.hengyu.web.Constant;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;
import com.zams.www.weiget.EvaluatedStateView;

import org.jsoup.Connection;

/**
 * Created by Administrator on 2018/6/8.
 * 评价页面
 */

public class NoEvaluatedActivity extends BaseActivity implements View.OnClickListener {

    private String mOrderNo;
    private EditText serviceEvaluatedTv;
    private EditText personEvaluatedTv;
    private EvaluatedStateView serviceEvaluatedView;
    private EvaluatedStateView personEvaluatedView;
    private Button submitBtn;
    private String mUserId;
    private int mEvaluateStatus = 0;
    private String mEvaluateDesc;
    private int mWaiterStatus = 0;
    private String mWaiterDesc;
    private View backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_no_evaluated);
        initView();
        initData();
        initListener();
        requestData();
    }

    private void initView() {
        serviceEvaluatedTv = ((EditText) findViewById(R.id.service_evaluated_tv));
        serviceEvaluatedView = ((EvaluatedStateView) findViewById(R.id.service_evaluated_view));
        personEvaluatedTv = ((EditText) findViewById(R.id.person_evaluated_tv));
        personEvaluatedView = ((EvaluatedStateView) findViewById(R.id.person_evaluated_view));
        submitBtn = ((Button) findViewById(R.id.submit_btn));
        backImg = findViewById(R.id.back_img);

    }

    private void initData() {
        Intent intent = getIntent();
        mOrderNo = intent.getStringExtra(Constant.ORDER_NO);
        SharedPreferences sp = getSharedPreferences(Constant.LONGUSERSET, MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
    }

    private void initListener() {
        submitBtn.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    private void requestData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                mEvaluateDesc = serviceEvaluatedTv.getText().toString();
                mWaiterDesc = personEvaluatedTv.getText().toString();
                mEvaluateStatus = serviceEvaluatedView.getMaxIndex();
                mWaiterStatus = personEvaluatedView.getMaxIndex();
                if (TextUtils.isEmpty(mEvaluateDesc)) {
                    Toast.makeText(this, "请输入对我们的评价", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mWaiterDesc)) {
                    Toast.makeText(this, "请输入对我的服务评价", Toast.LENGTH_SHORT).show();
                } else {
                    HttpProxy.submitEvaluatedData(this, mUserId, mOrderNo,
                            String.valueOf(mEvaluateStatus), mEvaluateDesc, String.valueOf(mWaiterStatus), mWaiterDesc, new HttpCallBack<Boolean>() {
                                @Override
                                public void onSuccess(Boolean responseData) {
                                    if (responseData) {
                                        Intent intent = new Intent(NoEvaluatedActivity.this, EvaluatedOKActivity.class);
                                        NoEvaluatedActivity.this.startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onError(Connection.Request request, String e) {
                                    Toast.makeText(NoEvaluatedActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
            case R.id.back_img:
                finish();
                break;

        }
    }
}
