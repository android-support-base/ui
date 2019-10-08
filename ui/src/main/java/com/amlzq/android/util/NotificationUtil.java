package com.amlzq.android.util;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by amlzq on 2018/6/8.
 * <p>
 * 通知工具
 * https://www.jianshu.com/p/5318dc8db4f4
 */

@SuppressWarnings(value = {"unused", "unchecked", "deprecation"})
public class NotificationUtil {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 原生android
     * API 19以前是没有通知管理的，默认开启
     * API 19-24虽加入了通知管理功能，但没有开放检测是否开启了通知的接口，开发者只能用反射来获取权限值
     * Api 24以上，NotificationManager提供了areNotificationsEnabled方法检测通知权限
     * <p>
     * 其他ROM
     * <p>
     * 权限
     * permission.ACCESS_NOTIFICATION_POLICY
     * <p>
     * 此方法弃用，替代方法
     * support包的NotificationManagerCompat.from(context).areNotificationsEnabled()
     *
     * @param context 上下文
     * @return 是否在系统应用管理中开启通知权限
     */
    @Deprecated
    public static boolean areNotificationsEnabled(Context context) {
        if (SystemUtil.has24()) {
//            NotificationManager.areNotificationsEnabled();
            return true;
        } else if (SystemUtil.has19()) {
            AppOpsManager mAppOps = (AppOpsManager)
                    context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
            try {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (int) opPostNotificationValue.get(Integer.class);
                return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打开应用通知管理
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void openManagement(Activity activity, int requestCode) {
        switch (Build.MANUFACTURER) {
            case Manufacturer.OPPO:
                SystemUtil.openApplicationInfo(activity, requestCode);
                // requires oppo.permission.OPPO_COMPONENT_SAFE
//                OPPO(activity, requestCode);
                break;
            default:
                SystemUtil.openApplicationInfo(activity, requestCode);
                break;
        }
    }

    /**
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void OPPO(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.coloros.notificationmanager", "com.coloros.notificationmanager.AppDetailPreferenceActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, requestCode);
//        activity.startActivity(intent);
    }

}
