package com.hengyushop.demo.hotel;

import java.util.ArrayList;

import com.hengyushop.dao.CityDao;
import com.hengyushop.demo.airplane.CityDB;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

public class HotelHomeActivity extends BaseActivity implements OnClickListener {

	private Button btn_hotel_select;
	private Spinner now_city;
	private HotelCityAdapter adapter = null;
	private ArrayList<HotelCity> citys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_home);
		init();
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		CityDao db = new CityDao(getApplicationContext());
		citys = db.getHotelCitys();
		adapter = new HotelCityAdapter(citys, getApplicationContext());
		now_city = (Spinner) findViewById(R.id.now_city);
		now_city.setAdapter(adapter);
		btn_hotel_select = (Button) findViewById(R.id.btn_hotel_select);
		btn_hotel_select.setOnClickListener(this);

	}

	private void loadData() {

		// now_city.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_hotel_select:
			Intent intent = new Intent(HotelHomeActivity.this,
					HotelSelectResultActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("city",
					citys.get(now_city.getSelectedItemPosition()).getId());
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
