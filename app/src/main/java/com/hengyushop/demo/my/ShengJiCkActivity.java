package com.hengyushop.demo.my;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.ctrip.openapi.java.utils.BitmapUtils;
import com.ctrip.openapi.java.utils.Validator;
import com.example.uploadpicdemo.Utils;
import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.hengyushop.dao.CityDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.service.ApplyBusiness2Activity;
import com.hengyushop.entity.DizhiData;
import com.hengyushop.entity.UserSenJiBean;
import com.lglottery.www.widget.MyAlertDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.IndividualCenterActivity;
import com.zams.www.R;

/**
 * 升级为服务顾问
 * 
 * @author Administrator
 * 
 */
public class ShengJiCkActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui;
	private TextView tv_xiabu, tv_city,tv_mobile;
	private EditText edt_contact, edt_mobile, edt_identity_card, edt_address,
			edt_jxdz, edt_dizhi,edt_tuijianren;
	private SharedPreferences spPreferences;
	String user_name, user_id;
	private DialogProgress progress;
//	public static Handler handler;
	String dizhi = "选择地址";
	private String cityTxt,cityTxt1,cityTxt2,cityTxt3;
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	private ImageView iv_personal_icon,iv_personal_icon1;
	private int zhaopian;
    private String tupian1 = "";
    private String tupian2 = "";
    private String identity_card_a,identity_card_b;
    private String sheng_zhi,shi_zhi,qu_zhi;
    private LocationManager locationManager;// 位置管理类
	private String provider;// 位置提供器
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String jingdu,weidu;
	public static String address,path,time,yth,province,city,area,lng,lat,imagePath;
	private TextView regise_tip;
	UserSenJiBean bean;
	String strUrlone;
	private ArrayList<String> al_sheng, al_shi, al_xian;
	private Spinner sp_sheng, sp_shi, sp_xian;
	private CityDao cityDao;
	private int sheng_code, shi_code, area_code;
	private String sheng, shi, xian;
	private ArrayAdapter aa_sheng, aa_shi, aa_area;
	private ArrayList<DizhiData> list_1;
	private ArrayList<DizhiData> list_2;
	private ArrayList<DizhiData> list_3;
	ArrayList<String> list_shen;
	ArrayList<String> list_shi;
	ArrayList<String> list_diqu;
	ArrayList<String> list_code1;
	ArrayList<String> list_code2;
	ArrayList<String> list_code3;
	DizhiData data;
	String shen_code,chengshi_code,diqu_code;
	String mobile = "";
