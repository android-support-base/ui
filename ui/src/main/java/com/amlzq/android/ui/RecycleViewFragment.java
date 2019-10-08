package com.amlzq.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amlzq.android.app.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amlzq on 2018/10/2.
 * RecycleViewFragment as android.support.v4.app.ListFragment
 */

public class RecycleViewFragment<M> extends BaseFragment {

    protected SwipeRefreshLayout mSwipeRefresh;
    protected RecyclerView mRecyclerView;
    protected int mNextRequestPage = 1;
    protected RecyclerView.Adapter mAdapter;
    protected List<M> mDatas = new ArrayList<M>();

    protected View mVEmpty;
    protected View mVThrowable;
    protected View mVLoad;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
