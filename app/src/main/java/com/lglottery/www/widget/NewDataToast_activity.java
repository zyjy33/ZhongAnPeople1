package com.lglottery.www.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zams.www.R;

/**
 * @author
 * @version 1.0
 * @created 2012-8-30
 */
@SuppressLint("ViewConstructor")
public class NewDataToast_activity extends Toast {
	private static NewDataToast_activity result;
	@SuppressWarnings("unused")
	private MediaPlayer mPlayer;
	@SuppressWarnings("unused")
	private boolean isSound;
	public static final int PHONE = 1;
	public static final int ORDER_NULL = 2;
	public static final int NULL = 0;
	public static final int ORDER_IN = 3;

	public NewDataToast_activity(Context context, boolean isSound, int rawid) {
		super(context);
		this.isSound = isSound;
		// switch (rawid) {
		// case NULL:
		// mPlayer = MediaPlayer.create(context, R.raw.phone);
		// break;
		// case PHONE:
		// mPlayer = MediaPlayer.create(context, R.raw.phone);
		// break;
		// case ORDER_NULL:
		// mPlayer = MediaPlayer.create(context, R.raw.order_null);
		// break;
		// case ORDER_IN:
		// mPlayer = MediaPlayer.create(context, R.raw.order_in);
		// break;
		// default:
		// break;
		// }
		// mPlayer.setOnCompletionListener(new
		// MediaPlayer.OnCompletionListener(){
		// public void onCompletion(MediaPlayer mp) {
		// mp.release();
		// }
		// });
	}

	@Override
	public void show() {
		super.show();
		// if(isSound){
		// mPlayer.start();
		// }
	}

	/**
	 * 璁剧疆鏄惁鎾斁澹伴煶
	 */
	public void setIsSound(boolean isSound) {
		this.isSound = isSound;
	}

	/**
	 * 鑾峰彇鎺т欢瀹炰緥
	 *
	 * @param context
	 * @param text
	 *            鎻愮ず娑堟伅
	 * @param isSound
	 *            鏄惁鎾斁澹伴煶
	 * @return
	 */
	public static NewDataToast_activity makeText(Context context, CharSequence text,
												 boolean isSound, int rawid) {
		if (result == null) {
			result = new NewDataToast_activity(context, isSound, rawid);
		}
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		View v = inflate.inflate(R.layout.new_data_toast, null);
		TextView tv = (TextView) v.findViewById(R.id.new_data_toast_message);
		tv.setText(text);
		result.setView(v);
		result.setDuration(1000);
		result.setGravity(Gravity.BOTTOM, 0, (int) (dm.density * 75));
		return result;
	}

	public static void hodden() {

		if (result != null) {

			result.cancel();

		}
	}
}
