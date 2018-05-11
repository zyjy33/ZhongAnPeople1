package com.zams.www;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.ShopingCartOrderAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.at.SlipButton;
import com.hengyushop.demo.at.SlipButton.OnChangedListener;
import com.hengyushop.demo.home.ZhiFuFangShiActivity;
import com.hengyushop.demo.my.MyJuDuiHuanActivity;
import com.hengyushop.demo.my.MyOrderActivity;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.InScrollListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 订单确认
 *
 * @author Administrator
 */
public class MyOrderConfrimActivity extends BaseActivity {
    public static final int ADD_FIRST_REQUEST = 11;
    private String pwd, username;
    private ArrayList<ShopCartData> mList; //购物车列表
    private DialogProgress progress;
    private int checkedAddressId;
    private StringBuilder orderid;
    private MyPopupWindowMenu popupWindowMenu;
    private int express_fee;
    private TextView tv_user_name, tv_user_address, tv_user_phone, tv_hongbao;
    private SharedPreferences spPreferences;
    private ImageButton btn_add_address;
    private ShopingCartOrderAdapter adapter;
    private InScrollListView list_shop_cart;
    ArrayList<ShopCartData> list_ll = new ArrayList<ShopCartData>();
    private LinearLayout layout0, ll_ljgm, layout2, ll_zhifufs;
    private RelativeLayout layout1, rl_hongbao;
    private TextView heji, tv_1, tv_2;
    private Button confrim_btn;
    private LinearLayout yu_pay0, yu_pay1, yu_pay2;
    private CheckBox yu_pay_c0, yu_pay_c1, yu_pay_c2;
    String jiekou_type_ysj;
    String jiekou_type;
    ImageView img_ware;
    TextView tv_warename, tv_jiaguo, tv_hb_ye, tv_hb_ye_2, tv_hb_ye_3;
    TextView tv_color;
    Activity activity;
    TextView tv_size, tv_zhifu;
    String name = "";
    private String ZhiFuFangShi, express_id;
    String type = "5";
    String order_str, notify_url;
    int zhifu, kou_hongbao;
    private IWXAPI api;
    private String partner_id, prepayid, noncestr, timestamp, package_, sign;
    private String user_dizhiname;
    boolean zhuangtai = false;
    public static boolean teby = false;
    String url;
    String login_sign, dandu_goumai;
    boolean flag;
    // double di_hongbao = 0;
    double packet;//红包
    String total_fee, kedi_hongbao;
    ArrayList<JuTuanGouData> list_zf;
    SlipButton sb;
    double jiaguo = 0;
    public static String province, city, area, user_address, user_accept_name,
            user_mobile, shopping_address_id;
    public static String province1, city1, area1, user_address1, accept_name1,
            user_mobile1, recharge_no1, article_id1;
    public static String recharge_no, order_no, datetime1, sell_price1,
            give_pension1;
    private LinearLayout market_information_juduihuan;
    public AQuery mAq;

    /**
     * 用户名字和用户id
     */
    private String user_name, user_id;
    /**
     * 支付总额
     */
    private double mNeedSumMoney = 0;
    /**
     * 可用红包抵押的钱
     */
    double cashing_packet = 0.0;
    private String buy_no;
    private String mExchangePoint;
    private LinearLayout sumMoneyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupWindowMenu = new MyPopupWindowMenu(this);
        progress = new DialogProgress(MyOrderConfrimActivity.this);
        api = WXAPIFactory.createWXAPI(MyOrderConfrimActivity.this, null);
        api.registerApp(Constant.APP_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confrim);
        progress.CreateProgress();

        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");

        mAq = new AQuery(this);
        sb = (SlipButton) findViewById(R.id.splitbutton);
        tv_hb_ye = (TextView) findViewById(R.id.tv_hb_ye);
        tv_hb_ye_2 = (TextView) findViewById(R.id.tv_hb_ye_2);
        tv_hb_ye_3 = (TextView) findViewById(R.id.tv_hb_ye_3);
        sumMoneyLayout = (LinearLayout) findViewById(R.id.ll_mmoney_ts);
        sb.setCheck(true);

