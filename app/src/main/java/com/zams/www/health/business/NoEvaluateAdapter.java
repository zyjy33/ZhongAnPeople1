package com.zams.www.health.business;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.Constant;
import com.zams.www.R;
import com.zams.www.health.NoEvaluatedActivity;
import com.zams.www.health.model.HealthOrder;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NoEvaluateAdapter extends CommonAdaper<HealthOrder> {
    public NoEvaluateAdapter(Context context, List list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, HealthOrder item) {
        holder.setText(R.id.order_num, "订单号：" + item.getOrder_no());
        String orderSubmitTime = item.getConfirm_time();
        if (orderSubmitTime != null) {
            orderSubmitTime = orderSubmitTime.trim();
            int endIndex = orderSubmitTime.indexOf(" ");
            String date = orderSubmitTime.substring(0, endIndex);
            holder.setText(R.id.no_evaluated_date, date);
        }
        ListView listView = (ListView) holder.getView(R.id.list_view);
        listView.setAdapter(new ExaminationAdapter(context, item.getMedical_record(), R.layout.examination_item));
        TextView view = (TextView) holder.getView(R.id.evaluated_tv);
        final HealthOrder fItem = item;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoEvaluatedActivity.class);
                intent.putExtra(Constant.ORDER_NO, fItem.getOrder_no());
                context.startActivity(intent);
            }
        });
    }

}
