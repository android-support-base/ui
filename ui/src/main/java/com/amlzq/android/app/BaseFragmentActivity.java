package com.amlzq.android.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.amlzq.android.log.Log;
import com.amlzq.android.ui.R;
import com.amlzq.android.util.ActivityUtil;
import com.amlzq.android.util.FragmentUtil;
import com.amlzq.android.util.KeyboardUtil;
import com.amlzq.android.util.SystemUtil;
import com.amlzq.android.util.ToastUtil;

/**
 * Created by amlzq on 2017/10/31.
 * android.support.v4.app.FragmentActivity 基类
 * <p>
 * Fragment是Android 3.0 API 11 引入的
 * API level1 之前要使用Support library
 * <p>
 * 片段容器的活动
 */

public abstract class BaseFragmentActivity
        extends android.support.v4.app.FragmentActivity
        implements BaseFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    /**
     * 唯一标识
     * 默认为类名
     */
    protected String mUniqueTag;
    protected android.support.v4.app.FragmentActivity mContext;
    /**
     * 携带参数包
     */
    protected Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUniqueTag = ActivityUtil.getTag(this);
        ActivityUtil.add(this, getClass());
        mContext = this;
        mBundle = getIntent().getExtras();
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
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.remove(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    public void finishSelf() {
        finishSelf(RESULT_OK, null);
    }

    public void finishSelf(int resultCode) {
        finishSelf(resultCode, null);
    }

    public void finishSelf(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        hideSoftInput();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @ColorInt
    protected int getColorCompat(@NonNull int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    /**
     * 硬件加速
     */
    protected void hardwareAccelerate() {
        // 3.0 需打开硬件加速
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
    // 管理Fragment
    // TODO: 待整理
    // =============================================================================================

    /**
     * @param fm FragmentManager
     * @param ft FragmentTransaction
     */
    private void commitCompat(FragmentManager fm, FragmentTransaction ft) {
        if (SystemUtil.has24()) {
//            ft.commitNow();
            ft.commitAllowingStateLoss();
        } else {
            // commitAllowingStateLoss 优于 commit 方法,当使用commit方法时，系统将进行状态判断，如果状态（mStateSaved）已经保存，将发生"Can not perform this action after onSaveInstanceState"错误。
            // 如果mNoTransactionsBecause已经存在，将发生"Can not perform this action inside of " + mNoTransactionsBecause错误。
            ft.commitAllowingStateLoss();
            // 加入到UI线程队列中
//            ft.commit();
            // 立即执行
//            fm.executePendingTransactions();
        }
        hideSoftInput();
    }

    /**
     * 返回目标Fragment
     * <pre>
     * hide-preFragment
     * add-show-fragment
     * </pre>
     *
     * @param preFragment 当前隐藏的Fragment
     * @param fragment    目标显示的Fragment
     * @param tag         目标Fragment标签
     */
    protected void switchFragment(BaseFragment preFragment, BaseFragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 过渡动画-渐变
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // 添加自定义动画
        // try {
        // transaction.setCustomAnimations(android.R.animator.fade_in,
        // android.R.animator.fade_out,
        // android.R.animator.fade_in, android.R.animator.fade_out);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        if (fragment.isAdded()) { // 先判断是否被add过
            // 隐藏当前的fragment，显示下一个
            ft.hide(preFragment).show(fragment);
        } else {
            // 隐藏当前的fragment，add下一个到Activity中
            ft.hide(preFragment).add(R.id.fragment_container, fragment, tag).show(fragment);
        }
        ft.addToBackStack(null);
        commitCompat(fm, ft);
    }

    /**
     * <pre>
     * add-show-fragment
     * hide-remove-preFragment
     * </pre>
     *
     * @param fragment 目标Fragment
     * @param tag      目标Fragment标签
     */
    protected void replaceFragment(BaseFragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 过渡动画-渐变
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        /**
         * replace分解为如下
         * transaction.add(viewId, fragment);
         * transaction.show(fragment);
         * transaction.hide(preFragment);
         * transaction.remove(preFragment);
         */
        ft.replace(R.id.fragment_container, fragment, tag);
        // 记录commit一次操作，与mFragmentManager.popBackStack();匹配使用
        ft.addToBackStack(null);
        // commitAllowingStateLoss 优于 commit 方法,当使用commit方法时，系统将进行状态判断，如果状态（mStateSaved）已经保存，将发生"Can not perform this action after onSaveInstanceState"错误。
        // 如果mNoTransactionsBecause已经存在，将发生"Can not perform this action inside of " + mNoTransactionsBecause错误。
        ft.commitAllowingStateLoss();
        hideSoftInput();
    }

    protected int getBackStackEntryCount() {
        FragmentManager fm = getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }

    protected boolean isBackStackBottom() {
        int count = getBackStackEntryCount();
        Log.d("SupportFragmentManager.getBackStackEntryCount:" + count);
        return count == 1;
    }

    protected void popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, 0);
        hideSoftInput();
    }

    /**
     * <pre>
     * hide-remove-add-show-commit-executePendingTransactions
     * </pre>
     *
     * @param fragment       目标碎片
     * @param tag            目标碎片标签
     * @param addToBackStack 是否加入回退栈
     */
    protected void openFragment(BaseFragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 过渡动画-渐变
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // 添加自定义动画
        // try {
        // transaction.setCustomAnimations(android.R.animator.fade_in,
        // android.R.animator.fade_out,
        // android.R.animator.fade_in, android.R.animator.fade_out);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        if (fm.findFragmentByTag(tag) != null) {
            ft.hide(fragment);
            ft.remove(fragment);
        }
        ft.add(R.id.fragment_container, fragment, tag);
        ft.show(fragment);
        if (addToBackStack) ft.addToBackStack(null);
        commitCompat(fm, ft);
    }

    protected Fragment findFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Log.d(fragment.getTag());
        return fragment;
    }

    // =============================================================================================
    // 加载对话框
    // =============================================================================================


    /**
     * @param text        text
     * @param replaceText replaceText
     * @return getString
     */
    public String getString(String text, String replaceText) {
        return text != null && !text.isEmpty() ? text : replaceText;
    }

    /**
     * @param text  text
     * @param resId resId
     * @return getString
     */
    public String getString(String text, int resId) {
        String replaceText = getString(resId);
        return text != null && !text.isEmpty() ? text : replaceText;
    }

    protected void showToastShort(@StringRes int id) {
        ToastUtil.showShort(mContext, id);
    }

    protected void showToastLong(@StringRes int id) {
        ToastUtil.showLong(mContext, id);
    }

    protected void showToastShort(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    protected void showToastLong(String msg) {
        ToastUtil.showLong(mContext, msg);
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

}