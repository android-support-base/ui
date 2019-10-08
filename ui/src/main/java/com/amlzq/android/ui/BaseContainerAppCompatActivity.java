package com.amlzq.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.amlzq.android.app.BaseAppCompatActivity;
import com.amlzq.android.util.ActivityUtil;
import com.amlzq.android.util.FragmentUtil;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * android.support.v7.app.AppCompatActivity extends FragmentActivity
 */

public abstract class BaseContainerAppCompatActivity
        extends BaseAppCompatActivity {

    protected String mTitle;
    protected int mTheme;
    protected boolean isCancelable;
    /**
     * 携带参数- Fragment Tag
     * {@link ActivityUtil}CHILD_FRAGMENT_TAG键对应的值
     */
    protected String mChildTargetFragmentTag;

    // ================================================================
    // TODO: 活动的7个生命周期回调
    // onCreate, onStart, onResume, onPause, onStop, onRestart, onDestroy
    // ================================================================

    /**
     * 活动创建的时候被回调。
     *
     * @param savedInstanceState 保存的实例状态
     */
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
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    // =============================================================
    // TODO: 事件处理
    // =============================================================

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

    /**
     * https://android-developers.googleblog.com/2009/12/back-and-other-hard-keys-three-stories.html
     *
     * @param keyCode keyCode
     * @param event   KeyEvent
     * @return 物理键按下事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);

        // Example code

//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            // 返回键Back键测试
//            return true;
//        }

//        if (mTargetFragment != null && mTargetFragment.onKeyDown(keyCode, event)) {
//            return true;
//        }

//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onBackPressed();
//            return true;
//        }
    }

    /**
     * added to Android 2.0 (API 5).
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Example code

        //检查当前Fragment内部是否有待处理的回退逻辑
//        if (mTargetFragment != null && mTargetFragment.onBackPressed()) {
//            return;
//        }

        //检查当前Fragment的自维护的回退栈是否需要回退
//        if (FragmentUtil.onBackPressed(getSupportFragmentManager())) {
//            return;
//        }

    }

}