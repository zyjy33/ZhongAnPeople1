package com.zams.www.health;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.bumptech.glide.Glide;
import com.guanggao.G;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.OrderBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zams.www.BaseFragment;
import com.zams.www.R;
import com.zams.www.health.business.MedicalItems;
import com.zams.www.health.business.MedicalListItems;
import com.zams.www.health.model.AddHealthOrderBean;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;

import org.jsoup.Connection;

import java.util.List;


public class DescriptionActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DescriptionActivity";
    public static final String DESCRIPTION_ID = "description_id";
    public static final String COMPANY_ID = "company_id";
    private ImageView backImg;
    private ImageView descriptionImg;
    private TextView descriptionTitle;
    private TextView number;
    private TextView monoy;
    private TextView descriptionTv;
    private int mDescriptionId;
    private int mCompanyId;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        if (intent != null) {
            mDescriptionId = intent.getIntExtra(DESCRIPTION_ID, 0);
            mCompanyId = intent.getIntExtra(COMPANY_ID, 0);
        }

        initView();
        requestNet();
    }


    private void initView() {
        backImg = (ImageView) findViewById(R.id.iv_back);
        descriptionImg = (ImageView) findViewById(R.id.hall_describe_img);
        descriptionTitle = (TextView) findViewById(R.id.hall_describe_title);
        number = (TextView) findViewById(R.id.hall_describe_number);
        monoy = (TextView) findViewById(R.id.hall_describe_money);
        descriptionTv = (TextView) findViewById(R.id.hall_description_tv);

        SharedPreferences sp = getSharedPreferences(Constant.LONGUSERSET, MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
        backImg.setOnClickListener(this);
    }

    private void requestNet() {
        AsyncHttp.get("http://zams.cn/tools/mobile_ajax.asmx/get_medical_projects_info?id=" + mDescriptionId,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String data) {
                        super.onSuccess(arg0, data);
                        MedicalListItems list = JSON.parseObject(data,
                                MedicalListItems.class);
                        if (list != null && Constant.YES.equals(list.getStatus())) {
                            List<MedicalItems> datas = list.getData();
                            if (datas != null && datas.size() > 0) {
                                MedicalItems medicalItem = datas.get(0);
                                descriptionTitle.setText(medicalItem.getMedical_name());
                                number.setText("每月体检" + medicalItem.getMedical_barcode() + "人");
                                monoy.setText(medicalItem.getMedical_price() + "元或" + medicalItem.getPoints() + "积分");
                                descriptionTv.setText(medicalItem.getContent());
                                Glide.with(DescriptionActivity.this)
                                        .load(RealmName.REALM_NAME + medicalItem.getImg_url())
                                        .placeholder(R.drawable.sj_fw)//图片加载出来前，显示的图片
                                        .error(R.drawable.sj_fw)//图
                                        .into(descriptionImg);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }
                }, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    private void addCar() {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/submit_medical_orderdetails";
        RequestParams params = new RequestParams();
        params.put("tjitem", mDescriptionId + "_1");
        params.put("jine", "1");
        params.put("payment_id", "5");
        params.put("user_id", mUserId);
        params.put("company_id", "" + mCompanyId);
        HttpProxy.addCarRequest(this, url, params, new HttpCallBack<AddHealthOrderBean>() {
            @Override
            public void onSuccess(AddHealthOrderBean responseData) {
                Toast.makeText(DescriptionActivity.this, "已成功添加到订单中", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Connection.Request request, String e) {
                Toast.makeText(DescriptionActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
