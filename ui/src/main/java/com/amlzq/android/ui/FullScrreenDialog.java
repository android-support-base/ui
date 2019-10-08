package com.amlzq.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by amlzq on 2018/10/24.
 * <p>
 * 全屏对话框
 * <p>
 * 参考: https://juejin.im/post/58de0a9a44d904006d04cead
 */

public abstract class FullScrreenDialog extends Dialog {

    public FullScrreenDialog(Context context) {
        super(context);
        // 必须在setContentView之前执行，为了兼容一些低版本的，不让显示Title部分
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(getLayoutResId(), null);
        setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 设置全屏属性
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 必须在setContentView的后面执行
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    /**
     * @return 返回该Fragment的布局资源
     */
    @LayoutRes
    protected abstract int getLayoutResId();

}