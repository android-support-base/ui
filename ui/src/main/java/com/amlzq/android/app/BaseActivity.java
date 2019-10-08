package com.amlzq.android.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.amlzq.android.ui.R;
import com.amlzq.android.util.ActivityUtil;
import com.amlzq.android.util.KeyboardUtil;
import com.amlzq.android.util.SnackbarUtil;
import com.amlzq.android.util.StringUtil;
import com.amlzq.android.util.ToastUtil;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * android.app.Activity 基类
 */

public abstract class BaseActivity extends android.app.Activity
        implements View.OnClickListener {

    /**
     * 唯一标识
     * 默认为类名
     */
    protected String mUniqueTag;
    protected android.app.Activity mContext;

    protected String mTitle;
    /**
     * theme resource id
     */
    protected int mTheme;
    protected boolean isCancelable;
    // 携带参数包
    protected Bundle mBundle;

    // =============================================================================================
    // TODO: 活动的7个生命周期回调
    // <a href="https://developer.android.com/reference/android/app/Activity#activity-lifecycle">
    // onCreate, onStart, onResume, onPause, onStop, onRestart, onDestroy
    // =============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTheme = getIntent().getIntExtra(ActivityUtil.THEME, -1);
        if (mTheme > 0) setTheme(mTheme);
        int orientation = getIntent().getIntExtra(ActivityUtil.ORIENTATION, -2);
        if (orientation > -2) setRequestedOrientation(orientation);

        super.onCreate(savedInstanceState);

        ActivityUtil.add(this, getClass());

        mUniqueTag = ActivityUtil.getTag(this);
        mContext = this;

        isCancelable = getIntent().getBooleanExtra(ActivityUtil.CANCELABLE, true);
        setFinishOnTouchOutside(isCancelable);
        mTitle = getIntent().getStringExtra(ActivityUtil.TITLE);
        mBundle = getIntent().getExtras();
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
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.remove(this);
    }

    protected boolean isAlive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isFinishing() || isDestroyed()) {
                return false;
            }
        } else if (isFinishing()) {
            return false;
        }
        return true;
    }

    /**
     * onNewIntent触发时机
     * https://www.jianshu.com/p/bd1cfc31b035
     * https://stackoverflow.com/questions/8619883/onnewintent-lifecycle-and-registered-listeners
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    // =============================================================================================
    // TODO: 封装活动的方法
    // 获取资源，res/strings/colors/styles/dimens/
    // =============================================================================================

    /**
     * @param resId resId
     * @return getColorCompat
     */
    @ColorInt
    protected int getColorCompat(@NonNull int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    public String getString(String text, String defaultValue) {
        return StringUtil.get(text, defaultValue);
    }

    public String getString(String text, @StringRes int defaultValueResId) {
        String defaultValue = getString(defaultValueResId);
        return getString(text, defaultValue);
    }

    public String getString(@StringRes int id, @StringRes int defaultValueResId) {
        String text = getString(id);
        return getString(text, defaultValueResId);
    }

    public String getString(@StringRes int id, String defaultValue) {
        String text = getString(id);
        return getString(text, defaultValue);
    }

    /**
     * 3.0 需打开硬件加速
     */
    protected void hardwareAccelerate() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getWindow().setFlags(0x1000000, 0x1000000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        KeyboardUtil.hideSoftInput(this);
    }

    /**
     * 打开软键盘
     *
     * @param view View
     */
    public void showSoftInput(View view) {
        KeyboardUtil.showSoftInput(this, view);
    }

    /**
     * 切换软键盘
     */
    public void toggleSoftInput() {
        KeyboardUtil.toggleSoftInput(this);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T extends View> T findViewById(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

    // =============================================================================================
    // TODO: 显示Toast或者Snackbar
    // =============================================================================================

    protected void showToastShort(@StringRes int resId) {
        ToastUtil.showShort(mContext, resId);
    }

    protected void showToastShort(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    protected void showToastLong(@StringRes int resId) {
        ToastUtil.showLong(mContext, resId);
    }

    protected void showToastLong(String msg) {
        ToastUtil.showLong(mContext, msg);
    }

    protected void showSnackbar(View view, @StringRes int resId) {
        SnackbarUtil.showShort(view, getString(resId));
    }

    protected void showSnackbar(View view, String msg) {
        SnackbarUtil.showShort(view, msg);
    }

    protected void showErrorSnackbar(View view, String msg) {
        SnackbarUtil.showShort(view, msg, getColorCompat(R.color.colorError), getColorCompat(R.color.colorLight));
    }

    protected void showWarnSnackbar(View view, String msg) {
        SnackbarUtil.showShort(view, msg, getColorCompat(R.color.colorWarn), getColorCompat(R.color.colorLight));
    }

    // ====================================================
    // TODO: 事件分发
    // ====================================================

    @Override
    public void onClick(View v) {
    }

}