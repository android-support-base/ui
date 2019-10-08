package com.amlzq.android.util;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by amlzq on 2018/9/13.
 * Snackbar utils
 * <p>
 * 参考:https://www.jianshu.com/p/cd1e80e64311
 * https://github.com/HuanHaiLiuXin/SnackbarUtils
 */
public class SnackbarUtil {

    /**
     * @hide
     */
    SnackbarUtil() {
    }

    /**
     * 短时间内显示
     *
     * @param view
     * @param messageText
     */
    public static void showShort(@NonNull View view,
                                 @NonNull CharSequence messageText) {
        Snackbar snackbar = make(view, messageText, -1,
                Snackbar.LENGTH_SHORT, -1,
                "", -1, null);
        snackbar.show();
    }

    @SuppressLint("Range")
    public static void showShort(@NonNull View view,
                                 @NonNull CharSequence messageText,
                                 @NonNull int messageTextColor,
                                 @NonNull int backgroundColor) {
        Snackbar snackbar = make(view, messageText, messageTextColor,
                Snackbar.LENGTH_SHORT, backgroundColor,
                "", -1, null);
        snackbar.show();
    }

    /**
     * 长时间显示
     *
     * @param view
     * @param messageText
     */
    public static void showLong(@NonNull View view,
                                @NonNull CharSequence messageText) {
        Snackbar snackbar = make(view, messageText, -1,
                Snackbar.LENGTH_LONG, -1,
                "", -1, null);
        snackbar.show();
    }

    /**
     * 无限期地展示
     *
     * @param view
     * @param messageText
     */
    public static void showIndefinite(@NonNull View view, @NonNull CharSequence messageText) {
        Snackbar snackbar = make(view, messageText, -1,
                Snackbar.LENGTH_INDEFINITE, -1,
                "", -1, null);
        snackbar.show();
    }

    /**
     * @param view             官方推荐使用CoordinatorLayout
     * @param messageText
     * @param duration
     * @param messageTextColor
     * @param backgroundColor
     * @param actionText
     * @param actionTextColor
     * @param listener
     */
    private static Snackbar make(@NonNull View view,
                                 @NonNull CharSequence messageText,
                                 int messageTextColor,
                                 int duration,
                                 int backgroundColor,
                                 CharSequence actionText,
                                 int actionTextColor,
                                 View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, messageText, duration);
        setColor(snackbar, messageTextColor, backgroundColor);
        snackbar.setAction(actionText, listener);
        if (actionTextColor != -1) snackbar.setActionTextColor(actionTextColor);
        return snackbar;
    }

    /**
     * @param snackbar        snackbar
     * @param messageColor    -1 则不改
     * @param backgroundColor -1 则不改
     */
    public static void setColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView(); //获取Snackbar的view
        if (view != null) {
            if (messageColor != -1)
                ((TextView) view.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
            if (backgroundColor != -1) view.setBackgroundColor(backgroundColor);//修改view的背景色
        }
    }

}