package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.hengyu.web.RealmName;
import com.hengyushop.db.JDB;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.lglottery.www.adapter.JDLayoutAdapter;
import com.lglottery.www.adapter.JDbean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JDActivity extends BaseActivity {
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					final ArrayList<JDbean> list = (ArrayList<JDbean>) msg.obj;
					// 将获得的数据传递到适配器当中
					SharedUtils utils = new SharedUtils(getApplicationContext(),
							Common.locationName);
					double lat = Double.parseDouble(utils.getStringValue("lat"));
					double lon = Double.parseDouble(utils.getStringValue("log"));
					JDLayoutAdapter adapter = new JDLayoutAdapter(
							getApplicationContext(), list, imageLoader, lat, lon);
					jd_list.setAdapter(adapter);
					jd_list.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							Intent intent = new Intent(JDActivity.this,
									JD_detailActivity.class);
							intent.putExtra("id", list.get(arg2).getScenID());
							startActivity(intent);
						}
					});
					break;

				default:
					break;
			}
		};
	};
	private ArrayList<String> province, city;
	private ArrayAdapter<String> provinceAdapter, cityAdapter;
	private ListView jd_list;
	private Spinner advert_order_province;
	private Spinner advert_order_city;
	private String provinceValue, cityValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jd_activity);
		advert_order_province = (Spinner) findViewById(R.id.advert_order_province);
		advert_order_city = (Spinner) findViewById(R.id.advert_order_city);

		province = new JDB(getApplicationContext()).getProvinceW(
				"select name from TongChengLY_ProvinceList", false);

		provinceAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, province);

		provinceAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		advert_order_province.setAdapter(provinceAdapter);
		// 城市
		city = new JDB(getApplicationContext())
				.getProvince("select name from TongChengLY_CityList where provinceId =(select provinceId from TongChengLY_ProvinceList where name='"
						+ province.get(0) + "')");
		cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, city);
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		advert_order_city.setAdapter(cityAdapter);
		provinceValue = new JDB(getApplicationContext())
				.getName("select provinceId from TongChengLY_ProvinceList where name='"
						+ province.get(advert_order_province
						.getSelectedItemPosition()) + "'");

		advert_order_province
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
											   int arg2, long arg3) { // TODO Auto-generated
						city = new JDB(getApplicationContext())
								.getProvince("select name from TongChengLY_CityList where provinceId =(select provinceId from TongChengLY_ProvinceList where name='"
										+ province.get(arg2) + "')");

						cityAdapter = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_spinner_item, city);
						cityAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						advert_order_city.setAdapter(cityAdapter);

						provinceValue = new JDB(getApplicationContext())
								.getName("select provinceId from TongChengLY_ProvinceList where name='"
										+ province.get(advert_order_province
										.getSelectedItemPosition())
										+ "'");

						load(provinceValue, cityValue);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		advert_order_city
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
											   int arg2, long arg3) {

						cityValue = new JDB(getApplicationContext())
								.getName("select cityId from TongChengLY_CityList where name='"
										+ city.get(advert_order_city
										.getSelectedItemPosition())
										+ "'");

						load(provinceValue, cityValue);
					}
				});

		jd_list = (ListView) findViewById(R.id.jd_list);

	}

	private void load(String province, String city) {
		if (province == null || city == null)
			return;
		RequestParams params = new RequestParams();
		params.put("provinceId", province);
		params.put("cityId", city);
		System.out.println(province + "-->" + city);
		AsyncHttp.post(RealmName.REALM_NAME
						+ "/mi/LY_Scenery.ashx?act=GetSceneryList", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							int status = jsonObject.getInt("status");
							if (status == 1) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("items");
								int len = jsonArray.length();
								ArrayList<JDbean> lists = new ArrayList<JDbean>();

								for (int i = 0; i < len; i++) {
									JSONObject object = jsonArray
											.getJSONObject(i);
									JDbean bean = new JDbean();
									bean.setAmount(object.getString("amount"));
									bean.setAmountAdv(object
											.getString("amountAdv"));
									bean.setCityId(object.getString("cityId"));
									bean.setCityName(object
											.getString("cityName"));
									bean.setCountId(object
											.getString("countyId"));
									bean.setCountName(object
											.getString("countyName"));
									bean.setDisrance(object
											.getString("distance"));
									bean.setId(object.getString("ID"));
									bean.setLat(object.getString("lat"));
									bean.setLon(object.getString("lon"));
									bean.setName(object
											.getString("sceneryName"));
									bean.setPath(object.getString("imgPath"));
									bean.setScenAdd(object
											.getString("sceneryAddress"));
									bean.setScenID(object
											.getString("sceneryId"));
									bean.setScenSum(object
											.getString("scenerySummary"));
									lists.add(bean);
								}
								Message msg = new Message();
								msg.what = 1;
								msg.obj = lists;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, null);
	}
}
