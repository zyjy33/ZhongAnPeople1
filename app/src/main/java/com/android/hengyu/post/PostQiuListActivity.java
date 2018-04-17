package com.android.hengyu.post;

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
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WorkDB;
import com.hengyushop.demo.airplane.CityDB;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostQiuListActivity extends BaseActivity {
	@SuppressWarnings("unused")
	private WorkIndexDo indexDo;
	private ListView post_list;
	private PostQiuListAdapter listAdapter;
	private Spinner advert_order_city, work_lists, work_price;
	private ArrayList<String> province, city, works, price, priceValue;
	private ArrayAdapter<String> provinceAdapter, cityAdapter, workAdapter,
			priceAdapter;
	private String JobCategoryId = "";
	private String provinceValue = "";
	private String cityValue = "";
	private String salaryValue = "";
	private ArrayList<QiuListDo> listDos;
	private TextView title;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					listDos = (ArrayList<QiuListDo>) msg.obj;

					listAdapter = new PostQiuListAdapter(listDos,
							getApplicationContext());
					post_list.setAdapter(listAdapter);
					post_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
							Intent intent = new Intent(PostQiuListActivity.this,
									QiuDetailActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("id", listDos.get(arg2)
									.getUserResumeID());
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				/*
				 * advert_order_province .setOnItemSelectedListener(new
				 * OnItemSelectedListener() {
				 * 
				 * @Override public void onItemSelected(AdapterView<?> arg0,
				 * View arg1, int arg2, long arg3) { // TODO Auto-generated
				 * method stub city = new CityDB(getApplicationContext())
				 * .getProvince(
				 * "select name from city where provinceId =(select code from province where name='"
				 * + province.get(arg2) + "')"); cityAdapter = new
				 * ArrayAdapter<String>( getApplicationContext(),
				 * android.R.layout.simple_spinner_item, city); cityAdapter
				 * .setDropDownViewResource
				 * (android.R.layout.simple_spinner_dropdown_item);
				 * advert_order_city.setAdapter(cityAdapter); area = new
				 * CityDB(getApplicationContext()) .getProvince(
				 * "select name from area where cityId =(select code from city where name='"
				 * + city.get(0) + "')"); areaAdapter = new
				 * ArrayAdapter<String>( getApplicationContext(),
				 * android.R.layout.simple_spinner_item, area); areaAdapter
				 * .setDropDownViewResource
				 * (android.R.layout.simple_spinner_dropdown_item);
				 * advert_order_area.setAdapter(areaAdapter);
				 * 
				 * provinceValue = new CityDB( getApplicationContext()).getName(
				 * "select code from province where name='" +
				 * province.get(advert_order_province
				 * .getSelectedItemPosition()) + "'");
				 * 
				 * load(); }
				 * 
				 * @Override public void onNothingSelected(AdapterView<?> arg0)
				 * { } });
				 */
					advert_order_city
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {
								}

								@Override
								public void onItemSelected(AdapterView<?> arg0,
														   View arg1, int arg2, long arg3) {

									if (advert_order_city.getSelectedItemPosition() == 0) {
										cityValue = "";
									} else {
										cityValue = new CityDB(
												getApplicationContext()).getName("select code from city where name='"
												+ city.get(advert_order_city
												.getSelectedItemPosition())
												+ "'");
									}
									load();
								}
							});

					work_lists
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
														   View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									if (work_lists.getSelectedItemPosition() == 0) {
										JobCategoryId = "";
									} else {
										String id = works.get(work_lists
												.getSelectedItemPosition());
										JobCategoryId = new WorkDB(
												getApplicationContext())
												.getId("select id from company_jobcategory where jobcategoryname = '"
														+ id + "'");
									}
									load();
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});
					work_price
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
														   View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									salaryValue = priceValue.get(work_price
											.getSelectedItemPosition());
									load();
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_qiu_list);
		indexDo = (WorkIndexDo) getIntent().getSerializableExtra("obj");
		title = (TextView) findViewById(R.id.title);
		title.setText(indexDo.getName());
		post_list = (ListView) findViewById(R.id.post_list);
		JobCategoryId = indexDo.getId();
		provinceValue = "320000";
		p();
		cityValue = "";
		load();
	}

	private void load() {
		Map<String, String> params = new HashMap<String, String>();
		// yth=&JobCategoryId=2152&WorkAddressProvince=&
		// WorkAddressCity=&WorkAddressArea=&MonthlySalary=
		params.put("jobCategoryId", JobCategoryId);
		params.put("jobProvinceCode", provinceValue);
		params.put("jobCityCode", cityValue);
		params.put("monthlySalary", salaryValue);
		params.put("yth", "");

		System.out.println(JobCategoryId + "-" + salaryValue + "--"
				+ provinceValue + "---" + cityValue + "----");
		// mi/getData.ashx?act=UserResumeList&yth=任意值&JobCategoryId=职位类别ID&jobProvinceCode=省的code&jobCityCode=市的code&MonthlySalary=县的code

		params.put("act", "UserResumeList");
		AsyncHttp.post_1(RealmName.REALM_NAME
						+ "/mi/getData.ashx?act=UserResumeList", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						System.out.println(arg1);
						ArrayList<QiuListDo> listDos = new ArrayList<QiuListDo>();
						try {
							JSONObject json = new JSONObject(arg1);
							JSONArray array = json.getJSONArray("items");
							int len = array.length();
							for (int i = 0; i < len; i++) {
								JSONObject jsonObject = array.getJSONObject(i);
								QiuListDo listDo = new QiuListDo();
								listDo.setAge(jsonObject.getString("age"));
								listDo.setCreateTime(jsonObject
										.getString("CreateTime"));
								listDo.setEducation_tmp(jsonObject
										.getString("Education_tmp"));
								listDo.setGender(jsonObject.getString("gender"));
								listDo.setResumeTitle(jsonObject
										.getString("ResumeTitle"));
								listDo.setUsername(jsonObject
										.getString("username"));
								listDo.setUserResumeID(jsonObject
										.getString("UserResumeID"));
								listDo.setWorkExperience_tmp(jsonObject
										.getString("UserResumeID"));
								listDos.add(listDo);

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Message msg = new Message();
						msg.what = 0;
						msg.obj = listDos;
						handler.sendMessage(msg);

					}
				});
	}

	private void p() {
		advert_order_city = (Spinner) findViewById(R.id.advert_order_city);
		work_lists = (Spinner) findViewById(R.id.work_lists);
		works = new WorkDB(getApplicationContext())
				.getWorks("select JobCategoryName from company_JobCategory where parentid="
						+ JobCategoryId);
		work_price = (Spinner) findViewById(R.id.work_price);
		price = new ArrayList<String>();
		price.add("不限");
		price.add("面议");
		price.add("2000元以下");
		price.add("2000-5000");
		price.add("5000-7000");
		price.add("7000-10000");
		price.add("100000以上");
		priceValue = new ArrayList<String>();
		priceValue.add("");
		priceValue.add("0");
		priceValue.add("1");
		priceValue.add("2");
		priceValue.add("3");
		priceValue.add("4");
		priceValue.add("5");

		priceAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, price);
		work_price.setAdapter(priceAdapter);
		workAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, works);
		work_lists.setAdapter(workAdapter);
		workAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		priceAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		province = new CityDB(getApplicationContext()).getProvinceW(
				"select name from province", false);

		provinceAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, province);

		provinceAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		// advert_order_province.setAdapter(provinceAdapter);
		// 城市
		/*
		 * city = new CityDB(getApplicationContext()) .getProvince(
		 * "select name from city where provinceId =(select code from province where name='"
		 * + province.get(0) + "')");
		 */
		System.out.println("select name from city where provinceid = '"
				+ provinceValue + "'");
		city = new CityDB(getApplicationContext()).getProvinceW(
				"select name from city where provinceid = '" + provinceValue
						+ "'", false);
		cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, city);
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		advert_order_city.setAdapter(cityAdapter);
		// 地区

	}
}
