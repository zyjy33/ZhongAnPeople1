package com.hengyushop.demo.airplane;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.Common;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DialogCang {
	private ImageView deteView;
	/**
	 * with dialog
	 *
	 * @author zhuchao
	 */
	private Dialog dialog;// 对话框
	private Context context;
	private Handler handler;
	private CalendarView calendar;
	private ListView fly_cang_list;
	private FlyCangAdapter adpater;
	private Bundle bundle;
	private ArrayList<FlyCangItem> listCangItems = new ArrayList<FlyCangItem>();

	public DialogCang(Context context, Handler handler, Bundle bundle) {

		this.context = context;
		this.handler = handler;
		this.bundle = bundle;
		cityDialog(context);
	}

	private Handler cangHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					ArrayList<FlyCangItem> list = (ArrayList<FlyCangItem>) msg.obj;
					adpater.upDataAdapter(list);
					break;
				default:
					break;
			}
		};
	};

	/**
	 * create dialog with this xml
	 *
	 * @param context
	 */
	private void cityDialog(Context context) {
		dialog = new Dialog(context, R.style.mydialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.fly_cang,
				null);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(false);
		adpater = new FlyCangAdapter(context, listCangItems, handler);
		deteView = (ImageView) view.findViewById(R.id.exit_dialog);
		deteView.setOnClickListener(clickListener);
		fly_cang_list = (ListView) view.findViewById(R.id.fly_cang_list);
		fly_cang_list.setAdapter(adpater);
		SharedPreferences preferences = context.getSharedPreferences("logo",
				Activity.MODE_PRIVATE);
		String bossUid = preferences.getString("bossUid", "");
		FlyResult result = (FlyResult) bundle.getSerializable("fly_cang");
		// mi/FlightTicket.ashx?imei=11&act=GetSeatWithPriceAndCommisionItems&bossUid=111&seatItems_SerialCode=1&flightNo=HO1276
		RequestParams params = new RequestParams();
		// Map<String, String> params = new HashMap<String, String>();
		params.put("imei", Common.IMEI);
		params.put("seatItems_SerialCode", result.getSeatItems_SerialCode());
		params.put("bossUid", bossUid);
		params.put("flightNo", result.getFlightNo());
		String ur = "/mi/FlightTicket.ashx?act=GetSeatWithPriceAndCommisionItems";
		AsyncHttp.post(RealmName.REALM_NAME + ur, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						parse(arg1);
					}
				}, context);

	}

	private void parse(String arg1) {


		ArrayList<FlyCangItem> tempItems = new ArrayList<FlyCangItem>();
		try {
			JSONObject jsonObject = new JSONObject(arg1);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				FlyCangItem item = new FlyCangItem();
				item.setDiscount(object.getString("discount"));
				item.setFlightNo(object.getString("flightNo"));
				item.setParPrice(object.getString("parPrice"));
				item.setSeatCode(object.getString("seatCode"));
				item.setSeatMsg(object.getString("seatMsg"));
				item.setSeatStatus(object.getString("seatStatus"));
				item.setSeatType(object.getString("seatType"));
				item.setSettlePrice(object.getString("settlePrice"));
				tempItems.add(item);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = tempItems;
			cangHandler.sendMessage(msg);
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			switch (arg0.getId()) {
				case R.id.exit_dialog:
					closeDialog();
					break;

				default:
					break;
			}
		}
	};

	/**
	 * close dialog
	 */
	private void closeDialog() {
		Message msg = new Message();
		msg.what = 3;
		handler.sendMessage(msg);
	}

	/**
	 * 发送消息
	 *
	 * @param MSG
	 */
	private void sendMsg(int MSG) {
		Message msg = new Message();
		msg.what = MSG;
		handler.sendMessage(msg);
	}

	public Dialog show() {
		dialog.show();
		return dialog;
	}

	public Dialog getDialog() {
		return dialog;
	}
}
