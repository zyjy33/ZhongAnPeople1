package com.zams.www.health;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.http.HttpUtils;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.health.business.HallListAdapter;
import com.zams.www.health.business.HospitalAssessAdapter;
import com.zams.www.health.business.MedicalItems;
import com.zams.www.health.business.MedicalListItems;
import com.zams.www.health.model.HealthEvaluateBean;
import com.zams.www.health.model.HealthOrder;
import com.zams.www.health.response.HealthEvaluateResponse;
import com.zams.www.health.response.HealthOrderResponse;
import com.zams.www.weiget.NoticeView;
import com.zams.www.weiget.SelectScrollView;

public class HospitalHallActivity extends BaseActivity implements SelectScrollView.onSelectItemClick, View.OnClickListener, AdapterView.OnItemClickListener, NoticeView.OnNoticeListener, XListView.IXListViewListener {
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
    private NoticeView noticeView;
    private LinearLayout left_layout;
    private LinearLayout rightLayout;

    private ListView rightListView;
    private int mRightPage = 0;
    private boolean mRightHasRequest = false;
    private HospitalAssessAdapter mRightAdapter;
    private ArrayList<Object> mRightDatas;
    private PullToRefreshView rightRefreshView;
    private String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_hall);
        Intent intent = getIntent();
        if (intent != null) {
            mCompanyId = intent.getIntExtra(HELL_KEY, 1);
        }
        SharedPreferences sp = getSharedPreferences(Constant.LONGUSERSET, MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
        initView();
        requestData(1);
    }

    private void initView() {
        //左边
        left_layout = (LinearLayout) findViewById(R.id.left_layout);
        scrollView = (SelectScrollView) findViewById(R.id.hall_title_sv);
        projectListTv = (TextView) findViewById(R.id.project_list_tv);
        projectAssessTv = (TextView) findViewById(R.id.project_assess_tv);
        hallListLv = (ListView) findViewById(R.id.hall_lv);
        assessTv = (ListView) findViewById(R.id.hall_assess_lv);
        backImg = (ImageView) findViewById(R.id.iv_back);
        noticeView = ((NoticeView) (NoticeView) findViewById(R.id.notice_view));
        //右边
        rightLayout = (LinearLayout) findViewById(R.id.right_layout);
        rightListView = (ListView) findViewById(R.id.right_list_view);

        mRightDatas = new ArrayList<>();
        mRightAdapter = new HospitalAssessAdapter(this, mRightDatas, R.layout.hospital_item);
        rightListView.setAdapter(mRightAdapter);

        mSelectDrawable = getResources().getDrawable(R.drawable.orange_bg);
        mSelectDrawable.setBounds(0, 0,
                HttpUtils.dip2px(this, 50f), HttpUtils.dip2px(this, 1f));
        projectListTv.setCompoundDrawables(null, null, null, mSelectDrawable);

        mMedicalItems = new ArrayList<MedicalItems>();
        mHallListAdapter = new HallListAdapter(this, mUserId, String.valueOf(mCompanyId), mMedicalItems,
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
        noticeView.setOnNoticeListener(this);
        rightListView.setOnItemClickListener(this);
        SharedPreferences sp = this.getSharedPreferences("longuserset", MODE_PRIVATE);
        String id = sp.getString("user_id", "");


    }

    private static final String TAG = "HospitalHallActivity";

    private void requestData(int type) {
        dialogProgress.CreateProgress();
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_medical_projects_list?medical_type=" + type
                + "&company_id=" + mCompanyId;
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String data) {
                super.onSuccess(arg0, data);
                MedicalListItems list = JSON.parseObject(data,
                        MedicalListItems.class);
                if (list != null && list.getData().size() > 0) {
                    List<MedicalItems> datas = list.getData();
                    mHallListAdapter.upData(datas);
                } else {
                    mHallListAdapter.upData(null);
                }
                dialogProgress.CloseProgress();
            }

            @Override
            public void onFailure(Throwable arg0, String arg1) {
                super.onFailure(arg0, arg1);
                mHallListAdapter.upData(null);
                dialogProgress.CloseProgress();
            }

        }, this);
    }

    private void rightRequestData() {
        if (mRightHasRequest) {
            return;
        }
        mRightHasRequest = true;
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_medical_evaluate_list?company_id=" + mCompanyId;
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                HealthEvaluateResponse orderResponse = JSON.parseObject(s, HealthEvaluateResponse.class);
                if (orderResponse != null) {
                    if (Constant.YES.equals(orderResponse.getStatus())) {
                        List<HealthEvaluateBean> data = orderResponse.getData();
                        mRightAdapter.upData(data);
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
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
            case R.id.project_list_tv://左边
                left_layout.setVisibility(View.VISIBLE);
                rightLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                hallListLv.setVisibility(View.VISIBLE);
                assessTv.setVisibility(View.GONE);
                projectListTv.setSelected(true);
                projectAssessTv.setSelected(false);

                projectListTv.setBackgroundColor(getResources().getColor(R.color.white));
                projectListTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                projectAssessTv.setCompoundDrawables(null, null, null, null);
                break;
            case R.id.project_assess_tv:
                rightRequestData();
                left_layout.setVisibility(View.GONE);
                rightLayout.setVisibility(View.VISIBLE);
                hallListLv.setVisibility(View.GONE);
                assessTv.setVisibility(View.VISIBLE);
                projectListTv.setSelected(false);
                projectAssessTv.setSelected(true);
                scrollView.setVisibility(View.GONE);
                projectAssessTv.setCompoundDrawables(null, null, null, mSelectDrawable);
                projectListTv.setCompoundDrawables(null, null, null, null);
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
                intent.putExtra(DescriptionActivity.COMPANY_ID, mCompanyId);
                startActivity(intent);
                break;
            case R.id.hall_assess_lv:
                Toast.makeText(this, "体检项目", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_list_view: //评价
//                Intent intent1 = new Intent(this, AllEvaluatedOrderActivity.class);
//                startActivity(intent1);
                break;
        }
    }

    @Override
    public void noticeClick(View view) {

    }

    @Override
    public void onRefresh() {
        mRightPage = 0;
        Log.e(TAG, "onRefresh: " + mRightPage);
        rightRequestData();
    }

    @Override
    public void onLoadMore() {
        mRightPage++;
        Log.e(TAG, "onLoadMore: " + mRightPage);
        rightRequestData();
    }
}
