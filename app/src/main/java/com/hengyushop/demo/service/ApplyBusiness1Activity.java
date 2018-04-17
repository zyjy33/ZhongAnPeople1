package com.hengyushop.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.Validator;
import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.widget.MyAlertDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

/**
 * �����̼�1
 * 
 * @author Administrator
 * 
 */
public class ApplyBusiness1Activity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui,iv_dingwei;
	private TextView tv_xiabu,tv_city,tv_dingwei,tv_dianhao,tv_sj_biaoqian;
	private EditText edt_sj_name,edt_sj_biaoqian,edt_sj_dianhao,edt_sj_xqjd;
	String dizhi = "ѡ���ַ";
	private String cityTxt,cityTxt1,cityTxt2,cityTxt3;
	private LocationManager locationManager;// λ�ù�����
	private String provider;// λ���ṩ��
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static double jingdu,weidu;
	private DialogProgress progress;
	String sj_bq = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sq1_shangjia);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(ApplyBusiness1Activity.this);
//		loadWeather();
		initUI();
		userloginqm();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		sj_bq = MerchantsLabelActivity.str;
		System.out.println("sj_bq-------1-----"+sj_bq);
		if (!sj_bq.equals("")) {
			tv_sj_biaoqian.setText(sj_bq);
//			MerchantsLabelActivity.str = "";
		}
		else {
			tv_sj_biaoqian.setText("");
		}
	}
	private void initUI() {
		// TODO Auto-generated method stub
		edt_sj_name = (EditText) findViewById(R.id.edt_sj_name);
//		edt_sj_biaoqian = (EditText) findViewById(R.id.edt_sj_biaoqian);
		edt_sj_dianhao = (EditText) findViewById(R.id.edt_sj_dianhao);
		edt_sj_xqjd = (EditText) findViewById(R.id.edt_sj_xqjd);
		tv_sj_biaoqian = (TextView) findViewById(R.id.tv_sj_biaoqian);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_dingwei = (TextView) findViewById(R.id.tv_dingwei);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_dingwei = (ImageView) findViewById(R.id.iv_dingwei);
		tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
		tv_dianhao = (TextView) findViewById(R.id.tv_dianhao);
		tv_sj_biaoqian.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);
		tv_xiabu.setOnClickListener(this);
		tv_city.setOnClickListener(this);
		tv_dingwei.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.tv_sj_biaoqian:
			sj_bq = "";
			Intent intent1 = new Intent(ApplyBusiness1Activity.this,MerchantsLabelActivity.class);
			startActivity(intent1);
			break;
		case R.id.tv_dingwei:
//			Intent intent1 = new Intent(ApplyBusiness1Activity.this,BaiduActivity.class);
//			Intent intent1 = new Intent(ApplyBusiness1Activity.this,BaiduActivity.class);
//			startActivity(intent1);
			progress.CreateProgress();	
			dingwei();
			break;
		case R.id.tv_city:
			View view = dialogm();
			final MyAlertDialog dialog1 = new MyAlertDialog(
					ApplyBusiness1Activity.this).builder()
					// .setTitle(tv_chengshi.getText().toString())
					.setTitle(dizhi.toString()).setView(view)
					.setNegativeButton("ȡ��", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog1.setPositiveButton("ȷ��", new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Toast.makeText(getApplicationContext(), cityTxt,
					// 1).show();
					tv_city.setText(cityTxt);
				}
			});
			dialog1.show();

			break;
		case R.id.tv_xiabu:
			String sj_name = edt_sj_name.getText().toString().trim();
//			String sj_bq = edt_sj_biaoqian.getText().toString().trim();
			String haoma = edt_sj_dianhao.getText().toString().trim();
			String dizhi = tv_city.getText().toString().trim();
			String xq_dizhi = edt_sj_xqjd.getText().toString().trim();
			String dingwei = tv_dingwei.getText().toString().trim();
			System.out.println("sj_bq------------"+sj_bq);
			if (sj_name.equals("")) {
				Toast.makeText(ApplyBusiness1Activity.this, "����д�̼�����", 100).show();
			} else if (sj_bq.equals("")) {
				Toast.makeText(ApplyBusiness1Activity.this, "����д�̼ұ�ǩ", 100).show();
			} else if (haoma.equals("")) {
				Toast.makeText(ApplyBusiness1Activity.this, "����д�ֻ�����", 100).show();
//		    } else if (haoma.length() > 11 || haoma.length() < 11) {
//			    Toast.makeText(ApplyBusiness1Activity.this, "�ֻ����벻��", 100).show();
			} else{
				if(Validator.isMobile(haoma)){
				if (dizhi.equals("")) {
				Toast.makeText(ApplyBusiness1Activity.this, "����д��ַ", 100).show();
			} else if (xq_dizhi.equals("")) {
				Toast.makeText(ApplyBusiness1Activity.this, "����д��ϸ�ֵ���ַ", 100).show();
			} else if (dingwei.equals("")) {
				Toast.makeText(ApplyBusiness1Activity.this, "�̼�δ��λ", 100).show();
			} else{
			
			Intent intent = new Intent(ApplyBusiness1Activity.this,ApplyBusiness2Activity.class);
			intent.putExtra("sj_name", sj_name);
			intent.putExtra("sj_bq", sj_bq);
			intent.putExtra("haoma", haoma);
			intent.putExtra("province", cityTxt1);
			intent.putExtra("city", cityTxt2);
			intent.putExtra("area", cityTxt3);
			intent.putExtra("xq_dizhi", xq_dizhi);
			intent.putExtra("jingdu", jingdu);
			intent.putExtra("weidu", weidu);
			System.out.println("jingdu------------"+jingdu);
			System.out.println("weidu------------"+weidu);
			startActivity(intent);
			}
			
			}else {
				showToast("��֤�ֻ���ʧ��!");
			}
			}	
