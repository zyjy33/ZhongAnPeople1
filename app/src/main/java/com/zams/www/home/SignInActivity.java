package com.zams.www.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.JuDuiHuanActivity;
import com.hengyushop.demo.my.MyAssetsActivity;
import com.zams.www.R;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;
import com.zams.www.utils.UiUtils;

import org.jsoup.Connection;

/**
 * Created by Administrator on 2018/6/22.
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private TextView signInRule;
    private LinearLayout signInLayout;
    private TextView signInSubTv;
    private TextView signInTv;
    private TextView signInDayTv;
    private TextView signInWelfareTv;
    private ImageView signInExchangeImg;
    private ImageView signInWelfareImg;
    private FrameLayout signTopLayout;
    private Dialog mSignRuleDialog;
    private Dialog mSignOKDialog;
    private ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);
        initView();
        initData();
        initListener();
        requestDatas();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        signInRule = (TextView) findViewById(R.id.sign_in_rule);
        signInLayout = (LinearLayout) findViewById(R.id.sign_in_layout);
        signTopLayout = (FrameLayout) findViewById(R.id.sign_top_layout);
        signInTv = (TextView) findViewById(R.id.sign_in_tv);
        signInSubTv = (TextView) findViewById(R.id.sign_in_sub_tv);
        signInDayTv = (TextView) findViewById(R.id.sign_in_day_tv);
        signInWelfareTv = (TextView) findViewById(R.id.sign_in_welfare_tv);
        signInExchangeImg = (ImageView) findViewById(R.id.sign_in_exchange_img);
        signInWelfareImg = (ImageView) findViewById(R.id.fuli_select_img);
    }

    private void initData() {
        int height = (int) (UiUtils.getScreenWidth() * 0.288);
        signInExchangeImg.getLayoutParams().height = height;
        signInWelfareImg.getLayoutParams().height = height;
        signTopLayout.getLayoutParams().height = (int) (UiUtils.getScreenWidth() * 0.59);
    }


    private void initListener() {
        signInRule.setOnClickListener(this);
        signInLayout.setOnClickListener(this);
        signInExchangeImg.setOnClickListener(this);
        signInWelfareImg.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    private void requestDatas() {
        HttpProxy.checkIsSignIn(new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                if (responseData) {
                    signInTv.setText("已签到");
                    signInSubTv.setVisibility(View.GONE);
                    signInTv.setTextColor(getResources().getColor(R.color.color_888888));
                    signInLayout.setClickable(false);
                    signInLayout.setFocusable(false);
                } else {
                    signInTv.setText("签到");
                    signInSubTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Connection.Request request, String e) {

            }
        });
        getUserWelfareValue();
        getUserCommentSignIn();
    }

    private void getUserCommentSignIn() {
        HttpProxy.userCommentSignIn(new HttpCallBack<Integer>() {
            @Override
            public void onSuccess(Integer responseData) {
                signInDayTv.setText(String.valueOf(responseData));
            }

            @Override
            public void onError(Connection.Request request, String e) {

            }
        });
    }

    private void getUserWelfareValue() {
        /**
         * 获取福利
         */
        HttpProxy.userWelfareValue(new HttpCallBack<Integer>() {
            @Override
            public void onSuccess(Integer responseData) {
                signInWelfareTv.setText(String.valueOf(responseData));
            }

            @Override
            public void onError(Connection.Request request, String e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.sign_in_rule://活动规则
                showRuleDialog();
                break;
            case R.id.sign_in_layout:
                signIn();
                break;
            case R.id.sign_in_exchange_img:
                Intent Intent2 = new Intent(this, JuDuiHuanActivity.class);
                startActivity(Intent2);
                break;
            case R.id.fuli_select_img:
                Intent intent = new Intent(this, MyAssetsActivity.class);
                intent.putExtra("status", "3");
                startActivity(intent);
                break;

        }
    }

    /**
     * 签到
     */
    private void signIn() {
        HttpProxy.userSignIn(new HttpCallBack<Integer>() {
            @Override
            public void onSuccess(Integer responseData) {
                if (responseData != -1) {
                    signInTv.setText("已签到");
                    signInTv.setTextColor(getResources().getColor(R.color.color_888888));
                    signInSubTv.setVisibility(View.GONE);
                    signInLayout.setClickable(false);
                    signInLayout.setFocusable(false);
                    getUserWelfareValue();
                    getUserCommentSignIn();
                    showSignOKDialog(responseData);
                } else {
                    Toast.makeText(SignInActivity.this, "签到失败请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Connection.Request request, String e) {

            }
        });
    }

    /**
     * 签到规则
     */
    private void showRuleDialog() {
        if (mSignRuleDialog == null) {
            mSignRuleDialog = new Dialog(this, R.style.AlertDialogStyle);
            View view = getLayoutInflater().inflate(R.layout.sign_in_rule_layout, null);
            view.findViewById(R.id.dialog_close_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSignRuleDialog.isShowing()) {
                        mSignRuleDialog.dismiss();
                    }
                }
            });
            int width = UiUtils.getScreenWidth() - 80;
            int height = UiUtils.getScreenHeight() / 3 * 2;
            mSignRuleDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
            mSignRuleDialog.setContentView(view, params);
            mSignRuleDialog.setCanceledOnTouchOutside(false);
        }

        if (!mSignRuleDialog.isShowing()) {
            mSignRuleDialog.show();
        }
    }

    /**
     * 签到成功
     */
    private void showSignOKDialog(Integer vaule) {
        if (mSignOKDialog == null) {
            mSignOKDialog = new Dialog(this, R.style.AlertDialogStyle);
            View view = getLayoutInflater().inflate(R.layout.sign_in_ok_layout, null);
            view.findViewById(R.id.sign_in_ok_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSignOKDialog.isShowing()) {
                        mSignOKDialog.dismiss();
                    }
                }
            });
            int width = UiUtils.getScreenWidth() / 3 * 2;
            int height = UiUtils.getScreenHeight() / 2;
            mSignOKDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
            mSignOKDialog.setContentView(view, params);
            mSignOKDialog.setCanceledOnTouchOutside(false);
        }
        TextView welfareValueTv = (TextView) mSignOKDialog.findViewById(R.id.welfare_value);
        welfareValueTv.setText(String.valueOf(vaule));
        if (!mSignOKDialog.isShowing()) {
            mSignOKDialog.show();
        }

    }

}
