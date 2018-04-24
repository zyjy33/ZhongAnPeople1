package com.hengyushop.demo.home;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.ModPassActivity;
import com.zams.www.R;

public class GenderFangShiActivity extends BaseActivity implements
        OnClickListener {
    private DialogProgress progress;
    String user_name, user_id, login_sign, value;
    private SharedPreferences spPreferences;
    TextView tv_nan, tv_nv;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_fangshi);
        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");
        progress = new DialogProgress(GenderFangShiActivity.this);

        setUpViews();
    }

    private void setUpViews() {
        System.out.println("======login_sign=============" + login_sign);
        RelativeLayout rl_nan = (RelativeLayout) findViewById(R.id.rl_nan);
        RelativeLayout rl_nv = (RelativeLayout) findViewById(R.id.rl_nv);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        rl_nan.setOnClickListener(this);
        rl_nv.setOnClickListener(this);
        tv_nan = (TextView) findViewById(R.id.tv_nan);
        tv_nv = (TextView) findViewById(R.id.tv_nv);
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            tv_nan.setText("男");
            tv_nv.setText("女");
        } else if (type.equals("2")) {
            tv_nan.setText("用户密码");
            tv_nv.setText("支付密码");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_nan:

                if (type.equals("1")) {
                    value = "男";
                    userloginqm();
                } else if (type.equals("2")) {
                    try {
                        value = "1";
                        Intent intent = new Intent(GenderFangShiActivity.this,
                                ModPassActivity.class);
                        intent.putExtra("value", value);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rl_nv:
                if (type.equals("1")) {
                    value = "女";
                    userloginqm();
                } else if (type.equals("2")) {
                    try {
                        value = "2";
                        Intent intent = new Intent(GenderFangShiActivity.this,
                                ModPassActivity.class);
                        intent.putExtra("value", value);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_login:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取登录签名
     *
     * @param
     */
    private void userloginqm() {
        String strUrlone = RealmName.REALM_NAME_LL
                + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if (status.equals("y")) {
                        JSONObject obj = object.getJSONObject("data");
                        UserRegisterllData data = new UserRegisterllData();
                        data.login_sign = obj.getString("login_sign");
                        login_sign = data.login_sign;
                        System.out.println("======login_sign============="
                                + login_sign);
                        loadusersex(login_sign);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ;
        }, null);
    }

    /**
     * 修改昵称
     *
     * @param login_sign
     * @param
     */
    private void loadusersex(String login_sign) {
        AsyncHttp.get(RealmName.REALM_NAME_LL + "/user_update_field?user_id="
                        + user_id + "&user_name=" + user_name + ""
                        + "&field=sex&value=" + value + "&sign=" + login_sign + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        try {
                            JSONObject object = new JSONObject(arg1);
                            System.out
                                    .println("2================================="
                                            + arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                progress.CloseProgress();
                                // Toast.makeText(GenderFangShiActivity.this,
                                // info, 200).show();
                                finish();
                            } else {
                                progress.CloseProgress();
                                Toast.makeText(GenderFangShiActivity.this,
                                        info, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, null);
    }

}
