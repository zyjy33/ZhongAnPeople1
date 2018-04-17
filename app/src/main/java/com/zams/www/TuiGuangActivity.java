package com.zams.www;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.adapter.TuiGuangAdapter;
import com.lglottery.www.domain.TuiGuangBean;
import com.lglottery.www.http.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TuiGuangActivity extends BaseActivity {
	private ExpandableListView listview;
	private TuiGuangAdapter adapter;
	private IWXAPI api;
	private WareDao wareDao;
	private String yth;
	private ArrayList<TuiGuangBean> lists;

	private void load() {
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetWeiXinReplyInfoList_ForAPP");
		params.put("yth", yth);
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							lists = new ArrayList<TuiGuangBean>();
							JSONObject jsonObject = new JSONObject(arg1);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							int len = jsonArray.length();
							for (int i = 0; i < len; i++) {
								TuiGuangBean bean = new TuiGuangBean();
								JSONObject object = jsonArray.getJSONObject(i);
								bean.setTitle(object.getString("title"));
								bean.setImage(object.getString("ImageUrl"));
								bean.setContent(object.getString("description"));
								bean.setDetail(object
										.getString("DetailPageContent"));
								bean.setHttp(object.getString("LinkUrl"));
								bean.setTime(object.getString("CreateTime"));
								bean.setWeiXin_LinkUrl(object
										.getString("WeiXin_LinkUrl"));
								lists.add(bean);
							}
							Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = lists;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuiguang_layout);
		load();
		// init();
		listview = (ExpandableListView) findViewById(R.id.listview);
	};

	private void softshareWxChat(TuiGuangBean bean) {
		api = WXAPIFactory.createWXAPI(getApplicationContext(),
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = bean.getWeiXin_LinkUrl();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = bean.getTitle();
		msg.description = bean.getContent();
		Bitmap thumb = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);
		System.out.println("微信註冊" + flag + "-->" + msg.thumbData);
	}

	private void softshareWxFriend(TuiGuangBean bean) {
		api = WXAPIFactory.createWXAPI(getApplicationContext(),
				Constant.APP_ID, false);
		api.registerApp(Constant.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = bean.getWeiXin_LinkUrl();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = bean.getTitle();
		msg.description = bean.getContent();
		Bitmap thumb = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
		System.out.println(flag + "-->" + msg.thumbData);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					TuiGuangBean content = (TuiGuangBean) msg.obj;
					Intent intent = new Intent(TuiGuangActivity.this,
							TuiGuangDetailActivity.class);
					intent.putExtra("content", content);
					startActivity(intent);
					break;

				case 2:
					TuiGuangBean bean = (TuiGuangBean) msg.obj;
					softshareWxChat(bean);
					break;
				case 3:
					TuiGuangBean bean1 = (TuiGuangBean) msg.obj;
					softshareWxFriend(bean1);
					break;
				case 4:
					TuiGuangBean bean3 = (TuiGuangBean) msg.obj;
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", bean3.getContent() + bean3.getHttp());
					startActivity(it);
					break;

				case 1:
					adapter = new TuiGuangAdapter(getApplicationContext(), lists,
							imageLoader, handler);
					listview.setAdapter(adapter);
					listview.setOnGroupClickListener(new OnGroupClickListener() {

						@Override
						public boolean onGroupClick(ExpandableListView arg0,
													View arg1, int arg2, long arg3) {
							return false;
						}
					});
					// 这里是控制只有一个group展开的效果
					listview.setOnGroupExpandListener(new OnGroupExpandListener() {
						@Override
						public void onGroupExpand(int groupPosition) {
							for (int i = 0; i < adapter.getGroupCount(); i++) {
								if (groupPosition != i) {
									listview.collapseGroup(i);
								}
							}
						}
					});

					break;
				default:
					break;
			}
		};
	};

	private void con(final int index, int type) {
		WareDao wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		String yth = registerData.getHengyuCode();
		AsyncHttp.get(RealmName.REALM_NAME
				+ "/mi/getdata.ashx?act=GetDownloadAPK_URL&yth=" + yth
				+ "&reqType=" + type, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					Message msg = new Message();
					msg.what = index;
					msg.obj = jsonObject.getString("msg");
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, getApplicationContext());
	}

	private void init() {
	}
}
