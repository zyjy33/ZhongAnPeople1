package com.android.cricle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.domain.CircleBean;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.JulsActivity;
import com.zams.www.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CircleActivity extends BaseActivity {
	/** Called when the activity is first created. */
	private LinearLayout layout;
	private ImageView lottery, ico_image;
	private Circleview claert;
	private int screnWidth;
	private Button circle_tip, start;
	private LayoutInflater mLayoutInflater;
	private PopupWindow mPopupWindow;
	private View popView, point;
	private int layoutW;
	private int layoutH;
	private float x, x1;
	private float y, y1;
	private Thread thread;
	private int FINAL_START = -1;
	private CircleBean circleBean;
	private WebView web_c;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AppManager.getAppManager().finishActivity();
		}
		return true;

	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case -2:
					String mss = (String) msg.obj;
					NewDataToast.makeText(getApplicationContext(), mss, false, 0)
							.show();
					break;

				case 0:
					// Toast.makeText(getApplicationContext(), "准备", 200).show();
					claert = new Circleview(CircleActivity.this, screnWidth, x, y,
							handler);
					layout.addView(claert, layoutW, layoutH);
					System.out.println(layoutW + "~" + layoutH);
					start.setOnClickListener(clickListener);
					break;
				case 1:
					claert.setStopRoter(true);
					thread = null;
					// Toast.makeText(getApplicationContext(), "开奖", 200).show();
					initPopupWindowTip(circleBean);
					showPopupWindowTip(circle_tip);
					start.setEnabled(true);
					break;
				case 3:
					thread = (Thread) msg.obj;
					break;
				case 4:
					FINAL_START = msg.arg1;
					thread.start();
					int place = msg.arg1;
					claert.setReset(0);
					claert.setStopPlace(place);
					claert.setStopRoter(false);
					break;
				case 5:
					// 抽奖结果
					FINAL_START = (int) (Math.random() * 10);
					thread.start();
					int place1 = FINAL_START;
					claert.setReset(0);
					claert.setStopPlace(place1);
					claert.setStopRoter(false);
					circleBean = (CircleBean) msg.obj;
					if (circleBean.getPrizeTypeID().equals("1")) {
						// 商品

					} else {
						// 福利

					}
					break;
				default:
					break;
			}
		};
	};
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
				case R.id.circle_tip:

					initPopupWindow();
					showPopupWindow(circle_tip);
					break;
				case R.id.begin_btn:
					start.setEnabled(false);
					FINAL_START = -1;// 重置
					if (yth.length() != 0) {
						Map<String, String> params = new HashMap<String, String>();
						params.put("yth", yth);
						params.put("luckDrawSetName", "转啊转");
						params.put("act", "GetLuckDrawResult");
						AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getData.ashx",
								params, new AsyncHttpResponseHandler() {
									public void onSuccess(int arg0, String arg1) {
										try {
											System.out.println(arg1);
											JSONObject jsonObject = new JSONObject(
													arg1);
											String status = jsonObject
													.getString("status");
											if (status.equals("1")) {
												// 正确，开始处理
												String PrizeTypeID = jsonObject
														.getString("PrizeTypeID");// 1.商品，2.表示福利
												CircleBean bean = new CircleBean();
												bean.setLuckDrawSerialNumber(jsonObject
														.getString("LuckDrawSerialNumber"));
												bean.setMsg(jsonObject
														.getString("msg"));
												bean.setMsg1(jsonObject
														.getString("msg2"));
												bean.setPrizeTypeID(PrizeTypeID);
												Message msg = new Message();
												msg.what = 5;
												msg.obj = bean;
												handler.sendMessage(msg);
											} else {
												Message msg = new Message();
												msg.what = -2;
												msg.obj = jsonObject
														.getString("msg");
												handler.sendMessage(msg);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}

									};
								});
					}

					break;
				default:
					break;
			}
		}
	};

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.circle_tip, null);
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
		Button lglottery_pop_closed = (Button) popView
				.findViewById(R.id.lglottery_pop_closed);
		lglottery_pop_closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});

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

	private void initPopupWindowTip(CircleBean circleBean) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.circle_tip_text, null);
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
		Button lglottery_pop_closed = (Button) popView
				.findViewById(R.id.lglottery_pop_closed);
		TextView start_f_name = (TextView) popView
				.findViewById(R.id.start_f_name);
		start_f_name.setText(circleBean.getMsg1());
		TextView start_f_name0 = (TextView) popView
				.findViewById(R.id.start_f_name0);
		start_f_name0.setText(circleBean.getMsg());
		Button ji_xu = (Button) popView.findViewById(R.id.ji_xu);
		Button ji_ls = (Button) popView.findViewById(R.id.ji_ls);
		lglottery_pop_closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		ji_ls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CircleActivity.this,
						JulsActivity.class);
				startActivity(intent);
			}
		});
		ji_xu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				layout.removeAllViews();
				claert = new Circleview(CircleActivity.this, screnWidth, x, y,
						handler);
				layout.addView(claert, layoutW, layoutH);
				System.out.println(layoutW + "~" + layoutH);
				start.setOnClickListener(clickListener);
			}
		});

	}

	private void showPopupWindowTip(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

	private WareDao wareDao;
	private UserRegisterData registerData;
	private String yth = "";
	private LinearLayout main;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_layout1);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		wareDao = new WareDao(getApplicationContext());
		try {
			registerData = wareDao.findIsLoginHengyuCode();
			yth = registerData.getHengyuCode().toString();
		} catch (NullPointerException e) {
			// TODO: handle exception
			NewDataToast.makeText(getApplicationContext(), "未登录", false, 0)
					.show();
			AppManager.getAppManager().finishActivity();

		}

		web_c = (WebView) findViewById(R.id.web_c);
		web_c.getSettings().setJavaScriptEnabled(true);
		web_c.setWebChromeClient(new WebChromeClient());
		web_c.loadUrl(RealmName.REALM_NAME + "/wapSite/LotteryResultsJu918");
		main = (LinearLayout) findViewById(R.id.main);
		layout = (LinearLayout) findViewById(R.id.lottery1);
		ico_image = (ImageView) findViewById(R.id.ico_image);
		imageLoader.displayImage("drawable://" + R.drawable.circle_bg1,
				ico_image);
		lottery = (ImageView) findViewById(R.id.lottery);
		imageLoader.displayImage("drawable://" + R.drawable.circle_layout1,
				lottery);
		circle_tip = (Button) findViewById(R.id.circle_tip);
		point = findViewById(R.id.point);
		start = (Button) findViewById(R.id.begin_btn);
		circle_tip.setOnClickListener(clickListener);
		screnWidth = getWindowManager().getDefaultDisplay().getWidth();
		/**
		 * 添加随机数，制定奖项数量为上限，一般抽奖中什么都是服务器返回的，可以在请求服务器接口成功在制定转盘转到那个奖项
		 */

		layout.post(new Runnable() {

			@Override
			public void run() {
				layoutW = layout.getMeasuredWidth();
				layoutH = layout.getMeasuredHeight();
				// claertW = claert.getMeasuredWidth();
				// claertH = claert.getMeasuredHeight();

				/*
				 * point.getLocationOnScreen(location); x = location[0]; y =
				 * location[1];
				 */
				x = point.getX();
				y = point.getY();
				System.out.println(x + "--" + y + "---" + x1 + "---" + y1);
				handler.sendEmptyMessage(0);
			}
		});
	}
}