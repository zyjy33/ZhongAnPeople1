package com.zams.www.health.business;

import android.content.Context;

import com.zams.www.R;
import com.zams.www.health.model.MedicalRecordBean;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class ExaminationAdapter extends CommonAdaper<MedicalRecordBean> {
    public ExaminationAdapter(Context context, List<MedicalRecordBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MedicalRecordBean item) {
        int position = holder.getPosition();
        holder.setText(R.id.serial_number, String.format("%02d", position + 1));
        holder.setText(R.id.inspect_name, item.getMedical_name());
        holder.setText(R.id.inspect_result, item.getMedical_result());
        holder.setText(R.id.inspect_range, item.getNormal_value());
        holder.setText(R.id.inspect_remark, item.getNormal_desc());
    }
}
