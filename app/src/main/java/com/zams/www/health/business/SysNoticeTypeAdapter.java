package com.zams.www.health.business;

import android.content.Context;

import com.zams.www.R;
import com.zams.www.health.model.SysNoticeTypeBean;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class SysNoticeTypeAdapter extends CommonAdaper<SysNoticeTypeBean> {
    public SysNoticeTypeAdapter(Context context, List<SysNoticeTypeBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, SysNoticeTypeBean item) {
        holder.setText(R.id.sys_notice_title, item.getTitle());
        holder.setText(R.id.sys_notice_description, item.getSubtitle());
        if (item.getImgResId() != 0) {
            holder.setImageResource(R.id.sys_notice_img, item.getImgResId());
        } else {
            holder.setImageByUrl(R.id.sys_notice_img, item.getImg_url());
        }
    }
}
