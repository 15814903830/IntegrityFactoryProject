package com.xsylsb.integrity.Examination_adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *  fragment和viewpager适配器
 */
public class MyExaminatioFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    public MyExaminatioFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
