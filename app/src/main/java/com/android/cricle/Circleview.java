package com.android.cricle;


import com.zams.www.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class Circleview extends ImageView {

	private Bitmap mHourBitmap;
	private Thread thread;
	private boolean bInitComplete = false;
	private boolean stopRoter = true;
	float Angel = 0.0f;
	Matrix matx = new Matrix();
	private Handler handler;
	/**
	 * 中奖各种计算参数 maxAngel=转动到中奖的角度
	 */
	float maxAngel = 0.0f;

	/**
	 * 屏幕的宽度
	 */
	int screnWidth = 0;

	/**
	 * 初始抽奖滑动组件
	 * @param context
	 * @param width
	 * 屏幕宽度
	 */
	private float x,y;
	public Circleview(Context context, int width,float x,float y,Handler handler) {
		super(context);
		this.handler = handler;
		this.screnWidth = width;
		this.x = x;
		this.y = y;
		init();
		// new Thread(this).start();
		run();

	}

	private void run() {
		thread = new Thread() {
			@Override
			public void run() {
				super.run();

				try {

					while (true) {
						if (!isStopRoter()) {
							if (maxAngel != 0 && Angel >= maxAngel) {
								setStopRoter(true);
								maxAngel = 0.0f;
							} else {
								if (maxAngel - Angel < 360)
									setRotate_degree(Angel += 8);
								else
									setRotate_degree(Angel += 8);
								postInvalidate();
								Thread.sleep(20);
							}
						} else {
							//							setRotate_degree(0);

							handler.sendEmptyMessage(1);
							break;
						}
					}

				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
		};

		Message msg = new Message();
		msg.what = 3;
		msg.obj = thread;
		handler.sendMessage(msg);
	}

	public void init() {
		mHourBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.share_lottery_pointer);
		bInitComplete = true;

	}

	public void setRotate_degree(float degree) {
		Angel = degree;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		matx.reset();
		canvas.drawColor(Color.TRANSPARENT);

		if (!bInitComplete) {
			return;
		}
		Paint localPaint = new Paint();
		// 设置取消锯齿效果
		localPaint.setAntiAlias(true);
		localPaint.setFilterBitmap(true);
		/**
		 * 初始中间指针
		 */
		//		matx.setTranslate(DensityUtil.dip2px(getContext(), 300) / 2,
		//				DensityUtil.dip2px(getContext(), 300) / 4);
		matx.setTranslate(x+mHourBitmap.getHeight()/2+mHourBitmap.getWidth()/2,
				y-mHourBitmap.getWidth()/2);
		// matx.setTranslate(this.screnWidth/2-mHourBitmap.getWidth()/2,
		// DensityUtil.dip2px(getContext(),
		// 300)/2-mHourBitmap.getHeight()+DensityUtil.dip2px(getContext(), 20));
		/**
		 * 设置绕点旋转
		 */
		// matx.preRotate(Angel, mHourBitmap.getWidth() /
		// 2,mHourBitmap.getHeight() * 4 / 5);
		matx.preRotate(Angel, mHourBitmap.getWidth() / 2,
				mHourBitmap.getHeight() * 4 / 5);

		canvas.drawBitmap(mHourBitmap, matx, localPaint);
	}

	/**
	 * 获取当前的角度，并设置停止角度
	 *
	 * @param
	 *            位置
	 * @return
	 */
	public void setReset(int place) {
		maxAngel = 0;
	}

	public void setStopPlace(int place) {
		getRoterByPlace(place);
	}

	/**
	 * 顺时针旋转 1 = 330-30 2 = 30-90 3 = 90-150 4 = 150-210 5 = 210-270 6 = 270-330
	 *
	 * @param place
	 * @return
	 */
	void getRoterByPlace(int place) {
		float roter = getRoteCenter(place);
		float currentRoter = getCurrentRoter();

		// 如果当前的角度小于位置的角度，则表示需要多转多少角度
		float difRoter = currentRoter - roter;
		// 固定三圈360*3，后在加上当前的角度差
		maxAngel = Angel + 360 * 3 + 360 - difRoter;
	}

	/**
	 * 得到奖项位置的角度 -转盘360度 根据奖项取各个奖项的平均值，在设置指定各个奖项的中间点
	 *
	 * @param place
	 * @return
	 */
	float getRoteCenter(int place) {
		float roter = 0.0f;
		switch (place) {
			case 0:
				roter = 0|360;
				break;
			case 1:
				roter = 36;
				break;
			case 2:
				roter = 72;
				break;
			case 3:
				roter = 108;
				break;
			case 4:
				roter = 144;
				break;
			case 5:
				roter = 180;
				break;
			case 6:
				roter = 216;
				break;
			case 7:
				roter = 252;
				break;
			case 8:
				roter = 288;
				break;
			case 9:
				roter = 324;
				break;
			default:
				break;
		}
		return roter;
		//		return 90;
	}

	/**
	 * 得到转动的实际角度--换算角度值
	 *
	 * @return
	 */
	float getCurrentRoter() {
		int current = (int) Angel / 360;
		if (0 == current)
			return Angel;
		float roter = Angel - 360 * current;
		return roter;
	}

	public boolean isStopRoter() {
		return stopRoter;
	}

	public void setStopRoter(boolean stopRoter) {
		if (stopRoter) {
			thread = null;
		}
		this.stopRoter = stopRoter;
	}
}