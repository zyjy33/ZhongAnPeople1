package com.zams.www.http;

import com.alibaba.fastjson.annotation.JSONField;
import com.zams.www.http.model.RedPackageData;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class RedPacketResponse extends BaseResponse {

    @JSONField(name = "data")
    private List<RedPackageData> data;

    public List<RedPackageData> getData() {
        return data;
    }

    public void setData(List<RedPackageData> data) {
        this.data = data;
    }


}
