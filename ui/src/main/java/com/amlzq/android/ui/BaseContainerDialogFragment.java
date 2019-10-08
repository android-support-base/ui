package com.amlzq.android.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.amlzq.android.app.BaseFragment;
import com.amlzq.android.util.ActivityUtil;
import com.amlzq.android.util.FragmentUtil;
import com.amlzq.android.util.KeyboardUtil;
import com.amlzq.android.util.StringUtil;
import com.amlzq.android.util.ToastUtil;

/**
 * Created by amlzq on 2018/10/24.
 * <p>
 * 片段容器对话框片段
 * 与 {@link BaseContainerFragmentActivity} 功能相同
 */

public abstract class BaseContainerDialogFragment extends DialogFragment {

    /**
     * 标题
     */
    protected String mTitle;

    protected android.content.Context mContext;
    protected android.support.v4.app.FragmentActivity mActivity;

    protected View mContentView;
    protected Bundle mArguments;

    /**
     * 在片段已与Activity关联时被调用（Activity传递到此方法内）。
     *
     * @param context 上下文
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context; // 在此处保存全局的Context

        // 如需 Fragment 内的某个 Context 对象，可以调用 getActivity()。
        // 但要注意，请仅在片段附加到 Activity 时调用 getActivity()。
        // 如果片段尚未附加，或在其生命周期结束期间分离，则 getActivity() 将返回 null。
        mActivity = (FragmentActivity) context; // 不建议调用getActivity，而是在onAttach()中将Context对象强转为Activity对象。
    }

    /**
     * 在创建片段实例时被调用
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // fragment愿意添加item到选项菜单，OptionsMenu生效
        setRetainInstance(true); // Fragment恢复时会跳过onCreate()和onDestroy()方法
    }

    /**
     * 创建与片段关联的视图层次结构。
     * <br></>
     * 存在被调用情况，所以需要判断 mContentView 是否为空
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(getLayoutResId(), container, false);
        }
        //缓存的View需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个View已经有parent的错误。
//        ViewGroup parent = (ViewGroup) mContentView.getParent();
//        if (parent != null) {
//            parent.removeView(mContentView);
//        }
        mArguments = getArguments();
        if (mArguments != null) {
            mTargetFragmentTag = mArguments.getString(ActivityUtil.FRAGMENT_TAG);
        }

        return mContentView;
    }

    /**
     * 在onCreateView之后触发
     *
     * @param view               view
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 在Activity的onCreate()方法已返回时调用。
     * <br></>
     * 恢复状态。
     * 不要在Fragment里面保存ViewState
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        afterCreate(savedInstanceState);
        // Example code
//        if (savedInstanceState != null) {
//            // Restore last state for checked position.
//            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 当使用show/hide方法操作片段时候，onResume调用一次，onPause不调用，但会回调此方法
     * <p>
     * https://blog.csdn.net/cml_blog/article/details/41411451
     *
     * @param hidden 隐
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        // Example code
//        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
//            return;
//        } else {  // 在最前端显示 相当于调用了onResume();
//            //网络数据刷新
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 在移除与片段关联的视图层次结构时调用。
     * <p>
     * 只是销毁视图，片段实例和成员变量还在。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // fix The specified child already has a parent. You must call removeView() on the child's parent first.
        // onCreateView中复用了rootview，对父容器调用removeView()移除
        ((ViewGroup) mContentView.getParent()).removeView(mContentView);
    }

    /**
     * 在销毁片段的实例时被调用。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 在取消片段与Activity的关联时调用。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    // ==============================================================================
    // TODO: 实例的状态保存与恢复
    // Activity 的进程被终止，需要在重建 Activity 时恢复片段状态。
    // 可以使用 Bundle 保留片段的状态，在片段的 onSaveInstanceState() 回调期间保存状态，
    // 并可在 onCreate()、onCreateView() 或 onActivityCreated() 期间恢复状态。
    // <br>
    // 参考: https://www.jianshu.com/p/5c2ec2420a0e
    // ==============================================================================

    /**
     * 保存状态
     *
     * @param outState outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Example code
        // outState.putInt("curChoice", mCurCheckPosition);
        // outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    // ================================
    // TODO: 封装基类
    // ================================

    /**
     * @return 返回该Fragment的布局资源
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 创建之后的操作均写在该方法
     */
    protected abstract void afterCreate(Bundle savedInstanceState);

    /**
     * 所有的子类都将在这个方法中实现物理键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     *
     * @param keyCode keyCode
     * @param event   event
     * @return 是否消费该事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void onTitleChanged(CharSequence title, @ColorRes int resId) {

    }

    protected String getActivityTitle() {
        if (TextUtils.isEmpty(mActivity.getTitle())) return "";
        return mActivity.getTitle().toString();
    }

    protected void setActivityTitle(@StringRes int resId) {
        mActivity.setTitle(resId);
    }

    protected void setActivityTitle(String title) {
        mActivity.setTitle(title);
    }

    @ColorInt
    protected int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    /**
     * @param resId View res id
     * @param <T>   T
     * @return 视图对象
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public final <T extends View> T findViewById(@IdRes int resId) {
        return (T) mContentView.findViewById(resId);
    }

    /**
     * 获取要替换的布局ID
     *
     * @return 替换的布局ID
     */
    @IdRes
    protected int getChildrenFragmentContainerResId() {
        return R.id.fragment_container;
    }

