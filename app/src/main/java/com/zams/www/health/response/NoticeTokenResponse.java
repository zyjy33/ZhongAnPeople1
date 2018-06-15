package com.zams.www.health.response;


import com.zams.www.health.model.NoticeTokeBean;
import com.zams.www.http.BaseResponse;

/**
 * Created by Administrator on 2018/6/14.
 */

public class NoticeTokenResponse extends BaseResponse{

    /**
     * data : {"token_code":"success","device_token":"8986833B4CF3F6FF323E20B9BF34C519B46119FC9DEC15F4"}
     */

    private NoticeTokeBean data;

    public NoticeTokeBean getData() {
        return data;
    }

    public void setData(NoticeTokeBean data) {
        this.data = data;
    }

}
