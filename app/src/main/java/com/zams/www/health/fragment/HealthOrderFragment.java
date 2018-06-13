package com.zams.www.health.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyOrderActivity;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.hengyushop.entity.MyOrderData;
import com.hengyushop.entity.OrderBean;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.BaseFragment;
import com.zams.www.R;
import com.zams.www.health.business.HealthOrderAdapter;
import com.zams.www.health.model.HealthOrder;
import com.zams.www.health.response.HealthOrderResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/6/8.
 */

public class HealthOrderFragment extends BaseFragment implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
    private ListView orderListView;
    private PullToRefreshView rightRefreshView;
    private int mPageIndex = 1;
    private boolean mHasMore = true;
    private boolean mIsLoadMore = false;
    private String mUserId;
    private ArrayList<HealthOrder> mDatas;
    private HealthOrderAdapter mAdapter;
    private String mOrderNo;
    private static MyHandler sHandler;
    private DialogProgress progress;
    private String mLoginSign;
    private String mUserName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_health_order;
    }

    @Override
    protected void initView() {
        orderListView = (ListView) rootView.findViewById(R.id.order_info_lv);
        rightRefreshView = (PullToRefreshView) rootView.findViewById(R.id.refresh_right);
        progress = new DialogProgress(getActivity());
    }

    @Override
    protected void initData() {
        sHandler = new MyHandler(this);
        SharedPreferences sp = getActivity().getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
        mLoginSign = sp.getString("login_sign", "");
        mUserName = sp.getString("user_name", "");
        mDatas = new ArrayList<>();
        mAdapter = new HealthOrderAdapter(sHandler, getActivity(), mDatas, R.layout.health_order_itme);
        orderListView.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_medical_order_list?pageSize=10&pageIndex=" + mPageIndex + "&user_id=" + mUserId;
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String data) {
                super.onSuccess(arg0, data);
                HealthOrderResponse list = JSON.parseObject(data, HealthOrderResponse.class);
                if (list != null && Constant.YES.equals(list.getStatus())) {
                    List<HealthOrder> datas = list.getData();
                    if (mIsLoadMore) {
                        boolean hasMore = mAdapter.addData(datas);
                        if (!hasMore) {
                            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mAdapter.upData(datas);
                    }
                }
            }

            @Override
            public void onFailure(Throwable arg0, String arg1) {
                super.onFailure(arg0, arg1);
            }

        }, getActivity());
    }

    @Override
    protected void initListener() {
        rightRefreshView.setOnFooterRefreshListener(this);
        rightRefreshView.setOnHeaderRefreshListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_COMPLETE_ORDER) {
                mIsLoadMore = true;
                mPageIndex = 1;
                mHasMore = true;
                requestData();
            }
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        switch (view.getId()) {
            case R.id.refresh_right:
                rightRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHasMore = true;
                        mIsLoadMore = false;
                        mPageIndex = 1;
                        requestData();
                        rightRefreshView.onHeaderRefreshComplete();
                    }
                }, 1000);
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        switch (view.getId()) {
            case R.id.refresh_right:
                rightRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHasMore = true;
                        mIsLoadMore = true;
                        mPageIndex++;
                        requestData();
                        rightRefreshView.onFooterRefreshComplete();
                    }
                }, 1000);
                break;
        }
    }

    protected void dialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("是否确定删除订单?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                fukuanok3();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 申请退款
     */
    protected void dialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("是否确定申请退款?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                userloginqm();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 获取登录签名
     */
    private void userloginqm() {
        String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username=" + mUserName + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    JSONObject obj = object.getJSONObject("data");
                    if (status.equals("y")) {
                        String login_sign = obj.getString("login_sign");
                        getKuiKuan(login_sign);

                    } else {
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, getActivity());


    }

    /**
     * 退款
     *
     * @param login_sign
     * @param
     */
    private void getKuiKuan(String login_sign) {
        String strUrlone = RealmName.REALM_NAME_LL + "/order_refund?user_id=" + mUserId + "&trade_no=" + mOrderNo + "&sign=" + login_sign + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    if ("y".equals(status)) {
                        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                        requestData();
                    } else {
                        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, getActivity());


    }

    public void fukuanok3() {
        progress.CreateProgress();
        AsyncHttp.get(RealmName.REALM_NAME_LL
                        + "/delete_order?user_id=" + mUserId + "&user_name=" + mUserName + "" +
                        "&trade_no=" + mOrderNo + "&sign=" + mLoginSign + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            System.out.println("取消订单=================================" + arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                                requestData();
                            } else {
                                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progress.CloseProgress();
                    }

                }, getActivity());
    }

    private static class MyHandler extends Handler {
        WeakReference<HealthOrderFragment> weakReference;

        public MyHandler(HealthOrderFragment fragment) {
            this.weakReference = new WeakReference<HealthOrderFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            HealthOrderFragment fragment = weakReference.get();
            super.handleMessage(msg);
            if (fragment != null && !fragment.isDetached()) {
                switch (msg.what) {
                    case Constant.DELETE:
                        fragment.mOrderNo = (String) msg.obj;
                        fragment.dialog3();
                        break;
                    case Constant.COMPLETE_ORDER:

                        break;
                    case Constant.REFUND:
                        fragment.mOrderNo = (String) msg.obj;
                        fragment.dialog1();
                        break;
                }

            }
        }
    }
}
