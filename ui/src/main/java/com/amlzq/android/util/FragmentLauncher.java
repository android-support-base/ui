package com.amlzq.android.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 片段加载器
 *
 * @reference https://github.com/Sugarya/FragmentCapsulation
 */
public class FragmentLauncher {

    // =============================================================================================
    // 片段事务操作命令，片段是通过事务操作命令加载的
    // @reference https://stackoverflow.com/a/45059794
    // =============================================================================================

    /**
     * add/remove/hide/show
     */
    public static final int FLAG_ADD_REMOVE_HIDE_SHOW = 1;
    /**
     * attach/detach(replace)
     */
    public static final int FLAG_REPLACE = 2;
    /**
     * ViewPager+TabLayout+Fragment方式开启片段
     */
    public static final int FLAG_ATTACH_DETACH_ADD_REMOVE = 3;

    @IntDef({FLAG_ADD_REMOVE_HIDE_SHOW, FLAG_REPLACE, FLAG_ATTACH_DETACH_ADD_REMOVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TransactionOpCmd {
    }

}
