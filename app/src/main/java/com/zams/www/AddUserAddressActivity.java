package com.zams.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.PhoneAndEmailProving;
import com.android.hengyu.web.RealmName;
import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.hengyushop.dao.CityDao;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.DBManager;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.SlipButton;
import com.hengyushop.demo.at.SlipButton.OnChangedListener;
import com.hengyushop.entity.CityData;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.MyAlertDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 云商聚
 * @author Administrator
 *
 */
public class AddUserAddressActivity extends BaseActivity {

	private EditText et_username, et_userphone, et_address;
	private Button btn_hold;
	private String name, phone, address;
	private Spinner sp_sheng, sp_shi, sp_xian;
	private ArrayList<String> al_sheng, al_shi, al_xian;
	private String sheng, shi, xian, yth, key;
	private int sheng_code, shi_code, area_code, index,is_default;
	private WareDao wareDao;
	private UserRegisterData registerData;
	private DialogProgress progress;
	private DBManager dbManager;
	private CityDao cityDao;
	@SuppressWarnings("rawtypes")
	private ArrayAdapter aa_sheng, aa_shi, aa_area;
	private String strUrl;
	private MyPopupWindowMenu popupWindowMenu;
	private SharedPreferences spPreferences;
	private RelativeLayout rl_xzdz;
	private TextView tv_city;
	private String cityTxt,cityTxt1,cityTxt2,cityTxt3;
	String dizhi = "选择地区";
	SlipButton sb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {

			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.add_user_address);
			popupWindowMenu = new MyPopupWindowMenu(this);
			wareDao = new WareDao(getApplicationContext());
			progress = new DialogProgress(AddUserAddressActivity.this);
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			innidade();

			//			cityDao = new CityDao(AddUserAddressActivity.this);
			//			ArrayList<CityData> shengs = cityDao.findSheng();
			//			ArrayList<String> list = new ArrayList<String>();
			//			for (int i = 0; i < shengs.size(); i++) {
			//				list.add(shengs.get(i).name);
			//			}
			//			Log.v("data2", shengs.get(0).name + "     " + shengs.size());
			//
			//			Message message = new Message();
			//			message.what = 2;
			//			message.obj = list;
			//			handler.sendMessage(message);

