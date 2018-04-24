package com.zams.www;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.movie.adapter.OneResultAdapter;
import com.hengyushop.movie.adapter.OneResultBean;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneResultActivity extends BaseActivity{
	private ListView listview;
	private OneResultAdapter resultAdapter;
	private TextView jiexiaoj;
	private ArrayList<OneResultBean> lists;
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 1:
					//关于数据访问成功之后返回的信息
					ArrayList<OneResultBean> lists = (ArrayList<OneResultBean>) msg.obj;
					resultAdapter.putData(lists);
					//AnnouncedTime
					jiexiao.setText("截止揭晓时间【"+AnnouncedTime+"】");
					//
					jiexiaoj.setText("最后"+lists.size()+"条全站购买时间记录");
					break;
				default:
					break;
			}
		};
	};
	String AnnouncedTime ;
	private TextView jiexiao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_result_activity);
		listview = (ListView) findViewById(R.id.listview);
		lists = new ArrayList<OneResultBean>();
		resultAdapter = new OneResultAdapter(OneResultActivity.this, lists, imageLoader);
		listview.setAdapter(resultAdapter);
		jiexiaoj = (TextView) findViewById(R.id.jiexiaoj);

		jiexiao = (TextView) findViewById(R.id.jiexiao);
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetLuckReleaseRecords");
		params.put("yth", "");
		params.put("productItemId", getIntent().getStringExtra("id"));
		params.put("luckDrawBatchOrderNumber", getIntent().getStringExtra("idex"));
		/*
		params.put("act", "GetLuckReleaseRecords");
		params.put("yth", "");
		params.put("productItemId", getIntent().getStringExtra("id"));
		params.put("luckDrawBatchOrderNumber", "1");
		*/
		//mi/getdata.ashx?act=GetLuckReleaseRecords&yth=test或为空&ProductItemId=1&LuckDrawBatchOrderNumber=已结束的抽奖
		//mi/getdata.ashx?act=GetLuckYiYuanJuGouAnnounceRecords&yth=test或为空&ProductItemId=1
		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getdata.ashx", params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					AnnouncedTime = jsonObject.getString("AnnouncedTime");
					if(status.equals("1")){
						JSONArray array = jsonObject.getJSONArray("items");
						ArrayList<OneResultBean> lists = new ArrayList<OneResultBean>();
						int len = array.length();
						for(int i=0;i<len;i++){
							if(i!=0){
								JSONObject object = array.getJSONObject(i);
								OneResultBean bean = new OneResultBean();
								bean.setCode(object.getString("HengYuCode"));
								bean.setComplete(object.getString("proName"));
								bean.setEnd_time(object.getString("LuckDrawTime"));
								bean.setLuck(object.getString("LuckDrawTimeFormat"));
								bean.setName(object.getString("username"));
								lists.add(bean);
							}
						}
						Message msg = new Message();
						msg.what = 1;
						msg.obj = lists;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//mi/getdata.ashx?act=GetLuckReleaseRecords&yth=test或为空&ProductItemId=1&LuckDrawBatchOrderNumber=已结束的抽奖
	}
}
