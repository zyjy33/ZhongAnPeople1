package com.zams.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 关于刮刮卡的滑动操作
 *
 * @author cloor
 *
 */
@SuppressLint("HandlerLeak")
public class GuaGuaUtil extends TextView {
	private int W;
	private int H;
	private static final int MV = 1;
	private static final int SW = 50;
	private static final int MC = 0xFFD6D6D6;
	private int mWidth;
	private int mHeight;
	private int mMaskColor;
	private int mStrokeWidth;
	private float mX;
	private float mY;
	private boolean mRun;
	private boolean caculate;
	private Path mPath;
	private Paint mPaint;
	private Paint mBitmapPaint;
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private int[] mPixels;
	private Thread mThread;
	private onWipeListener mWipeListener;
	private Handler parentHandler;

	public void clear() {
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}
	}

	public GuaGuaUtil(Context context, int W, int H, Handler parentHandler) {
		super(context);
		this.W = W;
		this.H = H;
		this.parentHandler = parentHandler;
		init(context);
	}

	public GuaGuaUtil(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mMaskColor = MC;
		mStrokeWidth = SW;
		mPath = new Path();
		mBitmapPaint = new Paint();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);// 抗锯齿
		mPaint.setDither(true);// 递色
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND); // 前圆角
		mPaint.setStrokeCap(Paint.Cap.ROUND); // 后圆角
		mPaint.setStrokeWidth(mStrokeWidth); // 笔宽
		mBitmap = Bitmap.createBitmap(W, H, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mCanvas.drawColor(mMaskColor);
		mRun = true;
		mThread = new Thread(mRunnable);
		mThread.start();
		setGravity(Gravity.CENTER);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mCanvas.drawPath(mPath, mPaint);
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		if (w > 0 && h > 0) {
			mWidth = W;
			mHeight = H;
		}
	}

	public void reset() {
		mPath.reset();
		mCanvas.drawPaint(mPaint);
		mCanvas.drawColor(mMaskColor);
		invalidate();
	}

	public void setOnWipeListener(onWipeListener listerer) {
		this.mWipeListener = listerer;
	}

	public void setStrokeWidth(int width) {
		this.mStrokeWidth = width;
		mPaint.setStrokeWidth(width);
	}

	public void setMaskColor(int color) {
		this.mMaskColor = color;
		reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean invalidate = false;
		boolean consume = false;
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				consume = true;
				touchDown(event);
				break;
			case MotionEvent.ACTION_MOVE:
				consume = true;
				invalidate = touchMove(event);
				break;
			case MotionEvent.ACTION_UP:
				consume = true;
				touchUp(event);
				break;
		}
		if (invalidate) {
			invalidate();
		}
		if (consume) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	// 手指点下屏幕时调用
	private void touchDown(MotionEvent event) {
		caculate = false;
		// 重置绘制路线，即隐藏之前绘制的轨迹
		mPath.reset();
		float x = event.getX();
		float y = event.getY();
		mX = x;
		mY = y;
		// mPath绘制的绘制起点
		mPath.moveTo(x, y);
	}

	// 手指在屏幕上滑动时调用
	private boolean touchMove(MotionEvent event) {
		caculate = false;
		final float x = event.getX();
		final float y = event.getY();
		final float previousX = mX;
		final float previousY = mY;
		// 设置贝塞尔曲线的操作点为起点和终点的一半
		float cX = (x + previousX) / 2;
		float cY = (y + previousY) / 2;
		final float dx = Math.abs(x - previousX);
		final float dy = Math.abs(y - previousY);
		boolean move = false;
		if (dx >= MV || dy >= MV) {
			// 二次贝塞尔，实现平滑曲线；cX, cY为操作点 x,y为终点
			mPath.quadTo(cX, cY, x, y);
			// 第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
			mX = x;
			mY = y;
			move = true;
		}
		return move;
	}

	private void touchUp(MotionEvent event) {
		caculate = true;
		mRun = true;
	}

	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			while (mRun) {
				SystemClock.sleep(100);
				// 收到计算命令，立即开始计算
				if (caculate) {
					caculate = false;
					int w = mWidth;
					int h = mHeight;
					float wipeArea = 0;
					float totalArea = w * h;
					// 计算耗时100毫秒左右
					Bitmap bitmap = mBitmap;
					if (mPixels == null) {
						mPixels = new int[w * h];
					}
					bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							int index = i + j * w;
							if (mPixels[index] == 0) {
								wipeArea++;
							}
						}
					}
					if (wipeArea > 0 && totalArea > 0) {
						int percent = (int) (wipeArea * 100 / totalArea);
						if (percent >= 5) {
							// mHandler的顺序
							Message msg = mHandler.obtainMessage();
							msg.what = 0;
							parentHandler.sendEmptyMessage(-1);
							mHandler.sendMessage(msg);
						} else {
							Message msg = mHandler.obtainMessage();
							msg.what = 1;
							msg.arg1 = percent;
							mHandler.sendMessage(msg);
						}
					}
				}
			}
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					mRun = false;
					GuaGuaUtil.this.setEnabled(false);
					mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
					invalidate();
					if (mWipeListener != null) {
						// -1代表完成了抽奖动作
						mWipeListener.onWipe(-1);
					}
					break;
				case 1:
					if (mWipeListener != null) {
						int percent = msg.arg1;
						mWipeListener.onWipe(percent);
					}
					break;
				default:
					break;
			}
		};
	};

	public interface onWipeListener {

		public void onWipe(int percent);

	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mRun = false;
	}
}
