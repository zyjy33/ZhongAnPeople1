package com.zams.www.health;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hengyushop.demo.at.AsyncHttp;
import com.lglottery.www.http.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.health.business.HealthListModel;
import com.zams.www.health.business.HealthManageAdapter;
import com.zams.www.health.business.HealthManagerModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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
    private ImageView backImg;
    private Drawable mSelectDrawable;

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
        backImg = (ImageView) findViewById(R.id.iv_back);

        mSelectDrawable = getResources().getDrawable(R.drawable.orange_bg);
        mSelectDrawable.setBounds(0, 0,
                HttpUtils.dip2px(this, 50f),HttpUtils.dip2px(this, 1f));
        healthTv.setCompoundDrawables(null, null, null, mSelectDrawable);

        healthTv.setSelected(true);
        healthListView.setOnItemClickListener(this);
        orderListView.setOnItemClickListener(this);
        healthTv.setOnClickListener(this);
        orderTv.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

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
    public void onItemClick(AdapterView<?> listView, View view, int position,
                            long arg3) {
        switch (listView.getId()) {
            case R.id.health_listView:
                HealthManagerModel data = mHealthData.get(position);
                int id = data.getCompany_id();
                Intent intent = new Intent(this, HospitalHallActivity.class);
                intent.putExtra(HospitalHallActivity.HELL_KEY, id);
                startActivity(intent);
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
                healthTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                orderTv.setCompoundDrawables(null,null,null,null);
                break;
            case R.id.order_info_tv:
                healthTv.setSelected(false);
                orderTv.setSelected(true);
                healthListView.setVisibility(View.GONE);
                orderListView.setVisibility(View.VISIBLE);
                orderTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                healthTv.setCompoundDrawables(null,null,null,null);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
