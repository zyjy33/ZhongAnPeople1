package com.zams.www.health.fragment;

import android.widget.ListView;

import com.zams.www.BaseFragment;
import com.zams.www.R;
import com.zams.www.health.business.NoEvaluateAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NoEvaluatedFragment extends BaseFragment {

    private ListView listView;
    private NoEvaluateAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_no_evaluated;
    }

    @Override
    protected void initView() {
        listView = (ListView) rootView.findViewById(R.id.list_view);

        mAdapter = new NoEvaluateAdapter(getActivity(), new ArrayList<String>(), R.layout.no_evaluated_item);
        listView.setAdapter(mAdapter);
        ArrayList<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        datas.add("6");
        datas.add("7");
        datas.add("8");
        mAdapter.upData(datas);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }
}
