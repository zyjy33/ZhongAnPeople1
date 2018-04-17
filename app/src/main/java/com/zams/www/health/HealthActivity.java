package com.zams.www.health;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hengyushop.demo.at.AsyncHttp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HealthActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	private ListView healthListView;
	private TextView healthTv;
	
	private TextView orderTv;
	private ListView orderListView;
	
	private List<HealthManagerModel> mHealthData;
	private HealthManageAdapter mHealthAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.health_activity);
		initView();
		initData();
	}

	private void initView() {
		
		healthListView = (ListView) findViewById(R.id.health_listView);
		healthTv = (TextView) findViewById(R.id.health_manager_tv);
		orderTv = (TextView) findViewById(R.id.order_info_tv);
		orderListView = (ListView) findViewById(R.id.order_info_lv);

		healthTv.setSelected(true);
		healthListView.setOnItemClickListener(this);
		orderListView.setOnItemClickListener(this);
		healthTv.setOnClickListener(this);
		orderTv.setOnClickListener(this);

	};

	private void initData() {
		mHealthData = new ArrayList<HealthManagerModel>();
		mHealthAdapter = new HealthManageAdapter(this, mHealthData);
		healthListView.setAdapter(mHealthAdapter);

		AsyncHttp
				.get("http://zams.cn/tools/mobile_ajax.asmx/get_medical_company_list",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String data) {
								super.onSuccess(arg0, data);
								Log.e("zhangyong2 ", "data=" + data);
								HealthListModel list = JSON.parseObject(data,
										HealthListModel.class);
								if (list != null) {
									List<HealthManagerModel> datas = list
											.getData();
									mHealthAdapter.upData(datas);
									Log.e("zhangyong1 ",
											"data= " + datas.toString());
								}

							}

							@Override
							public void onFailure(Throwable arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onFailure(arg0, arg1);
							}

						}, this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View listView, int position,
			long arg3) {
		switch (listView.getId()) {
		case R.id.health_listView:
			HealthManagerModel data = mHealthData.get(position);
			int id = data.getCompany_id();
			Log.e("onItemClick ", "id= " + id);
			break;
		case R.id.order_info_lv:
			break;

		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.health_manager_tv:
			healthTv.setSelected(true);
			orderTv.setSelected(false);
			healthListView.setVisibility(View.VISIBLE);
			orderListView.setVisibility(View.GONE);
			break;
		case R.id.order_info_tv:
			healthTv.setSelected(false);
			orderTv.setSelected(true);
			healthListView.setVisibility(View.GONE);
			orderListView.setVisibility(View.VISIBLE);
			break;
		}
	}

}
