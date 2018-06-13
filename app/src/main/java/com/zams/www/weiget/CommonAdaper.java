package com.zams.www.weiget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 */

public abstract class CommonAdaper<T> extends BaseAdapter {
    protected Context context;
    protected List<T> mList;
    private LayoutInflater inflater;
    private int itemLayoutId;


    public CommonAdaper(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.mList = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = getViewHolder(position, convertView, parent);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T item);


    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(context, convertView, parent, itemLayoutId, position);
    }

    public boolean addData(List<T> datas) {
        if (datas != null && datas.size() > 0) {
            mList.addAll(datas);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void upData(List<T> datas) {
        mList.clear();
        if (datas != null) {
            mList.addAll(datas);
        }
        this.notifyDataSetChanged();
    }
}
