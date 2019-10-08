package com.amlzq.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amlzq.android.app.BaseFragment;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * 能嵌套的Fragment
 * <p>
 * Able Nested Fragments
 */

public abstract class BaseChildFragment
        extends BaseFragment {

    // 嵌套父级fragment对象
    protected OnChildFragmentInteractionListener mChildListener;

    public OnChildFragmentInteractionListener getChildListener() {
        return mChildListener;
    }

    public void setChildListener(OnChildFragmentInteractionListener listener) {
        this.mChildListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mChildListener != null) mChildListener.onTargetFragmentChanged(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mChildListener.onTargetFragmentChanged(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mChildListener.onTargetFragmentChanged(this);
    }

    public interface OnChildFragmentInteractionListener {

        // TODO:fragment内部活动
        // uri[0]:fragmant tag
        // uri[1]:addToBackStack
        // uri[2-n]:params
        void onChildFragmentInteraction(String... uri);

        void onTargetFragmentChanged(BaseChildFragment fragment);

    }

    public abstract View createOptionsMenu();

    @Override
    public void onTitleChanged(CharSequence title, int color) {
    }

}