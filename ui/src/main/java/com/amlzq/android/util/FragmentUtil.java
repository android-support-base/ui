package com.amlzq.android.util;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Created by amlzq on 2018/10/3.
 * <p>
 * 片段工具
 * <p>
 * https://medium.com/@bherbst/fragment-transitions-with-shared-elements-7c7d71d31cbb
 */
public class FragmentUtil {

    /**
     * @hide
     */
    FragmentUtil() {
    }

    // =============================================================================================
    // 通用常量
    // 实例化参数的键（instantiation bundle arguments key）
    // 状态保存参数的键（state bundle arguments key）
    // =============================================================================================

    /**
     * 通用参数的键
     * 当只有一个参数时候可用
     */
    public static final String PARAMETER = "KEY_ARG_PARAMETER";
    public static final String PARAMETER_2 = "KEY_ARG_PARAMETER_2";
    public static final String PARAMETER_3 = "KEY_ARG_PARAMETER_3";
    public static final String PARAMETER_4 = "KEY_ARG_PARAMETER_4";
    public static final String PARAMETER_5 = "KEY_ARG_PARAMETER_5";

    /**
     * Fragment标识
     * 可选传值
     * 嵌套子模块的fragment tag
     */
    public static final String CHILD_FRAGMENT_TAG = "KEY_CHILD_FRAGMENT_TAG";

    public static final String STATE_SAVE_IS_HIDDEN = "KEY_STATE_SAVE_IS_HIDDEN";

    // =============================================================================================
    // 初始化
    // =============================================================================================



    // =============================================================================================
    // 片段启动模式的静态常量
    // @reference https://github.com/Sugarya/FragmentCapsulation
    // =============================================================================================

    /**
     * 默认 不记录回退记录
     */
    public static final int DEFAULT = 1;
    /**
     * 标准模式 记录回退记录
     */
    public static final int STANDARD = 2;
    /**
     * 置换当前自维护的栈顶记录
     */
    public static final int EXCHANGE = 3;
    /**
     * 单例模式，旧的目标Fragment对象从自维护的mFragmentBackStack栈里退出
     */
    public static final int SINGLE = 4;
    /**
     * 强化版单例模式，旧的目标Fragment对象从FragmentManager栈和自维护的mFragmentBackStack栈里退出
     */
    public static final int SINGLE_ENHANCEMENT = 5;

