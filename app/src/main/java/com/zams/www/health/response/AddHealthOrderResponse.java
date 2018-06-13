package com.zams.www.health.response;

import com.zams.www.health.model.AddHealthOrderBean;
import com.zams.www.http.BaseResponse;

/**
 * Created by Administrator on 2018/6/12.
 */

public class AddHealthOrderResponse extends BaseResponse{

    /**
     * data : {"order_no":"M180612164945494539","trade_no":"T180612164945494539"}
     */

    private AddHealthOrderBean data;

    public AddHealthOrderBean getData() {
        return data;
    }

    public void setData(AddHealthOrderBean data) {
        this.data = data;
    }

}
