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
 * �Զ����IPHONE��ʽ�ĶԻ���
 * */
public class LoadingDialog {
	private Dialog dialog;// �Ի���
	private LinearLayout layout;

	/**
	 * ����Ի�������
	 */
	public LoadingDialog(Context context) {
		dialog = new Dialog(context, R.style.spinnerdialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.load_dialog,
				null);
		dialog.setContentView(view);
	}

	/**
	 * ��װ�˵ĵ���¼����ɱ�֤�������ĵ���¼��������У����ҵùرմ���
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
