package com.amlzq.android.util;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amlzq.android.content.ContextHolder;

/**
 * design.widget.TabLayout util
 */
public class TabLayoutUtil {

    /**
     * In your activity or fragment use this method
     * https://stackoverflow.com/questions/34173080/android-font-size-of-tabs/43156384#43156384
     */
    private void changeTabsFont(TabLayout tabLayout, int childrenIndex) {
        Typeface font = Typeface.createFromAsset(ContextHolder.getContext().getAssets(), "fonts/tabFontStyle");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(childrenIndex);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(font);
                    ((TextView) tabViewChild).setTextSize(15);
                }
            }
        }
    }

    private void changeTabTextSize(TabLayout tabLayout, int childrenIndex) {
        Typeface font = Typeface.createFromAsset(ContextHolder.getContext().getAssets(), "fonts/tabFontStyle");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(childrenIndex);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(font);
                    ((TextView) tabViewChild).setTextSize(15);
                }
            }
        }
    }

    public static TextView getItemTextView(TabLayout tabLayout, TabLayout.Tab tab) {
        TextView title = (TextView) (((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
        return title;
    }

}
