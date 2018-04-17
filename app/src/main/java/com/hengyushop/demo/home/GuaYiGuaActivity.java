package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.pub.GuaYiGuaAdapter;
import com.android.hengyu.pub.TuanchengyuanAdapterll;
import com.android.hengyu.web.RealmName;
import com.baidu.mapapi.utils.d;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.XsgyListData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.TuiGuang2Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class GuaYiGuaActivity extends Activity implements OnClickListener
{
	RelativeLayout start_layout;
	public static String id,id_ll,id_gyg,login_sign,point;
	private ArrayList<XsgyListData> list;
	private Button more_gua,start;
	private TextView result;
	private LinearLayout gua_jiang,guayigua_buju;
	public static Handler handlerll;
	private String activity_rule;
	public static String drawn,drawn_ll;
	LayoutInflater inflater;
	Intent intent;
	ImageView iv_fanhui;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		getjiangxiangxq();
//		getjiangxiang();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guayigua);
		//http://blog.csdn.net/lmj623565791/article/details/40162163
		start_layout = (RelativeLayout) findViewById(R.id.index_layout);
		start = (Button) findViewById(R.id.start);
		more_gua = (Button) findViewById(R.id.more_gua);
		result = (TextView) findViewById(R.id.result);
		gua_jiang = (LinearLayout) findViewById(R.id.gua_jiang);
		guayigua_buju = (LinearLayout) findViewById(R.id.guayigua_buju);
		LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
		ll_buju.setBackgroundResource(R.drawable.ysj_gyg);
		FrameLayout fl_buju = (FrameLayout) findViewById(R.id.fl_buju);
		fl_buju.setBackgroundResource(R.drawable.ysj_gjq);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		start.setOnClickListener(this);
		more_gua.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);
		
//		start.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				getjiangxiang();
////				int jifen = Integer.valueOf(point);
////				System.out.println("jifen=================="+jifen);
////				if (jifen >= 100) {
////					start_layout.setVisibility(View.GONE);
////				}else {
////					Toast.makeText(GuaYiGuaActivity.this, "您还不够100福利", 200).show();
////				}
//			}
//		});
//		
//	   more_gua.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				initPopupWindow();
//				showPopupWindow(gua_jiang);
//			}
//		});
//	
//		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//		iv_fanhui.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
		
		
//		getintner();
		 handlerll = new Handler() {
		   		public void handleMessage(Message msg) {
		   			switch (msg.what) {
		   			case 2:
//		   				start_layout.setVisibility(View.VISIBLE);
//		   				start.setText("再刮一次");
//		   				result.setText(TuiGuang2Activity.drawn);
//		   				View layout = inflater.inflate(R.layout.activity_guayigua, null);
//		   				drawn = "恭喜您,抽中2等奖";
		   				
//		   				if (JuDuiHuanActivity.id_ll.equals("")) {
//		   					id_gyg = JuDuiHuanActivity.id_ll;
//		   					drawn_ll = JuDuiHuanActivity.drawn_ll;
//		   					JuDuiHuanActivity.id_ll = "";
//		   					JuDuiHuanActivity.drawn_ll = "";
//						}else {
//							id_gyg = id;
//							drawn_ll = drawn;
//						}
//		   				System.out.println("id_gyg=================="+id_gyg);
//		   				System.out.println("drawn_ll=================="+drawn_ll);
		   				intent = new Intent(GuaYiGuaActivity.this,ZyZTiShiActivity.class);
//	   					intent.putExtra("drawn", JuDuiHuanActivity.drawn);//
//	   					intent.putExtra("id", JuDuiHuanActivity.id);//
	   					intent.putExtra("drawn", drawn);//
	   					intent.putExtra("id", id);//
//		   				intent.putExtra("drawn", drawn_ll);
//	   					intent.putExtra("id", id_gyg);
	   					intent.putExtra("quxiao", "取消");
	   					startActivity(intent);
	   					getintner2();
		   				break;
		   			}
		   		}
		 };
		   	
		userloginqm();
		getjiangxiangxq();
