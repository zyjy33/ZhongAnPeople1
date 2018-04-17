package com.zams.www;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.cricle.CircleActivity;
import com.android.cricle.GuaGuaActivity;
import com.android.cricle.JifenMainActivity;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.adapter.TuiGuang2Adapter;
import com.lglottery.www.domain.TuiGuangBean;
import com.lglottery.www.widget.InScrollListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TuiGuang2Activity extends BaseActivity{
	private InScrollListView scrool;
	private LinearLayout item0,item1,item2;
	private int screenWidth;
	private TuiGuang2Adapter adapter;
	private String yth;
	private ArrayList<TuiGuangBean> lists;
	private WareDao wareDao;

	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					TuiGuangBean content = (TuiGuangBean) msg.obj;
					Intent intent = new Intent(TuiGuang2Activity.this,
							TuiGuangDetailActivity.class);
					intent.putExtra("content", content);
					startActivity(intent);
					break;
				case 1:
					adapter = new TuiGuang2Adapter(getApplicationContext(), lists,
							imageLoader, handler);
					scrool.setAdapter(adapter);


					// 这里是控制只有一个group展开的效果

					break;

				default:
					break;
			}
		};
	};

	private void load() {
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		Map<String,String> params = new HashMap<String, String>();
		params.put("act", "GetWeiXinReplyInfoList_ForAPP");
		params.put("yth", yth);
		AsyncHttp.post_1(RealmName.REALM_NAME
				+ "/mi/getdata.ashx",params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					lists = new ArrayList<TuiGuangBean>();
					JSONObject jsonObject = new JSONObject(arg1);
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					int len = jsonArray.length();
					for (int i = 0; i < len; i++) {
						TuiGuangBean bean = new TuiGuangBean();
						JSONObject object = jsonArray.getJSONObject(i);
						bean.setTitle(object.getString("title"));
						bean.setImage(object.getString("ImageUrl"));
						bean.setContent(object.getString("description"));
						bean.setDetail(object.getString("DetailPageContent"));
						bean.setHttp(object.getString("LinkUrl"));
						bean.setTime(object.getString("CreateTime"));
						bean.setWeiXin_LinkUrl(object.getString("WeiXin_LinkUrl"));
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
		setContentView(R.layout.tuiguang2_layout);
		item0 = (LinearLayout) findViewById(R.id.item0);
		item1 = (LinearLayout) findViewById(R.id.item1);
		item2 = (LinearLayout) findViewById(R.id.item2);
		scrool = (InScrollListView) findViewById(R.id.scrool);
		load();


		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;// 宽度height = dm.heightPixels ;
		set(item0, 203, 307);
		set1(item1, 81, 307);
		set1(item2, 81, 307);
		item0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent40 = new Intent(TuiGuang2Activity.this,
						JifenMainActivity.class);
				startActivity(intent40);
			}
		});
		item1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent42 = new Intent(TuiGuang2Activity.this,
						CircleActivity.class);
				startActivity(intent42);
			}
		});
		item2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent41 = new Intent(TuiGuang2Activity.this,
						GuaGuaActivity.class);
				startActivity(intent41);
			}
		});
	};
	private void set(LinearLayout layout,int x,int y){
		double scale_b = (double) x / y;
		double layout2_height = screenWidth * scale_b;
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) layout2_height));
	}
	private void set1(LinearLayout layout,int x,int y){
		double scale_b = (double) x / y;
		double layout2_height = screenWidth * scale_b;
		layout.setLayoutParams(new LayoutParams(screenWidth/2,
				(int) layout2_height));
	}
}
