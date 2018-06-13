package com.zams.www.health;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

/**
 * Created by Administrator on 2018/6/8.
 */

public class EvaluatedOKActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backImg;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluated_ok);
        initView();
        initData();
        initListener();
        requestData();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        backButton = (Button) findViewById(R.id.back_btn);
    }


    private void initData() {
    }

    private void initListener() {
        backImg.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    private void requestData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
            case R.id.back_img:
                finish();
                break;
        }
    }
}