//		getguizhe();
	}
	private void getintner2() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_guayigua);
		start_layout = (RelativeLayout) findViewById(R.id.index_layout);
		start = (Button) findViewById(R.id.start);
		more_gua = (Button) findViewById(R.id.more_gua);
		result = (TextView) findViewById(R.id.result);
		gua_jiang = (LinearLayout) findViewById(R.id.gua_jiang);
		guayigua_buju = (LinearLayout) findViewById(R.id.guayigua_buju);
		LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
		ll_buju.setBackgroundResource(R.drawable.ysj_gyg);
		FrameLayout fl_buju = (FrameLayout) findViewById(R.id.fl_buju);
		fl_buju.setBackgroundResource(R.drawable.ysj_gjq);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		start.setOnClickListener(this);
		more_gua.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);
		
//		start.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				getjiangxiang();
////				int jifen = Integer.valueOf(point);
////				System.out.println("jifen=================="+jifen);
////				if (jifen >= 100) {
////					start_layout.setVisibility(View.GONE);
////				}else {
////					Toast.makeText(GuaYiGuaActivity.this, "您还不够100福利", 200).show();
////				}
//			}
//		});
//		
//	   more_gua.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				initPopupWindow();
//				showPopupWindow(gua_jiang);
//			}
//		});
//	
//		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//		iv_fanhui.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
		
	}
	
	private void getintner() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_guayigua);
		start_layout = (RelativeLayout) findViewById(R.id.index_layout);
		start = (Button) findViewById(R.id.start);
		more_gua = (Button) findViewById(R.id.more_gua);
		result = (TextView) findViewById(R.id.result);
		gua_jiang = (LinearLayout) findViewById(R.id.gua_jiang);
		guayigua_buju = (LinearLayout) findViewById(R.id.guayigua_buju);
		LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
		ll_buju.setBackgroundResource(R.drawable.ysj_gyg);
		FrameLayout fl_buju = (FrameLayout) findViewById(R.id.fl_buju);
		fl_buju.setBackgroundResource(R.drawable.ysj_gjq);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		start_layout.setVisibility(View.GONE);
		start.setOnClickListener(this);
		more_gua.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);
		
//		start.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				getjiangxiang();
////				int jifen = Integer.valueOf(point);
////				System.out.println("jifen=================="+jifen);
////				if (jifen >= 100) {
////					start_layout.setVisibility(View.GONE);
////				}else {
////					Toast.makeText(GuaYiGuaActivity.this, "您还不够100福利", 200).show();
////				}
//			}
//		});
//		
//	   more_gua.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				initPopupWindow();
//				showPopupWindow(gua_jiang);
//			}
//		});
//	
//		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//		iv_fanhui.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
	}
	
	
	/**
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		intent = new Intent();
		switch (v.getId()) {
		case R.id.start://
			getjiangxiang();
			break;
		case R.id.more_gua://
			initPopupWindow();
			showPopupWindow(gua_jiang);
			break;
		case R.id.iv_fanhui://取消
			 finish();
			break;
		default:
			break;
		}
	}
	
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.gualottery_tip, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		try {
		Button closed = (Button) popView.findViewById(R.id.lglottery_pop_closed);
		TextView tv_guizhe = (TextView) popView.findViewById(R.id.tv_guizhe);
		System.out.println("activity_rule=======0============"+activity_rule);
//		activity_rule = "活动规则内容";
		tv_guizhe.setText(activity_rule);
		closed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}
	
	/**
	 * 获取登录签名
	 * @param order_no 
	 */
	private void userloginqm() {
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
//						System.out.println("登录签名==================="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							login_sign = obj.getString("login_sign");
							point = obj.getString("point");
						}else{
						}
