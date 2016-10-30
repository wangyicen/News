package com.feicui.edu.newsapp.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.fragment.ViewPagerFragment;

/**
 * Created by asus on 2016/10/30.
 */
public class BasePagerAdapter extends FragmentStatePagerAdapter {

    private int[] pics = {R.drawable.bd, R.drawable.small, R.drawable.welcome, R.drawable.wy};
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.initData(pics[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
