package com.zams.www.health;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.bumptech.glide.Glide;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.http.HttpUtils;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;
import com.zams.www.health.business.HallListAdapter;
import com.zams.www.health.business.HospitalAssessAdapter;
import com.zams.www.health.business.MedicalItems;
import com.zams.www.health.business.MedicalListItems;
import com.zams.www.health.model.AddHealthOrderBean;
import com.zams.www.health.model.HealthEvaluateBean;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;
import com.zams.www.health.response.HealthEvaluateResponse;
import com.zams.www.weiget.NoticeView;
import com.zams.www.weiget.SelectScrollView;

import org.jsoup.Connection;

public class HospitalHallActivity extends BaseActivity implements SelectScrollView.onSelectItemClick, View.OnClickListener, AdapterView.OnItemClickListener, NoticeView.OnNoticeListener, XListView.IXListViewListener {
    public static final String HELL_KEY = "HELL_KEY";
    public static final String PIC_IMG = "pic_img";
    public static final String NAME = "name";
    public static final String CONTENT = "content";
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
    private LinearLayout rootLayout;
    private ImageView hospitalImg;
    private TextView hospitalNameTv;
    private TextView hospitalContentTv;
    private String mPicImg;
    private String mName;
    private String mContent;
    private Button addShopBtn;
    private int mCurrentTypeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_hall);

        SharedPreferences sp = getSharedPreferences(Constant.LONGUSERSET, MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
        initView();
        mCurrentTypeId = 1;
        requestData(mCurrentTypeId);
    }

    private void initView() {
        Intent intent = getIntent();

        if (intent != null) {
            mCompanyId = intent.getIntExtra(HELL_KEY, 1);
            mPicImg = intent.getStringExtra(PIC_IMG);
            mName = intent.getStringExtra(NAME);
            mContent = intent.getStringExtra(CONTENT);
            hospitalImg = (ImageView) findViewById(R.id.pic);
            hospitalNameTv = (TextView) findViewById(R.id.name);
            hospitalContentTv = (TextView) findViewById(R.id.content);
            Glide.with(this)
                    .load(mPicImg)
                    .placeholder(R.drawable.sj_fw)//图片加载出来前，显示的图片
                    .error(R.drawable.sj_fw)//图片加载失败后，显示的图片
                    .into(hospitalImg);
            hospitalNameTv.setText(mName);
            hospitalContentTv.setText(mContent);
        }
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);

        addShopBtn = (Button) findViewById(R.id.add_shop_btn);

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
        addShopBtn.setOnClickListener(this);
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
        mCurrentTypeId = typeId;
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
            case R.id.add_shop_btn:
                addCar();
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

    private void addCar() {
        int size = mMedicalItems.size();
        String tjitemStr = "";
        int jineCount = 0;
        for (int i = 0; i < size; i++) {
            MedicalItems items = mMedicalItems.get(i);
            if (items.getGoodsCount() > 0) {
                tjitemStr += (items.getId() + "_" + items.getGoodsCount()) + ",";
                jineCount++;
            }
        }
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/submit_medical_orderdetails";
        RequestParams params = new RequestParams();
//        params.put("tjitem", id + "_1");
//        params.put("jine", "1");
        params.put("tjitem", tjitemStr);
        params.put("jine", String.valueOf(jineCount));
        params.put("payment_id", "5");
        params.put("user_id", mUserId);
        params.put("company_id", "" + mCompanyId);
        HttpProxy.addCarRequest(this, url, params, new HttpCallBack<AddHealthOrderBean>() {
            @Override
            public void onSuccess(AddHealthOrderBean responseData) {
                Toast.makeText(HospitalHallActivity.this, "已成功添加到订单中", Toast.LENGTH_SHORT).show();
                requestData(mCurrentTypeId);
            }

            @Override
            public void onError(Connection.Request request, String e) {
                Toast.makeText(HospitalHallActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void setImmerseLayout(View view) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(HospitalHallActivity.this);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
