package com.zams.www.health.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.lglottery.www.widget.PullToRefreshView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.BaseFragment;
import com.zams.www.R;
import com.zams.www.health.business.NoEvaluateAdapter;
import com.zams.www.health.model.HealthOrder;
import com.zams.www.health.response.HealthOrderResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/5.
 */

/**
 * 订单列表
 */
public class NoEvaluatedFragment extends BaseFragment {

    private ListView listView;
    private NoEvaluateAdapter mAdapter;
    private int mPageIndex = 1;
    private boolean mIsLoadMore = false;
    private ArrayList<HealthOrder> mDatas;
    private PullToRefreshView refreshView;
    private String mUserId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_no_evaluated;
    }

    @Override
    protected void initView() {
        listView = (ListView) rootView.findViewById(R.id.list_view);
        refreshView = (PullToRefreshView) rootView.findViewById(R.id.refresh_view);
        mAdapter = new NoEvaluateAdapter(getActivity(), new ArrayList<String>(), R.layout.no_evaluated_item);
        listView.setAdapter(mAdapter);
        mDatas = new ArrayList<>();
        mAdapter.upData(mDatas);
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constant.LONGUSERSET, Context.MODE_PRIVATE);
        mUserId = sp.getString(Constant.USER_ID, "");
    }

    @Override
    protected void requestData() {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/get_medical_order_list?pageSize=10&pageIndex=" + mPageIndex + "&user_id=" + mUserId;
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String data) {
                super.onSuccess(arg0, data);
                HealthOrderResponse list = JSON.parseObject(data, HealthOrderResponse.class);
                if (list != null && Constant.YES.equals(list.getStatus())) {
                    List<HealthOrder> datas = list.getData();
                    if (mIsLoadMore) {
                        boolean hasMore = mAdapter.addData(datas);
                        if (!hasMore) {
                            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mAdapter.upData(datas);
                        Toast.makeText(getActivity(), "数据已更新", Toast.LENGTH_SHORT).show();
                    }
                }
                if (mIsLoadMore) {
                    refreshView.onFooterRefreshComplete();
                } else {
                    refreshView.onHeaderRefreshComplete();
                }
            }

            @Override
            public void onFailure(Throwable arg0, String arg1) {
                super.onFailure(arg0, arg1);
                if (mIsLoadMore) {
                    refreshView.onFooterRefreshComplete();
                } else {
                    refreshView.onHeaderRefreshComplete();
                }
            }

        }, getActivity());
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
