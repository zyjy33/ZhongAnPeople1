package com.zams.www;
//package com.zams.www;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import com.android.hengyu.web.RealmName;
//import com.android.internal.telephony.ITelephony;
//import com.hengyushop.airplane.adapter.YihuaCallListAdapter;
//import com.hengyushop.demo.at.AsyncHttp;
//import com.lglottery.www.common.SharedUtils;
//import com.lglottery.www.domain.YiHuaCall;
//import com.lglottery.www.http.PhoneUtils;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.zams.www.R;
//import com.zams.www.MyLetterListView.OnTouchingLetterChangedListener;
//import com.yzx.api.CallType;
//import com.yzx.api.UCSCall;
//import com.yzx.api.UCSService;
//
//import android.annotation.SuppressLint;
//import android.app.Fragment;
//import android.content.AsyncQueryHandler;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.database.Cursor;
//import android.graphics.PixelFormat;
//import android.media.AudioManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.os.RemoteException;
//import android.provider.CallLog;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.telephony.PhoneStateListener;
//import android.telephony.ServiceState;
//import android.telephony.SignalStrength;
//import android.telephony.TelephonyManager;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.ToggleButton;
//import android.widget.TextView;
//
//public class YiHuaActivity extends Fragment {
//	private RadioGroup yihua_bottom;
//	private RadioButton yb0,yb1,yb2,yb3;
//	private ViewPager viewPager;
//	private ArrayList<View> views;
//	private PageItemAdapter itemAdapter;
//	private LinearLayout IndexContent0, IndexContent1, IndexContent2,
//			IndexContent3;
//	private ListView listview;
//	private YihuaCallListAdapter callListadapter;
//	public String[] cltype = { "已接电话", "已接电话", "已拨电话", "未接来电" };
//	private Button call_send;
//	private RadioButton call_coste;
//	private List<ContentValues> cons_list,tempList,friendList;
//	private int isEdit = -1;
//	private MainFragment context;
//	private void init(Context context,View layout) {
//		yb0 = (RadioButton)layout. findViewById(R.id.yb0);
//		yb1 = (RadioButton) layout.findViewById(R.id.yb1);
//		yb2 = (RadioButton) layout.findViewById(R.id.yb2);
//		yb3 = (RadioButton) layout.findViewById(R.id.yb3);
//
//		call_coste = (RadioButton) layout.findViewById(R.id.call_coste);
//		call_send = (Button)layout. findViewById(R.id.call_send);
//		call_send.setOnClickListener(clickListener);
//		yihua_bottom = (RadioGroup)layout. findViewById(R.id.yihua_bottom);
//		layout1 = (LinearLayout) layout.findViewById(R.id.layout1);
//		layout2 = (LinearLayout) layout.findViewById(R.id.layout2);
//		layout_view = (LinearLayout) layout.findViewById(R.id.layout_view);
//		layout_view.removeAllViews();
//		viewPager = new ViewPager(context);
//		layout_view.addView(viewPager);
//		IndexContent0 = new YiHuaContent1(context, handler);
//		IndexContent1 = new YiHuaContent2(context);
//		IndexContent2 = new YiHuaContent3(context, handler);
//		IndexContent3 = new YiHuaContent4(context);
//		views = new ArrayList<View>();
//		views.add(IndexContent0);
//		views.add(IndexContent1);
//		views.add(IndexContent2);
//		views.add(IndexContent3);
//		itemAdapter = new PageItemAdapter(views);
//		viewPager.setAdapter(itemAdapter);
//		viewPager.setOnPageChangeListener(new XonPageChangeListener());
//		changeCheckStatus(0);
//		yihua_bottom.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup arg0, int arg1) {
//				changeCheckStatus(arg1);
//			}
//		});
//		call_coste.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				changeCheckStatus(2);
//				layout1.setVisibility(View.VISIBLE);
//				layout2.setVisibility(View.GONE);
//			}
//		});
//	}
//	private LayoutInflater inflater;
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		this.inflater = inflater;
//		View layout = inflater.inflate(R.layout.yihua_layout, null);
//		UCSService.init(context, true);
//		init(context, layout);
//		start();
//		context.startService(new Intent(context, ConnectService.class));
//		return layout;
//	}
//	private View popView;
//	private PopupWindow mPopupWindow;
//	private LocalBroadcastManager broadcastManager;
//	private void initPopupWindow() {
//		popView = inflater.inflate(R.layout.yihua_diget, null);
//		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, screenHeight);
//		// mPopupWindow.setBackgroundDrawable(new
//		// BitmapDrawable());//必须设置background才能消失
//		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
//				R.color.calendar_bg));
//		mPopupWindow.setOutsideTouchable(true);
//		// 自定义动画
//		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
//		// 使用系统动画
//		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//		mPopupWindow.update();
//		mPopupWindow.setTouchable(true);
//		mPopupWindow.setFocusable(true);
//		Button btn1 = (Button) popView.findViewById(R.id.btn1);
//		Button btn2 = (Button) popView.findViewById(R.id.btn2);
//		Button btn3 = (Button) popView.findViewById(R.id.btn3);
//		btn1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				if (UCSService.isConnected()) {
//					initPopupWindowDiget();
//					showPopupWindowDiget(call_send);
//				}
//			}
//		});
//		btn2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				System.out.println("回拨:" + UCSService.isConnected());
//
//				//暂时做个展示
//				String phone = yihua_phone.getText().toString();
//				DIAL.VOIP_NUMBER = phone;
//
//				if (!TextUtils.isEmpty(DIAL.VOIP_NUMBER)) {
//					System.out.println("拨打" + DIAL.VOIP_NUMBER);
//					UCSCall.dial(context, CallType.CALLBACK,
//							DIAL.VOIP_NUMBER);
//					initPopupWindowDiget2();
//					showPopupWindowDiget(call_send);
//					registerThis();
//
//
//				}
//
//
//
//			}
//		});
//		btn3.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				mPopupWindow.dismiss();
//			}
//		});
//	}
//	 private PhoneCallListener mPhoneCallListener;
//	    private TelephonyManager mTelephonyManager;
//	    // private PhoneStateListener mPhoneStateListener;
//	    private AudioManager mAudioManager;
//	 //按钮1-注册广播
//    public void registerThis() {
//    	mPhoneCallListener = new PhoneCallListener();
//        mTelephonyManager = (TelephonyManager)context. getSystemService(Context.TELEPHONY_SERVICE);
//        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        mTelephonyManager.listen(mPhoneCallListener, PhoneCallListener.LISTEN_CALL_STATE);
//    }
//    public class PhoneCallListener extends PhoneStateListener {
//
//        @Override
//        public void onCallStateChanged(int state, String incomingNumber) {
//            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//            Log.v(this.getClass().getName(), "onCallStateChanged-state: " + state);
//            Log.v(this.getClass().getName(), "onCallStateChanged-incomingNumber: " + incomingNumber);
//            switch (state) {
//            case TelephonyManager.CALL_STATE_IDLE:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                break;
//            case TelephonyManager.CALL_STATE_OFFHOOK:
//                // CALL_STATE_OFFHOOK;
//
//                break;
//            case TelephonyManager.CALL_STATE_RINGING:
//                if (incomingNumber.equals(incomingNumber)) {
//                    // mTelephonyService.endCall();
//                    // endCall();
////                    if (PhoneCallActivity.checkedId == R.id.rbtnAutoAccept) {
//                        try {
//                            Method method = Class.forName("android.os.ServiceManager").getMethod("getService",
//                                    String.class);
//
//                            IBinder binder = (IBinder) method.invoke(null, new Object[] { "phone" });
//                            ITelephony telephony = ITelephony.Stub.asInterface(binder);
//                            telephony.cancelMissedCallsNotification();
//                            telephony.answerRingingCall();
//
//
//                        } catch (Exception e) {
//                        	System.out.println("-->"+e.getMessage());
//                            try {
//                                Log.e("Sandy", "for version 4.1 or larger");
//                                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
//                                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//                                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
//                                context.sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");
//                            } catch (Exception e2) {
//                                System.out.println("==>"+e2.getMessage());
//                                Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//                                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//                                meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
//                                context. sendOrderedBroadcast(meidaButtonIntent, null);
//                            }
//                        }
////                    } else if (PhoneCallActivity.checkedId == R.id.rbtnAutoReject) {
////                        try {
////                            endCall();
////                        } catch (Exception e) {
////                            Log.e("error", e.getMessage());
////
////                        }
////                    }
//
//                } else {
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                }
//                break;
//            default:
//                break;
//            }
//            super.onCallStateChanged(state, incomingNumber);
//        }
//
//        @Override
//        public void onDataConnectionStateChanged(int state) {
//            Log.v(this.getClass().getName(), "onDataConnectionStateChanged-state: " + state);
//            super.onDataConnectionStateChanged(state);
//        }
//
//        @SuppressLint("NewApi")
//        @Override
//        public void onDataConnectionStateChanged(int state, int networkType) {
//            Log.v(this.getClass().getName(), "onDataConnectionStateChanged-state: " + state);
//            Log.v(this.getClass().getName(), "onDataConnectionStateChanged-networkType: " + networkType);
//            super.onDataConnectionStateChanged(state, networkType);
//        }
//
//        @Override
//        public void onServiceStateChanged(ServiceState serviceState) {
//            Log.v(this.getClass().getName(), "onServiceStateChanged-ServiceState: " + serviceState);
//            super.onServiceStateChanged(serviceState);
//        }
//
//        @Override
//        public void onSignalStrengthChanged(int asu) {
//            Log.v(this.getClass().getName(), "onSignalStrengthChanged-asu: " + asu);
//            super.onSignalStrengthChanged(asu);
//        }
//
//        @SuppressLint("NewApi")
//        @Override
//        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//            Log.v(this.getClass().getName(), "onSignalStrengthsChanged-signalStrength: " + signalStrength);
//            super.onSignalStrengthsChanged(signalStrength);
//        }
//    }
//    /**
//     * 利用JAVA反射机制调用ITelephony的endCall()结束通话。
//     */
//    void endCall() {
//        // 初始化iTelephony
//        Class<TelephonyManager> c = TelephonyManager.class;
//        Method getITelephonyMethod = null;
//        try {
//            // 获取所有public/private/protected/默认
//            // 方法的函数，如果只需要获取public方法，则可以调用getMethod.
//            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
//            // 将要执行的方法对象设置是否进行访问检查，也就是说对于public/private/protected/默认
//            // 我们是否能够访问。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false
//            // 则指示反射的对象应该实施 Java 语言访问检查。
//            getITelephonyMethod.setAccessible(true);
//            ITelephony iTelephony = (ITelephony) getITelephonyMethod.invoke(mTelephonyManager, (Object[]) null);
//            iTelephony.endCall();
//            Log.v(this.getClass().getName(), "endCall......");
//        } catch (Exception e) {
//            Log.e(this.getClass().getName(), "endCallError", e);
//        }
//    }
//
//    //按钮2-撤销广播
//    public void unregisterThis() {
//    	endCall();
//    }
//    public void downPhone(){
//    	TelephonyManager telephony = (TelephonyManager) context
//				.getSystemService(Context.TELEPHONY_SERVICE);
//		int state = telephony.getCallState();
//		if(state!=TelephonyManager.CALL_STATE_IDLE){
//			//不是挂断状态
//			try {
//				ITelephony iTelephony = PhoneUtils.getITelephony(telephony);
//				boolean flag = iTelephony.endCall();
//				System.out.println("挂断 吗"+flag);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//    }
//
//	private void showPopupWindow(View view) {
//		if (!mPopupWindow.isShowing()) {
//			// mPopupWindow.showAsDropDown(view,0,0);
//			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
//			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
//			// int[] location = new int[2];
//			// view.getLocationOnScreen(location);
//			mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//		}
//	}
//	private void initPopupWindowDiget2() {
//		popView = inflater.inflate(R.layout.yihua_diget_view1, null);
//		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);
//		// mPopupWindow.setBackgroundDrawable(new
//		// BitmapDrawable());//必须设置background才能消失
//		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
//				R.color.calendar_bg));
//		mPopupWindow.setOutsideTouchable(true);
//		// 自定义动画
//		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
//		// 使用系统动画
//		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//		mPopupWindow.update();
//		mPopupWindow.setTouchable(true);
//		mPopupWindow.setFocusable(true);
//		Button yihua_diget_send = (Button) popView
//				.findViewById(R.id.yihua_diget_send);
//		yihua_diget_send.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//
////				unregisterThis();//取消监听
//				downPhone();
//				mPopupWindow.dismiss();
//			}
//		});
//	}
//	int index = -1;
//	private void initPopupWindowDiget() {
//		popView = inflater.inflate(R.layout.yihua_diget_view, null);
//		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);
//		// mPopupWindow.setBackgroundDrawable(new
//		// BitmapDrawable());//必须设置background才能消失
//		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
//				R.color.calendar_bg));
//		mPopupWindow.setOutsideTouchable(true);
//		// 自定义动画
//		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
//		// 使用系统动画
//		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//		mPopupWindow.update();
//		mPopupWindow.setTouchable(true);
//		mPopupWindow.setFocusable(true);
//		System.out.println("连接状态:" + UCSService.isConnected());
//		String phone = yihua_phone.getText().toString();
//		DIAL.VOIP_NUMBER = phone;
//
//		if (!TextUtils.isEmpty(DIAL.VOIP_NUMBER)) {
//			System.out.println("拨打" + DIAL.VOIP_NUMBER);
//			UCSCall.dial(context, CallType.DIRECT, DIAL.VOIP_NUMBER);
//		}
//		Button btn1 = (Button) popView.findViewById(R.id.btn1);
//		Button btn2 = (Button) popView.findViewById(R.id.btn2);
//		Button btn3 = (Button) popView.findViewById(R.id.btn3);
//		Button yihua_diget_send = (Button) popView
//				.findViewById(R.id.yihua_diget_send);
//		btn1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				UCSCall.setMicMute(!UCSCall.isMicMute());
//			}
//		});
//
//		btn2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//
//				UCSCall.setSpeakerphone(!UCSCall.isSpeakerphoneOn());
//			}
//		});
//		btn3.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//			}
//		});
//		yihua_diget_send.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				UCSCall.hangUp(DIAL.VOIP_NUMBER);
//				mPopupWindow.dismiss();
//			}
//		});
//	}
//
//	private void showPopupWindowDiget(View view) {
//		if (!mPopupWindow.isShowing()) {
//			// mPopupWindow.showAsDropDown(view,0,0);
//			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
//			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
//			// int[] location = new int[2];
//			// view.getLocationOnScreen(location);
//			mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//		}
//	}
//
//	public ArrayList<YiHuaCall> getCallLogs() {
//		ArrayList<YiHuaCall> callLogs = new ArrayList<YiHuaCall>();
//		String[] projection = { CallLog.Calls.DATE, // 日期
//				CallLog.Calls.NUMBER, // 号码
//				CallLog.Calls.TYPE, // 类型
//				CallLog.Calls.CACHED_NAME, // 名字
//		};
//		SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
//		Date date;
//		try {
//			ContentResolver cr = context.getContentResolver();
//			Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, projection,
//					null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
//			if (cursor != null && cursor.getCount() > 0) {
//				cursor.moveToFirst();
//				while (cursor.moveToNext()) {
//					YiHuaCall call = new YiHuaCall();
//					date = new Date(cursor.getLong(cursor
//							.getColumnIndex(CallLog.Calls.DATE)));
//					String number = cursor.getString(cursor
//							.getColumnIndex(CallLog.Calls.NUMBER));
//					int type = cursor.getInt(cursor
//							.getColumnIndex(CallLog.Calls.TYPE));
//					String cachedName = cursor.getString(cursor
//							.getColumnIndex(CallLog.Calls.CACHED_NAME));
//					call.setName(cachedName);
//					call.setPhone(number);
//					call.setTime(sfd.format(date));
//					call.setType(type);
//
//					/*
//					 * String callLog = cltype[type] + ";" + sfd.format(date) +
//					 * ";" + cachedName + ";" + number;
//					 */
//					callLogs.add(call);
//				}
//				cursor.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return callLogs;
//	}
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		context.stopService(new Intent(context, ConnectService.class));
//	}
//
//	private void changeCheckStatus(int i) {
//		switch (i) {
//
//		case 0:
//		case R.id.yb0:
//			// 承接radio按钮事件
//			viewPager.setCurrentItem(0);
//			yb0.setChecked(true);
//			if(	isEdit == 1){
//
//				layout1.setVisibility(View.GONE);
//				layout2.setVisibility(View.VISIBLE);
//			} else {
//				layout1.setVisibility(View.VISIBLE);
//				layout2.setVisibility(View.GONE);
//			}
//			break;
//		case 1:
//		case R.id.yb1:
//			viewPager.setCurrentItem(1);
//			yb1.setChecked(true);
//			break;
//		case 2:
//		case R.id.yb2:
//			// 点击到通讯录的时候就开始查询公共类目下面的信息
//			start();
//			viewPager.setCurrentItem(2);
//			yb2.setChecked(true);
//			break;
//		case 3:
//		case R.id.yb3:
//
//			viewPager.setCurrentItem(3);
//			yb3.setChecked(true);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * PageView监听:
//	 */
//	private class XonPageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageScrollStateChanged(int key) {
//
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//		}
//
//		@Override
//		public void onPageSelected(int arg0) {
//
//			changeCheckStatus(arg0);
//
//		}
//
//	}
//
//	private Handler handler = new Handler() {
//		public void dispatchMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case 0:
//				View parent = (View) msg.obj;
//				init_view0(parent);
//				break;
//			case 1:
//				View parent2 = (View) msg.obj;
//				tongxunlu_tog = (ToggleButton) parent2.findViewById(R.id.tongxunlu_tog);
//
//				personList = (ListView) parent2.findViewById(R.id.list_view);
//				letterListView = (MyLetterListView) parent2
//						.findViewById(R.id.MyLetterListView01);
//				letterListView
//						.setOnTouchingLetterChangedListener(new LetterListViewListener());
//				alphaIndexer = new HashMap<String, Integer>();
//				handler2 = new Handler();
//				overlayThread = new OverlayThread();
//				initOverlay();
//				tongxunlu_tog.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//						// TODO Auto-generated method stub
//						if(arg1){
//							asnyLoader();
//						}else {
//							start();
//						}
//					}
//				});
//				break;
//			case 2:
//					setAdapter(friendList);
//				break;
//
//			case 3:
//				String index = (String) msg.obj;
//				int len= 0;
//				if(cons_list!=null)
//				  len = cons_list.size();
//				System.out.println("长度"+len);
//				listDomains = new ArrayList<YiHuaCall>();
//				for(int j=0;j<len;j++){
//					ContentValues cv = cons_list.get(j);
//					if(cv.getAsString(NUMBER).startsWith(index)){
//						//包含这样的数值
//
//						YiHuaCall call = new YiHuaCall();
//						call.setName(cv.getAsString(NAME));
//						call.setPhone(cv.getAsString(NUMBER));
//						call.setType(-1);
//						listDomains.add(call);
//					}
//
//
//
//				}
//				callListadapter.putLists(listDomains);
//				break;
//			case 4:
//				listDomains = getCallLogs();
//				callListadapter.putLists(listDomains);
//				break;
//
//			default:
//				break;
//			}
//		};
//	};
//	SharedUtils utils_list;
//	private void asnyLoader(){
//		SharedUtils utils = new SharedUtils(context, "constans_size");
//		//好友列表
//		 utils_list = new SharedUtils(context, "constans_list");
//		//if(!String.valueOf(utils.getIntValue("size")).equals(String.valueOf(cons_list.size()))){
//			//如果两者长度不一样则，联网同步
//			ArrayList<String> constans = new ArrayList<String>();
//			for(int i=0;i<cons_list.size();i++){
//				constans.add(cons_list.get(i).getAsString(NUMBER));
//
//
//			}
//			String temps = constans.toString().replace("[", "").replace("]", "");
//			utils.setIntValue("size", cons_list.size());
//			asnyConstant(temps, utils_list);
//		/*}else {
//			//直接获取配置文件显示
//			friendList = utils_list.getStringParams(cons_list);
//			if(friendList.size()>0){
//				setAdapter(friendList);
//			}
//		}*/
//	}
//	private void asnyConstant(String am,SharedUtils utils){
//		System.out.println(RealmName.REALM_NAME+"/mi/getdata.ashx?yth=&act=GetUCPAAS_BindUsers&mobiles="+am.replaceAll(" ", ""));
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("mobiles", am.replaceAll(" ", ""));
//		params.put("yth", "");
//		params.put("act", "GetUCPAAS_BindUsers");
//		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getdata.ashx", params, new AsyncHttpResponseHandler(){
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0, arg1);
//				System.out.println(arg1);
//				try {
//					JSONObject jsonObject = new JSONObject(arg1);
//					JSONArray jsonArray = jsonObject.getJSONArray("items");
//					int len = jsonArray.length();
//					tempList = new ArrayList<ContentValues>();
//					for(int i=0;i<len;i++){
//						JSONObject object = jsonArray.getJSONObject(i);
//
//						ContentValues values = new ContentValues();
//						values.put(NUMBER, object.getString("phone"));
////						String name = findBy( cons_list, object.getString("phone"), NAME);
////						String sort = findBy(cons_list, object.getString("phone"),SORT_KEY);
////						values.put(NAME, name);
////						values.put(SORT_KEY, sort);
//					//	utils_list.setStringValue(object.getString("phone"), name+"_"+sort);
//						tempList.add(values);
//					}
//					friendList = new ArrayList<ContentValues>();
//					for(int i=0;i<cons_list.size();i++){
//					//通讯录和上传的比较
//						for(int j=0;j<tempList.size();j++){
//							if(cons_list.get(i).get(NUMBER).equals(tempList.get(j).getAsString(NUMBER))){
//								utils_list.setStringValue(tempList.get(j).getAsString(NUMBER), cons_list.get(i).get(NAME)+"_"+cons_list.get(i).get(SORT_KEY));
//								friendList.add(cons_list.get(i));
//							}
//						}
//					}
//					handler.sendEmptyMessage(2);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		});
//	}
//	/**list通訊錄
//	 * 根据某种值查找
//	 * @param key
//	 * @param compare
//	 * @return
//	 */
//	private String findBy(List<ContentValues> list, String compare,String value){
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).getAsString(NUMBER).equals(compare)){
//				return list.get(i).getAsString(value);
//			}
//		}
//		return "";
//	}
//	private ImageLoader imageLoader;
//	public YiHuaActivity(ImageLoader imageLoader,MainFragment context) {
//		// TODO Auto-generated constructor stub
//		this.imageLoader = imageLoader;
//		this.context = context;
//	}
//
//	private Button k0, k1, k2, k3, k4, k5, k6, k7, k8, k9, kx, kh;
//	private Button keyboard_delete;
//	private EditText yihua_phone;
//	private LinearLayout layout1, layout_view, layout2,lay_b;
//	ArrayList<YiHuaCall> listDomains;
//	private int screenHeight = -1;
//
//	private void init_view0(View parent) {
//		listview = (ListView) parent.findViewById(R.id.yihua_list);
//		listDomains = getCallLogs();
//		callListadapter = new YihuaCallListAdapter(context,
//				listDomains, imageLoader, handler);
//		listview.setAdapter(callListadapter);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				yihua_phone.setText(listDomains.get(arg2).getPhone());
//			}
//
//		});
//		k0 = (Button) parent.findViewById(R.id.k0);
//		k1 = (Button) parent.findViewById(R.id.k1);
//		k2 = (Button) parent.findViewById(R.id.k2);
//		k3 = (Button) parent.findViewById(R.id.k3);
//		k4 = (Button) parent.findViewById(R.id.k4);
//		k5 = (Button) parent.findViewById(R.id.k5);
//		k6 = (Button) parent.findViewById(R.id.k6);
//		k7 = (Button) parent.findViewById(R.id.k7);
//		k8 = (Button) parent.findViewById(R.id.k8);
//		k9 = (Button) parent.findViewById(R.id.k9);
//		kx = (Button) parent.findViewById(R.id.kx);
//		kh = (Button) parent.findViewById(R.id.kh);
//		keyboard_delete = (Button) parent.findViewById(R.id.keyboard_delete);
//		yihua_phone = (EditText) parent.findViewById(R.id.yihua_phone);
//		lay_b = (LinearLayout) parent.findViewById(R.id.lay_b);
//		k0.setOnClickListener(clickListener);
//		k1.setOnClickListener(clickListener);
//		k2.setOnClickListener(clickListener);
//		k3.setOnClickListener(clickListener);
//		k4.setOnClickListener(clickListener);
//		k5.setOnClickListener(clickListener);
//		k6.setOnClickListener(clickListener);
//		k7.setOnClickListener(clickListener);
//		k8.setOnClickListener(clickListener);
//		k9.setOnClickListener(clickListener);
//		kx.setOnClickListener(clickListener);
//		kh.setOnClickListener(clickListener);
//		lay_b.post(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				screenHeight = lay_b.getMeasuredHeight();
//			}
//		});
//		keyboard_delete.setOnClickListener(clickListener);
//		yihua_phone.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				String temp = arg0.toString();
//				int len = temp.length();
//				if (len != 0) {
//					Message msg = new Message();
//					msg.what = 3;
//					msg.obj = temp;
//					handler.sendMessage(msg);
//					isEdit = 1;
//					layout1.setVisibility(View.GONE);
//					layout2.setVisibility(View.VISIBLE);
//				} else {
//					isEdit = -1;
//					handler.sendEmptyMessage(4);
//					layout1.setVisibility(View.VISIBLE);
//					layout2.setVisibility(View.GONE);
//				}
//			}
//		});
//	}
//
//	private OnClickListener clickListener = new OnClickListener() {
//		@Override
//		public void onClick(View view) {
//			switch (view.getId()) {
//			case R.id.call_send:
//				SharedUtils utils = new SharedUtils(context,
//						"online_phone");
//				UCSService.init(context, true);
//				String name = utils.getStringValue("name");
//				String pass = utils.getStringValue("pass");
//				System.out.println(name + "-" + pass);
//				UCSService.connect("202d08e24f0f7d69e95b46bc5370e4ca",
//						"2b80147e84797d3daf0b7e142ed00e10", name, pass);
//				initPopupWindow();
//				showPopupWindow(call_send);
//				break;
//			case R.id.k0:
//				yihua_phone.append("0");
//				break;
//			case R.id.k1:
//				yihua_phone.append("1");
//				break;
//			case R.id.k2:
//				yihua_phone.append("2");
//				break;
//			case R.id.k3:
//				yihua_phone.append("3");
//				break;
//			case R.id.k4:
//				yihua_phone.append("4");
//				break;
//			case R.id.k5:
//				yihua_phone.append("5");
//				break;
//			case R.id.k6:
//				yihua_phone.append("6");
//				break;
//			case R.id.k7:
//				yihua_phone.append("7");
//				break;
//			case R.id.k8:
//				yihua_phone.append("8");
//				break;
//			case R.id.k9:
//				yihua_phone.append("9");
//				break;
//			case R.id.kx:
//				yihua_phone.append("*");
//				UCSCall.hangUp(DIAL.VOIP_NUMBER);
//				DIAL.VOIP_NUMBER = "";
//				break;
//			case R.id.kh:
//
//				yihua_phone.append("#");
//				break;
//			case R.id.keyboard_delete:
//				String temp = yihua_phone.getText().toString();
//				int len = temp.length();
//				if (len > 1) {
//					yihua_phone.setText(temp.substring(0, len - 1));
//				} else {
//					yihua_phone.setText("");
//				}
//
//				break;
//			default:
//				break;
//			}
//		}
//	};
//	private void start() {
//		// 点击到通讯录的时候就开始查询公共类目下面的信息
//		Uri uri = Uri.parse("content://com.android.contacts/data/phones");
//		String[] projection = { "_id", "display_name", "data1", "sort_key" };
//		asyncQuery = new MyAsyncQueryHandler(context.getContentResolver());
//		asyncQuery.startQuery(0, null, uri, projection, null, null,
//				"sort_key COLLATE LOCALIZED asc");
//	}
//
//
//
//	/****
//	 * 以下是通讯录的代码
//	 */
//
//	private BaseAdapter adapter;
//	private ListView personList;
////	private RadioButton lianxiren_item1,lianxiren_item2;
//	private ToggleButton tongxunlu_tog;
//	private TextView overlay;
//	private MyLetterListView letterListView;
//	private AsyncQueryHandler asyncQuery;
//	private static final String NAME = "name", NUMBER = "number",
//			SORT_KEY = "sort_key",CON_TYPE = "con_type",CLIENT = "client";
//	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
//	private String[] sections;// 存放存在的汉语拼音首字母
//	private Handler handler2;
//	private OverlayThread overlayThread;
//
//	// 查询联系人
//	private class MyAsyncQueryHandler extends AsyncQueryHandler {
//
//		public MyAsyncQueryHandler(ContentResolver cr) {
//			super(cr);
//
//		}
//
//		@Override
//		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
//			if (cursor != null && cursor.getCount() > 0) {
//				cons_list = new ArrayList<ContentValues>();
//				cursor.moveToFirst();
//				for (int i = 0; i < cursor.getCount(); i++) {
//					ContentValues cv = new ContentValues();
//					cursor.moveToPosition(i);
//					String name = cursor.getString(1);
//					String number = cursor.getString(2);
//					String sortKey = cursor.getString(3);
//					if (number.startsWith("+86")) {
//						cv.put(NAME, name);
//						cv.put(NUMBER, number.substring(3).replaceAll(" ", "")); // 去掉+86
//						cv.put(SORT_KEY, sortKey);
//					} else {
//						cv.put(NAME, name);
//						cv.put(NUMBER, number.replaceAll(" ", ""));
//						cv.put(SORT_KEY, sortKey);
//					}
////					SharedUtils ii = new SharedUtils(context, "demo");
////					ii.setStringValue(number.replaceAll(" ", ""),name+"_"+sortKey);
//					//-1代表是通讯录，如果是0则代表好友列表
//					cv.put(CON_TYPE, -1);
//					cons_list.add(cv);
//				}
//				if (cons_list.size() > 0) {
//					//保存通讯录的长度
//					setAdapter(cons_list);
//				}
//			}
//		}
//
//	}
//
//	private void setAdapter(List<ContentValues> list) {
//		adapter = new ListAdapter(context, list);
//		personList.setAdapter(adapter);
//
//	}
//
//	private class ListAdapter extends BaseAdapter {
//		private LayoutInflater inflater;
//		private List<ContentValues> list;
//
//		public ListAdapter(Context context, List<ContentValues> list) {
//			this.inflater = LayoutInflater.from(context);
//			this.list = list;
//			alphaIndexer = new HashMap<String, Integer>();
//			sections = new String[list.size()];
//			System.out.println(list.size());
//
//
//
//			for (int i = 0; i < list.size(); i++) {
//				// 当前汉语拼音首字母
//				String currentStr = getAlpha(list.get(i).getAsString(SORT_KEY));
//				// 上一个汉语拼音首字母，如果不存在为“ ”
//				String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
//						.getAsString(SORT_KEY)) : " ";
//				if (!previewStr.equals(currentStr)) {
//					String name = getAlpha(list.get(i).getAsString(SORT_KEY));
//					alphaIndexer.put(name, i);
////					System.out.println(name+"==="+i);
//					sections[i] = name;
//				}
//			}
//		}
//
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.yihua_con_item, null);
//				holder = new ViewHolder();
//				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
//				holder.name = (TextView) convertView.findViewById(R.id.name);
//				holder.number = (TextView) convertView
//						.findViewById(R.id.number);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			ContentValues cv = list.get(position);
//			holder.name.setText(cv.getAsString(NAME));
//			holder.number.setText(cv.getAsString(NUMBER));
//			String currentStr = getAlpha(list.get(position).getAsString(
//					SORT_KEY));
//			String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
//					position - 1).getAsString(SORT_KEY)) : " ";
//			if (!previewStr.equals(currentStr)) {
//				holder.alpha.setVisibility(View.VISIBLE);
//				holder.alpha.setText(currentStr);
//			} else {
//				holder.alpha.setVisibility(View.GONE);
//			}
//			return convertView;
//		}
//
//		private class ViewHolder {
//			TextView alpha;
//			TextView name;
//			TextView number;
//		}
//
//	}
//
//	// 初始化汉语拼音首字母弹出提示框
//	// 控制手机和显示的情况
//	private void initOverlay() {
//
//		overlay = (TextView) inflater.inflate(R.layout.yihua_con_overlay, null);
//		overlay.setVisibility(View.INVISIBLE);
//		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.TYPE_APPLICATION,
//				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//				PixelFormat.TRANSLUCENT);
//		WindowManager windowManager = (WindowManager) context
//				.getSystemService(Context.WINDOW_SERVICE);
//		windowManager.addView(overlay, lp);
//	}
//
//	private class LetterListViewListener implements
//			OnTouchingLetterChangedListener {
//
//		@Override
//		public void onTouchingLetterChanged(final String s) {
//			if (alphaIndexer.get(s) != null) {
//				int position = alphaIndexer.get(s);
//				personList.setSelection(position);
//				overlay.setText(sections[position]);
//				overlay.setVisibility(View.VISIBLE);
//				handler2.removeCallbacks(overlayThread);
//				// 延迟一秒后执行，让overlay为不可见
//				handler2.postDelayed(overlayThread, 1500);
//			}
//		}
//
//	}
//
//	// 设置overlay不可见
//	private class OverlayThread implements Runnable {
//		@Override
//		public void run() {
//			overlay.setVisibility(View.GONE);
//		}
//	}
//
//	// 获得汉语拼音首字母
//	private String getAlpha(String str) {
//		if (str == null) {
//			return "#";
//		}
//
//		if (str.trim().length() == 0) {
//			return "#";
//		}
//
//		char c = str.trim().substring(0, 1).charAt(0);
//		// 正则表达式，判断首字母是否是英文字母
//		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
//		if (pattern.matcher(c + "").matches()) {
//			return (c + "").toUpperCase();
//		} else {
//			return "#";
//		}
//	}
//
//}