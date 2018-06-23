package com.lglottery.www.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.cricle.DensityUtil;
import com.guanggao.G;
import com.zams.www.R;

/**
 * Created by Administrator on 2018/6/23.
 */

public class GoodsNumControlView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private EditText goodsCountEdt;
    private View leftView;
    private View rightView;


    public GoodsNumControlView(Context context) {
        super(context);
        initView(context);
    }

    public GoodsNumControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GoodsNumControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.goods_num_view, this);
        leftView = view.findViewById(R.id.reduce_img);
        rightView = view.findViewById(R.id.add_img);
        goodsCountEdt = (EditText) view.findViewById(R.id.goods_count_edt);
        leftView.setOnClickListener(this);
        rightView.setOnClickListener(this);


    }

    private static final String TAG = "GoodsNumControlView";

    @Override
    public void onClick(final View v) {
        int visibility = leftView.getVisibility();
        String numStr = goodsCountEdt.getText().toString();
        switch (v.getId()) {
            case R.id.reduce_img: //减
                int numR = Integer.parseInt(numStr);
                numR -= 1;
                if (visibility == VISIBLE && numR == 0) {
                    startReduceAnimator();
                }
                goodsCountEdt.setText(String.valueOf(numR));
                break;
            case R.id.add_img: //加
                if (visibility == GONE) {
                    leftView.setVisibility(VISIBLE);
                    goodsCountEdt.setText("1");
                    startAddAnimator();
                } else {
                    if (TextUtils.isEmpty(numStr)) {
                        goodsCountEdt.setText("1");
                    } else {
                        int num = Integer.parseInt(numStr);
                        num += 1;
                        goodsCountEdt.setText(String.valueOf(num));
                    }
                }
                break;
        }
    }

    private void startReduceAnimator() {
        int edtWidth = DensityUtil.dip2px(mContext, 60);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(edtWidth, 0);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = goodsCountEdt.getLayoutParams();
                lp.width = (int) value;
                goodsCountEdt.setLayoutParams(lp);
                if (value == 0) {
                    goodsCountEdt.setVisibility(GONE);
                    leftView.setVisibility(GONE);
                }
            }
        });
        valueAnimator.start();
    }

    private void startAddAnimator() {
        goodsCountEdt.setVisibility(VISIBLE);
        int edtWidth = DensityUtil.dip2px(mContext, 60);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, edtWidth);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = goodsCountEdt.getLayoutParams();
                lp.width = (int) value;
                goodsCountEdt.setLayoutParams(lp);
            }
        });
        valueAnimator.start();
    }

}
