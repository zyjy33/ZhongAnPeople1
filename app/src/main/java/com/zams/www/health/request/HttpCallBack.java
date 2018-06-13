package com.zams.www.health.request;

import org.jsoup.Connection;

/**
 * Created by Administrator on 2018/6/11.
 */

public interface HttpCallBack<T> {
    public void onSuccess(T responseData);

    public void onError(Connection.Request request, String e);
}
