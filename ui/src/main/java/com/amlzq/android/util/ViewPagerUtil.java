package com.amlzq.android.util;

/**
 * Created by amlzq on 2018/10/23.
 * <p>
 * android.support.v4.view.ViewPager 工具
 */

public class ViewPagerUtil {

    // setOffscreenPageLimit方法的limit参数表示保持当前view前面的limit个view和后面limit个view。
    // 默认值为1，第一次显示ViewPager有两个view（当前，后面）在内存中，
    // 当划到第二个view时候，有三个view（前面，当前，后面）在内存中。

    // ===================================
    // TODO: 懒加载
    // 当每组view中有网络请求情况，为了避免缓存中的所有view都发送网络请求去获取数据，所以需要懒加载。
    //
    // 参考: https://juejin.im/entry/582180cd570c350060bc7617
    // ===================================

}