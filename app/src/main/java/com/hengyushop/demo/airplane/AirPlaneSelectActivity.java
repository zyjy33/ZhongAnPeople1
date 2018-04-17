package com.hengyushop.demo.airplane;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AirPlaneSelectActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_select;
	private EditText air_fly_city, air_come_city;
	private TextView air_time_fly;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplane_select);
		air_fly_city = (EditText) findViewById(R.id.air_fly_city);
		air_come_city = (EditText) findViewById(R.id.air_come_city);
		air_time_fly = (TextView) findViewById(R.id.air_time_fly);
		btn_select = (Button) findViewById(R.id.btn_select);
		air_time_fly.setOnClickListener(this);
		btn_select.setOnClickListener(this);
	}

	private CalendarPickerView calendar;
	private CalendarPickerView dialogView;
	private AlertDialog theDialog;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_select:
				AirStand stand = new AirStand();
				String fly_city = air_fly_city.getText().toString();
				String arr_city = air_come_city.getText().toString();
				CityDB c1 = new CityDB(getApplicationContext());
				String fly_code = c1.getCodes(fly_city);
				CityDB c2 = new CityDB(getApplicationContext());
				String arr_code = c2.getCodes(arr_city);
				stand.setArr_code(arr_code);
				stand.setFly_code(fly_code);
				stand.setArr_city(arr_city);
				stand.setFly_city(fly_city);
				stand.setTime(air_time_fly.getText().toString());
				if (fly_code != null || arr_code != null) {
					Intent intent = new Intent(AirPlaneSelectActivity.this,
							AirPlaneSelectTwoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bean", stand);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "请检查查询信息", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.air_time_fly:
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
				theDialog = new AlertDialog.Builder(AirPlaneSelectActivity.this)

						.setView(dialogView)
						.setNeutralButton("选中",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialogInterface, int i) {
										SimpleDateFormat formatter = new SimpleDateFormat(
												"yyyy-MM-dd");
										air_time_fly.setText(formatter
												.format(calendar.getSelectedDate()));
										dialogInterface.dismiss();
									}
								}).create();
				theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
					@Override
					public void onShow(DialogInterface dialogInterface) {
						dialogView.fixDialogDimens();
					}
				});
				theDialog.show();

				break;

			default:
				break;
		}
	}

}
