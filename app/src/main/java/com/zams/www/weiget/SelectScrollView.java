package com.zams.www.weiget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lglottery.www.http.HttpUtils;
import com.zams.www.R;

/**
 * Created by yunsenA on 2018/4/16.
 */

public class SelectScrollView extends ScrollView implements View.OnClickListener {
    private LinearLayout rootView;
    private onSelectItemClick mItemClick;
    private TextView mCheckView;

    public SelectScrollView(Context context) {
        super(context);
        initView();
    }

    public SelectScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SelectScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        rootView = new LinearLayout(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rootView.setOrientation(LinearLayout.VERTICAL);
        this.addView(rootView, 0, lp);
    }

    public void addItemView(int typeId, String data) {
        TextView textView = createTextView();
        textView.setText(data);
        textView.setTag(typeId);
        textView.setOnClickListener(this);
        if (rootView.getChildCount() == 0) {
            setSelectColor(textView);
            mCheckView = textView;
        } else {
            setNoSelectColor(textView);
        }
        rootView.addView(textView);
    }

    private TextView createTextView() {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(HttpUtils.dip2px(getContext(), 75), HttpUtils.dip2px(getContext(), 35));
        lp.gravity = Gravity.CENTER;
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        return tv;
    }

    public void setSelectColor(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.color999));
        Drawable drawable = getResources().getDrawable(R.drawable.orange_bg);
        tv.setBackgroundColor(getResources().getColor(R.color.white));
        drawable.setBounds(0, 0,  HttpUtils.dip2px(getContext(), 2f),HttpUtils.dip2px(getContext(), 35f));
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    public void setNoSelectColor(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.color333));
        tv.setBackgroundColor(getResources().getColor(R.color.color_f8f8fb));
        tv.setCompoundDrawables(null, null, null, null);
    }

    public void setItemClick(onSelectItemClick itemClick) {
        this.mItemClick = itemClick;
    }

    @Override
    public void onClick(View v) {

        if (mCheckView != v) {
            int count = rootView.getChildCount();
            for (int i = 0; i < count; i++) {
                View childView = rootView.getChildAt(i);
                if (childView == v) {
                    setSelectColor((TextView) childView);
                } else {
                    setNoSelectColor((TextView) childView);
                }
            }
        }

        mCheckView = (TextView) v;
        if (mItemClick != null) {
            int tagId = (int) v.getTag();
            mItemClick.onItemClick(tagId);
        }
    }

    public interface onSelectItemClick {
        public void onItemClick(int typeId);
    }

}
