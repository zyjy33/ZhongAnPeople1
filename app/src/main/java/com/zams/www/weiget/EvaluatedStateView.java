package com.zams.www.weiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.taobaohead.headview.Utils;
import com.zams.www.R;

/**
 * Created by Administrator on 2018/6/12.
 */

public class EvaluatedStateView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private ImageView[] mImageViews = new ImageView[5];
    private int mWidth;
    private int mMaxIndex;

    public EvaluatedStateView(Context context) {
        super(context);
        initView(context);
    }

    public EvaluatedStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EvaluatedStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        mWidth = Utils.dip2px(40);
        for (int i = 0; i < mImageViews.length; i++) {
            this.addView(createImageView(i));
        }
    }

    private ImageView createImageView(int i) {
        mImageViews[i] = new ImageView(mContext);
        mImageViews[i].setImageResource(R.drawable.score_selected);
        LayoutParams lp = new LayoutParams(mWidth, mWidth);
        lp.setMargins(20, 2, 20, 2);
        mImageViews[i].setLayoutParams(lp);
        mImageViews[i].setTag(i);
        mImageViews[i].setOnClickListener(this);
        return mImageViews[i];
    }


    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        mMaxIndex = index;
        for (int i = 0; i < mImageViews.length; i++) {
            if (i <= index) {
                mImageViews[i].setSelected(true);
            } else {
                mImageViews[i].setSelected(false);
            }
        }
    }

    public int getMaxIndex() {
        return mMaxIndex;
    }
}
