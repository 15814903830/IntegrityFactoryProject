package com.xsylsb.integrity.mianfragment.homepage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *  fragment和viewpager适配器
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> slist;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> list,List<String> slist) {
        super(fm);
        this.list=list;
        this.slist=slist;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return slist.get(position);
    }
}
