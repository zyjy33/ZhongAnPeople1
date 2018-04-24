package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyJuFenMxAdapter;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.MyJuFenData;
import com.lglottery.www.http.Util;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zams.www.R;

/**
 *
 * 我的聚粉
 *
 * @author Administrator
 *
 */
public class MyChuangYouActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_fanhui, mImageView, mImageView1, mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private LinearLayout img_btn_order;
	private ArrayList<MyJuFenData> list;
	String user_name, user_id;
	private TextView tv_geshu, tv_tjfs, tv_hgfs, tv_fensi_geshu;
	private Button btn_settle_accounts;
	LayoutInflater mLayoutInflater;
	PopupWindow mPopupWindow;
	protected PopupWindow pop;
	private View view;
	private ImageButton btn_wechat;
	private View btn_sms;
	private View btn_wx_friend;
	private ImageButton img_btn_tencent;
	private IWXAPI api;
	private ListView listView;
	private PullToRefreshView refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_chuangyou);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(MyChuangYouActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		intren();
		String fensi = getIntent().getStringExtra("fensi");
		System.out.println("-----fensi--------------------" + fensi);
		// if (!fensi.equals("")) {
		// url = "";
		// }else {
		//
		// }
		load_list(true);
	}

	// @Override
	// protected void onResume() {
	//
	// super.onResume();
	// TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
	// if (list.size() > 0) {
	// tv_geshu.setText(list.size());
	// }
	// }

	public void intren() {
		try {
			// refresh = (PullToRefreshView) findViewById(R.id.refresh);
			// refresh.setOnHeaderRefreshListener(listHeadListener);
			// refresh.setOnFooterRefreshListener(listFootListener);
			listView = (ListView) findViewById(R.id.new_list);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			img_btn_order = (LinearLayout) findViewById(R.id.img_btn_order);
			tv_geshu = (TextView) findViewById(R.id.tv_geshu);
			tv_fensi_geshu = (TextView) findViewById(R.id.tv_fensi_geshu);
			// tv_tjfs = (TextView) findViewById(R.id.tv_tjfs);
			// tv_hgfs = (TextView) findViewById(R.id.tv_hgfs);
			btn_settle_accounts = (Button) findViewById(R.id.btn_settle_accounts);
			iv_fanhui.setOnClickListener(this);
			img_btn_order.setOnClickListener(this);
			btn_settle_accounts.setOnClickListener(this);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// /**
	// * 上拉列表刷新加载
	// */
	// private OnHeaderRefreshListener listHeadListener = new
	// OnHeaderRefreshListener() {
	//
	// @Override
	// public void onHeaderRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// refresh.onHeaderRefreshComplete();
	// }
	// }, 1000);
	// }
	// };
	//
	// /**
	// * 下拉列表刷新加载
	// */
	// private OnFooterRefreshListener listFootListener = new
	// OnFooterRefreshListener() {
	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// if(RUN_METHOD==0){
	// System.out.println("RUN_METHOD========="+RUN_METHOD);
	// load_list2(true);
	// }else {
	// load_list(false);
	// }
	// refresh.onFooterRefreshComplete();
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	// }, 1000);
	// }
	// };

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					// list = (ArrayList<MyJuFenData>) msg.obj;
					try {

						System.out.println("-------------------------"
								+ list.size());
						if (list.size() > 0) {
							String num = String.valueOf(list.size());
							tv_geshu.setText(num + "个");
							// tv_fensi_geshu.setText(num+"个");
						}

						// MyJuFenMxAdapter adapter = new
						// MyJuFenMxAdapter(list,MyChuangYouActivity.this,
						// imageLoader);
						// listView.setAdapter(adapter);

						progress.CloseProgress();

					} catch (Exception e) {

						e.printStackTrace();
					}
					break;

				default:
					break;
			}
		};
	};
	/**
	 * 第1个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag) {
		RUN_METHOD = 1;
		list = new ArrayList<MyJuFenData>();
		if (flag) {
			// 计数和容器清零
			CURRENT_NUM = 1;
			list = new ArrayList<MyJuFenData>();
		}
		// get_user_child_all_list
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_user_child_all_list?user_id=" + user_id + "&user_name="
				+ user_name + "" + "&page_size=" + VIEW_NUM + "&page_index="
				+ CURRENT_NUM + "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				System.out.println("=====================二级值1" + arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					String info = jsonObject.getString("info");
					if (status.equals("y")) {
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						int len = jsonArray.length();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject object = jsonArray.getJSONObject(i);
							MyJuFenData data = new MyJuFenData();
							data.mobile = object.getString("mobile");
							data.login_sign = object.getString("login_sign");
							data.avatar = object.getString("avatar");
							data.real_name = object.getString("real_name");
							data.update_time = object.getString("update_time");
							data.nick_name = object.getString("nick_name");
							String avatar = data.avatar;//
							System.out.println("二级值2====================="
									+ avatar);
							list.add(data);
						}
						Message msg = new Message();
						msg.what = 0;
						msg.obj = list;
						handler.sendMessage(msg);
						// if(len!=0){
						// CURRENT_NUM =CURRENT_NUM+VIEW_NUM;
						// }

						load_yanglaojin();
					} else {
						progress.CloseProgress();
						Toast.makeText(MyChuangYouActivity.this, "暂无创友", 200)
								.show();
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		}, null);
	}

	private void load_yanglaojin() {
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_income_from_child_sum?to_user_id=" + user_id
				+ "&fund_id=2&expenses_id=6", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				System.out.println("=====================二级值1" + arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					String info = jsonObject.getString("info");
					if (status.equals("y")) {
						JSONObject jobt = jsonObject.getJSONObject("data");
						// String num = jobt.getString("sum");
						double num = jobt.getDouble("sum");
						String num_ll = String.valueOf(num);
						tv_fensi_geshu.setText("从粉丝团共获得福利总数:" + num_ll + "分");
					} else {
						progress.CloseProgress();
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		}, null);
	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.img_btn_order:
				// Intent intent = new Intent(MyJuFenActivity.this,
				// MyJuFenMxActivity.class);
				// startActivity(intent);
				break;
			case R.id.btn_settle_accounts:
				// Intent intent1 = new Intent(MyJuFenActivity.this,
				// FenXiangActivity.class);
				// startActivity(intent1);
				SoftWarePopuWindow(btn_settle_accounts, MyChuangYouActivity.this);
				break;
			default:
				break;
		}
	}

	/**
	 * 分享
	 *
	 * @param view2
	 * @param context
	 */
	private void SoftWarePopuWindow(View view2, final Context context) {
		try {
			mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			// inflater = LayoutInflater.from(context);
			view = mLayoutInflater.inflate(R.layout.ware_infromation_share,
					null);
			pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, false);
			final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setOutsideTouchable(true);
			// pop.setFocusable(true);
			// pop.setTouchable(true); // 设置PopupWindow可触摸
			//
			if (!pop.isShowing()) {
				pop.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
			}
			btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) view
					.findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) view
					.findViewById(R.id.img_btn_tencent);
			Button btn_holdr = (Button) findViewById(R.id.btn_holdr);
			btn_holdr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					pop.dismiss();
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

		// 新浪
		img_btn_tencent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				con(19, 1);
			}
		});

		// 微信
		btn_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				// progress.CreateProgress();
				con(16, 1);
			}
		});
		// 朋友圈
		btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pop.dismiss();
				con(17, 1);
			}
		});
		// 短信
		btn_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				pop.dismiss();
				con(18, 0);
			}
		});
	}

	private void con(final int index, int type) {
		try {

			String user_name = spPreferences.getString("user", "");
			String id = spPreferences.getString("user_id", "");
			// String data = "http://zams.cn/appshare/"+id+".html";
			String data = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zams.www";
			System.out.println("分享data======================" + data);
			String zhou = "中安民生下载地址,下载后可帮分享的好友获得福利" + data;
			System.out.println("==========" + zhou);
			softshareWxChat(zhou);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 微信分享
	 *
	 * @param text
	 */
	private void softshareWxChat(String text) {
		String temp[] = text.split("http");

		api = WXAPIFactory.createWXAPI(MyChuangYouActivity.this,
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		// webpage.webpageUrl = temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		// msg.title = "我发你一个软件,看看呗!";
		msg.title = "中安民生APP分享";
		msg.description = temp[0];
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.app_zams);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

}