			//			spinnerData();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					et_username.setText("");
					et_userphone.setText("");
					et_address.setText("");
					String strmsg = (String) msg.obj;
					progress.CloseProgress();
					Toast.makeText(getApplicationContext(), strmsg, 200).show();
					AppManager.getAppManager().finishActivity();
				/*
				 * if (index == 1) { Intent intent = new
				 * Intent(AddUserAddressActivity.this,
				 * AddressManagerActivity.class); startActivity(intent); } else
				 * if (index == 0) { Intent intent = new
				 * Intent(AddUserAddressActivity.this,
				 * OrderConfrimActivity.class); startActivity(intent); }
				 */
					finish();
					break;
				case 1:
					et_username.setText("");
					et_userphone.setText("");
					et_address.setText("");
					String strmsgll = (String) msg.obj;
					progress.CloseProgress();
					// Toast.makeText(getApplicationContext(), "添加失败,请重新添加。",
					// 200).show();
					Toast.makeText(getApplicationContext(), strmsgll, 200).show();
					break;
				case 2:
					al_sheng = (ArrayList<String>) msg.obj;
					aa_sheng = new ArrayAdapter(AddUserAddressActivity.this,android.R.layout.simple_spinner_item, al_sheng);
					aa_sheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_sheng.setAdapter(aa_sheng);
					break;
				case 3:
					al_shi = (ArrayList<String>) msg.obj;
					aa_shi = new ArrayAdapter(AddUserAddressActivity.this,
							android.R.layout.simple_spinner_item, al_shi);
					aa_shi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_shi.setAdapter(aa_shi);
					break;
				case 4:
					al_xian = (ArrayList<String>) msg.obj;
					aa_area = new ArrayAdapter(AddUserAddressActivity.this,
							android.R.layout.simple_spinner_item, al_xian);
					aa_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_xian.setAdapter(aa_area);
					break;
				default:
					break;
			}

		};
	};



	private void innidade() {
		sb = (SlipButton) findViewById(R.id.sli);
		rl_xzdz = (RelativeLayout) findViewById(R.id.rl_xzdz);
		tv_city = (TextView) findViewById(R.id.tv_city);
		et_username = (EditText) findViewById(R.id.et_user_name);
		et_userphone = (EditText) findViewById(R.id.et_user_phone);
		et_address = (EditText) findViewById(R.id.et_user_address);
		//		sp_sheng = (Spinner) findViewById(R.id.sp_sheng);
		//		sp_shi = (Spinner) findViewById(R.id.sp_shi);
		//		sp_xian = (Spinner) findViewById(R.id.sp_xian);
		btn_hold = (Button) findViewById(R.id.btn_holdr);

		/**
		 * 判断是否使用红包
		 */
		sb.SetOnChangedListener(new OnChangedListener()
		{
			@Override
			public void OnChanged(boolean isCheck)
			{
				System.out.println("isCheck================"+isCheck);
				if (isCheck == true) {
					try {

					} catch (Exception e) {

						e.printStackTrace();
					}
					is_default = 1;
					//	            		hongbao_tety =  isCheck;//选择红包状态下
				}else if (isCheck == false){
					//						 sb.setCheck(true);
					//						hongbao_tety =  isCheck;//不选择红包状态下
					is_default = 0;
				}
			}
		});

		rl_xzdz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				View view = dialogm();
				final MyAlertDialog dialog1 = new MyAlertDialog(
						AddUserAddressActivity.this).builder()
						.setTitle(dizhi.toString()).setView(view)
						.setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {

							}
						});
				dialog1.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {
						tv_city.setText(cityTxt);
					}
				});
				dialog1.show();
			}
		});

		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});


		btn_hold.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				name = et_username.getText().toString();
				phone = et_userphone.getText().toString();
				address = et_address.getText().toString();
				String dizhi = tv_city.getText().toString().trim();


				if (name.equals("")) {
					Toast.makeText(getApplicationContext(), "请填写用户名", 200).show();
				} else if (phone.equals("")) {
					Toast.makeText(getApplicationContext(), "请填写联系电话", 200).show();
				}
				else if (phone.length() < 11) {
					Toast.makeText(getApplicationContext(), "手机号码少于11位", 200).show();
				}
				else if (dizhi.equals("")) {
					Toast.makeText(getApplicationContext(), "请填写收货地址", 200).show();
				}else if (address.equals("")) {
					Toast.makeText(getApplicationContext(), "请填写详细收货地址", 200).show();
				} else {
					progress.CreateProgress();
					String id = spPreferences.getString("user_id", "");
					String user_name = spPreferences.getString("user", "");

					//					String pingjiedizhi = sheng + "、" + shi + "、" + xian;
					strUrl = RealmName.REALM_NAME_LL
							+ "/add_user_shopping_address?user_id=" + id
							+ "&user_name=" + user_name + ""
							+ "&user_accept_name=" + name + "&user_province="+cityTxt1+"&user_city="+cityTxt2+"&user_area="
							+ cityTxt3 + "&user_street=&user_address=" + address
							+ "&user_mobile=" + phone + "&user_telphone="
							+ "&user_email=&user_post_code=&is_default="+is_default+"";

					System.out.println("11================" + cityTxt1);
					System.out.println("11================" + cityTxt2);
					System.out.println("11================" + cityTxt3);
					System.out.println("strUrl================" + strUrl);
					AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
						public void onSuccess(int arg0, String arg1) {
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("0================" + arg1);
								String status = object.getString("status");
								// String info = object.getString("info");
								if (status.equals("y")) {
									System.out.println("1================");
									String msg = object.getString("info");
									Log.v("data1", msg);
									Message message = new Message();
									message.what = 0;
									message.obj = msg;
									handler.sendMessage(message);
								} else {
									System.out.println("2================");
									String msg = object.getString("info");
									Message message2 = new Message();
									message2.what = 1;
									message2.obj = msg;

									handler.sendMessage(message2);
								}
							} catch (JSONException e) {

								e.printStackTrace();
							}
						};

						public void onFailure(Throwable arg0, String arg1) {
							System.out.println("3================" + arg0);
							System.out.println("3================" + arg1);

						};

					}, null);

				}
			}
		});
	}

	/**
	 * 选择城市
	 * @return
	 */
	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView
				.findViewById(R.id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = AddressData.CITIES;
		final String ccities[][][] = AddressData.COUNTIES;
		final WheelView city = (WheelView) contentView
				.findViewById(R.id.wheelcity_city);
		city.setVisibleItems(0);

		// 地区选择
		final WheelView ccity = (WheelView) contentView
				.findViewById(R.id.wheelcity_ccity);
		ccity.setVisibleItems(0);// 不限城市

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "、"
						+ AddressData.CITIES[country.getCurrentItem()][city
						.getCurrentItem()]
						+ "、"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
						.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt1 = AddressData.PROVINCES[country.getCurrentItem()];
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updatecCities(ccity, ccities, country.getCurrentItem(),
						newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "、"
						+ AddressData.CITIES[country.getCurrentItem()][city
						.getCurrentItem()]
						+ "、"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
						.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt2 = AddressData.CITIES[country.getCurrentItem()][city
						.getCurrentItem()];
			}
		});

		ccity.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "、"
						+ AddressData.CITIES[country.getCurrentItem()][city
						.getCurrentItem()]
						+ "、"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
						.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt3 = AddressData.COUNTIES[country.getCurrentItem()][city
						.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		country.setCurrentItem(1);// 设置北京
		city.setCurrentItem(1);
		ccity.setCurrentItem(1);
		return contentView;
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Updates the ccity wheel
	 */
	private void updatecCities(WheelView city, String ccities[][][], int index,
							   int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				ccities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = AddressData.PROVINCES;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(R.id.wheelcity_country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

	// 字符串上传服务器 乱码 问题的解决方法
	private String processParam(String temp)
			throws UnsupportedEncodingException {
		// return URLEncoder.encode(temp, "UTF-8");
		return temp;
	}

	private void spinnerData() {
		sp_sheng.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {


				sheng = al_sheng.get(arg2);
				cityDao = new CityDao(AddUserAddressActivity.this);
				CityData cityData = cityDao.findShengCode(sheng);
				sheng_code = cityData.getCode();
				Log.v("data2", cityData.getCode() + "");
				cityDao = new CityDao(AddUserAddressActivity.this);
				ArrayList<CityData> shis = cityDao.findCity(sheng_code);
				ArrayList<String> list2 = new ArrayList<String>();
				for (int i = 0; i < shis.size(); i++) {
					list2.add(shis.get(i).name);
				}
				Message message = new Message();
				message.what = 3;
				message.obj = list2;
				handler.sendMessage(message);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		sp_shi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {

				shi = al_shi.get(arg2);
				cityDao = new CityDao(AddUserAddressActivity.this);
				CityData cityData = cityDao.findCityCode(shi);
				shi_code = cityData.getCode();
				cityDao = new CityDao(AddUserAddressActivity.this);
				ArrayList<CityData> areas = cityDao.findArea(shi_code);
				ArrayList<String> list3 = new ArrayList<String>();
				for (int i = 0; i < areas.size(); i++) {
					list3.add(areas.get(i).name);
				}
				Message message = new Message();
				message.what = 4;
				message.obj = list3;
				handler.sendMessage(message);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		sp_xian.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {

				xian = al_xian.get(arg2);
				cityDao = new CityDao(AddUserAddressActivity.this);
				CityData cityData = cityDao.findAreaCode(xian);
				area_code = cityData.getCode();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}


}
