package com.zams.www.health.business;

import android.content.Context;

import com.zams.www.R;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 * 医院大厅评价
 */

public class HospitalAssessAdapter extends CommonAdaper {
    public HospitalAssessAdapter(Context context, List list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, Object item) {
        holder.setText(R.id.assess_phone_num, "电话号码");
        holder.setText(R.id.assess_date, "日期");
        holder.setText(R.id.assess_content, "内容描述");
        holder.setText(R.id.assess_flag_1, "龙床保健");
        holder.setText(R.id.assess_flag_2, "餐前血糖");
        holder.setImageByUrl(R.id.assess_img, "http://img.zcool.cn/community/0181845834f4eda8012060c8c95113.JPG@1280w_1l_2o_100sh.png");
    }

    public void upData(List<String> datas) {
        if (datas != null) {
            this.mList.clear();
            this.mList.addAll(datas);
            this.mList.addAll(datas);
            this.notifyDataSetChanged();
        }
    }

    public void loadMore(List<String> datas) {
        if (datas != null) {
            this.mList.addAll(datas);
            this.notifyDataSetChanged();
        }
    }
}
