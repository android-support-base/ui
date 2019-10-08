package com.amlzq.android.ui;

import android.os.Bundle;
import android.view.MotionEvent;

import com.amlzq.android.app.BaseFragmentActivity;
import com.amlzq.android.util.ActivityUtil;
import com.amlzq.android.util.FragmentUtil;

/**
 * Created by amlzq on 2018/11/18.
 * android.support.v4.app.FragmentActivity 基类
 * <p>
 * Fragment是Android 3.0 API 11 引入的
 * API level1 之前要使用Support library
 * <p>
 * 片段容器的活动
 */

public abstract class BaseContainerFragmentActivity
        extends BaseFragmentActivity {

    /**
     * 传给1级fragment，activity无标题
     */
    protected String mTitle;
    protected int mTheme;
    protected boolean isCancelable;
    // 携带参数包
    protected Bundle mBundle;
    // 携带参数-Fragment Tag
    protected String mTargetFragmentTag;
    /**
     * 携带参数- Fragment Tag
     * {@link ActivityUtil}CHILD_FRAGMENT_TAG键对应的值
     */
    protected String mChildTargetFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTheme = getIntent().getIntExtra(ActivityUtil.THEME, -1);
        if (mTheme > 0) setTheme(mTheme);
        int orientation = getIntent().getIntExtra(ActivityUtil.ORIENTATION, -2);
        if (orientation > -2) setRequestedOrientation(orientation);

        super.onCreate(savedInstanceState);

        isCancelable = getIntent().getBooleanExtra(ActivityUtil.CANCELABLE, true);
        setFinishOnTouchOutside(isCancelable);
        mTitle = getIntent().getStringExtra(ActivityUtil.TITLE);
        mTargetFragmentTag = getIntent().getStringExtra(ActivityUtil.FRAGMENT_TAG);
        mChildTargetFragmentTag = getIntent().getStringExtra(FragmentUtil.CHILD_FRAGMENT_TAG);
        mBundle = getIntent().getExtras();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction() && isCancelable) {
            finishSelf();
            return true;
        }
        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }

}