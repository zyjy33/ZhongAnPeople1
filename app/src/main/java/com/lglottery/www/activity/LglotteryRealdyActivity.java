package com.lglottery.www.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.LglotteryBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����
 * 
 * @author cloor
 * 
 */
public class LglotteryRealdyActivity extends BaseActivity {
	private TextView lglottery_realdy_tip;
	private SharedUtils sharedUtils, personUtil;
	private TextView readly_xjq, readly_jd;
	private CheckBox readly_xjq_btn, readly_jd_btn;
	private final double PAY = 50;
	private double balance, jinbi;
	private LglotteryBean bean;
	private double pay_balance, pay_jinbi;
	private Button lottery_pay;
	private EditText readly_mm;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// ���������Ϣ
				balance = Double.parseDouble(personUtil
						.getValue("PassTicketBalance"));// ʹ�õ����ֽ�ȯ
				jinbi = Double.parseDouble(personUtil.getValue("credits"));
				readly_xjq.setText(getString(R.string.readly_xjq, balance));
				readly_jd.setText(getString(R.string.readly_jd, jinbi));
				break;
			default:
				break;
			}
		};
	};

	/**
	 * �������ݵĳ�ʼ��
	 */
	private void init() {
		sharedUtils = new SharedUtils(getApplicationContext(),
				Config.LOGIN_STATUS);
		personUtil = new SharedUtils(getApplicationContext(),
				Config.PERSONAL_STATUS);
		Bundle bundle = getIntent().getExtras();
		bean = (LglotteryBean) bundle.getSerializable("object");
		lglottery_realdy_tip = (TextView) findViewById(R.id.lglottery_realdy_tip);
		lottery_pay = (Button) findViewById(R.id.lottery_pay);
		switch (bean.getTag()) {
		// 0�������ȯ��1�����
		case 0:
			lglottery_realdy_tip.setText(getString(R.string.readly_tip_d,
					bean.getBalance()));
			break;
		case 1:
			lglottery_realdy_tip.setText(getString(R.string.readly_tip_j,
					bean.getJinbi()));
			break;
		default:
			break;
		}
		readly_xjq = (TextView) findViewById(R.id.readly_xjq);
		readly_jd = (TextView) findViewById(R.id.readly_jd);
		readly_mm = (EditText) findViewById(R.id.readly_mm);
		readly_xjq_btn = (CheckBox) findViewById(R.id.readly_xjq_btn);
		readly_jd_btn = (CheckBox) findViewById(R.id.readly_jd_btn);
		readly_xjq_btn.setOnCheckedChangeListener(changeListener);
		readly_jd_btn.setOnCheckedChangeListener(changeListener);
		lottery_pay.setOnClickListener(clickListener);
	}

	/**
	 * �������ݼ���
	 * 
	 * @param entry
	 * @return
	 */
	private String createGoods(Iterator<Entry<String, Queue<String>>> entry) {
		String temp = "";
		while (entry.hasNext()) {
			Queue<String> queue = entry.next().getValue();
			Iterator<String> iterator = queue.iterator();
			while (iterator.hasNext()) {
				temp += "_" + iterator.next();
			}
		}
		return temp.substring(1, temp.length());
	}

	/**
	 * ֧������
	 */
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.lottery_pay:
				if (pay_balance + pay_jinbi != 50.0) {
					Toast.makeText(getApplicationContext(), "�˻�����޷��ﵽ�۳��Ľ��",
							200).show();
				} else {
					RequestParams params = new RequestParams();
					params.put("yth", sharedUtils.getStringValue("yth"));
					Map<String, Queue<String>> maps = bean.getMaps();
					System.out.println(createGoods(maps.entrySet().iterator()));
					params.put("LotteryGameGroupId", bean.getId());
					params.put("LotteryGameItemId", createGoods(maps.entrySet()
							.iterator()));
					switch (bean.getTag()) {
					case 0:
						// ����ȯ
						params.put("GameGroupCostshopPassTicket",
								String.valueOf(bean.getBalance()));
						break;
					case 1:
						// ��
						params.put("GameGroupCostCredit",
								String.valueOf(bean.getJinbi()));
						break;
					default:
						break;
					}
					params.put("paypwd", readly_mm.getText().toString());
					params.put("OnePhaseCostPassTicket",
							String.valueOf((int) pay_balance));
					params.put("OnePhaseGroupCostCredit",
							String.valueOf((int) pay_jinbi));
					WLog.v("LotteryGameItemId="
							+ createGoods(maps.entrySet().iterator()));
					WLog.v("LotteryGameGroupId=" + bean.getId());
					WLog.v("yth=" + sharedUtils.getStringValue("yth"));
					WLog.v("GameGroupCostCredit="
							+ String.valueOf(bean.getJinbi()));
					WLog.v("GameGroupCostshopPassTicket="
							+ String.valueOf(bean.getBalance()));
					WLog.v("OnePhaseCostPassTicket="
							+ String.valueOf(pay_balance));
					WLog.v("paypwd=" + readly_mm.getText().toString());
					WLog.v("OnePhaseGroupCostCredit="
							+ String.valueOf(pay_jinbi));

					AsyncHttp.post(U.LOTTERY_READLY, params,
							new AsyncHttpResponseHandler() {
								@SuppressWarnings("deprecation")
								public void onStart() {
								};

								public void onSuccess(int arg0, String arg1) {
									WLog.v(arg1);
									try {
										JSONObject jsonObject = new JSONObject(
												arg1);
										String status = jsonObject
												.getString("status");
										if (status.equals("0")) {
											Toast.makeText(
													getApplicationContext(),
													jsonObject.getString("msg"),
													200).show();
										} else {
											Intent intent = new Intent(
													LglotteryRealdyActivity.this,
													LglotteryGoActivity.class);
											intent.putExtra(
													"gamephaseorder",
													jsonObject
															.getString("GamePhaseOrder"));
											startActivity(intent);
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								};

								@SuppressWarnings("deprecation")
								public void onFinish() {

								};
							}, getApplicationContext());
				}
				break;
			default:
				break;
			}
		}
	};
	/**
	 * ����ȯ�ͽ�ҵ��¼�����
	 */
	private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			switch (arg0.getId()) {
			case R.id.readly_xjq_btn:
				// ����ȯ
				if (arg1) {
					changeBalance();

				} else {
					pay_balance = 0;
					readly_xjq.setText(getString(R.string.readly_xjq, balance));
					if (readly_jd_btn.isChecked()) {
						// �������ѡ�е�״̬����ô�ٴθ���ѡ��״̬
						changeJindou();
					}
				}
				WLog.v("����Ż�ȯ֮��  ���:" + jinbi + "�Ż�ȯ:" + balance + "֧�����:"
						+ pay_jinbi + "֧���Ż�ȯ:" + pay_balance);
				break;
			case R.id.readly_jd_btn:
				// ��
				if (arg1) {
					changeJindou();

				} else {
					pay_jinbi = 0;
					readly_jd.setText(getString(R.string.readly_jd, jinbi));
					if (!readly_xjq_btn.isChecked()) {
						readly_xjq_btn.setClickable(true);// �ָ��Ż�ȯ��ť�ɵ��
					} else {
						readly_xjq_btn.setClickable(true);
						changeBalance();
					}
				}
				WLog.v("������֮��  ���:" + jinbi + "�Ż�ȯ:" + balance + "֧�����:"
						+ pay_jinbi + "֧���Ż�ȯ:" + pay_balance);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ����ȯ�ı仯
	 */
	private void changeBalance() {
		double balance_temp = PAY - pay_jinbi;
		if (balance > balance_temp) {
			// �������ȯ���֧�����~
			pay_balance = balance_temp;
			readly_xjq.setText(getString(R.string.readly_xjq_y, balance_temp,
					balance - balance_temp));
		} else {
			pay_balance = balance;
			readly_xjq.setText(getString(R.string.readly_xjq_y, balance, 0));
		}
	}

	/**
	 * �𶹵�׃��
	 */
	private void changeJindou() {
		if (jinbi > PAY) {
			// �����Ҵ�����֧���Ľ���ô���ȿ۳���
			pay_jinbi = PAY;
			readly_jd
					.setText(getString(R.string.readly_jd_y, PAY, jinbi - PAY));
			readly_xjq.setText(getString(R.string.readly_xjq, balance));
			readly_xjq_btn.setChecked(false);
			readly_xjq_btn.setClickable(false);// �Ż�ȯ��ť���ɵ��
		} else {
			readly_xjq_btn.setClickable(true);// �ָ��Ż�ȯ��ť�ɵ��
			// �鿴�Ż�ȯ�Ƿ���
			pay_jinbi = jinbi;
			readly_jd.setText(getString(R.string.readly_jd_y, pay_jinbi, 0));
			if (readly_xjq_btn.isChecked()) {
				changeBalance();
			}
			// } else {
			// pay_jinbi = jinbi;
			// readly_jd.setText(getString(R.string.readly_jd_y, jinbi, 0));
			// }
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_readly);
		init();
		// handler.sendEmptyMessage(0);
		init_personal();
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void init_personal() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", sharedUtils.getStringValue("key"));
		params.put("yth", sharedUtils.getStringValue("yth"));
		params.put("act", "myInfo");
		AsyncHttp.post_1(U.LOTTERY_PERSONAL_INFO, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						WLog.v(arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							Iterator<String> iterator = jsonObject.keys();
							if (jsonObject.getInt("status") == 1) {
								while (iterator.hasNext()) {
									String key = iterator.next();
									personUtil.setStringValue(key,
											jsonObject.getString(key));
								}

								handler.sendEmptyMessage(0);

							} else {
								// ��ʾ�д���
								Toast.makeText(getApplicationContext(),
										"�����֤���ڣ������µ�¼!", 200).show();
								sharedUtils.clear();
								AppManager.getAppManager().finishActivity();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
						// for()
					}

					@SuppressWarnings("deprecation")
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();

					}
				});
	}
}
