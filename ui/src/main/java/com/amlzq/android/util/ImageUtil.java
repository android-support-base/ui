package com.amlzq.android.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import com.amlzq.android.content.ContextHolder;

/**
 * Created by amlzq on 2018/11/29.
 * <p>
 * 图片工具，解决兼容性问题
 */
public class ImageUtil {

    /**
     * @hide
     */
    ImageUtil() {
    }

    /**
     * ImageView设置Drawable
     * 处理版本兼容问题
     *
     * @param iv    ImageView
     * @param resId 资源ID
     */
    @SuppressWarnings("deprecation")
    public void setDrawable(ImageView iv, int resId) {
        Context cxt = ContextHolder.getContext();
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = cxt.getResources().getDrawable(resId, cxt.getTheme());
        } else {
            drawable = cxt.getResources().getDrawable(resId);
        }
        iv.setImageDrawable(drawable);
    }

}
