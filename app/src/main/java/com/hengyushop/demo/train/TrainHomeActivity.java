package com.hengyushop.demo.train;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.android.hengyu.ui.CalendarPickerView;
import com.android.hengyu.ui.CalendarPickerView.SelectionMode;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainHomeActivity extends BaseActivity {
	private CalendarPickerView calendar;
	private CalendarPickerView dialogView;
	private AlertDialog theDialog;
	private Button btn_select, change_city;
	private TextView train_time;
	private AutoCompleteTextView startStation, arrvieStation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.train_home2);
		train_time = (TextView) findViewById(R.id.train_time);
		train_time.setOnClickListener(clickListener);
		btn_select = (Button) findViewById(R.id.btn_select);
		btn_select.setOnClickListener(clickListener);
		startStation = (AutoCompleteTextView) findViewById(R.id.startStation);
		arrvieStation = (AutoCompleteTextView) findViewById(R.id.arrvieStation);
		startStation.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

				// startStation.showDropDown();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				String temp = arg0.toString();
				HczDB czDb = new HczDB(getApplicationContext());
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						TrainHomeActivity.this,
						android.R.layout.simple_dropdown_item_1line, czDb
								.getZ(temp));
				startStation.setAdapter(adapter);
				// startStation.setDropDownHeight(50);
				startStation.setThreshold(1);
				startStation.setCompletionHint("关于'" + temp + "'关键字的站名");

			}
		});
		arrvieStation.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// startStation.showDropDown();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				String temp = arg0.toString();
				HczDB czDb = new HczDB(getApplicationContext());
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						TrainHomeActivity.this,
						android.R.layout.simple_dropdown_item_1line, czDb
								.getZ(temp));
				arrvieStation.setAdapter(adapter);
				arrvieStation.setThreshold(1);
				arrvieStation.setCompletionHint("关于'" + temp + "'关键字的站名");

			}
		});
		change_city = (Button) findViewById(R.id.change_city);
		change_city.setOnClickListener(clickListener);

	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.train_time:
				dialogView = (CalendarPickerView) getLayoutInflater().inflate(
						R.layout.dialog, null, false);
				final Calendar nextYear = Calendar.getInstance();
				nextYear.add(Calendar.YEAR, 1);
				final Calendar lastYear = Calendar.getInstance();
				lastYear.add(Calendar.YEAR, -1);
				calendar = (CalendarPickerView) dialogView
						.findViewById(R.id.calendar_view);
				calendar.init(lastYear.getTime(), nextYear.getTime()) //
						.inMode(SelectionMode.SINGLE) //
						.withSelectedDate(new Date());

				dialogView.init(lastYear.getTime(), nextYear.getTime()) //
						.withSelectedDate(new Date());
				theDialog = new AlertDialog.Builder(TrainHomeActivity.this)
						.setView(dialogView)
						.setNeutralButton("选中",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialogInterface,
											int i) {
										SimpleDateFormat formatter = new SimpleDateFormat(
												"yyyy-MM-dd");
										train_time.setText(formatter
												.format(calendar
														.getSelectedDate()));
										dialogInterface.dismiss();
									}
								}).create();
				theDialog
						.setOnShowListener(new DialogInterface.OnShowListener() {
							@Override
							public void onShow(DialogInterface dialogInterface) {
								dialogView.fixDialogDimens();
							}
						});
				theDialog.show();

				break;
			case R.id.btn_select:
				Intent intent = new Intent(TrainHomeActivity.this,
						TrainSelectResultActivity.class);
				Bundle bule = new Bundle();
				String s = startStation.getText().toString().trim();
				String a = arrvieStation.getText().toString().trim();
				HczDB db1 = new HczDB(getApplicationContext());
				String param1 = db1.getCode(s);
				HczDB db2 = new HczDB(getApplicationContext());
				String param2 = db2.getCode(a);
				if (param1.length() == 0) {
					Toast.makeText(getApplicationContext(), "键入始发地", 200)
							.show();
				} else if (param2.length() == 0) {
					Toast.makeText(getApplicationContext(), "键入目的地", 200)
							.show();
				} else {
					bule.putString("startStation", param1);
					bule.putString("arrvieStation", param2);
					bule.putString("add", s + "-" + a);
					bule.putString("time", train_time.getText().toString());
					intent.putExtras(bule);
					startActivity(intent);
				}
				break;
			case R.id.change_city:
				String temp = startStation.getText().toString();
				startStation.setText(arrvieStation.getText().toString());
				arrvieStation.setText(temp);
				break;
			default:
				break;
			}
		}
	};

}
