package com.android.hengyu.pub;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.SpListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;

import java.util.ArrayList;
import java.util.List;

public class MySpListAdapter extends BaseAdapter {

    private ArrayList<SpListData> mLsts;
    private Context context;
    private ImageLoader loader;
    public static AQuery query;
    public static boolean type = false;

    public MySpListAdapter(ArrayList<SpListData> lists,
                           Context context, ImageLoader loader) {

        this.context = context;
        this.mLsts = lists;
        this.loader = loader;
        query = new AQuery(context);
    }

    /**
     * 更新数据
     *
     * @param lists
     */
    public void upData(ArrayList<SpListData> lists) {
        if (lists != null) {
            this.mLsts.clear();
            this.mLsts.addAll(lists);
            this.notifyDataSetChanged();
        }
    }

    public void putData(ArrayList<SpListData> lists) {
        this.mLsts = lists;
        this.notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param lists
     */
    public void loadMoreData(List<SpListData> lists) {
        if (this.mLsts != null) {
            this.mLsts.addAll(lists);
            this.notifyDataSetChanged();
        }
    }

    public int getCount() {
        return mLsts == null ? 0 : mLsts.size();
    }

    public Object getItem(int position) {
        return mLsts.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LinearLayout.inflate(context, R.layout.listitem_goods_time, null);
        }
        LinearLayout ll_kedikou = (LinearLayout) convertView.findViewById(R.id.ll_kedikou);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
        TextView tv_rePrice = (TextView) convertView.findViewById(R.id.tv_hengyu_money);
        TextView tv_maPrice = (TextView) convertView.findViewById(R.id.tv_market_money);
        TextView tv_hongbao = (TextView) convertView.findViewById(R.id.tv_hongbao);
        ImageView img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
        View vi_ = (View) convertView.findViewById(R.id.vi_);
        vi_.setVisibility(View.GONE);
        tv_maPrice.getPaint().setFlags(
                Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

        SpListData data = mLsts.get(position);
        tv_name.setText(data.title);
        tv_rePrice.setText("￥" + data.getSell_price());
        tv_maPrice.setText("￥" + data.getMarket_price());
        tv_hongbao.setText("￥" + data.getCashing_packet());
        if (data.getCashing_packet().equals("0.0")) {
            ll_kedikou.setVisibility(View.GONE);
        } else {
            ll_kedikou.setVisibility(View.VISIBLE);
        }
        query.id(img_ware).image(RealmName.REALM_NAME_HTTP + data.img_url);
        type = true;
        return convertView;
    }
}
