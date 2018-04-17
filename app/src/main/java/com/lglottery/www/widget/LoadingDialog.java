package com.lglottery.www.widget;

import com.zams.www.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * 自定义的IPHONE样式的对话框
 * */
public class LoadingDialog {
	private Dialog dialog;// 对话框
	private LinearLayout layout;

	/**
	 * 构造对话框数据
	 */
	public LoadingDialog(Context context) {
		dialog = new Dialog(context, R.style.spinnerdialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.load_dialog,
				null);
		dialog.setContentView(view);
	}

	/**
	 * 封装了的点击事件，可保证传过来的点击事件正常运行，并且得关闭窗口
	 * */
	public OnClickListener getLinstener(OnClickListener linstener) {
		final OnClickListener linstener2 = linstener;
		OnClickListener linstener3 = new OnClickListener() {
			public void onClick(View v) {
				if (linstener2 != null)
					linstener2.onClick(v);
				if (dialog.isShowing())
					dialog.dismiss();
			}
		};
		return linstener3;
	}

	public Dialog show() {
		dialog.show();
		return dialog;
	}

	public Dialog getDialog() {
		return dialog;
	}
}
