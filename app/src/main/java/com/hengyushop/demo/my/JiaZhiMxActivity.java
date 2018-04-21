package com.hengyushop.demo.my;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.JiaZhiAdapter;
import com.android.hengyu.pub.MyAssetsAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.NewDataToast;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

/**
 *
 * 价值明细
 *
 * @author Administrator
 *
 */
public class JiaZhiMxActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private Button fanhui, btn_chongzhi;
	private LinearLayout index_item_1,index_item_2,index_item0, index_item1, index_item2, index_item3,index_item4;
	private EditText et_chongzhi;
	private SharedPreferences spPreferences;
	private TextView tv_jz_bt_1,tv_jz_bt_2;
	private TextView tv_title_1,tv_title_2,tv_title_3,tv_title_4,tv_title_5;
	private ArrayList<MyAssetsBean> list;
	private ListView listView;
	private PullToRefreshView refresh;
	JiaZhiAdapter adapter;
	int len;
	public static String fund_id = "12";
	private int RUN_METHOD = -1;
	private DialogProgress progress;
	private ImageView iv_biaoti_1,iv_biaoti_2,iv_biaoti1, iv_biaoti2, iv_biaoti3,
			iv_biaoti4,iv_biaoti5;
	MyAssetsBean data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jiazhi_mx);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(this);
		Initialize();

		list = new ArrayList<MyAssetsBean>();
		adapter = new JiaZhiAdapter(list, JiaZhiMxActivity.this, imageLoader);
		listView.setAdapter(adapter);
		getptzjz();
		loadguanggao();
		load_list(true, fund_id);

	}


	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
			iv_biaoti_1 = (ImageView) findViewById(R.id.iv_biaoti_1);
			iv_biaoti_2 = (ImageView) findViewById(R.id.iv_biaoti_2);
			iv_biaoti1 = (ImageView) findViewById(R.id.iv_biaoti1);
			iv_biaoti2 = (ImageView) findViewById(R.id.iv_biaoti2);
			iv_biaoti3 = (ImageView) findViewById(R.id.iv_biaoti3);
			iv_biaoti4 = (ImageView) findViewById(R.id.iv_biaoti4);
			iv_biaoti5 = (ImageView) findViewById(R.id.iv_biaoti5);
			refresh = (PullToRefreshView) findViewById(R.id.refresh);
			refresh.setOnHeaderRefreshListener(listHeadListener);
			refresh.setOnFooterRefreshListener(listFootListener);
			listView = (ListView) findViewById(R.id.new_list);
			tv_jz_bt_1 = (TextView) findViewById(R.id.tv_jz_bt_1);
			tv_jz_bt_2 = (TextView) findViewById(R.id.tv_jz_bt_2);
			tv_title_1 = (TextView) findViewById(R.id.tv_title_1);
			tv_title_2 = (TextView) findViewById(R.id.tv_title_2);
			tv_title_3 = (TextView) findViewById(R.id.tv_title_3);
			tv_title_4 = (TextView) findViewById(R.id.tv_title_4);
			tv_title_5 = (TextView) findViewById(R.id.tv_title_5);
			index_item_1 = (LinearLayout) findViewById(R.id.index_item_1);
			index_item_2 = (LinearLayout) findViewById(R.id.index_item_2);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			index_item3 = (LinearLayout) findViewById(R.id.index_item3);
			index_item4 = (LinearLayout) findViewById(R.id.index_item4);
			//			cursor1 = (ImageView) findViewById(R.id.cursor1);
			//			cursor2 = (ImageView) findViewById(R.id.cursor2);
			//			cursor3 = (ImageView) findViewById(R.id.cursor3);
			//			cursor4 = (ImageView) findViewById(R.id.cursor4);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			index_item3.setOnClickListener(this);
			index_item4.setOnClickListener(this);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			Bitmap bm11 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_pt_1);
			BitmapDrawable bd11 = new BitmapDrawable(this.getResources(), bm11);
			iv_biaoti_1.setBackgroundDrawable(bd11);
			Bitmap bm22 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_pt_2);
			BitmapDrawable bd22 = new BitmapDrawable(this.getResources(), bm22);
			iv_biaoti_2.setBackgroundDrawable(bd22);


			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_1);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			iv_biaoti1.setBackgroundDrawable(bd1);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_2);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			iv_biaoti2.setBackgroundDrawable(bd2);
			Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_3);
			BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
			iv_biaoti3.setBackgroundDrawable(bd3);
			Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_4);
			BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
			iv_biaoti4.setBackgroundDrawable(bd4);
			Bitmap bm5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jz_5);
			BitmapDrawable bd5 = new BitmapDrawable(this.getResources(), bm5);
			iv_biaoti5.setBackgroundDrawable(bd5);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			//12 8 13 14 15
			case R.id.index_item0:
				fund_id = "12";
				load_list(true, fund_id);
				break;
			case R.id.index_item1:
				fund_id = "8";
				load_list(true, fund_id);
				break;
			case R.id.index_item2:
				fund_id = "13";
				load_list(true, fund_id);
				break;
			case R.id.index_item3:
				fund_id = "14";
				load_list(true, fund_id);
				break;
			case R.id.index_item4:
				fund_id = "15";
				load_list(true, fund_id);
				break;
			default:
				break;
		}
	}

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};

	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						// if (RUN_METHOD == 0) {
						// System.out.println("RUN_METHOD1========="+
						// RUN_METHOD);
						// load_list2(false);
						// } else {
						System.out.println("RUN_METHOD2=========" + RUN_METHOD);
						load_list(false, fund_id);
						// }
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	// private OnFooterRefreshListener listFootListener = new
	// OnFooterRefreshListener() {
	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	// // TODO Auto-generated method stub
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// if(RUN_METHOD==0){
	// load_P(false);
	// }else {
	// // if(INDX!=-1)
	// load(INDX, false);
	// }
	// refresh.onFooterRefreshComplete();
	// }
	// }, 1000);
	// }
	// };

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					adapter.putData(list);
					// list = (ArrayList<MyAssetsBean>) msg.obj;
					// adapter = new MyAssetsAdapter(list,
					// MyAssetsActivity.this,imageLoader);
					// listView.setAdapter(adapter);
					// adapter.notifyDataSetChanged();
					break;

				default:
					break;
			}
		};
	};

	/**
	 * 第1个列表数据解析
	 */
	// private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag, String fund_id) {
		progress.CreateProgress();
		RUN_METHOD = 1;
		if (flag) {
			// 计数和容器清零
			System.out.println("=====================flag==" + flag);
			CURRENT_NUM = 1;
			list = new ArrayList<MyAssetsBean>();
		}
		String user_name = spPreferences.getString("user", "");
		String user_id = spPreferences.getString("user_id", "");
		System.out.println("=====================fund_id--" + fund_id);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_payrecord_list?user_id="
						+ user_id + "&user_name=" + user_name + "&fund_id=" + fund_id
						+ "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object
										.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray
											.getJSONObject(i);
									data = new MyAssetsBean();
									data.fund = json.getString("fund");
									data.income = json.getString("income");
									data.user_name = json
											.getString("user_name");
									data.add_time = json.getString("add_time");
									data.expense = json.getString("expense");
									data.remark = json.getString("remark");
									data.balance = json.getString("balance");
									list.add(data);
								}
							} else {
								Toast.makeText(JiaZhiMxActivity.this, info, 200)
										.show();
							}
							System.out.println("=====================二级值12");
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);

							if (len != 0) {
								CURRENT_NUM = CURRENT_NUM + 1;
							}
							progress.CloseProgress();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}


	/**
	 * 平台总价值
	 */
	private void getptzjz() {
		try {
			String strUrl = RealmName.REALM_NAME_LL+ "/get_payrecord_total?fund_ids=8,12,13,14,15";
			AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======平台总价值=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							String fund_total = obj.getString("fund_total");
							System.out.println("fund_total==============="+ fund_total);
							//							BigDecimal w = new BigDecimal(fund_total);
							//							double fund_zjz = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							//							System.out.println("fund_zjz==============="+ fund_zjz);
							//							tv_jz_bt_1.setText(String.valueOf(fund_total_1));//平台总价值
							tv_jz_bt_1.setText(fund_total);//平台总价值
						} else {
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					NewDataToast.makeText(JiaZhiMxActivity.this, "连接超时", false,
							0).show();
				}
			}, getApplicationContext());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 我的总价值
	 */
	double exp;
	private void loadguanggao() {
		try {
			String user_name = spPreferences.getString("user", "");
			String strUrl = RealmName.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======我的总价值=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");

							double exp = obj.getDouble("exp");
							double exp_weal = obj.getDouble("exp_weal");
							double exp_invest = obj.getDouble("exp_invest");
							double exp_action = obj.getDouble("exp_action");
							double exp_time = obj.getDouble("exp_time");

							double dzongjia = exp+exp_weal+exp_invest+exp_action+exp_time;
							BigDecimal w = new BigDecimal(dzongjia);
							double zong_jz = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							System.out.println("zong_jz==============="+ zong_jz);

							tv_jz_bt_2.setText(String.valueOf(zong_jz));//价值

							tv_title_1.setText(String.valueOf(exp_weal));
							tv_title_2.setText(String.valueOf(exp));
							tv_title_3.setText(String.valueOf(exp_invest));
							tv_title_4.setText(String.valueOf(exp_action));
							tv_title_5.setText(String.valueOf(exp_time));

						} else {

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					NewDataToast.makeText(JiaZhiMxActivity.this, "连接超时", false,0).show();
				}
			}, getApplicationContext());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void onDestroy() {
		super.onDestroy();
		try {
			fund_id = "12";

			if (list.size() > 0) {
				list.clear();
				list = null;
			}

			BitmapDrawable bd11 = (BitmapDrawable)iv_biaoti_1.getBackground();
			iv_biaoti_1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd11.setCallback(null);
			bd11.getBitmap().recycle();
			BitmapDrawable bd22 = (BitmapDrawable)iv_biaoti_2.getBackground();
			iv_biaoti_2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd22.setCallback(null);
			bd22.getBitmap().recycle();
			BitmapDrawable bd1 = (BitmapDrawable)iv_biaoti1.getBackground();
			iv_biaoti1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)iv_biaoti2.getBackground();
			iv_biaoti2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
			BitmapDrawable bd3 = (BitmapDrawable)iv_biaoti3.getBackground();
			iv_biaoti3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd3.setCallback(null);
			bd3.getBitmap().recycle();
			BitmapDrawable bd4 = (BitmapDrawable)iv_biaoti4.getBackground();
			iv_biaoti4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd4.setCallback(null);
			bd4.getBitmap().recycle();
			BitmapDrawable bd5 = (BitmapDrawable)iv_biaoti5.getBackground();
			iv_biaoti5.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd5.setCallback(null);
			bd5.getBitmap().recycle();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	};
}
