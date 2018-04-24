package com.hengyushop.demo.home;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.ShengJiCkActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.YlyhData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;

/**
 * 养老银行明细
 *
 * @author Administrator
 *
 */
public class EndowmentBankMxActivity extends BaseActivity implements
		OnClickListener {
	private ListView vip_list;
	private LinearLayout index_item0, index_item1, index_item2;
	private ImageView ling_tip;
	String strUrlone;
	private SharedPreferences spPreferences;
	private TextView tv_ylyhye, tv_xfylj, tv_xfylj2;
	private TextView tv_yljye1, tv_yljye2, tv_yljye3, tv_yljye4, tv_yljye5;
	private TextView tv_lilv1, tv_lilv2, tv_lilv3, tv_lilv4, tv_lilv5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ylyhmx);// endowment_bank_activity
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		Initialize();
		// loadyue();
	}

	private void Initialize() {

		try {

			ling_tip = (ImageView) findViewById(R.id.ling_tip);
			tv_xfylj = (TextView) findViewById(R.id.tv_xfylj);
			tv_xfylj2 = (TextView) findViewById(R.id.tv_xfylj2);
			tv_ylyhye = (TextView) findViewById(R.id.tv_yljye);
			String pensions = getIntent().getStringExtra("pensions");
			String pensions_yue = getIntent().getStringExtra("pensions_yue");
			tv_xfylj.setText(pensions_yue + "元");// 本月消费养老金
			tv_xfylj2.setText(pensions_yue + "元");// 新投入养老金
			System.out.println("==========" + pensions);
			if (pensions != null) {
				tv_ylyhye.setText(pensions + "元");
			}

			tv_yljye1 = (TextView) findViewById(R.id.tv_yljye1);
			tv_yljye2 = (TextView) findViewById(R.id.tv_yljye2);
			tv_yljye3 = (TextView) findViewById(R.id.tv_yljye3);
			tv_yljye4 = (TextView) findViewById(R.id.tv_yljye4);
			tv_yljye5 = (TextView) findViewById(R.id.tv_yljye5);
			tv_lilv1 = (TextView) findViewById(R.id.tv_lilv1);
			tv_lilv2 = (TextView) findViewById(R.id.tv_lilv2);
			tv_lilv3 = (TextView) findViewById(R.id.tv_lilv3);
			tv_lilv4 = (TextView) findViewById(R.id.tv_lilv4);
			tv_lilv5 = (TextView) findViewById(R.id.tv_lilv5);

			String add1 = getIntent().getStringExtra("add1");
			String add2 = getIntent().getStringExtra("add2");
			String add3 = getIntent().getStringExtra("add3");
			String add4 = getIntent().getStringExtra("add4");
			String add5 = getIntent().getStringExtra("add5");
			System.out.println("add1==========" + add1);
			BigDecimal b1 = new BigDecimal(Double.parseDouble(add1));
			BigDecimal b2 = new BigDecimal(Double.parseDouble(add2));
			BigDecimal b3 = new BigDecimal(Double.parseDouble(add3));
			BigDecimal b4 = new BigDecimal(Double.parseDouble(add4));
			BigDecimal b5 = new BigDecimal(Double.parseDouble(add5));
			double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double f2 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double f3 = b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double f4 = b4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double f5 = b5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			tv_lilv1.setText(String.valueOf(f1));
			tv_lilv2.setText(String.valueOf(f2));
			tv_lilv3.setText(String.valueOf(f3));
			tv_lilv4.setText(String.valueOf(f4));
			tv_lilv5.setText(String.valueOf(f5));
			System.out.println("add2==========" + f1);
			// if (f1 > 0.00) {
			// tv_lilv1.setText(String.valueOf(f1));
			// tv_lilv2.setText(add2.substring(0,4));
			// tv_lilv3.setText(add3.substring(0,4));
			// tv_lilv4.setText(add4.substring(0,4));
			// tv_lilv5.setText(add5.substring(0,4));
			// }else {
			// tv_lilv1.setText(String.valueOf(f1));
			// tv_lilv2.setText(add2);
			// tv_lilv3.setText(add3);
			// tv_lilv4.setText(add4);
			// tv_lilv5.setText(add5);
			// }

			String pensions1 = getIntent().getStringExtra("pensions1");
			String pensions2 = getIntent().getStringExtra("pensions2");
			String pensions3 = getIntent().getStringExtra("pensions3");
			String pensions4 = getIntent().getStringExtra("pensions4");
			String pensions5 = getIntent().getStringExtra("pensions5");
			System.out.println("pensions1==========" + pensions1);
			BigDecimal pen1 = new BigDecimal(Double.parseDouble(pensions1));
			BigDecimal pen2 = new BigDecimal(Double.parseDouble(pensions2));
			BigDecimal pen3 = new BigDecimal(Double.parseDouble(pensions3));
			BigDecimal pen4 = new BigDecimal(Double.parseDouble(pensions4));
			BigDecimal pen5 = new BigDecimal(Double.parseDouble(pensions5));
			double ye1 = pen1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			double ye2 = pen2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			double ye3 = pen3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			double ye4 = pen4.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			double ye5 = pen5.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			tv_yljye1.setText(String.valueOf(ye1));
			tv_yljye2.setText(String.valueOf(ye2));
			tv_yljye3.setText(String.valueOf(ye3));
			tv_yljye4.setText(String.valueOf(ye4));
			tv_yljye5.setText(String.valueOf(ye5));

			System.out.println("ye1==========" + ye1);
			// if (!pensions1.equals("0")) {
			// tv_yljye1.setText(pensions1.substring(0,4));
			// tv_yljye2.setText(pensions2.substring(0,4));
			// tv_yljye3.setText(pensions3.substring(0,4));
			// tv_yljye4.setText(pensions4.substring(0,4));
			// tv_yljye5.setText(pensions5.substring(0,4));
			// }else {
			// tv_yljye1.setText(pensions1);
			// tv_yljye2.setText(pensions2);
			// tv_yljye3.setText(pensions3);
			// tv_yljye4.setText(pensions4);
			// tv_yljye5.setText(pensions5);
			// }

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 养老银行余额
	 */
//	private void loadyue() {
//		String user_name = spPreferences.getString("user", "");
//		strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="
//				+ user_name + "";
//		System.out.println("======11=============" + strUrlone);
//		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
//			public void onSuccess(int arg0, String arg1) {
//				try {
//					System.out.println("======输出用户资料=============" + arg1);
//					JSONObject object = new JSONObject(arg1);
//					String status = object.getString("status");
//					if (status.equals("y")) {
//						JSONObject obj = object.getJSONObject("data");
//						UserRegisterllData data = new UserRegisterllData();
//						data.user_name = obj.getString("user_name");
//						data.user_code = obj.getString("user_code");
//						data.agency_id = obj.getInt("agency_id");
//						data.amount = obj.getString("amount");
//						data.pension = obj.getString("pension");
//						data.packet = obj.getString("packet");
//						data.point = obj.getString("point");
//						data.group_id = obj.getString("group_id");
//
//						tv_ylyhye.setText(data.pension);// 养老银行余额
//
//					} else {
//
//					}
//				} catch (JSONException e) {
//
//					e.printStackTrace();
//				}
//			};
//		}, null);
//	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					break;

				default:
					break;
			}
		};
	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.index_item0:
				// Intent intent1 = new
				// Intent(ChuangKeActivity.this,ShengJiCkActivity.class);
				// // Intent intent = new
				// Intent(ChuangKeActivity.this,ChongZhiActivity.class);
				// startActivity(intent1);
				break;

			default:
				break;
		}

	}
}