        handlerll = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        finish();
                        break;
                }
            }
        };
        initdata();
        initData();
        requestData();
    }

    private void initData() {
        // 获取地址
        Intent intent = getIntent();
        user_accept_name = intent.getStringExtra("user_accept_name");
        System.out.println("name==============" + user_accept_name);
        buy_no = intent.getStringExtra("buy_no");

        if (!TextUtils.isEmpty(user_accept_name)) {
            province = intent.getStringExtra("province");
            city = intent.getStringExtra("city");
            area = intent.getStringExtra("user_area");
            user_address = intent.getStringExtra("user_address");
            user_mobile = intent.getStringExtra("user_mobile");
            tv_user_name.setText("收货人：" + user_accept_name);
            tv_user_address.setText("地址：" + province + "、" + city + "、" + area
                    + "、" + user_address);
            tv_user_phone.setText(user_mobile);
        } else {
            getuseraddress2();
        }
    }

    private void requestData() {
        mNeedSumMoney = 0;
        cashing_packet = 0.0;
        System.out.println("teby==============" + teby);
        // 余额支付成功后更新订单
        if (teby) {
            teby = false;
            finish();
        }
        // 微信支付成功后关闭此界面
        if (flag) {
            userloginqm();
            // finish();
        }

        // 余额支付取消关闭此界面
        if (TishiCarArchivesActivity.yue_zhuangtai != null) {
            TishiCarArchivesActivity.yue_zhuangtai = null;
            finish();
        }

        // 立即购买
        img_ware = (ImageView) findViewById(R.id.img_ware);
        tv_warename = (TextView) findViewById(R.id.tv_ware_name);
        tv_color = (TextView) findViewById(R.id.tv_color);
        tv_size = (TextView) findViewById(R.id.tv_size);
        ll_ljgm = (LinearLayout) findViewById(R.id.ll_ljgm);
        getuserhongbao(user_name);//获取用户的红包余额

    }


    public static Handler handlerll;

    private double mOwnedPacket = 0.0; //拥有的红包
    private double mExpectPacket = 0.0; //可用红包来抵押的值

    /**
     * 获取用户红包
     *
     * @param user_name
     */
    private void getuserhongbao(String user_name) {
        String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    Log.e(TAG, "获取红包值================" + arg1);
                    String status = object.getString("status");
                    JSONObject obj = object.getJSONObject("data");
                    if (status.equals("y")) {
                        mOwnedPacket = obj.getDouble("packet");
                    } else {
                        mOwnedPacket = 0;
                    }
                    load_list();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, MyOrderConfrimActivity.this);
    }

    private static final String TAG = "MyOrderConfrimActivity";

    private void gethongbao() {

        /**
         * 判断是否使用红包
         */
        sb.SetOnChangedListener(new OnChangedListener() {
            @Override
            public void OnChanged(boolean isCheck) {
                Log.e(TAG, "isCheck================" + isCheck);
                Log.e(TAG, "mNeedSumMoney================" + mNeedSumMoney);
                Log.e(TAG, "packet================" + packet);
                Log.e(TAG, "cashing_packet================" + cashing_packet);
                Log.e(TAG, "kedi_hongbao================" + kedi_hongbao);
                double payMoney = 0.0;
                if (isCheck) { //选择的使用红包

                    if (mOwnedPacket == 0 || mExpectPacket == 0) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("无可抵红包");
                        kou_hongbao = 0;// 红包为0
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("￥" + doubleToString(mNeedSumMoney) + " , " + mSumQuantity + "件，红包可抵扣: ￥" + 0 + "元");
                        payMoney = mNeedSumMoney;
                    } else {
                        heji.setVisibility(View.VISIBLE);
                        rl_hongbao.setVisibility(View.VISIBLE);
                        kou_hongbao = 1;// 已低红包
                        double userRedPacket = 0.0;
                        tv_hongbao.setText("可用" + mExpectPacket + "元红包抵" + mExpectPacket + "元");
                        if (mOwnedPacket >= mExpectPacket) {// 拥有的红包够大
                            userRedPacket = mExpectPacket;
                        } else {//拥有小红包
                            userRedPacket = mOwnedPacket;
                        }
                        tv_jiaguo.setText("￥" + doubleToString(mNeedSumMoney) + " , " + mSumQuantity + "件，红包可抵扣: ￥" + userRedPacket + "元");
                        payMoney = mNeedSumMoney - userRedPacket;
                    }
                } else { //不使用红包
                    System.out.println("dzongjia2================" + mNeedSumMoney);
                    heji.setVisibility(View.VISIBLE);
                    tv_hongbao.setText("不可以使用红包");
                    tv_jiaguo.setText("￥" + doubleToString(mNeedSumMoney) + " , " + mSumQuantity + "件，红包可抵扣: ￥" + 0 + "元");
                    payMoney = mNeedSumMoney;
                    kou_hongbao = 0;// 不抵扣红包
                }
                tv_hb_ye.setText(String.valueOf(packet));
                tv_hb_ye_2.setText(String.valueOf(packet));
                tv_hb_ye_3.setText("当前红包:￥" + String.valueOf(mOwnedPacket) + "元");
                heji.setText("实付款:" + doubleToString(payMoney) + "元");
                isShowThreePay(payMoney);
            }
        });
        sb.setNowChooseCheck(true);
        tv_hb_ye.setText(String.valueOf(packet));
        tv_hb_ye_2.setText(String.valueOf(packet));
        tv_hb_ye_3.setText("当前红包:￥" + String.valueOf(mOwnedPacket) + "元");

    }

    public String doubleToString(double num) {
        if (num < 0) {
            num = 0;
        }
        return new DecimalFormat("0.00").format(num);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 在这里进行查询地址的操作
        // Toast.makeText(getApplicationContext(), "查询地址联网操作",200).show();
        // handler.sendEmptyMessage(4);
        if (resultCode == 100) {
            layout0.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);
            UserAddressData dt = (UserAddressData) data
                    .getSerializableExtra("data");
            checkedAddressId = dt.consigneeAddressId;
            String name = dt.user_accept_name;
            String user_area = dt.user_area;
            String user_mobile = dt.user_mobile;
            String user_address = dt.user_address;
            System.out.println("checkedAddressId==================" + name);
            tv_user_name.setText("收货人:" + name);
            tv_user_address.setText(user_area + " " + user_address);
            tv_user_phone.setText(user_mobile);
        }
        if (resultCode == RESULT_OK && requestCode == 111) {
            showOrderActivity();
        }
        if (resultCode == 0 && requestCode == ADD_FIRST_REQUEST) {
            layout0.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);
            getuseraddress2();
        }


    }

    private void initdata() {
//        try {
        market_information_juduihuan = (LinearLayout) findViewById(R.id.market_information_juduihuan);
        confrim_btn = (Button) findViewById(R.id.confrim_btn);
        list_shop_cart = (InScrollListView) findViewById(R.id.list_shop_cart);
        list_shop_cart.setFocusable(false);
        btn_add_address = (ImageButton) findViewById(R.id.img_btn_add_address);
        layout0 = (LinearLayout) findViewById(R.id.layout0);
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        rl_hongbao = (RelativeLayout) findViewById(R.id.rl_hongbao);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        ll_zhifufs = (LinearLayout) findViewById(R.id.ll_zhifufs);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_address = (TextView) findViewById(R.id.tv_user_address);
        tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
        tv_hongbao = (TextView) findViewById(R.id.tv_hongbao);
        tv_zhifu = (TextView) findViewById(R.id.tv_zhifu);
        tv_jiaguo = (TextView) findViewById(R.id.tv_jiaguo);
        heji = (TextView) findViewById(R.id.heji);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);

        yu_pay0 = (LinearLayout) findViewById(R.id.yu_pay0);
        yu_pay1 = (LinearLayout) findViewById(R.id.yu_pay1);
        yu_pay2 = (LinearLayout) findViewById(R.id.yu_pay2);
        yu_pay_c0 = (CheckBox) findViewById(R.id.yu_pay_c0);
        yu_pay_c1 = (CheckBox) findViewById(R.id.yu_pay_c1);
        yu_pay_c2 = (CheckBox) findViewById(R.id.yu_pay_c2);

        //分割线
        ImageView imageView = (ImageView) findViewById(R.id.iv_tupian);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiantiap);
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        bd.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        imageView.setBackgroundDrawable(bd);
        //返回键
        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        /**
         * 支付方式
         */
        ll_zhifufs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MyOrderConfrimActivity.this,
                        ZhiFuFangShiActivity.class);
                intent.putExtra("order_confrim", "order_confrim");//
                startActivity(intent);
            }
        });

        /**
         * 收货地址列表
         */
        layout0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String title = getIntent().getStringExtra("title");
                String stare = getIntent().getStringExtra("stare");
                String groupon_price = getIntent().getStringExtra("groupon_price");
                String img_url = getIntent().getStringExtra("img_url");
                String price = getIntent().getStringExtra("price");
                String goods_price = getIntent().getStringExtra("goods_price");
                String jubi = getIntent().getStringExtra("point");
                String shopping_ids = getIntent().getStringExtra("shopping_ids");
                String tuangou_id = getIntent().getStringExtra("tuangou_id");

                Intent intent = new Intent(MyOrderConfrimActivity.this,
                        AddressManagerActivity.class);
                intent.putExtra("order_confrim", "order_confrim");// 标示
                intent.putExtra("title", title);// 标示
                intent.putExtra("stare", stare);
                intent.putExtra("groupon_price", groupon_price);
                intent.putExtra("img_url", img_url);
                intent.putExtra("price", price);
                intent.putExtra("goods_price", goods_price);
                intent.putExtra("mExchangePoint", jubi);
                intent.putExtra("shopping_ids", shopping_ids);
                intent.putExtra("tuangou_id", tuangou_id);
                intent.putExtra("buy_no", getIntent().getStringExtra("buy_no"));
                startActivity(intent);
                // startActivityForResult(intent, 100);
                // startActivityForResult(intent, 0);
            }
        });

        /**
         * 添加收货地址
         */
        layout1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOrderConfrimActivity.this, AddUserAddressActivity.class);
                startActivityForResult(intent, ADD_FIRST_REQUEST);
            }
        });