//						SharedPreferences spPreferences = getSharedPreferences("longuserset",MODE_PRIVATE);
//						Editor editor = spPreferences.edit();
//						editor.putString("login_sign", login_sign);
//						editor.commit();
//						getjiangxiang(login_sign);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);
	}
	
	
	
	/**
	 * 输出刮一刮奖
	 * @param login_sign 
	 */
	ArrayList list_data2;
	private void getjiangxiang(){
		list_data2 = new ArrayList();
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_id = spPreferences.getString("user_id", "");
			String user_name = spPreferences.getString("user", "");
//			String login_sign = spPreferences.getString("login_sign", "");
//			String login_sign = getIntent().getStringExtra("login_sign");
			String strUrlone = RealmName.REALM_NAME_LL + 
					"/get_article_activity_award?user_id="+user_id+"&user_name="+user_name+"&article_id=7825&sign="+login_sign+"";
//			System.out.println("======输出抽奖幸奖项============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出抽奖幸奖项============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
							JSONObject obct = object.getJSONObject("data");
							    id = obct.getString("id");
//								String title = obct.getString("title");
//								String drawn = obct.getString("drawn");
								System.out.println("id-----------------------------------"+id);
								for (int i = 0; i < list.size(); i++) {
									if (list.get(i).id.equals(id)) {
										drawn = list.get(i).drawn;
									}
								}
//								id_ll = id;
//								drawn_ll = drawn;
//								list_data2.add(drawn);
								
								System.out.println("drawn-----------------------------------"+drawn);
//								start_layout.setVisibility(View.GONE);
//								guayigua_buju.setVisibility(View.VISIBLE);
								try {
//								setContentView(R.layout.activity_guayigua);
								getintner();
//								View convertView = LinearLayout.inflate(GuaYiGuaActivity.this,R.layout.guayigua_item, null);
//								LinearLayout gua_jiang = (LinearLayout) convertView.findViewById(R.id.gua_jiang2);
//								MyGridView gridView2=(MyGridView)findViewById(R.id.gridview2);
//								ListView list_tuanjia = (ListView) findViewById(R.id.list_tuanjia);
//								GuaYiGuaAdapter adapter = new GuaYiGuaAdapter(list_data2,GuaYiGuaActivity.this);
//								list_tuanjia.setAdapter(adapter);
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
						}else{
							Toast.makeText(GuaYiGuaActivity.this, info, 200).show();
						}
						System.out.println("======输出抽奖幸奖项=======id======"+id);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
				
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("======访问接口失败============="+arg1);
//					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
				}
			}, GuaYiGuaActivity.this);
			
	}
	
	/**
	 * 输出刮一刮奖详情
	 */
	private void getjiangxiangxq() {
		list = new ArrayList<XsgyListData>();
		String strUrlone = RealmName.REALM_NAME_LL + "/get_article_model?id=7825";
//			System.out.println("======输出抽奖详情============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出抽奖详情============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
							JSONObject obct = object.getJSONObject("data");
							org.json.JSONArray jsonArray = obct.getJSONArray("activity_award");
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jobject = jsonArray.getJSONObject(i);
								XsgyListData spList = new XsgyListData();
								spList.id = jobject.getString("id");
								spList.title = jobject.getString("title");
								spList.drawn = jobject.getString("drawn");
								list.add(spList);
							 }
							JSONObject obct1 = obct.getJSONObject("activity");						
							activity_rule = obct1.getString("activity_rule");
							System.out.println("activity_rule==================="+activity_rule);
						}else{
							Toast.makeText(GuaYiGuaActivity.this, info, 200).show();
						}
						System.out.println("======list.size()============="+list.size());

//						System.out.println("id-----------------------------------"+id);
//						for (int i = 0; i < list.size(); i++) {
//							if (list.get(i).id.equals(id)) {
//								drawn = list.get(i).drawn;
//							}
//						}
//						System.out.println("drawn-----------------------------------"+drawn);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
				
				
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("======访问接口失败============="+arg1);
//					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
				}
			}, GuaYiGuaActivity.this);
	}
	
	/**
	 * 输出内容
	 */
//	private void getguizhe() {
////		list = new ArrayList<XsgyListData>();
//		String strUrlone = RealmName.REALM_NAME_LL + "/get_article_model?id=7285";
//			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
//				public void onSuccess(int arg0, String arg1) {
//					try {
//						System.out.println("======输出抽奖列表============="+arg1);
//						JSONObject object = new JSONObject(arg1);
//						String status = object.getString("status");
//						String info = object.getString("info");
//						if (status.equals("y")) {
////							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
//							JSONObject obct = object.getJSONObject("data");
////							org.json.JSONArray jsonArray = obct.getJSONArray("activity");
//							JSONObject obct1 = obct.getJSONObject("activity");						
////							for(int i=0;i<jsonArray.length();i++){
////								JSONObject jobject = jsonArray.getJSONObject(i);
////								XsgyListData spList = new XsgyListData();
////								spList.id = jobject.getString("id");
//								activity_rule = obct1.getString("activity_rule");
////								list.add(spList);
////							 }
//								System.out.println("activity_rule==================="+activity_rule);
//						}else{
////							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				};
//				
//				@Override
//				public void onFailure(Throwable arg0, String arg1) {
//					// TODO Auto-generated method stub
//					super.onFailure(arg0, arg1);
//					System.out.println("======访问接口失败============="+arg1);
////					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
//				}
//			},getApplicationContext());
//			
//	}
}
