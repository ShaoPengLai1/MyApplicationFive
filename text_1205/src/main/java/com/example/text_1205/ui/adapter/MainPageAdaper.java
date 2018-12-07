package com.example.text_1205.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.text_1205.ui.fragment.BusinessFragment;
import com.example.text_1205.ui.fragment.DataFragment;

public class MainPageAdaper extends FragmentPagerAdapter {
    private String[] page=new  String[]{"我的数据","我的名片"};
    public MainPageAdaper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new DataFragment();
            case 1:
                return new BusinessFragment();
                default:
                    return new Fragment();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return page[position];
    }

    @Override
    public int getCount() {
        return page.length;
    }
}
