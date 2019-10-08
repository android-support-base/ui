# android-support-ui
- 基于原生API，提供可复用的资源（string style color dimen anim layout drawable ），可复用代码（封装基类，工具类）
- 展示Android UI的多样性

## 发布
* 最新版本

| groupId | artifactId | version |
| -------- | -------- | -------- |
| com.amlzq.android | ui | [ ![Download](https://api.bintray.com/packages/amlzq/android-support-base/ui/images/download.svg) ](https://bintray.com/amlzq/android-support-base/ui/_latestVersion) |

* 使用
```
dependencies{
    ...
    implementation 'com.amlzq.android:ui:latest.integration'
}
```

## 工程结构
* ./ui
> ui library
```
package:
com.amlzq.android.ui
```

* ./sample
> sample application
```
package: com.amlzq.asb
appName: UI支持库
applicationId: com.amlzq.asb.ui
```
- [Install APK](https://www.pgyer.com/eYXy)

## 规范
- 布局控件id的命名
    ```
    以控件缩写为前缀
    ```
- 举例:
    ```
    iv_
    tv_
    btn_
    ll_
    rl_
    ```
- 布局文件的命名
    ```
    activity_
    fragment_
    dialog_
    popwin_
    toast_
    snackbar_
    content_
    ```
- 资源文件的命名
    ```
    activity_
    ```

#### Fragment相关
- [Fragment中onHiddenChanged、setUserVisibleHint触发条件](https://github.com/LoganZy/AndroidTotal/blob/master/Fragment%E4%B8%ADonHiddenChanged%E3%80%81setUserVisibleHint%E8%A7%A6%E5%8F%91%E6%9D%A1%E4%BB%B6.md)
* [fragment启动方式](https://github.com/Sugarya/FragmentCapsulation)
* [fragment切换动画](https://github.com/SpikeKing/TestFragmetnSharedElement)

#### Android的设计风格变迁
```
Android的设计风格变迁可以划分到三个时代：
1、无序时代
2、Holo Theme
3、Material Design
```

- Holo Theme
```
Android4.X时代(API 14)
简单，质朴
```

- Material Design
```
Google在I/O 2014上推出了新的设计语言Material Design。
Material Design以现实世界的纸墨为隐喻，强调了阴影和层次，用动画效果代表现实的力反馈，试图把物理世界的规则带回电子界面。
而就Android 平台而言，Material Design 不像此前的Holo 风格那样深沉，它更加跳动和富有活力。

Material design有三个设计原则，也是其核心部分：
1、隐喻
2、视觉设计
3、动画
```
- [来源](http://www.voidcn.com/article/p-vvtgnziw-bqr.html)
- 实践参考[Mike-bel/MDStudySamples，以及它推荐的博客](https://github.com/Mike-bel/MDStudySamples)

#### 联动系统栏
```
1.Android5.0以上：material design风格，半透明(APP 的内容不被上拉到状态)
2.Android4.4(kitkat)以上至5.0：全透明(APP 的内容不被上拉到状态)
3.Android4.4(kitkat)以下:不占据status bar
```

- [Android使用fitsSystemWindows属性实现–状态栏【status_bar】各版本适配方案](https://blog.csdn.net/ys408973279/article/details/49994407)
- [Android 沉浸式状态栏的实现](https://www.jianshu.com/p/dc20e98b9a90)
- [Android 关于状态栏开发的几件事](http://www.10tiao.com/html/169/201803/2650825153/1.html)
- [Android 沉浸式状态栏 渐变颜色的实现](https://juejin.im/post/5c468686e51d452c8e6d52ba#heading-0)

##### 样式
- [Android Translucent System Bar 使用指南](http://blog.coderclock.com/2016/02/04/android/Android%E5%BC%80%E5%8F%91%EF%BC%9ATranslucent%20System%20Bar%20%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5/)
- [Android 沉浸式状态栏的实现](https://www.jianshu.com/p/dc20e98b9a90)
- [Android 状态栏操作，你想知道的都在这里了](http://yifeng.studio/2017/02/19/android-statusbar/)

##### 代码
- 通过代码setSystemUiVisibility实现
- [Android状态栏和虚拟导航栏的适配总结](https://blog.csdn.net/leogentleman/article/details/54566319)
- [Android状态栏微技巧，带你真正理解沉浸式模式](https://blog.csdn.net/guolin_blog/article/details/51763825)

##### 实践
- 样式实现
```
最终叫重叠状态栏
Activity.FitsStatusBar
```

#### ActionBar&Toolbar
- 操作栏&工具栏
- ActionBar 是android 3.0(API11)的推出的
- [Android ActionBar完全解析](https://blog.csdn.net/guolin_blog/article/details/18234477)
- Toolbar 是android 5.0(API21)的推出的，在v7库中

- [android夜间模式浅析](http://flyou.ren/2016/08/18/android%E5%A4%9C%E9%97%B4%E6%A8%A1%E5%BC%8F%E6%B5%85%E6%9E%90/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io)

#### sample增加材料设计示例
- [Android 详细分析AppBarLayout的五种ScrollFlags](https://www.jianshu.com/p/7caa5f4f49bd)
- [MDStudySamples](https://github.com/Mike-bel/MDStudySamples)

#### UI分析
- [看看淘宝是怎么做的哔哩哔哩是怎么做的](https://www.jianshu.com/p/49d437f0074f)
- [工具 hierarchyviewer](https://developer.android.com/studio/profile/hierarchy-viewer)

- android 点击抖动问题
- [Android 这才是实现防抖动(防快速点击)的最优雅写法，重点是评论区](https://www.jianshu.com/p/06c5b35b4e51)

### 关于闪屏
- [用“视频”来打造你的Splash闪屏页](https://www.jianshu.com/p/2b489bb119cb)
- [Android Splash 闪屏页流程与功能分析](https://juejin.im/entry/59900f6551882548630c0ffe)
- [启动体验设计-闪屏，启动页，引导页](http://reezy.me/p/20160911/make-launch-page/)
- [Android Splash 动态闪屏页面及三秒跳转实现](https://importeffort.github.io/2017/08/22/Android-Splash-%E5%8A%A8%E6%80%81%E9%97%AA%E5%B1%8F%E9%A1%B5%E9%9D%A2%E5%8F%8A%E4%B8%89%E7%A7%92%E8%B7%B3%E8%BD%AC%E5%AE%9E%E7%8E%B0/)

### 增加behavior
- [FloatingActionButton滚动时的显示与隐藏小结](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0407/4126.html)
- [FloatingActionButton的滚动隐藏和显示](https://www.jianshu.com/p/04bd1d2223a7)
- [Floating Action Buttons](https://guides.codepath.com/android/floating-action-buttons)
- [FloatingActionButton在RecycleView中滑动隐藏显示](https://juejin.im/post/5bc4349a6fb9a05cec4dd661)

### iconFont、SVG、.9、PNG图的比较
- iconFont 方便换肤
- vectorDrawable API版本限制，android5.0才推出
- [安卓中如何使用iconfont字体图标](https://blog.csdn.net/juladoe/article/details/59110864?tdsourcetag=s_pcqq_aiomsg)
- [https://blog.csdn.net/yanbober/article/details/50276769](Android应用开发之PNG、IconFont、SVG图标资源优化详解)

### 学习约束布局
-

### ViewPager Item 单击事件
- [单击事件](https://stackoverflow.com/questions/16350987/viewpager-onitemclicklistener)
- [子视图单击事件](https://stackoverflow.com/questions/16350987/viewpager-onitemclicklistener)
- [Android用ViewPager实现一个中间大两边小并且带指示器的轮播图，也称画廊效果](https://blog.csdn.net/zhouzhangfu/article/details/82222637)

### 底部导航栏
- [bottom navigation view with fragments]()
- [bottom navigation view with activitys](https://stackoverflow.com/questions/41744219/how-to-highlight-the-item-when-pressed-using-bottomnavigationview-between-activi)
- [Using Activities with Bottom navigation view in Phimpme Android](https://blog.fossasia.org/using-activities-with-bottom-navigation-view-in-phimpme-android/)
- [BottomNavigationViewBetweenActivities](https://github.com/ddekanski/BottomNavigationViewBetweenActivities)
- [通过BottomNavigationViewEx实现](https://github.com/ittianyu/BottomNavigationViewEx)
- 通过TabLayout实现
- [chaychan/BottomBarLayout](https://github.com/chaychan/BottomBarLayout)

### TabLayout相关
- [TabLayout动态调整大小](https://stackoverflow.com/questions/45877475/android-tablayout-dynamically-resize)
- [Android TabLayout定制CustomView与ViewPager交互双向联动](https://blog.csdn.net/zhangphil/article/details/48934039)
- [TabLayout实现Tab选中图片&文字放大效果](https://www.jianshu.com/p/c8ea7d2025be)

### ReclycleView
- 增加baseQuick基础上增加吸顶功能

### 屏幕适配
- autoLayout
- 美团方案
- 系统方案
- [字节跳动方案](https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA)

### 展开/折叠 TextView
- [Manabu-GT/ExpandableTextView](https://github.com/Manabu-GT/ExpandableTextView)

### 转场动画
- [最炫Material Design风过渡动画](https://www.jianshu.com/p/cdf36a191677)
- [ActivityOptionsCompat](https://developer.android.com/reference/android/support/v4/app/ActivityOptionsCompat)
- [你所不知道的Activity转场动画——ActivityOptions](https://blog.csdn.net/qibin0506/article/details/48129139)


### Service
- [Context.startForegroundService() did not then call Service.startForeground？](https://blog.csdn.net/sinat_20059415/article/details/80584487#)

### BroadcastReceiver
- [如何在Android Oreo上注册ACTION_PACKAGE_ADDED和ACTION_PACKAGE_REMOVED？](https://stackoverflow.com/questions/45996338/how-to-register-for-action-package-added-and-action-package-removed-on-android-o/45996525#45996525)
- [Android8.0以上广播限制](https://www.imooc.com/article/22923)

### 圆形ImageView
- [hdodenhof/CircleImageView](https://github.com/hdodenhof/CircleImageView)
- 环信的圆角头像可以拿来

### Dialog
- [对话框](https://developer.android.com/guide/topics/ui/dialogs?hl=zh-CN)
- [Material Dialogs（对话框组件）](https://material.io/develop/android/components/dialog/)
- [Android——最全的系统对话框（AlertDialog）详解](https://juejin.im/post/5a32a1bc5188257dcc2fa71d)
- [ProgressDialog](https://www.cnblogs.com/guop/p/5139937.html)

### Widget
- [Snackbar]()
- [粘连效果的ViewPager指示器](http://www.see-source.com/androidwidget/detail.html?wid=97)
- [简单好用的ViewPagerIndicator](https://juejin.im/entry/59b6aef3f265da0646187206)

- [自定义折叠效果](https://github.com/xmuSistone/stickyViewpager)

- [进度条按钮]()

### Material Design
- [Material Design Android](https://material.io/develop/android/)
-

### iOS风格UI
- 对话框，底部
- 通知栏
- Toast，头部

### 动画实现方案
```
SVGA
Lottie
```

### 阴影
```

```
- [写了那么多Android布局，你知道elevation属性吗](https://www.jianshu.com/p/c1d17a39bc09)
- [Android 视图高度和阴影](https://yifeng.studio/2017/02/26/android-elevation-and-shadow/)

### 点击涟漪效果
- [为Android RecyclerView项添加涟漪效果](https://medium.com/@studymongolian/adding-a-ripple-effect-to-an-android-recyclerview-item-61249eb382b0)


```
了解材料设计，并做到锦上添花
对于Android UI设计的要求
```

### 下拉刷新
- [SwipeRefreshLayout](https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout)
- [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
- [Android几种强大的下拉刷新库](http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2018/0502/9660.html)

### 第三方UI库
- [QMUI Android](https://qmuiteam.com/android/)

### 实践自定义视图
- [Android自定义控件三部曲文章索引](https://blog.csdn.net/harvic880925/article/list/1?t=1&)

### 换肤
-

### 管理Fragment
- [YoKeyword / Fragmentation](https://github.com/YoKeyword/Fragmentation)
    ```
    为"单Activity ＋ 多Fragment","多模块Activity + 多Fragment"架构而生，简化开发，轻松解决动画、嵌套、事务相关等问题。
    ```
- []()
