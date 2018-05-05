package com.zams.www.health.business;

import android.content.Context;

import com.zams.www.R;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NoEvaluateAdapter extends CommonAdaper<String> {
    public NoEvaluateAdapter(Context context, List list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        holder.setText(R.id.order_num, item);
    }

    public void upData(List<String> datas) {
        if (datas != null) {
            this.mList.clear();
            this.mList.addAll(datas);
        }
    }
}
