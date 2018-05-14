package com.zams.www.wxapi;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.YangLaoChongZhiActivity;
import com.hengyushop.demo.my.ChongZhiActivity;
import com.hengyushop.demo.my.MonneyChongZhiActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.UpgradeChongZhiActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.R;

/**
 * 微信支付回调
 *
 * @author Administrator
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    private SharedPreferences spPreferences;
    String recharge_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);

        LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
        ll_buju.getBackground().setAlpha(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            // AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // builder.setTitle(R.string.app_picker_name);
            // builder.setMessage(getString(R.string.bookmark_picker_name,
            // String.valueOf(resp.errCode)));
            // builder.show();

            int zhuangtai = resp.errCode;//
            String shuzi = String.valueOf(zhuangtai);
            System.out.println("zhuangtai-----------------/" + zhuangtai);
            // Toast.makeText(WXPayEntryActivity.this, "返回状态/"+shuzi,
            // 200).show();

            if (zhuangtai == 0) {
                userloginqm();
            } else if (zhuangtai == -1) {
                Toast.makeText(WXPayEntryActivity.this, "支付异常", 200).show();
            } else if (zhuangtai == -2) {
                Toast.makeText(WXPayEntryActivity.this, "微信支付失败", 200).show();
            }
        }
        finish();
    }

    /**
     * 获取登录签名
     */
    private void userloginqm() {
        try {
            SharedPreferences spPreferences = getSharedPreferences(
                    "longuserset", MODE_PRIVATE);
            String user_name = spPreferences.getString("user", "");
            String strUrlone = RealmName.REALM_NAME_LL
                    + "/get_user_model?username=" + user_name + "";
            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        JSONObject obj = object.getJSONObject("data");
                        if (status.equals("y")) {
                            UserRegisterllData data = new UserRegisterllData();
                            data.login_sign = obj.getString("login_sign");
                            String login_sign = data.login_sign;
                            System.out.println("======login_sign============="
                                    + login_sign);

                            String ck_recharge_no = ChongZhiActivity.recharge_no;
                            String sjck_recharge_no = UpgradeChongZhiActivity.recharge_no;
                            String ylyh_recharge_no = YangLaoChongZhiActivity.recharge_no;
                            String order_recharge_no = MyOrderConfrimActivity.recharge_no;
                            String zhifu_order_recharge_no = MyOrderZFActivity.recharge_no;

                            // System.out.println("======yue_recharge_no============="+yue_recharge_no);
                            System.out
                                    .println("======ck_recharge_no============="
                                            + ck_recharge_no);
                            System.out
                                    .println("======sjck_recharge_no============="
                                            + sjck_recharge_no);
                            System.out
                                    .println("======ylyh_recharge_no============="
                                            + ylyh_recharge_no);
                            System.out
                                    .println("======order_recharge_no============="
                                            + order_recharge_no);
                            System.out
                                    .println("======zhifu_order_recharge_no============="
                                            + zhifu_order_recharge_no);

                            // if (yue_recharge_no != null) {
                            // // recharge_no = yue_recharge_no;
                            // loadweixinzf1(login_sign,yue_recharge_no);
                            // MonneyChongZhiActivity.recharge_no = null;
                            // System.out.println("======yue_recharge_no============="+yue_recharge_no);
                            // }
                            // else
                            if (ck_recharge_no != null) {
                                // recharge_no = ck_recharge_no;
                                loadweixinzf2(login_sign, ck_recharge_no);
                                ChongZhiActivity.recharge_no = null;
                            } else if (sjck_recharge_no != null) {
                                // recharge_no = sjck_recharge_no;
                                loadweixinzf2(login_sign, sjck_recharge_no);
                                UpgradeChongZhiActivity.recharge_no = null;
                            } else if (ylyh_recharge_no != null) {
                                // recharge_no = ylyh_recharge_no;
                                loadweixinzf3(login_sign, ylyh_recharge_no);
                                YangLaoChongZhiActivity.recharge_no = null;
                                // }else if (order_recharge_no != null){
                                // // recharge_no = ylyh_recharge_no;
                                // // MyOrderConfrimActivity.zhuangtai = true;
                                // loadweixinzf4(login_sign,order_recharge_no);
                                // MyOrderConfrimActivity.recharge_no = null;
                                // // MyOrderConfrimActivity.zhuangtai = false;
                            } else if (zhifu_order_recharge_no != null) {
                                loadweixinzf4(login_sign, zhifu_order_recharge_no);
                                MyOrderZFActivity.recharge_no = null;
                            }

                        } else {
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, WXPayEntryActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 充值更新
     *
     * @param login_sign
     * @param recharge_no
     * @param payment_id
     */
    private void loadweixinzf1(String login_sign, String recharge_no) {
        try {
            SharedPreferences spPreferences = getSharedPreferences(
                    "longuserset", MODE_PRIVATE);
            String user_name = spPreferences.getString("user", "");
            String user_id = spPreferences.getString("user_id", "");
            System.out.println("recharge_no================================="
                    + recharge_no);
            System.out.println("login_sign================================="
                    + login_sign);
            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/update_user_amount?user_id=" + user_id + "&user_name="
                            + user_name + "" + "&recharge_no=" + recharge_no + "&sign="
                            + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                System.out.println("1================================="
                                        + arg1);
                                JSONObject object = new JSONObject(arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    Toast.makeText(WXPayEntryActivity.this, info, 200)
                                            .show();
                                    // finish();
                                    MonneyChongZhiActivity.handlerll
                                            .sendEmptyMessage(1);
                                } else {
                                    Toast.makeText(WXPayEntryActivity.this, info, 200)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            Toast.makeText(WXPayEntryActivity.this, "异常", 200).show();
                            System.out.println("11================================="
                                    + arg0);
                            System.out.println("22================================="
                                    + arg1);
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 服务顾问升级更新
     *
     * @param login_sign
     * @param recharge_no
     * @param
     */
    private void loadweixinzf2(String login_sign, String recharge_no) {
        try {
            SharedPreferences spPreferences = getSharedPreferences(
                    "longuserset", MODE_PRIVATE);
            String user_name = spPreferences.getString("user", "");
            String user_id = spPreferences.getString("user_id", "");
            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/update_amount_reserve?user_id=" + user_id
                            + "&user_name=" + user_name + "" + "&recharge_no="
                            + recharge_no + "&sign=" + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                System.out.println("1================================="
                                        + arg1);
                                JSONObject object = new JSONObject(arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    // Toast.makeText(WXPayEntryActivity.this, info,
                                    // 200).show();
                                    // MyOrderXqActivity.handler.sendEmptyMessage(1);
                                    // finish();
                                    // ChuangKeActivity.handler.sendEmptyMessage(2);
                                } else {
                                    Toast.makeText(WXPayEntryActivity.this, info, 200)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            Toast.makeText(WXPayEntryActivity.this, "异常", 200).show();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 养老基金更新
     *
     * @param login_sign
     * @param recharge_no
     * @param
     */
    private void loadweixinzf3(String login_sign, String recharge_no) {
        try {
            SharedPreferences spPreferences = getSharedPreferences(
                    "longuserset", MODE_PRIVATE);
            String user_name = spPreferences.getString("user", "");
            String user_id = spPreferences.getString("user_id", "");
            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/update_user_pension?user_id=" + user_id + "&user_name="
                            + user_name + "" + "&recharge_no=" + recharge_no + "&sign="
                            + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                System.out.println("1================================="
                                        + arg1);
                                JSONObject object = new JSONObject(arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    // Toast.makeText(WXPayEntryActivity.this, info,
                                    // 200).show();
                                    // MyOrderXqActivity.handler.sendEmptyMessage(1);
                                } else {
                                    Toast.makeText(WXPayEntryActivity.this, info, 200)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            Toast.makeText(WXPayEntryActivity.this, "异常", 200).show();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 订单更新
     *
     * @param login_sign
     * @param recharge_no
     * @param
     */
    private void loadweixinzf4(String login_sign, String recharge_no) {
        try {
            SharedPreferences spPreferences = getSharedPreferences(
                    "longuserset", MODE_PRIVATE);
            String user_name = spPreferences.getString("user", "");
            String user_id = spPreferences.getString("user_id", "");
            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/update_order_payment?user_id=" + user_id
                            + "&user_name=" + user_name + "" + "&trade_no="
                            + recharge_no + "&sign=" + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                System.out
                                        .println("1===============订单更新=================="
                                                + arg1);
                                JSONObject object = new JSONObject(arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    // Toast.makeText(WXPayEntryActivity.this, info,
                                    // 200).show();
                                    // MyOrderXqActivity.handler.sendEmptyMessage(1);
                                } else {
                                    Toast.makeText(WXPayEntryActivity.this, info, 200)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            Toast.makeText(WXPayEntryActivity.this, "异常", 200).show();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}