package com.zams.www.weiget;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class YSBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mList; // 数据
    protected int mLayout; // 布局id

    public YSBaseAdapter(Context context, List<T> list, int layout) {
        this.mContext = context;
        this.mList = list;
        if(mList==null){
            mList =new ArrayList<T>();
        }

        this.mLayout = layout;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getInstance(mContext, convertView, mLayout);
        convert(holder, mList.get(position)); // 子类重写的方法，完成holder中view的初始化
        return holder.getConvertView();
    }

    /**
     * 子类必须重写的方法，通过这个方法初始化holder中的View即可
     */
    public abstract void convert(ViewHolder holder, T t);

    protected static class ViewHolder {

        private View convertView;
        private SparseArray<View> views;

        private ViewHolder(View convertView) {
            this.views = new SparseArray<View>();
            this.convertView = convertView;
            convertView.setTag(this);
        }

        public static ViewHolder getInstance(Context context, View convertView, int layout) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layout, null);
                return new ViewHolder(convertView);
            }
            return (ViewHolder) convertView.getTag(); // 重用convertView
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T findViewById(int id) {
            View view = views.get(id); // 同id的控件可重复使用，无需再次findViewById
            if (view == null) {
                view = convertView.findViewById(id);
                views.append(id, view); // 将此id的控件添加进sparseArray
            }
            return (T) view;
        }

        private View getConvertView() {
            return convertView;
        }
    }

}