package com.zams.www.health.response;

import com.zams.www.health.model.HealthOrder;
import com.zams.www.http.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class HealthOrderResponse extends BaseResponse {

    private List<HealthOrder> data;

    public List<HealthOrder> getData() {
        return data;
    }

    public void setData(List<HealthOrder> data) {
        this.data = data;
    }
}
