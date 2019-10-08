package com.amlzq.android.app;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by amlzq on 2017/11/6.
 * 通知栏控件
 * <p>
 * Android 3.0 (API level 11)之前，使用new Notification()方式创建通知
 * Android 3.0开始弃用new Notification()方式，改用Notification.Builder()来创建通知
 * 为了兼容API level 11之前的版本，v4 Support Library中提供了NotificationCompat.Builder()这个替代方法
 * <p>
 * 取消通知有如下4种方式:
 * 点击通知栏的清除按钮，会清除所有可清除的通知
 * 设置了 setAutoCancel() 或 FLAG_AUTO_CANCEL的通知，点击该通知时会清除它
 * 通过 NotificationManager 调用 cancel() 方法清除指定ID的通知
 * 通过 NotificationManager 调用 cancelAll() 方法清除所有该应用之前发送的通知
 * <p>
 * 大视图通知(Big Views)
 * Android 4.1(API level 16)引入，且仅支持Android 4.1及更高版本。
 * <p>
 * 浮动通知(Heads-up Notifications)
 * Android 5.0(API level 21)引入，当屏幕未上锁且亮屏时，通知可以以小窗口形式显示。用户可以在不离开当前应用前提下操作该通知。
 * <p>
 * 锁屏通知
 * Android 5.0(API level 21)引入，通知可以显示在锁屏上。用户可以通过设置选择是否允许敏感的通知内容显示在安全的锁屏上。
 * <p>
 * 自定义通知
 * Android系统允许使用RemoteViews来自定义通知。
 * 自定义普通视图通知高度限制为64dp，大视图通知高度限制为256dp。同时，建议自定义通知尽量简单，以提高兼容性。
 * <p>
 * https://developer.android.com/guide/topics/ui/notifiers/notifications.html?hl=zh-cn
 *
 * @see Notification
 * @see NotificationCompat
 */

public class NotificationWrapper {

    private Context mContext;

    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    private int mNotificationId = -1;

    @SuppressWarnings("deprecation")
    public NotificationWrapper(Context context, int notificationId) {
        this.mContext = context;
        this.mNotificationId = notificationId;

        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        /**
         * NotificationCompat.Builder自动设置的默认值:
         * priority: PRIORITY_DEFAULT
         * when: System.currentTimeMillis()
         * audio stream: STREAM_DEFAULT
         */
    }

    private void send(Notification notification) {
        // Sets an ID for the notification
        mManager.notify(mNotificationId, notification);
    }

    /**
     * 清除所有通知
     */
    private void clearAllNotify() {
        mManager.cancelAll();
    }