//	String shen_code = "";
//	String chengshi_code = "";
//	String diqu_code = "";
	SharedPreferences spPreferences3;
	public AQuery mAq;
	String tuijianrenString = "";
	ImageLoader imageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shengji_chuangke);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(ShengJiCkActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		spPreferences3 = getSharedPreferences("baozunbiaohao", MODE_PRIVATE);
		mAq = new AQuery(this);
		mobile = spPreferences.getString("mobile", "");
		System.out.println("======mobile============="+mobile);
		initUI();
		userpanduan();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		userpanduan();
//		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
//		String haoma = getIntent().getStringExtra("zhou");
//		System.out.println("======haoma============"+haoma);
//		tv_mobile.setText(haoma);
	}
	
	public void initUI() {
		regise_tip = (TextView) findViewById(R.id.regise_tip);
		edt_contact = (EditText) findViewById(R.id.edt_contact);
		edt_identity_card = (EditText) findViewById(R.id.edt_identity_card);
		tv_city = (TextView) findViewById(R.id.tv_city);
		edt_tuijianren = (EditText) findViewById(R.id.tv_tuijianren);
		edt_dizhi = (EditText) findViewById(R.id.edt_dizhi);
		// edt_name = (EditText) findViewById(R.id.edt_name);
		iv_personal_icon = (ImageView) findViewById(R.id.iv_personal_icon);
		iv_personal_icon1 = (ImageView) findViewById(R.id.iv_personal_icon1);
		sp_sheng = (Spinner) findViewById(R.id.sp_sheng);
		sp_shi = (Spinner) findViewById(R.id.sp_shi);
		sp_xian = (Spinner) findViewById(R.id.sp_xian);

		Button button = (Button) findViewById(R.id.btn_xiayibu);
		button.setOnClickListener(this);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		iv_fanhui.setOnClickListener(this);
		tv_xiabu.setOnClickListener(this);
		tv_city.setOnClickListener(this);
		iv_personal_icon.setOnClickListener(this);
		iv_personal_icon1.setOnClickListener(this);
		regise_tip.setOnClickListener(this);
		
		if (!mobile.equals("")) {
			tv_mobile.setText(mobile);
		}
	}
	
	/**
	 * 判断是否升级获取用户信息
	 */
	public void userpanduan(){
		progress.CreateProgress();
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_commpany_model?user_id="+user_id+"&datatype=Sales";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							System.out.println("判断是否升级================================="+arg1);
							JSONObject obj = object.getJSONObject("data");
							bean = new UserSenJiBean();
							bean.contact = obj.getString("contact");
							bean.mobile = obj.getString("mobile");
							bean.idcard = obj.getString("idcard");
							bean.idcard_a = obj.getString("idcard_a");
							bean.idcard_b = obj.getString("idcard_b");
							bean.lng = obj.getString("lng");
							bean.lat = obj.getString("lat");
							bean.address = obj.getString("address");
							bean.province = obj.getString("province");
							bean.city = obj.getString("city");
							bean.area = obj.getString("area");
							bean.recommend_name = obj.getString("recommend_name");
							province = bean.province;
							city = bean.city;
							area = bean.area;
							lng = bean.lng;
							lat = bean.lat;
							identity_card_a = bean.idcard_a;
							identity_card_b = bean.idcard_b;
							
					        imageLoader=ImageLoader.getInstance();
					        imageLoader.displayImage(RealmName.REALM_NAME_FTP+bean.idcard_a,iv_personal_icon);
					        imageLoader.displayImage(RealmName.REALM_NAME_FTP+bean.idcard_b,iv_personal_icon1);
					        
//					        mAq.id(iv_personal_icon).image(RealmName.REALM_NAME_FTP+bean.idcard_a);
//					        mAq.id(iv_personal_icon1).image(RealmName.REALM_NAME_FTP+bean.idcard_b);
//					        String url = "/upload/phone/112022999/20170216141624727.jpg";
//							 imageLoader.displayImage((String)RealmName.REALM_NAME_HTTP+url,iv_personal_icon1);
							 
							 
//					        if (iv_personal_icon1.getDrawable() == null) {  
//					            Toast.makeText(ShengJiCkActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();  
//					            iv_personal_icon1.setImageDrawable(getResources().getDrawable(R.drawable.zams_tp));  
//					        }else {
//					        	 Toast.makeText(ShengJiCkActivity.this, "图片加载成功", Toast.LENGTH_SHORT).show();  
//							}
							 
							edt_contact.setText(bean.contact);
//							edt_mobile.setText(bean.mobile);
							edt_identity_card.setText(bean.idcard);
//							String dizhi = bean.province +"、"+ bean.city +"、"+ bean.area;
//							tv_city.setText(dizhi);
							edt_dizhi.setText(bean.address);
							
							String content = getIntent().getStringExtra("zhou");
							if (content != null) {
								System.out.println("======content============"+content);
								String haoma =  (String) content.subSequence(17, 28);
								System.out.println("haoma=================="+haoma);
								if (haoma != null) {
									edt_tuijianren.setText(haoma);
								}
							}
							 else {
								String tjr = bean.recommend_name;
								System.out.println("tjr------------------"+tjr);
								if (bean.recommend_name.length() > 4) {
									edt_tuijianren.setText(tjr);
								    System.out.println("tjr1------------------"+tjr);
								}else {
								    edt_tuijianren.setText("");
								    System.out.println("tjr2------------------"+tjr);
								}
							}
							
							
//							if ((tjr==null)||(tjr.equals(""))||(tjr.trim().length()>4)) {
//								System.out.println("tjr3------------------"+tjr);
//							}
//							if (TextUtils.isEmpty(tjr)) {
//								System.out.println("tjr4------------------"+tjr);
//							}
							progress.CloseProgress();
						}else{
							progress.CloseProgress();
						}
						bean = null;
						getdizhishen();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, getApplicationContext());
			
	}
	
	/**
	 * 获取省份
	 */
	private void getdizhishen() {
		// TODO Auto-generated method stub
		list_1 = new ArrayList<DizhiData>();
		list_shen = new ArrayList<String>();
		list_code1 = new ArrayList<String>();
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_province",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						try {
							JSONObject object = new JSONObject(arg1);
//							System.out.println("2================================="+arg1);
							  String status = object.getString("status");
							    if (status.equals("y")) {
							    	JSONArray jsonArray = object.getJSONArray("data");
							    	System.out.println("province================================="+province);
							    	if (province != null ) {
							    		sheng = province;
							    		list_shen.add(province);
							    		spPreferences3 = getSharedPreferences("baozunbiaohao", MODE_PRIVATE);
							    		shen_code = spPreferences3.getString("shen_code", "");
										System.out.println("shen_code================================="+shen_code);
										list_code1.add(shen_code);
									} else {
										shen_code = "";
									}
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject obj= jsonArray.getJSONObject(i);
										data = new DizhiData();
										data.name = obj.getString("name");
										data.code = obj.getString("code");
										if (data.name.equals(province)) {
											shen_code = data.code;
											System.out.println("shen_code======"+shen_code);
										}
										list_1.add(data);
										list_shen.add(data.name);
										list_code1.add(data.code);
									}
									Message message = new Message();
									message.what = 2;
									message.obj = list_shen;
									handler.sendMessage(message);
									
							    } else {
							    	
								}
							    if (!shen_code.equals("")) {
								    getdizhishi(shen_code);
								}else {
								sheng = list_1.get(0).name;
							    String code = list_1.get(0).code;
								System.out.println("code================================="+code);
							    getdizhishi(code);
								}
							    
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, null);
		
	}
	/**
	 * 获取城市
	 */
	private void getdizhishi(String code) {
		// TODO Auto-generated method stub
		try {
			
		list_2 = new ArrayList<DizhiData>();
		list_shi = new ArrayList<String>();
		list_code2 = new ArrayList<String>();
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_city?province_code="+code+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						try {
							JSONObject object = new JSONObject(arg1);
//							System.out.println("get_city================================="+arg1);
							  String status = object.getString("status");
							    if (status.equals("y")) {
							    	System.out.println("city================================="+city);
							    	if (city != null ) {
							    		shi = city;
							    		list_shi.add(city);
							    		chengshi_code = spPreferences3.getString("chengshi_code", "");
										System.out.println("chengshi_code================================="+chengshi_code);
										list_code2.add(chengshi_code);
									}else {
										chengshi_code = "";
									}
							    	JSONArray jsonArray = object.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject obj= jsonArray.getJSONObject(i);
										data = new DizhiData();
										data.name = obj.getString("name");
										data.code = obj.getString("code");
										if (data.name.equals(city)) {
											chengshi_code = data.code;
											System.out.println("chengshi_code======"+chengshi_code);
										}
										list_shi.add(data.name);
										list_2.add(data);
										list_code2.add(data.code);
									}
//									  System.out.println("list_shi================================="+list_shi.size());
									    Message message = new Message();
										message.what = 3;
										message.obj = list_shi;
										handler.sendMessage(message);
							    } else {
								}
							    
							    if (!chengshi_code.equals("")) {
							    	gedizhidiqu(chengshi_code);
								}else {
							    shi = list_2.get(0).name;
							    String code = list_2.get(0).code;
							    gedizhidiqu(code);
								}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, null);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取地区
	 */
	private void gedizhidiqu(String code) {
		// TODO Auto-generated method stub
		try {
		list_3 = new ArrayList<DizhiData>();
		list_diqu = new ArrayList<String>();
		list_code3 = new ArrayList<String>();
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_area?city_code="+code+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						try {
							JSONObject object = new JSONObject(arg1);
//							System.out.println("get_area================================="+arg1);
							  String status = object.getString("status");
							    if (status.equals("y")) {
							    	System.out.println("area================================="+area);
							    	if (area != null ) {
							    		xian = area;
							    	    list_diqu.add(area);
							    		diqu_code = spPreferences3.getString("diqu_code", "");
										System.out.println("diqu_code================================="+diqu_code);
										list_code3.add(diqu_code);
									}else {
										diqu_code = "";
									}
							    	JSONArray jsonArray = object.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject obj= jsonArray.getJSONObject(i);
										data = new DizhiData();
										data.name = obj.getString("name");
										data.code = obj.getString("code");
										data.lng = obj.getString("lng");
										data.lat = obj.getString("lat");
										list_diqu.add(data.name);
										list_3.add(data);
										list_code3.add(data.code);
									}
									Message message = new Message();
									message.what = 4;
									message.obj = list_diqu;
									handler.sendMessage(message);
									
							    } else {
								}
//							    System.out.println("sheng1---------------"+sheng);
							    spinnerData();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, null);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	
	
	
	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent = new Intent(ShengJiCkActivity.this,UpgradeChongZhiActivity.class);
				startActivity(intent);
				finish();
				break;
			case 2:
				
				list_shen = (ArrayList<String>) msg.obj;
				aa_sheng = new ArrayAdapter(ShengJiCkActivity.this,android.R.layout.simple_spinner_item, list_shen);
				aa_sheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_sheng.setAdapter(aa_sheng);
				
				break;
			case 3:
				list_shi = (ArrayList<String>) msg.obj;
				aa_shi = new ArrayAdapter(ShengJiCkActivity.this,android.R.layout.simple_spinner_item, list_shi);
				aa_shi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_shi.setAdapter(aa_shi);
				break;
			case 4:
				try {
					 
				list_diqu = (ArrayList<String>) msg.obj;
				aa_area = new ArrayAdapter(ShengJiCkActivity.this,android.R.layout.simple_spinner_item, list_diqu);
				aa_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_xian.setAdapter(aa_area);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

		};
	};

	private void spinnerData() {
		sp_sheng.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
//				System.out.println("arg2================================="+arg2);
//				int num = arg2-1;
//				System.out.println("num================================="+num);
				sheng = list_shen.get(arg2);
				System.out.println("arg2================================="+sheng);
//				shen_code = list_1.get(num).code;
				shen_code = list_code1.get(arg2);
				System.out.println("shen_code================================="+sheng+"/"+shen_code);
			    getdizhishi(shen_code);
				
			    
			    if (province != null) {
					province = null;
					city = null;
					area = null;
				}
			    
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		sp_shi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
				shi = list_shi.get(arg2);
				System.out.println("shi================================="+shi);
//				chengshi_code = list_2.get(arg2).code;
				chengshi_code = list_code2.get(arg2);
				System.out.println("chengshi_code================================="+shi+"/"+chengshi_code);
				gedizhidiqu(chengshi_code);
				
				if (province != null) {
					province = null;
					city = null;
					area = null;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		sp_xian.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
//					System.out.println("list_3================================="+list_3.size());
				xian = list_diqu.get(arg2);
				diqu_code = list_3.get(arg2).code;
				System.out.println("xian================================="+xian+"/"+diqu_code);
				if (list_3.size() > 0) {
					jingdu = list_3.get(arg2).lng;
					weidu = list_3.get(arg2).lat;
					System.out.println("jingdu3================================="+jingdu+"/"+weidu);
				}
				
//				System.out.println("sheng2---------------"+sheng);
				if (province != null) {
					province = null;
					city = null;
					area = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}
	
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.regise_tip:
			Intent intent4 = new Intent(ShengJiCkActivity.this,Webview1.class);
			intent4.putExtra("chuanke_id", "5998");
			startActivity(intent4);
			break;
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.tv_city:
			// Intent intent = new
			// Intent(ShengJiCkActivity.this,CityXzActivity.class);
			// startActivity(intent);

			View view = dialogm();
			final MyAlertDialog dialog1 = new MyAlertDialog(
					ShengJiCkActivity.this).builder()
					// .setTitle(tv_chengshi.getText().toString())
					.setTitle(dizhi.toString()).setView(view)
					.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog1.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Toast.makeText(getApplicationContext(), cityTxt,
					// 1).show();
					tv_city.setText(cityTxt);
				}
			});
			dialog1.show();

			break;
		case R.id.iv_personal_icon:
			zhaopian = 1;
			showChoosePicDialog();
			break;
		case R.id.iv_personal_icon1:
			zhaopian = 2;
			showChoosePicDialog();
			break;
