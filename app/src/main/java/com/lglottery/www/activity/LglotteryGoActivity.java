package com.lglottery.www.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.adapter.LglotteryWheelAdapter;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.Lottery_Go;
import com.lglottery.www.domain.Lottery_Gobean;
import com.lglottery.www.widget.OnWheelChangedListener;
import com.lglottery.www.widget.OnWheelScrollListener;
import com.lglottery.www.widget.WheelView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LglotteryGoActivity extends BaseActivity {
	private Button lglottery_lottery_view;
	private WheelView lglottery_go1, lglottery_go2, lglottery_go3;
	private LinearLayout lglottery_lottery_content;
	private LinearLayout lottery_layout1, lottery_layout2, lottery_layout3;
	private TextView lottery_layout_view1, lottery_layout_view2,
			lottery_layout_view3;
	private SharedUtils sharedUtils;
	private ArrayList<Lottery_Go> parentLists;
	private String number;
	private TextView lglottery_lg_id;

	/**
	 * ��ѯ�齱��Ա
	 * 
	 * @param gamephaseorder
	 */
	private void connect(String gamephaseorder) {
		RequestParams params = new RequestParams();
		params.put("yth", sharedUtils.getStringValue("yth"));
		params.put("GamePhaseOrder", gamephaseorder);
		AsyncHttp.post(U.LOTTERY_GO, params, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				WLog.v("�齱���" + arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					ArrayList<Lottery_Go> parentList = new ArrayList<Lottery_Go>();
					int len = jsonArray.length();
					for (int i = 0; i < len; i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						Lottery_Go lottery_Go = new Lottery_Go();
						lottery_Go.setUserName(object.getString("username"));
						lottery_Go.setGameOrder(object
								.getString("GamePhaseOrder"));
						lottery_Go.setYth(object.getString("HengYuCode"));
						/*
						 * ����齱����������һ�����е�����
						 */
						switch (Integer.parseInt(jsonObject
								.getString("LotteryGameStatus"))) {
						case 0:

							break;
						case 1:
							lottery_Go.setProductItemId(object
									.getString("ProductItemId"));
							lottery_Go.setLotteryGameTypeId(object
									.getString("LotteryGameTypeId"));
							break;
						default:
							break;
						}

						ArrayList<Lottery_Gobean> childGobeans = new ArrayList<Lottery_Gobean>();
						JSONArray childArray = object
								.getJSONArray("HistoryInfo");
						int childLen = childArray.length();
						/*
						 * "LotteryGameTypeId": "3", "ProductItemId": "125",
						 * "proName": "����ƽ��"
						 */
						for (int j = 0; j < childLen; j++) {
							JSONObject childObject = childArray
									.getJSONObject(j);
							Lottery_Gobean lottery_Gobean = new Lottery_Gobean();
							lottery_Gobean.setItemId(childObject
									.getString("ProductItemId"));
							lottery_Gobean.setProName(childObject
									.getString("proName"));
							lottery_Gobean.setTypeId(childObject
									.getString("LotteryGameTypeId"));
							childGobeans.add(lottery_Gobean);
						}
						lottery_Go.setArrayList(childGobeans);
						parentList.add(lottery_Go);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = parentList;
						handler.sendMessage(msg);

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}
		}, getApplicationContext());
	}

	/**
	 * ��������Ķ���ʵ��
	 */
	private void init() {
		sharedUtils = new SharedUtils(getApplicationContext(),
				Config.LOGIN_STATUS);
		Bundle bundle = getIntent().getExtras();
		if (bundle.containsKey("gamephaseorder")) {
			// ���������ĳ齱����
			number = bundle.getString("gamephaseorder");
		} else {
			// �����������ĳ齱����
			// �رյ�ǰ����Ϸ
			AppManager.getAppManager().finishActivity();
		}
		lglottery_lottery_view = (Button) findViewById(R.id.lglottery_lottery_view);
		lglottery_go1 = (WheelView) findViewById(R.id.lglottery_lottery_go1);
		lglottery_go2 = (WheelView) findViewById(R.id.lglottery_lottery_go2);
		lglottery_go3 = (WheelView) findViewById(R.id.lglottery_lottery_go3);
		lglottery_lottery_content = (LinearLayout) findViewById(R.id.lglottery_lottery_content);
		lottery_layout1 = (LinearLayout) findViewById(R.id.lottery_layout1);
		lottery_layout2 = (LinearLayout) findViewById(R.id.lottery_layout2);
		lottery_layout3 = (LinearLayout) findViewById(R.id.lottery_layout3);
		invisible(lottery_layout1);
		invisible(lottery_layout2);
		invisible(lottery_layout3);

		lottery_layout_view1 = (TextView) findViewById(R.id.lottery_layout_view1);
		lottery_layout_view2 = (TextView) findViewById(R.id.lottery_layout_view2);
		lottery_layout_view3 = (TextView) findViewById(R.id.lottery_layout_view3);
		lglottery_lg_id = (TextView) findViewById(R.id.lglottery_lg_id);
		lglottery_lg_id.setText(getString(R.string.lglottery_lg_id, number));
	}

	private void invisible(View view) {
		if (view != null) {
			view.setVisibility(View.INVISIBLE);
		}
	}

	private void visible(View view, TextView textView, String value) {
		if (view != null) {
			view.setVisibility(View.VISIBLE);
			WLog.v("��ʾ����:" + value);
			textView.setText(value);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				connect(number);
				// lglottery_lottery_view.setOnClickListener(clickListener);
				break;
			case 1:
				parentLists = (ArrayList<Lottery_Go>) msg.obj;
				int size = parentLists.size();
				switch (size) {
				case 1:
					visible(lottery_layout1, lottery_layout_view1, parentLists
							.get(0).getUserName());
					initWheel(lglottery_go1, parentLists.get(0).getArrayList());
					break;
				case 2:
					visible(lottery_layout1, lottery_layout_view1, parentLists
							.get(0).getUserName());
					visible(lottery_layout2, lottery_layout_view2, parentLists
							.get(1).getUserName());
					initWheel(lglottery_go1, parentLists.get(0).getArrayList());
					initWheel(lglottery_go2, parentLists.get(1).getArrayList());
					break;
				case 3:
					visible(lottery_layout1, lottery_layout_view1, parentLists
							.get(0).getUserName());
					visible(lottery_layout2, lottery_layout_view2, parentLists
							.get(1).getUserName());
					visible(lottery_layout3, lottery_layout_view3, parentLists
							.get(2).getUserName());
					initWheel(lglottery_go1, parentLists.get(0).getArrayList());
					initWheel(lglottery_go2, parentLists.get(1).getArrayList());
					initWheel(lglottery_go3, parentLists.get(2).getArrayList());

					break;
				default:
					break;
				}
				lglottery_lottery_view.setOnClickListener(clickListener);
				break;
			default:
				break;
			}
		};
	};
	/**
	 * ����¼�
	 */
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.lglottery_lottery_view:
				if (parentLists.size() != 3) {
					Toast.makeText(getApplicationContext(), "�齱��Աδ��!", 200)
							.show();
				} else {
					initWheel(lglottery_go1, parentLists.get(0).getArrayList());
					initWheel(lglottery_go2, parentLists.get(1).getArrayList());
					initWheel(lglottery_go3, parentLists.get(2).getArrayList());

					System.out.println("�c���¼�");
					String index1 = parentLists.get(0).getProductItemId();
					String index2 = parentLists.get(1).getProductItemId();
					String index3 = parentLists.get(2).getProductItemId();
					WLog.v("�քe��:" + index1 + "   " + index2 + "    " + index3);
					int index[] = process(index1, index2, index3);
					mixWheel(lglottery_go1, index[0]);
					mixWheel(lglottery_go2, index[1]);
					mixWheel(lglottery_go3, index[2]);
				}
				break;

			default:
				break;
			}
		}
	};

	private ArrayList<Lottery_Gobean> getLists(int index) {
		return parentLists.get(index).getArrayList();
	}

	private int[] process(String... params) {
		int[] indexs = new int[params.length];
		// �����һ��
		ArrayList<Lottery_Gobean> index1 = getLists(0);
		for (int i = 0; i < index1.size(); i++) {
			if (params[0].equals(index1.get(i).getItemId())) {
				indexs[0] = i;
				WLog.v("��һ���齱��:" + params[0] + "ƥ����" + indexs[0]);
			}
		}
		// �����2��
		ArrayList<Lottery_Gobean> index2 = getLists(1);
		for (int i = 0; i < index2.size(); i++) {
			if (params[1].equals(index2.get(i).getItemId())) {
				indexs[1] = i;
				WLog.v("�ڶ����齱��:" + params[1] + "ƥ����" + indexs[1]);
			}
		}
		// �����3��
		ArrayList<Lottery_Gobean> index3 = getLists(2);
		for (int i = 0; i < index3.size(); i++) {
			if (params[2].equals(index3.get(i).getItemId())) {
				indexs[2] = i;
				WLog.v("�������齱��:" + params[2] + "ƥ����" + indexs[2]);
			}
		}

		return indexs;

	}

	// Wheel scrolled flag
	private boolean wheelScrolled = false;

	/**
	 * Tests wheels
	 * 
	 * @return true
	 */
	private boolean test(WheelView wheel) {
		int value = wheel.getCurrentItem();
		return testWheelValue(lglottery_go2, value)
				&& testWheelValue(lglottery_go3, value);
	}

	/**
	 * Tests wheel value
	 * 
	 * @param id
	 *            the wheel Id
	 * @param value
	 *            the value to test
	 * @return true if wheel value is equal to passed value
	 */
	private boolean testWheelValue(WheelView wheel, int value) {
		return wheel.getCurrentItem() == value;
	}

	private void updateStatus() {
		if (test(lglottery_go1)) {
			System.out.println("�н���");
		} else {
			System.out.println("����");
		}
	}

	// Wheel scrolled listener
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
		public void onScrollingStarted(WheelView wheel) {
			wheelScrolled = true;
		}

		public void onScrollingFinished(WheelView wheel) {
			wheelScrolled = false;
			// updateStatus();
		}
	};

	// Wheel changed listener
	private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (!wheelScrolled) {
				// updateStatus();
			}
		}
	};
	public static int HEIGHT = 0;

	private void initWheel(WheelView wheel, ArrayList<Lottery_Gobean> goBeans) {

		wheel.setViewAdapter(new LglotteryWheelAdapter(this, goBeans));
		wheel.setCurrentItem(0);
		wheel.addChangingListener(changedListener);
		wheel.addScrollingListener(scrolledListener);
		wheel.setCyclic(true);
		wheel.setEnabled(true);
	}

	/**
	 * ��������
	 * 
	 * @param id
	 * @param length
	 *            :�������ڹ���15000Ȧָ���������ڼ���λ��ֹͣ
	 */
	private void mixWheel(WheelView wheel, int length) {
		// wheel.scroll(-350 + (int) (Math.random() * 50), 5000);
		wheel.scroll(10 * 3 + length, 5000);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_lottery_activity);
		init();

		lglottery_lottery_content.post(new Runnable() {
			@Override
			public void run() {
				HEIGHT = lglottery_lottery_content.getMeasuredHeight() / 3;
				handler.sendEmptyMessage(0);
			}
		});
		/*
		 * ViewTreeObserver observer = lglottery_lottery_content
		 * .getViewTreeObserver(); observer.addOnPreDrawListener(new
		 * ViewTreeObserver.OnPreDrawListener() {
		 * 
		 * @Override public boolean onPreDraw() { // TODO Auto-generated method
		 * stub HEIGHT = lglottery_lottery_content.getMeasuredHeight() / 3;
		 * handler.sendEmptyMessage(0); return true; } });
		 */

	}
}
