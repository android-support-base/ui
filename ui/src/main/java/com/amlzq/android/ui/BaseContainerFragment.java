package com.amlzq.android.ui;

import com.amlzq.android.app.BaseFragment;

/**
 * Created by amlzq on 2019/7/26.
 * <p>
 * 容器片段
 */

public abstract class BaseContainerFragment
        extends BaseFragment {

    private BaseFragment mTargetFragment;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibilityChangedToUser(boolean isVisibleToUser, String method) {
        super.onVisibilityChangedToUser(isVisibleToUser, method);

        // 子视图可见性
//        if (mTargetFragment == null) return;
//        if (isVisibleToUser == mTargetFragment.isVisibleToUser()) return;
//        mTargetFragment.onVisibilityChangedToUser(isVisibleToUser, mUniqueTag + "#" + method);
    }

}