//		case R.id.btn_login:
//			Intent intent2 = new Intent(ShengJiCkActivity.this,ShengJiCzActivity.class);
//			startActivity(intent2);
//			break;
		case R.id.btn_xiayibu:
			try {
//				progress.CreateProgress();
			String contact = edt_contact.getText().toString().trim();
//			String mobile = edt_mobile.getText().toString().trim();
			String identity_card = edt_identity_card.getText().toString().trim();
			String dizhi = tv_city.getText().toString().trim();
		    String xqdz = edt_dizhi.getText().toString().trim();
		    String tuijianren = edt_tuijianren.getText().toString().trim();
//		    System.out.println("tupian1---------------"+tupian1);
//			System.out.println("tupian2---------------"+tupian2);
//			   if (!tupian1.equals("")) {
//				   System.out.println("tupian11---------------"+tupian1);
//			    	 identity_card_a = tupian1;
////			    	 identity_card_b = lat;
//			    	 if(!tupian2.equals("")){
////			    		 identity_card_a = tupian1;
//						 identity_card_b = tupian2;
//			    	 }
//			   }else if(!tupian2.equals("")){
//				   System.out.println("tupian22---------------"+tupian2);
////				     identity_card_a = lng;
//					 identity_card_b = tupian2;
//					 if (!tupian1.equals("")){
//				    	 identity_card_a = tupian1;
////				    	 identity_card_b = tupian2;
//					 }
//			   }
			   
			    System.out.println("tupian1---------------"+tupian1);
				System.out.println("tupian2---------------"+tupian2);
				   if (!tupian1.equals("")) {
					   System.out.println("tupian11---------------"+tupian1);
				    	 identity_card_a = tupian1;
//				    	 identity_card_b = lat;
				    	 if(!tupian2.equals("")){
//				    		 identity_card_a = tupian1;
							 identity_card_b = tupian2;
				    	 }
				   }else if(!tupian2.equals("")){
					   System.out.println("tupian22---------------"+tupian2);
//					     identity_card_a = lng;
						 identity_card_b = tupian2;
						 if (!tupian1.equals("")){
					    	 identity_card_a = tupian1;
//					    	 identity_card_b = tupian2;
						 }
				   }
			   
				System.out.println("identity_card_a---------------"+identity_card_a);
				System.out.println("identity_card_b---------------"+identity_card_b);
				System.out.println("jingdu3================================="+jingdu+"/"+weidu);
				
//				if (sheng != null) {
					 String dizhi_heji = sheng+"、"+shi+"、"+xian;
					 address = dizhi_heji+"、"+xqdz;
					 System.out.println("================address=="+address);
//				}	
			
				System.out.println("shen---------------"+sheng+"++"+xqdz);
			if (contact.equals("")) {
				Toast.makeText(ShengJiCkActivity.this, "请输入姓名", 100).show();
			} else if (mobile.equals("")) {
				Toast.makeText(ShengJiCkActivity.this, "请输入手机号码", 100).show();
			} else if (mobile.length() > 11) {
				Toast.makeText(ShengJiCkActivity.this, "手机号码少于11位", 100).show();
			} else if (identity_card.equals("")) {
				Toast.makeText(ShengJiCkActivity.this, "请输入身份证号", 100).show();
			} else if (identity_card.length() < 18) {
				Toast.makeText(ShengJiCkActivity.this, "请输入正确的身份证号", 100).show();
//			} else if (identity_card_a.equals("")) {
			} else if (identity_card_a == null) {
				Toast.makeText(ShengJiCkActivity.this, "请上传身份证正面照片", 100).show();
//			} else if (identity_card_b.equals("")) {
			} else if (identity_card_b == null) {
				Toast.makeText(ShengJiCkActivity.this, "请上传身份证反面照片", 100).show();
//			} else if (sheng.equals("")) {
//				Toast.makeText(ShengJiCkActivity.this, "请输入经销地址", 100).show();
			} else if (xqdz.equals("")) {
				Toast.makeText(ShengJiCkActivity.this, "请输入详细街道地址", 100).show();
			}else{
				
				String str = xian;
				System.out.println(str.replaceAll("\\s*", "")); 
				String area = xian.replaceAll("\\s*", "");
				
//				if(Validator.isMobile(phone)){
				try {
//					if (province != null){
//				        strUrlone = RealmName.REALM_NAME_LL
//						+ "/user_upgrade_sales?user_id="+ user_id+ "&user_name="+ user_name+ 
//						"&parent_id=0&recommend_name="
//						+"&contact="+ contact+ "&mobile="+ mobile+ "&identity_card="+ identity_card
//						+ "&identity_card_a="+identity_card_a+"&identity_card_b="+identity_card_b+"&provice="+province+
//						"&city="+city+ "&area="+area+"&address=" + xqdz + "&lng="+lng+"&lat="+lat+"";
//				        System.out.println("=================002==" + strUrlone);
//			       }else {  
//					if (sheng.contains("新疆")) {
					        strUrlone = RealmName.REALM_NAME_LL
							+ "/user_upgrade_sales?user_id="+ user_id+ "&user_name="+ user_name+ 
							"&recommend_name="+tuijianren+""
							+"&contact="+ contact+ "&mobile="+ mobile+ "&identity_card="+ identity_card
							+ "&identity_card_a="+identity_card_a+"&identity_card_b="+identity_card_b+"&provice="+sheng+
							"&city="+shi+"&area="+xian+"&address=" + xqdz + "&lng="+jingdu+"&lat="+weidu+"";
//					        System.out.println("=================001==" + strUrlone);//&parent_id=0
//				    }
					
					
					
					AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								System.out.println("=================1=="+ arg1);

								String status = jsonObject.getString("status");
								System.out.println("status: " + status);
								String info = jsonObject.getString("info");
								if (status.equals("y")) {
									
									Toast.makeText(ShengJiCkActivity.this,info, 200).show();
									progress.CloseProgress();
									// finish();
//									spPreferences3.edit().clear().commit(); 
									Editor editor = spPreferences3.edit();
									editor.putString("shen_code", shen_code);
									editor.putString("chengshi_code", chengshi_code);
									editor.putString("diqu_code", diqu_code);
									editor.commit();
									handler.sendEmptyMessage(0);
									
								} else if (status.equals("n")) {
									Toast.makeText(ShengJiCkActivity.this,info, 200).show();
									progress.CloseProgress();
								}

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

						}
						
						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1);
							Toast.makeText(ShengJiCkActivity.this,"异常", 200).show();
						}
						
					}, null);
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

