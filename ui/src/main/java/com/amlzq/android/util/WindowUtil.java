package com.amlzq.android.util;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.amlzq.android.content.ContextHolder;

/**
 * Created by amlzq on 2018/5/21.
 * <p>
 * 窗口工具
 */

public class WindowUtil {

    /**
     * @hide
     */
    WindowUtil() {
    }

    // =============================================================================================
    // TODO: 窗口属性
    // =============================================================================================

    public static void setSize(Window window, int width, int height) {
        Context cxt = ContextHolder.getContext();
        if (null != window) {
            // 一定要设置Background，如果不设置，window属性设置无效
            // window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisplayUtil.getInt(cxt, width);
            lp.height = DisplayUtil.getInt(cxt, height);
            window.setAttributes(lp);
            window.setGravity(Gravity.CENTER);
        }
    }

    public static void setStatusBarColor(Window window, int color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            //解决Android5.0以上，状态栏设置颜色后变灰的问题
            window.setStatusBarColor(color);
        }
    }

    /**
     * 当该属性设置 true 时，会在屏幕最上方预留出状态栏高度的 padding。
     * 避免在每一个页面增加android:fitsSystemWindows="true"
     * 在基类的setContentView之后执行。
     * http://blog.coderclock.com/2016/02/04/android/Android%E5%BC%80%E5%8F%91%EF%BC%9ATranslucent%20System%20Bar%20%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5/#%E7%AC%AC%E4%B8%80%E7%A7%8D%E6%96%B9%E5%BC%8F
     *
     * @param view activity.getWindow().getDecorView()
     */
    public static void setFitsSystemWindows(View view) {
        ViewGroup contentFrameLayout = (ViewGroup) view.findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    // =============================================================================================
    // TODO: Control the system UI visibility
    // https://developer.android.com/training/system-ui/
    // status bar 状态栏
    // navigation bar 导航栏（虚拟键栏）
    // =============================================================================================

    /**
     * 调暗状态和导航栏
     * Dim the Status and Navigation Bars
     *
     * @param window activity.getWindow()
     * @author https://developer.android.com/training/system-ui/dim#dim
     */
    public static void dimSystemUI(Window window) {
        // This example uses decor view, but you can use any visible view.
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示状态和导航栏
     * Reveal the Status and Navigation Bars
     *
     * @param window activity.getWindow()
     * @author https://developer.android.com/training/system-ui/dim#reveal
     */
    public static void revealSystemUI(Window window) {
        View decorView = window.getDecorView();
        // Calling setSystemUiVisibility() with a value of 0 clears
        // all flags.
        decorView.setSystemUiVisibility(0);
    }

    /**
     * 隐藏状态栏
     * Hide the status bar
     * 在setContentView之前执行
     *
     * @param window    activity.getWindow()
     * @param actionBar activity.getActionBar()
     * @author https://developer.android.com/training/system-ui/status
     */
    public static void hideStatusBar(Window window, android.app.ActionBar actionBar) {
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT >= 16) {
            View decorView = window.getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            if (actionBar != null) actionBar.hide();
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * Make Content Appear Behind the Status Bar
     *
     * @author https://developer.android.com/training/system-ui/status#behind
     */
    public static void behindStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= 16) {
            View decorView = window.getDecorView();
            // On Android 4.1 and higher, you can set your application's content to appear behind the status bar,
            // so that the content doesn't resize as the status bar hides and shows.
            // To do this, use SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.
            // You may also need to use SYSTEM_UI_FLAG_LAYOUT_STABLE to help your app maintain a stable layout.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * Hide the navigation bar
     * <p>
     * set UI flags in onResume() or onWindowFocusChanged().
     * <p>
     * 会影响悬浮球
     *
     * @param window activity.getWindow()
     * @author https://developer.android.com/training/system-ui/navigation#40
     */
    public static void hideNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = window.getDecorView();
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } else if (Build.VERSION.SDK_INT >= 12) {
            // lower api
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.GONE);
        }
    }

    /**
     * Make Content Appear Behind the Navigation Bar
     *
     * @author https://developer.android.com/training/system-ui/navigation#behind
     */
    public static void behindNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT >= 16) {
            View decorView = window.getDecorView();
            // On Android 4.1 and higher, you can set your application's content to appear behind the navigation bar,
            // so that the content doesn't resize as the navigation bar hides and shows.
            // To do this, use SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.
            // You may also need to use SYSTEM_UI_FLAG_LAYOUT_STABLE to help your app maintain a stable layout.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * Lean back
     * 后倾模式
     * <p>
     * activity.onWindowFocusChanged hasFocus true run
     * <p>
     * 任何地方点击屏幕恢复系统栏
     *
     * @param window activity.getWindow()
     * @author https://developer.android.com/training/system-ui/immersive#leanback
     */
    public static void enableLeanbackMode(Window window) {
        // Enables lean back mode.
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(// Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * Immersive
     * 沉浸模式
     * <p>
     * activity.onWindowFocusChanged hasFocus true run
     * <p>
     * 从隐藏系统栏的任何边缘滑动恢复系统栏,同时系统栏是非透明色
     *
     * @param window activity.getWindow()
     * @author https://developer.android.com/training/system-ui/immersive#immersive
     */
    public static void enableImmersiveMode(Window window) {
        // Enables regular immersive mode.
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * Sticky immersive
     * 粘性沉浸模式
     * <p>
     * activity.onWindowFocusChanged hasFocus true run
     * <p>
     * 从隐藏系统栏的任何边缘滑动恢复系统栏,同时系统栏是半透明的
     *
     * @param window activity.getWindow()
     * @author https://developer.android.com/training/system-ui/immersive#sticky-immersive
     */
    public static void enableStickyImmersiveMode(Window window) {
        // Enables sticky immersive mode.
        View decorView = window.getDecorView();
        if (Build.VERSION.SDK_INT >= 19) {
            // Enables sticky immersive mode.
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            // lower api
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    /**
     * exit fullscreen mode
     * Shows the system bars by removing all the flags
     * except for the ones that make the content appear under the system bars.
     *
     * @param window activity.getWindow()
     */
    public static void showSystemUI(Window window) {
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 状态栏或虚拟导航栏半透明，遮掩APP
     * <p>
     * Make Content Appear Behind the Status Bar
     * <p>
     * 在代码中实现兼容性更好，style 资源中设置的方式在某些国产手机厂商定制的系统中存在一些问题
     * 在基类的setContentView之后执行。
     * http://yifeng.studio/2017/02/19/android-statusbar/
     *
     * @param window activity.getWindow()
     */
    public static void translucentSystemBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
        }
    }

    // =============================================================================================
    // TODO: 悬浮窗口
    // =============================================================================================

}