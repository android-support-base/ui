package com.amlzq.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.amlzq.android.app.BaseFragment;
import com.amlzq.android.view.OnListListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amlzq on 2018/10/2.
 * 简单轻便的RecycleViewFragment
 */

public abstract class BaseRecycleViewFragment<M, VH extends RecyclerView.ViewHolder>
        extends BaseFragment
        implements OnListListener {

    protected SwipeRefreshLayout mSwipeRefresh;
    /**
     * 下一个请求数据的页码
     */
    protected int mNextRequestPage = 1;
    protected RecyclerView mRecyclerView;
    // protected RecyclerView.Adapter<H> mAdapter;
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
    protected void afterCreate(Bundle savedInstanceState) {
        if (mSwipeRefresh == null) {
            // 下拉刷新
            mSwipeRefresh = findViewById(R.id.swipe_refresh);
            mSwipeRefresh.setColorSchemeColors(getColor(R.color.colorAccent));
            mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 刷新时关闭加载更多

                    // 总数恢复为无穷大

                    // 索引恢复为0

                    mDatas.clear();// 清空数据列表

                    // 同步数据集

                    onPullDownToRefresh();

                    // 刷新后开启加载更多
                }
            });
        }

        if (mRecyclerView == null) {
            mRecyclerView = findViewById(R.id.recycler_view);
            if (getItemAnimator() != null) mRecyclerView.setItemAnimator(getItemAnimator());
            if (getItemDecoration() != null) mRecyclerView.addItemDecoration(getItemDecoration());
            mRecyclerView.setLayoutManager(getLayoutManager());
            mRecyclerView.setHasFixedSize(true);
//            mRecyclerView.setNestedScrollingEnabled(false); // 是否允许滚动联动 appbar_scrolling_view_behavior
            mRecyclerView.setAdapter(getAdapter());
        }

        if (mVEmpty == null) {
            mVEmpty = getLayoutInflater().inflate(R.layout.content_empty, (ViewGroup) mRecyclerView.getParent(), false);
            mVEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDataEmptyClick();
                }
            });
        }
        if (mVThrowable == null) {
            mVThrowable = getLayoutInflater().inflate(R.layout.content_throwable, (ViewGroup) mRecyclerView.getParent(), false);
            mVThrowable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDataThrowableClick();
                }
            });
        }
        if (mVLoad == null) {
            mVLoad = getLayoutInflater().inflate(R.layout.content_loading, (ViewGroup) mRecyclerView.getParent(), false);
        }
    }

    /**
     * 获取适配器
     */
    protected abstract RecyclerView.Adapter<VH> getAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.ItemAnimator getItemAnimator();

    protected abstract RecyclerView.ItemDecoration getItemDecoration();

    @Override
    public void onPullDownToRefresh() {
        mNextRequestPage = 1;
    }

}
