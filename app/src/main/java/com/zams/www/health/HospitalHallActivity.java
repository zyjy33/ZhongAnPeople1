package com.zams.www.health;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.http.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.health.business.HallListAdapter;
import com.zams.www.health.business.MedicalItems;
import com.zams.www.health.business.MedicalListItems;
import com.zams.www.weiget.SelectScrollView;

public class HospitalHallActivity extends BaseActivity implements SelectScrollView.onSelectItemClick, View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String HELL_KEY = "HELL_KEY";
    private TextView projectListTv;
    private TextView projectAssessTv;
    private ListView hallListLv;
    private int mCompanyId;//medical_type=2&company_id=5808medical_type=2&company_id=5808
    private ArrayList<MedicalItems> mMedicalItems;
    private HallListAdapter mHallListAdapter;
    private SelectScrollView scrollView;
    private ListView assessTv;
    private ImageView backImg;
    private Drawable mSelectDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_hall);
        Intent intent = getIntent();
        if (intent != null) {
            mCompanyId = intent.getIntExtra(HELL_KEY, 1);
        }
        initView();
        requestData(1);
    }

    private void initView() {
        scrollView = (SelectScrollView) findViewById(R.id.hall_title_sv);
        projectListTv = (TextView) findViewById(R.id.project_list_tv);
        projectAssessTv = (TextView) findViewById(R.id.project_assess_tv);
        hallListLv = (ListView) findViewById(R.id.hall_lv);
        assessTv = (ListView) findViewById(R.id.hall_assess_lv);
        backImg = (ImageView) findViewById(R.id.iv_back);

        mSelectDrawable = getResources().getDrawable(R.drawable.orange_bg);
        mSelectDrawable.setBounds(0, 0,
                HttpUtils.dip2px(this, 50f),HttpUtils.dip2px(this, 1f));
        projectListTv.setCompoundDrawables(null, null, null, mSelectDrawable);

        mMedicalItems = new ArrayList<MedicalItems>();
        mHallListAdapter = new HallListAdapter(this, mMedicalItems,
                R.layout.hall_list_item);
        hallListLv.setAdapter(mHallListAdapter);

        projectListTv.setSelected(true);
        scrollView.addItemView(1, "体检项目");
        scrollView.addItemView(2, "体验项目");

        backImg.setOnClickListener(this);
        scrollView.setItemClick(this);
        projectListTv.setOnClickListener(this);
        projectAssessTv.setOnClickListener(this);
        hallListLv.setOnItemClickListener(this);
    }

    private void requestData(int type) {
        AsyncHttp
                .get("http://zams.cn/tools/mobile_ajax.asmx/get_medical_projects_list?medical_type=" + type
                                + "&company_id=" + mCompanyId,
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String data) {
                                super.onSuccess(arg0, data);
                                Log.e("zhangyong3", "data=" + data);
                                MedicalListItems list = JSON.parseObject(data,
                                        MedicalListItems.class);
                                if (list != null) {
                                    List<MedicalItems> datas = list.getData();
                                    Log.e("datas = ", "" + datas.size());
                                    mHallListAdapter.upData(datas);
                                }

                            }

                            @Override
                            public void onFailure(Throwable arg0, String arg1) {

                                super.onFailure(arg0, arg1);
                            }

                        }, this);
    }

    @Override
    public void onItemClick(int typeId) {
        requestData(typeId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.project_list_tv:
                scrollView.setVisibility(View.VISIBLE);
                hallListLv.setVisibility(View.VISIBLE);
                assessTv.setVisibility(View.GONE);
                projectListTv.setSelected(true);
                projectAssessTv.setSelected(false);

                projectListTv.setBackgroundColor(getResources().getColor(R.color.white));
                projectListTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                projectAssessTv.setCompoundDrawables(null,null,null,null);
                break;
            case R.id.project_assess_tv:
                hallListLv.setVisibility(View.GONE);
                assessTv.setVisibility(View.VISIBLE);
                projectListTv.setSelected(false);
                projectAssessTv.setSelected(true);
                scrollView.setVisibility(View.GONE);

                projectAssessTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                projectListTv.setCompoundDrawables(null,null,null,null);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.hall_lv:
                MedicalItems items = mHallListAdapter.getItem(position);
                int hallId = items.getId();
                Intent intent = new Intent(this, DescriptionActivity.class);
                intent.putExtra(DescriptionActivity.DESCRIPTION_ID, hallId);
                startActivity(intent);
                break;
            case R.id.hall_assess_lv:
                break;
        }
    }
}
