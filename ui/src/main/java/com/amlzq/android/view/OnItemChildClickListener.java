package com.amlzq.android.view;

import android.view.View;

/**
 * 给一些没有单击事件的列表项自定义监听
 * 比如：BaseAdapter,PagerAdapter
 */
public interface OnItemChildClickListener {
    void onItemChildClick(View view, int position);
}