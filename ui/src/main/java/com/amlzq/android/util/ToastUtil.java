package com.amlzq.android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amlzq on 2017/7/28.
 * Toast util
 */

public class ToastUtil {

    /**
     * @hide
     */
    ToastUtil() {
    }

    // ==================================================
    // TODO: 通用常量
    // ==================================================

    // 解决多次点击后重复创建并显示的问题
    private static Toast mToast;

    public static void show(@NonNull final Context context, @NonNull final String msg, @NonNull int duration, @NonNull int gravity) {

        // 因为toast对象是静态的，因此它的生命周期与Application同样长，因此Activity退出后，它的实例引用依然被toast持有，导致它无法被回收从而内存泄露了。
        // 所以，改为一下写法,用getApplicationContext（）即可解决问题。

        new Handler(Looper.getMainLooper()) {
            @SuppressLint("ShowToast")
            public void handleMessage(Message message) {
                if (null == mToast) {
                    mToast = Toast.makeText(context.getApplicationContext(), msg, duration);
                } else {
                    mToast.setText(msg);
                    mToast.setDuration(duration);
                }
                mToast.setGravity(gravity, 0, 0);
                mToast.show();
            }
        }.sendEmptyMessage(1);
    }

    /**
     * @param context The context to use.  Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     * @param msg
     */
    public static void showShort(@NonNull final Context context, @NonNull final String msg) {
        show(context, msg, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public static void showLong(@NonNull final Context context, @NonNull final String msg) {
        show(context, msg, Gravity.CENTER, Toast.LENGTH_LONG);
    }

    public static void showShort(@NonNull Context context, @StringRes int resId) {
        final String msg = context.getString(resId);
        showShort(context, msg);
    }

    public static void showLong(@NonNull Context context, @StringRes int resId) {
        final String msg = context.getString(resId);
        showLong(context, msg);
    }

    public static void showShortInBottom(@NonNull final Context context, @NonNull final String msg) {
        show(context, msg, Gravity.BOTTOM, Toast.LENGTH_SHORT);
    }

    public static void showLongInBottom(@NonNull final Context context, @NonNull final String msg) {
        show(context, msg, Gravity.BOTTOM, Toast.LENGTH_LONG);
    }

    /**
     * 显示自定义UI和持续时间
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param toast    吐司
     * @param duration 持续时间
     */
    public static void show(@NonNull final Context context, final Toast toast, final int duration) {
        new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {

                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toast.show();
                    }
                }, 0, 3000);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toast.cancel();
                        timer.cancel();
                    }
                }, duration);
            }
        }.sendEmptyMessage(1);
    }

}