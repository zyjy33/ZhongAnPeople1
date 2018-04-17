package com.zams.www;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.dao.WideDo;
import com.hengyushop.demo.airplane.CityDB;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.WareData;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WideMarketActivity extends BaseActivity {
	private String url = RealmName.REALM_NAME + "/mi/getData.ashx";
	private ListView wide;
	private WareData ware;
	private WareDao waredao;
	private String CITY_CODE, PRO_NAME;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					// start
					ArrayList<WideDo> list = waredao.findAllWide();
					WideMarketAdapter adapter = new WideMarketAdapter(list,
							getApplicationContext(), handler);
					wide.setAdapter(adapter);
					break;
				case 1:
					TextView view = (TextView) msg.obj;
					String id = (String) view.getTag();
					System.out.println("获取的ID" + id);
					Intent intent = new Intent(WideMarketActivity.this,
							WideThreeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("city", CITY_CODE);
					bundle.putString("name", view.getText().toString());
					bundle.putInt("id", Integer.parseInt(id));
					intent.putExtras(bundle);
					startActivity(intent);
					break;
				default:
					break;
			}
		};
	};
	private ArrayList<String> province;
	private ArrayAdapter<String> provinceAdapter;
	private String provinceValue;
	private ArrayList<String> city;
	private ArrayAdapter<String> cityAdapter;
	private Spinner advert_order_city, advert_order_province;

	private void loadData() {
		advert_order_city = (Spinner) findViewById(R.id.advert_order_city);
		/*
		 * advert_order_province = (Spinner)
		 * findViewById(R.id.advert_order_province); //临时
		 * advert_order_province.setVisibility(View.INVISIBLE);
		 *
		 *
		 * province = new CityDB(getApplicationContext()).getProvinceW(
		 * "select name from province", false); PRO_NAME = province.get(0);
		 * provinceAdapter = new ArrayAdapter<String>(getApplicationContext(),
		 * android.R.layout.simple_spinner_item, province);
		 *
		 * provinceAdapter
		 * .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line
		 * ); advert_order_province.setAdapter(provinceAdapter);
		 */
		// 城市

		// city = new CityDB(getApplicationContext())
		// .getProvince("select name from city where provinceId =(select code from province where name='"
		// + province.get(0) + "')");
		// 临时
		city = new ArrayList<String>();

		city.add("南京市");
		city.add("上海市");
		city.add("苏州市");
		city.add("重庆市");
		city.add("深圳市");

		PRO_NAME = "南京市";
		/*
		 * city = new CityDB(getApplicationContext()).getProvinceW(
		 * "select name from city where provinceid = '" + provinceValue + "'",
		 * false);
		 */
		cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, city);
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		advert_order_city.setAdapter(cityAdapter);
		CITY_CODE = new CityDB(getApplicationContext())
				.getName("select code from city where name='" + PRO_NAME + "'");
		/*
		 * advert_order_province .setOnItemSelectedListener(new
		 * OnItemSelectedListener() {
		 *
		 * @Override public void onItemSelected(AdapterView<?> arg0, View arg1,
		 * int arg2, long arg3) { PRO_NAME = province.get(arg2); // TODO
		 * Auto-generated method stub city = new CityDB(getApplicationContext())
		 * .getProvince(
		 * "select name from city where provinceId =(select code from province where name='"
		 * + province.get(arg2) + "')"); cityAdapter = new ArrayAdapter<String>(
		 * getApplicationContext(), android.R.layout.simple_spinner_item, city);
		 * cityAdapter
		 * .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line
		 * ); advert_order_city.setAdapter(cityAdapter);
		 *
		 * }
		 *
		 * @Override public void onNothingSelected(AdapterView<?> arg0) { //
		 * TODO Auto-generated method stub
		 *
		 * } });
		 */
		advert_order_city
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
											   int arg2, long arg3) {
						// TODO Auto-generated method stub
						CITY_CODE = new CityDB(getApplicationContext())
								.getName("select code from city where name='"
										+ city.get(arg2) + "'");
						System.out.println("城市" + CITY_CODE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

	}

	private void init() {
		wide = (ListView) findViewById(R.id.wide);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wide_market_activity);
		loadData();
		waredao = new WareDao(getApplicationContext());
		init();
		if (waredao.finWideID().size() == 0) {
			Map<String, String> params = new HashMap<String, String>();
			// ?act=ProductCateInfo&yth=admin&ProductCateParentId=3
			params.put("act", "ProductCateInfo");
			params.put("yth", "admin");
			params.put("productCateParentId", "3");
			AsyncHttp.post_1(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					parse(arg1);

				}
			});
		} else {
			handler.sendEmptyMessage(0);
		}
	}

	// 解析网络数据 并插入到Sqlite数据库中
	public void parse(String st) {
		try {
			// List<WareData> allids = waredao.finAddID();

			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("Data");
			for (int i = 0; i < jsonArray.length(); i++) {
				WareData data = new WareData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.ID = object.getInt("id");
				data.productTypeName = object.getString("productTypeName");
				data.parentId = object.getInt("parentId");
				data.layer = object.getInt("layer");
				data.openUrl = object.getString("openUrl");
				data.SpecCommend = object.getInt("SpecCommend");
				Log.v("data1", data.ID + "");

				JSONArray jsonArray2 = object.getJSONArray("ProductName");
				for (int j = 0; j < jsonArray2.length(); j++) {
					WareData data2 = new WareData();
					JSONObject object2 = jsonArray2.getJSONObject(j);
					data2.ID = object2.getInt("id");
					data2.productTypeName = object2
							.getString("productTypeName");
					data2.parentId = object2.getInt("parentId");
					data2.layer = object2.getInt("layer");
					data2.openUrl = object2.getString("openUrl");
					data2.SpecCommend = object2.getInt("SpecCommend");

					Log.v("data2", data2.ID + "");

					JSONArray jsonArray3 = object2.getJSONArray("ProductName");
					for (int k = 0; k < jsonArray3.length(); k++) {
						WareData data3 = new WareData();
						JSONObject object3 = jsonArray3.getJSONObject(k);
						data3.ID = object3.getInt("id");
						data3.productTypeName = object3
								.getString("productTypeName");
						data3.parentId = object3.getInt("parentId");
						data3.layer = object3.getInt("layer");
						data3.openUrl = object3.getString("openUrl");
						data3.SpecCommend = object3.getInt("SpecCommend");
						Log.v("data3", data3.ID + "");

						ware = new WareData();
						ware.setID(data3.ID);
						ware.setProductTypeName(data3.productTypeName);
						ware.setParentId(data3.parentId);
						ware.setLayer(data3.layer);
						ware.setOpenUrl(data3.openUrl);
						ware.setSpecCommend(data3.SpecCommend);
						waredao.insertWide(ware);
					}
					ware = new WareData();
					ware.setID(data2.ID);
					ware.setProductTypeName(data2.productTypeName);
					ware.setParentId(data2.parentId);
					ware.setLayer(data2.layer);
					ware.setOpenUrl(data2.openUrl);
					ware.setSpecCommend(data2.SpecCommend);
					waredao.insertWide(ware);
				}

				ware = new WareData();
				ware.setID(data.ID);
				ware.setProductTypeName(data.productTypeName);
				ware.setParentId(data.parentId);
				ware.setLayer(data.layer);
				ware.setOpenUrl(data.openUrl);
				ware.setSpecCommend(data.SpecCommend);
				waredao.insertWide(ware);
			}
			handler.sendEmptyMessage(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
