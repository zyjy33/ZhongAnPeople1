package com.zams.www.health.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.BaseFragment;
import com.zams.www.R;
import com.zams.www.health.HospitalHallActivity;
import com.zams.www.health.business.HealthListModel;
import com.zams.www.health.business.HealthManageAdapter;
import com.zams.www.health.business.HealthManagerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class HealthManagerFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView healthListView;
    private List<HealthManagerModel> mHealthData;
    private HealthManageAdapter mHealthAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_health_manager;
    }

    @Override
    protected void initView() {
        healthListView = (ListView) rootView.findViewById(R.id.health_listView);
    }

    @Override
    protected void initData() {
        mHealthData = new ArrayList<HealthManagerModel>();
        mHealthAdapter = new HealthManageAdapter(getActivity(), mHealthData);
        healthListView.setAdapter(mHealthAdapter);
    }

    @Override
    protected void requestData() {
        AsyncHttp.get(RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_medical_company_list",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String data) {
                        super.onSuccess(arg0, data);
                        HealthListModel list = JSON.parseObject(data,
                                HealthListModel.class);
                        if (list != null) {
                            List<HealthManagerModel> datas = list
                                    .getData();
                            mHealthAdapter.upData(datas);
                        }

                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {

                        super.onFailure(arg0, arg1);
                    }

                }, getActivity());
    }

    @Override
    protected void initListener() {
        healthListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
        HealthManagerModel data = mHealthData.get(position);
        int id = data.getCompany_id();
        Intent intent = new Intent(getActivity(), HospitalHallActivity.class);
        intent.putExtra(HospitalHallActivity.HELL_KEY, id);
        startActivity(intent);
    }
}
