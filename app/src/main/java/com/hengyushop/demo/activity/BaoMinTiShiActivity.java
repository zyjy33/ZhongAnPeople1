package com.hengyushop.demo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.Validator;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.MainFragment;
import com.zams.www.R;

/**
 * 提示手机号
 *
 * @author
 */
public class BaoMinTiShiActivity extends Activity implements OnClickListener {
    private TextView btnConfirm, tv_activity_ent;//
    private ImageView iv_guanxi, iv_fanhui;
    private Intent intent;
    public Activity mContext;
    public static Handler handler;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    String login_sign, amount, mobile, beizhu;
    public static String yue_zhuangtai, phone, name;
    String user_id, access_token, sex, unionid;
    public static String user_name = "";
    public static String real_name = "";
    public static String user_real_name = "";
    public static String hd_user_name = "";
    public static String hd_real_name = "";
    String country = "";
    String nickname = "";
    EditText et_user_name, et_user_shoujihao, et_beizhu;
    String id, buy_no;
    LinearLayout index_item;
    public static String province, city, area, user_address, user_accept_name, user_mobile, shopping_address_id;
    public static String sp_id, proFaceImg, proInverseImg, proDoDetailImg, proDesignImg,
            proName, proTip, retailPrice, marketPrice, proSupplementImg, goods_price, price,
            proComputerInfo, yth, key, releaseBossUid, AvailableJuHongBao, Atv_integral, company_id,
            productCount, title_ll, spec_ids, article_id, goods_id, subtitle, spec_text, point_id;
    public static String huodong_zf_type = "0";
    private String bm_tishi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baomin_tishi);
        progress = new DialogProgress(BaoMinTiShiActivity.this);
        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");
        //		login_sign = spPreferences.getString("login_sign", "");
        mobile = spPreferences.getString("mobile", "");
        real_name = spPreferences.getString("real_name", "");
        user_real_name = spPreferences.getString("real_name", "");
        user_mobile = getIntent().getStringExtra("user_mobile");
        getUserxinxi(user_name);
        initUI();
    }

    protected void initUI() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_shoujihao = (EditText) findViewById(R.id.et_user_shoujihao);
        et_beizhu = (EditText) findViewById(R.id.et_beizhu);
        btnConfirm = (TextView) findViewById(R.id.btnConfirm);
        index_item = (LinearLayout) findViewById(R.id.index_item);
        index_item.setOnClickListener(this);
        iv_guanxi = (ImageView) findViewById(R.id.iv_guanxi);
        iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_guanxi.setOnClickListener(this);
        iv_fanhui.setOnClickListener(this);
        tv_activity_ent = (TextView) findViewById(R.id.tv_activity_ent);
        if (getIntent().getStringExtra("datatype_id").equals("8")) {
            tv_activity_ent.setText("立即签到");
        } else if (getIntent().getStringExtra("datatype_id").equals("6")) {
            tv_activity_ent.setText("立即报名");
        } else if (getIntent().getStringExtra("datatype_id").equals("5")) {
            tv_activity_ent.setText("立即报名");
        } else if (getIntent().getStringExtra("datatype_id").equals("4")) {
            tv_activity_ent.setText("立即投票");
        }
        //		if (getIntent().getStringExtra("sell_price").equals("0.0")) {
        if (getIntent().getStringExtra("sell_price").equals("0") || getIntent().getStringExtra("sell_price").equals("0.0")
                || getIntent().getStringExtra("sell_price").equals("0.00")) {
            btnConfirm.setVisibility(View.GONE);
        } else {
            btnConfirm.setText("合计：￥" + getIntent().getStringExtra("sell_price"));
        }
        retailPrice = getIntent().getStringExtra("sell_price");
        et_user_name.setText(real_name);
        //		et_user_shoujihao.setText(mobile);

        //		handler = new Handler() {
        //			public void handleMessage(Message msg) {
        //				switch (msg.what) {
        //				case 8:
        //
        //				}
        //			}
        //		};
    }

    private void getUserxinxi(String user_name) {

        String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    //						System.out.println("======输出用户资料============="+arg1);
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    JSONObject obj = object.getJSONObject("data");
                    if (status.equals("y")) {
                        login_sign = obj.getString("login_sign");
                    } else {
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, BaoMinTiShiActivity.this);
    }

    /**
     * 点击触发事件
     */
    @Override
    public void onClick(View v) {


        intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_guanxi://取消
                finish();
                break;
            case R.id.iv_fanhui://返回
                finish();
                break;
            case R.id.index_item://立即提交
                if ("".equals(real_name)) {
                    real_name = et_user_name.getText().toString().trim();
                } else {
                    real_name = et_user_name.getText().toString().trim();
                }

                if ("".equals(mobile)) {
                    mobile = et_user_shoujihao.getText().toString().trim();
                } else {
                    mobile = et_user_shoujihao.getText().toString().trim();
                }
                System.out.println("mobile----------------" + mobile);
                System.out.println("real_name----------------" + real_name);
                System.out.println("user_real_name----------------" + user_real_name);

                if (user_real_name.equals(real_name)) {
                    bm_tishi = "1";
                } else {
                    bm_tishi = "2";
                }

                System.out.println("bm_tishi----------------" + bm_tishi);
                if ("".equals(mobile)) {
                    Toast.makeText(BaoMinTiShiActivity.this, "请输入证件号", Toast.LENGTH_SHORT).show();
                } else if (real_name.equals("")) {
                    Toast.makeText(BaoMinTiShiActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else {
                    getjianche_activity_2();

                }

                break;

            default:
                break;


        }
    }

    /**
     * 检测是否报名
     *
     * @param
     */
    private void getjianche_activity_2() {

        // progress.CreateProgress();
        AsyncHttp.get(RealmName.REALM_NAME_LL + "/check_order_signup?mobile="
                        + mobile + "&article_id=" + getIntent().getStringExtra("id") + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            String status = jsonObject.getString("status");
                            System.out.println("检测是否报名================" + arg1);
                            String info = jsonObject.getString("info");

                            if (status.equals("y")) {
                                //未报名
                                progress.CloseProgress();
                                //								Toast.makeText(ZhongAnMinShenXqActivity.this,info, Toast.LENGTH_SHORT).show();
                                getguowuqingdan();// 购物清单
                            } else {
                                //已报名
                                progress.CloseProgress();
                                String datall = jsonObject.getString("data");
                                if (datall.equals("null")) {
                                    Toast.makeText(BaoMinTiShiActivity.this, "号码不存在", Toast.LENGTH_SHORT).show();
                                } else {
                                    //										  if (bm_tishi.equals("2")) {
                                    //										    	Toast.makeText(BaoMinTiShiActivity.this,"您已报名，不可重复报名", 1000).show();
                                    //										    	finish();
                                    //										  }else {
                                    JSONObject jsot = jsonObject.getJSONObject("data");
                                    String trade_no = jsot.getString("trade_no");
                                    Intent intent = new Intent(BaoMinTiShiActivity.this, TishiBaoMinOkActivity.class);
                                    intent.putExtra("bm_tishi", bm_tishi);
                                    intent.putExtra("trade_no", trade_no);
                                    intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
                                    intent.putExtra("title", getIntent().getStringExtra("title"));
                                    intent.putExtra("start_time", getIntent().getStringExtra("start_time"));
                                    intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
                                    intent.putExtra("address", getIntent().getStringExtra("address"));
                                    intent.putExtra("id", getIntent().getStringExtra("id"));
                                    intent.putExtra("real_name", real_name);
                                    intent.putExtra("mobile", mobile);
                                    startActivity(intent);
                                    finish();
                                    //										    }
                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {

                        progress.CloseProgress();
                        System.out.println("==========================" + arg1);
                        Toast.makeText(BaoMinTiShiActivity.this, "异常", Toast.LENGTH_SHORT).show();
                        super.onFailure(arg0, arg1);
                    }

                }, BaoMinTiShiActivity.this);
    }


    /**
     * 活动清单
     *
     * @param
     * @param
     */
    private void getguowuqingdan() {

        try {
            progress.CreateProgress();
            System.out.println("real_name==========================！"
                    + real_name);
            if (real_name.equals("")) {
                real_name = "空";
            }
            String article_id = getIntent().getStringExtra("article_id");
            String goods_id = getIntent().getStringExtra("goods_id");
            AsyncHttp.get(RealmName.REALM_NAME_LL + "/add_signup_buy?user_id="
                            + user_id + "&user_name=" + user_name + "&user_sign="
                            + login_sign + "&signup_mobile=" + mobile + "&signup_name="
                            + real_name + "&article_id=" + article_id + ""
                            + "&goods_id=" + goods_id + "&quantity=" + 1 + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                String status = jsonObject.getString("status");
                                System.out.println("购物清单================" + arg1);
                                String info = jsonObject.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    JSONObject obj = jsonObject.getJSONObject("data");
                                    buy_no = obj.getString("buy_no");
                                    //									String count = obj.getString("count");
                                    // Toast.makeText(ZhongAnMinShenXqActivity.this,info, Toast.LENGTH_SHORT).show();
                                    try {
                                        loadusertijiao(buy_no);//提交用户订单
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                } else {
                                    progress.CloseProgress();
                                    String code = jsonObject.getString("code");
                                    if (code.equals("Exist")) {
                                        //										  if (bm_tishi.equals("2")) {
                                        //										    	Toast.makeText(BaoMinTiShiActivity.this,"您已报名，不可重复报名", 1000).show();
                                        //										    	finish();
                                        //										  }else {
                                        JSONObject obj = jsonObject.getJSONObject("data");
                                        String trade_no = obj.getString("trade_no");
                                        Intent intent = new Intent(BaoMinTiShiActivity.this, TishiBaoMinOkActivity.class);
                                        intent.putExtra("bm_tishi", bm_tishi);
                                        intent.putExtra("trade_no", trade_no);
                                        intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
                                        intent.putExtra("title", getIntent().getStringExtra("title"));
                                        intent.putExtra("start_time", getIntent().getStringExtra("start_time"));
                                        intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
                                        intent.putExtra("address", getIntent().getStringExtra("address"));
                                        intent.putExtra("id", getIntent().getStringExtra("id"));
                                        intent.putExtra("real_name", real_name);
                                        intent.putExtra("mobile", mobile);
                                        startActivity(intent);
                                        finish();
                                        //										    }
                                    } else {
                                        Toast.makeText(BaoMinTiShiActivity.this, info, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            progress.CloseProgress();
                            System.out.println("==========================" + arg1);
                            Toast.makeText(BaoMinTiShiActivity.this, "异常", Toast.LENGTH_SHORT).show();
                            super.onFailure(arg0, arg1);
                        }

                    }, BaoMinTiShiActivity.this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * 报名-提交用户订单
     *
     * @param
     * @param
     */
    private void loadusertijiao(String buy_no) {
        try {
            // progress.CreateProgress();
            beizhu = et_beizhu.getText().toString().trim();
            System.out.println("buy_no=====================" + buy_no);
            String url = RealmName.REALM_NAME_LL + "/add_order_signup_2017?user_id="
                    + user_id + "&user_name=" + user_name + "&user_sign="
                    + login_sign + "" + "&buy_no=" + buy_no
                    + "&payment_id=1&is_invoice=0&invoice_title=0&remark=" + beizhu + "";
            AsyncHttp.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {
                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject object = new JSONObject(arg1);
                        System.out.println("提交用户订单 =================================" + arg1);
                        try {

                            String status = object.getString("status");
                            String info = object.getString("info");
                            //							retailPrice ="0.00";
                            System.out.println("=========retailPrice===========" + retailPrice);
                            if (status.equals("y")) {
                                progress.CloseProgress();
                                JSONObject jsonObject = object.getJSONObject("data");
                                String trade_no = jsonObject.getString("trade_no");
                                // String total_amount = jsonObject.getString("total_amount");
                                // order_no = jsonObject.getString("order_no");

                                //									getjianche(trade_no);//检测是否已经报名
                                try {
                                    progress.CloseProgress();
                                    if (retailPrice.equals("0") || retailPrice.equals("0.0") || retailPrice.equals("0.00")) {
                                        try {
                                            Intent intent = new Intent(BaoMinTiShiActivity.this, BaoMinOKActivity.class);
                                            intent.putExtra("trade_no", trade_no);
                                            intent.putExtra("total_c", retailPrice);
                                            intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
                                            intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
                                            intent.putExtra("hd_title", getIntent().getStringExtra("title"));
                                            intent.putExtra("start_time", getIntent().getStringExtra("start_time"));
                                            intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
                                            intent.putExtra("address", getIntent().getStringExtra("address"));
                                            intent.putExtra("id", getIntent().getStringExtra("id"));
                                            intent.putExtra("real_name", real_name);
                                            intent.putExtra("mobile", mobile);
                                            startActivity(intent);
                                            finish();
                                        } catch (Exception e) {

                                            e.printStackTrace();
                                        }
                                    } else {
                                        huodong_zf_type = "1";// 活动支付成功不显示详情
                                        Intent intent = new Intent(BaoMinTiShiActivity.this, MyOrderZFActivity.class);
                                        intent.putExtra("order_no", trade_no);
                                        intent.putExtra("total_c", retailPrice);
                                        intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
                                        intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
                                        intent.putExtra("title", getIntent().getStringExtra("title"));
                                        intent.putExtra("start_time", getIntent().getStringExtra("start_time"));
                                        intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
                                        intent.putExtra("address", getIntent().getStringExtra("address"));
                                        intent.putExtra("id", getIntent().getStringExtra("id"));
                                        intent.putExtra("real_name", real_name);
                                        intent.putExtra("mobile", mobile);
                                        startActivity(intent);
                                        finish();
                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            } else {
                                progress.CloseProgress();
                                String datall = object.getString("data");
                                if (datall.equals("null")) {
                                    Toast.makeText(BaoMinTiShiActivity.this, info, Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONObject jsonObject = object.getJSONObject("data");
                                    String trade_no = jsonObject.getString("trade_no");
                                    Intent intent = new Intent(BaoMinTiShiActivity.this, TishiBaoMinOkActivity.class);
                                    intent.putExtra("bm_tishi", bm_tishi);
                                    intent.putExtra("trade_no", trade_no);
                                    intent.putExtra("datatype_id", getIntent().getStringExtra("datatype_id"));
                                    intent.putExtra("title", getIntent().getStringExtra("title"));
                                    intent.putExtra("start_time", getIntent().getStringExtra("start_time"));
                                    intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
                                    intent.putExtra("address", getIntent().getStringExtra("address"));
                                    intent.putExtra("id", getIntent().getStringExtra("id"));
                                    intent.putExtra("real_name", real_name);
                                    intent.putExtra("mobile", mobile);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        } catch (Exception e) {


                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 检测是否已经报名
     *
     * @param total_amount
     * @param trade_no
     */


}