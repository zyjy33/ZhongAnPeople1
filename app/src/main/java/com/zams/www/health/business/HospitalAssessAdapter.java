package com.zams.www.health.business;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zams.www.R;
import com.zams.www.health.model.HealthEvaluateBean;
import com.zams.www.health.model.HealthOrder;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 * 医院大厅评价
 */

public class HospitalAssessAdapter extends CommonAdaper<HealthEvaluateBean> {
    public HospitalAssessAdapter(Context context, List list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, HealthEvaluateBean item) {
        String mobile = item.getMobile();
        if (mobile != null && mobile.length() >= 11) {
            String startMobile = mobile.substring(0, 3);
            String endMobile = mobile.substring(7);
            holder.setText(R.id.assess_phone_num, startMobile + "****" + endMobile);
        }
        String orderSubmitTime = item.getOrder_submit_time();
        if (orderSubmitTime != null) {
            orderSubmitTime = orderSubmitTime.trim();
            int endIndex = orderSubmitTime.indexOf(" ");
            String date = orderSubmitTime.substring(0, endIndex);
            holder.setText(R.id.assess_date, date);
        }
        holder.setText(R.id.assess_content, item.getEvaluate_desc());
        holder.setText(R.id.assess_flag_1, "龙床保健");
        holder.setText(R.id.assess_flag_2, "餐前血糖");
        ImageView imgView = (ImageView) holder.getView(R.id.assess_img);
        Glide.with(context)
                .load("http://p.jianke.net/article/201512/20151223222721564.jpg")
                .placeholder(R.drawable.sj_fw)//图片加载出来前，显示的图片
                .error(R.drawable.sj_fw)//图
                .into(imgView);
    }

    public void upData(List<HealthEvaluateBean> datas) {
        this.mList.clear();
        if (datas != null) {
            this.mList.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public void loadMore(List<HealthEvaluateBean> datas) {
        if (datas != null) {
            this.mList.addAll(datas);
            this.notifyDataSetChanged();
        }
    }
}