    /**
     * Return the application that owns this activity.
     */
    public final Application getApplication() {
        return mActivity.getApplication();
    }

    // =========================
    // TODO: 管理片段
    // =========================

    /**
     * 目标片段（当前显示的片段）
     */
    protected BaseFragment mTargetFragment;
    /**
     * 携带参数
     * 目标片段标签
     */
    protected String mTargetFragmentTag;

    /**
     * 加载片段
     * <p>
     * 显示指定Tag的Fragment, 如果是第一次显示则新建并添加该Fragment
     */
    protected void startFragment(@NonNull String fragmentTag, Object... args) {

        mTargetFragment = fragmentProvider(fragmentTag, args);
        mTargetFragmentTag = fragmentTag;

        FragmentUtil.showFragment(getChildFragmentManager(),
                getFragmentContainerResId(),
                mTargetFragment,
                mTargetFragmentTag,
                R.anim.fade_in, R.anim.fade_out,
                0, 0,
                null, null,
                false, FragmentUtil.DEFAULT);
    }

    /**
     * 获取要替换的布局ID
     *
     * @return 加载fragment的布局ID
     */
    protected int getFragmentContainerResId() {
        return R.id.fragment_container;
    }

    /**
     * @param fragmentTag Fragment标签
     * @param args        传入Fragment的参数
     * @return 提供fragment
     */
    protected BaseFragment fragmentProvider(String fragmentTag, Object... args) {
        return null;
    }

    // ===============================
    // TODO: 向应用栏添加项目
    // ===============================

    /**
     * 向 Activity 的选项菜单（并因此向应用栏）贡献菜单项。
     * 此方法能够收到调用，您必须在 onCreate() 期间调用 setHasOptionsMenu()。
     * 从片段添加到选项菜单的任何菜单项都将追加到现有菜单项
     *
     * @param menu     menu
     * @param inflater inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 选定菜单项时，片段收到回调
     *
     * @param item item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 在片段布局中注册一个视图来提供上下文菜单。
     *
     * @param view view
     */
    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    /**
     * 用户打开上下文菜单时，片段会回调此方法
     *
     * @param menu     menu
     * @param view     view
     * @param menuInfo menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    /**
     * 当用户选择某个菜单项时，片段会收到对 onContextItemSelected() 的调用
     *
     * @param item item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    // ===============================
    // TODO: 与活动交互
    // ===============================

    /**
     * 关于片段交互监听器
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     * <p>
     * 这个接口必须由包含这个片段的活动来实现，以允许在这个片段中的交互被传送给该活动中包含的活动和潜在的其他片段。
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String... args);
    }

    public void startActivity(Intent intent, int enterAnim, int exitAnim) {
        super.startActivity(intent);
        mActivity.overridePendingTransition(enterAnim, exitAnim);
    }

    public void startActivityForResult(Intent intent, int requestCode, int enterAnim, int exitAnim) {
        super.startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(enterAnim, exitAnim);
    }

    public void finishActivity() {
        finishActivity(BaseContainerFragmentActivity.RESULT_OK, null);
    }

    public void finishActivity(int resultCode) {
        finishActivity(resultCode, null);
    }

    public final void finishActivity(int resultCode, Intent intent) {
        if (null != mActivity) {
            mActivity.setResult(resultCode, intent);
            hideSoftInput();
            mActivity.finish();
            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public String getString(String text, String defaultValue) {
        return StringUtil.get(text, defaultValue);
    }

    public String getString(String text, @StringRes int defaultValueResId) {
        String defaultValue = getString(defaultValueResId);
        return getString(text, defaultValue);
    }

    public String getString(@StringRes int resId, @StringRes int defaultValueResId) {
        String text = getString(resId);
        return getString(text, defaultValueResId);
    }

    public String getString(@StringRes int resId, String defaultValue) {
        String text = getString(resId);
        return getString(text, defaultValue);
    }

    // ========================
    // TODO: 管理子Fragment
    // ========================

    protected void replaceSelf(Fragment fragment, String tag) {
        // 本级别管理者
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();
        hideSoftInput();
    }

    protected void popSelf() {
        FragmentManager manager = getFragmentManager();
        manager.popBackStack(null, 0);
        hideSoftInput();
    }

// ========================
    // TODO: 软键盘系列相关
    // ========================

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        KeyboardUtil.hideSoftInput(mActivity);
    }

    /**
     * 打开软键盘
     *
     * @param view View
     */
    public void showSoftInput(View view) {
        KeyboardUtil.showSoftInput(mActivity, view);
    }

    /**
     * 切换软键盘
     */
    public void toggleSoftInput() {
        KeyboardUtil.toggleSoftInput(mActivity);
    }

    // ========================
    // TODO: Toast系列方法
    // ========================

    protected void showToastShort(@StringRes int id) {
        ToastUtil.showShort(mContext, id);
    }

    protected void showToastLong(@StringRes int id) {
        ToastUtil.showLong(mContext, id);
    }

    protected void showToastShort(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    protected void showToastLong(String msg) {
        ToastUtil.showLong(mContext, msg);
    }

}