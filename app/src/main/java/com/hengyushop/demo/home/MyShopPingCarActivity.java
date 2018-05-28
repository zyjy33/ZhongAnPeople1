package com.hengyushop.demo.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GouWuCheAGoodsAdaper;
import com.android.hengyu.pub.QiYeJinMianAdaper;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.GoodsMyGridViewAdaper;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.shopcart.MyShopCarActivity;
import com.hengyushop.demo.shopcart.TuiJianSpListActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.DataBean;
import com.hengyushop.entity.ShopCartBean;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.MyPosterView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.HomeActivity;
import com.zams.www.MainFragment;
import com.zams.www.MyOrderConfrimActivity;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;
import com.zams.www.UserLoginWayActivity;
import com.zams.www.WareInformationActivity;

public class MyShopPingCarActivity extends Fragment implements OnClickListener {

    private static final int INITIALIZE = 0;
    private SharedPreferences spPreferences;
    private static ListView mListView;// 列表
    public static String user_id;
    private ListAdapter mListAdapter;// adapter
    private LinearLayout adv_pager, ll_tjsp;
    private LinearLayout ll_xianshi;
    private List<DataBean> mListData = new ArrayList<DataBean>();// 数据

    private boolean isBatchModel;// 是否可删除模式

    private RelativeLayout mBottonLayout;
    private CheckBox mCheckAll; // 全选 全不选
    private TextView mEdit; // 切换到删除模式

    private TextView mPriceAll; // 商品总价

    private TextView mSelectNum; // 选中数量

    private TextView mFavorite; // 移到收藏夹,分享

    private TextView mDelete; // 删除 结算

