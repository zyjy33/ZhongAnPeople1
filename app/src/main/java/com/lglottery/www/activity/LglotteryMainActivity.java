package com.lglottery.www.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.adapter.LglotteryContentAdapter;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.common.U;
import com.lglottery.www.common.WLog;
import com.lglottery.www.domain.LglotteryBean;
import com.lglottery.www.domain.Lglottery_Main;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zams.www.R;

public class LglotteryMainActivity extends BaseActivity {
	private GridView lglottery_main_content;
	private LglotteryContentAdapter lglotteryContentAdapter;
	private Button lglottery_main_ok;
	private RadioGroup lglottery_main_group;
	private RadioButton lglottery_main_a, lglottery_main_b, lglottery_main_c;
	private SharedUtils sharedUtils;
	private ArrayList<Lglottery_Main> lists;
	private Map<String, Queue<String>> maps;
	private LglotteryBean bean;
	private Bundle bundle;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					ArrayList<Lglottery_Main> lists = (ArrayList<Lglottery_Main>) msg.obj;
					lglotteryContentAdapter.reloadQueen();
					lglotteryContentAdapter.putLists(lists);
					break;
				case 1:
					ArrayList<Lglottery_Main> lists1 = (ArrayList<Lglottery_Main>) msg.obj;
					lglotteryContentAdapter.putLists(lists1);
					// lglotteryContentAdapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		};
	};

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
				case R.id.lglottery_pop_closed:
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}

					break;
				case R.id.lglottery_main_ok:

					try {
						if (maps.get("1").size() == 2 && maps.get("2").size() == 2
								&& maps.get("3").size() == 2) {
							// 数据在这里
							bean.setMaps(maps);
							Intent realdyIntent = new Intent(
									LglotteryMainActivity.this,
									LglotteryRealdyActivity.class);
							bundle.putSerializable("lglottery_object", bean);
							realdyIntent.putExtras(bundle);
							startActivity(realdyIntent);
						} else {
							Toast.makeText(getApplicationContext(), "请从ABC中各选2份",
									200).show();
						}
					} catch (NullPointerException e) {
						Toast.makeText(getApplicationContext(), "请从ABC中各选2份", 200)
								.show();
						e.printStackTrace();
					}

				/*
				 * //注释掉以前的扣金币的方式 initPopupWindow();
				 * showPopupWindow(lglottery_main_content); try {
				 * System.out.println(maps.size() + "的长度");
				 * System.out.println(maps.get("1").size() + "的長度"); } catch
				 * (NullPointerException e) { // TODO: handle exception }
				 */
				/*
				 * initPopupWindow(); showPopupWindow(lglottery_main_content);
				 */
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 拿取奖品
	 *
	 * @param typeId
	 */
	private void connect(String typeId) {
		// LotteryGameGroupId=1&LotteryGameTypeId=3&yth=lemon123
		RequestParams params = new RequestParams();
		WLog.v("这里需要修改" + bean.getId());
		params.put("LotteryGameGroupId", bean.getId());
		params.put("LotteryGameTypeId", typeId);
		params.put("yth", sharedUtils.getStringValue("yth"));
		AsyncHttp.post(U.LOTTERY_ITEM_BY_CLICK, params,
				new AsyncHttpResponseHandler() {
					@SuppressWarnings("deprecation")
					@Override
					public void onStart() {
						super.onStart();
					}

					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						WLog.v(arg1);
						try {
							ArrayList<Lglottery_Main> list = new ArrayList<Lglottery_Main>();
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							for (int i = 0; i < len; i++) {
								Lglottery_Main main = new Lglottery_Main();
								JSONObject object = jsonArray.getJSONObject(i);
								main.setTypeId(object
										.getString("LotteryGameTypeId"));
								main.setItemId(object
										.getString("LotteryGameItemId"));
								main.setNum(object
										.getString("RemainGameProductNum"));
								main.setName(object.getString("proName"));
								main.setImage(object.getString("proFaceImg"));
								list.add(main);

							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@SuppressWarnings("deprecation")
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						// dismissDialog(Config.SHOW_LOADING);

					}
				}, getApplicationContext());
	}

	private void statusRadioButton(int index) {
		switch (index) {
			case R.id.lglottery_main_a:
				lglottery_main_a.setChecked(true);
				connect("1");
				break;
			case R.id.lglottery_main_b:
				lglottery_main_b.setChecked(true);
				connect("2");
				break;
			case R.id.lglottery_main_c:
				lglottery_main_c.setChecked(true);
				connect("3");
				break;
			default:
				break;
		}
	}

	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup radioGroup, int index) {
			statusRadioButton(index);
		}
	};

	@SuppressWarnings("deprecation")
	private void init() {
		bundle = getIntent().getExtras();
		bean = (LglotteryBean) bundle.getSerializable("object");
		sharedUtils = new SharedUtils(getApplicationContext(), "login");
		lglottery_main_ok = (Button) findViewById(R.id.lglottery_main_ok);
		lglottery_main_ok.setOnClickListener(clickListener);
		lglottery_main_content = (GridView) findViewById(R.id.lglottery_main_content);
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int longPx = (int) ((screenWidth - 60) / 2);
		lists = new ArrayList<Lglottery_Main>();
		maps = new HashMap<String, Queue<String>>();
		lglotteryContentAdapter = new LglotteryContentAdapter(longPx,
				getApplicationContext(), lists, imageLoader, handler, maps);
		lglottery_main_content.setAdapter(lglotteryContentAdapter);
		// lglottery_main_content.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lglottery_main_a = (RadioButton) findViewById(R.id.lglottery_main_a);
		lglottery_main_b = (RadioButton) findViewById(R.id.lglottery_main_b);
		lglottery_main_c = (RadioButton) findViewById(R.id.lglottery_main_c);
		statusRadioButton(R.id.lglottery_main_a);
		lglottery_main_group = (RadioGroup) findViewById(R.id.lglottery_main_group);
		lglottery_main_group.setOnCheckedChangeListener(changeListener);
		lglottery_main_content
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
						// TODO Auto-generated method stub
						lglotteryContentAdapter.setSelectItem(arg2);
						lglotteryContentAdapter.notifyDataSetChanged();
					}
				});

	}

	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";
	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		pauseOnScroll = savedInstanceState.getBoolean(STATE_PAUSE_ON_SCROLL,
				false);
		pauseOnFling = savedInstanceState
				.getBoolean(STATE_PAUSE_ON_FLING, true);
	}

	private void applyScrollListener() {
		lglottery_main_content.setOnScrollListener(new PauseOnScrollListener(
				imageLoader, pauseOnScroll, pauseOnFling));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lglottery_main_activity);
		init();
		applyScrollListener();

	}

	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.lglottery_main_tip, null);
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
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		handler.sendEmptyMessage(2);
	}

}
