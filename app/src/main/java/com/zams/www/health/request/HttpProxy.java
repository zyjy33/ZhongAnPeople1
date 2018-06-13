package com.zams.www.health.request;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.OrderBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.health.DescriptionActivity;
import com.zams.www.health.model.AddHealthOrderBean;
import com.zams.www.health.response.AddHealthOrderResponse;
import com.zams.www.health.response.HealthOrderResponse;
import com.zams.www.http.BaseResponse;

/**
 * Created by Administrator on 2018/6/11.
 */

public class HttpProxy {

    /**
     * 提交评价
     *
     * @param context
     * @param mUserId
     * @param httpCallBack
     */
    public static void submitEvaluatedData(Context context, String mUserId,
                                           String orderNo, String evaluateStatus,
                                           String evaluateDesc, String waiterDtatus,
                                           String waiterDesc, final HttpCallBack<Boolean> httpCallBack) {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/submit_medical_evaluate";
        RequestParams params = new RequestParams();
        params.put("user_id", mUserId);
        params.put("order_no", orderNo);
        params.put("evaluate_status", evaluateStatus);
        params.put("evaluate_desc", evaluateDesc);
        params.put("waiter_status", waiterDtatus);
        params.put("waiter_desc", waiterDesc);
        AsyncHttp.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                BaseResponse baseResponse = JSON.parseObject(s, BaseResponse.class);
                String status = baseResponse.getStatus();
                if (Constant.YES.equals(status)) {
                    httpCallBack.onSuccess(true);
                } else {
                    httpCallBack.onSuccess(false);
                }

            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                httpCallBack.onError(null, s);
            }
        }, context);
    }

    /**
     * 添加订单
     *
     * @param ctx
     * @param url
     * @param httpCallBack
     */
    public static void addCarRequest(Context ctx, String url,RequestParams params, final HttpCallBack<AddHealthOrderBean> httpCallBack) {
        AsyncHttp.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("zyjy", "onSuccess: " + s);
                AddHealthOrderResponse healthOrderResponse = JSON.parseObject(s, AddHealthOrderResponse.class);
                if (healthOrderResponse != null && Constant.YES.equals(healthOrderResponse.getStatus())) {
                    AddHealthOrderBean data = healthOrderResponse.getData();
                    if (data != null) {
                        httpCallBack.onSuccess(data);
                    } else {
                        httpCallBack.onError(null, "数据为空");
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                httpCallBack.onError(null, s);
            }

        }, ctx);
    }

//    public void getHealthOrder(String url,){
//        AsyncHttp.get();
//    }
}
