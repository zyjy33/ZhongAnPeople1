package com.zams.www.health.response;

import com.zams.www.health.model.HealthEvaluateBean;
import com.zams.www.http.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class HealthEvaluateResponse extends BaseResponse{

    private List<HealthEvaluateBean> data;

    public List<HealthEvaluateBean> getData() {
        return data;
    }

    public void setData(List<HealthEvaluateBean> data) {
        this.data = data;
    }

}
