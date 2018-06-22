package com.zams.www.health.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.Location;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.health.model.AddHealthOrderBean;
import com.zams.www.health.model.NoticeTokeBean;
import com.zams.www.health.model.OneNoticeInfoBean;
import com.zams.www.health.model.SysNoticeTypeBean;
import com.zams.www.health.response.AddHealthOrderResponse;
import com.zams.www.health.response.NoticeTokenResponse;
import com.zams.www.health.response.OneNticeListResponse;
import com.zams.www.health.response.SysNoticeTypeResponse;
import com.zams.www.http.BaseResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class HttpProxy {
    private static final String TAG = "HttpProxy";

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
    public static void addCarRequest(Context ctx, String url, RequestParams params, final HttpCallBack<AddHealthOrderBean> httpCallBack) {
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

    public static void getSysNoticeTypeData(Context context, String userId, final HttpCallBack<List<SysNoticeTypeBean>> callBack) {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_user_message_template_list?user_id=" + userId;
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        SysNoticeTypeResponse noticeTypeResponse = JSON.parseObject(s, SysNoticeTypeResponse.class);
                        if (noticeTypeResponse != null && Constant.YES.equals(noticeTypeResponse.getStatus())) {
                            List<SysNoticeTypeBean> data = noticeTypeResponse.getData();
                            callBack.onSuccess(data);
                        } else {
                            callBack.onError(null, "数据为空");
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        super.onFailure(throwable, s);
                        callBack.onError(null, s);
                    }
                }
                , context);
    }

    public static void getOneTypeNoticeList(Context context, String userId, String requestId, String pageIndex, final HttpCallBack<List<OneNoticeInfoBean>> callBack) {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_user_message_list?user_id=60627" +
                "&template_id=" + requestId + "&size=10&index=" + pageIndex;  // TODO: 2018/6/13  userId zyjy
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        OneNticeListResponse noticeTypeResponse = JSON.parseObject(s, OneNticeListResponse.class);
                        if (noticeTypeResponse != null && Constant.YES.equals(noticeTypeResponse.getStatus())) {
                            List<OneNoticeInfoBean> data = noticeTypeResponse.getData();
                            callBack.onSuccess(data);
                        } else {
                            callBack.onError(null, "数据为空");
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        super.onFailure(throwable, s);
                        callBack.onError(null, s);
                    }
                }
                , context);
    }


    /**
     * 消息推送
     */

    public static void postGetToken(String appId, String appSecret, final HttpCallBack<NoticeTokeBean> callBack) {
        SharedPreferences sp = Location.getInstance().getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        String userCode = sp.getString(Constant.USER_CODE, "");
        String unionId = sp.getString(Constant.UNION_ID, "");
        String openId = sp.getString(Constant.OAUTH_OPEN_ID, "");
        RequestParams params = new RequestParams();
        params.put("app_id", appId);
        params.put("app_secret", appSecret);
        params.put("code_id", "111152699");
        params.put("union_id", unionId);
        params.put("open_id", "");
        params.put("device_type", "3");
        String url = "https://ju918.com/tools/client_ajax.asmx/get_token";
        AsyncHttp.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                NoticeTokenResponse tokenResponse = JSON.parseObject(s, NoticeTokenResponse.class);
                if (tokenResponse != null && Constant.YES.equals(tokenResponse.getStatus())) {
                    callBack.onSuccess(tokenResponse.getData());
                } else {
                    callBack.onError(null, "数据为空");
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                callBack.onError(null, "数据为空");
            }
        }, Location.getInstance());


    }

    /**
     * g更新 消息用户数据
     *
     * @param sessionId
     * @param callBack
     */
    public static void postUpUserInfo(String sessionId, String accessToken, final HttpCallBack<Boolean> callBack) {
        SharedPreferences sp = Location.getInstance().getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        String companyId = sp.getString(Constant.COMPANY_ID, "");
        String groupId = sp.getString(Constant.GROUP_ID, "");
        String nickName = sp.getString(Constant.NICK_NAME, "");
        String avatarUrl = sp.getString(Constant.AVATAR, "");
        String sex = sp.getString(Constant.SEX, "");
        String userCountry = sp.getString(Constant.COUNTRY, "中国");
        String userProvince = sp.getString(Constant.PROVINCE, "");
        String userCity = sp.getString(Constant.CITY, "");
        String userArea = sp.getString(Constant.AREA, "");
        String userGender;
        if ("男".equals(sex)) {
            userGender = "1";
        } else if ("女".equals(sex)) {
            userGender = "2";
        } else {
            userGender = "3";
        }
        RequestParams params = new RequestParams();
        params.put("company_id", companyId);
        params.put("group_id", groupId);
        params.put("role_id", "");
        params.put("nick_name", nickName);
        params.put("avatar_url", avatarUrl);
        params.put("user_gender", userGender);
        params.put("user_country", userCountry);
        params.put("user_province", userProvince);
        params.put("user_city", userCity);
        params.put("user_area", userArea);
        params.put("device_type", "3");
        params.put("device_name", "android");
        params.put("session_id", sessionId);
        String url = "https://ju918.com/tools/client_ajax.asmx/user_refresh?access_token=" + accessToken;
        AsyncHttp.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                BaseResponse response = JSON.parseObject(s, BaseResponse.class);
                if (response != null && Constant.YES.equals(response.getStatus())) {
                    callBack.onSuccess(true);
                } else {
                    callBack.onError(null, "失败");
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                callBack.onError(null, "失败");

            }
        }, Location.getInstance());

    }

}
