package com.lglottery.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zams.www.R;

public class CommomConfrim {
	public interface onDeleteSelect {
		void onClick(int resID);
	}

	/**
	 * Ö§¸¶µ¯³ö¿ò
	 */
	private CommomConfrim() {
	}

	public static Dialog showSheet(Context context,
								   final onDeleteSelect actionSheetSelected,
								   OnCancelListener cancelListener, final Object object) {
		final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.common_delete_pop, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		TextView item0 = (TextView) layout.findViewById(R.id.item0);
		TextView item1 = (TextView) layout.findViewById(R.id.item1);
		TextView item2 = (TextView) layout.findViewById(R.id.item2);
		TextView item3 = (TextView) layout.findViewById(R.id.item3);
		TextView item4 = (TextView) layout.findViewById(R.id.item4);

		item0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				actionSheetSelected.onClick(R.id.item0);
				dlg.dismiss();
			}
		});
		item1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				actionSheetSelected.onClick(R.id.item1);
				dlg.dismiss();
			}
		});
		item2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				actionSheetSelected.onClick(R.id.item2);
				dlg.dismiss();
			}
		});
		item3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				actionSheetSelected.onClick(R.id.item3);
				dlg.dismiss();
			}
		});

		item4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// actionSheetSelected.onClick(object);
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(false);
		if (cancelListener != null)
			dlg.setOnCancelListener(cancelListener);

		dlg.setContentView(layout);
		dlg.show();

		return dlg;
	}

}
