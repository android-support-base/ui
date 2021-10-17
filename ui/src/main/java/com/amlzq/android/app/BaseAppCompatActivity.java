package com.amlzq.android.app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.amlzq.android.ui.LoadingDialog;
import com.amlzq.android.ui.R;
import com.amlzq.android.util.ActivityUtil;
import com.amlzq.android.util.DialogUtil;
import com.amlzq.android.util.FragmentUtil;
import com.amlzq.android.util.KeyboardUtil;
import com.amlzq.android.util.SnackbarUtil;
import com.amlzq.android.util.StringUtil;
import com.amlzq.android.util.ToastUtil;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * android.support.v7.app.AppCompatActivity extends FragmentActivity
 */

public abstract class BaseAppCompatActivity
        extends android.support.v7.app.AppCompatActivity
        implements BaseFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    /**
     * 唯一标识
     * 默认为类名
     */
    protected String mUniqueTag;
    protected android.support.v7.app.AppCompatActivity mContext;
    /**
     * 携带参数包
     */
    protected Bundle mBundle;

    // =============================================================================================
    // TODO: 活动的7个生命周期回调
    // onCreate, onStart, onResume, onPause, onStop, onRestart, onDestroy
    // =============================================================================================

    /**
     * 活动创建的时候被回调。
     *
     * @param savedInstanceState 保存的实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUniqueTag = ActivityUtil.getTag(this);
        ActivityUtil.add(this, getClass());
        mContext = this;
        mBundle = getIntent().getExtras();

        // Example code
//        WeatherFragment fragment = null;
//        if(savedInstanceState!=null){
//            fragment = getSupportFragmentManager().findFragmentByTag(WeatherFragment.TAG);
//        }
//        if(fragment == null){
//            fragment = WeatherFragment.newInstance(...);
//        }
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
        ActivityUtil.remove(this);
    }

    /**
     * @return Activity还活着
     */
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void finishSelf() {
        finishSelf(RESULT_OK, null);
    }

    public void finishSelf(int resultCode) {
        finishSelf(resultCode, null);
    }

    public void finishSelf(Intent intent) {
        finishSelf(RESULT_OK, intent);
    }

    public void finishSelf(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        hideSoftInput();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    // =============================================================================================
    // TODO: 实例的状态保存与恢复
    //
    // 参考: https://www.jianshu.com/p/5c2ec2420a0e
    // =============================================================================================

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mTargetFragment != null)
            mTargetFragment.onActivityResult(requestCode, resultCode, data);
    }

    // =============================================================================================
    // TODO: 事件处理
    // =============================================================================================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
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

    /**
     * 每次旋屏都会导致activity的销毁与重建
     *
     * @param newConfig 新配置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // =============================================================================================
    // TODO: 封装获取res方法
    // =============================================================================================

    @ColorInt
    protected int getColorCompat(@ColorRes int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    @Nullable
    protected Drawable getDrawableCompat(@DrawableRes int resId) {
        return AppCompatResources.getDrawable(mContext, resId);
    }

    public String getString(String text, String defaultValue) {
        return StringUtil.get(text, defaultValue);
    }

    public String getString(String text, @StringRes int defaultValueResId) {
        String defaultValue = getString(defaultValueResId);
        return getString(text, defaultValue);
    }

    public String getString(@StringRes int resId, String defaultValue) {
        String text = getString(resId);
        return getString(text, defaultValue);
    }

    public String getString(@StringRes int resId, @StringRes int defaultValueResId) {
        String text = getString(resId);
        return getString(text, defaultValueResId);
    }

    public float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    protected void hardwareAccelerate() {
        // API-11 需打开硬件加速
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getWindow().setFlags(0x1000000, 0x1000000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过API动态改变当前屏幕的显示方向
     * orient最终值会是明确的横屏landscape或竖屏portrait
     *
     * @return 屏幕方向值
     */
    protected int getOrientationWrapper() {
        // 宽>高为横屏,反正为竖屏
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width > height ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    // =============================================================================================
    // TODO: 软键盘相关
    // =============================================================================================

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

    // =============================================================================================
    // TODO: 管理片段
    // =============================================================================================

    /**
     * 目标片段（当前显示的片段）
     */
    protected BaseFragment mTargetFragment;
    /**
     * 携带参数
     * 目标片段标签
     */
    protected String mTargetFragmentTag;

    /**
     * 加载片段
     * <p>
     * 显示指定Tag的Fragment, 如果是第一次显示则新建并添加该Fragment
     */
    protected void startFragment(@NonNull String fragmentTag, String... args) {
        startFragment(fragmentTag, fragmentProvider(fragmentTag, args));
    }

    protected void startFragment(@NonNull String fragmentTag, @NonNull BaseFragment fragment) {

        mTargetFragment = fragment;
        mTargetFragmentTag = fragmentTag;

        FragmentUtil.showFragment(getSupportFragmentManager(),
                getFragmentContainerResId(),
                mTargetFragment,
                mTargetFragmentTag,
                R.anim.fade_in, R.anim.fade_out,
                0, 0,
                null, null,
                false, FragmentUtil.DEFAULT);
    }

    /**
     * 获取要替换的布局ID
     *
     * @return 加载fragment的布局ID
     */
    protected int getFragmentContainerResId() {
        return R.id.fragment_container;
    }

    /**
     * @param fragmentTag Fragment标签
     * @param args        传入Fragment的参数
     * @return 提供fragment
     */
    protected BaseFragment fragmentProvider(String fragmentTag, String... args) {
        return null;
    }

    // =============================================================================================
    // TODO: 通过对话框的容器（DialogFragment）显示对话框
    // =============================================================================================

    protected final void showDialog(Dialog dialog) {
        DialogUtil.showDialogFragment(dialog, getSupportFragmentManager(), null, true);
    }

    protected final void showDialog(Dialog dialog, String tag) {
        DialogUtil.showDialogFragment(dialog, getSupportFragmentManager(), tag, true);
    }

    // =============================================================================================
    // TODO: 显示加载中对话框
    // =============================================================================================

    private final Object mLock = new Object();
    protected LoadingDialog mLoadingDialog;

    public boolean isShowLoading() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    protected void showLoadingDilaog(String msg) {
        showLoadingDilaog(msg, null, true);
    }

    protected void showLoadingDilaog(String msg, DialogInterface.OnCancelListener onCancelListener, boolean cancelable) {
        synchronized (this.mLock) {
            closeLoadingDilaog();
            mLoadingDialog = new LoadingDialog(mContext);
            mLoadingDialog.setMessage(msg);
            mLoadingDialog.setOnCancelListener(onCancelListener);
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.show();
        }
    }

    public final void closeLoadingDilaog() {
        synchronized (this.mLock) {
            try {
                if (this.mLoadingDialog != null) {
                    this.mLoadingDialog.dismiss();
                    this.mLoadingDialog = null;
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

//    private LoadingDialogFragment mLoadingDialogFragment;
//
//    protected final void showLoading(String msg) {
//        synchronized (this.mLock) {
//            closeLoading();
//
//            mLoadingDialogFragment = new LoadingDialogFragment();
//            mLoadingDialogFragment.setMessage(msg);
//            mLoadingDialogFragment.setCancelable(false);
//            mLoadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);
//        }
//    }
//
//    public final void closeLoading() {
//        synchronized (this.mLock) {
//            try {
//                if (this.mLoadingDialogFragment != null) {
//                    this.mLoadingDialogFragment.dismiss();
//                    this.mLoadingDialogFragment = null;
//                }
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        }
//    }

    // =============================================================================================
    // 显示Toast或者Snackbar
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

    public View getContentView() {
        return findViewById(android.R.id.content);
    }

}