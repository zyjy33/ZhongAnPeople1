package com.zams.www.health.business;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.zams.www.R;
import com.zams.www.health.model.OneNoticeInfoBean;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class OneNoticeAdapter extends CommonAdaper<OneNoticeInfoBean> {
    public OneNoticeAdapter(Context context, List<OneNoticeInfoBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, final OneNoticeInfoBean item) {
        holder.setText(R.id.notice_one_date_tv, item.getPost_time());
        String title = item.getTitle();
        if (title != null) {
            holder.setText(R.id.notice_one_title, title.trim());
        }
        final String fTitle = title;
        holder.setText(R.id.notice_one_content, item.getContent());
        holder.getView(R.id.notice_one_info_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, fTitle, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