    private TextView subtitle;
    ImageView imageView1;
    public boolean ptye = false;
    private double totalPrice = 0; // 商品总价
    public static double dzongjia = 0;
    /**
     * 批量模式下，用来记录当前选中状态
     */
    private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
    private ImageView back;
    List<DataBean> result;
    private DialogProgress progress;
    public static AQuery query;
    private static List<String> list_id = new ArrayList<String>();
    private static List<String> list_size = new ArrayList<String>();
    String num = "1";
    public static StringBuffer str;
    boolean zhuangtai = true;
    private Button btn_register;
    private MyGridView myGridView;
    public static boolean type = false;
    GouWuCheAGoodsAdaper jdhadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_gouwuche, null);

        progress = new DialogProgress(getActivity());
        query = new AQuery(getActivity());
        initView(layout);
        initListener();
        load_list();
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    String weixin = "";
    String qq = "";
    String nickname = "";
    String user_name = "";
    String user_name_phone = "";
    String oauth_name;
    String datall;

    @Override
    public void onResume() {

        super.onResume();
        WareInformationActivity.jdh_type = "";//聚兑换判断为空

        SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
        nickname = spPreferences_login.getString("nickname", "");

        System.out.println("nickname=================" + nickname);
        if (!nickname.equals("")) {
            getjianche();//后台检测是否绑定手机
        } else {
            getuserxinxi();
        }


        spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");

        System.out.println("user_id================" + user_id);
        System.out.println("user_name================" + user_name);

        if (user_name.equals("")) {
            adv_pager.setVisibility(View.VISIBLE);
            subtitle.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
            ll_xianshi.setVisibility(View.GONE);
        } else {
            getgouwuche();
        }

        MyShopCarActivity.str = null;//商品详情里的购物车清空
        //计算商品提交ID清空
        if (list_id.size() > 0) {
            list_id.clear();

        }
        //计算个数清空
        if (list_size.size() > 0) {
            list_size.clear();
            setQuantitySum();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();

        if (lists.size() > 0) {
            lists.clear();
            lists = null;
        }

        if (result.size() > 0) {
            result.clear();
            result = null;
        }

    }

    private void getjianche() {

        SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
        nickname = spPreferences_login.getString("nickname", "");
        String headimgurl = spPreferences_login.getString("headimgurl", "");
        String unionid = spPreferences_login.getString("unionid", "");
        String access_token = spPreferences_login.getString("access_token", "");
        String sex = spPreferences_login.getString("sex", "");

        System.out.println("UserLoginActivity=====================" + UserLoginActivity.oauth_name);
        System.out.println("UserLoginWayActivity=====================" + UserLoginWayActivity.oauth_name);

        if (UserLoginActivity.oauth_name.equals("weixin")) {
            oauth_name = "weixin";
        } else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
            oauth_name = "qq";
        }

        System.out.println("nickname-----1-----" + nickname);
        String nick_name = nickname.replaceAll("\\s*", "");
        System.out.println("nick_name-----2-----" + nick_name);

        String oauth_openid = spPreferences_login.getString("oauth_openid", "");
        String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0217?nick_name=" + nick_name + "&sex=" + sex + "&avatar=" + headimgurl + "" +
                "&province=&city=&country=&oauth_name=" + oauth_name + "&oauth_unionid=" + unionid + "" +
                "&oauth_openid=" + oauth_openid + "";
        System.out.println("我的======11======1=======" + strUrlone);
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                System.out.println("我的======输出=====1========" + arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    datall = object.getString("data");
                    System.out.println("datall==============" + datall);
                    if (datall.equals("null")) {

                        SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
                        weixin = spPreferences_tishi.getString("weixin", "");
                        qq = spPreferences_tishi.getString("qq", "");
                        if (!nickname.equals("")) {

                            if (UserLoginActivity.panduan_tishi == true) {
                                if (weixin.equals("weixin")) {
                                } else {
                                    Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                                    startActivity(intent1);
                                    UserLoginActivity.panduan_tishi = false;
                                }

                            } else if (UserLoginWayActivity.panduan_tishi == true) {
                                if (qq.equals("qq")) {
                                } else {
                                    Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
                                    startActivity(intent2);
                                    UserLoginWayActivity.panduan_tishi = false;
                                }
                            }
                        }

                    } else {
                        UserRegisterllData data = new UserRegisterllData();
                        JSONObject obj = object.getJSONObject("data");
                        data.id = obj.getString("id");
                        data.user_name = obj.getString("user_name");
                        data.login_sign = obj.getString("login_sign");
                        user_id = data.id;
                        System.out.println("---data.user_name-------------------" + data.user_name);
                        System.out.println("---user_id-------------------" + user_id);
                        if (data.user_name.equals("匿名")) {
                            //									if (data.id.equals("0")) {
                            System.out.println("---微信还未绑定-------------------");

                        } else {
                            SharedPreferences spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
                            Editor editor = spPreferences.edit();
                            editor.putString("user", data.user_name);
                            editor.putString("user_id", data.id);
                            editor.putString("login_sign", data.login_sign);
                            editor.commit();
                            String user_name = spPreferences.getString("user", "");
                            System.out.println("---2-------------------" + user_name);
                        }
                    }

                    getuserxinxi();

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            ;
        }, getActivity());

    }

    private void getuserxinxi() {

        spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");
        System.out.println("user_name=================" + user_name);
        System.out.println("user_id=================" + user_id);
    }

    DataBean dm;

    private void getgouwuche() {

        System.out.println("ptye================" + ptye);
        if (ptye == false) {
            progress.CreateProgress();
            //			ptye = true;
        }

        result = new ArrayList<DataBean>();
        //		user_id = spPreferences.getString("user_id", "");
        //		if (!user_id.equals("")) {

        AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_shopping_cart?pageSize=500&pageIndex=1&user_id=" + user_id + ""
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            System.out.println("购物车数据================");
                            //							System.out.println("购物车数据================"+arg1);
                            JSONArray jsot = jsonObject.getJSONArray("data");
                            System.out.println("jsot================" + jsot.length());
                            if (jsot.length() > 0) {
                                for (int i = 0; i < jsot.length(); i++) {
                                    dm = new DataBean();
                                    JSONObject object = jsot.getJSONObject(i);
                                    dm.setId(object.getInt("id"));
                                    dm.setTitle(object.getString("title"));
                                    dm.setMarket_price(object.getString("market_price"));
                                    dm.setSell_price(object.getDouble("sell_price"));
                                    dm.setImg_url(object.getString("img_url"));
                                    dm.setQuantity(object.getInt("quantity"));
                                    dm.setArticle_id(object.getString("article_id"));
                                    dm.setGoods_id(object.getString("goods_id"));
                                    result.add(dm);

                                }
                                dm = null;
                                progress.CloseProgress();
                                adv_pager.setVisibility(View.GONE);
                                subtitle.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.VISIBLE);
                                ll_xianshi.setVisibility(View.VISIBLE);
                                System.out.println("1================");
                            } else {
                                progress.CloseProgress();
                                System.out.println("2================");
                                adv_pager.setVisibility(View.VISIBLE);
                                subtitle.setVisibility(View.GONE);
                                mListView.setVisibility(View.GONE);
                                //								mPriceAll.setText("￥"+0.00);
                                ll_xianshi.setVisibility(View.GONE);
                            }
                            //							refreshListView();
                            progress.CloseProgress();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }

                }, null);
        //		}else {
        //			progress.CloseProgress();
        ////			adv_pager.setVisibility(View.VISIBLE);
        //		}
        totalPrice = 0;
        mPriceAll.setText("￥" + totalPrice);
        setQuantitySum();
        mCheckAll.setChecked(false);
        System.out.println("result22-------------" + result.size());
        loadData();
    }

    /**
     * 热销专区
     */
    private ArrayList<SpListData> lists;
    SpListData spList;

    private void load_list() {
        lists = new ArrayList<SpListData>();
        try {
            //821
            AsyncHttp.get(RealmName.REALM_NAME_LL +
                            "/get_article_top_list?channel_name=goods&top=5&strwhere=status=0%20and%20is_top=1",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            super.onSuccess(arg0, arg1);
                            //								System.out.println("热销专区====================="+arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                String status = jsonObject.getString("status");
                                String info = jsonObject.getString("info");
                                if (status.equals("y")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    //									 len = jsonArray.length();
                                    for (int i = 1; i < jsonArray.length(); i++) {
                                        spList = new SpListData();
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        spList.id = object.getString("id");
                                        spList.img_url = object.getString("img_url");
                                        spList.title = object.getString("title");
                                        spList.market_price = object.getString("market_price");
                                        spList.sell_price = object.getString("sell_price");
                                        lists.add(spList);
                                    }
                                    spList = null;
                                } else {
                                    //										progress.CloseProgress();
                                    Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                }
                                System.out.println("lists.size()=====================" + lists.size());
                                jdhadapter = new GouWuCheAGoodsAdaper(lists, getActivity());
                                myGridView.setAdapter(jdhadapter);
                                myGridView.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                        System.out.println("=====================" + lists.get(arg2).id);
                                        Intent intent = new Intent(getActivity(), WareInformationActivity.class);
                                        intent.putExtra("id", lists.get(arg2).id);
                                        startActivity(intent);
                                    }
                                });
                                //									progress.CloseProgress();
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    }, null);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void initView(View layout) {
        imageView1 = (ImageView) layout.findViewById(R.id.imageView1);
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.zams_gwc);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
        imageView1.setBackgroundDrawable(bd);

        myGridView = (MyGridView) layout.findViewById(R.id.gridView);
        myGridView.setFocusable(false);
        back = (ImageView) layout.findViewById(R.id.back);
        adv_pager = (LinearLayout) layout.findViewById(R.id.adv_pager);
        ll_xianshi = (LinearLayout) layout.findViewById(R.id.ll_xianshi);
        mBottonLayout = (RelativeLayout) layout.findViewById(R.id.cart_rl_allprie_total);
        mCheckAll = (CheckBox) layout.findViewById(R.id.check_box_all);
        mEdit = (TextView) layout.findViewById(R.id.subtitle);
        mPriceAll = (TextView) layout.findViewById(R.id.tv_cart_total);
        mFavorite = (TextView) layout.findViewById(R.id.tv_cart_move_favorite);
        mDelete = (TextView) layout.findViewById(R.id.tv_cart_buy_or_del);
        subtitle = (TextView) layout.findViewById(R.id.subtitle);
        mListView = (ListView) layout.findViewById(R.id.listview);
        mListView.setSelector(R.drawable.list_selector);

        btn_register = (Button) layout.findViewById(R.id.btn_register);

        ll_tjsp = (LinearLayout) layout.findViewById(R.id.ll_tjsp);
        ll_tjsp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getActivity(), TuiJianSpListActivity.class);
                startActivity(intent);
            }
        });


        //购物车无商品去逛逛
        btn_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!TextUtils.isEmpty(nickname)) {
                    if (!user_name.equals("")) {
                        //								Intent Intent2 = new Intent(getActivity(),NewWare.class);
                        //								Intent2.putExtra("channel_name", "life");
                        //								startActivity(Intent2);
                    } else {
                        Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (user_name.equals("")) {
                        Intent intentll = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intentll);
                    } else {
                        //						Intent intent = new Intent(getActivity(),NewWare.class);
                        //						intent.putExtra("channel_name", "life");
                        //						startActivity(intent);
                        MainFragment.handlerll.sendEmptyMessage(0);
                    }
                }

            }
        });
    }

    private void initListener() {
        mEdit.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mCheckAll.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void loadData() {
        new LoadDataTask().execute(new Params(INITIALIZE));
    }

    private void refreshListView() {
        if (mListAdapter == null) {
            try {
                mListAdapter = new ListAdapter();
                mListView.setAdapter(mListAdapter);
                mListView.setOnItemClickListener(mListAdapter);
                //			query.clear();
                //			System.out.println("clear------11-------清除内存");
                setListViewHeightBasedOnChildren(mListView);
            } catch (Exception e) {

                e.printStackTrace();
            }
        } else {
            try {
                mListAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(mListView);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private List<DataBean> getData() {
        //		System.out.println("result11-------------"+result.size());
        return result;
    }

    class Params {
        int op;

        public Params(int op) {
            this.op = op;
            System.out.println("result1-------------");
        }

    }

    class Result {
        int op;
        List<DataBean> list;
    }

    private class LoadDataTask extends AsyncTask<Params, Void, Result> {
        @Override
        protected Result doInBackground(Params... params) {
            Params p = params[0];
            Result result = new Result();
            result.op = p.op;
            try {// 模拟耗时
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.list = getData();
            return result;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.op == INITIALIZE) {
                mListData = result.list;
                query.clear();
                System.out.println("clear-------------清除内存");
                System.out.println("result2-------------");
            } else {
                System.out.println("result3-------------");
                mListData.addAll(result.list);
                Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();
            }

            refreshListView();
        }

    }

    boolean isSelect = false;

    /**
     * 商品列表
     *
     * @author Administrator
     */
    private class ListAdapter extends BaseAdapter implements
            OnItemClickListener {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder = null;

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                try {


                    holder = new ViewHolder();
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_list_item, null);
                    holder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
                    holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
                    holder.image = (ImageView) view.findViewById(R.id.iv_adapter_list_pic);
                    holder.content = (TextView) view.findViewById(R.id.tv_intro);
                    holder.carNum = (TextView) view.findViewById(R.id.tv_num);
                    holder.price = (TextView) view.findViewById(R.id.tv_price);
                    holder.add = (TextView) view.findViewById(R.id.tv_add);
                    holder.red = (TextView) view.findViewById(R.id.tv_reduce);
                    holder.frontView = view.findViewById(R.id.item_left);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            try {


                final DataBean data = mListData.get(position);
                bindListItem(holder, data);


                //			mListData.get(position).setChoose(true);
                if (data != null) {
                    // 判断是否选择
                    if (data.isChoose()) {
                        holder.checkBox.setChecked(true);
                    } else {
                        holder.checkBox.setChecked(false);
                    }

                    // 选中操作
                    //				holder.checkBox.setOnClickListener(new CheckBoxOnClick(data));
                    // 减少操作
                    holder.red.setOnClickListener(new ReduceOnClick(data, holder.carNum));

                    // 增加操作
                    holder.add.setOnClickListener(new AddOnclick(data, holder.carNum));

                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return view;
        }


        private class AddOnclick implements OnClickListener {

            DataBean shopcartEntity;
            TextView shopcart_number_btn;

            private AddOnclick(DataBean shopcartEntity,
                               TextView shopcart_number_btn) {
                this.shopcartEntity = shopcartEntity;
                this.shopcart_number_btn = shopcart_number_btn;

            }

            @Override
            public void onClick(View arg0) {
                shopcartEntity.setChoose(true);
                String numberStr = shopcart_number_btn.getText().toString();
                if (!TextUtils.isEmpty(numberStr)) {
                    int number = Integer.parseInt(numberStr);

                    int currentNum = number + 1;
                    // 设置列表
                    shopcartEntity.setQuantity(currentNum);
                    holder.carNum.setText("" + currentNum);
                    int cart_id = shopcartEntity.getId();
                    System.out.println("============cart_id==============" + cart_id);
                    AsyncHttp.get(RealmName.REALM_NAME_LL + "/cart_goods_update?cart_id=" + cart_id + "&user_id=" + user_id + "&quantity=" + currentNum + "", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            System.out.println("==========================访问接口成功！" + arg1);
                            super.onSuccess(arg0, arg1);
                        }

                    }, getActivity());
                    notifyDataSetChanged();
                }
                count();
            }

        }

        private class ReduceOnClick implements OnClickListener {
            DataBean shopcartEntity;
            TextView shopcart_number_btn;

            private ReduceOnClick(DataBean shopcartEntity, TextView shopcart_number_btn) {
                this.shopcartEntity = shopcartEntity;
                this.shopcart_number_btn = shopcart_number_btn;
            }

            @Override
            public void onClick(View arg0) {
                shopcartEntity.setChoose(true);
                String numberStr = shopcart_number_btn.getText().toString();
                if (!TextUtils.isEmpty(numberStr)) {
                    int number = Integer.parseInt(numberStr);
                    if (number == 1) {
                        Toast.makeText(getActivity(), "不能往下减少了", Toast.LENGTH_SHORT).show();
                    } else {
                        int currentNum = number - 1;
                        // 设置列表
                        shopcartEntity.setQuantity(currentNum);

                        holder.carNum.setText("" + currentNum);
                        int cart_id = shopcartEntity.getId();
                        System.out.println("============cart_id==============" + cart_id);
                        AsyncHttp.get(RealmName.REALM_NAME_LL + "/cart_goods_update?cart_id=" + cart_id + "&user_id=" + user_id + "&quantity=" + currentNum + "", new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {
                                System.out.println("==========================2访问接口成功！" + arg1);
                                super.onSuccess(arg0, arg1);
                            }

                        }, getActivity());
                        notifyDataSetChanged();

                    }

                }
                count();
            }

        }

        private void bindListItem(ViewHolder holder, DataBean data) {

            // holder.shopName.setText(data.getShopName());
            holder.content.setText(data.getTitle());
            holder.price.setText("￥" + data.getSell_price());
            holder.carNum.setText(data.getQuantity() + "");
            holder.tv_size.setText("￥" + data.getMarket_price());
            holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            query.id(holder.image).image(RealmName.REALM_NAME_HTTP + data.getImg_url());
            type = true;
            int _id = data.getId();
            boolean selected = mSelectState.get(_id, false);
            //			System.out.println("selected-------------"+selected);
            holder.checkBox.setChecked(selected);

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            DataBean bean = mListData.get(position);

            ViewHolder holder = (ViewHolder) view.getTag();
            int _id = (int) bean.getId();

            boolean selected = !mSelectState.get(_id, false);
            holder.checkBox.toggle();
            // 将CheckBox的选中状况记录下来
            mListData.get(position).setChoose(holder.checkBox.isChecked());
            // 调整选定条目
            if (holder.checkBox.isChecked()) {
                list_size.add(num);
                setQuantitySum();
                totalPrice += bean.getQuantity() * bean.getSell_price();
            } else {
                list_size.remove(num);
                setQuantitySum();
                mSelectState.delete(position);
                totalPrice -= bean.getQuantity() * bean.getSell_price();
            }
            BigDecimal c = new BigDecimal(totalPrice);
            dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mPriceAll.setText("￥" + dzongjia + "");
            setQuantitySum();
            if (mSelectState.size() == mListData.size()) {
                mCheckAll.setChecked(true);
            } else {
                mCheckAll.setChecked(false);
            }

        }

    }


    class ViewHolder {
        CheckBox checkBox;

        ImageView image;
        TextView shopName;
        TextView content, tv_size;
        TextView carNum;
        TextView price;
        TextView add;
        TextView red;
        Button button; // 用于执行删除的button
        View frontView;
        LinearLayout item_right, item_left;
    }

    List<Integer> list_num;
    List<Integer> list_num2;
    int i;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.subtitle:
                isBatchModel = !isBatchModel;
                if (isBatchModel) {//删除商品
                    mEdit.setText(getResources().getString(R.string.menu_enter));
                    mDelete.setText(getResources().getString(R.string.menu_del));
                    mBottonLayout.setVisibility(View.VISIBLE);
                    mFavorite.setVisibility(View.GONE);
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("￥" + dzongjia);
                    setQuantitySum();
                    zhuangtai = false;
                } else {
                    mEdit.setText(getResources().getString(R.string.menu_edit));
                    mDelete.setText(getResources().getString(R.string.menu_sett));
                    mFavorite.setVisibility(View.GONE);
                    mBottonLayout.setVisibility(View.VISIBLE);
                    //				totalPrice=0;
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("￥" + dzongjia);
                    setQuantitySum();
                    zhuangtai = true;
                }

                break;

            case R.id.check_box_all:
                totalPrice = 0;
                if (mCheckAll.isChecked()) {
                    list_size.clear();
                    for (int i = 0; i < mListData.size(); i++) {
                        mListData.get(i).setChoose(true);
                        // 如果为选中
                        if (mListData.get(i).isChoose()) {
                            totalPrice = totalPrice + mListData.get(i).getQuantity() * mListData.get(i).getSell_price();
                        }
                    }

                    // 刷新
                    mListAdapter.notifyDataSetChanged();
                    // 显示
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("￥" + dzongjia);
                    setQuantitySum();
                } else {
                    for (int i = 0; i < mListData.size(); i++) {
                        mListData.get(i).setChoose(false);
                        list_size.clear();
                    }
                    //刷新
                    mListAdapter.notifyDataSetChanged();
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("￥" + dzongjia);
                    setQuantitySum();
                }
                break;

            case R.id.tv_cart_buy_or_del:
                list_num = new ArrayList<Integer>();
                list_num2 = new ArrayList<Integer>();
                System.out.println("isBatchModel-------------" + isBatchModel);
                if (isBatchModel) {
                    if (list_size.size() == 0) {
                        Toast.makeText(getActivity(), "请选择要删除的商品", Toast.LENGTH_SHORT).show();
                    } else {

                        String str1 = "";
                        for (i = 0; i < mListData.size(); i++) {
                            if (mListData.get(i).isChoose()) {
                                System.out.println("i==========================" + i);
                                list_num.add(i);
                                //								String fegefu = str1.length()>0?",":"";
                                //								str1 = str1+fegefu+String.valueOf(mListData.get(i).getId());
                                String strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?" + "clear=0&user_id=" + user_id + "&cart_id=" + mListData.get(i).getId();
                                AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int arg0, String arg1) {

                                        System.out.println("==========================删除接口成功！" + arg1);
                                        super.onSuccess(arg0, arg1);
                                        try {

                                            JSONObject object = new JSONObject(arg1);
                                            String status = object.getString("status");
                                            String info = object.getString("info");
                                            if (status.equals("y")) {
                                                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                                System.out.println("mListData.size()==========================" + mListData.size());
                                                ptye = true;
                                                System.out.println("list_size.size()==========================" + list_size.size());
                                                list_num2.add(i);
                                                //										    	 	result.clear();
                                                //													System.out.println("result.size()=========================="+result.size());
                                                System.out.println("list_num.size()==========================" + list_num.size());
                                                System.out.println("list_num2.size()==========================" + list_num2.size());
                                                if (list_num.size() == list_num2.size()) {
                                                    getgouwuche();
                                                    list_num.clear();
                                                    list_num2.clear();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {

                                            e.printStackTrace();
                                        }
                                    }

                                }, getActivity());

                            }

                        }

                        //计算个数清空
                        if (list_size.size() > 0) {
                            list_size.clear();
                            setQuantitySum();
                        }

                        //						String strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?"+ "clear=0&user_id=" + user_id+ "&cart_id=" + str1;
                        //						AsyncHttp.get(strUrl,new AsyncHttpResponseHandler() {
                        //							@Override
                        //							public void onSuccess(int arg0, String arg1) {
                        //
                        //								System.out.println("==========================访问接口成功！"+arg1);
                        //								super.onSuccess(arg0, arg1);
                        //								try {
                        //
                        //								JSONObject object = new JSONObject(arg1);
                        //								  String status = object.getString("status");
                        //								    String info = object.getString("info");
                        //								    if (status.equals("y")) {
                        //								    	Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                        //								    	// 刷新
                        ////										mListAdapter.notifyDataSetChanged();
                        //								    }else {
                        //								    	Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                        //									}
                        //								} catch (Exception e) {
                        //
                        //									e.printStackTrace();
                        //								}
                        //							}
                        //
                        //						}, getActivity());
                    }
                } else {
                    String str1 = "";
                    String str2 = "";
                    String str3 = "";
                    if (totalPrice != 0) {
                        for (int i = 0; i < mListData.size(); i++) {
                            if (mListData.get(i).isChoose()) {
                                //							str1 = "";//先清空
                                //							str2 = "";//先清空
                                //							str3 = "";//先清空
                                String fegefu = str1.length() > 0 ? "," : "";
                                str1 = str1 + fegefu + String.valueOf(mListData.get(i).getArticle_id());
                                str2 = str2 + fegefu + String.valueOf(mListData.get(i).getGoods_id());
                                str3 = str3 + fegefu + String.valueOf(mListData.get(i).getQuantity());

                            }
                        }
                        System.out.println("str1-------------" + str1);
                        String zhou = str1 + "/" + str2 + "/" + str3;
                        if (str1.equals("")) {
                            Toast.makeText(getActivity(), "请选择要支付的商品", Toast.LENGTH_SHORT).show();
                            //							mListAdapter.notifyDataSetChanged();
                        } else {
                            loadgouwuche(str1, str2, str3);
                        }

                    } else {
                        Toast.makeText(getActivity(), "请选择要支付的商品", Toast.LENGTH_SHORT).show();
                        mListAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                break;
            case R.id.back:
                //			onBackPressed();
                break;
            default:
                break;
        }
    }

    /**
     * 计算价格
     */
    public void count() {

        totalPrice = 0;// 人民币
        int quantitySum = 0;
        if (mListData != null && mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                DataBean dataBean = mListData.get(i);
                if (dataBean.isChoose()) {
                    int quantity = dataBean.getQuantity();
                    totalPrice = totalPrice + quantity
                            * dataBean.getSell_price();
                    quantitySum += quantity;
                }
            }
            BigDecimal c = new BigDecimal(totalPrice);
            dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mPriceAll.setText("￥" + dzongjia);
            setQuantitySum();
        }
    }

    private void setQuantitySum() {
        int quantitySum = 0;
        if (mListData != null && mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                DataBean dataBean = mListData.get(i);
                if (dataBean.isChoose()) {
                    int quantity = dataBean.getQuantity();
                    quantitySum += quantity;
                }
            }

            if (isBatchModel) {
                subtitle.setText(R.string.menu_edit);
                mDelete.setText(getResources().getString(R.string.menu_del) + "(" + quantitySum + ")");
            } else {
                subtitle.setText(R.string.menu_enter);
                mDelete.setText(getResources().getString(R.string.menu_sett) + "(" + quantitySum + ")");
            }
        }
    }

    public void select() {
        int count = 0;
        for (int i = 0; i < mListData.size(); i++) {
            if (mListData.get(i).isChoose()) {
                count++;
            }
        }
        if (count == mListData.size()) {
            mCheckAll.setChecked(true);
        } else {
            isSelect = true;
            mCheckAll.setChecked(false);
        }

    }

    private void loadgouwuche(String str1, String str2, String str3) {
        try {
            progress.CreateProgress();
            String login_sign = spPreferences.getString("login_sign", "");
            AsyncHttp.get(RealmName.REALM_NAME_LL + "/add_shopping_buys?user_id=" + user_id + "&user_name=" + user_name_phone +
                            "&user_sign=" + login_sign + "&article_id=" + str1 + "&goods_id=" + str2 + "&quantity=" + str3 + "",
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
                                    String buy_no = obj.getString("buy_no");
                                    String count = obj.getString("count");

                                    //									JSONArray jsot = jsonObject.getJSONArray("data");
                                    //									ShopCartBean bean = new ShopCartBean();
                                    //									for (int i = 0; i < jsot.length(); i++) {
                                    //									JSONObject obj = jsot.getJSONObject(i);
                                    //									bean.setId(obj.getString("id"));
                                    //									String id = obj.getString("id");
                                    //									list_id.add(id);
                                    //									}
                                    //									    str = new StringBuffer();
                                    //								        for(String s:list_id){
                                    //								        	str.append(s+",");
                                    //								        }
                                    //								        str.delete(str.lastIndexOf(","),str.length());
                                    //								        System.out.println("id拼接之后---------------"+str);


                                    //									Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MyOrderConfrimActivity.class);
                                    intent.putExtra("buy_no", buy_no);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                }
                                progress.CloseProgress();
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            System.out.println("==========================访问接口失败！");
                            System.out.println("=========================" + arg0);
                            System.out.println("==========================" + arg1);
                            super.onFailure(arg0, arg1);
                        }


                    }, getActivity());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //	private void loadgouwuche(String str1, String str2, String str3){
    //		try {
    //			progress.CreateProgress();
    //
    //			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buys?user_id="+user_id+"&user_name="+user_name+
    //					"&article_id="+str1+"&goods_id="+str2+"&quantity="+str3+"",
    //
    //					new AsyncHttpResponseHandler() {
    //						@Override
    //						public void onSuccess(int arg0,String arg1) {
    //
    //							super.onSuccess(arg0, arg1);
    //							try {
    //								JSONObject jsonObject = new JSONObject(arg1);
    //								String status = jsonObject.getString("status");
    //								System.out.println("购物清单================"+arg1);
    //								String info = jsonObject.getString("info");
    //								if (status.equals("y")) {
    //									progress.CloseProgress();
    //									JSONArray jsot = jsonObject.getJSONArray("data");
    //									ShopCartBean bean = new ShopCartBean();
    //									for (int i = 0; i < jsot.length(); i++) {
    //									JSONObject obj = jsot.getJSONObject(i);
    //									bean.setId(obj.getString("id"));
    //									String id = obj.getString("id");
    //									list_id.add(id);
    //									}
    //									    str = new StringBuffer();
    //								        for(String s:list_id){
    //								        	str.append(s+",");
    //								        }
    //								        str.delete(str.lastIndexOf(","),str.length());
    //								        System.out.println("id拼接之后---------------"+str);
    //
    //
    ////									Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    //									Intent intent=new Intent(getActivity(), MyOrderConfrimActivity.class);
    //									startActivity(intent);
    //								}else {
    //									Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    //								}
    //								progress.CloseProgress();
    //							} catch (JSONException e) {
    //
    //								e.printStackTrace();
    //							}
    //
    //						}
    //						@Override
    //						public void onFailure(Throwable arg0, String arg1) {
    //
    //							System.out.println("==========================访问接口失败！");
    //							System.out.println("========================="+arg0);
    //							System.out.println("=========================="+arg1);
    //							super.onFailure(arg0, arg1);
    //						}
    //
    //
    //					}, getActivity());
    //
    //			} catch (Exception e) {
    //
    //				e.printStackTrace();
    //			}
    //	}
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = (ListAdapter) mListView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
