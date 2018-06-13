package com.zams.www.health.response;

import com.zams.www.health.model.SysNoticeTypeBean;
import com.zams.www.http.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class SysNoticeTypeResponse extends BaseResponse {

    private List<SysNoticeTypeBean> data;

    public List<SysNoticeTypeBean> getData() {
        return data;
    }

    public void setData(List<SysNoticeTypeBean> data) {
        this.data = data;
    }

}
