package com.hengyushop.demo.my;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.example.uploadpicdemo.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.DBFengXiangActivity;
import com.hengyushop.demo.home.FenXiangActivity;
import com.hengyushop.demo.home.MainDuiMianllActivity;
import com.hengyushop.demo.home.MianDuiMianSySActivity;
import com.hengyushop.demo.home.MyChuangYouActivity;
import com.hengyushop.demo.home.MyJuFenActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;

/**
 * 服务顾问
 *
 * @author Administrator
 *
 */
public class ChuangKeUserActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_fanhui, img_menu, im_je1, im_je2, im_je3, im_je4,
			im_je5, im_je6, im_je7, im_je8, im_je9, im_je10, im_weizhi1,
			im_weizhi2, im_weizhi3, im_weizhi4, im_weizhi5, im_weizhi6,
			im_weizhi7, im_weizhi8, im_weizhi9, im_weizhi10;
	private ImageView iv_mianduimian;
	private Button fanhui, btn_chongzhi;
	private LinearLayout adv_pager, adv_pager1, index_item0;
	private EditText et_chongzhi;
	String user_name, user_id;
	private SharedPreferences spPreferences;
	private TextView tv_monney1, tv_monney2, tv_monney3, tv_monney4,
			tv_monney5, tv_monney6, tv_monney7, tv_monney8, tv_monney9,
			tv_monney10, tv_dengji, tv_dengji2, tv_shouyi1, tv_shouyi2,
			tv_shouyi3;
	int reserves, reserveb;
	String reserves1;
	RelativeLayout rl_tupian;
	String erweima = "";
	Bitmap bitmap;// 二维码中间图片
	private int iv_halfWidth = 20;// 显示中间图片的宽度的一半
	Bitmap mBitmap;// 二维码图片;
	Bitmap bitmap_tx, bitmap_touxiang;
	String avatar = "";
	String touxiang = "";
	private LinearLayout ll_jufen, ll_mdmtg, ll_fenxiang, ll_fzjf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chuangke_title_ok);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		avatar = spPreferences.getString("avatar", "");
		System.out.println("avatar=============" + avatar);
		if (!avatar.equals("")) {
			new Thread(getPicByUrl).start();
		}
		Initialize();
		userxinxi();
		// getmonney1();
		loadguanggao();
	}

	Runnable getPicByUrl = new Runnable() {
		@Override
		public void run() {
			try {
				String img_url2 = RealmName.REALM_NAME_HTTP + avatar;
				System.out.println("img_url2==============" + img_url2);
				bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
				// Bitmap bitMap_tx =
				// Utils.toRoundBitmap(bitmap_touxiang,null);//
				// 这个时候的图片已经被处理成圆形的了
				// touxiang = BitUtil.bitmaptoString(bitMap_tx);
				// System.out.println("touxiang=============="+touxiang);
				System.out.println("bitmap_touxiang=============="
						+ bitmap_touxiang);
				bitmap_tx = bitmap_touxiang;

				try {
					System.out.println("avatar=============" + avatar);
					System.out.println("bitmap_tx1=============" + bitmap_tx);
					if (!avatar.equals("")) {
						// new Thread(getPicByUrl).start();
						bitmap_tx = bitmap_touxiang;
					} else {
						bitmap_tx = BitmapFactory.decodeResource(
								getResources(), R.drawable.app_zams);
					}

					System.out.println("bitmap_tx=============" + bitmap_tx);
					LogoConfig logoConfig = new LogoConfig();
					bitmap = logoConfig.modifyLogo(
							BitmapFactory.decodeResource(getResources(),
									R.drawable.white_bg), bitmap_tx);
					// 缩放图片，用到矩阵去做
					Matrix matrix = new Matrix();
					float sx = (float) 2 * iv_halfWidth / bitmap.getWidth();
					float sy = (float) 2 * iv_halfWidth / bitmap.getHeight();
					matrix.setScale(sx, sy);
					// 生成缩放后的图片
					bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix,
							false);

					String content = "H45642FGFh123rty:" + user_name;// Sfgh45rqer123hgert
					// String content = user_name;
					System.out.println("content=============" + content);
					try {
						mBitmap = createBitmap(new String(content.getBytes(),
								"ISO-8859-1"));
						erweima = BitUtil.bitmaptoString(mBitmap);
						System.out.println("erweima=============" + erweima);
					} catch (WriterException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

	/**
	 * 控件初始化
	 */
	private void Initialize() {
		try {
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			Button enter_shop = (Button) findViewById(R.id.enter_shop);
			// enter_shop.getBackground().setAlpha(50);
			enter_shop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent4 = new Intent(ChuangKeUserActivity.this,
							Webview1.class);
					intent4.putExtra("ylsc_id", "5977");
					startActivity(intent4);
				}
			});
			fanhui = (Button) findViewById(R.id.fanhui);
			btn_chongzhi = (Button) findViewById(R.id.btn_chongzhi);
			fanhui.setOnClickListener(this);
			btn_chongzhi.setOnClickListener(this);
			index_item0.setOnClickListener(this);
			img_menu = (ImageView) findViewById(R.id.img_menu);
			tv_shouyi1 = (TextView) findViewById(R.id.tv_shouyi1);
			tv_shouyi2 = (TextView) findViewById(R.id.tv_shouyi2);
			tv_shouyi3 = (TextView) findViewById(R.id.tv_shouyi3);

			et_chongzhi = (EditText) findViewById(R.id.et_chongzhi);
			tv_monney1 = (TextView) findViewById(R.id.tv_monney1);
			tv_monney2 = (TextView) findViewById(R.id.tv_monney2);
			tv_monney3 = (TextView) findViewById(R.id.tv_monney3);
			tv_monney4 = (TextView) findViewById(R.id.tv_monney4);
			tv_monney5 = (TextView) findViewById(R.id.tv_monney5);
			tv_monney6 = (TextView) findViewById(R.id.tv_monney6);
			tv_monney7 = (TextView) findViewById(R.id.tv_monney7);
			tv_monney8 = (TextView) findViewById(R.id.tv_monney8);
			tv_monney9 = (TextView) findViewById(R.id.tv_monney9);
			tv_monney10 = (TextView) findViewById(R.id.tv_monney10);
			im_weizhi1 = (ImageView) findViewById(R.id.im_weizhi1);
			im_weizhi2 = (ImageView) findViewById(R.id.im_weizhi2);
			im_weizhi3 = (ImageView) findViewById(R.id.im_weizhi3);
			im_weizhi4 = (ImageView) findViewById(R.id.im_weizhi4);
			im_weizhi5 = (ImageView) findViewById(R.id.im_weizhi5);
			im_weizhi6 = (ImageView) findViewById(R.id.im_weizhi6);
			im_weizhi7 = (ImageView) findViewById(R.id.im_weizhi7);
			im_weizhi8 = (ImageView) findViewById(R.id.im_weizhi8);
			im_weizhi9 = (ImageView) findViewById(R.id.im_weizhi9);
			im_weizhi10 = (ImageView) findViewById(R.id.im_weizhi10);
			im_je1 = (ImageView) findViewById(R.id.im_je1);
			im_je2 = (ImageView) findViewById(R.id.im_je2);
			im_je3 = (ImageView) findViewById(R.id.im_je3);
			im_je4 = (ImageView) findViewById(R.id.im_je4);
			im_je5 = (ImageView) findViewById(R.id.im_je5);
			im_je6 = (ImageView) findViewById(R.id.im_je6);
			im_je7 = (ImageView) findViewById(R.id.im_je7);
			im_je8 = (ImageView) findViewById(R.id.im_je8);
			im_je9 = (ImageView) findViewById(R.id.im_je9);
			im_je10 = (ImageView) findViewById(R.id.im_je10);
			tv_dengji = (TextView) findViewById(R.id.tv_dengji);
			tv_dengji2 = (TextView) findViewById(R.id.tv_dengji2);

			ImageView iv_zhuti1 = (ImageView) findViewById(R.id.iv_zhuti1);
			ImageView iv_zhuti2 = (ImageView) findViewById(R.id.iv_zhuti2);
			ImageView iv_zhuti3 = (ImageView) findViewById(R.id.iv_zhuti3);
			ImageView iv_zhuti4 = (ImageView) findViewById(R.id.iv_zhuti4);
			iv_zhuti1.setBackgroundResource(R.drawable.juyou);
			iv_zhuti2.setBackgroundResource(R.drawable.mianduim);
			iv_zhuti3.setBackgroundResource(R.drawable.shejiao);
			iv_zhuti4.setBackgroundResource(R.drawable.jiqiao);
			ll_jufen = (LinearLayout) findViewById(R.id.ll_jufen);
			ll_mdmtg = (LinearLayout) findViewById(R.id.ll_mdmtg);
			ll_fenxiang = (LinearLayout) findViewById(R.id.ll_fenxiang);
			ll_fzjf = (LinearLayout) findViewById(R.id.ll_fzjf);
			ll_mdmtg.setOnClickListener(this);
			ll_fenxiang.setOnClickListener(this);
			ll_jufen.setOnClickListener(this);
			ll_fzjf.setOnClickListener(this);

			// iv_mianduimian = (ImageView) findViewById(R.id.iv_mianduimian);
			// iv_mianduimian.setOnClickListener(this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// System.out.println("======haoma============"+haoma);
	// }

	public void userxinxi() {
		try {

			String strUrlone = RealmName.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			System.out.println("======11=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out
								.println("======66输出用户资料=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.user_name = obj.getString("user_name");
							data.user_code = obj.getString("user_code");
							data.agency_id = obj.getInt("agency_id");
							data.amount = obj.getString("amount");
							data.pension = obj.getString("pension");
							data.packet = obj.getString("packet");
							data.point = obj.getString("point");
							data.group_id = obj.getString("group_id");
							data.login_sign = obj.getString("login_sign");// reserves

							data.reserves = obj.getInt("reserves");
							data.reserveb = obj.getInt("reserveb");
							reserves = data.reserves;
							reserveb = data.reserveb;
							TextView tv_beihuojin = (TextView) findViewById(R.id.tv_beihuojin);
							// DecimalFormat decimalFormat = new
							// DecimalFormat();
							// reserves1 = decimalFormat.format(reserves);
							String monney = String.valueOf(reserveb);
							System.out.println("reserveb----------" + reserveb);
							tv_beihuojin.setText(monney + "元");
							panduan();
						} else {

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, getApplicationContext());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @param
	 *
	 */
	public void getmonney1() {

		String strUrlone = RealmName.REALM_NAME_LL
				+ "/get_payrecord_expenses_sum?" + "to_user_id=" + user_id
				+ "&fund_id=1&expenses_id=6";
		System.out.println("======11=============" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======11=============" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					JSONObject obj = object.getJSONObject("data");
					if (status.equals("y")) {
						String pensions = obj.getString("pensions");
						tv_shouyi1.setText(pensions + "元");
						getmonney2();
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, ChuangKeUserActivity.this);

	}

	/**
	 * @param
	 *
	 */
	public void getmonney2() {

		String strUrlone = RealmName.REALM_NAME_LL
				+ "/get_payrecord_expenses_sum?" + "to_user_id=" + user_id
				+ "&fund_id=1&expenses_id=10";
		System.out.println("======11=============" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======22=============" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					JSONObject obj = object.getJSONObject("data");
					if (status.equals("y")) {
						String pensions = obj.getString("pensions");
						tv_shouyi2.setText(pensions + "元");
						getmonney3();
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, ChuangKeUserActivity.this);

	}

	/**
	 * @param expenses_id
	 *
	 */
	public void getmonney3() {

		String strUrlone = RealmName.REALM_NAME_LL
				+ "/get_payrecord_expenses_sum?" + "to_user_id=" + user_id
				+ "&fund_id=1&expenses_id=5";
		System.out.println("======11=============" + strUrlone);
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("======33=============" + arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					JSONObject obj = object.getJSONObject("data");
					if (status.equals("y")) {
						String pensions = obj.getString("pensions");
						tv_shouyi3.setText(pensions + "元");
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, ChuangKeUserActivity.this);

	}

	private void panduan() {
		// TODO Auto-generated method stub
		try {

			// int yue = Integer.parseInt(reserves1);
			System.out.println("----------" + reserves);
			if (reserves < 500) {
				int zdj_monney = 500;
				int monney = zdj_monney - reserves;
				im_je1.setVisibility(View.VISIBLE);
				tv_monney1.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi1
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 3000) {
				int zdj_monney = 3000;
				int monney = zdj_monney - reserves;
				im_je2.setVisibility(View.VISIBLE);
				tv_monney2.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi2
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 5000) {
				int zdj_monney = 5000;
				int monney = zdj_monney - reserves;
				im_je3.setVisibility(View.VISIBLE);
				tv_monney3.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi3
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 10000) {
				int zdj_monney = 10000;
				int monney = zdj_monney - reserves;
				im_je4.setVisibility(View.VISIBLE);
				tv_monney4.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi4
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 20000) {
				int zdj_monney = 20000;
				int monney = zdj_monney - reserves;
				im_je5.setVisibility(View.VISIBLE);
				tv_monney5.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi5
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 30000) {
				int zdj_monney = 30000;
				int monney = zdj_monney - reserves;
				im_je6.setVisibility(View.VISIBLE);
				tv_monney6.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi6
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 40000) {
				int zdj_monney = 40000;
				int monney = zdj_monney - reserves;
				im_je7.setVisibility(View.VISIBLE);
				tv_monney7.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi7
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 50000) {
				int zdj_monney = 50000;
				int monney = zdj_monney - reserves;
				im_je8.setVisibility(View.VISIBLE);
				tv_monney8.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi8
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 60000) {
				int zdj_monney = 60000;
				int monney = zdj_monney - reserves;
				im_je9.setVisibility(View.VISIBLE);
				tv_monney9.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi9
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			} else if (reserves < 70000) {
				int zdj_monney = 70000;
				int monney = zdj_monney - reserves;
				im_je10.setVisibility(View.VISIBLE);
				tv_monney10.setTextColor(Color.RED);
				// tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi10
						.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				// tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
				String reserves_ll = String.valueOf(reserveb);
				tv_dengji.setText(reserveb + "元");
				tv_dengji2.setText(monney + "元");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.fanhui:
				finish();
				break;
			case R.id.ll_mdmtg:
				System.out.println("erweima=============" + erweima);
				Intent intent = new Intent(ChuangKeUserActivity.this,
						MianDuiMianSySActivity.class);
				intent.putExtra("erweima", erweima);
				intent.putExtra("saoyisao", "1");
				startActivity(intent);
				break;
			case R.id.ll_jufen:
				Intent intent1 = new Intent(ChuangKeUserActivity.this,
						MyChuangYouActivity.class);
				intent1.putExtra("fensi", "1");// get_user_child_all_list
				startActivity(intent1);
				break;
			case R.id.ll_fenxiang:
				user_name = spPreferences.getString("user", "");
				if (user_name.equals("")) {
					Intent intentll = new Intent(ChuangKeUserActivity.this,
							UserLoginActivity.class);
					startActivity(intentll);
				} else {
					try {

						// SoftWarePopuWindow(ll_mdmtg, ChuangKeUserActivity.this);
						// Intent intentll = new
						// Intent(ChuangKeUserActivity.this,DBFengXiangActivity.class);
						// startActivity(intentll);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;
			case R.id.ll_fzjf:
				Intent intent4 = new Intent(ChuangKeUserActivity.this,
						Webview1.class);
				intent4.putExtra("web_id", "10332");
				startActivity(intent4);
				break;
			// case R.id.btn_login:
			// Intent intent = new
			// Intent(ChuangKeUserActivity.this,ShengJiCkActivity.class);
			// // Intent intent = new
			// Intent(ChuangKeUserActivity.this,ChongZhiActivity.class);
			// startActivity(intent);
			// break;
			case R.id.btn_chongzhi:
				try {

					// Intent intent = new
					// Intent(ChuangKeActivity.this,ShengJiCkActivity.class);
					String chongzhi = et_chongzhi.getText().toString().trim();
					if (chongzhi.equals("")) {
						Toast.makeText(ChuangKeUserActivity.this, "充值不能为空", Toast.LENGTH_SHORT)
								.show();
					} else {
						int cz = Integer.parseInt(chongzhi);
						if (cz < 100) {
							Toast.makeText(ChuangKeUserActivity.this, "充值不能小于100",
									Toast.LENGTH_SHORT).show();
						} else {
							Intent intent0 = new Intent(ChuangKeUserActivity.this,
									ChongZhiActivity.class);
							intent0.putExtra("chongzhi", chongzhi);
							startActivity(intent0);
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case R.id.enter_shop:
				Intent intent2 = new Intent(ChuangKeUserActivity.this,
						Webview1.class);
				intent2.putExtra("ck_id", "5977");
				startActivity(intent2);

				break;
			case R.id.index_item0:
				Intent intent3 = new Intent(ChuangKeUserActivity.this,
						BeiHuoJinMxActivity.class);
				intent3.putExtra("chuanke", "1");
				startActivity(intent3);

				break;

			default:
				break;
		}
	}

	/**
	 * 获取广告图片
	 */
	private void loadguanggao() {
		try {

			// 广告滚动
			AsyncHttp.get(RealmName.REALM_NAME_LL
							+ "/get_adbanner_list?advert_id=13",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									AdvertDao1 ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									String ad_url = ada.getAd_url();
									System.out.println("图片值是================"
											+ ad_url);

									ImageLoader imageLoader = ImageLoader
											.getInstance();
									imageLoader.displayImage(
											RealmName.REALM_NAME_HTTP + ad_url,
											img_menu);

									images.add(ada);
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
	 * 根据字符串生成二维码
	 *
	 * @param
	 * @return
	 * @throws WriterException
	 */
	private Bitmap createBitmap(String str) throws WriterException {
		// 生成而为矩阵，编码是指定大小，不要生成了图片在进行缩放，这样会导致模糊识别失败，就是扫描失败了。
		BitMatrix mBitMatrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300);// BarcodeFormat.QR_CODE-编码格式
		// 二维矩阵的宽高
		int w = mBitMatrix.getWidth();
		int h = mBitMatrix.getHeight();

		// 头像的宽度
		int halfw = w / 2;
		int halfh = h / 2;
		// 准备画二维码，把二维矩阵转换为一维数组，一直横着画
		int[] pixels = new int[w * h];// 数组长度就是矩阵的面积值
		for (int y = 0; y < h; y++) {
			int outputOffset = y * w;
			for (int x = 0; x < w; x++) {
				// 画一个普通的二维码
				// if (mBitMatrix.get(x, y)) {// 表示二维矩阵有值，对应画一个黑点
				// pixels[outputOffset + x] = 0xff000000;
				// } else {
				// pixels[outputOffset + x] = 0xffffffff;
				// }

				// 画一个有图片的二维码图片
				if (x > (halfw - iv_halfWidth) && x < (halfw + iv_halfWidth)
						&& y > (halfh - iv_halfWidth)
						&& y < (halfh + iv_halfWidth)) {// 中间图片的区域
					pixels[outputOffset + x] = bitmap.getPixel(x - halfw
							+ iv_halfWidth, y - halfh + iv_halfWidth);// 这里画图之后会很明显的显示出来
				} else {
					if (mBitMatrix.get(x, y)) {// 表示二维矩阵有值，对应画一个黑点
						pixels[outputOffset + x] = 0xff000000;
					} else {
						pixels[outputOffset + x] = 0xffffffff;
					}
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个300*300bitmap
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h);// 像素点、起始点、宽度、其起始像素、宽、高
		return bitmap;

	}
}
