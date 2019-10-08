package com.amlzq.android.util;

import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by amlzq on 2017/11/21.
 * <p>
 * 视图工具
 */
public class ViewUtil {

    /**
     * @hide
     */
    ViewUtil() {
    }

    // ============================================
    // TODO: 通用常量
    // ============================================


    // ============================================
    // TODO: 视图属性
    // ============================================

    /**
     * 展开触摸区域
     *
     * @param bigView      bigView
     * @param smallView    smallView
     * @param extraPadding extraPadding
     */
    public static void expandTouchArea(final View bigView, final View smallView, final int extraPadding) {
        bigView.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                smallView.getHitRect(rect);
                rect.top -= extraPadding;
                rect.left -= extraPadding;
                rect.right += extraPadding;
                rect.bottom += extraPadding;
                bigView.setTouchDelegate(new TouchDelegate(rect, smallView));
            }
        });
    }

    private static long lastClickTime;

    /**
     * 点击抖动
     *
     * @return 判断两次双击是否在0-0.5S内, true:则取消操作
     */
    public static boolean isFastDoubleClick() {
        long now = System.currentTimeMillis();
        long timeDifference = now - lastClickTime;
        if (0L < timeDifference && timeDifference < 500L) {
            //500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = now;
        return false;
    }

    public static void setProgressBarColors(ProgressBar progressBar, int backgroundColor, int progressColor) {
        // Background
        ClipDrawable bgClipDrawable = new ClipDrawable(new ColorDrawable(backgroundColor), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        bgClipDrawable.setLevel(10000);
        // Progress
        ClipDrawable progressClip = new ClipDrawable(new ColorDrawable(progressColor), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        // Setup LayerDrawable and assign to progressBar
        Drawable[] progressDrawables = {bgClipDrawable, progressClip/*second*/, progressClip};
        LayerDrawable progressLayerDrawable = new LayerDrawable(progressDrawables);
        progressLayerDrawable.setId(0, android.R.id.background);
        progressLayerDrawable.setId(1, android.R.id.secondaryProgress);
        progressLayerDrawable.setId(2, android.R.id.progress);
        progressBar.setProgressDrawable(progressLayerDrawable);
    }

}