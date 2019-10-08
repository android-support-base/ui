package com.amlzq.android.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的片段适配器
 *
 * @reference https://stackoverflow.com/a/45059794
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTabIndicators = new ArrayList<>();
    private List<Fragment> mTabFragments = new ArrayList<>();

    public List<String> getIndicators() {
        return mTabIndicators;
    }

    public List<Fragment> getFragments() {
        return this.mTabFragments;
    }

    public Fragment getFragment(int position) {
        return mTabFragments.get(position);
    }

    public void addTab(int position, String indicator, Fragment fragment) {
        this.mTabIndicators.add(indicator);
        this.mTabFragments.add(fragment);
    }

    public void addTab(String indicator, Fragment fragment) {
        this.mTabIndicators.add(indicator);
        this.mTabFragments.add(fragment);
    }

    public SimpleFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    /**
     * @param container
     * @param position
     * @return 实例化的项
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public int getCount() {
        return mTabFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabIndicators.get(position);
    }

}