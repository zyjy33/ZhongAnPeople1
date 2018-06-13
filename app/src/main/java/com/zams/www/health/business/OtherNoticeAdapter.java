package com.zams.www.health.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zams.www.R;
import com.zams.www.health.model.OneNoticeInfoBean;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class OtherNoticeAdapter extends BaseAdapter {

    private List<OneNoticeInfoBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public OtherNoticeAdapter(List<OneNoticeInfoBean> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public OneNoticeInfoBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final OneNoticeInfoBean data = mDatas.get(position);
        switch (getItemViewType(position)) {
            case 1:
            case 4:
            case 5:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.notice_one_item, parent, false);
                    holder = new ViewHolder();
                    holder.dateTv = (TextView) convertView.findViewById(R.id.notice_one_date_tv);
                    holder.titleTv = (TextView) convertView.findViewById(R.id.notice_one_title);
                    holder.moreLayout = (LinearLayout) convertView.findViewById(R.id.notice_one_info_layout);
                    holder.contentTv = (TextView) convertView.findViewById(R.id.notice_one_content);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.contentTv.setText(data.getContent());
                holder.moreLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, data.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
            case 3:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.notice_two_item, parent, false);
                    holder = new ViewHolder();
                    holder.dateTv = (TextView) convertView.findViewById(R.id.notice_two_date_tv);
                    holder.titleTv = (TextView) convertView.findViewById(R.id.notice_two_title);
                    holder.moreInfoTv = (TextView) convertView.findViewById(R.id.notice_two_info_layout);
                    holder.contentTv = (TextView) convertView.findViewById(R.id.notice_two_content);
                    holder.imgView = (ImageView) convertView.findViewById(R.id.notice_two_img);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.contentTv.setText(data.getContent());
                Glide.with(mContext)
                        .load(data.getImg_url())
                        .into(holder.imgView);
                holder.moreInfoTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, data.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 6:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.notice_three_item, parent, false);
                    holder = new ViewHolder();
                    holder.dateTv = (TextView) convertView.findViewById(R.id.notice_three_date_tv);
                    holder.titleTv = (TextView) convertView.findViewById(R.id.notice_three_title);
                    holder.contentTv = (TextView) convertView.findViewById(R.id.notice_three_content);
                    holder.moreLayout = (LinearLayout) convertView.findViewById(R.id.notice_three_info_layout);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.contentTv.setText(data.getContent());
                holder.moreLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, data.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        holder.dateTv.setText(data.getUpdate_time());
        holder.titleTv.setText(data.getTitle());
        return convertView;

    }

    @Override
    public int getItemViewType(int position) {
        int datatypeId = mDatas.get(position).getDatatype_id();
        return datatypeId;
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }

    public boolean addData(List<OneNoticeInfoBean> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void upData(List<OneNoticeInfoBean> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView dateTv;
        private TextView titleTv;
        private TextView contentTv;
        private ImageView imgView;
        private TextView moreInfoTv;
        private LinearLayout moreLayout;
    }


}
