package com.android.hengyu.post;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostCYPX_Activity extends BaseActivity {
	/**
	 * 声明组件对象
	 */
	private ListView post_list;
	private Button post_jydt_back;
	private PostCyAdapter adapter;
	ArrayList<PostCyDo> list=null;
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.post_jydt_back:
					AppManager.getAppManager().finishActivity();
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 组件创建
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		post_jydt_back = (Button) findViewById(R.id.post_jydt_back);
		post_jydt_back.setOnClickListener(clickListener);
		post_list = (ListView) findViewById(R.id.post_list);


	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_cypx_layout);
		init();
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "VentureEmploymentTrain");
		params.put("yth", "admin");
		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getData.ashx", params,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {

				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
	}
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					list = (ArrayList<PostCyDo>) msg.obj;
					adapter = new PostCyAdapter(list, getApplicationContext());
					post_list.setAdapter(adapter);
					post_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
												long arg3) {

							Intent intent = new Intent(PostCYPX_Activity.this,
									PostCyDetail.class);
							intent.putExtra("ob", list.get(arg2));
							startActivity(intent);
						}
					});
					break;

				default:
					break;
			}
		};
	};
	private void parse(String result){
		try {
			JSONObject object = new JSONObject(result);
			JSONArray array = object.getJSONArray("items");
			int len = array.length();
			ArrayList<PostCyDo> alist = new ArrayList<PostCyDo>();

			for(int i=0;i<len;i++){
				JSONObject json = array.getJSONObject(i);
				PostCyDo cyDo = new PostCyDo();
				cyDo.setAdd(json.getString("provinceName"));
				cyDo.setTitle(json.getString("NewsTitle"));
				cyDo.setImg(json.getString("NewsTitleImgURL"));
				cyDo.setTime(json.getString("CreateTime"));
				cyDo.setInfo(json.getString("NewsInfo"));
				alist.add(cyDo);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = alist;
			handler.sendMessage(msg);

		} catch (JSONException e) {

			e.printStackTrace();
		}
	}
}
