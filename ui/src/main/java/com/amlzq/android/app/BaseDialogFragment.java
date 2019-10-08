package com.amlzq.android.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * A simple {@link DialogFragment} abstract subclass.
 * 一个简单的对话框片段抽象子类
 * <p>
 * 参考文章: https://www.jianshu.com/p/af6499abd5c2
 */

public abstract class BaseDialogFragment
        extends android.support.v4.app.DialogFragment
        implements View.OnClickListener {

    protected android.content.Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context; // 在此处保存全局的Context
    }

    @ColorInt
    protected int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    protected Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(mContext, resId);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        // 系统API是通过commit提交事务，有时候会报IllegalStateException : Can not perform this action after onSaveInstanceSate
        // 解决方式一：忽略此Bug。
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
        }
        // 解决方式二：重写DialogFragment
//        mDismissed = false;
//        mShownByMe = true;
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.add(this, tag);
//        ft.commitAllowingStateLoss();
        // from https://www.jianshu.com/p/f6570ce9e413
    }

    @Override
    public void onClick(View v) {
    }

}