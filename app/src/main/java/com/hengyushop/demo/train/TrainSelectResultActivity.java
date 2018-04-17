package com.hengyushop.demo.train;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

public class TrainSelectResultActivity extends BaseActivity implements
		OnClickListener {
	private ListView listView;
	private TextView tv_shuaxuan, tv_paixu;
	private LinearLayout ll_paicha;
	private TextView tv1;
	private static LayoutInflater inflater;
	private static View view;
	private static PopupWindow pop;
	private int w, h, width, height;
	private ArrayList<ChePiaoData> lists;
	private String start, arrive, time;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					lists = (ArrayList<ChePiaoData>) msg.obj;
					TrainItemAdapter adapter = new TrainItemAdapter(
							getApplicationContext(), lists, imageLoader);
					listView.setAdapter(adapter);
					break;
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.train_select_result);
		example();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels; // 屏幕宽度（像素）
		height = metric.heightPixels; // 屏幕高度（像素）
		w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		start = getIntent().getExtras().getString("startStation");
		arrive = getIntent().getExtras().getString("arrvieStation");
		time = getIntent().getExtras().getString("time");
		RequestParams params = new RequestParams();
		params.put("from_station", start);
		params.put("to_station", arrive);
		params.put("queryDate", time);
		// params.put("userid", Common.TRAIN_USERID);
		// params.put("seckey", Common.TRAIN_SECKEY);
		// &queryDate=2014-06-26&from_station=VAP&to_station=GZQ
		AsyncHttp.post(RealmName.REALM_NAME
						+ "/mi/TrainHandler.ashx?yth=&act=QueryTrain", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println(arg1);

						getLists(arg1);

					}
				}, getApplicationContext());
	}

	/**
	 * 重新构造火车票的数据格式
	 *
	 * @param result
	 * @return
	 */
	private void getLists(String result) {
		result = result.replaceAll("-", "_");
		ArrayList<ChePiaoData> piaos = new ArrayList<ChePiaoData>();
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.getString("status").equals("0")) {
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			} else {
				JSONObject object1 = new JSONObject(jsonObject.getString("msg"));
				JSONObject jsonObject2 = object1.getJSONObject("data");
				JSONArray jsonArray = jsonObject2.getJSONArray("datas");
				int len = jsonArray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					ChePiaoData data = new ChePiaoData();
					data.setSeat_types(IsHas(object, "seat_types"));
					data.setFrom_station_no(IsHas(object, "from_station_no"));
					data.setTo_station_no(IsHas(object, "to_station_no"));
					data.setTrainNum(IsHas(object, "train_no"));
					data.setBest_seat(IsHas(object, "tz_num"));
					data.setBusiness(IsHas(object, "swz_num"));
					data.setEndStationCode(IsHas(object, "end_station_telecode"));
					data.setFromStation(IsHas(object, "from_station_name"));
					data.setFromStationCode(IsHas(object,
							"from_station_telecode"));
					data.setFromTime(IsHas(object, "start_time"));
					data.setHard_seat(IsHas(object, "yz_num"));
					data.setNone_seat(IsHas(object, "wz_num"));
					data.setHard_sleeper(IsHas(object, "yw_num"));
					data.setStart_train_date(IsHas(object, "start_train_date"));
					data.setOne_seat(IsHas(object, "zy_num"));
					data.setSoft_seat(IsHas(object, "rz_num"));
					data.setSoft_sleeper(IsHas(object, "rw_num"));
					data.setStartStationCode(IsHas(object,
							"start_station_telecode"));
					data.setTakeTime(IsHas(object, "lishi").replace(":", "时"));
					data.setToStation(IsHas(object, "to_station_name"));
					data.setToStationCode(IsHas(object, "to_station_telecode"));
					data.setToTime(IsHas(object, "arrive_time"));
					data.setTrainCode(IsHas(object, "station_train_code"));
					data.setTwo_seat(IsHas(object, "ze_num"));
					data.setVag_sleeper(IsHas(object, "gg_num"));
					data.setDay_difference(IsHas(object, "day_difference"));
					piaos.add(data);
				}
				Message msg = new Message();
				msg.what = 1;
				msg.obj = piaos;
				handler.sendMessage(msg);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 判断是否存在这样的数据，如果存在就解析，如果不存在就返回空值
	 *
	 * @param object
	 * @param param
	 * @return
	 */
	private String IsHas(JSONObject object, String param) {
		if (object.has(param)) {
			try {
				return object.getString(param);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private void example() {
		tv1 = (TextView) findViewById(R.id.tv1);
		listView = (ListView) findViewById(R.id.list_train);
		listView.setCacheColorHint(0);
		tv_shuaxuan = (TextView) findViewById(R.id.tv_shuaxuan);
		tv_paixu = (TextView) findViewById(R.id.tv_paixu);
		ll_paicha = (LinearLayout) findViewById(R.id.ll_paicha);
		tv_shuaxuan.setOnClickListener(this);
		tv_paixu.setOnClickListener(this);
		tv1.setText(getIntent().getStringExtra("add"));// 标题栏
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {/*
								 * Intent intent = new
								 * Intent(TrainSelectResultActivity.this,
								 * TrainInformationActivity.class);
								 * intent.putExtra("code",
								 * lists.get(arg2).getTrainCode());
								 * intent.putExtra("add",
								 * getIntent().getStringExtra("add"));
								 * startActivity(intent);
								 */

				Intent intent = new Intent(TrainSelectResultActivity.this,
						TrainDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("obj", lists.get(arg2));
				bundle.putString("time", time);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void PopuWindowFilter(View view2, Context context) {
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.train_select_filter, null);
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);

		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		pop.setTouchable(true); // 设置PopupWindow可触摸
		LinearLayout textView = (LinearLayout) view.findViewById(R.id.ll_01);
		textView.measure(w, h);
		int fwidth = textView.getMeasuredWidth();
		int fheight = textView.getMeasuredHeight();
		int pw = -(width - (fwidth + 1)) / 2;
		int ph = height - fheight - 70;

		if (!pop.isShowing()) {
			pop.showAtLocation(view2, Gravity.TOP, pw, ph);
		}
	}

	@SuppressWarnings("deprecation")
	private void PopuWindowSort(View view3, Context context) {

		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.train_select_sort, null);
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);

		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		pop.setTouchable(true); // 设置PopupWindow可触摸

		LinearLayout textView = (LinearLayout) view.findViewById(R.id.ll_01);
		textView.measure(w, h);
		int sheight = textView.getMeasuredHeight();

		int pw = width / 2;
		int ph = height - sheight - 70;

		if (!pop.isShowing()) {
			pop.showAtLocation(view3, Gravity.TOP, pw, ph);
		}
	}

	// private void listdata() {
	// String[] type = new String[] { "G110", "G2", "G112", "D318", "G114",
	// "G14", "G116", "G42", "G120", "G16" };
	// List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	// Map<String, String> lines = null;
	// for (int i = 0; i < type.length; i++) {
	// lines = new HashMap<String, String>();
	// lines.put("text", type[i]);
	// list.add(lines);
	// }
	// SimpleAdapter sa = new SimpleAdapter(TrainSelectResultActivity.this,
	// list, R.layout.train_listitem_select, new String[] { "text" },
	// new int[] { R.id.tv_train_number });
	// listView.setAdapter(sa);
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_shuaxuan:
				PopuWindowFilter(ll_paicha, TrainSelectResultActivity.this);
				break;
			case R.id.tv_paixu:
				PopuWindowSort(ll_paicha, TrainSelectResultActivity.this);
				break;

			default:
				break;
		}
	}

}
