package com.zams.www.health;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;

import java.util.List;

/**
 *
 * 我的订单
 *
 * @author Administrator
 * Failed to find provider info for com.qihoo360.apm.apmdatamanager.ApmDataProvider
 */
public class HealthManageActivity extends BaseActivity{


	private FrameLayout mLayout;
	private ListView healthListView;
	private List<HealthManagerModel> mHealthData;
	private HealthManageAdapter mHealthAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_manage);
		//		initView();
		// initData();
	}

	private void initView() {
		mLayout = (FrameLayout) findViewById(R.id.health_and_comment_layout);
		healthListView = (ListView) findViewById(R.id.health_listView);

	};

	//	private void initData() {
	//		mHealthData = new ArrayList<HealthManagerModel>();
	//		mHealthAdapter = new HealthManageAdapter(this, mHealthData);
	//		healthListView.setAdapter(mHealthAdapter);
	//
	//		AsyncHttp
	//				.get("http://zams.cn/tools/mobile_ajax.asmx/get_medical_company_list",
	//						new AsyncHttpResponseHandler() {
	//							@Override
	//							public void onSuccess(int arg0, String data) {
	//								super.onSuccess(arg0, data);
	//								HealthListModel list = JSON.parseObject(data,
	//										HealthListModel.class);
	//								if (list != null) {
	//									List<HealthManagerModel> datas = list
	//											.getData();
	//									// mHealthAdapter.upData(datas);
	//									Log.e("zhangyong ",
	//											"data= " + datas.toString());
	//								}
	//
	//							}
	//
	//							@Override
	//							public void onFailure(Throwable arg0, String arg1) {
	//								// TODO Auto-generated method stub
	//								super.onFailure(arg0, arg1);
	//							}
	//
	//						}, this);
	//	}


}