//				}else {
//					Toast.makeText(MobilePhoneActivity.this, "手机号码不正确", 200).show();
//				}
			}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			break;
		default:
			break;
		}
	}
	

	public final static int CONSULT_DOC_PICTURE = 1000;
	public final static int CONSULT_DOC_CAMERA = 1001;

	private static final int PHOTO_REQUEST_CAMERA = 10;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 11;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 12;// 裁剪

	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

	private int SELECT_PICTURE = 1;
	private int SELECT_CAMERA = 2;
	private int SELECT_SCAN = 0;
	private ImageView photo;
	private File file;
	private ProgressDialog pd;
	private Bitmap bmp;
	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle("设置头像");
//		String[] items = { "选择本地照片", "拍照" };
//		builder.setNegativeButton("取消", null);
//		builder.setItems(items, new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				switch (which) {
//				case CHOOSE_PICTURE: // 选择本地照片
//					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//					System.out.println("本地照片-----------------"+openAlbumIntent);
//					openAlbumIntent.setType("image/*");
//					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
//					break;
//				case TAKE_PICTURE: // 拍照
//					Intent openCameraIntent = new Intent(
//							MediaStore.ACTION_IMAGE_CAPTURE);
//					tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
//					System.out.println("拍照================"+tempUri);
//					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
//					startActivityForResult(openCameraIntent, TAKE_PICTURE);
//					
//					break;
//				}
//			}
//		});
//		builder.create().show();
		System.out.println("SELECT_PICTURE------------1-------相册--------"+SELECT_PICTURE);
		CharSequence[] items = { "手机相册", "手机拍照" };//"查看头像", 
		new AlertDialog.Builder(ShengJiCkActivity.this).setTitle("上传照片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						if (which == SELECT_PICTURE) {
							Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
							// 判断存储卡是否可以用，可用进行存储
							if (hasSdcard()) {
								intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(
												Environment.getExternalStorageDirectory(),PHOTO_FILE_NAME)));
							}
							startActivityForResult(intent,PHOTO_REQUEST_CAMERA);
							System.out.println("SELECT_PICTURE------------1-------相册--------"+SELECT_PICTURE);
						} else if (which == SELECT_CAMERA) {
							System.out.println("SELECT_CAMERA-------------2----------拍照----"+SELECT_CAMERA);
						} else if (which == SELECT_SCAN) {
							Intent intent = new Intent(Intent.ACTION_PICK);
							intent.setType("image/*");
							startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
							System.out.println("SELECT_SCAN----------------4-----查看头像------"+SELECT_SCAN);
						}

					}
				}).create().show();
	}
	
	/**
	 * 是否有sd卡
	 * 
	 * @return
	 */
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PHOTO_REQUEST_GALLERY) {// 图库
				if (data == null) {
					return;
				}
				Uri uri = data.getData();
				System.out.println("图片的值=========uri========"+uri);
				crop(uri);
			} else if (requestCode == PHOTO_REQUEST_CAMERA) {// 拍照

				if (hasSdcard()) {
					file = new File(Environment.getExternalStorageDirectory(),
							PHOTO_FILE_NAME);
					crop(Uri.fromFile(file));
					System.out.println("图片的值=========file========"+file);
				} else {
//					Toast.makeText(this, "未找到存储卡，无法存储照片！", 0).show();
				}
			} else if (requestCode == PHOTO_REQUEST_CUT) {// 裁剪
				try {
					bmp = data.getParcelableExtra("data");
//					networkImage.setImageBitmap(bmp);
					if (!bmp.equals("")) {
						if (zhaopian == 1) {
//							System.out.println("图片的值1=================");
							iv_personal_icon.setImageBitmap(bmp);
						}else if (zhaopian == 2){
						iv_personal_icon1.setImageBitmap(bmp);
						
//						}else if (zhaopian == 3){
//							iv_personal_icon2.setImageBitmap(bmp);
//						}else if (zhaopian == 4){
//							iv_personal_icon3.setImageBitmap(bmp);
						}
						}else {
						}
					File tempFile = BitmapUtils.saveBitmapFile(bmp,
							PHOTO_FILE_NAME);
					System.out.println("图片的值1================="+bmp);
					System.out.println("图片的值2================="+tempFile);
//					upload(tempFile);//上传到服务器 
//					pd = new ProgressDialog(this);
//					pd.setMessage("头像正在上传中请稍后");
//					pd.show();
					try {
						imagePath = Utils.savePhoto(bmp, Environment.getExternalStorageDirectory().getAbsolutePath(), 
								String.valueOf(System.currentTimeMillis()));
						System.out.println("imagePath======================="+imagePath);
						
						new Thread() {
							public void run() {
								try {
									FTPClient client = new FTPClient();
									client.connect("60.205.151.160", 2021);
									client.login("zams", "zams1230.");
									SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
					                time = f.format(new Date());  
					                yth = IndividualCenterActivity.yth;
					                String remotePathTmp = "phone/" + "" + yth +"";//路径
									System.out.println("========================"+remotePathTmp);
									
									try {
										client.createDirectory(remotePathTmp);//客户端创建目录
									} catch (Exception e) {
							                e.printStackTrace();
									} finally {
										client.changeDirectory(remotePathTmp);
										
										File file = new File(imagePath);
										FileInputStream fis = new FileInputStream(file);
										try {
											client.upload(time + ".jpg", fis, 0, 0, null);
										} catch (FTPDataTransferException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (FTPAbortedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										fis.close();
										client.logout();//exit
									}
									if (zhaopian == 1) {
										tupian1 = "/upload/phone/" + yth + "/"+ time + ".jpg";
									}else if (zhaopian == 2){
										tupian2 = "/upload/phone/" + yth + "/"+ time + ".jpg";
									}
//									else if (zhaopian == 3){
//										tupian3 = "/upload/phone/" + yth + "/"+ time + ".jpg";
//									}else if (zhaopian == 4){
//										tupian4 = "/upload/phone/" + yth + "/"+ time + ".jpg";
//									}
									System.out.println("tupian1--------------------------"+tupian1);
									System.out.println("tupian2--------------------------"+tupian2);
//									System.out.println("tupian3--------------------------"+tupian3);
//									System.out.println("tupian4--------------------------"+tupian4);
									
//										tupian = "/upload/phone/" + yth + "/"+ time + ".jpg";
//									System.out.println("tupian1--------------------------"+tupian);

								} catch (IllegalStateException e) {
									e.printStackTrace();//非法状态异常
								}
								catch (FTPIllegalReplyException e) {
									e.printStackTrace();//非法回复异常
								} catch (FTPException e) {
									e.printStackTrace();//异常
								} catch (IOException e) {
									e.printStackTrace();
								}
							};
							
						}.start();
						
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
	}

	/**
	 * 剪切图片
	 * 
	 * @function:
	 * @author:yl
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
//			switch (requestCode) {
//			//拍照
//			case TAKE_PICTURE:
//				startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
//				break;
//			//上传图片	
//			case CHOOSE_PICTURE:
//				startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
//				break;
//			case CROP_SMALL_PICTURE:
//				if (data != null) {
//					setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
//				}
//				break;
//			}
//		}
//	}

//	/**
//	 * 裁剪图片方法实现
//	 * 
//	 * @param uri
//	 */
//	protected void startPhotoZoom(Uri uri) {
//		if (uri == null) {
//			Log.i("tag", "The uri is not exist.");
//		}
//		tempUri = uri;
////		System.out.println("裁剪图片方法实现================"+tempUri);
//		Intent intent = new Intent("com.android.camera.action.CROP");
//		intent.setDataAndType(uri, "image/*");
//		// 设置裁剪
//		intent.putExtra("crop", "true");
//		// aspectX aspectY 是宽高的比例
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		// outputX outputY 是裁剪图片宽高
//		intent.putExtra("outputX", 300);
//		intent.putExtra("outputY", 300);
//		intent.putExtra("return-data", true);
//		startActivityForResult(intent, CROP_SMALL_PICTURE);
//	}
//	
//	public void saveBitmapFile(Bitmap bitmap){
//        File file=new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
//        try {
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                
//                bos.flush();
//                bos.close();
//        } catch (IOException e) {
//                e.printStackTrace();
//        }
//}
 
	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param
	 * 
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		Bitmap photo = extras.getParcelable("data");
		if (extras != null) {
//			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			System.out.println("图片的值1================="+photo);
			System.out.println("图片的值2================="+tempUri);
			if (!photo.equals("")) {
			if (zhaopian == 1) {
				iv_personal_icon.setImageBitmap(photo);
			}else if (zhaopian == 2){
			    iv_personal_icon1.setImageBitmap(photo);
			}
			
			try {
				imagePath = Utils.savePhoto(photo, Environment.getExternalStorageDirectory().getAbsolutePath(), 
						String.valueOf(System.currentTimeMillis()));
				
				System.out.println("imagePath======================="+imagePath);
				
//				// 这里开始的第二部分，获取图片的路径：
//				String[] proj = { MediaStore.Images.Media.DATA };
//				System.out.println("proj======================="+proj);
//				
//				// 好像是android多媒体数据库的封装接口，具体的看Android文档
//				Cursor cursor = this.managedQuery(tempUri, proj,null, null, null);
//				System.out.println("cursor========================"+cursor);
//				
//				// 按我个人理解 这个是获得用户选择的图片的索引值
//				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//				System.out.println("column_index========================"+column_index);
//				
//				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
//				cursor.moveToFirst();
//				
//				// 最后根据索引值获取图片路径
//				path = cursor.getString(column_index);
//				System.out.println("path========================"+path);
				
				new Thread() {
					public void run() {
						try {
							FTPClient client = new FTPClient();
//							client.connect("183.62.138.31", 2021);
//							client.login("zams", "yunsen1230.");
							client.connect("60.205.151.160", 2021);
							client.login("zams", "zams1230.");
							SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
			                time = f.format(new Date());  
			                yth = IndividualCenterActivity.yth;
			                String remotePathTmp = "phone/" + "" + yth +"";//路径
							System.out.println("========================"+remotePathTmp);
							
							try {
								client.createDirectory(remotePathTmp);//客户端创建目录
							} catch (Exception e) {
					                e.printStackTrace();
							} finally {
								client.changeDirectory(remotePathTmp);
								
								File file = new File(imagePath);
								FileInputStream fis = new FileInputStream(file);
								try {
									client.upload(time + ".jpg", fis, 0, 0, null);
								} catch (FTPDataTransferException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (FTPAbortedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								fis.close();
								client.logout();//exit
							}
							
							if (zhaopian == 1) {
								tupian1 = "/upload/phone/" + yth + "/"+ time + ".jpg";
							}else if (zhaopian == 2){
								tupian2 = "/upload/phone/" + yth + "/"+ time + ".jpg";
							}
							System.out.println("tupian1------------1--------------"+tupian1);
							System.out.println("tupian2------------2--------------"+tupian2);

						} catch (IllegalStateException e) {
							e.printStackTrace();//非法状态异常
						}
						catch (FTPIllegalReplyException e) {
							e.printStackTrace();//非法回复异常
						} catch (FTPException e) {
							e.printStackTrace();//异常
						} catch (IOException e) {
							e.printStackTrace();
						}
					};
					
				}.start();
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		
//			    uploadPic(photo);
	}

//	private void uploadPic(Bitmap bitmap) {
//		// 上传至服务器
//		// ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
//		// 注意这里得到的图片已经是圆形图片了
//		// bitmap是没有做个圆形处理的，但已经被裁剪了
//		System.out.println("bitmap================"+bitmap);
//		String imagePath = Utils.savePhoto(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
//		if (zhaopian  == 1) {
////			tupian1 = imagePath;
//			System.out.println("图片imagePath================"+imagePath);
//		}else if (zhaopian  == 2){
////			tupian2 = imagePath;
//		}
//		Log.e("imagePath", imagePath+"");
//		
//		if(imagePath != null){
//			// 拿着imagePath上传了
//			// ...
//		}
//	}
	
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
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
