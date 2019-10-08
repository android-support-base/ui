package com.amlzq.android.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.log.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by amlzq on 2018/5/29.
 * <p>
 * This provides methods to help Activities load their UI.
 * <p>
 * 管理活动栈
 * 管理碎片
 */
public class ActivityUtil {

    /**
     * @hide
     */
    ActivityUtil() {
    }

    // =============================================================================================
    // 通用常量
    // =============================================================================================

    /**
     * 通用额外参数 <br/>
     * Activity common extra params
     */
    public static final String PARAMS = "KEY_PARAMS";
    public static final String PARAMS_2 = "KEY_PARAMS_2";
    public static final String PARAMS_3 = "KEY_PARAMS_3";

    /**
     * 活动样式 <br/>
     * Activity style
     */
    public static final String THEME = "KEY_THEME";
    /**
     * 活动方向 <br/>
     * Activity Orientation
     */
    public static final String ORIENTATION = "KEY_ORIENTATION";
    /**
     * 点击窗口空白处Activity是否finish <br/>
     * 针对的是非全屏活动
     */
    public static final String CANCELABLE = "KEY_CANCELABLE";
    /**
     * Activity Title <br/>
     * 活动标题
     */
    public static final String TITLE = "KEY_TITLE";
    /**
     * Activity Bundle
     * 活动携带捆绑数据
     */
    @Deprecated
    public static final String EXTRAS_BUNDLE = "KEY_EXTRAS_BUNDLE";
    /**
     * Fragment标识键 <br/>
     * 在容器中的目标片段的标签的值
     */
    public static final String FRAGMENT_TAG = "KEY_FRAGMENT_TAG";

    // =============================================================================================
    // 管理活动栈
    // ActivityManager
    // =============================================================================================

    /**
     * 存放activity的列表
     */
    private static HashMap<Class<?>, Activity> activities = new LinkedHashMap<Class<?>, Activity>();

    /**
     * 添加Activity
     *
     * @param activity 活动
     * @param clazz    泛型类型信息
     */
    public static void add(Activity activity, Class<?> clazz) {
        activities.put(clazz, activity);
    }

    /**
     * @param clazz 泛型类型信息
     * @param <T>   返回值类型
     * @return 判断一个Activity 是否在活动栈中
     */
    public static <T extends Activity> boolean isExisting(Class<T> clazz) {
        Activity activity = get(clazz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity != null && !activity.isFinishing() && !activity.isDestroyed();
        } else {
            return activity != null && !activity.isFinishing();
        }
    }

    /**
     * @param clazz Activity类对象
     * @param <T>   返回值类型
     * @return 获得指定activity实例
     */
    @SuppressWarnings("unchecked")
    public static <T extends Activity> T get(Class<T> clazz) {
        return (T) activities.get(clazz);
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity 活动
     */
    public static void remove(Activity activity) {
        if (activities.containsValue(activity)) {
            activity.finish();
            activities.remove(activity.getClass());
        }
    }

    /**
     * 移除activity,代替finish
     *
     * @param clazz 泛型类型信息
     */
    public static void remove(Class clazz) {
        if (activities.containsKey(clazz)) {
            Activity activity = activities.get(clazz);
            remove(activity);
        }
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAll() {
        if (activities != null && activities.size() > 0) {
            Set<Map.Entry<Class<?>, Activity>> sets = activities.entrySet();
            for (Map.Entry<Class<?>, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
            activities.clear();
        }
    }

    /**
     * 获取栈顶活动类名
     *
     * @return
     * @permission GET_TASKS
     * @author com.hyphenate.util.EasyUtils
     */
    public static String getTopActivityName() {
        final Context applicationContext = ContextHolder.getContext();
        ActivityManager manager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            List tasks = manager.getRunningTasks(1);
            return tasks != null && tasks.size() >= 1 ? ((ActivityManager.RunningTaskInfo) tasks.get(0)).topActivity.getClassName() : "";
        } catch (SecurityException e) {
            Log.w("doesn't hold GET_TASKS permission", e);
            return "";
        }
    }

    // =============================================================================================
    // 操作活动的生命
    // =============================================================================================

    /**
     * 加载活动
     *
     * @param activity  activity
     * @param intent    intent
     * @param enterAnim enterAnim
     * @param exitAnim  exitAnim
     */
    public static void startActivity(Activity activity, Intent intent, int enterAnim, int exitAnim) {
        activity.startActivity(intent);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 加载活动并有结果
     *
     * @param activity    activity
     * @param intent      intent
     * @param requestCode requestCode
     * @param enterAnim   enterAnim
     * @param exitAnim    exitAnim
     */
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, int enterAnim, int exitAnim) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 获取活动的标签
     */
    public static String getTag(Context context) {
        try {
            return context.getClass().getSimpleName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 全屏活动
     * 在setContentView之前执行
     * 不是彻底的全屏，还有虚拟导航栏，可使用这个{@link ViewUtil}
     */
    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
    }

}