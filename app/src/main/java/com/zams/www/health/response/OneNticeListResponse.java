package com.zams.www.health.response;

import com.zams.www.health.model.OneNoticeInfoBean;
import com.zams.www.http.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class OneNticeListResponse extends BaseResponse {

    private List<OneNoticeInfoBean> data;

    public List<OneNoticeInfoBean> getData() {
        return data;
    }

    public void setData(List<OneNoticeInfoBean> data) {
        this.data = data;
    }

}
