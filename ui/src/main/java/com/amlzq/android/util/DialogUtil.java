package com.amlzq.android.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;

import com.amlzq.android.ui.CustomDialogFragment;
import com.amlzq.android.ui.LoadingDialog;

/**
 * Created by amlzq on 2018/10/25.
 * <p>
 * 对话框工具
 * <p>
 * https://developer.android.com/guide/topics/ui/dialogs?hl=zh-cn
 */

public class DialogUtil {

    /**
     * @hide
     */
    private DialogUtil() {
    }

    // =========================
    // TODO: 通用常量
    // =========================


    // ===============================================================
    // TODO: 通过对话框的容器（DialogFragment）显示对话框
    // ===============================================================

    public static void showDialogFragment(Dialog dialog,
                                          FragmentManager manager,
                                          String tag,
                                          boolean executeNow) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.setDialog(dialog);
        if (executeNow) {
            fragment.showNow(manager, tag);
        } else {
            fragment.show(manager, tag);
        }
    }

    // ====================================================
    // TODO: 显示加载中对话框
    // ====================================================

    private static Object mLock = new Object();
    private static LoadingDialog mLoadingDialog;

    public boolean isShowLoading() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    public static void showLoadingDilaog(Context context, String msg) {
        showLoadingDilaog(context, msg, null, true);
    }

    public static void showLoadingDilaog(Context context, String msg, DialogInterface.OnCancelListener onCancelListener, boolean cancelable) {
        synchronized (mLock) {
            closeLoadingDilaog();
            mLoadingDialog = new LoadingDialog(context);
            mLoadingDialog.setMessage(msg);
            mLoadingDialog.setOnCancelListener(onCancelListener);
            mLoadingDialog.show();
        }
    }

    public static void closeLoadingDilaog() {
        synchronized (mLock) {
            try {
                if (mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                    mLoadingDialog = null;
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

}