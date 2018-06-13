package com.zams.www.health.business;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zams.www.R;
import com.zams.www.health.model.OneNoticeInfoBean;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class TwoNoticeAdapter extends CommonAdaper<OneNoticeInfoBean> {
    public TwoNoticeAdapter(Context context, List<OneNoticeInfoBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, final OneNoticeInfoBean item) {
        holder.setText(R.id.notice_two_date_tv, item.getPost_time());
        String title = item.getTitle();
        if (title != null) {
            holder.setText(R.id.notice_two_title, title.trim());
        }
        final String fTitle = title;
        holder.setText(R.id.notice_two_content, item.getContent());
        ImageView view = (ImageView) holder.getView(R.id.notice_two_img);
        Glide.with(context)
                .load(item.getImg_url())
                .into(view);
        holder.getView(R.id.notice_two_info_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, fTitle, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
