package com.lglottery.www.activity;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.adapter.LglotteryListAdapter;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.Lglottery_Item;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zams.www.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class LglotteryActivity extends BaseActivity {
	private ImageView lglottery_person_ico;
	private FrameLayout lglottery_person_frame;
	// private Button lglottery_play1, lglottery_play2;
	private DisplayImageOptions options;
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private TextView lglottery_tip;
	private SharedUtils sharedUtils, personUtil;
	private final int REQUESTCODE_LOGIN = 0;
	private ListView lglottery_list;
	private LglotteryListAdapter lglotteryListAdapter;
	private ArrayList<Lglottery_Item> lglottery_Items;
	private TextView lglottery_person_title, lglottery_person_dai,
			lglottery_person_dou, lglottery_person_xian;
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				ArrayList<Lglottery_Item> lglottery_Items = (ArrayList<Lglottery_Item>) msg.obj;
				lglotteryListAdapter.putList(lglottery_Items);
				break;
			case 1:
				Intent enterIntent1 = new Intent(LglotteryActivity.this,
						LglotteryEnterActivity.class);
				Bundle enterBundle1 = new Bundle();
				Lglottery_Item item = (Lglottery_Item) msg.obj;
				if (item.isClick()) {
					enterBundle1.putSerializable("pay_lottery", item);
					enterIntent1.putExtras(enterBundle1);
					startActivity(enterIntent1);
				} else {
					Toast.makeText(getApplicationContext(), "上期抽奖未完成!", 200)
							.show();
				}
				break;
			default:
				break;
			}
		};
	};

	private void initOptions() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.color.no_color)
				.showImageForEmptyUri(R.drawable.lglottery_login_ico)
				.showImageOnFail(R.drawable.lglottery_login_ico)
				.cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(200)).build();
	}

	private void iniData() {
		personUtil = new SharedUtils(getApplicationContext(),
				Config.PERSONAL_STATUS);
		lglottery_person_title = (TextView) findViewById(R.id.lglottery_person_title);
		lglottery_person_dai = (TextView) findViewById(R.id.lglottery_person_dai);
		lglottery_person_dou = (TextView) findViewById(R.id.lglottery_person_dou);
		lglottery_person_xian = (TextView) findViewById(R.id.lglottery_person_xian);

	}

	/**
	 * 组件事件
	 */
	private void init() {
		iniData();
		lglottery_list = (ListView) findViewById(R.id.lglottery_list);
		lglottery_person_frame = (FrameLayout) findViewById(R.id.lglottery_person_frame);
		// lglottery_play1 = (Button) findViewById(R.id.lglottery_play1);
		// lglottery_play2 = (Button) findViewById(R.id.lglottery_play2);
		lglottery_tip = (TextView) findViewById(R.id.lglottery_tip);
		lglottery_tip.setOnClickListener(clickListener);
		lglottery_person_ico = (ImageView) findViewById(R.id.lglottery_person_ico);
		lglottery_person_frame.setOnClickListener(clickListener);
		// lglottery_play1.setOnClickListener(clickListener);
		// lglottery_play2.setOnClickListener(clickListener);
		lglottery_Items = new ArrayList<Lglottery_Item>();
		lglotteryListAdapter = new LglotteryListAdapter(
				getApplicationContext(), lglottery_Items, handler);
		lglottery_list.setAdapter(lglotteryListAdapter);
		lglottery_person_frame
				.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						sharedUtils.clear();
						return true;
					}
				});

	}

	/**
	 * 获得信息
	 */
	private void connect() {
		RequestParams params = new RequestParams();
		params.put("yth", sharedUtils.getStringValue("yth"));
		System.out.println("抽奖" + U.GET_LOTTERY_ITEM);
		AsyncHttp.post(U.GET_LOTTERY_ITEM, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
						// showDialog(Config.SHOW_LOADING);
					}

					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						WLog.v(arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							ArrayList<Lglottery_Item> lglottery_Items = new ArrayList<Lglottery_Item>();
							for (int i = 0; i < len; i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								Lglottery_Item item = new Lglottery_Item();
								// if (jsonObject.getString("LotteryGameStatus")
								// .equals("0")) {
								// item.setClick(false);
								// } else {
								item.setClick(true);
								// }
								item.setId(object
										.getString("LotteryGameGroupId"));
								item.setPlayName(object
										.getString("LotteryGameGroupName"));
								item.setBalance((int) Double.parseDouble(object
										.getString("GameGroupCostshopPassTicket")));
								item.setJinbi((int) Double.parseDouble(object
										.getString("GameGroupCostCredit")));
								lglottery_Items.add(item);

							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = lglottery_Items;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@SuppressWarnings("deprecation")
					@Override
					public void onFinish() {
						super.onFinish();
						// dismissDialog(Config.SHOW_LOADING);
						// removeDialog(Config.SHOW_LOADING);
					}
				}, getApplicationContext());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lglottery_person_dai.setText(getString(R.string.person_view_dai,
				personUtil.getValue("shopPassTicket")));
		lglottery_person_dou.setText(getString(R.string.person_view_dou,
				personUtil.getValue("credits")));
		lglottery_person_title.setText(personUtil.getStringValue("username"));
		lglottery_person_xian.setText(getString(R.string.person_view_xian,
				personUtil.getValue("PassTicketBalance")));
		if (lglottery_Items.size() == 0) {
			connect();
		}
		SharedUtils in = new SharedUtils(getApplicationContext(), "shouyi");
		if (in.isHere("avatarimageURL")) {
			imageLoader.displayImage(
					RealmName.REALM_NAME + in.getStringValue("avatarimageURL"),
					lglottery_person_ico);
		} else {
			imageLoader.displayImage("drawable://"
					+ R.drawable.lglottery_login_ico, lglottery_person_ico,
					options);
		}

	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.lglottery_tip:
				initPopupWindow();
				showPopupWindow(lglottery_tip);
				break;
			case R.id.lglottery_pop_closed:
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				break;
			case R.id.lglottery_person_frame:
				if (sharedUtils.getStringValue("key").length() > 0) {
					// 表示登录成功
					Intent intent = new Intent(LglotteryActivity.this,
							LglotteryPersonActivity.class);
					startActivity(intent);
				} else {
					// 表示未登录
					Intent personIntent = new Intent(LglotteryActivity.this,
							LgLotteryLoginActivity.class);
					Bundle bundle = getIntent().getExtras();
					personIntent.putExtras(bundle);
					startActivityForResult(personIntent, REQUESTCODE_LOGIN);
				}

				break;
			/*
			 * case R.id.lglottery_play1: Intent enterIntent = new
			 * Intent(LglotteryActivity.this, LglotteryEnterActivity.class);
			 * Bundle enterBundle = new Bundle();
			 * enterBundle.putInt("pay_lottery", 1);
			 * enterIntent.putExtras(enterBundle); startActivity(enterIntent);
			 * break; case R.id.lglottery_play2: Intent enterIntent1 = new
			 * Intent(LglotteryActivity.this, LglotteryEnterActivity.class);
			 * Bundle enterBundle1 = new Bundle();
			 * enterBundle1.putInt("pay_lottery", 2);
			 * enterIntent1.putExtras(enterBundle1);
			 * startActivity(enterIntent1); break;
			 */
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_activity);
		sharedUtils = new SharedUtils(getApplicationContext(),
				Config.LOGIN_STATUS);
		if (sharedUtils.getStringValue("key").length() == 0) {

			// 表示未登录
			Intent personIntent = new Intent(LglotteryActivity.this,
					LgLotteryLoginActivity.class);
			Bundle bundle = getIntent().getExtras();
			personIntent.putExtras(bundle);
			startActivityForResult(personIntent, REQUESTCODE_LOGIN);
		}
		initOptions();
		init();
	}

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.lglottery_tip, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		Button closed = (Button) popView
				.findViewById(R.id.lglottery_pop_closed);
		closed.setOnClickListener(clickListener);

	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

}
