package com.amlzq.android.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by amlzq on 2017/10/31.
 * <p>
 * 显示定制对话框的对话框容器
 */

public class CustomDialogFragment
        extends android.support.v4.app.DialogFragment {

    /**
     * 自定义对话框
     */
    private Dialog mDialog;

    /**
     * 根据得到的参数，建立一个dialog
     *
     * @param savedInstanceState savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return mDialog;
    }

    /**
     * 点空白处消失时触发
     *
     * @param dialog
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    /**
     * 对话框消失的时候触发
     *
     * @param dialog
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    /**
     * 设置对话框能否消失，如：触摸对话框边缘外部和者单击返回键
     *
     * @param cancelable
     */
    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    /**
     * 设置对话框在触摸对话框边缘外部是否消失
     *
     * @param cancelable
     */
    public void setCanceledOnTouchOutside(boolean cancelable) {
        if (mDialog != null) mDialog.setCanceledOnTouchOutside(cancelable);
    }

    // ==============================================
    // TODO: 封装的方法
    // ==============================================

    public final void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

}