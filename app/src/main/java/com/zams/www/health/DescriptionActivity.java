package com.zams.www.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.health.business.MedicalItems;
import com.zams.www.health.business.MedicalListItems;

import java.util.List;


public class DescriptionActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DescriptionActivity";
    public static final String DESCRIPTION_ID = "description_id";
    private ImageView backImg;
    private ImageView descriptionImg;
    private TextView descriptionTitle;
    private TextView number;
    private TextView monoy;
    private Button addCartBtn;
    private TextView descriptionTv;
    private int mDescriptionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        if (intent != null) {
            mDescriptionId = intent.getIntExtra(DESCRIPTION_ID, 0);
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
        addCartBtn = (Button) findViewById(R.id.hall_add_order);
        descriptionTv = (TextView) findViewById(R.id.hall_description_tv);

        addCartBtn.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    private void requestNet() {
        AsyncHttp
                .get("http://zams.cn/tools/mobile_ajax.asmx/get_medical_projects_info?id=" + mDescriptionId,
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String data) {
                                super.onSuccess(arg0, data);
                                Log.e("zhangyong3", "data=" + data);
                                MedicalListItems list = JSON.parseObject(data,
                                        MedicalListItems.class);
                                if (list != null) {
                                    List<MedicalItems> datas = list.getData();
                                    if (datas != null && datas.size() > 0) {
                                        MedicalItems medicalItem = datas.get(0);
                                        descriptionTitle.setText(medicalItem.getMedical_name());
                                        number.setText(""+medicalItem.getPoints());
                                        monoy.setText(""+medicalItem.getMedical_price());
                                        descriptionTv.setText("\t"+medicalItem.getContent());
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
            case R.id.hall_add_order:
                Log.e(TAG, "onClick: 加入购物车");
                break;
        }
    }
}
