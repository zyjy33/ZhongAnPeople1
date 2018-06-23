package com.zams.www.notice;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hengyu.web.Constant;
import com.hengyushop.demo.at.BaseActivity;
import com.zams.www.R;
import com.zams.www.health.business.SysNoticeTypeAdapter;
import com.zams.www.health.model.SysNoticeTypeBean;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 * 系统消息
 */

public class SystemNoticeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView backImg;
    private ListView listView;
    private ArrayList<SysNoticeTypeBean> mDatas;
    private SysNoticeTypeAdapter mAdapter;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sys_notice);
        initView();
        initData();
        initListener();
        requestData();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        listView = ((ListView) findViewById(R.id.list_view));
        mDatas = new ArrayList<>();
        SysNoticeTypeBean noticeTypeBean = new SysNoticeTypeBean();
        noticeTypeBean.setImgResId(R.drawable.online_service);
        noticeTypeBean.setTitle("在线客服");
        noticeTypeBean.setSubtitle("查看与客服沟通的记录");
        mDatas.add(noticeTypeBean);
        mAdapter = new SysNoticeTypeAdapter(this, mDatas, R.layout.sys_notice_type_item);
        listView.setAdapter(mAdapter);
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
        SharedPreferences noticeSp = getSharedPreferences(Constant.SP_NOTICE, MODE_PRIVATE);
        noticeSp.edit().putBoolean(Constant.SHOW_RED_POINT, false).commit();
    }

    private void initListener() {
        backImg.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void requestData() {
        HttpProxy.getSysNoticeTypeData(this, mUserId, new HttpCallBack<List<SysNoticeTypeBean>>() {
            @Override
            public void onSuccess(List<SysNoticeTypeBean> responseData) {
                mAdapter.addData(responseData);
            }

            @Override
            public void onError(Connection.Request request, String e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_img) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Toast.makeText(this, "在线客服", Toast.LENGTH_SHORT).show();
        } else if (mDatas != null && mDatas.size() > position) {
            SysNoticeTypeBean bean = mDatas.get(position);
            startSysNoticeManagerActivity(bean.getTitle(), bean.getDatatype_id(), String.valueOf(bean.getId()));
        }
    }

    private void startSysNoticeManagerActivity(String title, int typeId, String requestId) {
        Intent intent = new Intent(this, SysNoticeManagerActivity.class);
        intent.putExtra(SysNoticeManagerActivity.TITLE_KEY, title);
        intent.putExtra(SysNoticeManagerActivity.OPEN_TYPE_KEY, typeId);
        intent.putExtra(SysNoticeManagerActivity.REQUEST_TYPE_KEY, requestId);
        startActivity(intent);
    }
}
