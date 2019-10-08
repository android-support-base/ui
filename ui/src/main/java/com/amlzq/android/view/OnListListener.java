package com.amlzq.android.view;

/**
 * 列表事件监听
 * 不同的下拉刷新控件
 */
public interface OnListListener {

    /**
     * 下拉刷新事件
     */
    void onPullDownToRefresh();

    /**
     * 点击空数据集事件
     */
    void onDataEmptyClick();

    /**
     * 点击异常数据集事件
     */
    void onDataThrowableClick();

}
