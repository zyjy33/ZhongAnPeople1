package com.zams.www.notice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.hengyu.web.Constant;
import com.lglottery.www.widget.PullToRefreshView;
import com.zams.www.BaseFragment;
import com.zams.www.R;
import com.zams.www.health.business.OneNoticeAdapter;
import com.zams.www.health.model.OneNoticeInfoBean;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;
import com.zams.www.notice.SysNoticeManagerActivity;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 * 第一种消息
 */

public class OneNoticeFragment extends BaseFragment {

    private PullToRefreshView refreshView;
    private ListView listView;
    private OneNoticeAdapter mAdapter;
    private String mUserId;
    private String mRequestId;
    private int mPageIndex = 1;
    private boolean mIsLoadMore = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_notice;
    }

    @Override
    protected void initView() {
        refreshView = (PullToRefreshView) rootView.findViewById(R.id.refresh_view);
        listView = (ListView) rootView.findViewById(R.id.notice_list_view);
        mAdapter = new OneNoticeAdapter(getActivity(), new ArrayList<OneNoticeInfoBean>(), R.layout.notice_one_item);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserId = bundle.getString(Constant.USER_ID);
            mRequestId = bundle.getString(SysNoticeManagerActivity.REQUEST_TYPE_KEY);
        }
    }

    @Override
    protected void requestData() {
        HttpProxy.getOneTypeNoticeList(getActivity(), mUserId, mRequestId, String.valueOf(mPageIndex), new HttpCallBack<List<OneNoticeInfoBean>>() {
            @Override
            public void onSuccess(List<OneNoticeInfoBean> responseData) {
                if (mIsLoadMore) {
                    boolean hasMore = mAdapter.addData(responseData);
                    if (!hasMore) {
                        Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mAdapter.upData(responseData);
                }

                if (mIsLoadMore) {
                    refreshView.onFooterRefreshComplete();
                } else {
                    Toast.makeText(getActivity(), "数据已更新", Toast.LENGTH_SHORT).show();
                    refreshView.onHeaderRefreshComplete();
                }
            }


            @Override
            public void onError(Connection.Request request, String e) {
                if (mIsLoadMore) {
                    refreshView.onFooterRefreshComplete();
                } else {
                    refreshView.onHeaderRefreshComplete();
                }
            }
        });
    }

    @Override
    protected void initListener() {
        refreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsLoadMore = false;
                        mPageIndex = 1;
                        requestData();
                    }
                }, 500);
            }
        });
        refreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsLoadMore = true;
                        mPageIndex++;
                        requestData();
                    }
                }, 500);
            }
        });
    }
}
