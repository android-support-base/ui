package com.amlzq.android.app;

import android.app.Dialog;
import android.content.Context;

/**
 * 对话框基类
 */
public class BaseDialog extends Dialog {
    private int res;

    public BaseDialog(Context context, int theme, int res) {
        super(context, theme);
        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }

}