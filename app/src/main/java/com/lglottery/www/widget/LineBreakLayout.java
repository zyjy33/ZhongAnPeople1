package com.lglottery.www.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LineBreakLayout extends ViewGroup {
	/**
	 * 不规则布局
	 */

	private final static String TAG = "LineBreakLayout";
	public int ROWCOUNT = 0;
	private final static int WIDTH_MARGIN = 0;
	private final static int HEIGHT_MARGIN = 0;

	public LineBreakLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LineBreakLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Log.d(TAG, "widthMeasureSpec = " + widthMeasureSpec+
		// " heightMeasureSpec" + heightMeasureSpec);
		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			// measure
			child.measure(MeasureSpec.UNSPECIFIED, 40);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public int getRow() {
		return ROWCOUNT;

	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// Log.d(TAG, "changed = " + arg0 + " left = " + arg1 + " top = " +
		// arg2+ " right = " + arg3 + " botom = " + arg4);
		final int count = getChildCount();
		int row = 0;// which row lay you view relative to parent
		int lengthX = arg1; // right position of child relative to parent
		int lengthY = arg2; // bottom position of child relative to parent
		int height = 0;
		for (int i = 0; i < count; i++) {

			final View child = this.getChildAt(i);
			child.setPadding(15, 10, 15, 10);
			int width = child.getMeasuredWidth();
			height = child.getMeasuredHeight();
			lengthX += width + WIDTH_MARGIN;
			lengthY = row * (height + HEIGHT_MARGIN) + HEIGHT_MARGIN + height
					+ arg2;
			// if it can't drawing on a same line , skip to next line
			if (lengthX > arg3) {
				lengthX = width + WIDTH_MARGIN + arg1;
				row++;
				lengthY = row * (height + HEIGHT_MARGIN) + HEIGHT_MARGIN
						+ height + arg2;

			}

			// child.layout(lengthX - width, lengthY - height, lengthX,
			// lengthY);
			System.out
					.println(lengthX - width + "--" + lengthX + "---" + width);
			child.layout(lengthX - width - 63, lengthY - height, lengthX - 63,
					lengthY);
		}

		// this.setLayoutParams(new
		// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ROWCOUNT =
		// (row+1)*height));

	}

}