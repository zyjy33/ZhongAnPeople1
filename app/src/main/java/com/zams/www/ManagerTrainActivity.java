package com.zams.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ManagerTrainDo;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.train.ManagerOrderAdapter;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

public class ManagerTrainActivity extends BaseActivity {
	private ListView train;
	private ManagerOrderAdapter orderAdapter;
	private WareDao wareDao;
	private String yth;
	private ArrayList<CardItem> banks;
	private String[] bankNames;
	private ArrayList<ManagerTrainDo> trainLists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.managet_train_layout);
		train = (ListView) findViewById(R.id.train);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		if (yth != null) {
			RequestParams params = new RequestParams();
			params.put("yth", yth);
			AsyncHttp.post(RealmName.REALM_NAME
							+ "/mi/TrainHandler.ashx?act=TrainTicketOrder", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							System.out.println(arg1);
							parse(arg1);
						}
					}, getApplicationContext());
		} else {
			Toast.makeText(getApplicationContext(), "未登录!", 200).show();
		}
	}

	private void parse(String result) {
		try {
			ArrayList<ManagerTrainDo> trainList = new ArrayList<ManagerTrainDo>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			int lens = jsonArray.length();
			for (int i = 0; i < lens; i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ManagerTrainDo trainDo = new ManagerTrainDo();
				trainDo.setArriveTime(object.getString("ArriveTime"));
				trainDo.setFromStation(object.getString("FromStationTrainName"));
				trainDo.setLishi(object.getString("LiShi"));
				trainDo.setOrderNum(object.getString("orderSerialNumber"));
				trainDo.setOrderTag(object.getString("orderStatusId"));
				trainDo.setOrderTime(object.getString("orderTime"));
				trainDo.setStartTime(object.getString("StartTime"));
				trainDo.setStatusTag(object.getString("orderStatusTmp"));
				trainDo.setToStation(object.getString("ToStationTrainName"));
				trainDo.setTrade_no(object.getString("UMPtrade_no"));
				trainDo.setCheci(object.getString("StationTrainCode"));
				trainDo.setPrice(object.getString("TotalPrice"));
				trainList.add(trainDo);
			}

			JSONArray array = jsonObject.getJSONArray("UserSignedBankList");
			int len = array.length();
			if (len != 0) {
				banks = new ArrayList<CardItem>();
				bankNames = new String[len + 1];
				for (int i = 0; i < len; i++) {
					JSONObject object2 = array.getJSONObject(i);
					CardItem item = new CardItem();
					item.setType(object2.getString("pay_type"));
					item.setBankName(object2.getString("gate_id"));
					item.setLastId(object2.getString("last_four_cardid"));
					item.setId(object2.getString("UserSignedBankID"));
					banks.add(item);
					bankNames[i] = ParseBank.parseBank(item.getBankName(),
							getApplicationContext())
							+ "("
							+ ParseBank.paseName(item.getType())
							+ ")"
							+ item.getLastId();
				}
				CardItem item = new CardItem();
				item.setBankName("-1");
				item.setId("-1");
				item.setLastId("-1");
				item.setType("-1");
				banks.add(item);
				bankNames[len] = "新支付方式";
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = trainList;
			handler.sendMessage(msg);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					trainLists = (ArrayList<ManagerTrainDo>) msg.obj;
					orderAdapter = new ManagerOrderAdapter(trainLists,
							getApplicationContext(), handler);
					train.setAdapter(orderAdapter);
					break;
				case 1:

					String trade_no = (String) msg.obj;
					System.out.println(trade_no);
					if (banks != null && banks.size() != 0) {
						// 表示是第二次支付
						System.out.println("写第二次支付");
						// initPopupWindow1();
						// showPopupWindow1(btn_OK);
						Intent intent = new Intent(ManagerTrainActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 1);
						bundle.putSerializable("trade_no", trade_no);
						bundle.putStringArray("bank_names", bankNames);
						bundle.putSerializable("bank_objs", banks);
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						// 表示首次支付
						Intent intent = new Intent(ManagerTrainActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 0);
						bundle.putSerializable("trade_no", trade_no);
						intent.putExtras(bundle);
						startActivity(intent);
						// initPopupWindow();
						// showPopupWindow(btn_OK);
					}
					break;
				default:
					break;
			}
		};
	};
}