    /**
     * 基础设置
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    private void sample(String ticker, String title, String content, int smallIcon, PendingIntent intent,
                        boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        //状态栏文字
        mBuilder.setTicker(ticker);
        //通知栏标题
        mBuilder.setContentTitle(title);
        //通知栏内容
        mBuilder.setContentText(content);
        //触发的intent
        mBuilder.setContentIntent(intent);
        //这边设置颜色，可以给5.0及以上版本smallIcon设置背景色
        mBuilder.setColor(Color.RED);
        //小图标
        mBuilder.setSmallIcon(smallIcon);
        //大图标(这边同时设置了小图标跟大图标，在5.0及以上版本通知栏里面的样式会有所不同)
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), largeIconResid));
        //设置该条通知时间
        mBuilder.setWhen(System.currentTimeMillis());
        //设置为true，点击该条通知会自动删除，false时只能通过滑动来删除
        mBuilder.setAutoCancel(true);
        //设置优先级，级别高的排在前面
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        int defaults = 0;
        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }
        //设置声音、闪光、震动
        mBuilder.setDefaults(defaults);
        //设置是否为一个正在进行中的通知，这一类型的通知将无法删除
        mBuilder.setOngoing(true);
    }

    /**
     * 单行文本使用
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendSingleLineNotification(String ticker, String title, String content, int smallIcon,
                                           PendingIntent intent, boolean sound, boolean vibrate, boolean lights,
                                           int largeIconResid) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        Notification notification = mBuilder.build();
        send(notification);
    }

    /**
     * 多行文本使用
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendMoreLineNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent,
                                         boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(mContext, "您的手机低于Android 4.1.2，不支持！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        Notification notification = new NotificationCompat.BigTextStyle(mBuilder).bigText(content).build();
        send(notification);
    }

    /**
     * 大视图样式
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendBigPicNotification(String ticker, String title, String content, int smallIcon,
                                       PendingIntent intent, boolean sound, boolean vibrate,
                                       boolean lights, int bigPictureResid, int bigLargeIconResid, int largeIconResid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(mContext, "您的手机低于Android 4.1.2，不支持！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        //大图
        Bitmap bigPicture = BitmapFactory.decodeResource(mContext.getResources(), bigPictureResid);
        //图标
        Bitmap bigLargeIcon = BitmapFactory.decodeResource(mContext.getResources(), bigLargeIconResid);
        Notification notification = new NotificationCompat.BigPictureStyle(mBuilder)
                .bigLargeIcon(bigLargeIcon)
                .bigPicture(bigPicture).build();
        send(notification);
    }

    /**
     * 列表型通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param conntents
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendListNotification(String ticker, String title, String content, int smallIcon,
                                     PendingIntent intent, ArrayList<String> conntents,
                                     boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(mContext, "您的手机低于Android 4.1.2，不支持！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(mBuilder);
        for (String conntent : conntents) {
            style.addLine(conntent);
        }
        style.setSummaryText(conntents.size() + "条消息");
        style.setBigContentTitle(title);
        Notification notification = style.build();
        send(notification);
    }

    /**
     * 双折叠双按钮通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param leftIcon
     * @param leftText
     * @param leftPI
     * @param rightIcon
     * @param rightText
     * @param rightPI
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendActionNotification(String ticker, String title, String content, int smallIcon,
                                       PendingIntent intent, int leftIcon, String leftText,
                                       PendingIntent leftPI, int rightIcon, String rightText,
                                       PendingIntent rightPI, boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        mBuilder.addAction(leftIcon, leftText, leftPI);
        mBuilder.addAction(rightIcon, rightText, rightPI);
        Notification notification = mBuilder.build();
        send(notification);
    }

    /**
     * 进度条通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendProgressNotification(String ticker, String title, String content, int smallIcon,
                                         PendingIntent intent, boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        // 提醒(铃声/震动/滚动通知摘要)只执行一次
        mBuilder.setOnlyAlertOnce(true);
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i <= 100; i += 10) {
                    // max: 最大进度值, progress: 当前进度,false: 是否是未知进度的进度条
                    mBuilder.setProgress(100, i, false);
                    send(mBuilder.build());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //下载完成
                mBuilder.setContentText("下载完成").setProgress(0, 0, false);
                send(mBuilder.build());
            }
        }).start();
    }

    /**
     * 浮动通知
     *
     * @param ticker    ticker
     * @param title     title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendHeadsupNotifications(String ticker, String title, String content, int smallIcon,
                                         PendingIntent intent, boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(mContext, "您的手机低于Android 5.0，不支持！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        mBuilder.setFullScreenIntent(intent, false);
        Notification notification = mBuilder.build();
        send(notification);
    }

    /**
     * 自定义通知视图
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param contentView
     * @param bigContentView
     * @param sound
     * @param vibrate
     * @param lights
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void sendCustomerNotification(String ticker, String title, String content, int smallIcon,
                                         PendingIntent intent, RemoteViews contentView, RemoteViews bigContentView,
                                         boolean sound, boolean vibrate, boolean lights, int largeIconResid) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights, largeIconResid);
        Notification notification = mBuilder.build();
        //在api大于等于16的情况下，如果视图超过一定范围，可以转变成bigContentView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = bigContentView;
        }
        notification.contentView = contentView;
        send(notification);
    }

    /**
     * @param context 上下文
     * @return 暗黑主题
     */
    public static boolean isDarkNotificationTheme(Context context) {
        return !isSimilarColor(Color.BLACK, getNotificationColor(context));
    }

    /**
     * @param context 上下文
     * @return 获取通知栏颜色，RemoteViews适配
     */
    @SuppressWarnings("deprecation")
    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Notification notification = mBuilder.build();
        int layoutId = notification.contentView.getLayoutId();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title) != null) {
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static int findColor(ViewGroup viewGroupSource) {
        int color = Color.TRANSPARENT;
        LinkedList<ViewGroup> viewGroups = new LinkedList<ViewGroup>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size() > 0) {
            ViewGroup viewGroup1 = viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                } else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor() != -1) {
                        color = ((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }

    private static boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor = baseColor | 0xff000000;
        int simpleColor = color | 0xff000000;
        int baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor);
        int baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor);
        int baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor);
        double value = Math.sqrt(baseRed * baseRed + baseGreen * baseGreen + baseBlue * baseBlue);
        return value < 180.0;
    }

}