//			Intent intent = new Intent(ApplyBusiness1Activity.this,ApplyBusiness2Activity.class);
//			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	private void showToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	/**
	 * ѡ�����
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

		// ����ѡ��
		final WheelView ccity = (WheelView) contentView
				.findViewById(R.id.wheelcity_ccity);
		ccity.setVisibleItems(0);// ���޳���

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "��"
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ "��"
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
						+ "��"
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ "��"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt2 = AddressData.CITIES[country.getCurrentItem()][city
				                        								.getCurrentItem()];
			}
		});

		ccity.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "��"
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ "��"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt3 = AddressData.COUNTIES[country.getCurrentItem()][city
					                          								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		country.setCurrentItem(1);// ���ñ���
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

	private void dingwei() {
		// TODO Auto-generated method stub
		// ���LocationManager��ʵ��
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				// ��ȡ���п��õ�λ���ṩ��
				List<String> providerList = locationManager.getProviders(true);
				if (providerList.contains(LocationManager.GPS_PROVIDER)) {
					//����ʹ��gps
					provider = LocationManager.GPS_PROVIDER;
				} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
					provider = LocationManager.NETWORK_PROVIDER;
				} else {
					// û�п��õ�λ���ṩ��
//					Toast.makeText(ApplyBusiness1Activity.this, "û��λ���ṩ���ɹ�ʹ��", Toast.LENGTH_LONG).show();
					Toast.makeText(ApplyBusiness1Activity.this, "������δ����λ�÷���Ȩ��", Toast.LENGTH_LONG).show();
					return;
				}

				Location location = locationManager.getLastKnownLocation(provider);
				System.out.println();
				if (location != null) {
					// ��ʾ��ǰ�豸��λ����Ϣ
					String firstInfo = "��һ���������Ϣ";
					showLocation(location, firstInfo);
				} else {
					progress.CloseProgress();
//					String info = "�޷���õ�ǰλ��";
//					Toast.makeText(this, info, 1).show();
//					positionText.setText(info);
					Toast.makeText(ApplyBusiness1Activity.this, "�Ѷ�λ��ǰ��ַ", 100).show();
					jingdu = 113.94716677;
					weidu = 22.5567556;
					tv_dingwei.setText("�Ѷ�λ");//�޷���õ�ǰλ��ʱ����ʱ�ȵ������ھ�γ��
					iv_dingwei.setVisibility(View.INVISIBLE);
				}

				// ���µ�ǰλ��
				locationManager.requestLocationUpdates(provider, 10 * 1000, 1,
						locationListener);
	}
	protected void onDestroy() {
		super.onDestroy();
		if (locationManager != null) {
			// �رճ���ʱ���������Ƴ�
			locationManager.removeUpdates(locationListener);
		}

	};

	LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onLocationChanged(Location location) {
			// �豸λ�÷����ı�ʱ��ִ������Ĵ���
			String changeInfo = "��10��ˢ�µ���ʾ��\n ʱ�䣺" + sdf.format(new Date())
					+ ",\n��ǰ�ľ����ǣ�" + location.getLongitude() + ",\n ��ǰ��γ���ǣ�"
					+ location.getLatitude();
			showLocation(location, changeInfo);
		}
	};

	/**
	 * ��ʾ��ǰ�豸��λ����Ϣ
	 * 
	 * @param location
	 */
	private void showLocation(Location location, String changeInfo) {
		// TODO Auto-generated method stub
		String currentLocation = "��ǰ�ľ����ǣ�" + location.getLongitude() + ",\n"
				+ "��ǰ��γ���ǣ�" + location.getLatitude();
		jingdu = location.getLongitude();
		weidu = location.getLatitude();
		
		progress.CloseProgress();
		
		System.out.println("jingdu------------"+jingdu);
		System.out.println("weidu------------"+weidu);
		Toast.makeText(ApplyBusiness1Activity.this, "�Ѷ�λ��ǰλ��", 100).show();
		tv_dingwei.setText("�Ѷ�λ");
		iv_dingwei.setVisibility(View.INVISIBLE);
		
//		tipInfo.setText(changeInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ���վ������
	 */
	private void userloginqm() {
		try{
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String login_sign = spPreferences.getString("login_sign", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_site_config?username="+user_name+"&site=mobile&sign="+login_sign+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======arg1============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							String webtel = obj.getString("webtel");
							tv_dianhao.setText("�ͷ��绰�� "+webtel);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, ApplyBusiness1Activity.this);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
