package com.hengyushop.demo.airplane;

import android.app.Dialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.AirPlaneTicketInfromationActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AirPlaneSelectTwoActivity extends BaseActivity {
	private TextView air_tag, air_num;
	private ListView mListView;
	private FlyResult tempFlyResult;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					System.out.println("数据有吗");
					// 飞机票查询完成
					ArrayList<FlyResult> list = (ArrayList<FlyResult>) msg.obj;
					FlyResultAdapter adapter = new FlyResultAdapter(
							getApplicationContext(), list, handler);
					mListView.setAdapter(adapter);
					air_tag.setText(stand.getTime() + "   " + stand.getFly_city()
							+ "-" + stand.getArr_city());
					air_num.setText("(共" + list.size() + "个结果)");
					break;
				case 2:
					// 弹出信息栏
					// mi/FlightTicket.ashx?imei=11&act=GetSeatWithPriceAndCommisionItems&bossUid=111&seatItems_SerialCode=1&flightNo=HO1276
					Bundle bundle = new Bundle();
					bundle.putSerializable("fly_cang", (Serializable) msg.obj);
					tempFlyResult = (FlyResult) msg.obj;
					showDialog(1, bundle);
				/*
				 * Intent intent = new Intent(FlyResultActivity.this,
				 * FlyDetailActivity.class); Bundle bundle = new Bundle();
				 * bundle.putSerializable("flyBean", (Serializable) msg.obj);
				 * intent.putExtras(bundle); startActivity(intent);
				 */
					break;
				case 4:
					FlyCangItem item = (FlyCangItem) msg.obj;
					tempFlyResult.setFlightNo(item.getFlightNo());
					tempFlyResult.setSeatCode(item.getSeatCode());
					tempFlyResult.setDiscount(item.getDiscount());
					tempFlyResult.setCangName(item.getSeatMsg());
					tempFlyResult.setPrice(item.getParPrice());
					Intent intent = new Intent(AirPlaneSelectTwoActivity.this,
							AirPlaneTicketInfromationActivity.class);
					Bundle bundle1 = new Bundle();
					bundle1.putSerializable("flyBean", tempFlyResult);
					bundle1.putSerializable("bean", stand);
					intent.putExtras(bundle1);
					startActivity(intent);
					dismissDialog(1);
					removeDialog(1);

					break;

				default:
					break;
			}
		};
	};

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		Dialog dialog = null;
		switch (id) {
			case 1:
				DialogCang dialogUtils = new DialogCang(
						AirPlaneSelectTwoActivity.this, handler, bundle);
				dialog = dialogUtils.show();
				break;
			default:
				break;
		}
		return dialog;
	};

	private AirStand stand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airplane_select_two);
		air_tag = (TextView) findViewById(R.id.air_tag);
		air_num = (TextView) findViewById(R.id.air_num);
		mListView = (ListView) findViewById(R.id.Bookbox_explistview); // 可扩展listView
		stand = (AirStand) getIntent().getExtras().getSerializable("bean");
		if (stand != null) {

			init(stand);

		}

	}

	// 初始化飞机列表
	private void init(AirStand stand) {
		/*
		 * Map<String, String> map = new HashMap<String, String>(); //
		 * date=2014-03-14&orgCity=SZX&dstCity=SHA map.put("date",
		 * stand.getTime()); map.put("orgCity", stand.getFly_code());
		 * map.put("dstCity", stand.getArr_code());
		 * System.out.println(stand.getFly_code
		 * ()+"-"+stand.getArr_code()+"-"+stand.getTime()); String url =
		 * "/mi/flightTicket.ashx?act=GetAvailableFlightWithPriceAndCommision";
		 * try { InputStream in =
		 * HttpClientUtil.postRequest(RealmName.REALM_NAME + url, map); String
		 * arg1 = new HttpUtils().convertStreamToString(in, "UTF-8");
		 * parse(arg1); } catch (Exception e) {   catch
		 * block e.printStackTrace(); }
		 */
		String url = "/mi/flightTicket.ashx?act=GetAvailableFlightWithPriceAndCommision";
		RequestParams params = new RequestParams();
		params.put("date", stand.getTime());
		params.put("orgCity", stand.getFly_code());
		params.put("dstCity", stand.getArr_code());
		AsyncHttp.post(RealmName.REALM_NAME + url, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						parse(arg1);
					}
				}, getApplicationContext());

	}

	/**
	 * 解析飞机票
	 *
	 * @param arg1
	 */
	private void parse(String arg1) {
		System.out.println(arg1);
		try {

			JSONObject jsonObject = new JSONObject(arg1);
			ArrayList<FlyResult> lists = new ArrayList<FlyResult>();
			if (jsonObject.getInt("status") == 0) {
				// 访问失败
				Toast.makeText(getApplicationContext(), "访问异常", 200).show();
			} else {
				JSONArray jsonArray = jsonObject.getJSONArray("Data");
				int len = jsonArray.length();
				for (int i = 0; i < len; i++) {
					FlyResult fly_item = new FlyResult();
					JSONObject object = jsonArray.getJSONObject(i);
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					Date startDate = dateFormat.parse(object
							.getString("DepTime"));
					Date endDate = dateFormat.parse(object
							.getString("ArriTime"));
					fly_item.setFirstTime(dateFormat.format(startDate));
					fly_item.setEndTime(dateFormat.format(endDate));
					fly_item.setFlyCompany(object.getString("AirlineName"));
					fly_item.setOrgJetquay(object.getString("OrgJetquay"));
					fly_item.setDstJetquay(object.getString("DstJetquay"));
					fly_item.setPrice(object.getString("BasePrice"));
					fly_item.setDiscount(String.valueOf(Double
							.parseDouble(object.getString("Discount")) * 10));

					fly_item.setOrgCity(object.getString("OrgCity"));
					fly_item.setDstCity(object.getString("DstCity"));
					fly_item.setFlightNo(object.getString("FlightNo"));
					SimpleDateFormat dateFormat1 = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					SimpleDateFormat dateFormat2 = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date d = dateFormat1.parse(object.getString("Date"));

					fly_item.setDate(dateFormat2.format(d));
					fly_item.setPlaneType(object.getString("PlaneType"));
					fly_item.setAudletAirportTax(object
							.getString("AudletAirportTax"));
					fly_item.setSeatItems_SerialCode(object
							.getString("SeatItems_SerialCode"));
					fly_item.setAudletFuelTax(object.getString("AudletFuelTax"));
					fly_item.setSeatCode(object.getString("SeatCode"));
					fly_item.setInsurance(object.getString("Insurance"));
					lists.add(fly_item);
				}
				Message msg = new Message();
				msg.what = 1;
				msg.obj = lists;
				handler.sendMessage(msg);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (java.text.ParseException e) {

			e.printStackTrace();
		}
	}

}
