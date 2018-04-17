package com.hengyushop.airplane;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.airplane.AirOrderSendActivity;
import com.hengyushop.demo.airplane.AirStand;
import com.hengyushop.demo.airplane.CityDB;
import com.hengyushop.demo.airplane.FlyResult;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AirPlaneTicketInfromationActivity extends BaseActivity {
	private FlyResult flyResult;
	private TextView fly_detail_local, fly_detail_canl;
	private TextView fly_detail_starttime, fly_detail_endtime;
	private TextView fly_detail_endlocal, fly_detail_startlocal,
			fly_detail_all;
	private TextView fly_detail_bx, fly_detail_ry, fly_detail_price;
	private TextView fly_detail_gg, fly_detail_qz, fly_detail_tp;
	private TextView fly_detail_cangname;
	private Button check_ok;
	private AirStand stand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplane_ticket_information);
		if (getIntent().hasExtra("flyBean")) {
			flyResult = (FlyResult) getIntent().getExtras().getSerializable(
					"flyBean");
			stand = (AirStand) getIntent().getExtras().getSerializable("bean");
			init(flyResult);
		}
	}

	/**
	 * 生成组件对象
	 */
	private void init(FlyResult bean) {
		check_ok = (Button) findViewById(R.id.check_ok);
		check_ok.setOnClickListener(clickListener);
		fly_detail_local = (TextView) findViewById(R.id.fly_detail_local);
		fly_detail_canl = (TextView) findViewById(R.id.fly_detail_canl);
		fly_detail_starttime = (TextView) findViewById(R.id.fly_detail_starttime);
		fly_detail_endtime = (TextView) findViewById(R.id.fly_detail_endtime);
		fly_detail_endlocal = (TextView) findViewById(R.id.fly_detail_endlocal);
		fly_detail_startlocal = (TextView) findViewById(R.id.fly_detail_startlocal);
		fly_detail_all = (TextView) findViewById(R.id.fly_detail_all);
		fly_detail_bx = (TextView) findViewById(R.id.fly_detail_bx);
		fly_detail_ry = (TextView) findViewById(R.id.fly_detail_ry);
		fly_detail_price = (TextView) findViewById(R.id.fly_detail_price);
		fly_detail_gg = (TextView) findViewById(R.id.fly_detail_gg);
		fly_detail_cangname = (TextView) findViewById(R.id.fly_detail_cangname);
		fly_detail_tp = (TextView) findViewById(R.id.fly_detail_tp);
		fly_detail_qz = (TextView) findViewById(R.id.fly_detail_qz);
		loadBase(bean);
		fly_detail_price.setText("￥" + bean.getPrice());
		fly_detail_bx.setText("￥" + bean.getInsurance());
		fly_detail_ry.setText("￥" + bean.getAudletAirportTax() + "/" + "￥"
				+ bean.getAudletFuelTax());
		CityDB db0 = new CityDB(getApplicationContext());
		CityDB db2 = new CityDB(getApplicationContext());
		fly_detail_startlocal.setText(db0.getJicBySam(bean.getOrgCity())
				+ bean.getOrgJetquay());
		fly_detail_endlocal.setText(db2.getJicBySam(bean.getDstCity())
				+ bean.getDstJetquay());
		fly_detail_starttime.setText(bean.getFirstTime());
		fly_detail_endtime.setText(bean.getEndTime());
		fly_detail_cangname.setText(bean.getCangName());
		SimpleDateFormat dateFormat0 = new SimpleDateFormat("HH:mm");

		try {
			Date d0 = dateFormat0.parse(bean.getFirstTime());
			Date d1 = dateFormat0.parse(bean.getEndTime());
			long between = (d1.getTime() - d0.getTime()) / 1000;// 除以1000是为了转换成秒
			long day1 = between / (24 * 3600);
			long hour1 = between % (24 * 3600) / 3600;
			long minute1 = between % 3600 / 60;
			fly_detail_all.setText("" + day1 + "天" + hour1 + "小时" + minute1
					+ "分");
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date d = null;
		try {
			try {
				d = dateFormat.parse(bean.getDate());
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int w = cal.get(Calendar.DAY_OF_WEEK);
		fly_detail_canl.setText(bean.getDate() + " " + weekDays[w - 1]);
		CityDB db = new CityDB(getApplicationContext());
		CityDB db1 = new CityDB(getApplicationContext());
		fly_detail_local.setText(db.getCinBySam(bean.getOrgCity()) + "―"
				+ db1.getCinBySam(bean.getDstCity()));

	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.check_ok:
					// 确认信息无误
					Intent intent = new Intent(
							AirPlaneTicketInfromationActivity.this,
							AirOrderSendActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("flyBean", flyResult);
					intent.putExtras(bundle);
					startActivity(intent);
					break;

				default:
					break;
			}
		}
	};

	/**
	 * 加载信息
	 */
	private void loadBase(FlyResult bean) {
		RequestParams params = new RequestParams();
		// airlineCode=CA&classCode=Y&depDate=2013-11-01&depCode=SZX&arrCode=SHA
		params.put("airlineCode", bean.getFlightNo().substring(0, 2));
		params.put("classCode", bean.getSeatCode());
		params.put("depDate", bean.getDate());
		params.put("depCode", bean.getOrgCity());
		params.put("arrCode", bean.getDstCity());
		System.out.println("airlineCode=" + bean.getFlightNo() + "&classCode="
				+ bean.getSeatCode() + "&depDate=" + bean.getDate()
				+ "&depCode=" + bean.getOrgCity() + "&arrCode="
				+ bean.getDstCity());
		String url = "/mi/flightTicket.ashx?act=GetModifyAndRefundStipulates";
		AsyncHttp.post(RealmName.REALM_NAME + url, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("Data");
							int len = jsonArray.length();
							String param[] = new String[3];
							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								param[0] = object.getString("RefundStipulate");
								param[1] = object.getString("ModifyStipulate");
								param[2] = object.getString("ChangeStipulate");
							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = param;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, getApplicationContext());
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					String param[] = (String[]) msg.obj;
					fly_detail_gg.setText(getString(R.string.fly_detail_gg,
							param[0]));
					fly_detail_qz.setText(getString(R.string.fly_detail_qz,
							param[1]));
					fly_detail_tp.setText(getString(R.string.fly_detail_tp,
							param[2]));
					break;

				default:
					break;
			}
		};
	};
}
