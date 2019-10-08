package com.amlzq.android.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.amlzq.android.app.BaseFragment;
import com.amlzq.android.log.Log;
import com.amlzq.android.util.KeyboardUtil;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * 作为宿主提供嵌套Fragment的容器
 * 嵌套Fragments是Android 4.2 API 17 引入的
 * <p>
 * Nested Fragments Container
 */

public abstract class BaseNestedFragment
        extends BaseFragment
        implements BaseChildFragment.OnChildFragmentInteractionListener {

    /**
     * 标题
     * bundle arguments value
     */
    protected String mTitle;

    // 子fragment标签
    protected String mTargetFragmentTag;

    // 子fragment
    protected BaseChildFragment mTargetFragment;

    // 携带参数包
    protected Bundle mBundle;

    // activity对象
    protected OnNestedFragmentListener mNestedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNestedFragmentListener) {
            mNestedListener = (OnNestedFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNestedFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNestedListener.onTargetFragmentChanged(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mNestedListener.onTargetFragmentChanged(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public interface OnNestedFragmentListener {

        // TODO:fragment内部活动
        void onNestedFragmentInteraction(String... uri);

        void onTargetFragmentChanged(BaseNestedFragment fragment);

    }

    @Override
    public void onTitleChanged(CharSequence title, int color) {
        if (null != mTargetFragment) mTargetFragment.onTitleChanged(title, color);
    }

    protected boolean isBackStackBottom() {
        int count = getBackStackEntryCount();
        Log.d("ChildFragmentManager.getBackStackEntryCount: " + count);
        return count == 0;
    }

    // ========================
    // TODO:管理子片段
    // ========================

    /**
     * @return 回退栈中FragmentTransaction的数量
     */
    protected int getBackStackEntryCount() {
        // 获取
        FragmentManager fm = getChildFragmentManager();
        return fm.getBackStackEntryCount();
    }

    protected void popChildFragment() {
        FragmentManager fm = getChildFragmentManager();
        fm.popBackStack(null, 0);
        fm.executePendingTransactions();
        KeyboardUtil.hideSoftInput(mActivity);
    }

    protected void popAllChildFragment() {
        FragmentManager fm = getChildFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.executePendingTransactions();
        KeyboardUtil.hideSoftInput(mActivity);
    }

    protected void replaceChildFragment(BaseChildFragment fragment, String tag) {
        replaceChildFragment(fragment, tag, true);
    }

    /**
     * 只有在上一个Fragment不再需要时采用
     *
     * @param fragment       fragment
     * @param tag            tag
     * @param addToBackStack if add to backstack
     */
    protected void replaceChildFragment(BaseChildFragment fragment, String tag, boolean addToBackStack) {
        fragment.setChildListener(this);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 过渡动画-渐变
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) ft.addToBackStack(null);
        commitCompat(fm, ft, addToBackStack);
    }

    private void openChildFragment(BaseChildFragment fragment, String tag) {
        fragment.setChildListener(this);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 过渡动画-渐变
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (fm.findFragmentByTag(tag) != null) {
            ft.hide(fragment);
            ft.remove(fragment);
        }
        ft.add(R.id.fragment_container, fragment, tag);
        ft.addToBackStack(null);
        commitCompat(fm, ft, true);
    }

    /**
     * 切换时不重新实例化
     *
     * @param preFragment preFragment
     * @param fragment    showfragment
     * @param tag         tag
     */
    protected void switchChildFragment(BaseChildFragment preFragment, BaseChildFragment fragment, String tag) {
        fragment.setChildListener(this);
        if (null == preFragment) {
            openChildFragment(fragment, tag);
            return;
        }
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 过渡动画-渐变
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (fragment.isAdded()) { // 先判断是否被add过
            // 隐藏当前的fragment，显示下一个
            ft.hide(preFragment).show(fragment);
        } else {
            // 隐藏当前的fragment，add下一个
            ft.hide(preFragment).add(R.id.fragment_container, fragment, tag);
        }
        ft.addToBackStack(null);
        commitCompat(fm, ft, true);
    }

    /**
     * 事务提交兼容性
     *
     * @param fm             FragmentManager
     * @param ft             FragmentTransaction
     * @param addToBackStack if add to backstack
     */
    private void commitCompat(FragmentManager fm, FragmentTransaction ft, boolean addToBackStack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (addToBackStack) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commitNow(); // 同步操作，not add to backstack
            }
        } else {
            ft.commit(); // 异步操作，加入到UI线程队列中
//            fm.executePendingTransactions(); // 立即执行 bug:FragmentManager is already executing transactions
//            ft.commitAllowingStateLoss();
        }
        KeyboardUtil.hideSoftInput(mActivity);
    }

}