//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }


        /**
         * 微信支付
         */
        yu_pay_c0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(false);
                yu_pay_c0.setChecked(true);
                System.out.println("type======微信支付========" + type);
                // 微信
                type = "5";
            }
        });
        yu_pay0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // 点击设置是否为点击状态
                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(false);
                yu_pay_c0.setChecked(true);
                System.out.println("type======微信支付========" + type);
                // 微信
                type = "5";
            }
        });
        /**
         * 支付宝支付
         */
        yu_pay_c1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(true);
                yu_pay_c2.setChecked(false);
                System.out.println("type======支付宝支付========" + type);
                // 支付宝
                type = "3";
            }
        });
        yu_pay1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(true);
                yu_pay_c2.setChecked(false);
                System.out.println("type======支付宝支付========" + type);
                // 支付宝
                type = "3";
            }
        });


        /**
         * 余额支付
         */
        yu_pay_c2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(true);
                // 余额支付
                type = "2";
            }
        });

        /**
         * 结算方式
         */
        confrim_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                System.out.println("user_accept_name======11========" + user_accept_name);
                if (user_accept_name == null) {
                    Toast.makeText(MyOrderConfrimActivity.this, "您还未添加收货地址",
                            Toast.LENGTH_SHORT).show();
                } else if (sb.isNowChoose() && !"2".equals(type) && mNeedSumMoney - (mExpectPacket > mOwnedPacket ? mOwnedPacket : mExpectPacket) == 0) {
                    Toast.makeText(MyOrderConfrimActivity.this, "支付金额小于0，请使用余额支付", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("type======结算方式========" + type);
                    loadusertijiao(type, kou_hongbao);// 提交聚团订单
                    //						CommomConfrim.showSheet(MyOrderConfrimActivity.this,new onDeleteSelect() {
                    //
                    //									@Override
                    //		packet							public void onClick(int resID) {
                    //
                    //										switch (resID) {
                    //										case R.id.item0:
                    //											// 余额支付
                    //											type = "2";
                    //											loadusertijiao(type, kou_hongbao);
                    //											break;
                    //										case R.id.item1:
                    //											break;
                    //										case R.id.item2:// 支付宝
                    //											// 支付宝
                    //											type = "3";
                    //											loadusertijiao(type, kou_hongbao);
                    //											break;
                    //										case R.id.item3:// 微信
                    //											// 微信
                    //											type = "5";
                    //											loadusertijiao(type, kou_hongbao);
                    //											break;
                    //										case R.id.item4:
                    //											break;
                    //										default:
                    //											break;
                    //										}
                    //									}
                    //
                    //								}, cancelListener, null);
                }

            }
        });

    }

    /**
     * 获取登录签名
     */
    private void userloginqm() {
        try {
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
                            login_sign = data.login_sign;
                            loadguanggaoll(recharge_no, login_sign);
                        } else {
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, MyOrderConfrimActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 输出用户默认收货地址
     */
    boolean dizhi_type = false;

    private void getuseraddress2() {
        try {

            list_ll = new ArrayList<ShopCartData>();
            AsyncHttp.get(RealmName.REALM_NAME_LL
                    + "/get_user_shopping_address?user_name=" + user_name// get_user_shopping_address_default
                    + "", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {
                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject jsonObject = new JSONObject(arg1);
                        System.out.println("输出用户默认收货地址================" + arg1);
                        String status = jsonObject.getString("status");
                        if (status.equals("y")) {
                            try {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsot = jsonArray.getJSONObject(i);
                                    // UserAddressData data = new UserAddressData();
                                    String user_dizhiname = jsot.getString("user_accept_name");
                                    shopping_address_id = jsot.getString("id");
                                    province = jsot.getString("province");
                                    city = jsot.getString("city");
                                    area = jsot.getString("area");
                                    user_mobile = jsot.getString("user_mobile");
                                    user_address = jsot.getString("user_address");
                                    user_accept_name = user_dizhiname;

                                    int is_default = jsot.getInt("is_default");
                                    if (is_default == 1) {
                                        tv_user_name.setText("收货人："
                                                + user_accept_name);
                                        tv_user_address.setText("地址：" + province
                                                + "、" + city + "、" + area + "、"
                                                + user_address);
                                        tv_user_phone.setText(user_mobile);
                                        dizhi_type = true;
                                    }
                                }
                                if (dizhi_type == false) {
                                    tv_user_name.setText("收货人：" + user_accept_name);
                                    tv_user_address.setText("地址：" + province + "、"
                                            + city + "、" + area + "、"
                                            + user_address);
                                    tv_user_phone.setText(user_mobile);
                                }

                                SharedPreferences spPreferences = getSharedPreferences(
                                        "user_dizhixinxi", MODE_PRIVATE);
                                Editor editor = spPreferences.edit();
                                editor.putString("province", province);
                                editor.putString("city", city);
                                editor.putString("area", area);
                                editor.putString("user_address", user_address);
                                editor.putString("user_mobile", user_mobile);
                                editor.commit();

                                layout1.setVisibility(View.GONE);
                                progress.CloseProgress();
                                layout0.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {

                                progress.CloseProgress();
                                e.printStackTrace();
                            }
                        } else {
                            progress.CloseProgress();
                            layout1.setVisibility(View.VISIBLE);
                            layout0.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {

                        progress.CloseProgress();
                        e.printStackTrace();
                    }
                }

            }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 获取购单物信息 第1个列表数据解析
     */
    private int CURRENT_NUM = 1;
    private final int VIEW_NUM = 10;
    private int RUN_METHOD = -1;
    //    private double mSumMoney = 0.0;
    int mSumQuantity = 0; //z总计商品个数

    private void load_list() {//购物清单
        RUN_METHOD = 1;
        mList = new ArrayList<ShopCartData>();
        System.out.println("buy_no=====================" + buy_no);
        url = RealmName.REALM_NAME_LL + "/get_shopping_buy?buy_no=" + buy_no + "";
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    mNeedSumMoney = 0.0f;
                    int len = 0;
                    if (status.equals("y")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        len = jsonArray.length();
                        mSumQuantity = 0;
                        ShopCartData data = null;
                        for (int i = 0; i < len; i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            data = new ShopCartData();
                            data.title = json.getString("title");
                            data.market_price = json.getString("market_price");//市场价格
                            data.sell_price = json.getString("sell_price");//销售价格
                            data.cashing_packet = json.getDouble("cashing_packet"); //可用多少红包抵
                            data.exchange_price = json.getString("exchange_price"); //现金
                            data.exchange_point = json.getString("exchange_point");  //积分
                            data.id = json.getString("id");
                            // data.quantity = json.getInt("quantity");
                            data.setQuantity(json.getInt("quantity"));//数量
                            data.img_url = json.getString("img_url");//图片
                            mSumQuantity += data.quantity;
                            // 商品价格
                            BigDecimal goodPrice = new BigDecimal(Double.parseDouble(data.sell_price) * data.quantity);
                            // 保留2位小数
                            mNeedSumMoney += goodPrice.doubleValue();
                            System.out.println("zyjy  sum=" + mNeedSumMoney);
                            // 用红包抵的金额
                            BigDecimal redPacket = new BigDecimal(data.cashing_packet * data.quantity);
                            // 保留2位小数
                            mExpectPacket += redPacket.doubleValue();
                            cashing_packet = mExpectPacket;
                            mList.add(data);
                        }

                        // 判断红包是否
                        kedi_hongbao = String.valueOf(cashing_packet);
                        System.out.println("可用红包---------------" + packet);
                        System.out.println("可抵红包---------------" + cashing_packet);
                        double payMoney = 0.0;
                        if (0 == mOwnedPacket) {
                            tv_hongbao.setText("不可以使用红包");
//                            sb.setVisibility(View.GONE);
                            payMoney = mNeedSumMoney;
                        } else if (0 == mExpectPacket) {
                            tv_hongbao.setText("不可以使用红包" + "元");
                            payMoney = mNeedSumMoney;
                            sb.setVisibility(View.GONE);
                        } else if (mOwnedPacket > mExpectPacket) {
                            tv_hongbao.setText("可用" + mExpectPacket + "元红包抵" + mExpectPacket + "元");
                            sb.setVisibility(View.VISIBLE);
                            payMoney = mNeedSumMoney - mOwnedPacket;
                        } else if (mOwnedPacket < mExpectPacket) {
                            payMoney = mNeedSumMoney - mOwnedPacket;
                            tv_hongbao.setText("可用" + mOwnedPacket + "元红包抵" + mOwnedPacket + "元");
                            sb.setVisibility(View.VISIBLE);
                        }
                        isShowThreePay(payMoney);
                        heji.setText("实付款:" + doubleToString(payMoney) + "元");
                        System.out.println("mNeedSumMoney---------------" + mNeedSumMoney);

                        zhuangtai = false;
                        progress.CloseProgress();
                        jiekou_type_ysj = WareInformationActivity.jdh_type;
                        System.out.println("jiekou_type_ysj=====================" + jiekou_type_ysj);
                        if ("1".equals(jiekou_type_ysj) && data != null) {
                            ll_ljgm.setVisibility(View.VISIBLE);
                            rl_hongbao.setVisibility(View.GONE);
                            mExchangePoint = data.exchange_point;
                            mNeedSumMoney = Double.parseDouble(data.exchange_price);
                            mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + data.img_url);
                            tv_color.setText(mExchangePoint);
                            tv_color.setVisibility(View.GONE);
                            tv_size.setText(mExchangePoint + "福利" + "+" + mNeedSumMoney + "元");// 价格
                            tv_2.setText("兑换价:");
                            tv_warename.setText(data.title);
                            tv_1.setText("聚币:");
                            tv_1.setVisibility(View.GONE);
                            sumMoneyLayout.setVisibility(View.GONE);

                        } else {
                            adapter = new ShopingCartOrderAdapter(mList, MyOrderConfrimActivity.this);
                            list_shop_cart.setAdapter(adapter);
                        }
                        loadWeather();
                    } else {
                        progress.CloseProgress();
                        // Toast.makeText(MyOrderConfrimActivity.this, info,
                        // 200).show();
                        finish();
                    }

                    if (len != 0) {
                        // CURRENT_NUM = CURRENT_NUM + VIEW_NUM;
                        CURRENT_NUM = 1;
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, null);
    }

    private void isShowThreePay(double payMoney) {
        if (payMoney <= 0) {
            yu_pay0.setVisibility(View.GONE);
            yu_pay1.setVisibility(View.GONE);
            yu_pay_c2.callOnClick();
        } else {
            yu_pay0.setVisibility(View.VISIBLE);
            yu_pay1.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 配送方式
     */
    private void loadWeather() {
        AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_express_list?top=5",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        System.out.println("输出所有拼团活动列表=========" + arg1);
                        try {
                            list_zf = new ArrayList<JuTuanGouData>();
                            progress.CloseProgress();
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            if (status.equals("y")) {
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    JuTuanGouData data = new JuTuanGouData();
                                    data.setId(obj.getString("id"));
                                    data.setTitle(obj.getString("title"));
                                    data.setExpress_fee(obj.getInt("express_fee"));
                                    list_zf.add(data);
                                }
                                double payMoney = 0.0;

                                try {
                                    ZhiFuFangShi = ZhiFuFangShiActivity.title;
                                    express_id = ZhiFuFangShiActivity.express_id;
                                    express_fee = ZhiFuFangShiActivity.express_fee;
                                    if (ZhiFuFangShi != null) {
                                        if (express_fee == 0) {
                                            tv_zhifu.setText(ZhiFuFangShi + "(免邮)");
                                            if (mExchangePoint != null) {
                                                if (mNeedSumMoney > 0) {
                                                    heji.setText("实付款:" + " 福利" + mExchangePoint + " + " + "￥" + mNeedSumMoney);
                                                } else {
                                                    heji.setText("实付款:" + " 福利" + mExchangePoint);
                                                }
                                            } else {
                                                heji.setText("实付款:" + " ￥" + mNeedSumMoney);
                                                isShowThreePay(mNeedSumMoney);
                                            }
                                        } else {
                                            String price = String.valueOf(express_fee);
                                            tv_zhifu.setText(ZhiFuFangShi + "(" + "￥" + price + ")");
                                            BigDecimal c = new BigDecimal(mNeedSumMoney + express_fee);
                                            mNeedSumMoney = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                            if (mExchangePoint != null) {
                                                heji.setText("实付款:" + " 福利" + mExchangePoint + " + " + "￥" + mNeedSumMoney);
                                            } else {
                                                heji.setText("实付款:" + " ￥" + mNeedSumMoney);
                                                isShowThreePay(mNeedSumMoney);
                                            }
                                        }
                                    } else {
                                        ZhiFuFangShi = list_zf.get(0).getTitle();
                                        express_id = list_zf.get(0).getId();
                                        express_fee = list_zf.get(0).getExpress_fee();
                                        if (express_fee == 0) {
                                            tv_zhifu.setText(ZhiFuFangShi + "(免邮)");
                                            if (mExchangePoint != null) {
                                                if (mNeedSumMoney > 0) {
                                                    heji.setText("实付款:" + " 福利" + mExchangePoint + " + " + "￥" + mNeedSumMoney);
                                                } else {
                                                    heji.setText("实付款:" + " 福利" + mExchangePoint);
                                                }
                                            } else {
                                                heji.setText("实付款:" + " ￥" + mNeedSumMoney);
                                                isShowThreePay(mNeedSumMoney);
                                            }
                                        } else {
                                            String price = String.valueOf(express_fee);
                                            tv_zhifu.setText(ZhiFuFangShi + "(" + "￥" + price + ")");
                                            BigDecimal c = new BigDecimal(mNeedSumMoney + express_fee);
                                            mNeedSumMoney = c.setScale(2, BigDecimal.ROUND_HALF_UP)
                                                    .doubleValue();
                                            if (mExchangePoint != null) {
                                                heji.setText("实付款:" + " 福利" + mExchangePoint + " + " + "￥"
                                                        + mNeedSumMoney);
                                            } else {
                                                heji.setText("实付款:" + " ￥" + mNeedSumMoney);
                                                isShowThreePay(mNeedSumMoney);
                                            }
                                        }
                                    }

                                    System.out.println("合计2dzongjia----------------" + mNeedSumMoney);
                                    gethongbao();

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                                progress.CloseProgress();
                            } else {
                                progress.CloseProgress();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, null);
    }

    /**
     * 提交用户订单
     *
     * @param payment_id
     * @param kou_hongbao
     */
    String total_amount, is_cashing_point;

    private void loadusertijiao(String payment_id, int kou_hongbao) {
        try {
            jiekou_type_ysj = WareInformationActivity.jdh_type;
            System.out.println("jiekou_type_ysj=====================" + jiekou_type_ysj);
            if (jiekou_type_ysj.equals("1")) {
                // WareInformationActivity.jdh_type = "";
                jiekou_type = "add_order_point";// 提交兑换订单
                is_cashing_point = "1";
            } else {
                jiekou_type = "order_save";// 商品提交订单
                is_cashing_point = "0";
            }
            login_sign = spPreferences.getString("login_sign", "");
            System.out.println("login_sign=====================" + login_sign);
            String buy_no = getIntent().getStringExtra("buy_no");
            System.out.println("buy_no=====================" + buy_no);
            url = RealmName.REALM_NAME_LL + "/" + jiekou_type + "?user_id="
                    + user_id + "&user_name=" + user_name + "&user_sign="
                    + login_sign + "&is_cashing_packet=" + kou_hongbao + ""
                    + "&is_cashing_point=" + is_cashing_point + "&buy_no=" + buy_no + "&payment_id="
                    + payment_id + "&express_id=" + express_id
                    + "&is_invoice=0&invoice_title=&address_id="
                    + shopping_address_id + "" + "&accept_name="
                    + user_accept_name + "&province=" + province + "&city="
                    + city + "&area=" + area + "&address=" + user_address
                    + "&telphone=" + "&mobile=" + user_mobile
                    + "&email=&post_code=&message=";

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
                            if (status.equals("y")) {
                                JSONObject jsonObject = object.getJSONObject("data");
                                recharge_no = jsonObject.getString("trade_no");

                                System.out.println("jiekou_type_ysj=====================" + jiekou_type_ysj);
                                if (jiekou_type_ysj.equals("1")) {
                                    WareInformationActivity.jdh_type = "";
                                    total_amount = jsonObject.getString("payable_amount");
                                } else {
                                    total_amount = jsonObject.getString("total_amount");
                                }
                                // data = new ShopCartData();
                                if (type.equals("3")) {
                                    loadzhidu(recharge_no, total_amount);
                                } else if (type.equals("5")) {
                                    loadweixinzf2(recharge_no, total_amount);
                                } else if (type.equals("2")) {
                                    // loadYue(recharge_no);
                                    // teby = true;
                                    Intent intent = new Intent(MyOrderConfrimActivity.this,
                                            TishiCarArchivesActivity.class);
                                    intent.putExtra("order_no", recharge_no);
                                    intent.putExtra("yue", "yue");
                                    startActivityForResult(intent, 111);
                                }
                            } else {
//                                Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
                                if ("1".equals(jiekou_type_ysj)) {
                                    showMyJuDuiHuanActivity();
                                } else {
                                    showOrderActivity();
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

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            /*
             * http://www.ju918.com/mi/getdata.ashx?act=UserCartInfo&appkey=
			 * 0762222540
			 * &key=QUPgWi93j719&sign=AAE3474591B6B22950AD09A11082D4D751DDABC9
			 * &yth=112967999
			 */
        }
    };

    Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    ali_pay();
                    break;
                case 2:// 微信支付
                    try {
                        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                        // System.err.println("isPaySupported=============="+isPaySupported);
                        // Toast.makeText(MyOrderConfrimActivity.this, "获取订单中...",
                        // Toast.LENGTH_SHORT).show();
                        String zhou = String.valueOf(isPaySupported);
                        // Toast.makeText(MyOrderConfrimActivity.this, zhou,
                        // Toast.LENGTH_SHORT).show();
                        if (isPaySupported) {
                            try {
                                PayReq req = new PayReq();
                                req.appId = Constant.APP_ID;
                                req.partnerId = Constant.MCH_ID;
                                req.prepayId = prepayid;// 7
                                req.nonceStr = noncestr;// 3
                                req.timeStamp = timestamp;// -1
                                req.packageValue = package_;
                                req.sign = sign;// -3
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.registerApp(Constant.APP_ID);
                                // api.sendReq(req);
                                flag = api.sendReq(req);
                                System.out.println("支付" + flag);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MyOrderConfrimActivity.this, "支付失败，请在我的订单中查看", Toast.LENGTH_SHORT).show();
                            if ("1".equals(jiekou_type_ysj)) {
                                showMyJuDuiHuanActivity();
                            } else {
                                showOrderActivity();
                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case 5:// 支付宝
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    System.out.println(resultInfo + "---" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MyOrderConfrimActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        userloginqm();
                        //finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MyOrderConfrimActivity.this, "支付结果确认中,请在我订单中查看",
                                    Toast.LENGTH_SHORT).show();
                            if ("1".equals(jiekou_type_ysj)) {
                                showMyJuDuiHuanActivity();
                            } else {
                                showOrderActivity();
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MyOrderConfrimActivity.this, "支付失败，请在我的订单查看", Toast.LENGTH_SHORT).show();
                            if ("1".equals(jiekou_type_ysj)) {
                                showMyJuDuiHuanActivity();
                            } else {
                                showOrderActivity();
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 订单更新支付
     *
     * @param login_sign
     * @param
     */
    private void loadguanggaoll(String recharge_noll, String login_sign) {
        try {
            // recharge_no = recharge_noll;
            AsyncHttp.get(RealmName.REALM_NAME_LL
                            + "/update_order_payment?user_id=" + user_id
                            + "&user_name=" + user_name + "" + "&trade_no="
                            + recharge_noll + "&sign=" + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("更新订单=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    list_shop_cart.setVisibility(View.GONE);
                                    teby = false;

                                    //get_order_trade_list //

                                    // JSONObject jsonObject =
                                    // object.getJSONObject("data");
                                    // JSONArray jay =
                                    // jsonObject.getJSONArray("orders");
                                    // for (int j = 0; j < jay.length(); j++){
                                    // JSONObject objc= jay.getJSONObject(j);
                                    // accept_name1 = objc.getString("accept_name");
                                    // province1 = objc.getString("province");
                                    // city1 = objc.getString("city");
                                    // area1 = objc.getString("area");
                                    // user_mobile1 = objc.getString("mobile");
                                    // user_address1 = objc.getString("address");
                                    // recharge_no1 = objc.getString("order_no");
                                    // datetime1 = objc.getString("add_time");
                                    // sell_price1 = objc.getString("payable_amount");
                                    // JSONArray jsonArray =
                                    // objc.getJSONArray("order_goods");
                                    // for (int i = 0; i < jsonArray.length(); i++) {
                                    // JSONObject json = jsonArray.getJSONObject(i);
                                    // article_id1 = json.getString("article_id");
                                    // // sell_price1 = json.getString("sell_price");
                                    // give_pension1 = json.getString("give_pension");
                                    // }
                                    // }

                                    //							 Intent intent = new Intent(MyOrderConfrimActivity.this,MyOrderXqActivity.class);
                                    //							 startActivity(intent);
                                    //							Toast.makeText(MyOrderConfrimActivity.this, info,Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    progress.CloseProgress();
                                    teby = false;
                                    Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            System.out.println("11================================="
                                    + arg0);
                            System.out.println("22================================="
                                    + arg1);
                            Toast.makeText(MyOrderConfrimActivity.this, "异常", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 支付宝
     *
     * @param total_amount
     * @param
     */
    private void loadzhidu(String recharge_no3, String total_amount) {
        try {
            recharge_no = recharge_no3;
            // total_fee = String.valueOf(Double.parseDouble(retailPrice) +
            // Double.parseDouble(String.valueOf(express_fee)));
            total_fee = total_amount;
            System.out.println("22retailPrice================================="
                    + total_fee);
            AsyncHttp.get(RealmName.REALM_NAME_LL + "/payment_sign?user_id="
                    + user_id + "&user_name=" + user_name + "" + "&total_fee="
                    + total_amount + "&out_trade_no=" + recharge_no
                    + "&payment_type=alipay", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {
                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject object = new JSONObject(arg1);
                        System.out.println("2================================="
                                + arg1);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        if (status.equals("y")) {
                            JSONObject obj = object.getJSONObject("data");
                            notify_url = obj.getString("notify_url");
                            progress.CloseProgress();
                            handler.sendEmptyMessage(1);
                        } else {
                            progress.CloseProgress();
                            Toast.makeText(MyOrderConfrimActivity.this, info,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 微信支付
     *
     * @param total_amount
     * @param
     */
    private void loadweixinzf2(String recharge_no2, String total_amount) {
        try {
            recharge_no = recharge_no2;
            // String monney =
            // String.valueOf(Integer.parseInt(retailPrice)*100);
            String monney = String
                    .valueOf(Double.parseDouble(total_amount) * 100);

            AsyncHttp.get(RealmName.REALM_NAME_LL + "/payment_sign?user_id="
                            + user_id + "&user_name=" + user_name + "" + "&total_fee="
                            + monney + "&out_trade_no=" + recharge_no
                            + "&payment_type=weixin",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {

                                JSONObject object = new JSONObject(arg1);
                                System.out
                                        .println("weixin================================="
                                                + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject jsonObject = object
                                            .getJSONObject("data");
                                    partner_id = jsonObject.getString("mch_id");
                                    prepayid = jsonObject.getString("prepay_id");
                                    noncestr = jsonObject.getString("nonce_str");
                                    timestamp = jsonObject.getString("timestamp");
                                    package_ = "Sign=WXPay";
                                    sign = jsonObject.getString("sign");
                                    System.out
                                            .println("weixin================================="
                                                    + package_);
                                    progress.CloseProgress();
                                    handler.sendEmptyMessage(2);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MyOrderConfrimActivity.this, info,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 余额支付
     *
     * @param
     */
    private void loadYue(String recharge_no) {
        try {
            AsyncHttp.get(RealmName.REALM_NAME_LL + "/payment_balance?user_id="
                            + user_id + "&user_name=" + user_name + "" + "&trade_no="
                            + recharge_no + "&paypassword=" + pwd + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2================================="
                                        + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    userloginqm();
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MyOrderConfrimActivity.this, info,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void ali_pay() {
        try {

            //
            String orderInfo = getOrderInfo("中安民生商品", "商品描述", recharge_no);

            // 对订单做RSA 签名
            String sign = sign(orderInfo);
            try {
                // 仅需对sign 做URL编码
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 完整的符合支付宝参数规范的订单信息
            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                    + getSignType();

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(MyOrderConfrimActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo);
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, Common.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String dingdan) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + total_fee + "\"";
        // orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";

        // 服务器异步通知页面路径
        System.out.println("======notify_url=============" + notify_url);
        // String str = notify_url;
        // String notify_url_ll = str.replaceAll("\\s*", "");
        // orderInfo += "&notify_url=" + "\""+notify_url_ll+"\"";
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
        System.out.println("======orderInfo=============" + orderInfo);

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        System.out.println(orderInfo);
        return orderInfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progress.CloseProgress();
    }

    /**
     * 去我的订单页面
     */
    private void showOrderActivity() {
        Intent intent = new Intent(MyOrderConfrimActivity.this, MyOrderActivity.class);
        intent.putExtra("status", "1");
        startActivity(intent);
        finish();
    }

    private void showMyJuDuiHuanActivity() {
        Intent intent = new Intent(MyOrderConfrimActivity.this, MyJuDuiHuanActivity.class);
        intent.putExtra("num", "2");
        startActivity(intent);
        finish();
    }
}