    // 用一个@IntDef({})将其全部变量包含，其次需要一个Retention声明其保留级别，最后定义其接口名称
    // 需要support-annotations library
    @IntDef({DEFAULT, STANDARD, EXCHANGE, SINGLE, SINGLE_ENHANCEMENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    /**
     * 自维护 Fragment 回退栈
     * <p>
     * Deque的含义是“double ended queue”，即双端队列，它既可以当作栈使用，也可以当作队列使用。
     * https://www.cnblogs.com/CarpenterLee/p/5468803.html
     * https://blog.csdn.net/chengqiuming/article/details/70139127
     */
    private static Deque<String> mFragmentBackStack = new ArrayDeque<String>();

    /**
     * 单例模式下，管理自维护的Fragment后退栈
     *
     * @param tag fragment Tag
     */
    private static void synchronizeFragmentBackDequeWhenSingleLaunchMode(String tag) {
        if (mFragmentBackStack.contains(tag)) {
            // 获取但不删除栈顶元素
            String peekElement = mFragmentBackStack.peek();
            while (!tag.equals(peekElement)) {
                if (mFragmentBackStack.isEmpty()) {
                    break;
                }
                mFragmentBackStack.pop();
                peekElement = mFragmentBackStack.peek();
            }
        }
    }

    /**
     * 一次弹出多个Fragment
     */
    private static void popMultipleBackStack(FragmentManager manager,
                                             String tag) {

        synchronizeFragmentBackDequeWhenSingleLaunchMode(tag);

        // 异步的方法
        // manager.popBackStack(tag, 0);

        // 同步的方法
        manager.popBackStackImmediate(tag, 0);

        // reorderAvailIndicesToFixBug();
    }

    /**
     * 返回键显示特定Tag的 Fragment
     */
    private static void showFragmentOnBackPressed(@NonNull FragmentManager manager,
                                                  @AnimatorRes @AnimRes int enter,
                                                  @AnimatorRes @AnimRes int exit,
                                                  @AnimatorRes @AnimRes int popEnter,
                                                  @AnimatorRes @AnimRes int popExit) {

        mFragmentBackStack.pop();
        String fragmentTag = mFragmentBackStack.peek();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(enter, exit, popEnter, popExit);

        List<Fragment> fragments = manager.getFragments();
        for (Fragment f : fragments) {
            if (f != null) {
                transaction.hide(f);
            }
        }

        Fragment fragmentByTag = manager.findFragmentByTag(fragmentTag);
        if (fragmentByTag != null) {
            transaction.show(fragmentByTag);
        }
        commitCompat(manager, transaction, false, false, true);
    }

    public static boolean onBackPressed(@NonNull FragmentManager manager) {
        //检查自维护的Fragment回退栈是否需要回退
        if (mFragmentBackStack.size() >= 2) {
            showFragmentOnBackPressed(manager, 0, 0, 0, 0);
            return true;
        }
        return false;
    }

    // =============================================================================================
    // 片段事务的系列方法
    // 片段事务方法有: add/remove, hide/show, replace, attach/,detach
    // 片段事务方法对应的生命周期方法
    // add: onAttach-onCreate-onCreateView-onActivityCreated-onStart-onResume
    // remove:
    // isAddToBackStack: false[onPause-onStop-onDestroyView-onDestroy-onDetach]
    // isAddToBackStack: true[onPause-onStop-onDestroyView]
    // hide: onHiddenChanged,hidden:true
    // show: onHiddenChanged,hidden:false
    // replace:
    // new fragment[onAttach-onCreate-onCreateView-onActivityCreated-onStart-onResume]
    // old fragment[onPause-onStop-onDestroyView-onDestroy-onDetach]
    // attach: onCreateView-onActivityCreated-onStart-onResume
    // detach: onPause-onStop-onDestroyView
    //
    // @reference https://segmentfault.com/a/1190000000650573, https://blog.csdn.net/goodlixueyong/article/details/50257079
    // =============================================================================================

    /**
     * <pre>
     * show/hide方法操作片段
     * </pre>
     *
     * @param manager
     * @param containerResId
     * @param fragment
     * @param fragmentTag
     * @param enter
     * @param exit
     * @param popEnter
     * @param popExit
     * @param shareElement
     * @param transitionName
     * @param isAddToBackStack
     * @param launchMode
     */
    public static void showFragment(@NonNull FragmentManager manager,
                                    @IdRes int containerResId,
                                    @NonNull Fragment fragment,
                                    @NonNull String fragmentTag,
                                    @AnimatorRes @AnimRes int enter,
                                    @AnimatorRes @AnimRes int exit,
                                    @AnimatorRes @AnimRes int popEnter,
                                    @AnimatorRes @AnimRes int popExit,
                                    View shareElement,
                                    String transitionName,
                                    boolean isAddToBackStack,
                                    @LaunchMode int launchMode) {

        Fragment fragmentByTag = manager.findFragmentByTag(fragmentTag);

        if (launchMode == SINGLE_ENHANCEMENT && fragmentByTag != null) {
            popMultipleBackStack(manager, fragmentTag);
            // 情况1:
            return;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        //设置过渡动画
        transaction.setCustomAnimations(enter, exit, popEnter, popExit);

        //隐藏当前所有fragment
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment f : fragments) {
                if (f != null) {
                    transaction.hide(f);
                }
            }
        }

        // 第一次添加该Fragment
        if (fragmentByTag == null) {
//            if (launchMode != DEFAULT) {
//                mFragmentBackStack.push(fragmentTag);
//            }

            switch (launchMode) {
                case DEFAULT:
                    break;
                case EXCHANGE:
                    if (mFragmentBackStack.size() >= 1) {
                        // 移除栈顶元素
                        mFragmentBackStack.pop();
                        // 栈顶插入元素
                        mFragmentBackStack.push(fragmentTag);
                    }
                    break;
                default:
                    mFragmentBackStack.push(fragmentTag);
            }
            transaction.add(containerResId, fragment, fragmentTag);
            if (shareElement != null && !TextUtils.isEmpty(transitionName)) {
                transaction.addSharedElement(shareElement, transitionName);
            }
            if (isAddToBackStack) {
                transaction.addToBackStack(fragmentTag);
            }
            commitCompat(manager, transaction, false, isAddToBackStack, true);
            // 情况2: 管理器第一次操作此片段，调用add
            return;
        }

        //根据启动模式类型，采取不同的方式维护后退栈
        switch (launchMode) {
            case EXCHANGE:
                if (mFragmentBackStack.size() >= 1) {
                    mFragmentBackStack.pop();
                    mFragmentBackStack.push(fragmentTag);
                }
                break;
            case STANDARD:
                mFragmentBackStack.push(fragmentTag);
                break;
            case SINGLE:
                synchronizeFragmentBackDequeWhenSingleLaunchMode(fragmentTag);
                break;
        }
        if (shareElement != null && !TextUtils.isEmpty(transitionName)) {
            transaction.addSharedElement(shareElement, transitionName);
        }
        transaction.show(fragmentByTag);
        commitCompat(manager, transaction, false, isAddToBackStack, true);
        // 情况3: 管理器第二或N次操作此片段，调用show
    }

    /**
     * <pre>
     * replace 方法操作片段
     * 新片段生命周期方法: 创建并可见
     * 旧片段生命周期方法: 等于销毁
     * </pre>
     *
     * @param manager
     * @param containerResId
     * @param fragment
     * @param fragmentTag
     * @param enter
     * @param exit
     * @param popEnter
     * @param popExit
     * @param isAddToBackStack
     */
    public static void replaceFragment(@NonNull FragmentManager manager,
                                       @IdRes int containerResId,
                                       @NonNull Fragment fragment,
                                       @NonNull String fragmentTag,
                                       @AnimatorRes @AnimRes int enter,
                                       @AnimatorRes @AnimRes int exit,
                                       @AnimatorRes @AnimRes int popEnter,
                                       @AnimatorRes @AnimRes int popExit,
                                       boolean isAddToBackStack) {

        FragmentTransaction transaction = manager.beginTransaction();

        // 过渡动画-渐变，使用动画资源实现
        // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.setCustomAnimations(enter, exit, popEnter, popExit);

        /*
         * replace分解为如下
         * transaction.add(layoutId, fragment);
         * transaction.show(fragment);
         * transaction.hide(preFragment);
         * transaction.remove(preFragment);
         */
        transaction.replace(containerResId, fragment, fragmentTag);

        if (isAddToBackStack) {
            transaction.addToBackStack(fragmentTag);
        }

        commitCompat(manager, transaction, false, isAddToBackStack, true);
    }

    /**
     * 提交事务的兼容方法
     *
     * @param manager          片段管理器
     * @param transaction      片段事务
     * @param allowStateLoss   允许状态丢失
     * @param isAddToBackStack 添加到回退栈
     * @param executeNow       立即执行
     * @reference https://juejin.im/entry/57c8f430128fe10069616aa8, https://www.cnblogs.com/mengdd/p/5827045.html
     */
    public static void commitCompat(FragmentManager manager,
                                    FragmentTransaction transaction,
                                    boolean allowStateLoss,
                                    boolean isAddToBackStack,
                                    boolean executeNow) {

        // 需要保存 Fragment 的状态使用 commit 方法提交
        // Fragment 的状态是否保存不重要则使用 commitAllowingStateLoss 提交
        // 如果 Activity 的 onSaveInstanceState 方法调用（mStateSaved已经保存）之后调用 commit 提交片段，
        // 将抛出"Can not perform this action after onSaveInstanceState"错误。
        // 如果 mNoTransactionsBecause 已经存在，
        // 将抛出"Can not perform this action inside of " + mNoTransactionsBecause错误。

        if (allowStateLoss) {
            if (executeNow) {
                // Support v4 v24.0.0才有。
                transaction.commitNowAllowingStateLoss();
            } else {
                transaction.commitAllowingStateLoss();
            }
        } else if (isAddToBackStack) {
            // 事务提交并没有立刻发生，会安排到在主线程下次准备好的时候来执行（所以是异步方法）。
            transaction.commit();
            if (executeNow) {
                manager.executePendingTransactions(); // 立即执行
            }
        } else {
            if (executeNow) {
                // 同步提交事务并且无需添加到 BackStack 中，即addToBackStack()和commitNow()不能同时使用。
                // Support v4 v24.0.0才有。
                transaction.commitNow();
            } else {
                transaction.commit();
            }
        }
    }

    // Example code
//    /**
//     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
//     * performed by the {@code fragmentManager}.
//     *
//     * @param manager            FragmentManager
//     * @param containerResId Optional identifier of the container this fragment is
//     *                           to be placed in.  If 0, it will not be placed in a container.
//     * @param fragment           The fragment to be added.  This fragment must not already
//     *                           be added to the activity.
//     */
//    public static void addFragment(@NonNull FragmentManager manager,
//                                   int containerResId, @NonNull Fragment fragment) {
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(containerResId, fragment);
//        transaction.commit();
//    }

    // =============================================================================================
    // ViewPager懒加载片段
    //
    // FragmentPagerAdapter:
    // 采用 attach/detach 控制显示和隐藏，用于相对静态的、少量界面的ViewPager，划过的fragment会保存在内存中。
    // FragmentStatePagerAdapter:
    // 采用 add/remove 控制显示和隐藏，适用于数据动态性较大、页面比较多的情况，它并不会保存所有的fragment。
    //
    // @reference https://blog.csdn.net/goodlixueyong/article/details/50257079,
    // https://www.jianshu.com/p/9eb1c379f3e7,
    // https://juejin.im/entry/582180cd570c350060bc7617
    // =============================================================================================

    /**
     * 获取片段的标签
     * 注意混淆后类名问题
     */
    public static String getTag(android.support.v4.app.Fragment fragment) {
        try {
            return fragment.getClass().getSimpleName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressWarnings("deprecation")
    public static String getTag(android.app.Fragment fragment) {
        try {
            return fragment.getClass().getSimpleName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}