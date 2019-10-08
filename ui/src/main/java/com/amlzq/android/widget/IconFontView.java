package com.amlzq.android.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 字体图标，图标藏在字体文件里面，看着是个图标，其实却是个文字。
 * iconFont是矢量图标
 */
public class IconFontView extends android.support.v7.widget.AppCompatTextView {

    public IconFontView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        setTypeface(typeface);
    }

}
