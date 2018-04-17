package com.android.cricle;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.zams.www.GuaGuaUtil;
import com.zams.www.GuaGuaUtil.onWipeListener;
import com.zams.www.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * about guaguajiang programe
 */
public class GuaGuaActivity extends BaseActivity{
	private LinearLayout gua_jiang;
	private RelativeLayout start_layout;
	private Button start;
	private GuaGuaUtil gua;
	private int  screenWidth ;
	private Button more_gua;
	private int screenHeight ;
	private ImageView tag1,tag2;
	private TextView result;
	private int COUNT = 0;
	private void addNewView(CharSequence value){

		if(gua!=null){
			gua.clear();
		}
		gua = new GuaGuaUtil(getApplicationContext(),screenWidth, screenHeight,handler);
		gua.setBackgroundColor(getResources().getColor(R.color.ff));
		//			gua.setText(value);
		gua.setBackground(getResources().getDrawable(R.drawable.j1));
		gua.setTextColor(getResources().getColor(R.color.blcak));
		gua.setTextSize(20);
		gua.setGravity(Gravity.CENTER);
		gua_jiang.removeAllViews();
		gua_jiang.addView(gua,screenWidth,screenHeight);
		gua.setEnabled(false);
		gua.setOnWipeListener(new onWipeListener() {
			@Override
			public void onWipe(int percent) {
				//-1代表完成了抽奖动作
				if(percent==-1){
					handler.sendEmptyMessage(0);
				}else {
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			AppManager.getAppManager().finishActivity();
		}
		return true;

	}
	private int until = -1;
	private Handler handler = new Handler(){
		private CircleBean circleBean;

		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case -2:
					String mss = (String) msg.obj;
					NewDataToast.makeText(getApplicationContext(), mss, false, 0).show();
					break;
				case 5:
					//				screenWidth = gua_jiang.getWidth();
					//		          screenHeight = gua_jiang.getHeight();
					until = 0;
					circleBean = (CircleBean) msg.obj;
					start_layout.setVisibility(View.GONE);
					String str = circleBean.getMsg()+"\n\n"+circleBean.getMsg1();
					addNewView(null);
					break;
				case 0:
					if(until!=-1){
						start.setText("再刮一次");
					}
					start_layout.setVisibility(View.VISIBLE);
					break;
				case -1:
					String str0 = circleBean.getMsg()+"\n\n"+circleBean.getMsg1();
					SpannableStringBuilder style=new SpannableStringBuilder(str0);
					style.setSpan(new ForegroundColorSpan(Color.RED), 0, circleBean.getMsg().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
					result.setText(style);
					break;

				default:
					break;
				//this is
			}
		};
	};
	private WareDao wareDao;
	private UserRegisterData registerData;
	private String yth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guagua_layout);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		result = (TextView) findViewById(R.id.result);
		more_gua = (Button) findViewById(R.id.more_gua);
		gua_jiang = (LinearLayout) findViewById(R.id.gua_jiang);
		start_layout = (RelativeLayout) findViewById(R.id.index_layout);
		start = (Button) findViewById(R.id.start);
		tag1 = (ImageView) findViewById(R.id.tag1);
		tag2 = (ImageView) findViewById(R.id.tag2);
		imageLoader.displayImage("drawable://"+R.drawable.gua_ctitle, tag2);
		imageLoader.displayImage("drawable://"+R.drawable.gua_title1, tag1);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				load();

			}
		});
		more_gua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initPopupWindow();
				showPopupWindow(gua_jiang);
			}
		});
		gua_jiang.post(new Runnable() {
			@Override
			public void run() {
				screenWidth = gua_jiang.getMeasuredWidth() ;
				screenHeight = gua_jiang.getMeasuredHeight();

			}
		});

		wareDao = new WareDao(getApplicationContext());
		try {
			registerData = wareDao.findIsLoginHengyuCode();
			yth = registerData.getHengyuCode().toString();
		} catch (NullPointerException e) {
			// TODO: handle exception
			NewDataToast.makeText(getApplicationContext(), "未登录", false,0).show();
			AppManager.getAppManager().finishActivity();

		}



	}
	private void load(){
		if(yth.length()!=0){
			Map<String, String> params = new HashMap<String, String>();
			params.put("yth", yth);
			params.put("luckDrawSetName", "刮啊刮");
			params.put("act", "GetLuckDrawResult");
			AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getData.ashx", params, new AsyncHttpResponseHandler(){
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println(arg1);
						JSONObject jsonObject = new JSONObject(arg1);
						String status = jsonObject.getString("status");
						if(status.equals("1")){
							//正确，开始处理
							String PrizeTypeID = 	jsonObject.getString("PrizeTypeID");//1.商品，2.表示福利
							CircleBean bean = new CircleBean();
							bean.setLuckDrawSerialNumber(jsonObject.getString("LuckDrawSerialNumber"));
							bean.setMsg(jsonObject.getString("msg"));
							bean.setMsg1(jsonObject.getString("msg2"));
							bean.setPrizeTypeID(PrizeTypeID);
							Message msg = new Message();
							msg.what = 5;
							msg.obj = bean;
							handler.sendMessage(msg);
						}else{
							Message msg = new Message();
							msg.what = -2;
							msg.obj = jsonObject.getString("msg");
							handler.sendMessage(msg);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				};
			});
		}
	}

	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.gualottery_tip, null);
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
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		Button closed = (Button) popView
				.findViewById(R.id.lglottery_pop_closed);
		closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
}
